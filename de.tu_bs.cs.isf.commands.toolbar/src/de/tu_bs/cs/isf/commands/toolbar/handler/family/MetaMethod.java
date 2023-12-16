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

import de.ovgu.featureide.fm.core.ExtensionManager;
import de.ovgu.featureide.fm.core.base.IFeature;
import de.tu_bs.cs.isf.cbc.cbcclass.Field;
import de.tu_bs.cs.isf.cbc.cbcclass.Method;
import de.tu_bs.cs.isf.cbc.cbcclass.Parameter;
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
import de.tu_bs.cs.isf.cbc.tool.helper.CodeHandler;
import de.tu_bs.cs.isf.cbc.tool.helper.GenerateDiagramFromModel;
import de.tu_bs.cs.isf.cbc.tool.helper.GetDiagramUtil;
import de.tu_bs.cs.isf.cbc.tool.helper.UpdateConditionsOfChildren;
import de.tu_bs.cs.isf.cbc.tool.helper.UpdateModifiableOfConditions;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;

public class MetaMethod {
	MetaClass metaClass;
	CbCFormula metaMethodFormula;
	String metaMethodName; 
	Condition metaPreConditon, metaPostCondition;
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
	List<MetaClass> metaClasses;
	
	
	public MetaMethod(List<MetaClass> metaClasses, URI uriToRootProject, MetaClass metaClass, String className, String methodName, 
			List<MethodStruct> listOfMethods, 			//All different implementations of Method.
			String featureModelFormulaCNF, 
			ArrayList<IFeature> features, 				//Name of all features in Productline. Necessary to introduce Features Variables.
			List<String> featureOrderList,				
			List<String> featuresImplementingMethods) { //Name of all Features implementing the method
		
		this.metaClasses = metaClasses;
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
		return newHiearchie;
	}

	Condition createMetaPreCondition() {
		int cur = this.listOfMethods.size()-1;
		Condition condition = this.listOfMethods.get(cur).preCondition;
		Condition metaCondition = CbcmodelFactory.eINSTANCE.createCondition();
		metaCondition.setName(condition.getName());
		metaCondition.getModifiables().addAll(condition.getModifiables());
		String curFeature = this.listOfMethods.get(cur).nameOfFeature.toUpperCase();
		if (cur == 0) {
			metaCondition.setName("(" + MetaVariablesClass.NAME + "." + "FV_" + curFeature + " = TRUE" + " -> (" + metaCondition.getName() + "))");
			return metaCondition;
		}
		metaCondition.setName("(" + MetaVariablesClass.NAME 
				+ "." + "FV_"+ curFeature 
				+ " = TRUE" + " -> ("  + metaCondition.getName() + "))" 
				+ " & "
				+ "(" + MetaVariablesClass.NAME + "." + "FV_" + curFeature + " = FALSE" + " -> (original))");
		while (metaCondition.getName().contains("original")) {
			cur--;
			curFeature = this.listOfMethods.get(cur).nameOfFeature.toUpperCase();
			var oriCondition = this.listOfMethods.get(cur).preCondition.getName(); 
			if (cur == 0) {
				metaCondition.setName(metaCondition.getName().replaceAll("original", Matcher.quoteReplacement(
						"(" + MetaVariablesClass.NAME + "." + "FV_" + curFeature + " = TRUE" 
					    + " -> (" + oriCondition + "))")));
				break;
			} else {
				metaCondition.setName(metaCondition.getName().replaceAll("original", Matcher.quoteReplacement(
						"(" + MetaVariablesClass.NAME 
						+ "." + "FV_"+ curFeature 
						+ " = TRUE" + " -> ("  + oriCondition + "))" 
						+ " & "
						+ "(" + MetaVariablesClass.NAME + "." + "FV_" + curFeature + " = FALSE " + " -> (original))")));
			}
		}
		return metaCondition;
	}
	
