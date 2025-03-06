package de.tu_bs.cs.isf.cbc.util;

import java.util.ArrayList;

import de.tu_bs.cs.isf.cbc.exceptions.SettingsException;

public class SolverOutputCleaner {
	private String counterexample;
	private ArrayList<Variable> variables;

	public SolverOutputCleaner() {
		this.variables = new ArrayList<Variable>();
	}

	public void clean(String counterexample) throws SettingsException {
		this.counterexample = counterexample;
		var exampleParts = counterexample.split(";;");
		var values = exampleParts[exampleParts.length - 1];
		values = values.substring(values.indexOf("\n") + 1, values.length());
		var lines = values.split("\n");
		var relevantLines = extractRelevantLines(lines);
		createVariables(relevantLines);
	}

	public String raw() {
		return this.counterexample;
	}

	public String cleaned() {
		String out = "";
		for (var f : this.variables) {
			if (!isBoilerplateFunction(f)) {
				out += "\t[" + f.getRep() + " = " + f.getValue() + "]\n";
			}
		}
		return out;
	}

	private ArrayList<String> extractRelevantLines(String[] lines) {
		var relevantLines = new ArrayList<String>();
		for (int i = 0; i < lines.length - 1; i++) {
			if (isConcreteFunctionDefinition(lines[i])) {
				i = addFunctionDefinition(relevantLines, lines, i);
			}
		}
		return relevantLines;
	}

	private boolean isConcreteFunctionDefinition(String line) {
		return !line.endsWith(" u") && line.contains("define-fun");
	}

	private int addFunctionDefinition(ArrayList<String> relevantLines, String[] lines, int i) {
		relevantLines.add(lines[i]);
		while (CodeHandler.countBrackets(lines[i++], '(') != -1) {
			relevantLines.add(" " + lines[i]);
		}
		return i - 1;
	}

	private void createVariables(ArrayList<String> relevantLines) throws SettingsException {
		for (int i = 0; i < relevantLines.size(); i++) {
			relevantLines.set(i, relevantLines.get(i).trim());
			if (!isConcreteFunctionDefinition(relevantLines.get(i))) {
				continue;
			}
			var variable = createVariable(relevantLines, i);
			this.variables.add(variable);
		}
	}

	private boolean isBoilerplateFunction(Variable f) {
		return f.getName().startsWith("type_of_") || f.getName().contains("wellFormed_");
	}

	private Variable createVariable(ArrayList<String> relevantLines, int i) throws SettingsException {
		var parts = relevantLines.get(i).split("\\s");
		var functionName = parts[1];
		var functionType = parts[parts.length - 1];
		var functionValue = getEntireFunctionValue(relevantLines, i);
		var variable = new Variable(functionType, functionName);
		variable.setValue(functionValue);
		return variable;
	}

	private String getEntireFunctionValue(ArrayList<String> relevantLines, int i) {
		var functionValue = "";
		for (int j = i + 1; j < relevantLines.size(); j++) {
			if (isConcreteFunctionDefinition(relevantLines.get(j))) {
				break;
			}
			functionValue += relevantLines.get(j);
		}
		return functionValue.substring(0, functionValue.length() - 1);
	}
}
