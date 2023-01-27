package de.tu_bs.cs.isf.cbc.tool.helper;

import java.util.ArrayList;
import java.util.List;

/**
 * Class handling conditional branching used in specifications.
 * @author Fynn
 */
public class Branch {
	private BranchType type;
	private String condition;
	private List<String> assertions;
	private List<String> iterConditions;
	private List<String> quantorBodyConditions;
	private String iterType;
	private String iterName;
	
	public Branch(BranchType type, String condition, List<String> assertions) {
		this.type = type;
		//condition = condition.replaceAll("\\(", "");
		//condition = condition.replaceAll("\\)", "");
		this.condition = condition;
		this.assertions = new ArrayList<String>();
		for (var assertion : assertions) {
			//assertion = assertion.replaceAll("\\(", "");
			//assertion = assertion.replaceAll("\\)", "");
			this.assertions.add(assertion);
		}
		this.iterConditions = new ArrayList<String>();
		this.quantorBodyConditions = new ArrayList<String>();
	}

	public String getBranchCondition() {
		return condition;
	}

	public BranchType getType() {
		return type;
	}

	public List<String> getAssertions() {
		return assertions;
	}
	
	public String getIterType() {
		return iterType;
	}
	
	public String getIterName() {
		return iterName;
	}

	public List<String> getIterConditions() {
		return this.iterConditions;
	}
	
	public List<String> getQuantorBodyConditions() {
		return this.quantorBodyConditions;
	}
	
	public void setIterConditions(final List<String> conditions) {
		iterConditions.addAll(conditions);
	}
	
	public void setQuantorBodyConditions(final List<String> conditions) {
		quantorBodyConditions.addAll(conditions);
	}
	
	public void setIterType(final String type) {
		this.iterType = type;
	}
	
	public void setIterName(final String name) {
		this.iterName = name;
	}
}
