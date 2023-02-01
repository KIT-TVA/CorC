package de.tu_bs.cs.isf.commands.toolbar.handler.family;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import de.ovgu.featureide.fm.core.analysis.cnf.formula.FeatureModelFormula;
import de.ovgu.featureide.fm.core.base.IFeature;
import de.ovgu.featureide.fm.core.base.IFeatureModel;
import de.ovgu.featureide.fm.core.base.IFeatureStructure;
import de.ovgu.featureide.fm.core.init.FMCoreLibrary;
import de.ovgu.featureide.fm.core.io.manager.FeatureModelManager;
import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.OriginalStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.ReturnStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SkipStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.VariableKind;
import de.tu_bs.cs.isf.cbc.tool.helper.GenerateDiagramFromModel;
import de.tu_bs.cs.isf.cbc.tool.helper.UpdateConditionsOfChildren;
import de.tu_bs.cs.isf.cbc.tool.model.CbcModelUtil;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.ConstructCodeBlock;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.cbc.util.VerifyFeatures;


public class GenerateMetaProductHandler extends AbstractHandler implements IHandler{
	
	static {
		FMCoreLibrary.getInstance().install();
	}
	
	private static IFolder FEATURE_FOLDER_SELECTED_BY_USER;
	private static IProject project;
	private static URI uriToRootProject;
	private static ArrayList<MethodStruct> detectedMethods = new ArrayList<>();
	private static ArrayList<String> uniqueMetaMethodNames = new ArrayList<>();
	private static ArrayList<UniqueMetaMethod> uniqueMetaMethods = new ArrayList<>();
	private static Map<String, List<String>> alternativeFeatures = new HashMap<String, List<String>>(); //Parent: Features
	private static String FEATURE_MODEL_FORMULA_CNF = ""; 
	private static ArrayList<IFeature> FEATURES;
	private static List<String> FEATURE_ORDER;
	private static String NAME_OF_JAVA_FILE;
	private static String[] FEATURE_VARIABLES;
	private static Map<String, List<MethodStruct>> methodNameToMethodStructMap = new HashMap<String, List<MethodStruct>>();
	private static Map<String, List<String>> methodNameToImplementingFeature = new HashMap<String, List<String>>(); 
	@Override
	
	/*
	 * Important assumptions: -User triggers execute() by right clicking on folder called 'features' (-> Generate Meta Product)
							
	 *	    				  -Project is structured like: <ProjectName>/features/<FeatureName(s)>/diagram/<MethodName>.cbcmodel
	*/	
	//testkommentar um zu sehen ob smartSVN es erkennt!!!
	//esdfsdfsdfsdf
	
	public Object execute(ExecutionEvent event) throws ExecutionException {
			long start = System.nanoTime();
			Console.clear();
			
			ISelection selection = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().getSelection();
			
			if (selection != null && selection instanceof IStructuredSelection) {
				
				IStructuredSelection strucSelection = (IStructuredSelection) selection;
				
				FEATURE_FOLDER_SELECTED_BY_USER = (IFolder) strucSelection.getFirstElement();
				
				if(FEATURE_FOLDER_SELECTED_BY_USER == null) {
					Console.println("ERROR: folder 'features' not found");
					return null;
				}
			
			}
			
			project = getProject(FEATURE_FOLDER_SELECTED_BY_USER.getFullPath().toPortableString());
			Console.println("Generating MetaProduct for " + project.getName() + "...\n");
			
			uriToRootProject = URI.createPlatformResourceURI(FEATURE_FOLDER_SELECTED_BY_USER.getFullPath().toPortableString(), true).trimSegments(1);
			NAME_OF_JAVA_FILE = "MetaProduct_" + project.getName();
			
			getInformationFromFeatureModel();
			
			extractCbCModelFilesFromProject();
			
			printAllDetectedMethods();
			
			generateMetaMethodFiles();
			
			/*GenerateDiagramFromModel modelGenerator = new GenerateDiagramFromModel();
			MethodStruct firstMethod = detectedMethods.get(0);
			Resource firstMethodResource = firstMethod.toResourceObject();
			modelGenerator.execute(firstMethodResource);
			*/
			
			/* FUNKTIONIERT!
			Console.println("------Map-------");
			
			/for (Map.Entry<String, List<MethodStruct>> entry : methodNameToMethodStructMap.entrySet()) {
			    Console.println("UniqueMethod: = " + entry.getKey());
			    for(MethodStruct struct: entry.getValue()) {
			    	Console.println("\tFeature: " + struct.nameOfFeature + "\n\tMethod: " + struct.nameOfMethod);
			    }
			}*/
			
			
			
			
			
			for (Map.Entry<String, List<MethodStruct>> entry : methodNameToMethodStructMap.entrySet()) {
				//if(entry.getKey().equals("Push")) {
				List<String> implementingFeaturesOfMethod = methodNameToImplementingFeature.get(entry.getKey());
				String nameOfMethod = entry.getKey();
				List<MethodStruct> listOfMethodStructs = entry.getValue();
				
				uniqueMetaMethods.add(new UniqueMetaMethod(nameOfMethod, listOfMethodStructs, FEATURE_MODEL_FORMULA_CNF, FEATURES, FEATURE_ORDER, implementingFeaturesOfMethod));
				//}
				
			}
			
			//create and save .diagram and .cbcmodel files.
			for(UniqueMetaMethod metaMethod: uniqueMetaMethods) {
				GenerateDiagramFromModel diagramGenerator = new GenerateDiagramFromModel();
					//if(metaMethod.metaMethodName.equals("Push")) {
						diagramGenerator.execute(metaMethod.toResourceObject());
						Console.println("Generated MetaMethod File for :" + metaMethod.metaMethodName + ".diagram");
					//}
			}
			
			
			createAndSaveJavaFilesWithMethodStubs();
			
			Console.println("methodNameToImplementingFeature: " + methodNameToImplementingFeature);
			Console.println("\nMeta product generation done. Clearing data...");
			
			// clear all data
			clearData();
			long end = System.nanoTime();
			Console.println("Time needed: " + ((end - start) / 1000000) + "ms");
		return null;
	}
	
