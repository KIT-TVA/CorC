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
import de.tu_bs.cs.isf.cbc.cbcmodel.Composition3Statement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodClass;
import de.tu_bs.cs.isf.cbc.cbcmodel.ProductVariant;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.cbcmodel.ReturnStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.RepetitionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Variant;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.ConstructCodeBlock;
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
		prove = proveChildStatement(statement.getRefinement(), vars, conds, renaming, javaClass, getDiagram().eResource().getURI(), null);	
		if (prove) {
			statement.setProven(true);
		} else {
			statement.setProven(false);
		}
		Console.println("All statements verified");
    }
    
	private void writeFile(String location, String code) {
		File javaFile = new File(location);
		try {
			if (!javaFile.exists()) {
				javaFile.createNewFile();
			}
			FileWriter fw = new FileWriter(javaFile);
			BufferedWriter bw = new BufferedWriter(fw);

			bw.write(code);
			bw.close();
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IPath iLocation = Path.fromOSString(javaFile.getAbsolutePath());
			IFile ifile = workspace.getRoot().getFileForLocation(iLocation);
			ifile.refreshLocal(0, null);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
    
    
    private static boolean proveChildStatement(AbstractStatement statement, JavaVariables vars, GlobalConditions conds, Renaming renaming,
    		MethodClass javaClass, URI uri, IProgressMonitor monitor) {
		boolean prove = false;
		 if (statement instanceof SmallRepetitionStatement) {
			prove = proveSmallReptitionStatement(statement, vars, conds, renaming, javaClass, uri, monitor);
		} else if (statement instanceof CompositionStatement) {
			prove = proveCompositionStatement(statement, vars, conds, renaming, javaClass, uri, monitor);
		} else if (statement instanceof Composition3Statement) {
			prove = proveComposition3Statement(statement, vars, conds, renaming, javaClass, uri, monitor);
		} else if (statement instanceof SelectionStatement) {
			prove = proveSelectionStatement(statement, vars, conds, renaming, javaClass, uri, monitor);
		} else if (statement instanceof RepetitionStatement) {
			prove = proveRepetitionStatement(statement, vars, conds, renaming, javaClass, uri, monitor);
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
    		MethodClass javaClass,URI uri, IProgressMonitor monitor) {
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
    
    private static boolean proveComposition3Statement(AbstractStatement statement, JavaVariables vars, GlobalConditions conds, Renaming renaming, MethodClass javaClass, URI uri, IProgressMonitor monitor) {
    	boolean prove1, prove2, prove3 = false;
    	Composition3Statement compositionStatement = (Composition3Statement) statement;
    	if (compositionStatement.getFirstStatement().getRefinement() != null) {
    		prove1 = proveChildStatement(compositionStatement.getFirstStatement().getRefinement(), vars, conds, renaming, javaClass, uri, monitor);
    	} else {
    		prove1 = proveChildStatement(compositionStatement.getFirstStatement(), vars, conds, renaming, javaClass, uri, monitor);
    	}
    	if (compositionStatement.getSecondStatement().getRefinement() != null) {
    		prove2 = proveChildStatement(compositionStatement.getSecondStatement().getRefinement(), vars, conds, renaming, javaClass, uri, monitor);
    	} else {
    		prove2 = proveChildStatement(compositionStatement.getSecondStatement(), vars, conds, renaming, javaClass, uri, null);
    	}
    	if (compositionStatement.getThirdStatement().getRefinement() != null) {
    		prove3 = proveChildStatement(compositionStatement.getThirdStatement().getRefinement(), vars, conds, renaming, javaClass, uri, monitor);
    	} else {
    		prove3 = proveChildStatement(compositionStatement.getThirdStatement(), vars, conds, renaming, javaClass, uri, monitor);
    	}
    	if (prove1 && prove2 && prove3 && true) {
			statement.setProven(true);
		} else {
			statement.setProven(false);//
		}
		return (prove1 && prove2 && prove3 && true);
    }

	private static boolean proveAbstractStatement(AbstractStatement statement, JavaVariables vars, GlobalConditions conds, boolean returnStatement,
			Renaming renaming, MethodClass javaClass, URI uri, IProgressMonitor monitor) {
		if (!statement.isProven()) {
			boolean prove = false;
			String variants = null;
			//TODO für SPLs anpassen
			prove = ProveWithKey.proveStatementWithKey(statement, vars, conds, returnStatement, false, renaming, javaClass, variants, uri,0, monitor, "", "");
			if (prove) {
				statement.setProven(true);
			} else {
				statement.setProven(false);//
			}
	    	return prove;
		} else {
			Console.println("Abstract statement: " + statement.getName() +" already true");
			return true;
		}
    }
    
    private static boolean proveSelectionStatement(AbstractStatement statement, JavaVariables vars, GlobalConditions conds, Renaming renaming, 
    		MethodClass javaClass, URI uri, IProgressMonitor monitor) {
    	boolean prove = true;
    	SelectionStatement selectionStatement = (SelectionStatement) statement;
		for (AbstractStatement childStatement : selectionStatement.getCommands()) {
			prove = (proveChildStatement(childStatement.getRefinement(), vars, conds, renaming, javaClass, uri, monitor) && prove && true);
		}
		boolean provePre = selectionStatement.isPreProve();
		if (!(selectionStatement.isProven() && provePre && true)) {
			if (!selectionStatement.isPreProve()) {
				EList<Condition> guards = selectionStatement.getGuards();
				Condition preCondition = selectionStatement.getParent().getPreCondition();
				provePre = ProveWithKey.provePreSelWithKey(guards, preCondition, vars, javaClass, conds, renaming, uri, null);
				selectionStatement.setPreProve(provePre);
			}
			if (provePre && prove && true) {
				selectionStatement.setProven(true);
			} else {
				selectionStatement.setProven(false);//
			}
			return (prove && provePre && true);
    	} else {
    		Console.println("Selection statement already true");
    		return true;
    	}
    }

	private static boolean proveRepetitionStatement(AbstractStatement statement, JavaVariables vars, GlobalConditions conds, Renaming renaming,
			MethodClass javaClass, URI uri, IProgressMonitor monitor) {
		boolean prove = true;
		boolean provePre, provePost, proveVar = false;
		RepetitionStatement repStatement = (RepetitionStatement) statement;

		if (repStatement.getLoopStatement().getRefinement() != null) {
			prove = (proveChildStatement(repStatement.getLoopStatement().getRefinement(), vars, conds, renaming, javaClass, uri, monitor) && true);
		}
		if (repStatement.getStartStatement().getRefinement() != null) {
			prove = (proveChildStatement(repStatement.getStartStatement().getRefinement(), vars, conds, renaming, javaClass, uri, monitor) && prove && true);
		}
		if (repStatement.getEndStatement().getRefinement() != null) {
			prove = (proveChildStatement(repStatement.getEndStatement().getRefinement(), vars, conds, renaming, javaClass, uri, monitor) && prove && true);
		}
		if (!(repStatement.isVariantProven() && repStatement.isProven()) && true) {
			Condition invariant = repStatement.getInvariant();
			Condition preCondition = repStatement.getPreCondition();
			Condition guard = repStatement.getGuard();
			Condition postCondition = repStatement.getPostCondition();
			String code = ConstructCodeBlock.constructCodeBlockAndVerify3(statement);
			Variant variant = repStatement.getVariant();
			provePre = ProveWithKey.provePreWithKey(invariant, preCondition, vars, javaClass,conds, renaming, uri, monitor);
			provePost = ProveWithKey.provePostWithKey(invariant, guard, postCondition, javaClass, vars, conds, renaming, uri, monitor);
			proveVar = ProveWithKey.proveVariant2WithKey(code, invariant, guard, variant, javaClass, vars, conds, renaming, uri, monitor);
			repStatement.setVariantProven(proveVar);
			if (prove && provePre && provePost && proveVar && true) {
				statement.setProven(true);
			} else {
				statement.setProven(false);//
			}	
	    	return (provePre && provePost && proveVar && true);
		} else {
			repStatement.setVariantProven(true);
    		Console.println("Repetition statement already true");
    		return true;
		}
    }
	
	private static boolean proveSmallReptitionStatement(AbstractStatement statement, JavaVariables vars, GlobalConditions conds, Renaming renaming, 
			MethodClass javaClass, URI uri, IProgressMonitor monitor) {
		SmallRepetitionStatement repStatement = (SmallRepetitionStatement) statement;
		boolean prove = true;
		if (repStatement.getLoopStatement().getRefinement() != null) {
			prove = (proveChildStatement(repStatement.getLoopStatement().getRefinement(), vars, conds, renaming, javaClass, uri, null) && prove && true);
		}
		boolean provePre = repStatement.isPreProven();
		boolean provePost = repStatement.isPostProven();
		boolean proveVar = repStatement.isVariantProven();
		if (!(repStatement.isProven() && provePre && provePost && proveVar && true)) {
			Condition invariant = repStatement.getInvariant();
			Condition preCondition = repStatement.getParent().getPreCondition();
			Condition guard = repStatement.getGuard();
			Condition postCondition = repStatement.getParent().getPostCondition();
			String code = ConstructCodeBlock.constructCodeBlockAndVerify(statement);
			Variant variant = repStatement.getVariant();
			if (!provePre) {
				provePre = ProveWithKey.provePreWithKey(invariant, preCondition, vars, javaClass, conds, renaming, uri, monitor);
				repStatement.setPreProven(provePre);
			}
			if (!provePost) {
				provePost = ProveWithKey.provePostWithKey(invariant, guard, postCondition, javaClass, vars, conds, renaming, uri, monitor);
				repStatement.setPostProven(provePost);
			}
			if (!proveVar) {
				proveVar = ProveWithKey.proveVariant2WithKey(code, invariant, guard, variant, javaClass, vars, conds, renaming, uri, monitor);
				repStatement.setVariantProven(proveVar);	
			}
			if (prove && provePre && provePost && proveVar) {
				repStatement.setProven(true);
			} else {
				repStatement.setProven(false);//
			} 
	    	return prove;
		} else {
			repStatement.setPreProven(true);
			repStatement.setPostProven(true);
			repStatement.setVariantProven(true);
    		Console.println("SRepetition statement already true");
			return true;
		}
	}

}