package de.tu_bs.cs.isf.commands.toolbar.handler.family;

import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.cbc.tool.helper.CodeHandler;
import de.tu_bs.cs.isf.cbc.tool.helper.MethodHandler;

public class JavaMethods {
	final List<MethodHandler> methods = new ArrayList<MethodHandler>();
	
	public JavaMethods(List<String> codes) {
		for (var code : codes) {
			int index = code.indexOf("{");

			while (index != -1) {
				String methodCode = CodeHandler.getCurrentBlock(code, index);
				String signature = MethodHandler.getSignatureFromCode(methodCode);
				String contract = getContract(code, signature);
				methodCode = methodCode.substring(methodCode.indexOf("{") + 1, methodCode.lastIndexOf("}"));
				methods.add(new MethodHandler(contract, signature, methodCode));
				code = code.substring(index + 1, code.length());
				index = code.indexOf("{");
			}
		}
	}
	
	public List<MethodHandler> getAll() {
		return this.methods;
	}
	
	private String getContract(String code, String signature) {
		code = code.substring(0, code.indexOf(signature));
		code = code.substring(code.lastIndexOf("/*@"), code.lastIndexOf("*/") + 2);
		return code;
	}
}
