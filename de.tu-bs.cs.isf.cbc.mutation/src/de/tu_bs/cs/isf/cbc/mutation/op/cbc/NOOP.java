package de.tu_bs.cs.isf.cbc.mutation.op.cbc;

import java.util.Arrays;

import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;

public class NOOP extends CbCMutationOp {
	public NOOP() {
		ops = Arrays.asList("");
	}
	
	@Override
	public String[] apply(Condition condition) {
		return new String[] {};
	}

}
