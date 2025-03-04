package de.tu_bs.cs.isf.cbc.tool.features;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
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
import de.tu_bs.cs.isf.cbc.exceptions.DiagnosticsException;
import de.tu_bs.cs.isf.cbc.exceptions.ExceptionMessages;
import de.tu_bs.cs.isf.cbc.exceptions.FeatureCallerException;
import de.tu_bs.cs.isf.cbc.exceptions.PreConditionSolverException;
import de.tu_bs.cs.isf.cbc.exceptions.ReferenceException;
import de.tu_bs.cs.isf.cbc.exceptions.SettingsException;
import de.tu_bs.cs.isf.cbc.exceptions.TestAndAssertionGeneratorException;
import de.tu_bs.cs.isf.cbc.exceptions.TestStatementException;
import de.tu_bs.cs.isf.cbc.exceptions.UnexpectedTokenException;
import de.tu_bs.cs.isf.cbc.tool.helper.ConditionHandler;
import de.tu_bs.cs.isf.cbc.tool.helper.PreConditionSolver;
import de.tu_bs.cs.isf.cbc.tool.helper.TestStatementListener;
import de.tu_bs.cs.isf.cbc.util.ClassHandler;
import de.tu_bs.cs.isf.cbc.util.CodeHandler;
import de.tu_bs.cs.isf.cbc.util.Colors;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.DiagramPartsExtractor;
import de.tu_bs.cs.isf.cbc.util.Features;
import de.tu_bs.cs.isf.cbc.util.FileHandler;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.cbc.util.InputData;
import de.tu_bs.cs.isf.cbc.util.JavaCondition;
import de.tu_bs.cs.isf.cbc.util.MethodHandler;
import de.tu_bs.cs.isf.cbc.util.MyAbstractAsynchronousCustomFeature;
import de.tu_bs.cs.isf.cbc.util.TestUtilSPL;
import de.tu_bs.cs.isf.cbc.util.Token;
import de.tu_bs.cs.isf.cbc.util.TokenType;
import de.tu_bs.cs.isf.cbc.util.Tokenizer;
import de.tu_bs.cs.isf.cbc.util.Variable;
import de.tu_bs.cs.isf.cbc.util.diagnostics.DataCollector;
import de.tu_bs.cs.isf.cbc.util.diagnostics.DataType;
import de.tu_bs.cs.isf.cbc.util.statistics.StatDataCollector;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.testng.TestNG;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlSuite.ParallelMode;

/**
 * Class for testing a single statement.
 *
 * @author Fynn
 */
