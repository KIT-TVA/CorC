package de.kit.tva.lost.interfaces;

import org.antlr.v4.runtime.ParserRuleContext;

public class Result {
    private ParserRuleContext target;
    private boolean successful;
    private long time;

    public Result(ParserRuleContext statementCtx) {
	this.target = statementCtx;
	this.successful = false;
	this.time = -1;
    }

    public ParserRuleContext get() {
	return target;
    }

    public void done(long time, boolean testSuccessful) {
	this.time = time;
	this.successful = testSuccessful;
    }

    public long getTime() {
	return this.time;
    }

    public boolean wasSuccessful() {
	return this.successful;
    }
}
