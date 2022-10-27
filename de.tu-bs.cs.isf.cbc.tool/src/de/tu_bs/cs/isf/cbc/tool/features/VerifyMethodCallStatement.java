package de.tu_bs.cs.isf.cbc.tool.features;

import java.util.List;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;

import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field;
import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.statistics.DataCollector;
import de.tu_bs.cs.isf.cbc.tool.helper.GenerateCodeForVariationalVerification;
import de.tu_bs.cs.isf.cbc.util.CompareMethodBodies;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.cbc.util.Parser;
import de.tu_bs.cs.isf.cbc.util.ProveWithKey;
import de.tu_bs.cs.isf.cbc.util.VerifyFeatures;

public class VerifyMethodCallStatement extends MyAbstractAsynchronousCustomFeature {

	/**
	 * Constructor of the class
	 * 
	 * @param fp The FeatureProvider
	 */
	public VerifyMethodCallStatement(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Verify a method-call statement";
	}

	@Override
	public String getDescription() {
		return "Verifies a method-call statement using the pre and post condition.";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		boolean ret = false;
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo != null && bo instanceof MethodStatement) {
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
		long startTime = System.nanoTime();
		monitor.beginTask("Verify method-call statement", IProgressMonitor.UNKNOWN);
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof AbstractStatement) {
				AbstractStatement statement = (AbstractStatement) bo;
				JavaVariables vars = null;
				GlobalConditions conds = null;
				Renaming renaming = null;
				CbCFormula formula = null;
				for (Shape shape : getDiagram().getChildren()) {
					Object obj = getBusinessObjectForPictogramElement(shape);
					if (obj instanceof JavaVariables) {
						vars = (JavaVariables) obj;
					} else if (obj instanceof GlobalConditions) {
						conds = (GlobalConditions) obj;
					} else if (obj instanceof Renaming) {
						renaming = (Renaming) obj;
					} else if (obj instanceof CbCFormula) {
						formula = (CbCFormula) obj;
					}
				}
				
				boolean proven = false;
				URI uri = getDiagram().eResource().getURI();
				IProject project = FileUtil.getProjectFromFileInProject(uri);
				boolean isVariational = false;
				try {
					if (project.getNature("de.ovgu.featureide.core.featureProjectNature") != null) {
						isVariational = true;
					}
				} catch (CoreException e) {
					e.printStackTrace();
				}

				if (isVariational) {
					proven = executeVariationalVerification(project, uri, statement, vars, conds, renaming, formula, false, monitor);
				} else {
					proven = executeNormalVerification(statement, vars, conds, renaming, formula, false, monitor);
				}
				statement.setProven(proven);
				updatePictogramElement(((Shape) pes[0]).getContainer());
			}
		}
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1000000;
		Console.println("--------------- Verification completed --------------- " + duration + "ms");
		monitor.done();
	}

	private boolean executeNormalVerification(AbstractStatement statement, JavaVariables vars, GlobalConditions conds, Renaming renaming, CbCFormula formula, boolean returnStatement, IProgressMonitor monitor) {
		if (!DataCollector.checkForId(statement)) return false;
		boolean proven = false;
		Console.println("--------------- Triggered verification ---------------");
		if (CompareMethodBodies.readAndTestMethodBodyWithJaMoPP2(statement.getName())) {
			URI uri = getDiagram().eResource().getURI();
			String platformUri = uri.toPlatformString(true);
			String callingClass = uri.segment(uri.segmentCount() - 2) + "";
			ProveWithKey prove = new ProveWithKey(statement, vars, conds, renaming, monitor, platformUri, formula, new FileUtil(platformUri), "");
			proven = prove.proveStatementWithKey(returnStatement, false, callingClass, true);
		} else {
			Console.println("Statement is not in correct format.");
		}
		return proven;
	}

	private boolean executeVariationalVerification(IProject project, URI uri, AbstractStatement statement, JavaVariables vars, GlobalConditions conds, Renaming renaming, CbCFormula formula, boolean returnStatement, IProgressMonitor monitor) {
		if (!DataCollector.checkForId(statement)) return false;
		boolean proven = false;
		VerifyStatement verifyStmt = new VerifyStatement(super.getFeatureProvider());
		String callingFeature = uri.segment(uri.segmentCount() - 3) + "";
		String callingClass = uri.segment(uri.segmentCount() - 2) + "";
		String callingMethod = uri.trimFileExtension().segment(uri.segmentCount()-1) + "";
		String varM = handleVarM(Parser.extractMethodNameFromStatemtent(statement.getName()), callingClass, vars);
		String varMParts[] = varM.split("\\.");
		String[][] featureConfigs = VerifyFeatures.verifyConfig(uri, varM, false, callingClass, false, null);
		String[][] featureConfigsRelevant = VerifyFeatures.verifyConfig(uri, varM, false, callingClass, true, null);
		String[][] originalFeatureConfigsRelevant = VerifyFeatures.verifyConfig(uri, varM, false, callingClass, true, uri.segment(uri.trimFileExtension().segmentCount() - 1));
		
		Console.println("--------------- Triggered variational verification ---------------");

		GenerateCodeForVariationalVerification genCode = new GenerateCodeForVariationalVerification(super.getFeatureProvider());
		
		if (featureConfigs != null) {
			String[] variants = verifyStmt.generateVariantsStringFromFeatureConfigs(featureConfigsRelevant, callingFeature, varM.contains(".") ? varMParts[0] : callingClass);
			String[] variantsOriginal = verifyStmt.generateVariantsStringFromFeatureConfigs(originalFeatureConfigsRelevant, callingFeature, callingClass);
			if (CompareMethodBodies.readAndTestMethodBodyWithJaMoPP2(statement.getName())) {
				for (int i = 0; i < variants.length; i++) {
					genCode.generate(project.getLocation(), callingFeature, callingClass, callingMethod, featureConfigs[i]);
					String configName = "";
					for (String s : featureConfigs[i]) configName += s;
					ProveWithKey prove = new ProveWithKey(statement, vars, conds, renaming, monitor, uri.toPlatformString(true), formula, new FileUtil(uri.toPlatformString(true)), configName);
					List<CbCFormula> refinements = verifyStmt.generateCbCFormulasForRefinements(variants[i], varMParts[1].toLowerCase());
					List<CbCFormula> refinementsOriginal = verifyStmt.generateCbCFormulasForRefinements(variantsOriginal[i], callingMethod);
					List<JavaVariables> refinementsVars = verifyStmt.generateJavaVariablesForRefinements(variants[i], varMParts[1].toLowerCase());
					proven = prove.proveStatementWithKey(refinementsOriginal.isEmpty() ? null : refinementsOriginal, refinements, refinementsVars, returnStatement, false, callingMethod, varM, callingClass, true);
				}
			} else {
				Console.println("  Statement is not in correct format.");
			}
		}
		return proven;
	}

	private String handleVarM(String methodCall, String callingClass, JavaVariables vars) {
		if (!methodCall.contains(".")) { // Call without class reference
			return callingClass + "." + methodCall;
		} else if (methodCall.contains("this.")) { // Call with this reference
			return methodCall.replace("this.", "." + callingClass);
		} else if (Character.isUpperCase(methodCall.charAt(0))) { // Call with static class reference
			return methodCall;
		} else { // Call with reference to a variable/object
			String type = methodCall.split("\\.")[0];
			for (JavaVariable var : vars.getVariables()) {
				if (var.getName().split(" ")[1].equals(type)) {
					return methodCall.replace(type, var.getName().split(" ")[0]);
				}
			}
			for (Field f : vars.getFields()) {
				if (f.getName().equals(type)) {
					return methodCall.replace(type, f.getType());
				}
			}
		}
		return methodCall;
	}
}