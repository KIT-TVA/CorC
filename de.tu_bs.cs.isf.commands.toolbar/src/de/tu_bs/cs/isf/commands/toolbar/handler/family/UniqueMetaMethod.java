package de.tu_bs.cs.isf.commands.toolbar.handler.family;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import de.ovgu.featureide.fm.core.base.IFeature;
import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.OriginalStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.ReturnStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SkipStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.VariableKind;
import de.tu_bs.cs.isf.cbc.tool.helper.UpdateConditionsOfChildren;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;

public class UniqueMetaMethod {
	String metaMethodName; 
	String metaPreConditon, metaPostCondition;
	String featureModelFormulaCNF;
	List<MethodStruct> listOfMethods;
	ArrayList<IFeature> featureVariables;
	String[] hierachicalComposition;
	List<String> featureOrderList;
	List<String> namesOfImplementingMethods; 
	MethodStruct[] methodsInDescendingOrder;
	MethodStruct currentMethod;
	int currentMethodIndex = 0;
	CbCFormula metaMethodFormula;
	JavaVariables metaJavaVariables;
	String metaClassName;
	URI uriToRootProject;
	
	
	public UniqueMetaMethod(URI uriToRootProject, String className, String methodName, 
			List<MethodStruct> listOfMethods, 			//All different implementations of Method.
			String featureModelFormulaCNF, 
			ArrayList<IFeature> features, 				//Name of all features in Productline. Necessary to introduce Features Variables.
			List<String> featureOrderList,				
			List<String> featuresImplementingMethods) { //Name of all Features implementing the method
		
		this.metaClassName = className;
		this.metaMethodName = methodName;
		this.featureModelFormulaCNF = featureModelFormulaCNF;
		this.listOfMethods = listOfMethods;
		this.namesOfImplementingMethods = featuresImplementingMethods;
		
		this.uriToRootProject = uriToRootProject;
		
		this.featureOrderList = featureOrderList;

		this.hierachicalComposition = calculateHierarchie();
		
		this.methodsInDescendingOrder = bringMethodsToDescendingOrder();
		this.currentMethod = this.methodsInDescendingOrder[currentMethodIndex];
		
		//this.metaPreConditon = calculateMetaPreCondition(hierachicalComposition, 0);
		this.metaPreConditon = calculateMetaPreCondition3(0);
		this.metaPostCondition = calculateMetaPostCondition3(0);
		
		//ALLE \modifiable aus den conditions entfernen.
		this.metaPreConditon = this.metaPreConditon.replaceAll("modifiable\\([^;]+;", "");
		this.metaPostCondition = this.metaPostCondition.replaceAll("modifiable\\([^\\;]+\\;", "");
		
		//this.metaPostCondition = calculateMetaPostCondition(hierachicalComposition, 0);
		this.featureVariables = features;
	
		
		
		Console.println("Generated meta method for method " + this.metaMethodName + ":");
		Console.println("{");
		Console.println("\tFeature Model: " +this.featureModelFormulaCNF);
		Console.println("\tMeta Pre Condition: " + this.metaPreConditon.replaceAll("\\r\\n|\\r|\\n", " "));
		Console.println("\tMeta Post Condition: " + this.metaPostCondition.replaceAll("\\r\\n|\\r|\\n", " "));
		Console.println("\t Resolved Methods:");
		for(MethodStruct method: this.listOfMethods) {
			Console.println("\t\t Feature: " +method.nameOfFeature);
		}
		Console.println("}");
		
		for(int i = 0 ; i < this.listOfMethods.size(); i++) {
			if(this.listOfMethods.get(i).preCondition.contains("original")) {
				this.listOfMethods.get(i).preCondition = this.listOfMethods.get(i).preCondition.replaceAll("original", this.calculateMetaPreCondition(this.hierachicalComposition, 0));
			}
			
			if(this.listOfMethods.get(i).postCondition.contains("original")) {
				this.listOfMethods.get(i).postCondition = this.listOfMethods.get(i).postCondition.replaceAll("original", this.calculateMetaPostCondition(this.hierachicalComposition, 0));
			}
		}
	}
	
	private MethodStruct[] bringMethodsToDescendingOrder() {
		MethodStruct [] methods = new MethodStruct[this.listOfMethods.size()];
		int listIndex = 0;
		
		for(String currentFeautureName: this.hierachicalComposition) {
			for(MethodStruct currentMethod: this.listOfMethods) {
				if(listIndex < methods.length && currentFeautureName.equals(currentMethod.nameOfFeature)) {
					methods[listIndex++]  = currentMethod; 
				}
			}
		}
		
		return methods;
	}
	
