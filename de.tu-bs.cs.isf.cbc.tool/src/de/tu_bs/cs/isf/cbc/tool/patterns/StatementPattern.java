package de.tu_bs.cs.isf.cbc.tool.patterns;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.context.IDeleteContext;
import org.eclipse.graphiti.features.context.IDirectEditingContext;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Image;
import org.eclipse.graphiti.mm.algorithms.MultiText;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.RoundedRectangle;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.algorithms.styles.Font;
import org.eclipse.graphiti.mm.algorithms.styles.Orientation;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.pattern.IPattern;
import org.eclipse.graphiti.pattern.id.IdLayoutContext;
import org.eclipse.graphiti.pattern.id.IdPattern;
import org.eclipse.graphiti.pattern.id.IdUpdateContext;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;
import org.eclipse.graphiti.services.IPeService;
import org.eclipse.graphiti.util.IColorConstant;
import org.eclipse.graphiti.util.PredefinedColoredAreas;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.parser.exceptions.IFbCException;
import de.tu_bs.cs.isf.cbc.tool.diagram.CbCImageProvider;
import de.tu_bs.cs.isf.cbc.tool.helper.GetProjectUtil;
import de.tu_bs.cs.isf.cbc.tool.helper.HighlightHelper;
import de.tu_bs.cs.isf.cbc.tool.helper.UpdateInformationFlow;
import de.tu_bs.cs.isf.cbc.tool.features.TestStatement;
import de.tu_bs.cs.isf.cbc.tool.helper.UpdateModifiableOfConditions;
import de.tu_bs.cs.isf.cbc.util.CompareMethodBodies;
import de.tu_bs.cs.isf.cbc.util.DiagramPartsExtractor;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.lattice.Lattice;
import de.tu_bs.cs.isf.lattice.Lattices;

/**
 * Class that creates the graphical representation of Conditions
 * 
 * @author Tobias
 *
 */
public class StatementPattern extends IdPattern implements IPattern {

	private static final String ID_NAME_TEXT = "statementText";
	private static final String ID_PRE_TEXT = "preText";
	private static final String ID_PRE_MOD = "preConditionModifiables";
	private static final String ID_POST_TEXT = "postText";
	private static final String ID_POST_MOD = "postConditionModifiables";
	private static final String ID_MAIN_RECTANGLE = "mainRectangle";
	private static final String ID_IMAGE_PROVEN = "imageproven";
	// Headers:
	private static final String ID_PRE_HEADER = "preHeader";
	private static final String ID_POST_HEADER = "postHeader";
	private static final String ID_NAME_HEADER = "statementHeader";
	// Separating lines:
	private static final String ID_HEADER_SEPARATER = "headerSeparater";
	private static final String ID_POST_SEP = "postSep";
	private static final String ID_PRE_SEP = "preSep";
	private static final String ID_HOR1_LINE = "hor1Line";
	private static final String ID_HOR2_LINE = "hor2Line";

	/**
	 * Constructor of the class
	 */
	public StatementPattern() {
		super();
	}

	@Override
	public String getCreateName() {
		return "Statement";
	}

	@Override
	public String getCreateDescription() {
		return "Create a Statement.";
	}

	@Override
	public boolean isMainBusinessObjectApplicable(Object mainBusinessObject) {
		return mainBusinessObject instanceof AbstractStatement;
	}

	@Override
	public boolean canCreate(ICreateContext context) {
		return context.getTargetContainer() instanceof Diagram;
	}

	@Override
	public Object[] create(ICreateContext context) {
		AbstractStatement statement = CbcmodelFactory.eINSTANCE.createAbstractStatement();
		statement.setName("Statement");
		Condition pre = CbcmodelFactory.eINSTANCE.createCondition();
		pre.setName("");
		statement.setPreCondition(pre);
		Condition post = CbcmodelFactory.eINSTANCE.createCondition();
		post.setName("");
		statement.setPostCondition(post);

		addGraphicalRepresentation(context, statement);
		return new Object[]{statement};
	}

	@Override
	public boolean canAdd(IAddContext context) {
		return super.canAdd(context) && context.getTargetContainer() instanceof Diagram;
	}

