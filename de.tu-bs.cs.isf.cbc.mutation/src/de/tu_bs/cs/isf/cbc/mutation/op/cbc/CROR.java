package de.tu_bs.cs.isf.cbc.mutation.op.cbc;

import java.util.Arrays;

import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.exceptions.MutatorException;

public class CROR extends CbCMutationOp {
	public CROR() {
		ops = Arrays.asList("<", "<=", "=", ">=", ">", "<>"); // TODO: 'true' and 'false' should be mutated too
	}
	@Override
	public String[] apply(Condition condition) throws MutatorException {
		return applyOperators(condition, ops);
	}
}
