package de.tu_bs.cs.isf.cbc.tool.helper;

import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.VariableKind;

/**
 * Used for representing java variables for testing purposes. Also contains some useful methods for variable handling.
 * @author Fynn
 */
public class Variable {
	private String type;
	private String name;
	private VariableKind kind = null;
	private String value;
	private String modifier;
	
	public Variable(String type, String name) {
		this.type = type;
		this.name = name;
		this.modifier = "";
	}
	
	public Variable(String modifier, String type, String name) {
		this.modifier = modifier;
		this.type = type;
		this.name = name;
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
			case "short":
				return "Short";
			case "int":
				return "Integer";
			case "long":
				return "Long";
			default:
				return null;
		}
	}
	
	public static boolean containsVar(final List<Variable> lst, final Variable toFind) {
		for (int i = 0; i < lst.size(); i++) {
			if (lst.get(i).getType().equals(toFind.getType()) && lst.get(i).getName().equals(toFind.getName())) {
				return true;
			}
		}
		return false;
	}
	
	public static List<Variable> getAllGVars(final JavaVariables vars) {
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
		int start = code.indexOf(varName);
		if (start == -1) 
			return false;
		while (Character.isWhitespace(code.charAt(--start)));
		if (Character.isAlphabetic(code.charAt(start))) {
			return true;
		} else {
			return false;
		}
	}
	
	public static List<Variable> getAllVars(final JavaVariables vars) {
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
}
