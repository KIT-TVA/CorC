package de.tu_bs.cs.isf.cbc.tool.patterns;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.context.IDeleteContext;
import org.eclipse.graphiti.features.context.IDirectEditingContext;
import org.eclipse.graphiti.features.context.IRemoveContext;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.MultiText;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.pattern.IPattern;
import org.eclipse.graphiti.pattern.id.IdLayoutContext;
import org.eclipse.graphiti.pattern.id.IdPattern;
import org.eclipse.graphiti.pattern.id.IdUpdateContext;

import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.ModelClass;
import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.StrengthWeakStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.AbstractStatementImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.CompositionStatementImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.ReturnStatementImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.SkipStatementImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.StrengthWeakStatementImpl;
import de.tu_bs.cs.isf.cbc.parser.exceptions.IFbCException;
import de.tu_bs.cs.isf.cbc.tool.features.TestStatement;
import de.tu_bs.cs.isf.cbc.tool.helper.GetProjectUtil;
import de.tu_bs.cs.isf.cbc.tool.helper.UpdateConditionsOfChildren;
import de.tu_bs.cs.isf.cbc.tool.helper.UpdateContractsToProve;
import de.tu_bs.cs.isf.cbc.tool.helper.UpdateInformationFlow;
import de.tu_bs.cs.isf.cbc.tool.helper.UpdateMethodCallsToProve;
import de.tu_bs.cs.isf.cbc.tool.helper.UpdateOriginalCallsToProve;
import de.tu_bs.cs.isf.lattice.Lattice;
import de.tu_bs.cs.isf.lattice.Lattices;

/**
 * Class that creates the graphical representation of Conditions
 * 
 * @author Tobias
 *
 */
public class ConditionPattern extends IdPattern implements IPattern {

