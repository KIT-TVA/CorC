package de.tu_bs.cs.isf.cbc.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;

import com.google.common.collect.Lists;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionTechnique;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;

public class Parser {
	public static final String KEYWORD_JML_PRE = "requires";
	public static final String KEYWORD_JML_POST = "ensures";
	public static final String KEYWORD_JML_MODIFIABLE = "assignable";
	private Map<String, String> variablesToStatements = new HashMap<String, String>();

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

	public static List<String> findAllVariables(AbstractStatement abstractStatement, JavaVariables vars)
			throws ParserException {
		String input = abstractStatement.getName().trim();
		List<String> variableList = new LinkedList<String>();
		if (input.charAt(input.length() - 1) != ';') {
			throw new ParserException("Statement must end with ';'. " + input);
		}
		String[] statements = input.split(";");

		for (String nextStatement : statements) {
			if (nextStatement.contains("=")) {
				String[] nextStatementTokens = nextStatement.split("=");
				String variable = parseVariable(nextStatementTokens[0].trim());
				variableList.add(variable);
			}
			if (nextStatement.contains(".") && nextStatement.contains("(")) {
				Pattern methodCallPattern = Pattern.compile("\\w+\\.\\w+\\(");
				Matcher matcher = methodCallPattern.matcher(nextStatement);
				while (matcher.find()) {
					String[] nextStatementTokens = matcher.group(0).split("\\.", 2);
					String variableName = nextStatementTokens[0];
					String methodName = nextStatementTokens[1].replaceAll("\\(", "");
					String variableType = getTypeOfVariable(variableName, vars);
					IFile javaFile = FileUtil.getClassFile(variableType);
					if (javaFile != null) {
						String assignablesFromMethodCall = getContentFromJML(javaFile, methodName,
								KEYWORD_JML_MODIFIABLE);
						if (!assignablesFromMethodCall.equals("")) {
							for (String var : assignablesFromMethodCall.split(",")) {
								variableList.add(variableName + "." + var);
							}
						}
					}
				}

			}
		}
		return variableList;

	}

	private static String getTypeOfVariable(String variableName, JavaVariables vars) {
		for (JavaVariable var : vars.getVariables()) {
			String[] splittedName = var.getName().split(" ");
			if (splittedName.length > 1) {
				if (splittedName[1].equals(variableName)) {
					return splittedName[0];
				}
			}
		}
		return null;
	}

