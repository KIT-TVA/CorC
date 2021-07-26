package de.tu_bs.cs.isf.cbcclass.tool.patterns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.context.IDirectEditingContext;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
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
import org.eclipse.graphiti.mm.pictograms.impl.ShapeImpl;
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
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import helper.ModelClassHelper;

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
		return "Create a class field: \n[modifier] [static] [final] type name \n[] means optional.\nExample: private final int[] number";
	}
	

	@Override
	protected PictogramElement doAdd(IAddContext context) {

		return null;
	}
	
	
	@Override
	public boolean canCreate(ICreateContext context) {
		return getBusinessObjectForPictogramElement(context.getTargetContainer()) instanceof ModelClass;
	}
	
	
	@Override
	public boolean isMainBusinessObjectApplicable(Object mainBusinessObject) {
		// TODO Auto-generated method stub
		return mainBusinessObject instanceof Field;
	}
	
	
	// Create all elements
	public Object[] create (ICreateContext context) {
		ModelClass modelClass = (ModelClass) getBusinessObjectForPictogramElement(context.getTargetContainer());
		
		de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field field = CbcclassFactory.eINSTANCE.createField();
		field.setName("int[] field");
		field.setType("int[]");
		
		modelClass.getFields().add(field);
		ModelClassHelper.setObject(field);
		
		updatePictogramElement(context.getTargetContainer());
		return new Object[] { field };
			
	}
	
	
	@Override
	public void setValue(String value, IDirectEditingContext context) {
		
		Field field = (Field) getBusinessObjectForPictogramElement(context.getPictogramElement());
		field.setName(value.trim());

		ShapeImpl shape = (ShapeImpl)context.getPictogramElement();
		TextImpl text = (TextImpl)shape.getGraphicsAlgorithm();
		text.setValue(field.getName());
		
		
		updatePictogramElement(context.getPictogramElement());
	
	}
	
	
	@Override
	public int getEditingType() {
	    return TYPE_TEXT;
	}
	
	public boolean canAdd(IAddContext context) {
		return false;
	}
		
	
	/*
	// TODO
	@Override
	public String checkValueValid(String value, IDirectEditingContext context) {
		if (value == null || value.length() == 0) {
			return "Variable must not be empty";
		} 
		
		
		else if (value.length() > 0 && !value.toLowerCase().matches(
				
				"(static\\s)?"+")?(int|char|float|long|boolean|byte|short|double|([A-Za-z]\\w*))(\\[\\])?\\s[a-zA-Z]\\w*")) {
			return "Variable must contain a kind, a type and a name";
		}
		return null;
	}
	*/
	
	
	@Override
	public String checkValueValid(String value, IDirectEditingContext context) {
		if (value == null || value.length() == 0) {
			return "Variable must not be empty";
		} 
		
		
		else if (value.length() > 0 && !value.toLowerCase().matches(
				
				/*
				"(public\\s|private\\s|protected\\s)?" +
				"(static\\s)?" +
				"(final\\s)?" +		
				"(int|char|float|long|boolean|byte|short|double|([A-Za-z]\\w*))(\\[\\])?\\s[a-zA-Z]\\w*")) {
				*/
				
			
			
			"(public\\s|private\\s|protected\\s|)" +
					"(static\\s)?" +
					"(final\\s)?" +		
					"(int|char|float|long|boolean|byte|short|double|([A-Za-z]\\w*))(\\[\\])?\\s[a-zA-Z]\\w*")) {
			
			
			//return "Variable must contain a kind, a type and a name";
			return "Field must contain a type and a name";
		}
		
		
		if (hasKeywordsAsTypeOrName(value)) {
			return "Keywords aren't allowed as a data type or field name";
		} 
		
	
	
		
		return null;
	}
	
	
	private boolean hasKeywordsAsTypeOrName(String value) {
		
		//String[] keywords = {"public", "private", "protected", "static", "final"};
		ArrayList<String> keywords = new ArrayList<>(Arrays.asList("public", "private", "protected", "static", "final"));
		

		
		String[] tokens = value.split(" ");
		
		if (tokens.length > 1) {
			
			String lastValue = tokens[tokens.length -1 ].trim();
			String penultimateValue = tokens[tokens.length - 2].trim();
			
			if (keywords.contains(lastValue) || keywords.contains(penultimateValue)) {
				return true;
			} 
			
			return false;
		}
	
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

	// TODO:
	@Override
	protected IReason updateNeeded(IdUpdateContext context, String id) {
		// TODO Auto-generated method stub
		//return null;
		return Reason.createFalseReason();
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

	// TODO: change behavior
	@Override
	public boolean canDirectEdit(IDirectEditingContext context) {
		Object domainObject = getBusinessObjectForPictogramElement(context.getPictogramElement());
		GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
		if ((domainObject instanceof Field || domainObject instanceof JavaVariable) && ga instanceof Text) {
			return true;
		}
		return false;

	}
	
	@Override
	public String getInitialValue(IDirectEditingContext context) {
		Field field = (Field) getBusinessObjectForPictogramElement(context.getPictogramElement());
		return field.getName();
	}



}
