package de.tu_bs.cs.isf.cbc.parser.data.ifbc.entities;

import java.util.List;

import de.tu_bs.cs.isf.cbc.parser.annotations.MDF;

public class IFbCFieldAssignEntity extends IFbCFieldEntity {

	public IFbCFieldAssignEntity(String name, String securityLevel, MDF mutationModifier, String type,
			List<IFbCReferenceEntity> scopes) {
		super(name, securityLevel, mutationModifier, type, scopes);
	}

	@Override
	public EntityType getEntityType() {
		return EntityType.FIELD_ASSIGN;
	}

	public String getQualifiedName() {
		final StringBuilder sb = new StringBuilder();

		scopes.forEach(s -> {
			sb.append(s.getName() + ".");
		});

		return sb.append(this.getName()).toString();
	}

	@Override
	public String toString() {
		return "IFbCFieldAssignEntity [scopes=" + scopes + ", getSecurityLevel()=" + getSecurityLevel()
				+ ", getMutationModifier()=" + getMutationModifier() + ", getName()=" + getName() + ", getType()="
				+ getType() + "]";
	}
}
