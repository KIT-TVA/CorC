package de.tu_bs.cs.isf.cbc.parser.data.ifbc.entities;

import de.tu_bs.cs.isf.cbc.parser.annotations.MDF;

public interface IFbCEntity {
	
	public EntityType getEntityType();

	public String getSecurityLevel();

	public MDF getMutationModifier();
	
	public enum EntityType {
		ENTITY,
		FIELD_ACCESS,
		FIELD_ASSIGN,
		METHOD,
		NEW,
		DECLASSIFY,
		BINARY_EXPRESSION;
	}
}
