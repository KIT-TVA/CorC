package de.tu_bs.cs.isf.lattice.test;

import java.util.Arrays;

import de.tu_bs.cs.isf.lattice.Lattice;
import de.tu_bs.cs.isf.lattice.Node;
import de.tu_bs.cs.isf.lattice.Transition;
import de.tu_bs.cs.isf.lattice.calculation.LeastUpperBound;

public class Test {
	public static void main(String[] args) {
		Lattice lattice = new Lattice();
		Node bottom = new Node("bottom");
		Node a2 = new Node("a2");
		Node a1 = new Node("a1");
		Node b2 = new Node("b2");
		Node b1 = new Node("b1");
		Node top = new Node("top");
		lattice.getNodes().addAll(Arrays.asList(a2, a1, b2, b1, bottom, top));
		Transition bottomA2 = new Transition(bottom, a2);
		Transition bottomB2 = new Transition(bottom, b2);
		Transition a2A1 = new Transition(a2, a1);
		Transition b2B1 = new Transition(b2, b1);
		Transition b2A1 = new Transition(b2, a1);
		Transition a1Top = new Transition(a1, top);
		Transition b1Top = new Transition(b1, top);

		System.out.println(
				"a2, b2, a1: " + LeastUpperBound.leastUpperBound(Arrays.asList(a2, b2, a1), lattice).getName());
		System.out.println("a1, b1: " + LeastUpperBound.leastUpperBound(Arrays.asList(a1, b1), lattice).getName());
		System.out.println("a2, b1: " + LeastUpperBound.leastUpperBound(Arrays.asList(a2, b1), lattice).getName());
		System.out.println("a2, a1: " + LeastUpperBound.leastUpperBound(Arrays.asList(a2, a1), lattice).getName());
		System.out.println("b2, a1: " + LeastUpperBound.leastUpperBound(Arrays.asList(b2, a1), lattice).getName());
		System.out.println(
				"bottom, bottom: " + LeastUpperBound.leastUpperBound(Arrays.asList(bottom, bottom), lattice).getName());
		System.out.println(
				"bottom, a1: " + LeastUpperBound.leastUpperBound(Arrays.asList(bottom, a1), lattice).getName());
		System.out.println("a1, a1: " + LeastUpperBound.leastUpperBound(Arrays.asList(a1, a1), lattice).getName());
		System.out.println("b2, b1: " + LeastUpperBound.leastUpperBound(Arrays.asList(b2, b1), lattice).getName());
	}
}
