package de.tu_bs.cs.isf.cbc.exceptions;

public class MutatorException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public MutatorException(String msg) {
		super("MutatorException: " + msg);
	}
}
