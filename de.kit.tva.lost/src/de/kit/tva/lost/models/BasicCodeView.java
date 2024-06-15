package de.kit.tva.lost.models;

import java.util.List;

import org.antlr.v4.runtime.tree.ParseTree;

import de.kit.tva.lost.interfaces.AbstractCodeView;
import de.kit.tva.lost.interfaces.CodeView;
import de.kit.tva.lost.models.LostParser.CompositionContext;
import de.kit.tva.lost.models.LostParser.DiagramContext;
import de.kit.tva.lost.models.LostParser.FormulaContext;
import de.kit.tva.lost.models.LostParser.GuardContext;
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
	addDiagram(lostTree.root().diagram());
	return basicCode;
    }

    private void addDiagram(DiagramContext diagramCtx) {
	appendLine("Diagram(name: " + diagramCtx.name().ID().getText() + ")");
	this.indentLevel++;
	for (int i = 0; i < diagramCtx.getChildCount(); ++i) {
	    addInitializers(diagramCtx.initializer(i));
	}
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
	appendLine("Formula");
	this.indentLevel++;
	walkRefinement(formulaTree.refinement().refinementRule().getChild(0));
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

    private void walkRefinement(ParseTree subtree) {
	if (subtree instanceof CompositionContext) {
	    addComposition((CompositionContext) subtree);
	} else if (subtree instanceof StatementContext) {
	    addStatement((StatementContext) subtree);
	} else if (subtree instanceof SelectionContext) {
	    addSelection((SelectionContext) subtree);
	} else if (subtree instanceof RepetitionContext) {
	    addRepetition((RepetitionContext) subtree);
	} else if (subtree instanceof ReturnSContext) {
	    addReturnStatement((ReturnSContext) subtree);
	} else if (subtree instanceof OriginalSContext) {
	    addOriginalCallStatement((OriginalSContext) subtree);
	} else if (subtree instanceof SkipSContext) {
	    addSkipStatement((SkipSContext) subtree);
	} else if (subtree instanceof MethodCallSContext) {
	    addMethodCallStatement((MethodCallSContext) subtree);
	} else if (subtree instanceof MlexprContext) {
	    addMlExpr((MlexprContext) subtree);
	}
	this.indentLevel--;
    }

    private void addComposition(CompositionContext csCtx) {
	appendLine("Composition");
	this.indentLevel++;
	walkRefinement(csCtx.refinement(0).refinementRule().getChild(0));
	this.indentLevel++;
	walkRefinement(csCtx.refinement(1).refinementRule().getChild(0));

    }

    private void addStatement(StatementContext sCtx) {
	if (sCtx.javaReturn() != null) {
	    // This is a special case since spaces are ignored by default. Hence we need to
	    // insert it manually.
	    appendLine("return " + sCtx.javaReturn().assigner().getText() + ";");
	} else {
	    appendLine(sCtx.getText().trim());
	}
    }

    private void addSelection(SelectionContext selCtx) {
	appendLine("Selection");
	indentLevel++;
	List<ParseTree> guards = selCtx.guards().children.stream().filter(c -> c instanceof GuardContext).toList();
	for (int i = 0; i < guards.size(); ++i) {
	    walkRefinement(selCtx.refinement(i).refinementRule().getChild(0));
	    indentLevel++;
	}
	indentLevel--;
    }

    private void addRepetition(RepetitionContext repCtx) {
	appendLine("Loop");
	indentLevel++;
	walkRefinement(repCtx.refinement().refinementRule().getChild(0));
    }

    private void addReturnStatement(ReturnSContext retCtx) {
	appendLine("R: " + retCtx.statement().getText().trim());
    }

    private void addMethodCallStatement(MethodCallSContext methodCCtx) {
	appendLine("M: " + methodCCtx.statement().getText().trim());
    }

    private void addOriginalCallStatement(OriginalSContext origCtx) {
	appendLine("O: " + origCtx.statement().getText().trim());
    }

    private void addSkipStatement(SkipSContext skipCtx) {
	appendLine("skip");
    }

    private void addMlExpr(MlexprContext mlexprCtx) {
	appendLine("{");
	indentLevel++;
	for (var statementCtx : mlexprCtx.statement()) {
	    addStatement(statementCtx);
	}
	indentLevel--;
	appendLine("}");
    }
}
