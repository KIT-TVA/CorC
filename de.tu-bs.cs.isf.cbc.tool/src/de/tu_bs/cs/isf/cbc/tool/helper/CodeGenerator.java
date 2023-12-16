package de.tu_bs.cs.isf.cbc.tool.helper;

import java.io.File;
import java.util.LinkedList;

import org.eclipse.emf.common.util.URI;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import de.tu_bs.cs.isf.cbc.cbcclass.Field;
import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.cbcmodel.VariableKind;
import de.tu_bs.cs.isf.cbc.exceptions.CodeGeneratorException;
import de.tu_bs.cs.isf.cbc.util.ConstructCodeBlock;
import de.tu_bs.cs.isf.cbc.util.FileUtil;

public class CodeGenerator {
	private static CodeGenerator instance;

	private CodeGenerator() {
		
	}
	
	public static CodeGenerator getInstance() {
		if (instance == null) {
			instance = new CodeGenerator();
		}
		return instance;
	}
	
	public String generateCodeFor(Diagram diag) throws CodeGeneratorException {
		DiagramPartsExtractor extractor = new DiagramPartsExtractor(diag);
		JavaVariables vars = extractor.getVars();
		GlobalConditions globalConditions = extractor.getConds();
		Renaming renaming = extractor.getRenaming();
		CbCFormula formula = extractor.getFormula();
		
		String signatureString = formula.getMethodObj() != null ? formula.getMethodObj().getSignature() : ("public void " + formula.getName().toLowerCase() + " ()");
		
		JavaVariable returnVariable = null;
		int counter = 0;
		LinkedList<String> localVariables = new LinkedList<String>();
		for(int i = 0; i < vars.getVariables().size(); i++) {
			JavaVariable currentVariable = vars.getVariables().get(i);
		if(currentVariable.getKind() == VariableKind.RETURN) {
			counter++;
			if(!signatureString.substring(0, signatureString.indexOf('(')).contains(currentVariable.getName().replace("non-null", "").trim().split(" ")[0])) {
				//throw new CodeGeneratorException("Method return type and variable type does not match.");
			}
			if(counter > 1) {
				//throw new CodeGeneratorException("Too much variables of kind RETURN.");
			}
			returnVariable = currentVariable;
		}else if(currentVariable.getKind() == VariableKind.LOCAL) {
			localVariables.add(currentVariable.getName().replace("non-null", ""));
		}
		}
		String globalVariables ="";
		for (Field field : vars.getFields()) {
			globalVariables += ("\t" + field.getVisibility().getName().toLowerCase() + " /*@spec_public@*/ " + field.getType() + " " + field.getName().replace("non-null ", "") + ";\n");
		}
		
		URI uri = diag.eResource().getURI();
		String location = FileUtil.getProjectLocation(uri);
		location += "/code-gen" + File.separator + (formula.getClassName().equals("") ? ("Class" + formula.getName()) : formula.getClassName()) + ".java";
		String code = ConstructCodeBlock.constructCodeBlockForExport(formula, globalConditions, renaming, localVariables, returnVariable, signatureString);
		return code;
	}

}
