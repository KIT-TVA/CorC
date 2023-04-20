package de.tu_bs.cs.isf.commands.toolbar.handler.family;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.URI;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SkipStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;
import de.tu_bs.cs.isf.cbc.tool.model.CbcModelUtil;
import de.tu_bs.cs.isf.cbc.util.Console;

public class MethodStruct {
	String nameOfFeature, nameOfMethod, preCondition, postCondition;
	URI fullPlatFormPath;
	CbCFormula CbcFormula;
	JavaVariables javaVariables;
	GlobalConditions globalConditions;
	boolean callsOriginal;
	AbstractStatement originalCallingStatement;
	String[] featureVariables;
	
	public MethodStruct(String javaFileName, String nameOfFeature, String[] featureVariables, IResource methodFile){
		
		this.fullPlatFormPath = URI.createPlatformResourceURI(methodFile.getFullPath().toPortableString(), false);
		this.CbcFormula = CbcModelUtil.readFormula(fullPlatFormPath);
		this.javaVariables = CbcModelUtil.readJavaVariables(fullPlatFormPath);
		this.globalConditions = CbcModelUtil.readGlobalConditions(fullPlatFormPath);
		
		this.featureVariables = featureVariables;
		
		
		this.nameOfFeature = nameOfFeature;
		this.nameOfMethod = methodFile.getName().split("\\.")[0];
		this.preCondition = CbcFormula.getStatement().getPreCondition().getName();
		this.postCondition = CbcFormula.getStatement().getPostCondition().getName();
		AbstractStatement varCall = searchVarCall(this.CbcFormula.getStatement());
		
		//Adapt method Call to: <NameOfJavaFile>.<varMethodName>(<FV_1>, ..., <FV_N> , <Arg1>,..., <ArgN>);
		if(varCall != null) {
			  String paramString = "";
			  if (featureVariables != null) {
				  for (int i = 0 ; i < featureVariables.length; i++) {
					paramString += featureVariables[i];
					paramString += ", ";
				  }
			  }
			  String newName = varCall.getName().replaceAll("(\\w+)\\(", javaFileName + ".$1(" + paramString);
			  varCall.setName(newName);
		}
		
	}

	private AbstractStatement searchVarCall (AbstractStatement statement) {
		if (statement == null) {
			return null;
		}
		
		if( statement.getName().contains("(")
			&& !statement.getName().contains("original(")) {
			return statement;
		}
		
		AbstractStatement foundStatement = null;
		if (statement instanceof CompositionStatement) {
			foundStatement = searchVarCall(((CompositionStatement) statement).getFirstStatement());
			if(foundStatement == null) {
				foundStatement = searchVarCall(((CompositionStatement) statement).getSecondStatement());
			}
			
		}

		if (statement instanceof SelectionStatement) {
				
			for (AbstractStatement currentSelectionStatement : ((SelectionStatement) statement).getCommands() ) {
				
				if(foundStatement != null) {break;}
				foundStatement = searchVarCall(currentSelectionStatement);
			
			}
		}
		
		if (statement instanceof SmallRepetitionStatement) {
			
			//foundStatement = searchVarCall(((SmallRepetitionStatement) statement).getStartStatement()); 
			
			if(foundStatement == null) {
				foundStatement = searchVarCall(((SmallRepetitionStatement) statement).getLoopStatement());
			}
		}

		if (statement instanceof SmallRepetitionStatement) {
			foundStatement = searchVarCall(((SmallRepetitionStatement) statement).getLoopStatement());
		}
		
		if(statement instanceof SkipStatement) {
			return null;
		}

		if(foundStatement == null) {
			foundStatement = searchVarCall(statement.getRefinement());
		}
		
		return foundStatement;
		
	}
	
	
	public void printFeatureToConsole() {
		Console.println("------");
		Console.println("{");
		Console.println("\tFeature: \t"+ this.nameOfFeature);
		Console.println("\tMethod: \t"+ this.nameOfMethod);
		Console.println("\tPath: \t\t"+ this.fullPlatFormPath);
		Console.println("\tPre Condition: \t"+ this.preCondition.replaceAll("\\r\\n|\\r|\\n", " "));
		Console.println("\tPost Condition: "+ this.postCondition.replaceAll("\\r\\n|\\r|\\n", " "));
		Console.println("\tCalls Orignal: \t"+ this.callsOriginal);
		Console.println("}");
		Console.println("------");
	}
	
	
}
