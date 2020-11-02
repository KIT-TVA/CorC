package de.tu_bs.cs.isf.cbc.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
			String variable = "";
			if(nextStatement.matches("(\\+\\+)(\\w+)")) {
				variable = nextStatement.split("\\+\\+")[0];
			} else if(nextStatement.matches("(\\-\\-)(\\w+)")) {
				variable = nextStatement.split("\\-\\-")[0];
			} else if(nextStatement.matches("(\\w+)(\\-\\-)")) {
				variable = nextStatement.split("\\-\\-")[0];
			} else if(nextStatement.matches("(\\w+)(\\+\\+)")) {
				variable = nextStatement.split("\\+\\+")[0];
			}
			
			if(!variable.isEmpty()) {
				variableList.add(variable);
				variable = "";
			}
				
			if (nextStatement.contains("=")) {
				String[] nextStatementTokens;
				if(nextStatement.contains("*="))
					nextStatementTokens = nextStatement.split("[*]=");
				else if(nextStatement.contains("-="))
					nextStatementTokens = nextStatement.split("[-]=");
				else if(nextStatement.contains("+="))
					nextStatementTokens = nextStatement.split("[+]=");
				else if(nextStatement.contains("/="))
					nextStatementTokens = nextStatement.split("[/]=");
				else
					nextStatementTokens = nextStatement.split("=");
			    variable = parseVariable(nextStatementTokens[0].trim());
				//add only variables of kind param or global
				/*String typeOfVariable = getTypeOfVariable(variable, vars);
				if(variable.startsWith("this.") || 
						vars.getVariables().stream().filter(e -> e.getName().equals(typeOfVariable + " " + variable) 
						&& e.getKind() == VariableKind.PARAM).count() > 0) {
					variableList.add(variable);
				}*/
				//--------------------------------
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
								/*if(vars.getVariables().stream().filter(e -> e.getName().equals(variableName) && e.getKind() == VariableKind.PARAM).count() > 0) {
									variableList.add(variableName + "." + var);
								}*/
								if(nextStatement.trim().startsWith("this.")) {
									if(!var.equals("\\nothing"))
										variableList.add("this." + variableName + "." + var);
									else
										variableList.add("this." + variableName);
								} else {
									if(!var.equals("\\nothing"))
										variableList.add(variableName + "." + var);
									else
										variableList.add(variableName);	
								}
							}
						}
					}
				}

			}
		}
		return variableList;

	}

	public static String getTypeOfVariable(String variableName, JavaVariables vars) {
		variableName = variableName.replaceFirst("\\w+\\.", "");
		variableName = variableName.replaceFirst("\\[\\w+\\]", "");
		for (JavaVariable var : vars.getVariables()) {
			String[] splittedName = var.getName().split(" ");
			if (splittedName.length > 1) {
				if (splittedName[1].trim().equals(variableName.trim())) {
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

	public static CompositionTechnique getCompositionTechniqueForMethod(IFile classFile, String feature,
			String keyword, String callingMethod) {
		String path = classFile.getLocation().toOSString();
		String pathParts[] = path.split("\\\\");
		String location = "";
		for (int i = 0; i < pathParts.length - 2; i++) {
			location += pathParts[i] + "\\";
		}
		location += "features\\" + feature.substring(0, 1).toUpperCase() + feature.substring(1) + "\\diagram\\" + callingMethod + ".cbcmodel";
		List<String> linesOfFile = FileUtil.readFileInList(location);
		for (int i = 0; i < linesOfFile.size(); i++) {
			String currLine = linesOfFile.get(i);
			if (currLine.contains("EXPLICIT_CONTRACTING")) {
				return CompositionTechnique.EXPLICIT_CONTRACTING;
			} else if (currLine.contains("CONJUNCTIVE_CONTRACTING")) {
				return CompositionTechnique.CONJUNCTIVE_CONTRACTING;
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

	public static String getModifieableVarsFromCondition(String condition) {//modifiable in assignable+++++++++++++++++++++++++++++++++++++++++++++++++++
//		String variables = "\\\\everything;";
		String variables = "\\nothing";
		if (condition.contains("modifiable(") && condition.split(";").length > 1) {
			if(!condition.contains("modifiable(*)") && !condition.contains("nothing") ) {
				variables = condition.split(";")[0];
				if (variables != null) {
					variables = variables.substring(variables.indexOf("(") + 1, variables.indexOf(")"));
					variables = variables.replace(" ", "");
					variables = variables.replace(System.getProperty("line.separator"), "");
				}
			} else
				variables = "\\everything";
		}
		return variables;
	}
	
	
	public static String rewriteConditionToJML(String condition) {
		condition = condition.replaceAll("(?<!<|>|!|=)(\\s*=\\s*)(?!<|>|=)", " == ");
		condition = condition.replaceAll("->", "==>");
		condition = condition.replaceAll("<->", "<==>");
		condition = condition.replaceAll("&", "&&");
		condition = condition.replace("|", "||");
		condition = condition.replaceAll("(?<==\\s?)TRUE", "true");
		condition = condition.replaceAll("(?<==\\s?)FALSE", "false");
		condition = condition.replaceAll("(\\w*)::instance\\((\\w*)\\) = TRUE", "$2 instanceof $1");
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
	
	public static String getModifieableVarsFromCondition2(String condition, LinkedList<String> vars) {
		String variables = getModifieableVarsFromCondition(condition);
		Console.println("vars: " + variables);
		if(variables.contains("nothing") || variables.contains("everything")) {
			return variables;
		} else {
			String[] assignableVariables = variables.split(",");			
//			String s;//should be a list
			variables = "";
			if(assignableVariables[0].startsWith("this.")) {
				assignableVariables[0] = assignableVariables[0].replaceAll("\\[.*\\]", "\\[\\*\\]");
				variables = assignableVariables[0]; 
			}
			for(int i = 1; i < assignableVariables.length; i++) {//only global vars are modifiable
				if(assignableVariables[i].startsWith("this.")) {
					assignableVariables[i] = assignableVariables[i].replaceAll("\\[.*\\]", "\\[\\*\\]");
					if(variables.isEmpty())
						variables = assignableVariables[i];
					else if(!Arrays.stream(variables.split(",")).anyMatch(assignableVariables[i]::equals))
						variables = variables + "," + assignableVariables[i];
				}
				/*if(assignableVariables[i].contains("[")) {
					s = assignableVariables[i].substring(0, assignableVariables[i].indexOf('[')) + "[*]";
					variables = variables.replaceFirst(
							assignableVariables[i].substring(0, assignableVariables[i].indexOf('[')) + "\\[\\w*.?\\w+\\]", s);
					variables = variables.replaceAll(
							"\\," + assignableVariables[i].substring(0, assignableVariables[i].indexOf('[')) + "\\[\\w*.?\\w+\\]", "");
					variables = variables.replaceAll(
							assignableVariables[i].substring(0, assignableVariables[i].indexOf('[')) + "\\[\\w*.?\\w+\\]\\,", "");
					assignableVariables[i] = s;
				}
				int j = i;
				if(vars.stream().filter(e -> e.split(" ")[1].equals(assignableVariables[j])
						|| (e.split(" ")[1] + "[*]").equals(assignableVariables[j])).count() > 0) {
					if(variables.contains(assignableVariables[i] + ","))
						variables = variables.replace(assignableVariables[i] + "," , "");
					else if(variables.contains("," + assignableVariables[i]))
						variables = variables.replace("," + assignableVariables[i] , "");
					else
						return variables = "\\nothing";
				}*/
			}
		}		
		if(variables.isEmpty()) {
			return variables = "\\nothing";
		}
		return variables;
	}

	public static String extractMethodNameFromStatemtent(String stmt) {
		String methodName = "";
		boolean isInSameClass = false;
		char stmtChar[] = stmt.toCharArray();
		boolean name = false;
		for (int i = stmtChar.length -1; i >= 0; i--) {
			if (!name && stmtChar[i] == '(') {
				name = true;
			} else if (name && Character.isLetter(stmtChar[i])) {
				methodName = stmtChar[i] + methodName;
				isInSameClass = true;
			} else if (name && stmtChar[i] == '.') {
				isInSameClass = false;
				break;
			} else {
				name = false;
			}
		}
		if (isInSameClass) {
			methodName = methodName.substring(0, 1).toUpperCase() + methodName.substring(1) + ".diagram";
		}
		return methodName;
	}
}
