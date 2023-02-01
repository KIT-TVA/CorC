package de.tu_bs.cs.isf.commands.toolbar.handler.family;

import java.util.UUID;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SkipStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.StrengthWeakStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Variant;
import de.tu_bs.cs.isf.cbc.util.Console;

public class CopyCbCFormiula {
	
	private static String EMPTY_STRING = new String("\n");
	
	public static CbCFormula copyCbCFormula(final CbCFormula formulaToCopy) {
		
		CbCFormula newCbcFormula = CbcmodelFactory.eINSTANCE.createCbCFormula();
		
		newCbcFormula.setName(formulaToCopy.getName());
		newCbcFormula.setCompositionTechnique(formulaToCopy.getCompositionTechnique());
		newCbcFormula.setPreCondition(formulaToCopy.getPreCondition());
		newCbcFormula.setPostCondition(formulaToCopy.getPreCondition());
		
		
		AbstractStatement newFormulaStatement = CbcmodelFactory.eINSTANCE.createAbstractStatement();
		String newName = new String(formulaToCopy.getStatement().getName());
		newFormulaStatement.setName(newName);
		newFormulaStatement.setPreCondition(copyCondition(formulaToCopy.getStatement().getPreCondition()));
		newFormulaStatement.setPostCondition(copyCondition(formulaToCopy.getStatement().getPostCondition()));
		
		copyAllRefinements(newFormulaStatement, formulaToCopy.getStatement().getRefinement());
		newCbcFormula.setStatement(newFormulaStatement);
		
		return newCbcFormula;
	}
	
	private static void copyAllRefinements(AbstractStatement previousStatement, AbstractStatement statementToCopy) {
			
			if(previousStatement == null || statementToCopy == null) {
				return;
			}
			
			if (statementToCopy instanceof SmallRepetitionStatement) {
				SmallRepetitionStatement smallRepetitionStatement = (SmallRepetitionStatement) statementToCopy;
				copySmallRepetitionStatement(previousStatement, smallRepetitionStatement);
			} else if (statementToCopy instanceof SelectionStatement) {
				SelectionStatement selectionStatement = (SelectionStatement) statementToCopy;
				copySelectionStatement(previousStatement, selectionStatement);
			} else if (statementToCopy instanceof CompositionStatement) {
				CompositionStatement compositionStatement = (CompositionStatement) statementToCopy;
				copyCompositionStatement(previousStatement, compositionStatement);
			} else if (statementToCopy instanceof StrengthWeakStatement) {
				StrengthWeakStatement StrengthWeakStatement = (StrengthWeakStatement) statementToCopy;
				copyStrengthWeakStatement(StrengthWeakStatement);
			} /*else if (statementToCopy instanceof RepetitionStatement) {
				copyRepetitionStatement(previousStatement, (RepetitionStatement) statementToCopy);
			}*/ else if (statementToCopy instanceof SkipStatement) {
				SkipStatement skipStatement = (SkipStatement) statementToCopy;
				previousStatement.setRefinement(copySkipStatement(skipStatement));
			}
			else {
				previousStatement.setRefinement(copyAbstractStatement(statementToCopy));
			}
			
			
			
	}
	
	private static SkipStatement copySkipStatement(SkipStatement statementToCopy) {
		SkipStatement skipStatement = CbcmodelFactory.eINSTANCE.createSkipStatement();
		String newName = new String(statementToCopy.getName());
		skipStatement.setName(newName);
		skipStatement.setPreCondition(copyCondition(statementToCopy.getPreCondition()));
		skipStatement.setPostCondition(copyCondition(statementToCopy.getPostCondition()));
		return skipStatement;
	}
/*
	private static void copyRepetitionStatement(AbstractStatement previousStatement, RepetitionStatement repetitionStatement) {
		Console.println("ERROR: CopyFormula: Big RepettionStatement Copy Not Implemented");
	}*/
	
	
	private static void copySelectionStatement(AbstractStatement previousStatement, SelectionStatement selectionStatementToCopy) {
		SelectionStatement newSelection = CbcmodelFactory.eINSTANCE.createSelectionStatement();
		String newName = new String(selectionStatementToCopy.getName());
		newSelection.setName(newName);
		previousStatement.setRefinement(newSelection);
		
		for(int i = 0 ; i < selectionStatementToCopy.getGuards().size(); i++) {
			newSelection.getGuards().add(copyCondition(selectionStatementToCopy.getGuards().get(i)));
		}
		
		for(int i = 0 ; i < selectionStatementToCopy.getCommands().size(); i++) {
			newSelection.getCommands().add(copyAbstractStatement(selectionStatementToCopy.getCommands().get(i)));
			copyAllRefinements(newSelection.getCommands().get(i), selectionStatementToCopy.getCommands().get(i).getRefinement());
		}
		
	}
	
