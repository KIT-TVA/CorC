package de.tu_bs.cs.isf.cbc.util;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import de.tu_bs.cs.isf.cbc.cbcclass.Field;
import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.VariableKind;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.AbstractStatementImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.CompositionStatementImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.MethodStatementImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.OriginalStatementImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.ReturnStatementImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.SelectionStatementImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.SkipStatementImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.SmallRepetitionStatementImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.StrengthWeakStatementImpl;
import de.tu_bs.cs.isf.cbc.exceptions.CodeGeneratorException;
import de.tu_bs.cs.isf.cbc.exceptions.ExceptionMessages;
import de.tu_bs.cs.isf.cbc.exceptions.SettingsException;
import de.tu_bs.cs.isf.cbc.exceptions.TestAndAssertionGeneratorException;

public class CodeGenerator {
    public static final CodeGenerator instance = new CodeGenerator();

    private int positionIndex = 0;
    private JavaVariable returnVariable = null;

    private CodeGenerator() {

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
		String code = ConstructCodeBlock.constructCodeBlockForExport(formula, globalConditions, renaming, localVariables, returnVariable, signatureString, new String[0]);
		return code;
	}
	

    public String genDiagramCode(CbCFormula formula, Renaming renaming) throws TestAndAssertionGeneratorException {
	String code;
	if (formula.getStatement().getRefinement() != null) {
	    code = constructCodeBlockOfChildStatement(formula.getStatement().getRefinement());
	    if (renaming != null)
		code = ConstructCodeBlock.useRenamingCondition(code);
	} else {
	    code = constructCodeBlockOfChildStatement(formula.getStatement());
	    if (renaming != null)
		code = ConstructCodeBlock.useRenamingCondition(code);
	}
	return code;
    }

    private String constructCodeBlockOfChildStatement(AbstractStatement refinement)
	    throws TestAndAssertionGeneratorException {
	if (refinement.getClass().equals(AbstractStatementImpl.class)
		|| refinement.getClass().equals(OriginalStatementImpl.class)
		|| refinement.getClass().equals(MethodStatementImpl.class)) {
	    String allStatements = refinement.getName().replace("\r\n", "");
	    allStatements = allStatements.trim();
	    allStatements = allStatements.replaceAll("\\s+", " ");
	    allStatements = allStatements.replace("/ =", " /= ");
	    allStatements = allStatements.replace("+ =", " += ");
	    allStatements = allStatements.replace("- =", " -= ");
	    allStatements = allStatements.replace("* =", " *= ");

	    String abstractStatementSplit[] = allStatements.split(";");
	    String statements;
	    if (abstractStatementSplit.length > 1) {
		statements = abstractStatementSplit[0].trim() + ";\n";
		for (int i = 1; i < abstractStatementSplit.length; i++) {
		    for (int j = 0; j < positionIndex; j++) {
			statements = statements + "\t";
		    }
		    statements = statements + (abstractStatementSplit[i].trim() + ";\n");
		}
	    } else {
		statements = allStatements + "\n";
	    }
	    return statements;
	} else if (refinement.getClass().equals(SkipStatementImpl.class)) {
	    return ";\n";
	} else if (refinement.getClass().equals(ReturnStatementImpl.class)) {
	    if (returnVariable != null) {// In case of void method with "return;", returnVariable will be null
		String returnString = returnStatement(returnVariable.getName().split(" ")[1],
			refinement.getName().trim());
		if (returnString.isEmpty()) {
		    return "return " + refinement.getName() + "\n";
		}
		for (int i = 0; i < positionIndex; i++) {
		    returnString = returnString + "\t";
		}
		returnString = returnString + "return " + returnVariable.getName().split(" ")[1] + ";\n";
		return returnString;
	    }
	    return "return " + refinement.getName() + "\n";
	} else if (refinement.getClass().equals(SelectionStatementImpl.class)) {
	    return constructSelection((SelectionStatement) refinement);
	} else if (refinement.getClass().equals(CompositionStatementImpl.class)) {
	    return constructComposition((CompositionStatement) refinement);
	} else if (refinement.getClass().equals(SmallRepetitionStatementImpl.class)) {
	    return constructSmallRepetition((SmallRepetitionStatement) refinement);
	} else if (refinement.getClass().equals(StrengthWeakStatementImpl.class)) {
	    if (refinement.getRefinement() != null) {
		return constructCodeBlockOfChildStatement(refinement.getRefinement());
	    } else {
		return refinement.getName() + ";\n";
	    }
	}
	throw new TestAndAssertionGeneratorException(
		"The refinement of type '" + refinement.getClass() + "' is not supported by the code generator.");

    }

