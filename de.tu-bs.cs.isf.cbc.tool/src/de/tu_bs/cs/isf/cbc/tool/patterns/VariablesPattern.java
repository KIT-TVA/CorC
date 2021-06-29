package de.tu_bs.cs.isf.cbc.tool.patterns;

import java.io.IOException;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
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

import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.CbcclassPackage;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.VariableKind;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbCFormulaImpl;
import de.tu_bs.cs.isf.cbc.tool.model.CbcModelUtil;

/**
 * Class that creates the graphical representation of Methods
 * 
 * @author Tobias
 *
 */
public class VariablesPattern extends IdPattern implements IPattern {

	private static final String ID_NAME_TEXT = "variablesName";
	private static final String ID_VARIABLE_TEXT = "variable";
	private static final String ID_MAIN_RECTANGLE = "mainRectangle";
	// lines:
	private static final String ID_HOR1_LINE = "hor1Line";

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
		if (vars != null)
			return false;
		return context.getTargetContainer() instanceof Diagram;
	}

	@Override
	public Object[] create(ICreateContext context) {
		JavaVariables variables = CbcmodelFactory.eINSTANCE.createJavaVariables();
		JavaVariable variable = CbcmodelFactory.eINSTANCE.createJavaVariable();
		variable.setName("int a");
		variable.setKind(VariableKind.LOCAL);
		variables.getVariables().add(variable);

		try {
			Resource resource = CbcModelUtil.getResource(getDiagram());
			for(EObject c:resource.getContents()){
				if(c instanceof CbCFormulaImpl) {
					CbCFormula formula = (CbCFormula) c;
					if(formula.getMethodObj()!= null) {
						EList<Field> fields = formula.getMethodObj().getParentClass().getFields();
						variables.eSet(CbcmodelPackage.eINSTANCE.getJavaVariables_Fields(), fields);
					}
				}
			}
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

		int width = context.getWidth() <= 0 ? 200 : context.getWidth();
		int height = context.getHeight() <= 0 ? 100 : context.getHeight();

		Font headerFont = gaService.manageFont(getDiagram(), "Arial", 9, false, true);

		// Main contents area
		ContainerShape outerContainerShape = peCreateService.createContainerShape(targetDiagram, true);
		RoundedRectangle mainRectangle = gaService.createRoundedRectangle(outerContainerShape, 20, 20);
		mainRectangle.setFilled(true);
		gaService.setRenderingStyle(mainRectangle, PredefinedColoredAreas.getBlueWhiteAdaptions());
		setId(mainRectangle, ID_MAIN_RECTANGLE);
		gaService.setLocationAndSize(mainRectangle, context.getX(), context.getY(), width, height);

		// create link and wire it
		link(outerContainerShape, addedVariables);

		// method name
		Shape nameTextShape = peCreateService.createShape(outerContainerShape, false);
		Text variablesNameText = gaService.createText(nameTextShape, "Variables");
		setId(variablesNameText, ID_NAME_TEXT);
		variablesNameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		variablesNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		variablesNameText.setFont(headerFont);

		// line:
		Shape hor1Shape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor1line = gaService.createPolyline(hor1Shape);
		setId(hor1line, ID_HOR1_LINE);

		link(outerContainerShape, addedVariables);
		link(nameTextShape, addedVariables);

		return outerContainerShape;
	}

	@Override
	protected boolean layout(IdLayoutContext context, String id) {
		boolean changesDone = false;

		GraphicsAlgorithm mainRectangle = context.getRootPictogramElement().getGraphicsAlgorithm();
		JavaVariables variables = (JavaVariables) getBusinessObjectForPictogramElement(
				context.getRootPictogramElement());
		int size =variables.getVariables().size() + variables.getFields().size();
		GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
		int height = mainRectangle.getHeight();
		if (size >= 1) {
			height = height / (size + 1);
		}

		if (id.equals(ID_NAME_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, 0, mainRectangle.getWidth(), height);
			changesDone = true;
		} else if (id.equals(ID_VARIABLE_TEXT)) {
			int index = getIndex(context.getGraphicsAlgorithm());
			Graphiti.getGaService().setLocationAndSize(ga, 0, height * (index + 1), mainRectangle.getWidth(), height);
			changesDone = true;
		} else if (id.equals(ID_HOR1_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService()
					.createPointList(new int[] { 0, height, mainRectangle.getWidth(), height });
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
			int size = variables.getVariables().size() + variables.getFields().size();
			if (containerShape.getChildren().size() - 2 != size) {
				return Reason.createTrueReason("Number of Variables differ. Expected: "
						+ size + " " + (containerShape.getChildren().size() - 2));
			}
		}
		return Reason.createFalseReason();
	}

	@Override
	protected boolean update(IdUpdateContext context, String id) {
		if (id.equals(ID_MAIN_RECTANGLE)) {
			EList<JavaVariable> variables = ((JavaVariables) context.getDomainObject()).getVariables();
			EList<Field> fields = ((JavaVariables) context.getDomainObject()).getFields();
			int size = variables.size() + fields.size();
			while (((ContainerShape) context.getPictogramElement()).getChildren().size() - 2 < size) {
				int newIndex = ((ContainerShape) context.getPictogramElement()).getChildren().size() - 2;
				if(newIndex < fields.size()) {
					Field field = fields.get(newIndex);
					Shape shapeText = Graphiti.getPeCreateService()
							.createShape((ContainerShape) context.getPictogramElement(), true);
					Text variableNameText = Graphiti.getGaService().createText(shapeText, field.getDisplayedName());
					setId(variableNameText, ID_VARIABLE_TEXT);
					setIndex(variableNameText, newIndex);
					variableNameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
					variableNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
					link(shapeText, field);
				}else {
					JavaVariable variable = variables.get(newIndex - fields.size());
					Shape shapeText = Graphiti.getPeCreateService()
						.createShape((ContainerShape) context.getPictogramElement(), true);
					Text variableNameText = Graphiti.getGaService().createText(shapeText, variable.getDisplayedName());
					setId(variableNameText, ID_VARIABLE_TEXT);
					setIndex(variableNameText, newIndex);
					variableNameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
					variableNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
					link(shapeText, variable);
				}
			}
			return true;
		}
		return false;
	}
}
