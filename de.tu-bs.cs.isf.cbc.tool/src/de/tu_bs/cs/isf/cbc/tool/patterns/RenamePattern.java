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

import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.Rename;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;

/**
 * Class that creates the graphical representation of Methods
 * @author Tobias
 *
 */
public class RenamePattern extends IdPattern implements IPattern {
	
	private static final String ID_RENAME_FUNCTION = "functionName";
	private static final String ID_RENAME_TYPE = "functionType";
	private static final String ID_RENAME_NEW = "newFunctionName";

	/**
	 * Constructor of the class
	 */
	public RenamePattern() {
		super();
	}
	
	@Override
	public String getCreateName() {
		return "RenameFunction";
	}
	
	@Override
	public String getCreateDescription() {
		return "Rename a function.";
	}

	@Override
	public boolean isMainBusinessObjectApplicable(Object mainBusinessObject) {
		return mainBusinessObject instanceof Rename;
	}

	@Override
	public boolean canCreate(ICreateContext context) {
		return getBusinessObjectForPictogramElement(context.getTargetContainer()) instanceof Renaming;
	}

	@Override
	public Object[] create(ICreateContext context) {
		Renaming renaming = (Renaming) getBusinessObjectForPictogramElement(context.getTargetContainer());
		Rename rename = CbcmodelFactory.eINSTANCE.createRename();
		rename.setFunction("Exp.exp");
		rename.setType("boolean");
		rename.setNewName("exp");
		renaming.getRename().add(rename);
		updatePictogramElement(context.getTargetContainer());
		return new Object[] { rename };
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
        return (bo instanceof Rename);
    }
 
    public IReason updateNeeded(IdUpdateContext context, String id) {
    	Text nameText = (Text) context.getPictogramElement().getGraphicsAlgorithm();
		Rename domainObject = (Rename) getBusinessObjectForPictogramElement(context.getPictogramElement());
    	if (id.equals(ID_RENAME_FUNCTION)) {
    		if (domainObject.getFunction() == null || !domainObject.getFunction().equals(nameText.getValue())) {
    			return Reason.createTrueReason("Name differs. Expected: '" + domainObject.getFunction() + "'");
    		}
    	} else if (id.equals(ID_RENAME_TYPE)) {
    		if (domainObject.getType() == null || !domainObject.getType().equals(nameText.getValue())) {
    			return Reason.createTrueReason("Name differs. Expected: '" + domainObject.getType() + "'");
    		}
    	} else if (id.equals(ID_RENAME_NEW)) {
    		if (domainObject.getNewName() == null || !domainObject.getNewName().equals(nameText.getValue())) {
    			return Reason.createTrueReason("Name differs. Expected: '" + domainObject.getNewName() + "'");
    		}
    	}
		return Reason.createFalseReason();
    }
 
    public boolean update(IdUpdateContext context, String id) {
    	Text nameText = (Text) context.getPictogramElement().getGraphicsAlgorithm();
    	Rename domainObject = (Rename) getBusinessObjectForPictogramElement(context.getPictogramElement());
    	if (id.equals(ID_RENAME_FUNCTION)) {
    		nameText.setValue(domainObject.getFunction());
    	} else if (id.equals(ID_RENAME_TYPE)) {
    		nameText.setValue(domainObject.getType());
    	} else if (id.equals(ID_RENAME_NEW)) {
    		nameText.setValue(domainObject.getNewName());
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
		if (domainObject instanceof Rename && ga instanceof Text 
				&& (getId(ga).equals(ID_RENAME_FUNCTION) || getId(ga).equals(ID_RENAME_TYPE) || getId(ga).equals(ID_RENAME_NEW))) {
			return true;
		}
		return false;
	}
	

	@Override
	public String getInitialValue(IDirectEditingContext context) {
		Rename rename = (Rename) getBusinessObjectForPictogramElement(context.getPictogramElement());
		if (getId(context.getPictogramElement().getGraphicsAlgorithm()).equals(ID_RENAME_FUNCTION)) {
			return rename.getFunction();
		} else if (getId(context.getPictogramElement().getGraphicsAlgorithm()).equals(ID_RENAME_TYPE)) {
			return rename.getType();
		} else if (getId(context.getPictogramElement().getGraphicsAlgorithm()).equals(ID_RENAME_NEW)) {
			return rename.getNewName();
		}
		return "Something went wrong";
	}

	@Override
	public String checkValueValid(String value, IDirectEditingContext context) {
		if (value == null || value.length() == 0) {
			return "Text field must not be empty.";
		}
		return null;
	}

	@Override
	public void setValue(String value, IDirectEditingContext context) {
		Rename rename = (Rename) getBusinessObjectForPictogramElement(context.getPictogramElement());
		if (getId(context.getPictogramElement().getGraphicsAlgorithm()).equals(ID_RENAME_FUNCTION)) {
			rename.setFunction(value);
		} else if (getId(context.getPictogramElement().getGraphicsAlgorithm()).equals(ID_RENAME_TYPE)) {
			rename.setType(value);
		} else if (getId(context.getPictogramElement().getGraphicsAlgorithm()).equals(ID_RENAME_NEW)) {
			rename.setNewName(value);
		}
		updatePictogramElement(context.getPictogramElement());
	}
	
	@Override
	public void delete(IDeleteContext context) {
		Shape shape = (Shape) context.getPictogramElement();
		ContainerShape container = shape.getContainer();
		
		Rename rename = (Rename) getBusinessObjectForPictogramElement(context.getPictogramElement());
		if (rename.eContainer() != null && rename.eContainer() instanceof Renaming) {
			int indexToDelete = getIndex(shape.getGraphicsAlgorithm());
			List<Shape> shapesToDelete = new ArrayList<Shape>();
			for (Shape childShape : container.getChildren()) {
				if (getIndex(childShape.getGraphicsAlgorithm()) == indexToDelete) {
					shapesToDelete.add(childShape);
				}
			}
			for (Shape childShape : container.getChildren()) {
				if (getIndex(childShape.getGraphicsAlgorithm()) > indexToDelete) {
					setIndex(childShape.getGraphicsAlgorithm(), getIndex(childShape.getGraphicsAlgorithm()) - 1);
				}
			}
			super.delete(context);
			for (Shape shapeToDelete : shapesToDelete) {
				EcoreUtil.delete(shapeToDelete, true);
			}
			layoutPictogramElement(container);
		}
	}

}

