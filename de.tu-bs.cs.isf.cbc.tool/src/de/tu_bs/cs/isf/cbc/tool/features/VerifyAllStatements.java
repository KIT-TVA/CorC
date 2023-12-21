package de.tu_bs.cs.isf.cbc.tool.features;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.Shape;

import de.tu_bs.cs.isf.cbc.cbcclass.Field;
import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.OriginalStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.cbcmodel.ReturnStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Variant;
import de.tu_bs.cs.isf.cbc.tool.helper.GenerateCodeForVariationalVerification;
import de.tu_bs.cs.isf.cbc.util.CompareMethodBodies;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.ConstructCodeBlock;
import de.tu_bs.cs.isf.cbc.util.DiagramPartsExtractor;
import de.tu_bs.cs.isf.cbc.util.FeatureUtil;
import de.tu_bs.cs.isf.cbc.util.FileHandler;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.cbc.util.IdAdder;
import de.tu_bs.cs.isf.cbc.util.Parser;
import de.tu_bs.cs.isf.cbc.util.ProveWithKey;
import de.tu_bs.cs.isf.cbc.util.UpdateDiagram;
import de.tu_bs.cs.isf.cbc.util.VerifyFeatures;
import de.tu_bs.cs.isf.cbc.util.statistics.StatDataCollector;


/**
 * Class that changes the abstract value of algorithms
 * @author Tobias
 *
 */
public class VerifyAllStatements extends MyAbstractAsynchronousCustomFeature {
    static int configNum = 0;
    static int configs = 0;
    static boolean isVariational = false;
    static String configName = "";
    static VerifyStatement verifyStmt;
    static GenerateCodeForVariationalVerification genCode;
    /**
     * Constructor of the class
     * @param fp	The FeatureProvider
     */
    public VerifyAllStatements(IFeatureProvider fp) {
        super(fp);
    }
 
    @Override
    public String getName() {
        return "Verify All Statements";
    }
 
    @Override
    public String getDescription() {
        return "Verify all of the statements.";
    }
 
    @Override
    public boolean canExecute(ICustomContext context) {
        return true;
    }
 
    @Override
    public void execute(ICustomContext context, IProgressMonitor monitor) {
    	configName = "";
    	Console.clear();
    	long startTime = System.nanoTime();
		DiagramPartsExtractor extractor = new DiagramPartsExtractor(getDiagram());
		CbCFormula formula = extractor.getFormula();		
		AbstractStatement statement = formula.getStatement();
		URI uri = getDiagram().eResource().getURI();
		// delete 'tests' folder if it exists because it will cause reference errors
		// since key doesn't use TestNG.
		FileHandler.instance.deleteFolder(uri, "tests");

		IProject project = FileUtil.getProjectFromFileInProject(uri);
		
		StatDataCollector.checkForId(statement);
		
		verifyStmt = new VerifyStatement(super.getFeatureProvider());
		genCode = new GenerateCodeForVariationalVerification(super.getFeatureProvider());
		if (FileHandler.instance.isSPL(uri)) {
			isVariational = true;
		} else {
			isVariational = false;
		}
		
		if (isVariational) {
			Console.println("Starting variational verification...\n");
			String callingClass = FeatureUtil.getInstance().getCallingClass(uri);
			String callingFeature = FeatureUtil.getInstance().getCallingFeature(uri);
			String callingMethod = FeatureUtil.getInstance().getCallingMethod(uri);
			String[][] featureConfigs = VerifyFeatures.verifyConfig(uri, uri.segment(uri.segmentCount()-1), true, callingClass, false, null);				
			configs = featureConfigs.length-1;
			for (int i = 0; i < featureConfigs.length; i++) {
				configName = "";
				for (String s : featureConfigs[i]) configName += s;
				configNum = i;
				genCode.generate(FileUtil.getProjectFromFileInProject(getDiagram().eResource().getURI()).getLocation(), callingFeature, callingClass, callingMethod, featureConfigs[i]);
				if (!proveChildStatement(statement.getRefinement(), getDiagram(), null)) {
					statement.setProven(false);
					return;
				}
			}
		} else {
			Console.println("Starting verification...\n");
			if (!proveChildStatement(statement.getRefinement(), getDiagram(), null)) {
				statement.setProven(false);
				return;
			}
		}		
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1000000;
		Console.println("\nVerification done."); 
		Console.println("Time needed: " + duration + "ms");
    }   
    