    private String constructSelection(SelectionStatement statement) throws TestAndAssertionGeneratorException {
	StringBuffer buffer = new StringBuffer();

	if (!statement.getCommands().isEmpty()) {
	    String guard = statement.getGuards().get(0).getName();

	    guard = rewriteGuardToJavaCode(guard);

	    if (guard.trim().equals("TRUE"))
		guard = "true";
	    if (guard.trim().equals("FALSE"))
		guard = "false";

	    buffer.append("if (" + guard + ") {\n");

	    positionIndex++;
	    if (statement.getCommands().get(0).getRefinement() != null) {
		for (int i = 0; i < positionIndex; i++) {
		    buffer.append("\t");
		}
		buffer.append(constructCodeBlockOfChildStatement(statement.getCommands().get(0).getRefinement()));
		positionIndex--;
		for (int i = 0; i < positionIndex; i++) {
		    buffer.append("\t");
		}
		buffer.append("}");
	    } else {
		for (int i = 0; i < positionIndex; i++) {
		    buffer.append("\t");
		}
		buffer.append(constructCodeBlockOfChildStatement(statement.getCommands().get(0)));
		positionIndex--;
		for (int i = 0; i < positionIndex; i++) {
		    buffer.append("\t");
		}
		buffer.append("}");
	    }
	}

	for (int i = 1; i < statement.getCommands().size(); i++) {
	    String guard = statement.getGuards().get(i).getName();
	    guard = rewriteGuardToJavaCode(guard);

	    if (guard.trim().equals("TRUE"))
		guard = "true";
	    if (guard.trim().equals("FALSE"))
		guard = "false";

	    buffer.append(" else if (" + guard + ") {\n");
	    positionIndex++;
	    if (statement.getCommands().get(i).getRefinement() != null) {
		for (int j = 0; j < positionIndex; j++) {
		    buffer.append("\t");
		}
		buffer.append(constructCodeBlockOfChildStatement(statement.getCommands().get(i).getRefinement()));
		positionIndex--;
		for (int j = 0; j < positionIndex; j++) {
		    buffer.append("\t");
		}
		buffer.append("}");
	    } else {
		for (int j = 0; j < positionIndex; j++) {
		    buffer.append("\t");
		}
		buffer.append(constructCodeBlockOfChildStatement(statement.getCommands().get(i)));
		positionIndex--;
		for (int j = 0; j < positionIndex; j++) {
		    buffer.append("\t");
		}
		buffer.append("}");
	    }

	}

	buffer.append("\n");
	return buffer.toString();
    }

    private String constructComposition(CompositionStatement statement) throws TestAndAssertionGeneratorException {
	StringBuffer buffer = new StringBuffer();

	if (statement.getFirstStatement().getRefinement() != null) {
	    buffer.append(constructCodeBlockOfChildStatement(statement.getFirstStatement().getRefinement()));
	} else {
	    buffer.append(constructCodeBlockOfChildStatement(statement.getFirstStatement()));
	}

	for (int i = 0; i < positionIndex; i++) {
	    buffer.append("\t");
	}

	if (statement.getSecondStatement().getRefinement() != null) {
	    buffer.append(constructCodeBlockOfChildStatement(statement.getSecondStatement().getRefinement()));
	} else {
	    buffer.append(constructCodeBlockOfChildStatement(statement.getSecondStatement()));
	}

	return buffer.toString();
    }

