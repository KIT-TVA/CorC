package de.tu_bs.cs.isf.cbc.tool.features;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.testng.TestNG;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlSuite.ParallelMode;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.OriginalStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.ReturnStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SkipStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.StrengthWeakStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.VariableKind;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.AbstractStatementImpl;
import de.tu_bs.cs.isf.cbc.tool.helper.Branch;
import de.tu_bs.cs.isf.cbc.tool.helper.BranchType;
import de.tu_bs.cs.isf.cbc.tool.helper.InputData;
import de.tu_bs.cs.isf.cbc.tool.helper.JavaCondition;
import de.tu_bs.cs.isf.cbc.tool.helper.PreConditionSolver;
import de.tu_bs.cs.isf.cbc.tool.helper.PreConditionSolverException;
import de.tu_bs.cs.isf.cbc.tool.helper.TestStatementListener;
import de.tu_bs.cs.isf.cbc.tool.helper.Token;
import de.tu_bs.cs.isf.cbc.tool.helper.TokenType;
import de.tu_bs.cs.isf.cbc.tool.helper.Tokenizer;
import de.tu_bs.cs.isf.cbc.tool.helper.Util;
import de.tu_bs.cs.isf.cbc.tool.helper.Variable;
import de.tu_bs.cs.isf.cbc.tool.helper.conditionparser.ConditionParser;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.FileUtil;

/**
 * Class for testing a single statement.
 * @author Fynn
 */
public class TestStatement extends MyAbstractAsynchronousCustomFeature {
	private final String DUMMY_SIGNATURE = "public void dummyMethod(ITestContext context)";
	private final String STATEMENT_PH = "<statement>";
	public static final String EXECUTED_TAG = "context.setAttribute(\"executed\", \"\");";
	public static final String PRECHECKS_START = "//[checks]";
	public static final String PRECHECKS_END = "//[end_checks]";
	public static final Color blue = new Color(new RGB(10, 10, 200));
	private URI projectPath;
	private final IFeatureProvider fp;
	
	public TestStatement(IFeatureProvider fp) {
		super(fp);
		this.fp = fp;
	}
	
	public String getName() {
		return "Test a statement";
	}
	
	public void setUri(URI projectPath) {
		this.projectPath = projectPath;
	}