	//Returns Name of Features in reversed Order defined by Feature Model
	private String[] calculateHierarchie() { //->Funktioniert
		
		String[] newHiearchie = new String[this.namesOfImplementingMethods.size()];
		int listIndex = 0;
		
		//Sort implementing Features by Feature Model rank 
		for(String currentFeature: this.featureOrderList) {
			for(String currentImplementingFeature: this.namesOfImplementingMethods) {
				if(currentFeature.equals(currentImplementingFeature)) {
					newHiearchie[listIndex++] = currentImplementingFeature;
				}
			}
		}
		//reverse Feature List to start with highest Rank.
		for (int i = 0; i < newHiearchie.length / 2; i++) {
			String temp = newHiearchie[i];
			newHiearchie[i] = newHiearchie[newHiearchie.length - 1 - i];
			newHiearchie[newHiearchie.length - 1 - i] = temp;
		}
		
		String hierachialChain = "";
		for(String feature: newHiearchie) {
			hierachialChain += feature + "->";
		}
		Console.println(this.metaMethodName + ": "+hierachialChain);
		
		return newHiearchie;
	}
	
	String calculateMetaPreCondition2 (int index){
		if(index == this.methodsInDescendingOrder.length -1){
			MethodStruct currentMethod = methodsInDescendingOrder[index];
			return "(" + currentMethod.preCondition + ")";
		}
		MethodStruct currentMethod = this.methodsInDescendingOrder[index];
			if(index == 0) {
				return "(FV_"+currentMethod.nameOfFeature.toUpperCase() +" = TRUE" +" -> "  + currentMethod.preCondition + ")" + " & "
						   +"(FV_"+currentMethod.nameOfFeature.toUpperCase() + " = FALSE " + " -> " + calculateMetaPreCondition2(index + 1) + ")";
			}else {
				return "(FV_"+currentMethod.nameOfFeature.toUpperCase() +" = TRUE" +"->"  + currentMethod.preCondition + " & "
						   +"FV_"+currentMethod.nameOfFeature.toUpperCase() + " = FALSE " + " -> " + calculateMetaPreCondition2(index + 1) + ")";
			}
		
	}
	
	String calculateMetaPostCondition2 (int index){
		if(index == this.methodsInDescendingOrder.length -1){
			MethodStruct currentMethod = methodsInDescendingOrder[index];
			return "(" + currentMethod.postCondition+ ")";
		}
		MethodStruct currentMethod = this.methodsInDescendingOrder[index];
			if(index == 0) {
				return "(FV_"+currentMethod.nameOfFeature.toUpperCase() +" = TRUE" +" -> "  + currentMethod.postCondition + ")" + " & "
						   +"(FV_"+currentMethod.nameOfFeature.toUpperCase() + " = FALSE " + " -> " + calculateMetaPostCondition2(index + 1) + ")";
			}else {
				return "(FV_"+currentMethod.nameOfFeature.toUpperCase() +" = TRUE" +" -> "  + currentMethod.postCondition + " & "
						   +"FV_"+currentMethod.nameOfFeature.toUpperCase() + " = FALSE " + " -> " + calculateMetaPostCondition2(index + 1) + ")";
			}
		
	}
	
	String calculateMetaPostCondition3 (int index){
		if(index == this.methodsInDescendingOrder.length -1){
			MethodStruct currentMethod = methodsInDescendingOrder[index];
			return "(FV_" + currentMethod.nameOfFeature.toUpperCase() + " = TRUE" + " -> (" + currentMethod.postCondition + "))";
		}
		MethodStruct currentMethod = this.methodsInDescendingOrder[index];
			
				return "(FV_"+currentMethod.nameOfFeature.toUpperCase() +" = TRUE" +" -> ("  + currentMethod.postCondition + "))" + " & "
						   +"(FV_"+currentMethod.nameOfFeature.toUpperCase() + " = FALSE " + " -> (" + calculateMetaPostCondition3(index + 1) + "))";
			
		
	}
	
	String calculateMetaPreCondition3 (int index){
		if(index == this.methodsInDescendingOrder.length -1){
			MethodStruct currentMethod = methodsInDescendingOrder[index];
			return "(FV_" + currentMethod.nameOfFeature.toUpperCase() + " = TRUE" + " -> (" + currentMethod.preCondition + "))";
		}
		MethodStruct currentMethod = this.methodsInDescendingOrder[index];
			
				return "(FV_"+currentMethod.nameOfFeature.toUpperCase() +" = TRUE" +" -> ("  + currentMethod.preCondition + "))" + " & "
						   +"(FV_"+currentMethod.nameOfFeature.toUpperCase() + " = FALSE " + " -> (" + calculateMetaPreCondition3(index + 1) + "))";
			
		
	}
	
	private String calculateMetaPreCondition(String[] hierachicalComposition,  int index) {
		

		if(index == hierachicalComposition.length -1) {
			for(MethodStruct method: this.listOfMethods) {
				if(method.nameOfFeature.equals(hierachicalComposition[index])) {
					return (method.preCondition);
				}
			}
		}
		
		for(MethodStruct method: this.listOfMethods) {
			if(method.nameOfFeature.equals(hierachicalComposition[index])) {
				return "(FV_"+method.nameOfFeature.toUpperCase() +" = TRUE" +"->" + method.preCondition + ") & " +"(FV_"+method.nameOfFeature.toUpperCase() + " = FALSE " + "->" + calculateMetaPreCondition(hierachicalComposition, index+1) + ")";
			}
		}
		return "something went wrong";
	}
	
