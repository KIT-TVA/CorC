package de.tu_bs.cs.isf.cbc.tool.helper;

/**
 * Thrown when an identifier couldn't be found.
 * @author Fynn
 */
public class IdentifierNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public IdentifierNotFoundException(String identifier) {
		super("IdentifierNotFoundException: Identifier '" + identifier + "' is unknown.");
	}
}