	public static String getContentFromJML(IFile javaFile, String methodName, String keyword) {
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
					if (currLine.contains(keyword)) {
						return currLine
								.substring(currLine.indexOf(keyword) + keyword.length(), currLine.lastIndexOf(";"))
								.trim();
					} else if (currLine.contains("}")) {
						if (keyword == KEYWORD_JML_MODIFIABLE) {
							return "";
						} else {
							return "true";
						}

					}
				}

			}
		}
		return "";

	}

	private static Set<String> parseRightVariables(String statement) {
		Set<String> rightVariables = new HashSet<String>();
		String[] inputTokens = statement.split("[\\+\\-\\*/\\(\\),]");

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
			inputTokens = new String[] { input };
		}

		for (String nextRelation : inputTokens) {
			String regex = "";
			if (nextRelation.contains("<=")) {
				regex = "<=";
			} else if (nextRelation.contains(">=")) {
				regex = ">=";
			} else if (nextRelation.contains("<")) {
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
					if (nextRelationToken.contains("<") || nextRelationToken.contains(">")
							|| nextRelationToken.contains("=")) {
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
							startPosition = input.indexOf(nextRelationToken)
									+ nextRelationToken.indexOf(" " + variable + " ") + 1;
						} else if (nextRelationToken.contains(" " + variable + ",")) {
							startPosition = input.indexOf(nextRelationToken)
									+ nextRelationToken.indexOf(" " + variable + ",") + 1;
						} else if (nextRelationToken.contains("," + variable + " ")) {
							startPosition = input.indexOf(nextRelationToken)
									+ nextRelationToken.indexOf("," + variable + " ") + 1;
						} else if (nextRelationToken.contains("," + variable + ",")) {
							startPosition = input.indexOf(nextRelationToken)
									+ nextRelationToken.indexOf("," + variable + ",") + 1;
						}
						if (startPosition != -1) {
							int endPosition = startPosition + variable.length();
							input = input.substring(0, startPosition) + variablesToStatements.get(variable)
									+ input.substring(endPosition, input.length());
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
						input = input.substring(0, startPosition) + variablesToStatements.get(variable)
								+ input.substring(endPosition, input.length());
					}
				}
			}
		}
		return input;
	}

	public static String parseVariable(String input) throws ParserException {
		String[] inputTokens = input.split(" ");
		if (inputTokens.length == 2) {
			return inputTokens[1].trim();
		} else if (inputTokens.length == 1) {
			return inputTokens[0].trim();
		} else {
			throw new ParserException("Variable not in format \"<type> <name>\"." + input);
		}
	}

	public static CompositionTechnique getCompositionTechniqueForMethod(IFile classFile, String methodName,
			String keyword) {
		List<String> linesOfFile = FileUtil.readFileInList(classFile.getLocation().toOSString());
		boolean methodFound = false;
		for (int i = linesOfFile.size() - 1; i >= 0; i--) {
			if (!methodFound) {
				if (linesOfFile.get(i).contains(methodName + "(")) {
					methodFound = true;
				}
			} else {
				String currLine = linesOfFile.get(i);
				if (currLine.contains(keyword)) {
					if (currLine.contains(ProveWithKey.REGEX_ORIGINAL)) {
						return CompositionTechnique.EXPLICIT_CONTRACTING;
					} else if (currLine.contains("conjunctive_contract")) {
						return CompositionTechnique.CONJUNCTIVE_CONTRACTING;
					}
				}
			}

		}
		return CompositionTechnique.CONTRACT_OVERRIDING;
	}

	public static String getConditionFromCondition(String condition) {
		if (condition.contains("modifiable")) {
			String[] splittedCondition = condition.split(";", 2);
			if (splittedCondition.length > 1) {
				return splittedCondition[1].trim();
			}
		}
		return condition;
	}

	public static List<String> getUnmodifiedVars(List<String> modifiedVars, EList<JavaVariable> declaredVariables) {
		List<String> unmodifiedVariables = Lists.newArrayList();
		if (!modifiedVars.contains("\\everything")) {
			for (JavaVariable var : declaredVariables) {
				String varName = var.getName().split(" ")[1];
				if (!modifiedVars.contains(varName)) {
					unmodifiedVariables.add(var.getName());
				}
			}
		}
		return unmodifiedVariables;
	}

	public static List<String> getModifiedVarsFromCondition(String condition) {
		String variables = null;
		List<String> modifiedVars = Lists.newArrayList();
		if (!condition.contains("modifiable(")) {
			modifiedVars.add("\\everything");
		} else if (condition.contains("modifiable(")) {
			variables = condition.split(";", 2)[0];
			if (variables != null) {
				variables = variables.substring(variables.indexOf("(") + 1, variables.indexOf(")"));
				variables = variables.replace(" ", "");
				variables = variables.replace(System.getProperty("line.separator"), "");
				modifiedVars = Lists.newArrayList(variables.split(","));
			}
		}
		return modifiedVars;
	}

	public static String getModifieableVarsFromCondition(String condition) {
		String variables = "\\\\everything;";
		if (condition.contains("modifiable(") && condition.split(";").length > 1) {
			variables = condition.split(";")[0];
			if (variables != null) {
				variables = variables.substring(variables.indexOf("(") + 1, variables.indexOf(")"));
				variables = variables.replace(" ", "");
				variables = variables.replace(System.getProperty("line.separator"), "");
			}
		}
		return variables;
	}

	public static String rewriteConditionToJML(String condition) {
		condition = condition.replaceAll("(?<![!<>])=", "==");
		condition = condition.replaceAll("->", "==>");
		condition = condition.replaceAll("<->", "<==>");
		condition = condition.replaceAll("&", "&&");
		condition = condition.replaceAll("(?<==\\s?)TRUE", "true");
		condition = condition.replaceAll("(?<==\\s?)FALSE", "false");
		condition = condition.replaceAll("(\\w*)::instance\\((\\w*)\\) = TRUE", "$2 instanceof $1");
		return condition;
	}

	public static String rewriteJMLConditionToKeY(String condition) {

		condition = condition.replaceAll("==>", "->");
		condition = condition.replaceAll("<==>", "<->");
		condition = condition.replaceAll("==", "=");
		condition = condition.replaceAll("&&", "&");
		condition = condition.replaceAll("(?<==\\s?)true", "TRUE");
		condition = condition.replaceAll("(?<==\\s?)false", "FALSE");
		condition = condition.replaceAll("(\\w*)\\sinstanceof\\s(\\w*)", "$2::instance($1) = TRUE");
		return condition;
	}

	public static String getMethodStubFromFile(String className, String methodName) {
		String methodStub = "";
		IFile file = FileUtil.getClassFile(className);
		boolean methodFound = false;
		int braketCounter = 0;
		if (file != null) {
			List<String> linesOfFile = FileUtil.readFileInList(file.getLocation().toOSString());
			for (int i = 0; i < linesOfFile.size(); i++) {
				String currLine = linesOfFile.get(i);
				if (!methodFound) {
					if (currLine.contains(methodName + "(")) {
						methodFound = true;
						methodStub = currLine;
						braketCounter++;
					}
				} else {
					methodStub += currLine;
					if (currLine.contains("{")) {
						braketCounter++;
					}
					if (currLine.contains("}")) {
						braketCounter--;
					}
					if (currLine.contains("}") && braketCounter == 0) {
						return methodStub;
					}
				}
			}
		}
		return methodStub;
	}

	// public static void main(String[] args) {
	//
	// AbstractStatement st = CbcmodelFactory.eINSTANCE.createAbstractStatement();
	// Condition con = CbcmodelFactory.eINSTANCE.createCondition();
	// String s = "int i = 5 + 2; do(x); int y = x(f(ff),g+1,h); int z = 7 - 4; int
	// u = 8 / 5;";
	// String c = "x & y | x";
	// st.setName(s);
	// con.setName(c);
	// try {
	// Parser p = new Parser();
	// System.out.println(p.findAllVariables(st));
	// p.addVariableStatementPairs(st);
	// p.destructConditionAndReplace(con);
	// System.out.println(p.destructConditionAndReplace(con));
	// System.out.println(con);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// System.out.println("ende");
	// }
}
