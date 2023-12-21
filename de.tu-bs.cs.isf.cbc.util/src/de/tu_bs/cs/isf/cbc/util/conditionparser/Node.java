package de.tu_bs.cs.isf.cbc.util.conditionparser;

import de.tu_bs.cs.isf.cbc.util.TokenType;

/**
 * The node interface.
 * @author Fynn
 */
public interface Node {
	public TokenType getType();
	public String getRep();
	public Node getLeft();
	public Node getRight();
}
