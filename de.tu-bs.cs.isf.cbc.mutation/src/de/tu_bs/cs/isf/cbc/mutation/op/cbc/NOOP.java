package de.tu_bs.cs.isf.cbc.mutation.op.cbc;

import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;

public class NOOP extends CbCMutationOp {
	@Override
	public String[] apply(Condition condition) {
		return new String[] {};
	}

}