public class TestStatement extends MyAbstractAsynchronousCustomFeature {
	private final String DUMMY_SIGNATURE = "public void dummyMethod(ITestContext context)";
	private final String STATEMENT_PH = "<statement>";
	public static final String EXECUTED_TAG = "context.setAttribute(\"executed\", \"\");";
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
					|| bo instanceof ReturnStatement || bo instanceof MethodStatement
					|| bo instanceof OriginalStatement)) {
				AbstractStatement statement = (AbstractStatement) bo;
				if (statement.getRefinement() == null) {
					ret = true;
				}
			}
		}
		return ret;
	}

	public boolean testStatement(Diagram diagram, AbstractStatement statement) {
		final URI uri;
		boolean returnStatement;

		returnStatement = statement instanceof ReturnStatement;
		DiagramPartsExtractor extractor = new DiagramPartsExtractor(diagram);
		JavaVariables vars = extractor.getVars();
		GlobalConditions conds = extractor.getConds();
		CbCFormula formula = extractor.getFormula();
		uri = diagram.eResource().getURI();
		this.projectPath = uri;
		DataCollector dataCollector;
		try {
			dataCollector = new DataCollector(projectPath, DataType.PATH, diagram.getName());
		} catch (DiagnosticsException e) {
			e.printStackTrace();
			return false;
		}
		FileHandler.instance.clearLog(this.projectPath);
		Features features = null;
		if (FileHandler.instance.isSPL(uri)) {
			try {
				features = new Features(uri);
			} catch (FeatureCallerException e) {
				e.printStackTrace();
				return false;
			}
		} else {
			features = null;
		}
		if (features != null) {
			Console.println("[SPL detected]", Colors.BLUE);
			for (int i = 0; i < features.getSize(); i++) {
				features.getNextConfig();
				Console.println(" > Configuration: [" + features.getConfigRep() + "]", Colors.BLUE);
				float pathTime;
				try {
					pathTime = testPath(statement, vars, conds, formula, returnStatement, features);
				} catch (SettingsException e) {
					e.printStackTrace();
					return false;
				}
				if (pathTime == -1) {
					continue;
				}
				dataCollector.addData(features.getCurConfigName(), getStatementPath(statement), pathTime);
				// save configuration in a separate file
				FileHandler.instance.saveConfig(uri, formula, features, false);
			}
		} else {
			float pathTime;
			try {
				pathTime = testPath(statement, vars, conds, formula, returnStatement, features);
			} catch (SettingsException e) {
				e.printStackTrace();
				return false;
			}
			dataCollector.addData(getStatementPath(statement), pathTime);
		}
		dataCollector.finish();
		return true;
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
				DiagramPartsExtractor extractor = new DiagramPartsExtractor(getDiagram());
				JavaVariables vars = extractor.getVars();
				GlobalConditions conds = extractor.getConds();
				CbCFormula formula = extractor.getFormula();
				uri = getDiagram().eResource().getURI();
				this.projectPath = uri;
				DataCollector dataCollector;
				try {
					dataCollector = new DataCollector(projectPath, DataType.PATH, getDiagram().getName());
				} catch (DiagnosticsException e) {
					e.printStackTrace();
					return;
				}
				FileHandler.instance.clearLog(this.projectPath);
				Features features = null;
				if (FileHandler.instance.isSPL(uri)) {
					try {
						features = new Features(uri);
					} catch (FeatureCallerException e) {
						e.printStackTrace();
						return;
					}
				} else {
					features = null;
				}
				if (features != null) {
					Console.println("[SPL detected]", Colors.BLUE);
					for (int i = 0; i < features.getSize(); i++) {
						features.getNextConfig();
						Console.println(" > Configuration: [" + features.getConfigRep() + "]", Colors.BLUE);
						float pathTime;
						try {
							pathTime = testPath(statement, vars, conds, formula, returnStatement, features);
						} catch (SettingsException e) {
							e.printStackTrace();
							return;
						}
						if (pathTime == -1) {
							continue;
						}
						dataCollector.addData(features.getCurConfigName(), getStatementPath(statement), pathTime);
						// save configuration in a separate file
						FileHandler.instance.saveConfig(uri, formula, features, false);
					}
				} else {
					float pathTime;
					try {
						pathTime = testPath(statement, vars, conds, formula, returnStatement, features);
					} catch (SettingsException e) {
						e.printStackTrace();
						return;
					}
					dataCollector.addData(getStatementPath(statement), pathTime);
				}
				// update pictogram
				updatePictogramElement(((Shape) pes[0]).getContainer());
				dataCollector.finish();
			}
		}
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1000000;
		monitor.done();
		Console.println("Testing finished.");
		Console.println("Time needed: " + duration + "ms");
	}

	public float testPath(final AbstractStatement statement, final JavaVariables vars, final GlobalConditions conds,
			final CbCFormula formula, final boolean returnStatement, final Features features) throws SettingsException {
		long start = System.nanoTime();
		Console.println(" > Testing path:", Colors.BLUE);
		Console.println("\t" + getStatementPath(statement));
		try {
			testStatement(statement, vars, conds, formula, returnStatement, features);
			return (System.nanoTime() - start) / 1000000;
		} catch (TestAndAssertionGeneratorException | TestStatementException | ReferenceException
				| UnexpectedTokenException | DiagnosticsException | MalformedURLException | ClassNotFoundException e) {
			Console.println(e.getMessage(), Colors.RED);
			e.printStackTrace();
			return -1;
		}
	}

	public static void setPathTested(EObject selectedStatement, boolean status) {
		EObject cur = selectedStatement;

		while (cur != null) {
			if (cur instanceof MethodStatement || cur instanceof ReturnStatement || cur instanceof SelectionStatement
					|| cur instanceof SkipStatement || cur instanceof SmallRepetitionStatement
					|| cur instanceof StrengthWeakStatement || cur instanceof OriginalStatement) {
				if (!((AbstractStatement) cur).isProven()) {
					((AbstractStatement) cur).setTested(status);
				} else {
					((AbstractStatement) cur).setTested(false);
				}
			} else if (cur instanceof CompositionStatement) {
				var comp = (CompositionStatement) cur;
				if ((comp.getFirstStatement().getRefinement() != null
						&& (comp.getFirstStatement().getRefinement().isTested()
								|| comp.getFirstStatement().getRefinement().isProven()))
						&& (comp.getSecondStatement().getRefinement() != null
								&& (comp.getSecondStatement().getRefinement().isTested()
										|| comp.getSecondStatement().getRefinement().isProven()))
						&& !comp.isProven()) {
					comp.setTested(status);
				} else {
					comp.setTested(false);
				}
			} else if (cur instanceof AbstractStatement) {
				if (((AbstractStatement) cur).getRefinement() == null) {
					if (!((AbstractStatement) cur).isProven()) {
						((AbstractStatement) cur).setTested(status);
					} else {
						((AbstractStatement) cur).setTested(false);
					}
				}
			} else if (cur instanceof CbCFormula) {
				if (!((CbCFormula) cur).isProven()) {
					((CbCFormula) cur).setTested(status);
				} else {
					((CbCFormula) cur).setTested(false);
				}
			}
			cur = cur.eContainer();
		}
	}

	private List<Variable> getUsedVars(final String str, final JavaVariables vars) throws SettingsException {
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

	private String insertOldVars(String code, final String postCon, final List<Variable> initializedVars,
			final JavaVariables vars, final String preCon, final List<InputData> data)
			throws UnexpectedTokenException, SettingsException {
		var allVars = Variable.getAllVars(vars);
		Tokenizer tokenizer = new Tokenizer(preCon + " " + postCon);
		Token token;
		try {
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
					if (!(classCode = ClassHandler.classExists(this.projectPath, name)).isEmpty()) {
						// get the identifier after the first '.'
						String identifier = token.getValue().substring(token.getValue().indexOf("."),
								token.getValue().length());
						final var idVariable = new Variable("", name + identifier);
						final var variable = new Variable(name, "old_" + name);
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
						final var variable = new Variable(v.getType(), "old_" + name);
						if (v.getName().equals(name)) {
							if (Variable.containsVar(initializedVars, variable)) {
								continue;
							}
							initializedVars.add(variable);
							code = v.getType() + " " + "old_" + name + " = " + v.getName() + ";\n" + code;
						}
					}
					// happens when a variable isn't defined in the current diagram
					final var variable = new Variable("", "old_" + name);
					if (!Variable.containsVar(initializedVars, variable)) {
						if (!name.contains("[")) {
							code = "var old_" + name + " = " + name + ";\n" + code;
							initializedVars.add(variable);
						}
					}
				}
			}
		} catch (UnexpectedTokenException e) {
			throw e;
		}
		return code;
	}

	private String insertParams(String code, final List<Variable> usedVars, final List<Variable> initializedVars,
			final List<InputData> data) throws SettingsException {
		for (var v : usedVars) {
			if (v.getKind() == VariableKind.PARAM) {
				for (int i = 0; i < data.size(); i++) {
					if (data.get(i).getName().equals(v.getName())) {
						// add attribute for the testng context
						var val = data.get(i).getRandomValue();
						// var contextStr = "context.setAttribute(\"0" + data.get(i).getName() + "\",
						// \"" + val + "\");\n";
						if (data.get(i).getDimensions() == 0) {
							code = data.get(i).getType() + " " + data.get(i).getName() + " = " + val + ";\n" + code;
						} else {
							if (data.get(i).isPrimitive()) {
								code = data.get(i).getPrimitiveArrayInit() + ";\n" + code;
							} else {
								code = data.get(i).getType() + " " + data.get(i).getName() + " = "
										+ data.get(i).getArrayRep() + ";\n" + code;
							}
						}
						initializedVars.add(new Variable(v.getType(), v.getName()));
					}
				}
			}
		}
		return code;
	}

	private String insertFixture(String code, final List<InputData> data, final List<Variable> gVars,
			final List<Variable> usedVars, final JavaVariables vars, final String postCon, final String preCon)
			throws UnexpectedTokenException, SettingsException {
		final var initializedVars = new ArrayList<Variable>();
		code = "\n" + code;
		code = insertOldVars(code, postCon, initializedVars, vars, preCon, data);
		code = insertParams(code, usedVars, initializedVars, data);

		for (int i = 0; i < data.size(); i++) {
			final var variable = new Variable(data.get(i).getType(), data.get(i).getName());
			if (Variable.containsVar(initializedVars, variable)) {
				continue;
			}
			// add var to the code
			// also add the var + value to the testng context
			var val = data.get(i).getRandomValue();
			if (val == null || val.isEmpty()) {
				continue;
			}
			// var contextStr = "context.setAttribute(\"0" + data.get(i).getName() + "\",
			// \"" + val + "\");\n";
			if (data.get(i).getDimensions() == 0) {
				if (Variable.containsVar(gVars, variable)) {
					code = data.get(i).getName() + " = " + val + ";\n" /* + contextStr */ + code;
				} else {
					code = data.get(i).getType() + " " + data.get(i).getName() + " = " + val + ";\n" /* + contextStr */
							+ code;
				}
			} else {
				if (data.get(i).isPrimitive()) {
					String arrayInitStr = data.get(i).getPrimitiveArrayInit();
					if (Variable.containsVar(gVars, variable)) {
						arrayInitStr = arrayInitStr.split("\\s", 2)[1];
					}
					code = arrayInitStr + ";\n" + code;
				} else {
					if (Variable.containsVar(gVars, variable)) {
						code = data.get(i).getName() + " = " + data.get(i).getArrayRep() + ";\n" + code;
					} else {
						code = data.get(i).getType() + " " + data.get(i).getName() + " = " + data.get(i).getArrayRep()
								+ ";\n" + code;
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
			// var contextStr = "context.setAttribute(\"0" + uData.getName() + "\", \"" +
			// val + "\");\n";
			if (uData.getDimensions() == 0) {
				if (Variable.containsVar(gVars, usedVars.get(i))) {
					code = uData.getName() + " = " + val + ";\n" /* + contextStr */ + code;
				} else {
					code = uData.getType() + " " + uData.getName() + " = " + val + ";\n" /* + contextStr */
							+ code;
				}
			} else {
				if (uData.isPrimitive()) {
					code = uData.getPrimitiveArrayInit() + ";\n" + code;
				} else {
					if (Variable.containsVar(gVars, usedVars.get(i))) {
						code = uData.getName() + " = " + uData.getArrayRep() + ";\n" + code;
					} else {
						code = uData.getType() + " " + uData.getName() + " = " + uData.getArrayRep() + ";\n" + code;
					}
				}
			}
		}
		return code;
	}

	private String constructDummyMethod(String innerMethod, final JavaCondition javaCondition,
			final List<InputData> data) {
		var code = "@Test\n" + DUMMY_SIGNATURE + " {\n" + innerMethod.substring(0, innerMethod.length()); // 1, ...
		code = code.substring(0, code.indexOf(STATEMENT_PH) + STATEMENT_PH.length()) + "\n" + javaCondition.get()
				+ code.substring(code.indexOf(STATEMENT_PH) + STATEMENT_PH.length(), code.length());
		code += "\n}";
		code = CodeHandler.indentCode(code, 1);
		return code;
	}

	private List<String> placeDummyMethod(final List<String> dependencies, final String testClassName,
			final String innerMethod, String fullMethod) {
		final var code = new StringBuffer();
		code.append(ClassHandler.getImportsStr());

		for (int i = 0; i < dependencies.size(); i++) {
			if (dependencies.get(i).isBlank()) {
				continue;
			}
			var className = dependencies.get(i).split("\\s", 4)[2].replaceAll("\\{", "");
			if (className.equals(testClassName)) {
				dependencies.set(i, dependencies.get(i).replaceAll(Pattern.quote(innerMethod), ""));
				// replace return statements in test
				fullMethod = replaceMethodReturns(fullMethod);
				// fullMethod = fullMethod.replaceAll("return\\s", "//[REMOVED BY GENERATOR]
				// return ");
				var stripped = dependencies.get(i).strip();
				dependencies.set(i,
						code.toString() + stripped.substring(0, stripped.length() - 1) + fullMethod + "\n}");
			}
		}
		return dependencies;
	}

	private String replaceMethodReturns(String code) {
		int offset = 0;
		String curCode = code;

		while (curCode.contains("return ")) {
			int retIndex = curCode.indexOf("return ");
			String blockCode = CodeHandler.getCurrentBlock(code, retIndex + offset);
			if (!blockCode.contains("Supplier<Boolean>")) {
				code = code.substring(0, retIndex + offset) + "//[REMOVED BY GENERATOR] return "
						+ code.substring(retIndex + offset + "return ".length(), code.length());
			}
			curCode = curCode.substring(retIndex + "return ".length(), curCode.length());
			offset = Math.abs(code.length() - curCode.length());
		}
		return code;
	}

	private boolean compile(final List<String> dependencies, final String className, final String methodName,
			final TestAndAssertionGenerator generator)
			throws TestAndAssertionGeneratorException, TestStatementException {
		var pathOfPlugins = System.getProperty("osgi.syspath");
		var pluginFile = new File(pathOfPlugins);
		var highestVersion = findLatestTestNGversion(pluginFile);

		// add existing src code to dependencies
		addExistingDependencies(dependencies, generator);

		// compile dependencies
		var options = Arrays.asList("-cp", pathOfPlugins + "/" + highestVersion);
		if (dependencies.size() > 0 && !generator.compileFileContents(dependencies, methodName, options)) {
			return false;
		}
		return true;
	}

	private void addExistingDependencies(final List<String> dependencies, TestAndAssertionGenerator generator) {
		for (var fileName : generator.getSrcFiles()) {
			boolean contained = false;
			for (int i = 0; i < dependencies.size(); ++i) {
				if (ClassHandler.getClassNameFromCode(dependencies.get(i)).equals(fileName)) {
					contained = true;
					break;
				}
			}
			if (contained)
				continue;
			var code = ClassHandler.classExists(projectPath, fileName);
			dependencies.add(code);
		}
	}

	private String findLatestTestNGversion(File pluginFile) throws TestStatementException {
		List<String> testNgFiles = new ArrayList<String>();
		for (var file : pluginFile.list()) {
			if (!file.contains("org.testng_")) {
				continue;
			}
			testNgFiles.add(file);
		}
		if (testNgFiles.isEmpty()) {
			throw new TestStatementException(ExceptionMessages.dependencyNotFound("TestNG"));
		}
		var highestVersion = testNgFiles.stream().map(s -> s.split("org.testng\\_")[1]).sorted()
				.reduce((first, second) -> second).get();
		return "org.testng_" + highestVersion;
	}

	private boolean executeTest(final String testName, final TestAndAssertionGenerator generator)
			throws DiagnosticsException, MalformedURLException, ClassNotFoundException {
		final XmlSuite suite;
		// first create the xml suite needed to run TestNG
		suite = generator.createXmlSuite("file://" + FileUtil.getProjectLocation(this.projectPath) + "/tests/",
				testName);
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

	private String getAllPreConditions(EObject statement, final GlobalConditions conds) {
		String preCon = ((AbstractStatement) statement).getPreCondition().getName();
		if (preCon.contains("modifiable")) {
			preCon = preCon.split(";", 2)[1].trim();
		}
		String output = preCon;
		var cur = statement.eContainer();

		while (cur != null) {
			if (cur instanceof AbstractStatement) {
				if (((AbstractStatement) cur).getPreCondition() != null) {
					preCon = ((AbstractStatement) cur).getPreCondition().getName();
				}
			} else if (cur instanceof CompositionStatement) {
				if (((CompositionStatement) cur).getPreCondition() != null) {
					preCon = ((CompositionStatement) cur).getPreCondition().getName();
				}
			} else if (cur instanceof MethodStatement) {
				if (((MethodStatement) cur).getPreCondition() != null) {
					preCon = ((MethodStatement) cur).getPreCondition().getName();
				}
			} else if (cur instanceof ReturnStatement) {
				if (((ReturnStatement) cur).getPreCondition() != null) {
					preCon = ((ReturnStatement) cur).getPreCondition().getName();
				}
			} else if (cur instanceof SelectionStatement) {
				if (((SelectionStatement) cur).getPreCondition() != null) {
					preCon = ((SelectionStatement) cur).getPreCondition().getName();
				}
			} else if (cur instanceof SkipStatement) {
				if (((SkipStatement) cur).getPreCondition() != null) {
					preCon = ((SkipStatement) cur).getPreCondition().getName();
				}
			} else if (cur instanceof SmallRepetitionStatement) {
				if (((SmallRepetitionStatement) cur).getPreCondition() != null) {
					preCon = ((SmallRepetitionStatement) cur).getPreCondition().getName();
				}
			} else if (cur instanceof StrengthWeakStatement) {
				if (((StrengthWeakStatement) cur).getPreCondition() != null) {
					preCon = ((StrengthWeakStatement) cur).getPreCondition().getName();
				}
			} else if (cur instanceof OriginalStatement) {
				if (((OriginalStatement) cur).getPreCondition() != null) {
					preCon = ((OriginalStatement) cur).getPreCondition().getName();
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
		final List<String> invariants = conds == null
				? new ArrayList<String>()
				: conds.getConditions().stream().map(c -> c.getName()).toList();
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
			if (cur instanceof CompositionStatement) {
				containers.push(((CompositionStatement) cur).getName());
			} else if (cur instanceof MethodStatement) {
				containers.push(((MethodStatement) cur).getName());
			} else if (cur instanceof ReturnStatement) {
				containers.push(((ReturnStatement) cur).getName());
			} else if (cur instanceof SelectionStatement) {
				containers.push(((SelectionStatement) cur).getGuards().get(0).getName());
			} else if (cur instanceof SkipStatement) {
				containers.push(((SkipStatement) cur).getName());
			} else if (cur instanceof SmallRepetitionStatement) {
				containers.push(((SmallRepetitionStatement) cur).getName());
			} else if (cur instanceof StrengthWeakStatement) {
				containers.push(((StrengthWeakStatement) cur).getName());
			} else if (cur instanceof OriginalStatement) {
				containers.push(((OriginalStatement) cur).getName());
			} else if (cur instanceof AbstractStatement) {
				containers.push(((AbstractStatement) cur).getName());
			}
			cur = cur.eContainer();
		}
		while (containers.size() > 1) {
			path += containers.pop() + " -> ";
		}
		path += containers.pop().replaceAll("\\n", "").replaceAll("\\r", "");
		return path;
	}

	private String insertExecutedTag(String code) {
		return code.substring(0, code.indexOf(STATEMENT_PH)) + EXECUTED_TAG + "\n"
				+ code.substring(code.indexOf(STATEMENT_PH), code.length());
	}

	private static void getChildStatements(EObject statement, final ArrayList<EObject> childStatements) {
		if (statement.getClass().equals(AbstractStatementImpl.class)) {
			if (((AbstractStatement) statement).getRefinement() == null) {
				childStatements.add(statement);
			}
		} else if (statement instanceof SkipStatement) {
			childStatements.add(statement);
		} else if (statement instanceof ReturnStatement) {
			childStatements.add(statement);
		} else if (statement instanceof MethodStatement) {
			childStatements.add(statement);
		} else if (statement instanceof OriginalStatement) {
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
	 * Generates inputs for given preconditions. If the generation of input data
	 * fails, this method uses the following strategy to generate data anyways: try
	 * only the preconditions of the provided startStatement. If that also doesn't
	 * work, try just the precondition from the first hoare tripel. If there is
	 * still no solution, return false
	 *
	 * @param preConditions
	 * @param className
	 * @param statement
	 * @param formula
	 * @param vars
	 * @param generator
	 * @return Generated data.
	 * @throws UnexpectedTokenException
	 * @throws SettingsException
	 * @throws TestStatementException
	 */
	private List<InputData> genInputs(String preConditions, final String className, final AbstractStatement statement,
			final CbCFormula formula, final JavaVariables vars)
			throws UnexpectedTokenException, SettingsException, TestStatementException {
		final PreConditionSolver preSolver = new PreConditionSolver(vars);
		List<InputData> data;
		try {
			data = preSolver.solve(preConditions);
		} catch (Exception e) {
			Console.println(e.getClass().getName() + ": " + e.getMessage(), Colors.RED);
			e.printStackTrace();
			data = null;
		}
		// fallback strategy:
		// try only the preconditions of the provided startStatement
		// if that also doesn't work, try just the precondition from the first hoare
		// tripel
		// if there is still no solution, return false
		if (data == null) {
			Console.printWarn(
					"TestStatementInfo: Couldn't generate data using all preconditions. Falling back to the precondition of the statement under test.");
			preConditions = ConditionHandler.cleanCondition(projectPath, statement.getPreCondition().getName(),
					className);
			try {
				data = preSolver.solve(preConditions);
			} catch (Exception e) {
				Console.println(e.getClass().getName() + ": " + e.getMessage(), Colors.RED);
				e.printStackTrace();
				data = null;
			}
			if (data == null) {
				Console.printWarn(
						"TestStatementInfo: Couldn't generate data using the precondition of the statement under test. Falling back to the precondition of the formula.");
				preConditions = ConditionHandler.cleanCondition(projectPath,
						formula.getStatement().getPreCondition().getName(), className);
				try {
					data = preSolver.solve(preConditions);
				} catch (PreConditionSolverException e) {
					Console.println(e.getClass().getName() + ": " + e.getMessage(), Colors.RED);
					e.printStackTrace();
					Console.printWarn(
							"TestStatementInfo: Couldn't parse preconditions of neither the statement nor the formula.");
					Console.printWarn(
							"TestStatementInfo: Consider using 'usePreConditions(false)' in 'PreConditionSolver'.");
					throw new TestStatementException("Canno't generate tests for this method.");
				}
			}
		}
		return data;
	}

	private List<JavaVariable> addBaseVars(final JavaVariables vars) {
		var addedFields = new ArrayList<JavaVariable>();
		IProject p = FileUtil.getProject(projectPath);
		var newFields = ClassHandler.getFields(projectPath);

		for (var field : newFields) {
			var newVar = CbcmodelFactory.eINSTANCE.createJavaVariable();
			newVar.setKind(VariableKind.GLOBAL);
			newVar.setName(field.getName());
			vars.getVariables().add(newVar);
			addedFields.add(newVar);
		}
		return addedFields;
	}

	private void restoreVars(final Features features, final JavaVariables toRestore, final List<JavaVariable> oldVars) {
		if (features != null) {
			toRestore.getVariables().clear();
			toRestore.getVariables().addAll(oldVars);
		}
	}

	private void resolveOriginals(String code, final Features features, List<MethodHandler> originalMethods,
			List<MethodHandler> abstractMethods, final CbCFormula formula, final JavaVariables vars,
			final JavaVariable returnVar)
			throws TestAndAssertionGeneratorException, SettingsException, ReferenceException {
		if (FileHandler.instance.isSPL(projectPath)) {
			if (code.contains("original")) {
				TestUtilSPL.getInstance().handleOriginalCode(fp, projectPath, code, features, originalMethods,
						formula.getMethodObj().getSignature(), vars, returnVar);
				code = code.replaceAll("original\\(", originalMethods.get(0).getMethodName() + "(");
			}
			TestUtilSPL.getInstance().handleAbstractMethodCalls(fp, projectPath, code, features, abstractMethods);
			for (var originalMethod : originalMethods) {
				TestUtilSPL.getInstance().handleAbstractMethodCalls(fp, projectPath, originalMethod.getInnerCode(),
						features, abstractMethods);
			}
		}
	}

	private void resolveDepOriginals(List<ClassHandler> dependencies, final Features features, final CbCFormula formula,
			final JavaVariables vars, final JavaVariable returnVar)
			throws TestAndAssertionGeneratorException, SettingsException, ReferenceException {
		var originalMethods = new ArrayList<MethodHandler>();
		var abstractMethods = new ArrayList<MethodHandler>();
		for (var clazz : dependencies) {
			for (var method : clazz.getAllMethods()) {
				// TODO
			}
			originalMethods.clear();
		}
	}

	public boolean testStatement(final AbstractStatement statement, final JavaVariables vars,
			final GlobalConditions conds, final CbCFormula formula, boolean isReturnStatement, final Features features)
			throws TestAndAssertionGeneratorException, TestStatementException, ReferenceException,
			UnexpectedTokenException, DiagnosticsException, SettingsException, MalformedURLException,
			ClassNotFoundException {
		final JavaVariable returnVar = Variable.getReturnVar(vars);
		final String className = ClassHandler.getClassName(formula);
		String statementName = statement.getName().trim();
		StatDataCollector.checkForId(statement);
		TestAndAssertionGenerator generator = new TestAndAssertionGenerator(fp);
		generator.setProjectPath(projectPath);

		var oldVars = new ArrayList<JavaVariable>();
		oldVars.addAll(vars.getVariables());

		// make sure vars contains base variables as well
		if (features != null) {
			addBaseVars(vars);
		}

		// generate the code until the selection
		String code;
		code = TestAndAssertionGenerator.genCodeUntilStatement(formula, statement);
		if (code.isEmpty()) {
			// remove temporarily added vars
			restoreVars(features, vars, oldVars);
			return false;
		}
		final var originalMethods = new ArrayList<MethodHandler>();
		final var abstractMethods = new ArrayList<MethodHandler>();
		var codeWithStatement = code.replaceAll(Pattern.quote(STATEMENT_PH), statementName);
		resolveOriginals(codeWithStatement, features, originalMethods, abstractMethods, formula, vars, returnVar);
		// now add pre conditions of selection as assertions
		// get all vars used in the code
		final List<Variable> usedVars = getUsedVars(code.replaceAll(Pattern.quote(STATEMENT_PH), statementName), vars);
		// use PreconditionSolver to solve preconditions and determine values for all
		// variables
		var allPreConditions = getAllPreConditions(statement, conds);
		allPreConditions = ConditionHandler.cleanCondition(projectPath, allPreConditions, className);
		final List<InputData> data = genInputs(allPreConditions, className, statement, formula, vars);
		if (data == null) {
			// remove temporarily added vars
			restoreVars(features, vars, oldVars);
			return false;
		}
		var programPreConsStr = formula.getStatement().getPreCondition().getName();
		var postCon = statement.getPostCondition().getName();
		if (features != null) {
			boolean isPreCon = true;
			programPreConsStr = TestUtilSPL.getInstance().handleOriginalCondition(fp, programPreConsStr, isPreCon,
					features);
			isPreCon = false;
			postCon = TestUtilSPL.getInstance().handleOriginalCondition(fp, postCon, isPreCon, features);
		}
		final var programPreCons = ConditionHandler.translateConditionToJava(projectPath, programPreConsStr, "", data);
		postCon = postCon.replaceAll("this\\.", "");
		// replace the keyword \result
		try {
			postCon = ConditionHandler.replaceResultKeyword(postCon, returnVar);
		} catch (ReferenceException e) {
			restoreVars(features, vars, oldVars);
			throw e;
		}
		final List<Variable> usedVarsPostCon = getUsedVars(postCon, vars);
		final List<Variable> usedVarsPreCon = getUsedVars(programPreConsStr, vars);
		usedVarsPostCon.addAll(usedVarsPreCon);
		for (var v : usedVarsPostCon) {
			if (!Variable.containsVar(usedVars, v)) {
				usedVars.add(v);
			}
		}
		code = CodeHandler.removeTabs(code);
		// insert fixture for variables used in the statement and the post condition
		code = insertFixture(code, data, ClassHandler.getGvarsOfCbCClassAsVar(projectPath, className), usedVars, vars,
				postCon, allPreConditions);
		// insert precondition checks
		code = CodeHandler.insertPreconditionChecks(code, programPreCons, 0);
		// handle the post condition and translate it to JavaCondition
		var javaCondition = ConditionHandler.translateConditionToJava(projectPath, postCon, "", data);
		// generate dependencies as well (method call ect.)
		var innerMethod = "{\n" + code + "\n}";
		var codeWoHelper = replaceHelper(innerMethod, statementName);
		// codeWoHelper = codeWoHelper.substring(0, codeWoHelper.length() - 1) +
		var dependencies = generator.genAllDependenciesOfMethod(codeWoHelper, null, className,
				statement.getPostCondition().getName());
		// replace original calls in dependencies
		resolveDepOriginals(dependencies, features, formula, vars, returnVar);
		// insert an executed tag so that we can determine if the statement was executed
		code = insertExecutedTag(code);
		// code = insertAssertions(code, preJavaCondition);
		code = CodeHandler.removeTabs(code);
		// get path to statement
		final var path = getStatementPath(statement);
		code = code + "context.setAttribute(\"path\", \"" + path + "\");\n";
		// insert assertions for the post condition
		var fullMethod = constructDummyMethod(code, javaCondition, data);
		// handle original call in the statement
		if (features != null) {
			statementName = statementName.replaceAll("original",
					"original_" + MethodHandler.getMethodNameFromSig(formula.getMethodObj().getSignature()));
		}
		// insert statement to the code
		if (isReturnStatement) {
			if (returnVar == null) {
				// remove temporarily added vars
				restoreVars(features, vars, oldVars);
				throw new TestStatementException(ExceptionMessages.RET);
			}
			String returnVarName = returnVar.getName().split("\\s")[1];
			if (Variable.containsVarDefinition(code, returnVarName)) {
				fullMethod = fullMethod.replaceAll(Pattern.quote(STATEMENT_PH), returnVarName + " = " + statementName);
			} else {
				fullMethod = fullMethod.replaceAll(Pattern.quote(STATEMENT_PH),
						"var " + returnVarName + " = " + statementName);
			}
		} else {
			fullMethod = fullMethod.replaceAll(Pattern.quote(STATEMENT_PH), statementName);
		}
		fullMethod = fullMethod.replaceAll("self\\.", "this.");
		// place original methods inside the dependencies
		placeOriginalMethods(dependencies, className, originalMethods);
		// place abstract methods inside the dependencies
		placeAbstractMethods(dependencies, className, abstractMethods);
		// place dummy method inside code
		final List<String> codes = new ArrayList<String>();
		codes.addAll(dependencies.stream().map(cc -> cc.getCode()).toList());
		placeDummyMethod(codes, className, innerMethod.replaceAll(Pattern.quote(STATEMENT_PH), statementName),
				fullMethod);

		// remove temporarily added vars
		restoreVars(features, vars, oldVars);

		// compile
		if (!compile(codes, className, formula.getMethodName(), generator)) {
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

	private void placeOriginalMethods(final List<ClassHandler> dependencies, final String className,
			final ArrayList<MethodHandler> originalMethods) {
		for (var classCode : dependencies) {
			if (classCode.getClassName().equals(className)) {
				classCode.addMethods(originalMethods);
			}
		}
	}

	private void placeAbstractMethods(final List<ClassHandler> dependencies, final String className,
			final ArrayList<MethodHandler> abstractMethods) {
		for (var classCode : dependencies) {
			if (classCode.getClassName().equals(className)) {
				classCode.addMethods(abstractMethods);
			}
		}
	}

	private String removeKeyword(String code, final String keyWord) {
		int start = code.indexOf(keyWord);
		while (start != -1) {
			int end = start + keyWord.length();
			if (code.charAt(end) == '(') {
				end = CodeHandler.findClosingBracketIndex(code, end, '(');
			}
			code = code.substring(0, start) + code.substring(end + 1, code.length());
			start = code.indexOf(keyWord);
		}
		return code;
	}

	private String replaceHelper(String code, String statementName) {
		statementName = statementName.trim();
		code = code.replaceAll(Pattern.quote(STATEMENT_PH), statementName);
		code = removeKeyword(code, "original");
		code = code.replaceAll("self\\.", "this.");
		code = code.substring(0, code.indexOf(CodeHandler.PRECHECKS_START))
				+ code.substring(code.indexOf(CodeHandler.PRECHECKS_END) + CodeHandler.PRECHECKS_END.length());
		return code;
	}

	public boolean testStatement(final AbstractStatement statement, final JavaVariables vars,
			final GlobalConditions conds, final CbCFormula formula, boolean isReturnStatement)
			throws TestAndAssertionGeneratorException, TestStatementException, ReferenceException,
			UnexpectedTokenException, DiagnosticsException, SettingsException, MalformedURLException,
			ClassNotFoundException {
		return testStatement(statement, vars, conds, formula, isReturnStatement, null);
	}

	public static EObject getPathLeaf(EObject container) {
		while (container.eContents().size() > 0) {
			container = container.eContents().get(0);
		}
		return container;
	}
}
