package de.tu_bs.cs.isf.cbc.util;

import java.util.ArrayList;
import java.util.List;

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

    private boolean addClause(Clause clause) {
	for (var c : this.clauses) {
	    if (c.getName().equals(clause.getName())) {
		return false;
	    }
	}
	this.clauses.add(clause);
	return true;
    }

    private boolean extractClauses(Node tree) {
	if (tree instanceof RelNode) {
	    addClause(new AtomicClause(tree));
	    return true;
	} else if (tree instanceof NotNode) {
	    addClause(new NotClause(tree));
	    return true;
	} else if (tree instanceof QuantorNode) {
	    int forAllIndex = tree.getRep().indexOf("forall") == -1 ? Integer.MAX_VALUE
		    : tree.getRep().indexOf("forall");
	    int existsIndex = tree.getRep().indexOf("exists") == -1 ? Integer.MAX_VALUE
		    : tree.getRep().indexOf("exists");
	    if (forAllIndex < existsIndex) {
		addClause(new ForallClause(tree));
	    } else {
		addClause(new ExistsClause(tree));
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

	int totalNumBranches = this.clauses.size();
	int partiallyCoveredBranches = 0;
	int fullyCoveredBranches = 0;
	for (var clause : clauses) {
	    if (clause.isFullyCovered()) {
		fullyCoveredBranches++;
	    }
	    if (clause.isPartiallyCovered()) {
		partiallyCoveredBranches++;
	    }
	}

	if (failedTests == 0) {
	    Console.println(" > Clause Coverage (Post Condition):", Colors.GREEN);
	} else {
	    Console.println(" > Clause Coverage (Post Condition):", Colors.RED);
	}
	if (totalNumBranches == 0) {
	    Console.println("\tCoverage: 1/1 = 100%");
	} else {
	    Console.println("\t> Fully covered clauses:");
	    for (var clause : this.clauses) {
		if (clause.isFullyCovered()) {
		    Console.println("\t\t" + clause.getName(), Colors.GREEN);
		}
	    }
	    Console.println("\t> Partially covered clauses:");
	    for (var clause : this.clauses) {
		if (clause.isPartiallyCovered()) {
		    Console.println("\t\t" + clause.getName(), Colors.GREEN);
		}
	    }
	    double percentage = ((double) fullyCoveredBranches / (double) totalNumBranches) * 100.0;
	    Console.println("\t> Full Coverage Percentage: " + fullyCoveredBranches + "/" + totalNumBranches + " = "
		    + percentage + "%");
	    percentage = ((double) partiallyCoveredBranches / (double) totalNumBranches) * 100.0;
	    Console.println("\t> Partial Coverage Percentage: " + partiallyCoveredBranches + "/" + totalNumBranches
		    + " = " + percentage + "%");
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
	for (var attrName : context.getAttributeNames()) {
	    var value = context.getAttribute(attrName);
	    Clause clause = null;
	    for (var c : this.clauses) {
		if (c.getName().equals(attrName)) {
		    clause = c;
		}
	    }
	    if (clause == null) {
		// throw new IdentifierNotFoundException(attrName);
		continue;
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
	sb.append("\n");
	for (var clause : clauses) {
	    sb.append(clause.toJava());
	}
	return sb.toString();
    }
}