	@Override
	public PictogramElement doAdd(IAddContext context) {
		manageColor(IColorConstant.DARK_GREEN);
		manageColor(IColorConstant.LIGHT_ORANGE);

		Diagram targetDiagram = (Diagram) context.getTargetContainer();
		// System.out.println("contributorID:
		// "+targetDiagram.getDiagramTypeId()+".PropertyContributor");
		AbstractStatement addedStatement = (AbstractStatement) context.getNewObject();
		IPeCreateService peCreateService = Graphiti.getPeCreateService();
		IGaService gaService = Graphiti.getGaService();

		int width = context.getWidth() <= 0 ? 350 : context.getWidth();
		int height = context.getHeight() <= 0 ? 150 : context.getHeight();
		// Font:
		Font headerFont = gaService.manageFont(getDiagram(), "Arial", 9, false, true);

		// Main contents area
		ContainerShape outerContainerShape = peCreateService.createContainerShape(targetDiagram, true);
		RoundedRectangle mainRectangle = gaService.createRoundedRectangle(outerContainerShape, 20, 20);
		mainRectangle.setFilled(true);
		gaService.setRenderingStyle(mainRectangle, PredefinedColoredAreas.getBlueWhiteAdaptions());
		mainRectangle.setForeground(manageColor(IColorConstant.RED));
		mainRectangle.setLineWidth(2);
		setId(mainRectangle, ID_MAIN_RECTANGLE);
		gaService.setLocationAndSize(mainRectangle, context.getX(), context.getY(), width, height);

		// create link and wire it
		link(outerContainerShape, addedStatement);

		// Statement name
		Shape textShape = peCreateService.createShape(outerContainerShape, true);
		MultiText statementNameText = gaService.createMultiText(textShape, "");
		setId(statementNameText, ID_NAME_TEXT);
		statementNameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		statementNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);

