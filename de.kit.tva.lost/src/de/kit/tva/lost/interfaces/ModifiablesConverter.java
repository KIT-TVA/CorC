package de.kit.tva.lost.interfaces;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.tree.ParseTree;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;

public abstract class ModifiablesConverter {
	protected String methodModifiables = "";

	abstract public boolean setModifiables(AbstractStatement statement, ParseTree ruleCtx);

	public String getModifiables(AbstractStatement statement) {
		String modifiables = "";
		if (statement.getPostCondition() == null) {
			return modifiables;
		}
		if (statement.getPostCondition().getModifiables() == null) {
			return modifiables;
		}
		for (var m : statement.getPostCondition().getModifiables()) {
			modifiables += m + ", ";
		}
		if (statement.getPostCondition().getModifiables().size() >= 1) {
			modifiables = modifiables.substring(0, modifiables.length() - 2);
		}
		return modifiables;
	}

	public String getMethodModifiables() {
		return this.methodModifiables;
	}

	protected List<String> parseModifiables(String modifiables) {
		var modsList = new ArrayList<String>();
		var mods = modifiables.split("\\,");
		for (var m : mods) {
			m = m.trim();
			modsList.add(m);
		}
		return modsList;
	}
}
