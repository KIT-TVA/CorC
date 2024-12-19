package de.tu_bs.cs.isf.cbc.util;

import org.testng.ITestContext;

import de.tu_bs.cs.isf.cbc.exceptions.IdentifierNotFoundException;
import de.tu_bs.cs.isf.cbc.util.conditionparser.Node;

/**
 * A TestCoverageProvider is responsible for two tasks: 1. Given an AST of a
 * typed FOL formula, it must initialize the coverage process for TestNG using
 * ITestContext. 2. After the execution of TestNG test cases, it must evaluate a
 * given ITestContext context.
 */
public interface TestCoverageProvider {
    boolean initialize(Node tree);

    boolean evaluate(ITestContext context) throws IdentifierNotFoundException;

    String getJavaCode();

    void printSummary();
}
