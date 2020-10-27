package de.tu_bs.cs.isf.cbc.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.key_project.util.collection.ImmutableSet;

import com.google.common.collect.Lists;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionTechnique;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodClass;
import de.tu_bs.cs.isf.cbc.cbcmodel.Rename;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.cbcmodel.VariableKind;
import de.tu_bs.cs.isf.cbc.cbcmodel.Variant;
import de.uka.ilkd.key.control.KeYEnvironment;
import de.uka.ilkd.key.gui.MainWindow;
import de.uka.ilkd.key.java.abstraction.KeYJavaType;
import de.uka.ilkd.key.logic.op.IObserverFunction;
import de.uka.ilkd.key.proof.Goal;
import de.uka.ilkd.key.proof.Proof;
import de.uka.ilkd.key.proof.Statistics;
import de.uka.ilkd.key.proof.init.ProofInputException;
import de.uka.ilkd.key.proof.io.ProblemLoaderException;
import de.uka.ilkd.key.settings.ChoiceSettings;
import de.uka.ilkd.key.settings.ProofSettings;
import de.uka.ilkd.key.speclang.Contract;
import de.uka.ilkd.key.strategy.StrategyProperties;
import de.uka.ilkd.key.util.KeYTypeUtil;
import de.uka.ilkd.key.util.MiscTools;

public class ProveWithKey {
	public static final String REGEX_ORIGINAL = "original";
	public static final String REGEX_RESULT = "\\\\result";

	public static boolean proveStatementWithKey(AbstractStatement statement, JavaVariables vars, GlobalConditions conds, boolean returnStatement,
			boolean inlining, Renaming renaming, MethodClass javaClass, String variants, URI uri, int numberfile, IProgressMonitor monitor, String callingMethod, String varM) {
		if (variants == null || variants.length() == 0) {
			File location = createProveStatementWithKey(statement, vars, conds, renaming, javaClass, null, uri, 0, true, callingMethod, varM, inlining);
			Console.println("  Verify Pre -> {Statement} Post");
			return proveWithKey(location, monitor, inlining);
		} else {
			boolean proven = true;
			
				List<String> refinements = Lists.newArrayList(variants.split(","));
				File location = createProveStatementWithKey(statement, vars, conds, renaming, javaClass, refinements, uri, numberfile,
						true, callingMethod, varM, proven);
				Console.println("  Verify Pre -> {Statement} Post");

				if (!proveWithKey(location, monitor, inlining)) {
					proven = false;
				}
			
			return proven;
			}
		}

