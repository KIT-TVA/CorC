package de.kit.tva.lost.models.modifiablesconverters;

import org.antlr.v4.runtime.tree.ParseTree;

import de.kit.tva.lost.interfaces.ModifiablesConverter;
import de.kit.tva.lost.models.parser.LostParser.FormulaContext;
import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;

public class FormulaModifiablesConverter extends ModifiablesConverter {
    @Override
    public boolean setModifiables(AbstractStatement statement, ParseTree ruleCtx) {
	var fCtx = (FormulaContext) ruleCtx;
	if (fCtx.mod() == null) {
	    this.methodModifiables = "";
	    statement.getPreCondition().getModifiables().clear();
	    statement.getPostCondition().getModifiables().clear();
	    return false;
	}
	this.methodModifiables = fCtx.mod().condition().getText();
	var mods = parseModifiables(this.methodModifiables);
	for (var m : mods) {
	    statement.getPreCondition().getModifiables().add(m);
	    statement.getPostCondition().getModifiables().add(m);
	}
	return false;
    }

}
