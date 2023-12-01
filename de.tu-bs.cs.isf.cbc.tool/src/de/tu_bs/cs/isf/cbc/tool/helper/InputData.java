package de.tu_bs.cs.isf.cbc.tool.helper;

import java.util.Random;

import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.exceptions.SettingsException;
import de.tu_bs.cs.isf.cbc.tool.propertiesview.TestValues;

/**
 * Class for handling input data for methods.
 * @author Fynn
 */
public class InputData {
	private String arrayVarName;
	private String[] values;
	/**
	 * Variable *rep* represent the last representation of a class implementing this. This means if there
	 * was a call to *getRandomValue()* at the latest, then it will be just the string representation of
	 * that value. If the last call was *getArrayRep()* then it will be a string representation of 
	 * that array.
	 */
	private String rep;
	private String arrayRep;
	private String primitiveArrayInit;
	private String classTypeName;
	private int dimensions;
	
	public InputData(String variableName, String type) throws SettingsException {
		this.arrayVarName = variableName;
		this.dimensions = (int)type.chars().filter(c -> c == '[').count();
		type = type.replaceAll("\\[[^\\]]*\\]", "");
		this.classTypeName = type;
		setDefaultValues();
	}
	
	public InputData(String variableName, String type, String[] defaultValues) throws SettingsException {
		this.arrayVarName = variableName;
		this.values = defaultValues;
		this.rep = defaultValues[0] + "";	
		this.dimensions = (int)type.chars().filter(c -> c == '[').count();
		type = type.replaceAll("\\[[^\\]]*\\]", "");
		this.classTypeName = type;
		setDefaultValues();
	}
	
	public boolean isPrimitive() {
		return Character.isLowerCase(classTypeName.charAt(0));
	}
	
	public boolean isArray() {
		return this.dimensions > 0;
	}
	
	public String getName() {
		return this.arrayVarName;
	}
	
	public int getDimensions() {
		return this.dimensions;
	}
		
	public String getType() {
		var output = classTypeName;
		for (int i = 0; i < dimensions; i++) {
			output += "[]";
		}
		return output;
	}
	
	public String getRep() {
		return this.rep;
	}
	
	public String getArrayRep() {
		genArrayRep();
		genStructure();
		this.rep = arrayRep;
		return arrayRep;
	}
	
	public String getPrimitiveArrayName() {
		return arrayVarName;
	}
	
	public String getPrimitiveArrayInit() {
		genPrimitiveArrayInit(this.dimensions);
		return primitiveArrayInit;
	}
	
	public String getArrayValueRep() {
		genArrayRep();
		return this.arrayRep;
	}
	
	public String getRandomValue() {
		if (this.values.length == 0) {
			//Console.println("InputDataInfo: Variable " + getName() + " has no values.");
			return "";
		}
		final Random rng = new Random();
		int rngIndex = rng.nextInt(this.values.length);
		this.rep = values[rngIndex] + "";
		return values[rngIndex];
	}
	
	public String getValue(int position) {
		return values[position];
	}
	
	public String[] getValues() {
		return this.values;
	}
	
	public void setValues(String[] values) {
		this.values = values;
	}
	
	private void genArrayRep() {
		//shuffleArray(this.values);
		if (this.values.length == 0) {
			rep = "";
			return;
		}
		rep = this.values[0];
		arrayRep = "{" + rep;
		for (int i = 1; i < values.length; i++) {
			arrayRep += ", " + this.values[i];
		}
		arrayRep += "}";
		if (this.dimensions > 1) {
			arrayRep = arrayRep + ", " + arrayRep;
		}
		for (int i = 0; i < this.dimensions - 1; i++) {
			arrayRep = "{" + arrayRep;
			arrayRep += "}";
		}
	}
	
	private void genStructure() {
		String structure = "new " + classTypeName;
		for (int i = 0; i < this.dimensions; i++) {
			structure += "[]";
		}
		arrayRep = structure + arrayRep;
	}
	
	public static String getDefaultValue(String type) throws SettingsException {
		if (type.contains("[")) {
			final InputData data = new InputData("", type);
			data.setDefaultValues();
			return data.getArrayRep();
		}
		switch(type) {
			case "byte":
				return "" + 0;
			case "short":
				return "" + 0;
			case "int":
				return "" + 0;
			case "integer":
				return "" + 0;
			case "long":
				return "" + 0;
			case "char":
				return "" + 0;
			case "character":
				return "x";
			case "boolean":
				return "false";
			case "string":
				return "";
			default:
				return "new " + type + "()";
		}		
	}
	
	public static boolean isBuiltInType(String type) {
		if (type.contains("[")) {
			type = type.substring(0, type.indexOf("["));
		}
		type = type.toLowerCase();
		switch(type) {
			case "byte":
			case "short":
			case "int":
			case "integer":
			case "long":
			case "char":
			case "character":
			case "boolean":
			case "string":
				return true;
			default:
				return false;
		}
	}
	
	public void setDefaultValues() throws SettingsException {
		var vals = TestValues.get();
		switch(classTypeName.toLowerCase()) {
			case "byte":
				this.values = vals.getValues(vals.getByteStr());
				break;
			case "short":
				this.values = vals.getValues(vals.getShortStr());
				break;
			case "int":
				this.values = vals.getValues(vals.getIntStr());
				break;
			case "integer":
				this.values = vals.getValues(vals.getIntStr());
				break;
			case "long":
				this.values = vals.getValues(vals.getLongStr());
				break;
			case "char":
				this.values = vals.getValues(vals.getCharStr());
				break;
			case "character":
				this.values = vals.getValues(vals.getCharStr());
				break;
			case "boolean":
				this.values = vals.getValues(vals.getBooleanStr());
				break;
			case "string":
				this.values = vals.getValues(vals.getStringStr());
		}
		if (this.values == null) {
			this.values = new String[]{"new " + this.classTypeName + "()"};
		}
		
	}
	
	private String insertValueLen(String brackets) {
		int start = brackets.indexOf("[]");
		if (start == -1) {
			return brackets;
		}
		brackets = brackets.substring(0, start + 1) + this.values.length + brackets.substring(start + 1, brackets.length());
		return insertValueLen(brackets);
	}
	
	private void genPrimitiveArrayInit(int dimension) {
		String primitiveType = "";
		genArrayRep();
		primitiveArrayInit = this.classTypeName;
		String brackets = "";
		for (int i = 0; i < dimension; i++) {
			brackets += "[]";
		}
		primitiveArrayInit += brackets;
		if (arrayRep != null) {
			primitiveArrayInit += " " + arrayVarName + " = " + "new " + this.classTypeName + brackets + arrayRep;
		} else {
			brackets = insertValueLen(brackets);
			primitiveArrayInit += " " + arrayVarName + " = " + "new " + this.classTypeName + brackets;  //+ arrayRep;
		}
		this.rep = arrayVarName;
	}

	public void setRep(String newRep) {
		this.rep = newRep;
	}
}