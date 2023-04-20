package de.tu_bs.cs.isf.commands.toolbar.handler.family;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
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
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.CbcclassFactory;
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
	private URI testUri; 
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
				try {
					FEATURE_FOLDER_SELECTED_BY_USER = (IFolder) strucSelection.getFirstElement();
				} catch (ClassCastException e) {
					e.printStackTrace();
					Console.println("Please select a 'features'-folder to start generating meta products.");
					return null;
				}
				
				if(FEATURE_FOLDER_SELECTED_BY_USER == null) {
					Console.println("Please select a 'features'-folder to start generating meta products.");
					return null;
				}
			
			}
			
			project = getProject(FEATURE_FOLDER_SELECTED_BY_USER.getFullPath().toPortableString());
			Console.println("Generating MetaProduct for " + project.getName() + "...\n");
			
			this.testUri = URI.createPlatformResourceURI(project.getLocation().toOSString(), true);
			uriToRootProject = URI.createPlatformResourceURI(FEATURE_FOLDER_SELECTED_BY_USER.getFullPath().toPortableString(), true).trimSegments(1);
			NAME_OF_JAVA_FILE = project.getName();//"MetaProduct_" + project.getName();
			
			
			getInformationFromFeatureModel();

		//--------	
			
			var classesToGen = new ArrayList<String>();
			FileUtil.getCbCClasses(project).stream().map(c -> c.getURI().lastSegment()).forEach(n -> {if(!classesToGen.contains(n.split("\\.")[0])) classesToGen.add(n.split("\\.")[0]);});
			for (var cbcClass : classesToGen) {
				NAME_OF_JAVA_FILE = cbcClass;//"MetaProduct_" + cbcClass;
				extractCbCModelFilesFromClass(cbcClass);
				printAllDetectedMethods();
				determineUniqueMethods(cbcClass);
				createUniqueMethodFilesForClass(cbcClass);
				MetaClass.compose(project, cbcClass).create();
				createAndSaveJavaFilesWithMethodStubsForClass(cbcClass);
				Console.println("Generated meta product for class " + cbcClass + ".");
			}
			//createResource("test", "java");
		//-------	

			/*
			extractCbCModelFilesFromProject();
			
			printAllDetectedMethods();
			
			generateMetaMethodFiles();
		
			for (Map.Entry<String, List<MethodStruct>> entry : methodNameToMethodStructMap.entrySet()) {
				//if(entry.getKey().equals("Push")) {
				List<String> implementingFeaturesOfMethod = methodNameToImplementingFeature.get(entry.getKey());
				String nameOfMethod = entry.getKey();
				List<MethodStruct> listOfMethodStructs = entry.getValue();
				
				uniqueMetaMethods.add(new UniqueMetaMethod("", nameOfMethod, listOfMethodStructs, FEATURE_MODEL_FORMULA_CNF, FEATURES, FEATURE_ORDER, implementingFeaturesOfMethod));
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
			
			Console.println("\nMeta product generation done. Clearing data...");
		*/	
			// clear all data
		clearData();
		long end = System.nanoTime();
		Console.println("Time needed: " + ((end - start) / 1000000) + "ms");
		return null;
	}

	private void createUniqueMethodFilesForClass(String className) {
		for(UniqueMetaMethod metaMethod: uniqueMetaMethods) {
			GenerateDiagramFromModel diagramGenerator = new GenerateDiagramFromModel();
			diagramGenerator.execute(metaMethod.toResourceObject(className));
			Console.println("Generated MetaMethod File for :" + metaMethod.metaMethodName + ".diagram");
		}
	}
	
	private void createUniqueMethodFiles() {
		for(UniqueMetaMethod metaMethod: uniqueMetaMethods) {
			GenerateDiagramFromModel diagramGenerator = new GenerateDiagramFromModel();
			diagramGenerator.execute(metaMethod.toResourceObject());
			Console.println("Generated MetaMethod File for :" + metaMethod.metaMethodName + ".diagram");
		}
	}
	
	private void determineUniqueMethods(String className) {
		for (Map.Entry<String, List<MethodStruct>> entry : methodNameToMethodStructMap.entrySet()) {
			//if(entry.getKey().equals("Push")) {
			List<String> implementingFeaturesOfMethod = methodNameToImplementingFeature.get(entry.getKey());
			String nameOfMethod = entry.getKey();
			List<MethodStruct> listOfMethodStructs = entry.getValue();
			
			uniqueMetaMethods.add(new UniqueMetaMethod(this.uriToRootProject, className, nameOfMethod, listOfMethodStructs, FEATURE_MODEL_FORMULA_CNF, FEATURES, FEATURE_ORDER, implementingFeaturesOfMethod));
			//}
			
		}
	}
	

	private boolean createResource(String name, String type) {
		URI uri = uriToRootProject.appendSegment("MetaProduct_GEN").appendSegment(name);
		if (!type.isEmpty()) {
			uri.appendFileExtension(type);
		}
		ResourceSet rs = new ResourceSetImpl();
		Resource r = rs.createResource(uri);

		try {
			r.save(Collections.EMPTY_MAP);
			r.setTrackingModification(true);
			IFile ifile = ResourcesPlugin.getWorkspace().getRoot().getFile(new org.eclipse.core.runtime.Path(uri.toPlatformString(true)));
			ifile.getParent().refreshLocal(1, null);
		} catch (IOException | CoreException e1) {
			e1.printStackTrace();
			return false;
		} 
		return true;
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
	
	
	
	//Fynn
	private void createAndSaveJavaFilesWithMethodStubsForClass(String className) {
		String code = "public class " + NAME_OF_JAVA_FILE + " {\n\n";
		
		for(UniqueMetaMethod metaMethod: uniqueMetaMethods) {
			Console.println(project.getLocation().toString());
			
			JavaVariables newVariables = CbcmodelFactory.eINSTANCE.createJavaVariables();
			
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
		String location = project.getLocation().toString() + "/MetaProduct_GEN/" + className + "/" + NAME_OF_JAVA_FILE + ".java";
		this.saveJavaFile(location, code);
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
	

	
	
	// Fynn
	private void extractCbCModelFilesFromClass(String className) {
		try {
			
			Console.println("-----------");
			Console.println("Finding cbcmodel files for class " + className + ":");
			for(IResource currentFolderMember : FEATURE_FOLDER_SELECTED_BY_USER.members()) {
				// features/<fn>/
				IFolder currentfeatureNameSubFolder = (IFolder) currentFolderMember;
				String currentNameOfFeature = currentfeatureNameSubFolder.getName();
				for(IResource classFolder: currentfeatureNameSubFolder.members()) {
					if (!classFolder.getName().equals(className)) {
						continue;
					}
					// features/<fn>/className/
					for(IResource currentFileInDiagram: ((IFolder) classFolder).members()) {
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
							methodNameToMethodStructMap.get(nameOfCurrentMethod).add(new MethodStruct(this.NAME_OF_JAVA_FILE, currentNameOfFeature, this.FEATURE_VARIABLES, currentFileInDiagram));
							methodNameToImplementingFeature.get(nameOfCurrentMethod).add(currentNameOfFeature);
							detectedMethods.add(new MethodStruct(this.NAME_OF_JAVA_FILE, currentNameOfFeature, this.FEATURE_VARIABLES, currentFileInDiagram));
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
									
									methodNameToMethodStructMap.get(nameOfCurrentMethod).add(new MethodStruct(this.NAME_OF_JAVA_FILE, currentNameOfFeature, this.FEATURE_VARIABLES, currentFileInDiagram));
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

		
		detectedMethods.add(new MethodStruct(this.NAME_OF_JAVA_FILE, nameOfFeature, this.FEATURE_VARIABLES, currentFileInDiagram));
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
	
	
	
	
}
