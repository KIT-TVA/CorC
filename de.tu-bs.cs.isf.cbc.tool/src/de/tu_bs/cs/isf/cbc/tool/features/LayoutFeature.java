package de.tu_bs.cs.isf.cbc.tool.features;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.graph.CompoundDirectedGraph;
import org.eclipse.draw2d.graph.CompoundDirectedGraphLayout;
import org.eclipse.draw2d.graph.Edge;
import org.eclipse.draw2d.graph.EdgeList;
import org.eclipse.draw2d.graph.Node;
import org.eclipse.draw2d.graph.NodeList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.graphiti.datatypes.IDimension;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.MultiText;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.algorithms.styles.Font;
import org.eclipse.graphiti.mm.pictograms.AnchorContainer;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.ui.services.GraphitiUi;
import org.eclipse.graphiti.ui.services.IUiLayoutService;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.Rename;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SkipStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Variant;

/**
 * Maps the Graphiti Diagram to a graph structure which can be consumed by the
 * GEF Layouter, layouts the graph structure and maps the new coordinates back
 * to the diagram. Refresh is triggered automatically by the changes on the
 * diagram model.
 * 
 * Disclaimer: this is just an example to show how to plug an arbitrary layouter
 * into a Graphiti diagram editor. For instance, the basic layouting here does
 * not consider bendpoints etc.
 * 
 */
public class LayoutFeature extends AbstractCustomFeature {

	/**
	 * Minimal distance between nodes.
	 */
	private final int PADDING = 30;
	private final IUiLayoutService uiL = GraphitiUi.getUiLayoutService();
	private Font font;
	private final IFeatureProvider featureProvider = getFeatureProvider();
	private final IGaService gaService = Graphiti.getGaService();

	public LayoutFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getDescription() {
		return "Layout diagram with GEF Layouter";
	}

	@Override
	public String getName() {
		return "Layout Diagram";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		return true;
	}

	@Override
	public void execute(ICustomContext context) {
		font = GraphitiUi.getGaService().manageFont(getDiagram(), "Arial", 10, false, false);
		final CompoundDirectedGraph graph = mapDiagramToGraph();
		graph.setDefaultPadding(new Insets(PADDING));
		new CompoundDirectedGraphLayout().visit(graph);
		mapGraphCoordinatesToDiagram(graph);
	}

	private Diagram mapGraphCoordinatesToDiagram(CompoundDirectedGraph graph) {
		NodeList myNodes = new NodeList();
		myNodes.addAll(graph.nodes);
		myNodes.addAll(graph.subgraphs);
		for (Object object : myNodes) {
			Node node = (Node) object;
			Shape shape = (Shape) node.data;
			shape.getGraphicsAlgorithm().setX(node.x);
			shape.getGraphicsAlgorithm().setY(node.y);
			shape.getGraphicsAlgorithm().setWidth(node.width);
			shape.getGraphicsAlgorithm().setHeight(node.height);
			layoutPictogramElement(shape);
		}
		return null;
	}

	private CompoundDirectedGraph mapDiagramToGraph() {
		Map<AnchorContainer, Node> shapeToNode = new HashMap<AnchorContainer, Node>();
		Diagram d = getDiagram();
		CompoundDirectedGraph dg = new CompoundDirectedGraph();
		EdgeList edgeList = new EdgeList();
		NodeList nodeList = new NodeList();
		EList<Shape> children = d.getChildren();
		for (Shape shape : children) {
			Node node = new Node();
			GraphicsAlgorithm ga = shape.getGraphicsAlgorithm();
			resizeGraphicsAlgo(ga);
			node.x = ga.getX();
			node.y = ga.getY();
			node.width = ga.getWidth();
			node.height = ga.getHeight();
			node.data = shape;
			shapeToNode.put(shape, node);
			nodeList.add(node);
		}
		EList<Connection> connections = d.getConnections();
		for (Connection connection : connections) {
			AnchorContainer source = (AnchorContainer) connection.getStart().getParent().eContainer();
			AnchorContainer target = connection.getEnd().getParent();
			Edge edge = new Edge(shapeToNode.get(source), shapeToNode.get(target));
			edge.data = connection;
			edgeList.add(edge);
		}
		dg.nodes = nodeList;
		dg.edges = edgeList;
		return dg;
	}

