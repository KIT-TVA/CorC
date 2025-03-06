package de.tu_bs.cs.isf.cbc.exceptions;

public class CodeGeneratorException extends Exception {
	private static final long serialVersionUID = 1L;

	public CodeGeneratorException(String msg) {
		super("CodeGeneratorException: " + msg);
	}
}
