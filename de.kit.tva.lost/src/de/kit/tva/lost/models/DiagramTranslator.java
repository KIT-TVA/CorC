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
	    appendLine(cond.getName());
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

    private void appendFields(JavaVariables vars) {
	if (vars.getFields() == null) {
	    return;
	}
	for (var field : vars.getFields()) {
	    appendLine("GLOBAL " + field.getName());
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
	String fStr = "F(pre: " + formula.getStatement().getPreCondition().getName() + ", post: "
		+ formula.getStatement().getPostCondition().getName() + ")";
	appendInitializer(fStr);
	appendRefinement(formula.getStatement().getRefinement());
    }

    private void appendRefinement(AbstractStatement refinement) {
	if (refinement instanceof CompositionStatement) {
	    var composition = (CompositionStatement) refinement;
	    appendLine("C(intm: " + composition.getIntermediateCondition().getName() + ")");
	    this.indentLevel++;
	    appendRefinement(composition.getFirstStatement().getRefinement());
	    this.indentLevel++;
	    appendRefinement(composition.getSecondStatement().getRefinement());
	} else if (refinement instanceof SelectionStatement) {
	    var selection = (SelectionStatement) refinement;
	    String selLine = "S(";
	    for (int i = 0; i < selection.getGuards().size(); ++i) {
		selLine += "guard: " + selection.getGuards().get(i).getName() + ", ";
	    }
	    selLine = selLine.substring(0, selLine.length() - 2) + ")";
	    appendLine(selLine);
	    this.indentLevel++;
	    for (int i = 0; i < selection.getCommands().size(); ++i) {
		appendRefinement(selection.getCommands().get(i).getRefinement());
		this.indentLevel++;
	    }
	} else if (refinement instanceof SmallRepetitionStatement) {
	    var repetition = (SmallRepetitionStatement) refinement;
	    appendLine("L(inv: " + repetition.getInvariant().getName() + ", guard: " + repetition.getGuard().getName()
		    + ", var: " + repetition.getVariant().getName() + ")");
	    this.indentLevel++;
	    appendRefinement(repetition.getLoopStatement().getRefinement());
	} else if (refinement instanceof SkipStatement) {
	    appendLine(";");
	} else if (refinement instanceof OriginalStatement) {
	    var originalS = (OriginalStatement) refinement;
	    appendLine("O: " + originalS.getName().trim());
	} else if (refinement instanceof MethodStatement) {
	    var methodS = (MethodStatement) refinement;
	    appendLine("M: " + methodS.getName().trim());
	} else if (refinement instanceof ReturnStatement) {
	    var returnS = (ReturnStatement) refinement;
	    appendLine("R: " + returnS.getName().trim());
	} else {
	    appendLine(refinement.getName().trim());
	}
	this.indentLevel--;
    }
}
