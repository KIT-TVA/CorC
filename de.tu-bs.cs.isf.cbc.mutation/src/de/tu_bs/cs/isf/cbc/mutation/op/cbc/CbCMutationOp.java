package de.tu_bs.cs.isf.cbc.mutation.op.cbc;

import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.exceptions.MutatorException;
import de.tu_bs.cs.isf.cbc.util.Console;

public abstract class CbCMutationOp {
	protected String[] mutations;
	
	public static CbCMutationOp get(String opName) throws MutatorException {
		switch (opName) {
			case "CAORB":
				return new CAORB();
			default:
				Console.println("Operator '" + opName + "' ignored.");
				return new NOOP();
		}
	}
	
	public String[] apply(Condition condition) throws MutatorException {
		throw new MutatorException("Canno't apply the abstract CbCMutationOp operator.");
	};
}