    private String constructSmallRepetition(SmallRepetitionStatement statement)
	    throws TestAndAssertionGeneratorException {
	StringBuffer buffer = new StringBuffer();
	String guard = statement.getGuard().getName();
	guard = rewriteGuardToJavaCode(guard);

	if (guard.trim().equals("TRUE"))
	    guard = "true";
	if (guard.trim().equals("FALSE"))
	    guard = "false";

	buffer.append("while (" + guard + ") {\n");
	positionIndex++;
	for (int i = 0; i < positionIndex; i++) {
	    buffer.append("\t");
	}
	if (statement.getLoopStatement().getRefinement() != null) {
	    buffer.append(constructCodeBlockOfChildStatement(statement.getLoopStatement().getRefinement()));
	} else {
	    buffer.append(constructCodeBlockOfChildStatement(statement.getLoopStatement()));
	}
	positionIndex--;
	for (int i = 0; i < positionIndex; i++) {
	    buffer.append("\t");
	}
	buffer.append("}\n");
	return buffer.toString();
    }

    private String rewriteGuardToJavaCode(String guard) {
	guard = guard.replaceAll("(?<!<|>|!|=)(\\s*=\\s*)(?!<|>|=)", " == ");
	guard = guard.replace("&", "&&");
	guard = guard.replace("|", "||");
	guard = guard.replaceAll("\\s+TRUE\\s*|TRUE\\s+", " true ");
	guard = guard.replaceAll("\\s+FALSE\\s*|FALSE\\s+", " false ");
	guard = guard.trim();
	return guard;
    }

    private String returnStatement(String variableName, String refinementName) {
	String s = "";
	if (!refinementName.trim().split(";")[0].equals(variableName)
		&& !refinementName.trim().split(";")[0].equals("this." + variableName)) {
	    if (refinementName.contains("=") && refinementName.charAt(refinementName.indexOf('=') + 1) != '='
		    && refinementName.charAt(refinementName.indexOf('=') - 1) != '>'
		    && refinementName.charAt(refinementName.indexOf('=') - 1) != '<') {
		s = refinementName + "\n";
		if (!refinementName.trim().substring(0, refinementName.indexOf('=') - 1).equals(variableName)) {
		    for (int i = 0; i < positionIndex; i++) {
			s = s + "\t";
		    }
		    s = s + variableName + " = " + refinementName.trim().split("=")[0] + ";\n";
		}
	    } else {
		s = variableName + " = " + refinementName + "\n";
	    }
	}
	return s;
    }

