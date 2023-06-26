package de.tu_bs.cs.isf.cbc.parser.annotations;

import java.util.HashMap;
import java.util.Map;

public enum MDF {
	MUTABLE("MDF.MUTABLE"),
	IMMUTABLE("MDF.IMMUTABLE"),
	CAPSULE("MDF.CAPSULE"),
	READ("MDF.READ");
	
	private final String label;
	
	private MDF(final String label) {
		this.label = label;
	}
	private static final Map<String, MDF> BY_STRING = new HashMap<>();
	
	static {
		for (MDF mdf : values()) {
			BY_STRING.put(mdf.label, mdf);
		}
	}
	
	public static MDF forAnnotationExpress(final String expr) {
		return BY_STRING.get(expr);
	}
	
	public static boolean isSubtypeOf(MDF mdf1, MDF mdf2) {
		if (mdf1.equals(MDF.CAPSULE) ) return true;
		if (mdf2.equals(MDF.READ) ) return true;
		return (mdf1.equals(mdf2));
		
	}
}
