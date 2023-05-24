package de.tu_bs.cs.isf.lattice.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.paypal.digraph.parser.GraphEdge;
import com.paypal.digraph.parser.GraphNode;
import com.paypal.digraph.parser.GraphParser;
import com.paypal.digraph.parser.GraphParserException;

import de.tu_bs.cs.isf.lattice.Lattice;
import de.tu_bs.cs.isf.lattice.LatticeBuilder;
import de.tu_bs.cs.isf.lattice.Node;
import de.tu_bs.cs.isf.lattice.Transition;
import de.tu_bs.cs.isf.lattice.calculation.LeastUpperBound;

public class DotTest {

	public static void main(String[] args) throws GraphParserException, FileNotFoundException {
		long startTime;
		long stopTime;
		
		System.out.println("### DIGRAPH-PARSER ###");
		startTime = System.nanoTime();
		Lattice lattice = LatticeBuilder.buildLatticeFromDot(new FileInputStream("resources/test2.dot"));
		stopTime = System.nanoTime();
		System.out.println("Execution time: " +  ((double) (stopTime - startTime) / 1_000_000_000));
		Node bottom = lattice.getNodePerName("bottom");
		Node a2 = lattice.getNodePerName("a2");
		Node a1 = lattice.getNodePerName("a1");
		Node b2 = lattice.getNodePerName("b2");
		Node b1 = lattice.getNodePerName("b1");
		Node top = lattice.getNodePerName("top");
		
		System.out.println("a2, b2, a1: " + LeastUpperBound.leastUpperBound(Arrays.asList(a2,b2,a1), lattice).getName());
		System.out.println("a1, b1: " + LeastUpperBound.leastUpperBound(Arrays.asList(a1,b1), lattice).getName());
		System.out.println("a2, b1: " + LeastUpperBound.leastUpperBound(Arrays.asList(a2,b1), lattice).getName());
		System.out.println("a2, a1: " + LeastUpperBound.leastUpperBound(Arrays.asList(a2,a1), lattice).getName());
		System.out.println("b2, a1: " + LeastUpperBound.leastUpperBound(Arrays.asList(b2,a1), lattice).getName());
		System.out.println("bottom, bottom: " + LeastUpperBound.leastUpperBound(Arrays.asList(bottom,bottom), lattice).getName());
		System.out.println("bottom, a1: " + LeastUpperBound.leastUpperBound(Arrays.asList(bottom,a1), lattice).getName());
		System.out.println("a1, a1: " + LeastUpperBound.leastUpperBound(Arrays.asList(a1,a1), lattice).getName());
		System.out.println("b2, b1: " + LeastUpperBound.leastUpperBound(Arrays.asList(b2,b1), lattice).getName());
		
		System.out.println("");
	}

}
