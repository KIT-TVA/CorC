package de.tu_bs.cs.isf.cbc.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import de.tu_bs.cs.isf.cbc.util.consts.MetaNames;

public class GetDiagramUtil {

	public static Collection<Diagram> getDiagrams(IProject p, boolean includeMetaDiagrams) {
		final List<IFile> files = getDiagramFiles(p);
		final List<Diagram> diagramList = new ArrayList<Diagram>();
		final ResourceSet rSet = new ResourceSetImpl();
		for (final IFile file : files) {
			if (!includeMetaDiagrams && file.getLocation().toOSString().contains(MetaNames.FOLDER_NAME)) {
				continue;
			}
			final Diagram diagram = getDiagramFromFile(file, rSet);
			if (diagram != null) {
				diagramList.add(diagram);
			}
		}
		return diagramList;
	}

	public static Collection<Diagram> getDiagrams(IProject p) {
		return getDiagrams(p, true);
	}

	private static List<IFile> getDiagramFiles(IContainer folder) {
		final List<IFile> ret = new ArrayList<IFile>();
		try {
			final IResource[] members = folder.members();
			for (final IResource resource : members) {
				if (resource instanceof IContainer) {
					ret.addAll(getDiagramFiles((IContainer) resource));
				} else if (resource instanceof IFile) {
					final IFile file = (IFile) resource;
					if (file.getName().endsWith(".diagram")) {
						ret.add(file);
					}
				}
			}
		} catch (final CoreException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static Diagram getDiagramFromFile(IFile file, ResourceSet resourceSet) {
		final URI resourceURI = getFileURI(file, resourceSet);
		Resource resource;
		try {
			resource = resourceSet.getResource(resourceURI, true);
			if (resource != null) {
				// does resource contain a diagram as root object?
				final EList<EObject> contents = resource.getContents();
				for (final EObject object : contents) {
					if (object instanceof Diagram) {
						return (Diagram) object;
					}
				}
			}
		} catch (final WrappedException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Diagram getDiagramFromResource(Resource resource) {
		if (resource != null) {
			URI uri = resource.getURI();
			if (!uri.fileExtension().equals("diagram")) {
				uri = uri.trimFragment();
				uri = uri.trimFileExtension();
				uri = uri.appendFileExtension("diagram");
				ResourceSet rSet = new ResourceSetImpl();
				resource = rSet.getResource(uri, true);
			}
			if (resource != null) {
				// does resource contain a diagram as root object?
				final EList<EObject> contents = resource.getContents();
				for (final EObject object : contents) {
					if (object instanceof Diagram) {
						return (Diagram) object;
					}
				}
			}

		}
		return null;
	}

	public static Resource getResourceFromFile(IFile file, ResourceSet resourceSet) {
		final URI resourceURI = getFileURI(file, resourceSet);
		Resource resource = resourceSet.getResource(resourceURI, true);
		return resource;
	}

	private static URI getFileURI(IFile file, ResourceSet resourceSet) {
		final String pathName = file.getLocation().toPortableString();
		URI resourceURI = URI.createFileURI(pathName);
		resourceURI = resourceSet.getURIConverter().normalize(resourceURI);
		return resourceURI;
	}
}