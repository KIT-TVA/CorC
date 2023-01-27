package de.tu_bs.cs.isf.cbc.tool.helper.conditionparser;

import de.tu_bs.cs.isf.cbc.tool.helper.Token;
import de.tu_bs.cs.isf.cbc.tool.helper.TokenType;

/**
 * Contains definition of a quantor node.
 * @author Fynn
 */
public class QuantorNode implements Node {
	private String quantor;
	private Token iteratorType;
	private Token iterator;
	private Node conditions;
	
	public QuantorNode(String quantor) {
		this.quantor = quantor;
		this.iteratorType = null;
		this.iterator = null;
		this.conditions = null;
	}
	
	public Node getConditions() {
		return this.conditions;
	}
	
	public void addIteratorType(Token itType) {
		iteratorType = itType;
	}
	
	public void addIterator(Token it) {
		iterator = it;
	}
	
	public void addConditions(Node conditions) {
		this.conditions = conditions;
	}
	
	public Node getLeft() {
		return conditions;
	}
	
	public Node getRight() {
		return null;
	}
	
	public String getIteratorType() {
		return this.iteratorType.getValue();
	}
	
	public String getIterator() {
		return this.iterator.getValue();
	}

	@Override
	public TokenType getType() {
		return TokenType.KEY;
	}

	@Override
	public String getRep() {
		return "(" + quantor + " " + iteratorType.getValue() + " " + iterator.getValue() + ";" + conditions.getRep() + ")";
	}
}
