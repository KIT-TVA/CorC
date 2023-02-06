package de.tu_bs.cs.isf.cbc.tool.features;

import org.testng.TestNG;

import org.testng.xml.XmlSuite.ParallelMode;

import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

import de.tu_bs.cs.isf.cbc.cbcclass.Field;
import de.tu_bs.cs.isf.cbc.cbcclass.ModelClass;
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
import de.tu_bs.cs.isf.cbc.tool.exceptions.ExceptionMessages;
import de.tu_bs.cs.isf.cbc.tool.exceptions.PreConditionSolverException;
import de.tu_bs.cs.isf.cbc.tool.exceptions.ReferenceException;
import de.tu_bs.cs.isf.cbc.tool.exceptions.TestAndAssertionGeneratorException;
import de.tu_bs.cs.isf.cbc.tool.exceptions.TestStatementException;
import de.tu_bs.cs.isf.cbc.tool.exceptions.UnexpectedTokenException;
import de.tu_bs.cs.isf.cbc.tool.helper.ClassHandler;
import de.tu_bs.cs.isf.cbc.tool.helper.CodeHandler;
import de.tu_bs.cs.isf.cbc.tool.helper.ConditionHandler;
import de.tu_bs.cs.isf.cbc.tool.helper.Features;
import de.tu_bs.cs.isf.cbc.tool.helper.InputData;
import de.tu_bs.cs.isf.cbc.tool.helper.InputDataTupel;
import de.tu_bs.cs.isf.cbc.tool.helper.JavaCondition;
import de.tu_bs.cs.isf.cbc.tool.helper.MethodHandler;
import de.tu_bs.cs.isf.cbc.tool.helper.PreConditionSolver;
import de.tu_bs.cs.isf.cbc.tool.helper.TestAndAssertionListener;
import de.tu_bs.cs.isf.cbc.tool.helper.TestCaseData;
import de.tu_bs.cs.isf.cbc.tool.helper.TestStatementListener;
import de.tu_bs.cs.isf.cbc.tool.helper.TestUtilSPL;
import de.tu_bs.cs.isf.cbc.tool.helper.FileHandler;
import de.tu_bs.cs.isf.cbc.tool.helper.conditionparser.ConditionParser;
import de.tu_bs.cs.isf.cbc.tool.helper.conditionparser.Node;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.ConstructCodeBlock;
import de.tu_bs.cs.isf.cbc.util.FileUtil;

/**
 * The test generator.
 * @author Fynn
 */
public class TestAndAssertionGenerator extends MyAbstractAsynchronousCustomFeature{
	private static int positionIndex = 0;
	private static JavaVariable returnVariable = null;
	private URI projectPath;
	private List<String> projectJavaFiles;
	private List<String> projectInternalClasses;
	private static final String INSTANCE_TOKEN = "<[";
	private static final String INSTANCE_CLOSED_TOKEN = "]>";
	public static final String ARRAY_TOKEN = "<{";
	public static final String ARRAY_CLOSED_TOKEN = "}>";
	public static final String GENERATED_CLASSNAME = "GeneratedClass";
	private final static String STATEMENT_PH = "<statement>";
	private boolean showWarnings = false;
	private static final Color red = new Color(new RGB(150, 10, 10));
		
	public TestAndAssertionGenerator(IFeatureProvider fp) {
		super(fp);
	}
	
	@Override
	public String getName() {
		return "Generate blackbox tests";
	}

	@Override
	public String getDescription() {
		return "Generates tests for the diagram.";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		return true;
	}

