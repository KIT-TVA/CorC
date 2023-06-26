 package de.tu_bs.cs.isf.lattice;

import java.util.ArrayList;
import java.util.List;

public class Node {

	private String name;
	private List<Transition> outgoingTransitions;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Transition> getOutgoingTransitions() {
		return outgoingTransitions;
	}

	public void setOutgoingTransitions(List<Transition> transitions) {
		this.outgoingTransitions = transitions;
	}

	public Node(String name, List<Transition> transitions) {
		super();
		this.name = name;
		this.outgoingTransitions = transitions;
	}
	
	public Node(String name) {
		this.name = name;
		this.outgoingTransitions = new ArrayList<Transition>();
	}

	@Override
	public String toString() {
		return "Node [name=" + name + ", outgoingTransitions=" + outgoingTransitions + "]";
	}
}
