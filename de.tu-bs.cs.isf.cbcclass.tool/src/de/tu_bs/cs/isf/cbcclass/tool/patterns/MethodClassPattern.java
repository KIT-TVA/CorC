package de.tu_bs.cs.isf.cbcclass.tool.patterns;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import javax.swing.JOptionPane;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.context.IDeleteContext;
import org.eclipse.graphiti.features.context.IDirectEditingContext;
import org.eclipse.graphiti.features.context.impl.DirectEditingContext;
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
import org.eclipse.graphiti.util.IColorConstant;
import org.eclipse.graphiti.util.PredefinedColoredAreas;

import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.CbcclassFactory;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.ModelClass;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.VariableKind;
import de.tu_bs.cs.isf.cbcclass.tool.diagram.CbCClassImageProvider;
import de.tu_bs.cs.isf.cbc.tool.helper.GenerateDiagramFromModel;
import de.tu_bs.cs.isf.cbc.tool.helper.UpdateConditionsOfChildren;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import helper.ClassUtil;
import helper.ModelClassHelper;
import model.CbcClassUtil;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;

public class MethodClassPattern extends IdPattern implements IPattern {

	private static final String ID_PRE_TEXT = "preConditionText";
	private static final String ID_POST_TEXT = "postConditionText";
	private static final String ID_SIG_TEXT = "signatureText";
	private static final String ID_MOD_TEXT = "modifiableText";
	private static final String ID_MAIN_RECTANGLE = "mainRectangle";
	private static final String ID_IMAGE_PROVEN = "imageproven";

	// Header:
	private static final String ID_PRE_HEADER = "preHeader";
	private static final String ID_SIG_HEADER = "sigHeader";
	private static final String ID_POST_HEADER = "postHeader";
	private static final String ID_MOD_HEADER = "modHeader";

	// lines:
	private static final String ID_LINE_HEAD = "headerLine";
	private static final String ID_LINE_LEFT = "leftLine";
	private static final String ID_LINE_MID = "midLine";
	private static final String ID_LINE_RIGHT = "rightLine";

	private int width;
	private int height;

	private Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
	private Map<String, Object> m = reg.getExtensionToFactoryMap();
	private ResourceSet rs = new ResourceSetImpl();

	public MethodClassPattern() {
		super();
	}

	@Override
	public int getEditingType() {
		return TYPE_TEXT;
	}

	@Override
	public String getCreateName() {
		return "Class Method";
	}

	@Override
	public String getCreateDescription() {
		return "Create a Class Method";
	}

	@Override
	public boolean isMainBusinessObjectApplicable(Object mainBusinessObject) {
		return mainBusinessObject instanceof Method;
	}

	@Override
	public boolean canCreate(ICreateContext context) {
		return true;
	}

