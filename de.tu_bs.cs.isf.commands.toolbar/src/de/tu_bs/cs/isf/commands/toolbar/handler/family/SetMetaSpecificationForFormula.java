package de.tu_bs.cs.isf.commands.toolbar.handler.family;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SkipStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;

public class SetMetaSpecificationForFormula {
	
	private static CurrentFeatureConfigTrackingStack trackingStack = new CurrentFeatureConfigTrackingStack();
	
	public static void passMetaSpeficiationThroughFormula(CbCFormula formula, String preConditionToPass, String postConditionToPass) {
		AbstractStatement firstFormulaStatement = formula.getStatement();
		firstFormulaStatement.getPreCondition().setName(preConditionToPass);
		firstFormulaStatement.getPostCondition().setName(postConditionToPass);
		
		passSpecificationThroughRefinements(firstFormulaStatement.getRefinement(), preConditionToPass, postConditionToPass);
	}

	private static void passSpecificationThroughRefinements(AbstractStatement refinement, String preConditionToPass,
			String postConditionToPass) {
		
		if(refinement == null) {
			return;
		}
		
		if(refinement instanceof CompositionStatement) {
			CompositionStatement compositionStatement = (CompositionStatement) refinement;
			compositionStatement.getFirstStatement().getPreCondition().setName(preConditionToPass);
			compositionStatement.getSecondStatement().getPostCondition().setName(postConditionToPass);
			
			//String intermediateCondition = compositionStatement.getIntermediateCondition().getName().substring(0, compositionStatement.getIntermediateCondition().getName().length()-1);
			String intermediateCondition = "(" + compositionStatement.getIntermediateCondition().getName() + ")";
			
			compositionStatement.getIntermediateCondition().setName(intermediateCondition + trackingStack.toConjunction());
			
			
			
			
			
			passSpecificationThroughRefinements(compositionStatement.getFirstStatement().getRefinement(), 
					preConditionToPass, 
					compositionStatement.getIntermediateCondition().getName());
			
			passSpecificationThroughRefinements(compositionStatement.getSecondStatement().getRefinement(), 
					compositionStatement.getIntermediateCondition().getName(), 
					postConditionToPass);
		}else if(refinement instanceof SelectionStatement) {
			SelectionStatement selectionStatement = (SelectionStatement) refinement;
			
			if(selectionStatement.getGuards().get(0).getName().contains("FV_")) {
				
				trackingStack.push(selectionStatement.getGuards().get(0).getName());
				selectionStatement.getCommands().get(0).getPreCondition().setName(preConditionToPass);
				selectionStatement.getCommands().get(0).getPostCondition().setName(postConditionToPass);
				passSpecificationThroughRefinements(selectionStatement.getCommands().get(0).getRefinement(), 
						preConditionToPass, 
						postConditionToPass);
				trackingStack.pop();
				
				trackingStack.push(selectionStatement.getGuards().get(1).getName());
				selectionStatement.getCommands().get(1).getPreCondition().setName(preConditionToPass);
				selectionStatement.getCommands().get(1).getPostCondition().setName(postConditionToPass);
				passSpecificationThroughRefinements(selectionStatement.getCommands().get(1).getRefinement(), 
						preConditionToPass, 
						postConditionToPass);
				trackingStack.pop();
			}else {
				for(AbstractStatement currentStatement: selectionStatement.getCommands()) {
					currentStatement.getPreCondition().setName(preConditionToPass);
					currentStatement.getPreCondition().setName(postConditionToPass);
					passSpecificationThroughRefinements(currentStatement.getRefinement(), preConditionToPass, postConditionToPass);
				}
			}
			
		}else if(refinement instanceof SkipStatement) {
			SkipStatement skipStatement = (SkipStatement) refinement;
			if(skipStatement.getName().contains("final")) {
				skipStatement.getPreCondition().setName(preConditionToPass + trackingStack.toConjunction());
				skipStatement.getPostCondition().setName(postConditionToPass + trackingStack.toConjunction());
				skipStatement.setName(";");
			}else {
				skipStatement.getPreCondition().setName(preConditionToPass);
				skipStatement.getPostCondition().setName(postConditionToPass);
			}
		
			
			
		}else if (refinement instanceof SmallRepetitionStatement) {
			SmallRepetitionStatement repetitionStatement = (SmallRepetitionStatement) refinement;
			repetitionStatement.getPreCondition().setName(preConditionToPass);
			repetitionStatement.getPostCondition().setName(preConditionToPass);
			
			String currentGuard = repetitionStatement.getGuard().getName();
			repetitionStatement.getGuard().setName(currentGuard);
			
			String currentVariant = repetitionStatement.getVariant().getName();
			repetitionStatement.getVariant().setName(currentVariant);
			
			String currentInvariant = "(" + repetitionStatement.getInvariant().getName() + ")";
			repetitionStatement.getInvariant().setName(currentInvariant + trackingStack.toConjunction());
			
			passSpecificationThroughRefinements(repetitionStatement.getLoopStatement().getRefinement(), preConditionToPass, postConditionToPass);
		}else {
			refinement.getPreCondition().setName(preConditionToPass);
			refinement.getPostCondition().setName(postConditionToPass);
		}
		
	}
}
