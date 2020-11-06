package de.tu_bs.cs.isf.cbc.tool.features;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
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
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodClass;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.cbcmodel.ReturnStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Variant;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.ConstructCodeBlock;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.cbc.util.ProveWithKey;


/**
 * Class that changes the abstract value of algorithms
 * @author Tobias
 *
 */
public class VerifyAllStatements extends MyAbstractAsynchronousCustomFeature {
	 
    
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
    	JavaVariables vars = null;
		Renaming renaming = null;
		CbCFormula formula = null;
		GlobalConditions conds = null;
		MethodClass javaClass = null;
		
		for (Shape shape : getDiagram().getChildren()) {
			Object obj = getBusinessObjectForPictogramElement(shape);
			if (obj instanceof JavaVariables) {
				vars = (JavaVariables) obj;
			} else if (obj instanceof Renaming) {
				renaming = (Renaming) obj;
			} else if (obj instanceof CbCFormula) {
				formula = (CbCFormula) obj;
			} else if (obj instanceof GlobalConditions) {
				conds = (GlobalConditions) obj;
			} else if(obj instanceof MethodClass) {
				javaClass = (MethodClass) obj;
			}
		}
		AbstractStatement statement = formula.getStatement();
		boolean prove = false;
		String uriString = getDiagram().eResource().getURI().toPlatformString(true);
		prove = proveChildStatement(statement.getRefinement(), vars, conds, renaming, javaClass, uriString, null);	
		if (prove) {
			statement.setProven(true);
		} else {
			statement.setProven(false);
		}
		Console.println("All statements verified");
    }
    
    
    private static boolean proveChildStatement(AbstractStatement statement, JavaVariables vars, GlobalConditions conds, Renaming renaming,
    		MethodClass javaClass, String uri, IProgressMonitor monitor) {
		boolean prove = false;
		 if (statement instanceof SmallRepetitionStatement) {
			prove = proveSmallReptitionStatement(statement, vars, conds, renaming, javaClass, uri, monitor);
		} else if (statement instanceof CompositionStatement) {
			prove = proveCompositionStatement(statement, vars, conds, renaming, javaClass, uri, monitor);
		} else if (statement instanceof SelectionStatement) {
			prove = proveSelectionStatement(statement, vars, conds, renaming, javaClass, uri, monitor);
		} else if(statement instanceof ReturnStatement) {
			Console.println("+++++++++++++++++++++++++++++++++Return Statement++++++++++++++++++++++++++++++++++");
			prove = proveAbstractStatement(statement, vars, conds, true, renaming, javaClass, uri, monitor);
		} else if(statement.getComment() != null) {
			Console.println("+++++++++++++++++++++++++++++++++Return Statement++++++++++++++++++++++++++++++++++");
			if(statement.getComment().equals("returnStatement"))
				prove = proveAbstractStatement(statement, vars, conds, true, renaming, javaClass, uri, monitor);
		} else if (statement instanceof AbstractStatement) {
			prove = proveAbstractStatement(statement, vars, conds, false, renaming, javaClass, uri, monitor);
		}
		return prove;
	}

