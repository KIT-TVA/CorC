package de.tu_bs.cs.isf.cbc.tool.helper;

import java.util.ArrayList;
import java.util.List;

public class Predicate {

	public String signature;
	public String name;
	public List<PredicateDefinition> definitions;
	public List<String> varsTerms;
	
	public Predicate(String signature) {
		this.signature = signature;
		this.varsTerms = new ArrayList<>();
		this.definitions = new ArrayList<>();
		this.name = signature.substring(0, signature.indexOf("("));
		resolveVars();
	}
	
	public void resolveVars() {
		varsTerms.clear();
		String[] signatureSplit = this.signature.substring(this.signature.indexOf("(") + 1, this.signature.indexOf(")")).split(",");
		for (int i = 0; i < signatureSplit.length; i++) {
			if (!signatureSplit[i].trim().equals("")) {
				varsTerms.add(signatureSplit[i].trim());
			}
		}
	}
	
	public String removeNamesFromSignature(String sig) {
		String output = sig.substring(0, sig.indexOf("(") + 1);
		sig = sig.substring(sig.indexOf("(") + 1, sig.length() - 2);
		for (String s : sig.split(",")) {
			output += s.trim().split(" ")[0] + ", ";
		}
		return output.substring(0,output.length() - 2) + ")";
	}

	public String getSignature(boolean withVarNames) {
		String output = this.name + "(";
		for (String var : varsTerms) {
			if (withVarNames) {
				output += var + ", ";
			} else {
				output += var.split(" ")[0] + ", ";
			}
		}
		return output.substring(0, output.length()-2) + ")";
	}
	
	public String getFindTerm() {
		String output = this.name + "(";
		for (String var : varsTerms) {
			if (!var.equals("")) {
				output += var.split(" ")[1] + ", ";
			}
		}
		return output.substring(0, output.length()-2) + ")";
	}

	public String print() {
		String output = "";
		for (PredicateDefinition pDef : definitions) {
			output += "\t" + signature + "; //" + pDef.name + ":" + pDef.presenceCondition + "\n";
			output += "\t\\replacewith (" + pDef.replace + ")\n";
		}
		return output;
	}

	public String printDefForKeY() {
		return "\t" + this.getSignature(false) + ";\n";
	}

	public String printReplaceForKeY() {
		String output = "\t" + this.name + "{\n";
		for (String s : varsTerms) {
			output += "\t\t\\schemaVar \\term " + s + ";\n";
		}
		for (PredicateDefinition def : definitions) {
			for (String s : def.varsBound) {
				output += "\t\t\\schemaVar \\variable " + s + ";\n";
			}
		}		
		output += "\t\t\\find (" + getFindTerm() + ")\n";
		String freeIn = "\t\t\\varcond (";
		for (PredicateDefinition def : definitions) {
			if (!def.varsBound.isEmpty() && !varsTerms.isEmpty()) {
				for (String free : def.varsBound) {
					for (String term : varsTerms) {
						freeIn += "\\notFreeIn(" + free.split(" ")[1] + ", " + term.split(" ")[1] + "), ";
					}
				}
			}
			if (freeIn.endsWith(", ")) freeIn = freeIn.substring(0, freeIn.length() - 2);
			freeIn += ")\n";
		}
		output += freeIn.contains("notFreeIn") ? freeIn : "";
		String replaceString = "";
		for (PredicateDefinition def : definitions) {
			replaceString += replaceString.length() == 0 ? def.getReplace(false) : " & " + def.getReplace(false);
		}
		output += "\t\t\\replacewith (" + replaceString + ")\n";
		output += "\t\t\\heuristics(simplify)\n\t};\n\n";
		return output;
	}
}
