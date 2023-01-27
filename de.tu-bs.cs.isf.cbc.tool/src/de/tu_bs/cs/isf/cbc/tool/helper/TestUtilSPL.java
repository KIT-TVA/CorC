package de.tu_bs.cs.isf.cbc.tool.helper;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;

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
	public static String handleOriginalCondition(final IFeatureProvider fp, String condition, boolean isPreCon, final Features features, final String[] curConfig) throws ReferenceException {
		if (!condition.contains("original")) {
			return condition;
		}
		String refFeature = getRefFeature(features, curConfig);
		// get diagram of method with signature *signature* in feature *refFeature*
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
		condition = handleOriginalCondition(fp, originalCondition, isPreCon, features, curConfig);
		return condition;
	}
	
	public static String handleOriginalCode(final IFeatureProvider fp, String code, final Features features) {
		
		return null;
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
