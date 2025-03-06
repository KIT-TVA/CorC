package de.tu_bs.cs.isf.cbc.parser.data.ifbc.entities;

import java.util.List;

import de.tu_bs.cs.isf.cbc.parser.annotations.MDF;
import de.tu_bs.cs.isf.cbc.parser.data.ParameterDefinition;

public class IFbCNewEntity extends IFbCReferenceEntity {

	private List<ParameterDefinition> parameters;

	public IFbCNewEntity(String name, String securityLevel, MDF mutationModifier, String type,
			List<ParameterDefinition> parameters) {
		super(name, securityLevel, mutationModifier, type);
		this.parameters = parameters;
	}

	public List<ParameterDefinition> getParameters() {
		return parameters;
	}

	@Override
	public String toString() {
		return "IFbCNewEntity [parameters=" + parameters + ", getSecurityLevel()=" + getSecurityLevel()
				+ ", getMutationModifier()=" + getMutationModifier() + ", getName()=" + getName() + ", getType()="
				+ getType() + "]";
	}

	@Override
	public EntityType getEntityType() {
		return EntityType.NEW;
	}

}
