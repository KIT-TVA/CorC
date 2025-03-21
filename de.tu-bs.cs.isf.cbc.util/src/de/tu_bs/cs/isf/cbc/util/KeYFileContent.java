package de.tu_bs.cs.isf.cbc.util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

import de.tu_bs.cs.isf.cbc.cbcclass.Field;
import de.tu_bs.cs.isf.cbc.cbcclass.Method;
import de.tu_bs.cs.isf.cbc.cbcclass.ModelClass;
import de.tu_bs.cs.isf.cbc.cbcclass.Parameter;
import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
//import de.tu_bs.cs.isf.cbc.cbcmodel.BaseVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.Rename;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.cbcmodel.VariableKind;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.ReturnStatementImpl;

public class KeYFileContent {
	public static final Pattern REGEX_THIS_KEYWORD = Pattern.compile("\\b(this)\\b");// ("(?<![a-zA-Z0-9_])(this)(?![a-zA-Z0-9_])");
	public static final String OLD_VARS_SUFFIX = "_oldVal";
	public static final Pattern REGEX_RESULT_KEYWORD = Pattern.compile("(\\\\result)");

	private final String helper = "helper.key";
	private String location = "";
	private String srcFolder = "";
	private String statement = "";

	private Renaming renaming;
	private String self;

	private JavaVariables programVariables;
	private List<Condition> globalConditions;
	private List<Condition> preConditions;
	private List<Condition> postConditions;
	// TODO: ProveWithKey passes this to us as String. It would be better to keep it
	// as Variable.
	private List<String> unmodifiableVars;
	private String variant;

	private Map<String, List<Field>> methodClassVarMap = null;
	private Map<String, String> returnTypeMap = null;

	private List<String> nameOfLocalVars;
	private List<String> varsForOldReplacement;

	List<String> assignments = new ArrayList<>();
	boolean abstractProof = false;

