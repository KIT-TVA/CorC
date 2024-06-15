package de.kit.tva.lost.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.ParserRuleContext;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;

public class ModelLinker {
    private Map<ParserRuleContext, AbstractStatement> links;

    public ModelLinker() {
	reset();
    }

    public void reset() {
	this.links = new HashMap<ParserRuleContext, AbstractStatement>();
    }

    public List<ParserRuleContext> getAllContexts() {
	var contexts = new ArrayList<ParserRuleContext>();
	for (var key : links.keySet()) {
	    contexts.add(key);
	}
	return contexts;
    }

    public ParserRuleContext getContextFor(AbstractStatement statement) {
	return getContextLink(statement);
    }

    public AbstractStatement getStatementFor(ParserRuleContext ctx) {
	return links.get(ctx);
    }

    public void link(AbstractStatement statement, ParserRuleContext ctx) {
	links.put(ctx, statement);
    }

    private ParserRuleContext getContextLink(AbstractStatement statement) {
	for (var key : links.keySet()) {
	    if (links.get(key) == statement) {
		return key;
	    }
	}
	return null;

    }
}
