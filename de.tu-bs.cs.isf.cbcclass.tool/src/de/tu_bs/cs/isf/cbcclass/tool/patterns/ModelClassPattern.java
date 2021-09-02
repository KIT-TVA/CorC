package de.tu_bs.cs.isf.cbcclass.tool.patterns;

import java.awt.Frame;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateContext;
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
import org.eclipse.graphiti.mm.pictograms.PictogramLink;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.mm.pictograms.impl.PictogramLinkImpl;
import org.eclipse.graphiti.mm.pictograms.impl.ShapeImpl;
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
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.impl.FieldImpl;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.impl.MethodImpl;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.impl.ModelClassImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.ConditionImpl;
import de.tu_bs.cs.isf.cbcclass.tool.diagram.CbCClassImageProvider;
import helper.ModelClassHelper;
import model.CbcClassUtil;


public class ModelClassPattern extends IdPattern implements IPattern {

	
	@Override
	public String getCreateName() {
		return "Model Class";
	}

	@Override
	public String getCreateDescription() {
		return "Create a Model Class";
	}
	
	// TODO: GUI anpassen
	private static final String ID_NAME_TEXT = "nameText";
	private static final String ID_CLASS_FIELDS_TEXT = "fieldsNameText";
	private static final String ID_CLASS_INVARIANTS_TEXT = "classInvariantsText";
	private static final String ID_CLASS_METHODS_POST_TEXT = "classMethodsPostText";
	private static final String ID_CLASS_METHODS_PRE_TEXT = "classMethodsPreText";
	private static final String ID_CLASS_METHODS_SIGNATURE_TEXT = "classMethodsSignatureText";
	
	private static final String ID_MAIN_RECTANGLE = "mainRectangle";
	private static final String ID_IMAGE_PROVEN = "imageproven";
	// Header:
	private static final String ID_CLASS_INVARIANTS_HEADER = "ivHeader";
	private static final String ID_CLASS_METHODS_HEADER = "mtHeader";
	private static final String ID_CLASS_FIELDS_HEADER = "fHeader";
	// lines:
	private static final String ID_HOR1_LINE = "hor1Line";
	private static final String ID_HOR2_LINE = "hor2Line";
	private static final String ID_HOR3_LINE = "hor3Line";
	private static final String ID_HOR4_LINE = "hor4Line";
	private static final String ID_HOR5_LINE = "hor5Line";
	private static final String ID_HOR6_LINE = "hor6Line";
	//private static final String ID_VER1_LINE = "ver1Line";
	//private static final String ID_VER2_LINE = "ver2Line";
	
	private MultiText invariantsText;
	private Shape textShapeInvariants;
	
	private Shape textShapeFields;
	private MultiText fieldsText;
	
	private Shape textShapeMethods;
	private MultiText methodsText;
	
	private int width;
	private int height;
	
	
	public static ModelClass instance;
	
	
	
	public ModelClassPattern() {
		super();
	}
	
	@Override
	public boolean isMainBusinessObjectApplicable(Object mainBusinessObject) {
		// TODO Auto-generated method stub
		return mainBusinessObject instanceof ModelClass;
	}
	
	// TODO: check correctness
	public boolean canCreate(ICreateContext context) {
	ModelClass modelClass = null;
	for (Shape shape : getDiagram().getChildren()) {
		Object obj = getBusinessObjectForPictogramElement(shape);
		if (obj instanceof ModelClass) {
			modelClass = (ModelClass) obj;
		}
	}
	
	if (modelClass != null) return false;
	
	return context.getTargetContainer() instanceof Diagram;
	
	} 
	
	
	// Create all elements
	public Object[] create (ICreateContext context) {
		
		Frame jf=new JFrame();
		jf.setAlwaysOnTop(true);
		String input = "Model Class";
		
		input = (String) JOptionPane.showInputDialog(null, "Choose a class name");
	
		//input = (String) JOptionPane.showInputDialog(null, "Choose a class name",
		//		"Class Name", JOptionPane.QUESTION_MESSAGE, null, new String[] {"a", "b"}, "a");
		
		
		ModelClass modelClass = CbcclassFactory.eINSTANCE.createModelClass();
		modelClass.setName(getDiagram().getName());
		modelClass.setName(input);
		
		instance = modelClass;
		
		modelClass.setJavaClassURI("zero");

		
	

		try {
			CbcClassUtil.saveModelClass(modelClass, getDiagram());
		} catch (CoreException | IOException e) {
			e.printStackTrace();
		}

		
		
		addGraphicalRepresentation(context, modelClass);
		return new Object[] { modelClass };
		
	}
	
