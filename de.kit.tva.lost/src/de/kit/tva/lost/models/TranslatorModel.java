package de.kit.tva.lost.models;

import de.kit.tva.lost.interfaces.Model;

public class TranslatorModel extends Model {
    LOSTTranslator lostTranslator;

    public TranslatorModel() {
	lostTranslator = new LOSTTranslator();
    }

    public boolean translate(String lostCode) {
	return lostTranslator.translate(lostCode);
    }
}
