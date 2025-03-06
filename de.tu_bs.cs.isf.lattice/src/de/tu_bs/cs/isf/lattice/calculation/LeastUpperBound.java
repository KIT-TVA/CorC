package de.tu_bs.cs.isf.lattice.calculation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.tu_bs.cs.isf.lattice.Lattice;
import de.tu_bs.cs.isf.lattice.Node;
import de.tu_bs.cs.isf.lattice.Transition;

public class LeastUpperBound {

	public static Node leastUpperBound(List<Node> nodes, Lattice lattice) {
		if (nodes.size() == 1) {
			return nodes.get(0);
		} else if (nodes.size() > 1) {
			Node compareNode = nodes.get(0);
			for (int i = 1; i < nodes.size(); i++) {
				Node secondNode = nodes.get(i);
				if (compareNode.equals(secondNode)) {
					continue;
				}
				Map<Node, Integer> upper1 = getUpper(compareNode);
				Map<Node, Integer> upper2 = getUpper(secondNode);
				Node leastUpperNode = calculateLeast(upper1, upper2);
				compareNode = leastUpperNode;
			}
			return compareNode;
		}
		return null;
	}

	private static Map<Node, Integer> getUpper(Node node) {
		Map<Node, Integer> uppers = new HashMap<Node, Integer>();
		uppers.put(node, 0);
		for (Transition transition : node.getOutgoingTransitions()) {
			uppers.put(transition.getTarget(), 1);
			getUpperRecursive(uppers, transition.getTarget(), 1);
		}
		return uppers;
	}

	public static boolean secondHigherThanFirst(Node node1, Node node2) {
		Map<Node, Integer> uppers = getUpper(node1);
		return uppers.keySet().contains(node2);
	}

	private static void getUpperRecursive(Map<Node, Integer> uppers, Node node, int i) {
		for (Transition transition : node.getOutgoingTransitions()) {
			uppers.put(transition.getTarget(), i + 1);
			getUpperRecursive(uppers, transition.getTarget(), i + 1);
		}
	}

	private static Node calculateLeast(Map<Node, Integer> upper1, Map<Node, Integer> upper2) {
		Node least = null;
		int distance = Integer.MAX_VALUE;
		for (Node node1 : upper1.keySet()) {
			if (upper2.containsKey(node1)) {
				int newDistance = upper1.get(node1) + upper2.get(node1);
				if (newDistance < distance) {
					least = node1;
					distance = newDistance;
				} else if (newDistance == distance) {
					System.out.println("ERROR SAME DISTANCE -> NO LATTICE");
				}
			}
		}
		return least;
	}
}
