package de.tu_bs.cs.isf.lattice;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Lattice {

	private List<Node> nodes;

	public static final String LATTICE_DOT = "lattice.dot";

	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

	public Lattice(List<Node> nodes) {
		this.nodes = nodes;
	}

	public Lattice() {
		this.nodes = new ArrayList<Node>();
	}

	public List<Node> getNodesPerName(Set<String> names) {
		List<Node> returnList = new ArrayList<Node>();
		for (Node node : nodes) {
			for (String name : names) {
				if (node.getName().equals(name)) {
					returnList.add(node);
				}
			}
		}
		return returnList;
	}

	public Node getNodePerName(String name) {
		for (Node node : nodes) {
			if (node.getName().equals(name)) {
				return node;
			}
		}
		return null;
	}

	public Node getBottom() {
		List<Node> allNodes = new ArrayList<Node>();
		allNodes.addAll(nodes);
		for (Node node : nodes) {
			for (Transition transition : node.getOutgoingTransitions()) {
				if (allNodes.contains(transition.getTarget())) {
					allNodes.remove(transition.getTarget());
				}
			}
		}
		if (allNodes.size() == 1) {
			return allNodes.get(0);
		} else {
			return null;
		}
	}
}
