package de.kit.tva.lost.models.lost;

import de.kit.tva.lost.interfaces.LostBaseListener;
import de.kit.tva.lost.models.parser.LostParser.FormulaContext;
import de.kit.tva.lost.models.parser.LostParser.GlobalConditionsContext;
import de.kit.tva.lost.models.parser.LostParser.RenamingContext;
import de.kit.tva.lost.models.parser.LostParser.VarsContext;

public class LostParserListener extends LostBaseListener {
	private static String TRANSLATOR_EXC = "TranslatorException: ";
	
	private boolean formula;
	private boolean globalConditions;
	private boolean vars;
	private boolean renaming;
	
	private String dublicateDefinition(String target) {
		return TRANSLATOR_EXC + "Cannot define '" + target + "' more than once";
	}

	@Override 
	public void exitVars(VarsContext ctx) {
		if (vars) {
			throw new RuntimeException(dublicateDefinition("Vars"));
		}
	}
	
	@Override 
	public void exitGlobalConditions(GlobalConditionsContext ctx) {
		if (globalConditions) {
			throw new RuntimeException(dublicateDefinition("GC"));
		}
	}
	
	@Override 
	public void exitRenaming(RenamingContext ctx) {
		if (renaming) {
			throw new RuntimeException(dublicateDefinition("Renaming"));
		}
	}
	
	@Override 
	public void exitFormula(FormulaContext ctx) {
		if (formula) {
			throw new RuntimeException(dublicateDefinition("F"));
		}
	}
}
