package de.tu_bs.cs.isf.cbc.util.presenceconditionparser;

public class PresenceConditionBracketNode implements PresenceConditionNode{
	
	private final PresenceConditionNode expr;
	
	public PresenceConditionBracketNode(PresenceConditionNode expr) {
		this.expr = expr;
	}
	
	public PresenceConditionNode getExpr() {
		return this.expr;
	}
	
	@Override
	public String print() {
		return "("+ expr.print() + ")";
	}

}
