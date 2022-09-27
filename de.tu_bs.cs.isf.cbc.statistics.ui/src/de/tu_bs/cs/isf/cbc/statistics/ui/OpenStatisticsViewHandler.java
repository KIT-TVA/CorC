package de.tu_bs.cs.isf.cbc.statistics.ui;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.handlers.HandlerUtil;

public class OpenStatisticsViewHandler extends AbstractHandler {

	// TODO: get the name of a diagram and find already executed proofs related to it
	// TODO: if a package or folder or something is clicked -> collect statistics for multiple diagrams
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().getSelection();
		List<IResource> resourceList = new LinkedList<>();
		
		if (selection != null & selection instanceof IStructuredSelection) {
			IStructuredSelection strucSelection = (IStructuredSelection) selection;
			for(Object selectedElement : strucSelection.toList()) {
				if(selectedElement instanceof IJavaElement) {
					IResource res = ((IJavaElement) selectedElement).getResource();
					resourceList.add(res);
				}
				else if(selectedElement instanceof IResource) {
					resourceList.add((IResource) selectedElement);
				}
			}
		}
		if(resourceList.isEmpty()) {
			return null;
		}
		
		List<IFile> allDiagramFiles = new LinkedList<>();
		for(IResource res : resourceList) {
			collectAllDiagramFiles(allDiagramFiles, res);
		}
		
		StatisticsDialog dialog = new StatisticsDialog(Display.getCurrent().getActiveShell());
		dialog.setData(allDiagramFiles);
		dialog.open();
		
		return null;
	}

	private void collectAllDiagramFiles(List<IFile> allDiagramFiles, IResource selectedResource) {
		if(selectedResource instanceof IFile) {
			addFileToDiagramList(allDiagramFiles, selectedResource);
			System.out.println("file");
		}
		else if(selectedResource instanceof IFolder) {
			getFilesWithinFolder(selectedResource, allDiagramFiles);
			System.out.println("folder");
		}
		else if(selectedResource instanceof IProject) {
			System.out.println("project");
		}
	}
	
	private void getFilesWithinFolder(IResource selectedResource, List<IFile> allDiagramFiles) {

		IFolder folder = (IFolder) selectedResource;
		IResource[] res;
		try {
			res = folder.members();
			for (IResource r : res) {
				if (r instanceof IFile) {
					addFileToDiagramList(allDiagramFiles, r);
				}
				if (r instanceof IFolder) {
					// TODO: handle nested folder
				}
			}
			
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	private void addFileToDiagramList(List<IFile> allDiagramFiles, IResource selectedResource) {
		if (getExtensionByStringHandling(selectedResource.getFullPath().toString()).get().equals("diagram") ) {
			allDiagramFiles.add((IFile) selectedResource);
		}
	}

	private Optional<String> getExtensionByStringHandling(String filename) {
	    return Optional.ofNullable(filename)
	      .filter(f -> f.contains("."))
	      .map(f -> f.substring(filename.lastIndexOf(".") + 1));
	}

}
