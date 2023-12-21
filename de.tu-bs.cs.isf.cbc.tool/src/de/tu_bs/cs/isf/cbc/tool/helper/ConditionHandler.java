package de.tu_bs.cs.isf.cbc.tool.helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.URI;

import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.exceptions.ExceptionMessages;
import de.tu_bs.cs.isf.cbc.exceptions.ReferenceException;
import de.tu_bs.cs.isf.cbc.exceptions.UnexpectedTokenException;
import de.tu_bs.cs.isf.cbc.util.CodeHandler;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.cbc.util.InputData;
import de.tu_bs.cs.isf.cbc.util.JavaCondition;
import de.tu_bs.cs.isf.cbc.util.conditionparser.ConditionParser;
import de.tu_bs.cs.isf.cbc.util.conditionparser.Node;

public final class ConditionHandler {
	/**
	 * Translates condition from JavaDL into Java and returns the resulting java condition.
	 * @param condition
	 * @param instanceName
	 * @param gVars
	 * @param projectPath
	 * @return
	 * @throws UnexpectedTokenException 
	 */
	public static JavaCondition translateConditionToJava(final URI projectPath, String condition, String instanceName, List<InputData> gVars) throws UnexpectedTokenException {
		var tree = parseCondition(projectPath, condition, instanceName, gVars);
		JavaCondition javaCondition = new JavaCondition(tree);
		return javaCondition;
	}
	
	public static String cleanCondition(final URI projectPath, String condition, final String className) {
		condition = translateCondition(projectPath, condition, "this", new ArrayList<InputData>(), false);
		condition = CodeHandler.removeFunctions(condition);
		condition = CodeHandler.removeFloatingClosingBrackets(condition);
		condition = CodeHandler.removeDotIdentifiers(condition, className);
		return condition;
	}
	
	public static String replaceResultKeyword(String condition, final JavaVariable returnVar) throws ReferenceException {
		if (condition.contains("\\result") && returnVar == null) {
			throw new ReferenceException(ExceptionMessages.RETRESOLVE);
		}
		if (returnVar == null) {
			return condition;
		}
		final String target = "\\result";
		int start = condition.indexOf(target);
		int len = target.length();
		
		while (start != -1) {
			condition = condition.substring(0, start) 
					+ returnVar.getName().split("\\s")[1] 
					+ condition.substring(start + len, condition.length());
			start = condition.indexOf(target);		
		}
		return condition;
	}
	
	public static String replaceMathFuncs(String c) {
		Pattern p = Pattern.compile("max\\([^\\,]+\\,\\s*[^\\,]+\\,\\s*[^\\,]+\\,\\s*[^\\,\\)]+\\)");
		Matcher m = p.matcher(c);
		
		while (m.find()) {
			final String innerPart = m.group(0).substring(m.group(0).indexOf("(") + 1, m.group(0).indexOf(")"));
			final String[] params = innerPart.split("\\,");
			c = c.substring(0, m.start()) + "IntStream.of(" + params[0].trim() + ").boxed().toList().subList(" + params[1].strip() + ", " + params[2].strip() + ").stream().max(Integer::compare).get() == " 
			+ params[0].trim() 
			+ "[" + params[3].trim() 
			+ "]" + c.substring(m.end(), c.length());
			m = p.matcher(c);
		}
		
		p = Pattern.compile("appears\\([^\\,]+\\,\\s*[^\\,]+\\,\\s*[^\\,]+\\,\\s*[^\\,\\)]+\\)");
		m = p.matcher(c);		
		while (m.find()) {
			final String innerPart = m.group(0).substring(m.group(0).indexOf("(") + 1, m.group(0).indexOf(")"));
			final String[] params = innerPart.split("\\,");
			c = c.substring(0, m.start()) + "IntStream.of(" + params[0].strip() + ")" 
			+ ".boxed().toList().subList(" + params[2].strip() + ", " + params[3].strip() 
			+ ").contains(" + params[1].strip() + ")" 
			+ c.substring(m.end(), c.length());
			m = p.matcher(c);
		}
		
		p = Pattern.compile("pow\\([^\\,]+\\,\\s*[^\\,]+\\)");
		m = p.matcher(c);		
		while (m.find()) {
			if (m.start() > 0 && c.charAt(m.start() - 1) == '.') {
				continue;
			}
			final String innerPart = m.group(0).substring(m.group(0).indexOf("(") + 1, m.group(0).indexOf(")"));
			c = c.substring(0, m.start()) + "Math.pow(" + innerPart + ")" + c.substring(m.end(), c.length());
			m = p.matcher(c);
		}		
		return c;
	}
	
