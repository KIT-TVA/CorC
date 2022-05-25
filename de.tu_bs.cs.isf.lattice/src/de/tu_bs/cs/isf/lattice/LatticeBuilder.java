package de.tu_bs.cs.isf.lattice;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.gef.dot.internal.DotAttributes;
import org.eclipse.gef.dot.internal.DotImport;
import org.eclipse.gef.dot.internal.language.terminals.ID;
import org.eclipse.gef.graph.Edge;
import org.eclipse.gef.graph.Graph;

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
	
	public static Lattice buildLatticeFromDotWithGEF(final File file) {		
		final Lattice lattice = new Lattice();
		DotImport dotImport = new DotImport();
		final List<Graph> importDot = dotImport.importDot(file);
		final Graph graph = importDot.get(0);

		final Map<String, Node> nodes = new HashMap<>(graph.getNodes().size());
		for (final org.eclipse.gef.graph.Node node : graph.getNodes()) {
			final ID name = (ID) node.getAttributes().get(DotAttributes._NAME__GNE);
			System.out.println("Creating node: " + name.toValue());
			nodes.put(name.toValue(), new Node(name.toValue()));			
		}
		System.out.println("");
		lattice.getNodes().addAll(nodes.values());
		
		for (Edge edge : graph.getEdges()) {
			final ID source = (ID) edge.getSource().getAttributes().get(DotAttributes._NAME__GNE);
			final Node from = nodes.get(source.toValue());
			final ID target = (ID) edge.getTarget().getAttributes().get(DotAttributes._NAME__GNE);
			final Node to = nodes.get(target.toValue());
			System.out.println("Creating transition from node " + source.toValue() + " to  node " + target.toValue());
			System.out.println("Node 1: " + from.getName());
			System.out.println("Node 2: " + to.getName());
			System.out.println("");
			final Transition transition = new Transition(from, to);
		}
		
		return lattice;
	}
}
