package de.tu_bs.cs.isf.cbc.parser.data.ifbc.entities;

import de.tu_bs.cs.isf.cbc.parser.annotations.MDF;

public class IFbCReferenceEntity implements IFbCEntity {

	private String name;
	private String securityLevel;
	private MDF mutationModifier;
	private String type;
	private Boolean literal = false;

	public IFbCReferenceEntity(String name, String securityLevel, MDF mutationModifier, String type) {
		super();
		this.name = name;
		this.securityLevel = securityLevel;
		this.mutationModifier = mutationModifier;
		this.type = type;
	}

	public IFbCReferenceEntity(String name, String securityLevel, MDF mutationModifier, String type, Boolean literal) {
		super();
		this.name = name;
		this.securityLevel = securityLevel;
		this.mutationModifier = mutationModifier;
		this.type = type;
		this.literal = literal;
	}

	public String getSecurityLevel() {
		return securityLevel;
	}

	public void setSecurityLevel(String securityLevel) {
		this.securityLevel = securityLevel;
	}

	public MDF getMutationModifier() {
		return mutationModifier;
	}

	public void setMutationModifier(MDF mutationModifier) {
		this.mutationModifier = mutationModifier;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public Boolean getLiteral() {
		return literal;
	}

	@Override
	public String toString() {
		return "IFbCReferenceEntity [name=" + name + ", securityLevel=" + securityLevel + ", mutationModifier="
				+ mutationModifier + ", type=" + type + ", literal=" + literal + "]";
	}

	@Override
	public EntityType getEntityType() {
		return EntityType.ENTITY;
	}

}