	public static String translateCondition(final URI projectPath, final String condition, String instanceName, List<InputData> gVars, boolean replaceMathFuncs) {
		String c = condition;
		//c = c.replaceAll("[a-z]\\w*\\.", ""); 

		if (c.contains("modifiable")) {
			c = c.split("\\;", 2)[1].trim();
		}
		//c = c.replaceAll("this\\.\\s*", instanceName + "\\."); 
		c = c.replaceAll("(?<!<|>|!|=)(\\s*=\\s*)(?!<|>|=)", " == ");
		c = c.replaceAll("(?<==\\s?)TRUE", "true");
		c = c.replaceAll("(?<==\\s?)FALSE", "false");
		c = c.replaceAll(".<created>\\s*=\\s*TRUE", " != null");
		c = c.replaceAll(".<created>\\s*=\\s*FALSE", " == null");
		//c = c.replaceAll("this\\.", instanceName + ".");
		c = replacePrimitives(c);
		if (replaceMathFuncs) {
			c = replaceMathFuncs(c);
		}
		// place instanceName before each gVar and method
		if (!instanceName.isEmpty()) {
			c = c.replaceAll("(\\W)this(\\W)", "$1" + instanceName + "$2");
			c = c.replaceAll("this$", instanceName);
			c = c.replaceAll("^this", instanceName);
			c = addInstanceNameToVar(c, instanceName, gVars);
			var className = Character.toUpperCase(instanceName.charAt(0)) + instanceName.substring(1, instanceName.length());
			c = addInstanceNameToMethod(c, instanceName, getAllMethodsOfClass(className, projectPath));
		}
		return c;
	}
	
	private static List<String> getAllMethodsOfClass(final String className, final URI projectPath) {
		final var output = new ArrayList<String>();
		final var javaFile = new File(FileUtil.getProjectLocation(projectPath) + "\\tests\\" + className + ".java");
		
		
		if (!javaFile.exists()){
			return output;
	    }
		try {			
			var code = Files.readString(javaFile.toPath());
			return getAllMethodsOfCode(code);
		} catch (IOException e) {
			Console.println(e.getMessage());
		}
		return null;
	}
	
	public static List<String> getAllMethodsOfCode(final String code) {
		final var output = new ArrayList<String>();
		int startingBracketIndex;
		int closingBracketIndex;
		Pattern p = Pattern.compile("\\w+\\s\\w+\\s\\w+\\(.*\\)\\s*\\{");
		Matcher m = p.matcher(code);
		
		
		while (m.find()) {
			startingBracketIndex =  m.start() + m.group(0).indexOf('{');
			closingBracketIndex = CodeHandler.findClosingBracketIndex(code, startingBracketIndex, '{');
			output.add(code.substring(m.start(), closingBracketIndex + 1));
		}
		return output;
	}
	
	public static String translateCondition(final URI projectPath, final String condition, String instanceName, List<InputData> gVars) {
		return translateCondition(projectPath, condition, instanceName, gVars, true);
	}
	
	public static Node parseCondition(final URI projectPath, final String condition, String instanceName, List<InputData> gVars) throws UnexpectedTokenException {
		String c = translateCondition(projectPath, condition, instanceName, gVars);
		
		ConditionParser parser = new ConditionParser();
		var tree = parser.parse(c);
		return tree;
	}
	
