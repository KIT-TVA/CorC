package de.tu_bs.cs.isf.cbc.util.presenceconditionparser;

public class PresenceConditionNotNode implements PresenceConditionNode{
	
	private final PresenceConditionNode expr;
	
	public PresenceConditionNotNode(PresenceConditionNode expr) {
		this.expr = expr;
	}
	
	
	public PresenceConditionNode getExpr() {
		return this.expr;
	}
	
	@Override
	public String print() {
		return "!" + expr.print();
	}

}
