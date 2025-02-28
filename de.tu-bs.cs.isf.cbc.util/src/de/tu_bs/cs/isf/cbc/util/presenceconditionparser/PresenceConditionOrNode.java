package de.tu_bs.cs.isf.cbc.util.presenceconditionparser;

public class PresenceConditionOrNode implements PresenceConditionNode{
	private final PresenceConditionNode partA;
	private final PresenceConditionNode partB;
	
	public PresenceConditionOrNode(PresenceConditionNode partA, PresenceConditionNode partB) {
		this.partA = partA;
		this.partB = partB;
	}
	
	public PresenceConditionNode getPartA() {
		return this.partA;
	}

	public PresenceConditionNode getPartB() {
		return this.partB;
	}
	
	@Override
	public String print() {
		return partA.print() + " | " + partB.print();
	}
}