	private void clearData() {
		detectedMethods = new ArrayList<>();
		uniqueMetaMethodNames = new ArrayList<>();
		uniqueMetaMethods = new ArrayList<>();
		alternativeFeatures = new HashMap<String, List<String>>(); //Parent: Features
		FEATURE_MODEL_FORMULA_CNF = ""; 
		methodNameToMethodStructMap = new HashMap<String, List<MethodStruct>>();
		methodNameToImplementingFeature = new HashMap<String, List<String>>(); 
	}

	private void createAndSaveJavaFilesWithMethodStubs() {
		String code = "public class " + NAME_OF_JAVA_FILE + " {\n\n";
		//create and save java file with method stub for each  meta method
		
		
		
		for(UniqueMetaMethod metaMethod: uniqueMetaMethods) {
			Console.println(project.getLocation().toString());
			
			JavaVariables newVariables = CbcmodelFactory.eINSTANCE.createJavaVariables();
			
			//Um in die Feature Variablen in der Signatur zu übergeben, müssen sie den Kind PARAM kriegen.
			//Alles andere wird so kopiert wies vorher war.
			for(int i = 0 ; i < metaMethod.metaJavaVariables.getVariables().size(); i++) {
				JavaVariable copiedVariable = CbcmodelFactory.eINSTANCE.createJavaVariable();
				JavaVariable variableToCopy = metaMethod.metaJavaVariables.getVariables().get(i);
				//copiedVariable.setDisplayedName(variableToCopy.getDisplayedName());
				copiedVariable.setName(variableToCopy.getName());
				
				if(variableToCopy.getDisplayedName().contains("FV_")){
					copiedVariable.setKind(VariableKind.PARAM);
				}else {
					copiedVariable.setKind(variableToCopy.getKind());
				}
				newVariables.getVariables().add(copiedVariable);
			}
			
			
			code  += ConstructCodeBlock.constructMethodStubsForExport(metaMethod.metaMethodFormula, null, newVariables, metaMethod.metaMethodName, "");
			code += "\n\n\n";
		}
		code += "\n\n}";
		String location = project.getLocation().toString() + "/MetaProduct_GEN/" + NAME_OF_JAVA_FILE + ".java";
		this.saveJavaFile(location, code);
	}

