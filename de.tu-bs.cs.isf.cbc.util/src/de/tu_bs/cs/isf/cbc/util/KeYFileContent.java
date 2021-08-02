package de.tu_bs.cs.isf.cbc.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field;
import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.Rename;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.cbcmodel.VariableKind;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.ReturnStatementImpl;

public class KeYFileContent {
	
	public static final String REGEX_BEFORE_VARIABLE_KEYWORD = "(?<![a-zA-Z0-9_]|self\\.)(";
	public static final String REGEX_AFTER_VARIABLE_KEYWORD = ")(?![a-zA-Z0-9_])";
	public static final Pattern REGEX_THIS_KEYWORD = Pattern.compile("(?<![a-zA-Z0-9_])(this)(?![a-zA-Z0-9_])");
	public static final String OLD_VARS_SUFFIX = "_oldVal";
	public static final Pattern REGEX_RESULT_KEYWORD = Pattern.compile("(\\\\result)");
	
	private String location = "";
	private String helper = "helper.key";
	private String programVariables = "";
	private String globalConditions = "";
	private String conditionObjectsCreated = "";
	private String self = "";
	private String selfConditions = "";
	private String assignment = "";
	private String pre = "";
	private String post = "";
	private Map<String, String> replacements = new HashMap<String, String>();
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

