package de.tu_bs.cs.isf.cbc.tool.features;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
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
import de.tu_bs.cs.isf.cbc.statistics.DataCollector;
import de.tu_bs.cs.isf.cbc.tool.helper.GenerateCodeForVariationalVerification;
import de.tu_bs.cs.isf.cbc.util.CompareMethodBodies;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.ConstructCodeBlock;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.cbc.util.Parser;
import de.tu_bs.cs.isf.cbc.util.ProveWithKey;
import de.tu_bs.cs.isf.cbc.util.VerifyFeatures;


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
    	long startTime = System.nanoTime();
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
		AbstractStatement statement = formula.getStatement();
		if (!checkAllStatementsForId(statement.getRefinement())) return;
		String uriString = getDiagram().eResource().getURI().toPlatformString(true);
		URI uri = getDiagram().eResource().getURI();
		IProject project = FileUtil.getProjectFromFileInProject(uri);
		verifyStmt = new VerifyStatement(super.getFeatureProvider());
		genCode = new GenerateCodeForVariationalVerification(super.getFeatureProvider());
		try {
			if (project.getNature("de.ovgu.featureide.core.featureProjectNature") != null) {
				isVariational = true;
			} else {
				isVariational = false;
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
		if (isVariational) {
			Console.println("--------------- Triggered variational verification ---------------");
			String callingClass = uri.segment(uri.segmentCount()-2) + "";
			String callingFeature = uri.segment(uri.segmentCount()-3) + "";
			String callingMethod = uri.trimFileExtension().segment(uri.segmentCount()-1) + "";
			String[][] featureConfigs = VerifyFeatures.verifyConfig(uri, uri.segment(uri.segmentCount()-1), true, callingClass, false);				
			configs = featureConfigs.length-1;
			for (int i = 0; i < featureConfigs.length; i++) {
				configName = "";
				for (String s : featureConfigs[i]) configName += s;
				configNum = i;
				genCode.generate(FileUtil.getProjectFromFileInProject(getDiagram().eResource().getURI()).getLocation(), callingFeature, callingClass, callingMethod, featureConfigs[i]);
				if (!proveChildStatement(statement.getRefinement(), vars, conds, renaming, formula, uriString, null)) {
					statement.setProven(false);
					return;
				}
			}
		} else {
			Console.println("--------------- Triggered verification ---------------");
			if (!proveChildStatement(statement.getRefinement(), vars, conds, renaming, formula, uriString, null)) {
				statement.setProven(false);
				return;
			}
		}		
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1000000;
		Console.println("--------------- Verification completed --------------- " + duration + "ms");
    }    

	private static boolean proveChildStatement(AbstractStatement statement, JavaVariables vars, GlobalConditions conds, Renaming renaming,
    		CbCFormula formula, String uri, IProgressMonitor monitor) {
		boolean prove = false;
		 if (statement instanceof SmallRepetitionStatement) {
			prove = proveSmallReptitionStatement(statement, vars, conds, renaming, formula, uri, monitor);
		} else if (statement instanceof CompositionStatement) {
			prove = proveCompositionStatement(statement, vars, conds, renaming, formula, uri, monitor);
		} else if (statement instanceof SelectionStatement) {
			prove = proveSelectionStatement(statement, vars, conds, renaming, formula, uri, monitor);
		} else if(statement instanceof ReturnStatement) {
			Console.println("-> Return Statement");
			prove = proveAbstractStatement(statement, vars, conds, true, renaming, formula, uri, monitor);
		} else if(statement.getComment() != null) {
			Console.println("-> Return Statement");
			if(statement.getComment().equals("returnStatement"))
				prove = proveAbstractStatement(statement, vars, conds, true, renaming, formula, uri, monitor);
		} else if (statement instanceof MethodStatement) {
			prove = proveMethodStatement(statement, vars, conds, false, renaming, formula, uri, monitor);
		} else if (statement instanceof OriginalStatement) {
			prove = proveOriginalStatement(statement, vars, conds, false, renaming, formula, uri, monitor);
		} else if (statement instanceof AbstractStatement) {
			prove = proveAbstractStatement(statement, vars, conds, false, renaming, formula, uri, monitor);
		}
		return prove;
	}

	private static boolean proveOriginalStatement(AbstractStatement statement, JavaVariables vars,
			GlobalConditions conds, boolean returnStatement, Renaming renaming, CbCFormula formula, String uri,
			IProgressMonitor monitor) {
		if (!isVariational)
			return false;
		if (!statement.isProven()) {
			boolean proven = false;
			URI uRi = URI.createPlatformResourceURI(uri, true);
			String callingFeature = uri.split("/")[3];
			String callingClass = uri.split("/")[4];
			String callingMethod = uri.split("/")[5].split("\\.")[0];
			String[][] featureConfigsRelevant = VerifyFeatures.verifyConfig(uRi, uRi.trimFileExtension().segment(uRi.segmentCount() - 1), true, callingClass, true);
			if (featureConfigsRelevant != null) {
				String[] variants = verifyStmt.generateVariantsStringFromFeatureConfigs(featureConfigsRelevant, callingFeature, callingClass);
				if (CompareMethodBodies.readAndTestMethodBodyWithJaMoPP2(statement.getName())) {
					ProveWithKey prove = new ProveWithKey(statement, vars, conds, renaming, monitor, uRi.toPlatformString(true), formula, new FileUtil(uRi.toPlatformString(true)), configName);
					List<CbCFormula> refinements = verifyStmt.generateCbCFormulasForRefinements(variants[configNum], callingMethod);
					List<JavaVariables> refinementsVars = verifyStmt.generateJavaVariablesForRefinements(variants[configNum], callingMethod);
					proven = prove.proveStatementWithKey(refinements, refinementsVars, returnStatement, false, callingMethod, "", callingClass, false);
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

	private static boolean proveMethodStatement(AbstractStatement statement, JavaVariables vars, GlobalConditions conds,
			boolean returnStatement, Renaming renaming, CbCFormula formula, String uri, IProgressMonitor monitor) {
		if (!statement.isProven()) {
			boolean proven = false;
			if (!isVariational) {
				ProveWithKey prove = new ProveWithKey(statement, vars, conds, renaming, monitor, uri, formula, new FileUtil(uri), "");
				proven = prove.proveStatementWithKey(returnStatement, false, URI.createPlatformResourceURI(uri, true).segment(URI.createPlatformResourceURI(uri, true).segmentCount() - 2), false);
			} else {
				URI uRi = URI.createPlatformResourceURI(uri, true);
				String callingFeature = uri.split("/")[3];
				String callingClass = uri.split("/")[4];
				String callingMethod = uri.split("/")[5].split("\\.")[0];
				String varM = handleVarM(Parser.extractMethodNameFromStatemtent(statement.getName()), callingClass, vars);
				String[][] featureConfigsRelevant = VerifyFeatures.verifyConfig(uRi, varM, false, callingClass, true);
				String varMParts[] = varM.split("\\.");

				if (featureConfigsRelevant != null) {
					String[] variants = verifyStmt.generateVariantsStringFromFeatureConfigs(featureConfigsRelevant,	callingFeature, varM.contains(".") ? varMParts[0] : callingClass);
					if (CompareMethodBodies.readAndTestMethodBodyWithJaMoPP2(statement.getName())) {
						ProveWithKey prove = new ProveWithKey(statement, vars, conds, renaming, monitor, uRi.toPlatformString(true), formula, new FileUtil(uRi.toPlatformString(true)), configName);
						List<CbCFormula> refinements = verifyStmt.generateCbCFormulasForRefinements(variants[configNum], varMParts[1].toLowerCase());
						List<JavaVariables> refinementsVars = verifyStmt.generateJavaVariablesForRefinements(variants[configNum], varMParts[1].toLowerCase());
						proven = prove.proveStatementWithKey(refinements, refinementsVars, returnStatement, false, callingMethod, varM, callingClass, false);
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

	private static boolean proveCompositionStatement(AbstractStatement statement, JavaVariables vars, GlobalConditions conds, Renaming renaming, 
    		CbCFormula formula, String uri, IProgressMonitor monitor) {
    	boolean prove1, prove2 = false;
    	CompositionStatement compositionStatement = (CompositionStatement) statement;
    	if (compositionStatement.getFirstStatement().getRefinement() != null) {
    		prove1 = proveChildStatement(compositionStatement.getFirstStatement().getRefinement(), vars, conds, renaming, formula, uri, monitor);
    	} else {
    		prove1 = proveChildStatement(compositionStatement.getFirstStatement(), vars, conds, renaming, formula, uri, monitor);
    	}
    	if (compositionStatement.getSecondStatement().getRefinement() != null) {
    		prove2 = proveChildStatement(compositionStatement.getSecondStatement().getRefinement(), vars, conds, renaming, formula, uri, monitor);
    	} else {
    		prove2 = proveChildStatement(compositionStatement.getSecondStatement(), vars, conds, renaming, formula, uri, monitor);
    	}
    	if (prove1 && prove2 && true && configs == configNum)  {
    		statement.setProven(true);
    	} else {
    		statement.setProven(false);//
    	}
		return (prove1 && prove2 && true);
    }

	private static boolean proveAbstractStatement(AbstractStatement statement, JavaVariables vars,
			GlobalConditions conds, boolean returnStatement, Renaming renaming, CbCFormula formula, String uri,
			IProgressMonitor monitor) {
		if (!statement.isProven()) {
			boolean proven = false;
			if (!isVariational) {
				ProveWithKey prove = new ProveWithKey(statement, vars, conds, renaming, monitor, uri, formula, new FileUtil(uri), "");
				proven = prove.proveStatementWithKey(returnStatement, false, URI.createPlatformResourceURI(uri, true).segment(URI.createPlatformResourceURI(uri, true).segmentCount() - 2), false);
			} else {
				URI uRi = URI.createPlatformResourceURI(uri, true);
				String callingFeature = uri.split("/")[3];
				String callingClass = uri.split("/")[4];
				String callingMethod = uri.split("/")[5].split("\\.")[0];
				String[][] featureConfigsRelevant = VerifyFeatures.verifyConfig(uRi, uRi.trimFileExtension().segment(uRi.segmentCount() - 1), true, callingClass, true);

				if (featureConfigsRelevant != null) {
					String[] variants = verifyStmt.generateVariantsStringFromFeatureConfigs(featureConfigsRelevant, callingFeature, callingClass);
					if (CompareMethodBodies.readAndTestMethodBodyWithJaMoPP2(statement.getName())) {
						ProveWithKey prove = new ProveWithKey(statement, vars, conds, renaming, monitor, uRi.toPlatformString(true), formula, new FileUtil(uRi.toPlatformString(true)), configName);
						List<CbCFormula> refinements = verifyStmt.generateCbCFormulasForRefinements(variants[configNum], callingMethod);
						List<JavaVariables> refinementsVars = verifyStmt.generateJavaVariablesForRefinements(variants[configNum], callingMethod);
						proven = prove.proveStatementWithKey(refinements, refinementsVars, returnStatement, false, callingMethod, "", callingClass, false);
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
    
    private static boolean proveSelectionStatement(AbstractStatement statement, JavaVariables vars, GlobalConditions conds, Renaming renaming, 
    		CbCFormula formula, String uri, IProgressMonitor monitor) {
    	boolean proven = true;
    	SelectionStatement selectionStatement = (SelectionStatement) statement;
		for (AbstractStatement childStatement : selectionStatement.getCommands()) {
			proven = (proveChildStatement(childStatement.getRefinement(), vars, conds, renaming, formula, uri, monitor) && proven && true);
		}
		boolean provePre = selectionStatement.isPreProve();
		if (!(selectionStatement.isProven() && provePre && true)) {
			if (!selectionStatement.isPreProve()) {
				EList<Condition> guards = selectionStatement.getGuards();
				Condition preCondition = selectionStatement.getParent().getPreCondition();
				ProveWithKey prove = new ProveWithKey(statement, vars, conds, renaming, monitor, uri, formula, new FileUtil(uri), configName);
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
	
	private static boolean proveSmallReptitionStatement(AbstractStatement statement, JavaVariables vars, GlobalConditions conds, Renaming renaming, 
			CbCFormula formula, String uri, IProgressMonitor monitor) {
		SmallRepetitionStatement repStatement = (SmallRepetitionStatement) statement;
		boolean proven = true;
		if (repStatement.getLoopStatement().getRefinement() != null) {
			proven = (proveChildStatement(repStatement.getLoopStatement().getRefinement(), vars, conds, renaming, formula, uri, null) && proven && true);
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
			ProveWithKey prove = new ProveWithKey(statement, vars, conds, renaming, monitor, uri, formula, new FileUtil(uri), configName);
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

	private boolean checkAllStatementsForId(AbstractStatement statement) {
		if (statement instanceof SmallRepetitionStatement) {
			SmallRepetitionStatement repStatement = (SmallRepetitionStatement) statement;
			if (!checkAllStatementsForId(repStatement.getLoopStatement())) return false;
		} else if (statement instanceof CompositionStatement) {
			CompositionStatement comStatement = (CompositionStatement) statement;
			if (!checkAllStatementsForId(comStatement.getFirstStatement())) return false;
			if (!checkAllStatementsForId(comStatement.getSecondStatement())) return false;
		} else if (statement instanceof SelectionStatement) {
			SelectionStatement selStatement = (SelectionStatement) statement;
			for (AbstractStatement childStatement : selStatement.getCommands()) {
				if (!checkAllStatementsForId(childStatement.getRefinement())) return false;
			}
		} else {
			if (!DataCollector.checkForId(statement)) return false;
		}
		return true;
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