	private void resizeGraphicsAlgo(GraphicsAlgorithm ga) {
		Object businessObject = getBusinessObjectForPictogramElement(ga.getPictogramElement());
		if (businessObject instanceof CbCFormula) {
			resizeFormula((CbCFormula) businessObject, ga);
		} else if (businessObject instanceof CompositionStatement) {
			resizeComposition((CompositionStatement) businessObject, ga);
		} else if (businessObject instanceof SelectionStatement) {
			resizeSelection((SelectionStatement) businessObject, ga);
		} else if (businessObject instanceof SmallRepetitionStatement) {
			resizeRepetition((SmallRepetitionStatement) businessObject, ga);
		} else if (businessObject instanceof JavaVariables) {
			resizeVariables((JavaVariables) businessObject, ga);
		} else if (businessObject instanceof GlobalConditions) {
			resizeConditions((GlobalConditions) businessObject, ga);
		} else if (businessObject instanceof Renaming) {
			resizeRenaming((Renaming) businessObject, ga);
		} else if (businessObject instanceof SkipStatement) {
			resizeSkip((SkipStatement) businessObject, ga);
		} else if (businessObject instanceof AbstractStatement) {
			resizeStatement((AbstractStatement) businessObject, ga);
		}
	}

	private void resizeFormula(CbCFormula businessObject, GraphicsAlgorithm ga) {
		Condition pre = businessObject.getStatement().getPreCondition();
		Condition post = businessObject.getStatement().getPostCondition();
		String newPre = setWordWraps(pre.getName());
		pre.setName(newPre);
		String newPost = setWordWraps(post.getName());
		post.setName(newPost);
		MultiText preText = (MultiText) featureProvider.getPictogramElementForBusinessObject(pre)
				.getGraphicsAlgorithm();
		MultiText postText = (MultiText) featureProvider.getPictogramElementForBusinessObject(post)
				.getGraphicsAlgorithm();
		preText.setValue(newPre);
		postText.setValue(newPost);
		IDimension sizePre = uiL.calculateTextSize(preText.getValue(), font, true);
		IDimension sizePost = uiL.calculateTextSize(postText.getValue(), font, true);
		int width = Math.max(sizePre.getWidth(), sizePost.getWidth());
		int height = Math.max(sizePre.getHeight(), sizePost.getHeight());
		gaService.setSize(ga, width * 3 + 10, height + 80);
	}

	private void resizeComposition(CompositionStatement businessObject, GraphicsAlgorithm ga) {
		Condition pre = businessObject.getFirstStatement().getPreCondition();
		Condition post = businessObject.getSecondStatement().getPostCondition();
		Condition interm = businessObject.getIntermediateCondition();
		String newPre = setWordWraps(pre.getName());
		pre.setName(newPre);
		String newPost = setWordWraps(post.getName());
		post.setName(newPost);
		String newInterm = setWordWraps(interm.getName());
		interm.setName(newInterm);
		MultiText preText = (MultiText) featureProvider.getPictogramElementForBusinessObject(pre)
				.getGraphicsAlgorithm();
		MultiText postText = (MultiText) featureProvider.getPictogramElementForBusinessObject(post)
				.getGraphicsAlgorithm();
		MultiText intermText = (MultiText) featureProvider.getPictogramElementForBusinessObject(interm)
				.getGraphicsAlgorithm();
		preText.setValue(newPre);
		postText.setValue(newPost);
		intermText.setValue(newInterm);
		IDimension sizePre = uiL.calculateTextSize(preText.getValue(), font, true);
		IDimension sizePost = uiL.calculateTextSize(postText.getValue(), font, true);
		IDimension sizeInterm = uiL.calculateTextSize(intermText.getValue(), font, true);
		int width = Math.max(Math.max(sizePre.getWidth() * 2, sizePost.getWidth() * 2), sizeInterm.getWidth() * 3);
		int height = Math.max(Math.max(sizePre.getHeight(), sizePost.getHeight()), sizeInterm.getHeight());
		gaService.setSize(ga, width + 10, height * 2 + 150);
	}

	private void resizeSelection(SelectionStatement businessObject, GraphicsAlgorithm ga) {
		int width = 0;
		int height = 0;
		int numberGuards = 0;
		for (int i = 0; i < businessObject.getCommands().size(); i++) {
			Condition pre = businessObject.getCommands().get(i).getPreCondition();
			Condition post = businessObject.getCommands().get(i).getPostCondition();
			Condition guard = businessObject.getGuards().get(i);
			String newPre = setWordWraps(pre.getName());
			pre.setName(newPre);
			String newPost = setWordWraps(post.getName());
			post.setName(newPost);
			String newGuard = setWordWraps(guard.getName());
			guard.setName(newGuard);
			MultiText preText = (MultiText) featureProvider.getPictogramElementForBusinessObject(pre)
					.getGraphicsAlgorithm();
			MultiText postText = (MultiText) featureProvider.getPictogramElementForBusinessObject(post)
					.getGraphicsAlgorithm();
			MultiText guardText = (MultiText) featureProvider.getPictogramElementForBusinessObject(guard)
					.getGraphicsAlgorithm();
			preText.setValue(newPre);
			postText.setValue(newPost);
			guardText.setValue(newGuard);
			IDimension sizePre = uiL.calculateTextSize(preText.getValue(), font, true);
			IDimension sizePost = uiL.calculateTextSize(postText.getValue(), font, true);
			IDimension sizeGuard = uiL.calculateTextSize(guardText.getValue(), font, true);
			width = Math.max(Math.max(width, sizePre.getWidth()), Math.max(sizePost.getWidth(), sizeGuard.getWidth()));
			height = Math.max(Math.max(height, sizePre.getHeight()),
					Math.max(sizePost.getHeight(), sizeGuard.getHeight()));
			numberGuards = i + 1;
		}
		gaService.setSize(ga, width * numberGuards + 40, height * 4 + 180);
	}

