package de.tu_bs.cs.isf.cbc.exceptions;

public class DiagnosticsException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public DiagnosticsException(String msg) {
		super("DiagnosticsException: " + msg);
	}
}
