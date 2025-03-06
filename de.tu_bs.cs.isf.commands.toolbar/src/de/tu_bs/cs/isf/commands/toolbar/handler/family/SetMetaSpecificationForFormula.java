package de.tu_bs.cs.isf.commands.toolbar.handler.family;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SkipStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;

public class SetMetaSpecificationForFormula {

	private static CurrentFeatureConfigTrackingStack trackingStack = new CurrentFeatureConfigTrackingStack();

	public static void passMetaSpeficiationThroughFormula(CbCFormula formula, Condition preConditionToPass,
			Condition postConditionToPass) {
		AbstractStatement firstFormulaStatement = formula.getStatement();
		firstFormulaStatement.getPreCondition().setName(preConditionToPass.getName());
		// firstFormulaStatement.getPreCondition().getModifiables().addAll(preConditionToPass.getModifiables());
		firstFormulaStatement.getPostCondition().setName(postConditionToPass.getName());
		// firstFormulaStatement.getPostCondition().getModifiables().addAll(postConditionToPass.getModifiables());

		passSpecificationThroughRefinements(firstFormulaStatement.getRefinement(), preConditionToPass,
				postConditionToPass);
	}

	private static void passSpecificationThroughRefinements(AbstractStatement refinement, Condition preConditionToPass,
			Condition postConditionToPass) {

		if (refinement == null) {
			return;
		}

		if (refinement instanceof CompositionStatement) {
			CompositionStatement compositionStatement = (CompositionStatement) refinement;
			compositionStatement.getFirstStatement().getPreCondition().setName(preConditionToPass.getName());
			// compositionStatement.getFirstStatement().getPreCondition().getModifiables().addAll(preConditionToPass.getModifiables());
			compositionStatement.getSecondStatement().getPostCondition().setName(postConditionToPass.getName());
			// compositionStatement.getFirstStatement().getPostCondition().getModifiables().addAll(postConditionToPass.getModifiables());

			// String intermediateCondition =
			// compositionStatement.getIntermediateCondition().getName().substring(0,
			// compositionStatement.getIntermediateCondition().getName().length()-1);
			String intermediateCondition = "(" + compositionStatement.getIntermediateCondition().getName() + ")";

			compositionStatement.getIntermediateCondition()
					.setName(intermediateCondition + trackingStack.toConjunction());

			passSpecificationThroughRefinements(compositionStatement.getFirstStatement().getRefinement(),
					preConditionToPass, compositionStatement.getIntermediateCondition());

			passSpecificationThroughRefinements(compositionStatement.getSecondStatement().getRefinement(),
					compositionStatement.getIntermediateCondition(), postConditionToPass);
		} else if (refinement instanceof SelectionStatement) {
			SelectionStatement selectionStatement = (SelectionStatement) refinement;

			if (selectionStatement.getGuards().get(0).getName().contains("FV_")) {

				trackingStack.push(selectionStatement.getGuards().get(0).getName());
				selectionStatement.getCommands().get(0).getPreCondition().setName(preConditionToPass.getName());
				// selectionStatement.getCommands().get(0).getPreCondition().getModifiables().addAll(preConditionToPass.getModifiables());
				selectionStatement.getCommands().get(0).getPostCondition().setName(postConditionToPass.getName());
				// selectionStatement.getCommands().get(0).getPostCondition().getModifiables().addAll(postConditionToPass.getModifiables());
				passSpecificationThroughRefinements(selectionStatement.getCommands().get(0).getRefinement(),
						preConditionToPass, postConditionToPass);
				trackingStack.pop();

				trackingStack.push(selectionStatement.getGuards().get(1).getName());
				selectionStatement.getCommands().get(1).getPreCondition().setName(preConditionToPass.getName());
				// selectionStatement.getCommands().get(1).getPreCondition().getModifiables().addAll(preConditionToPass.getModifiables());
				selectionStatement.getCommands().get(1).getPostCondition().setName(postConditionToPass.getName());
				// selectionStatement.getCommands().get(1).getPostCondition().getModifiables().addAll(postConditionToPass.getModifiables());
				passSpecificationThroughRefinements(selectionStatement.getCommands().get(1).getRefinement(),
						preConditionToPass, postConditionToPass);
				trackingStack.pop();
			} else {
				for (AbstractStatement currentStatement : selectionStatement.getCommands()) {
					currentStatement.getPreCondition().setName(preConditionToPass.getName());
					// currentStatement.getPreCondition().getModifiables().addAll(preConditionToPass.getModifiables());
					currentStatement.getPostCondition().setName(postConditionToPass.getName());
					// currentStatement.getPostCondition().getModifiables().addAll(postConditionToPass.getModifiables());
					passSpecificationThroughRefinements(currentStatement.getRefinement(), preConditionToPass,
							postConditionToPass);
				}
			}

		} else if (refinement instanceof SkipStatement) {
			SkipStatement skipStatement = (SkipStatement) refinement;
			if (skipStatement.getName().contains("final")) {
				skipStatement.getPreCondition().setName(preConditionToPass + trackingStack.toConjunction());
				skipStatement.getPostCondition().setName(postConditionToPass + trackingStack.toConjunction());
				skipStatement.setName(";");
			} else {
				skipStatement.getPreCondition().setName(preConditionToPass.getName());
				// skipStatement.getPreCondition().getModifiables().addAll(preConditionToPass.getModifiables());
				skipStatement.getPostCondition().setName(postConditionToPass.getName());
				// skipStatement.getPostCondition().getModifiables().addAll(postConditionToPass.getModifiables());
			}

		} else if (refinement instanceof SmallRepetitionStatement) {
			SmallRepetitionStatement repetitionStatement = (SmallRepetitionStatement) refinement;
			repetitionStatement.getPreCondition().setName(preConditionToPass.getName());
			// repetitionStatement.getPreCondition().getModifiables().addAll(preConditionToPass.getModifiables());
			repetitionStatement.getPostCondition().setName(postConditionToPass.getName());
			// repetitionStatement.getPostCondition().getModifiables().addAll(postConditionToPass.getModifiables());

			String currentGuard = repetitionStatement.getGuard().getName();
			repetitionStatement.getGuard().setName(currentGuard);

			String currentVariant = repetitionStatement.getVariant().getName();
			repetitionStatement.getVariant().setName(currentVariant);

			String currentInvariant = "(" + repetitionStatement.getInvariant().getName() + ")";
			repetitionStatement.getInvariant().setName(currentInvariant + trackingStack.toConjunction());

			passSpecificationThroughRefinements(repetitionStatement.getLoopStatement().getRefinement(),
					preConditionToPass, postConditionToPass);
		} else {
			refinement.getPreCondition().setName(preConditionToPass.getName());
			// refinement.getPreCondition().getModifiables().addAll(preConditionToPass.getModifiables());
			refinement.getPostCondition().setName(postConditionToPass.getName());
			// refinement.getPostCondition().getModifiables().addAll(postConditionToPass.getModifiables());
		}

	}
}
