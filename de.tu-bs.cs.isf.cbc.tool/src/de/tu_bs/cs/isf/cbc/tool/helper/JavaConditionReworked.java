package de.tu_bs.cs.isf.cbc.tool.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.tu_bs.cs.isf.cbc.tool.features.NotImplementedException;
import de.tu_bs.cs.isf.cbc.tool.helper.conditionparser.*;
import de.tu_bs.cs.isf.cbc.util.Console;

/**
 * This class can be used to translate conditions given as an AST into java.
 * @author Fynn
 */
public class JavaConditionReworked {
	private int existsNr;
	private String translatedCondition;
	private String curAssertion;
	private List<String> addedPredicateNames = new ArrayList<String>();
	private List<String> conditions = new ArrayList<String>(); 
	private int conditionIndex;
	private int predNr;
	
	public JavaConditionReworked(final Node root) {
		this.translatedCondition = new String();
		this.curAssertion = new String();
		this.existsNr = 0;
		this.predNr = 0;
		this.conditionIndex = 0;
		translateTreeToJava(root);
	}
	
	public String get() {
		return CodeHandler.indentCode(this.translatedCondition, 0);
	}
	
	public String getNextCondition() {
		if (this.conditions.isEmpty()) {
			return "";
		} else {
			conditionIndex = (conditionIndex + 1) % this.conditions.size();
			return this.conditions.get(conditionIndex);
		}
	}
	
	public String getWithContext(int testNumber, final List<TestCaseData> inputs, final String instanceName) {
		String code = this.translatedCondition;
		String unchangedCode = code;
		String helper;
		int offset = 0;
		int start = 0;
		int branchCounter = 0;
		
		while (unchangedCode.contains("if (") || unchangedCode.contains("Assert.assertTrue")) {
			int ifIndex = unchangedCode.indexOf("if (") == -1 ? Integer.MAX_VALUE : unchangedCode.indexOf("if (");
			int assertIndex = unchangedCode.indexOf("Assert") == -1 ? Integer.MAX_VALUE : unchangedCode.indexOf("Assert");
			int end = 0;
			
			if (ifIndex < assertIndex) {
				helper = unchangedCode.substring(ifIndex, unchangedCode.length());
				final String branchString = helper.substring(4, helper.indexOf("\n"));
				final String branchCondition = branchString.substring(0, branchString.lastIndexOf(")"));
				start = ifIndex + helper.indexOf("\n") + 1;
				code = code.substring(0, start + offset)
						+ "context.setAttribute(\"" + testNumber + "branch" + branchCounter + "\", \"" + branchCondition + "\");"
						+ "\n" 
						+ code.substring(start + offset, code.length());
				end = start;
			} else {
				helper = unchangedCode.substring(assertIndex, unchangedCode.length());
				String assertionStr = helper.substring("Assert.assertTrue(".length(), helper.indexOf("\n"));
				String assertion = assertionStr.substring(0, assertionStr.lastIndexOf(")"));
				List<String> varNames = Variable.getVarNames(assertion, inputs.get(0).getInputDataTupel().getGlobalVarNames(), inputs.get(0).getInputDataTupel().getParameterNames(), instanceName);
				start = assertIndex;
				for (var v : varNames) {
					code = code.substring(0, start + offset)
							+ "context.setAttribute(\"" + testNumber + v + "\", " + v + ");"
							+ "\n"
							+ code.substring(start + offset, code.length());
				}
				end = start + "Assert.assertTrue".length(); 
			}
			if (end >= unchangedCode.length()) {
				break;
			}
			unchangedCode = unchangedCode.substring(end, unchangedCode.length());
			offset = Math.abs(code.length() - unchangedCode.length());
			branchCounter++;
		}
		return CodeHandler.indentCode(code, 2);
	}
	
