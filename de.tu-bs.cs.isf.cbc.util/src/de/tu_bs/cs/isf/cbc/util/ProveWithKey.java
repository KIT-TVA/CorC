package de.tu_bs.cs.isf.cbc.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.google.common.collect.Lists;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionTechnique;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodClass;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.cbcmodel.Variant;
import de.uka.ilkd.key.proof.Goal;
import de.uka.ilkd.key.proof.Proof;

public class ProveWithKey {
	public static final String REGEX_ORIGINAL = "original";
	public static final String REGEX_RESULT = "\\\\result";

	AbstractStatement statement;
	private JavaVariables vars;
	private GlobalConditions conds;
	private Renaming renaming;
	private IProgressMonitor monitor;
	private String uri;
	private MethodClass javaClass;
	private IFileUtil fileHandler;
	
	public ProveWithKey(AbstractStatement statement, JavaVariables vars, GlobalConditions conds, Renaming renaming,
			IProgressMonitor monitor, String uri, MethodClass javaClass, IFileUtil fileHandler) {
		super();
		this.statement = statement;
		this.vars = vars;
		this.conds = conds;
		this.renaming = renaming;
		this.monitor = monitor;
		this.uri = uri;
		this.javaClass = javaClass;
		this.fileHandler = fileHandler;
	}
	
	public boolean proveStatementWithKey(boolean returnStatement, boolean inlining, int numberfile) {
			File location = createProveStatementWithKey(null, 0, true, "", "", inlining);
			Console.println("  Verify Pre -> {Statement} Post");
			return proveWithKey(location, inlining);
	}

	public boolean proveStatementWithKey(boolean returnStatement, boolean inlining, String variants, int numberfile, String callingMethod, String varM) {
		if (variants == null || variants.length() == 0) {
			File location = createProveStatementWithKey(null, 0, true, callingMethod, varM, inlining);
			Console.println("  Verify Pre -> {Statement} Post");
			return proveWithKey(location, inlining);
		} else {
			boolean proven = true;
			
				List<String> refinements = Lists.newArrayList(variants.split(","));
				File location = createProveStatementWithKey(refinements, numberfile, true, callingMethod, varM, returnStatement);
				Console.println("  Verify Pre -> {Statement} Post");

				if (!proveWithKey(location, inlining)) {
					proven = false;
				}
			return proven;
		}
	}

	public File createProveStatementWithKey(List<String> refinements, int numberFile,
			boolean override, String callingMethod, String varM, boolean returnStatement) {
		KeYFileContent content = new KeYFileContent();
		content.setLocation(fileHandler.getProjectLocation(uri));
		JavaVariable returnVariable = content.readVariables(vars);
		content.readGlobalConditions(conds);
		readPrePostModVars(refinements, returnVariable, callingMethod, content);
		content.rename(renaming);
		replaceOriginalInStatement(refinements, callingMethod, content, varM);
		content.replaceThisWithSelf();
		content.addSelf(javaClass);
		
		if(returnStatement) { //TODO replace with correct handling of return
			content.setStatement(";");
		} else {
			content.setStatement(statement.getName());
		}

		String problem = content.getKeYStatementContent();	

		problem = problem.replaceAll("static", "");
		problem = problem.replaceAll("return", ""); //TODO replace with correct handling of return
		
		String location = fileHandler.getLocationString(uri);
		File keyFile = fileHandler.writeFile(problem, location, numberFile, override);
		return keyFile;
	}
	
	public void readPrePostModVars(List<String> refinements, JavaVariable returnVariable, String callingMethod, KeYFileContent content) {
		CbCFormula formula = getCbCFormula(statement);
		String preFormula = Parser.getConditionFromCondition(formula.getStatement().getPreCondition().getName());
		String postFormula = Parser.getConditionFromCondition(formula.getStatement().getPostCondition().getName());
		String pre = Parser.getConditionFromCondition(statement.getPreCondition().getName());
		String post = Parser.getConditionFromCondition(statement.getPostCondition().getName());
		List<String> modifiables = Parser.getModifiedVarsFromCondition(statement.getPostCondition().getName());

		if (refinements != null && pre.equals(preFormula)) { //TODO composition only for pre post of formula. Extend to every case
			pre = composeContractForCbCDiagram(formula.getCompositionTechnique(), refinements, pre,
					Parser.KEYWORD_JML_PRE, returnVariable, callingMethod);
		}
		if (refinements != null && post.equals(postFormula)) {
			modifiables = composeModifiables(refinements, modifiables, formula.getCompositionTechnique(), true, callingMethod);
			post = composeContractForCbCDiagram(formula.getCompositionTechnique(), refinements, post,
					Parser.KEYWORD_JML_POST, returnVariable, callingMethod);
		}

		content.setPre(pre);
		content.setPost(post);
		List<String> unmodifiedVariables = Parser.getUnmodifiedVars(modifiables, vars.getVariables());//what if a glob var is in another diagram defined?
		unmodifiedVariables = unmodifiedVariables.stream().distinct().collect(Collectors.toList());
		content.addUnmodifiableVars(unmodifiedVariables);
		
		if (pre == null || pre.length() == 0) {
			content.setPre("true");
		}
		if (post == null || post.length() == 0) {
			content.setPost("true");
		}
	}
	
