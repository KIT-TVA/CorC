package de.tu_bs.cs.isf.cbc.tool.helper;

/**
 * Defines what an expression is.
 * @author Fynn
 */
public class Expression {
	private String variable;
	private RelationType rel;
	private Boundary boundary;
	
	public Expression() {
		variable = null;
		rel = null;
		boundary = null;
	}
	
	public void addVar(String variable) {
		this.variable = variable;
	}
	
	public void addRel(RelationType rel) {
		this.rel = rel;
	}
	
	public void addBoundary(Boundary boundary) {
		this.boundary = boundary;
	}
	
	public String getVar() {
		return variable;
	}
	
	public RelationType getRel() {
		return rel;
	}
	
	public Boundary getBoundary() {
		return boundary;
	}
	
	public boolean hasNullValue() {
		return variable == null | rel == null | boundary == null;
	}
}
