package de.tu_bs.cs.isf.cbc.util;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;

import de.tu_bs.cs.isf.cbc.cbcclass.CbcclassFactory;
import de.tu_bs.cs.isf.cbc.cbcclass.Field;
import de.tu_bs.cs.isf.cbc.cbcclass.Method;
import de.tu_bs.cs.isf.cbc.cbcclass.ModelClass;
import de.tu_bs.cs.isf.cbc.cbcclass.Parameter;
import de.tu_bs.cs.isf.cbc.cbcclass.Visibility;
import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionTechnique;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.cbcmodel.Variant;
import de.tu_bs.cs.isf.cbc.statistics.FileNameManager;
import de.tu_bs.cs.isf.cbc.util.consts.MetaNames;
import de.uka.ilkd.key.proof.Goal;
import de.uka.ilkd.key.proof.Proof;

public class ProveWithKey {
	public static final String SRC_FOLDER = ""; // Fix für Pfadfehler bei Repetition, Selection und Methodenaufrufen.
	// War vorher "/src_gen"

	public static final String REGEX_ORIGINAL_PRE = "\\\\original_pre";
	public static final String REGEX_ORIGINAL_POST = "\\\\original_post";
	public static final String REGEX_ORIGINAL = "original";
	public static final String REGEX_RESULT = "\\\\result";

	public static final Pattern REGEX_THIS_KEYWORD = Pattern.compile("(?<![a-zA-Z0-9])(this)(?![a-zA-Z0-9])");
	public static final Pattern REGEX_RESULT_KEYWORD = Pattern.compile("(\\\\result)");
	public static final Pattern REGEX_OLD_KEYWORD = Pattern.compile("\\\\old\\((.*?)[\\)]+");
	public static final String OLD_VARS_SUFFIX = "_oldVal";
	public static HashMap<String, Integer> allStatistics = new HashMap<String, Integer>();

	AbstractStatement statement;
	private Diagram diagramToProve;
	private JavaVariables vars;
	private GlobalConditions conds;
	private Renaming renaming;
	private IProgressMonitor monitor;
	private String uri;
	private CbCFormula formula;
	private static IFileUtil fileHandler;
	private String sourceFolder;
	private boolean isVariationalProject;
	private static String configName;
	private static List<String> configList;
	private String problem = null;
	private String helper;
	private String subProofName = "";
	private static String proofType;
	private static List<Predicate> predicates = null;
	private static String predicatesForKeY = "";
	private static int configNum;
	private static boolean noResolve = false;

	public ProveWithKey(AbstractStatement statement, Diagram diagram, IProgressMonitor monitor, IFileUtil fileHandler,
			String[] config, int configNum, String proofType) {
		this(statement, diagram, monitor, fileHandler, ProveWithKey.SRC_FOLDER, Arrays.asList(config), configNum,
				proofType);
	}

	public ProveWithKey(AbstractStatement statement, Diagram diagram, IProgressMonitor monitor, IFileUtil fileHandler,
			List<String> config, int configNum, String proofType) {
		this(statement, diagram, monitor, fileHandler, ProveWithKey.SRC_FOLDER, config, configNum, proofType);
	}