	public static File createProveStatementWithKey(AbstractStatement statement, JavaVariables vars,
			GlobalConditions conds, Renaming renaming, MethodClass javaClass,List<String> refinements, URI uri, int numberFile,
			boolean override, String callingMethod, String varM, boolean returnStatement) {
		FileUtil.setApplicationUri(uri);
		String programVariablesString = "";
		String conditionArraysCreated = "";
		JavaVariable returnVariable = null;
		if (vars != null) {
			for (JavaVariable var : vars.getVariables()) {
				if (var.getKind() == VariableKind.RETURN) {
					returnVariable = var;
				} else {
					if(var.getKind() != VariableKind.GLOBAL) {  
						programVariablesString += var.getName() + "; ";
						// if variable is an Array add <created> condition for key
						if (var.getName().contains("[]")) {
							String varName = var.getName().substring(var.getName().indexOf(" ") + 1);
							conditionArraysCreated += " & " + varName + ".<created>=TRUE";
						}
					}
				}
			}
		}

		String globalConditionsString = "";
		if (conds != null) {
			for (Condition cond : conds.getConditions()) {
				if (!cond.getName().isEmpty()) {
					globalConditionsString += " & " + cond.getName();
				}
			}
		}

		IProject thisProject = FileUtil.getProject(uri);
		CbCFormula formula = getCbCFormula(statement);

		String assignmentString = "";
		String preFormula = Parser.getConditionFromCondition(formula.getStatement().getPreCondition().getName());
		String postFormula = Parser.getConditionFromCondition(formula.getStatement().getPostCondition().getName());
		String pre = Parser.getConditionFromCondition(statement.getPreCondition().getName());
		String post = Parser.getConditionFromCondition(statement.getPostCondition().getName());
		List<String> modifiables = Parser.getModifiedVarsFromCondition(statement.getPostCondition().getName());
		String stat = statement.getName();

		if (refinements != null && pre.equals(preFormula)) {
			pre = composeContractForCbCDiagram(formula.getCompositionTechnique(), refinements, pre,
					Parser.KEYWORD_JML_PRE, returnVariable, vars, callingMethod);
		}
		if (refinements != null && post.equals(postFormula)) {
			modifiables = composeModifiables(refinements, modifiables, formula.getCompositionTechnique(), vars, true, callingMethod);
			post = composeContractForCbCDiagram(formula.getCompositionTechnique(), refinements, post,
					Parser.KEYWORD_JML_POST, returnVariable, vars, callingMethod);
		}

		List<String> unmodifiedVariables = Parser.getUnmodifiedVars(modifiables, vars.getVariables());//what if a glob var is in another diagram defined?

		if (pre == null || pre.length() == 0) {
			pre = "true";
		}
		if (post == null || post.length() == 0) {
			post = "true";
		}

		if (renaming != null) {
			globalConditionsString = useRenamingCondition(renaming, globalConditionsString);
			pre = useRenamingCondition(renaming, pre);
			post = useRenamingCondition(renaming, post);
			stat = useRenamingStatement(renaming, stat);
		}

		unmodifiedVariables = unmodifiedVariables.stream().distinct().collect(Collectors.toList());
		for (String var : unmodifiedVariables) {
			String varName = var.substring(var.indexOf(" ") + 1);
			programVariablesString += var + "_old; ";
			assignmentString += "||" + varName + "_old:=" + varName;
			post += "&" + varName + "=" + varName + "_old";
			// if variable is an Array add <created> condition for key
			if (var.contains("[]")) {
				conditionArraysCreated += " & " + varName + "_old.<created>=TRUE";
			}
		}

		if (refinements != null && refinements.size() > 0 && stat.contains("original(")) {
			String[] splittedRefinement = refinements.get(0).split("\\.");
			if (Character.isLowerCase(splittedRefinement[0].charAt(0))) {
				stat = stat.replaceFirst("original", splittedRefinement[0] + ".generated_" + splittedRefinement[1]);
			} else {
				String className = FileUtil.generateComposedClass(thisProject, refinements, vars, callingMethod);
				stat = stat.replaceFirst("original", className + ".generated_" + splittedRefinement[1]);
			}
		} 
		
		if(returnStatement) {
			stat = ";";
		}

		stat = stat.replace("this.", "self.");
		pre = pre.replace("this.", "self.");
		post = post.replace("this.", "self.");
		globalConditionsString = globalConditionsString.replace("this.", "self.");
		String self = "";
		String selfConditions = "";
		if(javaClass != null) {
			self = javaClass.getMethodClass() +" self;";
			selfConditions = " & self.<created>=TRUE & " + javaClass.getMethodClass() + "::exactInstance(self)=TRUE &  !self = null & self.<inv> ";
		}
	

		/*String problem = "\\javaSource \"" + thisProject.getLocation() + "/\";" + "\\include \"helper.key\";"
				+ "\\programVariables {" + programVariablesString + javaClass.getMethodClass() +" self; Heap heapAtPre;}" + "\\problem {(" + pre + " "
				+ globalConditionsString + conditionArraysCreated + " & self.<created>=TRUE & " + javaClass.getMethodClass() + "::exactInstance(self)=TRUE &  !self = null & self.<inv>"
						+ "& wellFormed(heap)) -> {heapAtPre := heap"//
				+ assignmentString + "} \\<{" + stat + "}\\> (" + post + ")}";*/
		
		String problem = "\\javaSource \"" + thisProject.getLocation() + "/\";" + "\\include \"helper.key\";"//& source.<inv> & source.<created>=TRUE & destination.<inv> & destination.<created>=TRUE
				+ "\\programVariables {" + programVariablesString + self+" Heap heapAtPre;}" + "\\problem {(" + pre + " "//& a.<inv> & a.<created>=TRUE//& self.appl.<inv> & self.appl.<created>=TRUE& self.appl.account.<inv> & self.appl.account.<created>=TRUE
				+ globalConditionsString + conditionArraysCreated + selfConditions //self.appl.<inv> & self.appl.account.<inv> &  self.appl.<created>=TRUE & self.appl.account.<created>=TRUE"
						+ "& wellFormed(heap)) -> {heapAtPre := heap"//
				+ assignmentString + "} \\<{" + stat + "}\\> (" + post + ")}";

		problem = problem.replaceAll("static", "");
		problem = problem.replaceAll("return", "");
		
//		Console.println("============================");
//		Console.println(problem);
//		Console.println("============================");
		
		String location = thisProject.getLocation() + "/src/prove" + uri.trimFileExtension().lastSegment();
		File keyFile = FileUtil.writeFile(problem, location, numberFile, override);
		return keyFile;
	}

//	public static File createProveStatementWithKey2(String className, AbstractStatement statement, JavaVariables vars,
//			GlobalConditions conds, Renaming renaming, URI uri, int numberFile,
//			boolean override, String callingMethod, String varM, boolean returnStatement) {
//
//		String programVariablesString = "";
//		String conditionArraysCreated = "";
//		if (vars != null) {
//			for (JavaVariable var : vars.getVariables()) {
//				programVariablesString += var.getName() + "; ";
//				// if variable is an Array add <created> condition for key
//				if (var.getName().contains("[]")) {
//					String varName = var.getName().substring(var.getName().indexOf(" ") + 1);
//					conditionArraysCreated += " & " + varName + ".<created>=TRUE";
//				}
//
//			}
//		}
//
//		String globalConditionsString = "";
//		if (conds != null) {
//			for (Condition cond : conds.getConditions()) {
//				if (!cond.getName().isEmpty()) {
//					globalConditionsString += " & " + cond.getName();
//				}
//			}
//		}
//
//		IProject thisProject = FileUtil.getProject(uri);
//		CbCFormula formula = getCbCFormula(statement);
//
//		String assignmentString = "";
//		String preFormula = Parser.getConditionFromCondition(formula.getStatement().getPreCondition().getName());
//		String postFormula = Parser.getConditionFromCondition(formula.getStatement().getPostCondition().getName());
//		String pre = Parser.getConditionFromCondition(statement.getPreCondition().getName());
//		String post = Parser.getConditionFromCondition(statement.getPostCondition().getName());
//		List<String> modifiables = Parser.getModifiedVarsFromCondition(statement.getPostCondition().getName());
//		String stat = statement.getName();
//
//		if (refinements != null && pre.equals(preFormula)) {  
//			pre = composeContractForCbCDiagram(formula.getCompositionTechnique(), refinements, pre,
//					Parser.KEYWORD_JML_PRE, returnVariable, vars, callingMethod);
//		}
//		if (refinements != null && post.equals(postFormula)) {   
//			modifiables = composeModifiables(refinements, modifiables, formula.getCompositionTechnique(), vars, true, callingMethod);
//			post = composeContractForCbCDiagram(formula.getCompositionTechnique(), refinements, post,
//					Parser.KEYWORD_JML_POST, returnVariable, vars, callingMethod);
//		}
//		List<String> unmodifiedVariables = Lists.newArrayList();
//		if (vars != null) {
//			unmodifiedVariables = Parser.getUnmodifiedVars(modifiables, vars.getVariables());
//		}
//		
//
//		if (pre == null || pre.length() == 0) {
//			pre = "true";
//		}
//		if (post == null || post.length() == 0) {
//			post = "true";
//		}
//
//		if (renaming != null) {
//			globalConditionsString = useRenamingCondition(renaming, globalConditionsString);
//			pre = useRenamingCondition(renaming, pre);
//			post = useRenamingCondition(renaming, post);
//			stat = useRenamingStatement(renaming, stat);
//		}
//
///*		String problem = "\\javaSource \"" + thisProject.getLocation() + "/\";" + "\\include \"helper.key\";"
//				+ "\\programVariables {" + programVariablesString + " Heap heapAtPre;}" + "\\problem {(" + pre + " "
//				+ globalConditionsString + conditionArraysCreated + " & wellFormed(heap) & self != null & self.<inv> & self.<created> = TRUE & " + className +   "::exactInstance(self)=TRUE) -> {heapAtPre := heap"
//				+ assignmentString + "} \\<{" + stat + "}\\> (" + post + " & self.<inv>)}";*/
//
//		String problem = "\\javaSource \"" + thisProject.getLocation() + "/\";" + "\\include \"helper.key\";"
//				+ "\\programVariables {" + programVariablesString + " Heap heapAtPre;}" + "\\problem {(" + pre + " "
//				+ globalConditionsString + conditionArraysCreated + " & wellFormed(heap) & self != null & self.<inv> & self.<created> = TRUE & " + className +   "::exactInstance(self)=TRUE) -> {heapAtPre := heap"
//				+ assignmentString + "} \\<{" + stat + "}\\> (" + post + " & self.<inv>)}";
//		
//		for (String var : unmodifiedVariables) {
//			String varName = var.substring(var.indexOf(" ") + 1);
//			programVariablesString += var + "_old; ";
//			assignmentString += "||" + varName + "_old:=" + varName;
//			post += "&" + varName + "=" + varName + "_old";
//			// if variable is an Array add <created> condition for key
//			if (var.contains("[]")) {
//				conditionArraysCreated += " & " + varName + "_old.<created>=TRUE";
//			}
//		}
//
//		if (refinements != null && refinements.size() > 0 && (stat.contains("original(") || (stat.contains("(") && !stat.contains("\\.")))) {
//			// TODO
//			String[] splittedRefinement = refinements.get(0).split("\\.");
//			if (Character.isLowerCase(splittedRefinement[0].charAt(0))) {
//				stat = stat.replaceFirst("original", splittedRefinement[0] + ".generated_" + splittedRefinement[1]);
//			} else {
//				String composedClassName = FileUtil.generateComposedClass(thisProject, refinements, vars, callingMethod);
//				stat = stat.replaceFirst("original", classcomposedClassNameName + ".generated_" + splittedRefinement[1]);
//				if (!varM.equals("")) {
//					stat = stat.replaceFirst(varM.toLowerCase(), composedClassName + ".generated_" + splittedRefinement[1]);
//				}
//				
//			}
//		}
//
//		String problem = "\\javaSource \"" + thisProject.getLocation() + "/\";" + "\\include \"helper.key\";"
//				+ "\\programVariables {" + programVariablesString + " Heap heapAtPre;}" + "\\problem {(" + pre + " "
//				+ globalConditionsString + conditionArraysCreated + " & wellFormed(heap) ) -> {heapAtPre := heap"
//				+ assignmentString + "} \\<{" + stat + "}\\> (" + post + ")}";
//
//		String location = thisProject.getLocation() + "/features/" + uri.segment(uri.segmentCount()-3) + "/prove" + uri.trimFileExtension().lastSegment();
//		File keyFile = FileUtil.writeFile(problem, location, numberFile, override);
//		return keyFile;
//	}
//	

		
	public static IProject getProject(URI uri) {
		uri = uri.trimFragment();
		String uriPath = uri.toPlatformString(true);
		uriPath = uriPath.substring(1, uriPath.length());
		int positionOfSlash = uriPath.indexOf('/') + 1;
		uriPath = uriPath.substring(positionOfSlash, uriPath.length());
		IProject thisProject = null;
		for (IProject p : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			if (p.getFile(new Path(uriPath)).exists()) {
				thisProject = p;
			}
		}
//		if (thisProject.getName().contains("Userstudy")) {
//		File diagramFile = new File(thisProject.getLocation() + "/" + uriPath);
//		File diagramFileCopy = new File(thisProject.getLocation() + "/src/saved/ExDia" + proofCounter +  ".diagram");
//		File cbcFile = new File(thisProject.getLocation() + "/" + uriPath.substring(0, uriPath.indexOf(".")) + ".cbcmodel");
//		File cbcFileCopy = new File(thisProject.getLocation() + "/src/saved/ExDia" + proofCounter +  ".cbcmodel");
//		proofCounter++;
//		try {
//			IWorkspace workspace = ResourcesPlugin.getWorkspace();
//			Files.copy(diagramFile.toPath(), diagramFileCopy.toPath(), StandardCopyOption.REPLACE_EXISTING);
//			Files.copy(cbcFile.toPath(), cbcFileCopy.toPath(), StandardCopyOption.REPLACE_EXISTING);  
//			IPath iLocation = Path.fromOSString(diagramFileCopy.getAbsolutePath()); 
//			IFile ifile = workspace.getRoot().getFileForLocation(iLocation);
//			ifile.refreshLocal(0, null);
//			iLocation = Path.fromOSString(cbcFileCopy.getAbsolutePath()); 
//			ifile = workspace.getRoot().getFileForLocation(iLocation);
//			ifile.refreshLocal(0, null);
//		} catch (IOException | CoreException e) {
//			e.printStackTrace();
//		}
//	}
		return thisProject;
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

	static List<String> composeModifiables(List<String> refinements, List<String> modifiables,
			CompositionTechnique compTechnique, JavaVariables vars, boolean includeFormulaModifiable, String callingMethod) {
		if (refinements != null && refinements.size() > 0) {
			for (String refinement : refinements) {
				String[] splittedRefinement = refinement.split("\\.");
				IFile classFile = FileUtil.getClassFile(getRefinementClass("Generated_" + splittedRefinement[0], vars));
				String methodName = splittedRefinement[1];
				if (!includeFormulaModifiable) {
					compTechnique = Parser.getCompositionTechniqueForMethod(classFile, methodName,
							Parser.KEYWORD_JML_MODIFIABLE, callingMethod);
					String modifiableOriginal = Parser.getContentFromJML(classFile, methodName,
							Parser.KEYWORD_JML_MODIFIABLE);
					List<String> modifiableList = new ArrayList<String>();
					if (!modifiableOriginal.equals("")) {
						modifiableList = Parser.getModifiedVarsFromCondition("modifiable(" + modifiableOriginal + ");");
					}
					modifiables = applyCompositionTechniqueOnModifiables(modifiableList, modifiableOriginal,
							compTechnique);
					includeFormulaModifiable = true;
				} else {
					String modifiableOriginal = Parser.getContentFromJML(classFile, methodName,
							Parser.KEYWORD_JML_MODIFIABLE);
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
		case CONTRACT_OVERRIDING:
			// do nothing because original contract is overridden
			break;
		case EXPLICIT_CONTRACTING:
			modifiables.remove(REGEX_ORIGINAL);
			for (String var : modifiableOriginal.split(",")) {
				if (var != "" && !modifiables.contains(var)) {
					modifiables.add(var);
				}
			}
			break;
		case CONJUNCTIVE_CONTRACTING:
			modifiables.addAll(Lists.newArrayList(modifiableOriginal.split(",")));
			break;
		}

		return modifiables;
	}
	
	private static String applyCompositionTechnique(String condition, String conditionOriginal,
			CompositionTechnique compositionTechnique) {
		String composedCondition = condition;
		switch (compositionTechnique) {
		case CONTRACT_OVERRIDING:
			composedCondition = condition;
			break;
		case CONJUNCTIVE_CONTRACTING:
			if (conditionOriginal != "") {
				composedCondition = "(" + condition + ") & (" + conditionOriginal + ")";
			} else {
				composedCondition = condition;
			}
			
			break;
		case EXPLICIT_CONTRACTING:
			Pattern pattern = Pattern.compile(REGEX_ORIGINAL);
			Matcher matcher = pattern.matcher(condition);
			composedCondition = matcher.replaceAll(Matcher.quoteReplacement(conditionOriginal));
		}
		return composedCondition;
	}

	private static String composeContractForCbCDiagram(CompositionTechnique compositionTechnique,
			List<String> refinements, String condition, String keyword, JavaVariable returnVariable,
			JavaVariables vars, String callingMethod) {
		String composedCondition = condition;
		CompositionTechnique compTechnique = compositionTechnique;		
		for (int i = 0; i < refinements.size(); i++) {
			String[] splittedRefinement = refinements.get(i).split("\\.");
			IFile classFile = FileUtil.getClassFile(getRefinementClass("Generated_" + splittedRefinement[0], vars));
			String methodName = splittedRefinement[1];
			if (i != 0) {
				splittedRefinement = refinements.get(i-1).split("\\.");
				methodName = splittedRefinement[1];
				compTechnique = Parser.getCompositionTechniqueForMethod(classFile, methodName, keyword, callingMethod);
			}
			splittedRefinement = refinements.get(i).split("\\.");
			methodName = splittedRefinement[1];
			if (classFile != null) {
				String conditionOriginal = Parser.getContentFromJML(classFile, methodName, keyword);
				composedCondition = applyCompositionTechnique(composedCondition, conditionOriginal, compTechnique);
				if (compTechnique == CompositionTechnique.CONTRACT_OVERRIDING
						|| (compTechnique == CompositionTechnique.EXPLICIT_CONTRACTING
								&& !composedCondition.contains(REGEX_ORIGINAL))) {					
					return resolveResultKeyword(composedCondition, returnVariable);
				}

			}
		}
		return resolveResultKeyword(composedCondition, returnVariable);
	}

	static String composeContractForCalledOriginal(List<String> refinements, String keyword, String callingMethod) {
		String[] splittedRefinement = refinements.get(0).split("\\.");
		IFile classFile = FileUtil.getClassFile("Generated_" + splittedRefinement[0]);
		String methodName = splittedRefinement[1];
		String composedCondition = Parser.getContentFromJML(classFile, methodName, keyword);
		CompositionTechnique compositionTechnique = Parser.getCompositionTechniqueForMethod(classFile, methodName,
				keyword, callingMethod); 
		for (int i = 1; i < refinements.size(); i++) {
			IFile nextClassFile = FileUtil.getClassFile("Generated_" + splittedRefinement[0]);
			if (i != 1) {
				splittedRefinement = refinements.get(i-1).split("\\.");
				methodName = splittedRefinement[1];
				compositionTechnique = Parser.getCompositionTechniqueForMethod(nextClassFile, methodName, keyword, callingMethod);//old version statt methodName
			}
			splittedRefinement = refinements.get(i).split("\\.");
			String nextMethodName = splittedRefinement[1];
			if (classFile != null) {
				String conditionOriginal = Parser.getContentFromJML(nextClassFile, nextMethodName, keyword);
				composedCondition = applyCompositionTechnique(composedCondition, conditionOriginal,
						compositionTechnique);
				if (compositionTechnique == CompositionTechnique.CONTRACT_OVERRIDING
						|| (compositionTechnique == CompositionTechnique.EXPLICIT_CONTRACTING
								&& !composedCondition.contains(REGEX_ORIGINAL))) {
					return composedCondition;
				}

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

	private static String getRefinementClass(String string, JavaVariables vars) {
		if (Character.isLowerCase(string.charAt(0))) {
			for (JavaVariable var : vars.getVariables()) {
				String[] splitVarNameType = var.getName().split("\\s", 2);

				if (splitVarNameType[1].equals(string)) {
					return splitVarNameType[0];
				}
			}
		}
		return string;
	}

	public static boolean proveWithKey(File location, IProgressMonitor monitor, boolean inlining) {
		Proof proof = createKeyProof(location, monitor, inlining);
		if (proof != null) {
			// Show proof result
			boolean closed = proof.openGoals().isEmpty();
			Console.println("Proof is closed: " + closed);
			return closed;
		}
		return false;
	}

	private static String useRenamingCondition(Renaming renaming, String toRename) {
		for (Rename rename : renaming.getRename()) {
			if (rename.getType().equalsIgnoreCase("boolean")) {
				toRename = toRename.replaceAll(rename.getNewName(), "TRUE=" + rename.getFunction());
			} else {
				toRename = toRename.replaceAll(rename.getNewName(), rename.getFunction());
			}
		}
		return toRename;
	}

	private static String useRenamingCondition(List<Rename> renaming, String toRename) {
		for (Rename rename : renaming) {
			if (rename.getType().equalsIgnoreCase("boolean")) {
				toRename = toRename.replaceAll(rename.getNewName(), "TRUE=" + rename.getFunction());
			} else {
				toRename = toRename.replaceAll(rename.getNewName(), rename.getFunction());
			}
		}
		return toRename;
	}

	private static String useRenamingStatement(Renaming renaming, String toRename) {
		for (Rename rename : renaming.getRename()) {
			toRename = toRename.replaceAll(rename.getNewName(), rename.getFunction());
		}
		return toRename;
	}

	public static boolean provePreWithKey(Condition invariant, Condition preCondition, JavaVariables vars, MethodClass javaClass,
			GlobalConditions conds, Renaming renaming, URI uri, IProgressMonitor monitor) {
		File location = createProvePreWithKey(invariant, preCondition, vars, javaClass, conds, renaming, uri, 0, true);
		Console.println("  Verify Pre -> Invariant");
		return proveWithKey(location, monitor, false);
	}

	public static File createProvePreWithKey(Condition invariant, Condition preCondition, JavaVariables vars, MethodClass javaClass,
			GlobalConditions conds, Renaming renaming, URI uri, int numberFile, boolean override) {

		String programVariablesString = "";
		if (vars != null) {
			for (JavaVariable var : vars.getVariables()) {
				if(var.getKind() != VariableKind.GLOBAL) {  
					programVariablesString += var.getName() + "; ";					
				}
			}
		}

		String globalConditionsString = "";
		if (conds != null) {
			for (Condition cond : conds.getConditions()) {
				if (!cond.getName().isEmpty()) {
					globalConditionsString += " & " + cond.getName();
				}
			}
		}

		IProject thisProject = FileUtil.getProject(uri);
		String preString = Parser.getConditionFromCondition(preCondition.getName());
		String invariantString = invariant.getName();

		if (preString == null || preString.length() == 0) {
			preString = "true";
		}
		if (invariantString == null || invariantString.length() == 0) {
			invariantString = "true";
		}

		if (renaming != null) {
			globalConditionsString = useRenamingCondition(renaming, globalConditionsString);
			preString = useRenamingCondition(renaming, preString);
			invariantString = useRenamingCondition(renaming, invariantString);
		}

		preString = preString.replace("this.", "self.");
		globalConditionsString = globalConditionsString.replace("this.", "self.");
		invariantString = invariantString.replace("this.", "self.");

				  
				//conditionArraysCreated + " & self.<created> = TRUE & " + javaClass.getMethodClass() + "::exactInstance(self)=TRUE &  !self = null & self.<inv> "
					//	+ "& wellFormed(heap)) -> {heapAtPre := heap"//
				//+ assignmentString + "} \\<{" + stat + "}\\> (" + post + ")}";
		
		String problem = "\\javaSource \"" + thisProject.getLocation() + "/\";" + "\\include \"helper.key\";"
				+ "\\programVariables {" + programVariablesString + javaClass.getMethodClass() + " self; Heap heapAtPre;}" 
				+ "\\problem {(" + preString + " " + globalConditionsString + " & self.<created> = TRUE &" + javaClass.getMethodClass() + 
				"::exactInstance(self)=TRUE &  !self = null & self.<inv> & wellFormed(heap)) -> {heapAtPre := heap} (" 
				+ invariantString + ")}";
		/*String problem = "\\javaSource \"" + thisProject.getLocation() + "/\";" + "\\include \"helper.key\";"
				+ "\\programVariables {" + programVariablesString + javaClass.getMethodClass() + " self; Heap heapAtPre;}" 
				+ "\\problem {(" + preString + " " + globalConditionsString + ") -> {heapAtPre := heap} (" 
				+ invariantString + ")}";*/
		Console.println(problem);
		String location = thisProject.getLocation() + "/src/prove" + uri.trimFileExtension().lastSegment();
		File keyFile = FileUtil.writeFile(problem, location, numberFile, override);
		return keyFile;
	}

	public static boolean provePostWithKey(Condition invariant, Condition guard, Condition postCondition, MethodClass javaClass,
			JavaVariables vars, GlobalConditions conds, Renaming renaming, URI uri, IProgressMonitor monitor) {
		File location = createProvePostWithKey(invariant, guard, postCondition, javaClass, vars, conds, renaming, uri, 0, true);
		Console.println("  Verify (Invariant & !Guard) -> Post");
		return proveWithKey(location, monitor, false);
	}

	public static File createProvePostWithKey(Condition invariant, Condition guard, Condition postCondition, MethodClass javaClass,
			JavaVariables vars, GlobalConditions conds, Renaming renaming, URI uri, int numberFile, boolean override) {

		String programVariablesString = "";
		if (vars != null) {
			for (JavaVariable var : vars.getVariables()) {
				programVariablesString += var.getName() + "; ";
			}
		}

		String globalConditionsString = "";
		if (conds != null) {
			for (Condition cond : conds.getConditions()) {
				if (!cond.getName().isEmpty()) {
					globalConditionsString += " & " + cond.getName();
				}
			}
		}

		IProject thisProject = FileUtil.getProject(uri);

		String postString = Parser.getConditionFromCondition(postCondition.getName());
		String guardString = guard.getName();
		String invariantString = invariant.getName();

		if (postString == null || postString.length() == 0) {
			postString = "true";
		}
		if (guardString == null || guardString.length() == 0) {
			guardString = "true";
		}
		if (invariantString == null || invariantString.length() == 0) {
			invariantString = "true";
		}

		if (renaming != null) {
			globalConditionsString = useRenamingCondition(renaming, globalConditionsString);
			postString = useRenamingCondition(renaming, postString);
			invariantString = useRenamingCondition(renaming, invariantString);
		}
		String self ="";
		String selfConditions = "";
		if(javaClass != null) {
			self = javaClass.getMethodClass() +" self;";
			selfConditions = " & self.<created>=TRUE & " + javaClass.getMethodClass() + "::exactInstance(self)=TRUE &  !self = null & self.<inv> ";
		}

		String problem = "\\javaSource \"" + thisProject.getLocation() + "/\";" + "\\include \"helper.key\";"
				+ "\\programVariables {" + programVariablesString + self+ "Heap heapAtPre;}" + "\\problem {("
				+ invariantString + " & !(" + guardString + ") " + globalConditionsString +  selfConditions +") -> {heapAtPre := heap} ("
				+ postString + ")}";

		problem = problem.replace("this.", "self.");
		
		String location = thisProject.getLocation() + "/features/" + uri.segment(uri.segmentCount()-3) + "/prove" + uri.trimFileExtension().lastSegment();
		File keyFile = FileUtil.writeFile(problem, location, numberFile, override);
		return keyFile;
	}

	public static boolean provePreSelWithKey(EList<Condition> guards, Condition preCondition, JavaVariables vars, MethodClass javaClass, 
			GlobalConditions conds, Renaming renaming, URI uri, IProgressMonitor monitor) {
		File location = createProvePreSelWithKey(guards, preCondition, vars, javaClass, conds, renaming, uri, 0, true);
		Console.println("  Verify Pre -> GvGvG...");
		return proveWithKey(location, monitor, false);
	}

	public static File createProvePreSelWithKey(EList<Condition> guards, Condition preCondition, JavaVariables vars, MethodClass javaClass,
			GlobalConditions conds, Renaming renaming, URI uri, int numberFile, boolean override) {
		String programVariablesString = "";
		if (vars != null) {
			for (JavaVariable var : vars.getVariables()) {
				programVariablesString += var.getName() + "; ";
			}
		}

		String globalConditionsString = "";
		if (conds != null) {
			for (Condition cond : conds.getConditions()) {
				if (!cond.getName().isEmpty()) {
					globalConditionsString += " & " + cond.getName();
				}
			}
		}

		IProject thisProject = FileUtil.getProject(uri);

		String preString = Parser.getConditionFromCondition(preCondition.getName());
		String guardString;
		if (guards != null && guards.get(0) != null) {
			guardString = "((" + guards.get(0).getName() + ")";
			for (int i = 1; i < guards.size(); i++) {
				guardString += " | (" + guards.get(i).getName() + ")";
			}
			guardString += ")";
		} else {
			guardString = "true";
		}

		if (preString == null || preString.length() == 0) {
			preString = "true";
		}

		if (renaming != null) {
			globalConditionsString = useRenamingCondition(renaming, globalConditionsString);
			preString = useRenamingCondition(renaming, preString);
			guardString = useRenamingCondition(renaming, guardString);
		}
		String self = "";
		String selfConditions = "";
		if(javaClass != null) {
			self = javaClass.getMethodClass() +" self;";
			selfConditions = " & self.<created>=TRUE & " + javaClass.getMethodClass() + "::exactInstance(self)=TRUE &  !self = null & self.<inv> ";
		}

		String problem = "\\javaSource \"" + thisProject.getLocation() + "/\";" + "\\include \"helper.key\";"
				+ "\\programVariables {" + programVariablesString +self +"}" + "\\problem {(" + preString
				+ globalConditionsString + selfConditions +") -> (" + guardString + ")}";
		
		problem = problem.replace("this.", "self.");

		String location = thisProject.getLocation() + "/"/*features/"*/ + uri.segment(uri.segmentCount()-3) + "/prove" + uri.trimFileExtension().lastSegment();
		File keyFile = FileUtil.writeFile(problem, location, numberFile, override);
		return keyFile;
	}

	public static boolean proveVariantWithKey(String code, Condition invariant, JavaVariables vars,
			GlobalConditions conds, Renaming renaming, URI uri, IProgressMonitor monitor) {
		File location = createProveVariantWithKey(code, invariant, vars, conds, renaming, uri, 0, true);
		Console.println("  Verify Pre -> {WhileStatement} (true)");
		return proveWithKey(location, monitor, false);
	}

	public static File createProveVariantWithKey(String code, Condition invariant, JavaVariables vars,
			GlobalConditions conds, Renaming renaming, URI uri, int numberFile, boolean override) {
		String programVariablesString = "";
		if (vars != null) {
			for (JavaVariable var : vars.getVariables()) {
				programVariablesString += var.getName() + "; ";
			}
		}

		String globalConditionsString = "";
		if (conds != null) {
			for (Condition cond : conds.getConditions()) {
				if (!cond.getName().isEmpty()) {
					globalConditionsString += " & " + cond.getName();
				}
			}
		}

		IProject thisProject = FileUtil.getProject(uri);

		String invariantString = invariant.getName();

		if (invariantString == null || invariantString.length() == 0) {
			invariantString = "true";
		}

		if (renaming != null) {
			globalConditionsString = useRenamingCondition(renaming, globalConditionsString);
			invariantString = useRenamingCondition(renaming, invariantString);
		}

		String problem = "\\javaSource \"" + thisProject.getLocation() + "/\";" + "\\include \"helper.key\";"
				+ "\\programVariables {" + programVariablesString + " Heap heapAtPre;}" + "\\problem {("
				+ invariantString + globalConditionsString + ") -> {heapAtPre := heap} \\<{" + code + "}\\> (true)}";

		String location = thisProject.getLocation() + "/features/" + uri.segment(uri.segmentCount()-3) + "/prove" + uri.trimFileExtension().lastSegment();
		File keyFile = FileUtil.writeFile(problem, location, numberFile, override);
		return keyFile;
	}

	public static boolean proveVariant2WithKey(String code, Condition invariant, Condition guard, Variant variant, MethodClass javaClass,
			JavaVariables vars, GlobalConditions conds, Renaming renaming, URI uri, IProgressMonitor monitor) {
		File location = createProveVariant2WithKey(code, invariant, guard, variant, javaClass, vars, conds, renaming, uri, 0,
				true);
		Console.println("Verify Pre -> {WhileStatement} (variant<variant0 & variant >= 0)");
		return proveWithKey(location, monitor, false);
	}

	public static File createProveVariant2WithKey(String code, Condition invariant, Condition guard, Variant variant, MethodClass javaClass,
			JavaVariables vars, GlobalConditions conds, Renaming renaming, URI uri, int numberFile, boolean override) {
		String programVariablesString = "";
		if (vars != null) {
			for (JavaVariable var : vars.getVariables()) {
				programVariablesString += var.getName() + "; ";
			}
		}
		programVariablesString += "int variant;";

		String globalConditionsString = "";
		if (conds != null) {
			for (Condition cond : conds.getConditions()) {
				if (!cond.getName().isEmpty()) {
					globalConditionsString += " & " + cond.getName();
				}
			}
		}

		IProject thisProject = FileUtil.getProject(uri);

		String invariantString = invariant.getName();

		if (invariantString == null || invariantString.length() == 0) {
			invariantString = "true";
		}

		String guardString = guard.getName();

		if (guardString == null || guardString.length() == 0) {
			guardString = "true";
		}

		if (renaming != null) {
			globalConditionsString = useRenamingCondition(renaming, globalConditionsString);
			invariantString = useRenamingCondition(renaming, invariantString);
			guardString = useRenamingCondition(renaming, guardString);
		}

		String variantString = variant.getName();
		String self = "";
		String selfConditions = "";
		if(javaClass != null) {
			self = javaClass.getMethodClass() +" self;";
			selfConditions = " & self.<created>=TRUE & " + javaClass.getMethodClass() + "::exactInstance(self)=TRUE &  !self = null & self.<inv> ";
		}

		String problem = "\\javaSource \"" + thisProject.getLocation() + "/\";" + "\\include \"helper.key\";"
				+ "\\programVariables {" + programVariablesString + self +  "Heap heapAtPre;}" + "\\problem {("
				+ invariantString + " & " + guardString + globalConditionsString + selfConditions + ") ->{variant := " + variantString
				+ " || heapAtPre := heap} \\<{" + code + "}\\> ((" + variantString + ") <variant & " + variantString
				+ ">=0)}";

		problem = problem.replace("this.", "self.");
		
		String location = thisProject.getLocation() + "/src/prove" + uri.trimFileExtension().lastSegment();
		File keyFile = FileUtil.writeFile(problem, location, numberFile, override);
		return keyFile;
	}

	public static boolean provePreImplPreWithKey(Condition preParent, Condition preChild, JavaVariables vars,
			GlobalConditions conds, Renaming renaming, URI uri, IProgressMonitor monitor) {
		File location = createProvePreImplPreWithKey(preParent, preChild, vars, conds, renaming, uri, 0, true);
		Console.println("  Verify PreParent -> PreChild");
		return proveWithKey(location, monitor, false);
	}

	public static boolean provePostImplPostWithKey(Condition postParent, Condition postChild, JavaVariables vars,
			GlobalConditions conds, Renaming renaming, URI uri, IProgressMonitor monitor) {
		File location = createProvePreImplPreWithKey(postChild, postParent, vars, conds, renaming, uri, 0, true);
		Console.println("  Verify PostChild -> PostParent");
		return proveWithKey(location, monitor, false);
	}

	public static File createProvePreImplPreWithKey(Condition preParent, Condition preChild, JavaVariables vars,
			GlobalConditions conds, Renaming renaming, URI uri, int numberFile, boolean override) {

		String programVariablesString = "";
		if (vars != null) {
			for (JavaVariable var : vars.getVariables()) {
				programVariablesString += var.getName() + "; ";
			}
		}

		String globalConditionsString = "";
		if (conds != null) {
			for (Condition cond : conds.getConditions()) {
				if (!cond.getName().isEmpty()) {
					globalConditionsString += " & " + cond.getName();
				}
			}
		}

		IProject thisProject = FileUtil.getProject(uri);

		String preParentString = Parser.getConditionFromCondition(preParent.getName());
		String preChildString = Parser.getConditionFromCondition(preChild.getName());

		if (preParentString == null || preParentString.length() == 0) {
			preParentString = "true";
		}
		if (preChildString == null || preChildString.length() == 0) {
			preChildString = "true";
		}

		if (renaming != null) {
			globalConditionsString = useRenamingCondition(renaming, globalConditionsString);
			preParentString = useRenamingCondition(renaming, preParentString);
			preChildString = useRenamingCondition(renaming, preChildString);
		}

		String problem = "\\javaSource \"" + thisProject.getLocation() + "/\";" + "\\programVariables {"
				+ programVariablesString + " Heap heapAtPre;}" + "\\problem {(" + preParentString + " "
				+ globalConditionsString + ") -> {heapAtPre := heap} (" + preChildString + ")}";

		String location = thisProject.getLocation() + "/features/" + uri.segment(uri.segmentCount()-3) + "/prove" + uri.trimFileExtension().lastSegment();
		File keyFile = FileUtil.writeFile(problem, location, numberFile, override);
		return keyFile;
	}

	public static boolean checkFileIsProven(URI uri, int numberFile) {
		IProject project = FileUtil.getProject(uri);
		File location = new File(project.getLocation() + "/features/" + uri.segment(uri.segmentCount()-3) + "/prove" + uri.trimFileExtension().lastSegment() + "/prove"
				+ numberFile + ".key");
		if (location.exists()) {
			try {
				KeYEnvironment<?> env = KeYEnvironment.load(location, null, null, null);
				Proof proof = env.getLoadedProof();
				return proof.openGoals().isEmpty();
			} catch (ProblemLoaderException e) {
				e.printStackTrace();
			}
		}
		return false;

	}

	public static boolean proveMethodFormulaWithKey(Condition second, Condition first, String javaClass, List<JavaVariable> vars,
			List<Condition> conds, List<Rename> renaming, URI uri, IProgressMonitor monitor) {
		File location = createProveMethodFormulaWithKey(second, first, vars, javaClass, conds, renaming, uri, 0, true);
		Console.println("  Verify Condition -> Condition");
		return proveWithKey(location, monitor, false);
	}

	public static File createProveMethodFormulaWithKey(Condition second, Condition first, List<JavaVariable> vars, String javaClass,
			List<Condition> conds, List<Rename> renaming, URI uri, int numberFile, boolean override) {

		String programVariablesString = "";
		if (vars != null) {
			for (JavaVariable var : vars) {
				programVariablesString += var.getName() + "; ";
			}
		}

		String globalConditionsString = "";
		if (conds != null) {
			for (Condition cond : conds) {
				if (!cond.getName().isEmpty()) {
					globalConditionsString += " & " + cond.getName();
				}
			}
		}

		IProject thisProject = FileUtil.getProject(uri);

		String firstString = first.getName();
		String secondString = Parser.getConditionFromCondition(second.getName());

		if (firstString == null || firstString.length() == 0) {
			firstString = "true";
		}
		if (secondString == null || secondString.length() == 0) {
			secondString = "true";
		}

		if(firstString.contains("modifiable(")) {
			firstString = firstString.substring(firstString.indexOf(';') + 1);
		}
		if(secondString.contains("modifiable(")) {
			secondString = secondString.substring(secondString.indexOf(';') + 1);
		}
		
		if (renaming != null) {
			globalConditionsString = useRenamingCondition(renaming, globalConditionsString);
			firstString = useRenamingCondition(renaming, firstString);
			secondString = useRenamingCondition(renaming, secondString);
		}

		String problem = "\\javaSource \"" + thisProject.getLocation() + "/\";" + "\\include \"helper.key\";"
				+ "\\programVariables {" + programVariablesString + javaClass + " self; Heap heapAtPre;}" + "\\problem {(" + firstString
				+ " " + globalConditionsString + ") -> {heapAtPre := heap} (" + secondString + ")}";
		
		problem = problem.replaceAll("this.", "self.");
		
		/*String problem2 = "\\javaSource \"" + thisProject.getLocation() + "/\";" + "\\include \"helper.key\";"
				+ "\\programVariables {" + programVariablesString + javaClass + " self; Heap heapAtPre;}" 
				+ "\\problem {(" + preString + " " + globalConditionsString + " & self.<created> = TRUE &" + javaClass.getMethodClass() + 
				"::exactInstance(self)=TRUE &  !self = null & self.<inv> & wellFormed(heap)) -> {heapAtPre := heap} (" 
				+ invariantString + ")}";*/
		
		
		Console.println(problem);
		String location = thisProject.getLocation() + "/src/prove" + uri.trimFileExtension().lastSegment();
		File keyFile = FileUtil.writeFile(problem, location, numberFile, override);
		return keyFile;
	}

	public static String proveUseWeakestPreWithKey(AbstractStatement statement, JavaVariables vars,
			GlobalConditions conds, Renaming renaming, URI uri, IProgressMonitor monitor) {
		File location = createProveUseWeakestPreWithKey(statement, vars, conds, renaming, uri, 0, true);
		Console.println("Verify Pre -> {Statement} Post");
		return createWPWithKey(location, monitor);
	}

	private static File createProveUseWeakestPreWithKey(AbstractStatement statement, JavaVariables vars,
			GlobalConditions conds, Renaming renaming, URI uri, int numberFile, boolean override) {
		String programVariablesString = "";
		if (vars != null) {
			for (JavaVariable var : vars.getVariables()) {
				programVariablesString += var.getName() + "; ";
			}
		}

		String globalConditionsString = "";
		if (conds != null) {
			for (Condition cond : conds.getConditions()) {
				if (!cond.getName().isEmpty()) {
					globalConditionsString += " & " + cond.getName();
				}
			}
		}

		IProject thisProject = FileUtil.getProject(uri);

		List<String> modifiableVariables = Parser.getUnmodifiedVars(
				Parser.getModifiedVarsFromCondition(statement.getPostCondition().getName()), vars.getVariables());
		String post = Parser.getConditionFromCondition(statement.getPostCondition().getName());
		String stat = statement.getName();

		if (post == null || post.length() == 0) {
			post = "true";
		}

		if (renaming != null) {
			globalConditionsString = useRenamingCondition(renaming, globalConditionsString);
			post = useRenamingCondition(renaming, post);
			stat = useRenamingStatement(renaming, stat);
		}

		String problem = "\\javaSource \"" + thisProject.getLocation() + "/\";" + "\\include \"helper.key\";"
				+ "\\programVariables {" + programVariablesString + "}" + "\\problem {\\<{" + stat + "}\\> (" + post
				+ ")}";

		String location = thisProject.getLocation() + "/features/" + uri.segment(uri.segmentCount()-3) + "/prove" + uri.trimFileExtension().lastSegment();
		File keyFile = FileUtil.writeFile(problem, location, numberFile, override);
		return keyFile;
	}

	public static String createWPWithKey(File location, IProgressMonitor monitor) {
		Proof proof = createKeyProof(location, monitor, false);
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

	private static Proof createKeyProof(File location, IProgressMonitor monitor, boolean inlining) {
		Proof proof = null;
		List<File> classPaths = null; // Optionally: Additional specifications
										// for API classes
		File bootClassPath = null; // Optionally: Different default
									// specifications for Java API
		List<File> includes = null; // Optionally: Additional includes to
									// consider
		try {
			// Ensure that Taclets are parsed
			if (!ProofSettings.isChoiceSettingInitialised()) {
				KeYEnvironment<?> env = KeYEnvironment.load(location, classPaths, bootClassPath, includes);
				env.dispose();
			}
			// Set Taclet options
			ChoiceSettings choiceSettings = ProofSettings.DEFAULT_SETTINGS.getChoiceSettings();
			HashMap<String, String> oldSettings = choiceSettings.getDefaultChoices();
			HashMap<String, String> newSettings = new HashMap<String, String>(oldSettings);
			newSettings.putAll(MiscTools.getDefaultTacletOptions());
			newSettings.put("runtimeExceptions", "runtimeExceptions:ban");
			choiceSettings.setDefaultChoices(newSettings);
			// Load source code
			KeYEnvironment<?> env = KeYEnvironment.load(location, classPaths, bootClassPath, includes);
			proof = env.getLoadedProof();
			// Set proof strategy options
			StrategyProperties sp = proof.getSettings().getStrategySettings().getActiveStrategyProperties();
			if(inlining)
				sp.setProperty(StrategyProperties.METHOD_OPTIONS_KEY, StrategyProperties.METHOD_EXPAND);
			else
				sp.setProperty(StrategyProperties.METHOD_OPTIONS_KEY, StrategyProperties.METHOD_CONTRACT);//METHOD_EXPAND
			sp.setProperty(StrategyProperties.LOOP_OPTIONS_KEY, StrategyProperties.LOOP_EXPAND);
			sp.setProperty(StrategyProperties.DEP_OPTIONS_KEY, StrategyProperties.DEP_ON);
			sp.setProperty(StrategyProperties.QUERY_OPTIONS_KEY, StrategyProperties.QUERY_RESTRICTED);//
			sp.setProperty(StrategyProperties.NON_LIN_ARITH_OPTIONS_KEY, StrategyProperties.NON_LIN_ARITH_DEF_OPS);
			sp.setProperty(StrategyProperties.STOPMODE_OPTIONS_KEY, StrategyProperties.STOPMODE_NONCLOSE);
			proof.getSettings().getStrategySettings().setActiveStrategyProperties(sp);
			// Make sure that the new options are used
			int maxSteps = Integer.MAX_VALUE;
			ProofSettings.DEFAULT_SETTINGS.getStrategySettings().setMaxSteps(maxSteps);
			ProofSettings.DEFAULT_SETTINGS.getStrategySettings().setActiveStrategyProperties(sp);
			proof.getSettings().getStrategySettings().setMaxSteps(maxSteps);
			proof.setActiveStrategy(proof.getServices().getProfile().getDefaultStrategyFactory().create(proof, sp));
			// Start auto mode
			Console.println("  Start proof: " + location.getName());
			if (monitor != null) {
				env.getUi().getProofControl().startAutoMode(proof);
				while (env.getUi().getProofControl().isInAutoMode()) {
					if (monitor.isCanceled()) {
						env.getUi().getProofControl().stopAndWaitAutoMode();
						Console.println("  Proof is canceled.");
					}
				}
			} else {
				env.getUi().getProofControl().startAndWaitForAutoMode(proof);
			}

			// Show proof result
			try {
				proof.saveToFile(location);
				
				//printStatistics(proof, inlining);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} catch (ProblemLoaderException e) {
			Console.println("  Exception at '" + e.getCause() + "'");
			e.printStackTrace();
		}
		return proof;
	}
	
	private static void printStatistics(Proof proof, boolean inlining) {
		Statistics s = proof.getStatistics();
		if(inlining)
			Console.println("Inlining");
		else
			Console.println("Contracting");
		Console.println("Statistics: \n\t nodes: " + s.nodes + "\n\t rule apps: " + s.totalRuleApps
				+ "\n\t time in Millis: " + s.timeInMillis );
	}
	
	public static void createKeyProofUserstudy(File location, int proofCounter) {
		File keyFile = null;
		List<File> classPaths = null; // Optionally: Additional specifications
										// for API classes
		File bootClassPath = null; // Optionally: Different default
									// specifications for Java API
		List<File> includes = null; // Optionally: Additional includes to
									// consider
		try {
			// Ensure that Taclets are parsed
			if (!ProofSettings.isChoiceSettingInitialised()) {
				KeYEnvironment<?> env = KeYEnvironment.load(location, classPaths, bootClassPath, includes);
				env.dispose();
			}
			// Set Taclet options
			ChoiceSettings choiceSettings = ProofSettings.DEFAULT_SETTINGS.getChoiceSettings();
			HashMap<String, String> oldSettings = choiceSettings.getDefaultChoices();
			HashMap<String, String> newSettings = new HashMap<String, String>(oldSettings);
			newSettings.putAll(MiscTools.getDefaultTacletOptions());
			newSettings.put("runtimeExceptions", "runtimeExceptions:ban");
			choiceSettings.setDefaultChoices(newSettings);
			// Load source code
			KeYEnvironment<?> env = KeYEnvironment.load(location, classPaths, bootClassPath, includes);
			// proof = env.getLoadedProof();
			try {
				// List all specifications of all types in the source location
				// (not classPaths and bootClassPath)
				final List<Contract> proofContracts = new LinkedList<Contract>();
				Set<KeYJavaType> kjts = env.getJavaInfo().getAllKeYJavaTypes();
				for (KeYJavaType type : kjts) {
					if (!KeYTypeUtil.isLibraryClass(type)) {
						ImmutableSet<IObserverFunction> targets = env.getSpecificationRepository().getContractTargets(type);
						for (IObserverFunction target : targets) {
							ImmutableSet<Contract> contracts = env.getSpecificationRepository().getContracts(type, target);
							for (Contract contract : contracts) {
								proofContracts.add(contract);
							}
						}
					}
				}
				// Perform proofs
				Contract contract = proofContracts.get(0);
				Proof proof = null;
				try {
					// Create proof
					proof = env.createProof(contract.createProofObl(env.getInitConfig(), contract));
					// Set proof strategy options
					StrategyProperties sp = proof.getSettings().getStrategySettings().getActiveStrategyProperties();
					sp.setProperty(StrategyProperties.METHOD_OPTIONS_KEY, StrategyProperties.METHOD_EXPAND);
					sp.setProperty(StrategyProperties.LOOP_OPTIONS_KEY, StrategyProperties.LOOP_INVARIANT);
					sp.setProperty(StrategyProperties.DEP_OPTIONS_KEY, StrategyProperties.DEP_ON);
					sp.setProperty(StrategyProperties.QUERY_OPTIONS_KEY, StrategyProperties.QUERY_RESTRICTED);// StrategyProperties.QUERY_ON
					sp.setProperty(StrategyProperties.NON_LIN_ARITH_OPTIONS_KEY, StrategyProperties.NON_LIN_ARITH_DEF_OPS);
					sp.setProperty(StrategyProperties.STOPMODE_OPTIONS_KEY, StrategyProperties.STOPMODE_DEFAULT);
					proof.getSettings().getStrategySettings().setActiveStrategyProperties(sp);
					// Make sure that the new options are used
					int maxSteps = 5000;
					ProofSettings.DEFAULT_SETTINGS.getStrategySettings().setMaxSteps(maxSteps);
					ProofSettings.DEFAULT_SETTINGS.getStrategySettings().setActiveStrategyProperties(sp);
					proof.getSettings().getStrategySettings().setMaxSteps(maxSteps);
					proof.setActiveStrategy(proof.getServices().getProfile().getDefaultStrategyFactory().create(proof, sp));
					// Start auto mode
//						MainWindow.getInstance().setVisible(true);
					env.getUi().getProofControl().startAndWaitForAutoMode(proof);
					// Show proof result
					Console.println("Proof is closed: " + proof.openGoals().isEmpty());
	                try {
	                	String locationWithoutFileEnding = location.toString().substring(0, location.toString().indexOf("."));
	                	keyFile = new File(locationWithoutFileEnding + ".proof");
	    				proof.saveToFile(keyFile);
	    				IWorkspace workspace = ResourcesPlugin.getWorkspace();    
	    				IPath iLocation = Path.fromOSString(keyFile.getAbsolutePath()); 
	    				IFile ifile = workspace.getRoot().getFileForLocation(iLocation);
	    				ifile.refreshLocal(0, null);
	    			} catch (IOException | CoreException e) {
	    				e.printStackTrace();
	    			}
				} catch (ProofInputException e) {
					Console.println(
							"Exception at '" + contract.getDisplayName() + "' of " + contract.getTarget() + ":");
					e.printStackTrace();
				} finally {
					if (proof != null) {
						proof.dispose(); // Ensure always that all instances
											// of Proof are disposed
					}
				}
			} finally {
				env.dispose(); // Ensure always that all instances of
								// KeYEnvironment are disposed
			}
			MainWindow.getInstance().loadProblem(keyFile);
			MainWindow.getInstance().setVisible(true);
		} catch (ProblemLoaderException e) {
			Console.println("Exception at '" + e.getCause() + "'");
			e.printStackTrace();
		}
	}
}
