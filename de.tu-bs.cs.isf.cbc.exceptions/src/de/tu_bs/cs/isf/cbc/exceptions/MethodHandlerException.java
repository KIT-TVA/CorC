package de.tu_bs.cs.isf.cbc.exceptions;

public class MethodHandlerException extends Exception {
	private static final long serialVersionUID = 1L;

	public MethodHandlerException(String msg) {
		super("MethodHandlerException: " + msg);
	}
}
