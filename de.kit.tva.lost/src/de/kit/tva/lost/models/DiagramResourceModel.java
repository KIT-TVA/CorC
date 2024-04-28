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

public class DiagramResourceModel {
    private static DiagramResourceModel instance;
    private Resource diagramResource;
    private boolean wasCreated;

    private DiagramResourceModel() {
    }

    public static DiagramResourceModel getInstance() {
	if (instance == null) {
	    instance = new DiagramResourceModel();
	}
	return instance;
    }

    public boolean wasCreated() {
	return wasCreated;
    }

    public Resource get(String name) throws DiagramResourceModelException, IOException {
	ResourceSet resourceSet = new ResourceSetImpl();
	IFolder currentFolder = getCurrentFolder();
	var diagramFile = currentFolder.getFile(name + ".diagram");
	var diagramResFile = currentFolder.getFile(name + ".cbcmodel");
	if (diagramResFile.exists()) {
	    wasCreated = false;
	    return loadDiagramResource(diagramResFile);
	}
	wasCreated = true;
	URI modelUri = URI.createPlatformResourceURI(currentFolder.getFullPath() + "/" + name + ".cbcmodel");
	if (!createModelResource(resourceSet, modelUri)) {
	    throw new DiagramResourceModelException("Couldn't create the model for the diagram.");
	}
	refreshWorkspace();
	return this.diagramResource;
    }

    private Resource loadDiagramResource(IFile diagramResFile) throws DiagramResourceModelException, IOException {
	ResourceSet rSet = new ResourceSetImpl();
	URI diagramResFileUri = URI.createPlatformResourceURI(diagramResFile.getFullPath().toString(), true);
	Resource fileResource = rSet.createResource(diagramResFileUri);
	fileResource.load(null);
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
}
