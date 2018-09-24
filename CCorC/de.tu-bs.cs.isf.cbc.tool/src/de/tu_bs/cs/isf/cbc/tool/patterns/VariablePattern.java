package de.tu_bs.cs.isf.cbc.tool.patterns;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.context.IDeleteContext;
import org.eclipse.graphiti.features.context.IDirectEditingContext;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.pattern.IPattern;
import org.eclipse.graphiti.pattern.id.IdLayoutContext;
import org.eclipse.graphiti.pattern.id.IdPattern;
import org.eclipse.graphiti.pattern.id.IdUpdateContext;

import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.Confidentiality;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.tool.helper.UpdateVariablesOfConditions;

/**
 * Class that creates the graphical representation of Methods
 * @author Tobias
 *
 */
public class VariablePattern extends IdPattern implements IPattern {

	private static final String ID_VARIABLE_NAME = "variableName";
	private static final String ID_VARIABLE_TYPE = "variableType";
	private static final String ID_VARIABLE_CONF = "variableConf";
	
	/**
	 * Constructor of the class
	 */
	public VariablePattern() {
		super();
	}
	
	@Override
	public String getCreateName() {
		return "Variable";
	}
	
	@Override
	public String getCreateDescription() {
		return "Create a variable.";
	}

	@Override
	public boolean isMainBusinessObjectApplicable(Object mainBusinessObject) {
		return mainBusinessObject instanceof JavaVariable;
	}

	@Override
	public boolean canCreate(ICreateContext context) {
		return getBusinessObjectForPictogramElement(context.getTargetContainer()) instanceof JavaVariables;
	}

	@Override
	public Object[] create(ICreateContext context) {
		JavaVariables variables = (JavaVariables) getBusinessObjectForPictogramElement(context.getTargetContainer());
		JavaVariable variable = CbcmodelFactory.eINSTANCE.createJavaVariable();
		variable.setName("a");
		variable.setType("int");
		variable.setConfidentiality(Confidentiality.HIGH);
		variables.getVariables().add(variable);
		updatePictogramElement(context.getTargetContainer());
		return new Object[] { variable };
	}

	@Override
	public boolean canAdd(IAddContext context) {
		return false;
	}

	@Override
	public PictogramElement doAdd(IAddContext context) {
		return null;
	}

	@Override
	protected boolean layout(IdLayoutContext context, String id) {
		return false;
	}
	
	public boolean canUpdate(IdUpdateContext context) {
        Object bo = getBusinessObjectForPictogramElement(context.getPictogramElement());
        return (bo instanceof JavaVariable);
    }
 
    public IReason updateNeeded(IdUpdateContext context, String id) {
    	Text nameText = (Text) context.getPictogramElement().getGraphicsAlgorithm();
    	JavaVariable domainObject = (JavaVariable) getBusinessObjectForPictogramElement(context.getPictogramElement());
    	if (id.equals(ID_VARIABLE_NAME)) {
    		if (domainObject.getName() == null || !domainObject.getName().equals(nameText.getValue())) {
    			return Reason.createTrueReason("Name differs. Expected: '" + domainObject.getName() + "'");
    		}
    	} else if (id.equals(ID_VARIABLE_TYPE)) {
    		if (domainObject.getType() == null || !domainObject.getType().equals(nameText.getValue())) {
    			return Reason.createTrueReason("Name differs. Expected: '" + domainObject.getType() + "'");
    		}
    	} else if (id.equals(ID_VARIABLE_CONF)) {
    		if (domainObject.getConfidentiality() == null || !domainObject.getConfidentiality().getName().toLowerCase().equals(nameText.getValue())) {
    			return Reason.createTrueReason("Name differs. Expected: '" + domainObject.getConfidentiality().getName().toLowerCase() + "'");
    		}
    	}
		return Reason.createFalseReason();
    }
 
