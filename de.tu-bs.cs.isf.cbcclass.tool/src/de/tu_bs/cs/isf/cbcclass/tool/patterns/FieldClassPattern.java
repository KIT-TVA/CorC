package de.tu_bs.cs.isf.cbcclass.tool.patterns;

import java.util.List;

import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.context.IDirectEditingContext;
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

import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.CbcclassFactory;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.ModelClass;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;

public class FieldClassPattern extends IdPattern implements IPattern {
	
	
	private static final String ID_FIELD_TEXT = "fieldText";
	private static final String ID_MAIN_RECTANGLE = "mainRectangle";
	
	// line:
	private static final String ID_HOR1_LINE = "hor1Line";
	
	public FieldClassPattern() {
		super();
	}
	
	@Override
	public String getCreateName() {
		return "Class Field";
	}

	@Override
	public String getCreateDescription() {
		return "Create a Class Field";
	}
	

	@Override
	protected PictogramElement doAdd(IAddContext context) {
		/*
		Diagram targetDiagram = (Diagram) context.getTargetContainer();
		Field addedField = (Field) context.getNewObject();
		
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
		link(outerContainerShape, addedField);
		
		
		// method name
		Shape nameTextShape = peCreateService.createShape(outerContainerShape, true);
		Text fieldNameText = gaService.createText(nameTextShape, "");
		setId(fieldNameText, ID_FIELD_TEXT);
		fieldNameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		fieldNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
				
		Shape textShapeMethod = peCreateService.createShape(outerContainerShape, false);
		Text nameTextMethod = gaService.createText(textShapeMethod, "ClassField");
		setId(nameTextMethod, ID_FIELD_TEXT);
		nameTextMethod.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		nameTextMethod.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		nameTextMethod.setFont(headerFont);
				
		// line:
		Shape hor1Shape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor1line = gaService.createPolyline(hor1Shape);
		setId(hor1line, ID_HOR1_LINE);

		link(outerContainerShape, addedField);
		link(getDiagram(), addedField);
		link(nameTextShape, addedField);

		return outerContainerShape;
		*/
		return null;
	}
	
	
	@Override
	public boolean canCreate(ICreateContext context) {
		return getBusinessObjectForPictogramElement(context.getTargetContainer()) instanceof ModelClass;
	}
	
	
	// Create all elements
	public Object[] create (ICreateContext context) {
		ModelClass modelClass = (ModelClass) getBusinessObjectForPictogramElement(context.getTargetContainer());
		
		de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field field = CbcclassFactory.eINSTANCE.createField();
		field.setName("field");
		field.setType("int[]");
		
		modelClass.getFields().add(field);
		updatePictogramElement(context.getTargetContainer());

		return new Object[] { field };
			
	}
	
	
	@Override
	public int getEditingType() {
	    return TYPE_TEXT;
	}
	
	public boolean canAdd(IAddContext context) {
		return false;
	}
		
		

	@Override
	protected boolean layout(IdLayoutContext context, String id) {
		return false;
	}
	
	public boolean canUpdate(IdUpdateContext context) {
        Object bo = getBusinessObjectForPictogramElement(context.getPictogramElement());
        return (bo instanceof Field);
    }

	@Override
	protected IReason updateNeeded(IdUpdateContext context, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean update(IdUpdateContext context, String id) {
		if (id.equals(ID_FIELD_TEXT)) {
			Text nameText = (Text) context.getGraphicsAlgorithm();
			Field domainObject = (Field) context.getDomainObject();
			nameText.setValue(domainObject.getName());
			return true;
		}
		return false;
	}
	
	/*
	@Override
	public boolean canDirectEdit(IDirectEditingContext context) {
		Object domainObject = getBusinessObjectForPictogramElement(context.getPictogramElement());
		GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
		if (domainObject instanceof Field && ga instanceof Text) {
			return true;
		}
		return false;
	}
	
	*/
	
	@Override
	public boolean canDirectEdit(IDirectEditingContext context) {
		Object domainObject = getBusinessObjectForPictogramElement(context.getPictogramElement());
		GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
		if (domainObject instanceof JavaVariable && ga instanceof Text) {
			return true;
		}
		return false;
	}
	
	@Override
	public String getInitialValue(IDirectEditingContext context) {
		Field field = (Field) getBusinessObjectForPictogramElement(context.getPictogramElement());
		return field.getName();
	}

	@Override
	public boolean isMainBusinessObjectApplicable(Object mainBusinessObject) {
		// TODO Auto-generated method stub
		return mainBusinessObject instanceof Field;
	}

}
