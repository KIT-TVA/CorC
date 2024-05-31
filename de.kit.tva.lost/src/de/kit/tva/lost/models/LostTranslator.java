package de.kit.tva.lost.models;

import java.util.List;

import org.antlr.v4.runtime.tree.ParseTree;

import de.kit.tva.lost.models.LostParser.CompositionContext;
import de.kit.tva.lost.models.LostParser.ConditionContext;
import de.kit.tva.lost.models.LostParser.FormulaContext;
import de.kit.tva.lost.models.LostParser.GlobalConditionsContext;
import de.kit.tva.lost.models.LostParser.InitializerContext;
import de.kit.tva.lost.models.LostParser.MethodCallSContext;
import de.kit.tva.lost.models.LostParser.MlexprContext;
import de.kit.tva.lost.models.LostParser.OriginalSContext;
import de.kit.tva.lost.models.LostParser.ProgramContext;
import de.kit.tva.lost.models.LostParser.RenamerContext;
import de.kit.tva.lost.models.LostParser.RenamingContext;
import de.kit.tva.lost.models.LostParser.RepetitionContext;
import de.kit.tva.lost.models.LostParser.ReturnSContext;
import de.kit.tva.lost.models.LostParser.SelectionContext;
import de.kit.tva.lost.models.LostParser.SkipSContext;
import de.kit.tva.lost.models.LostParser.StatementContext;
import de.kit.tva.lost.models.LostParser.VariableContext;
import de.kit.tva.lost.models.LostParser.VarsContext;
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
import de.tu_bs.cs.isf.cbc.util.UpdateConditionsOfChildren;

/**
 * Translates a given LOST-Code into an equivalent CorC diagram model
 * {@link de.tu-bs.cs.isf.cbc.cbcclass.ModelClass}.
 */
public class LostTranslator {
    private JavaVariables vars;
    private GlobalConditions conds;
    private Renaming renaming;
    private CbCFormula formula;
    private ParseTreeGenerator parseTreeGenerator;
    private ProgramContext tree;

    public LostTranslator() {
	this.formula = CbcmodelFactory.eINSTANCE.createCbCFormula();
	this.parseTreeGenerator = new ParseTreeGenerator();
    }

    public boolean translate(String lostCode) {
	if (!this.parseTreeGenerator.generateParseTree(lostCode)) {
	    return false;
	}
	this.tree = this.parseTreeGenerator.get();
	formula.setName(tree.root().diagram().name().ID().getText());
	for (int i = 0; i < tree.root().diagram().getChildCount(); ++i) {
	    addInitializers(tree.root().diagram().initializer(i));
	}
	return true;
    }

    public CbCFormula getFormula() {
	return this.formula;
    }

    public GlobalConditions getGlobalConditions() {
	return this.conds;
    }

    public Renaming getRenaming() {
	return this.renaming;
    }

    public JavaVariables getVariables() {
	return this.vars;
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
	    var treeVariable = varsTree.variable(i);
	    variable.setKind(VariableKind.getByName(treeVariable.KIND().getText()));
	    variable.setName(treeVariable.TYPE().getText() + " " + treeVariable.ID());
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

    private void addRenamers(RenamingContext renamingTree) {
	renaming = CbcmodelFactory.eINSTANCE.createRenaming();
	for (int i = 0; i < renamingTree.getRuleContexts(RenamerContext.class).size(); i++) {
	    var renamer = CbcmodelFactory.eINSTANCE.createRename();
	    renamer.setFunction(renamingTree.renamer(i).ID().getText());
	    renamer.setNewName(renamingTree.renamer(i).condition().getText());
	    renaming.getRename().add(renamer);
	}
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
	    addComposition(parent, (CompositionContext) subtree);
	} else if (subtree instanceof StatementContext) {
	    addStatement(parent, (StatementContext) subtree);
	} else if (subtree instanceof SelectionContext) {
	    addSelection(parent, (SelectionContext) subtree);
	} else if (subtree instanceof RepetitionContext) {
	    addRepetition(parent, (RepetitionContext) subtree);
	} else if (subtree instanceof ReturnSContext) {
	    addReturnStatement(parent, (ReturnSContext) subtree);
	} else if (subtree instanceof OriginalSContext) {
	    addOriginalStatement(parent, (OriginalSContext) subtree);
	} else if (subtree instanceof SkipSContext) {
	    addSkip(parent, (SkipSContext) subtree);
	} else if (subtree instanceof MethodCallSContext) {
	    addMethodStatement(parent, (MethodCallSContext) subtree);
	} else if (subtree instanceof MlexprContext) {
	    addMlStatement(parent, (MlexprContext) subtree);
	}
    }