	private static boolean proveChildStatement(AbstractStatement statement, Diagram diagram, IProgressMonitor monitor) {
		boolean prove = false;
		DiagramPartsExtractor extractor = new DiagramPartsExtractor(diagram);
    	JavaVariables vars = extractor.getVars();
		GlobalConditions conds = extractor.getConds();
		Renaming renaming = extractor.getRenaming();
		CbCFormula formula = extractor.getFormula();		
		String uri = diagram.eResource().getURI().toPlatformString(true);
		 if (statement instanceof SmallRepetitionStatement) {
			prove = proveSmallReptitionStatement(statement, diagram, monitor);
		} else if (statement instanceof CompositionStatement) {
			prove = proveCompositionStatement(statement, diagram, monitor);
		} else if (statement instanceof SelectionStatement) {
			prove = proveSelectionStatement(statement, diagram, monitor);
		} else if(statement instanceof ReturnStatement) {
			Console.println("-> Return Statement");
			prove = proveAbstractStatement(statement, diagram, true, monitor);
		} else if(statement.getComment() != null) {
			Console.println("-> Return Statement");
			if(statement.getComment().equals("returnStatement"))
				prove = proveAbstractStatement(statement, diagram, true, monitor);
		} else if (statement instanceof MethodStatement) {
			prove = proveMethodStatement(statement, diagram, false, monitor);
		} else if (statement instanceof OriginalStatement) {
			prove = proveOriginalStatement(statement, diagram, false, monitor);
		} else if (statement instanceof AbstractStatement) {
			prove = proveAbstractStatement(statement, diagram, false, monitor);
		}
		return prove;
	}

	private static boolean proveOriginalStatement(AbstractStatement statement, Diagram diagram, boolean returnStatement,
			IProgressMonitor monitor) {
		if (!isVariational)
			return false;
		if (!statement.isProven()) {
			boolean proven = false;
			URI uRi = URI.createPlatformResourceURI(diagram.eResource().getURI().toPlatformString(true), true);
			String callingFeature = FeatureUtil.getInstance().getCallingFeature(uRi);
			String callingClass = FeatureUtil.getInstance().getCallingClass(uRi);
			String callingMethod = FeatureUtil.getInstance().getCallingMethod(uRi);
			String[][] featureConfigsRelevant = VerifyFeatures.verifyConfig(uRi, callingMethod, true, callingClass, true, null);
			if (featureConfigsRelevant != null) {
				String[] variants = verifyStmt.generateVariantsStringFromFeatureConfigs(featureConfigsRelevant, callingFeature, callingClass);
				if (CompareMethodBodies.readAndTestMethodBodyWithJaMoPP2(statement.getName())) {
					ProveWithKey prove = new ProveWithKey(statement, diagram, monitor, new FileUtil(uRi.toPlatformString(true)), configName);
					List<CbCFormula> refinements = verifyStmt.generateCbCFormulasForRefinements(variants[configNum], callingMethod);
					List<JavaVariables> refinementsVars = verifyStmt.generateJavaVariablesForRefinements(variants[configNum], callingMethod);
					proven = prove.proveStatementWithKey(null, refinements, refinementsVars, returnStatement, false, callingMethod, "", callingClass, false);
				} else {
					Console.println("  Statement is not in correct format.");
				}
			}

			if (proven && configs == configNum) {
				statement.setProven(true);
			} else {
				statement.setProven(false);//
			}
			return proven;
		} else {
			Console.println("Original-call statement: " + statement.getName().replaceAll("\n", "").replaceAll("\r", "") + " already true");
			return true;
		}
	}

