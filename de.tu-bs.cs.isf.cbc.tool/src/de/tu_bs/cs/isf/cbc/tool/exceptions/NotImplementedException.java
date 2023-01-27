package de.tu_bs.cs.isf.cbc.tool.exceptions;

public class NotImplementedException extends Exception {
	private static final long serialVersionUID = 1L;

	public NotImplementedException(String identifier) {
		super(identifier + " is not yet implemented.");
	}
}