	private String calculateMetaPostCondition(String[] hierachicalComposition,  int index) {
		
		
		if(index == hierachicalComposition.length -1) {
			for(MethodStruct method: this.listOfMethods) {
				if(method.nameOfFeature.equals(hierachicalComposition[index])) {
					return (method.postCondition);
				}
			}
		}
		
		for(MethodStruct method: this.listOfMethods) {
			if(method.nameOfFeature.equals(hierachicalComposition[index])) {
				return "(FV_"+method.nameOfFeature.toUpperCase() +" = TRUE" +"->"  + method.postCondition + ")" + "& " +"(FV_"+method.nameOfFeature.toUpperCase() + " = FALSE " + "->" + calculateMetaPostCondition(hierachicalComposition, index+1) + ")";
			}
		}
		return "something went wrong";
	}
	
	
	
	//Fynn
	public Resource toResourceObject(String className) {
		URI uri = uriToRootProject.appendSegment(MetaClass.FOLDER_NAME).appendSegment(this.metaClassName).appendSegment(this.metaMethodName).appendFileExtension("cbcmodel");
		ResourceSet rs = new ResourceSetImpl();
		Resource metaMethodResource = rs.createResource(uri);
		//Muss vom Typ .cbcModel sein.
		

	
		this.metaMethodFormula = CbcmodelFactory.eINSTANCE.createCbCFormula();
		metaMethodFormula.setName(this.metaMethodName);
		
		AbstractStatement formulaStatement = CbcmodelFactory.eINSTANCE.createAbstractStatement();
		formulaStatement.setName("statement");
		formulaStatement.setPreCondition(CbcmodelFactory.eINSTANCE.createCondition());
		formulaStatement.getPreCondition().setName("formulaStatement PreCondition");
		formulaStatement.setPostCondition(CbcmodelFactory.eINSTANCE.createCondition());
		formulaStatement.getPostCondition().setName("Formula Statement PostCondition");
		metaMethodFormula.setStatement(formulaStatement);
	
		//so klappt noch der alte Stand für sort()
		//formulaStatement.setRefinement(createSelectionRefinements(0));
		
		
		AbstractStatement initialOriginalCall = CbcmodelFactory.eINSTANCE.createAbstractStatement();
		initialOriginalCall.setName("original()");
		initialOriginalCall.setPreCondition(CbcmodelFactory.eINSTANCE.createCondition());
		initialOriginalCall.getPreCondition().setName("teststatement2 precondition");
		initialOriginalCall.setPostCondition(CbcmodelFactory.eINSTANCE.createCondition());
		initialOriginalCall.getPostCondition().setName("teststatement2 postcondition");
		
		formulaStatement.setRefinement(initialOriginalCall);
		
		GlobalConditions globalConditions = CbcmodelFactory.eINSTANCE.createGlobalConditions();
		Condition featureModelCondition = CbcmodelFactory.eINSTANCE.createCondition();
		featureModelCondition.setName(featureModelFormulaCNF);
		globalConditions.getConditions().add(featureModelCondition);
		
		
		
		
		boolean allOriginalsResolved = false;
		
		while(!allOriginalsResolved) {
			AbstractStatement statementThatCallsOriginal = searchOriginalStatement(formulaStatement);
			
			Console.println("Found original: " +statementThatCallsOriginal);
			
			allOriginalsResolved = (statementThatCallsOriginal == null);
			if(!allOriginalsResolved) {
				
				if (this.currentMethod.nameOfFeature.equals("Interest")) {
					int jsfj = 2;
				}
				statementThatCallsOriginal.setRefinement(resolveOriginal());
				Console.println("Replaced Original with Selection: " + this.currentMethod.nameOfFeature);
			}
			//if(this.currentMethodIndex == this.methodsInDescendingOrder.length-1) {
			//	break;
			//}
			this.currentMethodIndex = 0;
		}
		
		this.metaJavaVariables = CbcmodelFactory.eINSTANCE.createJavaVariables();
		for(IFeature currentFeature: this.featureVariables) {
			JavaVariable featureVariable = CbcmodelFactory.eINSTANCE.createJavaVariable();
			featureVariable.setName("boolean "+"FV_"+currentFeature.toString().toUpperCase());
			//featureVariable.setDisplayedName("boolean "+"FV_"+currentFeature.toString().toUpperCase());
			featureVariable.setKind(VariableKind.LOCAL);
			this.metaJavaVariables.getVariables().add(featureVariable);
		}
		
		/*for(MethodStruct currentMethod : this.listOfMethods) {
			if(currentMethod.globalConditions == null) continue;
			for(Condition currentMethodCondition: currentMethod.globalConditions.getConditions()) {
				globalConditions.getConditions().add(currentMethodCondition);
			}
		}*/
		
		/*for(int i = 0; i < this.listOfMethods.size(); i++) {
			for(int j = 0 ; j < this.listOfMethods.get(i).javaVariables.getVariables().size(); j++) {
				boolean alreadyInList = false;
				for(JavaVariable alreadyExistingVariable : javaVariables.getVariables()) {
					if(alreadyExistingVariable.getName().equals(this.listOfMethods.get(i).javaVariables.getVariables().get(j).getName()))
						alreadyInList = true;
				}
				if(i==0) {
					javaVariables.getVariables().add(this.listOfMethods.get(i).javaVariables.getVariables().get(j));
				}else if(!alreadyInList) {
					javaVariables.getVariables().add(this.listOfMethods.get(i).javaVariables.getVariables().get(j));
				}
				
			}
		}*/
		
		
		for(int i = 0; i < this.listOfMethods.size(); i++) {
			for(int j = 0 ; j < this.listOfMethods.get(i).javaVariables.getVariables().size(); j++) {
				boolean alreadyInList = false;
				
				JavaVariable variableToAdd = this.listOfMethods.get(i).javaVariables.getVariables().get(j);
				String  variableNameToAdd = variableToAdd.getDisplayedName();
				
				for(int k = 0 ; k < this.metaJavaVariables.getVariables().size(); k++) {

					if(this.metaJavaVariables.getVariables().get(k).getDisplayedName().equals(variableNameToAdd)) {
						alreadyInList = true;
					}
				}
				if(i==0) {
					this.metaJavaVariables.getVariables().add(CopyCbCFormiula.copyJavaVariable(variableToAdd));
				}else if(!alreadyInList) {
					this.metaJavaVariables.getVariables().add(CopyCbCFormiula.copyJavaVariable(variableToAdd));
				}
				
			}
		}
		
		
		
	/*	for(int i = 0; i < this.listOfMethods.size(); i++) {
			for(int j = 0 ; j < this.listOfMethods.get(i).globalConditions.getConditions().size(); j++) {
				boolean alreadyInList = false;
				for(Condition alreadyExistingVariable : globalConditions.getConditions()) {
					if(alreadyExistingVariable.getName().equals(this.listOfMethods.get(i).globalConditions.getConditions().get(j).getName()))
						alreadyInList = true;
				}
				if(!alreadyInList) {
					globalConditions.getConditions().add(this.listOfMethods.get(i).globalConditions.getConditions().get(j));
				}
				
			}
		}*/
		for(int i = 0; i < this.listOfMethods.size(); i++) {
			if (this.listOfMethods.get(i).globalConditions == null) {
				continue;
			}
			for(int j = 0 ; j < this.listOfMethods.get(i).globalConditions.getConditions().size(); j++) {
				boolean alreadyInList = false;
				Condition conditionToAdd = this.listOfMethods.get(i).globalConditions.getConditions().get(j);
				String nameOfConditionToAdd = conditionToAdd.getName();
				for(Condition alreadyExistingVariable : globalConditions.getConditions()) {
					if(alreadyExistingVariable.getName().equals(nameOfConditionToAdd))
						alreadyInList = true;
				}
				if(!alreadyInList ||i == 0) {
					globalConditions.getConditions().add(CopyCbCFormiula.copyCondition(conditionToAdd));
				}
				
			}
		}
		
		SetMetaSpecificationForFormula.passMetaSpeficiationThroughFormula(metaMethodFormula, this.metaPreConditon, this.metaPostCondition);	
		UpdateConditionsOfChildren.updateConditionsofChildren(metaMethodFormula.getStatement().getPreCondition());
		UpdateConditionsOfChildren.updateConditionsofChildren(metaMethodFormula.getStatement().getPostCondition());
		metaMethodResource.getContents().add(metaMethodFormula);
		metaMethodResource.getContents().add(globalConditions);
		metaMethodResource.getContents().add(this.metaJavaVariables);
		

			try {
				metaMethodResource.save(Collections.EMPTY_MAP);
				metaMethodResource.setTrackingModification(true);
			
				
				IFile ifile = ResourcesPlugin.getWorkspace().getRoot().getFile(new org.eclipse.core.runtime.Path(uri.toPlatformString(true)));
				ifile.getParent().refreshLocal(1, null);
				
				
			} catch (Exception e){//IOException | CoreException e1) {
				e.printStackTrace();
			} 

			
		
		return metaMethodResource;
	}
	
	
	
	
	
