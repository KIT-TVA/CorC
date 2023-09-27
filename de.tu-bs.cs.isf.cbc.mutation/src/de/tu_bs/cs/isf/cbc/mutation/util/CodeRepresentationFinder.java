package de.tu_bs.cs.isf.cbc.mutation.util;

import org.eclipse.emf.ecore.EObject;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;
import de.tu_bs.cs.isf.cbc.tool.exceptions.CodeRepresentationFinderException;

public class CodeRepresentationFinder {
	private String targetRep;
	
	public CodeRepresentationFinder() {
	}
	
	public EObject find(AbstractStatement firstStatement, String rep) throws CodeRepresentationFinderException {
		targetRep = rep;
		EObject target = findStatementRep(firstStatement);
		if (target != null) {
			return target;
		}
		return findConditionRep(firstStatement);
	}

	private AbstractStatement findStatementRep(EObject cur) {
		if (cur instanceof AbstractStatement) {
			AbstractStatement statement = (AbstractStatement)cur;
			/*
			if (statement.getCodeRepresentation() == null) {
				return null;
			}
			String[] lines = statement.getCodeRepresentation().split("\n");
			for (String line : lines) {
				if (line.trim().equals(targetRep)) {
					return statement;
				}
			}*/
			if (statement.getCodeRepresentation() != null 
					&& statement.getCodeRepresentation().trim().equals(targetRep)) {
				return statement;
			}
		}
		if (cur instanceof CompositionStatement) {
			return handleComposition(cur);
		}
		for (EObject child : cur.eContents()) {
			return findStatementRep(child);
		}
		return null;
	}
	
	private AbstractStatement handleComposition(EObject cur) {
			CompositionStatement cs = (CompositionStatement)cur;
			AbstractStatement c = findStatementRep(cs.getFirstStatement());
			if (c == null) {
				return findStatementRep(cs.getSecondStatement());
			} else {
				return c;
			}
	}
	
	private Condition findConditionRep(EObject cur) throws CodeRepresentationFinderException {
		if (cur instanceof SelectionStatement) {
			SelectionStatement statement = (SelectionStatement)cur;
			for (Condition condition : statement.getGuards()) {
				if (condition.getCodeRepresentation() != null 
						&& condition.getCodeRepresentation().trim().equals(targetRep)) {
					return condition;
				}
			}
		} else if (cur instanceof SmallRepetitionStatement) {
			SmallRepetitionStatement statement = (SmallRepetitionStatement)cur;
			if (statement.getGuard().getCodeRepresentation() != null 
					&& statement.getGuard().getCodeRepresentation().trim().equals(targetRep)) {
				return statement.getGuard();
			}
		}
		if (cur instanceof CompositionStatement) {
			CompositionStatement cs = (CompositionStatement)cur;
			Condition c = findConditionRep(cs.getFirstStatement());
			if (c == null) {
				return findConditionRep(cs.getSecondStatement());
			} else {
				return c;
			}
		}
		for (EObject child : cur.eContents()) {
			return findConditionRep(child);
		}
		return null;
		//throw new CodeRepresentationFinderException("The code representation '" + this.targetRep + "' couldn't be found.");
	}
}
