package de.kit.tva.lost.models;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.core.internal.resources.File;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.ui.PlatformUI;

import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;

public class DiagramResourceModel {
    private static DiagramResourceModel instance;
    private Resource diagramResource;

    private GlobalConditions conds;
    private Renaming renaming;
    private JavaVariables vars;
    private CbCFormula formula;

    private DiagramResourceModel() {
    }

    public static DiagramResourceModel getInstance() {
	if (instance == null) {
	    instance = new DiagramResourceModel();
	}
	return instance;
    }

    public Resource get(String name) throws DiagramResourceModelException, IOException {
	this.conds = null;
	this.renaming = null;
	this.vars = null;
	this.formula = null;
	ResourceSet resourceSet = new ResourceSetImpl();
	IFolder currentFolder = getCurrentFolder();
	var diagramResFile = currentFolder.getFile(name + ".cbcmodel");
	if (diagramResFile.exists()) {
	    return loadDiagramResource(diagramResFile);
	}
	URI modelUri = URI.createPlatformResourceURI(currentFolder.getFullPath() + "/" + name + ".cbcmodel");
	if (!createModelResource(resourceSet, modelUri)) {
	    throw new DiagramResourceModelException("Couldn't create the model for the diagram.");
	}
	refreshWorkspace();
	extractContents(this.diagramResource);
	return this.diagramResource;
    }

    public GlobalConditions getConds() {
	return this.conds;
    }

    public Renaming getRenaming() {
	return renaming;
    }

    public JavaVariables getVars() {
	return vars;
    }

    public CbCFormula getFormula() {
	return formula;
    }

    private Resource loadDiagramResource(IFile diagramResFile) throws DiagramResourceModelException, IOException {
	ResourceSet rSet = new ResourceSetImpl();
	URI diagramResFileUri = URI.createPlatformResourceURI(diagramResFile.getFullPath().toString(), true);
	Resource fileResource = rSet.createResource(diagramResFileUri);
	fileResource.load(null);
	extractContents(fileResource);
	return fileResource;
    }

    private boolean createModelResource(final ResourceSet resourceSet, URI modelUri) {
	diagramResource = resourceSet.createResource(modelUri);
	try {
	    diagramResource.save(Collections.EMPTY_MAP);
	    diagramResource.setTrackingModification(true);
	} catch (IOException e1) {
	    e1.printStackTrace();
	    return false;
	}
	return true;
    }

    private boolean refreshWorkspace() {
	try {
	    ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
	} catch (CoreException e) {
	    e.printStackTrace();
	    return false;
	}
	return true;
    }

    private IFolder getCurrentFolder() throws DiagramResourceModelException {
	var res = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()
		.getEditorInput().getAdapter(IResource.class);
	if (res instanceof File) {
	    res = (IFolder) res.getParent();
	}
	if (res == null) {
	    throw new DiagramResourceModelException(
		    "Please select a folder or file in the target folder in the project in which the diagram should be created.");
	}
	return (IFolder) res;
    }

    private void extractContents(Resource diagRes) {
	for (int i = 0; i < diagRes.getContents().size(); i++) {
	    if (diagRes.getContents().get(i) instanceof CbCFormula) {
		this.formula = (CbCFormula) diagRes.getContents().get(i);
	    } else if (diagRes.getContents().get(i) instanceof JavaVariables) {
		this.vars = (JavaVariables) diagRes.getContents().get(i);
	    } else if (diagRes.getContents().get(i) instanceof GlobalConditions) {
		this.conds = (GlobalConditions) diagRes.getContents().get(i);
	    } else if (diagRes.getContents().get(i) instanceof Renaming) {
		this.renaming = (Renaming) diagRes.getContents().get(i);
	    }
	}
    }
}