	public Resource toResourceObject() {
		URI uri = uriToRootProject.appendSegment(MetaClass.FOLDER_NAME)/*.appendSegment(this.metaClassName)*/.appendSegment(this.metaMethodName).appendFileExtension("cbcmodel");
		ResourceSet rs = new ResourceSetImpl();
		Resource metaMethodResource = rs.createResource(uri);
		//Muss vom Typ .cbcModel sein.
		

	
		
		this.metaMethodFormula = CbcmodelFactory.eINSTANCE.createCbCFormula();
		metaMethodFormula.setName(this.metaMethodName);
		
		AbstractStatement formulaStatement = CbcmodelFactory.eINSTANCE.createAbstractStatement();
		formulaStatement.setName("statement");
		formulaStatement.setPreCondition(CbcmodelFactory.eINSTANCE.createCondition());
		formulaStatement.getPreCondition().setName("formulaStatement PreCondition");
		formulaStatement.setPostCondition(CbcmodelFactory.eINSTANCE.createCondition());
		formulaStatement.getPostCondition().setName("Formula Statement PostCondition");
		metaMethodFormula.setStatement(formulaStatement);
	
		//so klappt noch der alte Stand für sort()
		//formulaStatement.setRefinement(createSelectionRefinements(0));
		
		
		AbstractStatement initialOriginalCall = CbcmodelFactory.eINSTANCE.createAbstractStatement();
		initialOriginalCall.setName("original()");
		initialOriginalCall.setPreCondition(CbcmodelFactory.eINSTANCE.createCondition());
		initialOriginalCall.getPreCondition().setName("teststatement2 precondition");
		initialOriginalCall.setPostCondition(CbcmodelFactory.eINSTANCE.createCondition());
		initialOriginalCall.getPostCondition().setName("teststatement2 postcondition");
		
		formulaStatement.setRefinement(initialOriginalCall);
		
		GlobalConditions globalConditions = CbcmodelFactory.eINSTANCE.createGlobalConditions();
		Condition featureModelCondition = CbcmodelFactory.eINSTANCE.createCondition();
		featureModelCondition.setName(featureModelFormulaCNF);
		globalConditions.getConditions().add(featureModelCondition);
		
		
		
		
		boolean allOriginalsResolved = false;
		
		while(!allOriginalsResolved) {
			AbstractStatement statementThatCallsOriginal = searchOriginalStatement(formulaStatement);
			
			Console.println("Found original: " +statementThatCallsOriginal);
			
			allOriginalsResolved = (statementThatCallsOriginal == null);
			if(!allOriginalsResolved) {
				
				if (this.currentMethod.nameOfFeature.equals("Interest")) {
					int jsfj = 2;
				}
				statementThatCallsOriginal.setRefinement(resolveOriginal());
				Console.println("Replaced Original with Selection: " + this.currentMethod.nameOfFeature);
			}
			//if(this.currentMethodIndex == this.methodsInDescendingOrder.length-1) {
			//	break;
			//}
			this.currentMethodIndex = 0;
		}
		
		this.metaJavaVariables = CbcmodelFactory.eINSTANCE.createJavaVariables();
		for(IFeature currentFeature: this.featureVariables) {
			JavaVariable featureVariable = CbcmodelFactory.eINSTANCE.createJavaVariable();
			featureVariable.setName("boolean "+"FV_"+currentFeature.toString().toUpperCase());
			//featureVariable.setDisplayedName("boolean "+"FV_"+currentFeature.toString().toUpperCase());
			featureVariable.setKind(VariableKind.LOCAL);
			this.metaJavaVariables.getVariables().add(featureVariable);
		}
		
		/*for(MethodStruct currentMethod : this.listOfMethods) {
			if(currentMethod.globalConditions == null) continue;
			for(Condition currentMethodCondition: currentMethod.globalConditions.getConditions()) {
				globalConditions.getConditions().add(currentMethodCondition);
			}
		}*/
		
		/*for(int i = 0; i < this.listOfMethods.size(); i++) {
			for(int j = 0 ; j < this.listOfMethods.get(i).javaVariables.getVariables().size(); j++) {
				boolean alreadyInList = false;
				for(JavaVariable alreadyExistingVariable : javaVariables.getVariables()) {
					if(alreadyExistingVariable.getName().equals(this.listOfMethods.get(i).javaVariables.getVariables().get(j).getName()))
						alreadyInList = true;
				}
				if(i==0) {
					javaVariables.getVariables().add(this.listOfMethods.get(i).javaVariables.getVariables().get(j));
				}else if(!alreadyInList) {
					javaVariables.getVariables().add(this.listOfMethods.get(i).javaVariables.getVariables().get(j));
				}
				
			}
		}*/
		
		
		for(int i = 0; i < this.listOfMethods.size(); i++) {
			for(int j = 0 ; j < this.listOfMethods.get(i).javaVariables.getVariables().size(); j++) {
				boolean alreadyInList = false;
				
				JavaVariable variableToAdd = this.listOfMethods.get(i).javaVariables.getVariables().get(j);
				String  variableNameToAdd = variableToAdd.getDisplayedName();
				
				for(int k = 0 ; k < this.metaJavaVariables.getVariables().size(); k++) {

					if(this.metaJavaVariables.getVariables().get(k).getDisplayedName().equals(variableNameToAdd)) {
						alreadyInList = true;
					}
				}
				if(i==0) {
					this.metaJavaVariables.getVariables().add(CopyCbCFormiula.copyJavaVariable(variableToAdd));
				}else if(!alreadyInList) {
					this.metaJavaVariables.getVariables().add(CopyCbCFormiula.copyJavaVariable(variableToAdd));
				}
				
			}
		}
		
		
		
	/*	for(int i = 0; i < this.listOfMethods.size(); i++) {
			for(int j = 0 ; j < this.listOfMethods.get(i).globalConditions.getConditions().size(); j++) {
				boolean alreadyInList = false;
				for(Condition alreadyExistingVariable : globalConditions.getConditions()) {
					if(alreadyExistingVariable.getName().equals(this.listOfMethods.get(i).globalConditions.getConditions().get(j).getName()))
						alreadyInList = true;
				}
				if(!alreadyInList) {
					globalConditions.getConditions().add(this.listOfMethods.get(i).globalConditions.getConditions().get(j));
				}
				
			}
		}*/
		for(int i = 0; i < this.listOfMethods.size(); i++) {
			if (this.listOfMethods.get(i).globalConditions == null) {
				continue;
			}
			for(int j = 0 ; j < this.listOfMethods.get(i).globalConditions.getConditions().size(); j++) {
				boolean alreadyInList = false;
				Condition conditionToAdd = this.listOfMethods.get(i).globalConditions.getConditions().get(j);
				String nameOfConditionToAdd = conditionToAdd.getName();
				for(Condition alreadyExistingVariable : globalConditions.getConditions()) {
					if(alreadyExistingVariable.getName().equals(nameOfConditionToAdd))
						alreadyInList = true;
				}
				if(!alreadyInList ||i == 0) {
					globalConditions.getConditions().add(CopyCbCFormiula.copyCondition(conditionToAdd));
				}
				
			}
		}
		
		SetMetaSpecificationForFormula.passMetaSpeficiationThroughFormula(metaMethodFormula, this.metaPreConditon, this.metaPostCondition);	
		UpdateConditionsOfChildren.updateConditionsofChildren(metaMethodFormula.getStatement().getPreCondition());
		UpdateConditionsOfChildren.updateConditionsofChildren(metaMethodFormula.getStatement().getPostCondition());
		metaMethodResource.getContents().add(metaMethodFormula);
		metaMethodResource.getContents().add(globalConditions);
		metaMethodResource.getContents().add(this.metaJavaVariables);
		

			try {
				metaMethodResource.save(Collections.EMPTY_MAP);
				metaMethodResource.setTrackingModification(true);
			
				
				IFile ifile = ResourcesPlugin.getWorkspace().getRoot().getFile(new org.eclipse.core.runtime.Path(uri.toPlatformString(true)));
				ifile.getParent().refreshLocal(1, null);
				
				
			} catch (Exception e) {//IOException | CoreException e1) {
				e.printStackTrace();
			} 

			
		
		return metaMethodResource;
	}
	
