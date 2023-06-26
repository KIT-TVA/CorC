package de.tu_bs.cs.isf.cbc.parser.data;

import de.tu_bs.cs.isf.cbc.parser.annotations.MDF;

public class Field implements IFieldOrMethod {
	
	private String name;
	private String securityLevel;
	private MDF mutationModifier;
	private String type;
	private Boolean staticReference;
	
	public Field(String name, String securityLevel, MDF mutationModifier, String type, Boolean staticReference) {
		super();
		this.name = name;
		this.securityLevel = securityLevel;
		this.mutationModifier = mutationModifier;
		this.type = type;
		this.staticReference = staticReference;
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
	
	public String getFieldType() {
		return type;
	}

	public Boolean isStaticReference() {
		return staticReference;
	}

	@Override
	public String toString() {
		return "Field [name=" + name + ", securityLevel=" + securityLevel + ", mutationModifier=" + mutationModifier
				+ ", type=" + type + ", staticReference=" + staticReference + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mutationModifier == null) ? 0 : mutationModifier.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((securityLevel == null) ? 0 : securityLevel.hashCode());
		result = prime * result + ((staticReference == null) ? 0 : staticReference.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Field other = (Field) obj;
		if (mutationModifier != other.mutationModifier)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (securityLevel == null) {
			if (other.securityLevel != null)
				return false;
		} else if (!securityLevel.equals(other.securityLevel))
			return false;
		if (staticReference == null) {
			if (other.staticReference != null)
				return false;
		} else if (!staticReference.equals(other.staticReference))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public Type getType() {
		return Type.FIELD;
	}
}
