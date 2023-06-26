package de.tu_bs.cs.isf.cbc.parser.data.ifbc.entities;

import de.tu_bs.cs.isf.cbc.parser.annotations.MDF;

public class IFbCBinaryExpression extends IFbCReferenceEntity {

	public IFbCBinaryExpression(String name, String securityLevel, MDF mutationModifier, String type) {
		super(name, securityLevel, mutationModifier, type);
	}

	@Override
	public EntityType getEntityType() {
		return EntityType.BINARY_EXPRESSION;
	}
}
