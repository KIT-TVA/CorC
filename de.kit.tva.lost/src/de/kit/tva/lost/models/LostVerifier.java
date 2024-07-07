package de.kit.tva.lost.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.graphics.Color;

import de.kit.tva.lost.interfaces.CodeColor;
import de.kit.tva.lost.interfaces.Result;
import de.kit.tva.lost.interfaces.VerifyModelNotifier;
import de.kit.tva.lost.models.LostParser.StatementContext;
import de.tu_bs.cs.isf.cbc.exceptions.SettingsException;
import de.tu_bs.cs.isf.cbc.tool.features.VerifyStatement;
import de.tu_bs.cs.isf.cbc.util.UpdateDiagram;

public class LostVerifier extends VerifyModelNotifier {
    private final static String VERIFY_MARKER = " <- V: ";
    private final static Color VERIFY_SUCCESS = new Color(0, 127, 0);
    private final static Color VERIFY_FAIL = new Color(188, 71, 73);
    private ArrayList<Result> verifiees;
    private LostTranslator lostTranslator;
    private LostCodeHandler lostCodeHandler;
    private ArrayList<CodeColor> highlights;

    public LostVerifier() {
	this.verifiees = new ArrayList<Result>();
	this.lostTranslator = new LostTranslator();
	this.lostCodeHandler = new LostCodeHandler();
	this.highlights = new ArrayList<CodeColor>();
    }

    public ArrayList<Result> getVerifiees() {
	return this.verifiees;
    }

    public void verify(String lostCode)
	    throws DiagramResourceModelException, IOException, CoreException, SettingsException {
	this.verifiees.clear();
	lostTranslator.translate(lostCode);
	generateVerifiees();
	verifyAll();
	UpdateDiagram.getInstance().updateDiagram(lostTranslator.getDiagram());
    }

    public ArrayList<CodeColor> getHighlights() {
	return this.highlights;
    }

    public String getResults(String viewCode) {
	String tmpCode = viewCode;
	for (var verifiee : verifiees) {
	    var statement = verifiee.get() instanceof StatementContext ? verifiee.get().getText().trim()
		    : verifiee.get().getChild(1).getText().trim();
	    var codeSplit = lostCodeHandler.getCodeUntilStatement(viewCode, statement, VERIFY_MARKER);
	    if (codeSplit == null)
		continue;
	    CodeColor highlight = new CodeColor();
	    tmpCode = lostCodeHandler.createInfoForStatement(highlight, tmpCode, statement, VERIFY_MARKER);
	    highlight.colorToSet = verifiee.wasSuccessful() ? VERIFY_SUCCESS : VERIFY_FAIL;
	    highlights.add(highlight);
	    viewCode = codeSplit[0] + VERIFY_MARKER + verifiee.wasSuccessful() + ", " + verifiee.getTime() + "ms"
		    + codeSplit[1];
	}
	return viewCode;
    }

    private boolean verifyAll() throws IOException {
	if (lostTranslator.getFormula() == null) {
	    return false;
	}
	for (int i = 0; i < verifiees.size(); ++i) {
	    verify(verifiees.get(i));
	}
	this.verificationFinished();
	return true;
    }

    private void verify(Result verifiee) throws IOException {
	long startTime = System.nanoTime();
	var statement = lostTranslator.getModelLinker().getStatementFor(verifiee.get());
	var featureProvider = UpdateDiagram.getInstance().getFeatureProvider(lostTranslator.getDiagram());
	VerifyStatement vs = new VerifyStatement(featureProvider);
	vs.verifyStatement(lostTranslator.getDiagram(), statement);
	long endTime = (System.nanoTime() - startTime) / 1000000;
	verifiee.done(endTime, statement.isProven());
	statement.eResource().save(Collections.EMPTY_MAP);
	this.verificationFinished(verifiee);
    }

    private void generateVerifiees() {
	for (var statementCtx : lostTranslator.getModelLinker().getAllContexts()) {
	    this.verifiees.add(new Result(statementCtx));
	}
    }
}
