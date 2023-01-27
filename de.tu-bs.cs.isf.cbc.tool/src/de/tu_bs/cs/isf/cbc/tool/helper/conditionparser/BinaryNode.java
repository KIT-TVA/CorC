package de.tu_bs.cs.isf.cbc.tool.helper.conditionparser;

import de.tu_bs.cs.isf.cbc.tool.helper.TokenType;

/**
 * Contains definition of a binary node.
 * @author Fynn
 */
public class BinaryNode implements Node {
	private TokenType type;
	private Node left;
	private Node right;
	
	public BinaryNode(TokenType type, Node left, Node right) {
		this.type = type;
		this.left = left;
		this.right = right;
	}
	
	public TokenType getType() {
		return type;
	}

	public String getRep() {
		if (left == null || right == null) {
			return null;
		}
		if (type == TokenType.AND) {
			return left.getRep() + " && " + right.getRep();
		} else if (type == TokenType.OR) {
			return left.getRep() + " || " + right.getRep();
		} else if (type == TokenType.IMPL) {
			return left.getRep() + " -> " + right.getRep();
		} else if (type == TokenType.EQUI) {
			return left.getRep() + " <-> " + right.getRep();
		} else {
			return left.getRep() + " " + type.name() + " " + right.getRep();
		}
	}
	
	public Node getLeft() {
		return left;
	}
	
	public Node getRight() {
		return right;
	}
}