    public boolean update(IdUpdateContext context, String id) {
    	Text nameText = (Text) context.getPictogramElement().getGraphicsAlgorithm();
    	JavaVariable domainObject = (JavaVariable) getBusinessObjectForPictogramElement(context.getPictogramElement());
    	if (id.equals(ID_VARIABLE_NAME)) {
    		nameText.setValue(domainObject.getName());
    	} else if (id.equals(ID_VARIABLE_TYPE)) {
    		nameText.setValue(domainObject.getType());
    	} else if (id.equals(ID_VARIABLE_CONF)) {
    		nameText.setValue(domainObject.getConfidentiality().getName().toLowerCase());
    	}
		return true;
    }
    
    @Override
	public int getEditingType() {
	    return TYPE_TEXT;
	}
	
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
		JavaVariable variable = (JavaVariable) getBusinessObjectForPictogramElement(context.getPictogramElement());
		if (getId(context.getPictogramElement().getGraphicsAlgorithm()).equals(ID_VARIABLE_NAME)) {
			return variable.getName();
		}
		else if (getId(context.getPictogramElement().getGraphicsAlgorithm()).equals(ID_VARIABLE_TYPE)) {
			return variable.getType();
		} else if (getId(context.getPictogramElement().getGraphicsAlgorithm()).equals(ID_VARIABLE_CONF)) {
			return variable.getConfidentiality().getName().toLowerCase();
		}
		return variable.getName();
	}

	@Override
	public String checkValueValid(String value, IDirectEditingContext context) {
		if (value == null || value.length() == 0) {
			return "Variable must not be empty";
		} 
		if (getId(context.getPictogramElement().getGraphicsAlgorithm()).equals(ID_VARIABLE_NAME)) {
			if (value.length() > 0 && !value.matches("[a-zA-Z]\\w*")) {
				return "Variable must contain a name";
			}
		}
		else if (getId(context.getPictogramElement().getGraphicsAlgorithm()).equals(ID_VARIABLE_TYPE)) {
			if (value.length() > 0 && !value.matches("[a-zA-Z]\\w*(\\[\\])?")) {
				return "Variable must contain a type";
			}
		} else if (getId(context.getPictogramElement().getGraphicsAlgorithm()).equals(ID_VARIABLE_CONF)) {
			if (!value.matches("(high|low)")) {
				return "Variable must have a confidentiality level";
			}
		}
		return null;
	}

	@Override
	public void setValue(String value, IDirectEditingContext context) {
		JavaVariable variable = (JavaVariable) getBusinessObjectForPictogramElement(context.getPictogramElement());
		if (getId(context.getPictogramElement().getGraphicsAlgorithm()).equals(ID_VARIABLE_NAME)) {
			variable.setName(value);
		}
		else if (getId(context.getPictogramElement().getGraphicsAlgorithm()).equals(ID_VARIABLE_TYPE)) {
			variable.setType(value);
		} else if (getId(context.getPictogramElement().getGraphicsAlgorithm()).equals(ID_VARIABLE_CONF)) {
			if (value.matches("high")) {
				variable.setConfidentiality(Confidentiality.HIGH);
			} else if (value.matches("low")) {
				variable.setConfidentiality(Confidentiality.LOW);
			}
			for (Shape shape : getDiagram().getChildren()) {
				Object obj = getBusinessObjectForPictogramElement(shape);
				if (obj instanceof CbCFormula) {
					CbCFormula formula = (CbCFormula) obj;
					UpdateVariablesOfConditions.updateConfidentiality(formula.getStatement(), Confidentiality.LOW);
				}
			}
		}
		updatePictogramElement(((Shape) context.getPictogramElement()));
	}
	
	@Override
	public void delete(IDeleteContext context) {
		Shape shape = (Shape) context.getPictogramElement();
		ContainerShape container = shape.getContainer();
		
		JavaVariable variable = (JavaVariable) getBusinessObjectForPictogramElement(context.getPictogramElement());
		if (variable.eContainer() != null && variable.eContainer() instanceof JavaVariables) {
			int indexToDelete = getIndex(shape.getGraphicsAlgorithm());
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
		}
	}

}

