package de.tu_bs.cs.isf.commands.toolbar.handler.family;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;

import de.tu_bs.cs.isf.cbc.util.CodeHandler;
import de.tu_bs.cs.isf.cbc.util.consts.MetaNames;

public class MetaVariablesClass {
	public static final String NAME = "MetaVariables";
	private String projectLocation;
	private String[] featureVars;
	private String code;

	public MetaVariablesClass(String projectLocation, String[] featureVars) {
		this.projectLocation = projectLocation;
		this.featureVars = featureVars;
		this.code = "";
		generate();
	}

	public String getCode() {
		return this.code;
	}

	public void saveToFile() throws IOException, CoreException {
		String location = this.projectLocation + "/" + MetaNames.FOLDER_NAME + "/MetaVariables.java";
		GenerateMetaProductHandler.saveJavaFile(location, this.code);
	}

	private void generate() {
		code += "public class " + NAME + " {\n";
		code += generateFields();
		code += "}";
		code = CodeHandler.indentCode(code, 0);
	}

	private String generateFields() {
		String fields = "";
		for (var field : featureVars) {
			field = getIdentifier(field);
			fields += "public static boolean " + field + ";\n";
		}
		return fields;
	}

	private String getIdentifier(String field) {
		if (field.contains(".")) {
			var splitted = field.split("\\.");
			field = splitted[splitted.length - 1];
		}
		return field;
	}
}
