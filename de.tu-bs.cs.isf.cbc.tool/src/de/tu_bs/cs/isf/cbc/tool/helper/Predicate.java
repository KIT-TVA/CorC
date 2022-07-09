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
	
	public void resolveVars() {
		// find varsTerms
		String[] signatureSplit = this.def.trim().split(",");
		String[] findSplit = this.find.trim().substring(find.trim().indexOf("(") + 1, find.trim().length() - 1).split(",");
		if (signatureSplit.length != findSplit.length) {
			System.out.println("Error: Number of type declarations in signature does not match number of variables in string to be found!");
			return;
		}
		for (int i = 0; i < signatureSplit.length; i++) {
			varsTerms.add(signatureSplit[i].trim() + " " + findSplit[i].trim());
		}
		
		// find varsFree
		String rep = this.replace;
		while (rep.contains("\\forall ")) {
			int index = rep.indexOf("\\forall ") + 8;
			String nameOfFree = "";
			while (rep.charAt(index) != ';') {
				nameOfFree += rep.charAt(index++);
			}
			if (!varsFree.contains(nameOfFree))  varsFree.add("int " + nameOfFree.trim()); //TODO ist nicht zwingend int
			rep = rep.replace("\\forall ", "done");
		}
		
		while (rep.contains("\\exists ")) {
			int index = rep.indexOf("\\exists ") + 8;
			String nameOfFree = "";
			while (rep.charAt(index) != ';') {
				nameOfFree += rep.charAt(index++);
			}
			if (!varsFree.contains(nameOfFree))  varsFree.add("int " + nameOfFree.trim()); //TODO ist nicht zwingend int
			rep = rep.replace("\\exists ", "done");
		}
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
		output += "\t\t\\find (" + find + ")\n";
		if (!varsFree.isEmpty() && !varsTerms.isEmpty()) {
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
	
	public String getDefinedInFeature() {
		return definedInFeature.equals("default") ? "All features" : definedInFeature;
	}
	
	public String getDefinedInClass() {
		return definedInClass.equals("default") ? "All classes" : definedInClass;
	}
	
	public String getDefinedInMethod() {
		return definedInMethod.equals("default") ? "All methods" : definedInMethod;
	}
}
