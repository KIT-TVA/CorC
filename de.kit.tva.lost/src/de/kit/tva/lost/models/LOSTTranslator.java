package de.kit.tva.lost.models;

import java.util.Arrays;
import java.util.List;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import de.kit.tva.lost.models.LOSTParser.CompositionContext;
import de.kit.tva.lost.models.LOSTParser.ConditionContext;
import de.kit.tva.lost.models.LOSTParser.FormulaContext;
import de.kit.tva.lost.models.LOSTParser.GlobalConditionsContext;
import de.kit.tva.lost.models.LOSTParser.GuardsContext;
import de.kit.tva.lost.models.LOSTParser.InitializerContext;
import de.kit.tva.lost.models.LOSTParser.MethodCallSContext;
import de.kit.tva.lost.models.LOSTParser.MlexprContext;
import de.kit.tva.lost.models.LOSTParser.OriginalSContext;
import de.kit.tva.lost.models.LOSTParser.ProgramContext;
import de.kit.tva.lost.models.LOSTParser.RenamingContext;
import de.kit.tva.lost.models.LOSTParser.RepetitionContext;
import de.kit.tva.lost.models.LOSTParser.ReturnSContext;
import de.kit.tva.lost.models.LOSTParser.SelectionContext;
import de.kit.tva.lost.models.LOSTParser.SkipSContext;
import de.kit.tva.lost.models.LOSTParser.StatementContext;
import de.kit.tva.lost.models.LOSTParser.VariableContext;
import de.kit.tva.lost.models.LOSTParser.VarsContext;
import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.VariableKind;
import de.tu_bs.cs.isf.cbc.util.GenerateModelFromCode;
import de.tu_bs.cs.isf.cbc.util.UpdateConditionsOfChildren;

/**
 * Translates a given LOST-Code into an equivalent CorC diagram model
 * {@link de.tu-bs.cs.isf.cbc.cbcclass.ModelClass}.
 */
public class LOSTTranslator {
    JavaVariables vars;
    GlobalConditions conds;
    Renaming renaming;
    CbCFormula formula = CbcmodelFactory.eINSTANCE.createCbCFormula();
    private ProgramContext tree;
    private GenerateModelFromCode gmfc = new GenerateModelFromCode();

    public boolean translate(String lostCode) {
	if (!genParseTree(lostCode)) {
	    return false;
	}
	formula.setName(tree.root().diagram().name().ID().getText());
	for (int i = 0; i < tree.root().diagram().getChildCount(); ++i) {
	    addInitializers(tree.root().diagram().initializer(i));
	}
	return true;
    }

    private boolean genParseTree(String lostCode) {
	LOSTLexer lexer = new LOSTLexer(CharStreams.fromString(lostCode));
	CommonTokenStream tokens = new CommonTokenStream(lexer);
	LOSTParser parser = new LOSTParser(tokens);
	parser.removeErrorListeners();
	parser.addErrorListener(TranslatorErrorListenerModel.getInstance());
	this.tree = parser.program();
	return !TranslatorErrorListenerModel.getInstance().errorOccurred();
    }

    private void addInitializers(InitializerContext partTree) {
	if (partTree == null) {
	    return;
	}
	if (partTree.vars() != null) {
	    addVariables(partTree.vars());
	} else if (partTree.globalConditions() != null) {
	    addGlobalConds(partTree.globalConditions());
	} else if (partTree.renaming() != null) {
	    addRenamers(partTree.renaming());
	} else if (partTree.formula() != null) {
	    addRefinements(partTree.formula());
	}
    }

    private void addVariables(VarsContext varsTree) {
	vars = CbcmodelFactory.eINSTANCE.createJavaVariables();
	for (int i = 0; i < varsTree.getRuleContexts(VariableContext.class).size(); i++) {
	    var variable = CbcmodelFactory.eINSTANCE.createJavaVariable();
	    var treeVariable = varsTree.variable(i).getText();
	    var variableParts = treeVariable.split("\\s");
	    for (int j = 0; j < variableParts.length; j++) {
		variable.setKind(VariableKind.getByName(variableParts[0]));
		variable.setName(String.join(" ", Arrays.copyOfRange(variableParts, 1, variableParts.length)));
	    }
	    vars.getVariables().add(variable);
	}
    }

