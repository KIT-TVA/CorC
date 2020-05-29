package de.tu_bs.cs.isf.taxonomy.graphiti.patterns;

import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.context.IDeleteContext;
import org.eclipse.graphiti.features.context.IDirectEditingContext;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.MultiText;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.pattern.IPattern;
import org.eclipse.graphiti.pattern.id.IdLayoutContext;
import org.eclipse.graphiti.pattern.id.IdPattern;
import org.eclipse.graphiti.pattern.id.IdUpdateContext;

import de.tu_bs.cs.isf.taxonomy.model.taxonomy.Condition;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.GlobalConditions;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.TaxonomyFactory;
import de.tu_bs.cs.isf.toolkit.support.compare.CompareMethodBodies;

/**
 * Class that creates the graphical representation of Conditions
 * @author Tobias
 *
 */
public class ConditionPattern extends IdPattern implements IPattern {

//	private static final String ID_NAME_TEXT = "conditionNameText";
//	private static final String ID_MAIN_RECTANGLE = "mainRectangle";


	/**
	 * Constructor of the class
	 */
	public ConditionPattern() {
		super();
	}
	
	@Override
	public String getCreateName() {
		return "Condition";
	}
	
	@Override
	public String getCreateDescription() {
		return "Create a Condition.";
	}

	@Override
	public boolean isMainBusinessObjectApplicable(Object mainBusinessObject) {
		return mainBusinessObject instanceof Condition;
	}

	@Override
	public boolean canCreate(ICreateContext context) {
		return getBusinessObjectForPictogramElement(context.getTargetContainer()) instanceof GlobalConditions;
	}
	
	@Override
	public Object[] create(ICreateContext context) {
		Condition condition = TaxonomyFactory.eINSTANCE.createCondition();
		condition.setName("{}");
		GlobalConditions conditions = (GlobalConditions) getBusinessObjectForPictogramElement(context.getTargetContainer());
		conditions.getConditions().add(condition);
		updatePictogramElement(context.getTargetContainer());
		return new Object[] { condition };
	}

	@Override
	public boolean canAdd(IAddContext context) {
		return super.canAdd(context) && context.getTargetContainer() instanceof GlobalConditions;
	}

	@Override
	public PictogramElement doAdd(IAddContext context) {
		return null;
	}

	@Override
	protected boolean layout(IdLayoutContext context, String id) {
		return false;
	}
	
	@Override
	protected IReason updateNeeded(IdUpdateContext context, String id) {
		if(context.getGraphicsAlgorithm() instanceof MultiText) {
			MultiText nameText = (MultiText) context.getGraphicsAlgorithm();
			Condition domainObject = (Condition) context.getDomainObject();
			if (domainObject.getName() == null || !(domainObject.getName().equals(nameText.getValue()) || nameText.getValue().equals("{" + domainObject.getName() + "}")) ) {
				return Reason.createTrueReason("Name differs. Expected: '" + domainObject.getName() + "'");
			}
		}
		

		return Reason.createFalseReason();
	}

	@Override
	protected boolean update(IdUpdateContext context, String id) {
		if(context.getGraphicsAlgorithm() instanceof MultiText) {
			MultiText nameText = (MultiText) context.getGraphicsAlgorithm();
			Condition domainObject = (Condition) context.getDomainObject();
			nameText.setValue(domainObject.getName());
			return true;
		}
		return false;
	}

	@Override
	public int getEditingType() {
		return TYPE_MULTILINETEXT;
	}

	@Override
	public boolean canDirectEdit(IDirectEditingContext context) {
		Object domainObject = getBusinessObjectForPictogramElement(context.getPictogramElement());
		GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
		if (domainObject instanceof Condition && ga instanceof MultiText) {
			return true;
			
		}
		return false;
	}

	@Override
	public String getInitialValue(IDirectEditingContext context) {
		Condition condition = (Condition) getBusinessObjectForPictogramElement(context.getPictogramElement());
		return condition.getName();
	}

	@Override
	public String checkValueValid(String value, IDirectEditingContext context) {
		if (value == null) {
			return "Condition must not be empty";
		}
		if (value.length() > 0 && (value.contains("forall") || value.contains("exists"))) {
			return null;
		} else if (value.length() > 0 && !CompareMethodBodies.readAndTestAssertWithJaMoPP(value.replaceAll("->", "&"))) {
			return "Condition has not the correct syntax.";
		}
		return null;
	}

	@Override
	public void setValue(String value, IDirectEditingContext context) {
		Condition condition = (Condition) getBusinessObjectForPictogramElement(context.getPictogramElement());
		condition.setName(value);
		updatePictogramElement(context.getPictogramElement());
	}
	
	@Override
	public void delete(IDeleteContext context) {
		Shape shape = (Shape) context.getPictogramElement();
		ContainerShape container = shape.getContainer();
		
		Condition condition = (Condition) getBusinessObjectForPictogramElement(context.getPictogramElement());
		if (condition.eContainer() != null && condition.eContainer() instanceof GlobalConditions) {
			int indexToDelete = getIndex(shape.getGraphicsAlgorithm());
			
			for (Shape childShape : container.getChildren()) {
				if (getIndex(childShape.getGraphicsAlgorithm()) > indexToDelete) {
					setIndex(childShape.getGraphicsAlgorithm(), getIndex(childShape.getGraphicsAlgorithm()) - 1);
				}
			}
			super.delete(context);
			layoutPictogramElement(container);
		} else {
			super.delete(context);
		}
	}
}

