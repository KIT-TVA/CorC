package de.tu_bs.cs.isf.cbc.mutation.util;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.CbcclassFactory;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.ModelClass;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Parameter;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.mutation.feature.ImplMutator;
import de.tu_bs.cs.isf.cbc.tool.helper.DiagramPartsExtractor;
import de.tu_bs.cs.isf.cbc.util.FileUtil;

public class MutatedClass {
	private URI projectPath;
	private JavaVariables diagramVars;
	private String[] mutantNames;
	int mutationCount;
	String mutationFolderPath;
	
	public MutatedClass(Diagram originalDiagram, String[] mutantNames) {
		DiagramPartsExtractor dpe = new DiagramPartsExtractor(originalDiagram);
		this.projectPath = originalDiagram.eResource().getURI();
		this.diagramVars = dpe.getVars();
		this.mutantNames = mutantNames;
		this.mutationCount = 0;
	}
	
	public void generate() throws IOException, CoreException {
		IProject project = FileUtil.getProject(projectPath);
		IFolder mutationFolder = project.getFolder(ImplMutator.FOLDER_NAME);
		mutationFolderPath = mutationFolder.getLocation().toOSString();
		Collection<Resource> classes = FileUtil.getCbCClasses(project);
		ModelClass clazz = findClass(classes);
		copyClass(clazz);
	}
	
	private void copyClass(ModelClass model) throws IOException, CoreException {
		if (model == null) {
			return;
		}
		String newClassPath = this.mutationFolderPath + File.separator + model.getName() + ".cbcclass";
		ModelClass newC = CbcclassFactory.eINSTANCE.createModelClass();
		newC.setJavaClassURI(newClassPath);
		newC.setName(model.getName());
		newC.getFields().addAll(model.getFields());
		newC.getClassInvariants().addAll(model.getClassInvariants());
		for (String mutantName : mutantNames) {
			newC.getMethods().add(createDummyMethod(newC, mutantName));
		}
		ResourceSet rs = new ResourceSetImpl();
		URI newClassUri = URI.createFileURI(newClassPath);
		Resource clazzResource = rs.createResource(newClassUri, null);
		clazzResource.getContents().add(newC);
		clazzResource.save(Collections.EMPTY_MAP);
		ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
	}

	private ModelClass findClass(Collection<Resource> classes) {
		String className = this.projectPath.segment(this.projectPath.segmentCount()-2);
		for (Resource r : classes) {
			ModelClass clazz = (ModelClass)r.getContents().get(0);
			if (clazz.getName().equals(className)) {
				return clazz;
			}
		}
		return null;
	}
	
	private Method createDummyMethod(ModelClass clazz, String methodName) {
		Method newM = CbcclassFactory.eINSTANCE.createMethod();
		newM.setParentClass(clazz);
		newM.setName(methodName);
		addDiagramParams(newM);
		return newM;
	}
	
	private void addDiagramParams(Method newM) {
		for (Parameter p : this.diagramVars.getParams()) {
			Parameter newP = CbcclassFactory.eINSTANCE.createParameter();
			newP.setName(p.getName());
			newP.setType(p.getType());
			newM.getParameters().add(newP);
		}
	}
	
/*	
	
	private Method copyMethod(ModelClass clazz, Method method) {
		Method newM = CbcclassFactory.eINSTANCE.createMethod();
		newM.setAssignable(method.getAssignable());
		newM.setCbcDiagramURI(projectPath.toFileString().replace(".cbcmodel", ".diagram"));
		//newM.setCbcStartTriple(newFormula);
		newM.setIsStatic(method.isIsStatic());
		newM.setName(this.mutantNames[this.mutationCount]);
		newM.setParentClass(clazz);
		if (method.getCbcStartTriple().getStatement() != null) {
			newM.getPrecondition().setName(method.getPrecondition().getName());
			newM.getPostcondition().setName(method.getPostcondition().getName());
		}
		newM.setReturnType(method.getReturnType());
		//String newSignature = method.getSignature().replace(method.getName(), name);
		//newM.setSignature(newSignature);
		newM.setVisibility(method.getVisibility());
		return newM;
	}*/
}
