package de.tu_bs.cs.isf.cbc.mutation.util;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import de.tu_bs.cs.isf.cbc.cbcclass.CbcclassFactory;
import de.tu_bs.cs.isf.cbc.cbcclass.Method;
import de.tu_bs.cs.isf.cbc.cbcclass.ModelClass;
import de.tu_bs.cs.isf.cbc.cbcclass.Parameter;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.exceptions.FileHandlerException;
import de.tu_bs.cs.isf.cbc.mutation.feature.Mutator;
import de.tu_bs.cs.isf.cbc.tool.helper.DiagramPartsExtractor;
import de.tu_bs.cs.isf.cbc.tool.helper.FileHandler;
import de.tu_bs.cs.isf.cbc.tool.helper.MethodHandler;
import de.tu_bs.cs.isf.cbc.tool.helper.UpdateDiagram;

public class MutatedClass {
	private Mutator mutator;
	private JavaVariables diagramVars;
	private de.tu_bs.cs.isf.cbc.cbcclass.ModelClass mutatedModel;
	int mutationCount;
	
	public MutatedClass(Mutator mutator) {
		this.mutator = mutator;
		DiagramPartsExtractor dpe = new DiagramPartsExtractor(mutator.getOriginalDiagram());
		this.diagramVars = dpe.getVars();
		this.mutationCount = 0;
	}
	
	public void generate() throws IOException, CoreException, FileHandlerException {
		if (!mutator.hasClass()) {
			return;
		}
		ModelClass clazz = FileHandler.getInstance().getClassOf(mutator.getOriginalDiagram());
		copyClass(clazz);
		addMutatedMethods();
	}
	
	private void addMutatedMethods() throws IOException, CoreException {
		if (mutatedModel == null) {
			return;
		}
		for (Diagram mutant : mutator.getMutantDiagrams()) {
			if (mutant == null) continue;
			mutatedModel.getMethods().add(createDummyMethod(mutatedModel, mutant));
		}
		mutatedModel.eResource().save(Collections.EMPTY_MAP);
	}
	
	private void copyClass(ModelClass model) throws IOException, CoreException {
		if (model == null) {
			return;
		}
		createMutatedClass(model.getName());
		mutatedModel.getFields().addAll(model.getFields());
		mutatedModel.getClassInvariants().addAll(model.getClassInvariants());
		generateMethods();
		saveClass();
		updateDiagrams();
		saveFormulae();
	}
	
	private void createMutatedClass(String mutateeName) {
		this.mutatedModel = CbcclassFactory.eINSTANCE.createModelClass();
		this.mutatedModel.setInheritsFrom(null);
		this.mutatedModel.setName(mutateeName);// + "Mutant");
	}
	
	private void generateMethods() throws IOException {
		for (Diagram mutant : mutator.getMutantDiagrams()) {
			if (mutant == null) continue;
			mutatedModel.getMethods().add(createDummyMethod(mutatedModel, mutant));
		}
	}
	
	private void saveClass() throws IOException {
		String mutationFolderPath = mutator.getMutationFolder().getLocation().toOSString();
		String newClassPath = mutationFolderPath + File.separator + mutatedModel.getName() + ".cbcclass";
		this.mutatedModel.setJavaClassURI(newClassPath);
		ResourceSet rs = new ResourceSetImpl();
		URI newClassUri = URI.createFileURI(newClassPath);
		Resource clazzResource = rs.createResource(newClassUri, null);
		clazzResource.getContents().add(mutatedModel);
		clazzResource.save(Collections.EMPTY_MAP);
	}
	
	private void updateDiagrams() throws IOException, CoreException {
		for (Diagram d : this.mutator.getMutantDiagrams()) {
			if (d == null) continue;
			DiagramPartsExtractor dpe = new DiagramPartsExtractor(d);
			dpe.getVars().getFields().addAll(this.mutatedModel.getFields());
			addParams(dpe);
			d.eResource().save(Collections.EMPTY_MAP);
		}
	}
	
	private void addParams(DiagramPartsExtractor dpe) {
		for (Method m : this.mutatedModel.getMethods()) {
			String curN = m.getName();
			String targetName = dpe.getFormula().getName();
			if (m.getName().equals(dpe.getFormula().getName())) {
				dpe.getVars().getParams().addAll(m.getParameters());
			}
		}
	}
	
	private void saveFormulae() throws IOException, CoreException {
		for (Method m : mutatedModel.getMethods()) {
			m.getCbcStartTriple().eResource().save(Collections.EMPTY_MAP);
		}
	}

	private Method createDummyMethod(ModelClass clazz, Diagram mutant) throws IOException {
		Method newM = CbcclassFactory.eINSTANCE.createMethod();
		newM.setCbcDiagramURI(mutant.getName() + ".diagram");
		newM.setParentClass(clazz);
		addDiagramParams(newM);
		DiagramPartsExtractor dpe = new DiagramPartsExtractor(mutant);
		newM.setName(mutant.getName()); // This will get overriden by setSignature.
		if (dpe.getFormula().getMethodName().contains(" ")) { 
			newM.setSignature(dpe.getFormula().getMethodName());
			// reset methodName of the formula (we used it to pass the signature) since if not done, verifying of some methods won't 
			// work because the original calls will not be resolved correctly
			dpe.getFormula().setMethodName(MethodHandler.getMethodNameFromSig(dpe.getFormula().getMethodName()));
		}
		newM.setCbcStartTriple(dpe.getFormula());
		mutant.eResource().getContents().add(newM);
		dpe.getFormula().setMethodObj(newM);
		//dpe.getFormula().setClassName(clazz.getName());
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
}