//    if (refinement.getClass().equals(AbstractStatementImpl.class)) {
//		String allStatements =  refinement.getName().replace("\n", "");
//		}
//		return statements;
//		//return refinement.getName()  + "\n";
//	} else if (refinement.getClass().equals(SkipStatementImpl.class)) {
//		return ";\n";
//	} else if (refinement.getClass().equals(ReturnStatementImpl.class)) {
//		return "return " + refinement.getName()  + "\n";
//	} else if (refinement.getClass().equals(MethodStatementImpl.class)) {
//		return refinement.getName()  + "();\n";
//	}else if (refinement.getClass().equals(SelectionStatementImpl.class)) {
//		return constructSelection((SelectionStatement) refinement);
//	} else if (refinement.getClass().equals(CompositionStatementImpl.class)) {
//		return constructComposition((CompositionStatement) refinement);
//	} else if (refinement.getClass().equals(Composition3StatementImpl.class)) {
//		return constructComposition3((Composition3Statement) refinement);
//	} else if (refinement.getClass().equals(RepetitionStatementImpl.class)) {
//		return constructRepetition((RepetitionStatement) refinement);
//	} else if (refinement.getClass().equals(SmallRepetitionStatementImpl.class)) {
//		return constructSmallRepetition((SmallRepetitionStatement) refinement);
//	} else if (refinement.getClass().equals(StrengthWeakStatementImpl.class)) {
//		if (refinement.getRefinement() != null) {
//			return constructCodeBlockOfChildStatement(refinement.getRefinement());
//		} else {
//			return refinement.getName() + ";\n";
//		}
//	}
//	return null;
    
    private static boolean proveCompositionStatement(AbstractStatement statement, JavaVariables vars, GlobalConditions conds, Renaming renaming, 
    		MethodClass javaClass, String uri, IProgressMonitor monitor) {
    	boolean prove1, prove2 = false;
    	CompositionStatement compositionStatement = (CompositionStatement) statement;
    	if (compositionStatement.getFirstStatement().getRefinement() != null) {
    		prove1 = proveChildStatement(compositionStatement.getFirstStatement().getRefinement(), vars, conds, renaming, javaClass, uri, monitor);
    	} else {
    		prove1 = proveChildStatement(compositionStatement.getFirstStatement(), vars, conds, renaming, javaClass, uri, monitor);
    	}
    	if (compositionStatement.getSecondStatement().getRefinement() != null) {
    		prove2 = proveChildStatement(compositionStatement.getSecondStatement().getRefinement(), vars, conds, renaming, javaClass, uri, monitor);
    	} else {
    		prove2 = proveChildStatement(compositionStatement.getSecondStatement(), vars, conds, renaming, javaClass, uri, monitor);
    	}
    	if (prove1 && prove2 && true)  {
    		statement.setProven(true);
    	} else {
    		statement.setProven(false);//
    	}
		return (prove1 && prove2 && true);
    }
	private static boolean proveAbstractStatement(AbstractStatement statement, JavaVariables vars, GlobalConditions conds, boolean returnStatement,
			Renaming renaming, MethodClass javaClass, String uri, IProgressMonitor monitor) {
		if (!statement.isProven()) {
			boolean proven = false;
			String variants = null;
			//TODO für SPLs anpassen
			ProveWithKey prove = new ProveWithKey(statement, vars, conds, renaming, monitor, uri, null, new FileUtil(uri));
			proven = prove.proveStatementWithKey(returnStatement, false, 0);
			if (proven) {
				statement.setProven(true);
			} else {
				statement.setProven(false);//
			}
	    	return proven;
		} else {
			Console.println("Abstract statement: " + statement.getName() +" already true");
			return true;
		}
    }
    
    private static boolean proveSelectionStatement(AbstractStatement statement, JavaVariables vars, GlobalConditions conds, Renaming renaming, 
    		MethodClass javaClass, String uri, IProgressMonitor monitor) {
    	boolean proven = true;
    	SelectionStatement selectionStatement = (SelectionStatement) statement;
		for (AbstractStatement childStatement : selectionStatement.getCommands()) {
			proven = (proveChildStatement(childStatement.getRefinement(), vars, conds, renaming, javaClass, uri, monitor) && proven && true);
		}
		boolean provePre = selectionStatement.isPreProve();
		if (!(selectionStatement.isProven() && provePre && true)) {
			if (!selectionStatement.isPreProve()) {
				EList<Condition> guards = selectionStatement.getGuards();
				Condition preCondition = selectionStatement.getParent().getPreCondition();
				ProveWithKey prove = new ProveWithKey(statement, vars, conds, renaming, monitor, uri, null, new FileUtil(uri));
				provePre = prove.provePreSelWithKey(guards, preCondition);
				selectionStatement.setPreProve(provePre);
			}
			if (provePre && proven && true) {
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
			MethodClass javaClass, String uri, IProgressMonitor monitor) {
		SmallRepetitionStatement repStatement = (SmallRepetitionStatement) statement;
		boolean proven = true;
		if (repStatement.getLoopStatement().getRefinement() != null) {
			proven = (proveChildStatement(repStatement.getLoopStatement().getRefinement(), vars, conds, renaming, javaClass, uri, null) && proven && true);
		}
		boolean provePre = repStatement.isPreProven();
		boolean provePost = repStatement.isPostProven();
		boolean proveVar = repStatement.isVariantProven();
		if (!(repStatement.isProven() && provePre && provePost && proveVar && true)) {
			Condition invariant = repStatement.getInvariant();
			Condition preCondition = repStatement.getParent().getPreCondition();
			Condition guard = repStatement.getGuard();
			Condition postCondition = repStatement.getParent().getPostCondition();
			String code = ConstructCodeBlock.constructCodeBlockAndVerify(statement, true);
			Variant variant = repStatement.getVariant();
			ProveWithKey prove = new ProveWithKey(statement, vars, conds, renaming, monitor, uri, null, new FileUtil(uri));
			if (!provePre) {
				provePre = prove.proveCImpliesCWithKey(preCondition, invariant);
				repStatement.setPreProven(provePre);
			}
			if (!provePost) {
				provePost = prove.provePostRepetitionWithKey(invariant, guard, postCondition);
				repStatement.setPostProven(provePost);
			}
			if (!proveVar) {
				proveVar = prove.proveVariantWithKey(code, invariant, guard, variant);
				repStatement.setVariantProven(proveVar);	
			}
			if (proven && provePre && provePost && proveVar) {
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