    private void addComposition(AbstractStatement parent, CompositionContext csCtx) {
	CompositionStatement cs = CbcmodelFactory.eINSTANCE.createCompositionStatement();
	cs.setFirstStatement(dummyStatement(1));
	cs.setSecondStatement(dummyStatement(2));
	var intm = CbcmodelFactory.eINSTANCE.createCondition();
	cs.setIntermediateCondition(intm);
	cs.getIntermediateCondition().setName(csCtx.intm().condition().getText());
	walkRefinement(cs.getFirstStatement(), csCtx.refinement(0).refinementRule().getChild(0));
	walkRefinement(cs.getSecondStatement(), csCtx.refinement(1).refinementRule().getChild(0));
	setRefinement(parent, cs);
    }

    private void addStatement(AbstractStatement parent, StatementContext sCtx) {
	AbstractStatement statement = CbcmodelFactory.eINSTANCE.createAbstractStatement();
	addEmptyPrePost(statement);
	statement.setName(sCtx.getText());
	setRefinement(parent, statement);
    }

    private void addSelection(AbstractStatement parent, SelectionContext selCtx) {
	SelectionStatement selection = CbcmodelFactory.eINSTANCE.createSelectionStatement();
	addEmptyPrePost(selection);
	List<ParseTree> guards = selCtx.guards().children.stream().filter(c -> c instanceof ConditionContext).toList();
	for (int i = 0; i < guards.size(); ++i) {
	    var g = CbcmodelFactory.eINSTANCE.createCondition();
	    g.setName(selCtx.guards().condition(i).getText());
	    selection.getGuards().add(g);
	    selection.getCommands().add(dummyStatement(i));
	    walkRefinement(selection.getCommands().get(i), selCtx.refinement(i).refinementRule().getChild(0));
	}
	setRefinement(parent, selection);

    }

    private void addRepetition(AbstractStatement parent, RepetitionContext repCtx) {
	var repetition = CbcmodelFactory.eINSTANCE.createSmallRepetitionStatement();
	addEmptyPrePost(repetition);
	var invariant = CbcmodelFactory.eINSTANCE.createCondition();
	var guard = CbcmodelFactory.eINSTANCE.createCondition();
	var variant = CbcmodelFactory.eINSTANCE.createVariant();
	invariant.setName(repCtx.inv().condition().getText());
	guard.setName(repCtx.guard().condition().getText());
	variant.setName(repCtx.var().condition().getText());
	repetition.setInvariant(invariant);
	repetition.setGuard(guard);
	repetition.setVariant(variant);
	repetition.setLoopStatement(dummyStatement(1));
	walkRefinement(repetition.getLoopStatement(), repCtx.refinement().refinementRule().getChild(0));
	setRefinement(parent, repetition);

    }

    private void addOriginalStatement(AbstractStatement parent, OriginalSContext origCtx) {
	var originalS = CbcmodelFactory.eINSTANCE.createOriginalStatement();
	addEmptyPrePost(originalS);
	originalS.setName(origCtx.statement().getText());
	setRefinement(parent, originalS);

    }

    private void addMethodStatement(AbstractStatement parent, MethodCallSContext methodCCtx) {
	var methodS = CbcmodelFactory.eINSTANCE.createMethodStatement();
	addEmptyPrePost(methodS);
	methodS.setName(methodCCtx.statement().getText());
	setRefinement(parent, methodS);

    }

    private void addSkip(AbstractStatement parent, SkipSContext sCtx) {
	var skipS = CbcmodelFactory.eINSTANCE.createSkipStatement();
	addEmptyPrePost(skipS);
	setRefinement(parent, skipS);
    }

    private void addReturnStatement(AbstractStatement parent, ReturnSContext retCtx) {
	var returnS = CbcmodelFactory.eINSTANCE.createReturnStatement();
	addEmptyPrePost(returnS);
	returnS.setName(retCtx.statement().getText());
	setRefinement(parent, returnS);

    }

    private void addMlStatement(AbstractStatement parent, MlexprContext mlexprCtx) {
	var mlexprS = CbcmodelFactory.eINSTANCE.createAbstractStatement();
	addEmptyPrePost(mlexprS);
	for (int i = 0; i < mlexprCtx.getRuleContexts(StatementContext.class).size(); i++) {
	    mlexprS.setName(mlexprCtx.statement(i).getText() + "\n");
	}
	setRefinement(parent, mlexprS);
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
