package de.tu_bs.cs.isf.cbc.util.presenceconditionparser;

public class PresenceConditionFeatureNode implements PresenceConditionNode{
	
	private final String name;
	
	public PresenceConditionFeatureNode(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	@Override
	public String print() {
		return this.name;
	}
}
