package de.kit.tva.lost.models;

import de.kit.tva.lost.interfaces.LOSTBaseListener;

public class LOSTParserListener extends LOSTBaseListener {
	private static String TRANSLATOR_EXC = "TranslatorException: ";
	
	private boolean formula;
	private boolean globalConditions;
	private boolean vars;
	private boolean renaming;
	
	private String dublicateDefinition(String target) {
		return TRANSLATOR_EXC + "Cannot define '" + target + "' more than once";
	}

	@Override 
	public void exitVars(LOSTParser.VarsContext ctx) {
		if (vars) {
			throw new RuntimeException(dublicateDefinition("Vars"));
		}
	}
	
	@Override 
	public void exitGlobalConditions(LOSTParser.GlobalConditionsContext ctx) {
		if (globalConditions) {
			throw new RuntimeException(dublicateDefinition("GC"));
		}
	}
	
	@Override 
	public void exitRenaming(LOSTParser.RenamingContext ctx) {
		if (renaming) {
			throw new RuntimeException(dublicateDefinition("Renaming"));
		}
	}
	
	@Override 
	public void exitFormula(LOSTParser.FormulaContext ctx) {
		if (formula) {
			throw new RuntimeException(dublicateDefinition("F"));
		}
	}
}