	Condition createMetaPostCondition() {
		int cur = this.listOfMethods.size()-1;
		Condition condition = this.listOfMethods.get(cur).postCondition;
		Condition metaCondition = CbcmodelFactory.eINSTANCE.createCondition();
		metaCondition.setName(condition.getName());
		metaCondition.getModifiables().addAll(condition.getModifiables());
		String curFeature = this.listOfMethods.get(cur).nameOfFeature.toUpperCase();
		if (cur == 0) {
			metaCondition.setName("(" + MetaVariablesClass.NAME + "." + "FV_" + curFeature + " = TRUE" + " -> (" + metaCondition.getName() + "))");
			return metaCondition;
		}
		metaCondition.setName("(" + MetaVariablesClass.NAME 
				+ "." + "FV_"+ curFeature 
				+ " = TRUE" + " -> ("  + metaCondition.getName() + "))" 
				+ " & "
				+ "(" + MetaVariablesClass.NAME + "." + "FV_" + curFeature + " = FALSE" + " -> (original))");
		while (metaCondition.getName().contains("original")) {
			cur--;
			curFeature = this.listOfMethods.get(cur).nameOfFeature.toUpperCase();
			var oriCondition = this.listOfMethods.get(cur).postCondition.getName(); 
			if (cur == 0) {
				metaCondition.setName(metaCondition.getName().replaceAll("original", Matcher.quoteReplacement(
						"(" + MetaVariablesClass.NAME + "." + "FV_" + curFeature + " = TRUE" 
					    + " -> (" + oriCondition + "))")));
				break;
			} else {
				metaCondition.setName(metaCondition.getName().replaceAll("original", Matcher.quoteReplacement(
						"(" + MetaVariablesClass.NAME 
						+ "." + "FV_"+ curFeature 
						+ " = TRUE" + " -> ("  + oriCondition + "))" 
						+ " & "
						+ "(" + MetaVariablesClass.NAME + "." + "FV_" + curFeature + " = FALSE " + " -> (original))")));
			}
		}
		return metaCondition;
	}
	
