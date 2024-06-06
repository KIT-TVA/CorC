package de.kit.tva.lost.models;

import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.ui.services.GraphitiUi;

import de.kit.tva.lost.interfaces.TestModelNotifier;
import de.kit.tva.lost.interfaces.Testee;
import de.tu_bs.cs.isf.cbc.tool.features.TestStatement;

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

    public void test(String lostCode) throws DiagramResourceModelException, IOException, CoreException {
	lostTranslator.translate(lostCode);
	generateTestees();
	testAll();
    }

    public boolean testAll() {
	if (lostTranslator.getFormula() == null) {
	    return false;
	}
	for (var testee : this.testees) {
	    test(testee);
	}
	this.testsFinished();
	return true;
    }

    private void test(Testee testee) {
	long startTime = System.nanoTime();
	var statement = lostTranslator.getModelLinker().getStatementFor(testee.get());
	Diagram d = lostTranslator.getDiagram();
	var featureProvider = getFeatureProvider(d);
	TestStatement ts = new TestStatement(featureProvider);
	ts.testStatement(d, this.lostTranslator.getModelLinker().getStatementFor(testee.get()));
	long endTime = System.nanoTime() - startTime;
	testee.done(endTime, statement.isTested());
	this.testFinished(testee);
    }

    public static IFeatureProvider getFeatureProvider(Diagram diagram) {
	// Use the Graphiti service to get the DiagramTypeProvider for the given Diagram
	IDiagramTypeProvider diagramTypeProvider = GraphitiUi.getExtensionManager().createDiagramTypeProvider(diagram,
		null);

	// Ensure the DiagramTypeProvider is valid
	if (diagramTypeProvider != null) {
	    return diagramTypeProvider.getFeatureProvider();
	}

	// If no DiagramTypeProvider found, return null
	return null;
    }

    private void generateTestees() {
	for (var statementCtx : lostTranslator.getModelLinker().getAllContexts()) {
	    this.testees.add(new Testee(statementCtx));
	}
    }
}
