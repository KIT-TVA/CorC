package de.tu_bs.cs.isf.cbc.textual.tool.validation;

import org.eclipse.emf.ecore.EObject;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.ReturnStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SkipStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.StrengthWeakStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.AbstractStatementImpl;

public class TraverseFormula {
	
	private int numberFile;
	public int foundFile;

	TraverseFormula() {
		this.numberFile = 0;
	}
	
	public void traverseFormula(CbCFormula formula, EObject searchObject) {
		AbstractStatement statement = formula.getStatement();
		castStatementAndTraverse(statement, searchObject);
	}

	private int castStatementAndTraverse(AbstractStatement statement, EObject searchObject) {
		if(statement.getClass().equals(AbstractStatementImpl.class)) {
			if(searchObject.equals(statement)) foundFile = numberFile;
			numberFile++;
		} else if(statement instanceof ReturnStatement) {
			if(searchObject.equals(statement)) foundFile = numberFile;
			numberFile++;
		} else if(statement instanceof SkipStatement) {
			if(searchObject.equals(statement)) foundFile = numberFile;
			numberFile++;
		} else if(statement instanceof MethodStatement) {
			if(searchObject.equals(statement)) foundFile = numberFile;
			numberFile += 2;
		} else if(statement instanceof StrengthWeakStatement) {
			if(searchObject.equals(statement)) foundFile = numberFile;
			numberFile += 3;
		} else if (statement instanceof SmallRepetitionStatement) {
			SmallRepetitionStatement repetitionStatement = (SmallRepetitionStatement) statement;
			traverseRepetitionStatement(repetitionStatement, searchObject);
		} else if (statement instanceof SelectionStatement) {
			SelectionStatement selectionStatement = (SelectionStatement) statement;
			traverseSelectionStatement(selectionStatement, searchObject);
		} else if (statement instanceof CompositionStatement) {
			CompositionStatement compositionStatement = (CompositionStatement) statement;
			traverseCompositionStatement(compositionStatement, searchObject);
		}
		return -1;
	}

	private void traverseRepetitionStatement(SmallRepetitionStatement repetitionStatement, EObject searchObject) {
		AbstractStatement loopStatement = repetitionStatement.getLoopStatement();
		if(searchObject.equals(repetitionStatement)) foundFile = numberFile;
		numberFile += 3;
		castStatementAndTraverse(loopStatement, searchObject);
	}
	
	private void traverseSelectionStatement(SelectionStatement selectionStatement, EObject searchObject) {
		if(searchObject.equals(selectionStatement)) foundFile = numberFile;
		numberFile++;
		for (int i = 0; i < selectionStatement.getCommands().size(); i++) {
			AbstractStatement childStatement = selectionStatement.getCommands().get(i);
			castStatementAndTraverse(childStatement, searchObject);
		}
	}
	
	private void traverseCompositionStatement(CompositionStatement compositionStatement, EObject searchObject) {
		AbstractStatement firstStatement = compositionStatement.getFirstStatement();
		AbstractStatement secondStatement = compositionStatement.getSecondStatement();
		castStatementAndTraverse(firstStatement, searchObject);
		castStatementAndTraverse(secondStatement, searchObject);
	}
}
