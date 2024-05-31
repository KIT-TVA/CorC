package de.kit.tva.lost.models;

import java.io.IOException;
import java.util.List;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.OriginalStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Rename;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.cbcmodel.ReturnStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SkipStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;
import de.tu_bs.cs.isf.cbc.util.DiagramPartsExtractor;

/**
 * Translates a given Diagram into equivalent LOST-Code.
 */
public class DiagramTranslator {
    String lostCode;

    private int indentLevel;

    public DiagramTranslator() {
	this.lostCode = new String();
	this.indentLevel = 0;
    }

    public boolean load(String name) throws DiagramTranslatorException, DiagramResourceModelException, IOException {
	DiagramResourceModel.getInstance().get(name);
	return load(DiagramResourceModel.getInstance().getRenaming(), DiagramResourceModel.getInstance().getConds(),
		DiagramResourceModel.getInstance().getVars(), DiagramResourceModel.getInstance().getFormula());
    }

    public boolean load(Renaming renaming, GlobalConditions conds, JavaVariables vars, CbCFormula formula)
	    throws DiagramTranslatorException {
	this.lostCode = "";
	this.indentLevel = 0;
	appendRoot("D(name: " + formula.getName() + ")");
	extractInitializers(renaming, conds, vars, formula);
	return true;
    }

    public String getTranslatedCode() {
	return lostCode.trim();
    }

    private String getIndent() {
	String indent = "";
	for (int i = 0; i < indentLevel; ++i) {
	    indent += '\t';
	}
	return indent;
    }

    private void extractInitializers(Renaming renaming, GlobalConditions conds, JavaVariables vars, CbCFormula formula)
	    throws DiagramTranslatorException {
	extractConds(conds);
	extractRenaming(renaming);
	extractVars(vars);
	extractFormula(formula);
    }

    private void extractInitializers(DiagramPartsExtractor dp) throws DiagramTranslatorException {
	extractInitializers(dp.getRenaming(), dp.getConds(), dp.getVars(), dp.getFormula());
    }

    private void appendLine(String line) {
	lostCode += getIndent() + line + '\n';
    }

    private void appendRoot(String rootStr) {
	appendLine(rootStr);
	indentLevel++;
    }

    private void appendInitializer(String initStr) {
	appendLine(initStr);
	indentLevel++;
    }

    private void appendConditions(List<Condition> conds) {
	for (var cond : conds) {
	    appendLine(cond.getName().trim());
	}
	indentLevel--;
    }

    private void appendRenamers(List<Rename> renamers) {
	for (var renamer : renamers) {
	    appendLine(renamer.getNewName() + " -> " + renamer.getFunction());
	}
	indentLevel--;
    }

    private void appendVars(JavaVariables vars) {
	appendFields(vars);
	appendParams(vars);
	appendLocals(vars);
	indentLevel--;
    }

    // TODO: Fields and params are handled by the cbc class. They are not stored in
    // the method diag.
    private void appendFields(JavaVariables vars) {
	if (vars.getFields() == null) {
	    return;
	}
	for (var field : vars.getFields()) {
	    appendLine("GLOBAL " + field.getName().trim());
	}
    }

    private void appendParams(JavaVariables vars) {
	if (vars.getParams() == null) {
	    return;
	}
	for (var param : vars.getParams()) {
	    appendLine("PARAM " + param.getName());
	}
    }

    private void appendLocals(JavaVariables vars) {
	if (vars.getVariables() == null) {
	    return;
	}
	for (var v : vars.getVariables()) {
	    appendLine("LOCAL " + v.getName());
	}
    }

    private void extractConds(GlobalConditions conds) {
	if (conds == null) {
	    return;
	}
	appendInitializer("GlobalConditions");
	appendConditions(conds.getConditions());
    }

    private void extractRenaming(Renaming renaming) {
	if (renaming == null) {
	    return;
	}
	appendInitializer("Renaming");
	appendRenamers(renaming.getRename());
    }

    private void extractVars(JavaVariables vars) {
	if (vars == null) {
	    return;
	}
	appendInitializer("Vars");
	appendVars(vars);
    }

