package de.kit.tva.lost.models.modifiablesconverters;

import org.antlr.v4.runtime.tree.ParseTree;

import de.kit.tva.lost.interfaces.ModifiablesConverter;
import de.kit.tva.lost.models.parser.LostParser.RepetitionContext;
import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;

public class RepetitionModifiablesConverter extends ModifiablesConverter {

	@Override
	public boolean setModifiables(AbstractStatement statement, ParseTree ruleCtx) {
		var repS = (SmallRepetitionStatement) statement;
		var repCtx = (RepetitionContext) ruleCtx;
		if (repCtx.mod() == null) {
			return false;
		}
		var mods = this.parseModifiables(repCtx.mod().condition().getText());
		for (var m : mods) {
			repS.getPreCondition().getModifiables().add(m);
			repS.getPostCondition().getModifiables().add(m);
		}
		return true;
	}

}
