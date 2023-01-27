package de.tu_bs.cs.isf.cbc.tool.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import de.tu_bs.cs.isf.cbc.tool.features.TestAndAssertionGenerator;
import de.tu_bs.cs.isf.cbc.tool.features.TestStatementException;

public final class CodeHandler {
	public static final String PRECHECKS_START = "//[checks]";
	public static final String PRECHECKS_END = "//[end_checks]";
	
	/**
	 * Splits toSplit on delimitor *delimitorRegEx* but ignores all terms inside brackets.
	 * @param toSplit
	 * @param delimitorRegEx
	 * @return
	 */
	public static String[] sSplit(String toSplit, String delimitorRegEx) {
		final String[] output;
		final var calls = new ArrayList<String>();
		String c;
		var helper = toSplit;
		int counter = 0;
		
		
		while (helper.contains("(")) {
			if (!helper.contains(")")) {
				break;
			}
			c = helper.substring(helper.indexOf('('), helper.indexOf(')') + 1);
			helper = helper.replace(c, "");
			toSplit = toSplit.replace(c, "<$" + counter + ">");
			calls.add(c);
			counter++;
		}
		output = toSplit.split(delimitorRegEx);
		for (int i = 0; i < calls.size(); i++) {
			for (int j = 0; j < output.length; j++) {
				output[j] = output[j].replace("<$" + i + ">", calls.get(i));
			}
		}	
		return output;
	}
	
	public static String getTabs(long num) {
		var out = "";
		for (int i = 0; i < num; i++) out+="\t";
		return out;
	}

	public static int findClosingBracketIndex(final String code, final int bracketIndex, char bracket) {
		char closingBracket;
		int bracketCounter = 1;
		
		if (bracket == '(') closingBracket = ')';
		else if (bracket == '[') closingBracket = ']';
		else if (bracket == '{') closingBracket = '}';
		else closingBracket = ')';
		
		
		for (int i = bracketIndex + 1; i < code.length(); i++) {
			if (code.charAt(i) == bracket) {
				bracketCounter++;
			} else if (code.charAt(i) == closingBracket) {
				bracketCounter--;
			}
			if (bracketCounter == 0) {
				//var tmp = code.substring(bracketIndex, i + 1);
				return i;
			}
		}
		return -1;
	}
	