    private void extractFormula(CbCFormula formula) throws DiagramTranslatorException {
	if (formula == null) {
	    throw new DiagramTranslatorException("Formula is null.");
	}
	String fStr = "F(pre: " + cleanString(formula.getStatement().getPreCondition().getName()) + ", post: "
		+ cleanString(formula.getStatement().getPostCondition().getName()) + ")";
	appendInitializer(fStr);
	appendRefinement(formula.getStatement().getRefinement());
    }

    private String cleanString(String input) {
	return input.replaceAll("[\n\r]+", " ").trim();
    }

    private void appendRefinement(AbstractStatement refinement) {
	if (refinement instanceof CompositionStatement) {
	    appendComposition((CompositionStatement) refinement);
	} else if (refinement instanceof SelectionStatement) {
	    appendSelection((SelectionStatement) refinement);
	} else if (refinement instanceof SmallRepetitionStatement) {
	    appendRepetition((SmallRepetitionStatement) refinement);
	} else if (refinement instanceof SkipStatement) {
	    appendSkipStatement((SkipStatement) refinement);
	} else if (refinement instanceof OriginalStatement) {
	    appendOriginalStatement((OriginalStatement) refinement);
	} else if (refinement instanceof MethodStatement) {
	    appendMethodStatement((MethodStatement) refinement);
	} else if (refinement instanceof ReturnStatement) {
	    appendReturnStatement((ReturnStatement) refinement);
	} else {
	    appendStatement(refinement);
	}
	this.indentLevel--;
    }

    private void appendComposition(CompositionStatement composition) {
	appendLine("C(intm: " + cleanString(composition.getIntermediateCondition().getName()) + ")");
	this.indentLevel++;
	appendRefinement(composition.getFirstStatement().getRefinement());
	this.indentLevel++;
	appendRefinement(composition.getSecondStatement().getRefinement());
    }

    private void appendSelection(SelectionStatement selection) {
	String selLine = "S(";
	for (int i = 0; i < selection.getGuards().size(); ++i) {
	    selLine += "guard: " + cleanString(selection.getGuards().get(i).getName()) + ", ";
	}
	selLine = selLine.substring(0, selLine.length() - 2) + ")";
	appendLine(selLine);
	this.indentLevel++;
	for (int i = 0; i < selection.getCommands().size(); ++i) {
	    appendRefinement(selection.getCommands().get(i).getRefinement());
	    this.indentLevel++;
	}
    }

    private void appendRepetition(SmallRepetitionStatement repetition) {
	appendLine("L(inv: " + cleanString(repetition.getInvariant().getName()) + ", guard: "
		+ cleanString(repetition.getGuard().getName()) + ", var: "
		+ cleanString(repetition.getVariant().getName()) + ")");
	this.indentLevel++;
	appendRefinement(repetition.getLoopStatement().getRefinement());
    }

    private void appendOriginalStatement(OriginalStatement statement) {
	var originalS = (OriginalStatement) statement;
	appendLine("O: " + cleanString(originalS.getName()));

    }

    private void appendSkipStatement(SkipStatement statement) {
	appendLine(";");

    }

    private void appendMethodStatement(MethodStatement statement) {
	var methodS = (MethodStatement) statement;
	appendLine("M: " + cleanString(methodS.getName()));

    }

    private void appendReturnStatement(ReturnStatement statement) {
	var returnS = (ReturnStatement) statement;
	appendLine("R: " + cleanString(returnS.getName()));
    }

    private void appendStatement(AbstractStatement statement) {
	if (statement.getName().chars().filter(s -> s == ';').count() > 1)
	    appendMlExpr(statement);
	else {
	    appendLine(cleanString(statement.getName()));
	}
    }

    private void appendMlExpr(AbstractStatement statement) {
	var statements = cleanString(statement.getName()).split(";");
	appendLine("{");
	indentLevel++;
	for (var s : statements) {
	    appendLine(cleanString(s) + ";");
	}
	indentLevel--;
	appendLine("}");
    }
}
