package de.tu_bs.cs.isf.cbc.exceptions;

public class ReferenceException extends Exception {
	private static final long serialVersionUID = 1L;

	public ReferenceException(String msg) {
		super("ReferenceException: " + msg);
	}

}