	private void getInformationFromFeatureModel() {
		Path pathToModelXML = Paths.get(project.getLocation() + "/model.xml");
		
		 if(!Files.exists(pathToModelXML)) {
			 Console.println("Error: Cannot find model.xml in <ProjectRoot>/model.xml");
			 return;
		 } else {
			 IFeatureModel featModel =  FeatureModelManager.load(pathToModelXML);
			 
			 FEATURE_ORDER = featModel.getFeatureOrderList();
			 Console.println("Feature Order List:" + FEATURE_ORDER);
			 Console.println("Features :" + featModel.getFeatures());
			 FEATURES = new ArrayList<>(featModel.getFeatures());
			 FeatureModelFormula formula = new FeatureModelFormula(featModel);
			 //Console.println("constraints: " + featModel.getConstraints());
			 
			 FEATURE_VARIABLES = new String [FEATURES.size()];
			 for(int i = 0 ; i < FEATURES.size(); i++) {
				 FEATURE_VARIABLES[i] = "FV_" + FEATURES.get(i).toString().toUpperCase();
			 }
			 
			 Console.println("Searching for alternative Features..");
			 //Searching for alterantive Feature Group and Store children in Map 'alternativeFeatures'
			 for(IFeature currentFeature: featModel.getFeatures()) {
				 Console.println("Feature: " + currentFeature.getName());
				 IFeatureStructure currentStructure = currentFeature.getStructure();
				
				 if(currentFeature.getStructure().isAlternative()) {
					 if(!alternativeFeatures.containsKey(currentFeature.getName())) {
						 alternativeFeatures.put(currentFeature.getName(), new ArrayList<String>());
						}
					 for(int i = 0 ; i < currentFeature.getStructure().getChildren().size(); i++) {
						 alternativeFeatures.get(currentFeature.getName()).add(currentFeature.getStructure().getChildren().get(i).getFeature().getName());
						 
					 }
					 
				 }
				 
			 }
			 if(alternativeFeatures.isEmpty()) {
				 Console.println("No alternative Feature Group found.");
			 }else {
				 Console.println("Found Alternative Features:" + alternativeFeatures);
			 }
			 
			 Console.println("Rohe Formel: " + formula.getCNF());
			 Console.println("Rohe Formel: " + formula.getCNF());
			 Console.println("Rohe Formel: " + formula.getCNFNode());
			 FEATURE_MODEL_FORMULA_CNF = String.valueOf(formula.getCNFNode());
			 //FEATURE_MODEL_FORMULA_CNF = FEATURE_MODEL_FORMULA_CNF.replaceAll("\\-", "!");
			 FEATURE_MODEL_FORMULA_CNF = FEATURE_MODEL_FORMULA_CNF.toUpperCase();
			 FEATURE_MODEL_FORMULA_CNF = FEATURE_MODEL_FORMULA_CNF.replaceAll("([\\w]+)", "FV_$1");
			 FEATURE_MODEL_FORMULA_CNF = FEATURE_MODEL_FORMULA_CNF.replaceAll("\\b(?<!-)(\\w+)\\b", "$1 = TRUE");
			 FEATURE_MODEL_FORMULA_CNF = FEATURE_MODEL_FORMULA_CNF.replaceAll("-(\\w+)", "$1 = FALSE");
			 Console.println("Feature Model Formula: " + FEATURE_MODEL_FORMULA_CNF);
		 }
	}
	
	private void printAllDetectedMethods() {
		for(MethodStruct currentFeature: detectedMethods) {
			currentFeature.printFeatureToConsole();
		}
	}
	
