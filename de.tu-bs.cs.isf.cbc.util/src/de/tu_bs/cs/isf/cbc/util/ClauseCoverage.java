package de.tu_bs.cs.isf.cbc.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.testng.ITestContext;

import de.tu_bs.cs.isf.cbc.exceptions.IdentifierNotFoundException;
import de.tu_bs.cs.isf.cbc.util.conditionparser.Node;
import de.tu_bs.cs.isf.cbc.util.conditionparser.NotNode;
import de.tu_bs.cs.isf.cbc.util.conditionparser.QuantorNode;
import de.tu_bs.cs.isf.cbc.util.conditionparser.RelNode;

public class ClauseCoverage implements TestCoverageProvider {
    private final List<Clause> clauses = new ArrayList<Clause>();
    private ITestContext context;

    public ClauseCoverage() {
    }

    @Override
    public void printSummary() {
	constructSummary();
    }

    @Override
    public boolean initialize(Node tree) {
	return extractClauses(tree);
    }

    private boolean extractClauses(Node tree) {
	if (tree instanceof RelNode) {
	    clauses.add(new AtomicClause(tree));
	    return true;
	} else if (tree instanceof NotNode) {
	    clauses.add(new NotClause(tree));
	    return true;
	} else if (tree instanceof QuantorNode) {
	    int forAllIndex = tree.getRep().indexOf("forall") == -1 ? Integer.MAX_VALUE
		    : tree.getRep().indexOf("forall");
	    int existsIndex = tree.getRep().indexOf("exists") == -1 ? Integer.MAX_VALUE
		    : tree.getRep().indexOf("exists");
	    if (forAllIndex < existsIndex) {
		clauses.add(new ForallClause(tree));
	    } else {
		clauses.add(new ExistsClause(tree));
	    }
	}
	boolean resultLeft = extractClauses(tree.getLeft());
	boolean resultRight = extractClauses(tree.getRight());
	return resultLeft & resultRight;
    }

    private void constructSummary() {
	int passedTests = context.getPassedTests().size();
	int failedTests = context.getFailedTests().size();
	int skippedTests = context.getSkippedTests().size();

	if (failedTests == 0) {
	    Console.println(" > " + context.getName(), Colors.GREEN);
	} else {
	    Console.println(" > " + context.getName(), Colors.RED);
	}

	StringBuilder bufLog = new StringBuilder("+ Total tests run: ");
	bufLog.append(passedTests + failedTests).append(", Passes: ").append(passedTests).append(", Failures: ")
		.append(failedTests).append(", Skips: ").append(skippedTests).append(" +");
	int len = CodeHandler.getLongestLineLen(bufLog.toString());
	String line = "+";
	for (int i = 0; i < len - 2; i++) {
	    line += "=";
	}
	line += "+";
	Console.println(line);
	Console.println(bufLog.toString());
	Console.println(line);

	// print predicate coverage
	int totalNumBranches = this.clauses.size();
	int executedNumBranches = 0;
	for (var clause : clauses) {
	    if (clause.isPartiallyCovered()) {
		executedNumBranches++;
	    }
	}
	double percentage = ((double) executedNumBranches / (double) totalNumBranches) * 100.0;

	if (failedTests == 0) {
	    Console.println(" > Predicate Coverage (Post Condition)", Colors.GREEN);
	} else {
	    Console.println(" > Predicate Coverage (Post Condition)", Colors.RED);
	}
	if (totalNumBranches == 0) {
	    Console.println("\tCoverage: 1/1 = 100%");
	} else {
	    Console.println("\tCoverage: " + executedNumBranches + "/" + totalNumBranches + " = " + percentage + "%");
	    Console.println();
	    Console.println(" > Branches that were executed at least once are highlighted in green:");
	    for (var clause : this.clauses) {
		if (clause.isFullyCovered()) {
		    Console.println("\t" + clause.getName(), Colors.GREEN);
		} else {
		    Console.println("\t" + clause.getName());
		}
	    }
	}
	Console.println();
    }

    @Override
    public boolean evaluate(ITestContext context) throws IdentifierNotFoundException {
	this.context = context;
	refreshClauses();
	return true;
    }

    private void refreshClauses() throws IdentifierNotFoundException {
	for (var attr : context.getAttributeNames()) {
	    var splitter = attr.split(Pattern.quote(Clause.VAL_DELIM));
	    var name = splitter[0];
	    var value = splitter[1];
	    Clause clause = null;
	    for (var c : this.clauses) {
		if (c.getName().equals(name)) {
		    clause = c;
		}
	    }
	    if (clause == null) {
		throw new IdentifierNotFoundException(name);
	    }
	    if (value.equals(Clause.TRUE_FLAG)) {
		clause.wasTrue();
	    } else {
		clause.wasFalse();
	    }
	}
    }

    @Override
    public String getJavaCode() {
	StringBuilder sb = new StringBuilder();
	for (var clause : clauses) {
	    sb.append(clause.toJava());
	}
	return sb.toString();
    }
}