	String createRandomUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	
	
	SelectionStatement createSelectionRefinements(int index) {
		SelectionStatement newSelection = CbcmodelFactory.eINSTANCE.createSelectionStatement();
		newSelection.getGuards().add(CbcmodelFactory.eINSTANCE.createCondition());
		newSelection.getGuards().get(0).setName("FV_"+this.methodsInDescendingOrder[index].nameOfFeature + " = TRUE");
		newSelection.getGuards().add(CbcmodelFactory.eINSTANCE.createCondition());
		newSelection.getGuards().get(1).setName("FV_"+this.methodsInDescendingOrder[index].nameOfFeature + " = FALSE");
		
		AbstractStatement firstStatement = CbcmodelFactory.eINSTANCE.createAbstractStatement();
		firstStatement.setName("statement");
		firstStatement.setPreCondition(CbcmodelFactory.eINSTANCE.createCondition());
		firstStatement.getPreCondition().setName("teststatement precondition"+createRandomUUID());
		firstStatement.setPostCondition(CbcmodelFactory.eINSTANCE.createCondition());
		firstStatement.getPostCondition().setName("teststatement postcondition"+createRandomUUID());
		
		AbstractStatement secondStatement = CbcmodelFactory.eINSTANCE.createAbstractStatement();
		secondStatement.setName("statement");
		secondStatement.setPreCondition(CbcmodelFactory.eINSTANCE.createCondition());
		
		if(this.methodsInDescendingOrder[index].nameOfFeature.contains("Base")) {
			secondStatement.getPreCondition().setName("debug"+createRandomUUID());
		}else {
			secondStatement.getPreCondition().setName("teststatement2 precondition"+createRandomUUID());
		}
		
		secondStatement.setPostCondition(CbcmodelFactory.eINSTANCE.createCondition());
		secondStatement.getPostCondition().setName("teststatement2 postcondition"+createRandomUUID());
		
		
		newSelection.getCommands().add(firstStatement);
		newSelection.getCommands().add(secondStatement);
		

		if(this.methodsInDescendingOrder[index].callsOriginal)
		{
			//this.methodsInDescendingOrder[index].CbcFormula.getStatement().getRefinement().setParent(firstStatement);
			firstStatement.setRefinement(this.methodsInDescendingOrder[index].CbcFormula.getStatement().getRefinement());
			int nextIndex = index + 1;
			this.methodsInDescendingOrder[index].originalCallingStatement.setRefinement(createSelectionRefinements(nextIndex));
		}
		else
		{	
			AbstractStatement relevantMethod = this.methodsInDescendingOrder[index].CbcFormula.getStatement().getRefinement();
			//relevantMethod.setParent(firstStatement);
			firstStatement.setRefinement(relevantMethod);
		}

		if(index == (this.methodsInDescendingOrder.length-1))
		{	
			SkipStatement finalSkip = CbcmodelFactory.eINSTANCE.createSkipStatement();
			finalSkip.setPreCondition(CbcmodelFactory.eINSTANCE.createCondition());
			finalSkip.getPreCondition().setName("first selection statement pre condition" + this.currentMethod.nameOfFeature);
			finalSkip.setPostCondition(CbcmodelFactory.eINSTANCE.createCondition());
			finalSkip.getPostCondition().setName("first selection statement post condition" + this.currentMethod.nameOfFeature);
			//finalSkip.setParent(secondStatement);
			secondStatement.setRefinement(finalSkip);
		}
		else
		{
			index = index +1;
			secondStatement.setRefinement(this.createSelectionRefinements(index));
			
		}

		return newSelection;

	}
	
	
	private SelectionStatement resolveOriginal() {
		
		SelectionStatement newSelection = CbcmodelFactory.eINSTANCE.createSelectionStatement();
		newSelection.getGuards().add(CbcmodelFactory.eINSTANCE.createCondition());
		newSelection.getGuards().get(0).setName("FV_"+ this.currentMethod.nameOfFeature.toUpperCase() + " = TRUE");
		newSelection.getGuards().add(CbcmodelFactory.eINSTANCE.createCondition());
		newSelection.getGuards().get(1).setName("FV_"+ this.currentMethod.nameOfFeature.toUpperCase() + " = FALSE");
		
		
		AbstractStatement firstStatement = CbcmodelFactory.eINSTANCE.createAbstractStatement();
		firstStatement.setName("statement");
		firstStatement.setPreCondition(CbcmodelFactory.eINSTANCE.createCondition());
		firstStatement.getPreCondition().setName("firstStatement precondition");
		firstStatement.setPostCondition(CbcmodelFactory.eINSTANCE.createCondition());
		firstStatement.getPostCondition().setName("firstStatement postcondition");
		
		AbstractStatement secondStatement = CbcmodelFactory.eINSTANCE.createAbstractStatement();
		secondStatement.setName("statement");
		secondStatement.setPreCondition(CbcmodelFactory.eINSTANCE.createCondition());
		if(this.currentMethod.nameOfFeature.contains("Sort")) {
			secondStatement.getPreCondition().setName("debug"+createRandomUUID().charAt(0));
		}else {
			secondStatement.getPreCondition().setName("secondStatement precondition"+createRandomUUID().charAt(0));
		}
		
		secondStatement.setPostCondition(CbcmodelFactory.eINSTANCE.createCondition());
		secondStatement.getPostCondition().setName("secondStatement postcondition"+createRandomUUID().charAt(0));
		
		newSelection.getCommands().add(firstStatement);
		newSelection.getCommands().add(secondStatement);
		//CbcFormula formula = CopyFormula.copyFormula(currentFeature.formula);
		//firstStatement.setRefinement(formula.getStatement.getRefinement());
		
		//check if the current Method is the last Feature and requires a skip statement.
		if(this.currentMethod.nameOfFeature.equals(this.hierachicalComposition[this.hierachicalComposition.length-1])) {
			SkipStatement finalSkip = CbcmodelFactory.eINSTANCE.createSkipStatement();
			finalSkip.setPreCondition(CbcmodelFactory.eINSTANCE.createCondition());
			finalSkip.getPreCondition().setName("final skip statement pre condition + PARENT" +this.currentMethod.nameOfFeature);
			finalSkip.setPostCondition(CbcmodelFactory.eINSTANCE.createCondition());
			finalSkip.getPostCondition().setName("final skip statement post condition + PARENT" +this.currentMethod.nameOfFeature);
			finalSkip.setName("final");
			secondStatement.setRefinement(finalSkip);
		}
		else {
			AbstractStatement rightOriginalCall = CbcmodelFactory.eINSTANCE.createAbstractStatement();
			rightOriginalCall.setName("original()");
			rightOriginalCall.setPreCondition(CbcmodelFactory.eINSTANCE.createCondition());
			rightOriginalCall.getPreCondition().setName("PARTENT: " + this.currentMethod.nameOfFeature);
			rightOriginalCall.setPostCondition(CbcmodelFactory.eINSTANCE.createCondition());
			rightOriginalCall.getPostCondition().setName("PARTENT: " + this.currentMethod.nameOfFeature);

			
			if(secondStatement.getRefinement() == null) {
				secondStatement.setRefinement(rightOriginalCall);
			}
			
			
		}
		
		//firstStatement.setRefinement(this.currentMethod.CbcFormula.getStatement().getRefinement());
		CbCFormula copiedFormula = CopyCbCFormiula.copyCbCFormula(this.currentMethod.CbcFormula);
		firstStatement.setRefinement(copiedFormula.getStatement().getRefinement());
		return newSelection;
		
	}
	