	public void replaceOriginalInStatement(List<String> refinements, String callingMethod, KeYFileContent content, String varM) {
		if (refinements != null && refinements.size() > 0 && content.statement.contains("original(")|| (content.statement.contains("(") && !content.statement.contains("\\."))) { //TODO check guard
			String[] splittedRefinement = refinements.get(0).split("\\.");
			if (Character.isLowerCase(splittedRefinement[0].charAt(0))) {
				content.statement = content.statement.replaceFirst("original", splittedRefinement[0] + ".generated_" + splittedRefinement[1]);
			} else {
				String className = generateComposedClass(refinements, callingMethod);
				content.statement = content.statement.replaceFirst("original", className + ".generated_" + splittedRefinement[1]);
				if (!varM.equals("")) { //TODO von Max
					content.statement = content.statement.replaceFirst(varM.toLowerCase(), className + ".generated_" + splittedRefinement[1]);
				}
			}
		} 
	}

	List<String> composeModifiables(List<String> refinements, List<String> modifiables,
			CompositionTechnique compTechnique, boolean includeFormulaModifiable, String callingMethod) {
		if (refinements != null && refinements.size() > 0) {
			for (String refinement : refinements) {
				String[] splittedRefinement = refinement.split("\\.");
				File classFile = fileHandler.getClassFile(getRefinementClass("Generated_" + splittedRefinement[0]));
				String methodName = splittedRefinement[1];
				if (!includeFormulaModifiable) {
					compTechnique = Parser.getCompositionTechniqueForMethod(classFile, methodName,
							Parser.KEYWORD_JML_MODIFIABLE, callingMethod, fileHandler);
					String modifiableOriginal = Parser.getContentFromJML(classFile, methodName,
							Parser.KEYWORD_JML_MODIFIABLE, fileHandler);
					List<String> modifiableList = new ArrayList<String>();
					if (!modifiableOriginal.equals("")) {
						modifiableList = Parser.getModifiedVarsFromCondition("modifiable(" + modifiableOriginal + ");");
					}
					modifiables = applyCompositionTechniqueOnModifiables(modifiableList, modifiableOriginal,
							compTechnique);
					includeFormulaModifiable = true;
				} else {
					String modifiableOriginal = Parser.getContentFromJML(classFile, methodName,
							Parser.KEYWORD_JML_MODIFIABLE, fileHandler);
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

	private String composeContractForCbCDiagram(CompositionTechnique compositionTechnique,
			List<String> refinements, String condition, String keyword, JavaVariable returnVariable, String callingMethod) {
		String composedCondition = condition;
		CompositionTechnique compTechnique = compositionTechnique;		
		for (int i = 0; i < refinements.size(); i++) {
			String[] splittedRefinement = refinements.get(i).split("\\.");
			File classFile = fileHandler.getClassFile(getRefinementClass("Generated_" + splittedRefinement[0]));
			String methodName = splittedRefinement[1];
			if (i != 0) {
				splittedRefinement = refinements.get(i-1).split("\\.");
				methodName = splittedRefinement[1];
				compTechnique = Parser.getCompositionTechniqueForMethod(classFile, methodName, keyword, callingMethod, fileHandler);
			}
			splittedRefinement = refinements.get(i).split("\\.");
			methodName = splittedRefinement[1];
			if (classFile != null) {
				String conditionOriginal = Parser.getContentFromJML(classFile, methodName, keyword, fileHandler);
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
	
	public String generateComposedClass(List<String> refinements, String callingMethod) {
		String[] splittedRefinement = refinements.get(0).split("\\.");
		String className = "Original" + splittedRefinement[0];
		String composedClassName = "Generated_" + splittedRefinement[0];
		String methodName = splittedRefinement[1];
		File file = fileHandler.getClassFile(className);
		if (file == null) {
			className = "Generated_" + splittedRefinement[0];
			file = fileHandler.getClassFile(className);
		}
		String methodStub = Parser.getMethodStubFromFile(className, methodName, fileHandler);
		methodStub = methodStub.replaceAll("\n", "");
		boolean alreadyGenerated = false;
		if (!methodStub.contains("generated_")) {
			methodStub = methodStub.replaceFirst("(\\w*)\\(", "generated_$1(");
		} else {
			alreadyGenerated = true;
		}
		String methodPreCondition = composeContractForCalledOriginal(refinements, Parser.KEYWORD_JML_PRE, callingMethod);
		String methodPostCondition = composeContractForCalledOriginal(refinements,
				Parser.KEYWORD_JML_POST, callingMethod);
		List<String> assignables = composeModifiables(refinements, new ArrayList<String>(), null, false, callingMethod);
		String assignableString = "";
		if (!assignables.isEmpty()) {
			assignableString = "@ assignable " + String.join(",", assignables) + ";";
		}
		if (vars != null) {
			for (JavaVariable actVar: vars.getVariables()) {
				if ((actVar.getKind().getName() != "PARAM")) {
					String splitName[] = actVar.getName().split(" ");
					assignableString = assignableString.replaceAll("," + splitName[splitName.length-1],"");
					assignableString = assignableString.replaceAll(splitName[splitName.length-1] + ";",";");
					assignableString = assignableString.replaceAll(splitName[splitName.length-1] + ",","");
				}
			}
		}

		List<String> lines = fileHandler.readFileInList(file.getAbsolutePath());
		String contentOriginal = "";

		String content = "";
		Collections.reverse(lines);
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i) + "";
			if (alreadyGenerated && line.contains("generated_" + methodName + "(")) {
				int index = lines.indexOf(line);
				while (!lines.get(index+1).contains("/*@")) {
					lines.remove(index+1);
				}
				content = "\n    @ public normal_behavior\n    @ requires " + methodPreCondition
						+ ";\n    @ ensures " + methodPostCondition + ";\n    " + assignableString + "\n" + "    @*/\n"
						+ methodStub + "\n" + content;
				contentOriginal = line + "\n" + contentOriginal;
			} else if (!alreadyGenerated && line.contains(" class ") && className == composedClassName) {
				content = line + "\n\n    /*@\n    @ public normal_behavior\n    @ requires " + methodPreCondition
						+ ";\n    @ ensures " + methodPostCondition + ";\n    " + assignableString + "\n" + "    @*/\n"
						+ methodStub + "  }\n" + content;
			} else if (!alreadyGenerated && line.contains(" class ") && className != composedClassName) {
				content = line.replaceFirst(className, composedClassName)
						+ "\n\n    /*@\n    @ public normal_behavior\n    @ requires " + methodPreCondition + ";\n    @ ensures "
						+ methodPostCondition + ";\n    " + assignableString + "\n" + "    @*/\n" + methodStub + "  }\n" + content;	
			} else {
				content = line + "\n" + content;
				contentOriginal = line + "\n" + contentOriginal;
			}				
		}
		
		return fileHandler.generateComposedClass(uri, composedClassName, className, content, contentOriginal);
	}

	String composeContractForCalledOriginal(List<String> refinements, String keyword, String callingMethod) {
		String[] splittedRefinement = refinements.get(0).split("\\.");
		File classFile = fileHandler.getClassFile("Generated_" + splittedRefinement[0]);
		String methodName = splittedRefinement[1];
		String composedCondition = Parser.getContentFromJML(classFile, methodName, keyword, fileHandler);
		CompositionTechnique compositionTechnique = Parser.getCompositionTechniqueForMethod(classFile, methodName,
				keyword, callingMethod, fileHandler); 
		for (int i = 1; i < refinements.size(); i++) {
			File nextClassFile = fileHandler.getClassFile("Generated_" + splittedRefinement[0]);
			if (i != 1) {
				splittedRefinement = refinements.get(i-1).split("\\.");
				methodName = splittedRefinement[1];
				compositionTechnique = Parser.getCompositionTechniqueForMethod(nextClassFile, methodName, keyword, callingMethod, fileHandler);//old version statt methodName
			}
			splittedRefinement = refinements.get(i).split("\\.");
			String nextMethodName = splittedRefinement[1];
			if (classFile != null) {
				String conditionOriginal = Parser.getContentFromJML(nextClassFile, nextMethodName, keyword, fileHandler);
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
	
	private static EObject getParentOfStatement(AbstractStatement statement) {
		EObject parent = null;
		if (statement.getParent() != null) {
			parent = statement.getParent().eContainer();
		} else if (statement.eContainer() != null) {
			parent = statement.eContainer();
		}
		return parent;
	}

	private String getRefinementClass(String string) {
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

	public boolean proveWithKey(File location, boolean inlining) {
		return proveWithKey(location, monitor, inlining);	
	}
	
	public static boolean proveWithKey(File location, IProgressMonitor monitor, boolean inlining) {
		Proof proof = KeYInteraction.startKeyProof(location, null, inlining);
		if (proof != null) {
			// Show proof result
			boolean closed = proof.openGoals().isEmpty();
			Console.println("Proof is closed: " + closed);
			return closed;
		}
		return false;
	}

	public boolean proveCImpliesCWithKey(Condition preCondition, Condition postCondition) {
		File location = createProveCImpliesCWithKey(preCondition.getName(), postCondition.getName(), 0, true);
		Console.println("  Verify Pre -> Invariant");
		return proveWithKey(location, false);
	}

	public File createProveCImpliesCWithKey(String preCondition, String postCondition, int numberFile, boolean override) {
		KeYFileContent content = new KeYFileContent();
		content.setLocation(fileHandler.getProjectLocation(uri));
		content.readVariables(vars);
		content.readGlobalConditions(conds);

		content.setPreFromCondition(preCondition);
		content.setPostFromCondition(postCondition);
		content.rename(renaming);
		content.replaceThisWithSelf();
		content.addSelf(javaClass);

        //String location = fileHandler.getProjectLocation(uri) + uri.segment(uri.segmentCount()-3) + "/prove" + uri.trimFileExtension().lastSegment();
		String location = fileHandler.getLocationString(uri);
		File keyFile = fileHandler.writeFile(content.getKeYCImpliesCContent(), location, numberFile, override);
		return keyFile;
	}

	public boolean provePostRepetitionWithKey(Condition invariant, Condition guard, Condition postCondition) {
		String pre = invariant.getName() + " & !(" + guard.getName() + ")";
		File location = createProveCImpliesCWithKey(pre, postCondition.getName(), 0, true);
		Console.println("  Verify (Invariant & !Guard) -> Post");
		return proveWithKey(location, false);
	}

	public boolean provePreSelWithKey(EList<Condition> guards, Condition preCondition) {
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
		File location = createProveCImpliesCWithKey(preCondition.getName(), guardString, 0, true);
		Console.println("  Verify Pre -> GvGvG...");
		return proveWithKey(location, false);
	}

	public boolean proveVariantWithKey(String code, Condition invariant, Condition guard, Variant variant) {
		File location = createProveVariantWithKey(code, invariant, guard, variant, 0, true);
		Console.println("Verify Pre -> {WhileStatement} (variant<variant0 & variant >= 0)");
		return proveWithKey(location, false);
	}

	public File createProveVariantWithKey(String code, Condition invariant, Condition guard, Variant variant, int numberFile, boolean override) {
		KeYFileContent content = new KeYFileContent();
		content.setLocation(fileHandler.getProjectLocation(uri));
		content.readVariables(vars);
		content.addVariable("int variant");
		content.readGlobalConditions(conds);
		content.setPreFromCondition(invariant.getName() + " & " +  guard.getName());
		content.setVariantPost(variant.getName());
		content.setStatement(code);
		content.rename(renaming);
		content.replaceThisWithSelf();
		content.addSelf(javaClass);
		
		String location = fileHandler.getLocationString(uri);
		File keyFile = fileHandler.writeFile(content.getKeYStatementContent(), location, numberFile, override);
		return keyFile;
	}

	public String proveUseWeakestPreWithKey() {
		File location = createProveUseWeakestPreWithKey(0, true);
		Console.println("Verify Pre -> {Statement} Post");
		return createWPWithKey(location);
	}

	private File createProveUseWeakestPreWithKey(int numberFile, boolean override) {
		KeYFileContent content = new KeYFileContent();
		content.setLocation(fileHandler.getProjectLocation(uri));
		content.readVariables(vars);
		content.readGlobalConditions(conds);
		content.setStatement(statement.getName());
		content.setPostFromCondition(statement.getPostCondition().getName());

		content.rename(renaming);
		
		String location = fileHandler.getLocationString(uri);
		File keyFile = fileHandler.writeFile(content.getKeYWPContent(), location, numberFile, override);
		return keyFile;
	}

	public String createWPWithKey(File location) {
		Proof proof = KeYInteraction.startKeyProof(location, monitor, false);
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
}
