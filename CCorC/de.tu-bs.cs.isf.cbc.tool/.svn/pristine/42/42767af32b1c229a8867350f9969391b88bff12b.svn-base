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
import org.eclipse.emf.common.util.URI;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.pictograms.Shape;

import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.util.ConstructCodeBlock;
import de.tu_bs.cs.isf.cbc.util.ProveWithKey;
import de.tu_bs.cs.isf.taxonomy.graphiti.features.MyAbstractAsynchronousCustomFeature;

/**
 * Class that changes the abstract value of algorithms
 * @author Tobias
 *
 */
public class PrintFormulaFeature extends MyAbstractAsynchronousCustomFeature {
	 
    
    /**
     * Constructor of the class
     * @param fp	The FeatureProvider
     */
    public PrintFormulaFeature(IFeatureProvider fp) {
        super(fp);
    }
 
    @Override
    public String getName() {
        return "Extract Code";
    }
 
    @Override
    public String getDescription() {
        return "Generates the code of the created algorithm.";
    }
 
    @Override
    public boolean canExecute(ICustomContext context) {
        return true;
    }
 
    @Override
    public void execute(ICustomContext context, IProgressMonitor monitor) {
//      printFormula(formula);
    	JavaVariables vars = null;
		Renaming renaming = null;
		CbCFormula formula = null;
		for (Shape shape : getDiagram().getChildren()) {
			Object obj = getBusinessObjectForPictogramElement(shape);
			if (obj instanceof JavaVariables) {
				vars = (JavaVariables) obj;
			} else if (obj instanceof Renaming) {
				renaming = (Renaming) obj;
			} else if (obj instanceof CbCFormula) {
				formula = (CbCFormula) obj;
			}
		}
		URI uri = getDiagram().eResource().getURI();
		String location = ProveWithKey.getProject(uri).getLocation() + "/src/Debug.java";
    	String code = ConstructCodeBlock.constructCodeBlockForExport(formula, renaming, vars);
    	writeFile(location, code);
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
    
//    private void printFormula(CbCFormula formula) {
//    	StringBuffer buffer = new StringBuffer();
//    	String tabs = "\t";
//    	buffer.append("Formula: " + formula.getName() + "\n");
//    	buffer.append("PreCondition: " + formula.getStatement().getPreCondition().getName() + "\n");
//    	if (formula.getStatement().getRefinement() != null) {
//    		chooseStatement(buffer, tabs, formula.getStatement());
//    	} else {
//    		buffer.append("Statement: " + formula.getStatement().getName() + "\n");
//    	}
//    	
//    	buffer.append("PostCondition: " + formula.getStatement().getPostCondition().getName() + "\n");
//    	System.out.println(buffer.toString());
//    }
//    
//    private void printAbstractStatement(StringBuffer buffer, String tabs, AbstractStatement statement) {
//    	if (statement.getRefinement() != null) {
//    		chooseStatement(buffer, tabs + "\t", statement);
//    	} else {
//    		buffer.append(statement.getName() + "\n");
//    	}
//    }
//    
//    private void printRepetitionStatement(StringBuffer buffer, String tabs, RepetitionStatement statement) {
//		buffer.append("RepetitionStatement" + "\n");
//		buffer.append(tabs + "Invariant: " + statement.getInvariant().getName() + "\n");
//		buffer.append(tabs + "Variant: " + statement.getVariant().getName() + "\n");
//		if (statement.getStartStatement().getRefinement() != null) {
//			buffer.append(tabs + "StartStatement: ");
//			chooseStatement(buffer, tabs + "\t", statement.getStartStatement());
//		} else {
//			buffer.append(tabs + "StartStatement: " + statement.getStartStatement().getName() + "\n");
//		}
//		buffer.append(tabs + "Guard: " + statement.getGuard().getName() + "\n");
//		if (statement.getLoopStatement().getRefinement() != null) {
//			buffer.append(tabs + "LoopStatement: ");
//			chooseStatement(buffer, tabs + "\t", statement.getLoopStatement());
//		} else {
//			buffer.append(tabs + "LoopStatement: " + statement.getLoopStatement().getName() + "\n");
//		}
//		if (statement.getEndStatement().getRefinement() != null) {
//			buffer.append(tabs + "EndStatement: ");
//			chooseStatement(buffer, tabs + "\t", statement.getEndStatement());
//		} else {
//			buffer.append(tabs + "EndStatement: " + statement.getEndStatement().getName() + "\n");
//		}
//		
//    }
//    
//    private void printCompositionStatement(StringBuffer buffer, String tabs, CompositionStatement statement) {
//    	buffer.append("CompositionStatement" + "\n");
//    	if (statement.getFirstStatement().getRefinement() != null) {
//    		buffer.append(tabs + "FirstStatement: ");
//    		chooseStatement(buffer, tabs + "\t", statement.getFirstStatement());
//    	} else {
//    		buffer.append(tabs + "FirstStatement: " + statement.getFirstStatement().getName() + "\n");
//    	}
//    	buffer.append(tabs + "IntermediateCondition: " + statement.getIntermediateCondition().getName() + "\n");
//    	if (statement.getSecondStatement().getRefinement() != null) {
//    		buffer.append(tabs + "SecondStatement: ");
//    		chooseStatement(buffer, tabs + "\t", statement.getSecondStatement());
//    	} else {
//    		buffer.append(tabs + "SecondStatement: " + statement.getSecondStatement().getName() + "\n");
//    	}
//    }
//    
//    private void printSelectionStatement(StringBuffer buffer, String tabs, SelectionStatement statement) {
//    	buffer.append("SelectionStatement" + "\n");
//    	for (int i = 0; i < statement.getGuards().size(); i++) {
//    		buffer.append(tabs + "Guard " + i + ": " + statement.getGuards().get(i).getName() + "\n");
//    		if (statement.getCommands().get(i).getRefinement() != null) {
//    			buffer.append(tabs + "SelectionStatement " + i + ": ");
//    			chooseStatement(buffer, tabs + "\t", statement.getCommands().get(i));
//        	} else {
//        		buffer.append(tabs + "SelectionStatement " + i + ": " + statement.getCommands().get(i).getName() + "\n");
//        	}
//    	}
//    }
//    
//    private void printSkipStatement(StringBuffer buffer, String tabs, SkipStatement statement) {
//    		buffer.append("Skip" + "\n");
//    }
//    
//    
//    private void chooseStatement(StringBuffer buffer, String tabs, AbstractStatement statement) {
//    	statement = statement.getRefinement();
//    	if (statement.getClass().equals(AbstractStatementImpl.class)) {
//    		printAbstractStatement(buffer, tabs, statement);
//    	} else if (statement.getClass().equals(SkipStatementImpl.class)) {
//    		printSkipStatement(buffer, tabs, (SkipStatement) statement);
//    	} else if (statement.getClass().equals(ReturnStatementImpl.class)) {
//    		printReturnStatement(buffer, tabs, (ReturnStatement) statement);
//    	} else if (statement.getClass().equals(MethodStatementImpl.class)) {
//    		printMethodStatement(buffer, tabs, (MethodStatement) statement);
//    	} else if (statement.getClass().equals(SelectionStatementImpl.class)) {
//    		printSelectionStatement(buffer, tabs, (SelectionStatement) statement);
//    	} else if (statement.getClass().equals(CompositionStatementImpl.class)) {
//    		printCompositionStatement(buffer, tabs, (CompositionStatement) statement);
//    	} else if (statement.getClass().equals(RepetitionStatementImpl.class)) {
//    		printRepetitionStatement(buffer, tabs, (RepetitionStatement) statement);
//    	}
//    	
//    }
//
//	private void printMethodStatement(StringBuffer buffer, String tabs, MethodStatement statement) {
//		buffer.append(statement.getName() + ";\n");
//	}
//
//	private void printReturnStatement(StringBuffer buffer, String tabs, ReturnStatement statement) {
//    	buffer.append(statement.getName() + "\n");
//	}
}