	private void saveJavaFile(String location, String code) {
		System.out.printf("writeFile: location: %s\n", location);
		System.out.printf("writeFile: code: %s\n", code);
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
			IPath iLocation = org.eclipse.core.runtime.Path.fromOSString(javaFile.getAbsolutePath());
			IFile ifile = workspace.getRoot().getFileForLocation(iLocation);
			ifile.refreshLocal(0, null);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
	
	

	/*
	 * Searches for all .cbcmodel files in project and stores them in ArrayList MethodStruct
	 * 
	 */
	private void extractCbCModelFilesFromProject() {
		try {
			
			Console.println("-----------");
			Console.println("extractCbCModelFilesFromProject():");
			Console.println("Searching for .cbcmodel files: ");
			for(IResource currentFolderMember : FEATURE_FOLDER_SELECTED_BY_USER.members()) {
				//removes .DS_Store
				if(currentFolderMember instanceof IFolder) {
					
					// /feature/<currentfeatureNameSubFolder>
					// currentfeatureNameSubFolder = name of the feature
					IFolder currentfeatureNameSubFolder = (IFolder) currentFolderMember;
					
					String currentNameOfFeature = currentfeatureNameSubFolder.getName();
					
					for(IResource diagramFolder: currentfeatureNameSubFolder.members()) {
						
					// /feature/<currentfeatureNameSubFolder>/<className>/
						//if(diagramFolder.getFullPath().toPortableString().endsWith("diagram")) {
							
							// /feature/<currentfeatureNameSubFolder>/diagram/<currentFileInDiagram>
							for(IResource currentFileInDiagram: ((IFolder) diagramFolder).members()) {
								if(currentFileInDiagram.getFullPath().toPortableString().endsWith("cbcmodel")) {
									
									Console.println("Found cbcmodel file: " + currentFileInDiagram.getFullPath());
									
									String nameOfCurrentMethod = currentFileInDiagram.getName().split("\\.")[0];
									if(!uniqueMetaMethodNames.contains(nameOfCurrentMethod)) {
										uniqueMetaMethodNames.add(nameOfCurrentMethod);
									}	
									if(!methodNameToMethodStructMap.containsKey(nameOfCurrentMethod)) {
										methodNameToMethodStructMap.put(nameOfCurrentMethod, new ArrayList<MethodStruct>());
									}
									if(!methodNameToImplementingFeature.containsKey(nameOfCurrentMethod)) {
										methodNameToImplementingFeature.put(nameOfCurrentMethod, new ArrayList<String>());
									}
									
									methodNameToMethodStructMap.get(nameOfCurrentMethod).add(new MethodStruct(currentNameOfFeature, currentFileInDiagram));
									methodNameToImplementingFeature.get(nameOfCurrentMethod).add(currentNameOfFeature);
									createMethodStructAndAddToList(currentNameOfFeature, currentFileInDiagram);
									
								}
							}
						//}
					}
					
		
				}
			}
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void generateMetaMethodFiles() {
		Console.println("-----");
		Console.println("Generating Files for unique Methods:");
		for(String currentMethodName: uniqueMetaMethodNames) {
			Console.println("\t-" + currentMethodName + ".diagram");
		}
	}

	private void createMethodStructAndAddToList(String nameOfFeature, IResource currentFileInDiagram) {

		
		detectedMethods.add(new MethodStruct(nameOfFeature, currentFileInDiagram));
	}
	
	/*
		param: URI String path in der Form: '/<ProjectName>/features'
		return: IProject Instanz für <ProjectName>
	*/
	private static IProject getProject(String uri) {
		IProject thisProject = null;
		uri = uri.substring(1);
		int remove_index = uri.indexOf("/");
		uri = uri.substring(0, remove_index);
		for (IProject p : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			
			if (p.getName().equals(uri)) {
				thisProject = p;
				return thisProject;
			}
		}
		return null;
		
	}
	
	private class MethodStruct {
		String nameOfFeature, nameOfMethod, preCondition, postCondition;
		URI fullPlatFormPath;
		CbCFormula CbcFormula;
		JavaVariables javaVariables;
		GlobalConditions globalConditions;
		boolean callsOriginal;
		AbstractStatement originalCallingStatement;
		
		public MethodStruct(String nameOfFeature, IResource methodFile){
			
			this.fullPlatFormPath = URI.createPlatformResourceURI(methodFile.getFullPath().toPortableString(), false);
			this.CbcFormula = CbcModelUtil.readFormula(fullPlatFormPath);
			this.javaVariables = CbcModelUtil.readJavaVariables(fullPlatFormPath);
			this.globalConditions = CbcModelUtil.readGlobalConditions(fullPlatFormPath);
			
			
			this.nameOfFeature = nameOfFeature;
			this.nameOfMethod = methodFile.getName().split("\\.")[0];
			this.preCondition = CbcFormula.getStatement().getPreCondition().getName();
			this.postCondition = CbcFormula.getStatement().getPostCondition().getName();
			AbstractStatement varCall = searchVarCall(this.CbcFormula.getStatement());
			
			//Adapt method Call to: <NameOfJavaFile>.<varMethodName>(<FV_1>, ..., <FV_N> , <Arg1>,..., <ArgN>);
			if(varCall != null) {
			      String paramString = "";
			      for (int i = 0 ; i < FEATURE_VARIABLES.length; i++) {
			        paramString += FEATURE_VARIABLES[i];
			        paramString += ", ";
			      }
			      String newName = varCall.getName().replaceAll("(\\w+)\\(", NAME_OF_JAVA_FILE + ".$1(" + paramString);
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
	
	private class UniqueMetaMethod {
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
		public UniqueMetaMethod(String methodName, 
				List<MethodStruct> listOfMethods, 			//All different implementations of Method.
				String featureModelFormulaCNF, 
				ArrayList<IFeature> features, 				//Name of all features in Productline. Necessary to introduce Features Variables.
				List<String> featureOrderList,				
				List<String> featuresImplementingMethods) { //Name of all Features implementing the method
			
			this.metaMethodName = methodName;
			this.featureModelFormulaCNF = featureModelFormulaCNF;
			this.listOfMethods = listOfMethods;
			this.namesOfImplementingMethods = featuresImplementingMethods;
			
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
		
			
			
			
			Console.println("Generated MetaMethod for "+this.metaMethodName +":");
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
		
		
		public Resource toResourceObject() {
			URI uri = uriToRootProject.appendSegment("MetaProduct_GEN").appendSegment(this.metaMethodName).appendFileExtension("cbcmodel");
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
					
					
				} catch (IOException | CoreException e1) {
					e1.printStackTrace();
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
	
	
}