	private String removeIdentifier(String str, String identifier) {
		List<Integer> positions = new ArrayList<Integer>();
		String originalStr = str;
		int offset = 0;
		
		while(str.length() > 0) {
			int start = str.indexOf(identifier);
			if (start == -1) {
				break;
			}
			int end = start + identifier.length();
			if (start > 0 && Character.isAlphabetic(str.charAt(start-1))) {
				str = str.substring(end, str.length());
				offset += end+1;
				continue;
			}
			if (end < str.length() && Character.isAlphabetic(str.charAt(end))) {
				str = str.substring(end, str.length());
				offset += end+1;
				continue;
			}
			positions.add(start+offset);
			if (end >= str.length()) {
				break;
			}
			str = str.substring(end, str.length());
		}
		
		for (var i : positions) {
			originalStr = originalStr.substring(0, i) 
					+ originalStr.substring(i + identifier.length(), originalStr.length());
		}
		
		return originalStr;
	}
	
	private String getIterValue(String condition) {
		condition = condition.replaceAll("<", "");
		condition = condition.replaceAll("\\s", "");
		condition = condition.replaceAll(">", "");
		condition = condition.replaceAll("=", "");
		condition = condition.replaceAll("\\(", "");
		condition = condition.replaceAll("\\)", "");
		return condition;
	}
	
	private String translateForAll(Node root) {
		String condition = "";
		final var quantor = (QuantorNode)root;
		final String firstCondition = removeIdentifier(quantor.getConditions().getLeft().getRep(), quantor.getIterator());
		final String initialValue = getIterValue(firstCondition);
		final String secondCondition = removeIdentifier(quantor.getConditions().getRight().getLeft().getRep(), quantor.getIterator());
		final String lastValue = getIterValue(secondCondition);
		final String name = getPredicateName();
		
		condition += "for (" + quantor.getIteratorType() + " " + quantor.getIterator() + " = " 
				+ initialValue + "; " + quantor.getIterator() + " < " + lastValue + "; "
				+ quantor.getIterator() + "++" + ") " + "{\n";
		condition += "Supplier<Boolean> " + name + " = () -> {\n";
		condition += translateTreeToJava(quantor.getConditions().getRight().getRight());
		condition += "return true;\n";
		condition += "};\n";
		condition += "Assert.assertTrue(" + name + ".get());\n";
		condition += "}\n";
		translatedCondition = condition;
		return condition;
	}
	
	// TODO: Test this.
	private String replaceAssertWithIf(String condition, String existsVar) {
		String helper;
		String target = "Assert.assertTrue";
		int start = condition.indexOf(target);
		while (start != -1) {
			helper = condition.substring(start, condition.length());
			int end = helper.indexOf(";\n") + start;
			var jsdjf = condition.substring(start, end);
			condition = condition.substring(0, start) 
					+ "if " + condition.substring(start + target.length(), end) + " {\n" 
					+ existsVar 
					+ " = true;\n}\n"
					+ condition.substring(end + 2, condition.length());
			start = condition.indexOf(target);
		}
		return condition;
	}	
		
	private String translateExists(Node root) {
		this.existsNr++;
		int curExists = this.existsNr;
		String condition = "";
		final var quantor = (QuantorNode)root;
		final String firstCondition = removeIdentifier(quantor.getConditions().getLeft().getRep(), quantor.getIterator());
		final String initialValue = getIterValue(firstCondition);
		final String secondCondition = removeIdentifier(quantor.getConditions().getRight().getLeft().getRep(), quantor.getIterator());
		final String lastValue = getIterValue(secondCondition);
		condition += "boolean exists" + curExists + " = false;\n";
		condition += "for (" + quantor.getIteratorType() + " " + quantor.getIterator() + " = " 
				+ initialValue + "; " + quantor.getIterator() + " < " + lastValue + "; "
				+ quantor.getIterator() + "++" + ") " + "{\n";
		String innerCondition = translateTreeToJava(quantor.getConditions().getRight().getRight());
		innerCondition = this.replaceAssertWithIf(innerCondition, "exists" + curExists);
		condition += innerCondition;
		condition += "}\n";
		condition += "Assert.assertTrue(exists" + curExists + ");\n";
		condition += "exists" + curExists + " = false;\n";
		translatedCondition = condition;
		return condition;
	}
	
