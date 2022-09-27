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

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.OriginalStatement;

import de.tu_bs.cs.isf.cbc.cbcmodel.Rename;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.cbcmodel.ReturnStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SkipStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.StrengthWeakStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.AbstractStatementImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.CompositionStatementImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.MethodStatementImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.OriginalStatementImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.ReturnStatementImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.SelectionStatementImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.SkipStatementImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.SmallRepetitionStatementImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.StrengthWeakStatementImpl;
import de.tu_bs.cs.isf.cbc.tool.model.CbcModelUtil;

/**
 * Class that changes the abstract value of algorithms
 * @author Tobias
 *
 */
public class GenerateTextualRepresentation extends MyAbstractAsynchronousCustomFeature {
	 
    
    /**
     * Constructor of the class
     * @param fp	The FeatureProvider
     */
    public GenerateTextualRepresentation(IFeatureProvider fp) {
        super(fp);
    }
 
    @Override
    public String getName() {
        return "Generate textual representation";
    }
 
    @Override
    public String getDescription() {
        return "Generates a file containing the algorithm textual represented.";
    }
 
    @Override
    public boolean canExecute(ICustomContext context) {
        return true;
    }
 
    @Override
    public void execute(ICustomContext context, IProgressMonitor monitor) {
    	monitor.beginTask("Generate the file", IProgressMonitor.UNKNOWN);
    	URI uri = getDiagram().eResource().getURI();
		uri = uri.trimFragment();
		uri = uri.trimFileExtension();
		URI uriTextFile = uri;
		//add Text
		String lastSegment =  uriTextFile.segment(uriTextFile.segmentCount() - 1);
		uriTextFile = uriTextFile.trimSegments(1);
		uriTextFile = uriTextFile.appendSegment(lastSegment + "Textual");
		uriTextFile = uriTextFile.appendFileExtension("cbctxt");
		uri = uri.appendFileExtension("cbcmodel");
    	CbCFormula formula = CbcModelUtil.readFormula(uri);
    	JavaVariables vars = null;
		GlobalConditions conds = null;
		Renaming renaming = null;
		for (Shape shape : getDiagram().getChildren()) {
			Object obj = getBusinessObjectForPictogramElement(shape);
			if (obj instanceof JavaVariables) {
				vars = (JavaVariables) obj;
			} else if (obj instanceof GlobalConditions) {
				conds = (GlobalConditions) obj;
			} else if (obj instanceof Renaming) {
				renaming = (Renaming) obj;
			}
		}
    	
    	
        String content = generateContentOfFormula(formula);
        if (conds != null)
        	content += generateContentOfConditions(conds);
        if (vars != null)
        	content += generateContentOfVariables(vars);
        if (renaming != null)
        	content += generateContentOfRenames(renaming);
        	content = replaceSlashesInConditions(content);
        	
        String workspace = ResourcesPlugin.getWorkspace().getRoot().getLocation().toString();
//        String workspace = FileUtil.getProject(uriTextFile).getLocation() + "";
        File textFile = new File(workspace + uriTextFile.toPlatformString(true));
        writeFile(content, textFile);
		monitor.done();
    }
    
