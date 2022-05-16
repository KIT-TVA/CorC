package de.tu_bs.cs.isf.cbc.tool.features;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

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
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.pictograms.Shape;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.cbcmodel.ReturnStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Variant;
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
	static int proveFileNumber = 0;
    static int configNum = 0;
    static int configs = 0;
    static boolean isVariational = false;
    static String configName = "";
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
		proveFileNumber = 0;
		AbstractStatement statement = formula.getStatement();
		String uriString = getDiagram().eResource().getURI().toPlatformString(true);
		URI uri = getDiagram().eResource().getURI();
		IProject project = FileUtil.getProjectFromFileInProject(uri);
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
			VerifyStatement verifyStmt = new VerifyStatement(super.getFeatureProvider());	
			configs = featureConfigs.length-1;
			for (int i = 0; i < featureConfigs.length; i++) {
				configName = "";
				for (String s : featureConfigs[i]) configName += s;
				configNum = i;
				verifyStmt.generate(FileUtil.getProjectFromFileInProject(getDiagram().eResource().getURI()).getLocation(), callingFeature, callingClass, callingMethod, featureConfigs[i]);
				if (!proveChildStatement(statement.getRefinement(), vars, conds, renaming, formula, uriString, null, verifyStmt)) {
					statement.setProven(false);
					return;
				}
			}
		} else {
			Console.println("--------------- Triggered verification ---------------");
			if (!proveChildStatement(statement.getRefinement(), vars, conds, renaming, formula, uriString, null, null)) {
				statement.setProven(false);
				return;
			}
		}		
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1000000;
		Console.println("--------------- Verification completed --------------- " + duration + "ms");
    }    
    
    private static boolean proveChildStatement(AbstractStatement statement, JavaVariables vars, GlobalConditions conds, Renaming renaming,
    		CbCFormula formula, String uri, IProgressMonitor monitor, VerifyStatement verifyStmt) {
		boolean prove = false;
		 if (statement instanceof SmallRepetitionStatement) {
			prove = proveSmallReptitionStatement(statement, vars, conds, renaming, formula, uri, monitor, verifyStmt);
		} else if (statement instanceof CompositionStatement) {
			prove = proveCompositionStatement(statement, vars, conds, renaming, formula, uri, monitor, verifyStmt);
		} else if (statement instanceof SelectionStatement) {
			prove = proveSelectionStatement(statement, vars, conds, renaming, formula, uri, monitor, verifyStmt);
		} else if(statement instanceof ReturnStatement) {
			Console.println("-> Return Statement");
			prove = proveAbstractStatement(statement, vars, conds, true, renaming, formula, uri, monitor, verifyStmt);
		} else if(statement.getComment() != null) {
			Console.println("-> Return Statement");
			if(statement.getComment().equals("returnStatement"))
				prove = proveAbstractStatement(statement, vars, conds, true, renaming, formula, uri, monitor, verifyStmt);
		} else if (statement instanceof AbstractStatement) {
			prove = proveAbstractStatement(statement, vars, conds, false, renaming, formula, uri, monitor, verifyStmt);
		}
		return prove;
	}

    private static boolean proveCompositionStatement(AbstractStatement statement, JavaVariables vars, GlobalConditions conds, Renaming renaming, 
    		CbCFormula formula, String uri, IProgressMonitor monitor, VerifyStatement verifyStmt) {
    	boolean prove1, prove2 = false;
    	CompositionStatement compositionStatement = (CompositionStatement) statement;
    	if (compositionStatement.getFirstStatement().getRefinement() != null) {
    		prove1 = proveChildStatement(compositionStatement.getFirstStatement().getRefinement(), vars, conds, renaming, formula, uri, monitor, verifyStmt);
    	} else {
    		prove1 = proveChildStatement(compositionStatement.getFirstStatement(), vars, conds, renaming, formula, uri, monitor, verifyStmt);
    	}
    	if (compositionStatement.getSecondStatement().getRefinement() != null) {
    		prove2 = proveChildStatement(compositionStatement.getSecondStatement().getRefinement(), vars, conds, renaming, formula, uri, monitor, verifyStmt);
    	} else {
    		prove2 = proveChildStatement(compositionStatement.getSecondStatement(), vars, conds, renaming, formula, uri, monitor, verifyStmt);
    	}
    	if (prove1 && prove2 && true && configs == configNum)  {
    		statement.setProven(true);
    	} else {
    		statement.setProven(false);//
    	}
		return (prove1 && prove2 && true);
    }
	private static boolean proveAbstractStatement(AbstractStatement statement, JavaVariables vars, GlobalConditions conds, boolean returnStatement,
			Renaming renaming, CbCFormula formula, String uri, IProgressMonitor monitor, VerifyStatement verifyStmt) {
			// check if there exists a file with this hash (AND its is proven) it is needed to:
				// check if file with same problem hash exists (get files in prove folder and compare with hashes in DB for same file names) 
				// -> PROBLEM: we do not have the problem to hash at this point
					// check is only possible later in program flow (at least without major changes to program flow)
				// check if statement prove was closed and only then do not check again 
				// but: if not closed and hash is the same it may also do not need a second verification 
					// -> still false
			// if criteria match -> already true AND match KeY file path with CorC node
		if (!statement.isProven()) {
			boolean proven = false;
		if (!isVariational) {
			ProveWithKey prove = new ProveWithKey(statement, vars, conds, renaming, monitor, uri, formula, new FileUtil(uri), "");
			proven = prove.proveStatementWithKey(returnStatement, false, proveFileNumber, URI.createPlatformResourceURI(uri, true).segment( URI.createPlatformResourceURI(uri, true).segmentCount() - 2), false);
		} else {
			URI uRi = URI.createPlatformResourceURI(uri, true);
			String callingFeature = uri.split("/")[3];
			String callingClass = uri.split("/")[4];
			String callingMethod = uri.split("/")[5].split("\\.")[0];
			String stmt = statement.getName();
			String varM = "";
			String[][] featureConfigsRelevant = null;
			if (!stmt.contains("original(") && stmt.matches(".*\\(.*\\).*")) { // contains method call and not original
				varM = verifyStmt.handleVarM(Parser.extractMethodNameFromStatemtent(stmt), callingClass, vars);
				if (varM.endsWith("."))	varM = "";
				featureConfigsRelevant = VerifyFeatures.verifyConfig(uRi, varM, false, callingClass, true);
			} else { // original or normal statement
				featureConfigsRelevant = VerifyFeatures.verifyConfig(uRi, uRi.trimFileExtension().segment(uRi.segmentCount() - 1), true, callingClass, true);
			}
			String varMParts[] = varM.split("\\.");
			if (featureConfigsRelevant != null) {
				String[] variants = verifyStmt.generateVariantsStringFromFeatureConfigs(featureConfigsRelevant, callingFeature, varM.contains(".") ? varMParts[0] : callingClass);
				if (CompareMethodBodies.readAndTestMethodBodyWithJaMoPP2(statement.getName())) {
					ProveWithKey prove = new ProveWithKey(statement, vars, conds, renaming, monitor, uRi.toPlatformString(true), formula, new FileUtil(uRi.toPlatformString(true)), configName);
						if (!varM.equals("")) {
							List<CbCFormula> refinements = verifyStmt.generateCbCFormulasForRefinements(variants[configNum], varMParts[1].toLowerCase());
							List<JavaVariables> refinementsVars = verifyStmt.generateJavaVariablesForRefinements(variants[configNum], varMParts[1].toLowerCase());
							proven = prove.proveStatementWithKey(refinements, refinementsVars, returnStatement, false, proveFileNumber++, callingMethod, varM, callingClass, false);
						} else {
							List<CbCFormula> refinements = verifyStmt.generateCbCFormulasForRefinements(variants[configNum], callingMethod);
							List<JavaVariables> refinementsVars = verifyStmt.generateJavaVariablesForRefinements(variants[configNum], callingMethod);
							proven = prove.proveStatementWithKey(refinements, refinementsVars, returnStatement, false, proveFileNumber++, callingMethod, varMParts[0], callingClass, false);
						}
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
			Console.println("Abstract statement: " + statement.getName().replaceAll("\n", "").replaceAll("\r", "") +" already true");
			return true;
		}
    }
    
    private static boolean proveSelectionStatement(AbstractStatement statement, JavaVariables vars, GlobalConditions conds, Renaming renaming, 
    		CbCFormula formula, String uri, IProgressMonitor monitor, VerifyStatement verifyStmt) {
    	boolean proven = true;
    	SelectionStatement selectionStatement = (SelectionStatement) statement;
		for (AbstractStatement childStatement : selectionStatement.getCommands()) {
			proven = (proveChildStatement(childStatement.getRefinement(), vars, conds, renaming, formula, uri, monitor, verifyStmt) && proven && true);
		}
		boolean provePre = selectionStatement.isPreProve();
		if (!(selectionStatement.isProven() && provePre && true)) {
			if (!selectionStatement.isPreProve()) {
				EList<Condition> guards = selectionStatement.getGuards();
				Condition preCondition = selectionStatement.getParent().getPreCondition();
				ProveWithKey prove = new ProveWithKey(statement, vars, conds, renaming, monitor, uri, formula, new FileUtil(uri), configName);
				provePre = prove.provePreSelWithKey(guards, preCondition, proveFileNumber++);
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
			CbCFormula formula, String uri, IProgressMonitor monitor, VerifyStatement verifyStmt) {
		SmallRepetitionStatement repStatement = (SmallRepetitionStatement) statement;
		boolean proven = true;
		if (repStatement.getLoopStatement().getRefinement() != null) {
			proven = (proveChildStatement(repStatement.getLoopStatement().getRefinement(), vars, conds, renaming, formula, uri, null, verifyStmt) && proven && true);
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
				provePre = prove.proveCImpliesCWithKey(preCondition, invariant, proveFileNumber++);
				if (configs == configNum) repStatement.setPreProven(provePre);
			}
			if (!provePost) {
				provePost = prove.provePostRepetitionWithKey(invariant, guard, postCondition, proveFileNumber++);
				if (configs == configNum) repStatement.setPostProven(provePost);
			}
			if (!proveVar) {
				proveVar = prove.proveVariantWithKey(code, invariant, guard, variant, proveFileNumber++);
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

}