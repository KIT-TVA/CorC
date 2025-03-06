package de.tu_bs.cs.isf.cbc.util;

import java.util.ArrayList;
import java.util.List;

/**
 * An object of this class contains all input data needed to execute a method
 * once.
 * 
 * @author Fynn
 */
public class InputDataTupel {
	private List<InputData> inputParameters;
	private List<InputData> inputGlobalVars;

	public InputDataTupel() {
		inputParameters = new ArrayList<InputData>();
		inputGlobalVars = new ArrayList<InputData>();
	}

	public void addParameter(InputData toAdd) {
		inputParameters.add(toAdd);
	}

	public void addGlobalVar(InputData toAdd) {
		inputGlobalVars.add(toAdd);
	}

	public List<String> getParametersValues() {
		return this.inputParameters.stream().map(p -> p.getDimensions() == 0 ? p.getRep() : p.getArrayValueRep())
				.toList();
	}

	public List<String> getGlobalVarsValues() {
		return this.inputGlobalVars.stream().map(v -> v.getDimensions() == 0 ? v.getRep() : v.getArrayValueRep())
				.toList();
	}

	public String getParametersNameRep() {
		return getNameRep(true);
	}

	public String getParameterRep() {
		return getRep(true);
	}

	public String getGlobalVarsRep() {
		return getRep(false);
	}

	public String getGlobalVarsNameRep() {
		return getNameRep(false);
	}

	public List<InputData> getParameters() {
		return this.inputParameters;
	}

	public List<String> getParameterNames() {
		return this.inputParameters.stream().map(p -> p.getName()).toList();
	}

	public List<String> getGlobalVarNames() {
		return this.inputGlobalVars.stream().map(v -> v.getName()).toList();
	}

	public List<InputData> getGlobalVars() {
		return this.inputGlobalVars;
	}

	public List<InputData> getAllVars() {
		final var output = new ArrayList<InputData>();
		output.addAll(this.inputParameters);
		output.addAll(this.inputGlobalVars);
		return output;
	}

	private String getNameRep(boolean isParameter) {
		final List<InputData> lst;
		if (isParameter) {
			lst = this.inputParameters;
		} else {
			lst = this.inputGlobalVars;
		}
		String rep = "(";
		for (int i = 0; i < lst.size() - 1; i++) {
			// get latest representation of the input data
			rep += lst.get(i).getName() + ", ";
		}
		if (lst.size() > 0) {
			rep += lst.get(lst.size() - 1).getName() + ")";
		} else {
			rep += ")";
		}
		return rep;
	}

	private String getRep(boolean isParameter) {
		final List<InputData> lst;
		if (isParameter) {
			lst = this.inputParameters;
		} else {
			lst = this.inputGlobalVars;
		}
		String rep = "(";
		for (int i = 0; i < lst.size() - 1; i++) {
			// get latest representation of the input data
			rep += lst.get(i).getRep() + ", ";
		}
		if (lst.size() > 0) {
			rep += lst.get(lst.size() - 1).getRep();
		}
		rep += ")";
		return rep;
	}

	public List<String> getAllVarNames() {
		var lst = new ArrayList<String>();
		lst.addAll(getParameterNames());
		lst.addAll(getGlobalVarNames());
		return lst;
	}
}
