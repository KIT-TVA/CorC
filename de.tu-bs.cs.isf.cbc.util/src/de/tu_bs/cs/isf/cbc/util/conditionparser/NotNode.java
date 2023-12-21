package de.tu_bs.cs.isf.cbc.util.conditionparser;

import de.tu_bs.cs.isf.cbc.util.TokenType;

/**
 * Contains definition of a not node.
 * @author Fynn
 */
public class NotNode implements Node{
	private Node next;
	
	public NotNode(Node next) {
		this.next = next;
	}

	public TokenType getType() {
		return TokenType.REL;
	}

	public String getRep() {
		return "!" + next.getRep();
	}

	public Node getLeft() {
		return next;
	}

	public Node getRight() {
		return null;
	}
}