	@Override
	public String getDescription() {
		return "Tests a statements using data which fulfills the precondition and uses assertions to check the postcondition.";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		boolean ret = false;
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo != null && (bo.getClass().equals(AbstractStatementImpl.class) || bo instanceof SkipStatement
					|| bo instanceof ReturnStatement || bo instanceof MethodStatement)) {
				AbstractStatement statement = (AbstractStatement) bo;
				if (statement.getRefinement() == null) {
					ret = true;
				}
			}
		}
		return ret;
	}

	@Override
	public void execute(ICustomContext context, IProgressMonitor monitor) {	
		Console.clear();
		Console.println("Start testing...\n");
		final long startTime = System.nanoTime();
		final PictogramElement[] pes = context.getPictogramElements();
		final URI uri;
		boolean returnStatement;
		final Object bo;
		
		monitor.beginTask("Test statement", IProgressMonitor.UNKNOWN);
		if (pes != null && pes.length == 1) {
			bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof AbstractStatement) {
				returnStatement = bo instanceof ReturnStatement;
				AbstractStatement statement = (AbstractStatement) bo;
				JavaVariables vars = null;
				GlobalConditions conds = null;
				CbCFormula formula = null;
				for (Shape shape : getDiagram().getChildren()) {
					Object obj = getBusinessObjectForPictogramElement(shape);
					if (obj instanceof JavaVariables) {
						vars = (JavaVariables) obj;
					} else if (obj instanceof GlobalConditions) {
						conds = (GlobalConditions) obj;
					} else if (obj instanceof CbCFormula) {
						formula = (CbCFormula) obj;
					}
				}	
				uri = getDiagram().eResource().getURI();
				this.projectPath = uri;
				Util.clearLog(this.projectPath);
				
				Console.println(" > Testing path:", blue);
				Console.println("\t" + getStatementPath(statement));
				try {
					testStatement(statement, vars, conds, formula, returnStatement);
				} catch (TestAndAssertionGeneratorException | TestStatementException e) {
					Console.println(e.getMessage());
					e.printStackTrace();
					return;
				}
				
				// update pictogram
				updatePictogramElement(((Shape) pes[0]).getContainer());
			}
		}
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1000000;
		monitor.done();
		Console.println("Testing finished.");
		Console.println("Time needed: " + duration + "ms");
	}	
	
	public static void setPathTested(EObject selectedStatement, boolean status) {
		EObject cur = selectedStatement;
			
		while (cur != null) {
			if (cur instanceof MethodStatement
					|| cur instanceof ReturnStatement
					|| cur instanceof SelectionStatement
					|| cur instanceof SkipStatement
					|| cur instanceof SmallRepetitionStatement
					|| cur instanceof StrengthWeakStatement
					|| cur instanceof OriginalStatement) {
				if (!((AbstractStatement)cur).isProven()) {
					((AbstractStatement)cur).setTested(status);
				} else {
					((AbstractStatement)cur).setTested(false);
				}
			} else if (cur instanceof CompositionStatement) {
				var comp = (CompositionStatement)cur;
				if ((comp.getFirstStatement().getRefinement().isTested() || comp.getFirstStatement().getRefinement().isProven())
						&& (comp.getSecondStatement().getRefinement().isTested() || comp.getSecondStatement().getRefinement().isProven()) && !comp.isProven()) {
					comp.setTested(status);
				} else {
					comp.setTested(false);
				}
			} else if (cur instanceof AbstractStatement) {
				if (((AbstractStatement)cur).getRefinement() == null) {
					if (!((AbstractStatement)cur).isProven()) {
						((AbstractStatement)cur).setTested(status);
					} else {
						((AbstractStatement)cur).setTested(false);
					}
				}
			}
			else if (cur instanceof CbCFormula) {
				if (!((CbCFormula)cur).isProven()) {
					((CbCFormula)cur).setTested(status);
				} else {
					((CbCFormula)cur).setTested(false);
				}
			}
			cur = cur.eContainer();
		}
	}
	
	private List<Variable> getUsedVars(final String str, final JavaVariables vars) {
		List<Variable> usedVars = Variable.getAllVars(vars);
		
		for (int i = 0; i < usedVars.size(); i++) {
			Pattern p = Pattern.compile("\\W*" + Pattern.quote(usedVars.get(i).getName()) + "\\W*");
			Matcher m = p.matcher(str);
			if (!m.find()) {
				usedVars.remove(usedVars.get(i));
				i--;
			}
		}
		return usedVars;
	}
	
	private String insertOldVars(String code, final String postCon, final List<Variable> initializedVars, final JavaVariables vars, final String preCon, final List<InputData> data, final TestAndAssertionGenerator generator) {
		var allVars = Variable.getAllVars(vars);
		Tokenizer tokenizer = new Tokenizer(postCon);
		Token token;
		while ((token = tokenizer.genNext()) != null) {
			if (token.getType() == TokenType.KEY && token.getValue().contains("old_")) {
				// initialize the old var
				String name;
				if (token.getValue().contains(".")) {
					// we want the first identifier
					name = token.getValue().substring("old_".length(), token.getValue().indexOf("."));
				} else {
					name = token.getValue().substring("old_".length(), token.getValue().length());
				}
				// if name is actually a class
				String classCode;
				if (!(classCode = generator.classExists(name)).isEmpty()) {
					// get the identifier after the first '.'
					String identifier = token.getValue().substring(token.getValue().indexOf("."), token.getValue().length());
					final var variable = new Variable(name, "old_" + name);
					final var idVariable = new Variable("", name + identifier);
					final String classInit = name + " " + "old_" + name + " = new " + name + "();\n";
					if (!Variable.containsVar(initializedVars, idVariable)) {												
						code = "old_" + name + identifier + " = " + name + identifier + ";\n" + code;
						if (Variable.containsVar(initializedVars, variable)) {
							// need to replace the class init to the beginning
							code = code.replaceFirst(Pattern.quote(classInit), "");
							code = classInit + code;
						}
						initializedVars.add(idVariable);
					}
					if (Variable.containsVar(initializedVars, variable)) {
						continue;
					}
					initializedVars.add(variable);
					code = classInit + code;
					continue;
				}
				for (var v : allVars) {
					if (v.getName().equals(name)) {
						final var variable = new Variable(v.getType(), "old_" + name); //v.name
						if (Variable.containsVar(initializedVars, variable)) {
							continue;
						}
						initializedVars.add(variable);
						code = v.getType() + " " + "old_" + name + " = " + v.getName() + ";\n" + code;
					}
				}
			}
		}
		return code;
	}
	
	private String insertParams(String code, final List<Variable> usedVars, final List<Variable> initializedVars, final List<InputData> data) {
		for (var v : usedVars) {
			if (v.getKind() == VariableKind.PARAM) {
				for (int i = 0; i < data.size(); i++) {
					if (data.get(i).getName().equals(v.getName())) {
						// add attribute for the testng context
						var val = data.get(i).getRandomValue();
						//var contextStr = "context.setAttribute(\"0" + data.get(i).getName() + "\", \"" + val + "\");\n";
						if (data.get(i).getDimensions() == 0) {
							code = data.get(i).getType() + " " + data.get(i).getName() + " = " + val + ";\n" + code;
						} else {
							if (data.get(i).isPrimitive()) {
								code = data.get(i).getPrimitiveArrayInit() + ";\n" + code;					
							} else {
								code = data.get(i).getType() + " " + data.get(i).getName() + " = " + data.get(i).getArrayRep() + ";\n" + code;
							}
						}
						initializedVars.add(new Variable(v.getType(), v.getName()));
					}
				}
			}
		}
		return code;
	}
		
	private String insertFixture(String code, final List<InputData> data, final List<Variable> gVars, final List<Variable> usedVars, final JavaVariables vars, final String postCon, final String preCon, final TestAndAssertionGenerator generator) {
		final var initializedVars = new ArrayList<Variable>();
		code = "\n" + code;
		code = insertOldVars(code, postCon, initializedVars, vars, preCon, data, generator);
		code = insertParams(code, usedVars, initializedVars, data);
		
		for (int i = 0; i < data.size(); i++) {
			final var variable = new Variable(data.get(i).getType(), data.get(i).getName());
			if (Variable.containsVar(initializedVars, variable)) {
				continue;
			}
			// add var to the code
			// also add the var + value to the testng context
			var val = data.get(i).getRandomValue();
			if (val.isEmpty()) {
				continue;
			}
			//var contextStr = "context.setAttribute(\"0" + data.get(i).getName() + "\", \"" + val + "\");\n";
			if (data.get(i).getDimensions() == 0) {
				if (Variable.containsVar(gVars , variable)) {
					code = data.get(i).getName() + " = " + val + ";\n" /*+ contextStr*/ + code;
				} else {
					code = data.get(i).getType() + " " + data.get(i).getName() + " = " + val + ";\n" /*+ contextStr*/ + code;
				}
			} else {
				if (data.get(i).isPrimitive()) {
					code = data.get(i).getPrimitiveArrayInit() + ";\n" + code;					
				} else {
					if (Variable.containsVar(gVars , variable)) {
						code = data.get(i).getName() + " = " + data.get(i).getArrayRep() + ";\n" + code;
					} else {
						code = data.get(i).getType() + " " + data.get(i).getName() + " = " + data.get(i).getArrayRep() + ";\n" + code;
					}
				}
			}
			initializedVars.add(variable);
		}
		for (int i = 0; i < usedVars.size(); i++) {
			if (Variable.containsVar(initializedVars, usedVars.get(i))) {
				continue;
			}
			InputData uData = new InputData(usedVars.get(i).getName(), usedVars.get(i).getType());
			uData.setDefaultValues();
			var val = uData.getRandomValue();
			//var contextStr = "context.setAttribute(\"0" + uData.getName() + "\", \"" + val + "\");\n";	
			if (uData.getDimensions() == 0) {
				if (Variable.containsVar(gVars , usedVars.get(i))) {
					code = uData.getName() + " = " + val + ";\n" /*+ contextStr*/ + code;
				} else {
					code = uData.getType() + " " + uData.getName() + " = " + val + ";\n" /*+ contextStr*/ + code;
				}
			} else {
				if (uData.isPrimitive()) {
					code = uData.getPrimitiveArrayInit() + ";\n" + code;					
				} else {
					if (Variable.containsVar(gVars , usedVars.get(i))) {
						code = uData.getName() + " = " + uData.getArrayRep() + ";\n" + code;
					} else {
						code = uData.getType() + " " + uData.getName() + " = " + uData.getArrayRep() + ";\n" + code;
					}
				}
			}
		}
		return code;
	}
	
	public static String insertTabs(String code, int numTabs) {
		StringBuffer out = new StringBuffer();
		var lines = code.split("\\n");
		int counter = 0;
		for (int i = 0; i < lines.length; i++) {
			lines[i] = TestAndAssertionGenerator.getTabs(numTabs) + lines[i];
			if (counter > 0) {
				out.append("\n");
			}
			out.append(lines[i]);
			counter++;
		}
		return out.toString();
	}
	
	private String insertAssertion(final String code, final String innerMethod, final String assertion) {
		return code.substring(0, code.indexOf(STATEMENT_PH) + STATEMENT_PH.length()) 
				+ "\n"
				+ getNumTabs("{" + code.substring(0, code.indexOf(STATEMENT_PH)) + innerMethod)
				+ "Assert.assertTrue(" + assertion + ");" 
				+ code.substring(code.indexOf(STATEMENT_PH) + STATEMENT_PH.length(), code.length());
	}
	
	private String insertBranch(final String code, final String innerMethod, final String branchCon, final String assertion) {
		return code.substring(0, code.indexOf(STATEMENT_PH) + STATEMENT_PH.length())
				+ "\n" 
				+ getNumTabs("{" + code.substring(0, code.indexOf(STATEMENT_PH)) + innerMethod)
				+ "if (" + branchCon + ")" + " Assert.assertTrue(" + assertion + ");" 
				+ code.substring(code.indexOf(STATEMENT_PH) + STATEMENT_PH.length(), code.length());
	}
	
	public static String getWrapper(String primitiveType) {
		switch (primitiveType) {
			case "short":
				return "Short";
			case "int":
				return "Integer";
			case "long":
				return "Long";
			default:
				return null;
		}
	}
	
	private String insertExists(final String code, final String innerMethod, final Branch branch) {
		String numTabs = getNumTabs("{" + code.substring(0, code.indexOf(STATEMENT_PH)) + innerMethod);
		return code.substring(0, code.indexOf(STATEMENT_PH) + STATEMENT_PH.length())
				+ "\n" 
				+ numTabs
				+ "boolean exists = false;\n"
				+ numTabs
				+ "for (" + branch.getIterType() + " " + branch.getIterName() + " = " 
				+ getWrapper(branch.getIterType()) + ".MIN_VALUE; " + branch.getIterName() + " < " + getWrapper(branch.getIterType()) + ".MAX_VALUE; " 
				+ branch.getIterName() + "++" + ")" + "{\n" 
				+ numTabs + "\t"
				+ "if (" + branch.getIterConditions().stream().reduce((f, s) -> f + " && " + s).get() + ") {\n"
				+ branch.getQuantorBodyConditions().stream().map(c -> numTabs + "\t\t" + "if(" + c + ") exists = true; break;\n").reduce((f, s) -> f + s).get()
				+ numTabs + "\t" + "}\n " + numTabs + "}\n"
				+ numTabs + "Assert.assertTrue(exists);\n"
				+ code.substring(code.indexOf(STATEMENT_PH) + STATEMENT_PH.length(), code.length());
	}
	
	private String insertForAll(final String code, final String innerMethod, final Branch branch) {
		String numTabs = getNumTabs("{" + code.substring(0, code.indexOf(STATEMENT_PH)) + innerMethod);
		return code.substring(0, code.indexOf(STATEMENT_PH) + STATEMENT_PH.length())
				+ "\n" 
				+ numTabs
				+ "for (" + branch.getIterType() + " " + branch.getIterName() + " = " 
				+ getWrapper(branch.getIterType()) + ".MIN_VALUE; " + branch.getIterName() + " < " + getWrapper(branch.getIterType()) + ".MAX_VALUE; " 
				+ branch.getIterName() + "++" + ")" + "{\n" 
				+ numTabs + "\t"
				+ "if (" + branch.getIterConditions().stream().reduce((f, s) -> f + " && " + s).get() + ") {\n"
				+ branch.getQuantorBodyConditions().stream().map(c -> numTabs + "\t\t" + "Assert.assertTrue(" + c + ");\n").reduce((f, s) -> f + s).get()
				+ numTabs + "\t" + "}\n " + numTabs + "}\n"
				+ code.substring(code.indexOf(STATEMENT_PH) + STATEMENT_PH.length(), code.length());
	}
	
	private String constructDummyMethod(String innerMethod, final JavaCondition javaCondition, final List<InputData> data) {
		innerMethod = insertTabs(innerMethod, 2);
		var code = "\t@Test\n\t" + DUMMY_SIGNATURE + " {\n" + innerMethod.substring(1, innerMethod.length());
		Branch branch;
		while ((branch = javaCondition.getNext()) != null) {
			if (branch.getType() == BranchType.NONE) {
				for (var assertion : branch.getAssertions()) {
					code = insertAssertion(code, innerMethod, assertion);
				}
			} else if (branch.getType() == BranchType.IMPL) {
				for (var assertion : branch.getAssertions()) {
					code = insertBranch(code, innerMethod, branch.getBranchCondition(), assertion);
				}
			} else if (branch.getType() == BranchType.EQUI) {
				for (var assertion : branch.getAssertions()) {
					code = insertBranch(code, innerMethod, branch.getBranchCondition(), assertion);
					String otherConditions = branch.getAssertions().stream().reduce((f, s) -> f + " && " + s).get();
					code = insertBranch(code, innerMethod, otherConditions, branch.getBranchCondition());
				}
			} else if (branch.getType() == BranchType.EXISTS) {
				code = insertExists(code, innerMethod, branch);
			} else if (branch.getType() == BranchType.FORALL) {
				code = insertForAll(code, innerMethod, branch);
			}
		}
		code += "\n\t}";
		return code;
	}
	
	private List<String> placeDummyMethod(final List<String> dependencies, final String testClassName, final String innerMethod, String fullMethod) {
		final var code = new StringBuffer();
		code.append("import org.testng.ITestContext;\n");
		code.append("import org.testng.Assert;\n");
		code.append("import org.testng.annotations.Test;\n");
		code.append("import java.util.Arrays;\n");
		code.append("import java.util.stream.IntStream;\n\n");
		
		for (int i = 0; i < dependencies.size(); i++) {
			if (dependencies.get(i).isBlank()) {
				continue;
			}
			var className = dependencies.get(i).split("\\s", 4)[2].replaceAll("\\{", "");
			if (className.equals(testClassName)) {
				dependencies.set(i, dependencies.get(i).replaceAll(Pattern.quote(innerMethod), ""));
				// replace return statements in test
				fullMethod = fullMethod.replaceAll("return\\s", "//[REMOVED BY GENERATOR] return ");
				var stripped = dependencies.get(i).strip();
				dependencies.set(i, code.toString() + stripped.substring(0, stripped.length() - 1) + fullMethod + "\n}");
			}
		}
		return dependencies;
	}
	
	private boolean compile(final List<String> dependencies, final String className, final String methodName, final TestAndAssertionGenerator generator) throws TestAndAssertionGeneratorException {		
		var pathOfPlugins = System.getProperty("osgi.syspath");
		var file = new File(pathOfPlugins);
		List<String> testNgFiles = Arrays.asList(file.list()).stream().filter(s -> s.contains("org.testng_")).toList();
		var highestVersion = testNgFiles.stream().map(s -> s.split("org.testng\\_")[1]).sorted().reduce((first, second) -> second).get();
		highestVersion = "org.testng_" + highestVersion;
		
		// compile dependencies
		var options = Arrays.asList("-cp", pathOfPlugins + "/" + highestVersion);
		try {
			if(dependencies.size() > 0 && !generator.compileFileContents(dependencies, methodName, options)) {
				return false;
			}
		} catch (TestAndAssertionGeneratorException e) {
			throw e;
		}
		return true;
	}
	
	private boolean executeTest(final String testName, final TestAndAssertionGenerator generator) {
		final XmlSuite suite;			
		// first create the xml suite needed to run TestNG
		suite = generator.createXmlSuite("file://" + FileUtil.getProjectLocation(this.projectPath) + "/tests/", testName);		
		// now start testng using the custom test listener
		var tla = new TestStatementListener(projectPath, null, null);
		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		suite.setParallel(ParallelMode.NONE);
		suites.add(suite);
		TestNG tng = new TestNG();
		tng.setUseDefaultListeners(false);
		tng.setParallel(ParallelMode.NONE);
		tng.setXmlSuites(suites);
		tng.addListener(tla);
		tng.run();
		if (tng.hasFailure()) {
			return false;
		} else {
			return true;
		}
	}
		
	private static String getNumTabs(String code) {
		String out = "";
		int bracketNum = TestAndAssertionGenerator.countBrackets(code, '{');
		if (bracketNum <= 0) {
			return "";
		}
		for (int i = 0; i < bracketNum; i++) {
			out += "\t";
		}
		return out;
	}
	
	/**
	 * Inserts checks into *code* that makes sure every precondition contained in *javaCondition* is satisfied by the data in *code*.
	 * Idea:
	 * Invert all conditions in *javaCondition* and use ifs to check if any inverted condition is true. 
	 * If so set an attribute, that notifies testng to skip this test/statement testing.
	 * @param code
	 * @param javaCondition
	 * @return
	 * @throws TestStatementException 
	 */
	public static String insertPreconditionChecks(String code, JavaCondition javaCondition, int numTabs) throws TestStatementException {
		// always put the checks between arrange and act parts
		Branch branch;
		if (!code.contains("\n\n")) {
			throw new TestStatementException("Couldn't check whether the preconditions of the program are satisfied.");
		}
		// assumes the parts are separated by an empty line
		int pos = code.indexOf("\n\n") + 1;
		String toAppend = code.substring(pos, code.length());
		code = code.substring(0, pos) + TestAndAssertionGenerator.getTabs(numTabs) + PRECHECKS_START + "\n";
			
		while ((branch = javaCondition.getNext()) != null) {
			if (branch.getType() == BranchType.NONE) {
				for (var assertion : branch.getAssertions()) {
					code += TestAndAssertionGenerator.getTabs(numTabs) + "if(!(" + assertion + ")) " + "context.setAttribute(\"skip\", \"" + assertion + "\");" + "\n";
				}
			}
		}
		
		return code + TestAndAssertionGenerator.getTabs(numTabs) + PRECHECKS_END + toAppend;
	}
			
	public static String removeTabs(final String code) {
		var lines = Stream.of(code.split("\\n")).toList();
		final StringBuffer buf = new StringBuffer();
		for (var line : lines) {
			line = line.stripLeading();
			if (line.contains("}")) {
				if (line.indexOf("}") + 1 < line.length() && line.charAt(line.indexOf("}") + 1) == ';') {
					buf.append(TestAndAssertionGenerator.getTabs(TestAndAssertionGenerator.countBrackets(buf.toString(), '{')) + line + "\n");
				} else {
					buf.append(TestAndAssertionGenerator.getTabs(TestAndAssertionGenerator.countBrackets(buf.toString(), '{') - 1) + line + "\n");
				}
			} else {
				buf.append(TestAndAssertionGenerator.getTabs(TestAndAssertionGenerator.countBrackets(buf.toString(), '{')) + line + "\n");
			}
		}
		
		return buf.toString();
	}
	
	private String getAllPreConditions(EObject statement, final GlobalConditions conds) {
		String preCon = ((AbstractStatement)statement).getPreCondition().getName();
		if (preCon.contains("modifiable")) {
			preCon = preCon.split(";", 2)[1].trim();
		}
		String output = preCon;
		var cur = statement.eContainer();
		
		while (cur != null) {
			if (cur instanceof AbstractStatement) {
				if (((AbstractStatement)cur).getPreCondition() != null) {
					preCon = ((AbstractStatement)cur).getPreCondition().getName();
				}
			} else if (cur instanceof CompositionStatement) {
				if (((CompositionStatement)cur).getPreCondition() != null) {
					preCon = ((CompositionStatement)cur).getPreCondition().getName();
				}
			} else if (cur instanceof MethodStatement) {
				if (((MethodStatement)cur).getPreCondition() != null) {
					preCon = ((MethodStatement)cur).getPreCondition().getName();
				}
			} else if (cur instanceof ReturnStatement) {
				if (((ReturnStatement)cur).getPreCondition() != null) {
					preCon = ((ReturnStatement)cur).getPreCondition().getName();
				}
			} else if (cur instanceof SelectionStatement) {
				if (((SelectionStatement)cur).getPreCondition() != null) {
					preCon = ((SelectionStatement)cur).getPreCondition().getName();
				}
			} else if (cur instanceof SkipStatement) {
				if (((SkipStatement)cur).getPreCondition() != null) {
					preCon = ((SkipStatement)cur).getPreCondition().getName();
				}
			} else if (cur instanceof SmallRepetitionStatement) {
				if (((SmallRepetitionStatement)cur).getPreCondition() != null) {
					preCon = ((SmallRepetitionStatement)cur).getPreCondition().getName();
				}
			} else if (cur instanceof StrengthWeakStatement) {
				if (((StrengthWeakStatement)cur).getPreCondition() != null) {
					preCon = ((StrengthWeakStatement)cur).getPreCondition().getName();
				}
			} else if (cur instanceof OriginalStatement) {
				if (((OriginalStatement)cur).getPreCondition() != null) {
					preCon = ((OriginalStatement)cur).getPreCondition().getName();
				}
			}
			if (preCon.contains("modifiable")) {
				preCon = preCon.split(";", 2)[1];
			}
			if (!output.contains(preCon)) {
				output += " & " + preCon;
			}
			cur = cur.eContainer();
		}
		final List<String> invariants = conds == null ? new ArrayList<String>() : conds.getConditions().stream()
				.map(c -> c.getName())
				.toList();
		for (int i = 0; i < invariants.size(); i++) {
			if (!invariants.get(i).contains("null") && !invariants.get(i).contains("self")) {
				output += " & " + invariants.get(i);			
			}
		}
		output = output.replaceAll("\\n", "");
		output = output.replaceAll("\\r", "");
		return output;
	}
	
	public String getStatementPath(EObject statement) {
		String path = "";
		Stack<String> containers = new Stack<String>();
		EObject cur = statement;
		
		
		while (cur != null) {
			if (cur instanceof AbstractStatement) {
				containers.push(((AbstractStatement)cur).getName());
			} else if (cur instanceof CompositionStatement) {
				containers.push(((CompositionStatement)cur).getName());
			} else if (cur instanceof MethodStatement) {
				containers.push(((MethodStatement)cur).getName());
			} else if (cur instanceof ReturnStatement) {
				containers.push(((ReturnStatement)cur).getName());
			} else if (cur instanceof SelectionStatement) {
				containers.push(((SelectionStatement)cur).getName());
			} else if (cur instanceof SkipStatement) {
				containers.push(((SkipStatement)cur).getName());
			} else if (cur instanceof SmallRepetitionStatement) {
				containers.push(((SmallRepetitionStatement)cur).getName());
			} else if (cur instanceof StrengthWeakStatement) {
				containers.push(((StrengthWeakStatement)cur).getName());
			} else if (cur instanceof OriginalStatement) {
				containers.push(((OriginalStatement)cur).getName());
			}
			cur = cur.eContainer();
		}
		while (containers.size() > 1) {
			path += containers.pop() + " -> ";
		}
		path += containers.pop().replaceAll("\\n", "").replaceAll("\\r", "");
		return path;
	}
	
	private String removeDotIdentifiers(String condition, String className) {
		condition = condition.replaceAll("\\s\\.", "");
		condition = condition.replaceAll(className + "\\.", "");
		condition = condition.replaceAll("this\\.", "");
		return condition;
	}
	
	private String removeFunctions(String condition) {
		Pattern p = Pattern.compile("IntStream");
		Matcher m = p.matcher(condition);
		
		while (m.find()) {
			String helper = condition.substring(0, m.start());
			int start = helper.lastIndexOf("&");
			helper = condition.substring(m.start(), condition.length());
			int end = helper.indexOf("&") + m.start();
			if (start == -1) {
				condition = condition.substring(end + 1, condition.length());
			} else if (end == -1) {
				condition = condition.substring(0, start);
			} else {
				condition = condition.substring(0, start) + condition.substring(end, condition.length());
			}
			m = p.matcher(condition);
		}
		return condition;
	}
	
	private String removeFloatingClosingBrackets(String condition) {
		int bracketCounter = 0;
		for (int i = 0; i < condition.length(); i++) {
			if (condition.charAt(i) == '(') bracketCounter++;
			else if (condition.charAt(i) == ')') bracketCounter--;
			if (bracketCounter < 0) {
				bracketCounter++;
				condition = condition.substring(0, i) + " " + condition.substring(i + 1, condition.length());
			}
		}
		return condition;
	}
	
	private String insertExecutedTag(String code) {
		return code.substring(0, code.indexOf(STATEMENT_PH)) 
				+ EXECUTED_TAG
				+ "\n"
				+ code.substring(code.indexOf(STATEMENT_PH), code.length());
	}
	
	private static void getChildStatements(EObject statement, final ArrayList<EObject> childStatements) {
		if (statement.getClass().equals(AbstractStatementImpl.class)) {
			if (((AbstractStatement)statement).getRefinement() == null) {
				childStatements.add(statement);
			}
		} else if (statement instanceof SkipStatement) {
			childStatements.add(statement);
		} else if (statement instanceof ReturnStatement) {
			childStatements.add(statement);
		} else if (statement instanceof MethodStatement) {
			childStatements.add(statement);
		}
		for (EObject s : statement.eContents()) {
			getChildStatements(s, childStatements);
		}
	}
	
	public static List<EObject> collectAllStatements(final CbCFormula formula) {
		var output = new ArrayList<EObject>();
		final var startStatement = formula.getStatement();
		getChildStatements(startStatement, output);
		return output;
	}
	
	/**
	 * Generates inputs for given preconditions. If the generation of input data fails, 
	 * this method uses the following strategy to generate data anyways:
	 * try only the preconditions of the provided startStatement. If that also doesn't work, 
	 * try just the precondition from the first hoare tripel. If there is still no solution, 
	 * return false	
	 * @param preConditions
	 * @param className
	 * @param statement
	 * @param formula
	 * @param vars
	 * @param generator
	 * @return Generated data.
	 */
	private List<InputData> genInputs(String preConditions, final String className, final AbstractStatement statement, final CbCFormula formula, final JavaVariables vars, final TestAndAssertionGenerator generator) {
		final PreConditionSolver preSolver = new PreConditionSolver(vars);
		List<InputData> data;
		try {
			data = preSolver.solve(preConditions);
		} catch (Exception e) {
			Console.println(e.getMessage());
			data = null;
		}
		// fallback strategy:
		// try only the preconditions of the provided startStatement
		// if that also doesn't work, try just the precondition from the first hoare tripel
		// if there is still no solution, return false	
		if (data == null) {
			Console.println("TestStatementInfo: Couldn't generate data using all preconditions. Falling back to the precondition of the statement under test.");
			preConditions = cleanCondition(statement.getPreCondition().getName(), className, generator);
			try {
				data = preSolver.solve(preConditions);
			} catch (Exception e) {
				Console.println(e.getMessage());
				data = null;
			}
			if (data == null) {
				Console.println("TestStatementInfo: Couldn't generate data using the precondition of the statement under test. Falling back to the precondition of the formula.");
				preConditions = cleanCondition(formula.getStatement().getPreCondition().getName(), className, generator);
				try {
					data = preSolver.solve(preConditions);
				} catch (PreConditionSolverException e) {
					Console.println(e.getMessage());
					e.printStackTrace();
					Console.println("TestStatement: Couldn't parse preconditions of neither the statement nor the formula.");
					Console.println("TestStatement: Consider using 'usePreConditions(false)' in 'PreConditionSolver'.");
					return null;
				}
			}
		}
		return data;
	}
		
	private String cleanCondition(String condition, final String className, final TestAndAssertionGenerator generator) {
		condition = generator.translateCondition(condition, "this", new ArrayList<InputData>(), false);
		condition = removeFunctions(condition);
		condition = removeFloatingClosingBrackets(condition);
		condition = removeDotIdentifiers(condition, className);
		return condition;
	}	
	
	public boolean testStatement(final AbstractStatement statement, final JavaVariables vars, final GlobalConditions conds, final CbCFormula formula, boolean isReturnStatement) throws TestAndAssertionGeneratorException, TestStatementException {		
		final String className;

		if (formula.getClassName().isEmpty()) {
			className = TestAndAssertionGenerator.GENERATED_CLASSNAME;
		} else {
			className = formula.getClassName();
		}
		final TestAndAssertionGenerator generator = new TestAndAssertionGenerator(fp);
		generator.setProjectPath(this.projectPath);		
					
		// generate the code until the selection
		String code;
		try {
			code = TestAndAssertionGenerator.genCodeUntilStatement(formula, statement);
		} catch (TestAndAssertionGeneratorException e) {
			throw e;
		}
		if (code.isEmpty()) {
			return false;
		}
		// now add pre conditions of selection as assertions
		var preCon = statement.getPreCondition().getName();
		if (preCon.contains("modifiable")) {
			preCon = preCon.split(";", 2)[1];
		}
		
		// get all vars used in the code
		final List<Variable> usedVars = getUsedVars(code.replaceAll(Pattern.quote(STATEMENT_PH), statement.getName().trim()), vars);
		// use PreconditionSolver to solve preconditions and determine values for all variables
		var allPreConditions = getAllPreConditions(statement, conds);
		allPreConditions = cleanCondition(allPreConditions, className, generator);
		final List<InputData> data = genInputs(allPreConditions, className, statement, formula, vars, generator);
		if (data == null) {
			return false;
		}
		final var programPreConsStr = formula.getStatement().getPreCondition().getName();//TestAndAssertionGenerator.parseConditions2(conds, formula.getStatement().getPreCondition());
		final var programPreCons = generator.translateConditionToJava(programPreConsStr, "", data, this.projectPath);
		var postCon = statement.getPostCondition().getName();
		if (postCon.contains("modifiable")) {
			postCon = postCon.split(";", 2)[1].trim();
		}
		postCon = postCon.replaceAll("this\\.", "");
		final List<Variable> usedVarsPostCon = getUsedVars(postCon, vars);
		final List<Variable> usedVarsPreCon = getUsedVars(programPreConsStr, vars);
		usedVarsPostCon.addAll(usedVarsPreCon);
		for (var v : usedVarsPostCon) {
			if(!Variable.containsVar(usedVars, v)) {
				usedVars.add(v);
			}
		}
		code = removeTabs(code);
		// insert fixture for variables used in the statement and the post condition
		code = insertFixture(code, data, Variable.getAllGVars(vars), usedVars, vars, postCon, allPreConditions, generator);	
		// insert precondition checks
		try {
			code = insertPreconditionChecks(code, programPreCons, 0);
		} catch (TestStatementException e) {
			throw e;
		}
		// handle the post condition and translate it to JavaCondition
		var javaCondition = generator.translateConditionToJava(postCon, "", data, this.projectPath);
		// generate dependencies as well (method call ect.)
		var innerMethod = "{\n" + code + "\n}";
		var codeWoHelper = innerMethod.replaceAll(Pattern.quote(STATEMENT_PH), statement.getName().trim());
		codeWoHelper = codeWoHelper.replaceAll("self\\.", "this.");
		codeWoHelper = codeWoHelper.substring(0, codeWoHelper.indexOf(PRECHECKS_START)) + codeWoHelper.substring(codeWoHelper.indexOf(PRECHECKS_END) + PRECHECKS_END.length());
		//codeWoHelper = codeWoHelper.substring(0, codeWoHelper.length() - 1) + 
		var dependencies = generator.genAllDependenciesOfMethod(codeWoHelper, className, statement.getPostCondition().getName());	
		// insert an executed tag so that we can determine if the statement was executed
		code = insertExecutedTag(code);
		//code = insertAssertions(code, preJavaCondition);
		code = removeTabs(code);
		// get path to statement
		final var path = getStatementPath(statement);
		code = code + "context.setAttribute(\"path\", \"" + path + "\");\n";
		// insert assertions for the post condition
		var fullMethod = constructDummyMethod(code, javaCondition, data);
		// insert statement to the code
		if (isReturnStatement) {
			if (Variable.containsVarDefinition(code, "result")) {
				fullMethod = fullMethod.replaceAll(Pattern.quote(STATEMENT_PH), "result = " + statement.getName().trim());
			} else {
				fullMethod = fullMethod.replaceAll(Pattern.quote(STATEMENT_PH), "var result = " + statement.getName().trim());
			}
		} else {
			fullMethod = fullMethod.replaceAll(Pattern.quote(STATEMENT_PH), statement.getName().trim());
		}
		fullMethod = fullMethod.replaceAll("self\\.", "this.");
		// place dummy method inside code
		placeDummyMethod(dependencies, className, innerMethod.replaceAll(Pattern.quote(STATEMENT_PH), statement.getName().trim()), fullMethod);	
		// compile
		if(!compile(dependencies, className, formula.getMethodName(), generator)) {
			return false;
		}
		// use testng to execute the file and check the statement
		if (executeTest(className, generator)) {
			setPathTested(statement, true);
		} else {
			setPathTested(statement, false);
		}	
		return true;
	}

	public static EObject getPathLeaf(EObject container) {
		while (container.eContents().size() > 0) {
			container = container.eContents().get(0);
		}
		return container;
	}
}
