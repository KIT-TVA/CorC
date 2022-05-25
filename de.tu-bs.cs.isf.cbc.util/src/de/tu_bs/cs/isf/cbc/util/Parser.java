package de.tu_bs.cs.isf.cbc.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;

public class Parser {
	
	private Map<String,String> variablesToStatements = new HashMap<String,String>();
	
	public void addVariableStatementPairs(AbstractStatement abstractStatement) throws ParserException {
		String input = abstractStatement.getName().trim();
		if (input.charAt(input.length() - 1) != ';') {
			throw new ParserException("Statement must end with ';'. " + input);
		}
		String[] statements = input.split(";");
		
		for (String nextStatement : statements) {
			if (nextStatement.contains("=")) {
				String[] nextStatementTokens = nextStatement.split("=");
				String variable = parseVariable(nextStatementTokens[0].trim());
				variablesToStatements.put(variable, nextStatementTokens[1].trim());
			}
		}
	}
	
	public static Map<String,Set<String>> findAllVariables(AbstractStatement abstractStatement) throws ParserException {
		String input = abstractStatement.getName().trim();
		Map<String,Set<String>> variableMap = new LinkedHashMap<String,Set<String>>();
		if (input.charAt(input.length() - 1) != ';') {
			throw new ParserException("Statement must end with ';'. " + input);
		}
		String[] statements = input.split(";");
		
		for (String nextStatement : statements) {
			if (nextStatement.contains("=")) {
				String[] nextStatementTokens = nextStatement.split("=");
				String variable = parseVariable(nextStatementTokens[0].trim());
				Set<String> rightVariables = parseRightVariables(nextStatementTokens[1].trim());
				variableMap.put(variable, rightVariables);
			}
		}
		return variableMap;
	}
	
	public static Map<String,List<String>> findAllMethods(AbstractStatement abstractStatement, JavaVariables vars) throws ParserException {
		String input = abstractStatement.getName().trim();
		Map<String,List<String>> methodMap = new LinkedHashMap<String,List<String>>();
		if (input.charAt(input.length() - 1) != ';') {
			throw new ParserException("Statement must end with ';'. " + input);
		}
		String[] statements = input.split(";");
		
		for (String nextStatement : statements) {
			if (nextStatement.contains("=")) {
				String[] nextStatementTokens = nextStatement.split("=");
				parseRightTerm(methodMap, nextStatementTokens[1].trim(), vars);
			} else {
				parseRightTerm(methodMap, nextStatement.trim(), vars);
			}
		}
		return methodMap;
	}
	
	public static Set<String> parseRightVariables(String statement) {
		Set<String> rightVariables = new HashSet<String>();
		String[] inputTokens = statement.split("[\\+\\-\\*/\\[\\]\\(\\),(<=)(>=)<>\\|\\&(!=)=]");
		for (String inputToken : inputTokens) {
			inputToken = inputToken.trim();
			
			if (inputToken.matches("\\d+") || inputToken.matches("\\s*")) {
			} else if (inputToken.matches("\\w+.*\\w+")) {
				rightVariables.add(inputToken.trim());
//				for (String nextField : inputToken.split("\\."))
//					rightVariables.add(nextField.trim());
			} else {
				rightVariables.add(inputToken);
			}
		}
		return rightVariables;
	}
	
	public static void parseRightTerm(Map<String,List<String>> methodMap, String statement, JavaVariables vars) {
		List<String> paramList = new ArrayList<String>();
		if ((statement.contains(".") || statement.contains("new")) && statement.contains("(")) {
			Pattern methodCallPattern = Pattern.compile("(\\w+\\.)?\\w+\\((\\w+\\,)*\\w?\\)");
			Matcher matcher = methodCallPattern.matcher(statement);
			while (matcher.find()) {
				if (matcher.group(0).contains(".")) {
					String[] nextStatementTokens = matcher.group(0).split("\\.", 2);
					String variableName = nextStatementTokens[0];
					String methodName = nextStatementTokens[1].substring(0, nextStatementTokens[1].indexOf("("));
					String params = nextStatementTokens[1].substring(nextStatementTokens[1].indexOf("(")+1, nextStatementTokens[1].length()-1);
					String[] paramArray = params.split(",");
					String variableType = getTypeOfVariable(variableName, vars);
					IFile javaFile = FileUtil.getClassFile(variableType);
					if (javaFile != null) {
						String confidentialitlyContract = getContentFromJML(javaFile, methodName);
						paramList.add(confidentialitlyContract);
					}
					paramList.addAll(Arrays.asList(paramArray));
					methodMap.put(methodName, paramList);
				} else {
					String nextStatementTokens = matcher.group(0);
					String methodName = nextStatementTokens.substring(0, nextStatementTokens.indexOf("("));
					String params = nextStatementTokens.substring(nextStatementTokens.indexOf("(")+1, nextStatementTokens.length()-1);
					String[] paramArray = params.split(",");
					paramList.addAll(Arrays.asList(paramArray));
					methodMap.put(methodName, paramList);
				}
			}
		}
	}
	
