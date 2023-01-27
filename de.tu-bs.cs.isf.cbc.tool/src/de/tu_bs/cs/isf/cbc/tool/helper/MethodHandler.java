package de.tu_bs.cs.isf.cbc.tool.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MethodHandler {
	private String signature;
	private String innerCode;
	
	private boolean isSignature(String s) {
		return s.contains("public") | s.contains("protected") | s.contains("private");
	}
	
	public MethodHandler(final String signature, String code) {
		this.signature = signature;
		int start = code.indexOf("{");
		if (start != -1 && isSignature(code.substring(0, start))) {
			this.signature = code.substring(0, start).trim();
			int end = CodeHandler.findClosingBracketIndex(code, start, '{');
			if (start+1 != end) {
				code = code.substring(start+1, end-1);
			} else {
				code = "";
			}
		}	
		this.innerCode = code.trim();
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
	
	public static String getSignatureFromCode(String method) {
		if (method.indexOf('{') == -1) {
			// TODO: This might lead to problems, maybe throw an exception if this happens.
			return method;
		}
		method = method.substring(0, method.indexOf('{'));
		method = method.trim();
		return method;
	}
	
	public static String getMethodNameFromSig(String sig) {
		if (sig.isBlank()) {
			return "";
		}
		final String[] splitter = CodeHandler.sSplit(sig, "\\s");	
		return splitter[splitter.length - 1].substring(0, splitter[splitter.length - 1].indexOf('('));
	}
	
	public static String getBySignature(final String code, final String methodSignature) {
		if (!code.contains(methodSignature)) {
			return "";
		}
		final int startIndex = code.indexOf(methodSignature);
		String methodCode = code.substring(startIndex, code.length());
		int closingBracketIndex = CodeHandler.findClosingBracketIndex(code, startIndex + methodCode.indexOf('{'), '{');
		if (closingBracketIndex == - 1) {
			closingBracketIndex = code.length() - 1;
		}
		methodCode = code.substring(startIndex, closingBracketIndex + 1);
		return methodCode;
	}
	
	public static String methodExists(final String code, final String methodName) {
		int start = code.indexOf(methodName + "(");
		if (start == -1 || code.isEmpty() || methodName.isEmpty()) {
			return "";
		}
		while (start > 0 && --start >= 0 && (Character.isLetter(code.charAt(start)) || Character.isSpaceChar(code.charAt(start))));
		String toCheck = code.substring(start, code.length());
		int end = toCheck.indexOf('{');
		if (end == -1) {
			return "";
		}
		toCheck = toCheck.substring(0, end);
		if (toCheck.contains(";") || toCheck.contains("}")) {
			return "";
		}
		// at this point we know the method is defined here
		int closingBracket = CodeHandler.findClosingBracketIndex(code, end + start, '{');
		if (closingBracket == -1) {
			return "";
		}
		return code.substring(start, closingBracket).trim();
	}
	
	public String getCode() {
		String code = "";
		code += signature + " {\n";
		code += innerCode;
		code += "\n}\n";
		CodeHandler.indentCode(code, 0);
		return code;
	}
	
	public String getInnerCode() {
		return innerCode;
	}
	
	public String getSignature() {
		return signature;
	}
	
	public String getMethodName() {
		if (this.signature.isBlank()) {
			return "";
		}
		final String[] splitter = CodeHandler.sSplit(this.signature, "\\s");	
		return splitter[splitter.length - 1].substring(0, splitter[splitter.length - 1].indexOf('('));
	}
}
