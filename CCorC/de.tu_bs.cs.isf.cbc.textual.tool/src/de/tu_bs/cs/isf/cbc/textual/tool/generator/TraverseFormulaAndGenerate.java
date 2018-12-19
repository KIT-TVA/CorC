package de.tu_bs.cs.isf.cbc.textual.tool.generator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCProblem;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.Confidentiality;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Rename;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.cbcmodel.ReturnStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SkipStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.StrengthWeakStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.AbstractStatementImpl;
import de.tu_bs.cs.isf.cbc.util.ConstructCodeBlock;
import de.tu_bs.cs.isf.cbc.util.ProveWithKey;

public class TraverseFormulaAndGenerate {
	
	private JavaVariables vars;
	private GlobalConditions conds;
	private Renaming renaming;
	private URI uri;
	private int numberFile;
	private CbCFormula formula;
	private Resource resource;
	private CbcmodelFactory factory;

	TraverseFormulaAndGenerate(JavaVariables vars, GlobalConditions conds, Renaming renaming, URI uri, CbCFormula formula, Resource resource) {
		this.vars = vars;
		this.conds = conds;
		this.renaming = renaming;
		this.uri = uri;
		this.numberFile = 0;
		this.formula = formula;
		this.resource = resource;
		this.factory = CbcmodelFactory.eINSTANCE;
	}
	
	public CbCFormula traverseFormulaAndGenerate() {
		AbstractStatement statement = formula.getStatement();
		statement.setPreCondition(factory.createCondition());
		statement.getPreCondition().setName(formula.getPreCondition().getName());
		statement.setPostCondition(factory.createCondition());
		statement.getPostCondition().setName(formula.getPostCondition().getName());
		castStatementAndTraverse(statement);
		UpdateVariablesOfConditions.updateConfidentiality(statement, Confidentiality.LOW);
		return formula;
	}

	private void castStatementAndTraverse(AbstractStatement statement) {
		if(statement.getClass().equals(AbstractStatementImpl.class)) {
			ProveWithKey.createProveStatementWithKey(statement, vars, conds, renaming, uri, numberFile++, false);
		} else if (statement instanceof SmallRepetitionStatement) {
			SmallRepetitionStatement repetitionStatement = (SmallRepetitionStatement) statement;
			traverseRepetitionStatement(repetitionStatement);
		} else if (statement instanceof SelectionStatement) {
			SelectionStatement selectionStatement = (SelectionStatement) statement;
			traverseSelectionStatement(selectionStatement);
		} else if (statement instanceof CompositionStatement) {
			CompositionStatement compositionStatement = (CompositionStatement) statement;
			traverseCompositionStatement(compositionStatement);
		} else if (statement instanceof MethodStatement) {
			Collection<CbCFormula> formulas = getLinkedFormulas(statement);
			if (formulas.size() == 1) {
				CbCFormula formula = formulas.iterator().next();
				CbCProblem problem = (CbCProblem) formula.eContainer();
				JavaVariables varsFormula = problem.getJavaVariable();
				GlobalConditions condsFormula = problem.getGlobalcondition();
				Renaming renamingFormula = problem.getRenaming();
				
				List<JavaVariable> vars = mergeJavaVariables(this.vars, varsFormula);
				List<Condition> conds = mergeGlobalConditions(this.conds, condsFormula);
				List<Rename> renaming = mergeRenaming(this.renaming, renamingFormula);
				ProveWithKey.createProveMethodFormulaWithKey(formula.getPreCondition(), statement.getPreCondition(), vars, conds, renaming, uri, numberFile++, false);
				ProveWithKey.createProveMethodFormulaWithKey(statement.getPostCondition(), formula.getPostCondition(), vars, conds, renaming, uri, numberFile++, false);
			}
		} else if (statement instanceof SkipStatement) {
			ProveWithKey.createProveStatementWithKey(statement, vars, conds, renaming, uri, numberFile++, false);
		} else if (statement instanceof ReturnStatement) {
			ProveWithKey.createProveStatementWithKey(statement, vars, conds, renaming, uri, numberFile++, false);
		} else if (statement instanceof StrengthWeakStatement) {
			ProveWithKey.createProvePreImplPreWithKey(((AbstractStatement)statement.eContainer()).getPreCondition(), statement.getPreCondition(), vars, conds, renaming, uri, numberFile++, false);
			ProveWithKey.createProvePreImplPreWithKey(statement.getPostCondition(), ((AbstractStatement)statement.eContainer()).getPostCondition(), vars, conds, renaming, uri, numberFile++, false);
			ProveWithKey.createProveStatementWithKey(statement, vars, conds, renaming, uri, numberFile++, false);
		}
	}

	private void traverseRepetitionStatement(SmallRepetitionStatement repetitionStatement) {
		AbstractStatement loopStatement = repetitionStatement.getLoopStatement();
		loopStatement.setPreCondition(factory.createCondition());
		loopStatement.getPreCondition().setName("(" + repetitionStatement.getInvariant().getName() + ") & (" + repetitionStatement.getGuard().getName() + ")");
		loopStatement.setPostCondition(factory.createCondition());
		loopStatement.getPostCondition().setName(repetitionStatement.getInvariant().getName());
		ProveWithKey.createProvePreWithKey(repetitionStatement.getInvariant(), repetitionStatement.getPreCondition(), vars, conds, renaming, uri, numberFile++, false);
		ProveWithKey.createProvePostWithKey(repetitionStatement.getInvariant(), repetitionStatement.getGuard(), repetitionStatement.getPostCondition(), vars, conds, renaming, uri, numberFile++, false);
		String code = ConstructCodeBlock.constructCodeBlockAndVerify2(repetitionStatement);
		ProveWithKey.createProveVariant2WithKey(code, repetitionStatement.getInvariant(), repetitionStatement.getGuard(), repetitionStatement.getVariant(), vars, conds, renaming, uri, numberFile++, false);
		
		castStatementAndTraverse(loopStatement);
	}
	
