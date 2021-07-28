package de.tu_bs.cs.isf.cbc.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.Rename;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.cbcmodel.VariableKind;

public class KeYFileContent {
	
	public static final String REGEX_BEFORE_VARIABLE_KEYWORD = "(?<![a-zA-Z0-9_]|self\\.)(";
	public static final String REGEX_AFTER_VARIABLE_KEYWORD = ")(?![a-zA-Z0-9_])";
	public static final Pattern REGEX_THIS_KEYWORD = Pattern.compile("(?<![a-zA-Z0-9_])(this)(?![a-zA-Z0-9_])");
	
	private String location = "";
	private String helper = "helper.key";
	private String programVariables = "";
	private String globalConditions = "";
	private String conditionArraysCreated = "";
	private String self = "";
	private String selfConditions = "";
	private String assignment = "";
	private String pre = "";
	private String post = "";
	String statement = "";
	
	
	public KeYFileContent() {
		
	}
	
	public void setLocation(String location) {
		this.location = location;
	}

	public void setHelper(String helper) {
		this.helper = helper;
	}

	public void setProgramVariables(String programVariables) {
		this.programVariables = programVariables;
	}

	public void setGlobalConditions(String globalConditions) {
		this.globalConditions = globalConditions;
	}

	public void setConditionArraysCreated(String conditionArraysCreated) {
		this.conditionArraysCreated = conditionArraysCreated;
	}

	public void setSelf(String self) {
		this.self = self;
	}

	public void setSelfConditions(String selfConditions) {
		this.selfConditions = selfConditions;
	}

	public void setAssignment(String assignment) {
		this.assignment = assignment;
	}

	public void setPre(String pre) {
		this.pre = pre;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}
	
	
	public JavaVariable readVariables(JavaVariables vars) {
		JavaVariable returnVariable = null;
		if (vars != null) {
			for (JavaVariable var : vars.getVariables()) {
				if (var.getKind() == VariableKind.RETURN) {
					returnVariable = var;
				} else {
					programVariables += var.getName() + "; ";
					// if variable is an Array add <created> condition for key
					if (var.getName().contains("[]")) {
						String varName = var.getName().substring(var.getName().indexOf(" ") + 1);
						conditionArraysCreated += " & " + varName + ".<created>=TRUE";
					}
				}
			}
		}
		return returnVariable;
	}
	
	public void addVariable(String var) {
		programVariables += var + "; ";				
	}
	
	public void readGlobalConditions(GlobalConditions conds) {
		if (conds != null) {
			for (Condition cond : conds.getConditions()) {
				if (!cond.getName().isEmpty()) {
					globalConditions += " & " + cond.getName();
				}
			}
		}
	}
	
	public void rename(Renaming renaming) {
		if (renaming != null) {
			globalConditions = useRenamingCondition(renaming, globalConditions);
			pre = useRenamingCondition(renaming, pre);
			post = useRenamingCondition(renaming, post);
			statement = useRenamingStatement(renaming, statement);
		}
	}
	
	private String useRenamingCondition(Renaming renaming, String toRename) {
		for (Rename rename : renaming.getRename()) {
			if (rename.getType().equalsIgnoreCase("boolean")) {
				toRename = toRename.replaceAll(rename.getNewName(), "TRUE=" + rename.getFunction());
			} else {
				toRename = toRename.replaceAll(rename.getNewName(), rename.getFunction());
			}
		}
		return toRename;
	}

	private String useRenamingStatement(Renaming renaming, String toRename) {
		for (Rename rename : renaming.getRename()) {
			toRename = toRename.replaceAll(rename.getNewName(), rename.getFunction());
		}
		return toRename;
	}
	
	public void replaceThisWithSelf() {

		statement = statement.replaceAll(REGEX_THIS_KEYWORD.pattern(), "self");
		pre = pre.replaceAll(REGEX_THIS_KEYWORD.pattern(), "self");
		post = post.replaceAll(REGEX_THIS_KEYWORD.pattern(), "self");
		globalConditions = globalConditions.replaceAll(REGEX_THIS_KEYWORD.pattern(), "self");
	}
	
	public void addSelfForFields(JavaVariables vars) {
		List<String> nameOfLocalVars = new ArrayList<>();
		for (JavaVariable var : vars.getVariables()) {
			String[] NameOfVar = var.getName().split(" ");
			nameOfLocalVars.add(NameOfVar[NameOfVar.length-1]);
		}
		for (Field field : vars.getFields()) {
			if (!nameOfLocalVars.contains(field.getName())) {
				Pattern pattern = Pattern.compile(REGEX_BEFORE_VARIABLE_KEYWORD + field.getName() + REGEX_AFTER_VARIABLE_KEYWORD);
				statement = statement.replaceAll(pattern.pattern(), "self." + field.getName());
				pre = pre.replaceAll(pattern.pattern(), "self." + field.getName());
				post = post.replaceAll(pattern.pattern(), "self." + field.getName());
				globalConditions = globalConditions.replaceAll(pattern.pattern(), "self." + field.getName());
			}
		}
	}
	
	public void addSelf(CbCFormula formula) {
		if(formula != null && formula.getClassName() != null && !formula.getClassName().isBlank()) {
			self = formula.getClassName() + " self;";
			selfConditions = " & self.<created>=TRUE & " + formula.getClassName() + "::exactInstance(self)=TRUE &  !self = null & self.<inv> ";
		}
	}
	
	public void addUnmodifiableVars(List<String> unmodifiedVariables) {
		for (String var : unmodifiedVariables) {
			String varName = var.substring(var.indexOf(" ") + 1);
			programVariables += var + "_old; ";
			assignment += "||" + varName + "_old:=" + varName;
			post += "&" + varName + "=" + varName + "_old";
			// if variable is an Array add <created> condition for key
			if (var.contains("[]")) {
				conditionArraysCreated += " & " + varName + "_old.<created>=TRUE";
			}
		}
	}
	
	public void setPostFromCondition(String cond) {
		String condition = Parser.getConditionFromCondition(cond);
		if (condition == null || condition.length() == 0) {
			post = "true";
		} else {
			post = condition;
		}
	}
	
	public void setPreFromCondition(String cond) {
		String condition = Parser.getConditionFromCondition(cond);
		if (condition == null || condition.length() == 0) {
			pre = "true";
		} else {
			pre = condition;
		}
	}

	public String getKeYStatementContent() {
		return 	getKeYContent(true);
	}
	
	public String getKeYCImpliesCContent() {
		return  getKeYContent(false);
	}
	
	public String getKeYContent(boolean withStatement) {
		String string  = 	keyHeader() + "\\problem {(" + pre + " "
				+ globalConditions + conditionArraysCreated + selfConditions
						+ "& wellFormed(heap)) -> {heapAtPre := heap"
				+ assignment + "}";
		if (withStatement) 
			string += " \\<{" + statement + "}\\>"; 
		return string + " (" + post + ")}";
	}
	
	//->{variant := " + variantString
		//	+ " || heapAtPre := heap}  ((" + variantString + ") <variant & " + variantString
		//	+ ">=0)}";
	
	public String keyHeader() {
		return "\\javaSource \"" + location + "\";" + "\\include \"" + helper + "\";"
				+ "\\programVariables {" + programVariables + self +" Heap heapAtPre;}";
	}
	
	public String getKeYWPContent() {
		return  keyHeader() + "\\problem {\\<{" + statement + "}\\> (" + post + ")}";
	}

	public void setVariantPost(String variant) {
		assignment += "|| variant := " + variant;
		post = "(" + variant + ") <variant & " + variant + ">=0";
	}
}