    private void addGlobalConds(GlobalConditionsContext condsTree) {
	conds = CbcmodelFactory.eINSTANCE.createGlobalConditions();
	for (int i = 0; i < condsTree.getRuleContexts(ConditionContext.class).size(); i++) {
	    var condition = CbcmodelFactory.eINSTANCE.createCondition();
	    condition.setName(condsTree.condition(i).getText());
	    conds.getConditions().add(condition);
	}
    }

    private void addRenamers(RenamingContext renamningTree) {
	renaming = CbcmodelFactory.eINSTANCE.createRenaming();
    }

    private void addRefinements(FormulaContext formulaTree) {
	var statement = CbcmodelFactory.eINSTANCE.createAbstractStatement();
	var preCon = CbcmodelFactory.eINSTANCE.createCondition();
	var postCon = CbcmodelFactory.eINSTANCE.createCondition();
	var preCon2 = CbcmodelFactory.eINSTANCE.createCondition();
	var postCon2 = CbcmodelFactory.eINSTANCE.createCondition();
	statement.setName("statement");
	statement.setPreCondition(preCon);
	statement.setPostCondition(postCon);
	formula.setPreCondition(preCon2);
	formula.setPostCondition(postCon2);
	statement.getPreCondition().setName(formulaTree.pre().condition().getText());
	statement.getPostCondition().setName(formulaTree.post().condition().getText());
	formula.getPreCondition().setName(formulaTree.pre().condition().getText());
	formula.getPostCondition().setName(formulaTree.post().condition().getText());
	formula.setStatement(statement);
	walkRefinement(formula.getStatement(), formulaTree.refinement().refinementRule().getChild(0));
	UpdateConditionsOfChildren.updateConditionsofChildren(formula.getStatement().getPreCondition(), true);
	UpdateConditionsOfChildren.updateConditionsofChildren(formula.getStatement().getPostCondition(), true);
    }

    private void addEmptyPrePost(final AbstractStatement s) {
	var preCon = CbcmodelFactory.eINSTANCE.createCondition();
	var postCon = CbcmodelFactory.eINSTANCE.createCondition();
	preCon.setName("");
	postCon.setName("");
	s.setPreCondition(preCon);
	s.setPostCondition(postCon);
    }

    private AbstractStatement dummyStatement(int num) {
	var statement = CbcmodelFactory.eINSTANCE.createAbstractStatement();
	addEmptyPrePost(statement);
	statement.setName("statement" + num);
	return statement;
    }

