package de.tu_bs.cs.isf.cbcclass.tool.patterns;

import java.util.List;
import org.eclipse.emf.common.util.EList;
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
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbcclass.tool.diagram.CbCClassImageProvider;


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
	private static final String ID_CLASS_METHODS_TEXT = "classMethodsText";
	private static final String ID_MAIN_RECTANGLE = "mainRectangle";
	private static final String ID_IMAGE_PROVEN = "imageproven";
	// Header:
	private static final String ID_CLASS_INVARIANTS_HEADER = "ivHeader";
	private static final String ID_CLASS_METHODS_HEADER = "mtHeader";
	private static final String ID_CLASS_FIELDS_HEADER = "fHeader";
	// lines:
	private static final String ID_HOR1_LINE = "hor1Line";
	private static final String ID_HOR2_LINE = "hor2Line";
	private static final String ID_VER1_LINE = "ver1Line";
	private static final String ID_VER2_LINE = "ver2Line";
	
	private MultiText invariantsText;
	private Shape textShapeInvariants;
	
	private Shape textShapeFields;
	private MultiText fieldsText;
	
	private Shape textShapeMethods;
	private MultiText methodsText;
	
	
	
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
		ModelClass modelClass = CbcclassFactory.eINSTANCE.createModelClass();
		modelClass.setName(getDiagram().getName());
		modelClass.setName("Model Class");
		
		
		//de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field field = CbcclassFactory.eINSTANCE.createField();
		//field.setName("field");
		//modelClass.getFields().add(field);

		
		//CbcmodelFactory.eINSTANCE.createCondition();
		
				
		//CbcclassFactory.eINSTANCE.createMethod();
		
		
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
		link(outerContainerShape, addedModelClass);
		
		
		/*
		// Invariants
		textShapeInvariants = peCreateService.createShape(outerContainerShape, true);
		invariantsText = gaService.createMultiText(textShapeInvariants, "");
		setId(invariantsText, ID_CLASS_INVARIANTS_TEXT);
		invariantsText.setValue("{" + "statement" + "}");
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

		Shape ver1LineShape = peCreateService.createShape(outerContainerShape, false);
		Polyline ver1Polyline = gaService.createPolyline(ver1LineShape);
		setId(ver1Polyline, ID_VER1_LINE);

		Shape ver2LineShape = peCreateService.createShape(outerContainerShape, false);
		Polyline ver2Polyline = gaService.createPolyline(ver2LineShape);
		setId(ver2Polyline, ID_VER2_LINE);

		peCreateService.createChopboxAnchor(textShapeFields);
		
		link(outerContainerShape, addedModelClass);
		link(textShapeName, addedModelClass);
		//link(textShapeFields, addedModelClass);
		//link(textShapeInvariants, addedModelClass);
		//link(textShapeMethods, addedModelClass);
		link(proveShape, addedModelClass);
		
		//link(textShapeFields, addedModelClass.getFields());
		//link(textShapeInvariants, addedModelClass.getClassInvariants());
		//link(textShapeMethods, addedModelClass.getMethods());
		
	
		return outerContainerShape;	

	}

	@Override
	protected boolean layout(IdLayoutContext context, String id) {
		boolean changesDone = false;

		GraphicsAlgorithm mainRectangle = context.getRootPictogramElement().getGraphicsAlgorithm();
		GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
		
		int third = mainRectangle.getWidth() / 3;
		// stable sizes from Name and Header save space when the diagram gets big!
		int sizeName = 30; // size from Formular block
		int sizeHeader = 20; // size from the Header
		int positionHeader = 40; // position where the Header is
		int sizeText = mainRectangle.getHeight() - positionHeader - sizeHeader; // size from the blocks (iv, fields, methods)
		int positionText = positionHeader + sizeHeader; // position from the blocks (pre, statement, post)
		
		ModelClass modelClass = (ModelClass) getBusinessObjectForPictogramElement(context.getRootPictogramElement());
		int size = modelClass.getClassInvariants().size();
		if (size >= 1) {
			sizeText = sizeText / (size + 1);
			
		}
		
		
		if (id.equals(ID_NAME_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, 5, mainRectangle.getWidth(), sizeName);
			changesDone = true;
		} else if (id.equals(ID_CLASS_INVARIANTS_TEXT)) {
			int index = getIndex(context.getGraphicsAlgorithm());
			Graphiti.getGaService().setLocationAndSize(ga, 0, positionText * (index + 1), third, sizeText);
			
			
			//Graphiti.getGaService().setLocationAndSize(ga, 0, positionText, third, sizeText);
			
			changesDone = true;
		} else if (id.equals(ID_CLASS_FIELDS_TEXT)) {
			int index = getIndex(context.getGraphicsAlgorithm());
			Graphiti.getGaService().setLocationAndSize(ga, third, positionText, third, sizeText);
			changesDone = true;
		} else if (id.equals(ID_CLASS_METHODS_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, third * 2, positionText, third, sizeText);
			changesDone = true;
		} else if (id.equals(ID_IMAGE_PROVEN)) {
			Graphiti.getGaService().setLocationAndSize(ga, mainRectangle.getWidth() - 20, 10, 10, 10);
			changesDone = true;
		} else if (id.equals(ID_CLASS_INVARIANTS_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, positionHeader, third, sizeHeader);
			changesDone = true;
		} else if (id.equals(ID_CLASS_FIELDS_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, third, positionHeader, third, sizeHeader);
			changesDone = true;
		} else if (id.equals(ID_CLASS_METHODS_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, third * 2, positionHeader, third, sizeHeader);
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
		} else if (id.equals(ID_VER1_LINE)) {
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
			
			String displayedText = "";
			for (Field field : fields) {
				displayedText += field.getType() + " " + field.getName() + "\n\n";
				
			}
			//fieldsText.setValue(displayedText);
			displayedText = "";
			
			int index = 0;
			for (Condition invariant : invariants) {
				//displayedText += invariant.getName() + "\n\n";
				
				Shape shapeText = Graphiti.getPeCreateService()
						.createShape((ContainerShape) context.getPictogramElement(), true);
				Text variableNameText = Graphiti.getGaService().createText(shapeText, invariant.getName());
				setId(variableNameText, ID_CLASS_INVARIANTS_TEXT);
				setIndex(variableNameText, index);
				variableNameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
				variableNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
				link(shapeText, invariant);
				index++;
				
			}
			
			/*
			invariantsText.setValue(displayedText);
			displayedText = "";
			*/
			
			
			for (Method method : methods) {
				displayedText += method.getSignature() + " " 
			+ method.getPrecondition().getName() + " " + method.getPostcondition().getName() +  "\n\n";
			}
			//methodsText.setValue(displayedText);
			
			return true;
		
			
		}
		
		
		/*else if(id.equals(ID_CLASS_FIELDS_TEXT)) {
			System.out.println("Aufruf updateMethod [classFields]");
			System.out.println(context.getClass());
			EList<Field> fields = ((ModelClass) context.getDomainObject()).getFields();
			System.out.println("updateMethd, fieldSize: " + fields.size());
			
			int newIndex = 0;
			if(fields.size() > 0) {
			Field field = fields.get(0);
			Shape shapeText = Graphiti.getPeCreateService()
					.createShape((ContainerShape) context.getPictogramElement(), true);
			Text fieldsNameText = Graphiti.getGaService().createText(shapeText, field.getName());
			setId(fieldsNameText, ID_CLASS_FIELDS_TEXT);
			setIndex(fieldsNameText, newIndex);
			fieldsNameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
			fieldsNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
			link(shapeText, field);
			
			}
			
		} else if(id.equals(ID_CLASS_INVARIANTS_TEXT)) {
			System.out.println("Aufruf updateMethod [classInvariants]");
		} else if(id.equals(ID_CLASS_METHODS_TEXT)) {
			System.out.println("Aufruf updateMethod [classMethods]");
		} */
		return false;
	}
	
	public void setValue(String value, IDirectEditingContext context) {
		ModelClass modelClass = (ModelClass) getBusinessObjectForPictogramElement(context.getPictogramElement());
		modelClass.setName(value);
		updatePictogramElement(context.getPictogramElement());
	}

}





