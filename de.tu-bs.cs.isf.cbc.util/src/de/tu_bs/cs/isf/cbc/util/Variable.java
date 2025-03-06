package de.tu_bs.cs.isf.cbc.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.VariableKind;
import de.tu_bs.cs.isf.cbc.exceptions.SettingsException;

/**
 * Used for representing java variables for testing purposes. Also contains some
 * useful methods for variable handling.
 * 
 * @author Fynn
 */
public class Variable {
	private String type;
	private String name;
	private VariableKind kind = null;
	private String value;
	private String modifier;

	public Variable(String type, String name) throws SettingsException {
		this.type = type;
		this.name = name;
		this.modifier = "";
		this.value = getDefaultValue();
	}

	public Variable(String modifier, String type, String name) throws SettingsException {
		this.modifier = modifier;
		this.type = type;
		this.name = name;
		this.value = getDefaultValue();
	}

	public String getRep() {
		return (this.getModifier() + " " + this.getType() + " " + this.getName()).trim();
	}

	public String getModifier() {
		return this.modifier;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public String getName() {
		return this.name;
	}

	public String getType() {
		return this.type;
	}

	public void setKind(VariableKind kind) {
		this.kind = kind;
	}

	public VariableKind getKind() {
		return kind;
	}

	public static String getWrapper(String primitiveType) {
		switch (primitiveType) {
			case "short" :
				return "Short";
			case "int" :
				return "Integer";
			case "long" :
				return "Long";
			default :
				return null;
		}
	}

	public static boolean containsVar(final List<Variable> lst, final Variable toFind) {
		if (lst == null || toFind == null) {
			return false;
		}
		for (int i = 0; i < lst.size(); i++) {
			if (toFind.getType().isEmpty() && lst.get(i).getName().equals(toFind.getName())) {
				return true;
			}
			if (lst.get(i).getType().equals(toFind.getType()) && lst.get(i).getName().equals(toFind.getName())) {
				return true;
			}
		}
		return false;
	}

	public static List<Variable> getAllGVars(final JavaVariables vars) throws SettingsException {
		List<Variable> allVars = new ArrayList<Variable>();
		for (var v : vars.getFields()) {
			if (v.getName() == null || v.getType() == null) {
				continue;
			}
			final var variable = new Variable(v.getType(), v.getName());
			variable.setKind(VariableKind.GLOBAL);
			allVars.add(variable);
		}
		return allVars;
	}

	// TODO: Test this
	public static boolean containsVarDefinition(String code, String varName) {
		int start = code.indexOf(varName + " ");
		while (start > 0 && Character.isAlphabetic(code.charAt(start - 1))) {
			start = code.indexOf(varName + " ");
		}
		if (start == -1)
			return false;
		while (Character.isWhitespace(code.charAt(--start)));
		while (Arrays.asList('[', ']').contains(code.charAt(start))) {
			start--;
		}
		if (Character.isAlphabetic(code.charAt(start))) {
			return true;
		} else {
			return false;
		}
	}

	// TODO: Test this
	public static String prefixAllVariables(String code, final JavaVariables vars) {
		final List<String> addedVars = new ArrayList<String>();
		String[] parts = code.split("\\s");
		for (int i = 0; i < parts.length; i++) {
			if (!parts[i].contains("=")) {
				continue;
			}
			String variable = parts[i - 1];
			if (i > 0 && !parts[i - 2].isEmpty() && parts[i - 2].charAt(parts[i - 2].length() - 1) != ';') {
				continue;
			}
			int index = code.indexOf(variable + " ");
			if (index > 1 && !Character.isSpace(code.charAt(index - 2))) {
				continue;
			}
			while (index > 0 && Character.isLetter(code.charAt(index - 1))) {
				index = code.indexOf(variable + " ");
			}
			if (containsVarDefinition(code, variable)) {
				continue;
			}
			if (addedVars.contains(variable)) {
				continue;
			}
			boolean isWord = true;
			for (int j = 0; j < variable.length(); j++) {
				if (!Character.isAlphabetic(variable.charAt(j))) {
					isWord = false;
					break;
				}
			}
			if (CodeHandler.countBrackets(code.substring(0, index), '{') > 1) {
				continue;
			}
			if (!isWord) {
				continue;
			}
			if (isVarType(variable)) {
				continue;
			}
			if (isClassVar(variable, vars)) {
				continue;
			}
			addedVars.add(variable);
			code = code.substring(0, index) + "var " + code.substring(index, code.length());
		}
		return code;
	}

	private static boolean isClassVar(String varName, final JavaVariables vars) {
		for (var v : vars.getVariables()) {
			if (v.getName().equals(varName)) {
				return true;
			}
		}
		for (var v : vars.getFields()) {
			if (v.getName().equals(varName)) {
				return true;
			}
		}
		return false;
	}

	private static boolean isVarType(String type) {
		type = type.toLowerCase();
		switch (type) {
			case "var" :
				return true;
			case "boolean" :
				return true;
			case "int" :
				return true;
			case "integer" :
				return true;
			case "short" :
				return true;
			case "character" :
				return true;
			case "char" :
				return true;
			case "long" :
				return true;
			case "string" :
				return true;
			default :
				return false;
		}
	}

	public static List<Variable> getAllVars(final JavaVariables vars) throws SettingsException {
		List<Variable> allVars = new ArrayList<Variable>();
		for (var v : vars.getFields()) {
			if (v.getName() == null || v.getType() == null) {
				continue;
			}
			final var variable = new Variable(v.getType(), v.getName());
			if (!containsVar(allVars, variable)) {
				variable.setKind(VariableKind.GLOBAL);
				allVars.add(variable);
			}
		}
		for (var v : vars.getParams()) {
			if (v.getName() == null || v.getType() == null) {
				continue;
			}
			final var variable = new Variable(v.getType(), v.getName());
			if (!containsVar(allVars, variable)) {
				variable.setKind(VariableKind.PARAM);
				allVars.add(variable);
			}
		}
		for (var v : vars.getVariables()) {
			if (v.getName() == null) {
				continue;
			}
			final var type = v.getName().split("\\s")[0];
			final var name = v.getName().split("\\s")[1];
			final var variable = new Variable(type, name);
			if (!containsVar(allVars, variable)) {
				variable.setKind(VariableKind.LOCAL);
				allVars.add(variable);
			}
			if (v.getKind() == VariableKind.RETURN) {
				var result = new Variable(type, "result");
				if (!containsVar(allVars, result)) {
					result.setKind(VariableKind.RETURN);
					allVars.add(result);
				}
			}
		}
		return allVars;
	}

	public String getDefaultValue() throws SettingsException {
		if (type.contains("[")) {
			final InputData data = new InputData("", type);
			data.setDefaultValues();
			return data.getArrayRep();
		}
		switch (type) {
			case "byte" :
				return "" + 0;
			case "short" :
				return "" + 0;
			case "int" :
				return "" + 0;
			case "integer" :
				return "" + 0;
			case "long" :
				return "" + 0;
			case "char" :
				return "" + 0;
			case "character" :
				return "x";
			case "boolean" :
				return "false";
			case "string" :
				return "";
			default :
				return "new " + type + "()";
		}
	}

	public static JavaVariable getReturnVar(final JavaVariables vars) {
		for (var v : vars.getVariables()) {
			if (v.getKind() == VariableKind.RETURN) {
				return v;
			}
		}
		for (var p : vars.getParams()) {
			if (p.getName().equals("ret")) {
				var returnVariable = CbcmodelFactory.eINSTANCE.createJavaVariable();
				returnVariable.setKind(VariableKind.RETURNPARAM);
				returnVariable.setName(p.getType() + " " + p.getName());
				return returnVariable;
			}
		}
		return null;
	}

	public static List<String> getVarNames(final String assertion, final List<String> globalVarNames,
			final List<String> parameterVarNames, final String instanceName) {
		var output = new ArrayList<String>();

		for (var v : globalVarNames) {
			if (assertion.contains(v)) {
				output.add(instanceName + "." + v);
			}
		}

		for (var v : parameterVarNames) {
			if (assertion.contains(v)) {
				output.add(v);
			}
		}
		return output;
	}
}
