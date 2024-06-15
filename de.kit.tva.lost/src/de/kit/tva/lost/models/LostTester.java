package de.kit.tva.lost.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.ui.services.GraphitiUi;

import de.kit.tva.lost.interfaces.TestModelNotifier;
import de.kit.tva.lost.interfaces.Testee;
import de.kit.tva.lost.models.LostParser.StatementContext;
import de.tu_bs.cs.isf.cbc.exceptions.SettingsException;
import de.tu_bs.cs.isf.cbc.tool.features.TestStatement;
import de.tu_bs.cs.isf.cbc.util.UpdateDiagram;

public class LostTester extends TestModelNotifier {
    private ArrayList<Testee> testees;
    private LostTranslator lostTranslator;

    public LostTester() {
	this.testees = new ArrayList<Testee>();
	this.lostTranslator = new LostTranslator();
    }

    public ArrayList<Testee> getTestees() {
	return this.testees;
    }

    public void test(String lostCode)
	    throws DiagramResourceModelException, IOException, CoreException, SettingsException {
	this.testees.clear();
	lostTranslator.translate(lostCode);
	generateTestees();
	testAll();
	UpdateDiagram.run(lostTranslator.getDiagram());
	lostTranslator.getDiagram().eResource().save(Collections.EMPTY_MAP);
    }

    public String getResults(String viewCode) {
	for (var testee : testees) {
	    var statement = testee.get() instanceof StatementContext ? testee.get().getText().trim()
		    : testee.get().getChild(1).getText().trim();
	    var codeSplit = getCodeUntilStatement(viewCode, statement);
	    viewCode = codeSplit[0] + " <- T: " + testee.getTestSuccessful() + ", " + testee.getTime() + "ms"
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

    private void test(Testee testee) throws IOException {
	long startTime = System.nanoTime();
	var statement = lostTranslator.getModelLinker().getStatementFor(testee.get());
	var featureProvider = getFeatureProvider(lostTranslator.getDiagram());
	TestStatement ts = new TestStatement(featureProvider);
	ts.testStatement(lostTranslator.getDiagram(), statement);
	long endTime = (System.nanoTime() - startTime) / 1000000;
	testee.done(endTime, statement.isTested());
	statement.eResource().save(Collections.EMPTY_MAP);
	this.testFinished(testee);
    }

    private IFeatureProvider getFeatureProvider(Diagram diagram) {
	IDiagramTypeProvider diagramTypeProvider = GraphitiUi.getExtensionManager().createDiagramTypeProvider(diagram,
		"de.tu-bs.cs.isf.cbc.tool.CbCDiagramTypeProvider");
	if (diagramTypeProvider != null) {
	    return diagramTypeProvider.getFeatureProvider();
	}
	return null;
    }

    private void generateTestees() {
	for (var statementCtx : lostTranslator.getModelLinker().getAllContexts()) {
	    this.testees.add(new Testee(statementCtx));
	}
    }

    private String[] getCodeUntilStatement(String code, String statementName) {
	String out[] = { "", "" };
	out[0] = code.substring(0, code.indexOf(statementName) + statementName.length());
	out[1] = code.substring(code.indexOf(statementName) + statementName.length(), code.length());
	return out;
    }
}
