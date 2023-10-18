package de.tu_bs.cs.isf.cbc.mutation.feature;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.exceptions.MutatorException;
import de.tu_bs.cs.isf.cbc.mutation.op.cbc.CbCMutationOp;
import de.tu_bs.cs.isf.cbc.mutation.util.CopyDiagram;
import de.tu_bs.cs.isf.cbc.mutation.util.MutatedClass;

public class CbCMutator extends Mutator {
	private Condition condition;
	private String targetId;
	private int totalMutantNum;
	private int curPos;

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
	}
	
	@Override
	protected void generateDiagrams() throws Exception {
		for (String mutant : this.mutants) {
			String mutantName = this.originalDiagram.getName() + "Mutant" + this.curPos;
			String diagCopyPath = this.mutationFolder.getLocation().toOSString() + File.separator + mutantName;
			CopyDiagram cd = new CopyDiagram(diagCopyPath, this.originalDiagram, mutantName);
			applyMutationsToDiagrams(cd.getResource(), mutant);
			cd.save();
			this.curPos++;
		}
	}
	
	private void generateMutants() throws Exception {
		applyMutationOperators();
		generateMutantNames(totalMutantNum);
		MutatedClass mc = new MutatedClass(this.originalDiagram, mutantNames);
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
	
	private void generateMutantNames(int size) {
		mutantNames = new String[size];
		for (int i = 0; i < size; i++) {
			mutantNames[i] = this.originalDiagram.getName() + "Mutant" + i;
		}
	}
	
	private void applyMutationsToDiagrams(Resource resource, String mutant) throws IOException {
		CbCFormula formula = this.getFormulaFrom(resource);
		Condition c = findConditionIn(formula.getStatement());
		if (c == null) {
			return;
		}
		c.setName(mutant);
		resource.save(Collections.EMPTY_MAP);
	}

	private Condition findConditionIn(EObject cur) {
		if (cur instanceof AbstractStatement && hasCorrectId(cur)) {
			return findConditionOf(cur);
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
				return (Condition)o;
			}
		}
		return null;
	}

}