	private String translateQuantor(Node root) {
		int forAllIndex = root.getRep().indexOf("forall") == -1 ? Integer.MAX_VALUE : root.getRep().indexOf("forall");
		int existsIndex = root.getRep().indexOf("exists") == -1 ? Integer.MAX_VALUE : root.getRep().indexOf("exists");
		if (forAllIndex < existsIndex) {
			return translateForAll(root);
		} else {
			return translateExists(root);
		}
	}
	
	private String translateImpl(Node root) {
		String condition = "";
		condition += "if (" + root.getLeft().getRep() + ") {\n";
		condition += translateTreeToJava(root.getRight());
		condition += "}\n";
		translatedCondition = condition;
		return condition;
	}
	
	private String translateEqui(Node root) {
		String condition = "";
		condition += "if (" + root.getLeft().getRep() + ") {\n";
		condition += translateTreeToJava(root.getRight());
		condition += "}\n";
		translatedCondition = condition;
		return condition;
	}
	
	private String translateOr(Node root) {
		String condition = "";
		String name1 = getPredicateName();
		condition += "Supplier<Boolean> " + name1 + " = () -> {\n";
		condition += translateTreeToJava(root.getLeft());
		condition += "return true;\n";
		condition += "};\n";
		String name2 = getPredicateName();
		condition += "Supplier<Boolean> " + name2 + " = () -> {\n";
		condition += translateTreeToJava(root.getRight());
		condition += "return true;\n";
		condition += "};\n";
		condition += "Assert.assertTrue(" + name1 + ".get() || " + name2 + ".get());\n";
		translatedCondition = condition;
		return condition;
	}
	
	private String getPredicateName() {
		//var name = this.generatePredicateName();
		final var name = "predicate" + this.predNr;
		this.predNr++;
		return name;
	}
	
	private String translateAnd(Node root) {
		String condition = "";
		String name1 = getPredicateName();
		condition += "Supplier<Boolean> " + name1 + " = () -> {\n";
		condition += translateTreeToJava(root.getLeft());
		condition += "return true;\n";
		condition += "};\n";
		String name2 = getPredicateName();
		condition += "Supplier<Boolean> " + name2 + " = () -> {\n";
		condition += translateTreeToJava(root.getRight());
		condition += "return true;\n";
		condition += "};\n";
		condition += "Assert.assertTrue(" + name1 + ".get() && " + name2 + ".get());\n";
		translatedCondition = condition;
		return condition;
	}
	
	private String translateDefault(Node root) {
		String condition = "";
		condition += "Assert.assertTrue(" + root.getRep() + ");\n";
		this.curAssertion = root.getRep();
		this.conditions.add(root.getRep());
		translatedCondition = condition;
		return condition;
	}
	
	private String translateTreeToJava(final Node root) {
		if (root.getType() == TokenType.KEY) {
			return translateQuantor(root);
		} else if (root.getType() == TokenType.IMPL) {
			return translateImpl(root);
		} else if (root.getType() == TokenType.EQUI) {
			return translateEqui(root);
		} else if (root.getType() == TokenType.OR) {
			return translateOr(root);
		} else if (root.getType() == TokenType.AND) {
			return translateAnd(root);
		} else {
			return translateDefault(root);
		}
	}
	
	private String generatePredicateName() {
		final StringBuffer name = new StringBuffer("");
		this.curAssertion.chars().forEach(c -> {
			if (Character.isJavaIdentifierPart(c)) {
				name.append((char)c);
			} else if (c == '<') {
				name.append("LT");
			} else if (c == '>') {
				name.append("GT");
			} else if (c == '=') {
				name.append("EQ");
			} else if (c == '!') {
				name.append("NOT");
			} else if (c == '.') {
				name.append("DOT"); 
			} else {
			}
		});
		this.addedPredicateNames.add(name.toString());
		return name.toString();
	}
}