	public static boolean isWord(String word) {
		for (int i = 0; i < word.length(); i++) {
			if (!Character.isLetter(word.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	public static int countBrackets(String condition, char bracket) {
		char closingBracket;
		int output = 0;
		
		if (bracket == '(') closingBracket = ')';
		else if (bracket == '[') closingBracket = ']';
		else closingBracket = '}';
		
		for (int i = 0; i < condition.length(); i++) {
			if (condition.charAt(i) == bracket) {
				output++;
			} else if (condition.charAt(i) == closingBracket) {
				output--;
			}
		}
		return output;
	}
	
	public static String removeAllComments(String code) {
		String helper;
		int curIndex = code.indexOf("/*");
		
		while (curIndex != -1) {
			if (curIndex - 1 >= 0 && code.charAt(curIndex - 1) == ' ') {
				helper = code.substring(curIndex - 1, code.length());
			} else {
				helper = code.substring(curIndex, code.length());
			}
			helper = helper.substring(0, helper.indexOf("*/") + 2);
			code = code.replace(helper, "");
			curIndex = code.indexOf("/*");
		}
		code = code.replaceAll("\\ssynchronized", "");
		return code;
	}
	
	public static String removeFunctions(String condition) {
		Pattern p = Pattern.compile("IntStream");
		Matcher m = p.matcher(condition);
		
		while (m.find()) {
			String helper = condition.substring(0, m.start());
			int start = helper.lastIndexOf("&");
			helper = condition.substring(m.start(), condition.length());
			int end = helper.indexOf("&") + m.start();
			if (start == -1) {
				condition = condition.substring(end + 1, condition.length());
			} else if (end == -1) {
				condition = condition.substring(0, start);
			} else {
				condition = condition.substring(0, start) + condition.substring(end, condition.length());
			}
			m = p.matcher(condition);
		}
		return condition;
	}
	
	public static String removeFloatingClosingBrackets(String condition) {
		int bracketCounter = 0;
		for (int i = 0; i < condition.length(); i++) {
			if (condition.charAt(i) == '(') bracketCounter++;
			else if (condition.charAt(i) == ')') bracketCounter--;
			if (bracketCounter < 0) {
				bracketCounter++;
				condition = condition.substring(0, i) + " " + condition.substring(i + 1, condition.length());
			}
		}
		return condition;
	}
	
	public static String indentCode(String code, int preTabs) {
		String cleanedCode = "";
		code = removeAllTabs(code);
		var lines = Stream.of(code.split("\\n")).toList();
		int numBrackets = preTabs;
		String preTabsStr = "";
		for (int i = 0; i < preTabs; i++) preTabsStr += "\t";
		
		for (var line : lines) {
			int closing = line.indexOf('}') != -1 ? numBrackets-- : 0;
			cleanedCode = cleanedCode + "\n" + insertLineTabs(line, numBrackets);	
			int opening = line.indexOf('{') != -1 ? numBrackets++ : 0;
		}
		return preTabsStr + cleanedCode.strip();
	}
	
	public static String removeDotIdentifiers(String condition, String className) {
		condition = condition.replaceAll("\\s\\.", "");
		condition = condition.replaceAll(className + "\\.", "");
		condition = condition.replaceAll("this\\.", "");
		return condition;
	}
	
	public static String insertLineTabs(String line, int numTabs) {
		for (int i = 0; i < numTabs; i++) 
			line = "\t" + line;
		return line;
	}
	
	public static String insertTabs(String code, int numTabs) {
		StringBuffer out = new StringBuffer();
		var lines = code.split("\\n");
		int counter = 0;
		for (int i = 0; i < lines.length; i++) {
			lines[i] = getTabs(numTabs) + lines[i];
			if (counter > 0) {
				out.append("\n");
			}
			out.append(lines[i]);
			counter++;
		}
		return out.toString();
	}
	
	public static String removeAllTabs(String code) {
		while (code.indexOf('\t') != -1) {
			code = code.replace("\t", "");
		}
		return code;
	}
	
	public static String removeTabs(final String code) {
		var lines = Stream.of(code.split("\\n")).toList();
		final StringBuffer buf = new StringBuffer();
		for (var line : lines) {
			line = line.stripLeading();
			if (line.contains("}")) {
				if (line.indexOf("}") + 1 < line.length() && line.charAt(line.indexOf("}") + 1) == ';') {
					buf.append(getTabs(countBrackets(buf.toString(), '{')) + line + "\n");
				} else {
					buf.append(getTabs(countBrackets(buf.toString(), '{') - 1) + line + "\n");
				}
			} else {
				buf.append(getTabs(countBrackets(buf.toString(), '{')) + line + "\n");
			}
		}
		
		return buf.toString();
	}
	
	public static String handlePrimitiveArrayUses(String output, String fullVarName, String val, int numTabs) {
		final Pattern p = Pattern.compile("[^\\s]+" + Pattern.quote(TestAndAssertionGenerator.ARRAY_TOKEN));
		Matcher m = p.matcher(val);
		
		while(m.find()) {
			String helper = val.substring(m.start(), val.length());
			int start = m.start() + m.group(0).indexOf(TestAndAssertionGenerator.ARRAY_TOKEN);
			int end = helper.indexOf(TestAndAssertionGenerator.ARRAY_CLOSED_TOKEN) + m.start();
			String type = m.group(0).substring(0, m.group(0).indexOf(";"));
			String name = m.group(0).substring(m.group(0).indexOf(";") + 1, m.group(0).indexOf(TestAndAssertionGenerator.ARRAY_TOKEN));
			String value = val.substring(start + TestAndAssertionGenerator.ARRAY_TOKEN.length(), end);
			output = "\n" + getTabs(numTabs) + type + " " + name + " = " + value + ";" + output;
			final var toReplace = val.substring(m.start(), end + TestAndAssertionGenerator.ARRAY_CLOSED_TOKEN.length());
			val = val.replace(toReplace, name);
			m = p.matcher(val);
		}
		return output + getTabs(numTabs) + "public " + fullVarName + " = " + val + ";\n";
	}
	
	/**
	 * Inserts checks into *code* that makes sure every precondition contained in *javaCondition* is satisfied by the data in *code*.
	 * Idea:
	 * Invert all conditions in *javaCondition* and use ifs to check if any inverted condition is true. 
	 * If so set an attribute, that notifies testng to skip this test/statement testing.
	 * @param code
	 * @param javaCondition
	 * @return
	 * @throws TestStatementException 
	 */
	public static String insertPreconditionChecks(String code, JavaCondition javaCondition, int numTabs) throws TestStatementException {
		// always put the checks between arrange and act parts
		TranslatedPredicate branch;
		if (!code.contains("\n\n")) {
			return code + PRECHECKS_START + "\n" + PRECHECKS_END;
			//throw new TestStatementException("Couldn't check whether the preconditions of the program are satisfied.");
		}
		// assumes the parts are separated by an empty line
		int pos = code.indexOf("\n\n") + 1;
		String toAppend = code.substring(pos, code.length());
		code = code.substring(0, pos) + CodeHandler.getTabs(numTabs) + PRECHECKS_START + "\n";
			
		while ((branch = javaCondition.getNext()) != null) {
			Branch cur = branch.getNext();
			if (cur == null) continue;
			if (cur.getType() == BranchType.NONE) {
				code += CodeHandler.getTabs(numTabs) + "if(!(" + cur.getBranchCondition() + ")) " + "context.setAttribute(\"skip\", \"" + cur.getBranchCondition() + "\");" + "\n";
			}
		}
		
		return code + CodeHandler.getTabs(numTabs) + PRECHECKS_END + toAppend;
	}
	
	public static String addInstanceNameToFields(ClassHandler classByName, String code) {
		String instanceName = Character.toLowerCase(classByName.getClassName().charAt(0)) 
				+ classByName.getClassName().substring(1, classByName.getClassName().length());
		int offset = 0;
		
		for (var fieldName : classByName.getFields()) {
			var occurences = findOccurences(code, fieldName.getName());
			int o;
			for (int i = 0; i < occurences.size(); i++) {
				o = occurences.get(i) + offset;
				if (isIdentifierDefinition(code, o)) {
					continue;
				}
				code = code.substring(0, o) 
						+ instanceName + "."
						+ code.substring(o, code.length());
				offset += classByName.getClassName().length() + 1;
			}
		}
		return code;
	}
	
	public static boolean isIdentifierDefinition(String code, int identPos) {
		if (identPos < 0 || code.isEmpty()) {
			return false;
		}
		code = code.substring(0, identPos).trim();
		int end = code.length();
		if (end == -1) {
			return false;
		}
		code = code.substring(0, end);
		int typePos = end-1;
		while (Character.isJavaIdentifierPart(code.charAt(typePos)) || Arrays.asList('[', ']').contains(code.charAt(typePos))) {
			typePos--;
		}
		if (typePos == end-1) {
			return false;
		}
		if (Character.isSpace(code.charAt(typePos))) {
			return true;
		}
		return false;
	}

	private static List<Integer> findOccurences(String code, final String word) {
		final List<Integer> indicies = new ArrayList<Integer>();
		String originalCode = code;
		int start = code.indexOf(word);
		int end = start + word.length();
		int offset = 0;
		
		while (start != -1) {
			if (start > 0 && !Character.isJavaIdentifierPart(code.charAt(start-1)) && code.charAt(start-1) != '.') {
				if (end == code.length()) {
					indicies.add(start + offset);
				} else {
					if (!Character.isJavaIdentifierPart(code.charAt(end))) {
						indicies.add(start + offset);
					}
				}
			} else if (start == 0) {
				if (end == code.length()) {
					indicies.add(start + offset);
				} else {
					if (!Character.isJavaIdentifierPart(code.charAt(end))) {
						indicies.add(start + offset);
					}
				}
			}
			code = code.substring(end, code.length());
			start = code.indexOf(word);
			offset = originalCode.length() - code.length();
			end = start + word.length();
		}
		return indicies;
	}
}
