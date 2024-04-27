package de.kit.tva.lost.models;

import de.kit.tva.lost.interfaces.AbstractModel;

public class TranslatorModel extends AbstractModel {
    private LOSTTranslator lostTranslator;

    public TranslatorModel() {
	lostTranslator = new LOSTTranslator();
    }

    public void translate(String lostCode) {
	lostTranslator.translate(lostCode);
    }
}
