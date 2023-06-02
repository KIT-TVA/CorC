package de.tu_bs.cs.isf.commands.toolbar.handler.family;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import de.ovgu.featureide.fm.core.base.IFeature;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Parameter;
import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.OriginalStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.ReturnStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SkipStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.StrengthWeakStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.VariableKind;
import de.tu_bs.cs.isf.cbc.tool.helper.UpdateConditionsOfChildren;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;

public class MetaMethod {
	MetaClass metaClass;
	CbCFormula metaMethodFormula;
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
	String metaClassName;
	URI uriToRootProject;
	
	
	public MetaMethod(URI uriToRootProject, MetaClass metaClass, String className, String methodName, 
			List<MethodStruct> listOfMethods, 			//All different implementations of Method.
			String featureModelFormulaCNF, 
			ArrayList<IFeature> features, 				//Name of all features in Productline. Necessary to introduce Features Variables.
			List<String> featureOrderList,				
			List<String> featuresImplementingMethods) { //Name of all Features implementing the method
		
		this.metaClass = metaClass;
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
		
		this.metaPreConditon = createMetaPreCondition();
		this.metaPostCondition = createMetaPostCondition();
		
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

	String createMetaPreCondition() {
		int cur = this.listOfMethods.size()-1;
		String condition = this.listOfMethods.get(cur).preCondition;
		String curFeature = this.listOfMethods.get(cur).nameOfFeature.toUpperCase();
		if (cur == 0) {
			return "(" + MetaVariablesClass.NAME + "." + "FV_" + curFeature + " = TRUE" + " -> (" + condition + "))";
		}
		condition = "(" + MetaVariablesClass.NAME 
				+ "." + "FV_"+ curFeature 
				+ " = TRUE" + " -> ("  + condition + "))" 
				+ " & "
				+ "(" + MetaVariablesClass.NAME + "." + "FV_" + curFeature + " = FALSE" + " -> (original))";
		while (condition.contains("original")) {
			cur--;
			curFeature = this.listOfMethods.get(cur).nameOfFeature.toUpperCase();
			var oriCondition = this.listOfMethods.get(cur).preCondition; 
			if (cur == 0) {
				condition = condition.replaceAll("original", Matcher.quoteReplacement(
						"(" + MetaVariablesClass.NAME + "." + "FV_" + curFeature + " = TRUE" 
					    + " -> (" + oriCondition + "))"));
				break;
			} else {
				condition = condition.replaceAll("original", Matcher.quoteReplacement(
						"(" + MetaVariablesClass.NAME 
						+ "." + "FV_"+ curFeature 
						+ " = TRUE" + " -> ("  + oriCondition + "))" 
						+ " & "
						+ "(" + MetaVariablesClass.NAME + "." + "FV_" + curFeature + " = FALSE " + " -> (original))"));
			}
		}
		return condition;
	}
	
	String createMetaPostCondition() {
		int cur = this.listOfMethods.size()-1;
		String condition = this.listOfMethods.get(cur).postCondition;
		String curFeature = this.listOfMethods.get(cur).nameOfFeature.toUpperCase();
		if (cur == 0) {
			return "(" + MetaVariablesClass.NAME + "." + "FV_" + curFeature + " = TRUE" + " -> (" + condition + "))";
		}
		condition = "(" + MetaVariablesClass.NAME 
				+ "." + "FV_"+ curFeature 
				+ " = TRUE" + " -> ("  + condition + "))" 
				+ " & "
				+ "(" + MetaVariablesClass.NAME + "." + "FV_" + curFeature + " = FALSE" + " -> (original))";
		while (condition.contains("original")) {
			cur--;
			curFeature = this.listOfMethods.get(cur).nameOfFeature.toUpperCase();
			var oriCondition = this.listOfMethods.get(cur).postCondition; 
			if (cur == 0) {
				condition = condition.replaceAll("original", Matcher.quoteReplacement(
						"(" + MetaVariablesClass.NAME + "." + "FV_" + curFeature + " = TRUE" 
					    + " -> (" + oriCondition + "))"));
				break;
			} else {
				condition = condition.replaceAll("original", Matcher.quoteReplacement(
						"(" + MetaVariablesClass.NAME 
						+ "." + "FV_"+ curFeature 
						+ " = TRUE" + " -> ("  + oriCondition + "))" 
						+ " & "
						+ "(" + MetaVariablesClass.NAME + "." + "FV_" + curFeature + " = FALSE " + " -> (original))"));
			}
		}
		return condition;
	}
	
	public Resource toResourceObject(String className) throws IOException, CoreException, MetaClassException {
		URI metaMethodUri = uriToRootProject.appendSegment(MetaClass.FOLDER_NAME).appendSegment(this.metaClassName).appendSegment(this.metaMethodName).appendFileExtension("cbcmodel");

		createMetaFormula(className);
		AbstractStatement formulaStatement = createMetaFormulaStatement();
		metaMethodFormula.setStatement(formulaStatement);
		AbstractStatement initialOriginalCall = createMetaInitialOriginalStatement();
		formulaStatement.setRefinement(initialOriginalCall);
		resolveOriginals(formulaStatement);
		var metaVariables = createMetaVariables();
		var globalConditions = createMetaInvariants();
		addFeatureModelCondition(globalConditions);
		var metaMethodResource = createMetaMethod(metaMethodUri, globalConditions, metaVariables, metaMethodFormula);
		saveMetaMethod(metaMethodUri, metaMethodResource);
		return metaMethodResource;
	}
	
	private void createMetaFormula(String className) {
		metaMethodFormula = CbcmodelFactory.eINSTANCE.createCbCFormula();
		metaMethodFormula.setClassName(className);
		metaMethodFormula.setName(this.metaMethodName);
	}
	
	private AbstractStatement createMetaFormulaStatement() {
		AbstractStatement formulaStatement = CbcmodelFactory.eINSTANCE.createAbstractStatement();
		formulaStatement.setName("statement");
		formulaStatement.setPreCondition(CbcmodelFactory.eINSTANCE.createCondition());
		formulaStatement.getPreCondition().setName("formulaStatement PreCondition");
		formulaStatement.setPostCondition(CbcmodelFactory.eINSTANCE.createCondition());
		formulaStatement.getPostCondition().setName("Formula Statement PostCondition");
		return formulaStatement;
	}
	
	private AbstractStatement createMetaInitialOriginalStatement() {
		AbstractStatement initialOriginalCall = CbcmodelFactory.eINSTANCE.createAbstractStatement();
		initialOriginalCall.setName("original()");
		initialOriginalCall.setPreCondition(CbcmodelFactory.eINSTANCE.createCondition());
		initialOriginalCall.getPreCondition().setName("teststatement2 precondition");
		initialOriginalCall.setPostCondition(CbcmodelFactory.eINSTANCE.createCondition());
		initialOriginalCall.getPostCondition().setName("teststatement2 postcondition");
		return initialOriginalCall;
	}
	
	private void resolveOriginals(AbstractStatement formulaStatement) {
		boolean allOriginalsResolved = false;
		while(!allOriginalsResolved) {
			AbstractStatement statementThatCallsOriginal = searchOriginalStatement(formulaStatement);
			if (statementThatCallsOriginal == null) {
				var kjsdfljsdf = 2;
			}
			Console.println("Found original: " +statementThatCallsOriginal);
			System.out.println("Found original: " +statementThatCallsOriginal);
			
			allOriginalsResolved = (statementThatCallsOriginal == null);
			if(!allOriginalsResolved) {
				var lolmaoa = this.currentMethod.nameOfFeature + " | " + this.currentMethod.nameOfMethod;
				if (lolmaoa.equals("Cons | push")) {
					var skldfjsldkfjklsdfj =2;
				}
				statementThatCallsOriginal.setRefinement(resolveOriginal());
				Console.println("Replaced Original with Selection: " + this.currentMethod.nameOfFeature);
				System.out.println("Replaced Original with Selection: " + this.currentMethod.nameOfFeature);
			}
			this.currentMethodIndex = 0;
		}
	}
	
	private JavaVariables createMetaVariables() throws MetaClassException {
		var metaJavaVariables = CbcmodelFactory.eINSTANCE.createJavaVariables();

		for(int i = 0; i < this.listOfMethods.size(); i++) {
			addVariables(this.listOfMethods.get(i), metaJavaVariables);
			addParameters(this.listOfMethods.get(i), metaJavaVariables);
		}
		return metaJavaVariables;
	}
	
	private void addVariables(MethodStruct method, JavaVariables metaJavaVariables) {
		for(int j = 0 ; j < method.javaVariables.getVariables().size(); j++) {
			boolean alreadyInList = false;
			JavaVariable variableToAdd = method.javaVariables.getVariables().get(j);
			String  variableNameToAdd = variableToAdd.getDisplayedName();
			
			for(int k = 0 ; k < metaJavaVariables.getVariables().size(); k++) {

				if(metaJavaVariables.getVariables().get(k).getDisplayedName().equals(variableNameToAdd)) {
					alreadyInList = true;
				}
			}
			if(!alreadyInList) {
				metaJavaVariables.getVariables().add(CopyCbCFormiula.copyJavaVariable(variableToAdd));
			}
		}
	}
	
	private void addParameters(MethodStruct method, JavaVariables metaJavaVariables) throws MetaClassException {
		var metaMethod = metaClass.getMethod(method.nameOfMethod);
		for(int j = 0 ; j < metaMethod.getParameters().size(); j++) {
			boolean alreadyInList = false;
			Parameter variableToAdd = metaMethod.getParameters().get(j);
			String variableNameToAdd = variableToAdd.getName();
			
			for(int k = 0 ; k < metaJavaVariables.getVariables().size(); k++) {
				if(metaJavaVariables.getVariables().get(k).getDisplayedName().equals(variableNameToAdd)) {
					alreadyInList = true;
				}
			}
			if(!alreadyInList) {
				metaJavaVariables.getVariables().add(CopyCbCFormiula.copyParameter(variableToAdd));
			}
		}
	}
	
	
	private GlobalConditions createMetaInvariants() {
		GlobalConditions globalConditions = CbcmodelFactory.eINSTANCE.createGlobalConditions();

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
		return globalConditions;
	}
	
	private void addFeatureModelCondition(GlobalConditions globalConditions) {
		Condition featureModelCondition = CbcmodelFactory.eINSTANCE.createCondition();
		featureModelCondition.setName(featureModelFormulaCNF);
		globalConditions.getConditions().add(featureModelCondition);
	}
	
	private Resource createMetaMethod(URI uri, GlobalConditions globalConditions, JavaVariables metaVariables, CbCFormula metaMethodFormula) {
		ResourceSet rs = new ResourceSetImpl();
		Resource metaMethodResource = rs.createResource(uri);
		SetMetaSpecificationForFormula.passMetaSpeficiationThroughFormula(metaMethodFormula, this.metaPreConditon, this.metaPostCondition);	
		UpdateConditionsOfChildren.updateConditionsofChildren(metaMethodFormula.getStatement().getPreCondition());
		UpdateConditionsOfChildren.updateConditionsofChildren(metaMethodFormula.getStatement().getPostCondition());
		placeThisInAllConditions(metaMethodFormula.getStatement());
		placeThisInGlobalConditions(globalConditions);
		metaMethodResource.getContents().add(metaMethodFormula);
		metaMethodResource.getContents().add(globalConditions);
		metaMethodResource.getContents().add(metaVariables);
		return metaMethodResource;
	}
	
	private void placeThisInGlobalConditions(GlobalConditions globalConditions) {
		for (var gc : globalConditions.getConditions()) {
			gc.setName(placeThis(gc.getName()));
		}
		
	}
	
	private String placeThis(String condition) {
		var metaModel = this.metaClass.getModel();
		for (var f : metaModel.getFields()) {
			condition = condition.replaceAll("(?<!this\\.)\\b" + f.getName() + "\\b" , "this." + f.getName());
		}
		return condition;
	}
	
	private void placeThisInAllConditions(EObject cur) {
		if (cur instanceof CompositionStatement) {
			if (((CompositionStatement)cur).getPreCondition() != null) {
				((CompositionStatement)cur).getPreCondition().setName(placeThis(((CompositionStatement)cur).getPreCondition().getName()));
			}
			if (((CompositionStatement)cur).getPostCondition() != null) {
				((CompositionStatement)cur).getPostCondition().setName(placeThis(((CompositionStatement)cur).getPostCondition().getName()));
			}
			((CompositionStatement)cur).getIntermediateCondition().setName(placeThis(((CompositionStatement)cur).getIntermediateCondition().getName()));
		} else if (cur instanceof MethodStatement) {
			if (((MethodStatement)cur).getPreCondition() != null) {
				((MethodStatement)cur).getPreCondition().setName(placeThis(((MethodStatement)cur).getPreCondition().getName()));
			}
			if (((MethodStatement)cur).getPostCondition() != null) {
				((MethodStatement)cur).getPostCondition().setName(placeThis(((MethodStatement)cur).getPostCondition().getName()));
			}
		} else if (cur instanceof ReturnStatement) {
			if (((ReturnStatement)cur).getPreCondition() != null) {
				((ReturnStatement)cur).getPreCondition().setName(placeThis(((SelectionStatement)cur).getPreCondition().getName()));
			}
			if (((ReturnStatement)cur).getPostCondition() != null) {
				((ReturnStatement)cur).getPostCondition().setName(placeThis(((SelectionStatement)cur).getPostCondition().getName()));
			}
		} else if (cur instanceof SelectionStatement) {
			if (((SelectionStatement)cur).getPreCondition() != null) {
				((SelectionStatement)cur).getPreCondition().setName(placeThis(((SelectionStatement)cur).getPreCondition().getName()));
			}
			if (((SelectionStatement)cur).getPostCondition() != null) {
				((SelectionStatement)cur).getPostCondition().setName(placeThis(((SelectionStatement)cur).getPostCondition().getName()));
			}
		} else if (cur instanceof SkipStatement) {
			if (((SkipStatement)cur).getPreCondition() != null) {
				((SkipStatement)cur).getPreCondition().setName(placeThis(((SkipStatement)cur).getPreCondition().getName()));
			}
			if (((SkipStatement)cur).getPostCondition() != null) {
				((SkipStatement)cur).getPostCondition().setName(placeThis(((SkipStatement)cur).getPostCondition().getName()));
			}
		} else if (cur instanceof SmallRepetitionStatement) {
			if (((SmallRepetitionStatement)cur).getPreCondition() != null) {
				((SmallRepetitionStatement)cur).getPreCondition().setName(placeThis(((SmallRepetitionStatement)cur).getPreCondition().getName()));
			}
			if (((SmallRepetitionStatement)cur).getPostCondition() != null) {
				((SmallRepetitionStatement)cur).getPostCondition().setName(placeThis(((SmallRepetitionStatement)cur).getPostCondition().getName()));
			}
			((SmallRepetitionStatement)cur).getInvariant().setName(placeThis(((SmallRepetitionStatement)cur).getInvariant().getName()));
			((SmallRepetitionStatement)cur).getVariant().setName(placeThis(((SmallRepetitionStatement)cur).getVariant().getName()));
			((SmallRepetitionStatement)cur).getGuard().setName(placeThis(((SmallRepetitionStatement)cur).getGuard().getName()));
		} else if (cur instanceof StrengthWeakStatement) {
			if (((StrengthWeakStatement)cur).getPreCondition() != null) {
				((StrengthWeakStatement)cur).getPreCondition().setName(placeThis(((StrengthWeakStatement)cur).getPreCondition().getName()));
			}
			if (((StrengthWeakStatement)cur).getPostCondition() != null) {
				((StrengthWeakStatement)cur).getPostCondition().setName(placeThis(((StrengthWeakStatement)cur).getPostCondition().getName()));
			}
		} else if (cur instanceof OriginalStatement) {
			if (((OriginalStatement)cur).getPreCondition() != null) {
				((OriginalStatement)cur).getPreCondition().setName(placeThis(((OriginalStatement)cur).getPreCondition().getName()));
			}
			if (((OriginalStatement)cur).getPostCondition() != null) {
				((OriginalStatement)cur).getPostCondition().setName(placeThis(((OriginalStatement)cur).getPostCondition().getName()));
			}
		} else if (cur instanceof AbstractStatement) {
			if (((AbstractStatement)cur).getPreCondition() != null) {
				((AbstractStatement)cur).getPreCondition().setName(placeThis(((AbstractStatement)cur).getPreCondition().getName()));
			}
			if (((AbstractStatement)cur).getPostCondition() != null) {
				((AbstractStatement)cur).getPostCondition().setName(placeThis(((AbstractStatement)cur).getPostCondition().getName()));
			}
		}
		for (var child : cur.eContents()) {
			placeThisInAllConditions(child);
		}
	}
	
	private void saveMetaMethod(URI uri, Resource metaMethodResource) throws IOException, CoreException {
		metaMethodResource.save(Collections.EMPTY_MAP);
		metaMethodResource.setTrackingModification(true);
		IFile ifile = ResourcesPlugin.getWorkspace().getRoot().getFile(new org.eclipse.core.runtime.Path(uri.toPlatformString(true)));
		ifile.getParent().refreshLocal(1, null);
	}
	
	
	
	
	String createRandomUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	
	
	SelectionStatement createSelectionRefinements(int index) {
		SelectionStatement newSelection = CbcmodelFactory.eINSTANCE.createSelectionStatement();
		newSelection.getGuards().add(CbcmodelFactory.eINSTANCE.createCondition());
		newSelection.getGuards().get(0).setName(MetaVariablesClass.NAME + "." + "FV_"+this.methodsInDescendingOrder[index].nameOfFeature + " = TRUE");
		newSelection.getGuards().add(CbcmodelFactory.eINSTANCE.createCondition());
		newSelection.getGuards().get(1).setName(MetaVariablesClass.NAME + "." + "FV_"+this.methodsInDescendingOrder[index].nameOfFeature + " = FALSE");
		
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
		newSelection.getGuards().get(0).setName(MetaVariablesClass.NAME + "." + "FV_"+ this.currentMethod.nameOfFeature.toUpperCase() + " = TRUE");
		newSelection.getGuards().add(CbcmodelFactory.eINSTANCE.createCondition());
		newSelection.getGuards().get(1).setName(MetaVariablesClass.NAME + "." + "FV_"+ this.currentMethod.nameOfFeature.toUpperCase() + " = FALSE");
		
		
		AbstractStatement firstStatement = CbcmodelFactory.eINSTANCE.createAbstractStatement();
		firstStatement.setName("statement");
		firstStatement.setPreCondition(CbcmodelFactory.eINSTANCE.createCondition());
		firstStatement.getPreCondition().setName("firstStatement precondition");
		firstStatement.setPostCondition(CbcmodelFactory.eINSTANCE.createCondition());
		firstStatement.getPostCondition().setName("firstStatement postcondition");
		
		AbstractStatement secondStatement = CbcmodelFactory.eINSTANCE.createAbstractStatement();
		secondStatement.setName("statement");
		secondStatement.setPreCondition(CbcmodelFactory.eINSTANCE.createCondition());
		secondStatement.getPreCondition().setName("secondStatement precondition"+createRandomUUID().charAt(0));
		
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
			if (foundStatement == null) {
				var jsdklfjsd = 2;
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
			if (foundStatement == null) {
				var lsdnjfsknf = 2;
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

	public CbCFormula getMetaMethodFormula() {
		return metaMethodFormula;
	}
}
