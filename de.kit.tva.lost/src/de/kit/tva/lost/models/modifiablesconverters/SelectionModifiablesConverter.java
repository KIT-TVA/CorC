package de.kit.tva.lost.models.modifiablesconverters;

import org.antlr.v4.runtime.tree.ParseTree;

import de.kit.tva.lost.interfaces.ModifiablesConverter;
import de.kit.tva.lost.models.parser.LostParser.SelectionContext;
import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;

public class SelectionModifiablesConverter extends ModifiablesConverter {

	@Override
	public boolean setModifiables(AbstractStatement statement, ParseTree ruleCtx) {
		var selS = (SelectionStatement) statement;
		var selCtx = (SelectionContext) ruleCtx;
		if (selCtx.mod() == null) {
			return false;
		}
		var mods = this.parseModifiables(selCtx.mod().condition().getText());
		for (var m : mods) {
			selS.getPreCondition().getModifiables().add(m);
			selS.getPostCondition().getModifiables().add(m);
		}
		return true;
	}

}
