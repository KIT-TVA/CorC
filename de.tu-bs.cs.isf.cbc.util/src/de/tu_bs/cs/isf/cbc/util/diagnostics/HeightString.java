package de.tu_bs.cs.isf.cbc.util.diagnostics;

public class HeightString {
	private String nodeRow;
	private String w1;
	private String w2;
	
	public HeightString(final String nodeRow) {
		this.nodeRow = nodeRow;
		this.w1 = getSpaces(nodeRow.length());
		this.w2 = getSpaces(nodeRow.length());
	}
	
	public String getNodeRow() {
		return this.nodeRow;
	}
	
	public void setW1(final String w1) {
		this.w1 = w1;
	}
	
	public void setW2(final String w2) {
		this.w2 = w2;
	}
	
	public String getW1() {
		return this.w1;
	}
	
	public String getW2() {
		return this.w2;
	}
	
	private String getSpaces(int amount) {
		String spaces = "";
		for (int i = 0; i < amount; i++) {
			spaces += " ";
		}
		return spaces;
	}
			
}
