package de.tu_bs.cs.isf.cbc.tool.features;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.impl.AbstractCreateFeature;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.tool.helper.UpdateConditionsOfChildren;

/**
 * Class that creates a new DataStructure in the diagram
 * @author Tobias
 *
 */
public class CreateExtraSelectionFeature extends AbstractCreateFeature {

	/**
	 * Constructor of the class
	 * @param fp The FeatureProvider
	 */
	public CreateExtraSelectionFeature(IFeatureProvider fp) {
		super(fp, "ExtraSelection", "Create an extra entry for the selection statement");
	}

	@Override
	public boolean canCreate(ICreateContext context) {
		return getBusinessObjectForPictogramElement(context.getTargetContainer()) instanceof SelectionStatement;
	}

	@Override
	public Object[] create(ICreateContext context) {
		SelectionStatement selectionStatement = (SelectionStatement) getBusinessObjectForPictogramElement(context.getTargetContainer());
		AbstractStatement statement = CbcmodelFactory.eINSTANCE.createAbstractStatement();
		statement.setName("statement");
		Condition guard = CbcmodelFactory.eINSTANCE.createCondition();
		guard.setName("guard");
		Condition pre = CbcmodelFactory.eINSTANCE.createCondition();
		pre.setName("");
		statement.setPreCondition(pre);
		Condition post = CbcmodelFactory.eINSTANCE.createCondition();
		post.setName("");
		statement.setPostCondition(post);
		selectionStatement.getCommands().add(statement);
		selectionStatement.getGuards().add(guard);
		if (selectionStatement.getParent() != null) {
			UpdateConditionsOfChildren.updateRefinedStatement(selectionStatement.getParent(), selectionStatement);
		}
		updatePictogramElement(context.getTargetContainer());
		return new Object[] { statement, guard };
	}
}
