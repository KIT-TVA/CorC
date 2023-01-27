package de.tu_bs.cs.isf.cbc.tool.helper.conditionparser;

import de.tu_bs.cs.isf.cbc.tool.helper.Token;
import de.tu_bs.cs.isf.cbc.tool.helper.TokenType;


/**
 * Contains definition of an operator node.
 * @author Fynn
 */
public class OpNode implements Node {
	private Token token;
	private Node left;
	private Node right;
	
	public OpNode(Token token, Node left, Node right) {
		this.token = token;
		this.left = left;
		this.right = right;
	}
	
	public OpNode() {
		this.token = null;
		this.left = null;
		this.right = null;		
	}

	@Override
	public TokenType getType() {
		return token.getType();
	}

	@Override
	public String getRep() {
		if (left == null || right == null) {
			return null;
		}
		return "(" + left.getRep() + " " + token.getValue() + " " + right.getRep() + ")";
	}
	
	public void addLeft(Token curToken) {
		this.left = new SingleNode(curToken.getType(), curToken.getValue());
	}
	
	public void addOperator(Token opToken) {
		this.token = opToken;
	}
	
	public String getOperator() {
		return this.token.getValue();
	}
	
	public void addRight(Node right) {
		this.right = right;
	}
	
	public Node getLeft() {
		return left;
	}
	
	public Node getRight() {
		return right;
	}
}
