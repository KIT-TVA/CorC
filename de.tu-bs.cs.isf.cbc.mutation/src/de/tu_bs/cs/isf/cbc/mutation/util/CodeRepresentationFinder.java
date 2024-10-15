package de.tu_bs.cs.isf.cbc.mutation.util;

import org.eclipse.emf.ecore.EObject;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;
import de.tu_bs.cs.isf.cbc.exceptions.CodeRepresentationFinderException;
import de.tu_bs.cs.isf.cbc.util.Parser;

public class CodeRepresentationFinder {
    private boolean isContract;
    private boolean isPreCon;
    private String targetRep;

    public CodeRepresentationFinder() {
    }

    public EObject find(AbstractStatement firstStatement, String rep) throws CodeRepresentationFinderException {
	rep = stripOriginal(rep);
	targetRep = rep;
	EObject target = findStatementRep(firstStatement);
	if (target != null) {
	    isContract = false;
	    return target;
	}
	target = findConditionRep(firstStatement);
	if (target != null) {
	    isContract = false;
	    return target;
	}
	this.targetRep = transformJMLToJavaDL(this.targetRep);
	return findContractRep(firstStatement);
    }

    public boolean isContract() {
	return isContract;
    }

    public String transformJMLToJavaDL(String contract) {
	this.isPreCon = contract.startsWith("@ requires") ? true : false;
	contract = Parser.rewriteJMLConditionToKeY(contract);
	contract = contract.trim();
	contract = contract.substring(0, contract.length() - 1); // remove added semicolon
	return contract;
    }

    public String stripOriginal(String rep) {
	int nextMatch = rep.indexOf("original_");
	while (nextMatch != -1) {
	    String helper = rep;
	    helper = helper.substring(nextMatch, helper.length());
	    helper = helper.substring(0, helper.indexOf("("));
	    rep = rep.replaceAll(helper, "original");
	    nextMatch = rep.indexOf("original_");
	}
	return rep;
    }

    private AbstractStatement findStatementRep(EObject cur) {
	if (cur instanceof CompositionStatement) {
	    return handleComposition(cur);
	} else if (cur instanceof SelectionStatement) {
	    return handleSelection(cur);
	} else if (cur instanceof SmallRepetitionStatement) {
	    return handleRepetition(cur);
	} else if (cur instanceof AbstractStatement) {
	    AbstractStatement statement = (AbstractStatement) cur;
	    if (statement.getName().trim().equals(targetRep)) {
		return statement;
	    }
	    /*
	     * if (statement.getCodeRepresentation() != null) { String[] lines =
	     * statement.getCodeRepresentation().split("\n"); for (String line : lines) { if
	     * (line.trim().equals(targetRep)) { return statement; } } }
	     */
	}
	for (EObject child : cur.eContents()) {
	    return findStatementRep(child);
	}
	return null;
    }

    private Condition findConditionRep(EObject cur) throws CodeRepresentationFinderException {
	if (cur == null) {
	    return null;
	}
	BoilerplateRemover br = new BoilerplateRemover();
	targetRep = br.removeIn(targetRep);
	if (cur instanceof SelectionStatement) {
	    SelectionStatement statement = (SelectionStatement) cur;
	    for (Condition condition : statement.getGuards()) {
		if (condition.getName().trim().equals(targetRep)) {
		    return condition;
		}
	    }
	    Condition foundCondition = null;
	    for (var command : statement.getCommands()) {
		if ((foundCondition = findConditionRep(command)) != null) {
		    return foundCondition;
		}
	    }
	} else if (cur instanceof SmallRepetitionStatement) {
	    SmallRepetitionStatement statement = (SmallRepetitionStatement) cur;
	    if (statement.getGuard().getName().trim().equals(targetRep)) {
		return statement.getGuard();
	    }
	    return findConditionRep(statement.getLoopStatement());
	} else if (cur instanceof CompositionStatement) {
	    CompositionStatement cs = (CompositionStatement) cur;
	    Condition c = findConditionRep(cs.getFirstStatement());
	    if (c == null) {
		return findConditionRep(cs.getSecondStatement());
	    } else {
		return c;
	    }
	} else {
	    return findConditionRep(((AbstractStatement) cur).getRefinement());
	}
	return null;
	// throw new CodeRepresentationFinderException("The code representation '" +
	// this.targetRep + "' couldn't be found.");
    }

    private Condition findContractRep(EObject cur) {
	this.targetRep = this.targetRep.replaceAll("[\t\b\f\r\n]", "");
	if (cur instanceof AbstractStatement) {
	    AbstractStatement statement = (AbstractStatement) cur;
	    if (isPreCon && statement.getPreCondition() != null) {
		String preCon = statement.getPreCondition().getName();
		preCon = preCon.replaceAll("[\t\b\f\r\n]", "");
		if (preCon.contains(this.targetRep)) {
		    isContract = true;
		    statement.setProven(false);
		    return statement.getPreCondition();
		}
	    }
	    if (!isPreCon && statement.getPostCondition() != null) {
		String postCon = statement.getPostCondition().getName().trim();
		postCon = postCon.replaceAll("[\t\b\f\r\n]", "");
		if (postCon.contains(this.targetRep)) {
		    isContract = true;
		    statement.setProven(false);
		    return statement.getPostCondition();
		}
	    }
	}
	if (cur instanceof SmallRepetitionStatement) {
	    // TODO
	}
	for (EObject child : cur.eContents()) {
	    return findContractRep(child);
	}
	return null;
    }

    private AbstractStatement handleSelection(EObject cur) {
	SelectionStatement selS = (SelectionStatement) cur;
	if (selS.getCodeRepresentation().trim().equals(targetRep)) {
	    return selS;
	}
	AbstractStatement target = null;
	for (int i = 0; i < selS.getCommands().size(); ++i) {
	    target = findStatementRep(selS.getCommands().get(i));
	    if (target != null) {
		return target;
	    }
	}
	return null;
    }

    private AbstractStatement handleComposition(EObject cur) {
	CompositionStatement cs = (CompositionStatement) cur;
	AbstractStatement c = findStatementRep(cs.getFirstStatement());
	if (c == null) {
	    return findStatementRep(cs.getSecondStatement());
	} else {
	    return c;
	}
    }

    private AbstractStatement handleRepetition(EObject cur) {
	SmallRepetitionStatement srs = (SmallRepetitionStatement) cur;
	if (srs.getCodeRepresentation().trim().equals(targetRep)) {
	    return srs;
	}
	return findStatementRep(srs.getLoopStatement());
    }

}