	private static boolean proveMethodStatement(AbstractStatement statement, Diagram diagram, boolean returnStatement, IProgressMonitor monitor) {
		if (!statement.isProven()) {
			boolean proven = false;
			DiagramPartsExtractor extractor = new DiagramPartsExtractor(diagram);
			JavaVariables vars = extractor.getVars();
			String uri = diagram.eResource().getURI().toPlatformString(true);
			if (!isVariational) {
				ProveWithKey prove = new ProveWithKey(statement, diagram, monitor, new FileUtil(uri), "");
				proven = prove.proveStatementWithKey(returnStatement, false, FeatureUtil.getInstance().getCallingClass(URI.createPlatformResourceURI(uri, true)), false);
			} else {
				URI uRi = URI.createPlatformResourceURI(uri, true);
				String callingFeature = FeatureUtil.getInstance().getCallingFeature(uRi);
				String callingClass = FeatureUtil.getInstance().getCallingClass(uRi);
				String callingMethod = FeatureUtil.getInstance().getCallingMethod(uRi);
				String varM = handleVarM(Parser.extractMethodNameFromStatemtent(statement.getName()), callingClass, vars);
				String[][] featureConfigsRelevant = VerifyFeatures.verifyConfig(uRi, varM, false, callingClass, true, null);
				String[][] originalFeatureConfigsRelevant = VerifyFeatures.verifyConfig(uRi, varM, false, callingClass, true, uri.split("/")[5]);
				
				String varMParts[] = varM.split("\\.");

				if (featureConfigsRelevant != null) {
					String[] variants = verifyStmt.generateVariantsStringFromFeatureConfigs(featureConfigsRelevant,	callingFeature, varM.contains(".") ? varMParts[0] : callingClass);
					String[] variantsOriginal = verifyStmt.generateVariantsStringFromFeatureConfigs(originalFeatureConfigsRelevant, callingFeature, callingClass);
					if (CompareMethodBodies.readAndTestMethodBodyWithJaMoPP2(statement.getName())) {
						ProveWithKey prove = new ProveWithKey(statement, diagram, monitor, new FileUtil(uRi.toPlatformString(true)), configName);
						List<CbCFormula> refinements = verifyStmt.generateCbCFormulasForRefinements(variants[configNum], varMParts[1].toLowerCase());
						List<CbCFormula> refinementsOriginal = verifyStmt.generateCbCFormulasForRefinements(variantsOriginal[configNum], callingMethod);
						List<JavaVariables> refinementsVars = verifyStmt.generateJavaVariablesForRefinements(variants[configNum], varMParts[1].toLowerCase());
						proven = prove.proveStatementWithKey(refinementsOriginal, refinements, refinementsVars, returnStatement, false, callingMethod, varM, callingClass, false);
					} else {
						Console.println("  Statement is not in correct format.");
					}
				}
			}
			if (proven && configs == configNum) {
				statement.setProven(true);
			} else {
				statement.setProven(false);//
			}
			return proven;
		} else {
			Console.println("Method-call statement: " + statement.getName().replaceAll("\n", "").replaceAll("\r", "")
					+ " already true");
			return true;
		}
	}

	private static boolean proveCompositionStatement(AbstractStatement statement, Diagram diagram, IProgressMonitor monitor) {
    	boolean prove1, prove2 = false;
    	CompositionStatement compositionStatement = (CompositionStatement) statement;
    	if (compositionStatement.getFirstStatement().getRefinement() != null) {
    		prove1 = proveChildStatement(compositionStatement.getFirstStatement().getRefinement(), diagram, monitor);
    	} else {
    		prove1 = proveChildStatement(compositionStatement.getFirstStatement(), diagram, monitor);
    	}
    	if (compositionStatement.getSecondStatement().getRefinement() != null) {
    		prove2 = proveChildStatement(compositionStatement.getSecondStatement().getRefinement(), diagram, monitor);
    	} else {
    		prove2 = proveChildStatement(compositionStatement.getSecondStatement(), diagram, monitor);
    	}
    	if (prove1 && prove2 && true && configs == configNum)  {
    		statement.setProven(true);
    	} else {
    		statement.setProven(false);//
    	}
		return (prove1 && prove2 && true);
    }

	private static boolean proveAbstractStatement(AbstractStatement statement, Diagram diagram, boolean returnStatement, IProgressMonitor monitor) {
		if (!statement.isProven()) {
			boolean proven = false;
			DiagramPartsExtractor extractor = new DiagramPartsExtractor(diagram);
			String uri = diagram.eResource().getURI().toPlatformString(true);
			if (!isVariational) {
				ProveWithKey prove = new ProveWithKey(statement, diagram, monitor, new FileUtil(uri), "");
				proven = prove.proveStatementWithKey(returnStatement, false, FeatureUtil.getInstance().getCallingClass(URI.createPlatformResourceURI(uri, true)), false);
			} else {
				URI uRi = URI.createPlatformResourceURI(uri, true);
				String callingFeature = FeatureUtil.getInstance().getCallingFeature(uRi);
				String callingClass = FeatureUtil.getInstance().getCallingClass(uRi);
				String callingMethod = FeatureUtil.getInstance().getCallingMethod(uRi);
				String[][] featureConfigsRelevant = VerifyFeatures.verifyConfig(uRi, callingMethod, true, callingClass, true, null);
				
				if (featureConfigsRelevant != null) {
					String[] variants = verifyStmt.generateVariantsStringFromFeatureConfigs(featureConfigsRelevant, callingFeature, callingClass);
					if (CompareMethodBodies.readAndTestMethodBodyWithJaMoPP2(statement.getName())) {
						ProveWithKey prove = new ProveWithKey(statement, diagram, monitor, new FileUtil(uRi.toPlatformString(true)), configName);
						List<CbCFormula> refinements = verifyStmt.generateCbCFormulasForRefinements(variants[configNum], callingMethod);
						List<JavaVariables> refinementsVars = verifyStmt.generateJavaVariablesForRefinements(variants[configNum], callingMethod);
						proven = prove.proveStatementWithKey(null, refinements, refinementsVars, returnStatement, false, callingMethod, "", callingClass, false);
					} else {
						Console.println("  Statement is not in correct format.");
					}
				}
			}
			if (proven && configs == configNum) {
				statement.setProven(true);
			} else {
				statement.setProven(false);
			}
			return proven;
		} else {
			Console.println("Abstract statement: " + statement.getName().replaceAll("\n", "").replaceAll("\r", "") + " already true");
			return true;
		}
	}
    
