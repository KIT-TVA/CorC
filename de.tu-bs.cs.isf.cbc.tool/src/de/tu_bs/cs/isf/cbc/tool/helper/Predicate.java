package de.tu_bs.cs.isf.cbc.tool.helper;

import java.util.ArrayList;
import java.util.List;

public class Predicate {

	public String name;
	public String def;
	public String replace;
	public String find;
	public List<String> varsTerms;
	public List<String> varsFree;
	
	public String definedInFeature;
	public String definedInClass;
	public String definedInMethod;
	
	public Predicate(String config) {
		this.definedInFeature = config.split(":")[0];
		this.definedInClass = config.split(":")[1];
		this.definedInMethod = config.split(":")[2];
		this.varsTerms = new ArrayList<>();
		this.varsFree = new ArrayList<>();
		this.name = "";
		this.def = "";
		this.replace = "";
		this.find = "";
	}
	
	public String printPredicate() {
		return "\t" + this.name + "(" + def + "); //" + this.definedInFeature + ":" + this.definedInClass + ":" + this.definedInMethod;
	}
	
	public String printRule() {
		String output = "\t" + name + "{\n";
		for (String s : varsTerms) {
			output += "\t\t\\schemaVar \\term " + s + ";\n";
		}
		for (String s : varsFree) {
			output += "\t\t\\schemaVar \\variable " + s + ";\n";
		}
		if (!varsFree.isEmpty() && !varsTerms.isEmpty()) {
			output += "\t\t\\find (" + find + ")\n";
			output += "\t\t\\varcond (";
			for (String free : varsFree) {
				for (String term : varsTerms) {
					output += "\\notFreeIn(" + free.split(" ")[1] + ", " + term.split(" ")[1] + "), ";
				}
			}
			if (output.endsWith(", ")) output = output.substring(0, output.length() - 2);
			output += ")\n";
		}
		output += "\t\t\\replacewith (" + replace + ")\n";
		output += "\t\t\\heuristics(simplify)\n\t};";
		return output;
	}
}
