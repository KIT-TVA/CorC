package de.tu_bs.cs.isf.cbc.util;

import de.tu_bs.cs.isf.cbc.util.conditionparser.Node;

public class NotClause extends Clause {

    public NotClause(Node node) {
	super(node);
    }

    @Override
    public String toJava() {
	return this.setIf(this.getName(), this.getName());
    }

}