	public static String getContentFromJML(IFile javaFile, String methodName) {
		if (javaFile != null) {
			List<String> linesOfFile = FileUtil.readFileInList(javaFile.getLocation().toOSString());
			boolean methodFound = false;
			for (int i = linesOfFile.size() - 1; i >= 0; i--) {
				if (!methodFound) {
					if (linesOfFile.get(i).contains(methodName + "(")) {
						methodFound = true;
					}
				} else {
					String currLine = linesOfFile.get(i);
						return currLine.trim();
				}

			}
		}
		return "";
	}
	
	public static List<String> startLevelsOfParams(String line, boolean start) {
		String subLine;
		if (start) {
			subLine = line.substring(2, line.indexOf("-")).trim();
		} else {
			subLine = line.substring(line.indexOf(">")+1).trim();
		}
		String[] levels = subLine.split(",");
		List<String> returnList = new ArrayList<String>();
		for (String level : levels) {
			if (level.contains("@")) {
				String[] withoutAt = level.split(" ");
				returnList.add(withoutAt[withoutAt.length-1]);
			} else {
				returnList.add(level);
			}
		}
		return returnList;
	}
	
	public static List<List<String>> startAtTypesOfParams(String line, boolean start) {
		String subLine;
		if (start) {
			subLine = line.substring(2, line.indexOf("-")).trim();
		} else {
			subLine = line.substring(line.indexOf(">")+1).trim();
		}
		String[] paramTypes = subLine.split(",");
		List<List<String>> returnList = new ArrayList<List<String>>();
		for (String param : paramTypes) {
			if (param.contains("@")) {
				String[] allAts = param.split(" ");
				List<String> paramType = new ArrayList<String>();
				for (int i = 0; i < allAts.length-1; i++) {
					paramType.add(allAts[i]);
				}
				returnList.add(paramType);
			} else {
				returnList.add(new ArrayList<String>());
			}
		}
		return returnList;
	}
	
	private static String getTypeOfVariable(String variableName, JavaVariables vars) {
		for (JavaVariable var : vars.getVariables()) {
			if (var.getName().equals(variableName)) {
				return var.getType();
			}
		}
		return null;
	}

