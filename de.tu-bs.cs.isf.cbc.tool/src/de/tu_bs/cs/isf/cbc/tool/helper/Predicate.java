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
			varsTerms.add(signatureSplit[i].trim());
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
			output += var.split(" ")[1] + ", ";
		}
		return output.substring(0, output.length()-2) + ")";
	}

	public String print() {
		String output = "";
		for (PredicateDefinition pDef : definitions) {
			output += "\t" + signature + "; //" + pDef.definedInFeature + ":" + pDef.definedInClass + ":" + pDef.definedInMethod + ":" + pDef.name + "\n";
			output += "\t\\replacewith (" + pDef.replace + ")\n";
		}
		return output;
	}

	public String printDefForKeY() {
		return "\t" + this.getSignature(false) + ";\n";
	}

	public String printReplaceForKeY(int defNum) {
		String output = "\t" + this.name + "{\n";
		for (String s : varsTerms) {
			output += "\t\t\\schemaVar \\term " + s + ";\n";
		}
		for (String s : definitions.get(defNum).varsBound) {
			output += "\t\t\\schemaVar \\variable " + s + ";\n";
		}
		output += "\t\t\\find (" + getFindTerm() + ")\n";
		if (!definitions.get(defNum).varsBound.isEmpty() && !varsTerms.isEmpty()) {
			output += "\t\t\\varcond (";
			for (String free : definitions.get(defNum).varsBound) {
				for (String term : varsTerms) {
					output += "\\notFreeIn(" + free.split(" ")[1] + ", " + term.split(" ")[1] + "), ";
				}
			}
			if (output.endsWith(", ")) output = output.substring(0, output.length() - 2);
			output += ")\n";
		}
		output += "\t\t\\replacewith (" + definitions.get(defNum).getReplace() + ")\n";
		output += "\t\t\\heuristics(simplify)\n\t};\n\n";
		return output;
	}
}