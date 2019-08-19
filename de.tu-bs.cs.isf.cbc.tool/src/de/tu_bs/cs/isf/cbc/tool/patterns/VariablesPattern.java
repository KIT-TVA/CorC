package de.tu_bs.cs.isf.cbc.tool.patterns;

import java.io.IOException;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
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
import org.eclipse.graphiti.util.PredefinedColoredAreas;

import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.tool.model.CbcModelUtil;

/**
 * Class that creates the graphical representation of Variables
 * @author Tobias
 *
 */
public class VariablesPattern extends IdPattern implements IPattern {
	
	private static final String ID_NAME_TEXT = "RenamingName";
	private static final String ID_VARIABLES_TYPE = "variableType";
	private static final String ID_VARIABLES_CONF = "variableConf";
	private static final String ID_VARIABLES_NAME = "variableName";
	private static final String ID_MAIN_RECTANGLE = "mainRectangle";
	//Header:
	private static final String ID_VARIABLES_CONF_HEADER = "confHeader";
	private static final String ID_VARIABLES_TYPE_HEADER = "typeHeader";
	private static final String ID_VARIABLES_NAME_HEADER = "nameHeader"; //the name that is used in the diagram
	//lines:
	private static final String ID_HOR1_LINE = "hor1Header";
	private static final String ID_HOR2_LINE = "hor2Header";
	private static final String ID_VER1_LINE = "ver1Header";
	private static final String ID_VER2_LINE = "ver2Header";

	/**
	 * Constructor of the class
	 */
	public VariablesPattern() {
		super();
	}
	
	@Override
	public String getCreateName() {
		return "Variables";
	}
	
	@Override
	public String getCreateDescription() {
		return "Create a list of variables.";
	}

	@Override
	public boolean isMainBusinessObjectApplicable(Object mainBusinessObject) {
		return mainBusinessObject instanceof JavaVariables;
	}

	@Override
	public boolean canCreate(ICreateContext context) {
		JavaVariables vars = null;
		for (Shape shape : getDiagram().getChildren()) {
			Object obj = getBusinessObjectForPictogramElement(shape);
			if (obj instanceof JavaVariables) {
				vars = (JavaVariables) obj;
			}
		}
		if (vars != null) return false;
		return context.getTargetContainer() instanceof Diagram;
	}
	
	@Override
	public Object[] create(ICreateContext context) {
		JavaVariables variables = CbcmodelFactory.eINSTANCE.createJavaVariables();
		JavaVariable variable = CbcmodelFactory.eINSTANCE.createJavaVariable();
		variable.setName("a");
		variable.setType("int");
		variable.setConfidentiality("low");
		variables.getVariables().add(variable);
		
		try {
			CbcModelUtil.saveVariablesToModelFile(variables, getDiagram());
		} catch (CoreException | IOException e) {
			e.printStackTrace();
		}
		
		addGraphicalRepresentation(context, variables);
		return new Object[] { variables };
	}

	@Override
	public boolean canAdd(IAddContext context) {
		return super.canAdd(context) && context.getTargetContainer() instanceof Diagram;
	}

	@Override
	public PictogramElement doAdd(IAddContext context) {
		
		Diagram targetDiagram = (Diagram) context.getTargetContainer();
		JavaVariables addedVariables = (JavaVariables) context.getNewObject();
		IPeCreateService peCreateService = Graphiti.getPeCreateService();
		IGaService gaService = Graphiti.getGaService();

		int width = context.getWidth() <= 0 ? 400 : context.getWidth();
        int height = context.getHeight() <= 0 ? 150 : context.getHeight();
        
        Font headerFont = gaService.manageFont(getDiagram(), "Arial", 9, false, true);
        
		// Main contents area
		ContainerShape outerContainerShape = peCreateService.createContainerShape(targetDiagram, true);
		RoundedRectangle mainRectangle = gaService.createRoundedRectangle(outerContainerShape, 20, 20);
		mainRectangle.setFilled(true);
		gaService.setRenderingStyle(mainRectangle, PredefinedColoredAreas.getBlueWhiteAdaptions());
		setId(mainRectangle, ID_MAIN_RECTANGLE);
		gaService.setLocationAndSize(mainRectangle,
	            context.getX(), context.getY(), width, height);

        // create link and wire it
        link(outerContainerShape, addedVariables);

		Shape nameTextShape = peCreateService.createShape(outerContainerShape, false);
		Text variablesNameText = gaService.createText(nameTextShape, "Variables");
		setId(variablesNameText, ID_NAME_TEXT);
		variablesNameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		variablesNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		variablesNameText.setFont(headerFont);
		
		//header:
		Shape confHeaderShape = peCreateService.createShape(outerContainerShape, false);
		Text confHeader = gaService.createText(confHeaderShape, "Confidentiality");
		setId(confHeader, ID_VARIABLES_CONF_HEADER);
		confHeader.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		confHeader.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);	
		confHeader.setFont(headerFont);
		
