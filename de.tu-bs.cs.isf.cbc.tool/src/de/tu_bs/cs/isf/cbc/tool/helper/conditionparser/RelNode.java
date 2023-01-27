package de.tu_bs.cs.isf.cbc.tool.helper.conditionparser;

import de.tu_bs.cs.isf.cbc.tool.helper.Token;
import de.tu_bs.cs.isf.cbc.tool.helper.TokenType;

/**
 * Contains definition of a relational node.
 * @author Fynn
 */
public class RelNode implements Node {
	private TokenType type;
	private Node identifier;
	private String relVal;
	private Node boundary;
	
	public RelNode() {
		this.type = TokenType.REL;
		this.identifier = null;
		this.relVal = null;
		this.boundary = null;
	}
	
	public RelNode(TokenType type, String value, Node left, Node right) {
		this.identifier = left;
		this.relVal = value;
		this.boundary = right;
	}

	@Override
	public TokenType getType() {
		return this.type;
	}

	public void addIdentifier(Token curToken) {
		identifier = new SingleNode(curToken.getType(), curToken.getValue());
	}
	
	public void addIdentifierNode(Node node) {
		identifier = node;
	}

	public void addRel(String value) {
		relVal = value;
	}

	public void addBoundary(Token curToken) {
		boundary = new SingleNode(curToken.getType(), curToken.getValue());
	}
	
	public void addBoundaryNode(Node node) {
		boundary = node;
	}
	
	public Node getLeft() {
		return identifier;
	}
	
	public Node getRight() {
		return boundary;
	}
	
	public String getValue() {
		return this.relVal;
	}

	@Override
	public String getRep() {
		if (identifier == null || boundary == null) {
			return null;
		}
		return identifier.getRep() + " " + relVal + " " + boundary.getRep();
	}
}
