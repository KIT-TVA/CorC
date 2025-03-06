package de.kit.tva.lost.models.diagrams;

public class DiagramTranslatorException extends Exception {
    private static final long serialVersionUID = 5693322156899994517L;

    public DiagramTranslatorException(String msg) {
	super("DiagramTranslatorException: " + msg);
    }

}
