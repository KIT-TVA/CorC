package de.tu_bs.cs.isf.cbc.mutation.op.cbc;

import java.util.Arrays;

import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.exceptions.MutatorException;

public class CCOD extends CbCMutationOp {
	public CCOD() {
		ops = Arrays.asList("!", OP_REMOVE);
	}

	@Override
	public String[] apply(Condition condition) throws MutatorException {
		return applyOperators(condition, ops);
	}
}
