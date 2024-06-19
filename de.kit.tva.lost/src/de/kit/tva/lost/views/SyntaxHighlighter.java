package de.kit.tva.lost.views;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import de.kit.tva.lost.interfaces.CodeColor;
import de.kit.tva.lost.interfaces.LostStyle;
import de.kit.tva.lost.interfaces.OperatorInfo;
import de.kit.tva.lost.models.LostParser.DiagramContext;
import de.kit.tva.lost.models.LostParser.FormulaContext;
import de.kit.tva.lost.models.LostParser.GlobalConditionsContext;
import de.kit.tva.lost.models.LostParser.GuardContext;
import de.kit.tva.lost.models.LostParser.IntmContext;
import de.kit.tva.lost.models.LostParser.InvContext;
import de.kit.tva.lost.models.LostParser.MethodCallSContext;
import de.kit.tva.lost.models.LostParser.MlexprContext;
import de.kit.tva.lost.models.LostParser.NameContext;
import de.kit.tva.lost.models.LostParser.OriginalSContext;
import de.kit.tva.lost.models.LostParser.PostContext;
import de.kit.tva.lost.models.LostParser.PreContext;
import de.kit.tva.lost.models.LostParser.ProgramContext;
import de.kit.tva.lost.models.LostParser.RefinementRuleContext;
import de.kit.tva.lost.models.LostParser.RenamingContext;
import de.kit.tva.lost.models.LostParser.ReturnSContext;
import de.kit.tva.lost.models.LostParser.SkipSContext;
import de.kit.tva.lost.models.LostParser.StatementContext;
import de.kit.tva.lost.models.LostParser.VarContext;
import de.kit.tva.lost.models.LostParser.VarsContext;
import de.kit.tva.lost.models.ParseTreeGenerator;

public class SyntaxHighlighter {
    private LostStyle style;
    private ParseTreeGenerator parseTreeGenerator;
    private StyledText codeField;
    private ProgramContext tree;
    private List<CodeColor> allStyles;

    public SyntaxHighlighter(final StyledText codeField, LostStyle style) {
	this.parseTreeGenerator = new ParseTreeGenerator();
	this.codeField = codeField;
	this.style = style;
	this.allStyles = new ArrayList<CodeColor>();
    }

    public void changeStyle(LostStyle style) {
	this.style = style;
    }

    public List<CodeColor> getHighlights() {
	return this.allStyles;
    }

    public boolean applyHighlights() {
	if (codeField.getText() == null) {
	    return false;
	}
	if (!this.parseTreeGenerator.generateParseTree(codeField.getText())) {
	    return false;
	}
	this.tree = this.parseTreeGenerator.get();
	if (!applyStyles())
	    return false;
	return true;
    }

    private boolean applyStyles() {
	setBackgroundRecursive(this.codeField.getParent(), style.getBackgroundColor());
	this.codeField.setBackground(style.getBackgroundColor());
	this.codeField.setForeground(style.getCodeColor());
	this.allStyles.clear();
	applyStylesToRules(this.tree.root());
	return true;
    }

    private void setBackgroundRecursive(Composite composite, Color color) {
	composite.setBackground(color);
	for (int i = 0; i < composite.getChildren().length; i++) {
	    setColorOfChild(composite.getChildren()[i], color);
	}
    }

    private void setColorOfChild(Control control, Color color) {
	control.setBackground(color);
    }

    private void applyStylesToRules(ParserRuleContext rule) {
	CodeColor codeColor = new CodeColor();
	codeColor.colorToSet = getColorForRule(rule);
	if (rule.stop == null) {
	    return;
	}
	codeColor.info = new OperatorInfo(rule.getStart().getLine(), rule.getStart().getStartIndex() + 1,
		rule.getStart().getStopIndex() + 2);
	if (isHighlightable((ParserRuleContext) rule)) {
	    this.allStyles.add(codeColor);
	}
	for (int i = 0; i < rule.getChildCount(); ++i) {
	    if (rule.getChild(i) instanceof ParserRuleContext) {
		applyStylesToRules((ParserRuleContext) rule.getChild(i));
	    }
	}
    }

    private Color getColorForRule(ParserRuleContext rule) {
	if (rule instanceof DiagramContext | rule instanceof FormulaContext | rule instanceof VarsContext
		| rule instanceof GlobalConditionsContext | rule instanceof RenamingContext) {
	    return this.style.getInitializerColor();
	} else if (rule instanceof PreContext | rule instanceof PostContext | rule instanceof IntmContext
		| rule instanceof InvContext | rule instanceof GuardContext | rule instanceof VarContext
		| rule instanceof NameContext) {
	    return this.style.getIndicatorColor();
	} else if (rule instanceof RefinementRuleContext) {
	    return this.style.getRefinementColor();
	}
	return null;
    }

    private boolean isHighlightable(ParserRuleContext rule) {
	if (rule instanceof RefinementRuleContext) {
	    if (isStatement((ParserRuleContext) rule.getChild(0)))
		return false;
	}
	return rule instanceof DiagramContext | rule instanceof FormulaContext | rule instanceof VarsContext
		| rule instanceof GlobalConditionsContext | rule instanceof RenamingContext | rule instanceof PreContext
		| rule instanceof PostContext | rule instanceof IntmContext | rule instanceof InvContext
		| rule instanceof GuardContext | rule instanceof VarContext | rule instanceof NameContext
		| rule instanceof RefinementRuleContext;
    }

    private boolean isStatement(ParserRuleContext rule) {
	return rule instanceof StatementContext | rule instanceof MlexprContext | rule instanceof SkipSContext
		| rule instanceof OriginalSContext | rule instanceof MethodCallSContext
		| rule instanceof ReturnSContext;
    }
}