    public String genCode(Diagram diagram, boolean onlyMethod)
	    throws TestAndAssertionGeneratorException, SettingsException {
	String signatureString;
	final LinkedList<String> localVariables = new LinkedList<String>();
	JavaVariable returnVariable = null;
	DiagramPartsExtractor extractor = new DiagramPartsExtractor(diagram);
	JavaVariables vars = extractor.getVars();
	Renaming renaming = extractor.getRenaming();
	CbCFormula formula = extractor.getFormula();
	GlobalConditions globalConditions = extractor.getConds();
	String globalVariables = "";
	int counter = 0;
	URI projectPath = diagram.eResource().getURI();

	if (formula.getMethodObj() == null) {
	    List<String> params = new ArrayList<String>();
	    signatureString = formula.getName();
	    for (var v : vars.getVariables()) {
		if (v.getKind().toString().equals("PARAM")) {
		    params.add(v.getName());
		} else if (v.getKind().toString().equals("RETURN")) {
		    signatureString = "public " + v.getName().split("\\s")[0] + " " + signatureString;
		}
	    }
	    signatureString += "(";
	    if (params.size() == 0) {
		signatureString += ")";
	    } else {
		for (int i = 0; i < params.size() - 1; i++) {
		    signatureString += params.get(i) + ", ";
		}
		signatureString += params.get(params.size() - 1) + ")";
	    }
	    if (!signatureString.contains("public")) {
		signatureString = "public void " + signatureString;
	    }
	} else {
	    signatureString = formula.getMethodObj().getSignature();
	}

	// TODO: Fix RETURN Variable appearing in parameters
	var customReturnName = "";

	for (int i = 0; i < vars.getVariables().size(); i++) {
	    JavaVariable currentVariable = vars.getVariables().get(i);
	    if (currentVariable.getKind() == VariableKind.RETURN) {
		var varName = currentVariable.getName().replace("non-null", "").split("\\s")[1];
		var varType = currentVariable.getName().split("\\s")[0];
		localVariables.add(currentVariable.getName().replace("non-null", ""));
		counter++;
		if (!signatureString.substring(0, signatureString.indexOf('('))
			.contains(currentVariable.getName().replace("non-null", "").trim().split("\\s")[0])) {
		    Console.clear();
		    Console.println("Method return type and variable type does not match.");
		    return "";
		}
		if (counter > 1) {
		    Console.clear();
		    Console.println("Too much variables of kind RETURN.");
		    return "";
		}
		returnVariable = currentVariable;
	    } else if (currentVariable.getKind() == VariableKind.LOCAL) {
		// also handle old_ annotated variables here
		var splitter = currentVariable.getName().split("\\s");
		var varName = splitter[splitter.length - 1];
		if (varName.startsWith("old_")) {
		    var modifiedPostCon = formula.getStatement().getPostCondition();
		    var newCon = modifiedPostCon.getName();
		    var helper = varName.substring("old_".length(), varName.length());
		    helper = "old_" + helper.replaceAll("\\_", "\\.");
		    newCon = newCon.replaceAll(varName, helper);
		    formula.getStatement().getPostCondition().setName(newCon);
		} else {
		    if (!localVariables.contains(currentVariable.getName().replace("non-null", ""))) {
			localVariables.add(currentVariable.getName().replace("non-null", ""));
		    }
		}
	    }
	}

	for (Field field : vars.getFields()) {
	    if (field.getName() != null) {
		if (field.isIsStatic()) {
		    globalVariables += ("\t" + field.getVisibility().getName().toLowerCase() + " static "
			    + field.getType() + " " + field.getName().replace("non-null ", "") + ";\n");
		} else {
		    globalVariables += ("\t" + field.getVisibility().getName().toLowerCase() + " " + field.getType()
			    + " " + field.getName().replace("non-null ", "") + ";\n");
		}
	    }
	}

	return generateCode(projectPath, formula, globalConditions, renaming, localVariables, returnVariable,
		signatureString, globalVariables,
		vars.getFields().stream().map(f -> f.getType() + " " + f.getName()).toList(), onlyMethod);
    }

    /**
     * Generates the code for a CbC method.
     * 
     * @param formula
     * @param globalConditions
     * @param renaming
     * @param vars
     * @param returnVar
     * @param signatureString
     * @param globalVariables
     * @param gVars
     * @return the generated java code if the method wasn't generated before. Else
     *         it returns an empty string.
     * @throws TestAndAssertionGeneratorException
     * @throws SettingsException
     */
    private String generateCode(URI projectPath, CbCFormula formula, GlobalConditions globalConditions,
	    Renaming renaming, LinkedList<String> vars, JavaVariable returnVar, String signatureString,
	    String globalVariables, List<String> gVars, boolean onlyMethod)
	    throws TestAndAssertionGeneratorException, SettingsException {
	String existingCode = "";
	String constructorCode;
	StringBuffer code = new StringBuffer();
	final String className;

	if (returnVar != null) {
	    returnVariable = returnVar;
	}

	if (formula.getMethodObj() == null) {
	    className = ClassHandler.GENERATED_CLASSNAME;
	} else {
	    className = formula.getMethodObj().getParentClass().getName();
	}

	// first check if the method already exists
	if (!onlyMethod && !(existingCode = ClassHandler.classExists(projectPath, className)).equals("")) {
	    // make sure the constructor is present
	    constructorCode = generateConstructor(className, gVars);
	    if (!containsConstructor(existingCode, constructorCode)) {
		existingCode = insertConstructorCode(existingCode, constructorCode);
	    }
	    // remove method if it is already present
	    existingCode = removeMethod(existingCode, signatureString);

	    code.append(existingCode.trim());
	    // only generate the method code
	    onlyMethod = true;
	}

	if (!onlyMethod) {
	    // generate class
	    code.append("public class " + className + " {\n");
	    // generate global vars
	    code.append(globalVariables);

	    // generate constructor for testing purposes
	    code.append(generateConstructor(className, gVars));
	}

	// generate method
	code.append("\n\n\t" + signatureString + " {\n");
	positionIndex = 2;// 2
	code = insertTabs(code);

	for (String var : vars) {// declare variables
	    if (!var.contains("old_")) {
		var defaultValue = InputData.getDefaultValue(var.split(" ")[0]);// genDefaultInputForVar(var,
										// null).get(0);
		code.append(var + " = " + defaultValue + ";\n");
		code = insertTabs(code);
	    }
	}

	if (returnVariable != null
		&& !MethodHandler.getBySignature(code.toString(), signatureString).contains(returnVariable.getName())) {
	    code.append(returnVariable.getName() + ";\n");
	    code = insertTabs(code);
	}

	String s = genDiagramCode(formula, renaming);
	code.append(s);

	var methodCode = code.toString().substring(code.indexOf(signatureString), code.length());
	int closingBracket = methodCode.contains("}") ? methodCode.lastIndexOf('}') : 0;
	var lastPartOfMethod = methodCode.substring(closingBracket, methodCode.length());
	if (returnVariable != null
		&& !lastPartOfMethod.contains("return " + returnVariable.getName().split("\\s")[1])) {
	    // if (!void_pattern.matcher(signatureString).find() &&
	    // !return_pattern.matcher(code.toString()).find()) {
	    var splitter = code.toString().trim().split(";");
	    if (!splitter[splitter.length - 1].contains("return")) {
		code.append("\t\treturn " + returnVariable.getName().split("\\s")[1] + ";\n");
	    }
	    // } else if (code.toString().)
	} else if (returnVariable == null
		&& (formula.getMethodObj() == null || !formula.getMethodObj().getReturnType().equals("void"))) {
	    throw new TestAndAssertionGeneratorException(ExceptionMessages.RET);
	}
	code.append("\t}");// }

	if (!onlyMethod || !existingCode.equals("")) {
	    // close class
	    code.append("\n}");
	}
	return code.toString();
    }

