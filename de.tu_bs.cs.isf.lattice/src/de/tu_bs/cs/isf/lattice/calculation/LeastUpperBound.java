package de.tu_bs.cs.isf.lattice.calculation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.tu_bs.cs.isf.lattice.Lattice;
import de.tu_bs.cs.isf.lattice.Node;
import de.tu_bs.cs.isf.lattice.Transition;

public class LeastUpperBound {
	
	private static Lattice lattice;
	
	public static Lattice getLattice() {
//		if (lattice == null) {
			createLattice();
//		}
		return lattice;
	}

	private static void createLattice() {
		Lattice lattice = new Lattice();
    	LeastUpperBound.setLattice(lattice);
//		Node bottom = new Node("bottom");
//		Node a2 = new Node("a2");
//		Node a1 = new Node("a1");
//		Node b2 = new Node("b2");
//		Node b1 = new Node("b1");
//		Node top = new Node("top");
//		lattice.getNodes().addAll(Arrays.asList(a2,a1,b2,b1,bottom,top));
//		Transition bottomA2 = new Transition(bottom, a2);
//		Transition bottomB2 = new Transition(bottom, b2);
//		Transition a2A1 = new Transition(a2, a1);
//		Transition b2B1 = new Transition(b2, b1);
//		Transition b2A1 = new Transition(b2, a1);
//		Transition a1Top = new Transition(a1, top);
//		Transition b1Top = new Transition(b1, top);
    	
//    	Node low = new Node("public");
//		Node high = new Node("private");
//		Node secret = new Node("secret");
//		lattice.getNodes().clear();
//		lattice.getNodes().addAll(Arrays.asList(low,high,secret));
//		Transition lowHigh = new Transition(low, high);
//		Transition HighSecret = new Transition(high, secret);
    	
    	Node low = new Node("public");
		Node high = new Node("private");
		Node secret = new Node("secret");
		Node employeeSecret = new Node("employeesecret");
		Node userSecret = new Node("usersecret");
		lattice.getNodes().clear();
		lattice.getNodes().addAll(Arrays.asList(low,high,secret,employeeSecret,userSecret));
		Transition lowHigh = new Transition(low, high);
		Transition HighESecret = new Transition(high, employeeSecret);
		Transition HighUSecret = new Transition(high, userSecret);
		Transition EToSecret = new Transition(employeeSecret, secret);
		Transition UToSecret = new Transition(userSecret, secret);
	}

	public static void setLattice(Lattice lattice) {
		LeastUpperBound.lattice = lattice;
	}

	public static Node leastUpperBound(List<Node> nodes) {
		if (nodes.size() == 1) {
			return nodes.get(0);
		} else if (nodes.size() > 1){
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
	
	private static void getUpperRecursive(Map<Node, Integer> uppers, Node node, int i) {
		for (Transition transition : node.getOutgoingTransitions()) {
			uppers.put(transition.getTarget(), i+1);
			getUpperRecursive(uppers, transition.getTarget(), i+1);
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
