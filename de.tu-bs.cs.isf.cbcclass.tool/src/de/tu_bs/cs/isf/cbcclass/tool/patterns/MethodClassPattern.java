package de.tu_bs.cs.isf.cbcclass.tool.patterns;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import javax.swing.JOptionPane;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.context.IDeleteContext;
import org.eclipse.graphiti.features.context.IDirectEditingContext;
import org.eclipse.graphiti.features.context.impl.DirectEditingContext;
import org.eclipse.graphiti.features.context.impl.UpdateContext;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Image;
import org.eclipse.graphiti.mm.algorithms.MultiText;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.RoundedRectangle;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.algorithms.impl.TextImpl;
import org.eclipse.graphiti.mm.algorithms.styles.Font;
import org.eclipse.graphiti.mm.algorithms.styles.Orientation;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.mm.pictograms.impl.ContainerShapeImpl;
import org.eclipse.graphiti.pattern.IPattern;
import org.eclipse.graphiti.pattern.id.IdLayoutContext;
import org.eclipse.graphiti.pattern.id.IdPattern;
import org.eclipse.graphiti.pattern.id.IdUpdateContext;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;
import org.eclipse.graphiti.ui.services.GraphitiUi;
import org.eclipse.graphiti.util.IColorConstant;
import org.eclipse.graphiti.util.PredefinedColoredAreas;
//import org.emftext.language.java.members.ClassMethod;
//import org.emftext.language.java.statements.Statement;

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
import de.tu_bs.cs.isf.cbc.tool.diagram.CbCFeatureProvider;
import de.tu_bs.cs.isf.cbc.tool.helper.GenerateDiagramFromModel;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import helper.ClassUtil;
import helper.ModelClassHelper;
import model.CbcClassUtil;