	private static final String ID_PRE_MOD = "preConditionModifiables";
	private static final String ID_POST_MOD = "postConditionModifiables";
	private static final String ID_OTHER_MOD = "otherConditionModifiables";

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
		Condition condition = CbcmodelFactory.eINSTANCE.createCondition();
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
		if (context.getGraphicsAlgorithm() instanceof MultiText) {
			MultiText nameText = (MultiText) context.getGraphicsAlgorithm();
			Condition domainObject = (Condition) context.getDomainObject();
			if (id.equals(ID_PRE_MOD) || id.equals(ID_POST_MOD) || id.equals(ID_OTHER_MOD)) {
				String modString = "";
				for (String s : domainObject.getModifiables()) {
					modString += s + ", ";
				}
				modString = "modifiable(" + (modString.equals("") ? "" : modString.substring(0, modString.length() - 2)) + ");";
				if (!(modString.equals(nameText.getValue()))) {
					return Reason.createTrueReason("Name differs. Expected: '" + modString + "'");
				} else {
					return Reason.createFalseReason();
				}
			}
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
			if (id.equals(ID_PRE_MOD) || id.equals(ID_POST_MOD) || id.equals(ID_OTHER_MOD)) {
				if (!nameText.getValue().equals("")) {
					String modString = "";
					for (String s : domainObject.getModifiables()) {
						modString += s + ", ";
					}
					nameText.setValue("modifiable(" + (modString.equals("") ? "" : modString.substring(0, modString.length() - 2)) + ");");
				}
				return true;
			}
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

	@Override
	public int getEditingType() {
		return TYPE_MULTILINETEXT;
	}

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
		if (domainObject instanceof Condition && ga instanceof Text) {
			Condition condition = ((Condition) domainObject);
			if (condition.eContainer() instanceof ModelClass) {
				ModelClass mc = (ModelClass) condition.eContainer();
				if (mc.getName().equals(getDiagram().getName())) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public String getInitialValue(IDirectEditingContext context, String id) {
		Condition condition = (Condition) getBusinessObjectForPictogramElement(context.getPictogramElement());
		if (id.equals(ID_PRE_MOD) || id.equals(ID_POST_MOD) || id.equals(ID_OTHER_MOD)) {
			String modString = "";
			for (String s : condition.getModifiables()) {
				modString += s + ", ";
			}			
			return System.getProperty("line.separator") + ("modifiable(" + (modString.equals("") ? "" : modString.substring(0, modString.length() - 2)) + ");") + System.getProperty("line.separator");
		} else {
			return System.getProperty("line.separator") + condition.getName() + System.getProperty("line.separator");
		}
	}

	@Override
	public String checkValueValid(String value, IDirectEditingContext context, String id) {
		if (value == null) {
			return "Must not be empty";
		}
		if (id.equals(ID_PRE_MOD) || id.equals(ID_POST_MOD) || id.equals(ID_OTHER_MOD)) {
			if (value.length() > 0) {
				if (!value.trim().startsWith("modifiable(") || !value.trim().endsWith(");")) {
					return "Does not match modifiable string";
				}
			}
		} else {
			if (value.length() > 0) {
				// valid string for condition or global condition
			}
		}
		return null;
	}

	@Override
	public void setValue(String value, IDirectEditingContext context, String id) {
		Condition condition = (Condition) getBusinessObjectForPictogramElement(context.getPictogramElement());
		var container = condition.eContainer().eContainer();
		if (container != null) {
			if (container.eContainer() instanceof AbstractStatement) {
				((AbstractStatement)container.eContainer()).setTested(false);
				TestStatement.setPathTested(TestStatement.getPathLeaf(container), false);
			}
		}
		
		if (id.equals(ID_PRE_MOD) || id.equals(ID_POST_MOD) || id.equals(ID_OTHER_MOD)) {
			String[] split = value.trim().replace("modifiable(", "").replace(");", "").split(",");
			condition.getModifiables().clear();
			for (String s : split) {
				if (!s.equals("")) condition.getModifiables().add(s.trim());
			}
			UpdateConditionsOfChildren.updateConditionsofChildren(condition);
			updatePictogramElement(context.getPictogramElement());
			return;
		}
		condition.setName(value.trim());
		URI peURI =	context.getPictogramElement().eResource().getURI();
		if (condition.eContainer() instanceof ModelClass) {
			Shape shape = (Shape)context.getPictogramElement();
			Text text = (Text)shape.getGraphicsAlgorithm();
			text.setValue("{" + condition.getName() + "}");
		} else if (!(condition.eContainer() instanceof GlobalConditions)) {
			if (peURI.lastSegment().contains(peURI.segment(peURI.segmentCount()-2))) { //change from CbCClass
				try {
					condition.eResource().setTrackingModification(true);
					condition.eResource().save(Collections.EMPTY_MAP);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			//Start of IFbC
			final IProject project = GetProjectUtil.getProjectForDiagram(getDiagram());
			final Lattice lattice = Lattices.getLatticeForProject(project);
			if (lattice != null) {
				for (Shape shape : getDiagram().getChildren()) {
					Object obj = getBusinessObjectForPictogramElement(shape);
					if (obj instanceof CbCFormula) {
						CbCFormula formula = (CbCFormula) obj;
						final AbstractStatement statement;
						
						// CbCFormula or AbstractStatement?
						if (formula.getPostCondition() == condition) {
							statement = formula.getStatement();
						} else {
							statement = findStatementForCondition(condition, formula);
						}
						try {
							UpdateInformationFlow.updateInformationFlow(project.getName(), statement, lattice);
						} catch (IFbCException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}

			UpdateOriginalCallsToProve.updateOriginalCallsToProve(condition);
			UpdateMethodCallsToProve.updateMethodCallsToProve(condition);
			UpdateContractsToProve.updateContractsToProve(condition);
			UpdateConditionsOfChildren.updateConditionsofChildren(condition);
		} else if (condition.eContainer() instanceof GlobalConditions) {
			CbCFormula formula = null;
			for (Shape shape : getDiagram().getChildren()) {
				Object obj = getBusinessObjectForPictogramElement(shape);
				if (obj instanceof CbCFormula) {
					formula = (CbCFormula) obj;
					formula.setProven(false);
					formula.setTested(false);
					TestStatement.setPathTested(formula.getStatement(), false);
					UpdateConditionsOfChildren.setAllStatementsUnproven(formula.getStatement());
				}
			}
		}
		updatePictogramElement(context.getPictogramElement());
	}

	@Override
	public boolean canRemove(IRemoveContext context) {
		Shape shape = (Shape) context.getPictogramElement();
		if (shape.getGraphicsAlgorithm() instanceof Text) {
			Text text = (Text) shape.getGraphicsAlgorithm();
			if (text.getValue().trim().endsWith("inherited")) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean canDelete(IDeleteContext context) {
		Shape shape = (Shape) context.getPictogramElement();
		if (shape.getGraphicsAlgorithm() instanceof Text) {
			Text text = (Text) shape.getGraphicsAlgorithm();
			if (text.getValue().trim().endsWith("inherited")) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public void delete(IDeleteContext context) {
		Shape shape = (Shape) context.getPictogramElement();
		ContainerShape container = shape.getContainer();

		Condition condition = (Condition) getBusinessObjectForPictogramElement(context.getPictogramElement());
		if (condition.eContainer() != null && (condition.eContainer() instanceof GlobalConditions) || condition.eContainer() instanceof ModelClass) {
			int indexToDelete = getIndex(shape.getGraphicsAlgorithm());

			for (Shape childShape : container.getChildren()) {
				if (getIndex(childShape.getGraphicsAlgorithm()) > indexToDelete) {
					setIndex(childShape.getGraphicsAlgorithm(), getIndex(childShape.getGraphicsAlgorithm()) - 1);
				}
			}
			if (condition.eContainer() instanceof ModelClass) {
				super.delete(context);
				layoutPictogramElement(container);
				return;
			}
			super.delete(context);
			layoutPictogramElement(container);
		} else {
			super.delete(context);
		}
		CbCFormula formula = null;
		for (Shape childShape : getDiagram().getChildren()) {
			Object obj = getBusinessObjectForPictogramElement(childShape);
			if (obj instanceof CbCFormula) {
				formula = (CbCFormula) obj;
			}
		}
		formula.setProven(false);
		formula.setTested(false);
		UpdateConditionsOfChildren.setAllStatementsUnproven(formula.getStatement());
	}
	
	private AbstractStatement findStatementForCondition(Condition condition, CbCFormula formula) {
		return findStatementForCondition(condition, formula.getStatement().getRefinement());
	}
	
	private AbstractStatement findStatementForCondition(Condition condition, AbstractStatement statement) {
		if (statement instanceof SelectionStatement) {
			final SelectionStatement selection = (SelectionStatement) statement;
			for (Condition guard : selection.getGuards()) {
				if (guard == condition) {
					return selection;
				}
			}
			
			for (AbstractStatement command : selection.getCommands()) {
				final AbstractStatement returnValue = findStatementForCondition(condition, command);
				if (returnValue != null) {
					return returnValue;
				}
			}
			
			return null;
		}
		
		if (statement instanceof SmallRepetitionStatement) {
			final SmallRepetitionStatement repetition = (SmallRepetitionStatement) statement;
			
			if (repetition.getGuard() == condition) {
				return repetition;
			}
			
			if (repetition.getLoopStatement() != null) {
				final AbstractStatement returnValue = findStatementForCondition(condition, repetition.getLoopStatement());
				if (returnValue != null) {
					return returnValue;
				}
			}
		}
		
		if (statement instanceof CompositionStatement) {
			final CompositionStatement composition = (CompositionStatement) statement;
			final AbstractStatement firstResult = findStatementForCondition(condition, composition.getFirstStatement());
			if (firstResult != null) {
				return firstResult;
			}
			
			return findStatementForCondition(condition, composition.getSecondStatement());
		}
		
		
		
		return null;
	}
}
