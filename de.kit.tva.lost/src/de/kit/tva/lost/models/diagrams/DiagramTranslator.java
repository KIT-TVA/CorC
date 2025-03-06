package de.kit.tva.lost.models.diagrams;

import java.io.IOException;
import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;

import de.kit.tva.lost.models.classes.ClassLoader;
import de.kit.tva.lost.models.modifiablesconverters.CompositionModifiablesConverter;
import de.kit.tva.lost.models.modifiablesconverters.FormulaModifiablesConverter;
import de.kit.tva.lost.models.modifiablesconverters.RepetitionModifiablesConverter;
import de.kit.tva.lost.models.modifiablesconverters.SelectionModifiablesConverter;
import de.kit.tva.lost.models.parser.LostParser;
import de.tu_bs.cs.isf.cbc.cbcclass.ModelClass;
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

/**
 * Translates a given Diagram into equivalent LOST-Code.
 */
public class DiagramTranslator {
    private Resource diagramResource;
    private ModelClass modelClass;
    private String lostCode;
    private static String indicatorDelim;

    private int indentLevel;

    public DiagramTranslator() {
	this.lostCode = new String();
	this.indentLevel = 0;
	this.modelClass = null;
	this.diagramResource = null;
	this.indicatorDelim = LostParser.tokenNames[LostParser.INDICATOR_DELIM];
	this.indicatorDelim = this.indicatorDelim.substring(1, this.indicatorDelim.length() - 1); // remove '
    }

    public boolean load(String name) throws DiagramTranslatorException, DiagramResourceModelException, IOException {
	diagramResource = DiagramResourceModel.getInstance().get(name);
	return load(DiagramResourceModel.getInstance().getRenaming(), DiagramResourceModel.getInstance().getConds(),
		DiagramResourceModel.getInstance().getVars(), DiagramResourceModel.getInstance().getFormula());
    }

    public boolean load(Renaming renaming, GlobalConditions conds, JavaVariables vars, CbCFormula formula)
	    throws DiagramTranslatorException {
	this.lostCode = "";
	this.indentLevel = 0;
	if (formula.eResource() != null && diagramResource != null) {
	    var cl = new ClassLoader(diagramResource.getURI());
	    this.modelClass = cl.get();
	}
	if (this.modelClass != null) {
	    appendRoot("D(sig: " + formula.getMethodObj().getSignature() + ")");
	} else {
	    appendRoot("D(name: " + formula.getName() + ")");
	}
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
	extractVars(formula, vars);
	extractFormula(formula);
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
	    appendLine(renamer.getType() + " " + renamer.getNewName() + " -> " + renamer.getFunction());
	}
	indentLevel--;
    }

    private void appendVars(CbCFormula formula, JavaVariables vars) {
	appendInitializer("Vars");
	appendFields();
	appendParams(formula);
	appendLocals(vars);
	indentLevel--;
    }

    private void appendFields() {
	if (this.modelClass == null) {
	    return;
	}
	for (var field : modelClass.getFields()) {
	    appendLine("PUBLIC " + field.getType() + " " + field.getName().trim());
	}
    }

    private void appendParams(CbCFormula formula) {
	if (this.modelClass == null) {
	    return;
	}
	for (var param : formula.getMethodObj().getParameters()) {
	    appendLine("PARAM " + param.getType() + " " + param.getName());
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

    private void extractVars(CbCFormula formula, JavaVariables vars) {
	if (vars == null) {
	    return;
	}
	appendVars(formula, vars);
    }

    private void extractFormula(CbCFormula formula) throws DiagramTranslatorException {
	if (formula == null) {
	    throw new DiagramTranslatorException("Formula is null.");
	}
	String fStr = constructFormula(formula);
	appendInitializer(fStr);
	appendRefinement(formula.getStatement().getRefinement());
    }

    private String constructFormula(CbCFormula formula) {
	String formulaStr = "F(";
	var modifiables = new FormulaModifiablesConverter().getModifiables(formula.getStatement());
	if (!modifiables.isEmpty()) {
	    formulaStr += "mod: " + modifiables + this.indicatorDelim + " ";
	}
	formulaStr += "pre: " + cleanString(formula.getStatement().getPreCondition().getName()) + this.indicatorDelim
		+ " post: " + cleanString(formula.getStatement().getPostCondition().getName());
	formulaStr += ')';
	return formulaStr;
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
	String compStr = "C(";
	String modifiables = new CompositionModifiablesConverter().getModifiables(composition);
	if (!modifiables.isEmpty()) {
	    compStr += "mod: " + modifiables + this.indicatorDelim + " ";
	}
	appendLine(compStr + "intm: " + cleanString(composition.getIntermediateCondition().getName()) + ")");
	this.indentLevel++;
	appendRefinement(composition.getFirstStatement().getRefinement());
	this.indentLevel++;
	appendRefinement(composition.getSecondStatement().getRefinement());
    }

    private void appendSelection(SelectionStatement selection) {
	String selLine = "S(";
	String modifiables = new SelectionModifiablesConverter().getModifiables(selection);
	if (!modifiables.isEmpty()) {
	    selLine += "mod: " + modifiables + this.indicatorDelim + " ";
	}
	for (int i = 0; i < selection.getGuards().size(); ++i) {
	    selLine += "guard: " + cleanString(selection.getGuards().get(i).getName()) + this.indicatorDelim + " ";
	}
	selLine = selLine.substring(0, selLine.length() - 2) + ")";
	appendLine(selLine);
	this.indentLevel++;
	for (int i = 0; i < selection.getCommands().size(); ++i) {
	    appendRefinement(selection.getCommands().get(i).getRefinement());
	    this.indentLevel++;
	}
	this.indentLevel--;
    }

    private void appendRepetition(SmallRepetitionStatement repetition) {
	String repStr = "L(";
	String modifiables = new RepetitionModifiablesConverter().getModifiables(repetition);
	if (!modifiables.isEmpty()) {
	    repStr += "mod: " + modifiables + this.indicatorDelim + " ";
	}
	appendLine(repStr + "inv: " + cleanString(repetition.getInvariant().getName()) + this.indicatorDelim
		+ " guard: " + cleanString(repetition.getGuard().getName()) + this.indicatorDelim + " var: "
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