	public String destructConditionAndReplace(Condition condition) throws ParserException {
		String input = condition.getName();
		String[] inputTokens = new String[0];
		if (input.contains("&")) {
			input = input.replace("&", "|");
		} 
		if (input.contains("|")) {
			inputTokens = input.split("\\|");
		}
		if (!input.contains("&") && !input.contains("|")) {
			inputTokens = new String[] {input};
		}
		
		for (String nextRelation : inputTokens) {
			String regex = "";
			if (nextRelation.contains("<=")) {
				regex = "<=";
			} else if (nextRelation.contains(">=")) {
				regex = ">=";
			} else
			if (nextRelation.contains("<")) {
				regex = "<";
			} else if (nextRelation.contains(">")) {
				regex = ">";
			} else if (nextRelation.contains("!=")) {
				regex = "!=";
			} else if (nextRelation.contains("=")) {
				regex = "=";
			} else {
				regex = null;
			}
			
			if (regex != null) {
				String[] nextRelationTokens = nextRelation.split(regex);
				
				for (String nextRelationToken : nextRelationTokens) {
					if (nextRelationToken.contains("<") || nextRelationToken.contains(">") || nextRelationToken.contains("=")) {
						throw new ParserException("Only one relation symbol per relation. " + nextRelation);
					}
				}
				
				if (nextRelationTokens.length > 2) {
					throw new ParserException("Exactly two operands per relation. " + nextRelation);
				}
				
				for (String nextRelationToken : nextRelationTokens) {
					for (String variable : variablesToStatements.keySet()) {
						int startPosition = -1;
						if (nextRelationToken.equals(variable)) {
							startPosition = input.indexOf(nextRelationToken) + nextRelationToken.indexOf(variable);
						} else if (nextRelationToken.contains(" " + variable + " ")) {
							startPosition = input.indexOf(nextRelationToken) + nextRelationToken.indexOf(" " + variable + " ") + 1;
						} else if (nextRelationToken.contains(" " + variable + ",")) {
							startPosition = input.indexOf(nextRelationToken) + nextRelationToken.indexOf(" " + variable + ",") + 1;
						} else if (nextRelationToken.contains("," + variable + " ")) {
							startPosition = input.indexOf(nextRelationToken) + nextRelationToken.indexOf("," + variable + " ") + 1;
						} else if (nextRelationToken.contains("," + variable + ",")) {
							startPosition = input.indexOf(nextRelationToken) + nextRelationToken.indexOf("," + variable + ",") + 1;
						}
						if (startPosition != -1) {
							int endPosition = startPosition + variable.length();
							input = input.substring(0, startPosition) + variablesToStatements.get(variable) + input.substring(endPosition, input.length());
						}
					}
				}
				
			} else {
				for (String variable : variablesToStatements.keySet()) {
					int startPosition = -1;
					if (nextRelation.equals(variable)) {
						startPosition = input.indexOf(nextRelation) + nextRelation.indexOf(variable);
					} else if (nextRelation.contains(" " + variable + " ")) {
						startPosition = input.indexOf(nextRelation) + nextRelation.indexOf(" " + variable + " ") + 1;
					} else if (nextRelation.contains(" " + variable + ",")) {
						startPosition = input.indexOf(nextRelation) + nextRelation.indexOf(" " + variable + ",") + 1;
					} else if (nextRelation.contains("," + variable + " ")) {
						startPosition = input.indexOf(nextRelation) + nextRelation.indexOf("," + variable + " ") + 1;
					} else if (nextRelation.contains("," + variable + ",")) {
						startPosition = input.indexOf(nextRelation) + nextRelation.indexOf("," + variable + ",") + 1;
					}
					if (startPosition != -1) {
						int endPosition = startPosition + variable.length();
						input = input.substring(0, startPosition) + variablesToStatements.get(variable) + input.substring(endPosition, input.length());
					}
				}
			}
		}
		return input;
	}
	
	public static String parseVariable(String input) throws ParserException {
		String[] inputTokens = input.split(" ");
		if (inputTokens.length == 2) {
			if (inputTokens[1].contains("[")) {
				return inputTokens[1].trim().substring(0, inputTokens[1].indexOf("["));
			} else {
				return inputTokens[1].trim();
			}
		} else if (inputTokens.length == 1) {
			if (inputTokens[0].contains("[")) {
				return inputTokens[0].trim().substring(0, inputTokens[0].indexOf("["));
			} else {
				return inputTokens[0].trim();
			}
		} else {
			throw new ParserException("Variable not in format \"<type> <name>\"." + input);
		}
	}
	
	public static void main(String[] args) {
		
		AbstractStatement st = CbcmodelFactory.eINSTANCE.createAbstractStatement();
		Condition con =  CbcmodelFactory.eINSTANCE.createCondition();
		String s = "int i = 5 + 2; do(x); int y = x(f(ff),g+1,h); int z = 7 - 4; int u = 8 / 5;";
		s = "a<=b-1 & b>c";
		String c = "x & y | x";
		st.setName(s);
		con.setName(c);
		try {
			Parser p = new Parser();
			System.out.println(p.parseRightVariables(s));
//			p.addVariableStatementPairs(st);
//			p.destructConditionAndReplace(con);
//			System.out.println(p.destructConditionAndReplace(con));
//			System.out.println(con);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("ende");
	}
}