	public Resource toResourceObject(String className) throws Exception {
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
		var rets = new ArrayList<ReturnStatement>();
		findAllReturnStatements(metaMethodFormula.getStatement(), rets);
		addReturnModifiables(rets, getReturnVar(rets, metaVariables));
		addMethodModifiables(metaMethodFormula.getStatement(), metaVariables);
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
			//Console.println("Found original: " +statementThatCallsOriginal);
			allOriginalsResolved = (statementThatCallsOriginal == null);
			if(!allOriginalsResolved) {
				statementThatCallsOriginal.setRefinement(resolveOriginal(statementThatCallsOriginal));
				//Console.println("Replaced Original with Selection: " + this.currentMethod.nameOfFeature);
			}
			this.currentMethodIndex = 0;
		}
	}
	
	private JavaVariables createMetaVariables() throws Exception {
		var metaJavaVariables = CbcmodelFactory.eINSTANCE.createJavaVariables();

		addFields(metaJavaVariables);
		for(int i = 0; i < this.listOfMethods.size(); i++) {
			addVariables(this.listOfMethods.get(i), metaJavaVariables);
			addParameters(this.listOfMethods.get(i), metaJavaVariables);
		}
		return metaJavaVariables;
	}
	
	private void findAllReturnStatements(EObject cur, List<ReturnStatement> rets) {
		if (cur instanceof ReturnStatement) {
			rets.add((ReturnStatement)cur);
		}
		for (var child : cur.eContents()) {
			findAllReturnStatements(child, rets);
		}
	}
	
	private JavaVariable getReturnVar(List<ReturnStatement> rets, JavaVariables vars) {
		for (var r : rets) {
			for (var v : vars.getVariables()) {
				if (r.getName().trim().equals(v.getName().split("\\s")[1] + ";")) {
					return v;
				}
			}
		}
		return null;
	}
	
	private void addReturnModifiables(List<ReturnStatement> rets, JavaVariable returnVariable) {
		if (returnVariable == null) {
			return;
		}
		var name = returnVariable.getName().split("\\s")[1];
		for (var r : rets) {
			if (!r.getPostCondition().getModifiables().contains(name)) {
				r.getPostCondition().getModifiables().add(name);
			}
		}
	}
	
	private void addMethodModifiables(AbstractStatement formulaStatement, JavaVariables vars) {
		for (var m : this.listOfMethods) {
			if (m.nameOfMethod.equals(this.metaMethodName)) {
				formulaStatement.getPostCondition().getModifiables().addAll(m.modifiables);
			}
		}
	}
	
	private void addFields(JavaVariables metaJavaVariables) {
		metaJavaVariables.getFields().addAll(this.metaClass.getModel().getFields());
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
				metaJavaVariables.getVariables().add(CopyCbCFormula.copyJavaVariable(variableToAdd));
			}
		}
	}
	
	private void addParameters(MethodStruct method, JavaVariables metaJavaVariables) throws Exception {
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
				metaJavaVariables.getVariables().add(CopyCbCFormula.copyParameter(variableToAdd));
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
					globalConditions.getConditions().add(CopyCbCFormula.copyCondition(conditionToAdd));
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
	
	private Resource createMetaMethod(URI uri, GlobalConditions globalConditions, JavaVariables metaVariables, CbCFormula metaMethodFormula) throws Exception {
		ResourceSet rs = new ResourceSetImpl();
		Resource metaMethodResource = rs.createResource(uri);
		SetMetaSpecificationForFormula.passMetaSpeficiationThroughFormula(metaMethodFormula, this.metaPreConditon, this.metaPostCondition);	
		this.metaClass.getMethod(this.metaMethodName).getCbcStartTriple().getStatement().getPreCondition().setName(this.metaPreConditon.getName());
		this.metaClass.getMethod(this.metaMethodName).getCbcStartTriple().getStatement().getPostCondition().setName(this.metaPostCondition.getName());
		UpdateConditionsOfChildren.updateConditionsofChildren(metaMethodFormula.getStatement().getPreCondition(), false);
		UpdateConditionsOfChildren.updateConditionsofChildren(metaMethodFormula.getStatement().getPostCondition(), false);
		placeThisInAllConditions(metaMethodFormula.getStatement());
		placeThisInGlobalConditions(globalConditions);
		//handleAbstractMethodCalls(metaMethodFormula.getStatement());
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
				((ReturnStatement)cur).getPreCondition().setName(placeThis(((ReturnStatement)cur).getPreCondition().getName()));
			}
			if (((ReturnStatement)cur).getPostCondition() != null) {
				((ReturnStatement)cur).getPostCondition().setName(placeThis(((ReturnStatement)cur).getPostCondition().getName()));
			}
			((ReturnStatement)cur).setName(placeThis(((ReturnStatement)cur).getName()));
		} else if (cur instanceof SelectionStatement) {
			if (((SelectionStatement)cur).getPreCondition() != null) {
				((SelectionStatement)cur).getPreCondition().setName(placeThis(((SelectionStatement)cur).getPreCondition().getName()));
			}
			if (((SelectionStatement)cur).getPostCondition() != null) {
				((SelectionStatement)cur).getPostCondition().setName(placeThis(((SelectionStatement)cur).getPostCondition().getName()));
			}
			for (var guard : ((SelectionStatement)cur).getGuards()) {
				guard.setName(placeThis(guard.getName()));
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
			((AbstractStatement)cur).setName(placeThis(((AbstractStatement)cur).getName()));
		}
		for (var child : cur.eContents()) {
			placeThisInAllConditions(child);
		}
	}
	
	private void handleAbstractMethodCalls(EObject cur) throws Exception {
		if (cur instanceof AbstractStatement) {
			var statement = (AbstractStatement)cur;
			if (containsMethodCall(statement.getName())) {
				var methodNames = getMethodCalls(statement.getName());
				for (var m : methodNames) {
					Method method = metaClass.getMethod(m);
					var methodPreCon = method.getCbcStartTriple().getStatement().getPreCondition();
					var methodPostCon = method.getCbcStartTriple().getStatement().getPostCondition();
					MetaClass metaClass = findMetaClass(method.getParentClass().getName());
					prefixFields(statement.getName(), metaClass.getModel().getFields(), methodPreCon);
					prefixFields(statement.getName(), metaClass.getModel().getFields(), methodPostCon);
					statement.getPreCondition().setName(methodPreCon.getName());
					statement.getPostCondition().setName(methodPostCon.getName());
				}
			}
		}
		for (var c : cur.eContents()) {
			handleAbstractMethodCalls(c);
		}
	}
	
	private boolean containsMethodCall(String code) {
		if (code == null) {
			return false;
		}
		Pattern p = Pattern.compile("\\w+\\(");
		Matcher m = p.matcher(code);
		if (m.find()) {
			return true;
		} else {
			return false;
		}
	}
	
	private MetaClass findMetaClass(String name) throws MetaMethodException {
		for (var metaClass : this.metaClasses) {
			if (metaClass.getModel().getName().equals(name)) {
				return metaClass;
			}
		}
		throw new MetaMethodException("Couldn't find a meta class with the name '" + name + "'.");
	}
	
	private void prefixFields(String statement, List<Field> fields, Condition condition) {
		var prefix = parsePrefix(statement);
		String c = condition.getName();
		for (var f : fields) {
			Pattern p = Pattern.compile("(?<!\\.)\\b" + f.getName() + "\\b");
			Matcher m = p.matcher(c);
			while(m.find()) {
				c = c.substring(0, m.start()) + prefix + c.substring(m.start(), c.length());
				m = p.matcher(c);
			}
		}
		condition.setName(c);
	}
	
	private String parsePrefix(String statement) {
		var prefix = "";
		Pattern p = Pattern.compile("\\w+\\.\\w+(\\.\\w+)?\\(");
		Matcher m = p.matcher(statement);
		while (m.find()) {
			prefix = m.group(0).substring(0, m.group(0).lastIndexOf(".") + 1);
		}
		return prefix;
	}
	
	private List<String> getMethodCalls(String code) {
		var methodNames = new ArrayList<String>();
		Pattern p = Pattern.compile("\\w+\\(");
		Matcher m = p.matcher(code);
		while (m.find()) {
			methodNames.add(m.group(0).substring(0, m.group(0).indexOf("(")));
		}
		return methodNames;
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
	
	
	private SelectionStatement resolveOriginal(AbstractStatement originalStatement) {
		
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
			//finalSkip.getPreCondition().setName("final skip statement pre condition + PARENT" +this.currentMethod.nameOfFeature);
			finalSkip.getPreCondition().setName(originalStatement.getPreCondition().getName());
			finalSkip.setPostCondition(CbcmodelFactory.eINSTANCE.createCondition());
			//finalSkip.getPostCondition().setName("final skip statement post condition + PARENT" +this.currentMethod.nameOfFeature);
			finalSkip.getPostCondition().setName(originalStatement.getPostCondition().getName());
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
		CbCFormula copiedFormula = CopyCbCFormula.copyCbCFormula(this.currentMethod.CbcFormula);
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
			var selectionGuardStatement = ((SelectionStatement) statement).getGuards().get(0);
			if(selectionGuardStatement.getName().contains("FV_"))
			{	
				if(this.currentMethodIndex < this.methodsInDescendingOrder.length-1) {
					this.currentMethodIndex = (this.currentMethodIndex +1);
					this.currentMethod = this.methodsInDescendingOrder[this.currentMethodIndex];
				}
			}
			for (AbstractStatement currentSelectionStatement : ((SelectionStatement) statement).getCommands() ) {
				if(foundStatement != null) {break;}
				foundStatement = searchOriginalStatement(currentSelectionStatement);
			}
			if(foundStatement == null && selectionGuardStatement.getName().contains("FV_")){
				String nameOfFeatureInSelectionUP = selectionGuardStatement.getName();
				Pattern pattern = Pattern.compile("FV_(.*?)\\s");
				Matcher matcher = pattern.matcher(nameOfFeatureInSelectionUP);
				if (matcher.find())
				{
					nameOfFeatureInSelectionUP = matcher.group(1); 
				}
				int searchIndex = -1;
				for(int i = 0 ; i < this.methodsInDescendingOrder.length; i++) {
					if (nameOfFeatureInSelectionUP.equals(this.methodsInDescendingOrder[i].nameOfFeature.toUpperCase())) {
						searchIndex = i;
						break;
					}
				}
				if(searchIndex != -1 && !(searchIndex == this.methodsInDescendingOrder.length -1)) {
					this.currentMethod = this.methodsInDescendingOrder[searchIndex];
					this.currentMethodIndex = searchIndex;
				}
			}
		}
		if (statement instanceof SmallRepetitionStatement) {
			foundStatement = searchOriginalStatement(((SmallRepetitionStatement) statement).getLoopStatement());
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