	public boolean canAdd(IAddContext context) {
		//return false;
		return super.canAdd(context) && context.getTargetContainer() instanceof Diagram;
	}
	
	
	@Override
	protected PictogramElement doAdd(IAddContext context) {
		manageColor(IColorConstant.DARK_GREEN);
		Diagram targetDiagram = (Diagram) context.getTargetContainer();
		ModelClass addedModelClass = (ModelClass) context.getNewObject();
		
		IPeCreateService peCreateService = Graphiti.getPeCreateService();
		IGaService gaService = Graphiti.getGaService();

		width = context.getWidth() <= 0 ? 400 : context.getWidth();
		height = context.getHeight() <= 0 ? 400 : context.getHeight();

		Font headerFont = gaService.manageFont(getDiagram(), "Arial", 9, false, true);
		
		// Main contents area
		ContainerShape outerContainerShape = peCreateService.createContainerShape(targetDiagram, true);
		RoundedRectangle mainRectangle = gaService.createRoundedRectangle(outerContainerShape, 20, 20);
		mainRectangle.setFilled(true);
		gaService.setRenderingStyle(mainRectangle, PredefinedColoredAreas.getBlueWhiteAdaptions());
		setId(mainRectangle, ID_MAIN_RECTANGLE);
		gaService.setLocationAndSize(mainRectangle, context.getX(), context.getY(), width, height);

		// create link and wire it
		link(outerContainerShape, addedModelClass);
		
		/*
		// Invariants
		textShapeInvariants = peCreateService.createShape(outerContainerShape, true);
		invariantsText = gaService.createMultiText(textShapeInvariants, "");
		setId(invariantsText, ID_CLASS_INVARIANTS_TEXT);
		invariantsText.setValue("{" + "condition" + "}");
		invariantsText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		invariantsText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		
		
		// Fields
		textShapeFields = peCreateService.createShape(outerContainerShape, true);
		fieldsText = gaService.createMultiText(textShapeFields, "");
		setId(fieldsText, ID_CLASS_FIELDS_TEXT);
		fieldsText.setValue(addedModelClass.getFields().toString());
		fieldsText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		fieldsText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);

		// Methods
		textShapeMethods = peCreateService.createShape(outerContainerShape, true);
		methodsText = gaService.createMultiText(textShapeMethods, "");
		setId(methodsText, ID_CLASS_METHODS_TEXT);
		methodsText.setValue(addedModelClass.getMethods().toString());
		methodsText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		methodsText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		*/
		
		Shape textShapeName = peCreateService.createShape(outerContainerShape, false);
		MultiText nameText = gaService.createMultiText(textShapeName, "Model Class");
		setId(nameText, ID_NAME_TEXT);
		nameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		nameText.setVerticalAlignment
		(Orientation.ALIGNMENT_CENTER);
		nameText.setFont(headerFont);
		
		// proving
		Shape proveShape = peCreateService.createShape(outerContainerShape, false);
		Image image = gaService.createImage(proveShape, CbCClassImageProvider.IMG_UNPROVEN);
		setId(image, ID_IMAGE_PROVEN);
		
		// Header:
		
		// Invariants
		Shape invariantsHeaderShape = peCreateService.createShape(outerContainerShape, false);
		Text invariantsHeader = gaService.createText(invariantsHeaderShape, "invariants");
		setId(invariantsHeader, ID_CLASS_INVARIANTS_HEADER);
		invariantsHeader.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		invariantsHeader.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		invariantsHeader.setFont(headerFont);
		
		// Fields
		Shape fieldsHeaderShape = peCreateService.createShape(outerContainerShape, false);
		Text fieldsHeader = gaService.createText(fieldsHeaderShape, "fields");
		setId(fieldsHeader, ID_CLASS_FIELDS_HEADER);
		fieldsHeader.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		fieldsHeader.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		fieldsHeader.setFont(headerFont);
		
		// Methods
		Shape mtHeaderShape = peCreateService.createShape(outerContainerShape, false);
		Text mtHeader = gaService.createText(mtHeaderShape, "methods");
		setId(mtHeader, ID_CLASS_METHODS_HEADER);
		mtHeader.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		mtHeader.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		mtHeader.setFont(headerFont);
		
		
		// lines:
		Shape hor1LineShape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor1Polyline = gaService.createPolyline(hor1LineShape);
		setId(hor1Polyline, ID_HOR1_LINE);

		Shape hor2LineShape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor2Polyline = gaService.createPolyline(hor2LineShape);
		setId(hor2Polyline, ID_HOR2_LINE);
		
		Shape hor3LineShape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor3Polyline = gaService.createPolyline(hor3LineShape);
		setId(hor3Polyline, ID_HOR3_LINE);

		Shape hor4LineShape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor4Polyline = gaService.createPolyline(hor4LineShape);
		setId(hor4Polyline, ID_HOR4_LINE);
		
		Shape hor5LineShape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor5Polyline = gaService.createPolyline(hor5LineShape);
		setId(hor5Polyline, ID_HOR5_LINE);
		
		Shape hor6LineShape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor6Polyline = gaService.createPolyline(hor6LineShape);
		setId(hor6Polyline, ID_HOR6_LINE);

		/*
		Shape ver1LineShape = peCreateService.createShape(outerContainerShape, false);
		Polyline ver1Polyline = gaService.createPolyline(ver1LineShape);
		setId(ver1Polyline, ID_VER1_LINE);

		Shape ver2LineShape = peCreateService.createShape(outerContainerShape, false);
		Polyline ver2Polyline = gaService.createPolyline(ver2LineShape);
		setId(ver2Polyline, ID_VER2_LINE);
		*/

		peCreateService.createChopboxAnchor(textShapeFields);
		
		link(outerContainerShape, addedModelClass);
		link(textShapeName, addedModelClass);
		//link(textShapeFields, addedModelClass);
		//link(textShapeInvariants, addedModelClass);
		//link(textShapeMethods, addedModelClass);
		link(proveShape, addedModelClass);
		
		/*for (EObject obj: addedModelClass.getFields()) {
			link(textShapeFields, obj);
		}
		
		for (EObject obj: addedModelClass.getClassInvariants()) {
			link(textShapeInvariants, obj);
		}
		
		for (EObject obj: addedModelClass.getMethods()) {
			link(textShapeMethods, obj);
		}
		*/
		
		//link(textShapeFields, addedModelClass.getFields());
		//link(textShapeInvariants, addedModelClass.getClassInvariants());
		//link(textShapeMethods, addedModelClass.getMethods());
		
		return outerContainerShape;	

	}

