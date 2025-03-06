package de.tu_bs.cs.isf.cbc.util;

import java.util.ArrayList;
import java.util.List;

public class PredicateDefinition {

	public String name;
	public String replace;
	public String presenceCondition;
	public List<String> varsBound;
	private String type = "(int|char|float|long|boolean|byte|short|double|([A-Z]\\w*))(\\[\\])?";

	public PredicateDefinition(String replace, String defined) {
		this.varsBound = new ArrayList<>();
		setReplace(replace);
		this.name = defined.trim().split(":")[0];
		this.presenceCondition = defined.trim().split(":").length > 1 ? defined.trim().split(":")[1] : "";
	}

	public void setPresenceCondition(String pc) {
		this.presenceCondition = pc;
		return;
	}

	public String setReplace(String replace) {
		String copy = replace;
		varsBound.clear();
		try {
			while (replace.contains("\\exists")) {
				int index = replace.indexOf("\\exists");
				String newVar = replace.substring(index + 8, replace.length()).split(";")[0];
				varsBound.add(newVar.trim().split(" ")[0].trim() + " " + newVar.trim().split(" ")[1].trim());
				replace = replace.replace("\\exists", "done");
			}

			while (replace.contains("\\forall")) {
				int index = replace.indexOf("\\forall");
				String newVar = replace.substring(index + 8, replace.length()).split(";")[0];
				varsBound.add(newVar.trim());
				replace = replace.replace("\\forall", "done");
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return "Error: Please provide data types of bound variables. Didn't save changes.";
		}

		this.replace = copy;
		return "";
	}

	public String getReplace(boolean withBoundDataType) {
		if (withBoundDataType)
			return replace;
		String output = replace;
		for (String var : varsBound) {
			output = output.replace(var, var.split(" ")[1]);
		}
		return output;
	}

	public String checkValidName(String toCheck) {
		if (toCheck == null || toCheck.length() == 0) {
			return "Error: Name must not be empty. Didn't save changes.";
		}

		if (!toCheck.matches("^[A-Za-z0-9_-]+$")) {
			return "Error: Name not valid. See help (?) for more information. Didn't save changes.";
		}
		return "";
	}

	public String checkValidSignature(String toCheck) {
		if (toCheck == null || toCheck.length() == 0) {
			return "Error: Signature must not be empty. Didn't save changes.";
		}

		if (!toCheck.matches("\\w+\\(((" + type + "\\s[A-Za-z]\\w*(\\s)?,(\\s)?)*(" + type + "\\s[A-Za-z]\\w*))?\\)")) {
			return "Error: Signature not valid. See help (?) for more information. Didn't save changes.";
		}
		return "";
	}

	public String checkValidReplace(String toCheck) {
		if (toCheck == null || toCheck.length() == 0) {
			return "Error: Definition must not be empty. Didn't save changes.";
		}
		return "";
	}

	public String checkValidPresenceCondition(String toCheck) {
		if (toCheck.contains(":")) {
			return "Error: Presence condition must not contain ':'. Didn't save changes.";
		}
		return "";
	}
}