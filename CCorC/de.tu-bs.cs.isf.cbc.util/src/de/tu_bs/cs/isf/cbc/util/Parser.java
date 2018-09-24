package de.tu_bs.cs.isf.cbc.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;

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
		Map<String,Set<String>> variableMap = new HashMap<String,Set<String>>();
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
	
	public static Set<String> parseRightVariables(String statement) {
		Set<String> rightVariables = new HashSet<String>();
		String[] inputTokens = statement.split("[\\+\\-\\*/\\(\\),(<=)(>=)<>\\|\\&(!=)=]");
		for (String inputToken : inputTokens) {
			inputToken = inputToken.trim();
			
			if (inputToken.matches("\\d+") || inputToken.matches("\\s*")) {
			} else if (inputToken.matches("\\w+.*\\w+")) {
				rightVariables.add(inputToken.split("\\.")[0].trim());
			} else {
				rightVariables.add(inputToken);
			}
		}
		return rightVariables;
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
