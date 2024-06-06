package de.kit.tva.lost.models;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;

import de.kit.tva.lost.interfaces.AbstractModel;

/**
 * Translates given LOST-Code into an equivalent CorC Diagram. If the diagram
 * already exists, it's contents are updated.
 */
public class TranslatorModel extends AbstractModel {
    private LostTranslator lostTranslator;

    public TranslatorModel() {
	lostTranslator = new LostTranslator();
    }

    public boolean translate(String lostCode) throws DiagramResourceModelException, IOException, CoreException {
	if (!lostTranslator.translate(lostCode)) {
	    return false;
	}
	/*
	 * GenerateDiagramFromModel generator = new GenerateDiagramFromModel(); var
	 * diagramRes =
	 * DiagramResourceModel.getInstance().get(lostTranslator.getFormula().getName())
	 * ; addInitializersTo(lostTranslator.getFormula().getName(), diagramRes);
	 * generator.execute(diagramRes);
	 */
	return true;
    }
}
