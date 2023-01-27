package de.tu_bs.cs.isf.cbc.tool.helper;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.URI;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.CbcclassFactory;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Visibility;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.tool.features.TestAndAssertionGenerator;
import de.tu_bs.cs.isf.cbc.tool.features.TestStatement;
import de.tu_bs.cs.isf.cbc.util.FileUtil;

/**
 * Provides all functions that handle the keyword original in conditions and code. 
 * This class also handles abstract method calls.
 * Note:
 * This class doesn't use the already existing code generation for SPLs, because that
 * generates all code at once, which could lead to performance issues.
 * Lazy generation of code and conditions is used instead.
 * @author Fynn Demmler
 */
public class TestUtilSPL {
	private static String originalBaseMsg = "Original calls are not allowed in base features.";
	
	/**
	 * Replaces original calls in a given condition.
	 * @param condition
	 * @param isPreCon
	 * @param signature
	 * @param features
	 * @return The new condition in which all occurrences of the original keywords were replaced.
	 * @throws ReferenceException
	 */
	public static String handleOriginalCondition(final IFeatureProvider fp, String condition, boolean isPreCon, final Features features) throws ReferenceException {
		if (!condition.contains("original")) {
			return condition;
		}
		final String nextFeature = features.getPrevFeature();
		if (nextFeature == null) {
			return "";
		}
		String refFeature = getRefFeature(features, nextFeature, features.getCurConfig());
		// get formula of method in feature *refFeature*
		CbCFormula originalFormula = features.loadFormulaFromFeature(fp, refFeature, features.getCallingClass(), features.getCallingMethod());
		if (originalFormula == null) {
			throw new ReferenceException("Formula of method '" + features.getCallingMethod() + "' in feature '" + refFeature + "' couldn't be found.");
		}
		// get the condition
		String originalCondition = "";
		if (isPreCon) {
			originalCondition = originalFormula.getStatement().getPreCondition().getName();	
		} else {
			originalCondition = originalFormula.getStatement().getPostCondition().getName();
		}
		condition = condition.substring(0, condition.indexOf("original")) 
				+ "(" + originalCondition + ")" 
				+ condition.substring(condition.indexOf("original") 
						+ "original".length(), condition.length());
		//condition = condition.replaceAll("original", "(" + originalCondition + ")");
		// handle any inner original calls TODO?
		// condition = handleOriginalCondition(fp, originalCondition, isPreCon, features);
		return condition;
	}
	