    private StringBuffer insertTabs(StringBuffer s) {
	for (int i = 0; i < positionIndex; i++) {
	    s.append("\t");
	}
	return s;
    }

    private String generateConstructor(final String className, final List<String> gVars) {
	final StringBuffer code = new StringBuffer();
	String globalVarName;

	code.append("\n\t" + "public " + className + "(");
	int counter = 0;
	for (var g : gVars) {
	    if (counter != gVars.size() - 1) {
		code.append(g + ", ");
	    } else {
		code.append(g);
	    }
	    counter++;
	}
	code.append(") {\n");

	for (var g : gVars) {
	    var splitter = g.split("\\s");
	    if (splitter.length > 1) {
		globalVarName = g.split("\\s")[splitter.length - 1];
	    } else {
		globalVarName = g.split("\\s")[0];
	    }
	    code.append("\t\t" + "this." + globalVarName + " = " + globalVarName + ";\n");
	}
	code.append("\t}\n\n");
	return code.toString().trim();
    }

    private String insertConstructorCode(final String code, final String constructorCode) {
	// find first empty line and insert constructor there
	return code.substring(0, code.indexOf('{') + 1) + "\n" + constructorCode
		+ code.substring(code.indexOf('{') + 1, code.length());
    }

    private String removeMethod(String code, String methodSignature) {
	// var split = sSplit(methodSignature, "\\s");
	// var methodName = split[split.length - 1];
	if (!code.contains(methodSignature)) {
	    return code.substring(0, code.lastIndexOf("}"));
	}
	String before = code.substring(0, code.indexOf(methodSignature));
	before = before.substring(0, before.lastIndexOf("\n")).trim();
	String after = code.substring(code.indexOf(methodSignature), code.length());
	int closingBracketIndex = CodeHandler.findClosingBracketIndex(code,
		code.indexOf(methodSignature) + after.indexOf('{'), '{');
	after = "\n\n\t" + code.substring(closingBracketIndex + 1, code.length()).trim();
	var output = before + after;
	return output.trim().substring(0, output.length() - 1);
    }

    private boolean containsConstructor(String code, String constructor) {
	constructor = constructor.replaceAll("\\s", "");
	code = code.replaceAll("\\s", "");
	if (code.indexOf(constructor) != -1 && code.charAt(code.indexOf(constructor) - 1) == ']') {
	    return false;
	}
	return code.contains(constructor);
    }
}
