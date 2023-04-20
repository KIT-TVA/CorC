package de.tu_bs.cs.isf.commands.toolbar.handler.family;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.CbcclassFactory;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.ModelClass;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.tool.helper.GenerateModelFromCode;
import de.tu_bs.cs.isf.cbc.util.FileUtil;

public class MetaClass {
	private ModelClass[] models;
	private IProject project;
	private ModelClass metaModel;

	public static MetaClass compose(IProject project, String className) {
		var cbcClasses = collectRelevantClasses(FileUtil.getCbCClasses(project).stream().collect(Collectors.toList()), className);
		var models = new ModelClass[cbcClasses.size()];
		for (int i = 0; i < cbcClasses.size(); i++) {
			models[i] = cbcClasses.get(i);
		}
		var metaClass = new MetaClass(models, className);
		metaClass.init(project);
		return metaClass;
	}
	
	private static List<ModelClass> collectRelevantClasses(final List<org.eclipse.emf.ecore.resource.Resource> allClasses, String className) {
		final var relevantClasses = new ArrayList<ModelClass>();
		
		for (var res : allClasses) {
			var cbcClass = (ModelClass)res.getContents().get(0);
			if (isRelevant(cbcClass, className, res)) {
				relevantClasses.add(cbcClass);
			}
		}
		return relevantClasses;
	}
	
	private static boolean isRelevant(ModelClass cbcClass, String className, org.eclipse.emf.ecore.resource.Resource res) {
		return cbcClass.getName().equals(className) && !res.getURI().path().contains("MetaProduct_GEN");
	}

	private MetaClass(ModelClass[] models, String className) {
		this.models = models;
		this.metaModel = CbcclassFactory.eINSTANCE.createModelClass();
		this.metaModel.setName(className);
	}
	
	private void init(IProject project) {
		this.project = project;
		this.createFields();
		this.createMethods();
		this.createInvariants();
	}
	
	private void createFields() {
		for (ModelClass model : models) {
			for (int i = 0; i < model.getFields().size(); i++) {
				if (!containsField(model.getFields().get(i))) {
					metaModel.getFields().add(model.getFields().get(i));
					i--;
				}
			}
		}
	}
	
	private boolean containsField(Field toFind) {
		return metaModel.getFields().stream().anyMatch(f -> f.getName().equals(toFind.getName()));
	}
	
	private void createMethods() {
		for (ModelClass model : models) {
			for (int i = 0; i < model.getMethods().size(); i++) {
				if (!containsMethod(model.getMethods().get(i))) {
					metaModel.getMethods().add(model.getMethods().get(i));
					i--;
				}
			}
		}
	}

	private boolean containsMethod(Method toFind) {
		return metaModel.getMethods().stream().anyMatch(m -> m.getSignature().equals(toFind.getSignature()));
	}
	
	private void createInvariants() {
		for (ModelClass model : models) {
			for (int i = 0; i < model.getClassInvariants().size(); i++) { 
				if (!containsInvariant(model.getClassInvariants().get(i))) {
					metaModel.getClassInvariants().add(model.getClassInvariants().get(i));
					i--;
				}
			}
		}
	}
	
	private boolean containsInvariant(Condition toFind) {
		return metaModel.getClassInvariants().stream().anyMatch(m -> m.getName().equals(toFind.getName()));
	}
	
	public boolean create() {
		IFolder metaFolder = project.getFolder("MetaProduct_GEN");
		IFolder classFolder = metaFolder.getFolder(metaModel.getName());
		ResourceSet rs = new ResourceSetImpl();
		var cbcclassResource = rs
					.createResource(URI.createFileURI(classFolder.getLocation() + "\\" + metaModel.getName() + ".cbcclass"));
		cbcclassResource.getContents().add(metaModel);
		GenerateModelFromCode.saveResource(cbcclassResource);
		return false;
	}
}
