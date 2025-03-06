package de.tu_bs.cs.isf.cbc.parser.data;

import java.util.List;

public class ClassSubtypes {

	private List<String> implementedTypes;
	private List<String> extendedTypes;

	public ClassSubtypes(List<String> implementedTypes, List<String> extendedTypes) {
		super();
		this.implementedTypes = implementedTypes;
		this.extendedTypes = extendedTypes;
	}

	public List<String> getImplementedTypes() {
		return implementedTypes;
	}

	public List<String> getExtendedTypes() {
		return extendedTypes;
	}

	@Override
	public String toString() {
		return "ClassSubtypes [implementedTypes=" + implementedTypes + ", extendedTypes=" + extendedTypes + "]";
	}

}
