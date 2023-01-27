package de.tu_bs.cs.isf.cbc.tool.helper;

import de.tu_bs.cs.isf.cbc.tool.features.TestAndAssertionGenerator;

public class MethodCode {
	private String signature;
	private String innerCode;
	
	private boolean isSignature(String s) {
		return s.contains("public") | s.contains("protected") | s.contains("private");
	}
	
	public MethodCode(final String signature, String code) {
		this.signature = signature;
		int start = code.indexOf("{");
		if (start != -1 && isSignature(code.substring(0, start))) {
			this.signature = code.substring(0, start).trim();
			int end = Util.findClosingBracketIndex(code, start, '{');
			if (start+1 != end) {
				code = code.substring(start+1, end-1);
			} else {
				code = "";
			}
		}	
		this.innerCode = code.trim();
	}
	
	public static String getSignatureFromCode(String method) {
		method = method.substring(0, method.indexOf('{'));
		method = method.trim();
		return method;
	}
	
	public String getCode() {
		String code = "";
		code += signature + " {\n";
		code += innerCode;
		code += "\n}\n";
		Util.indentCode(code, 0);
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
		final String[] splitter = Util.sSplit(this.signature, "\\s");	
		return splitter[splitter.length - 1].substring(0, splitter[splitter.length - 1].indexOf('('));
	}
}