		Shape typeHeaderShape = peCreateService.createShape(outerContainerShape, false);
		Text typeHeader = gaService.createText(typeHeaderShape, "Type");
		setId(typeHeader, ID_VARIABLES_TYPE_HEADER);
		typeHeader.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		typeHeader.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);	
		typeHeader.setFont(headerFont);
		
		Shape nameHeaderShape = peCreateService.createShape(outerContainerShape, false);
		Text nameHeader = gaService.createText(nameHeaderShape, "Name");
		setId(nameHeader, ID_VARIABLES_NAME_HEADER);
		nameHeader.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		nameHeader.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);	
		nameHeader.setFont(headerFont);
		
		//lines:	
		Shape hor1Shape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor1line = gaService.createPolyline(hor1Shape);
		setId(hor1line, ID_HOR1_LINE);
		
		Shape hor2Shape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor2line = gaService.createPolyline(hor2Shape);
		setId(hor2line, ID_HOR2_LINE);
		
		Shape ver1Shape = peCreateService.createShape(outerContainerShape, false);
		Polyline ver1line = Graphiti.getGaService().createPolyline(ver1Shape);
		setId(ver1line, ID_VER1_LINE);
		
		Shape ver2Shape = peCreateService.createShape(outerContainerShape, false);
		Polyline ver2line = Graphiti.getGaService().createPolyline(ver2Shape);
		setId(ver2line, ID_VER2_LINE);
		
		link(outerContainerShape, addedVariables);
		link(nameTextShape, addedVariables);

		return outerContainerShape;
	}

	@Override
	protected boolean layout(IdLayoutContext context, String id) {
		boolean changesDone = false;
		
		GraphicsAlgorithm mainRectangle = context.getRootPictogramElement().getGraphicsAlgorithm();
		JavaVariables variables = (JavaVariables) getBusinessObjectForPictogramElement(context.getRootPictogramElement());
		GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
		int height = mainRectangle.getHeight();
		if (variables.getVariables().size() >= 1) {
			height = height / (variables.getVariables().size() + 2);
		}
		int width = mainRectangle.getWidth() / 3;
		
		
		if (id.equals(ID_NAME_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, 0, mainRectangle.getWidth(), height);
			changesDone = true;
		} else if (id.equals(ID_VARIABLES_TYPE)) {
			int index = getIndex(context.getGraphicsAlgorithm());
			Graphiti.getGaService().setLocationAndSize(ga, width, height * (index + 2), width, height);
			changesDone = true;
		} else if (id.equals(ID_VARIABLES_CONF)) {
			int index = getIndex(context.getGraphicsAlgorithm());
			Graphiti.getGaService().setLocationAndSize(ga, 0, height * (index + 2), width, height);
			changesDone = true;
		} else if (id.equals(ID_VARIABLES_NAME)) {
			int index = getIndex(context.getGraphicsAlgorithm());
			Graphiti.getGaService().setLocationAndSize(ga, width * 2, height * (index + 2), width, height);
			changesDone = true;
		} else if (id.equals(ID_VARIABLES_CONF_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, height, width, height);
			changesDone = true;
		} else if (id.equals(ID_VARIABLES_TYPE_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, width, height, width, height);
			changesDone = true;
		} else if (id.equals(ID_VARIABLES_NAME_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, width * 2, height, width, height);
			changesDone = true;
		} else if (id.equals(ID_HOR1_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { 0, height, mainRectangle.getWidth(), height});
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if (id.equals(ID_HOR2_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { 0, height * 2, mainRectangle.getWidth(), height * 2 });
			polyline.getPoints().addAll(pointList);
			changesDone = true;	
		} else if (id.equals(ID_VER1_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { width, height, width, mainRectangle.getHeight() });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if (id.equals(ID_VER2_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { width * 2, height, width * 2, mainRectangle.getHeight() });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		}

		return changesDone;
	}
	
	@Override
	protected IReason updateNeeded(IdUpdateContext context, String id) {
		if (id.equals(ID_MAIN_RECTANGLE)) {
			ContainerShape containerShape = (ContainerShape) context.getPictogramElement();
			JavaVariables variables = (JavaVariables) context.getDomainObject();
			if (containerShape.getChildren().size() - 8 != variables.getVariables().size() * 3) {
				return Reason.createTrueReason("Number of variables differ. Expected: " + variables.getVariables().size() 
						+ " " + (containerShape.getChildren().size() - 8));
			}
		}
		return Reason.createFalseReason();
	}

	@Override
	protected boolean update(IdUpdateContext context, String id) {
		if (id.equals(ID_MAIN_RECTANGLE)) {
			EList<JavaVariable> variables = ((JavaVariables) context.getDomainObject()).getVariables();
			while ((((ContainerShape) context.getPictogramElement()).getChildren().size() - 8) / 3 < variables.size()) {
				int newIndex = (((ContainerShape) context.getPictogramElement()).getChildren().size() - 8) / 3;
				JavaVariable variable = variables.get(newIndex);
				Shape shapeText = Graphiti.getPeCreateService().createShape((ContainerShape) context.getPictogramElement(), true);
				Text variableNameText = Graphiti.getGaService().createText(shapeText, variable.getName());
				setId(variableNameText, ID_VARIABLES_NAME);
				setIndex(variableNameText, newIndex);
				variableNameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
				variableNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
				link(shapeText, variable);
				
				Shape shapeTypeText = Graphiti.getPeCreateService().createShape((ContainerShape) context.getPictogramElement(), true);
				Text variableTypeText = Graphiti.getGaService().createText(shapeTypeText, variable.getType());
				setId(variableTypeText, ID_VARIABLES_TYPE);
				setIndex(variableTypeText, newIndex);
				variableTypeText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
				variableTypeText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
				link(shapeTypeText, variable);
				
				Shape shapeConfText = Graphiti.getPeCreateService().createShape((ContainerShape) context.getPictogramElement(), true);
				Text variableConfText = Graphiti.getGaService().createText(shapeConfText, variable.getConfidentiality().toLowerCase());
				setId(variableConfText, ID_VARIABLES_CONF);
				setIndex(variableConfText, newIndex);
				variableConfText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
				variableConfText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
				link(shapeConfText, variable);
			}
			return true;
		}
		return false;
	}
}