import org.eclipse.emf.common.util.BasicEList;
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
	private String projectName;
	private String className;
	private String projectLocation;

	private ClassUtil utils = new ClassUtil("");
	private EList<Condition> invariants = new BasicEList<Condition>();
	private EList<Field> fields = new BasicEList<Field>();
	private ModelClass modelClass = null;
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

		projectLocation = FileUtil.getProjectLocation(getDiagram().eResource().getURI());
		String[] projectLocationFragments = projectLocation.split("/");
		projectName = projectLocationFragments[projectLocationFragments.length - 1];

		Resource resource = null;
		try {
			resource = CbcClassUtil.getResource(getDiagram());
			utils = new ClassUtil(projectName);
		} catch (CoreException | IOException e1) {
			e1.printStackTrace();
		}
		className = resource.getURI().trimFileExtension().lastSegment().toString();
		className = className.substring(0, 1).toUpperCase() + className.substring(1);
		String classPath = URI.createURI(projectLocation).trimSegments(1).toString() + "/" + resource.getURI().trimSegments(1).toString().substring(19) + "/";

		Method method = CbcclassFactory.eINSTANCE.createMethod();
		method.setSignature(input);
		method.setIsStatic(input.contains(" static "));

		for (EObject obj : resource.getContents()) {
			if (obj instanceof ModelClass) {
				method.setParentClass((ModelClass) obj);
			}
		}
		
		// check for existing cbcmodel file and read content
		File cbcmodelFile = new File(classPath + method.getName() + ".cbcmodel");
		String cbcmodelPath = getDiagram().eResource().getURI().trimSegments(1).appendSegment(method.getName() +".cbcmodel").toPlatformString(true);
		URI cbcmodelURI = URI.createFileURI(cbcmodelPath);
		Resource cbcmodelResource = ClassUtil.getCbcModelResource(projectLocation, method.getName());
		
		boolean alreadyExisting = false;
		if (cbcmodelFile.exists()) {
			alreadyExisting = true;			
			cbcmodelResource = rs.getResource(URI.createPlatformResourceURI(cbcmodelPath, true), true);
			for (EObject obj: cbcmodelResource.getContents()) {
				if (obj instanceof CbCFormula) {
					CbCFormula formula = (CbCFormula) obj;
					method.setCbcStartTriple(formula);
					method.setCbcDiagramURI(cbcmodelPath.replace(".cbcmodel", ".diagram"));
					formula.setMethodObj(method);
					formula.setClassName(className);
					formula.setProven(false);
				}
			}
		} else {
			// initialize cbcmodel file
			m.put("cbcmodel", new XMIResourceFactoryImpl());
			cbcmodelResource = rs.createResource(cbcmodelURI);
			
			CbCFormula formula = utils.createFormula(method.getName());
			formula.setMethodName(method.getName());
			formula.setClassName(className);
			formula.setProven(false);
			formula.setMethodObj(method);
			method.setCbcDiagramURI(cbcmodelPath.replace(".cbcmodel", ".diagram"));
			method.setCbcStartTriple(formula);
			
			cbcmodelResource.getContents().add(formula);
		}
		
		ModelClassHelper.setObject(method);
		updatePictogramElement(context.getTargetContainer());

		// save class fields to cbcmodel of new method
		Resource cbcclassResource = ClassUtil.getClassModelResource(projectLocation, className, method.getParentClass().getFeature());
		JavaVariables variables = CbcmodelFactory.eINSTANCE.createJavaVariables();

		// import already existing vars from cbcmodel or add fields to cbcmodel
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
		
		// create java class
		URI uri = getDiagram().eResource().getURI();
		String location = FileUtil.getProjectLocation(uri);
		for (int i = 2; i < uri.segments().length - 2; i++) {
			location += File.separator + uri.segment(i);
		}
		location += File.separator + method.getParentClass().getName().substring(0,1).toUpperCase() + method.getParentClass().getName().substring(1) + ".java";
		//utils.writeJavaClass(location, cbcclassResource, method);
		ClassUtil.refreshProject(projectLocation);
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
		if ((domainObject instanceof Method) && ga instanceof RoundedRectangle) {
			return true;
		}
		return false;
	}

	@Override
	public String checkValueValid(String value, IDirectEditingContext context) {
		String type = "(int|char|float|long|boolean|byte|short|double|([A-Z]\\w*))(\\[\\])?";
		if (value == null || value.length() == 0) {
			return "Methodsignature must not be empty";
		} else if (value.length() > 0 && !value.trim()
				.matches("(public\\s|private\\s|protected\\s)?" + "(static\\s)?"
						+ "((void|int|char|float|long|boolean|byte|short|double|([A-Z]\\w*))(\\[\\])?)?(\\s)?[a-z]\\w*"
						+ "\\(" + "((" + type + "\\s[A-Za-z]\\w*,(\\s)?)*(" + type + "\\s[A-Za-z]\\w*))?" + "\\)")) {
			return "Signature must contain a name and parentheses with optional arguments";
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

		Shape modShape = peCreateService.createShape(outerContainerShape, false);
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
			String path = FileUtil.getProjectLocation(getDiagram().eResource().getURI())+ "/features/" + domainObject.getParentClass().getFeature() + "/" + domainObject.getCbcStartTriple().getClassName() + "/" + domainObject.getName() + ".cbcmodel";
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
		} else if (id.equals(ID_IMAGE_PROVEN)) {
			Method domainObject = (Method) context.getDomainObject();
			Image image = (Image) context.getGraphicsAlgorithm();
			String path = FileUtil.getProjectLocation(getDiagram().eResource().getURI())+ "/features/" + domainObject.getParentClass().getFeature() + "/" + domainObject.getCbcStartTriple().getClassName() + "/" + domainObject.getName() + ".cbcmodel";
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
		String oldName = method.getName();
		String oldSignature = method.getSignature();
		int oldParamSize = method.getParameters().size();
		method.setSignature(value.trim());
		if (!oldName.equals(method.getName())) {
			method.setSignature(oldSignature);
			JOptionPane.showMessageDialog(null, "Can not change name of method in the this state of CorC.");
			return;
		}
			
		// create java class
		URI uri = getDiagram().eResource().getURI();
		String location = FileUtil.getProjectLocation(uri);
		for (int i = 2; i < uri.segments().length - 2; i++) {
			location += File.separator + uri.segment(i);
		}
		location += File.separator + method.getParentClass().getName().substring(0,1).toUpperCase() + method.getParentClass().getName().substring(1) + ".java";
		// TODO Without java class, verification of statments is not possible
		//utils.writeJavaClass(location, cbcclassResource, method);
		
		if (method.getParameters().size() != oldParamSize) {
			URI urii = getDiagram().eResource().getURI();
			ClassUtil.refreshProject(FileUtil.getProjectLocation(urii));
		}
		updatePictogramElement(((Shape) context.getPictogramElement()));		
	}

	@Override
	public String getInitialValue(IDirectEditingContext context) {
		Method method = (Method) getBusinessObjectForPictogramElement(context.getPictogramElement());
		return method.getSignature();
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
}