    private static void writeFile(String problem, File location) {
		try {
			location.createNewFile();
			FileWriter fw = new FileWriter(location);
			BufferedWriter bw = new BufferedWriter(fw);

			bw.write(problem);
			bw.close();
			IWorkspace workspace = ResourcesPlugin.getWorkspace();    
			IPath iLocation = Path.fromOSString(location.getAbsolutePath()); 
			IFile ifile = workspace.getRoot().getFileForLocation(iLocation);
			ifile.getParent().refreshLocal(1, null);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
    
    private String generateContentOfFormula(CbCFormula formula) {
    	StringBuffer buffer = new StringBuffer();
    	String tabs = "\t";
    	buffer.append("Formula \"" + formula.getName() + "\"\n");
    	buffer.append("pre: {\"" + formula.getStatement().getPreCondition().getName() + "\"}\n");
    	if (formula.getStatement().getRefinement() != null) {
    		buffer.append("{\n" + tabs + chooseStatement(tabs, formula.getStatement()) + "\n}\n");
    	} else {
    		buffer.append("{\n\"" + tabs + formula.getStatement().getName() + "\"\n" + tabs + "}\n");
    	}
    	
    	buffer.append("post: {\"" + formula.getStatement().getPostCondition().getName() + "\"}\n\n");
    	return buffer.toString();
    }
    
    private String printAbstractStatement(String tabs, AbstractStatement statement) {
    	if (statement.getRefinement() != null) {
    		return chooseStatement(tabs + "\t", statement);
    	} else {
    		return statement.getName();
    	}
    }
    
    private String printMethodStatement(String tabs, AbstractStatement statement) {
    		return "\"" + statement.getName() + "\"";
    }
    
    private String printSmallRepetitionStatement(String tabs, SmallRepetitionStatement statement) {
    	StringBuffer buffer = new StringBuffer();
		buffer.append("while (\"" + statement.getGuard().getName() + "\") do\n");
		buffer.append(tabs + "inv: [\"" + statement.getInvariant().getName() + "\"]");
		buffer.append(" var: [\"" + statement.getVariant().getName() + "\"]\n");
		buffer.append(tabs + "{\n"+ tabs + "\t");
		if (statement.getLoopStatement().getRefinement() != null) {
			buffer.append(chooseStatement(tabs + "\t", statement.getLoopStatement()));
    	} else {
    		buffer.append("\"" + statement.getLoopStatement().getName() + "\"");
    	}
    	buffer.append("\n" + tabs + "} od");
    	return buffer.toString();
		
	}
    
    private String printCompositionStatement(String tabs, CompositionStatement statement) {
    	StringBuffer buffer = new StringBuffer();
    	buffer.append("{\n" + tabs + "\t");
    	if (statement.getFirstStatement().getRefinement() != null) {
    		buffer.append(chooseStatement(tabs + "\t", statement.getFirstStatement()));
    	} else {
    		buffer.append("\"" + statement.getFirstStatement().getName() + "\"");
    	}
    	buffer.append("\n" + tabs + "}\n");
    	buffer.append(tabs + "intm: [\"" + statement.getIntermediateCondition().getName() + "\"]\n");
    	buffer.append(tabs + "{\n" + tabs + "\t");
    	if (statement.getSecondStatement().getRefinement() != null) {
    		buffer.append(chooseStatement(tabs + "\t", statement.getSecondStatement()));
    	} else {
    		buffer.append("\"" + statement.getSecondStatement().getName() + "\"");
    	}
    	buffer.append("\n" + tabs + "}");
    	return buffer.toString();
    }
    
    private String printSelectionStatement(String tabs, SelectionStatement statement) {
    	StringBuffer buffer = new StringBuffer();
    	if (!statement.getGuards().isEmpty()) {
    		buffer.append("if (\"" + statement.getGuards().get(0).getName() + "\") then\n");
    		buffer.append(tabs + "{\n"+ tabs + "\t");
    		if (statement.getCommands().get(0).getRefinement() != null) {
    			buffer.append(chooseStatement(tabs + "\t", statement.getCommands().get(0)));
        	} else {
        		buffer.append("\"" + statement.getCommands().get(0).getName() + "\"");
        	}
        	for (int i = 1; i < statement.getGuards().size(); i++) {
        		buffer.append("\n" + tabs + "} elseif (\"" + statement.getGuards().get(i).getName() + "\") then\n");
        		buffer.append(tabs + "{\n"+ tabs + "\t");
        		if (statement.getCommands().get(i).getRefinement() != null) {
        			buffer.append(chooseStatement(tabs + "\t", statement.getCommands().get(i)));
            	} else {
            		buffer.append("\"" + statement.getCommands().get(i).getName() + "\"");
            	}
        	}
        	buffer.append("\n" + tabs + "} fi");
    	}
    	return buffer.toString();
    }
    
    private String printSkipStatement(String tabs, SkipStatement statement) {
    		return ";";
    }
    
    private String printReturnStatement(String tabs, ReturnStatement statement) {
		return "return " + statement.getName();
    }
    
    private String printStrengthWeakStatement(String tabs, StrengthWeakStatement statement) {
    	StringBuffer buffer = new StringBuffer();
    	buffer.append("pre:{\"");
    	buffer.append(statement.getPreCondition().getName() + "\"}");
    	buffer.append("\n" + tabs + "{");
    	if(statement.getRefinement() != null) {
    		buffer.append(statement.getRefinement().getName());
    	}
    	buffer.append("}");
    	buffer.append("\n" + tabs + "post:{\"");
    	buffer.append(statement.getPostCondition().getName() + "\"}");
    	return buffer.toString();
	}
    
    private String printOriginalStatement(String tabs, OriginalStatement statement) {
    	StringBuffer buffer = new StringBuffer();
    	buffer.append("pre:{\"");
    	buffer.append(statement.getPreCondition().getName() + "\"}");
    	buffer.append("\n" + tabs + "{");
    	if(statement.getRefinement() != null) {
    		buffer.append(statement.getRefinement().getName());
    	}
    	buffer.append("}");
    	buffer.append("\n" + tabs + "post:{\"");
    	buffer.append(statement.getPostCondition().getName() + "\"}");
    	return buffer.toString();
	}
    
    private String chooseStatement(String tabs, AbstractStatement statement) {
    	statement = statement.getRefinement();
    	if (statement.getClass().equals(AbstractStatementImpl.class)) {
    		return printAbstractStatement(tabs, statement);
    	} else if (statement.getClass().equals(SkipStatementImpl.class)) {
    		return printSkipStatement(tabs, (SkipStatement) statement);
    	} else if (statement.getClass().equals(ReturnStatementImpl.class)) {
    		return printReturnStatement(tabs, (ReturnStatement) statement);
    	} else if (statement.getClass().equals(SelectionStatementImpl.class)) {
    		return printSelectionStatement(tabs, (SelectionStatement) statement);
    	} else if (statement.getClass().equals(CompositionStatementImpl.class)) {
    		return printCompositionStatement(tabs, (CompositionStatement) statement);
    	} else if (statement.getClass().equals(SmallRepetitionStatementImpl.class)) {
    		return printSmallRepetitionStatement(tabs, (SmallRepetitionStatement) statement);
    	} else if (statement.getClass().equals(StrengthWeakStatementImpl.class)) {
    		return printStrengthWeakStatement(tabs, (StrengthWeakStatement) statement);
    	} else if (statement.getClass().equals(OriginalStatementImpl.class)) {
    		return printOriginalStatement(tabs, (OriginalStatementImpl) statement);
    	} else if (statement.getClass().equals(MethodStatementImpl.class)) {
    		return printMethodStatement(tabs, (MethodStatementImpl) statement);
    	} else {
    		return "";
    	}
    }

	private String generateContentOfVariables(JavaVariables variables) {
    	StringBuffer buffer = new StringBuffer();
    	buffer.append("JavaVariables\n\tvariables {");
    	if (!variables.getVariables().isEmpty()) {
    		buffer.append("\"" + variables.getVariables().get(0).getName() + "\"");
    	}
    	for (int i = 1; i < variables.getVariables().size(); i++) {
    		JavaVariable variable = variables.getVariables().get(i);
    		buffer.append(", \"" + variable.getName() + "\"");
    	}
    	buffer.append("}\n\n");
    	return buffer.toString();
    }
    
    private String generateContentOfConditions(GlobalConditions conditions) {
    	StringBuffer buffer = new StringBuffer();
    	buffer.append("GlobalConditions\n\tconditions {");
    	if (!conditions.getConditions().isEmpty()) {
    		buffer.append("\"" + conditions.getConditions().get(0).getName() + "\"");
    	}
    	for (int i = 1; i < conditions.getConditions().size(); i++) {
    		Condition condition = conditions.getConditions().get(i);
    		buffer.append(", \"" + condition.getName() + "\"");
    	}
    	buffer.append("}\n\n");
    	return buffer.toString();
    }
    
    private String generateContentOfRenames(Renaming renaming) {
    	StringBuffer buffer = new StringBuffer();
    	buffer.append("Renaming\n\trenames {\n");
    	for (int i = 0; i < renaming.getRename().size(); i++) {
    		Rename rename = renaming.getRename().get(i);
    		buffer.append("\t\t{type \"" + rename.getType() + "\" function \"" + rename.getFunction() + "\" newName \"" + rename.getNewName() + "\"}\n");
    	}
    	buffer.append("\t}\n\n");
    	return buffer.toString();
    }
    
    private String replaceSlashesInConditions(String condition) {
    	condition = condition.replaceAll("\\\\forall", "\\\\\\\\forall");
    	return condition.replaceAll("\\\\exists", "\\\\\\\\exists");
    }
}