	private void resizeRepetition(SmallRepetitionStatement businessObject, GraphicsAlgorithm ga) {
		Condition pre = businessObject.getLoopStatement().getPreCondition();
		Condition post = businessObject.getLoopStatement().getPostCondition();
		Condition invariant = businessObject.getInvariant();
		Condition guard = businessObject.getGuard();
		Variant variant = businessObject.getVariant();
		String newPre = setWordWraps(pre.getName());
		pre.setName(newPre);
		String newPost = setWordWraps(post.getName());
		post.setName(newPost);
		String newInvariant = setWordWraps(invariant.getName());
		invariant.setName(newInvariant);
		String newGuard = setWordWraps(guard.getName());
		guard.setName(newGuard);
		String newVariant = setWordWraps(variant.getName());
		variant.setName(newVariant);
		MultiText preText = (MultiText) featureProvider.getPictogramElementForBusinessObject(pre)
				.getGraphicsAlgorithm();
		MultiText postText = (MultiText) featureProvider.getPictogramElementForBusinessObject(post)
				.getGraphicsAlgorithm();
		MultiText invariantText = (MultiText) featureProvider.getPictogramElementForBusinessObject(invariant)
				.getGraphicsAlgorithm();
		MultiText guardText = (MultiText) featureProvider.getPictogramElementForBusinessObject(guard)
				.getGraphicsAlgorithm();
		MultiText variantText = (MultiText) featureProvider.getPictogramElementForBusinessObject(variant)
				.getGraphicsAlgorithm();
		preText.setValue(newPre);
		postText.setValue(newPost);
		invariantText.setValue(newInvariant);
		guardText.setValue(newGuard);
		variantText.setValue(newVariant);
		IDimension sizePre = uiL.calculateTextSize(preText.getValue(), font, true);
		IDimension sizePost = uiL.calculateTextSize(postText.getValue(), font, true);
		IDimension sizeInvariant = uiL.calculateTextSize(invariantText.getValue(), font, true);
		IDimension sizeGuard = uiL.calculateTextSize(guardText.getValue(), font, true);
		IDimension sizeVariant = uiL.calculateTextSize(variantText.getValue(), font, true);
		int width = Collections.max(Arrays.asList(sizePre.getWidth(), sizePost.getWidth(), sizeInvariant.getWidth(),
				sizeGuard.getWidth(), sizeVariant.getWidth()));
		int height = Collections.max(Arrays.asList(sizePre.getHeight(), sizePost.getHeight(), sizeInvariant.getHeight(),
				sizeGuard.getHeight(), sizeVariant.getHeight()));
		gaService.setSize(ga, width * 3 + 50, height * 2 + 150);
	}

	private void resizeVariables(JavaVariables businessObject, GraphicsAlgorithm ga) {
		int width = 0;
		int height = 0;
		for (JavaVariable variable : businessObject.getVariables()) {
			Text varText = (Text) featureProvider.getPictogramElementForBusinessObject(variable).getGraphicsAlgorithm();
			IDimension sizeVar = uiL.calculateTextSize(varText.getValue(), font, true);
			width = Math.max(width, sizeVar.getWidth());
			height = Math.max(height, sizeVar.getHeight());
		}
		gaService.setSize(ga, width + 50, height * businessObject.getVariables().size() + 60);
	}

	private void resizeConditions(GlobalConditions businessObject, GraphicsAlgorithm ga) {
		int width = 0;
		int height = 0;
		for (Condition condition : businessObject.getConditions()) {
			MultiText conText = (MultiText) featureProvider.getPictogramElementForBusinessObject(condition)
					.getGraphicsAlgorithm();
			IDimension sizeCon = uiL.calculateTextSize(conText.getValue(), font, true);
			width = Math.max(width, sizeCon.getWidth());
			height = Math.max(height, sizeCon.getHeight());
		}
		gaService.setSize(ga, width + 50, height * businessObject.getConditions().size() + 60);
	}