	public void setConditionObjectsCreated(String conditionArraysCreated) {
		this.conditionObjectsCreated = conditionArraysCreated;
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
					programVariables += var.getName().replace("static", "").replace("non-null", "") + "; ";
					// if variable is an Array add <created> condition for key
					if (var.getName().contains("[]")) {
						String varNameWithoutStaticNonNull = var.getName().replace("static", "").replace("non-null", "").trim();
						varNameWithoutStaticNonNull = varNameWithoutStaticNonNull.substring(varNameWithoutStaticNonNull.indexOf(" ") + 1);
						conditionObjectsCreated += " & " + varNameWithoutStaticNonNull + ".<created>=TRUE";
					}
					if(var.getKind() == VariableKind.PARAM && var.getName().contains("non-null")){
						String varNameWithoutStaticNonNull = var.getName().replace("static", "").replace("non-null", "").trim();
						String varDataType = varNameWithoutStaticNonNull.substring(0, varNameWithoutStaticNonNull.indexOf(" "));
						String varName = varNameWithoutStaticNonNull.substring(varNameWithoutStaticNonNull.lastIndexOf(" ")).trim();
						addNonNullProperties(varDataType, varName, false);
					}
				}
			}
			for (Field field : vars.getFields()) { //TODO for which fields add non null?
				addNonNullProperties(field.getType(), field.getName(), true);
			}
			
		}
		return returnVariable;
	}

	private void addNonNullProperties(String varDataType, String varName, boolean field) {

		if (field) varName = "self." + varName;
		// Additionally add instance checks for nullable data types.
		if (!varDataType.matches("(void|byte|short|int|double|char|long|float|double)([\\[\\]]*)")) {
			conditionObjectsCreated += " & " + varDataType + "::exactInstance(" + varName + ") = TRUE";
			if (!varDataType.matches("(void|byte|short|int|double|char|long|float|boolean)([\\[\\]]*)"))
				conditionObjectsCreated += " & " + varName + ".<created> = TRUE";
			conditionObjectsCreated += " & " + varName + " != null";
		}
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
			String className = "";
			if (formula.getMethodObj() != null && formula.getMethodObj().getParentClass().getPackage() != null &&
					!formula.getMethodObj().getParentClass().getPackage().isBlank()) {
				className += formula.getMethodObj().getParentClass().getPackage() + ".";
			}
			className += formula.getClassName();
			self = className + " self;";
			selfConditions = " & self.<created>=TRUE & " + formula.getClassName() + "::exactInstance(self)=TRUE &  !self = null & self.<inv> ";
		}
	}
	
	public void addUnmodifiableVars(List<String> unmodifiedVariables) {
		for (String var : unmodifiedVariables) {
			String varName = var.substring(var.lastIndexOf(" ") + 1);
			varName = varName.replace("static", "").replace(" non-null", "");
			programVariables += var.replace("static", "").replace("non-null", "") + "_old; ";
			assignment += "||" + varName + "_old:=" + varName;
			post += "&" + varName + "=" + varName + "_old";
			// if variable is an Array add <created> condition for key
			if (var.contains("[]")) {
				conditionObjectsCreated += " & " + varName + "_old.<created>=TRUE";
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
				+ globalConditions + conditionObjectsCreated + selfConditions
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
	
	public void extractOldKeywordVariables() {
		String condition = pre + post + globalConditions;
		// Clear the list of replacements
		replacements.clear();
		ArrayList<Integer> beginIndizes = new ArrayList<>();
		ArrayList<Integer> endIndizes = new ArrayList<>();
		int openParenthesisCounter = 0;
		/*
		 * Iterate over current modified array access.
		 * Count the start and end indizes of first-level parenthesis.
		 */
		String currentOldMatch = condition;
		if (currentOldMatch.contains("\\old(")) {
			for(int i=currentOldMatch.indexOf("\\old");i<currentOldMatch.length();i++) {
//					Console.println("Checking substring: '" + currentOldMatch.substring(i) + "'");
				if (currentOldMatch.charAt(i) == '(') {
					if (openParenthesisCounter == 0) {
						beginIndizes.add(i+1);
					}
					openParenthesisCounter++;
				}
				if (currentOldMatch.charAt(i) == ')') {
					openParenthesisCounter--;
					if (openParenthesisCounter == 0) {
						endIndizes.add(i);
						if (currentOldMatch.substring(i+1).contains("\\old")) {
							int newIndex = currentOldMatch.indexOf("\\old", i);
							if (newIndex > 0)
								i = newIndex;
						} else {
//								break;
						}
					}
				}
			}
			for (int i1=0;i1<beginIndizes.size();i1++) {
				String content = currentOldMatch.substring(beginIndizes.get(i1),endIndizes.get(i1));
				if (!content.isEmpty()) {
					replacements.put(content, "\\old(" + content + ")");
				}				
			}
		}
	}
	
	/*
	 * TODO Masterarbeit Hayreddin
	 * Handle old keyword here.
	 */
	public void addOldVariables(CbCFormula formula, JavaVariables vars, Map<String, Set<JavaVariable>> methodClassVarMap, Map<String, Set<String>> sigReturnMap) {
		Map<String, String> newReplacements = new HashMap<>();
		// Add new old variables to variable List
		for (String varUsedInOldContext : replacements.keySet()) {
			// Get variable name with variable kind
			String var = "";
			String lastVarUsedInOldContext = varUsedInOldContext.substring(varUsedInOldContext.lastIndexOf(".") + 1);
			// Replace brackets
			lastVarUsedInOldContext = lastVarUsedInOldContext.replaceAll("\\[.*\\]", "");
			// This split only works with no method calls that have "." as parameter arguments.
			String[] splittedReplacementVar = varUsedInOldContext.split("\\.");
			boolean isFunction = lastVarUsedInOldContext.contains("(");
			if (isFunction) {
				// Check all diagram method signatures for return value of said object.
				// Get return type of function Call
				// If no function can be found give error message so developer
				// can modify the condition.
				String functionName = splittedReplacementVar[splittedReplacementVar.length -1];
				functionName = functionName.substring(0, functionName.indexOf("("));
				boolean found = false;
				String signatureOfFunction = "";
				for (String sig : sigReturnMap.keySet()) {
					if (sig.equals(functionName)) {
						found = true;
						signatureOfFunction = sig;
						break;
					}
				}
				if (!found && functionName.equals("size")) {
					Console.println("Did not find function " + varUsedInOldContext + " in Diagrams. Matching size and assuming function returns int.");
					var = "int " + functionName;
					lastVarUsedInOldContext = lastVarUsedInOldContext.substring(0, lastVarUsedInOldContext.indexOf("("));
				}
				// If found, get return type of method
				if (found) {
					Set<String> returnTypesOfFunction = sigReturnMap.get(signatureOfFunction);
					if (returnTypesOfFunction.size() > 1) {
						// HashSet can only contain each return type once.
						// Using same method name in multiple diagrams with different return types not supported.
						String allReturnTypes = "";
						for (String rt : returnTypesOfFunction)
							allReturnTypes += rt;
						Console.println("Function " + functionName + " was declared in multiple diagrams with different return value. Return values: " + allReturnTypes
								+ "\nThis is not yet supported. Please use a unique name as method for identification.");
					}
					else {
						var = returnTypesOfFunction.iterator().next() + " " + functionName;
					}
				} else {
					Console.println("Did not find function " + functionName + " in Diagrams.");
				}
			} else {
				/*
				 * If variable is in current method or class, check vars in diagram.
				 * If variable is not in current diagram, check all diagrams.
				 * Iterate over each nested variable and get Datatype.
				 */
				String currentClassName = splittedReplacementVar[0].replaceAll("\\[.*\\]", "");	
				boolean isFirstAccessedVarInCurrentClass = false;
				int startIndex = 1;
				for (JavaVariable v : vars.getVariables()) {
					if (v.getName().replace("non-null", "").substring(v.getName().indexOf(" ") + 1).equals(splittedReplacementVar[0].replaceAll("\\[.*\\]", "")))
						isFirstAccessedVarInCurrentClass = true;
				}
				if (currentClassName.startsWith("self") || currentClassName.startsWith("this") || isFirstAccessedVarInCurrentClass) {
					currentClassName = formula.getClassName();
				}
				if (isFirstAccessedVarInCurrentClass)
					startIndex = 0;
				String currentVarName = "";
				JavaVariable nestedVariable = null;
				boolean found = false;
				boolean penultimateIsArray = false;
				boolean accessArray = false;
				String penultimateVarName = "";
				for (int i = startIndex; i<splittedReplacementVar.length; i++) {
					currentVarName = splittedReplacementVar[i];
					accessArray = currentVarName.contains("[");
					// Replace brackets
					currentVarName = currentVarName.replaceAll("\\[.*\\]", "");
					if (methodClassVarMap.keySet().contains(currentClassName)) {
						for (JavaVariable methodVar : methodClassVarMap.get(currentClassName)) {
							String[] methodVarSplit = methodVar.getName().replace("static ", "").replace("non-null", "").trim().split(" ");
							String methodVarType = methodVarSplit[0];
							String methodVarName = methodVarSplit[methodVarSplit.length - 1];
							if (methodVarType.contains("[]") && methodVarName.equals(currentVarName)
									&& splittedReplacementVar.length >= 2
									&& i == (splittedReplacementVar.length - 2)) {
								penultimateIsArray = true;
								penultimateVarName = currentVarName;
							}
							if (methodVarName.equals(lastVarUsedInOldContext)) {
								nestedVariable = methodVar;
								currentVarName = methodVarName;
								found = true;
							}
							if (methodClassVarMap.containsKey(methodVarType)) {
								currentClassName = methodVarType;
							}			
						}
					}
					if (found) {
						break;
					}
				}
				// Last variable name should be the the wanted variable!
				if (currentVarName.equals(lastVarUsedInOldContext) && nestedVariable != null) {
					var = nestedVariable.getName().replace("static", "").replace("non-null", "");
					if (accessArray) {
						var = nestedVariable.getName().replace("static", "").replace("non-null", "").replaceAll("\\[.*\\]", "");
					}
				}
				if (currentVarName.equals("length") && penultimateIsArray) {
					var = "int " + penultimateVarName + "_" + currentVarName;
				}
			}
			if (!var.isEmpty()) {
				/*
				 * VarType::(heap, null, Class::$varName)
				 * What about access to static variables from other classes?
				 * Class.varName => we got the class name! But no VarType
				 */
				String varNameWithOldSuffix = var.substring(var.lastIndexOf(" ") + 1) + OLD_VARS_SUFFIX;
				// Add new modified replacements to map.
//				Console.println("Adding new Replacement: (" + varNameWithOldSuffix + ", " + replacements.get(varUsedInOldContext) + ")");
				newReplacements.put(varNameWithOldSuffix, replacements.get(varUsedInOldContext));
				programVariables += var.replace("static", "").replace(" non-null", "") + OLD_VARS_SUFFIX + "; ";
				if (!pre.contains("\\old(" + varUsedInOldContext + ")"))
					assignment += "||" + varNameWithOldSuffix + ":=" + varUsedInOldContext;
				// if variable is an Array add <created> condition for key
				if (var.contains("[]")) {
					conditionObjectsCreated += " & " + varNameWithOldSuffix + ".<created>=TRUE";
				}
			}
		}
		replacements = newReplacements;
	}
	
	public void handleOld(CbCFormula formula, JavaVariables vars, Map<String, Set<JavaVariable>> methodClassVarMap, Map<String, Set<String>> sigReturnMap) {
		extractOldKeywordVariables();
		addOldVariables(formula, vars, methodClassVarMap, sigReturnMap);
		replaceOldKeyword(pre);
		replaceOldKeyword(post);
		replaceOldKeyword(globalConditions);
	}
	
	public String replaceOldKeyword(String condition) {
		for(String key : replacements.keySet()) {
			String varNameOnly = key.substring(key.lastIndexOf(".") + 1);
			varNameOnly = varNameOnly.replaceAll("\\[.*\\]", "");
			if (varNameOnly.contains("("))
				varNameOnly = varNameOnly.substring(0, varNameOnly.indexOf("("));
			
			condition = condition.replace(replacements.get(key), varNameOnly);
		}
		if (condition.contains("\\old")) {
			Console.println("Unsupported usage of \\old keyword in condition: '" + condition + "'");
		}
		return condition;
	}
	
	public void handleReturn(AbstractStatement retStatement, JavaVariable returnVariable, CbCFormula formula, Map<String, Set<String>> sigReturnMap) {
		if(retStatement.getClass().equals(ReturnStatementImpl.class)) {
			if (returnVariable != null) {
				statement = returnVariable.getName().replace("static", "").replace(" non-null", "").split(" ")[1] + " = " + retStatement.getName();
			    post = post.replaceAll(REGEX_RESULT_KEYWORD.pattern(), returnVariable.getName().substring(returnVariable.getName().indexOf(" ")));
			} else {
				// Get Return Type of Variable
				String methodName = formula.getMethodName().substring(0, formula.getMethodName().indexOf("("));
				Set<String> returnTypesOfMethod = sigReturnMap.get(methodName);
				if (returnTypesOfMethod == null) {
					Console.println("No diagram was found that implements method '" + methodName + "'!");
				} else if (returnTypesOfMethod.size() > 1) {
					Console.println("Function " + methodName + " was declared in multiple diagrams with different return value. Return values: " + returnTypesOfMethod
						+ "\nThis is not yet supported. Please use a unique name as method for identification.\nThe first return value is used.");
				} else {
					String returnVariableType = returnTypesOfMethod.iterator().next();
					String returnVariableDeclaration = returnVariableType + " result_" + methodName;
					programVariables += returnVariableDeclaration + "; ";
					statement = "result_" + methodName + " = " + retStatement.getName().replaceAll(REGEX_THIS_KEYWORD.pattern(), "self");
//					post += "& " + returnVariableType + "::exactInstance(result_" + methodName + ") = TRUE";
					// Replace result keyword in post.
					post = post.replaceAll(REGEX_RESULT_KEYWORD.pattern(), "result_" + methodName);
				}
			}
		}
	}
}
