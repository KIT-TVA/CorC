package de.tu_bs.cs.isf.cbc.util.conditionparser;

import de.tu_bs.cs.isf.cbc.util.TokenType;

/**
 * Contains definition of a single node.
 * 
 * @author Fynn
 */
public class SingleNode implements Node {
	private TokenType type;
	private String value;

	public SingleNode(TokenType type, String value) {
		this.type = type;
		this.value = value;
	}

	@Override
	public TokenType getType() {
		return type;
	}

	public Node getLeft() {
		return null;
	}

	public Node getRight() {
		return null;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String getRep() {
		if (value == null) {
			return null;
		}
		return value;
	}

}
