package de.tu_bs.cs.isf.cbc.mutation.util;

import de.tu_bs.cs.isf.cbc.util.LinePair;

public class MutationInformationTripel {
    public LinePair mutation;
    public String mutantName;

    public MutationInformationTripel(LinePair mutation, String mutantName) {
	this.mutation = mutation;
	this.mutantName = mutantName;
    }
}