    private static boolean proveSelectionStatement(AbstractStatement statement, Diagram diagram, IProgressMonitor monitor) {
    	boolean proven = true;
    	SelectionStatement selectionStatement = (SelectionStatement) statement;
		for (AbstractStatement childStatement : selectionStatement.getCommands()) {
			proven = (proveChildStatement(childStatement.getRefinement(), diagram, monitor) && proven && true);
		}
		boolean provePre = selectionStatement.isPreProve();
		if (!(selectionStatement.isProven() && provePre && true)) {
			if (!selectionStatement.isPreProve()) {
				EList<Condition> guards = selectionStatement.getGuards();
				Condition preCondition = selectionStatement.getParent().getPreCondition();
				ProveWithKey prove = new ProveWithKey(statement, diagram, monitor, new FileUtil(diagram.eResource().getURI().toPlatformString(true)), configName);
				provePre = prove.provePreSelWithKey(guards, preCondition);
				if (configs == configNum) selectionStatement.setPreProve(provePre);
			}
			if (provePre && proven && true && configs == configNum) {
				selectionStatement.setProven(true);
			} else {
				selectionStatement.setProven(false);//
			}
			return (proven && provePre && true);
    	} else {
    		Console.println("Selection statement already true");
    		return true;
    	}
    }
	
	private static boolean proveSmallReptitionStatement(AbstractStatement statement, Diagram diagram, IProgressMonitor monitor) {
		SmallRepetitionStatement repStatement = (SmallRepetitionStatement) statement;
		boolean proven = true;
		if (repStatement.getLoopStatement().getRefinement() != null) {
			proven = (proveChildStatement(repStatement.getLoopStatement().getRefinement(), diagram, null) && proven && true);
		}
		boolean provePre = repStatement.isPreProven();
		boolean provePost = repStatement.isPostProven();
		boolean proveVar = repStatement.isVariantProven();
		if (!(repStatement.isProven() && provePre && provePost && proveVar && true)) {
			Condition invariant = repStatement.getInvariant();
			Condition preCondition = repStatement.getParent().getPreCondition();
			Condition guard = repStatement.getGuard();
			Condition postCondition = repStatement.getParent().getPostCondition();
			String code = ConstructCodeBlock.constructCodeBlockAndVerify(statement, false);
			Variant variant = repStatement.getVariant();
			ProveWithKey prove = new ProveWithKey(statement, diagram, monitor, new FileUtil(diagram.eResource().getURI().toPlatformString(true)), configName);
			if (!provePre) {
				provePre = prove.proveCImpliesCWithKey(preCondition, invariant);
				if (configs == configNum) repStatement.setPreProven(provePre);
			}
			if (!provePost) {
				provePost = prove.provePostRepetitionWithKey(invariant, guard, postCondition);
				if (configs == configNum) repStatement.setPostProven(provePost);
			}
			if (!proveVar) {
				proveVar = prove.proveVariantWithKey(code, invariant, guard, variant);
				if (configs == configNum) repStatement.setVariantProven(proveVar);	
			}
			if (proven && provePre && provePost && proveVar && configs == configNum) {
				repStatement.setProven(true);
			} else {
				repStatement.setProven(false);//
			} 
	    	return proven;
		} else {
			repStatement.setPreProven(true);
			repStatement.setPostProven(true);
			repStatement.setVariantProven(true);
    		Console.println("SRepetition statement already true");
			return true;
		}
	}
	
	private static String handleVarM(String methodCall, String callingClass, JavaVariables vars) {
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