	public ProveWithKey(AbstractStatement statement, Diagram diagram, IProgressMonitor monitor, IFileUtil fileHandler,
			String srcFolder, List<String> config, int configNum, String proofType) {
		if (proofType.equals(KeYInteraction.ABSTRACT_T_RESOLVED_PROOF)) {
			this.proofType = KeYInteraction.ABSTRACT_PROOF_FULL;
			this.noResolve = true;
		} else {
			this.proofType = proofType;
			this.noResolve = false;
		}

		if (srcFolder == null)
			srcFolder = ProveWithKey.SRC_FOLDER;
		this.statement = statement;
		this.monitor = monitor;
		this.fileHandler = fileHandler;
		this.diagramToProve = diagram;
		DiagramPartsExtractor extractor = new DiagramPartsExtractor(diagramToProve);
		vars = extractor.getVars();
		conds = extractor.getConds();
		renaming = extractor.getRenaming();
		formula = extractor.getFormula();
		this.uri = diagramToProve.eResource().getURI().toPlatformString(true);

		if (uri.contains(MetaNames.FOLDER_NAME)) {
			String className = uri.substring(0, uri.lastIndexOf("/"));
			className = className.substring(className.lastIndexOf("/") + 1, className.length());
			this.sourceFolder = MetaNames.FOLDER_NAME; /* + "/" + className; */
		} else {
			this.sourceFolder = srcFolder;
		}
		this.isVariationalProject = false;
		this.configList = new ArrayList<String>();
		if (config != null) {
			this.configList = config;
		}
		this.configName = "";
		this.configNum = configNum;

		if (config != null) {
			this.configName += "/";
			for (String s : config)
				this.configName += s;
		}
		IProject project = FileUtil.getProjectFromFileInProject(URI.createURI(uri));
		try {
			if (project.getNature("de.ovgu.featureide.core.featureProjectNature") != null) {
				this.isVariationalProject = true;
			} else {
				this.isVariationalProject = false;
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
		readPredicates(config, formula, fileHandler.getLocationString(uri));
	}

	public boolean proveStatementWithKey(boolean returnStatement, boolean inlining, String callingClass,
			boolean forceProving) {
		File location = createProveStatementWithKey(null, null, null, true, "", "", returnStatement, callingClass);
		if (location == null)
			return false;
		// TODO: proving will only skip if already true
		if (!forceProving) {
			String problemHash = Hashing.sha256().hashString(problem, StandardCharsets.UTF_8).toString();
			String filePath = location.getAbsolutePath().toString();
			String proveFolderLocation = filePath.substring(0, filePath.lastIndexOf(File.separator));
			FileNameManager fileManager = new FileNameManager();
			if (fileManager.isKeYFileWithHashProven(problemHash, proveFolderLocation) && !isVariationalProject) {
				Console.println("  Already true... skip\n");
				return true;
			}
		}

		Console.println("  Verify Pre -> {Statement} Post");
		return proveWithKey(location, inlining);
	}

	public boolean proveStatementWithKey(List<CbCFormula> refinementsOriginal, List<CbCFormula> refinements,
			List<JavaVariables> refinementsVars, boolean returnStatement, boolean inlining, String callingMethod,
			String varM, String callingClass, boolean forceProving) {
		File location = createProveStatementWithKey(refinementsOriginal, refinements, refinementsVars, true,
				callingMethod, varM, returnStatement, callingClass);
		if (location == null)
			return false;
		if (!forceProving) {
			String problemHash = Hashing.sha256().hashString(problem, StandardCharsets.UTF_8).toString();
			String filePath = location.getAbsolutePath().toString();
			String proveFolderLocation = filePath.substring(0, filePath.lastIndexOf(File.separator));
			FileNameManager fileManager = new FileNameManager();
			if (fileManager.isKeYFileWithHashProven(problemHash, proveFolderLocation) && !isVariationalProject) {
				Console.println("  Already true... skip\n");
				return true;
			}
		}
		Console.println("  Verify Pre -> {Statement} Post");
		return proveWithKey(location, inlining);
	}

	private Collection<Parameter> copyMethodParams(String uri, String callingMethod, String callingClass) {
		var project = FileUtil.getProjectLocationS(uri);
		ModelClass clazz = FileHandler.instance.getClassOf(this.diagramToProve);
		Method method = clazz.getMethods().stream().filter(m -> m.getName().equals(callingMethod)).findFirst().get();
		return EcoreUtil.copyAll(method.getParameters());
	}

	public File createProveStatementWithKey(List<CbCFormula> refinementsOriginal, List<CbCFormula> refinements,
			List<JavaVariables> refinementsVars, boolean override, String callingMethod, String varM,
			boolean returnStatement, String callingClass) {
		String callingFeature = FeatureUtil.getInstance().getCallingFeature(uri);
		KeYFileContent content = new KeYFileContent(fileHandler.getProjectLocation(uri));
		JavaVariables varsFromJavaClass = readFieldsFromClass(callingClass);
		addExistingVarsTo(varsFromJavaClass);
		content.setSrcFolder(sourceFolder);
		content.readVariables(varsFromJavaClass);
		JavaVariable returnVariable = content.readVariables(vars);

		if (!proofType.equals(KeYInteraction.ABSTRACT_PROOF_COMPLETE)) {
			content.readGlobalConditions(conds);
			readPrePostModVars(varM.length() == 0 ? refinements : refinementsOriginal, refinementsVars, returnVariable,
					content, callingClass);

			if (returnStatement) {
				content.setStatement(";");
				content.handleReturn(statement, returnVariable, formula);
			} else {
				content.setStatement(statement.getName());
			}
			content.rename(renaming);
			replaceOriginalInStatement(refinementsOriginal, refinements, refinementsVars, callingMethod, content, varM,
					callingClass, callingFeature);
			content.addSelf(formula);
			content.setAbstractProof(proofType.equals(KeYInteraction.ABSTRACT_PROOF_BEGIN));

			problem = content.getKeYStatementContent();
			problem = problem.replaceAll("static", "");
			problem = problem.replaceAll("return", ""); // TODO replace with correct handling of return
		}
		helper = collectPredicates();
		List<String> originalPrePost = proofType.equals(KeYInteraction.ABSTRACT_PROOF_COMPLETE)
				? calculateOriginalPredicates(
						(refinements == null || refinements.size() == 0) ? refinementsOriginal : refinements,
						varsFromJavaClass, callingClass, returnVariable)
				: null;

		String location = fileHandler.getLocationString(uri)
				+ (proofType.equals(KeYInteraction.ABSTRACT_PROOF_BEGIN) ? "" : configName).replaceAll("\\\\", "/");
		File keyFile = fileHandler.writeFile(proofType, problem, helper, location, override, statement, subProofName,
				configNum != 0, configName, originalPrePost);
		return keyFile;
	}

	private JavaVariables readFieldsFromClass(String className) {
		JavaVariables vars = CbcmodelFactory.eINSTANCE.createJavaVariables();
		fileHandler = new FileUtil(uri);
		File file = fileHandler.getClassFile(className);
		if (file == null) {
			return vars;
		}
		List<String> lines = fileHandler.readFileInList(file.getAbsolutePath());
		if (lines.get(0).contains(" extends ")) {
			String inheritedClassName = lines.get(0).trim();
			inheritedClassName = inheritedClassName
					.substring(inheritedClassName.indexOf(" extends ") + 9, inheritedClassName.indexOf("{")).trim();
			vars = readFieldsFromClass(inheritedClassName);
		}
		int i = 2;
		while (lines.get(i).contains(";")) {
			String field = lines.get(i++).replace(";", "");
			Field f = CbcclassFactory.eINSTANCE.createField();
			String split[] = field.replace("/*@spec_public@*/ ", "").trim().split(" ");
			int pointer = 1;
			switch (split[0].toLowerCase()) {
				case "private" :
					f.setVisibility(Visibility.PRIVATE);
					break;
				case "public" :
					f.setVisibility(Visibility.PUBLIC);
					break;
				case "protected" :
					f.setVisibility(Visibility.PROTECTED);
					break;
				case "package" :
					f.setVisibility(Visibility.PACKAGE);
					break;
				default :
					f.setVisibility(Visibility.PUBLIC);
					pointer = 0;
					break;
			}
			if (Arrays.stream(split).anyMatch("static"::equalsIgnoreCase)) {
				f.setIsStatic(true);
				pointer++;
			} else
				f.setIsStatic(false);
			if (Arrays.stream(split).anyMatch("final"::equalsIgnoreCase)) {
				f.setIsFinal(true);
				pointer++;
			} else
				f.setIsFinal(false);

			f.setType(split[pointer++]);
			f.setName(split[pointer]);
			if (!f.getType().trim().equals("")) {
				vars.getFields().add(f);
			}
		}
		return vars;
	}

	private List<Condition> readInvariantsFromClass(String className) {
		List<Condition> invariants = new ArrayList<>();
		fileHandler = new FileUtil(uri);
		File file = fileHandler.getClassFile(className);
		if (file == null) {
			return invariants;
		}
		List<String> lines = fileHandler.readFileInList(file.getAbsolutePath());
		for (String line : lines) {
			if (line.contains("/*@ invariant")) {
				String conditionName = line.trim().replace("/*@ invariant ", "").replace("; @*/", "");
				Condition c = CbcmodelFactory.eINSTANCE.createCondition();
				c.setName(conditionName);
				invariants.add(c);
			}
		}
		return invariants;
	}

	public void readPrePostModVars(List<CbCFormula> refinements, List<JavaVariables> refinementsVars,
			JavaVariable returnVariable, KeYFileContent content, String callingClass) {
		CbCFormula formula = getCbCFormula(statement);
		String preFormula = Parser.getConditionFromCondition(formula.getStatement().getPreCondition().getName());
		String postFormula = Parser.getConditionFromCondition(formula.getStatement().getPostCondition().getName());
		String pre = Parser.getConditionFromCondition(statement.getPreCondition().getName());
		String post = Parser.getConditionFromCondition(statement.getPostCondition().getName());
		List<String> modifiables = statement.getPostCondition().getModifiables();

		String preInherited = applyLiskovInheritance(pre, preFormula, "pre");
		String postInherited = applyLiskovInheritance(post, postFormula, "post");
		String preInvariant = "";
		String postInvariant = "";

		List<Condition> invariants = readInvariantsFromClass(callingClass);
		for (Condition c : invariants) {
			preInvariant += " & " + c.getName();
			postInvariant += " & " + c.getName();
		}

		if (refinements != null && pre.equals(preFormula)
				|| formula.getCompositionTechnique().equals(CompositionTechnique.EXPLICIT_CONTRACTING)) { // TODO
			// composition
			// only for
			// pre post of
			// formula.
			// Extend to
			// every case
			pre = composeContractForCbCDiagram(formula.getCompositionTechnique(), refinements, pre,
					Parser.KEYWORD_JML_PRE, returnVariable);
		}
		if (refinements != null && post.equals(postFormula)
				|| formula.getCompositionTechnique().equals(CompositionTechnique.EXPLICIT_CONTRACTING)) {
			modifiables = composeModifiables(refinements, refinementsVars, modifiables,
					formula.getCompositionTechnique(), true);
			post = composeContractForCbCDiagram(formula.getCompositionTechnique(), refinements, post,
					Parser.KEYWORD_JML_POST, returnVariable);
		}

		pre = "(" + pre + preInherited + ")";
		post = "(" + post + postInherited + ")";
		if (pre.equals(preFormula))
			pre += preInvariant;
		if (pre.equals(preFormula))
			post += postInvariant;

		content.setPreFromCondition(resolveResultKeyword(pre, returnVariable));
		content.setPostFromCondition(resolveResultKeyword(post, returnVariable));

		List<String> unmodifiedVariables = Parser.getUnmodifiedVars(modifiables, vars);
		unmodifiedVariables = unmodifiedVariables.stream().distinct().collect(Collectors.toList());
		content.addUnmodifiableVars(unmodifiedVariables);

		if (pre == null || pre.length() == 0) {
			content.setPreFromCondition("true");
		}
		if (post == null || post.length() == 0) {
			content.setPostFromCondition("true");
		}
	}

	private String applyLiskovInheritance(String cond, String condFormula, String type) {
		if (formula.getMethodObj() != null && formula.getMethodObj().getParentClass().getInheritsFrom() != null) {
			if (cond.equals(condFormula)) {
				for (Method m : formula.getMethodObj().getParentClass().getInheritsFrom().getMethods()) {
					if (m.getName().equals(formula.getMethodObj().getName())) {
						switch (type) {
							case "pre" :
								return " | (" + Parser.getConditionFromCondition(
										m.getCbcStartTriple().getStatement().getPreCondition().getName()) + ")";
							case "post" :
								return " & ("
										+ Parser.getConditionFromCondition(
												m.getCbcStartTriple().getStatement().getPostCondition().getName())
										+ ")";
						}
					}
				}
			}
		}
		return "";
	}

	public void replaceOriginalInStatement(List<CbCFormula> refinementsOriginal, List<CbCFormula> refinements,
			List<JavaVariables> refinementsVars, String callingMethod, KeYFileContent content, String varM,
			String callingClass, String callingFeature) {
		var jsl = content.getStatement();
		if (refinements != null && refinements.size() > 0 && content.getStatement().contains("(")) {
			generateComposedClass(refinementsOriginal, refinements, refinementsVars, callingMethod, varM, callingClass,
					callingFeature);
			if (content.getStatement().contains("this.original")) {
				content.setStatement(
						content.getStatement().replaceFirst("this.original", "self.original_" + callingMethod));
			} else
				content.setStatement(
						content.getStatement().replaceFirst("original", "self" + ".original_" + callingMethod));

			if (!varM.equals("")) {
				if (content.getStatement().contains(callingClass + "." + varM)) {
					content.setStatement(
							content.getStatement().replaceFirst(callingClass + "." + varM.split("\\.")[1].toLowerCase(),
									"self" + "." + varM.split("\\.")[1].toLowerCase()));
				} else if (!content.getStatement().contains("." + varM.split("\\.")[1].toLowerCase())) {
					content.setStatement(content.getStatement().replaceFirst(varM.split("\\.")[1].toLowerCase(),
							"self" + "." + varM.split("\\.")[1].toLowerCase()));
				}
			}
		}
	}

	List<String> composeModifiables(List<CbCFormula> refinements, List<JavaVariables> refinementsVars,
			List<String> modifiables, CompositionTechnique compTechnique, boolean includeFormulaModifiable) {
		if (refinements != null && refinements.size() > 0 && refinementsVars != null && refinementsVars.size() > 0) {
			for (int i = 0; i < refinements.size(); i++) {
				if (!includeFormulaModifiable) {
					compTechnique = refinements.get(i).getCompositionTechnique();
					String modifiableOriginal = Parser.getModifieableVarsFromConditionExceptLocals(
							refinements.get(i).getStatement().getPostCondition(), null, refinementsVars.get(i), null);
					List<String> modifiableList = new ArrayList<String>();
					if (!modifiableOriginal.equals("")) {
						modifiableList = Parser.getModifiedVarsFromCondition("modifiable(" + modifiableOriginal + ");");
					}
					modifiables = applyCompositionTechniqueOnModifiables(modifiableList, modifiableOriginal,
							compTechnique);
					includeFormulaModifiable = true;
				} else {
					String modifiableOriginal = Parser.getModifieableVarsFromConditionExceptLocals(
							refinements.get(i).getStatement().getPostCondition(), null, refinementsVars.get(i), null);
					modifiables = applyCompositionTechniqueOnModifiables(modifiables, modifiableOriginal,
							compTechnique);
				}
				if (compTechnique == CompositionTechnique.CONTRACT_OVERRIDING
						|| (compTechnique == CompositionTechnique.EXPLICIT_CONTRACTING
								&& !modifiables.contains(REGEX_ORIGINAL))) {
					return modifiables;
				}
			}
		}
		return modifiables;
	}

	private static List<String> applyCompositionTechniqueOnModifiables(List<String> modifiables,
			String modifiableOriginal, CompositionTechnique compTechnique) {
		switch (compTechnique) {
			case CONTRACT_OVERRIDING :
				break;
			case EXPLICIT_CONTRACTING :
				modifiables.remove(REGEX_ORIGINAL);
				for (String var : modifiableOriginal.split(",")) {
					if (var.equals("\\nothing") && !modifiables.isEmpty()) {
						break;
					}
					if (var != "" && !modifiables.contains(var)) {
						modifiables.add(var);
					}
				}
				break;
			case CONJUNCTIVE_CONTRACTING :
				if (!proofType.equals(KeYInteraction.ABSTRACT_PROOF_BEGIN))
					if (modifiableOriginal.contains("\\nothing") && !modifiables.isEmpty()) {
						break;
					}
				modifiables.addAll(Lists.newArrayList(modifiableOriginal.split(",")));
				break;
		}
		return modifiables;
	}

	private static String applyCompositionTechnique(String keyword, String condition, String conditionOriginal,
			CompositionTechnique compositionTechnique, boolean keyContent) {
		String composedCondition = condition;
		switch (compositionTechnique) {
			case CONTRACT_OVERRIDING :
				composedCondition = condition;
				break;
			case CONJUNCTIVE_CONTRACTING :
				if (proofType.equals(KeYInteraction.ABSTRACT_PROOF_BEGIN) && keyContent) {
					composedCondition = "(" + composedCondition + ") & (original)";
				} else {
					if (conditionOriginal != "") {
						composedCondition = "(" + condition + ") & (" + conditionOriginal + ")";
					} else {
						composedCondition = condition;
					}
				}
				break;
			case EXPLICIT_CONTRACTING :
				if (proofType.equals(KeYInteraction.ABSTRACT_PROOF_BEGIN) && keyContent) {
					composedCondition = composedCondition.replaceAll(REGEX_ORIGINAL_PRE, "original_pre")
							.replaceAll(REGEX_ORIGINAL_POST, "original_post");
					if (keyword.equals("requires")) {
						return composedCondition.replace("original ", "original_pre");
					} else {
						return composedCondition.replace("original ", "original_post");
					}

				}
				Pattern pattern = null;
				if (keyword.equals("requires")) {
					pattern = Pattern.compile(REGEX_ORIGINAL_PRE);
				} else {
					pattern = Pattern.compile(REGEX_ORIGINAL_POST);
				}
				Matcher matcher = pattern.matcher(condition);
				if (!matcher.find()) {
					pattern = Pattern.compile(REGEX_ORIGINAL);
					matcher = pattern.matcher(condition);
				}
				composedCondition = matcher.replaceAll(Matcher.quoteReplacement(conditionOriginal));
		}
		return composedCondition;
	}

	private String composeContractForCbCDiagram(CompositionTechnique compositionTechnique, List<CbCFormula> refinements,
			String condition, String keyword, JavaVariable returnVariable) {
		String composedCondition = condition;
		CompositionTechnique compTechnique = compositionTechnique;
		for (int i = 0; i < refinements.size(); i++) {
			boolean originalPre = false;
			boolean originalPost = false;
			if (composedCondition.contains("\\original_pre")) {
				originalPre = true;
			} else if (composedCondition.contains("\\original_post")) {
				originalPost = true;
			}

			if (i != 0) {
				refinements.get(i - 1).getCompositionTechnique();
			}
			String conditionOriginal = "";

			if ((keyword.equals("requires") || originalPre) && !originalPost) {
				conditionOriginal = Parser
						.getConditionFromCondition(refinements.get(i).getStatement().getPreCondition().getName())
						.replace("\n", "").replace("\r", "");
				composedCondition = applyCompositionTechnique("requires", composedCondition, conditionOriginal,
						compTechnique, true);
			} else {
				conditionOriginal = Parser
						.getConditionFromCondition(refinements.get(i).getStatement().getPostCondition().getName())
						.replace("\n", "").replace("\r", "");
				composedCondition = applyCompositionTechnique("ensures", composedCondition, conditionOriginal,
						compTechnique, true);
			}
			if (compTechnique == CompositionTechnique.CONTRACT_OVERRIDING
					|| (compTechnique == CompositionTechnique.EXPLICIT_CONTRACTING
							&& !composedCondition.contains(REGEX_ORIGINAL)
							&& !composedCondition.contains(REGEX_ORIGINAL_POST)
							&& !composedCondition.contains(REGEX_ORIGINAL_PRE))) {
				return resolveResultKeyword(composedCondition, returnVariable);
			}
		}
		return resolveResultKeyword(composedCondition, returnVariable);
	}

	public void generateComposedClass(List<CbCFormula> refinementsOriginal, List<CbCFormula> refinements,
			List<JavaVariables> refinementsVars, String callingMethod, String varM, String callingClass,
			String callingFeature) {
		boolean originalNecessary = refinementsOriginal != null && refinementsOriginal.size() > 0;
		int round = 0;
		String className = varM.equals("") ? callingClass : varM.split("\\.")[0];
		String methodName = varM.equals("") ? ("original_" + callingMethod) : varM.split("\\.")[1].toLowerCase();
		while (originalNecessary || round == 0) {
			File file = fileHandler.getClassFile(className);
			String methodStub = Parser.getMethodStubFromFile(className, methodName, fileHandler).replaceAll("\n", "");

			String methodPreCondition = composeContractForCalledOriginal(refinements, Parser.KEYWORD_JML_PRE);
			String methodPostCondition = composeContractForCalledOriginal(refinements, Parser.KEYWORD_JML_POST);
			// adding of class invs is necessary as .key requires invs to be fulfilled, but
			// called method(stub) does not
			if (refinements != null && refinements.size() > 0) {
				for (Condition c : readInvariantsFromClass(refinements.get(0).getClassName())) {
					methodPostCondition = methodPostCondition + " && " + Parser.rewriteConditionToJML(c.getName());
				}
			}

			List<String> assignables = composeModifiables(refinements, refinementsVars, new ArrayList<String>(), null,
					false);
			String assignableString = "";
			if (!assignables.isEmpty()) {
				assignableString = "@ assignable " + String.join(",", assignables) + ";";
			}
			if (vars != null) {
				for (JavaVariable actVar : vars.getVariables()) {
					if ((actVar.getKind().getName() != "PARAM")) {
						String splitName[] = actVar.getName().split(" ");
						assignableString = assignableString.replaceAll("," + splitName[splitName.length - 1], "");
						assignableString = assignableString.replaceAll(splitName[splitName.length - 1] + ";", ";");
						assignableString = assignableString.replaceAll(splitName[splitName.length - 1] + ",", "");
					}
				}
			}
			if (assignableString.trim().equals("@ assignable ;")) {
				assignableString = "@ assignable \\nothing;";
			}
			assignableString = assignableString.replaceAll("\\)", "").replaceAll("\\(", "");

			List<String> lines = fileHandler.readFileInList(file.getAbsolutePath());
			String contentOriginal = "";

			String content = "";
			Collections.reverse(lines);
			for (int i = 0; i < lines.size(); i++) {
				String line = lines.get(i) + "";
				if (line.contains(" " + methodName + "(") && line.contains(") {")) {
					int index = lines.indexOf(line);
					while (!lines.get(index + 1).contains("/*@")) {
						lines.remove(index + 1);
					}
					content = "    @ public normal_behavior\n    @ requires " + methodPreCondition + ";\n    @ ensures "
							+ methodPostCondition + ";\n    " + assignableString + "\n" + "    @*/\n"
							+ methodStub.substring(0, methodStub.indexOf("{") + 1) + "\n" + content;
					contentOriginal = line + "\n" + contentOriginal;
				} else {
					content = line + "\n" + content;
					contentOriginal = line + "\n" + contentOriginal;
				}
			}
			fileHandler.generateComposedClass(uri, className, className, content, contentOriginal);
			round++;
			if (originalNecessary) {
				methodName = "original_" + refinementsOriginal.get(0).getMethodName();
				className = refinementsOriginal.get(0).getClassName();
				refinements = refinementsOriginal;
				if (round > 1)
					originalNecessary = false;
			}
		}
		return;
	}

	String composeContractForCalledOriginal(List<CbCFormula> refinements, String keyword) {
		String composedCondition = "";
		if (keyword.equals("requires")) {
			composedCondition = Parser.rewriteConditionToJML(applyPredicates(
					Parser.getConditionFromCondition(refinements.get(0).getStatement().getPreCondition().getName())
							.replace("\n", "").replace("\r", "")));
		} else if (keyword.equals("ensures")) {
			composedCondition = Parser.rewriteConditionToJML(applyPredicates(
					Parser.getConditionFromCondition(refinements.get(0).getStatement().getPostCondition().getName())
							.replace("\n", "").replace("\r", "")));
		}

		CompositionTechnique compTechnique = refinements.get(0).getCompositionTechnique();
		for (int i = 1; i < refinements.size(); i++) {
			if (i != 1) {
				compTechnique = refinements.get(i - 1).getCompositionTechnique();
			}
			String conditionOriginal = "";
			if (keyword.equals("requires")) {
				conditionOriginal = Parser.rewriteConditionToJML(applyPredicates(
						Parser.getConditionFromCondition(refinements.get(i).getStatement().getPreCondition().getName())
								.replace("\n", "").replace("\r", "")));
			} else if (keyword.equals("ensures")) {
				conditionOriginal = Parser.rewriteConditionToJML(applyPredicates(
						Parser.getConditionFromCondition(refinements.get(i).getStatement().getPostCondition().getName())
								.replace("\n", "").replace("\r", "")));
			}
			composedCondition = applyCompositionTechnique(keyword, composedCondition, conditionOriginal, compTechnique,
					false);
			if (compTechnique == CompositionTechnique.CONTRACT_OVERRIDING
					|| (compTechnique == CompositionTechnique.EXPLICIT_CONTRACTING
							&& !composedCondition.contains(REGEX_ORIGINAL))) {
				return composedCondition;
			}
		}
		return composedCondition;
	}

	private static String resolveResultKeyword(String condition, JavaVariable returnVariable) {
		if (returnVariable != null) {
			String variableName = returnVariable.getName().substring(returnVariable.getName().indexOf(" "));
			return Parser.rewriteJMLConditionToKeY(condition.replaceAll(REGEX_RESULT, variableName));
		}
		return Parser.rewriteJMLConditionToKeY(condition);
	}

	private static CbCFormula getCbCFormula(AbstractStatement statement) {
		EObject parent = getParentOfStatement(statement);
		if (parent != null) {
			if (parent instanceof CbCFormula) {
				return (CbCFormula) parent;
			} else {
				return getCbCFormula((AbstractStatement) parent);
			}
		}
		return null;
	}

	private static EObject getParentOfStatement(AbstractStatement statement) {
		EObject parent = null;
		if (statement.getParent() != null) {
			parent = statement.getParent().eContainer();
		} else if (statement.eContainer() != null) {
			parent = statement.eContainer();
		}
		return parent;
	}

	public boolean proveWithKey(File location, boolean inlining) {
		try {
			return proveWithKey(location, monitor, inlining, formula, statement, problem, uri);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean proveWithKey(File location, IProgressMonitor monitor, boolean inlining, CbCFormula formula,
			AbstractStatement statement, String problem, String uri) throws Exception {
		Proof proof = null;
		proof = KeYInteraction.startKeyProof(noResolve ? KeYInteraction.ABSTRACT_T_RESOLVED_PROOF : proofType, location,
				monitor, inlining, formula, statement, problem, uri, predicatesForKeY);
		if (proof != null) {
			boolean closed = proof.openGoals().isEmpty();
			if (!closed) {
				Console.println("  Proof could not be closed.");
				CounterExampleGenerator generator = new CounterExampleGenerator();
				if (Settings.canRead()) {
					Settings settings = Settings.get();
					if (settings.getCounterExamples()) {
						generator.calculateExample(proof);
					}
				}
			} else {
				Console.println("  Proof is closed: " + closed + "\n");
				return closed;
			}
		}
		return false;
	}

	public boolean proveCImpliesCWithKey(List<CbCFormula> refinements, Condition preCondition,
			Condition postCondition) {
		subProofName = "precondition";
		File location = createProveCImpliesCWithKey(refinements, preCondition.getName(), postCondition.getName(), true);
		Console.println("  Verify Pre -> Invariant");
		return proveWithKey(location, false);
	}

	private String getMethodName(String uri) {
		uri = uri.replaceAll("\\\\", "/");
		return uri.substring(uri.lastIndexOf("/") + 1, uri.length()).split("\\.")[0];
	}

	private void addExistingVarsTo(JavaVariables targetVars) {
		JavaVariables copyVars = EcoreUtil.copy(vars);
		var callingClass = FeatureUtil.getInstance().getCallingClass(uri);
		addNewVars(targetVars, copyVars);
		if (callingClass.isEmpty())
			return;
		targetVars.getParams().addAll(copyMethodParams(uri, getMethodName(uri), callingClass));
	}

	private void addNewVars(final JavaVariables target, final JavaVariables source) {
		boolean found = false;
		for (int i = 0; i < source.getVariables().size(); i++) {
			for (int j = 0; j < target.getVariables().size(); j++) {
				if (target.getVariables().get(j).getName().equals(source.getVariables().get(i).getName())) {
					found = true;
					break;
				}
			}
			if (!found) {
				target.getVariables().add(source.getVariables().get(i));
				i--;
			}
			found = false;
		}
		for (int i = 0; i < source.getFields().size(); i++) {
			for (int j = 0; j < target.getFields().size(); j++) {
				if (target.getFields().get(j).getName().equals(source.getFields().get(i).getName())) {
					found = true;
					break;
				}
			}
			if (!found) {
				target.getFields().add(source.getFields().get(i));
				i--;
			}
			found = false;
		}
	}

	public File createProveCImpliesCWithKey(List<CbCFormula> refinements, String preCondition, String postCondition,
			boolean override) {
		KeYFileContent content = new KeYFileContent(fileHandler.getProjectLocation(uri));
		content.setLocation(fileHandler.getProjectLocation(uri));
		content.setSrcFolder(sourceFolder);
		var callingClass = FeatureUtil.getInstance().getCallingClass(uri);
		JavaVariables varsFromJavaClass = readFieldsFromClass(callingClass);
		addExistingVarsTo(varsFromJavaClass);
		JavaVariable returnVariable = content.readVariables(vars);
		content.readVariables(varsFromJavaClass);
		if (!proofType.equals(KeYInteraction.ABSTRACT_PROOF_COMPLETE)) {
			content.readGlobalConditions(conds);

			// content.readInvariants(readInvariantsFromClass(uri.split("/")[4]));
			content.setPreFromCondition(preCondition + applyLiskovInheritance(preCondition,
					Parser.getConditionFromCondition(formula.getStatement().getPreCondition().getName()), "pre"));
			content.setPostFromCondition(postCondition + applyLiskovInheritance(postCondition,
					Parser.getConditionFromCondition(formula.getStatement().getPostCondition().getName()), "post"));
			content.rename(renaming);
			content.addSelf(formula);

			problem = content.getKeYCImpliesCContent();
		}

		helper = collectPredicates();
		List<String> originalPrePost = proofType.equals(KeYInteraction.ABSTRACT_PROOF_COMPLETE)
				? calculateOriginalPredicates(refinements, varsFromJavaClass, callingClass, returnVariable)
				: null;

		String location = fileHandler.getLocationString(uri)
				+ (proofType.equals(KeYInteraction.ABSTRACT_PROOF_BEGIN) ? "" : configName);
		File keyFile = fileHandler.writeFile(proofType, problem, helper, location, override, statement, subProofName,
				configNum != 0, configName, originalPrePost);
		return keyFile;
	}

	public boolean provePostRepetitionWithKey(List<CbCFormula> refinements, Condition invariant, Condition guard,
			Condition postCondition) {
		subProofName = "postcondition";
		String pre = invariant.getName() + " & !(" + guard.getName() + ")";
		File location = createProveCImpliesCWithKey(refinements, pre, postCondition.getName(), true);
		Console.println("  Verify (Invariant & !Guard) -> Post");
		return proveWithKey(location, false);
	}

	public boolean provePreSelWithKey(List<CbCFormula> refinements, EList<Condition> guards, Condition preCondition) {
		String guardString = "";
		if (guards != null && guards.get(0) != null) {
			guardString = "((" + guards.get(0).getName() + ")";
			for (int i = 1; i < guards.size(); i++) {
				guardString += " | (" + guards.get(i).getName() + ")";
			}
			guardString += ")";
		} else {
			guardString = "true";
		}
		File location = createProveCImpliesCWithKey(refinements, preCondition.getName(), guardString, true);
		Console.println("  Verify Pre -> GvGvG...");
		return proveWithKey(location, false);
	}

	public boolean proveVariantWithKey(String code, Condition invariant, Condition guard, Variant variant) {
		subProofName = "variant";
		File location = createProveVariantWithKey(code, invariant, guard, variant, true);
		if (location == null)
			return false;
		Console.println("Verify Pre -> {WhileStatement} (variant<variant0 & variant >= 0)");
		return proveWithKey(location, false);
	}

	public File createProveVariantWithKey(String code, Condition invariant, Condition guard, Variant variant,
			boolean override) {
		if (!proofType.equals(KeYInteraction.ABSTRACT_PROOF_COMPLETE)) {
			KeYFileContent content = new KeYFileContent(fileHandler.getProjectLocation(uri));
			content.setSrcFolder(sourceFolder);
			var callingClass = FeatureUtil.getInstance().getCallingClass(uri);
			JavaVariables varsFromJavaClass = readFieldsFromClass(callingClass);
			addExistingVarsTo(varsFromJavaClass);
			content.readVariables(varsFromJavaClass);
			content.addVariable("int variant");
			content.readGlobalConditions(conds);
			content.setPreFromCondition(invariant.getName() + " & " + guard.getName());
			content.setVariantPost(variant.getName());
			content.setStatement(code);
			content.rename(renaming);
			content.addSelf(formula);
			content.setAbstractProof(proofType.equals(KeYInteraction.ABSTRACT_PROOF_BEGIN));

			problem = content.getKeYStatementContent();
		}
		helper = collectPredicates();

		String location = fileHandler.getLocationString(uri)
				+ (proofType.equals(KeYInteraction.ABSTRACT_PROOF_BEGIN) ? "" : configName);
		File keyFile = fileHandler.writeFile(proofType, problem, helper, location, override, statement, subProofName,
				configNum != 0, configName, null);
		return keyFile;
	}

	public String proveUseWeakestPreWithKey() {
		File location = createProveUseWeakestPreWithKey(true);
		if (location == null)
			return "";
		Console.println("Verify Pre -> {Statement} Post");
		return createWPWithKey(location);
	}

	private File createProveUseWeakestPreWithKey(boolean override) {
		if (!proofType.equals(KeYInteraction.ABSTRACT_PROOF_COMPLETE)) {
			KeYFileContent content = new KeYFileContent(fileHandler.getProjectLocation(uri));
			content.setSrcFolder(sourceFolder);
			content.readVariables(EcoreUtil.copy(vars));
			content.readGlobalConditions(conds);
			content.readInvariants(readInvariantsFromClass(uri.split("/")[4]));
			content.setStatement(statement.getName());
			content.setPostFromCondition("(" + statement.getPostCondition().getName()
					+ applyLiskovInheritance(statement.getPostCondition().getName(),
							Parser.getConditionFromCondition(formula.getStatement().getPreCondition().getName()), "pre")
					+ ")");
			content.addSelf(formula);
			content.rename(renaming);

			problem = content.getKeYStatementContent();
		}
		helper = collectPredicates();

		String location = fileHandler.getLocationString(uri)
				+ (proofType.equals(KeYInteraction.ABSTRACT_PROOF_BEGIN) ? "" : configName);
		File keyFile = fileHandler.writeFile(proofType, problem, helper, location, override, statement, subProofName,
				configNum != 0, configName, null);
		return keyFile;
	}

	public String createWPWithKey(File location) {
		Proof proof = KeYInteraction.startKeyProof(proofType, location, monitor, false, formula, statement, problem,
				uri, predicatesForKeY);
		if (proof != null) {
			String wp = "";
			Iterator<Goal> it = proof.openGoals().iterator();
			Goal goal = it.next();
			String[] goalString = goal.toString().split("==>");
			String antecedent = goalString[0].trim();
			String succedent = goalString[1].trim();
			wp += "(";
			if (antecedent.isEmpty()) {
				wp += succedent;
			} else {
				if (succedent.isEmpty()) {
					wp += "!(" + antecedent + ")";
				} else {
					wp += antecedent + " -> " + succedent;
				}
			}
			wp += ")";
			if (it.hasNext()) {
				goal = it.next();
				goalString = goal.toString().split("==>");
				antecedent = goalString[0].trim();
				succedent = goalString[1].trim();
				wp += " & (";
				if (antecedent.isEmpty()) {
					wp += succedent;
				} else {
					if (succedent.isEmpty()) {
						wp += "!" + antecedent;
					} else {
						wp += antecedent + " -> " + succedent;
					}
				}
				wp += ")";
			}
			Console.println("  Weakest precondition is: " + wp);
			return wp;
		}
		return "";
	}

	public void setConfigName(String configName) {
		this.configName = "/" + configName;
	}

	/*
	 * private static void readAdditionalRules(String path) { var lines =
	 * fileHandler.readFileInList(path); additionalRules =
	 * lines.stream().collect(Collectors.joining("\n")); }
	 */

	private static String getProjectName(CbCFormula formula, String filePath) {
		filePath = filePath.replaceAll("\\\\", "/");
		int nonSplProject = filePath.indexOf("src/");
		String projectName;
		if (nonSplProject != -1) {
			return filePath.substring(0, filePath.indexOf("src") - 1);
		} else {
			String[] splitUri = formula.eResource().getURI().toString().split("/features/");
			projectName = splitUri[0].split("/")[splitUri[0].split("/").length - 1];
			return projectName;
		}
	}

	private static void readPredicates(List<String> config, CbCFormula formula, String filePath) {
		predicates = new ArrayList<Predicate>();
		if (config == null)
			config = new ArrayList<String>();
		String projectName = getProjectName(formula, filePath);
		filePath = filePath.substring(0, filePath.indexOf(projectName)) + projectName + "/predicates.def";
		List<Predicate> readPredicates = fileHandler.readPredicates(filePath);
		for (Predicate p : readPredicates) {
			Predicate currentP = new Predicate(p.getSignature(true));
			for (int i = 0; i < p.definitions.size(); i++) {
				PredicateDefinition pDef = p.definitions.get(i);
				String expression = " " + pDef.presenceCondition + " ";
				List<String> allFeatures = VerifyFeatures.getAllFeatures(formula.eResource().getURI());
				for (String feature : config) {
					expression = expression.replaceAll(" " + feature + " ", " true ");
					allFeatures.remove(feature);
				}
				for (String feature : allFeatures)
					expression = expression.replaceAll(" !" + feature + " ", " true ");
				if (allFeatures.isEmpty() || expression.trim().equals("")
						|| evaluateFormula(expression.replaceAll("!true", "false").trim())) {
					currentP.definitions.add(pDef);
				}
			}
			if (currentP.definitions.size() != 0)
				predicates.add(currentP);
		}
	}

	private static boolean evaluateFormula(String ex) {
		ex = ex.trim();
		if (ex.equals("true"))
			return true;
		if (ex.equals("false"))
			return false;
		if (ex.equals("()"))
			return false;
		if (ex.equals("!false"))
			return true;
		if (ex.equals("!true"))
			return false;

		if (ex.startsWith("true")) {
			if (ex.charAt(5) == '&') {
				return evaluateFormula(ex.replaceFirst("true & ", ""));
			} else if (ex.charAt(5) == '|') {
				return true;
			} else if (ex.charAt(5) == '-' && ex.charAt(6) == '>') {
				return evaluateFormula(ex.replaceFirst("true -> ", ""));
			} else {
				return false;
			}
		} else if (ex.startsWith("false")) {
			if (ex.charAt(6) == '&') {
				return false;
			} else if (ex.charAt(6) == '|') {
				return evaluateFormula(ex.replaceFirst("false \\| ", ""));
			} else if (ex.charAt(6) == '-' && ex.charAt(7) == '>') {
				return true;
			} else {
				return false;
			}
		} else if (ex.startsWith("(")) {
			int i = ex.length() - 1;
			while (ex.charAt(i) != ')' && i > 1)
				i--;
			boolean innerEval = evaluateFormula(ex.substring(1, i));
			ex = ex.replace(ex.substring(0, i + 1), innerEval ? "true" : "false");
			return evaluateFormula(ex);
		} else if (ex.startsWith("!")) {
			return !evaluateFormula(ex.substring(1));
		} else {
			return false;
		}
	}

	private String collectPredicates() {
		String defString = "\\predicates {\n";
		String rulesString = "\\rules {\n";
		predicatesForKeY = "";
		for (Predicate p : predicates) {
			defString += p.printDefForKeY();
			rulesString += p.printReplaceForKeY();
			predicatesForKeY = predicatesForKeY.length() == 0 ? p.name : predicatesForKeY + "," + p.name;
		}
		if (proofType.equals(KeYInteraction.ABSTRACT_PROOF_BEGIN)) {
			defString += "\toriginal_pre;\n" + "\toriginal_post;\n";
			rulesString += "\toriginal_pre{\r\n" + "		\\find (original_pre)\r\n"
					+ "		\\replacewith (true)\r\n" + "		\\heuristics(simplify)\r\n" + "	};\r\n";
			rulesString += "\toriginal_post{\r\n" + "		\\find (original_post)\r\n"
					+ "		\\replacewith (true)\r\n" + "		\\heuristics(simplify)\r\n" + "	};\r\n";
			predicatesForKeY += predicatesForKeY.length() == 0
					? "original,original_pre,original_post"
					: ",original,original_pre,original_post";
		}
		String output = defString + "}\n\n" + rulesString + "}";
		output = KeYFunctionReplacer.getInstance().replaceIn(output);
		return output;
	}

	private List<String> calculateOriginalPredicates(List<CbCFormula> refinements, JavaVariables varsFromJavaClass,
			String callingClass, JavaVariable returnVar) {
		if (refinements == null || refinements.size() == 0)
			return null;
		String refinedPre = Parser
				.getConditionFromCondition(refinements.get(0).getStatement().getPreCondition().getName())
				.replace("\n", "").replace("\r", "");
		String refinedPost = Parser
				.getConditionFromCondition(refinements.get(0).getStatement().getPostCondition().getName())
				.replace("\n", "").replace("\r", "");;
		for (int i = 1; i < refinements.size(); i++) {
			refinedPre = applyCompositionTechnique("requires", refinedPre,
					Parser.getConditionFromCondition(refinements.get(i).getStatement().getPreCondition().getName())
							.replace("\n", "").replace("\r", ""),
					refinements.get(i - 1).getCompositionTechnique(), true);
			refinedPost = applyCompositionTechnique("ensures", refinedPost,
					Parser.getConditionFromCondition(refinements.get(i).getStatement().getPostCondition().getName())
							.replace("\n", "").replace("\r", ""),
					refinements.get(i - 1).getCompositionTechnique(), true);
		}

		refinedPre = replaceOld(refinedPre);
		refinedPost = replaceOld(refinedPost);
		refinedPre = resolveResultKeyword(refinedPre, returnVar);
		refinedPost = resolveResultKeyword(refinedPost, returnVar);
		for (Field var : varsFromJavaClass.getFields()) {
			Pattern pattern = Pattern.compile("([^a-zA-Z_\\.]|^)" + var.getName().trim() + "([^a-zA-Z_]|$|\\.)");
			Matcher matcher = pattern.matcher(refinedPre);
			while (matcher.find()) {
				if (matcher.start() <= 4) {
					refinedPre = new StringBuilder(refinedPre).insert(matcher.start(), "self.").toString();
					matcher = pattern.matcher(refinedPre);
				} else if (matcher.start() > 4 && !refinedPre.substring(matcher.start() - 4).startsWith("self.")) {
					refinedPre = new StringBuilder(refinedPre).insert(matcher.start() + 1, "self.").toString();
					matcher = pattern.matcher(refinedPre);
				}
			}
			matcher = pattern.matcher(refinedPost);
			while (matcher.find()) {
				if (matcher.start() <= 4) {
					refinedPost = new StringBuilder(refinedPost).insert(matcher.start(), "self.").toString();
					matcher = pattern.matcher(refinedPost);
				} else if (matcher.start() > 4 && !refinedPost.substring(matcher.start() - 4).startsWith("self.")) {
					refinedPost = new StringBuilder(refinedPost).insert(matcher.start() + 1, "self.").toString();
					matcher = pattern.matcher(refinedPost);
				}
			}

		}
		refinedPre = refinedPre.replaceAll("this.", "self.");
		refinedPost = refinedPost.replaceAll("this.", "self.");
		List<String> ret = new ArrayList<>();
		ret.add(refinedPre);
		ret.add(refinedPost);
		return ret;
	}

	private String replaceOld(String condition) {
		while (condition.contains("\\old(")) {
			boolean rememberPara = false;
			int start = condition.indexOf("\\old(");
			String toReplace = "\\old(";
			for (int i = start + 5; i < condition.length(); i++) {
				toReplace += condition.charAt(i);
				if ((condition.charAt(i) == ')' || condition.charAt(i) == '.') && !toReplace.endsWith("this.")) {
					if (condition.substring(i + 1).startsWith("size()"))
						rememberPara = true;
					break;
				}
			}
			String var = toReplace.replace("this.", "").replace("\\old(", "").replace(")", "").replace(".", "");
			if (toReplace.endsWith(")")) {
				condition = condition.replace(toReplace, var + "_oldVal");
			} else if (toReplace.endsWith(".") && rememberPara) {
				condition = condition.replace(toReplace + "size())", var + "_oldVal.size()");
			} else if (toReplace.endsWith(".")) {
				condition = condition.replace(toReplace, var + "_oldVal");
			}
		}
		return condition;
	}

	private static String applyPredicates(String condition) {
		if (predicates != null) {
			for (Predicate p : predicates) {
				while (condition.contains(p.name + "(")) {
					int index = condition.indexOf(p.name + "(") + p.name.length() + 1;
					int startIndex = index;
					int bracketCounter = 0;
					while (condition.charAt(index) != ')' || bracketCounter != 0) {
						if (condition.charAt(index) == ')')
							bracketCounter--;
						if (condition.charAt(index) == '(')
							bracketCounter++;
						index++;
					}
					String toReplace = condition.substring(startIndex, index);
					String def = p.definitions.get(0).getReplace(true);
					String[] params = toReplace.split(",");
					if (params.length == p.varsTerms.size()) {
						for (int i = 0; i < params.length; i++) {
							def = def.replace(p.varsTerms.get(i).split(" ")[1], params[i].trim());
						}
					}
					condition = condition.replace(p.name + "(" + toReplace + ")", def);
				}
			}
		}
		return condition;
	}
}
