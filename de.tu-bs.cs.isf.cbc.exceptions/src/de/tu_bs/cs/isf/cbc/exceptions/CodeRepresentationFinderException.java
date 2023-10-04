package de.tu_bs.cs.isf.cbc.exceptions;

public class CodeRepresentationFinderException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public CodeRepresentationFinderException(String msg) {
		super("CodeRepresentationFinderException: " + msg);
	}
}
