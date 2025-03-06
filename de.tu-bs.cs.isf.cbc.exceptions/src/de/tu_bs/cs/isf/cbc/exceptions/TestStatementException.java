package de.tu_bs.cs.isf.cbc.exceptions;

public class TestStatementException extends Exception {
	private static final long serialVersionUID = 1L;

	public TestStatementException(String msg) {
		super("TestStatementException: " + msg);
	}
}