		Shape preShape = peCreateService.createShape(outerContainerShape, false);
		MultiText preNameText = gaService.createMultiText(preShape,
				"{" + addedStatement.getPreCondition().getName() + "}");
		setId(preNameText, ID_PRE_TEXT);
		preNameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		preNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);

		Shape textShapePreConditionMod = peCreateService.createShape(outerContainerShape, true);
		MultiText preConditionTextMod = gaService.createMultiText(textShapePreConditionMod, "");
		setId(preConditionTextMod, ID_PRE_MOD);
		String modString = "";
		for (String s : addedStatement.getPreCondition().getModifiables()) {
			modString += s + ", ";
		}
		preConditionTextMod.setValue(
				"modifiable(" + (modString.equals("") ? "" : modString.substring(0, modString.length() - 2)) + ");");
		preConditionTextMod.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		preConditionTextMod.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);

		Shape postShape = peCreateService.createShape(outerContainerShape, false);
		MultiText postNameText = gaService.createMultiText(postShape,
				"{" + addedStatement.getPostCondition().getName() + "}");
		setId(postNameText, ID_POST_TEXT);
		postNameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		postNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);

		Shape textShapePostConditionMod = peCreateService.createShape(outerContainerShape, true);
		MultiText postConditionTextMod = gaService.createMultiText(textShapePostConditionMod, "");
		setId(postConditionTextMod, ID_POST_MOD);
		modString = "";
		for (String s : addedStatement.getPostCondition().getModifiables()) {
			modString += s + ", ";
		}
		postConditionTextMod.setValue(
				"modifiable(" + (modString.equals("") ? "" : modString.substring(0, modString.length() - 2)) + ");");
		postConditionTextMod.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		postConditionTextMod.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);

		Shape proveShape = peCreateService.createShape(outerContainerShape, false);
		Image image = gaService.createImage(proveShape, CbCImageProvider.IMG_UNPROVEN);
		setId(image, ID_IMAGE_PROVEN);

		// Header:---------------
		Shape textHeader = peCreateService.createShape(outerContainerShape, false);
		Text statementNameHeader = gaService.createText(textHeader, "statement");
		setId(statementNameHeader, ID_NAME_HEADER);
		statementNameHeader.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		statementNameHeader.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		statementNameHeader.setFont(headerFont);

		Shape preHeader = peCreateService.createShape(outerContainerShape, false);
		Text preNameHeader = gaService.createText(preHeader, "precondition");
		setId(preNameHeader, ID_PRE_HEADER);
		preNameHeader.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		preNameHeader.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		preNameHeader.setFont(headerFont);

		Shape postHeader = peCreateService.createShape(outerContainerShape, false);
		Text postNameHeader = gaService.createText(postHeader, "postcondition");
		setId(postNameHeader, ID_POST_HEADER);
		postNameHeader.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		postNameHeader.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		postNameHeader.setFont(headerFont);

		// Separator:
		Shape headerSepShape = peCreateService.createShape(outerContainerShape, false);
		Polyline headerPolyline = gaService.createPolyline(headerSepShape);
		setId(headerPolyline, ID_HEADER_SEPARATER);

		Shape preSepShape = peCreateService.createShape(outerContainerShape, false);
		Polyline prePolyline = gaService.createPolyline(preSepShape);
		setId(prePolyline, ID_PRE_SEP);

		Shape postSepShape = peCreateService.createShape(outerContainerShape, false);
		Polyline postPolyline = gaService.createPolyline(postSepShape);
		setId(postPolyline, ID_POST_SEP);

		Shape hor1LineShape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor1Polyline = gaService.createPolyline(hor1LineShape);
		setId(hor1Polyline, ID_HOR1_LINE);

		Shape hor2LineShape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor2Polyline = gaService.createPolyline(hor2LineShape);
		setId(hor2Polyline, ID_HOR2_LINE);

		peCreateService.createChopboxAnchor(outerContainerShape);
		peCreateService.createChopboxAnchor(textShape);

		link(outerContainerShape, addedStatement);
		link(textShape, addedStatement);
		link(preShape, addedStatement.getPreCondition());
		link(textShapePreConditionMod, addedStatement.getPreCondition());
		link(postShape, addedStatement.getPostCondition());
		link(textShapePostConditionMod, addedStatement.getPostCondition());
		link(proveShape, addedStatement);

		return outerContainerShape;
	}

	@Override
	protected boolean layout(IdLayoutContext context, String id) {
		boolean changesDone = false;

		GraphicsAlgorithm mainRectangle = context.getRootPictogramElement().getGraphicsAlgorithm();
		GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
		int third = mainRectangle.getWidth() / 3;
		int thirdBlockHeight = (mainRectangle.getHeight() - 40) / 3;

		if (id.equals(ID_NAME_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, third, 40, third, mainRectangle.getHeight() - 40);
			changesDone = true;
		} else if (id.equals(ID_PRE_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, thirdBlockHeight + 40, third, 2 * thirdBlockHeight);
			changesDone = true;
		} else if (id.equals(ID_PRE_MOD)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, 40, third, thirdBlockHeight);
			changesDone = true;
		} else if (id.equals(ID_POST_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, third * 2, thirdBlockHeight + 40, third,
					2 * thirdBlockHeight);
			changesDone = true;
		} else if (id.equals(ID_POST_MOD)) {
			Graphiti.getGaService().setLocationAndSize(ga, third * 2, 40, third, thirdBlockHeight);
			changesDone = true;
		} else if (id.equals(ID_IMAGE_PROVEN)) {
			Graphiti.getGaService().setLocationAndSize(ga, mainRectangle.getWidth() - 20, 10, 10, 10);
			changesDone = true;
			// Header:
		} else if (id.equals(ID_NAME_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, third, 20, third, 20); // mainrectangle anpassen
			changesDone = true;
		} else if (id.equals(ID_PRE_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, 20, third, 20);
			changesDone = true;
		} else if (id.equals(ID_POST_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, third * 2, 20, third, 20);
			changesDone = true;
			// LINES:
		} else if (id.equals(ID_HEADER_SEPARATER)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService()
					.createPointList(new int[]{0, 40, mainRectangle.getWidth(), 40});
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if (id.equals(ID_PRE_SEP)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService()
					.createPointList(new int[]{third * 2, 0, third * 2, mainRectangle.getHeight()});
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if (id.equals(ID_HOR1_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService()
					.createPointList(new int[]{0, thirdBlockHeight + 40, third, thirdBlockHeight + 40});
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if (id.equals(ID_POST_SEP)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService()
					.createPointList(new int[]{third, 0, third, mainRectangle.getHeight()});
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if (id.equals(ID_HOR2_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[]{2 * third, thirdBlockHeight + 40, mainRectangle.getWidth(), thirdBlockHeight + 40});
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		}

		return changesDone;
	}

	@Override
	protected IReason updateNeeded(IdUpdateContext context, String id) {
		if (context.getGraphicsAlgorithm() instanceof MultiText
				&& context.getDomainObject() instanceof AbstractStatement) {
			MultiText nameText = (MultiText) context.getGraphicsAlgorithm();
			AbstractStatement domainObject = (AbstractStatement) context.getDomainObject();
			if (domainObject.getName() == null || !domainObject.getName().equals(nameText.getValue())) {
				return Reason.createTrueReason("Name differs. Expected: '" + domainObject.getName() + "'");
			}
		} else if (id.equals(ID_MAIN_RECTANGLE)) {
			RoundedRectangle rectangle = (RoundedRectangle) context.getGraphicsAlgorithm();
			AbstractStatement domainObject = (AbstractStatement) context.getDomainObject();
			if (domainObject.isProven() && ((rectangle.getForeground() != null
					&& !rectangle.getForeground().equals(manageColor(IColorConstant.DARK_GREEN)))
					|| rectangle.getForeground() == null)) {
				return Reason.createTrueReason("Statement is proven. Expected green color.");
			} else if (!domainObject.isProven() && domainObject.isTested()
					&& ((rectangle.getForeground() != null
							&& !rectangle.getForeground().equals(manageColor(IColorConstant.LIGHT_ORANGE)))
							|| rectangle.getForeground() == null)) {
				return Reason.createTrueReason("Statement is tested. Expected orange color.");
			} else if (!domainObject.isProven() && !domainObject.isTested()
					&& ((rectangle.getForeground() != null
							&& (rectangle.getForeground().equals(manageColor(IColorConstant.DARK_GREEN))
									|| rectangle.getForeground().equals(manageColor(IColorConstant.LIGHT_ORANGE))))
							|| rectangle.getForeground() == null)) {
				return Reason.createTrueReason("Statement is not proven. Expected red color.");
			}
		} else if (id.equals(ID_IMAGE_PROVEN)) {
			AbstractStatement domainObject = (AbstractStatement) context.getDomainObject();
			Image image = (Image) context.getGraphicsAlgorithm();
			if (domainObject.isProven() && image.getId().equals(CbCImageProvider.IMG_UNPROVEN)) {
				return Reason.createTrueReason("Statement is proven. Expected green color.");
			} else if (!domainObject.isProven() && image.getId().equals(CbCImageProvider.IMG_PROVEN)) {
				return Reason.createTrueReason("Statement is not proven. Expected red color.");
			}
		}

		if (HighlightHelper.instance.needsInitialHighlightUpdate(context)) {
			return Reason.createTrueReason("Element needs to be highlighted.");
		}

		return Reason.createFalseReason();
	}

	@Override
	protected boolean update(IdUpdateContext context, String id) {
		if (context.getGraphicsAlgorithm() instanceof MultiText
				&& context.getDomainObject() instanceof AbstractStatement) {
			MultiText nameText = (MultiText) context.getGraphicsAlgorithm();
			AbstractStatement domainObject = (AbstractStatement) context.getDomainObject();
			nameText.setValue(domainObject.getName());
			return true;
		} else if (id.equals(ID_MAIN_RECTANGLE)) {
			RoundedRectangle rectangle = (RoundedRectangle) context.getGraphicsAlgorithm();
			AbstractStatement domainObject = (AbstractStatement) context.getDomainObject();
			if (domainObject.isProven()) {
				rectangle.setForeground(manageColor(IColorConstant.DARK_GREEN));
				if (domainObject.getParent() != null) {
					IPeService pe = Graphiti.getPeService();
					EObject[] objArray = {domainObject.getParent()};
					Object[] obj = pe.getLinkedPictogramElements(objArray, getDiagram());
					Shape pElement = (Shape) obj[0];
					if (pElement.getContainer() != null)
						updatePictogramElement(pElement.getContainer());
				}
			} else if (domainObject.isTested()) {
				rectangle.setForeground(manageColor(IColorConstant.LIGHT_ORANGE));
				if (domainObject.getParent() != null) {
					IPeService pe = Graphiti.getPeService();
					EObject[] objArray = {domainObject.getParent()};
					Object[] obj = pe.getLinkedPictogramElements(objArray, getDiagram());
					Shape pElement = (Shape) obj[0];
					if (pElement.getContainer() != null)
						updatePictogramElement(pElement.getContainer());
				}
			} else {
				rectangle.setForeground(manageColor(IColorConstant.RED));
				if (domainObject.getParent() != null) {
					IPeService pe = Graphiti.getPeService();
					EObject[] objArray = {domainObject.getParent()};
					Object[] obj = pe.getLinkedPictogramElements(objArray, getDiagram());
					if (obj.length > 0) {
						Shape pElement = (Shape) obj[0];
						if (pElement.getContainer() != null)
							updatePictogramElement(pElement.getContainer());
					}
				}
			}

			HighlightHelper.instance.handleHighlightDrawing(context, rectangle);

			updateParent(domainObject);
			return true;
		} else if (id.equals(ID_IMAGE_PROVEN)) {
			AbstractStatement domainObject = (AbstractStatement) context.getDomainObject();
			Image image = (Image) context.getGraphicsAlgorithm();
			if (domainObject.isProven()) {
				image.setId(CbCImageProvider.IMG_PROVEN);
			} else {
				image.setId(CbCImageProvider.IMG_UNPROVEN);
			}
		}
		return false;
	}

	private void updateParent(AbstractStatement statement) {
		if (statement.getParent() != null) {
			IPeService pe = Graphiti.getPeService();
			EObject[] objArray = {statement.getParent()};
			Object[] obj = pe.getLinkedPictogramElements(objArray, getDiagram());
			if (obj.length > 0) {
				Shape pElement = (Shape) obj[0];
				if (pElement.getContainer() != null)
					updatePictogramElement(pElement.getContainer());
			}
		}
	}

	@Override
	public int getEditingType() {
		return TYPE_MULTILINETEXT;
	}

	@Override
	public boolean canDirectEdit(IDirectEditingContext context) {
		Object domainObject = getBusinessObjectForPictogramElement(context.getPictogramElement());
		GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
		if (domainObject instanceof AbstractStatement && ga instanceof MultiText) {
			return true;
		}
		return false;
	}

	@Override
	public String getInitialValue(IDirectEditingContext context) {
		AbstractStatement statement = (AbstractStatement) getBusinessObjectForPictogramElement(
				context.getPictogramElement());
		return System.getProperty("line.separator") + statement.getName() + System.getProperty("line.separator");
	}

	@Override
	public String checkValueValid(String value, IDirectEditingContext context) {
		if (value == null || value.length() == 0) {
			return "Statement must not be empty";
		}
		if (Pattern.matches(".*original[(].*[)].*", value) == true) {
			return "Please use an Original-Call Statement for an original-call";
		} else if (Pattern.matches(".*[a-zA-Z0-9]+[(].*[)].*", value) == true) {
			return "Please use an Method-Call Statement for an method-call";
		}
		if (value.contains(";") && !CompareMethodBodies.readAndTestMethodBodyWithJaMoPP2(value)) {
			return "Statement has not the correct syntax.";
		}
		return null;
	}

	@Override
	public void setValue(String value, IDirectEditingContext context) {
		AbstractStatement statement = (AbstractStatement) getBusinessObjectForPictogramElement(
				context.getPictogramElement());
		statement.setName(value.trim());
		statement.setProven(false);
		statement.setTested(false);
		TestStatement.setPathTested(statement, false);
		updateParent(statement);

		DiagramPartsExtractor extractor = new DiagramPartsExtractor(getDiagram());
		JavaVariables vars = extractor.getVars();
		UpdateModifiableOfConditions.setVars(vars);
		UpdateModifiableOfConditions.updateAssignmentStatement(statement,
				new FileUtil(getDiagram().eResource().getURI().toPlatformString(true)));

		// Start of IFbC
		final IProject project = GetProjectUtil.getProjectForDiagram(getDiagram());
		final Lattice lattice = Lattices.getLatticeForProject(project);
		if (lattice != null) {
			try {
				UpdateInformationFlow.updateInformationFlow(project.getName(), statement, lattice);
			} catch (IFbCException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		updatePictogramElement(context.getPictogramElement());
	}

	@Override
	public void delete(IDeleteContext context) {
		Shape shape = (Shape) context.getPictogramElement();
		ContainerShape container = shape.getContainer();
		AbstractStatement statement = (AbstractStatement) getBusinessObjectForPictogramElement(
				context.getPictogramElement());
		if (statement != null && statement.eContainer() != null
				&& statement.eContainer() instanceof SelectionStatement) {
			int indexToDelete = getIndex(shape.getGraphicsAlgorithm());
			SelectionStatement selectionStatement = (SelectionStatement) statement.eContainer();
			int indexInSelSt = selectionStatement.getCommands().indexOf(statement);
			selectionStatement.getGuards().remove(indexInSelSt);
			super.delete(context);
			List<Shape> shapesToDelete = new ArrayList<Shape>();
			for (Shape childShape : container.getChildren()) {
				if (getIndex(childShape.getGraphicsAlgorithm()) == indexToDelete) {
					shapesToDelete.add(childShape);
				}
			}
			for (Shape deleteShape : shapesToDelete) {
				EcoreUtil.delete(deleteShape, true);
			}

			for (Shape childShape : container.getChildren()) {
				if (getIndex(childShape.getGraphicsAlgorithm()) > indexToDelete) {
					setIndex(childShape.getGraphicsAlgorithm(), getIndex(childShape.getGraphicsAlgorithm()) - 1);
				}
			}
			layoutPictogramElement(container);
		} else {
			super.delete(context);
		}
	}
}
