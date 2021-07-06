package de.tu_bs.cs.isf.cbcclass.tool.patterns;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.context.IDeleteContext;
import org.eclipse.graphiti.features.context.IDirectEditingContext;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.MultiText;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.algorithms.impl.TextImpl;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.mm.pictograms.impl.ShapeImpl;
import org.eclipse.graphiti.pattern.IPattern;
import org.eclipse.graphiti.pattern.id.IdLayoutContext;
import org.eclipse.graphiti.pattern.id.IdPattern;
import org.eclipse.graphiti.pattern.id.IdUpdateContext;
import org.eclipse.graphiti.services.Graphiti;

import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.ModelClass;
import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.StrengthWeakStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.AbstractStatementImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.CompositionStatementImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.ConditionImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.ReturnStatementImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.SkipStatementImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.StrengthWeakStatementImpl;
import helper.ModelClassHelper;
import helper.ShapeWithText;

public class InvariantClassPattern extends IdPattern implements IPattern {

	/**
	 * Constructor of the class
	 */
	public InvariantClassPattern() {
		super();
	}

	@Override
	public String getCreateName() {
		return "Class Invariant";
	}

	@Override
	public String getCreateDescription() {
		return "Create a Class Invariant.";
	}

	@Override
	public boolean isMainBusinessObjectApplicable(Object mainBusinessObject) {
		return mainBusinessObject instanceof Condition;
	}

	@Override
	public boolean canCreate(ICreateContext context) {
		return getBusinessObjectForPictogramElement(context.getTargetContainer()) instanceof ModelClass;
	}
	
	@Override
	public Object[] create(ICreateContext context) {
		ModelClass modelClass = (ModelClass) getBusinessObjectForPictogramElement(context.getTargetContainer());
		
		Condition condition = CbcmodelFactory.eINSTANCE.createCondition();
		condition.setName("{condition}");
		
		modelClass.getClassInvariants().add(condition);
		ModelClassHelper.setObject(condition);
		
		updatePictogramElement(context.getTargetContainer());
		return new Object[] { condition };
	}
	
	@Override
	public boolean canAdd(IAddContext context) {
		return super.canAdd(context) && context.getTargetContainer() instanceof Condition;
	}
	
	public PictogramElement doAdd(IAddContext context) {
		return null;
	}
	
	@Override
	protected boolean layout(IdLayoutContext context, String id) {
		
		return false;
	}
	
	@Override
	protected IReason updateNeeded(IdUpdateContext context, String id) {
		if (context.getGraphicsAlgorithm() instanceof MultiText) {
			MultiText nameText = (MultiText) context.getGraphicsAlgorithm();
			Condition domainObject = (Condition) context.getDomainObject();
			if (domainObject.getName() == null || !(domainObject.getName().equals(nameText.getValue())
					|| nameText.getValue().equals("{" + domainObject.getName() + "}"))) {
				return Reason.createTrueReason("Name differs. Expected: '" + domainObject.getName() + "'");
			}
		}

		return Reason.createFalseReason();
	}
	
	@Override
	protected boolean update(IdUpdateContext context, String id) {
		if (context.getGraphicsAlgorithm() instanceof MultiText) {
			MultiText nameText = (MultiText) context.getGraphicsAlgorithm();
			Condition domainObject = (Condition) context.getDomainObject();
			// TODO:
			if (domainObject.eContainer().getClass().equals(AbstractStatementImpl.class)
					|| domainObject.eContainer().getClass().equals(SkipStatementImpl.class)
					|| domainObject.eContainer().getClass().equals(ReturnStatementImpl.class)
					|| domainObject.eContainer().getClass().equals(StrengthWeakStatementImpl.class)
					|| domainObject.eContainer().getClass().equals(CompositionStatementImpl.class)) {
				nameText.setValue("{" + domainObject.getName() + "}");
			} else {
				nameText.setValue(domainObject.getName());
			}
			return true;
		}
		return false;
	}
	
	
	// CHECK
	/*
	@Override
	public boolean canDirectEdit(IDirectEditingContext context) {
		Object domainObject = getBusinessObjectForPictogramElement(context.getPictogramElement());
		GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
		if (domainObject instanceof Condition && ga instanceof MultiText) {
			Condition condition = ((Condition) domainObject);
			if (condition.eContainer() instanceof AbstractStatement) {
				AbstractStatement statement = (AbstractStatement) condition.eContainer();
				if (statement.eContainer() instanceof CbCFormula || statement instanceof StrengthWeakStatement) {
					return true;
				} else if (statement.getPreCondition() != null && statement.getPreCondition().equals(condition)) {
					return false;
				} else if (statement.getPostCondition() != null && statement.getPostCondition().equals(condition)) {
					return false;
				}
				return true;
			} else if (condition.eContainer() instanceof GlobalConditions) {
				return true;
			}

		}
		return false;
	}
	*/
	