	private void resizeRenaming(Renaming businessObject, GraphicsAlgorithm ga) {
		int width = 0;
		int height = 0;
		for (Rename rename : businessObject.getRename()) {
			for (PictogramElement pe : featureProvider.getAllPictogramElementsForBusinessObject(rename)) {
				if (pe.getGraphicsAlgorithm() instanceof Text) {
					Text text = (Text) pe.getGraphicsAlgorithm();
					IDimension sizeText = uiL.calculateTextSize(text.getValue(), font, true);
					width = Math.max(width, sizeText.getWidth());
					height = Math.max(height, sizeText.getHeight());
				}
			}
		}
		gaService.setSize(ga, width * 3 + 50, height * businessObject.getRename().size() + 100);
	}

	private void resizeSkip(SkipStatement businessObject, GraphicsAlgorithm ga) {
		Condition pre = businessObject.getPreCondition();
		Condition post = businessObject.getPostCondition();
		String newPre = setWordWraps(pre.getName());
		pre.setName(newPre);
		String newPost = setWordWraps(post.getName());
		post.setName(newPost);
		MultiText preText = (MultiText) featureProvider.getPictogramElementForBusinessObject(pre)
				.getGraphicsAlgorithm();
		MultiText postText = (MultiText) featureProvider.getPictogramElementForBusinessObject(post)
				.getGraphicsAlgorithm();
		preText.setValue(newPre);
		postText.setValue(newPost);
		IDimension sizePre = uiL.calculateTextSize(preText.getValue(), font, true);
		IDimension sizePost = uiL.calculateTextSize(postText.getValue(), font, true);
		int width = Math.max(sizePre.getWidth(), sizePost.getWidth());
		int height = Math.max(sizePre.getHeight(), sizePost.getHeight());
		gaService.setSize(ga, width * 2 + 50, height + 80);
	}

	private void resizeStatement(AbstractStatement businessObject, GraphicsAlgorithm ga) {
		Condition pre = businessObject.getPreCondition();
		Condition post = businessObject.getPostCondition();
		String newPre = setWordWraps(pre.getName());
		pre.setName(newPre);
		String newPost = setWordWraps(post.getName());
		post.setName(newPost);
		MultiText preText = (MultiText)
		featureProvider.getPictogramElementForBusinessObject(pre).getGraphicsAlgorithm();
		MultiText postText = (MultiText)
		featureProvider.getPictogramElementForBusinessObject(post).getGraphicsAlgorithm();
		MultiText statementText = null;
		for (PictogramElement pe : featureProvider.getAllPictogramElementsForBusinessObject(businessObject)) {
			if (pe.getGraphicsAlgorithm() instanceof MultiText) {
				statementText = (MultiText) pe.getGraphicsAlgorithm();
			}
		}
		preText.setValue(newPre);
		postText.setValue(newPost);
		IDimension sizePre = uiL.calculateTextSize(preText.getValue(), font, true);
		IDimension sizePost = uiL.calculateTextSize(postText.getValue(), font, true);
		IDimension sizeStatment = uiL.calculateTextSize(statementText.getValue(), font, true);
		int width = Math.max(Math.max(sizePre.getWidth(), sizePost.getWidth()), sizeStatment.getWidth());
		int height = Math.max(Math.max(sizePre.getHeight(), sizePost.getHeight()), sizeStatment.getHeight());
		gaService.setSize(ga, width * 3 + 10, height + 60);
	}

	/**
	 * adds suitable word wraps and deletes old word wraps
	 * 
	 * @param name pre or post condition
	 * @return name with additional word wraps
	 */
	private String setWordWraps(String name) {
		name = name.replace("\r\n", "");
		name = name.replace("\n", "");
		name = name.replace("\r", "");
		name = name.replace("  ", " ");
		int lineLength = 40;
		int checkedPart = lineLength;
		String lastPart = name;
		while (lastPart.length() > lineLength) {
			checkedPart = findBestCuttingPoint(name, checkedPart);
			if(checkedPart == -1) {
				break;
			}
			String firstPart = name.substring(0, checkedPart);
			lastPart = name.substring(checkedPart);
			firstPart = firstPart.concat("\r\n");
			name = firstPart + lastPart;
			checkedPart = firstPart.length() + lineLength;
		}
		return name;
	}

	/**
	 * looks for good split point(&, |, space)
	 * @param name
	 * @param checkedPart
	 * @return
	 */
	private int findBestCuttingPoint(String name, int checkedPart) {
		int cutIndex = name.indexOf("&", checkedPart - 8);
		if(cutIndex == -1 || cutIndex > checkedPart + 8) {
			cutIndex = name.indexOf("|", checkedPart - 8);
			if(cutIndex == -1 || cutIndex > checkedPart + 8) {
				cutIndex = name.indexOf(" ", checkedPart - 8);
			}
		}
		return cutIndex;
	}
}