	@Override
	public void execute(ICustomContext context, IProgressMonitor monitor) {
/*
			Node tree;
			try {
				tree = ConditionHandler.parseCondition(projectPath, "(\\forall int k; (0 <= k & k < \\old(data).length -> (\\exists int z; (0 <= z & z < data.length & data[z] = \\old(data)[k]))))& ((\\forall int k; (0 <= k & k < data.length-1 -> (data[k] >= data[k+1]))) | (\\forall int k; (0 <= k & k < data.length-1 -> (data[k] <= data[k+1]))))", "", null);
				var tmpss = new JavaConditionReworked(tree);
				var llwlwlw = tmpss.get();
				var sjdfjsdjf = 2;
			} catch (UnexpectedTokenException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
*/
		
	
		
		
		
		
		this.returnVariable = null;
		final URI uri = getDiagram().eResource().getURI();
		final List<String> globalVars = new ArrayList<String>();
		String signatureString = "";
		JavaVariables vars = null;
		CbCFormula formula = null;
		GlobalConditions globalConditions = null;
		
		setProjectPath(uri);
		FileHandler.clearLog(uri);

		for (Shape shape : getDiagram().getChildren()) {
			Object obj = getBusinessObjectForPictogramElement(shape);
			if (obj instanceof JavaVariables) {
				vars = (JavaVariables) obj;
			} else if (obj instanceof CbCFormula) {
				formula = (CbCFormula) obj;
			} else if (obj instanceof GlobalConditions) {
				globalConditions = (GlobalConditions) obj;
			}
		}
		
		if (formula.getMethodObj() == null) {
			Console.println("Method object is undefined. Assuming method is of type 'void'.");
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
		
		int counter = 0;
		LinkedList<String> localVariables = new LinkedList<String>();
		for(int i = 0; i < vars.getVariables().size(); i++) {
			JavaVariable currentVariable = vars.getVariables().get(i);
			if(currentVariable.getKind() == VariableKind.RETURN) {
				counter++;
				if(!signatureString.substring(0, signatureString.indexOf('(')).contains(currentVariable.getName().replace("non-null", "").trim().split("\\s")[0])) {
					Console.clear();
					Console.println("Method return type and variable type does not match.");
					return;
				}
				if(counter > 1) {
					Console.clear();
					Console.println("Too much variables of kind RETURN.");
					return;
				}
				returnVariable = currentVariable;
			}else if(currentVariable.getKind() == VariableKind.LOCAL) {
				localVariables.add(currentVariable.getName().replace("non-null", ""));
			}
		}
		for (Field field : vars.getFields()) {
			if (field.getName() == null) { 
				continue;
			}
			globalVars.add(field.getName().replace("non-null ", ""));
		}			
		long startTime = System.nanoTime();	
		Console.clear();	
		Console.println("Start testing...\n");  
		Features features = null;
		if (FileHandler.isSPL(uri)) {
			features = new Features(uri);
		} else {
			features = null;
		}
		try {
			if (features != null) {
				Console.println("[SPL detected]", TestStatement.blue);
				for (int i = 0; i < features.getSize(); i++) {
					features.getNextConfig();
					Console.println(" > Configuration: [" + features.getConfigRep() + "]", TestStatement.blue);
					if (!test(uri, formula, vars, globalConditions, signatureString, globalVars, features)) {
						continue;
					}
					// save configuration in a separate file
					FileHandler.saveConfig(uri, formula, features, true);
				}		
			} else {
				test(uri, formula, vars, globalConditions, signatureString, globalVars, features);
			}	
		} catch (ReferenceException | TestAndAssertionGeneratorException | PreConditionSolverException | UnexpectedTokenException | TestStatementException e) {
			Console.println(e.getMessage(), TestStatementListener.red);
			e.printStackTrace();
			return;
		}
		long endTime = System.nanoTime();
		double elapsedTime = (endTime - startTime) / 1000000;
		Console.println("Time needed: " + (int)elapsedTime + "ms");
	}
	
	private boolean test(final URI uri, final CbCFormula formula, final JavaVariables vars, final GlobalConditions globalConditions, final String signatureString, final List<String> globalVars, final Features features) throws ReferenceException, TestAndAssertionGeneratorException, PreConditionSolverException, UnexpectedTokenException, TestStatementException {
		var methodToGenerate = getDiagram();			
		String code2 = genCode(methodToGenerate);		
		var className = code2.split("public\\sclass\\s", 2)[1].split("\\s", 2)[0];
		className = className.replaceAll("\\{", "");		
		var code = genCode(methodToGenerate, true);
		final var methodSig = MethodHandler.getSignatureFromCode(code);
		code = code.replaceAll("self\\.", "this.");
		// handle original and abstract method calls
		final var originalMethods = new ArrayList<MethodHandler>();
		final var abstractMethods = new ArrayList<MethodHandler>();
		if (FileHandler.isSPL(uri)) {
			if (code.contains("original(")) {
				TestUtilSPL.handleOriginalCode(this.getFeatureProvider(), projectPath, code, features, originalMethods, formula.getMethodObj().getSignature(), vars);
				code = code.replaceAll("original\\(", originalMethods.get(0).getMethodName() + "(");
			}
			// TODO: ignore methods that are not abstract
			TestUtilSPL.handleAbstractMethodCalls(this.getFeatureProvider(), projectPath, code, features, abstractMethods);
			for (var originalMethod : originalMethods) {
				TestUtilSPL.handleAbstractMethodCalls(this.getFeatureProvider(), projectPath, originalMethod.getInnerCode(), features, abstractMethods);
			}
		}
		List<ClassHandler> classCodes; 
		String postCon = ConditionHandler.replaceResultKeyword(formula.getStatement().getPostCondition().getName(), returnVariable);
		if (FileHandler.isSPL(projectPath)) {
			boolean isPreCon = false;
			postCon = TestUtilSPL.handleOriginalCondition(this.getFeatureProvider(), postCon, isPreCon, features);
		}	
		classCodes = genAllDependenciesOfMethod(code, methodSig, className, postCon);
		if (FileHandler.isSPL(uri)) {
			TestUtilSPL.addNewMethods(classCodes, className, originalMethods, abstractMethods);
		}
		if(!compileFileContents2(classCodes, methodToGenerate.getName())) {
			return false;
		}
		// get code of class to be tested
		for (var clazz : classCodes) {
			if (clazz.getClassName().equals(className)) {
				code2 = clazz.getCode();
			}
		}

		List<TestCaseData> inputs;
		String testFileContent;
		
		String preCon = ConditionParser.parseConditions(globalConditions, formula.getStatement().getPreCondition());
		if (FileHandler.isSPL(projectPath)) {
			boolean isPreCon = true;
			preCon = TestUtilSPL.handleOriginalCondition(this.getFeatureProvider(), preCon, isPreCon, features);
		}	
		inputs = genInputs(preCon, vars, code2, signatureString, returnVariable);
		if (inputs.isEmpty()) {
			Console.println("TestAndAssertionGeneratorInfo: There are no controllable inputs for method " + methodToGenerate.getName() + ".");
			Console.println("Nothing to test.");
			return false;
		}
		testFileContent = genTestCases(className, inputs, postCon, globalConditions, formula);
		testFileContent = CodeHandler.addInstanceNameToFields(ClassHandler.getClassByName(classCodes, className), testFileContent);
		FileHandler.writeToFile(this.projectPath, className + "Test.java", testFileContent);
		executeTestCases("file://" + FileUtil.getProjectLocation(uri) + "/tests/", className + "Test.java", globalVars, inputs);
		return true;
	}

	//================================================================================================
	//| Start of copied code from existing methods.												 	 |
	//================================================================================================

	private static String rewriteGuardToJavaCode(String guard) {
		guard = guard.replaceAll("(?<!<|>|!|=)(\\s*=\\s*)(?!<|>|=)", " == ");
		guard = guard.replace("&", "&&");
		guard = guard.replace("|", "||");
		guard = guard.replaceAll("\\s+TRUE\\s*|TRUE\\s+", " true ");
		guard = guard.replaceAll("\\s+FALSE\\s*|FALSE\\s+", " false ");
		guard = guard.trim();
		return guard;
	}
	
	private static String constructSelection(SelectionStatement statement) {
		StringBuffer buffer = new StringBuffer();

		if (!statement.getCommands().isEmpty()) {
			String guard = statement.getGuards().get(0).getName();

			guard = rewriteGuardToJavaCode(guard);

			if(guard.trim().equals("TRUE"))
				guard = "true";
			if(guard.trim().equals("FALSE"))
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
			// guard = guard.replaceAll("\\s=\\s", "==");
			guard = rewriteGuardToJavaCode(guard);
			
			if(guard.trim().equals("TRUE"))
				guard = "true";
			if(guard.trim().equals("FALSE"))
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
	
	private static String constructComposition(CompositionStatement statement) {
		StringBuffer buffer = new StringBuffer();
		
		if (statement.getFirstStatement().getRefinement() != null) {
			buffer.append(constructCodeBlockOfChildStatement(statement.getFirstStatement().getRefinement()));
		} else {
			buffer.append(constructCodeBlockOfChildStatement(statement.getFirstStatement()));
		}

		//commented out to prevent generation of assets from intermediate condition
		/*
		for (int i = 0; i < positionIndex; i++) {
			buffer.append("\t");
		}
		if(statement.getIntermediateCondition().getName() != "" && withAsserts) {
			buffer.append("assert " + statement.getIntermediateCondition().getName().replace("\n", " ").replace("\r", " ") + ";\n");
		}
		*/
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
	
	private static String constructSmallRepetition(SmallRepetitionStatement statement) {
		StringBuffer buffer = new StringBuffer();
		/*
		if (withInvariants) {
			String invariant = statement.getInvariant().getName();
			invariant = Parser.rewriteConditionToJML(invariant);
			//invariant = useRenamingCondition(invariant);
			buffer.append("//@ loop_invariant " + invariant.replaceAll("\\r\\n", "") + ";\n");
			for (int i = 0; i < positionIndex; i++) {
				buffer.append("\t");
			}
			buffer.append("//@ decreases " + statement.getVariant().getName() + ";\n");
		}
		*/
		String guard = statement.getGuard().getName();
		// guard = guard.replaceAll("\\s=\\s", "==");
		guard = rewriteGuardToJavaCode(guard);
		/*for (int i = 0; i < positionIndex; i++) {
			buffer.append("\t");
		}*/
		
		if(guard.trim().equals("TRUE"))
			guard = "true";
		if(guard.trim().equals("FALSE"))
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
	
	private static String returnStatement(String variableName, String refinementName) {
		String s = "";
		if(!refinementName.trim().split(";")[0].equals(variableName)
				&& !refinementName.trim().split(";")[0].equals("this." + variableName)) {
			if(refinementName.contains("=") 
					&& refinementName.charAt(refinementName.indexOf('=') + 1) != '='
					&& refinementName.charAt(refinementName.indexOf('=') - 1) != '>'
					&& refinementName.charAt(refinementName.indexOf('=') - 1) != '<') {
				//s = variableName + refinementName.replace(refinementName.subSequence(0, refinementName.indexOf('=')), "") + "\n";
				s = refinementName + "\n";
				if(!refinementName.trim().substring(0, refinementName.indexOf('=') - 1).equals(variableName)) {
					for(int i = 0; i < positionIndex; i++) {
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
	
	private static String constructCodeBlockOfChildStatement(AbstractStatement refinement) {
		if (refinement.getClass().equals(AbstractStatementImpl.class) || refinement.getClass().equals(OriginalStatementImpl.class) || refinement.getClass().equals(MethodStatementImpl.class)) {
			// behandlung von AbstractStatementImpl nur von Tobi
			String allStatements = refinement.getName().replace("\r\n", "");
			allStatements = allStatements.trim();
			allStatements = allStatements.replaceAll("\\s+", " ");
			//allStatements = allStatements.split("\\w\\=\\w")[0]+ " = " + allStatements.split("\\w\\=\\w")[1];
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
			// return statements;
			return statements;
			// return refinement.getName() + "\n";
		} else if (refinement.getClass().equals(SkipStatementImpl.class)) {
			return ";\n";
		} else if (refinement.getClass().equals(ReturnStatementImpl.class)) {
			if(returnVariable != null) {//In case of void method with "return;", returnVariable will be null
				String returnString = returnStatement(returnVariable.getName().split(" ")[1], refinement.getName().trim());
				if(returnString.isEmpty()) {
					return "return " + refinement.getName() + "\n";
				}				
				for(int i = 0; i < positionIndex; i++) {
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
		return null;
	}
	
	private static StringBuffer insertTabs(StringBuffer s) {
		for (int i = 0; i < positionIndex; i++) {
			s.append("\t");
		}
		return s;
	}
	
	//================================================================================================
	//| End of copied code from existing methods.													 |
	//================================================================================================
	
	private static String constructCodeBlockOfChildStatement(final AbstractStatement refinement, final String id) throws TestAndAssertionGeneratorException {
		if (id == null) {
			//Console.clear();
			Console.println("Please add IDs to the diagram.");
			throw new TestAndAssertionGeneratorException(ExceptionMessages.IDNULL);
		}
		if (id.equals(refinement.getId())) {
			return STATEMENT_PH + "\n";
		}
		if (refinement.getClass().equals(AbstractStatementImpl.class) || refinement.getClass().equals(OriginalStatementImpl.class) || refinement.getClass().equals(MethodStatementImpl.class)) {
			// behandlung von AbstractStatementImpl nur von Tobi
			String allStatements = refinement.getName().replace("\r\n", "");
			allStatements = allStatements.trim();
			allStatements = allStatements.replaceAll("\\s+", " ");
			//allStatements = allStatements.split("\\w\\=\\w")[0]+ " = " + allStatements.split("\\w\\=\\w")[1];
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
			// return statements;
			return statements;
			// return refinement.getName() + "\n";
		} else if (refinement.getClass().equals(SkipStatementImpl.class)) {
			return ";\n";
		} else if (refinement.getClass().equals(ReturnStatementImpl.class)) {
			if(returnVariable != null) {//In case of void method with "return;", returnVariable will be null
				String returnString = returnStatement(returnVariable.getName().split(" ")[1], refinement.getName().trim());
				if(returnString.isEmpty()) {
					return "return " + refinement.getName() + "\n";
				}				
				for(int i = 0; i < positionIndex; i++) {
					returnString = returnString + "\t";
				}
				returnString = returnString + "return " + returnVariable.getName().split(" ")[1] + ";\n";
				return returnString; 
			}
			return "return " + refinement.getName() + "\n";
		} else if (refinement.getClass().equals(SelectionStatementImpl.class)) {
			return constructSelection((SelectionStatement) refinement, id);
		} else if (refinement.getClass().equals(CompositionStatementImpl.class)) {
			return constructComposition((CompositionStatement) refinement, id);
		} else if (refinement.getClass().equals(SmallRepetitionStatementImpl.class)) {
			return constructSmallRepetition((SmallRepetitionStatement) refinement, id);
		} else if (refinement.getClass().equals(StrengthWeakStatementImpl.class)) {
			if (refinement.getRefinement() != null) {
				return constructCodeBlockOfChildStatement(refinement.getRefinement(), id);
			} else {
				return refinement.getName() + ";\n";
			}
		}
		return null;
	}
	
	private static String constructSelection(final SelectionStatement statement, final String id) throws TestAndAssertionGeneratorException {
		StringBuffer buffer = new StringBuffer();

		if (!statement.getCommands().isEmpty()) {
			String guard = statement.getGuards().get(0).getName();

			guard = rewriteGuardToJavaCode(guard);

			if(guard.trim().equals("TRUE"))
				guard = "true";
			if(guard.trim().equals("FALSE"))
				guard = "false";
			
			buffer.append("if (" + guard + ") {\n");

			positionIndex++;
			if (statement.getCommands().get(0).getRefinement() != null) {
				for (int i = 0; i < positionIndex; i++) {
					buffer.append("\t");
				}
				try {
					buffer.append(constructCodeBlockOfChildStatement(statement.getCommands().get(0).getRefinement(), id));
				} catch (TestAndAssertionGeneratorException e) {
					throw e;
				}
				positionIndex--;
				for (int i = 0; i < positionIndex; i++) {
					buffer.append("\t");
				}
				buffer.append("}");
			} else {
				for (int i = 0; i < positionIndex; i++) {
					buffer.append("\t");
				}
				buffer.append(constructCodeBlockOfChildStatement(statement.getCommands().get(0), id));
				positionIndex--;
				for (int i = 0; i < positionIndex; i++) {
					buffer.append("\t");
				}
				buffer.append("}");
			}
		}

		for (int i = 1; i < statement.getCommands().size(); i++) {
			String guard = statement.getGuards().get(i).getName();
			// guard = guard.replaceAll("\\s=\\s", "==");
			guard = rewriteGuardToJavaCode(guard);
			
			if(guard.trim().equals("TRUE"))
				guard = "true";
			if(guard.trim().equals("FALSE"))
				guard = "false";
			
			buffer.append(" else if (" + guard + ") {\n");
			positionIndex++;
			if (statement.getCommands().get(i).getRefinement() != null) {
				for (int j = 0; j < positionIndex; j++) {
					buffer.append("\t");
				}
				buffer.append(constructCodeBlockOfChildStatement(statement.getCommands().get(i).getRefinement(), id));
				positionIndex--;
				for (int j = 0; j < positionIndex; j++) {
					buffer.append("\t");
				}
				buffer.append("}");
			} else {
				for (int j = 0; j < positionIndex; j++) {
					buffer.append("\t");
				}
				buffer.append(constructCodeBlockOfChildStatement(statement.getCommands().get(i), id));
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

	private static String constructComposition(final CompositionStatement statement, final String id) throws TestAndAssertionGeneratorException {
		StringBuffer buffer = new StringBuffer();
		try {
			if (statement.getFirstStatement().getRefinement() != null) {
				buffer.append(constructCodeBlockOfChildStatement(statement.getFirstStatement().getRefinement(), id));
			} else {
				buffer.append(constructCodeBlockOfChildStatement(statement.getFirstStatement(), id));
			}
	
			for (int i = 0; i < positionIndex; i++) {
				buffer.append("\t");
			}
			
			if (statement.getSecondStatement().getRefinement() != null) {
				buffer.append(constructCodeBlockOfChildStatement(statement.getSecondStatement().getRefinement(), id));
			} else {
				buffer.append(constructCodeBlockOfChildStatement(statement.getSecondStatement(), id));
			}
		} catch (TestAndAssertionGeneratorException e) {
			throw e;
		}
		return buffer.toString();
	}
	
	private static String constructSmallRepetition(final SmallRepetitionStatement statement, final String id) throws TestAndAssertionGeneratorException {
		StringBuffer buffer = new StringBuffer();

		String guard = statement.getGuard().getName();
		guard = rewriteGuardToJavaCode(guard);
		
		if(guard.trim().equals("TRUE"))
			guard = "true";
		if(guard.trim().equals("FALSE"))
			guard = "false";
		
		buffer.append("while (" + guard + ") {\n");
		positionIndex++;
		for (int i = 0; i < positionIndex; i++) {
			buffer.append("\t");
		}
		try {
			if (statement.getLoopStatement().getRefinement() != null) {
				buffer.append(constructCodeBlockOfChildStatement(statement.getLoopStatement().getRefinement(), id));
			} else {
				buffer.append(constructCodeBlockOfChildStatement(statement.getLoopStatement(), id));
			}
		} catch (TestAndAssertionGeneratorException e) {
			throw e;
		}
		positionIndex--;
		for (int i = 0; i < positionIndex; i++) {
			buffer.append("\t");
		}
		buffer.append("}\n");
		return buffer.toString();
	}

	// TODO: test this
	static private int findFirstOpenLoop(String code) {
		int start = code.indexOf("while");
		int oBracket = 0;
		int cBracket = 0;
		
		if (start == -1) {
			return -1;
		}
		while (cBracket != -1) {
			oBracket = code.substring(start, code.length()).indexOf("{");
			if (oBracket == -1) {
				return -1;
			}
			cBracket = CodeHandler.findClosingBracketIndex(code, oBracket + start, '{');
			if (cBracket == -1) {
				return oBracket + start;
			}
			code = code.substring(cBracket, code.length());
			start = 0;
		}
		return -1;
	}
	
	static public String genCodeUntilStatement(final CbCFormula formula, final AbstractStatement statement) throws TestAndAssertionGeneratorException {
		var code = new StringBuffer();
		String s;
		if (formula.getStatement().getRefinement() != null) {
			try {
				s = constructCodeBlockOfChildStatement(formula.getStatement().getRefinement(), statement.getId());
				code.append(s);
			} catch (TestAndAssertionGeneratorException e) {
				throw e;
			}
		} else {
			try {
				s = constructCodeBlockOfChildStatement(formula.getStatement(), statement.getId());
				code.append(s); 
			} catch (TestAndAssertionGeneratorException e) {
				Console.println(e.getMessage());
				throw e;
			}
		}
		// now remove all code that comes after the statement
		var cur = code.toString();
		if (cur.contains("TestAndAssertionError")) {
			return "";
		}
		String beforeStatement = cur.substring(0, cur.indexOf(STATEMENT_PH));
		String afterStatement;
		// TODO: test this part
		int loopIndex = findFirstOpenLoop(beforeStatement);
		//int lastLoopIndex = beforeStatement.lastIndexOf("while (");
		if (loopIndex != -1 && CodeHandler.countBrackets(beforeStatement, '{') > 0) {
			int endIndex = CodeHandler.findClosingBracketIndex(cur, loopIndex, '{');
			afterStatement = cur.substring(endIndex + 1, cur.length());
			cur = cur.substring(0, endIndex + 1);
		} else {			
			afterStatement = cur.substring(cur.indexOf(STATEMENT_PH), cur.length());
			cur = cur.substring(0, cur.indexOf(STATEMENT_PH) + STATEMENT_PH.length());
		}
		long numClosing = CodeHandler.countBrackets(afterStatement, '{');
		if (numClosing >= 0) return cur;
		numClosing *= -1;
		for (int i = 0; i < numClosing; i++) {
			cur += "\n" + CodeHandler.getTabs(numClosing-(i+1)) + "}";
		}
		return cur;
	}
	
	public void setShowWarnings(boolean b) {
		this.showWarnings = b;
	}

	public void setProjectPath(URI projectPath) {
		this.projectPath = projectPath;
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
		return code.substring(0, code.indexOf('{') + 1) + "\n" + constructorCode + code.substring(code.indexOf('{') + 1, code.length());
	}
	
	private String removeMethod(String code, String methodSignature) {
		//var split = sSplit(methodSignature, "\\s");
		//var methodName = split[split.length - 1];
		if (!code.contains(methodSignature)) {
			return code.substring(0, code.lastIndexOf("}"));
		}
		String before = code.substring(0, code.indexOf(methodSignature));
		before = before.substring(0, before.lastIndexOf("\n")).trim();
		String after = code.substring(code.indexOf(methodSignature), code.length());
		int closingBracketIndex = CodeHandler.findClosingBracketIndex(code, code.indexOf(methodSignature) + after.indexOf('{'), '{');
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
	
	/**
	 * Generates the code for a CbC method.
	 * @param formula
	 * @param globalConditions
	 * @param renaming
	 * @param vars
	 * @param returnVar
	 * @param signatureString
	 * @param globalVariables
	 * @param gVars
	 * @return the generated java code if the method wasn't generated before. Else it returns an empty string.
	 * @throws TestAndAssertionGeneratorException 
	 */
	private String generateCode(CbCFormula formula, GlobalConditions globalConditions, Renaming renaming, LinkedList<String> vars, JavaVariable returnVar, String signatureString, String globalVariables, List<String> gVars, boolean onlyMethod) throws TestAndAssertionGeneratorException {	
		String existingCode = "";
		String constructorCode;
		StringBuffer code = new StringBuffer();
		final String className;
		
		if(returnVar != null) {
			returnVariable = returnVar; 
		}
			
		if (formula.getMethodObj() == null) {
			className = GENERATED_CLASSNAME;
		} else {
			className = formula.getMethodObj().getParentClass().getName();
		}
		
		// first check if the method already exists
		if (!onlyMethod && !(existingCode = ClassHandler.classExists(this.projectPath, className)).equals("")) {
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
		positionIndex = 2;//2
		code = insertTabs(code);
		
		for(String var : vars) {//declare variables
			if(!var.contains("old_")) {
				var defaultValue = InputData.getDefaultValue(var.split(" ")[0]);//genDefaultInputForVar(var, null).get(0);
				code.append(var + " = " + defaultValue + ";\n");
				code = insertTabs(code);
			}
		}

		if (returnVariable != null && !MethodHandler.getBySignature(code.toString(), signatureString).contains(returnVariable.getName())) {
			code.append(returnVariable.getName() + ";\n");
			code = insertTabs(code);
		}

		String s = genDiagramCode(formula, renaming);
		code.append(s);
			
		var methodCode = code.toString().substring(code.indexOf(signatureString), code.length());
		int closingBracket = methodCode.contains("}") ? methodCode.lastIndexOf('}') : 0;
		var lastPartOfMethod = methodCode.substring(closingBracket, methodCode.length());
		if (returnVariable != null && !lastPartOfMethod.contains("return " + returnVariable.getName().split("\\s")[1])) {
			//if (!void_pattern.matcher(signatureString).find() && !return_pattern.matcher(code.toString()).find()) {
			var splitter = code.toString().trim().split(";");
			if (!splitter[splitter.length - 1].contains("return")) {
				code.append("\t\treturn " + returnVariable.getName().split("\\s")[1] + ";\n");
			}
			//} else if (code.toString().)
		} else if (returnVariable == null && (formula.getMethodObj() == null || !formula.getMethodObj().getReturnType().equals("void"))) {
			throw new TestAndAssertionGeneratorException(ExceptionMessages.RET);
		}
		code.append("\t}");//}
		
		if (!onlyMethod || !existingCode.equals("")) {
			// close class
			code.append("\n}");
		}
		return code.toString();
	}
	
	public static String genDiagramCode(CbCFormula formula, Renaming renaming) {
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

	/**
	 * Generates exactly one instance for the given class *clazz*. 
	 * @param clazz the class to be instantiated with random default data.
	 * @return the string representation for instantiating *clazz* with generated default data as parameters.
	 */
	private String genInstanceForClass(Class<?> clazz, HashMap<String, String> generatedClasses) {
		final String fullyQualifiedName = clazz.getSimpleName();
		final String className = fullyQualifiedName.substring(fullyQualifiedName.lastIndexOf(".") + 1, fullyQualifiedName.length());
		String output = "new " + className + "(";
		if (clazz.getDeclaredFields().length == 0) {
			return output + ")";
		}
			
		if (generatedClasses.get(className) != null) {
			return generatedClasses.get(className);
		}
		for (var field : clazz.getDeclaredFields()) {
			var name = field.getName();
			var type = field.getType().getSimpleName();
			if (type.contains(";")) {
				for (int i = type.length() - 1; i > 0; i--) {
					if (Character.isUpperCase(type.charAt(i))) {
						type = type.substring(i, type.length() - 1);
						break;
					}
				}
			}
			String variable = type + " " + name;
			if (generatedClasses.get(className) == null) {
				generatedClasses.put(className, "");
			}
			output += getRandomInput(genDefaultInputForVar(variable, generatedClasses)) + ", ";
		}
		output = output.substring(0, output.length() - 2) + ")";
		if (output.contains(INSTANCE_TOKEN)) {
			var toReplace = output.substring(output.indexOf(INSTANCE_TOKEN), output.indexOf(INSTANCE_CLOSED_TOKEN) + INSTANCE_CLOSED_TOKEN.length());
			var instanceName = toReplace.substring(INSTANCE_TOKEN.length(), toReplace.length() - INSTANCE_CLOSED_TOKEN.length());
			if (!generatedClasses.get(instanceName).isEmpty()) {
				output = output.replace(toReplace, generatedClasses.get(instanceName));
			}
		}
		generatedClasses.put(className, output);
		return output;
	}
	
	/**
	 * Searches for *className* in the current project and tries to load the class.
	 * @param className the name of the class to find.
	 * @return the loaded class.
	 */
	private Class<?> loadClassFromProject(String className) {
		// TODO: this should search all subfolders of the project path instead of hardcoded subfolders
		// because projects could use different subfolder names/structure.
		className = className.replaceAll("\\[\\.*\\]", "");
		final String loaderPath = FileUtil.getProjectLocation(this.projectPath) + "/";
		Class<?> clazz;
		String path = loaderPath + "tests/" + className + ".class";
		URL[] urls = null;
		try {
			urls = new URL[]{new URL("file://" + loaderPath + "tests/")};
		} catch (MalformedURLException e) {
			Console.println(e.getMessage());
		}
		URLClassLoader cl = new URLClassLoader(urls, getClass().getClassLoader());
		
		// (1) the class was already generated
		File f = new File(path);
		if (f.exists()) {
			try {
				clazz = Class.forName(className, false, cl);
				return clazz;
			} catch (ClassNotFoundException e) {}
		}
		try {
			urls = new URL[]{new URL("file://" + loaderPath + "bin/")};
		} catch (MalformedURLException e) {
			Console.println(e.getMessage());
		}
		cl = new URLClassLoader(urls, getClass().getClassLoader());
		path = loaderPath + "bin/" + className + ".class";
		f = new File(path);
		if (f.exists()) {
			try {
				clazz = Class.forName(className, false, cl);
				return clazz;
			} catch (ClassNotFoundException e) {
				Console.println(e.getMessage());
			}
		}
		// should never happen.
		return null;
	}
	
	private List<String> genDefaultInputForVar(final String v, HashMap<String, String> generatedClasses) {
		final String actualType = v.split("\\s")[0];
		String type = actualType.toLowerCase();
		String name = v.split("\\s")[1];
		
		if (generatedClasses == null) {
			generatedClasses = new HashMap<String, String>();
		}
		if (generatedClasses.get(actualType) != null) {
			if (generatedClasses.get(actualType).isEmpty()) {
				return Arrays.asList(INSTANCE_TOKEN + actualType + INSTANCE_CLOSED_TOKEN);
			}
			return Arrays.asList(generatedClasses.get(actualType));
		}	
		
		if (type.contains("[")) {
			final InputData data = new InputData(name, actualType);
			data.setDefaultValues();
			if (data.isPrimitive()) {
				return Arrays.asList(data.getType() + ";" + data.getName() + ARRAY_TOKEN + data.getArrayValueRep() + ARRAY_CLOSED_TOKEN);	
			} else {
				return Arrays.asList(data.getArrayRep());
			}
		}
		
		if (type.contains("byte")) {
				return Arrays.asList("" + Byte.MIN_VALUE, "" + -1, "" + 0, "" + 1, "" + Byte.MAX_VALUE);
		} else if (type.contains("int")) {
				return Arrays.asList("" + Integer.MIN_VALUE, "" + -1, "" + 0, "" + 1, "" + Integer.MAX_VALUE);
		} else if (type.contains("short")) {
			return Arrays.asList("" + Short.MIN_VALUE, "" + -1, "" + 0, "" + 1, "" + Short.MAX_VALUE);
		} else if (type.contains("long")) {
			return Arrays.asList("" + Long.MIN_VALUE, "" + -1, "" + 0, "" + 1, "" + Long.MAX_VALUE);
		} else if (type.contains("boolean")) {
			if (type.contains("[")) return Arrays.asList("{false}", "{true, false}", "{0}", "{true}", "{false, true}");
			return Arrays.asList("false", "true");
		} else if (type.contains("char")) {
			if (type.contains("[")) return Arrays.asList("{\' \'}", "{\'x\'}", "{\'x\', \'y\'}", "{\'x\', \'y\', \'z\'}", "{\'x\', \'y\', \'z\', \'x\'}");
			return Arrays.asList("\' \'", "\'x\'", "\'1\'", "\'@\'", "\';\'");
		} else if (type.contains("string")) {
			if (type.contains("[")) return Arrays.asList("{\"\"}", "{\"x\"}", "{\"x\", \"y\"}", "{\"x\", \"y\"}", "{\"x\", \"y\", \"z\"}", "{\"x\", \"y\", \"z\", \"x\"}");
			return Arrays.asList("\"\"", "\"x\"", "\"xy\"", "\"1xy\"", "\"1xy@;\"");
		} else {
			// means it is a complex data type. Use reflection.
			var clazz = loadClassFromProject(actualType);
			var input = genInstanceForClass(clazz, generatedClasses);
			return Arrays.asList(input);
		}
	}
	
	private int findMethodEndIndex(String code, String signature) {
		assert(code.contains(signature));
		int bracketDiff = 1;
		int start = code.indexOf(signature) + signature.length();
		int i = 0;
		
		
		code = code.substring(start, code.length());
		start += code.indexOf("\n");
		code = code.substring(code.indexOf("\n"), code.length());
		
		while (bracketDiff != 0 && i < code.length()) {
			var curChar = code.charAt(i);
			if (curChar == '{') {
				bracketDiff++;
			} else if (curChar == '}') {
				bracketDiff--;
			}
			i++;
		}
		if (bracketDiff == 0) {
			return i + start;
		} else {
			return -1;
		}
		
	}
	
	private List<String> getUsedVars(final String code, final String signature, final JavaVariables vars) throws TestAndAssertionGeneratorException {
		assert(code.contains(signature));
		var usedVars = new ArrayList<String>();
		String relevantCode = "";
		if (code.indexOf(signature) == -1) {
			throw new TestAndAssertionGeneratorException(ExceptionMessages.sigNotFound(signature));
		}
		relevantCode = code.substring(code.indexOf(signature), code.length());
		int methodEndIndex = findMethodEndIndex(relevantCode, signature);
		relevantCode = relevantCode.substring(0, methodEndIndex);
		
		for(Field v : vars.getFields()) {
			if (relevantCode.indexOf(v.getName()) != -1) {
				usedVars.add(v.getType() + " " + v.getName());
			}
		}
		usedVars.addAll(vars.getParams()
				.stream()
				.map(v -> v.getType() + " " + v.getName())
				.toList());
		usedVars.addAll(vars.getVariables().stream()
				.filter(v -> v.getKind()
						.toString()
						.equals("PARAM"))
				.map(v -> v.getName())
				.toList());
		return usedVars;
	}
		
	private String getRandomInput(final List<String> lst) {
		if (lst == null) return ""; // happens when a none primitive data type is present
		final StringBuffer content = new StringBuffer();
		final Random rng = new Random();
		int rngNr;
		String concatLst;	
		
		
		lst.stream().forEach(s -> content.append(s + " "));
		concatLst = content.toString();
		if (concatLst.contains(INSTANCE_TOKEN)) {
			var toReplace = concatLst.substring(concatLst.indexOf(INSTANCE_TOKEN), concatLst.indexOf(INSTANCE_CLOSED_TOKEN) + INSTANCE_CLOSED_TOKEN.length());
			var instanceName = toReplace.substring(INSTANCE_TOKEN.length(), toReplace.length() - INSTANCE_CLOSED_TOKEN.length());
			return "new " + instanceName + "()";
		}
		rngNr = rng.nextInt(lst.size());
		return lst.get(rngNr);
	}
		
	private int dataContainsVar(final List<InputData> data, final String varName) {
		if (data == null) {
			return -1;
		}
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).getName().equals(varName)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Generates InputDataTupels. The amount is determined by two factors. First, the amount of used variables n inside the 
	 * method under test. Second, the amount of values for each used variable k_i. Total amount of InputDataTupels and therefore test cases is sum_0^n(k_i).
	 * @param preconditions
	 * @param vars
	 * @param code
	 * @param signature
	 * @param returnVariable
	 * @return
	 * @throws TestAndAssertionGeneratorException 
	 * @throws PreConditionSolverException 
	 * @throws UnexpectedTokenException 
	 */
	public List<TestCaseData> genInputs(final String preconditions, final JavaVariables vars, final String code, final String signature, final JavaVariable returnVariable) throws TestAndAssertionGeneratorException, PreConditionSolverException, UnexpectedTokenException {
		List<String> globalVarsOfClass = new ArrayList<String>();
		List<String> usedVars;
		List<String> params;
		final List<String> allVars = new ArrayList<String>();
		int counter;		
		// maps testcaseName 
		final var output = new ArrayList<TestCaseData>();
		
		// this case happens when the precondition is just 'true' and there are no invariants
		vars.getFields().stream()
		.forEach(f -> globalVarsOfClass.add(f.getType() + " " + f.getName()));
		
		params = new ArrayList<String>();
		if (vars.getParams() != null) {
			vars.getParams().stream().forEach(p -> 
			{
				params.add(p.getType() + " " + p.getName());
			});
			params.addAll(vars.getVariables().stream()
					.filter(v -> v.getKind()
							.toString()
							.equals("PARAM") &&
							!params.contains(v.getName()))
					.map(v -> v.getName())
					.toList());
		}
		
		allVars.addAll(globalVarsOfClass);
		allVars.addAll(params);
		
		// generate only all combinations of inputs for used variables inside the method for all non used just use some value out of the list
		usedVars = getUsedVars(code, signature, vars);
		
		String returnType;
		String returnVar;
		if (returnVariable == null) {
			returnType = "void";
			returnVar = ""; //TODO: set this to the last variable used in a return statement.
		}
		else {
			returnType = returnVariable.getName().split("\\s")[0];
			returnVar = returnVariable.getName().split("\\s")[1];//"result";
		}
			

		// get data from solver for vars that have boundaries defined by preconditions
		PreConditionSolver solver = null;
		List<InputData> solverData = null;
		if (!preconditions.isEmpty()) {
			solver = new PreConditionSolver(vars);
			solver.showWarnings(false);
			try {
				solverData = solver.solve(preconditions);
			} catch (PreConditionSolverException e) {
				throw e;
			}
		}
		// for every variable that is inside solverData, use those values, for every other variable use default values.
		counter = 0;
		for(var usedVar : usedVars) {
			var varName = usedVar.split("\\s")[1];
			var usedVarData = new InputData(usedVar, usedVar.split("\\s")[0]);
			int index;
			if ((index = dataContainsVar(solverData, varName)) == -1) {
				usedVarData.setDefaultValues(); 
			} else {
				usedVarData.setValues(solverData.get(index).getValues());
			}
			for (int x = 0; x < usedVarData.getValues().length; x++) {
				TestCaseData t = new TestCaseData(getMethodName(signature), counter, returnType, returnVar);
				InputData input;
				InputDataTupel data = new InputDataTupel();
				for (var v : allVars) {
					var name = v.split("\\s")[1];
					var type = v.split("\\s")[0];
					int dimensions = (int)type.chars().filter(c -> c == '[').count();
					input = new InputData(name, type);
					if ((index = dataContainsVar(solverData, name)) == -1) {
						input.setDefaultValues();
					} else {
						input.setValues(solverData.get(index).getValues());
					}
					if (!isBuiltInType(type) && usedVars.contains(v)) {
						// need to initialize all variables	
						if (v.contains("[")) {
							if (v.lastIndexOf("]") == -1) {
								if (showWarnings) {
									Console.println("TestAndAssertionGeneratorWarning: Syntax of variable '" + v + "' is not correct.");
								}
								FileHandler.log(this.projectPath, "TestAndAssertionGeneratorWarning: Syntax of variable '" + v + "' is not correct.");
								continue;
							}
							v = v.substring(0, v.indexOf("[")) + v.substring(v.lastIndexOf("]") + 1, v.length());
						}
						var lst = genDefaultInputForVar(v, null);
						String[] values = new String[lst.size()];
						for (int i = 0; i < lst.size(); i++) values[i] = lst.get(i);
						input.setValues(values);
					}
					if (dimensions == 0) {
						input.getRandomValue();
					} else if (input.isPrimitive()) {
						input.getPrimitiveArrayInit();
					} else {
						input.getArrayRep();
					}
					if (params.contains(type + " " + name)) {
						data.addParameter(input);
					} else {
						data.addGlobalVar(input);
					}
				}
				t.setInputDataTupel(data);
				output.add(t);
				counter++;
			}
		}
		return output;
	}
				
	private String genTestCases(final String className, final List<TestCaseData> inputs, final String postCondition, final GlobalConditions conds, final CbCFormula formula) throws TestStatementException, UnexpectedTokenException {
		var instanceName = Character.toLowerCase(className.charAt(0)) + className.substring(1, className.length());
		if (inputs.isEmpty()) {
			Console.println("TestAndAssertionInfo: No input data was generated.");
			return "";
		}
		var translatedPostCondition = ConditionHandler.translateConditionToJava(projectPath, postCondition, instanceName, inputs.get(0).getInputDataTupel().getGlobalVars());
				
		StringBuffer code = new StringBuffer();
		code.append(ClassHandler.getImportsStr());
		code.append("public class " + className + "Test {\n");
		code.append("\t" + "private " + className + " " + instanceName + ";\n\n");
		for (var test : inputs) {
			code.append("\t@Test(singleThreaded = true)\n\t" + "public void " + test.getName() + "(ITestContext context) {\n");

			// add primitive arrays needed for class instatiation
			for (InputData data : test.getInputDataTupel().getAllVars()) {
				if (data.getRep().contains(ARRAY_TOKEN)) {
					//private String handlePrimitiveArrayUses(String output, String fullVarName, String val) 
					var toAdd = CodeHandler.handlePrimitiveArrayUses("", data.getType() + " " + data.getName(), data.getRep(), 2);
					var lines = toAdd.split("\\t\\t");
					var newRep = lines[lines.length-1].substring(lines[lines.length-1].indexOf("new"), lines[lines.length-1].lastIndexOf(";"));
					data.setRep(newRep);
					for (int i = 0; i < lines.length - 1; i++) {
						if (lines[i].isBlank()) {
							continue;
						}
						code.append(lines[i] + "\n");
					}
				}
				if (data.isPrimitive() && data.getDimensions() > 0) {
					code.append("\t\t" + data.getPrimitiveArrayInit() + ";\n");
				}
			}
			
			code.append("\t\t" + instanceName + " = new " + className + test.getInputDataTupel().getGlobalVarsRep() + ";\n");		
			
			// add variables to test for each parameter of the method
			for (InputData param : test.getInputDataTupel().getParameters()) {
				if (!param.isPrimitive() || param.getDimensions() == 0) {
					code.append("\t\t" + param.getType() + " " + param.getName() + " = " + param.getRep() + ";\n");
				}
			}

			// generate variables for old vars
			var oldVarsStr = ConditionHandler.initializeOldVars(translatedPostCondition.get(), instanceName, 2, test.getInputDataTupel().getParameterNames());
			if (!oldVarsStr.equals("")) {
				code.append(oldVarsStr);
			} else {
				code.append("\n");
			}
			
			// add precondition checks
			var programPreConsStr = formula.getStatement().getPreCondition().getName();
			var programPreCons = ConditionHandler.translateConditionToJava(this.projectPath, programPreConsStr, instanceName, test.getInputDataTupel().getGlobalVars());
			try {
				code.append(CodeHandler.insertPreconditionChecks("\n\n", programPreCons, 2).replaceFirst("\\n", ""));
			} catch (TestStatementException e) {
				throw e;
			}
			
			// add method call
			if (test.getReturnType().equals("void")) {
				code.append("\t\t" + instanceName + "." + test.getTesteeName() + test.getInputDataTupel().getParametersNameRep() + ";\n\n");
			} else {
				code.append("\t\t" + test.getReturnType() + " " + test.getReturnVar() + " = " + instanceName + "." + test.getTesteeName() + test.getInputDataTupel().getParametersNameRep() + ";\n");
				code.append("\t\t" + "context.setAttribute(\"" + test.getTestNumber() + "result\", " + test.getReturnVar() + ");\n\n");
			}
			
			// add post condition
			var translatedPostConditionNew = ConditionHandler.translateConditionToJava(projectPath, postCondition, instanceName, inputs.get(0).getInputDataTupel().getGlobalVars());
			code.append(translatedPostConditionNew.getWithContext(test.getTestNumber(), inputs, instanceName));
			code.append("\n\t}\n\n");
		}
		/*
		// append after each method that clears all previous attributes after each method run
		code.append("\t@AfterMethod\n");
		code.append("\tpublic void clearContext(ITestContext context) {\n");
		for (int i = 0; i < inputs.size(); i++) {
			code.append("\t\tcontext.removeAttribute(\"" + inputs.get(i).getName() + "\");\n");
		}
		code.append("\t}\n"); */
		code.append("}\n");
		return code.toString(); 
	}

	public XmlSuite createXmlSuite(final String classPath, final String className) {
		final XmlSuite suite = new XmlSuite();
		URLClassLoader externalClassLoader = null;
		
		suite.setName("AutoSuite");

		try {
			URL[] urls = {new URL(classPath)};
			externalClassLoader = new URLClassLoader(urls, getClass().getClassLoader());		
		} catch (MalformedURLException e1) {
			//log(e1.getMessage());
			return null;
		}	
		 
		XmlTest test = new XmlTest(suite);
		test.setName("Test results for class " + className);
		List<XmlClass> classes = new ArrayList<XmlClass>();
		try {
			XmlClass clazz = new XmlClass(Class.forName(className, false, externalClassLoader));// using reflection here because xmlclass's loader seems to be broken
			classes.add(clazz);
		} catch (ClassNotFoundException e) {
			return null;
		}
		test.setXmlClasses(classes);
		return suite;
	}
	
	private void executeTestCases(final String classPath, String className, final List<String> globalVars, final List<TestCaseData> inputs) {
		final XmlSuite suite;
		var pathOfPlugins = System.getProperty("osgi.syspath");
		var file = new File(pathOfPlugins);
		List<String> testNgFiles = Arrays.asList(file.list()).stream().filter(s -> s.contains("org.testng_")).toList();
		var highestVersion = testNgFiles.stream().map(s -> s.split("org.testng\\_")[1]).sorted().reduce((first, second) -> second).get();
		highestVersion = "org.testng_" + highestVersion;		
		var splitter = className.split("\\.");
		className = splitter[0];
		
		// compile test file
		var options = Arrays.asList("-cp", pathOfPlugins + "/" + highestVersion);
		if(!compileClass(className, options, false)) {
			Console.println("Stop testing...");
			return;
		}
		
		// first create the xml suite needed to run TestNG
		suite = createXmlSuite(classPath, className);
	
		
		// now start testng using the custom test listener
		var tla = new TestAndAssertionListener(projectPath, className, globalVars, inputs);
		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		suite.setParallel(ParallelMode.NONE);
		suites.add(suite);
		TestNG tng = new TestNG();
		tng.setUseDefaultListeners(false);
		tng.setParallel(ParallelMode.NONE);
		tng.setXmlSuites(suites);
		tng.addListener(tla);
		tng.run();
	}
	
	public String genCode(Diagram diagram, boolean onlyMethod) throws TestAndAssertionGeneratorException {
		String signatureString;
		final LinkedList<String> localVariables = new LinkedList<String>();
		JavaVariable returnVariable = null;
		JavaVariables vars = null;
		Renaming renaming = null;
		CbCFormula formula = null;
		GlobalConditions globalConditions = null;
		String globalVariables = "";
		int counter = 0;

		for (Shape shape : diagram.getChildren()) {
			Object obj = getBusinessObjectForPictogramElement(shape);
			if (obj instanceof JavaVariables) {
				vars = (JavaVariables) obj;
			} else if (obj instanceof Renaming) {
				renaming = (Renaming) obj;
			} else if (obj instanceof CbCFormula) {
				formula = (CbCFormula) obj;
			} else if (obj instanceof GlobalConditions) {
				globalConditions = (GlobalConditions) obj;
			}
		}
		
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
		/*
		for (var v : vars.getParams()) {
			final var name = v.getName().replace("non-null", "");
			if (!signatureString.contains(name)) {
				// means this is the RETURN var
				localVariables.add(v.getType() + " " + name);
				if (!name.equals("result")) {
					localVariables.add(v.getType() + " result");
					customReturnName = name;
				}
			}
		}*/
		
		for(int i = 0; i < vars.getVariables().size(); i++) {
			JavaVariable currentVariable = vars.getVariables().get(i);
			if (currentVariable.getKind() == VariableKind.RETURN) {
				var varName = currentVariable.getName().replace("non-null", "").split("\\s")[1];
				var varType = currentVariable.getName().split("\\s")[0];
				localVariables.add(currentVariable.getName().replace("non-null", ""));
				counter++;
				if(!signatureString.substring(0, signatureString.indexOf('(')).contains(currentVariable.getName().replace("non-null", "").trim().split("\\s")[0])) {
					Console.clear();
					Console.println("Method return type and variable type does not match.");
					return "";
				}
				if(counter > 1) {
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
					globalVariables += ("\t" + field.getVisibility().getName().toLowerCase() + " static " + field.getType() + " " + field.getName().replace("non-null ", "") + ";\n");
				} else {
					globalVariables += ("\t" + field.getVisibility().getName().toLowerCase() + " " + field.getType() + " " + field.getName().replace("non-null ", "") + ";\n");			
				}
			}
		}
		
		return generateCode(formula, globalConditions, renaming, localVariables, returnVariable, signatureString, globalVariables, vars.getFields().stream().map(f -> f.getType() + " " + f.getName()).toList(), onlyMethod);
	}
	
	private String genCode(Diagram diagram) throws TestAndAssertionGeneratorException {
		return genCode(diagram, false);
	}
				
	/**
	 * Gets every diagram for each method name in diagramNames and creates a map which has every occurring class as key. 
	 * As values each class has a LinkedList which contains all diagrams of it's methods.
	 * @param diagramNames The map of methods mapped to their class.
	 * @return The map.
	 */
	public HashMap<String, LinkedList<Diagram>> getDiagramsFromNames(HashMap<String, String> diagramNames) {
		return getDiagramsFromNames(diagramNames, null);
	}
	
	/**
	 * Gets every diagram for each method name in diagramNames and creates a map which has every occurring class as key. 
	 * As values each class has a LinkedList which contains all diagrams of it's methods.
	 * @param diagramNames The map of methods mapped to their class.
	 * @param allDiagrams A collection of all avaiable diagrams in the project.
	 * @return The map.
	 */
	public HashMap<String, LinkedList<Diagram>> getDiagramsFromNames(HashMap<String, String> diagramNames, final Collection<Diagram> allDiagrams) {
		// method is really slow because getDiagrams takes long
		HashMap<String, LinkedList<Diagram>> output = new HashMap<String, LinkedList<Diagram>>();
		Collection<Diagram> diagrams;
		
		
		if (allDiagrams != null) {
			diagrams = allDiagrams;
		} else {
			diagrams = null;//getDiagrams();
		}	
		for (var diagram: diagrams) {
			if (diagramNames.size() == 0) { 
				return output;
			}
			if(diagramNames.containsKey(diagram.getName())) {
				var className = diagramNames.get(diagram.getName()); 
				if(!output.containsKey(className)) {
					output.put(className, new LinkedList<Diagram>());
				} 
				output.get(className).add(diagram);
				diagramNames.remove(diagram.getName());
			}
		}
		return output;
	}
	
	private boolean isVarType(String potentialType) {
		if (isBuiltInType(potentialType)) {
			return true;
		}
		// look if there is a java file that name or a cbcclass.
		if (getProjectJavaFiles().contains(potentialType)) {
			return true;
		}
		var cbcClasses = FileUtil.getCbCClasses(FileUtil.getProject(projectPath));
		for (var cbcClass : cbcClasses) {
			if (cbcClass.getClass().getName().equals(potentialType)) {
				return true;
			}
		}
		return false;
	}
	
	public String extractVarType(final String code, final String varName) {
		if (!code.contains(varName)) {
			return "";
		}
		final var firstOccurence = code.substring(0, code.indexOf(" " + varName));
		final var typeReversed = new StringBuffer(firstOccurence).reverse().toString().trim().split("\\W", 2)[0];
		final var type = new StringBuffer(typeReversed).reverse().toString();
		if (isVarType(type)) {
			return type;
		}
		return "";
	}
	
	/**
	 * Tries finding the type of variable *varName*.
	 * @param code
	 * @param varName
	 * @param className
	 * @return The type of variable *varName* or empty string if *varName* couldn't be found.
	 */
	private String getVariableType(String code, String varName, String className) {
		// idea: find first occurence of *varName* in *code*. If there is a word x such that: 'x *varName*'
		// we know that x must be a type of x or is the class itself. If there is no such word there are two options:
		// 1. There is no word infront of *varName* => *varName* is a static class variable. Therefore
		// 		it's type is *className*.
		// 2. There is a prefix y such that 'y.x' => if y is 'this' return *className* else return y.
		String varType;
		String classCode;
		String potentialVarType;
		final Pattern p = Pattern.compile("[^\\w]" + Pattern.quote(varName) + "[^\\w]");
		final Matcher m = p.matcher(code);
		
		if (varName.equals("this") || varName.equals("result")) {
			return className;
		}
		
		if (!m.find()) {
			// try searching in the entire class
			classCode = ClassHandler.classExists(this.projectPath, className);
			return extractVarType(classCode, varName);
		}
		var firstOccurence = code.substring(0, m.start() + (m.end() - m.start() - 1));
		if (firstOccurence.lastIndexOf(' ') == - 1) {
			// no space infront of *varName*
		}
		var foundVarName = firstOccurence.substring(m.start() + 1, firstOccurence.length());
		/*if (foundVarName.contains("(")) {
			foundVarName = foundVarName
		}*/
		if (foundVarName.equals(varName)) {
			potentialVarType = firstOccurence.substring(0, m.start());
			final var potentialTypeReversed = new StringBuffer(potentialVarType).reverse().toString().trim().split("\\W", 2)[0];
			potentialVarType = new StringBuffer(potentialTypeReversed).reverse().toString();
			if (isVarType(potentialVarType)) {
				varType = potentialVarType;
				return varType;
			} else {
				// check if it's a class
				if (!ClassHandler.classExists(this.projectPath, foundVarName).isEmpty()) {
					return foundVarName;
				}
				// check if it's declared in a parameter
				if (potentialVarType.contains("(")) {
					potentialVarType = potentialVarType.substring(potentialVarType.lastIndexOf('(') + 1, potentialVarType.length());
					if (isVarType(potentialVarType)) {
						varType = potentialVarType;
						return varType;
					}
				}
				// word before *varName* is not a type, meaning *varName* is a static class variable.
				// get code of the current class.
				classCode = ClassHandler.classExists(this.projectPath, className);
				// find the variable
				varType = extractVarType(classCode, varName);
				if (varType.equals("")) {
					// this should never happen.
					if (showWarnings) {
						Console.println("TestAndAssertionGeneratorWarning: Variable type of variable " + varName + " couldn't be found.");
					}
					FileHandler.log(this.projectPath, "TestAndAssertionGeneratorWarning: Variable type of variable " + varName + " couldn't be found.");
					return "";
				} 
				return varType;
			}			
		} else {
			// this means there is no space infront of *varName* => *varName* 
			// check if there is a bracket infront of *varName*
			if (foundVarName.charAt(foundVarName.lastIndexOf(varName) - 1) == '(') {
				foundVarName = foundVarName.substring(foundVarName.lastIndexOf(varName), foundVarName.length());
				if (isVarType(foundVarName)) {
					return foundVarName;
				}
			} else if ((foundVarName.charAt(foundVarName.lastIndexOf(varName) - 1) == '<')) {
				foundVarName = foundVarName.substring(foundVarName.lastIndexOf(varName), foundVarName.length());
				if (isVarType(foundVarName)) {
					return foundVarName;
				}
			}
			// is either a static class variable or a field.
			int charIndexBefore = code.indexOf(varName) - 1;
			if (charIndexBefore == -1) {
				classCode = ClassHandler.classExists(this.projectPath, className);
				// static variable
				varType = extractVarType(classCode, varName);
				if (varType.equals("")) {
					// this should never happen.
					if (showWarnings) {
						Console.println("TestAndAssertionGeneratorWarning: Variable type of variable " + varName + " couldn't be found.");
					}
					FileHandler.log(this.projectPath, "TestAndAssertionGeneratorWarning: Variable type of variable " + varName + " couldn't be found.");
					return "";
				} 
				return varType;
			} else {
				if (code.charAt(charIndexBefore) == '.') {
					potentialVarType = code.substring(0, charIndexBefore);
					if (potentialVarType.lastIndexOf(' ') == -1) {
						if (showWarnings) {
							Console.println("TestAndAssertionGeneratorWarning: Variable type of variable " + varName + " couldn't be found.");
						}
						FileHandler.log(this.projectPath, "TestAndAssertionGeneratorWarning: Variable type of variable " + varName + " couldn't be found.");
						return "";
					}
					potentialVarType = potentialVarType.substring(potentialVarType.lastIndexOf(' ') + 1, potentialVarType.length());
					if (isVarType(potentialVarType)) {
						varType = potentialVarType;
						return varType;
					} else {
						if (potentialVarType.equals("this")) {
							varType = className;
							return varType;
						}
					}
				} else {
					// this shouldn't happen because the syntax of the code would not be correct.
					if (showWarnings) {
						Console.println("TestAndAssertionGeneratorWarning: Variable type of variable " + varName + " couldn't be found.");
					}
					FileHandler.log(this.projectPath, "TestAndAssertionGeneratorWarning: Variable type of variable " + varName + " couldn't be found.");
					return "";
				}
			}
		}
		if (showWarnings) {
			Console.println("TestAndAssertionGeneratorWarning: Variable type of variable " + varName + " couldn't be found.");
		}
		FileHandler.log(this.projectPath, "TestAndAssertionGeneratorWarning: Variable type of variable " + varName + " couldn't be found.");
		return "";
	}
	
	private boolean codeContainsMethodDefinition(String code, String methodName) {
		final Pattern p = Pattern.compile(Pattern.quote(methodName + "("));
		final Matcher m = p.matcher(code);
		String helper;

		
		while (m.find()) {
			helper = code.substring(m.start(), code.length());
			int endIndex = m.start() + helper.indexOf(")") + 1;
			for (int i = endIndex; i < code.length(); i++) {
				if (code.charAt(i) == '{') {
					return true;
				} else if (code.charAt(i) == ' ') 
				{
					// empty because whitespaces are valid therefore we will move on in the search for a {-bracket.				
				} else {
					break;
				}		
			}
		}
		return false;
	}
	
	public static boolean isBuiltInType(String type) {
		return type.contains("boolean") 
				| type.contains("byte")
				| type.contains("char") 
				| type.contains("short") 
				| type.contains("int") 
				| type.contains("long") 
				| type.contains("String");
	}
	
	/**
	 * Loads code of the given *className* in *subFolderName*.
	 * @param className
	 * @return The file name of the class with name *className*.
	 */
	private String loadFileFromClass(String className, String subFolderName) {
		final java.nio.file.Path p = Paths.get(FileUtil.getProjectLocation(projectPath) + "/" + subFolderName + "/" + className + ".java");
		final File f;
		
		try {
			f = new File(p.toString());
			if (!f.exists()) {
				return "";
			}
			var code = Files.readString(p);
			code = CodeHandler.removeAllComments(code);
			return code;
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}
		
	/**
	 * Parses all method dependencies inside code and returns a map which maps each method to it's class.
	 * @param code
	 * @return the map.
	 */
	public HashMap<String, String> getDiagramNamesFromCalledMethods(final String code, final String className) {
		final HashMap<String, String> output = new HashMap<String, String>();
		final Pattern methodCallPattern = Pattern.compile("\\w+\\.\\w+\\(");	
		var methods = ConditionHandler.getAllMethodsOfCode(code);

		
		for (final var method : methods) {
			final var lines = method.split("\n");
			for (final var line : lines) {
				Matcher matcher = methodCallPattern.matcher(line);
				while(matcher.find()) {
					final var methodCallParts = matcher.group(0).split("\\.", 2);
					var methodName = methodCallParts[1];
					final var varName = methodCallParts[0];
					methodName = methodName.replaceAll("\\(", "");
					final var type = getVariableType(code, varName, className);
					if (!type.equals(className) || !codeContainsMethodDefinition(code, methodName)) {
						// make sure the key value pair doesn't exists already
						if (!output.keySet().contains(methodName) || !output.get(methodName).equals(type)) {
							output.put(methodName, type);
						}
					}
				}
			}

		}
		return output;
	}
			
	private void loadInternalClasses() {
		var project = FileUtil.getProject(this.projectPath);
		var cbcClasses = FileUtil.getCbCClasses(project);
		for (var cbcClass : cbcClasses) {
			var modelClass = (ModelClass)cbcClass.getContents().get(0);
			var name = modelClass.getName();
			this.projectInternalClasses.add(name);
		}
	}
	
	private List<String> getProjectInternalClasses() {
		if (this.projectInternalClasses == null) {
			this.projectInternalClasses = new ArrayList<String>();
			loadInternalClasses();
		}
		return this.projectInternalClasses;
	}
	
	private List<String> getProjectJavaFiles() {
		if (this.projectJavaFiles == null) {
			this.projectJavaFiles = new ArrayList<String>();
			loadAllJavaFilesFromProject();
		}
		return this.projectJavaFiles;
	}
	
	private boolean isClass(String className) {
		if(getProjectInternalClasses().contains(className)) {
			return true;
		}
		if(getProjectJavaFiles().contains(className)) {
			return true;
		}
		return false;
	}
	
	private boolean isExternalClass(String className) {
		if(getProjectInternalClasses().contains(className)) {
			return false;
		}
		if(!getProjectJavaFiles().contains(className)) {
			return false;
		}
		return true;
	}
				
	private Diagram loadDiagramFromClass(String className, String diagramName) {
		return FileHandler.loadDiagramFromClass(this.projectPath, className, className, diagramName);
	}
	
	private List<String> findAllDistinctWords(String code, char wordDelim) {
		var output = new ArrayList<String>();
		int nextIndex = code.indexOf(wordDelim);
		int startIndex;
		String helper;
		
		
		while (nextIndex != -1) {
			helper = code.substring(nextIndex + 1, code.length());
			startIndex = nextIndex + 1;
			if (startIndex >= code.length()) {
				break;
			}
			if (!Character.isAlphabetic(code.charAt(nextIndex + 1))) {
				nextIndex += 1;
				if (nextIndex > code.length() - 1) {
					break;
				}
				continue;
			}
			while (Character.isAlphabetic(code.charAt(nextIndex + 1))) {
				nextIndex++;
			}
			if (nextIndex > startIndex + 1) {
				helper = helper.substring(0, nextIndex - startIndex + 1).trim();
				if (!output.contains(helper)) {
					output.add(helper);
				}
			}
			if (nextIndex + 1 > code.length() - 1) {
				break;
			}
			helper = code.substring(nextIndex + 1, code.length());
			nextIndex = helper.indexOf(wordDelim) + nextIndex + 1;
		}
		return output;
	}
	
	private List<String> getDependencies(final String className, List<String> output) {
		String code = ClassHandler.classExists(this.projectPath, className);
		
		if (output == null) {
			output = new ArrayList<String>();
		}
		var words = findAllDistinctWords(code, ' ');
		words.addAll(findAllDistinctWords(code, '('));
		words.addAll(findAllDistinctWords(code, '['));
		words.addAll(findAllDistinctWords(code, '<'));
		words.addAll(findAllDistinctWords(code, '{'));
		
		for (var word : words) {
			if (output.contains(word)) {
				continue;
			}
			if (isClass(word)) {
				output.add(word);
				var innerDependencies = getDependencies(word, output);
				for (var d : innerDependencies) {
					if (!output.contains(d)) {
						output.add(d);
					}
				}
			}
		}
		return output;
	}
		
	/**
	 * Determines all dependencies of class *className* and compiles the class using options *options*.
	 * @param className
	 * @param options
	 * @return Whether successful.
	 */
	public boolean compileClass(String className, List<String> options, boolean delete) {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
		var fullClassName = FileUtil.getProjectLocation(this.projectPath) + "/tests/" + className + ".java";
		var dependenciesNames = getDependencies(className, null);
		var dependencies = new ArrayList<String>();
		for (var d : dependenciesNames) {
			d = FileUtil.getProjectLocation(this.projectPath) + "/tests/" + d + ".java";
			if (!dependencies.contains(d)) {
				dependencies.add(d);
			}
		}
		if (!dependencies.contains(fullClassName)) {
			dependencies.add(fullClassName);
		}
		
		Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromStrings(dependencies);
		JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, options,
		        null, compilationUnits);
		boolean wasSuccessful = task.call();
		if (!wasSuccessful) {
			var errorMsgs = diagnostics.getDiagnostics();
			for (var errorMsg : errorMsgs) {
				Console.println();
				Console.println(" > Syntax error detected.", red);
				Console.println("\t" + errorMsg.toString());
				Console.println();
			}
			if (delete) {
				FileHandler.deleteFile(this.projectPath, className);
			}
			return false;
		}
		try {
			fileManager.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean compileFileContents(List<String> fileContents, String methodToGenerate, final List<String> options) throws TestAndAssertionGeneratorException {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
		List<String> dependencies = new ArrayList<String>();
		
		
		// determine all class dependencies
		for (var code : fileContents) {
			code = code.trim();
			if (code.isEmpty()) {
				throw new TestAndAssertionGeneratorException(ExceptionMessages.GENCODE);
			}
			var className = code.split("public\\sclass\\s")[1].split("\\s", 2)[0];
			className = className.replaceAll("\\{", "");
			var fullClassName = FileUtil.getProjectLocation(this.projectPath) + "/tests/" + className + ".java";
			FileHandler.createFile(this.projectPath, className, code);
			dependencies.add(fullClassName);
		}
		
		// start compiling all needed classes.
		Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromStrings(dependencies);
		JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, options,
		        null, compilationUnits);
		boolean wasSuccessful = task.call();
		if (!wasSuccessful) {
			var errorMsgs = diagnostics.getDiagnostics();
			for (var errorMsg : errorMsgs) {
				Console.println();
				Console.println(" > Syntax error detected.", red);
				Console.println("\t" + errorMsg.toString());
				FileHandler.log(dependencies.get(0));
				Console.println();
			}
			for (var code : fileContents) {
				var className = code.split("\\s", 4)[2];
				//deleteFile(className);
			}
			return false;
		}
		try {
			fileManager.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean compileFileContents2(List<ClassHandler> fileContents, String methodToGenerate, final List<String> options) throws TestAndAssertionGeneratorException {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
		List<String> dependencies = new ArrayList<String>();
		
		
		// determine all class dependencies
		for (var clazz : fileContents) {
			var className = clazz.getClassName();
			var fullClassName = FileUtil.getProjectLocation(this.projectPath) + "/tests/" + className + ".java";
			FileHandler.createFile(this.projectPath, className, clazz.getCode());
			dependencies.add(fullClassName);
		}
		
		// start compiling all needed classes.
		Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromStrings(dependencies);
		JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, options,
		        null, compilationUnits);
		boolean wasSuccessful = task.call();
		if (!wasSuccessful) {
			var errorMsgs = diagnostics.getDiagnostics();
			for (var errorMsg : errorMsgs) {
				Console.println();
				Console.println(" > Syntax error detected.", red);
				Console.println("\t" + errorMsg.toString());
				Console.println();
			}
			for (var clazz : fileContents) {
				var className = clazz.getClassName();
				//deleteFile(className);
			}
			return false;
		}
		try {
			fileManager.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean compileFileContents(List<String> fileContents, String methodToGenerate) throws TestAndAssertionGeneratorException {
		return compileFileContents(fileContents, methodToGenerate, null);
	}
	
	public boolean compileFileContents2(List<ClassHandler> fileContents, String methodToGenerate) throws TestAndAssertionGeneratorException {
		return compileFileContents2(fileContents, methodToGenerate, null);
	}
	
	private boolean loadAllJavaFilesFromProject() {
		var projectLocation = FileUtil.getProjectLocation(this.projectPath);
		var project = FileUtil.getProject(this.projectPath);
		var javaFiles = FileUtil.getFilesFromProject(project, ".java");
		File f;
		java.nio.file.Path p;
		java.nio.file.Path source;
		
		
		for (var javaFile : javaFiles) {
			this.projectJavaFiles.add(javaFile.getName().substring(0, javaFile.getName().indexOf('.')));
			// copy file into tests folder
			var testFolder = java.nio.file.Paths.get(projectLocation + "/tests");
			p = java.nio.file.Paths.get(projectLocation + "/tests/" + javaFile.getName());
			source = Paths.get(projectLocation + "/" + javaFile.getProjectRelativePath().toOSString());
			try {
				var folder = new File(testFolder.toString());
				if (!folder.exists()) {
					folder.mkdir();
				}
				f = new File(p.toString());
				if (!f.exists()) {
					String code = Files.readString(source);
					code = CodeHandler.removeAllComments(code);
					f.createNewFile();
					Files.write(p, code.getBytes(), StandardOpenOption.WRITE);
				}
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;	
	}
	
	
	
	/**
	 * Gets all dependencies (method calls and constructor calls) inside *methodCode*.
	 * @param methodCode The code of the method for which to get the dependencies.
	 * @param className The name of the class which contains *MethodCode*.
	 * @return Contents of the classes.
	 * @throws TestAndAssertionGeneratorException 
	 */
	public List<ClassHandler> genAllDependenciesOfMethod(String methodCode, final String methodSignature, String className, String postCondition) throws TestAndAssertionGeneratorException
	{
		var innerMethod = methodCode.substring(methodCode.indexOf('{') + 1, methodCode.lastIndexOf('}'));
		// add postCondition to the code in case there are method calls in there too
		innerMethod += "\n" + postCondition;
		innerMethod = innerMethod.replaceAll("\\\\", "");
		var allDiagramNames = getCalledMethods(methodCode, innerMethod, className);//getDiagramNamesFromCalledMethods(methodCode, className);
		final var classCodes = new ArrayList<ClassHandler>();
		ArrayList<MethodHandler> newMethodCodes = new ArrayList<MethodHandler>();
		final var addedMethods = new HashMap<String, List<MethodHandler>>();
		final ArrayList<String> addedClassNames = new ArrayList<String>();
		methodCode = methodCode.trim();
		final var lst = new ArrayList<String>();
		String keyToRemove = null;
		String methodSigToAdd = null;
		
		while (allDiagramNames.keySet().size() > 0) {
			String curClassName;
			lst.clear();
			lst.addAll(allDiagramNames.keySet());
			curClassName = lst.get(0);
			addedClassNames.add(curClassName);
			lst.clear();
			lst.addAll(allDiagramNames.get(curClassName));
			for (var methodSig : lst) {
				if (methodSig.contains("{")) {
					keyToRemove = methodSig;
					methodSig = MethodHandler.getSignatureFromCode(methodSig);
					methodSigToAdd = methodSig;
					allDiagramNames.get(curClassName).remove(keyToRemove);
					allDiagramNames.get(curClassName).add(methodSigToAdd);
				}
				if (addedMethods.get(curClassName) != null && addedMethods.get(curClassName).contains(methodSig)) {
					continue;
				}
				// gen code of all used methods of all classes
				var innerClassesWithDep = genMethodDependencies2(curClassName, methodSig, null);
				for (var innerClass: innerClassesWithDep) {
					if (innerClass.getClassName().equals(curClassName)) {
						// means there were more needed methods for class *curClassName*
						if (addedMethods.get(curClassName) == null) {
							addedMethods.put(curClassName, new ArrayList<MethodHandler>());
						}	
						for (final var newM : innerClass.getAllMethods()) {
							boolean added = false;
							for (final var addedM : addedMethods.get(curClassName)) {
								if (addedM.getSignature().equals(newM.getSignature())) {
									added = true;
									break;
								}
							}
							if (!added) {
								newMethodCodes.add(newM);
								// mark sig ad generated
								addedMethods.get(curClassName).add(newM);
							}
						}
					} else {
						// means there are even more dependencies of other classes to be generated
						if (allDiagramNames.get(innerClass.getClassName()) == null) {
							// class was not a outer dependency
							allDiagramNames.put(innerClass.getClassName(), new ArrayList<String>());
						}
						for (MethodHandler m : innerClass.getAllMethods()) {
							allDiagramNames.get(innerClass.getClassName()).add(m.getCode());
						}
					}
				}
			}
			// at this point, all needed methods of class *curClassName* are inside of newMethodCodes.
			// if the class was previously added, skip it
			if (addedClassNames.stream().filter(c -> c.equals(curClassName)).count() > 1) {
				allDiagramNames.remove(curClassName);
				continue;
			}
			
			var clazz = new ClassHandler(curClassName, this.projectPath);
			
			// look if methodCode starts with {, it means it comes from statement testing
			// and we do not want to add that partial method.
			if (curClassName.equals(className) && !methodCode.trim().startsWith("{")) {
				// add the original calling method here as well
				MethodHandler callingMethod = new MethodHandler(methodSignature == null ? "" : methodSignature, methodCode);
				newMethodCodes.add(callingMethod);
			}
			
			clazz.addMethods(newMethodCodes);
			classCodes.add(clazz);
			newMethodCodes.clear();
			// remove all added sigs from the to generate list
			for (var curClazz : addedMethods.keySet()) {
				for (var methodSig : addedMethods.get(curClazz)) {
					if (allDiagramNames.get(curClazz) == null) {
						continue;
					}
					while (allDiagramNames.get(curClazz).contains(methodSig)) {
						allDiagramNames.get(curClazz).remove(methodSig);
					}
				}
				if (allDiagramNames.get(curClazz) != null && allDiagramNames.get(curClazz).isEmpty()) {
					allDiagramNames.remove(curClazz);
				}
			}
		}
		if (classCodes.isEmpty() || !addedClassNames.contains(className)) {
			// means the method itself has no dependencies.
			var clazz = new ClassHandler(className, this.projectPath);
			if (methodSignature != null) {
				clazz.addMethod(methodSignature, methodCode);
			}
			classCodes.add(clazz);
			addedClassNames.add(className);
		}
		// every type that is not built in the generator but is needed in a constructor of a class
		// needs to be generated too but just without any methods in them
		
		for (int i = 0; i < classCodes.size(); i++) {
			var classes = getUsedClasses(classCodes.get(i).getCode());
			for (var clazz : classes) {
				if (!addedClassNames.contains(clazz)) {
					var c = new ClassHandler(clazz, this.projectPath);
					classCodes.add(c);
					addedClassNames.add(clazz);
				}
			}
		}
		return classCodes;
	}
	
	/**
	 * Returns the name of every class that is used at least once in *code*.
	 * @param code
	 * @return
	 */
	private List<String> getUsedClasses(String code) {
		final List<String> classes = new ArrayList<String>();
		final Pattern p = Pattern.compile("\\W*\\w+\\W*");
		final Matcher m = p.matcher(code);
		
		while (m.find()) {
			var word = m.group(0).replaceAll("\\W", "");
			if (isClass(word) && !classes.contains(word)) {
				classes.add(word);
			}
		}
		return classes;
	}
			
	private ArrayList<ClassHandler> genMethodDependencies2(String className, String methodSig, ArrayList<ClassHandler> methodCodes) throws TestAndAssertionGeneratorException {
		String methodCode;
		HashMap<String, ArrayList<String>> calledMethods;
		
		if (methodCodes == null) {
			methodCodes = new ArrayList<ClassHandler>();
		}

		for (var clazz : methodCodes) {
			if (clazz.getClassName().equals(className)) {
				if (clazz.containsMethod(methodSig)) {
					return null;
				}
			}
		}
		
		methodCode = getCodeOfSignature(className, methodSig);
		if (methodCode == null || methodCode.isEmpty()) {
			return methodCodes;
		}
		
		// add methodCode
		int classIndex = -1;
		for (int i = 0; i < methodCodes.size(); i++) {
			if (methodCodes.get(i).getClassName().equals(className)) {
				classIndex = i;
			}
		}
		if (classIndex == -1) {
			methodCodes.add(new ClassHandler(className, this.projectPath));
			classIndex = methodCodes.size() - 1;
		}
		methodCodes.get(classIndex).addMethod(methodSig, methodCode);
		// find out what methods are getting called inside *methodCode*.
		final var innerMethod = methodCodes.get(classIndex).getMethod(methodSig).getInnerCode();
		calledMethods = getCalledMethods(methodCode, innerMethod, className);
		for (var clazz : calledMethods.keySet()) {
			for (var m : calledMethods.get(clazz)) {
				genMethodDependencies2(clazz, m, methodCodes);
			}
		}		
		return methodCodes;
	}
	
	private String getSignatureOfLoadedFile(String className, String methodName, int numParams) {
		// load file
		var javaFile = new File(FileUtil.getProjectLocation(projectPath) + "/src/" + className + ".java");
		var destination = new File(FileUtil.getProjectLocation(projectPath) + "/tests/" + className + ".java");
		if (!javaFile.exists()) {
			if (FileHandler.isSPL(projectPath)) {
				javaFile = new File(FileUtil.getProjectLocation(projectPath) + "/src_gen/" + className + ".java");
				if (!javaFile.exists()) {
					return "";
				}
			} else {
				return "";
			}
		}
		FileHandler.deleteFile(this.projectPath, className);
		try {
			Files.copy(javaFile.toPath(), destination.toPath());
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}	
		
		var code = ClassHandler.classExists(this.projectPath, className);
		if (code.isEmpty()) {
			return "";
		}
		
		String signature = "";
		String params = "";
		// TODO: Check if this works for all possibilities
		while(code.length() > 0) {
			int start = code.indexOf(methodName);
			if (start == -1) {
				break;
			}
			int s = start - 1;
			while (s > 0 && Character.isWhitespace(code.charAt(s--)));
			if (!Character.isAlphabetic(code.charAt(++s))) {
				code = code.substring(start + methodName.length(), code.length());
				continue;
			}
			int end = code.substring(start, code.length()).indexOf('{') + start;
			if (start == -1 || end == -1) {
				return "";
			}
			start = code.substring(0, end).lastIndexOf(';');
			int cmp = code.substring(0, end).lastIndexOf('}');
			if (cmp > start) {
				start = cmp;
			}
			signature = code.substring(start, end).replaceAll("[^\\w\\s\\(\\)\\_\\,\\[\\]]", "").trim();
			// check if we found the right signature
			params = signature.substring(signature.indexOf('(') + 1, signature.lastIndexOf(')'));
			long actualParams = params.chars().filter(c -> c == ',').count() + 1;
			if (params.length() == 0) {
				actualParams = 0;
			}
			if (actualParams == numParams) {
				return signature;
			}
			code = code.substring(end + 1, code.length());
		}
		return signature;
		
		/*
		// parse signature
		var patternStr = methodName + "\\(";
		for (int i = 0; i < numParams; i++) {
			if (i >= 1) {
				patternStr += "\\,\\s";
			}
			patternStr += "[^\\,]+";
		}
		if (numParams > 1) {
			patternStr = patternStr.substring(0, patternStr.length() - 4);
		}
		patternStr += "\\)\\s*\\{";
		final Pattern p = Pattern.compile(patternStr);
		final Matcher m = p.matcher(code);
		
		while (m.find()) {
			var sig = code.substring(0, m.start() + m.group(0).indexOf('{'));
			sig = sig.substring(sig.lastIndexOf('\n'), sig.length()).trim();
			return sig;
		}
		return "";*/
	}
	
	/**
	 * Searches for definition of method *methodName* in class *className* which takes exactly *numParams* parameters.
	 * @param className
	 * @param methodName
	 * @param numParams
	 */
	private String getMethodSignature(String className, String methodName, int numParams) {
		Diagram diagram;
		
		if (isExternalClass(className)) {
			return getSignatureOfLoadedFile(className, methodName, numParams);
		} else {
			diagram = loadDiagramFromClass(className, methodName);
			if (diagram == null) {
				// try to load it in from java src
				var sig = getSignatureOfLoadedFile(className, methodName, numParams);
				return sig;
			}

			JavaVariables vars = null;
			CbCFormula formula = null;

			for (Shape shape : diagram.getChildren()) {
				Object obj = getBusinessObjectForPictogramElement(shape);
				if (obj instanceof JavaVariables) {
					vars = (JavaVariables) obj;
				} else if (obj instanceof CbCFormula) {
					formula = (CbCFormula) obj;
				}
			}	
			int diagramParams = 0;
			for (var v : vars.getVariables()) {
				if (v.getKind().equals(VariableKind.PARAM)) {
					diagramParams++;
				}
			}
			if (diagramParams == numParams) {
				return formula.getMethodObj().getSignature();
			}
			// this means the diagram is not the method we are looking for.
			// the method must be in code which was given in src.
			return getSignatureOfLoadedFile(className, methodName, numParams);
		}
	}
	
	private String getCodeOfSignatureOfLoadedFile(String className, String signature, boolean isConstructor) {
		// load file
		var javaFile = new File(FileUtil.getProjectLocation(projectPath) + "/src/" + className + ".java");
		var destination = new File(FileUtil.getProjectLocation(projectPath) + "/tests/" + className + ".java");
		if (!javaFile.exists()) {
			if (FileHandler.isSPL(projectPath)) {
				javaFile = new File(FileUtil.getProjectLocation(projectPath) + "/src_gen/" + className + ".java");
				if (!javaFile.exists()) {
					return "";
				}
			} else {
				return "";
			}
		}
		FileHandler.deleteFile(this.projectPath, className);
		try {
			Files.copy(javaFile.toPath(), destination.toPath());
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}	
		
		final var code = ClassHandler.classExists(this.projectPath, className);
		if (code.isEmpty()) {
			return "";
		}
		final String methodCode;
		if (isConstructor) {
			methodCode = getConstructorCode(signature);	
		} else {
			methodCode = MethodHandler.getBySignature(code, signature);	
		}	
		return methodCode;
	}
	
	private int getNumParamsFromSig(String signature) {
		int numParams = (int)signature.chars().filter(c -> c == ',').count();
		if (numParams > 0) {
			numParams++;
		} else {
			if (signature.charAt(signature.indexOf('(') + 1) == ')') {
				numParams = 0;
			} else {
				numParams = 1;
			}
		}
		return numParams;
	}
	
	/**
	 * Given the *className* and the *signature* either generate the code for *signature* if it is from a diagram or load it from a src file which 
	 * contains the definition for *signature*.
	 * @param className
	 * @param signature
	 * @return
	 * @throws TestAndAssertionGeneratorException 
	 */
	private String getCodeOfSignature(String className, String signature) throws TestAndAssertionGeneratorException {
		Diagram diagram;
		boolean isConstructor = false;
		
		
		if (getMethodName(signature).equals(className)) {
			isConstructor = true;
		}
		
		if (isExternalClass(className)) {
			return getCodeOfSignatureOfLoadedFile(className, signature, isConstructor);
		} else if (isConstructor) {
			return getConstructorCode(signature);
		} else {
			diagram = loadDiagramFromClass(className, MethodHandler.getMethodNameFromSig(signature));
			if (diagram == null) {
				// try to load the code from the java src folder
				return getCodeOfSignatureOfLoadedFile(className, signature, isConstructor);
			}
			JavaVariables vars = null;

			for (Shape shape : diagram.getChildren()) {
				Object obj = getBusinessObjectForPictogramElement(shape);
				if (obj instanceof JavaVariables) {
					vars = (JavaVariables) obj;
					break;
				}
			}	

			int diagramParams = 0;
			// TODO: Check if this works for all cases
			if (vars != null) {
				for (var v : vars.getVariables()) {
					if (v.getKind().equals(VariableKind.PARAM)) {
						diagramParams++;
					}
				}
				if (diagramParams == getNumParamsFromSig(signature)) {
					return genCode(diagram, true);
				}
			}
			// this means the diagram is not the method we are looking for.
			// the method must be in code which was given in src.
			return getCodeOfSignatureOfLoadedFile(className, signature, isConstructor);
		}
	}

	private String getConstructorCode(String signature) {
		final Pattern p = Pattern.compile(Pattern.quote(signature));
		var code = loadFileFromClass(getMethodName(signature), "src");
		final Matcher m = p.matcher(code);
		
		while (m.find()) {
			var helper = code.substring(m.start(), code.length());
			int startBracket = m.start() + helper.indexOf("{");
			int endBracket = CodeHandler.findClosingBracketIndex(code, startBracket, '{');
			if (endBracket == -1) {
				return null;
			}
			return code.substring(m.start(), endBracket + 1);
		}
		// just return the default constructor when no constructor was found
		return "public " + getMethodName(signature) + "(){}";
	}

	private String getConstructorSig(String code, String className, int numParams) {
		final Pattern p = Pattern.compile(className + "\\([^\\)]*\\)\\s*\\{");
		final Matcher m = p.matcher(code);
		String cCode;
		String params;
		
		while (m.find()) {
			params = m.group(0).substring(m.group(0).indexOf('(') + 1, m.group(0).lastIndexOf(')'));
			if (CodeHandler.sSplit(params, ",").length == numParams) {
				// we found the right constructor
				cCode = "public " + code.substring(m.start(), m.start() + m.group(0).indexOf('{')).trim();
				return cCode;
			}
		}
		// just return the default constructor sig when numParams is 0 and no constructor was found
		if (numParams == 0) {
			return "public " + className + "()";
		}
		return null;
	}
	
	/**
	 * Collects all method/constructor calls made in *matchee*. *matchee* can be any arbitrary string.
	 * @param methodCode
	 * @param innerMethod
	 * @param className
	 * @return a map of containing every class that has at least one called method. 
	 * Map contains mapping from the classes to their called methods.
	 */
	public HashMap<String, ArrayList<String>> getCalledMethods(String code, String matchee, String className) {
		final HashMap<String, ArrayList<String>> output = new HashMap<String, ArrayList<String>>();
		int start = matchee.indexOf("(");
		while (start != -1) {
			if (start != 0 && Character.isAlphabetic(matchee.charAt(start-1))) {
				int end = CodeHandler.findClosingBracketIndex(matchee, start, '(');
				if (end == -1) {
					matchee = matchee.replaceFirst("\\(", "");
					start = matchee.indexOf("(");
					continue;
				}
				// get identifiers infront of the method call
				int fullId = start - 1;
				while (fullId-- > 0 && (Character.isAlphabetic(matchee.charAt(fullId)) || Arrays.asList('.', '_' /*, '[', ']'*/).contains(matchee.charAt(fullId))));
				fullId++;
				final String methodCallStr = matchee.substring(fullId, end+1);
				final String fullIdStr = matchee.substring(fullId, start);
				if (fullIdStr.contains("original")) {
					if (showWarnings) {
						Console.println("TestAndAssertionWarning: Found unhandled 'original' keyword.");
					}
					matchee = matchee.replaceFirst("\\(", "");
					start = matchee.indexOf("(");
					continue;
				}
				long numDots = fullIdStr.chars().filter(c -> c == '.').count();
				if (numDots == 2) {
					final var methodCallParts = methodCallStr.split("\\.", 3);
					var methodName = methodCallParts[2].substring(0, methodCallParts[2].indexOf('('));
					final var varName = methodCallParts[1];
					//final var curClassName = methodCallParts[0];
					final var type = getVariableType(code, varName, className);
					if (type.isEmpty()) {
						if (showWarnings) {
							Console.println("TestAndAssertionWarning: Couldn't get type of variable '" + varName + "'.");
						}
						FileHandler.log(this.projectPath, "TestAndAssertionWarning: Couldn't get type of variable '" + varName + "'.");
						matchee = matchee.replaceFirst("\\(", "");
						start = matchee.indexOf("(");
						continue;
					}
					if (output.get(type) == null) {
						output.put(type, new ArrayList<String>());
					}
					int numParams = getNumParamsFromSig(methodCallStr);
					final var methodSig = getMethodSignature(type, methodName, numParams);
					
					if (!output.get(type).contains(methodSig)) {
						output.get(type).add(methodSig);
					}
				} else if (numDots == 1) {
					final var methodCallParts = methodCallStr.split("\\.", 2);
					var methodName = methodCallParts[1].substring(0, methodCallParts[1].indexOf('('));
					if (methodName.equals("setAttribute")) {
						// testng context, skip
						matchee = matchee.replaceFirst("\\(", "");
						start = matchee.indexOf("(");
						continue;
					}
					final var varName = methodCallParts[0];
					final var type = getVariableType(code, varName, className);
					if (type.isEmpty()) {
						if (showWarnings) {
							Console.println("TestAndAssertionWarning: Couldn't get type of variable '" + varName + "'.");
						}
						FileHandler.log(this.projectPath, "TestAndAssertionWarning: Couldn't get type of variable '" + varName + "'.");
						matchee = matchee.replaceFirst("\\(", "");
						start = matchee.indexOf("(");
						continue;
					}
					if (output.get(type) == null) {
						output.put(type, new ArrayList<String>());
					}
					int numParams = getNumParamsFromSig(methodCallStr);
					final var methodSig = getMethodSignature(type, methodName, numParams);
					
					if (!output.get(type).contains(methodSig)) {
						output.get(type).add(methodSig);
					}
				} else if (numDots == 0) {
					final var type = className;
					var methodName = methodCallStr.split("\\(", 2)[0].trim();
					// idea: as it is a constructor, we know it has no diagram and must have been
					// been provided by the user in the src.
					String classCode;
					if (!(classCode = loadFileFromClass(methodName, "src")).isEmpty()) { // TODO: make sure every loaded class has the custom constructor with all gvars initializable
						// means it is indeed a constructor call
						// determine amount of parameters.
						var call = methodCallStr
								.substring(methodCallStr.indexOf('(') + 1, methodCallStr.lastIndexOf(')'));
						int numParams;
						if (call.isEmpty()) {
							numParams = 0;
						} else {
							numParams = CodeHandler.sSplit(call, ",").length;
							if (numParams == 0) numParams = 1;
						}
						// get sig of the constructor
						var cSig = getConstructorSig(classCode, methodName, numParams); 
						if (cSig == null) {
							if (showWarnings) {
								Console.println("TestAndAssertionWarning: Couldn't get the signature of the constructor '" + methodName + "' which has " + numParams + " parameters.");
							}
							FileHandler.log(this.projectPath, "TestAndAssertionWarning: Couldn't get the signature of the constructor '" + methodName + "' which has " + numParams + " parameters.");
							matchee = matchee.replaceFirst("\\(", "");
							start = matchee.indexOf("(");
							continue;
						}
						if (output.get(methodName) == null) {
							output.put(methodName, new ArrayList<String>());
						}
						output.get(methodName).add(cSig);
						matchee = matchee.replaceFirst("\\(", "");
						start = matchee.indexOf("(");
						continue;
					}
					int numParams = getNumParamsFromSig(methodCallStr);
					final var methodSig = getMethodSignature(type, methodName, numParams);
					if (methodSig.isEmpty()) {
						if (showWarnings) {
							Console.println("TestAndAssertionWarning: Couldn't find signature for method '" + methodName + "'.");
						}
						FileHandler.log(this.projectPath, "TestAndAssertionWarning: Couldn't find signature for method '" + methodName + "'.");
						matchee = matchee.replaceFirst("\\(", "");
						start = matchee.indexOf("(");
						continue;
					}
					if (output.get(type) == null) {
						output.put(type, new ArrayList<String>());
					}
					if (!output.get(type).contains(methodSig)) {
						output.get(type).add(methodSig);
					}
				}
			}	
			matchee = matchee.replaceFirst("\\(", "");
			start = matchee.indexOf("(");
		}
		return output;
	}
			
	private String getMethodName(String methodCode) {
		if (methodCode.indexOf('(') == - 1) {
			return "";
		}
		if (methodCode.indexOf('{') != - 1 && methodCode.indexOf('(') > methodCode.indexOf('{')) {
			return "";
		}
		methodCode = methodCode.substring(0, methodCode.indexOf('('));
		methodCode = methodCode.substring(methodCode.lastIndexOf(' '), methodCode.length());
		return methodCode.trim();
	}
}