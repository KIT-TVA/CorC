package de.tu_bs.cs.isf.cbc.mutation.op.cbc;

import java.util.Arrays;
import java.util.List;

import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.exceptions.MutatorException;
import de.tu_bs.cs.isf.cbc.util.Console;

public abstract class CbCMutationOp {
	protected List<String> ops = Arrays.asList("");
	protected static String OP_REMOVE = "#";
	protected String[] mutations;
	int opToMutateStart;
	int opToMutateEnd;

	public static CbCMutationOp get(String opName) {
		switch (opName) {
			case "CAORB" :
				return new CAORB();
			case "CCOD" :
				return new CCOD();
			case "CCOR" :
				return new CCOR();
			case "CLOR" :
				return new CLOR();
			case "CROR" :
				return new CROR();
			default :
				Console.println("Operator '" + opName + "' ignored.");
				return new NOOP();
		}
	}

	public String[] apply(Condition condition) throws MutatorException {
		throw new MutatorException("Canno't apply the abstract CbCMutationOp operator.");
	};

	protected String[] applyOperators(Condition condition, List<String> ops) {
		getOperatorPosition(ops, condition.getName());
		if (noOpFound()) {
			return new String[]{};
		}
		return mutate(ops, condition.getName());
	}

	protected void getOperatorPosition(List<String> ops, String cStr) {
		for (String o : ops) {
			this.opToMutateStart = cStr.indexOf(o);
			if (isPartialOp(cStr, this.opToMutateStart))
				continue;
			this.opToMutateEnd = this.opToMutateStart + o.length();
			if (this.opToMutateStart != -1) {
				return;
			}
		}
	}

	protected boolean noOpFound() {
		return this.opToMutateStart == -1 ? true : false;
	}

	protected String[] mutate(List<String> ops, String cStr) {
		this.mutations = new String[ops.size() - 1];
		int i = 0;

		for (String o : ops) {
			if (o.equals(cStr.substring(this.opToMutateStart, this.opToMutateEnd))) {
				continue;
			}
			this.mutations[i] = cStr.substring(0, this.opToMutateStart) + handleOp(o)
					+ cStr.substring(this.opToMutateEnd, cStr.length());
			i++;
		}
		return this.mutations;
	}

	private boolean isPartialOp(String s, int pos) {
		if (pos == -1) {
			return false;
		}
		if (pos > 0 && !Character.isJavaIdentifierPart(s.charAt(pos - 1))
				&& !Arrays.asList(' ', '(', ')', '[', ']').contains(s.charAt(pos - 1))) {
			return true;
		}
		if (pos < s.length() - 1 && !Character.isJavaIdentifierPart(s.charAt(pos + 1))
				&& !Arrays.asList(' ', '(', ')', '[', ']').contains(s.charAt(pos + 1))) {
			return true;
		}
		return false;
	}

	private String handleOp(String o) {
		return o.equals(OP_REMOVE) ? "" : o;
	}

	private boolean hasOperator(String op) {
		return ops.contains(op);
	}
}
