package de.kit.tva.lost.models;

import java.util.List;

import org.antlr.v4.runtime.tree.ParseTree;

import de.kit.tva.lost.interfaces.AbstractCodeView;
import de.kit.tva.lost.interfaces.CodeView;
import de.kit.tva.lost.models.LostParser.CompositionContext;
import de.kit.tva.lost.models.LostParser.ConditionContext;
import de.kit.tva.lost.models.LostParser.FormulaContext;
import de.kit.tva.lost.models.LostParser.InitializerContext;
import de.kit.tva.lost.models.LostParser.MethodCallSContext;
import de.kit.tva.lost.models.LostParser.MlexprContext;
import de.kit.tva.lost.models.LostParser.OriginalSContext;
import de.kit.tva.lost.models.LostParser.ProgramContext;
import de.kit.tva.lost.models.LostParser.RepetitionContext;
import de.kit.tva.lost.models.LostParser.ReturnSContext;
import de.kit.tva.lost.models.LostParser.SelectionContext;
import de.kit.tva.lost.models.LostParser.SkipSContext;
import de.kit.tva.lost.models.LostParser.StatementContext;

public class BasicCodeView extends AbstractCodeView implements CodeView {
    private String basicCode;
    private ProgramContext lostTree;
    private int indentLevel;

    @Override
    public String transform(String code) {
	this.lostTree = parse(code);
	this.basicCode = "";
	this.indentLevel = 0;
	appendLine("D(name: " + lostTree.root().diagram().name().ID().getText() + ")");
	this.indentLevel++;
	for (int i = 0; i < lostTree.root().diagram().getChildCount(); ++i) {
	    addInitializers(lostTree.root().diagram().initializer(i));
	}
	return basicCode;
    }

    private void addInitializers(InitializerContext partTree) {
	if (partTree == null) {
	    return;
	}
	if (partTree.formula() != null) {
	    addRefinements(partTree.formula());
	}
    }

    private void addRefinements(FormulaContext formulaTree) {
	appendLine("F");
	this.indentLevel++;
	walkRefinement(formulaTree.refinement().refinementRule().getChild(0));
    }

    private void walkRefinement(ParseTree subtree) {
	if (subtree instanceof CompositionContext) {
	    var csCtx = ((CompositionContext) subtree);
	    appendLine("C");
	    this.indentLevel++;
	    walkRefinement(csCtx.refinement(0).refinementRule().getChild(0));
	    this.indentLevel++;
	    walkRefinement(csCtx.refinement(1).refinementRule().getChild(0));
	} else if (subtree instanceof StatementContext) {
	    var sCtx = ((StatementContext) subtree);
	    appendLine(sCtx.getText().trim());
	} else if (subtree instanceof SelectionContext) {
	    var selCtx = ((SelectionContext) subtree);
	    appendLine("S");
	    indentLevel++;
	    List<ParseTree> guards = selCtx.guards().children.stream().filter(c -> c instanceof ConditionContext)
		    .toList();
	    for (int i = 0; i < guards.size(); ++i) {
		walkRefinement(selCtx.refinement(i).refinementRule().getChild(0));
		indentLevel++;
	    }
	} else if (subtree instanceof RepetitionContext) {
	    var repCtx = ((RepetitionContext) subtree);
	    appendLine("L");
	    indentLevel++;
	    walkRefinement(repCtx.refinement().refinementRule().getChild(0));
	} else if (subtree instanceof ReturnSContext) {
	    var retCtx = ((ReturnSContext) subtree);
	    appendLine(retCtx.statement().getText().trim());
	} else if (subtree instanceof OriginalSContext) {
	    var origCtx = ((OriginalSContext) subtree);
	    appendLine(origCtx.statement().getText().trim());
	} else if (subtree instanceof SkipSContext) {
	    appendLine("skip");
	} else if (subtree instanceof MethodCallSContext) {
	    var methodCCtx = ((MethodCallSContext) subtree);
	    appendLine(methodCCtx.statement().getText().trim());
	} else if (subtree instanceof MlexprContext) {
	    appendLine("multiple_statements");
	}
	this.indentLevel--;
    }

    private void appendLine(String line) {
	basicCode += getIndent() + line + '\n';
    }

    private String getIndent() {
	String indent = "";
	for (int i = 0; i < indentLevel; ++i) {
	    indent += '\t';
	}
	return indent;
    }
}
