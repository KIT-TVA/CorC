package de.tu_bs.cs.isf.cbc.tool.helper;

public enum Type {
	PRE("Precondition"),
	POST("Postcondition"),
	VAR("Variant"),
	INV("Invariant"),
	GRD("Guard"),
	GLOB("Global"),
	IMP("Important"),
	UN("Unknown"),
	OTHER("Other"),
	
	MAIN("---"),
	SECONDARY("---"),
	TERTIARY("---"),;
	
	private final String str;
	
	Type (String str) {
		this.str = str;
	}
	
	/**
	 * Returns a {@link Type} for a given {@link String}
	 * 
	 * Valid arguments are "var", "inf", "pre", "post", "glob", "imp" and "un".
	 * Other inputs return {@link Type#OTHER}.
	 * 
	 * @param str	the string information 
	 * @return		the related {@link Type}
	 */
	public static Type getType(String str) {
		switch (str.toLowerCase()) {
			case "var":
				return VAR;
			case "inv":
				return INV;
			case "pre":
				return PRE;
			case "post":
				return POST;
			case "glob":
				return GLOB;
			case "imp":
				return IMP;
			case "un":
				return UN;
			case "grd":
			case "guard":
				return GRD;
			default:
				return OTHER;
		}
	}
	
	/**
	 * Checks whether a Type is classified as given information type.
	 * 
	 * @return	true iff type is considered information type
	 */
	public boolean isType(Type type) {
		switch (type) {
			case MAIN:
				return this == INV || this == VAR || this == PRE || this == POST || this == IMP || this == GRD;
			case SECONDARY:
				return this == GLOB;
			case TERTIARY:
				return this == OTHER || this == UN;
			default:
				return false;
		}
	}
	
	@Override
    public String toString() {
        return this.str;
    }
}
