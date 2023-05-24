package de.tu_bs.cs.isf.lattice;

public class Transition {

	Node source;
	Node target;
	public Node getSource() {
		return source;
	}
	public void setSource(Node source) {
		this.source = source;
	}
	public Node getTarget() {
		return target;
	}
	public void setTarget(Node target) {
		this.target = target;
	}
	public Transition(Node source, Node target) {
		super();
		this.source = source;
		this.target = target;
		source.getOutgoingTransitions().add(this);
	}
}
