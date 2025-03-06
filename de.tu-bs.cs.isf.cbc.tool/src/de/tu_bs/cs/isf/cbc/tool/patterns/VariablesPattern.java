package de.tu_bs.cs.isf.cbc.tool.patterns;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
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

import de.tu_bs.cs.isf.cbc.cbcclass.Field;
import de.tu_bs.cs.isf.cbc.cbcclass.Method;
import de.tu_bs.cs.isf.cbc.cbcclass.ModelClass;
import de.tu_bs.cs.isf.cbc.cbcclass.Parameter;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.VariableKind;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbCFormulaImpl;
import de.tu_bs.cs.isf.cbc.util.CbcModelUtil;
import de.tu_bs.cs.isf.cbc.util.ClassUtil;
import de.tu_bs.cs.isf.cbc.util.DiagramPartsExtractor;
import de.tu_bs.cs.isf.cbc.util.FeatureUtil;
import de.tu_bs.cs.isf.cbc.util.FileUtil;

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
		DiagramPartsExtractor extractor = new DiagramPartsExtractor(getDiagram());
		JavaVariables vars = extractor.getVars();
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
			for (EObject c : resource.getContents()) {
				if (c instanceof CbCFormulaImpl) {
					CbCFormula formula = (CbCFormula) c;
					if (formula.getMethodObj() != null) {
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
		return new Object[]{variables};
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
		EList<Field> inheritedFields = null;
		if (getDiagram().eResource().getURI().isPlatform()) {
			String featureName = FeatureUtil.getInstance().getCallingFeature(
					((JavaVariables) getBusinessObjectForPictogramElement(context.getRootPictogramElement()))
							.eResource().getURI());
			String className = FeatureUtil.getInstance().getCallingClass(
					((JavaVariables) getBusinessObjectForPictogramElement(context.getRootPictogramElement()))
							.eResource().getURI());
			Resource classResource = ClassUtil.getClassModelResource(
					FileUtil.getProjectLocation(
							getDiagram().eResource().getURI().trimFileExtension().appendFileExtension("cbcmodel")),
					className, featureName);
			if (classResource != null && classResource.getContents().size() > 0
					&& ((ModelClass) classResource.getContents().get(0)).getInheritsFrom() != null) {
				inheritedFields = ((ModelClass) classResource.getContents().get(0)).getInheritsFrom().getFields();
			}
		}
		int size = variables.getVariables().size() + (variables.getFields() != null ? variables.getFields().size() : 0)
				+ (inheritedFields != null ? inheritedFields.size() : 0)
				+ (variables.getParams() != null ? variables.getParams().size() : 0);
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
					.createPointList(new int[]{0, height, mainRectangle.getWidth(), height});
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		}
		return changesDone;
	}

	@Override
	protected IReason updateNeeded(IdUpdateContext context, String id) {
		if (id.equals(ID_MAIN_RECTANGLE)) {
			ContainerShape containerShape = (ContainerShape) context.getPictogramElement();
			EList<JavaVariable> variables = ((JavaVariables) context.getDomainObject()).getVariables();
			EList<Field> fields = ((JavaVariables) context.getDomainObject()).getFields();
			EList<Parameter> params = ((JavaVariables) context.getDomainObject()).getParams();
			EList<Field> inheritedFields = null;
			String className = FeatureUtil.getInstance()
					.getCallingClass(((JavaVariables) context.getDomainObject()).eResource().getURI());
			String featureName = FeatureUtil.getInstance()
					.getCallingFeature(((JavaVariables) context.getDomainObject()).eResource().getURI());
			String methodName = getDiagram().eResource().getURI().trimFileExtension()
					.segment(getDiagram().eResource().getURI().segmentCount() - 1);
			if (getDiagram().eResource().getURI().isPlatform()) {
				Resource classResource = ClassUtil.getClassModelResource(
						FileUtil.getProjectLocation(
								getDiagram().eResource().getURI().trimFileExtension().appendFileExtension("cbcmodel")),
						className, featureName);
				if (classResource != null && classResource.getContents().size() > 0
						&& ((ModelClass) classResource.getContents().get(0)).getInheritsFrom() != null) {
					inheritedFields = ((ModelClass) classResource.getContents().get(0)).getInheritsFrom().getFields();
				}
			}
			// in some cases getFields/getParams do not return fields/params; backup via
			// modelclass
			if (fields.size() == 0 || params.size() == 0) {
				Resource classResource = ClassUtil.getClassModelResource(
						FileUtil.getProjectLocation(
								getDiagram().eResource().getURI().trimFileExtension().appendFileExtension("cbcmodel")),
						className, featureName);
				if (classResource != null && classResource.getContents().size() > 0
						&& classResource.getContents().get(0) instanceof ModelClass) {
					ModelClass mc = (ModelClass) classResource.getContents().get(0);
					fields = mc.getFields();
					for (Method m : mc.getMethods()) {
						if (m.getName().equals(methodName)) {
							params = m.getParameters();
							break;
						}
					}
				}
			}

			int size = variables.size() + fields.size() + params.size()
					+ (inheritedFields != null ? inheritedFields.size() : 0);
			if (containerShape.getChildren().size() - 2 != size) {
				return Reason.createTrueReason("Number of Variables differ. Expected: " + size + " Actual: "
						+ (containerShape.getChildren().size() - 2));
			}

			List<Integer> found = new ArrayList<Integer>();
			EList<Shape> shapes = containerShape.getChildren();
			for (int i = 2; i < shapes.size(); i++) {
				Shape shape = shapes.get(i);
				EList<EObject> objects = shape.eContents();
				for (int j = 0; j < objects.size(); j++) {
					EObject obj = objects.get(j);
					if (obj instanceof Text) {
						for (int k = 0; k < fields.size(); k++) {
							Field f = fields.get(k);
							if (f.getDisplayedName().equalsIgnoreCase(((Text) obj).getValue())) {
								found.add(k);
								break;
							}
						}
						if (inheritedFields != null) {
							for (int k = 0; k < inheritedFields.size(); k++) {
								Field f = inheritedFields.get(k);
								if (f.getDisplayedName()
										.equalsIgnoreCase(((Text) obj).getValue().replace(" inherited", ""))) {
									found.add(k + fields.size());
									break;
								}
							}
						}
						for (int k = 0; k < params.size(); k++) {
							Parameter p = params.get(k);
							if ((("PARAM " + p.getType() + " " + p.getName()).equalsIgnoreCase(((Text) obj).getValue())
									|| ("RETURN " + p.getType() + " " + p.getName())
											.equalsIgnoreCase(((Text) obj).getValue()))) {
								found.add(k + fields.size() + (inheritedFields != null ? inheritedFields.size() : 0));
								break;
							}
						}
						break;
					}
				}
			}
			if (found.size() != (fields.size() + params.size()
					+ (inheritedFields != null ? inheritedFields.size() : 0))) {
				return Reason.createTrueReason("Class fields differ from ModelClass.");
			}
		}
		updateGraphicsAlgorithmChildren(context.getGraphicsAlgorithm(), context);
		return Reason.createFalseReason();
	}

	@Override
	public boolean update(IdUpdateContext context, String id) {
		if (id.equals(ID_MAIN_RECTANGLE)) {
			EList<JavaVariable> variables = ((JavaVariables) context.getDomainObject()).getVariables();
			EList<Field> fields = ((JavaVariables) context.getDomainObject()).getFields();
			EList<Parameter> params = ((JavaVariables) context.getDomainObject()).getParams();
			EList<Field> inheritedFields = null;
			String className = FeatureUtil.getInstance()
					.getCallingClass(((JavaVariables) context.getDomainObject()).eResource().getURI());
			String featureName = FeatureUtil.getInstance()
					.getCallingFeature(((JavaVariables) context.getDomainObject()).eResource().getURI());
			String methodName = getDiagram().eResource().getURI().trimFileExtension()
					.segment(getDiagram().eResource().getURI().segmentCount() - 1);
			if (getDiagram().eResource().getURI().isPlatform()) {
				Resource classResource = ClassUtil.getClassModelResource(
						FileUtil.getProjectLocation(
								getDiagram().eResource().getURI().trimFileExtension().appendFileExtension("cbcmodel")),
						className, featureName);
				if (classResource != null && classResource.getContents().size() > 0
						&& ((ModelClass) classResource.getContents().get(0)).getInheritsFrom() != null) {
					inheritedFields = ((ModelClass) classResource.getContents().get(0)).getInheritsFrom().getFields();
				}
			}

			// in some cases getFields/getParams do not return fields/params; backup via
			// modelclass
			if (fields.size() == 0 || params.size() == 0) {
				Resource classResource = ClassUtil.getClassModelResource(
						FileUtil.getProjectLocation(
								getDiagram().eResource().getURI().trimFileExtension().appendFileExtension("cbcmodel")),
						className, featureName);
				if (classResource != null && classResource.getContents().size() > 0
						&& classResource.getContents().get(0) instanceof ModelClass) {
					ModelClass mc = (ModelClass) classResource.getContents().get(0);
					fields = mc.getFields();
					for (Method m : mc.getMethods()) {
						if (m.getName().equals(methodName)) {
							params = m.getParameters();
							break;
						}
					}
				}
			}

			List<Integer> checkedShapes = new ArrayList<>();
			checkedShapes.add(0);
			checkedShapes.add(1);

			for (int i = 0; i < params.size() + variables.size() + fields.size()
					+ (inheritedFields != null ? inheritedFields.size() : 0); i++) {
				Parameter param = null;
				JavaVariable var = null;
				Field field = null;
				String name = "";
				if (i < params.size()) {
					param = params.get(i);
					param.eResource();
					if (!param.getName().equals("ret")) {
						name = "PARAM " + param.getType() + " " + param.getName();
					} else {
						name = "RETURN " + param.getType() + " " + param.getName();
					}
				} else if (i < variables.size() + params.size()) {
					var = variables.get(i - params.size());
					name = var.getDisplayedName();
				} else if (i < fields.size() + variables.size() + params.size()) {
					field = fields.get(i - params.size() - variables.size());
					name = field.getDisplayedName();
				} else {
					field = inheritedFields.get(i - params.size() - variables.size() - fields.size());
					name = field.getDisplayedName();
				}
				EList<Shape> shapes = ((ContainerShape) context.getPictogramElement()).getChildren();
				boolean check = false;
				for (int j = 2; j < shapes.size(); j++) {
					Shape shape = shapes.get(j);
					Text text = (Text) shape.getGraphicsAlgorithm();
					if (name.equals(text.getValue().replace(" inherited", ""))) {
						check = true;
						checkedShapes.add(j);
						break;
					}
				}
				if (!check) {
					int newIndex = 0;
					ContainerShape container = (ContainerShape) context.getPictogramElement();
					for (Shape childShape : container.getChildren()) {
						if (getIndex(childShape.getGraphicsAlgorithm()) >= newIndex) {
							setIndex(childShape.getGraphicsAlgorithm(),
									getIndex(childShape.getGraphicsAlgorithm()) + 1);
						}
					}
					Shape shapeText = Graphiti.getPeCreateService()
							.createShape((ContainerShape) context.getPictogramElement(), true);
					Text variableNameText = Graphiti.getGaService().createText(shapeText,
							(name + (i < (params.size() + variables.size() + fields.size()) ? "" : " inherited")));
					setId(variableNameText, ID_VARIABLE_TEXT);
					setIndex(variableNameText, newIndex);
					variableNameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
					variableNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
					link(shapeText, (param != null ? param : (var != null ? var : field)));
					checkedShapes.add(shapes.size() - 1);
				}
			}

			// delete shapes that didn't match any param, field or variable
			EList<Shape> shapes = ((ContainerShape) context.getPictogramElement()).getChildren();
			for (int i = 2; i < shapes.size(); i++) {
				if (!checkedShapes.contains(i)) {
					Shape shape = shapes.get(i);
					int indexToDelete = getIndex(shape.getGraphicsAlgorithm());
					for (Shape childShape : shapes) {
						if (getIndex(childShape.getGraphicsAlgorithm()) > indexToDelete) {
							setIndex(childShape.getGraphicsAlgorithm(),
									getIndex(childShape.getGraphicsAlgorithm()) - 1);
						}
					}
					EcoreUtil.delete(shape.getLink());
					EcoreUtil.delete(shape);
					for (int j = 0; j < checkedShapes.size(); j++) {
						int oldVal = checkedShapes.get(j);
						if (oldVal > i) {
							int newVal = oldVal - 1;
							checkedShapes.set(j, newVal);
						}
					}
					i--;
				}
			}
			return true;
		}
		return false;
	}
}
