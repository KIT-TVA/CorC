package de.tu_bs.cs.isf.cbc.exceptions;

public class TestAndAssertionGeneratorException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public TestAndAssertionGeneratorException (String msg) {
		super("TestAndAssertionGeneratorException: " + msg);
	}
}
