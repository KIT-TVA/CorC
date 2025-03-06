package de.tu_bs.cs.isf.cbc.statistics.ui;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;

public class AddIdsToCorCDiagramElementsHandler extends AbstractHandler {

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {

	de.tu_bs.cs.isf.cbc.util.Console.println("Adding IDs ...");

	ISelection selection = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().getSelection();
	List<IResource> resourceList = new LinkedList<>();

	if (selection != null & selection instanceof IStructuredSelection) {
	    IStructuredSelection strucSelection = (IStructuredSelection) selection;
	    for (Object selectedElement : strucSelection.toList()) {
		if (selectedElement instanceof IJavaElement) {
		    IResource res = ((IJavaElement) selectedElement).getResource();
		    resourceList.add(res);
		} else if (selectedElement instanceof IResource) {
		    resourceList.add((IResource) selectedElement);
		}
	    }
	}
	if (resourceList.isEmpty()) {
	    return null;
	}

	IResource clickedFile = resourceList.get(0);
//		System.out.println("this resource was found: " + clickedFile );

	IPath path = clickedFile.getFullPath();

	String pathString = path.toString();

	pathString = pathString.replace(".diagram", ".cbcmodel");
	URI uri = URI.createPlatformResourceURI(pathString, false);

	ResourceSet resourceSet = new ResourceSetImpl();
	Resource resource;
	try {
	    resource = resourceSet.getResource(uri, true);
	} catch (Exception e) {
	    resource = resourceSet.createResource(uri);
	}

	CbCFormula formula;
	for (int i = 0; i < resource.getContents().size(); i++) {

	    if (resource.getContents().get(i) instanceof CbCFormula) {
		formula = (CbCFormula) resource.getContents().get(i);
		distributeIds(formula);
		try {
		    formula.eResource().save(Collections.EMPTY_MAP);
		} catch (IOException e) {
		    e.printStackTrace();
		}

		de.tu_bs.cs.isf.cbc.util.Console.println("... Finished adding IDs");
	    }
	}

	return null;
    }

    private boolean distributeIds(EObject root) {

	for (int i = 0; i < root.eContents().size(); i++) {
	    EObject content = root.eContents().get(i);

	    if (content instanceof AbstractStatement) {

		if (((AbstractStatement) content).getId() == null || ((AbstractStatement) content).getId().isEmpty()) {
		    ((AbstractStatement) content).generateID();
		}

	    }

	    if (content.eContents().size() > 0)
		if (distributeIds(content))
		    return true;

	}
	return false;
    }
}