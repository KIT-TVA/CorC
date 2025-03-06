package de.tu_bs.cs.isf.cbc.exceptions;

public class UnexpectedTokenException extends Exception {
	private static final long serialVersionUID = 1L;

	public UnexpectedTokenException(char token) {
		super("UnexpectedTokenException: Token '" + token + "' was not expected.");
	}

}
