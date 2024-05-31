package de.kit.tva.lost.models;

import org.antlr.v4.runtime.ParserRuleContext;
import org.eclipse.swt.graphics.Color;

public class EnhancedRuleContext extends ParserRuleContext {
    ParserRuleContext context;
    Color color;

    public EnhancedRuleContext(ParserRuleContext parserRuleContext) {
	this.context = parserRuleContext;
	this.color = null;
    }

    public ParserRuleContext get() {
	return this.context;
    }

    public void setColor(Color color) {
	this.color = color;
    }
}