	@Override
	public Object[] create(ICreateContext context) {
		String input = "";
		boolean valid = false;
		do {
			input = (String) JOptionPane.showInputDialog(null, "Provide signature for new method");
			if (input == null) {
				return null;
			} else if (checkValueValid(input, null) == null)
				valid = true;
		} while (!valid);

		URI relURIdiagram = getDiagram().eResource().getURI();
		String projectName = relURIdiagram.segment(1);
		String className = relURIdiagram.segment(2).equals("features") ? relURIdiagram.segment(4) : relURIdiagram.segment(3);
		String featureName = relURIdiagram.segment(2).equals("features") ? relURIdiagram.segment(3) : "";

		Resource cbcclassResource = ClassUtil.getClassModelResource("platform:/resource/" + projectName, className, featureName);
		rs = cbcclassResource.getResourceSet();
		
		Method method = CbcclassFactory.eINSTANCE.createMethod();
		method.setSignature(input);
		method.setIsStatic(input.contains(" static "));
		
		Resource resource = null;
		try {
			resource = CbcClassUtil.getResource(getDiagram());
		} catch (IOException | CoreException e1) {
			e1.printStackTrace();
		}
		
		for (EObject obj : resource.getContents()) {
			if (obj instanceof ModelClass) {
				method.setParentClass((ModelClass) obj);
			}
		}
		
		Resource cbcmodelResource = ClassUtil.getCbcModelResource(FileUtil.getProjectLocation(relURIdiagram), method.getName(), featureName, className);
		boolean alreadyExisting = false;
		if (cbcmodelResource != null) {
			alreadyExisting = true;			
			for (EObject obj: cbcmodelResource.getContents()) {
				if (obj instanceof CbCFormula) {
					CbCFormula formula = (CbCFormula) obj;
					method.setCbcStartTriple(formula);
					method.setCbcDiagramURI(method.getName() + ".diagram");
					formula.setMethodObj(method);
					formula.setClassName(className);
					formula.setProven(false);
				}
			}
		} else {
			m.put("cbcmodel", new XMIResourceFactoryImpl());
			URI cbcmodelURI = URI.createPlatformResourceURI("/" + projectName + (!method.getParentClass().getFeature().equals("default") ? ("/features/" + method.getParentClass().getFeature()) : "/src") + "/" + className + "/" + method.getName() + ".cbcmodel", true);
			cbcmodelResource = rs.createResource(cbcmodelURI);
			
			CbCFormula formula = ClassUtil.createFormula(method.getName());
			formula.setMethodName(method.getName());
			formula.setClassName(className);
			formula.setProven(false);
			formula.setMethodObj(method);
			method.setCbcDiagramURI(method.getName() + ".diagram");
			method.setCbcStartTriple(formula);
			
			cbcmodelResource.getContents().add(formula);
		}
		
		ModelClassHelper.setObject(method);
		updatePictogramElement(context.getTargetContainer());

		// save class fields to cbcmodel of new method
		JavaVariables variables = CbcmodelFactory.eINSTANCE.createJavaVariables();
		if (alreadyExisting) {			
			for (EObject obj : cbcmodelResource.getContents()) {
				if (obj instanceof JavaVariables) {
					variables = (JavaVariables) obj;
					Predicate<JavaVariable> isParamOrReturn = (JavaVariable var) -> var.getKind().equals(VariableKind.RETURN) || var.getKind().equals(VariableKind.PARAM);
					variables.getVariables().removeIf(isParamOrReturn);		
				}
			}
		} else {
			for (EObject obj : cbcclassResource.getContents()) {
				if (obj instanceof ModelClass) {
					EList<Field> fields = ((ModelClass) obj).getFields();
					for (Field field : fields) {
						variables.getFields().add(field);
					}
				}
			}
			cbcmodelResource.getContents().add(variables);
		} 
		
		try {
			cbcmodelResource.save(Collections.EMPTY_MAP);
			cbcmodelResource.setTrackingModification(true);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (!alreadyExisting) {
			GenerateDiagramFromModel gdfm = new GenerateDiagramFromModel();
			gdfm.execute(cbcmodelResource);
		}		
		
		addGraphicalRepresentation(context, method);
		ClassUtil.refreshProject(FileUtil.getProjectLocation(relURIdiagram));
		return new Object[] { method };
	}

	@Override
	public boolean canAdd(IAddContext context) {
		return super.canAdd(context) && context.getTargetContainer() instanceof Diagram;
	}

	@Override
	public boolean canDirectEdit(IDirectEditingContext context) {
		Object domainObject = getBusinessObjectForPictogramElement(context.getPictogramElement());
		GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
		if ((domainObject instanceof Method) && (ga instanceof RoundedRectangle || ga instanceof MultiText)) {
			return true;
		}
		return false;
	}

	@Override
	public String checkValueValid(String value, IDirectEditingContext context) {
		if (context == null) {
			String type = "(int|char|float|long|boolean|byte|short|double|([A-Z]\\w*))(\\[\\])?";
			if (value == null || value.length() == 0) {
				return "Methodsignature must not be empty";
			} else if (value.length() > 0 && !value.trim()
					.matches("(public\\s|private\\s|protected\\s)?" + "(static\\s)?"
							+ "((void|int|char|float|long|boolean|byte|short|double|([A-Z]\\w*))(\\[\\])?)?(\\s)?[a-z]\\w*"
							+ "\\(" + "((" + type + "\\s[A-Za-z]\\w*,(\\s)?)*(" + type + "\\s[A-Za-z]\\w*))?" + "\\)")) {
				return "Signature must contain a name and parentheses with optional arguments";
			}
		} else {
			Method method = (Method) getBusinessObjectForPictogramElement(context.getPictogramElement());
			value = "-" + value.trim().replaceAll(" ", "").replaceAll(",", "--") + "-";
			for (Field f : method.getParentClass().getFields()) {
				value = value.replaceAll("-" + f.getName() + "(\\[.*\\])*-", "");
			}
			if (value.replaceAll("-", "").length() != 0) return "Modifiables must only contain fields of this class";
		}
		return null;
	}

	@Override
	protected PictogramElement doAdd(IAddContext context) {
		manageColor(IColorConstant.DARK_GREEN);
		Diagram targetDiagram = (Diagram) context.getTargetContainer();
		Method addedMethod = (Method) context.getNewObject();

		IPeCreateService peCreateService = Graphiti.getPeCreateService();
		IGaService gaService = Graphiti.getGaService();

		width = context.getWidth() <= 0 ? 600 : context.getWidth();
		height = context.getHeight() <= 0 ? 150 : context.getHeight();

		Font headerFont = gaService.manageFont(getDiagram(), "Arial", 9, false, true);

		// Main contents area
		ContainerShape outerContainerShape = peCreateService.createContainerShape(targetDiagram, true);
		RoundedRectangle mainRectangle = gaService.createRoundedRectangle(outerContainerShape, 20, 20);
		mainRectangle.setFilled(true);
		gaService.setRenderingStyle(mainRectangle, PredefinedColoredAreas.getBlueWhiteAdaptions());
		mainRectangle.setForeground(manageColor((IColorConstant.RED)));
		mainRectangle.setLineWidth(2);
		setId(mainRectangle, ID_MAIN_RECTANGLE);
		gaService.setLocationAndSize(mainRectangle, context.getX(), context.getY(), width, height);

		// create link and wire it
		link(outerContainerShape, addedMethod);

		// text
		Shape preShape = peCreateService.createShape(outerContainerShape, true);
		MultiText preText = gaService.createMultiText(preShape,
				"{" + addedMethod.getCbcStartTriple().getStatement().getPreCondition().getName() + "}");
		setId(preText, ID_PRE_TEXT);
		preText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		preText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);

		Shape sigShape = peCreateService.createShape(outerContainerShape, false);
		MultiText sigText = gaService.createMultiText(sigShape, addedMethod.getSignature());
		setId(sigText, ID_SIG_TEXT);
		sigText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		sigText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);

