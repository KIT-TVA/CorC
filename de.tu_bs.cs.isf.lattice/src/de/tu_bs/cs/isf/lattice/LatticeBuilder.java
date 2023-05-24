package de.tu_bs.cs.isf.lattice;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.paypal.digraph.parser.GraphEdge;
import com.paypal.digraph.parser.GraphNode;
import com.paypal.digraph.parser.GraphParser;

public class LatticeBuilder {
	
	// public static final String LATTICE_DG = "lattice.dg";
	
	public static Lattice buildLatticeFromDot(final InputStream is) {
		final GraphParser parser = new GraphParser(is);
		final Lattice lattice = new Lattice();
				
		final Map<String, GraphNode> graphNodes = parser.getNodes();
		final Map<String, Node> nodes = new HashMap<>(graphNodes.size());
		for (final GraphNode graphNode : graphNodes.values()) {
			System.out.println("Creating node: " + graphNode.getId());
			nodes.put(graphNode.getId(), new Node(graphNode.getId()));
		}
		System.out.println("");
		lattice.getNodes().addAll(nodes.values());
		
		final Map<String, GraphEdge> graphEdges = parser.getEdges();
		for (GraphEdge edge : graphEdges.values()) {
			final Node from = nodes.get(edge.getNode1().getId());
			final Node to = nodes.get(edge.getNode2().getId());
			System.out.println("Creating transition from node " + edge.getNode1().getId() + " to  node " + edge.getNode2().getId());
			System.out.println("Node 1: " + from.getName());
			System.out.println("Node 2: " + to.getName());
			System.out.println("");
			final Transition transition = new Transition(from, to);
		}
		
		return lattice;
	}
}
