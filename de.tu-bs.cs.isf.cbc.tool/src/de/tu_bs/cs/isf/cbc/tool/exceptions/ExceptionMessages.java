package de.tu_bs.cs.isf.cbc.tool.exceptions;

import org.eclipse.core.resources.IFolder;

public final class ExceptionMessages {
	public static final String RET = "Return variable not found. Please make sure that one is defined in the diagram.";
	public static final String GENCODE = "Couldn't generate Code.";
	public static final String IDNULL = "An ID was null.";
	public static final String RETRESOLVE = "Couldn't resolve '\\result'-keyword because the return variable was not found.";
	public static final String WRONG_DIAGNOSTICS_SYNTAX = "Processing a diagnostic file has failed, because the number of data in an expression was wrong.";
	public static final String URI_NULL = "An URI is null.";
	public static final String UNKNOWN_DATA_TYPE = "Found unknown data type.";
	
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
	
	public static String dependencyNotFound(String dep) {
		return "Couldn't locate '" + dep + "' in the system bundles.";
	}
	
	public static String invalidDiagnosticsSymbol(final String symbol) {
		return "Found unknown symbol '" + symbol + "'.";
	}
	
}