	@Override
	protected boolean layout(IdLayoutContext context, String id) {
		boolean changesDone = false;
		
		int indexInvariant = 0;
		int indexField = 0;
		int indexMethod = 0;
		
		
		// get index of the current invariant, field or method object
		ModelClassImpl modelClassImpl = (ModelClassImpl)(context.getRootPictogramElement().getLink().getBusinessObjects().get(0));
		PictogramLink link = context.getPictogramElement().getLink();
		if (link != null) {
			Object obj = link.getBusinessObjects().get(0);
			
			if (obj instanceof MethodImpl) {
				int index = modelClassImpl.getMethods().indexOf(obj);
				indexMethod = index != -1 ? index : 0;
			} else if (obj instanceof FieldImpl) {
				int index = modelClassImpl.getFields().indexOf(obj);
				indexField = index != -1 ? index : 0;
			} else if (obj instanceof ConditionImpl) {
				int index = modelClassImpl.getClassInvariants().indexOf(obj);
				indexInvariant = index != -1 ? index : 0;
			}
		}
		
		
		GraphicsAlgorithm mainRectangle = context.getRootPictogramElement().getGraphicsAlgorithm();
		GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
		
		width = mainRectangle.getWidth();
		height = mainRectangle.getHeight();
		
		int third = mainRectangle.getWidth() / 3;
		// stable sizes from Name and Header save space when the diagram gets big!
		int sizeName = 30; // size from Formular block
		int sizeHeader = 20; // size from the Header
		int positionHeader = 40; // position where the Header is
		int sizeText = mainRectangle.getHeight() - positionHeader - sizeHeader; // size from the blocks (iv, fields, methods)
		int positionText = positionHeader + sizeHeader; // position from the blocks (pre, statement, post)
		
		ModelClass modelClass = (ModelClass) getBusinessObjectForPictogramElement(context.getRootPictogramElement());
		int sizeInvariants = modelClass.getClassInvariants().size();
		int sizeFields = modelClass.getFields().size();
		int sizeMethods = modelClass.getMethods().size();
		
		sizeText = 30;
		
		// check if resizing is necessary
		int totalSize = sizeInvariants + sizeFields + sizeMethods;
		
		if (height / (totalSize * sizeText + 1) < 2.5) {
			mainRectangle.setHeight( height + 30);
			mainRectangle.setWidth( width + 20);
		}
		

		int beginYFieldHeader = positionText + sizeText * (modelClass.getClassInvariants().size()) + 50;
		int beginYMethodHeader = positionText + sizeText * (modelClass.getClassInvariants().size() + modelClass.getFields().size()) + 150;
		
		if (id.equals(ID_NAME_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, 5, mainRectangle.getWidth(), sizeName);
			changesDone = true;
		} else if (id.equals(ID_CLASS_INVARIANTS_TEXT)) {
			//int index = getIndex(context.getGraphicsAlgorithm());
			Graphiti.getGaService().setLocationAndSize(ga, 0, positionText + sizeText * (indexInvariant), mainRectangle.getWidth(), sizeText);
			
			changesDone = true;
		} else if (id.equals(ID_CLASS_FIELDS_TEXT)) {
			//int index = getIndex(context.getGraphicsAlgorithm());
			Graphiti.getGaService().setLocationAndSize(ga, 0, beginYFieldHeader + sizeText * (indexField + 1) - 7, mainRectangle.getWidth(), sizeText);
			changesDone = true;
		} else if (id.equals(ID_CLASS_METHODS_PRE_TEXT)) {
			//int index = getIndex(context.getGraphicsAlgorithm());
			Graphiti.getGaService().setLocationAndSize(ga, 0, beginYMethodHeader + sizeText * (indexMethod + 1) - 7, mainRectangle.getWidth() / 3, sizeText);
			changesDone = true;
		} else if (id.equals(ID_CLASS_METHODS_POST_TEXT)) {
			//int index = getIndex(context.getGraphicsAlgorithm());
			Graphiti.getGaService().setLocationAndSize(ga, third, beginYMethodHeader + sizeText * (indexMethod + 1) - 7, mainRectangle.getWidth() / 3, sizeText);
			changesDone = true;
		} else if (id.equals(ID_CLASS_METHODS_SIGNATURE_TEXT)) {
			//int index = getIndex(context.getGraphicsAlgorithm());
			Graphiti.getGaService().setLocationAndSize(ga, third * 2, beginYMethodHeader + sizeText * (indexMethod + 1) - 7, mainRectangle.getWidth() / 3, sizeText);
			changesDone = true;
		} else if (id.equals(ID_IMAGE_PROVEN)) {
			Graphiti.getGaService().setLocationAndSize(ga, mainRectangle.getWidth() - 20, 10, 10, 10);
			changesDone = true;
		} else if (id.equals(ID_CLASS_INVARIANTS_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, third, positionHeader, third, sizeHeader);
			changesDone = true;
		} else if (id.equals(ID_CLASS_FIELDS_HEADER)) {
			//Graphiti.getGaService().setLocationAndSize(ga, third, positionHeader, third, sizeHeader);
			Graphiti.getGaService().setLocationAndSize(ga, third, beginYFieldHeader, third, sizeHeader);
			changesDone = true;
		} else if (id.equals(ID_CLASS_METHODS_HEADER)) {
			//Graphiti.getGaService().setLocationAndSize(ga, third, positionHeader, third, sizeHeader);
			Graphiti.getGaService().setLocationAndSize(ga, third, beginYMethodHeader, third, sizeHeader);
			changesDone = true;
		} else if (id.equals(ID_HOR1_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService()
					.createPointList(new int[] { 0, positionHeader, mainRectangle.getWidth(), positionHeader });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if (id.equals(ID_HOR2_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(new int[] { 0, positionHeader + sizeHeader,
					mainRectangle.getWidth(), positionHeader + sizeHeader });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if (id.equals(ID_HOR3_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(new int[] { 0, beginYFieldHeader,
					mainRectangle.getWidth(), beginYFieldHeader });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if (id.equals(ID_HOR4_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(new int[] { 0, beginYFieldHeader + 20,
					mainRectangle.getWidth(), beginYFieldHeader + 20 });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if (id.equals(ID_HOR5_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(new int[] { 0, beginYMethodHeader,
					mainRectangle.getWidth(), beginYMethodHeader });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		}  else if (id.equals(ID_HOR6_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(new int[] { 0, beginYMethodHeader + 20,
					mainRectangle.getWidth(), beginYMethodHeader + 20});
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		}
		
		
		/* else if (id.equals(ID_VER1_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService()
					.createPointList(new int[] { third, positionHeader, third, mainRectangle.getHeight() });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if (id.equals(ID_VER2_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService()
					.createPointList(new int[] { third * 2, positionHeader, third * 2, mainRectangle.getHeight() });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		}
		*/

		return changesDone;
	}

	// TODO: CHECK NEEDED
	@Override
	protected IReason updateNeeded(IdUpdateContext context, String id) {		
		/*
		if (id.equals(ID_NAME_TEXT)) {
			MultiText nameText = (MultiText) context.getGraphicsAlgorithm();
			ModelClass domainObject = (ModelClass) context.getDomainObject();
			if (domainObject.getClass() == null || !domainObject.getClass().equals(nameText.getValue())) {
				return Reason.createTrueReason("Name differs. Expected: '" + domainObject.getClass() + "'");
			}
		} 
		*/
		return Reason.createFalseReason();
	}

	@Override
	protected boolean update(IdUpdateContext context, String id) {
		if (id.equals(ID_NAME_TEXT)) {
			MultiText nameText = (MultiText) context.getGraphicsAlgorithm();
			ModelClass domainObject = (ModelClass) context.getDomainObject();
			nameText.setValue(domainObject.getName());
			return true;
			
		} else if(id.equals(ID_MAIN_RECTANGLE)) {
			ModelClass modelClass = (ModelClass)context.getDomainObject();
			EList<Field> fields = modelClass.getFields();
			EList<Condition> invariants = modelClass.getClassInvariants();
			EList<Method> methods = modelClass.getMethods();
			
		
			

			//int size = invariants.size();
			int size = invariants.size() + fields.size() + methods.size() * 3;

			while (((ContainerShape) context.getPictogramElement()).getChildren().size() - 11 < size) {
				int newIndex = ((ContainerShape) context.getPictogramElement()).getChildren().size() - 11;
				
				if (ModelClassHelper.getObject() instanceof ConditionImpl) {
					if (newIndex < invariants.size()) {
						Condition invariant = invariants.get(invariants.size() - 1);
						Shape shapeText = Graphiti.getPeCreateService()
								.createShape((ContainerShape) context.getPictogramElement(), true);
						Text variableNameText = Graphiti.getGaService().createText(shapeText, invariant.getName());
						setId(variableNameText, ID_CLASS_INVARIANTS_TEXT);
						setIndex(variableNameText, newIndex);
						variableNameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
						variableNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
						link(shapeText, invariant);
					} else {
					
						Condition invariant = invariants.get(invariants.size() - 1);
						Shape shapeText = Graphiti.getPeCreateService()
							.createShape((ContainerShape) context.getPictogramElement(), true);
						Text variableNameText = Graphiti.getGaService().createText(shapeText, invariant.getName());
						setId(variableNameText, ID_CLASS_INVARIANTS_TEXT);
						setIndex(variableNameText, newIndex);
						variableNameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
						variableNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
						link(shapeText, invariant);
					}
					
				} else if (ModelClassHelper.getObject() instanceof FieldImpl) {
					if (newIndex < fields.size()) {
						Field field = fields.get(fields.size() - 1);
						Shape shapeText = Graphiti.getPeCreateService()
								.createShape((ContainerShape) context.getPictogramElement(), true);
						Text variableNameText = Graphiti.getGaService().createText(shapeText, field.getName());
						setId(variableNameText, ID_CLASS_FIELDS_TEXT);
						setIndex(variableNameText, newIndex);
						variableNameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
						variableNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
						link(shapeText, field);
					
					}  else {
							Field field = fields.get(fields.size() - 1);
							Shape shapeText = Graphiti.getPeCreateService()
									.createShape((ContainerShape) context.getPictogramElement(), true);
							Text variableNameText = Graphiti.getGaService().createText(shapeText, field.getName());
							setId(variableNameText, ID_CLASS_FIELDS_TEXT);
							setIndex(variableNameText, newIndex);
							variableNameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
							variableNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
							link(shapeText, field);
						}	
						
					} else if (ModelClassHelper.getObject() instanceof MethodImpl) {
						if (newIndex < methods.size()) {
							Method method = methods.get(methods.size() - 1);
							
							// Pre-Con
							Shape shapePreConditionText = Graphiti.getPeCreateService()
									.createShape((ContainerShape) context.getPictogramElement(), true);
							Text preConditionText = Graphiti.getGaService().createText(shapePreConditionText, method.getPrecondition().getName());
							setId(preConditionText, ID_CLASS_METHODS_PRE_TEXT);
							setIndex(preConditionText, newIndex);
							preConditionText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
							preConditionText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
							//link(shapePreConditionText, method.getPrecondition());
							link(shapePreConditionText, method);
							
							// Post-Con
							Shape shapePostConditionText = Graphiti.getPeCreateService()
									.createShape((ContainerShape) context.getPictogramElement(), true);
							Text postConditionText = Graphiti.getGaService().createText(shapePostConditionText, method.getPostcondition().getName());
							setId(postConditionText, ID_CLASS_METHODS_POST_TEXT);
							setIndex(postConditionText, newIndex + 1);
							postConditionText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
							postConditionText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
							//link(shapePostConditionText, method.getPostcondition());
							link(shapePostConditionText, method);
							
							//Signature
							Shape shapeSignatureText = Graphiti.getPeCreateService()
									.createShape((ContainerShape) context.getPictogramElement(), true);
							Text signatureText = Graphiti.getGaService().createText(shapeSignatureText, method.getSignature());
							setId(signatureText, ID_CLASS_METHODS_SIGNATURE_TEXT);
							setIndex(signatureText, newIndex + 2);
							signatureText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
							signatureText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
							link(shapeSignatureText, method);
							
	
						}  else {
							// Pre-Con
							Method method = methods.get(methods.size() - 1);
							Shape shapePreConditionText = Graphiti.getPeCreateService()
									.createShape((ContainerShape) context.getPictogramElement(), true);
							Text preConditionText = Graphiti.getGaService().createText(shapePreConditionText, method.getPrecondition().getName());
							setId(preConditionText, ID_CLASS_METHODS_PRE_TEXT);
							setIndex(preConditionText, newIndex);
							preConditionText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
							preConditionText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
							//link(shapePreConditionText, method.getPrecondition());
							link(shapePreConditionText, method);
							
							// Post-Con
							Shape shapePostConditionText = Graphiti.getPeCreateService()
									.createShape((ContainerShape) context.getPictogramElement(), true);
							Text postConditionText = Graphiti.getGaService().createText(shapePostConditionText, method.getPostcondition().getName());
							setId(postConditionText, ID_CLASS_METHODS_POST_TEXT);
							setIndex(postConditionText, newIndex + 1);
							postConditionText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
							postConditionText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
							//link(shapePostConditionText, method.getPostcondition());
							link(shapePostConditionText, method);
							
							//Signature
							Shape shapeSignatureText = Graphiti.getPeCreateService()
									.createShape((ContainerShape) context.getPictogramElement(), true);
							Text signatureText = Graphiti.getGaService().createText(shapeSignatureText, method.getSignature());
							setId(signatureText, ID_CLASS_METHODS_SIGNATURE_TEXT);
							setIndex(signatureText, newIndex + 2);
							signatureText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
							signatureText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
							link(shapeSignatureText, method);
						}	
							
					}
				}
	
			return true;
		
			
		}
		
	
		return false;
	}
	
	public void setValue(String value, IDirectEditingContext context) {
		ModelClass modelClass = (ModelClass) getBusinessObjectForPictogramElement(context.getPictogramElement());
		modelClass.setName(value);
		updatePictogramElement(context.getPictogramElement());
	}

}