	private void traverseSelectionStatement(SelectionStatement selectionStatement) {
		ProveWithKey.createProvePreSelWithKey(selectionStatement.getGuards(), selectionStatement.getPreCondition(), vars, conds, renaming, uri, numberFile++, false);
		for (int i = 0; i < selectionStatement.getCommands().size(); i++) {
			AbstractStatement childStatement = selectionStatement.getCommands().get(i);
			Condition childGuard = selectionStatement.getGuards().get(i);
			
			childStatement.setPreCondition(factory.createCondition());
			childStatement.getPreCondition().setName("(" + selectionStatement.getPreCondition().getName() + ") & (" + childGuard.getName() + ")");
			childStatement.setPostCondition(factory.createCondition());
			childStatement.getPostCondition().setName(selectionStatement.getPostCondition().getName());
			castStatementAndTraverse(childStatement);
		}
	}
	
	private void traverseCompositionStatement(CompositionStatement compositionStatement) {
		AbstractStatement firstStatement = compositionStatement.getFirstStatement();
		AbstractStatement secondStatement = compositionStatement.getSecondStatement();
		
		firstStatement.setPreCondition(factory.createCondition());
		firstStatement.getPreCondition().setName(compositionStatement.getPreCondition().getName());
		firstStatement.setPostCondition(factory.createCondition());
		firstStatement.getPostCondition().setName(compositionStatement.getIntermediateCondition().getName());
		secondStatement.setPreCondition(factory.createCondition());
		secondStatement.getPreCondition().setName(compositionStatement.getIntermediateCondition().getName());
		secondStatement.setPostCondition(factory.createCondition());
		secondStatement.getPostCondition().setName(compositionStatement.getPostCondition().getName());
		castStatementAndTraverse(firstStatement);
		castStatementAndTraverse(secondStatement);
	}
	
	private Collection<CbCFormula> getLinkedFormulas(AbstractStatement statement) {
		final Collection<CbCFormula> ret = new HashSet<CbCFormula>();
		final Collection<CbCFormula> allFormulas = getFormulas();
		for (final CbCFormula possibleFormula : allFormulas) {
			if (!EcoreUtil.equals(this.formula, possibleFormula)) { // always filter out the current formula
				if (possibleFormula != null && possibleFormula.getName().equals(statement.getName())) {
					ret.add(possibleFormula);
				}
			}
		}
		return ret;
	}
	
	private Collection<CbCFormula> getFormulas() {
       Collection<CbCFormula> result = Collections.emptyList();
       URI uri = resource.getURI();
       URI uriTrimmed = uri.trimFragment();
       if (uriTrimmed.isPlatformResource()){
           String platformString = uriTrimmed.toPlatformString(true);
           IResource fileResource = ResourcesPlugin.getWorkspace()
             .getRoot().findMember(platformString);
           if (fileResource != null){
               IProject project = fileResource.getProject();
               result = GetCbCFileUtil.getCbCFormulas(project);
           }
       }
       return result;
	}
	
	private List<JavaVariable> mergeJavaVariables(JavaVariables vars1, JavaVariables vars2) {
		if (vars1 == null && vars2 == null) {
			return null;
		} else if (vars1 != null && vars2 == null) {
			return vars1.getVariables();
		} else if (vars1 == null && vars2 != null) {
			return vars2.getVariables();
		}
		List<JavaVariable> newVars = new ArrayList<JavaVariable>();
		newVars.addAll(vars2.getVariables());
		for (JavaVariable var1 : vars1.getVariables()) {
			boolean isNew = true;
			for (JavaVariable var2 : vars2.getVariables()) {
				if (var1.getName().equals(var2.getName())) {
					isNew = false;
				}
			}
			if (isNew) {
				newVars.add(var1);
			}
			isNew = true;
		}
		return newVars;
	}
	
	private List<Condition> mergeGlobalConditions(GlobalConditions conds1, GlobalConditions conds2) {
		if (conds1 == null && conds2 == null) {
			return null;
		} else if (conds1 != null && conds2 == null) {
			return conds1.getConditions();
		} else if (conds1 == null && conds2 != null) {
			return conds2.getConditions();
		}
		List<Condition> newConds = new ArrayList<Condition>();
		newConds.addAll(conds2.getConditions());
		for (Condition cond1 : conds1.getConditions()) {
			boolean isNew = true;
			for (Condition cond2 : conds2.getConditions()) {
				if (cond1.getName().equals(cond2.getName())) {
					isNew = false;
				}
			}
			if (isNew) {
				newConds.add(cond1);
			}
			isNew = true;
		}
		return newConds;
	}
	
	private List<Rename> mergeRenaming(Renaming renaming1, Renaming renaming2) {
		if (renaming1 == null && renaming2 == null) {
			return null;
		} else if (renaming1 != null && renaming2 == null) {
			return renaming1.getRename();
		} else if (renaming1 == null && renaming2 != null) {
			return renaming2.getRename();
		}
		List<Rename> newRenaming = new ArrayList<Rename>();
		newRenaming.addAll(renaming2.getRename());
		for (Rename rename1 : renaming1.getRename()) {
			boolean isNew = true;
			for (Rename rename2 : renaming2.getRename()) {
				if (rename1.getFunction().equals(rename2.getFunction())
						&& rename1.getType().equals(rename2.getType())
						&& rename1.getNewName().equals(rename2.getNewName())) {
					isNew = false;
				}
			}
			if (isNew) {
				newRenaming.add(rename1);
			}
			isNew = true;
		}
		return newRenaming;
	}

	
}