	public KeYFileContent(String location) {
		this.location = location;

		programVariables = CbcmodelFactory.eINSTANCE.createJavaVariables();
		globalConditions = new ArrayList<>();
		preConditions = new ArrayList<>();
		postConditions = new ArrayList<>();

		unmodifiableVars = new ArrayList<>();

		methodClassVarMap = initMethodClassVarMap();
		returnTypeMap = initReturnTypeMap();

		nameOfLocalVars = new ArrayList<>();
		varsForOldReplacement = new ArrayList<>();
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setSrcFolder(String srcFolder) {
		this.srcFolder = srcFolder;
	}

	public void addPreCondition(Condition pre) {
		this.preConditions.add(pre);
	}

	public void addPostCondition(Condition post) {
		this.postConditions.add(post);
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	public void setAbstractProof(boolean isAbstract) {
		this.abstractProof = isAbstract;
	}

	public String getStatement() {
		return statement;
	}

	public void addVariable(String var) {
		JavaVariable variable = CbcmodelFactory.eINSTANCE.createJavaVariable();
		variable.setName(var);
		programVariables.getVariables().add(variable);
	}

	public JavaVariable readVariables(JavaVariables vars) {
		JavaVariable returnVariable = null;
		if (vars != null) {
			// TODO: For some reason we get a ConcurrentModificationException when we don't
			// copy our list first
			List<JavaVariable> variables = new ArrayList<>();
			variables.addAll(vars.getVariables());
			for (JavaVariable var : variables) {
				String[] NameOfVar = var.getName().split(" ");
				nameOfLocalVars.add(NameOfVar[NameOfVar.length - 1]);

				JavaVariable variable = CbcmodelFactory.eINSTANCE.createJavaVariable();
				variable.setKind(var.getKind());
				variable.setName(var.getName());

				if (var.getKind() == VariableKind.RETURN || var.getKind() == VariableKind.RETURNPARAM) {
					returnVariable = variable;
				}
				programVariables.getVariables().add(variable);
			}
			/*
			 * int originalSize = vars.getParams().size(); for (int i = 0; i < originalSize;
			 * i++) { Parameter param = vars.getParams().get(0); if
			 * (param.getName().equals("ret")) { returnVariable =
			 * CbcmodelFactory.eINSTANCE.createJavaVariable();
			 * returnVariable.setKind(VariableKind.RETURNPARAM);
			 * returnVariable.setName(param.getType() + " " + param.getName()); }
			 * programVariables.getParams().add(param); }
			 */
			for (Parameter param : vars.getParams()) {
				if (param.getName().equals("ret")) {
					returnVariable = CbcmodelFactory.eINSTANCE.createJavaVariable();
					returnVariable.setKind(VariableKind.RETURNPARAM);
					returnVariable.setName(param.getType() + " " + param.getName());
				}
				programVariables.getParams().add(EcoreUtil.copy(param));
			}
			for (Field field : vars.getFields()) {
				programVariables.getFields().add(EcoreUtil.copy(field));
			}

		}
		return returnVariable;
	}

	public void readGlobalConditions(GlobalConditions conds) {
		if (conds != null) {
			// We have to copy GlobalConditions so that the diagram won't be changed
			for (int i = 0; i < conds.getConditions().size(); i++) {
				if (!conds.getConditions().get(i).getName().isEmpty()) {
					Condition cond = CbcmodelFactory.eINSTANCE.createCondition();
					cond.setName(conds.getConditions().get(i).getName());
					cond.getModifiables().addAll(conds.getConditions().get(i).getModifiables());
					globalConditions.add(cond);
				}
			}
			// for (Condition cond : conds.getConditions()) {
			// if (!cond.getName().isEmpty()) {
			// globalConditions.add(cond);
			// }
			// }
		}
	}

	public void readInvariants(List<Condition> invariants) {
		for (Condition c : invariants) {
			preConditions.add(c);
			postConditions.add(c);
		}
	}

	public void setPreFromCondition(Condition cond) {
		if (cond != null) {
			preConditions.add(cond);
		}
	}

	@Deprecated
	public void setPreFromCondition(String cond) {
		cond = Parser.getConditionFromCondition(cond);
		Condition condition = CbcmodelFactory.eINSTANCE.createCondition();
		condition.setName(cond);

		setPreFromCondition(condition);
	}

	@Deprecated
	public void setPostFromCondition(String cond) {
		cond = Parser.getConditionFromCondition(cond);
		Condition condition = CbcmodelFactory.eINSTANCE.createCondition();
		condition.setName(cond);

		setPostFromCondition(condition);
	}

	public void setPostFromCondition(Condition cond) {
		if (cond != null) {
			postConditions.add(cond);
		}
	}

	public void addUnmodifiableVars(List<String> unmodifiedVariables) {
		for (String var : unmodifiedVariables) {
			this.unmodifiableVars.add(var);
		}
	}

	public void addSelf(CbCFormula formula) {
		if (formula != null && formula.getClassName() != null && !formula.getClassName().isBlank()) {
			String className = "";
			if (formula.getMethodObj() != null && formula.getMethodObj() != null
					&& formula.getMethodObj().getParentClass() != null
					&& formula.getMethodObj().getParentClass().getPackage() != null
					&& !formula.getMethodObj().getParentClass().getPackage().isBlank()) {
				className += formula.getMethodObj().getParentClass().getPackage() + ".";
			}
			className += formula.getClassName();
			self = className + " self";
		}
	}

	public void rename(Renaming renaming) {
		this.renaming = renaming;
	}

	public void handleReturn(AbstractStatement retStatement, JavaVariable returnVariable, CbCFormula formula) {
		if (retStatement.getClass().equals(ReturnStatementImpl.class)) {
			if (returnVariable != null) {
				statement = removeStaticNonNull(returnVariable.getName()).split(" ")[1] + " = "
						+ retStatement.getName();
				for (Condition c : postConditions) {
					c.getName().replaceAll(REGEX_RESULT_KEYWORD.pattern(),
							returnVariable.getName().substring(returnVariable.getName().indexOf(" ")));
				}
			} else {
				// Get Return Type of Variable
				String methodName = formula.getMethodName();
				String returnTypeOfMethod = returnTypeMap.get(methodName);
				if (returnTypeOfMethod == null) {
					Console.println("No diagram was found that implements method '" + methodName + "'!");
				} else {
					String returnVariableDeclaration = returnTypeOfMethod + " result_" + methodName;
					JavaVariable var = CbcmodelFactory.eINSTANCE.createJavaVariable();
					var.setName(returnVariableDeclaration);
					var.setKind(VariableKind.RETURNPARAM);
					programVariables.getVariables().add(var);
					statement = "result_" + methodName + " = "
							+ retStatement.getName().replaceAll(REGEX_THIS_KEYWORD.pattern(), "self");
					// Replace result keyword in post.
					for (Condition c : postConditions) {
						c.getName().replaceAll(REGEX_RESULT_KEYWORD.pattern(), "result_" + methodName);
					}
				}
			}
		}
	}

	public String getKeYStatementContent() {
		return getKeYContent(true);
	}

	public String getKeYCImpliesCContent() {
		return getKeYContent(false);
	}

	public String getKeYContent(boolean withStatement) {
		replaceThisForSelfInStatement();
		addSelfForFields();

		Set<String> oldKeywords = extractOldKeywordVariables();
		Map<String, OldReplacement> oldReplacements = addOldVariables(self, oldKeywords);

		StringBuilder builder = new StringBuilder();
		builder.append(getKeyHeader(oldReplacements));
		builder.append(getKeyProblem(oldReplacements));
		if (withStatement)
			builder.append(" \\<{" + renameStatement(statement) + "}\\>");

		builder.append(
				" (" + KeYFunctionReplacer.getInstance().replaceIn(getPostConditionsString(oldReplacements)) + ")}");
		return builder.toString();
	}

	private void replaceThisForSelfInStatement() {
		statement = statement.replaceAll("\\b(this.)\\b", "self.");
	}

	private void addSelfForFields() {
		for (Field field : programVariables.getFields()) {
			if (!nameOfLocalVars.contains(field.getName()) /* && !field.isIsStatic() */) {
				Pattern pattern = Pattern.compile("(?<!\\.)\\b" + field.getName() + "\\b");
				statement = statement.replaceAll(pattern.pattern(), "self." + field.getName());
				preConditions
						.forEach(c -> c.setName(c.getName().replaceAll(pattern.pattern(), "self." + field.getName())));
				postConditions
						.forEach(c -> c.setName(c.getName().replaceAll(pattern.pattern(), "self." + field.getName())));
				globalConditions
						.forEach(c -> c.setName(c.getName().replaceAll(pattern.pattern(), "self." + field.getName())));
				unmodifiableVars.forEach(s -> s.replaceAll(pattern.pattern(), "self." + field.getName()));
				if (variant != null) {
					variant = variant.replaceAll(pattern.pattern(), "self." + field.getName());
				}
			}
		}
	}

	private String getKeyHeader(Map<String, OldReplacement> oldReplacements) {
		return MessageFormat.format(
				"\\javaSource \"{0}\";\n" + "\\include \"{1}\";\n" + "\\programVariables '{'\n" + "{2}"
						+ "Heap heapAtPre;\n" + "'}'",
				location + srcFolder, helper, getProgramVariablesString(oldReplacements));
	}

	private String getKeyProblem(Map<String, OldReplacement> oldReplacements) {
		return MessageFormat.format("\\problem '{'({0}) -> '{'" + "{1}" + "'}'",
				KeYFunctionReplacer.getInstance().replaceIn(getPreConditionsString(oldReplacements)),
				getAssignmentString(oldReplacements));
	}

	private String getProgramVariablesString(Map<String, OldReplacement> oldReplacements) {
		StringBuilder builder = new StringBuilder();
		for (JavaVariable var : programVariables.getVariables()) {
			if (!builder.toString().contains(var.getName())) {
				builder.append(removeStaticNonNull(var.getName()) + ";\n");
			}
		}

		for (Parameter param : programVariables.getParams()) {
			if (!builder.toString().contains(param.getType() + " " + param.getName())) {
				builder.append(removeStaticNonNull(param.getType() + " " + param.getName()) + ";\n");
			}
		}

		for (String unmodifiableVar : getUnmodifiableProgramVars()) {
			if (!builder.toString().contains(unmodifiableVar)) {
				builder.append(removeStaticNonNull(unmodifiableVar) + ";\n");
			}
		}

		for (String var : varsForOldReplacement) {
			builder.append(removeStaticNonNull(var) + ";\n");
		}

		// TODO: This code is originally from handleOld from pp branch but adjusted to
		// fit new structure. This might not work as intented.
		assignments = new ArrayList<String>();
		if (abstractProof) {
			for (Parameter p : programVariables.getParams()) {
				if (!builder.toString().contains(p.getName() + OLD_VARS_SUFFIX)) {
					builder.append(p.getType() + " " + p.getName() + OLD_VARS_SUFFIX + ";\n");
					assignments.add(p.getName() + OLD_VARS_SUFFIX + " := " + p.getName());
				}
			}
			for (Field f : programVariables.getFields()) {
				if (!builder.toString().contains("self." + f.getName() + OLD_VARS_SUFFIX)) {
					builder.append(f.getType() + " " + f.getName() + OLD_VARS_SUFFIX + ";\n");
					assignments.add(f.getName() + OLD_VARS_SUFFIX + " := " + "self." + f.getName());
				}
			}
		}

		if (self != null && !self.isBlank()) {
			builder.append(self + ";\n");
		}
		return builder.toString();
	}

	private String getPreConditionsString(Map<String, OldReplacement> oldReplacements) {
		List<String> preConditions = new ArrayList<>();
		preConditions.addAll(this.preConditions.stream().map(Condition::getName).toList());
		preConditions.add(getGlobalConditionsString(oldReplacements));
		preConditions.add(getConditionObjectsCreatedString(oldReplacements));
		if (self != null) {
			preConditions.add(getSelfConditionsString());
		}
		preConditions.add("wellFormed(heap)");
		preConditions.removeIf(v -> v.equals(""));

		String result = String.join(" & ", preConditions);
		result = replaceThisWithSelf(result);
		result = renameCondition(result);

		return replaceOldKeyword(result, oldReplacements);
	}

	private String replaceOldKeyword(String condition, Map<String, OldReplacement> replacements) {
		for (String key : replacements.keySet()) {
			String varNameOnly = key.substring(key.lastIndexOf(".") + 1);
			varNameOnly = varNameOnly.replaceAll("\\[.*\\]", "");
			if (varNameOnly.contains("("))
				varNameOnly = varNameOnly.substring(0, varNameOnly.indexOf("("));

			condition = condition.replace("\\old(" + replacements.get(key).var + ")", varNameOnly);
		}
		if (condition.contains("\\old")) {
			Console.println("Unsupported usage of \\old keyword in condition: '" + condition + "'");
		}
		return condition;
	}

	private String replaceThisWithSelf(String string) {
		return string.replaceAll(REGEX_THIS_KEYWORD.pattern(), "self");
	}

	private String renameCondition(String condition) {
		String result = condition;

		if (this.renaming != null) {
			for (Rename rename : this.renaming.getRename()) {
				if (rename.getType().equalsIgnoreCase("boolean")) {
					result = result.replaceAll(rename.getNewName(), "TRUE=" + rename.getFunction());
				} else {
					result = result.replaceAll(rename.getNewName(), rename.getFunction());
				}
			}
		}
		return result;
	}

	private String getPostConditionsString(Map<String, OldReplacement> oldReplacements) {
		List<String> postConditions = new ArrayList<>();
		postConditions.addAll(this.postConditions.stream().map(Condition::getName).toList());
		postConditions.addAll(getUnmodifiablePostConditions());
		preConditions.removeIf(v -> v.getName().equals(""));

		String result = String.join(" & ", postConditions);
		result = replaceThisWithSelf(result);
		result = renameCondition(result);

		return replaceOldKeyword(result, oldReplacements);
	}

	private String getGlobalConditionsString(Map<String, OldReplacement> oldReplacements) {
		List<String> conditions = new ArrayList<>();

		for (Condition cond : globalConditions) {
			if (!cond.getName().isEmpty()) {
				cond.setName(replaceThisWithSelf(cond.getName()));
				cond.setName(renameCondition(cond.getName()));
				if (isNonNull(cond.getName())) { // non-null property added here TODO: up to change
					String[] conditionSplitted = cond.getName().split(" ");
					String varDataType = conditionSplitted[0];
					String varName = conditionSplitted[1];
					if (!isSimpleType(varDataType)) {
						conditions.add(varDataType + "::exactInstance(" + varName + ") = TRUE");
						conditions.add(varName + ".<created> = TRUE");
						conditions.add(varName + " != null");
					}
				} else {
					conditions.add(cond.getName());
				}
			}
		}
		return String.join(" & ", conditions);
	}

	private boolean isSimpleType(String type) {
		return type.matches("^(void|byte|short|int|double|char|long|float|boolean)$");
	}

	private String getConditionObjectsCreatedString(Map<String, OldReplacement> oldReplacements) {
		List<String> conditions = new ArrayList<>();
		for (JavaVariable var : programVariables.getVariables()) {
			String type = getTypeFromVar(var.getName());
			String name = getNameFromVar(var.getName());

			if (var.getKind() == VariableKind.PARAM && isNonNull(var)) {
				addNonNullCondition(conditions, type, name);
			} else if (isArray(var)) {
				conditions.add(name + ".<created> = TRUE");
			}
		}
		for (Parameter param : programVariables.getParams()) {
			if (isArray(param)) {
				conditions.add(removeStaticNonNull(param.getName()) + ".<created> = TRUE");
			}
			if (!param.getName().equals("ret") && isNonNull(param)) {
				addNonNullCondition(conditions, removeStaticNonNull(param.getType()),
						removeStaticNonNull(param.getName()));
			}
		}
		for (Field field : programVariables.getFields()) {
			addNonNullCondition(conditions, field.getType(), "self." + field.getName());
		}

		for (String unmodifiableVar : unmodifiableVars) {
			if (isArray(unmodifiableVar)) {
				String varName = getNameFromVar(unmodifiableVar);

				conditions.add(varName + "_old.<created> = TRUE");
			}
		}

		for (String var : oldReplacements.keySet()) {
			if (isArray(var)) {
				conditions.add(removeStaticNonNull(var) + oldReplacements.get(var).index + OLD_VARS_SUFFIX
						+ ".<created>=TRUE");
			}
		}
		return String.join(" & ", conditions);
	}

	private void addNonNullCondition(List<String> conditions, String type, String name) {
		if (!isSimpleType(type)) {
			conditions.add(type + "::exactInstance(" + name + ") = TRUE");
			conditions.add(name + ".<created> = TRUE");
			conditions.add(name + "!= null");
		}
	}

	private String getSelfConditionsString() {
		List<String> selfcond = new ArrayList<>();
		selfcond.add("self.<created>=TRUE & ");
		selfcond.add(self.replace(" self", ""));
		selfcond.add("::exactInstance(self)=TRUE & !self = null & self.<inv>");

		return String.join("", selfcond);
	}

	private String getAssignmentString(Map<String, OldReplacement> oldReplacements) {
		assignments.add("heapAtPre := heap");
		assignments.addAll(getUnmodifiableAssignments());
		assignments.addAll(getOldAssignments(oldReplacements));
		if (variant != null)
			assignments.add("variant := " + variant);
		assignments.removeIf(v -> v.equals(""));

		return String.join(" || ", assignments);
	}

	private List<String> getUnmodifiableAssignments() {
		List<String> assignments = new ArrayList<>();
		for (String var : unmodifiableVars) {
			String varName = var.substring(var.lastIndexOf(" ") + 1);
			varName = removeStaticNonNull(varName);
			assignments.add(varName + "_old := " + varName);
		}
		return assignments;
	}

	private List<String> getOldAssignments(Map<String, OldReplacement> oldReplacements) {
		List<String> assignments = new ArrayList<>();
		for (String var : oldReplacements.keySet()) {
			if (!getPreConditionsString(oldReplacements).contains(var)) {
				assignments.add(var + " := " + oldReplacements.get(var).getVar());

			}
		}
		return assignments;
	}

	private List<String> getUnmodifiableProgramVars() {
		List<String> vars = new ArrayList<>();
		for (String var : unmodifiableVars) {
			vars.add(removeStaticNonNull(var) + "_old");
		}
		return vars;
	}

	private List<String> getUnmodifiablePostConditions() {
		List<String> conds = new ArrayList<>();
		for (String var : unmodifiableVars) {
			String varName = var.substring(var.lastIndexOf(" ") + 1);
			varName = removeStaticNonNull(varName);
			conds.add(MessageFormat.format("{0} = {1}_old", varName, varName));
		}
		return conds;
	}

	public void setVariantPost(String variant) {
		this.variant = variant;
		setPostFromCondition("(" + variant + ") <variant & " + variant + ">=0");
	}

	private String renameStatement(String statement) {
		String result = statement;

		if (this.renaming != null) {
			for (Rename rename : renaming.getRename()) {
				result = result.replaceAll(rename.getNewName(), rename.getFunction());
			}
		}
		return result;
	}

	private Set<String> extractOldKeywordVariables() {
		// Clear the list of replacements
		Set<String> oldKeywords = new HashSet<>();
		ArrayList<Integer> beginIndizes = new ArrayList<>();
		ArrayList<Integer> endIndizes = new ArrayList<>();
		int openParenthesisCounter = 0;
		/*
		 * Iterate over current modified array access. Count the start and end indizes
		 * of first-level parenthesis.
		 */
		String conditions = Stream.of(preConditions, postConditions, globalConditions).flatMap(List::stream)
				.map(Condition::getName).collect(Collectors.joining(" & "));
		// All fields and params have to be added to create an old variable due to
		// partial proof for variable predicates
		if (programVariables != null) {
			for (Parameter p : programVariables.getParams()) {
				oldKeywords.add(p.getName());
			}
			for (Field f : programVariables.getFields()) {
				oldKeywords.add("self." + f.getName());
			}
		}

		String currentOldMatch = conditions;
		if (currentOldMatch.contains("\\old(")) {
			for (int i = currentOldMatch.indexOf("\\old"); i < currentOldMatch.length(); i++) {
				if (currentOldMatch.charAt(i) == '(') {
					if (openParenthesisCounter == 0) {
						beginIndizes.add(i + 1);
					}
					openParenthesisCounter++;
				}
				if (currentOldMatch.charAt(i) == ')') {
					openParenthesisCounter--;
					if (openParenthesisCounter == 0) {
						endIndizes.add(i);
						if (currentOldMatch.substring(i + 1).contains("\\old")) {
							int newIndex = currentOldMatch.indexOf("\\old", i);
							if (newIndex > 0)
								i = newIndex;
						} else {
							break;
						}
					}
				}
			}
			for (int i1 = 0; i1 < beginIndizes.size(); i1++) {
				String content = currentOldMatch.substring(beginIndizes.get(i1), endIndizes.get(i1));
				if (!content.isEmpty() && !oldKeywords.contains(content)) {
					oldKeywords.add(content);
				}
			}
		}
		return oldKeywords;
	}

	private Map<String, OldReplacement> addOldVariables(String self, Set<String> oldKeywords) {
		Map<String, OldReplacement> newReplacements = new HashMap<>();
		// Add new old variables to variable List
		int counterForVarNaming = 0;

		for (String varUsedInOldContext : oldKeywords) {
			counterForVarNaming++;
			// Get variable name with variable kind
			String var = "";
			String lastVarUsedInOldContext = varUsedInOldContext.substring(varUsedInOldContext.lastIndexOf(".") + 1);
			// Replace brackets
			lastVarUsedInOldContext = lastVarUsedInOldContext.replaceAll("\\[.*\\]", "");
			// This split only works with no method calls that have "." as parameter
			// arguments.
			String[] splittedReplacementVar = varUsedInOldContext.split("\\.");
			boolean isFunction = lastVarUsedInOldContext.contains("(");
			if (isFunction) {
				// Check all diagram method signatures for return value of said object.
				// Get return type of function Call
				// If no function can be found give error message so developer
				// can modify the condition.
				String functionName = splittedReplacementVar[splittedReplacementVar.length - 1];
				functionName = functionName.substring(0, functionName.indexOf("("));
				boolean found = false;
				String signatureOfFunction = "";
				for (String sig : this.returnTypeMap.keySet()) {
					if (sig.equals(functionName)) {
						found = true;
						signatureOfFunction = sig;
						break;
					}
				}
				if (!found && functionName.equals("size")) {
					Console.println("Did not find function " + varUsedInOldContext
							+ " in Diagrams. Matching size and assuming function returns int.");
					var = "int " + functionName;
					lastVarUsedInOldContext = lastVarUsedInOldContext.substring(0,
							lastVarUsedInOldContext.indexOf("("));
				}
				// If found, get return type of method
				if (found) {
					String returnTypeOfFunction = returnTypeMap.get(signatureOfFunction);
					var = returnTypeOfFunction + " " + functionName;
				} else {
					Console.println("Did not find function " + functionName + " in Diagrams.");
				}
			} else {
				/*
				 * If variable is in current method or class, check vars in diagram. If variable
				 * is not in current diagram, check all diagrams. Iterate over each nested
				 * variable and get Datatype.
				 */
				String currentClassName = splittedReplacementVar[0].replaceAll("\\[.*\\]", "");
				boolean isFirstAccessedVarInCurrentClass = false;
				int startIndex = 1;
				for (JavaVariable v : programVariables.getVariables()) {
					if (v.getName().replace("non-null", "").substring(v.getName().indexOf(" ") + 1)
							.equals(splittedReplacementVar[0].replaceAll("\\[.*\\]", "")))
						isFirstAccessedVarInCurrentClass = true;
				}
				for (Field f : programVariables.getFields()) {
					if (f.getName().equals(splittedReplacementVar[0].replaceAll("\\[.*\\]", "")))
						isFirstAccessedVarInCurrentClass = true;
				}
				if (currentClassName.startsWith("self") || currentClassName.startsWith("this")
						|| isFirstAccessedVarInCurrentClass) {
					// TODO: solve differently
					if (self != null) {
						currentClassName = self.replace(" self", "");
					}
				}
				if (isFirstAccessedVarInCurrentClass)
					startIndex = 0;
				String currentVarName = "";
				Field nestedVariable = null;
				boolean found = false;
				boolean penultimateIsArray = false;
				boolean accessArray = false;
				String penultimateVarName = "";
				for (int i = startIndex; i < splittedReplacementVar.length; i++) {
					currentVarName = splittedReplacementVar[i];
					accessArray = currentVarName.contains("[");
					// Replace brackets
					currentVarName = currentVarName.replaceAll("\\[.*\\]", "");

					if (methodClassVarMap.keySet().contains(currentClassName)) {
						for (Field methodVar : methodClassVarMap.get(currentClassName)) {
							String methodVarType = methodVar.getType();
							String methodVarName = methodVar.getName();
							if (methodVarType.contains("[]") && methodVarName.equals(currentVarName)
									&& splittedReplacementVar.length >= 2 && i == (splittedReplacementVar.length - 2)) {
								penultimateIsArray = true;
								penultimateVarName = currentVarName;
							}
							if (methodVarName.equals(lastVarUsedInOldContext)) {
								nestedVariable = methodVar;
								currentVarName = methodVarName;
								found = true;
							}
							if (methodClassVarMap.containsKey(methodVarType)) {
								currentClassName = methodVarType;
							}
						}
					} else {
						var defaultTypes = Arrays.asList("boolean", "byte", "short", "int", "long", "String");
						for (JavaVariable v : programVariables.getVariables()) {
							if (v.getName().replace("non-null", "").substring(v.getName().indexOf(" ") + 1)
									.equals(splittedReplacementVar[0].replaceAll("\\[.*\\]", ""))) {
								var type = v.getName().split("\\s")[0].replaceAll("\\[.*\\]", "");
								var name = v.getName().split("\\s")[1];
								var = type + " " + name;
								if (defaultTypes.contains(type)) {
									String varNameWithOldSuffix = name + counterForVarNaming + OLD_VARS_SUFFIX;
									newReplacements.put(varNameWithOldSuffix,
											new OldReplacement(varUsedInOldContext, counterForVarNaming));
									varsForOldReplacement.add(v.getName().split("\\s")[0] + " " + varNameWithOldSuffix);
									found = true;
									var = "";
									break;
								}
							}
						}
						for (Field f : programVariables.getFields()) {
							if (f.getName().equals(splittedReplacementVar[0].replaceAll("\\[.*\\]", ""))) {
								var type = f.getType().replaceAll("\\[.*\\]", "");
								var name = f.getName();
								var = type + " " + name;
								if (defaultTypes.contains(type)) {
									String varNameWithOldSuffix = name + counterForVarNaming + OLD_VARS_SUFFIX;
									newReplacements.put(varNameWithOldSuffix,
											new OldReplacement(varUsedInOldContext, counterForVarNaming));
									varsForOldReplacement.add(f.getType() + " " + varNameWithOldSuffix);
									found = true;
									var = "";
									break;
								}
							}
						}
					}
					if (found) {
						break;
					}
				}
				// Last variable name should be the the wanted variable!
				if (currentVarName.equals(lastVarUsedInOldContext) && nestedVariable != null) {
					var = nestedVariable.getType() + " "
							+ nestedVariable.getName().replace("static", "").replace("non-null", "");
					if (accessArray) {
						var = nestedVariable.getType() + " " + nestedVariable.getName().replace("static", "")
								.replace("non-null", "").replaceAll("\\[.*\\]", "");
					}
				}
				if (currentVarName.equals("length") && penultimateIsArray) {
					var = "int " + penultimateVarName + "_" + "length";
				}
			}
			if (!var.isEmpty()) {
				/*
				 * VarType::(heap, null, Class::$varName) What about access to static variables
				 * from other classes? Class.varName => we got the class name! But no VarType
				 */
				// EDIT: counterForVarNaming didn't exist until VarCorC OO, Hashmap needs more
				// detailed key, as only varname_oldVal may not be unique
				String varNameWithOldSuffix = var.substring(var.lastIndexOf(" ") + 1) + counterForVarNaming
						+ OLD_VARS_SUFFIX;
				// Add new modified replacements to map.
				newReplacements.put(varNameWithOldSuffix, new OldReplacement(varUsedInOldContext, counterForVarNaming));
				varsForOldReplacement.add(var.substring(0, var.indexOf(' ')) + " " + varNameWithOldSuffix);
			}
		}
		return newReplacements;
	}

	private Map<String, List<Field>> initMethodClassVarMap() {
		Map<String, List<Field>> methodClassVarMap = new HashMap<>();
		IProject project = FileUtil.getProjectFromProjectPath(location);
		Collection<Resource> resources = FileUtil.getCbCClasses(project);
		for (Resource resource : resources) {
			for (EObject object : resource.getContents()) {
				if (object instanceof ModelClass) {
					ModelClass modelClass = (ModelClass) object;
					if (!methodClassVarMap.containsKey(modelClass.getName())) {
						methodClassVarMap.put(modelClass.getName(), new ArrayList<>());
					}
					methodClassVarMap.get(modelClass.getName()).addAll(modelClass.getFields());
				}
			}
		}
		return methodClassVarMap;
	}

	private Map<String, String> initReturnTypeMap() {
		Map<String, String> returnTypeMap = new HashMap<>();
		IProject project = FileUtil.getProjectFromProjectPath(location);
		Collection<Resource> resources = FileUtil.getCbCClasses(project);
		for (Resource resource : resources) {
			for (EObject object : resource.getContents()) {
				if (object instanceof ModelClass) {
					ModelClass modelClass = (ModelClass) object;
					for (Method method : modelClass.getMethods()) {
						returnTypeMap.put(method.getName(), method.getReturnType());
					}
				}
			}
		}
		return returnTypeMap;
	}

	private static class OldReplacement {
		private String var;
		private int index;

		OldReplacement(String var, int index) {
			var = var.replaceAll("this\\.", "self.");
			this.var = var;
			this.index = index;
		}

		public String getVar() {
			return var;
		}

		public int getIndex() {
			return index;
		}
	}

	private String removeStaticNonNull(String varName) {
		return varName.replace("static", "").replace("non-null", "").trim();
	}

	private String getTypeFromVar(String varName) {
		varName = removeStaticNonNull(varName);
		return varName.substring(0, varName.indexOf(" "));
	}

	private String getNameFromVar(String varName) {
		varName = removeStaticNonNull(varName);
		return varName.substring(varName.lastIndexOf(" ")).trim();
	}

	private boolean isNonNull(String string) {
		return string.contains("non-null");
	}

	private boolean isNonNull(JavaVariable var) {
		return var.getName().contains("non-null");
	}

	private boolean isNonNull(Parameter param) {
		return (param.getType() + " " + param.getName()).contains("non-null");
	}

	private boolean isArray(JavaVariable var) {
		return isArray(var.getName());
	}

	private boolean isArray(Parameter param) {
		return isArray(param.getType() + " " + param.getName());
	}

	private boolean isArray(String var) {
		return var.contains("[]");
	}
}