	//returnt das statement, dessen Verfeinerung "original(" beinhaltet.
	private AbstractStatement searchOriginalStatement (AbstractStatement statement) {
		
		
		if (statement == null) {
			return null;
		}
		statement.setProven(false);
		if(statement.getRefinement() != null) {
			if(statement.getRefinement().getName() != null && statement.getRefinement().getName().contains("original(")) {
				return statement;
			}
		}
		AbstractStatement foundStatement = null;
		if (statement instanceof CompositionStatement) {
			foundStatement = searchOriginalStatement(((CompositionStatement) statement).getFirstStatement());
			if(foundStatement == null) {
				foundStatement = searchOriginalStatement(((CompositionStatement) statement).getSecondStatement());
			}
			
		}

		if (statement instanceof SelectionStatement) {
			
			if(((SelectionStatement) statement).getGuards().get(0).getName().contains("FV_"))
			{	
				/*String nameOfFeatureInSelection = ((SelectionStatement) statement).getGuards().get(0).getName();
				 Pattern pattern = Pattern.compile("FV_(.*?)\\s");
				 Matcher matcher = pattern.matcher(nameOfFeatureInSelection);
					if (matcher.find())
					{
						nameOfFeatureInSelection= matcher.group(1);
					}
				
				for(MethodStruct method: this.listOfMethods) {
					if (method.nameOfFeature.equals(nameOfFeatureInSelection)) {
						this.currentMethod = method;
					}
				}*/
			
				
				if(this.currentMethodIndex < this.methodsInDescendingOrder.length-1) {
					this.currentMethodIndex = (this.currentMethodIndex +1);
					this.currentMethod = this.methodsInDescendingOrder[this.currentMethodIndex];
				}
			}
			
			
			for (AbstractStatement currentSelectionStatement : ((SelectionStatement) statement).getCommands() ) {
				
				if(foundStatement != null) {break;}
				foundStatement = searchOriginalStatement(currentSelectionStatement);
				if(currentSelectionStatement.getPreCondition().getName().contains("debug")) {
					int debug = 10;
				}
			}
			
			/*if(foundStatement == null 
					&& ((SelectionStatement) statement).getGuards().get(0).getName().contains("FV_")
					&& !(this.currentMethod.nameOfFeature.equals(this.methodsInDescendingOrder[this.methodsInDescendingOrder.length-1].nameOfFeature))){	
				if(this.currentMethodIndex > 0 ) {
					this.currentMethodIndex = (this.currentMethodIndex -1);
					this.currentMethod = this.methodsInDescendingOrder[this.currentMethodIndex];
				}
			}*/
			if(foundStatement == null && ((SelectionStatement) statement).getGuards().get(0).getName().contains("FV_")){
				String nameOfFeatureInSelectionUP = ((SelectionStatement) statement).getGuards().get(0).getName();
				String nameOfFeatureInSelection  = "";
				Pattern pattern = Pattern.compile("FV_(.*?)\\s");
				Matcher matcher = pattern.matcher(nameOfFeatureInSelectionUP);
				if (matcher.find())
				{
					// == <FEATURENAME>
					nameOfFeatureInSelectionUP = matcher.group(1); 
					
					// == <Featurename>
					nameOfFeatureInSelection = nameOfFeatureInSelectionUP.charAt(0) + nameOfFeatureInSelectionUP.substring(1).toLowerCase();
					
				}
				int searchIndex = -1;
			
				for(int i = 0 ; i < this.methodsInDescendingOrder.length; i++) {
					if (nameOfFeatureInSelection.equals(this.methodsInDescendingOrder[i].nameOfFeature)) {
						searchIndex = i;
						break;
					}
				}
				if(searchIndex != -1 && !(searchIndex == this.methodsInDescendingOrder.length -1)) {
					this.currentMethod = this.methodsInDescendingOrder[searchIndex];
					this.currentMethodIndex = searchIndex;
					int debug = 0;
				}
			}
			 
		}
		if (statement instanceof SmallRepetitionStatement) {
			
			//foundStatement = searchOriginalStatement(((SmallRepetitionStatement) statement).getStartStatement());
			
			if(foundStatement == null) {
				foundStatement = searchOriginalStatement(((SmallRepetitionStatement) statement).getLoopStatement());
			}
			
			
		}
		/*
		if (statement instanceof Composition3Statement) {
			foundStatement = searchOriginalStatement(((Composition3Statement) statement).getFirstStatement());
			if(foundStatement == null) {
				foundStatement = searchOriginalStatement(((Composition3Statement) statement).getSecondStatement());
				if(foundStatement == null) {
					foundStatement = searchOriginalStatement(((Composition3Statement) statement).getThirdStatement());
				}
			}
			
			
		}*/

		if (statement instanceof ReturnStatement) {
			int a = 329;
		}
		if (statement instanceof MethodStatement) {
			int b = 2389;
		}
		if (statement instanceof OriginalStatement) {
			int c = 2398;
		}
		if(foundStatement == null) {
			foundStatement = searchOriginalStatement(statement.getRefinement());
		}
		
		return foundStatement;
		
	}
}
