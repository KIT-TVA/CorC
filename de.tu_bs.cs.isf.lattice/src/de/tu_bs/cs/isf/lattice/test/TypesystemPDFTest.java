package de.tu_bs.cs.isf.lattice.test;

import java.util.Arrays;

import de.tu_bs.cs.isf.lattice.Lattice;
import de.tu_bs.cs.isf.lattice.Node;
import de.tu_bs.cs.isf.lattice.Transition;
import de.tu_bs.cs.isf.lattice.calculation.LeastUpperBound;

public class TypesystemPDFTest {
	public static void main(String[] args) {
		Lattice lattice = new Lattice();
		Node P = new Node("P");
		Node PF = new Node("PF");
		Node PB = new Node("PB");
		Node SF = new Node("SF");
		Node SB = new Node("SB");
		Node TS = new Node("TS");

		Node E = new Node("E");

		lattice.getNodes().addAll(Arrays.asList(P, PF, PB, SF, SB, TS));

		Transition P_PF = new Transition(P, PF);
		Transition P_PB = new Transition(P, PB);
		Transition PF_SF = new Transition(PF, SF);
		Transition PB_SB = new Transition(PB, SB);
		Transition SF_TS = new Transition(SF, TS);
		Transition SB_TS = new Transition(SB, TS);

		System.out
				.println("P, PF, PB: " + LeastUpperBound.leastUpperBound(Arrays.asList(P, PF, PB), lattice).getName());
		System.out.println("P, PF: " + LeastUpperBound.leastUpperBound(Arrays.asList(P, PF), lattice).getName());

		Transition PF_SB = new Transition(PF, SB);

		System.out
				.println("P, PF, PB: " + LeastUpperBound.leastUpperBound(Arrays.asList(P, PF, PB), lattice).getName());

		Transition PB_E = new Transition(PB, E);
		Transition E_TS = new Transition(E, TS);

		System.out.println("P, PF, E: " + LeastUpperBound.leastUpperBound(Arrays.asList(P, PF, E), lattice).getName());
		System.out
				.println("P, PF, PB: " + LeastUpperBound.leastUpperBound(Arrays.asList(P, PF, PB), lattice).getName());
		System.out.println(
				"P, PF, PB, E: " + LeastUpperBound.leastUpperBound(Arrays.asList(P, PF, PB, E), lattice).getName());
	}

}
