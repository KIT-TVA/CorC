package de.tu_bs.cs.isf.cbc.parser.data.ifbc.entities;

import de.tu_bs.cs.isf.cbc.parser.annotations.MDF;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.entities.IFbCEntity.EntityType;

public class IFbCDeclassifyEntity extends IFbCReferenceEntity {
	private String parameterName;
	private String parameterSL;
	private MDF parameterMDF;

	public IFbCDeclassifyEntity(String type, String parameterName, String parameterSL, MDF parameterMDF) {
		super("declassify", "declassify", MDF.IMMUTABLE, type);
		this.parameterName = parameterName;
		this.parameterSL = parameterSL;
		this.parameterMDF = parameterMDF;
	}

	public String getParameterName() {
		return parameterName;
	}

	public String getParameterSL() {
		return parameterSL;
	}

	public MDF getParameterMDF() {
		return parameterMDF;
	}

	@Override
	public EntityType getEntityType() {
		return EntityType.DECLASSIFY;
	}

	@Override
	public String toString() {
		return "IFbCDeclassifyEntity [parameterName=" + parameterName + ", parameterSL=" + parameterSL
				+ ", parameterMDF=" + parameterMDF + "]";
	}
}
