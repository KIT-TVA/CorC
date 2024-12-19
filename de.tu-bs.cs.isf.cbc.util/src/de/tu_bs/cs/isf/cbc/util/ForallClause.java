package de.tu_bs.cs.isf.cbc.util;

import de.tu_bs.cs.isf.cbc.util.conditionparser.Node;
import de.tu_bs.cs.isf.cbc.util.conditionparser.QuantorNode;

public class ForallClause extends Clause {

    public ForallClause(Node node) {
	super(node);
    }

    @Override
    public String toJava() {
	QuantorNode node = (QuantorNode) getNode();
	var quantorCondition = node.getConditions().getLeft().getRep();
	return this.setIf(quantorCondition, this.getName());
    }

}
