package de.kit.tva.lost.models.diagrams;

public class DiagramResourceModelException extends Exception {
	private static final long serialVersionUID = 6358437107830086531L;

	public DiagramResourceModelException(String msg) {
		super("CreateDiagramModelException: " + msg);
	}
}
