package de.kit.tva.lost.interfaces;

import org.antlr.v4.runtime.ParserRuleContext;

public class Testee {
    private ParserRuleContext target;
    private boolean testSuccessful;
    private long time;

    public Testee(ParserRuleContext statementCtx) {
	this.target = statementCtx;
	this.testSuccessful = false;
	this.time = -1;
    }

    public ParserRuleContext get() {
	return target;
    }

    public void done(long time, boolean testSuccessful) {
	this.time = time;
	this.testSuccessful = testSuccessful;
    }

    public long getTime() {
	return this.time;
    }

    public boolean getTestSuccessful() {
	return this.testSuccessful;
    }
}
