package de.tu_bs.cs.isf.commands.toolbar.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
import org.emftext.language.java.classifiers.impl.ClassImpl;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.members.impl.FieldImpl;
import org.emftext.language.java.resource.java.util.JavaResourceUtil;
import org.emftext.language.java.statements.Statement;

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
import de.tu_bs.cs.isf.cbc.tool.helper.GenerateDiagramFromModel;
import de.tu_bs.cs.isf.cbc.tool.helper.GenerateModelFromCode;


public class GenerateDiagramFromClassHandler extends AbstractHandler {
	
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
			
			GenerateModelFromCode gmfc = new GenerateModelFromCode();
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
	
	private void buildDiagram(GenerateModelFromCode gmfc, IFile iFile, String selectedMethod) throws ExecutionException {
		ArrayList<String> jmlMethodConditions = new ArrayList<String>();

		String javaFileContent = gmfc.readFileToString(iFile.getLocation().toPortableString());

		gmfc.readJMLAnnotations(javaFileContent, jmlMethodConditions);

		EObject abstractSyntaxTreeRoot = JavaResourceUtil.getResourceContent(javaFileContent);
		CompilationUnit compilationUnit = (CompilationUnit) abstractSyntaxTreeRoot;

		if (compilationUnit.getClassifiers().isEmpty()
				|| compilationUnit.getClassifiers().get(0).getMembers().isEmpty()) {
			return;
		}
		if (compilationUnit.getNamespacesAsString() != null && !compilationUnit.getNamespacesAsString().isEmpty()) {
			gmfc.setPackageName(compilationUnit.getNamespacesAsString().substring(0, compilationUnit.getNamespacesAsString().length()-1));
		}
		gmfc.setupProjectStructure(iFile);
		
		ModelClass modelClass = gmfc.instantiateModelClass(selectedMethod);
		modelClass.setJavaClassURI(URI.createFileURI(iFile.getProjectRelativePath().toPortableString()).toFileString());
		modelClass.setPackage(gmfc.getPackageName());
		
		if (compilationUnit.getClassifiers().get(0) instanceof ClassImpl) {
			ClassImpl javaClass = (ClassImpl) compilationUnit.getClassifiers().get(0);

			for (Member member : javaClass.getMembers()) {
				if (member instanceof FieldImpl) {
					gmfc.addFieldToList((FieldImpl) member);
				}

				if (member instanceof ClassMethod) {
					ClassMethod classMethod = (ClassMethod) member;
					String methodName = classMethod.getName();
					
					if (methodName.equals(selectedMethod)) {
					
						Method method = CbcclassFactory.eINSTANCE.createMethod();
						
						Resource cbcmodelResource = gmfc.setupProjectForCbCModel(method, methodName);
	
						JavaVariables variables = CbcmodelFactory.eINSTANCE.createJavaVariables();
						gmfc.fillVariableList(variables, classMethod);
	
						//String signature = buildSignatureString(classMethod, variables);
						gmfc.settingSignature(classMethod, variables, method);
	
						//get global conditions from existing diagram
						GlobalConditions conditions = CbcmodelFactory.eINSTANCE.createGlobalConditions();
						for (EObject obj : cbcmodelResource.getContents()) {
							if (obj instanceof GlobalConditions) {
								conditions = (GlobalConditions) obj;
							}
						}
	
						CbCFormula formula = gmfc.createFormula(classMethod.getName());
						formula.setClassName(gmfc.getClassName());
						formula.setMethodName(method.getName());
						method.setCbcStartTriple(formula);
						formula.setMethodObj(method);
						variables.eSet(CbcmodelPackage.eINSTANCE.getJavaVariables_Fields(), gmfc.getFields());
						
						//parse JML contract to pre- and postconditions of cbcFormula
						String defaultAnnotation = "	/*@\r\n" + "	  @ public normal_behavior\r\n"
								+ "	  @ requires true;\r\n" + "	  @ ensures true;\r\n" + "	  @ assignable \nothing;\r\n"
								+ "	  @*/";
						String jmlAnnotation = classMethod.getAnnotationsAndModifiers().get(0).getLayoutInformations()
								.get(0).getHiddenTokenText();
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
//						modelClass.getMethods().add(method);
						gmfc.getMethods().add(method);
						for (Method m : modelClass.getMethods()) {
							gmfc.getMethods().add(m);
						}
	
						EList<Statement> listOfStatements = new BasicEList<Statement>();
						for (int j = 0; j < classMethod.getStatements().size(); j++) {
							listOfStatements.add(null);
						}
						Collections.copy(listOfStatements, classMethod.getStatements());
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
	
	private List<String> getMethodNames(IFile iFile, GenerateModelFromCode gmfc) {
		String javaFileContent = gmfc.readFileToString(iFile.getLocation().toPortableString());
		List<String> methodNames = new ArrayList<String>();
		
		EObject abstractSyntaxTreeRoot = JavaResourceUtil.getResourceContent(javaFileContent);
		CompilationUnit compilationUnit = (CompilationUnit) abstractSyntaxTreeRoot;
		
		if (compilationUnit.getClassifiers().isEmpty()
				|| compilationUnit.getClassifiers().get(0).getMembers().isEmpty()) {
			return null;
		}
		
		if (compilationUnit.getClassifiers().get(0) instanceof ClassImpl) {
			ClassImpl javaClass = (ClassImpl) compilationUnit.getClassifiers().get(0);
			
			for (Member member : javaClass.getMembers()) {
				if (member instanceof ClassMethod) {
					ClassMethod classMethod = (ClassMethod) member;
					methodNames.add(classMethod.getName());
				}
			}
		}
		return methodNames;
	}

}
