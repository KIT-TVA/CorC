package de.tu_bs.cs.isf.cbc.parser.data;

import de.tu_bs.cs.isf.cbc.parser.annotations.MDF;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.entities.IFbCReferenceEntity;

public class ParameterDefinition {

	private String name;
	private String securityLevel;
	private MDF mutationModifier;
	private String type;
	private IFbCReferenceEntity optionalEntity = null;

	public ParameterDefinition(String name, String securityLevel, MDF mutationModifier, String type) {
		super();
		this.name = name;
		this.securityLevel = securityLevel;
		this.mutationModifier = mutationModifier;
		this.type = type;
	}

	public ParameterDefinition(String name, String securityLevel, MDF mutationModifier, String type, IFbCReferenceEntity optionalEntity) {
		super();
		this.name = name;
		this.securityLevel = securityLevel;
		this.mutationModifier = mutationModifier;
		this.type = type;
		this.optionalEntity = optionalEntity;
	}

	public String getName() {
		return name;
	}

	public String getSecurityLevel() {
		return securityLevel;
	}

	public MDF getMutationModifier() {
		return mutationModifier;
	}

	public String getType() {
		return type;
	}

	public IFbCReferenceEntity getOptionalEntity() {
		return optionalEntity;
	}

	@Override
	public String toString() {
		return "ParameterDefinition [name=" + name + ", securityLevel=" + securityLevel + ", mutationModifier="
				+ mutationModifier + ", type=" + type + ", optionalEntity=" + optionalEntity + "]";
	}

}