	public static String addInstanceNameToVar(String condition, final String instanceName, final List<InputData> globalVars) {
		for (var globalVar: globalVars) {
			Pattern p = Pattern.compile("^" + Pattern.quote(globalVar.getName()) + "\\W|\\W" + Pattern.quote(globalVar.getName()) + "$|" + "\\W" + Pattern.quote(globalVar.getName()) + "\\W");
			Matcher m = p.matcher(condition);
			int start = -1;
			
			while (m.find()) {
				if (condition.charAt(m.start()) == '.') continue;
				if (m.start() == 0) {
					if (Character.isAlphabetic(condition.charAt(m.start()))) {
						start = 0;
					} else {
						start = 1;
					}
					condition = condition.substring(0, start) + instanceName + "." + m.group(0).substring(start, m.end() - 1) + condition.substring(m.end() - 1, condition.length());
				} else {
					if (condition.substring(0, m.start() + 1).endsWith("old(")) continue;
					condition = condition.substring(0, m.start() + 1) 
							+ instanceName + "." + condition.substring(m.start() + 1, m.end() - 1) + condition.substring(m.end() - 1, condition.length());	
				}
				m = p.matcher(condition);
			}
		}
		return condition;
	}
	
	private static String replacePrimitives(String input) {
		input = input.replace("instanceof byte", "instanceof Byte");
		input = input.replace("instanceof short", "instanceof Short");
		input = input.replace("instanceof int", "instanceof Integer");
		input = input.replace("instanceof long", "instanceof Long");
		input = input.replace("instanceof char", "instanceof Character");
		input = input.replace("instanceof string", "instanceof String");
		input = input.replace("instanceof boolean", "instanceof Boolean");
		return input;
	}
	
	public static String addInstanceNameToMethod(String condition, final String instanceName, final List<String> methodLst) {
		final var methods = new ArrayList<String>();
		methods.addAll(methodLst);
		int startIndex = 0;
		int offset = 0;
		for (int m = 0; m < methods.size(); m++) {
			var curMethod = methods.get(m);
			String params = curMethod.substring(curMethod.indexOf('('), curMethod.indexOf(')') + 1);
			String methodName = curMethod.split("\\s")[2];
			methodName = methodName.substring(0, methodName.indexOf('('));
			methodName += params;
			methods.set(m, methodName.trim());
		}
		for (var method : methods) {
			Pattern p = Pattern.compile(Pattern.quote(method));
			Matcher m = p.matcher(condition);
			while (m.find()) {
				startIndex = m.start() + offset;
				if (startIndex == 0 || condition.charAt(startIndex - 1) != '.') {			
					// make sure the string is not a substring of a bigger word
					if (startIndex > 0) {
						if (Character.isAlphabetic(condition.charAt(startIndex - 1))) continue;
					}
					if (m.end() + 1 < condition.length()) {
						if (Character.isAlphabetic(condition.charAt(m.end() + 1))) continue;
					}
					var prevLen = condition.length();
					condition = condition.substring(0, startIndex) 
							+ instanceName + "." + m.group(0) + condition.substring(m.end(), condition.length());
					offset += condition.length() - prevLen;
				}				
			}
			offset = 0;
		}
		return condition;
	}
	
	public static String initializeOldVars(final String postCondition, String classVarStr, int numTabs, List<String> paramNames) {
		// TODO: old keyword can be used for none instance variables as well like for parameters for example. (example diagram: transfer)
		String output = "";
		var tabs = "";
		var postConditionStr = postCondition;
		String oldVarStr;
		int varEndIndex;
		int indexOfOld = postConditionStr.indexOf("old_");
		String varStr;
		
		
		for (int i = 0; i < numTabs; i++) tabs += "\t";
		
		while (indexOfOld != -1) {
			oldVarStr = postConditionStr.substring(indexOfOld, postConditionStr.length());
			varEndIndex = oldVarStr.split("[^\\w]")[0].length();
			if (varEndIndex == -1) {
				oldVarStr = oldVarStr.substring(0, oldVarStr.length());
				postConditionStr = "";
			}
			else {
				oldVarStr = oldVarStr.substring(0, varEndIndex);
				postConditionStr = postConditionStr.substring(indexOfOld + varEndIndex, postConditionStr.length());
			}
			if (!output.contains(oldVarStr)) {
				varStr = oldVarStr.split("old\\_")[1];
				if (paramNames.contains(varStr) || varStr.toLowerCase().equals(classVarStr)) {
					output += tabs + "var " + oldVarStr + " = " + varStr.toLowerCase() + ";\n";
				} else {
					output += tabs + "var " + oldVarStr + " = " + classVarStr + "." + varStr + ";\n";
				}
			}
			indexOfOld = postConditionStr.indexOf("old_");
		}
		output += "\n";
		return output;
	}
}
