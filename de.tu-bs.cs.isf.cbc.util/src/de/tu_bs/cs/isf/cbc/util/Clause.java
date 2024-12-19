package de.tu_bs.cs.isf.cbc.util;

import de.tu_bs.cs.isf.cbc.util.conditionparser.Node;

public abstract class Clause implements Coveree {
    public static final String VAL_DELIM = "#";
    public static final String TRUE_FLAG = "true";
    public static final String FALSE_FLAG = "false";

    private String name;
    private Node node;
    private boolean wasTrue;
    private boolean wasFalse;

    public Clause(Node node) {
	this.name = node.getRep();
	this.node = node;
	this.wasFalse = false;
	this.wasTrue = false;
    }

    protected Node getNode() {
	return node;
    }

    protected void wasTrue() {
	this.wasTrue = true;
    }

    protected void wasFalse() {
	this.wasFalse = true;
    }

    protected String setContextAttr(String id, String value) {
	return "context.setAttribute(\"" + id + "\", \"" + value + "\");";
    }

    protected String setIf(String condition, String contextContent) {
	return "if (" + condition + ") { " + this.setContextAttr(contextContent, Clause.TRUE_FLAG) + " } else {"
		+ this.setContextAttr(contextContent, Clause.FALSE_FLAG) + " }\n";
    }

    @Override
    public String getName() {
	return name;
    }

    @Override
    public boolean isFullyCovered() {
	return wasTrue & wasFalse;
    }

    @Override
    public boolean isPartiallyCovered() {
	return wasTrue | wasFalse;
    }
}
