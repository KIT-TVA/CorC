package de.kit.tva.lost.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.eclipse.core.runtime.CoreException;

import de.kit.tva.lost.interfaces.Result;
import de.kit.tva.lost.interfaces.TestModelNotifier;
import de.kit.tva.lost.models.LostParser.StatementContext;
import de.tu_bs.cs.isf.cbc.exceptions.SettingsException;
import de.tu_bs.cs.isf.cbc.tool.features.TestStatement;
import de.tu_bs.cs.isf.cbc.util.UpdateDiagram;

public class LostTester extends TestModelNotifier {
    private final static String TEST_MARKER = " <- T: ";

    private ArrayList<Result> testees;
    private LostTranslator lostTranslator;
    private LostCodeHandler lostCodeHandler;

    public LostTester() {
	this.testees = new ArrayList<Result>();
	this.lostTranslator = new LostTranslator();
	this.lostCodeHandler = new LostCodeHandler();
    }

    public ArrayList<Result> getTestees() {
	return this.testees;
    }

    public void test(String lostCode)
	    throws DiagramResourceModelException, IOException, CoreException, SettingsException {
	this.testees.clear();
	lostTranslator.translate(lostCode);
	generateTestees();
	testAll();
	UpdateDiagram.getInstance().updateDiagram(lostTranslator.getDiagram());
    }

    public String getResults(String viewCode) {
	for (var testee : testees) {
	    var statement = testee.get() instanceof StatementContext ? testee.get().getText().trim()
		    : testee.get().getChild(1).getText().trim();
	    var codeSplit = lostCodeHandler.getCodeUntilStatement(viewCode, statement, TEST_MARKER);
	    viewCode = codeSplit[0] + TEST_MARKER + testee.wasSuccessful() + ", " + testee.getTime() + "ms"
		    + codeSplit[1];
	}
	return viewCode;
    }

    private boolean testAll() throws IOException {
	if (lostTranslator.getFormula() == null) {
	    return false;
	}
	for (int i = 0; i < testees.size(); ++i) {
	    test(testees.get(i));
	}
	this.testsFinished();
	return true;
    }

    private void test(Result testee) throws IOException {
	long startTime = System.nanoTime();
	var statement = lostTranslator.getModelLinker().getStatementFor(testee.get());
	var featureProvider = UpdateDiagram.getInstance().getFeatureProvider(lostTranslator.getDiagram());
	TestStatement ts = new TestStatement(featureProvider);
	ts.testStatement(lostTranslator.getDiagram(), statement);
	long endTime = (System.nanoTime() - startTime) / 1000000;
	testee.done(endTime, statement.isTested());
	statement.eResource().save(Collections.EMPTY_MAP);
	this.testFinished(testee);
    }

    private void generateTestees() {
	for (var statementCtx : lostTranslator.getModelLinker().getAllContexts()) {
	    this.testees.add(new Result(statementCtx));
	}
    }
}