	@Override
	public int getEditingType() {
	    return TYPE_TEXT;
	}
	
	@Override
	public boolean canDirectEdit(IDirectEditingContext context) {
		Object domainObject = getBusinessObjectForPictogramElement(context.getPictogramElement());
		GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
		if (domainObject instanceof Condition && ga instanceof Text) {
			return true;
		}
		return false;
	}
	
	
	
	@Override
	public String getInitialValue(IDirectEditingContext context) {
		System.out.println("Invariant.getInitialValue");
		Condition condition = (Condition) getBusinessObjectForPictogramElement(context.getPictogramElement());
		return System.getProperty("line.separator") + condition.getName() + System.getProperty("line.separator");
	}
	
	@Override
	public String checkValueValid(String value, IDirectEditingContext context) {
		if (value == null) {
			return "Condition must not be empty";
		}
		if (value.length() > 0) {
			if (value.contains("modifiable(")) {
				String[] valueSplitted = value.split(";");
				if (valueSplitted.length > 1
						&& !valueSplitted[0].trim().matches("modifiable\\(\\w+[\\[\\]\\*]*(,\\w+[\\[\\]\\*]*)*\\)")) {
					return null;// "modifiable variables must be defined as: modifiable(x,y,z,...);";
				} else if ((value.contains("forall") || value.contains("exists"))) {
					return null;
				}
			}
		}
		// else if (value.length() > 0 &&
		// !CompareMethodBodies.readAndTestAssertWithJaMoPP(value.replaceAll("<->",
		// "&").replaceAll("->", "&"))) {
		// return "Condition has not the correct syntax.";
		// }
		return null;
	}
	
	@Override
	public void setValue(String value, IDirectEditingContext context) {
		
		Condition condition = (Condition) getBusinessObjectForPictogramElement(context.getPictogramElement());
		condition.setName(value.trim());
		
		// TODO: CHECK
		/*
		if (!(condition.eContainer() instanceof GlobalConditions)) {
			UpdateConditionsOfChildren.updateConditionsofChildren(condition);
		} else if (condition.eContainer() instanceof GlobalConditions) {
			CbCFormula formula = null;
			for (Shape shape : getDiagram().getChildren()) {
				Object obj = getBusinessObjectForPictogramElement(shape);
				if (obj instanceof CbCFormula) {
					formula = (CbCFormula) obj;
					formula.setProven(false);
					UpdateConditionsOfChildren.setAllStatementsUnproven(formula.getStatement());
				}
			}
		}
		*/

		ShapeImpl shape = (ShapeImpl)context.getPictogramElement();
		TextImpl text = (TextImpl)shape.getGraphicsAlgorithm();
		text.setValue(condition.getName());
		
		
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
		
		/*
		CbCFormula formula = null;
		for (Shape childShape : getDiagram().getChildren()) {
			Object obj = getBusinessObjectForPictogramElement(childShape);
			if (obj instanceof CbCFormula) {
				formula = (CbCFormula) obj;
			}
		}
		formula.setProven(false);
		*/
		
		//TODO: CHECK
		//UpdateConditionsOfChildren.setAllStatementsUnproven(formula.getStatement());
		
	
	}
	

	
}
