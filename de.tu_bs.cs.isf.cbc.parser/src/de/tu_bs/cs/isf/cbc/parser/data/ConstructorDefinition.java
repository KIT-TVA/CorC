package de.tu_bs.cs.isf.cbc.parser.data;

import java.util.List;

public class ConstructorDefinition {
	private final List<ParameterDefinition> parameters;

	public ConstructorDefinition(List<ParameterDefinition> parameters) {
		super();
		this.parameters = parameters;
	}

	public List<ParameterDefinition> getParameters() {
		return parameters;
	}

	@Override
	public String toString() {
		return "ConstructorDefinition [parameters=" + parameters + "]";
	}
}
