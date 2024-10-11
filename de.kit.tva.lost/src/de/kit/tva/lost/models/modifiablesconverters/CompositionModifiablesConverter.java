package de.kit.tva.lost.models.modifiablesconverters;

import org.antlr.v4.runtime.tree.ParseTree;

import de.kit.tva.lost.interfaces.ModifiablesConverter;
import de.kit.tva.lost.models.parser.LostParser.CompositionContext;
import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;

public class CompositionModifiablesConverter extends ModifiablesConverter {
    @Override
    public boolean setModifiables(AbstractStatement statement, ParseTree ruleCtx) {
	var cStatement = (CompositionStatement) statement;
	var cCtx = (CompositionContext) ruleCtx;
	if (cCtx.mod() == null) {
	    return false;
	}
	var mods = parseModifiables(cCtx.mod().condition().getText());
	for (var m : mods) {
	    cStatement.getIntermediateCondition().getModifiables().add(m);
	}
	return true;
    }

    @Override
    public String getModifiables(AbstractStatement statement) {
	String modifiables = "";
	var cs = (CompositionStatement) statement;
	for (var m : cs.getIntermediateCondition().getModifiables()) {
	    modifiables += m + ", ";
	}
	if (cs.getIntermediateCondition().getModifiables().size() >= 1) {
	    modifiables = modifiables.substring(0, modifiables.length() - 2);
	}
	return modifiables;
    }
}
