package de.tu_bs.cs.isf.cbc.tool.helper;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.tool.features.TestAndAssertionGenerator;
import de.tu_bs.cs.isf.cbc.tool.features.TestStatement;

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
		String refFeature = getRefFeature(features, features.getCurConfig());
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
		condition = condition.replaceAll("original", "(" + originalCondition + ")");
		// handle any inner original calls
		condition = handleOriginalCondition(fp, originalCondition, isPreCon, features);
		return condition;
	}
	
	public static void handleOriginalCode(final IFeatureProvider fp, String code, final Features features, final List<MethodCode> newMethods, String signature) throws ReferenceException {
		if (!code.contains("original(")) {
			return;
		}
		String refFeature = getRefFeature(features, features.getCurConfig());
		CbCFormula originalFormula = features.loadFormulaFromFeature(fp, refFeature, features.getCallingClass(), features.getCallingMethod());
		String refCode = TestAndAssertionGenerator.genDiagramCode(originalFormula, null);
		signature = signature.substring(0, signature.indexOf(features.getCallingMethod())) 
				+ "original_" 
				+ signature.substring(signature.indexOf(features.getCallingMethod()) , signature.length());
		String methodName = Util.getMethodNameFromSig(signature);
		code = code.replaceAll("original", methodName);
		refCode = signature + "{\n"
				+ "\t" + refCode + "}\n";
		refCode = Util.indentCode(refCode, 0);
		newMethods.add(new MethodCode(signature, refCode));
		handleOriginalCode(fp, refCode, features, newMethods, signature);
	}
	
	private static String getRefFeature(final Features features, String[] curConfig) throws ReferenceException {
		String configName = features.getConfigName(curConfig);
		int index = features.getFeatureIndex(curConfig, features.getCallingFeature());
		if (index == -1) {
			throw new ReferenceException("Feature '" + features.getCallingFeature() + "' couldn't be found in the current config '" + configName + "'");
		} else if (index == 0) {
			throw new ReferenceException(originalBaseMsg);
		}
		index--;
		return curConfig[index];
	}
}
