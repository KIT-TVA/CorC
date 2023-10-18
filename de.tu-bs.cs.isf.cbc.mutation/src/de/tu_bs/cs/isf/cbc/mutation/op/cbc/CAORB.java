package de.tu_bs.cs.isf.cbc.mutation.op.cbc;

import java.util.Arrays;
import java.util.List;

import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.exceptions.MutatorException;

public class CAORB extends CbCMutationOp {
	@Override
	public String[] apply(Condition condition) throws MutatorException {
		List<Character> ops = Arrays.asList('+', '-', '*', '/', '%');
		int opPos = getOperatorPosition(ops, condition.getName());
		if (noOpFound(opPos, condition.getName().length())) {
			return new String[] {};
		}
		return mutate(ops, opPos, condition.getName());
	}
	
	private int getOperatorPosition(List<Character> ops, String cStr) {
		int counter = 0;
		for (char l : cStr.toCharArray()) {
			if (ops.contains(l)) {
				break;
			}
			counter++;
		}
		return counter;
	}
	
	private boolean noOpFound(int opPos, int len) {
		return opPos == len ? true : false;
	}
	
	private String[] mutate(List<Character> ops, int opPos, String cStr) {
		this.mutations = new String[ops.size()-1];
		int i = 0;
		for (Character l : ops) {
			if (l.equals(cStr.charAt(opPos))) {
				continue;
			}
			this.mutations[i] = cStr.replace(cStr.charAt(opPos), l);
			i++;
		}
		return this.mutations;
	}

}
