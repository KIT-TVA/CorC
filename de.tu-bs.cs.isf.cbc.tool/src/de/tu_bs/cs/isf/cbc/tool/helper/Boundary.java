package de.tu_bs.cs.isf.cbc.tool.helper;

/**
 * Class for representing boundaries of relations.
 * @author Fynn
 */
public class Boundary {
	private String value;
	private BoundaryType type;
	
	public Boundary(BoundaryType type, String value) {
		this.value = value;
		this.type = type;
	}
	
	public void addName(String value) {
		this.value = value;
	}
	
	public void addType(BoundaryType type) {
		this.type = type;
	}
	
	public BoundaryType getType() {
		return type;
	}
	
	public String getValue() {
		return value;
	}
}