	private static JavaVariables readFieldsFromClass(String className, final String uri) {
		JavaVariables vars = CbcmodelFactory.eINSTANCE.createJavaVariables();
		var fileHandler = new FileUtil(uri);
		File file = fileHandler.getClassFile(className);
		if (file == null) {
			return vars;
		}
		List<String> lines = fileHandler.readFileInList(file.getAbsolutePath());
		if (lines.get(0).contains(" extends ")) {
			String inheritedClassName = lines.get(0).trim();
			inheritedClassName = inheritedClassName.substring(inheritedClassName.indexOf(" extends ") + 9, inheritedClassName.indexOf("{")).trim();
			vars = readFieldsFromClass(inheritedClassName, uri);
		}
		int i = 1;
		while (lines.get(i).contains(";")) {
			String field = lines.get(i++).replace(";", "");
			Field f = CbcclassFactory.eINSTANCE.createField();
			String split[] = field.replace("/*@spec_public@*/ ","").trim().split(" ");
			int pointer = 1;
			switch (split[0].toLowerCase()) {
			case "private":
				f.setVisibility(Visibility.PRIVATE);
				break;
			case "public":
				f.setVisibility(Visibility.PUBLIC);
				break;
			case "protected":
				f.setVisibility(Visibility.PROTECTED);
				break;
			case "package":
				f.setVisibility(Visibility.PACKAGE);
				break;
			default:
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
	
	public static void handleOriginalCode(final IFeatureProvider fp, final URI projectPath, String code, final Features features, final List<MethodHandler> newMethods, String signature, final JavaVariables vars) throws ReferenceException {
		if (!code.contains("original")) {
			return;
		}
		String nextFeature = features.getPrevFeature();
		if (nextFeature == null) {
			return;
		}
		String refFeature = getRefFeature(features, nextFeature, features.getCurConfig());
		CbCFormula originalFormula = features.loadFormulaFromFeature(fp, refFeature, features.getCallingClass(), features.getCallingMethod());
		Diagram test;
		String refCode = TestAndAssertionGenerator.genDiagramCode(originalFormula, null);
		signature = signature.substring(0, signature.indexOf(features.getCallingMethod())) 
				+ "original_" 
				+ signature.substring(signature.indexOf(features.getCallingMethod()) , signature.length());
		String methodName = MethodHandler.getMethodNameFromSig(signature);
		refCode = refCode.replaceAll("original", "original_" + methodName);
		refCode = signature + "{\n"
				+ "\t" + refCode + "}\n";
		var classVars = readFieldsFromClass(features.getCallingClass(), projectPath.toPlatformString(false));
		vars.getFields().addAll(classVars.getFields());
		addFields(vars, classVars);
		refCode = Variable.prefixAllVariables(refCode, classVars);
		refCode = CodeHandler.indentCode(refCode, 0);
		newMethods.add(new MethodHandler(signature, refCode));
		handleOriginalCode(fp, projectPath, refCode, features, newMethods, signature, vars);
	}
	
	private static void addFields(final JavaVariables destination, final JavaVariables source) {
		final List<Field> toAdd = new ArrayList<Field>();
		
		for (var v : source.getFields()) {
			boolean found = false;
			for (var d : destination.getFields()) {
				if (d.getName().equals(v.getName())) {
					found = true;
				}
			}
			if (!found) {
				toAdd.add(v);
			}
		}
		destination.getFields().addAll(toAdd);
	}
	
	private static String getRefFeature(final Features features, final String callingFeature, String[] curConfig) throws ReferenceException {
		String configName = features.getConfigName(curConfig);
		int index = features.getFeatureIndex(curConfig, callingFeature);
		if (index == -1) {
			throw new ReferenceException("Feature '" + callingFeature + "' couldn't be found in the current config '" + configName + "'");
		} else if (index == 0) {
			throw new ReferenceException(originalBaseMsg);
		}
		index--;
		return curConfig[index];
	}
	
	private static List<String> getAbstractFunctionCalls(final String code) {
		final var calls = new ArrayList<String>();
		final Pattern p = Pattern.compile("\\s*\\w+\\(");
		final Matcher m = p.matcher(code);
		
		while (m.find()) {
			final String functionName = m.group(0).substring(0, m.group(0).indexOf('(')).trim();
			if (functionName.contains("original")) {
				continue;
			}
			String methodSig;
			if(!(methodSig = MethodHandler.methodExists(code, functionName)).isEmpty()) {
				continue;
			}
			calls.add(functionName);
		}
		return calls;
	}

	/**
	 * Handles abstract function calls inside a SPL. A function is abstract if it's definition depends on the configuration it is called in.
	 * For example, if the function 'sort' in the feature 'sorted' is called, the configuration must contain feature 'increasing' or 
	 * feature 'decreasing'. The definition of the function 'sort' will then be extracted from the one existing in the current configuration.
	 * @param fp
	 * @param projectPath
	 * @param code
	 * @param features
	 */
	public static List<MethodHandler> handleAbstractMethodCalls(final IFeatureProvider fp, final URI projectPath, final String code, final Features features, final List<MethodHandler> newMethods) {
		final var methodNames = getAbstractFunctionCalls(code);
		final var relevantFeatures = new ArrayList<String>();
		TestAndAssertionGenerator tg = new TestAndAssertionGenerator(fp);
		
		String next;
		while ((next = features.getNextFeature()) != null) {
			relevantFeatures.add(next);
		}
		for (var mName : methodNames) {
			for (var feature : relevantFeatures) {
				Diagram diag = FileHandler.loadDiagramFromClass(projectPath, "features/" + feature, mName);
				if (diag == null) {
					continue;
				}
				String functionCode = tg.genCode(diag, true);
				String sig = MethodHandler.getSignatureFromCode(functionCode);
				if (functionCode.isEmpty()) {
					continue;
				}
				boolean alreadyAdded = false;
				for (var m : newMethods) {
					if (m.getSignature().equals(sig)) {
						alreadyAdded = true;
					}
				}
				if (alreadyAdded) {
					break;
				}
				MethodHandler mCode = new MethodHandler(sig, functionCode);
				newMethods.add(mCode);
			}
		}
		return newMethods;
	}
}
