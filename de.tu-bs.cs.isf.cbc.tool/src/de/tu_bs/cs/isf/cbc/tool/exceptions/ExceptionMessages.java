package de.tu_bs.cs.isf.cbc.tool.exceptions;

import org.eclipse.core.resources.IFolder;

public final class ExceptionMessages {
	public static String RET = "Return variable not found. Please make sure that one is defined in the diagram.";
	public static String GENCODE = "Couldn't generate Code.";
	public static String IDNULL = "An ID was null.";
	public static String RETRESOLVE = "Couldn't resolve '\\result'-keyword because the return variable was not found.";
	
	public static String sigNotFound(final String signature) {
		return "The signature " + signature + " couldn't be found.";
	}
	
	public static String formulaNotFound(final String callingMethod, final String refFeature) {
		return "Formula of method '" + callingMethod + "' in feature '" + refFeature + "' couldn't be found.";
	}
	
	public static String featureNotFound(final String callingFeature, final String configName) {
		return "Feature '" + callingFeature + "' couldn't be found in the current config '" + configName + "'";
	}
	
	public static String gVarsOfClass(final String className) {
		return "Couldn't get global variables of class '" + className + "'.";
	}

	public static String invalidSymbol(final String indexTreeRep) {
		return "Found invalid symbol in array index '" + indexTreeRep + "'.";
	}
	
	public static String noVarsGiven() {
		return "No variables were given.";
	}
	
	public static String translateToSolverSyntax() {
		return "Couldn't translate given precondition to the solver's syntax.";
	}

	public static String diagnosticsFolderNotFound(IFolder diagnosticsFolder) {
		return "Couldn't locate diagnostics folder '" + diagnosticsFolder + "'."; 
	}
	
}
