package de.tu_bs.cs.isf.cbc.util;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.emf.ecore.EObject;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;

public class IdAdder {
    private CbCFormula formula;

    public IdAdder(CbCFormula formula) {
	this.formula = formula;
	distributeIds(formula);
	save();
    }

    private void save() {
	try {
	    formula.eResource().save(Collections.EMPTY_MAP);
	} catch (NullPointerException | IOException e) {
	    e.printStackTrace();
	}
    }

    private boolean distributeIds(EObject root) {
	for (int i = 0; i < root.eContents().size(); i++) {
	    EObject content = root.eContents().get(i);
	    if (content instanceof AbstractStatement) {
		if (((AbstractStatement) content).getId() != null && !((AbstractStatement) content).getId().isEmpty()) {
		    continue;
		}
		((AbstractStatement) content).generateID();
	    }
	    if (content.eContents().size() > 0) {
		if (distributeIds(content)) {
		    return true;
		}
	    }
	}
	return false;
    }
}