	private static void copyCompositionStatement(AbstractStatement previousStatement, CompositionStatement compositionStatementToCopy) {
		CompositionStatement newComposition = CbcmodelFactory.eINSTANCE.createCompositionStatement();
		
		String newName = new String(compositionStatementToCopy.getName());
		newComposition.setName(newName);
		newComposition.setIntermediateCondition(copyCondition(compositionStatementToCopy.getIntermediateCondition()));

		newComposition.setFirstStatement(copyAbstractStatement(compositionStatementToCopy.getFirstStatement()));
		newComposition.setSecondStatement(copyAbstractStatement(compositionStatementToCopy.getSecondStatement()));
		
		previousStatement.setRefinement(newComposition);
		
		copyAllRefinements(newComposition.getFirstStatement(), compositionStatementToCopy.getFirstStatement().getRefinement());
		copyAllRefinements(newComposition.getSecondStatement(), compositionStatementToCopy.getSecondStatement().getRefinement());
		
	}
	
	private static void copyStrengthWeakStatement(StrengthWeakStatement StrengthWeakStatement) {
		Console.println("ERROR: CopyFormula: StrengthWeakStatement Not Implemented");
	}
	
	private static void copySmallRepetitionStatement(AbstractStatement previousStatement, SmallRepetitionStatement repetitionStatementToCopy) {
		SmallRepetitionStatement newRepetitionStatement = CbcmodelFactory.eINSTANCE.createSmallRepetitionStatement();
		newRepetitionStatement.setGuard(copyCondition(repetitionStatementToCopy.getGuard()));
		newRepetitionStatement.setInvariant(copyCondition(repetitionStatementToCopy.getInvariant()));
		String newName = new String(repetitionStatementToCopy.getName());
		newRepetitionStatement.setName(newName);
		newRepetitionStatement.setVariant(copyVariant(repetitionStatementToCopy.getVariant()));
		newRepetitionStatement.setPreCondition(copyCondition(repetitionStatementToCopy.getPreCondition()));
		newRepetitionStatement.setPostCondition(copyCondition(repetitionStatementToCopy.getPostCondition()));
		newRepetitionStatement.setLoopStatement(copyAbstractStatement(repetitionStatementToCopy.getLoopStatement()));
		
		
		previousStatement.setRefinement(newRepetitionStatement);
		
		
		copyAllRefinements(newRepetitionStatement.getLoopStatement(), repetitionStatementToCopy.getLoopStatement().getRefinement());
	}
	private static Variant copyVariant(Variant variantToCopy) {
		Variant newVariant = CbcmodelFactory.eINSTANCE.createVariant();
		String newName = new String(variantToCopy.getName());
		newVariant.setName(newName);
		return newVariant;
	}
	private static String createRandomUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	public static Condition copyCondition(Condition oldCondition) {
		Condition newCondition = CbcmodelFactory.eINSTANCE.createCondition();
		String newConditionName = new String(oldCondition.getName());
		//newCondition.setName(newConditionName+createRandomUUID().charAt(0));
		/*StringBuilder sb = new StringBuilder(oldCondition.getName());
		sb.append(EMPTY_STRING);
		newCondition.setName(sb.toString());*/ 
		newCondition.setName(new String(oldCondition.getName()));
		return newCondition;
	}
	
	private static String removeLastFrom(String string) {
		return string.substring(0, string.length()-1);
	}
	
	private static AbstractStatement copyAbstractStatement(AbstractStatement statementToCopy) {
		
		AbstractStatement statement = CbcmodelFactory.eINSTANCE.createAbstractStatement();
		String newName = new String(statementToCopy.getName());
		statement.setName(newName);
		statement.setPreCondition(copyCondition(statementToCopy.getPreCondition()));
		statement.setPostCondition(copyCondition(statementToCopy.getPostCondition()));
		
		return statement;
		
	}
	
	public static JavaVariable copyJavaVariable(JavaVariable variableToCopy) {
		JavaVariable newJavaVariable = CbcmodelFactory.eINSTANCE.createJavaVariable();
		//newJavaVariable.setDisplayedName(new String(variableToCopy.getDisplayedName()));
		newJavaVariable.setKind(variableToCopy.getKind());
		newJavaVariable.setName(new String(variableToCopy.getName()));
		return newJavaVariable;
	}
	

}
