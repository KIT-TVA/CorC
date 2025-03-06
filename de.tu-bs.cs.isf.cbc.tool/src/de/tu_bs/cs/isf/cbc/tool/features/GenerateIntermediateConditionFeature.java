package de.tu_bs.cs.isf.cbc.tool.features;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.util.Parser;
import de.tu_bs.cs.isf.cbc.util.ParserException;
import de.tu_bs.cs.isf.cbc.util.UpdateConditionsOfChildren;

/**
 * Class that guesses a weakest precondition
 * 
 * @author Tobias
 *
 */
public class GenerateIntermediateConditionFeature extends AbstractCustomFeature {

	/**
	 * boolean that indicate if something changes
	 */
	private boolean hasDoneChanges = false;

	/**
	 * Constructor of the class
	 * 
	 * @param fp
	 *            The FeatureProvider
	 */
	public GenerateIntermediateConditionFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Generate intermediate condition";
	}

	@Override
	public String getDescription() {
		return "Generates a intermediate condition from an assignment and a post condition";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		boolean ret = false;
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof AbstractStatement && ((AbstractStatement) bo).getParent() != null
					&& ((AbstractStatement) bo).getParent().eContainer() instanceof CompositionStatement) {
				CompositionStatement compoStatement = (CompositionStatement) ((AbstractStatement) bo).getParent()
						.eContainer();
				if (compoStatement.getSecondStatement().equals(((AbstractStatement) bo).getParent())) {
					ret = true;
				}
			}
		}
		return ret;
	}

	@Override
	public void execute(ICustomContext context) {
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof AbstractStatement) {
				AbstractStatement statement = (AbstractStatement) bo;
				CompositionStatement comboStatement = (CompositionStatement) statement.getParent().eContainer();
				Parser parser = new Parser();
				String preConditionString = "";
				try {
					parser.addVariableStatementPairs(statement);
					preConditionString = parser.destructConditionAndReplace(statement.getPostCondition());
				} catch (ParserException e) {
					e.printStackTrace();
				}
				comboStatement.getIntermediateCondition().setName(preConditionString);
				UpdateConditionsOfChildren.updateConditionsofChildren(comboStatement.getIntermediateCondition());
				updatePictogramElement(pes[0]);
			}
		}
	}

	@Override
	public boolean hasDoneChanges() {
		return this.hasDoneChanges;
	}
}
