package de.tu_bs.cs.isf.cbc.mutation.op.cbc;

import java.util.Arrays;

import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.exceptions.MutatorException;

public class CAORB extends CbCMutationOp {
	public CAORB() {
		ops = Arrays.asList("+", "-", "*", "/", "%");
	}
	
	@Override
	public String[] apply(Condition condition) throws MutatorException {
		return applyOperators(condition, ops);
	}
}
