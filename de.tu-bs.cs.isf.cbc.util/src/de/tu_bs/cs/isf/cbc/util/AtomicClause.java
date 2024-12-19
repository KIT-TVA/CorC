package de.tu_bs.cs.isf.cbc.util;

import de.tu_bs.cs.isf.cbc.util.conditionparser.Node;

public class AtomicClause extends Clause {

    public AtomicClause(Node node) {
	super(node);
    }

    @Override
    public String toJava() {
	return this.setIf(this.getName(), this.getName());
    }

}
