package de.kit.tva.lost.models;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import de.tu_bs.cs.isf.cbc.cbcclass.ModelClass;
import de.tu_bs.cs.isf.cbc.util.FileUtil;

public class ClassLoader {
    private IProject project;
    private IFile classFile;
    private ModelClass modelClass;
    private boolean exists;

    public ClassLoader(URI resourceUri) {
	if (resourceUri.isPlatformResource()) {
	    resourceUri = URI.createURI(resourceUri.toPlatformString(true));
	}
	modelClass = null;
	var classPath = resourceUri.toString().replaceAll("\\\\", "/");
	project = FileUtil.getProjectLocationS(classPath);
	classPath = classPath.substring(1, classPath.lastIndexOf("/"));
	var className = classPath.substring(classPath.lastIndexOf("/") + 1, classPath.length());
	classPath = classPath.substring(classPath.indexOf("/"), classPath.length());
	classPath += "/" + className + ".cbcclass";
	classFile = project.getFile(classPath);
	exists = classFile.exists();
	read();
    }

    public boolean exists() {
	return exists;
    }

    public ModelClass get() {
	return this.modelClass;
    }

    public void read() {
	if (!exists)
	    return;
	final ResourceSet rSet = new ResourceSetImpl();
	final Resource classResource = FileUtil.getResourceFromFile(classFile, rSet);
	this.modelClass = (ModelClass) classResource.getContents().get(0);
    }

}
