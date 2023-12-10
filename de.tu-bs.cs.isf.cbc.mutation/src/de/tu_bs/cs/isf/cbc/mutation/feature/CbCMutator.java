package de.tu_bs.cs.isf.cbc.mutation.feature;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.exceptions.MutatorException;
import de.tu_bs.cs.isf.cbc.mutation.op.cbc.CbCMutationOp;
import de.tu_bs.cs.isf.cbc.mutation.util.MutatedClass;
import de.tu_bs.cs.isf.cbc.util.CopyDiagram;

public class CbCMutator extends Mutator {
	private Condition condition;
	private String targetId;
	private int totalMutantNum;
	private int curPos;

	private int test = 0;

	public CbCMutator(List<String> operators) {
		super(operators);
		curPos = 0;
		totalMutantNum = 0;
	}

	@Override
	public void mutate(Diagram diagram, Condition condition) throws Exception {
		this.condition = condition;
		this.targetId = ((AbstractStatement)condition.eContainer()).getId();
		setup(diagram);
		generateMutants();
		ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
	}
	
	@Override
	protected void generateDiagrams() throws Exception {
		for (String mutant : this.mutants) {
			String mutantName = this.mutantBaseName + this.curPos;
			String diagCopyPath = this.mutationFolder.getLocation().toOSString() + File.separator + mutantName;
			CopyDiagram cd = new CopyDiagram(diagCopyPath, this.getOriginalDiagram(), this.getOriginalDiagram().getName());//mutantName);
			applyMutationsToDiagrams(cd.getResource(), mutant);
			cd.save();
			this.getMutantDiagrams().add(cd.getDiagram());
			this.curPos++;
		}
	}
	
	private void generateMutants() throws Exception {
		applyMutationOperators();
		//generateMutantNames(totalMutantNum);
		MutatedClass mc = new MutatedClass(this);
		mc.generate();
	}
	
	private void applyMutationOperators() throws Exception {
		for (String opStr : operators) {
			CbCMutationOp op = CbCMutationOp.get(opStr);
			mutants = op.apply(condition);
			totalMutantNum += mutants.length;
			generateDiagrams();
		}
	}
	
	/*
	private void generateMutantNames(int size) {
		mutantNames = new String[size];
		for (int i = 0; i < size; i++) {
			mutantNames[i] = this.originalDiagram.getName() + "Mutant" + i;
		}
	}*/
	
	private void applyMutationsToDiagrams(Resource resource, String mutant) throws IOException {
		CbCFormula formula = this.getFormulaFrom(resource);
		// TODO: set everything to uproven.
		unproveEverything(formula.getStatement());
		Condition c = findConditionIn(formula.getStatement());
		if (c == null) {
			return;
		}
		c.setName(mutant);
		resource.save(Collections.EMPTY_MAP);
	}
	

	private Condition findConditionIn(EObject cur) {
		if (cur instanceof CompositionStatement) {
			Condition compCon = checkComposition(cur);
			if (compCon != null) {
				((CompositionStatement)cur).setProven(false);
				return compCon;
			}
		} else if (cur instanceof SelectionStatement) {
			Condition selCon = checkSelection(cur);
			if (selCon != null) {
				((SelectionStatement)cur).setProven(false);
				return selCon;
			}
		} else if (cur instanceof AbstractStatement) {
			if (hasCorrectId(cur)) {
				return findConditionOf(cur);
			}
		}
		for (EObject child : cur.eContents()) {
			return findConditionIn(child);
		}
		return null;
	}
	
	private boolean hasCorrectId(EObject cur) {
		return ((AbstractStatement)cur).getId().equals(this.targetId);
	}
	
	private boolean hasCorrectName(EObject o) {
		return ((Condition)o).getName().equals(this.condition.getName());
	}
	
	private Condition findConditionOf(EObject cur) {
		for (EObject o : cur.eContents()) {
			if (o instanceof Condition && hasCorrectName(o)) {
				((AbstractStatement)cur).setProven(false);
				return (Condition)o;
			}
		}
		return null;
	}
	
	private Condition checkComposition(EObject cur) {
		if (hasCorrectId(cur)) {
			return findConditionOf(cur);
		} else {
			Condition conInFirst = findConditionIn(((CompositionStatement)cur).getFirstStatement());
			Condition conInSecond = findConditionIn(((CompositionStatement)cur).getSecondStatement());
			return conInFirst != null ? conInFirst : conInSecond;
		}
	}
	
	private Condition checkSelection(EObject cur) {
		if (hasCorrectId(cur)) {
			return findConditionOf(cur);
		} else {
			SelectionStatement sel = (SelectionStatement)cur;
			for (AbstractStatement c : sel.getCommands()) {
				Condition con = findConditionIn(c);
				if (con != null) {
					return con;
				}
			}
		}
		return null;
	}

}
