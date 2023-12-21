package de.tu_bs.cs.isf.commands.toolbar.handler.family;

import java.util.List;

import de.tu_bs.cs.isf.cbc.util.CodeHandler;

public class CodeMerge {
	private String className;
	private String mergedCode;
	private final List<String> codes;
	
	public CodeMerge(List<String> codes) throws CodeMergeException {
		this.codes = codes;
		this.mergedCode = "";
		this.className = "";
		merge();
	}
	
	public String get() {
		return this.mergedCode;
	}
	
	private void merge() throws CodeMergeException {
		removeCodeClassLayers();
		addMergedClassLayer();
		String header = mergeHeaders();
		String methods = mergeMethods();
		mergedCode += header + methods + "}";
		mergedCode = CodeHandler.removeAllTabs(mergedCode);
		mergedCode = CodeHandler.indentCode(mergedCode, 0);
	}
	
	private void removeCodeClassLayers() throws CodeMergeException {
		for (int i = 0; i < codes.size(); i++) {
			if (!codes.get(i).contains(" class ")) {
				continue;
			}
			String curName = getClassNameFromCode(codes.get(i));
			setClassName(curName);
			codes.set(i, removeClassDefinition(codes.get(i)));
		}
	}
	
	private String getClassNameFromCode(String code) {
			String curName;
			curName = code.substring(code.indexOf("class ") + "class ".length(), code.indexOf("{"));
			curName = curName.substring(0, curName.indexOf(" ")).trim();
			return curName;
	}
	
	private void setClassName(String curName) throws CodeMergeException {
			if (className.isEmpty()) {
				className = curName;
				return;
			} 
			if (!curName.equals(className)) {
				throw new CodeMergeException("Tried merging code of different classes.");
			}
	}
	
	private String removeClassDefinition(String code) {
		return code.substring(code.indexOf("{") + 1, code.lastIndexOf("}"));
	}
	
	private void addMergedClassLayer() {
		this.mergedCode += "public class " + this.className + " {\n";
	}
	
	private String mergeHeaders() {
		String mergedHeaders = "";
		for (var code : codes) {
			var header = getHeader(code);
			mergedHeaders = addHeader(mergedHeaders, header);
		}
		return mergedHeaders;
	}
	
	private String[] getHeader(String code) {
		code = code.trim();
		var lines = code.split(";");
		int headerEnd = 0;
		while (!lines[headerEnd++].contains("normal_behavior"));
		if (--headerEnd == 0) {
			return new String[0];
		}
		String[] header = new String[headerEnd]; 
		for (int i = 0; i < headerEnd; i++) {
			if (lines[i].contains("@")) {
				header[i] = "/*@ " + removeComment(lines[i]) + "; @*/";
			} else {
				header[i] = lines[i].trim() + ";";
			}
		}
		return header;
	}
	
	private String addHeader(String mergedHeaders, String[] header) {
		for (var line : header) {
			if (!mergedHeaders.contains(line)) {
				mergedHeaders += line + "\n";
			}
		}
		return mergedHeaders + "\n";
	}
	
	private String removeComment(String line) {
		line = line.replaceAll("\\/|\\*|\\@", "");
		return line.trim();
	}
	
	private String mergeMethods() {
		String mergedMethods = "";
		var methods = new JavaMethods(this.codes);
		for (var m : methods.getAll()) {
			if(!mergedMethods.contains(m.getSignature())) {
				mergedMethods += m.getContract() + "\n" + m.getCode() + "\n";
			}
		}
		return mergedMethods;
	}
}
