package de.tu_bs.cs.isf.cbc.tool.helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.tool.features.TestAndAssertionGenerator;
import de.tu_bs.cs.isf.cbc.tool.features.TestStatementException;
import de.tu_bs.cs.isf.cbc.util.FileUtil;

/**
 * Util class for testing purposes.
 * @author Fynn
 */
public class Util {
	private static URI projectPath = null;
	public static final String PRECHECKS_START = "//[checks]";
	public static final String PRECHECKS_END = "//[end_checks]";
	
	public static void clearLog(final URI projectPath) {
		Util.projectPath = projectPath;
		var logFile = new File(FileUtil.getProjectLocation(projectPath) + "\\tests\\log.txt");
		if (logFile.exists()) {
			logFile.delete();
		} 
	}
	
	public static void log(final URI projectPath, String text) {
		Util.projectPath = projectPath;
		try {
			var logFile = new File(FileUtil.getProjectLocation(projectPath) + "\\tests\\log.txt");
			if (!logFile.exists()) {
				logFile.createNewFile();
			} 
			if (text.charAt(text.length()-1) != '\n') text += "\n";
			Files.write(Paths.get(logFile.getPath()), text.getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
		}
	}
	
	public static void log(String text) {	
		if (projectPath == null) {
			return;
		}
		try {
			var logFile = new File(FileUtil.getProjectLocation(Util.projectPath) + "\\tests\\log.txt");
			if (!logFile.exists()) {
				logFile.createNewFile();
			} 
			if (text.charAt(text.length()-1) != '\n') text += "\n";
			Files.write(Paths.get(logFile.getPath()), text.getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
		}
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
	
	/**
	 * Gets the signature of method *methodName* contained in *code*.
	 * @param code The code.
	 * @param methodName Name of the method to find.
	 * @return The signature when found, else empty string.
	 */
	public static String getMethodSignature(String code, String methodName) {
		final Pattern p = Pattern.compile("\\W" + methodName + "\\(");
		final Matcher m = p.matcher(code);
		String helper;
		int startIndex;

		
		while (m.find()) {
			helper = code.substring(0, m.start() + 1);
			startIndex = helper.lastIndexOf('\n') + 1;
			helper = code.substring(startIndex, code.length());
			int endIndex = startIndex + helper.indexOf(")") + 1;
			for (int i = endIndex; i < code.length(); i++) {
				if (code.charAt(i) == '{') {
					return code.substring(startIndex, endIndex).trim();
				} else if (code.charAt(i) == ' ') 
				{
					// empty because whitespaces are valid therefore we will move on in the search for a {-bracket.				
				} else {
					break;
				}		
			}
		}
		return "";
	}
	
	public static List<String> getVarNames(final String assertion, final List<String> globalVarNames, final List<String> parameterVarNames, final String instanceName) {
		var output = new ArrayList<String>();
		
		for (var v : globalVarNames) {
			if (assertion.contains(v)) {
				output.add(instanceName + "." + v);
			}
		}
		
		for (var v : parameterVarNames) {
			if (assertion.contains(v)) {
				output.add(v);
			}
		}
		return output;
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
			throw new TestStatementException("Couldn't check whether the preconditions of the program are satisfied.");
		}
		// assumes the parts are separated by an empty line
		int pos = code.indexOf("\n\n") + 1;
		String toAppend = code.substring(pos, code.length());
		code = code.substring(0, pos) + Util.getTabs(numTabs) + PRECHECKS_START + "\n";
			
		while ((branch = javaCondition.getNext()) != null) {
			Branch cur = branch.getNext();
			if (cur == null) continue;
			if (cur.getType() == BranchType.NONE) {
				code += Util.getTabs(numTabs) + "if(!(" + cur.getBranchCondition() + ")) " + "context.setAttribute(\"skip\", \"" + cur.getBranchCondition() + "\");" + "\n";
			}
		}
		
		return code + Util.getTabs(numTabs) + PRECHECKS_END + toAppend;
	}
	
	public static Diagram loadDiagramFromClass(final URI projectPath, final String folderName, final String className, final String diagramName) {
		if (className.isBlank() || diagramName.isBlank()) {
			return null;
		}
		final ResourceSet rSet = new ResourceSetImpl();
		final IContainer folder = FileUtil.getProject(projectPath).getFolder(folderName);
		if (folder == null) {
			return null;
		}
		final IFile file = FileUtil.getProject(projectPath).getFolder("src/" + className).getFile(diagramName + ".diagram");
		if (file == null) {
			return null;
		}
		return GetDiagramUtil.getDiagramFromFile(file, rSet);
	}
	
	/**
	 * Returns a string of all preconditions in JavaDL syntax or empty string, when there is no precondition.
	 * @param globalConditions
	 * @param formula
	 * @return
	 */
	public static String parseConditions(final GlobalConditions globalConditions, final Condition preCondition) {
		// By definition we know that the root formula does contain the strongest precondition,
		// therefore we only need to parse it's preconditions as any following refinement
		// can't have more (=stronger) requirements.
		var preCon = preCondition.getName().trim();
		if (preCon.equals("true")) {
			preCon = "";
		}
		// add invariants 
		List<String> invariants = globalConditions == null ? new ArrayList<String>() : globalConditions.getConditions().stream()
				.map(c -> c.getName())
				.toList();
		String invariantsStr = "";
		// non-null conditions can be discarded because the generator always generates values for complex data types.
		final Pattern p = Pattern.compile("[A-Z]\\w*\\.\\w+");
		int counter = 0;
		for (int i = 0; i < invariants.size(); i++) {
			if (!invariants.get(i).contains("null") && !invariants.get(i).contains("self")) {
				Matcher m = p.matcher(invariants.get(i));
				String con = invariants.get(i);
				while (m.find()) {
					return "";
					
					//con = con.substring(0, m.start()) + con.substring(m.start() + m.group(0).indexOf('.') + 1, con.length());
				}
				if (counter > 0) {
					invariantsStr += " & " + con;
				} else {
					invariantsStr += con;
				}
				counter++;
			}
		}
		//invariantsStr = invariantsStr.replaceAll("this\\.", "");
		if (preCon.isEmpty()) {
			return invariantsStr;
		} else {	
			if (invariantsStr.isEmpty()) {
				return preCon;
			} else {
				return preCon + " & " + invariantsStr;
			}
		}
	}
	
	public static String getTabs(long num) {
		var out = "";
		for (int i = 0; i < num; i++) out+="\t";
		return out;
	}
	
	public static String getMethodCode(final String code, final String methodSignature) {
		if (!code.contains(methodSignature)) {
			return "";
		}
		final int startIndex = code.indexOf(methodSignature);
		String methodCode = code.substring(startIndex, code.length());
		int closingBracketIndex = findClosingBracketIndex(code, startIndex + methodCode.indexOf('{'), '{');
		if (closingBracketIndex == - 1) {
			closingBracketIndex = code.length() - 1;
		}
		methodCode = code.substring(startIndex, closingBracketIndex + 1);
		return methodCode;
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
	
	public static String getMethodNameFromSig(String sig) {
		if (sig.isBlank()) {
			return "";
		}
		final String[] splitter = sSplit(sig, "\\s");	
		return splitter[splitter.length - 1].substring(0, splitter[splitter.length - 1].indexOf('('));
	}
	
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
	
	public static boolean writeToFile(String location, String className, String content) {
		try {
			var dir = new File(location);	
			if (!dir.exists()) {
				dir.mkdirs();
			}
			var javaFile = new File(location + "\\" + className + ".java");
			if (!javaFile.exists()) {
				javaFile.createNewFile();
			} 
			Files.write(Paths.get(javaFile.getPath()), new byte[] {}, StandardOpenOption.TRUNCATE_EXISTING);
			Files.write(Paths.get(javaFile.getPath()), content.getBytes(), StandardOpenOption.WRITE);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
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
			output = "\n" + Util.getTabs(numTabs) + type + " " + name + " = " + value + ";" + output;
			final var toReplace = val.substring(m.start(), end + TestAndAssertionGenerator.ARRAY_CLOSED_TOKEN.length());
			val = val.replace(toReplace, name);
			m = p.matcher(val);
		}
		return output + Util.getTabs(numTabs) + "public " + fullVarName + " = " + val + ";\n";
	}
	
	public static boolean createFile(final URI projectPath, String className, String code) {
		try {
			var dir = new File(FileUtil.getProjectLocation(projectPath) + "\\tests");	
			if (!dir.exists()) {
				dir.mkdirs();
			}
			var javaFile = new File(FileUtil.getProjectLocation(projectPath) + "\\tests\\" + className + ".java");
			if (!javaFile.exists()){
				javaFile.createNewFile();
		    }
			writeToFile(projectPath, className, code);
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	public static boolean deleteFile(final URI projectPath, String className) {
		try {
			var dir = new File(FileUtil.getProjectLocation(projectPath) + "\\tests");	
			if (!dir.exists()) {
				return false;
			}
			var javaFile = new File(FileUtil.getProjectLocation(projectPath) + "\\tests\\" + className + ".java");
			if (!javaFile.exists()){
				return false;
		    }
			Files.delete(Paths.get(javaFile.getPath()));
			javaFile = new File(FileUtil.getProjectLocation(projectPath) + "\\tests\\" + className + ".class");
			if (javaFile.exists()){
				Files.delete(Paths.get(javaFile.getPath()));
		    }
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	public static boolean writeToFile(final URI projectPath, final String className, String content) {
		writeToFile(FileUtil.getProjectLocation(projectPath) + "\\tests", className, content);
		return true;
	}
}