		Shape postShape = peCreateService.createShape(outerContainerShape, true);
		MultiText postText = gaService.createMultiText(postShape,
				"{" + addedMethod.getCbcStartTriple().getStatement().getPostCondition().getName() + "}");
		setId(postText, ID_POST_TEXT);
		postText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		postText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);

		Shape modShape = peCreateService.createShape(outerContainerShape, true);
		MultiText modText = gaService.createMultiText(modShape,
				"{" + (addedMethod.getAssignable() == null ? "" : addedMethod.getAssignable()) + "}");
		setId(modText, ID_MOD_TEXT);
		modText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		modText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);

		// header
		Shape preHeader = peCreateService.createShape(outerContainerShape, false);
		Text preTextHeader = gaService.createText(preHeader, "precondition");
		setId(preTextHeader, ID_PRE_HEADER);
		preTextHeader.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		preTextHeader.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		preTextHeader.setFont(headerFont);

		Shape sigHeader = peCreateService.createShape(outerContainerShape, false);
		Text sigTextHeader = gaService.createText(sigHeader, "signature");
		setId(sigTextHeader, ID_SIG_HEADER);
		sigTextHeader.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		sigTextHeader.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		sigTextHeader.setFont(headerFont);

		Shape postHeader = peCreateService.createShape(outerContainerShape, false);
		Text postTextHeader = gaService.createText(postHeader, "postcondition");
		setId(postTextHeader, ID_POST_HEADER);
		postTextHeader.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		postTextHeader.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		postTextHeader.setFont(headerFont);

		Shape modHeader = peCreateService.createShape(outerContainerShape, false);
		Text modTextHeader = gaService.createText(modHeader, "modifiable");
		setId(modTextHeader, ID_MOD_HEADER);
		modTextHeader.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		modTextHeader.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		modTextHeader.setFont(headerFont);

		// lines
		Shape headerShape = peCreateService.createShape(outerContainerShape, false);
		Polyline headerline = gaService.createPolyline(headerShape);
		setId(headerline, ID_LINE_HEAD);

		Shape leftShape = peCreateService.createShape(outerContainerShape, false);
		Polyline leftline = gaService.createPolyline(leftShape);
		setId(leftline, ID_LINE_LEFT);

		Shape midShape = peCreateService.createShape(outerContainerShape, false);
		Polyline midline = gaService.createPolyline(midShape);
		setId(midline, ID_LINE_MID);

		Shape rightShape = peCreateService.createShape(outerContainerShape, false);
		Polyline rightline = gaService.createPolyline(rightShape);
		setId(rightline, ID_LINE_RIGHT);

		// proving
		Shape proveShape = peCreateService.createShape(outerContainerShape, false);
		Image image = gaService.createImage(proveShape, CbCClassImageProvider.IMG_UNPROVEN);
		setId(image, ID_IMAGE_PROVEN);

		peCreateService.createChopboxAnchor(outerContainerShape);
		peCreateService.createChopboxAnchor(postShape);
		peCreateService.createChopboxAnchor(preShape);

		link(outerContainerShape, addedMethod);
		link(preShape, addedMethod.getCbcStartTriple().getStatement().getPreCondition());
		link(postShape, addedMethod.getCbcStartTriple().getStatement().getPostCondition());
		link(sigShape, addedMethod);
		link(modShape, addedMethod);
		link(proveShape, addedMethod);

		return outerContainerShape;
	}

	@Override
	protected boolean layout(IdLayoutContext context, String id) {
		boolean changesDone = false;

		GraphicsAlgorithm mainRectangle = context.getRootPictogramElement().getGraphicsAlgorithm();
		GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
		int fourth = mainRectangle.getWidth() / 4;

		if (id.equals(ID_PRE_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, 40, fourth, mainRectangle.getHeight() - 40);
			changesDone = true;
		} else if (id.equals(ID_SIG_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, fourth, 40, fourth, mainRectangle.getHeight() - 40);
			changesDone = true;
		} else if (id.equals(ID_POST_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, fourth * 2, 40, fourth, mainRectangle.getHeight() - 40);
			changesDone = true;
		} else if (id.equals(ID_MOD_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, fourth * 3, 40, fourth, mainRectangle.getHeight() - 40);
			changesDone = true;
		} else if (id.equals(ID_PRE_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, 20, fourth, 20);
			changesDone = true;
		} else if (id.equals(ID_SIG_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, fourth, 20, fourth, 20);
			changesDone = true;
		} else if (id.equals(ID_POST_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, fourth * 2, 20, fourth, 20);
			changesDone = true;
		} else if (id.equals(ID_MOD_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, fourth * 3, 20, fourth, 20);
			changesDone = true;
		} else if (id.equals(ID_LINE_LEFT)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService()
					.createPointList(new int[] { fourth, 0, fourth, mainRectangle.getHeight() });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if (id.equals(ID_LINE_MID)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService()
					.createPointList(new int[] { fourth * 2, 0, fourth * 2, mainRectangle.getHeight() });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if (id.equals(ID_LINE_RIGHT)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService()
					.createPointList(new int[] { fourth * 3, 0, fourth * 3, mainRectangle.getHeight() });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if (id.equals(ID_LINE_HEAD)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService()
					.createPointList(new int[] { 0, 40, mainRectangle.getWidth(), 40 });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		}
		return changesDone;
	}

	@Override
	protected IReason updateNeeded(IdUpdateContext context, String id) {
		if (id.equals(ID_IMAGE_PROVEN)) {
			Method domainObject = (Method) context.getDomainObject();
			Image image = (Image) context.getGraphicsAlgorithm();
			String path = FileUtil.getProjectLocation(getDiagram().eResource().getURI())+ (domainObject.getParentClass().getFeature().equals("default") ? "/src" : "/features/" + domainObject.getParentClass().getFeature()) + "/" + domainObject.getCbcStartTriple().getClassName() + "/" + domainObject.getName() + ".cbcmodel";
			File cbcmodelFile = new File(path);
			if (cbcmodelFile.exists()) {
				URI cbcmodelURI = URI.createFileURI(path);

				CbCFormula read = CbcClassUtil.readFormula(cbcmodelURI);
				if (read.isProven() && image.getId().equals(CbCClassImageProvider.IMG_UNPROVEN)) {
					return Reason.createTrueReason("Statement is proven. Expected green color.");
				} else if (!read.isProven() && image.getId().equals(CbCClassImageProvider.IMG_PROVEN)) {
					return Reason.createTrueReason("Statement is not proven. Expected red color.");
				}	
			}	
		} else if (id.equals(ID_PRE_TEXT)) {
			Condition domainObject = (Condition) context.getDomainObject();
			String[] fragments = domainObject.eResource().getURI().trimFileExtension().toString().split("/");
			String methodName = fragments[fragments.length-1];
			String cbcmodelPath = getDiagram().eResource().getURI().trimSegments(1).appendSegment(methodName +".cbcmodel").toPlatformString(true);
			Resource cbcmodelResource = rs.getResource(URI.createPlatformResourceURI(cbcmodelPath, true), true);
			
			for (EObject obj : cbcmodelResource.getContents()) {
				if (obj instanceof CbCFormula) {
					CbCFormula formula = (CbCFormula) obj;
					if (!formula.getStatement().getPreCondition().getName().equals(domainObject.getName())) {
						return Reason.createTrueReason("Precondition differs to cbcmodel of method.");
					}
				}
			}
		} else if (id.equals(ID_POST_TEXT)) {
			Condition domainObject = (Condition) context.getDomainObject();
			String[] fragments = domainObject.eResource().getURI().trimFileExtension().toString().split("/");
			String methodName = fragments[fragments.length-1];
			String cbcmodelPath = getDiagram().eResource().getURI().trimSegments(1).appendSegment(methodName +".cbcmodel").toPlatformString(true);
			Resource cbcmodelResource = rs.getResource(URI.createPlatformResourceURI(cbcmodelPath, true), true);
			
			for (EObject obj : cbcmodelResource.getContents()) {
				if (obj instanceof CbCFormula) {
					CbCFormula formula = (CbCFormula) obj;
					if (!formula.getStatement().getPostCondition().getName().equals(domainObject.getName())) {
						return Reason.createTrueReason("Postcondition differs to cbcmodel of method.");
					}
				}
			}
		} else if (id.equals(ID_SIG_TEXT)) {
			MultiText sigText = (MultiText) context.getGraphicsAlgorithm();
			Method domainObject = (Method) context.getDomainObject();
			if (!sigText.getValue().equals(domainObject.getSignature())) {
				return Reason.createTrueReason("Signature is not up to date.");
			}
		} else if (id.equals(ID_MOD_TEXT)) {
			MultiText modText = (MultiText) context.getGraphicsAlgorithm();
			Method domainObject = (Method) context.getDomainObject();
			if (!modText.getValue().equals(getModifiablesString(domainObject.getParentClass(), domainObject.getCbcStartTriple().getStatement().getPostCondition()))) {
				return Reason.createTrueReason("Modifiables are not up to date.");
			}
		}
		return Reason.createFalseReason();
	}

	public boolean canUpdate(IdUpdateContext context) {
		Object bo = getBusinessObjectForPictogramElement(context.getPictogramElement());
		return (bo instanceof Method);
	}

	@Override
	protected boolean update(IdUpdateContext context, String id) {
		if (id.equals(ID_MAIN_RECTANGLE)) {
			RoundedRectangle rectangle = (RoundedRectangle) context.getGraphicsAlgorithm();
			Method domainObject = (Method) context.getDomainObject();
			String classPath = FileUtil.getProjectLocation(getDiagram().eResource().getURI()) +"/"+ domainObject.getCbcStartTriple().getClassName() + "\\";
			File cbcmodelFile = new File(classPath + domainObject.getCbcStartTriple().getMethodName() + ".diagram");
			if (cbcmodelFile.exists()) {
				URI cbcmodelURI = URI.createFileURI(classPath + domainObject.getName() + ".cbcmodel");
				CbCFormula read = CbcClassUtil.readFormula(cbcmodelURI);
				if (read != null) {
					domainObject.getCbcStartTriple().setProven(read.isProven());
				}
			}
			if (domainObject.getCbcStartTriple().isProven()) {
				rectangle.setForeground(manageColor(IColorConstant.DARK_GREEN));
			} else {
				rectangle.setForeground(manageColor(IColorConstant.RED));
			}
			return true;
		} else if (context.getGraphicsAlgorithm() instanceof MultiText && context.getDomainObject() instanceof Method
				&& id.equals(ID_SIG_TEXT)) {
			MultiText nameText = (MultiText) context.getGraphicsAlgorithm();
			Method domainObject = (Method) context.getDomainObject();
			if (!nameText.getValue().equals(domainObject.getSignature())) {
				nameText.setValue(domainObject.getSignature());
				DirectEditingContext dec = new DirectEditingContext(context.getPictogramElement(), context.getGraphicsAlgorithm());
				setValue(domainObject.getSignature(), dec);
			}
			return true;
		} else if (id.equals(ID_PRE_TEXT) && context.getDomainObject() instanceof Condition) {
			Condition domainObject = (Condition) context.getDomainObject();
			String[] fragments = domainObject.eResource().getURI().trimFileExtension().toString().split("/");
			String methodName = fragments[fragments.length-1];
			String cbcmodelPath = getDiagram().eResource().getURI().trimSegments(1).appendSegment(methodName +".cbcmodel").toPlatformString(true);
			Resource cbcmodelResource = rs.getResource(URI.createPlatformResourceURI(cbcmodelPath, true), true);
			
			for (EObject obj : cbcmodelResource.getContents()) {
				if (obj instanceof CbCFormula) {
					CbCFormula formula = (CbCFormula) obj;
					domainObject.setName(formula.getStatement().getPreCondition().getName());
					return true;
				}
			}
		} else if (id.equals(ID_POST_TEXT) && context.getDomainObject() instanceof Condition) {
			Condition domainObject = (Condition) context.getDomainObject();
			String[] fragments = domainObject.eResource().getURI().trimFileExtension().toString().split("/");
			String methodName = fragments[fragments.length-1];
			String cbcmodelPath = getDiagram().eResource().getURI().trimSegments(1).appendSegment(methodName +".cbcmodel").toPlatformString(true);
			Resource cbcmodelResource = rs.getResource(URI.createPlatformResourceURI(cbcmodelPath, true), true);
			
			for (EObject obj : cbcmodelResource.getContents()) {
				if (obj instanceof CbCFormula) {
					CbCFormula formula = (CbCFormula) obj;
					domainObject.setName(formula.getStatement().getPostCondition().getName());
					return true;
				}
			}
		} else if (id.equals(ID_MOD_TEXT) && context.getGraphicsAlgorithm() instanceof MultiText && context.getDomainObject() instanceof Method) {
			MultiText modText = (MultiText) context.getGraphicsAlgorithm();
			Method domainObject = (Method) context.getDomainObject();
			if (!modText.getValue().equals(getModifiablesString(domainObject.getParentClass(), domainObject.getCbcStartTriple().getStatement().getPostCondition()))) {
				modText.setValue(getModifiablesString(domainObject.getParentClass(), domainObject.getCbcStartTriple().getStatement().getPostCondition()));
			}
			return true;
		} else if (id.equals(ID_IMAGE_PROVEN)) {
			Method domainObject = (Method) context.getDomainObject();
			Image image = (Image) context.getGraphicsAlgorithm();
			String path = FileUtil.getProjectLocation(getDiagram().eResource().getURI())+ (domainObject.getParentClass().getFeature().equals("default") ? "/src" : "/features/" + domainObject.getParentClass().getFeature()) + "/" + domainObject.getCbcStartTriple().getClassName() + "/" + domainObject.getName() + ".cbcmodel";
			File cbcmodelFile = new File(path);
			if (cbcmodelFile.exists()) {
				URI cbcmodelURI = URI.createFileURI(path);
				CbCFormula read = CbcClassUtil.readFormula(cbcmodelURI);
				if (read != null) {
					domainObject.getCbcStartTriple().setProven(read.isProven());
					if (read.isProven()) {
						image.setId(CbCClassImageProvider.IMG_PROVEN);
					} else {
						image.setId(CbCClassImageProvider.IMG_UNPROVEN);
					}
				}				
			}
		}
		return false;
	}

	@Override
	public void setValue(String value, IDirectEditingContext context) {
		Method method = (Method) getBusinessObjectForPictogramElement(context.getPictogramElement());
		GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
		if (ga instanceof RoundedRectangle) {
			String oldName = method.getName();
			String oldSignature = method.getSignature();
			int oldParamSize = method.getParameters().size();
			method.setSignature(value.trim());
			if (!oldName.equals(method.getName())) {
				method.setSignature(oldSignature);
				JOptionPane.showMessageDialog(null, "Can not change name of method in the this state of CorC.");
				return;
			}
		
			if (method.getParameters().size() != oldParamSize) {
				URI urii = getDiagram().eResource().getURI();
				ClassUtil.refreshProject(FileUtil.getProjectLocation(urii));
			}
		} else if (ga instanceof MultiText) {
			String[] split = value.trim().split(",");
			method.getCbcStartTriple().getStatement().getPostCondition().getModifiables().clear();
			for (String s : split) {
				if (!s.equals("")) method.getCbcStartTriple().getStatement().getPostCondition().getModifiables().add(s.trim());
			}
			try {
				method.getCbcStartTriple().getStatement().getPostCondition().eResource().save(Collections.EMPTY_MAP);
				method.getCbcStartTriple().getStatement().getPostCondition().eResource().setTrackingModification(true);
			} catch (IOException e) {
				e.printStackTrace();
			}
			UpdateConditionsOfChildren.updateConditionsofChildren(method.getCbcStartTriple().getStatement().getPostCondition());	
		}
		updatePictogramElement(((Shape) context.getPictogramElement()));		
	}

	@Override
	public String getInitialValue(IDirectEditingContext context) {
		Method method = (Method) getBusinessObjectForPictogramElement(context.getPictogramElement());
		GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
		if (ga instanceof RoundedRectangle) {
			return method.getSignature();
		} else {
			return getModifiablesString(method.getParentClass(), method.getCbcStartTriple().getStatement().getPostCondition());
		}
	}
	
	@Override
	public void delete(IDeleteContext context) {
		rs.getResources();
		Shape shape = (Shape) context.getPictogramElement();
		ContainerShape container = shape.getContainer();	
		Method method = (Method) getBusinessObjectForPictogramElement(context.getPictogramElement());
		String toDelete = method.getParentClass().getName() + "/" + method.getName() + ".cbcmodel";
		ModelClass mc = method.getParentClass();
		mc.getMethods().remove(method);
		super.delete(context);
		layoutPictogramElement(container);
		for (Resource resource : rs.getResources()) {
			if (resource.getURI().toString().endsWith(toDelete)) {
				rs.getResources().remove(resource);
				break;
			}
		}
	}
	
	private String getModifiablesString(ModelClass parent, Condition condition) {
		String modString = "";
		if (condition.getModifiables() != null && condition.getModifiables().size() > 0) {
			for (String mod : condition.getModifiables()) {
				for (Field f : parent.getFields()) {
					if (f.getName() != null) {
						String fieldName = f.getName().replace("[", "").replace("]", "").trim();
						if (mod.replace("this.","").startsWith(fieldName)) {
							if (modString.length() != 0) {
								modString = modString + ", " + mod.replace("this.","");
							} else {
								modString = mod.replace("this.","");
							}
							break;
						}
					}
				}
			}
		}
		return modString;
	}
}