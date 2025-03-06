package de.tu_bs.cs.isf.cbc.exceptions;

public class ClassHandlerException extends Exception {
	private static final long serialVersionUID = 1L;

	public ClassHandlerException(String msg) {
		super("ClassHandlerException: " + msg);
	}
}
