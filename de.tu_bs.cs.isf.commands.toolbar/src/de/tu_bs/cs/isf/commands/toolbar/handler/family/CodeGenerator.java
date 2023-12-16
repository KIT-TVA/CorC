package de.tu_bs.cs.isf.commands.toolbar.handler.family;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.resource.Resource;

import de.tu_bs.cs.isf.cbc.cbcclass.Field;
import de.tu_bs.cs.isf.cbc.cbcclass.ModelClass;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.tool.helper.CodeHandler;
import de.tu_bs.cs.isf.cbc.util.ConstructCodeBlock;
import de.tu_bs.cs.isf.cbc.util.Parser;
import de.tu_bs.cs.isf.cbc.util.FileUtil;

public class CodeGenerator {
	private final CbCFormula formula;
	private final ModelClass cbcClass;
	
	public CodeGenerator(IProject project, CbCFormula methodFormula) throws CodeGeneratorException {
		this.formula = methodFormula;
		this.cbcClass = getMetaCbcClass(FileUtil.getCbCClasses(project), formula.getClassName());
	}
	
	public String generate() {
		String code;
		code = generateClass();
		code = generateFields(code);
		code = generateInvariants(code);
		code = generateMethodStub(code);
		return CodeHandler.indentCode(code + "\n}", 0);
	}

	private ModelClass getMetaCbcClass(Collection<Resource> classes, String className) throws CodeGeneratorException {
		for (var c : classes) {
			ModelClass modelClass = ((ModelClass)c.getContents().get(0));
			if (!c.getURI().toFileString().contains(MetaClass.FOLDER_NAME)) {
				continue;
			}
			if (modelClass.getName().equals(className) && hasTargetMethod(modelClass)) {
				return modelClass;
			}
		}
		throw new CodeGeneratorException("Couldn't locate a cbc class that contains the method " + formula.getName() + ".");
	}
	
	private boolean hasTargetMethod(ModelClass modelClass) {
		for (var m : modelClass.getMethods()) {
			if (m.getName().equals(formula.getName())) {
				formula.setMethodObj(m);
				return true;
			}
		}
		return false;
	}
	
	private String generateClass() {
		return "public class " + cbcClass.getName() + " {\n";
	}
	
	private String generateFields(String code) {
		for (var field : cbcClass.getFields()) {
			code += fieldToStr(field);
			code += "\n";
		}
		return code + "\n";
	}
	
	private String fieldToStr(Field field) {
		return field.getVisibility().toString().toLowerCase() + " " 
					+ (field.isIsStatic() ? "static " : "") 
					+ field.getType() + " "
					+ field.getName() + ";";
	}
	
	private String generateInvariants(String code) {
		for (var c : cbcClass.getClassInvariants()) {
			code += "/*@ invariant " + c.getName() + "; @*/\n";
		}
		return code + "\n";
	}
	
	private String generateMethodStub(String code) {
		String jmlContract = generateJmlContract();
		code += jmlContract + "\n";
		code += formula.getMethodObj().getSignature() + " {}\n\n";
		return code;
	}
	
	private String generateJmlContract() {
		String preCondition = ConstructCodeBlock.createConditionJMLString(formula.getStatement()
				.getPreCondition().getName(), null, Parser.KEYWORD_JML_PRE);
		String postCondition = ConstructCodeBlock.createConditionJMLString(formula.getStatement()
				.getPostCondition().getName(), null, Parser.KEYWORD_JML_POST);
		String modStr = generateModifiables();
		String assignable = "@ assignable " + modStr + ";\n";
		String jmlContract = "/*@\n" +
							"@ normal_behavior\n" +
							preCondition +
							postCondition +
							assignable +
							"*/";
		return jmlContract;
	}
	
	private String generateModifiables() {
		String modStr = "";
		for (var mod : formula.getStatement().getPostCondition().getModifiables()) {
			String name = getVarName(mod);
			modStr = addIfField(modStr, name);
			modStr = addIfParam(modStr, name);
		}
		modStr = cleanModVarStr(modStr);
		return modStr;
	}
	
	private String getVarName(String varStr) {
		var name = varStr;
		if (name.contains("[")) {
			name = name.substring(0, name.indexOf("["));
		}
		return name;
	}
	
	private String addIfField(String modStr, String name) {
		if (modStr.contains(name)) {
			return modStr;
		}
		for (var field : this.cbcClass.getFields()) {
			if (field.getName().equals(name)) {
				modStr += name + ", ";
			}
		}
		return modStr;
	}
	
	private String addIfParam(String modStr, String name) {
		if (modStr.contains(name)) {
			return modStr;
		}
		for (var m : this.cbcClass.getMethods()) {
			if (m.getName().equals(formula.getMethodName())) {
				for (var param : m.getParameters()) {
					if (param.getName().equals(name)) {
						modStr += name + ", ";
					}
				}
			}
		}
		return modStr;
	}
	
	private String cleanModVarStr(String modStr) {
		if (modStr.length() > 2) {
			modStr = modStr.substring(0, modStr.length()-2);
		} else {
			modStr = "\\nothing";
		}
		return modStr;
	}
}