    private void walkRefinement(AbstractStatement parent, ParseTree subtree) {
	if (subtree instanceof CompositionContext) {
	    var csCtx = ((CompositionContext) subtree);
	    CompositionStatement cs = CbcmodelFactory.eINSTANCE.createCompositionStatement();
	    cs.setFirstStatement(dummyStatement(1));
	    cs.setSecondStatement(dummyStatement(2));
	    var intm = CbcmodelFactory.eINSTANCE.createCondition();
	    cs.setIntermediateCondition(intm);
	    cs.getIntermediateCondition().setName(csCtx.intm().condition().getText());
	    walkRefinement(cs, csCtx.refinement(0).refinementRule().getChild(0));
	    walkRefinement(cs, csCtx.refinement(1).refinementRule().getChild(0));
	    setRefinement(parent, cs);
	} else if (subtree instanceof StatementContext) {
	    var sCtx = ((StatementContext) subtree);
	    AbstractStatement statement = CbcmodelFactory.eINSTANCE.createAbstractStatement();
	    addEmptyPrePost(statement);
	    statement.setName(sCtx.getText());
	    setRefinement(parent, statement);
	} else if (subtree instanceof SelectionContext) {
	    var selCtx = ((SelectionContext) subtree);
	    SelectionStatement selection = CbcmodelFactory.eINSTANCE.createSelectionStatement();
	    addEmptyPrePost(selection);
	    List<ParseTree> guards = selCtx.guards().children.stream().filter(c -> c instanceof ConditionContext)
		    .toList();
	    for (int i = 0; i < guards.size(); ++i) {
		var g = CbcmodelFactory.eINSTANCE.createCondition();
		g.setName(((GuardsContext) guards).condition(i).getText());
		selection.getGuards().add(g);
		selection.getCommands().add(dummyStatement(i));
		walkRefinement(selection, selCtx.refinement(i));
	    }
	    setRefinement(parent, selection);
	} else if (subtree instanceof RepetitionContext) {
	    var repCtx = ((RepetitionContext) subtree);
	    var repetition = CbcmodelFactory.eINSTANCE.createSmallRepetitionStatement();
	    addEmptyPrePost(repetition);
	    var invariant = CbcmodelFactory.eINSTANCE.createCondition();
	    var guard = CbcmodelFactory.eINSTANCE.createCondition();
	    var variant = CbcmodelFactory.eINSTANCE.createVariant();
	    invariant.setName(repCtx.inv().condition().getText());
	    guard.setName(repCtx.guard().condition().getText());
	    variant.setName(repCtx.var().ID().getText());
	    repetition.setInvariant(invariant);
	    repetition.setGuard(guard);
	    repetition.setVariant(variant);
	    repetition.setLoopStatement(dummyStatement(1));
	    walkRefinement(repetition, repCtx.refinement());
	    setRefinement(parent, repetition);
	} else if (subtree instanceof ReturnSContext) {
	    var retCtx = ((ReturnSContext) subtree);
	    var returnS = CbcmodelFactory.eINSTANCE.createReturnStatement();
	    addEmptyPrePost(returnS);
	    returnS.setName(retCtx.statement().getText());
	    setRefinement(parent, returnS);
	} else if (subtree instanceof OriginalSContext) {
	    var origCtx = ((OriginalSContext) subtree);
	    var originalS = CbcmodelFactory.eINSTANCE.createOriginalStatement();
	    addEmptyPrePost(originalS);
	    originalS.setName(origCtx.statement().getText());
	    setRefinement(parent, originalS);
	} else if (subtree instanceof SkipSContext) {
	    var skipCtx = ((SkipSContext) subtree);
	    var skipS = CbcmodelFactory.eINSTANCE.createSkipStatement();
	    addEmptyPrePost(skipS);
	    setRefinement(parent, skipS);
	} else if (subtree instanceof MethodCallSContext) {
	    var methodCCtx = ((MethodCallSContext) subtree);
	    var methodS = CbcmodelFactory.eINSTANCE.createMethodStatement();
	    addEmptyPrePost(methodS);
	    methodS.setName(methodCCtx.statement().getText());
	    setRefinement(parent, methodS);
	} else if (subtree instanceof MlexprContext) {
	    var mlexprCtx = ((MlexprContext) subtree);
	    var mlexprS = CbcmodelFactory.eINSTANCE.createAbstractStatement();
	    addEmptyPrePost(mlexprS);
	    for (int i = 0; i < mlexprCtx.getRuleContexts(StatementContext.class).size(); i++) {
		mlexprS.setName(mlexprCtx.statement(i).getText() + "\n");
	    }
	    setRefinement(parent, mlexprS);
	}
    }

    private void setRefinement(AbstractStatement parent, AbstractStatement refinement) {
	if (parent instanceof CompositionStatement) {
	    if (((CompositionStatement) parent).getFirstStatement().getRefinement() == null) {
		((CompositionStatement) parent).getFirstStatement().setRefinement(refinement);
	    } else {
		((CompositionStatement) parent).getSecondStatement().setRefinement(refinement);
	    }
	} else if (parent instanceof SmallRepetitionStatement) {
	    ((SmallRepetitionStatement) parent).getLoopStatement().setRefinement(refinement);
	} else if (parent instanceof SelectionStatement) {
	    for (var command : ((SelectionStatement) parent).getCommands()) {
		if (command.getRefinement() == null) {
		    command.setRefinement(refinement);
		}
	    }
	} else if (parent instanceof AbstractStatement) {
	    parent.setRefinement(refinement);
	}
    }
}
