package de.tu_bs.cs.isf.cbc.tool.helper.conditionparser;

import de.tu_bs.cs.isf.cbc.tool.helper.TokenType;

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
