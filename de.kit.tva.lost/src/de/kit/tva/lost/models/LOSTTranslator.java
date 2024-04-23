package de.kit.tva.lost.models;

import java.util.Arrays;

import org.antlr.v4.runtime.tree.ParseTree;

import de.kit.tva.lost.models.LOSTParser.CompositionContext;
import de.kit.tva.lost.models.LOSTParser.FormulaContext;
import de.kit.tva.lost.models.LOSTParser.GlobalConditionsContext;
import de.kit.tva.lost.models.LOSTParser.InitializerContext;
import de.kit.tva.lost.models.LOSTParser.ProgramContext;
import de.kit.tva.lost.models.LOSTParser.RenamingContext;
import de.kit.tva.lost.models.LOSTParser.StatementContext;
import de.kit.tva.lost.models.LOSTParser.VarsContext;
import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.cbcmodel.VariableKind;

/**
 * Translates a given LOST-AST {@link de.kit.tva.lost.models.LOSTParser.ProgramContext} 
 * into an equivalent CorC diagram model {@link de.tu-bs.cs.isf.cbc.cbcclass.ModelClass}.
 */
public class LOSTTranslator {
	JavaVariables vars;
	GlobalConditions conds;
	Renaming renaming;
	CbCFormula formula;
	ProgramContext tree;
	
	public LOSTTranslator(ProgramContext tree) {
		this.tree = tree;
	}
	
	public boolean translate() {
		for (int i = 0; i < tree.getChildCount(); ++i) {
			addInitializers(tree.initializer(i));
		}
		return false;
	}
	
	private void addInitializers(InitializerContext partTree) {
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
		for (int i = 0; i < varsTree.getChildCount(); i++) {
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
		for (int i = 0; i < condsTree.getChildCount(); i++) {
			var condition = CbcmodelFactory.eINSTANCE.createCondition();
			condition.setName(condsTree.condition(i).getText());
			conds.getConditions().add(condition);
		}
	}
	
	private void addRenamers(RenamingContext renamningTree) {
		renaming = CbcmodelFactory.eINSTANCE.createRenaming();
	}
	
	private void addRefinements(FormulaContext formulaTree) {
		formula = CbcmodelFactory.eINSTANCE.createCbCFormula();
		var statement = CbcmodelFactory.eINSTANCE.createAbstractStatement();
		var preCon = CbcmodelFactory.eINSTANCE.createCondition();
		var postCon = CbcmodelFactory.eINSTANCE.createCondition();
		statement.setPreCondition(preCon);
		statement.setPostCondition(postCon);
		statement.getPreCondition().setName(formulaTree.pre().condition().getText());
		statement.getPostCondition().setName(formulaTree.post().condition().getText());
		formula.setStatement(statement);
		walkRefinement(formula.getStatement(), formulaTree.refinement().refinementRule().getChild(0));
	}
	
	private void walkRefinement(AbstractStatement parent, ParseTree subtree) {
		if (subtree instanceof CompositionContext) {
			var csCtx = ((CompositionContext)subtree);
			CompositionStatement cs = CbcmodelFactory.eINSTANCE.createCompositionStatement();
			var intm = CbcmodelFactory.eINSTANCE.createCondition();
			cs.setIntermediateCondition(intm);
			cs.getIntermediateCondition().setName(csCtx.intm().getText());
			setRefinement(parent, cs);
			walkRefinement(cs, csCtx.refinement(0).refinementRule().getChild(0));
			walkRefinement(cs, csCtx.refinement(1).refinementRule().getChild(0));
		} else if (subtree instanceof StatementContext) {
			var sCtx = ((StatementContext)subtree);
			AbstractStatement statement = CbcmodelFactory.eINSTANCE.createAbstractStatement();
			statement.setName(sCtx.getText());
			setRefinement(parent, statement);
		}
	}

	private void setRefinement(AbstractStatement parent, AbstractStatement refinement) {
		if (parent instanceof CompositionStatement) {
			if (((CompositionStatement) parent).getFirstStatement() == null) {
				((CompositionStatement) parent).setFirstStatement(refinement);
			} else {
				((CompositionStatement) parent).setSecondStatement(refinement);
			}
		} else if (parent instanceof AbstractStatement) {
			parent.setRefinement(refinement);
		}
	}
}
