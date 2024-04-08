package de.tu_bs.cs.isf.commands.toolbar.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import newDiagramWizard.SelectMethodWizard;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.internal.resources.File;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.jdt.internal.core.PackageFragment;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.handlers.HandlerUtil;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.stmt.Statement;

import de.tu_bs.cs.isf.cbc.cbcclass.CbcclassFactory;
import de.tu_bs.cs.isf.cbc.cbcclass.CbcclassPackage;
import de.tu_bs.cs.isf.cbc.cbcclass.Method;
import de.tu_bs.cs.isf.cbc.cbcclass.ModelClass;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.VariableKind;
import de.tu_bs.cs.isf.cbc.util.ClassOrInterfaceCollector;
import de.tu_bs.cs.isf.cbc.util.GenerateDiagramFromModel;
import de.tu_bs.cs.isf.cbc.util.GenerateModelFromCode;
import de.tu_bs.cs.isf.cbc.util.GenerateModelFromCodeNew;
import de.tu_bs.cs.isf.cbc.util.StatementsCollector;


public class GenerateDiagramFromClassHandlerNew extends AbstractHandler {
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().getSelection();
		if (selection != null & selection instanceof IStructuredSelection) {
			IStructuredSelection strucSelection = (IStructuredSelection) selection;
			if (strucSelection.size() != 1) {
				throw new ExecutionException("Select only one file.");
			}
			
			IFile file = null;
			
			//for OO Projects
			if (strucSelection.getFirstElement() instanceof org.eclipse.jdt.internal.core.CompilationUnit) {
				org.eclipse.jdt.internal.core.CompilationUnit cu = ((org.eclipse.jdt.internal.core.CompilationUnit) strucSelection.getFirstElement());
				file = ResourcesPlugin.getWorkspace().getRoot().getFile(cu.getPath());
			//for SPL Projects
			} else if (strucSelection.getFirstElement() instanceof IResource) {
				IResource res = ((File) strucSelection.getFirstElement());
				file = ResourcesPlugin.getWorkspace().getRoot().getFile(res.getFullPath());
			}
			
			if (file == null) {
				throw new ExecutionException("Select a valid file.");
			}
			
			final String PARM_MSG = "de.tu_bs.cs.isf.commands.toolbar.msg";
			String msg = event.getParameter(PARM_MSG);
			
			GenerateModelFromCodeNew gmfc = new GenerateModelFromCodeNew();
			List<String> methodNames = getMethodNames(file, gmfc);
			
			SelectMethodWizard smw = new SelectMethodWizard(methodNames);
			WizardDialog wd = new WizardDialog(null, smw);
			wd.open();
			
			if (smw.getMethodNames() == null || smw.getMethodNames().size() == 0) {
				throw new ExecutionException("Select a Method");
			}
			
			for (String name : smw.getMethodNames()) {
				buildDiagram(gmfc, file, name);
			}
			
			//Refresh Workspace
			for(IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()){
			    try {
					project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
		
	}
	
	private void buildDiagram(GenerateModelFromCodeNew gmfc, IFile iFile, String selectedMethod) throws ExecutionException {
		ArrayList<String> jmlMethodConditions = new ArrayList<String>();

		String javaFileContent = gmfc.readFileToString(iFile.getLocation().toPortableString());

		gmfc.readJMLAnnotations(javaFileContent, jmlMethodConditions);

		CompilationUnit compilationUnit = StaticJavaParser.parse(javaFileContent);
		
		if (compilationUnit.getChildNodes().isEmpty()) {
			return;
		}
		
		ClassOrInterfaceCollector collector = new ClassOrInterfaceCollector();
		collector.visit(compilationUnit, null);
		
		if (collector.getClassOrInterfaceDeclaration().getNameAsString() != null && !collector.getClassOrInterfaceDeclaration().getNameAsString().isEmpty()) {
			gmfc.setPackageName(collector.getClassOrInterfaceDeclaration().getNameAsString().substring(0, collector.getClassOrInterfaceDeclaration().getNameAsString().length()-1));
		}
		gmfc.setupProjectStructure(iFile);
		
		ModelClass modelClass = gmfc.instantiateModelClass(selectedMethod);
		modelClass.setJavaClassURI(URI.createFileURI(iFile.getProjectRelativePath().toPortableString()).toFileString());
		modelClass.setPackage(gmfc.getPackageName());
		
		if (!collector.getClassOrInterfaceDeclaration().isInterface()) {
			for (FieldDeclaration fieldDeclaration : collector.getFields()) {
				gmfc.addFieldToList(fieldDeclaration);
			}
			
			for (MethodDeclaration methodDeclaration : collector.getMethods()) {
				String methodName = methodDeclaration.getNameAsString();
				
				if (methodName.equals(selectedMethod)) {
				
					Method method = CbcclassFactory.eINSTANCE.createMethod();
					
					Resource cbcmodelResource = gmfc.setupProjectForCbCModel(method, methodName);

					JavaVariables variables = CbcmodelFactory.eINSTANCE.createJavaVariables();
					gmfc.fillVariableList(variables, methodDeclaration);

					//String signature = buildSignatureString(classMethod, variables);
					gmfc.settingSignature(methodDeclaration, variables, method);

					//get global conditions from existing diagram
					GlobalConditions conditions = CbcmodelFactory.eINSTANCE.createGlobalConditions();
					for (EObject obj : cbcmodelResource.getContents()) {
						if (obj instanceof GlobalConditions) {
							conditions = (GlobalConditions) obj;
						}
					}

					CbCFormula formula = gmfc.createFormula(methodDeclaration.getNameAsString());
					formula.setClassName(gmfc.getClassName());
					formula.setMethodName(method.getName());
					method.setCbcStartTriple(formula);
					formula.setMethodObj(method);
					variables.eSet(CbcmodelPackage.eINSTANCE.getJavaVariables_Fields(), gmfc.getFields());
					
					//parse JML contract to pre- and postconditions of cbcFormula
					String defaultAnnotation = "	/*@\r\n" + "	  @ public normal_behavior\r\n"
							+ "	  @ requires true;\r\n" + "	  @ ensures true;\r\n" + "	  @ assignable \nothing;\r\n"
							+ "	  @*/";
					String jmlAnnotation;
					Optional<Comment> comment = methodDeclaration.getComment();
					if (!comment.isPresent()) {
						jmlAnnotation = defaultAnnotation;
					} else {
						jmlAnnotation = comment.get().toString();
						if (!jmlAnnotation.contains("/*@")) {
							jmlAnnotation = defaultAnnotation;
						}
					}
					if (!jmlAnnotation.contains("/*@"))
						jmlAnnotation = defaultAnnotation;
					int index = 0;

					do {
						String currentJmlPart = "";
						index = jmlAnnotation.indexOf("also");
						if (index != -1) {
							currentJmlPart = jmlAnnotation.substring(0, index);
						} else {
							currentJmlPart = jmlAnnotation;
						}
						jmlAnnotation = jmlAnnotation.substring(index + 4);

						gmfc.addConditionsToFormula(formula, currentJmlPart, variables, method, conditions);

					} while (index != -1);
					
					cbcmodelResource.getContents().clear();
					cbcmodelResource.getContents().add(formula);
					cbcmodelResource.getContents().add(variables);
					cbcmodelResource.getContents().add(conditions);
//					modelClass.getMethods().add(method);
					gmfc.getMethods().add(method);
					for (Method m : modelClass.getMethods()) {
						gmfc.getMethods().add(m);
					}

					EList<Statement> listOfStatements = new BasicEList<Statement>();
					//TODO: check if assert statements are collected
					StatementsCollector statementsCollector = new StatementsCollector();
					statementsCollector.visit(methodDeclaration, null);
					for (int j = 0; j < statementsCollector.getStatements().size(); j++) {
						listOfStatements.add(null);
					}
					Collections.copy(listOfStatements, statementsCollector.getStatements());
					gmfc.handleListOfStatements(cbcmodelResource, listOfStatements, formula.getStatement());//throws error					
					for (int i = 0; i < variables.getVariables().size(); i++) {
						JavaVariable var = variables.getVariables().get(i);
						if (var.getKind().equals(VariableKind.PARAM) || var.getKind().equals(VariableKind.RETURN)) {
							variables.getVariables().remove(var);
							i--;
						}
					}
					gmfc.getCbcmodelResources().add(cbcmodelResource);
				}
			}
			
			modelClass.eSet(CbcclassPackage.eINSTANCE.getModelClass_Methods(), gmfc.getMethods());
			modelClass.eSet(CbcclassPackage.eINSTANCE.getModelClass_Fields(), gmfc.getFields());
			modelClass.eSet(CbcclassPackage.eINSTANCE.getModelClass_ClassInvariants(), gmfc.getInvariants());
			gmfc.getCbcclassResource().getContents().add(modelClass);
			gmfc.saveResource(gmfc.getCbcclassResource());
			// TODO: generate class diagram from model
			
			for(Resource cbcmodelResource: gmfc.getCbcmodelResources()) {
				gmfc.saveResource(cbcmodelResource);
				GenerateDiagramFromModel gdfm = new GenerateDiagramFromModel();
				gdfm.execute(cbcmodelResource);
			}
			
		}

	}
	
	private List<String> getMethodNames(IFile iFile, GenerateModelFromCodeNew gmfc) {
		String javaFileContent = gmfc.readFileToString(iFile.getLocation().toPortableString());
		List<String> methodNames = new ArrayList<String>();
		
		CompilationUnit compilationUnit = StaticJavaParser.parse(javaFileContent);
		if (compilationUnit.getChildNodes().isEmpty()) {
			return null;
		}
		
		ClassOrInterfaceCollector collector = new ClassOrInterfaceCollector();
		collector.visit(compilationUnit, null);
		
		for (MethodDeclaration methodDec : collector.getMethods()) {
			methodNames.add(methodDec.getNameAsString());
		}
		
		return methodNames;
	}

}
