package de.tu_bs.cs.isf.cbc.parser.data.ifbc.entities;

import java.util.List;

import de.tu_bs.cs.isf.cbc.parser.annotations.MDF;

public class IFbCFieldAccessEntity extends IFbCFieldEntity {
	
	public IFbCFieldAccessEntity(String name, String securityLevel, MDF mutationModifier, String type,
			List<IFbCReferenceEntity> scopes) {
		super(name, securityLevel, mutationModifier, type, scopes);
	}

	@Override
	public EntityType getEntityType() {
		return EntityType.FIELD_ACCESS;
	}

	@Override
	public String toString() {
		return "IFbCFieldAccessEntity [scopes=" + scopes + ", getSecurityLevel()=" + getSecurityLevel()
				+ ", getMutationModifier()=" + getMutationModifier() + ", getName()=" + getName() + ", getType()="
				+ getType() + "]";
	}
}
