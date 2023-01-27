package de.tu_bs.cs.isf.cbc.tool.helper;

/**
 * Contains information about a single test case.
 * @author Fynn
 */
public class TestCaseData {
	private InputDataTupel data;
	private String name;
	private String nameOfTestee;
	private String testeeReturnType;
	private int numberOfTest;
	private String returnVar;

	public TestCaseData(String nameOfTestee, int numberOfTest, String testeeReturnType, String testeeReturnVar) {
		this.name = nameOfTestee + "Test" + numberOfTest;
		this.nameOfTestee = nameOfTestee;
		this.numberOfTest = numberOfTest;
		this.testeeReturnType = testeeReturnType;
	}
	
	public int getTestNumber() {
		return this.numberOfTest;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getTesteeName() {
		return this.nameOfTestee;
	}
	
	public void setInputDataTupel(InputDataTupel data) {
		this.data = data;
	}
	
	public InputDataTupel getInputDataTupel() {
		return this.data;
	}
	
	public String getReturnType() {
		return this.testeeReturnType;
	}
	
	public String getReturnVar() {
		/** this will be either the return variable from the 
		 * java code or the variable used in a return refinement
		 */
		return this.returnVar;
	}
}
