package de.tu_bs.cs.isf.cbc.util;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.emf.ecore.EObject;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;

public class IdRemover {
	private CbCFormula formula;

	public IdRemover(CbCFormula formula) {
		this.formula = formula;
		deleteIds(formula);
		save();
	}
	
	private void save() {
		try {
			formula.eResource().save(Collections.EMPTY_MAP);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private boolean deleteIds(EObject root) {
		for (int i = 0; i < root.eContents().size(); i++) {
			EObject content = root.eContents().get(i);
			if (content instanceof AbstractStatement) {
				try { ((AbstractStatement) content).setId(null); } catch (IllegalStateException e) {}
			}
			if (content.eContents().size() > 0) {
				if (deleteIds(content)) {
					return true;
				}
			}
		}
		return false;
	}
}
