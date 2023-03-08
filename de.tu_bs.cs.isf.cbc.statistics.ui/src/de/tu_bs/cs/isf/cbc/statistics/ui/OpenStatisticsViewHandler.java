package de.tu_bs.cs.isf.cbc.statistics.ui;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.internal.resources.Resource;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.handlers.HandlerUtil;
import de.tu_bs.cs.isf.cbc.tool.helper.Features;
import de.tu_bs.cs.isf.cbc.tool.helper.FileHandler;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.FileUtil;

public class OpenStatisticsViewHandler extends AbstractHandler {

	public static final Color blue = new Color(new RGB(10, 10, 200));
	// TODO: get the name of a diagram and find already executed proofs related to it
	// TODO: if a package or folder or something is clicked -> collect statistics for multiple diagrams
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		long start = System.nanoTime();
		Console.clear();
		Console.println("Start generating statistics...\n");
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
			Console.println("Nothing to generate.");
			return null;
		}
		
		List<IFile> allDiagramFiles = new LinkedList<>();
	    IResource resource = null;
		
		for(IResource res : resourceList) {
			collectAllDiagramFiles(allDiagramFiles, res);
			resource = res;
		}
		
		if (resource == null) {
			Console.println("Non valid resource selected.");
			return null;
		}
		final StatisticsDialog dialog = new StatisticsDialog(Display.getCurrent().getActiveShell());
		final URI projectUri = URI.createPlatformResourceURI(resource.getFullPath().toOSString());
		if (FileHandler.getInstance().isSPL(projectUri)) {
			Console.println("[SPL detected]", blue);
			var features = new Features(projectUri);
			while (features.getNextConfig() != null) {
				Console.println(" > Configuration: [" + features.getConfigRep() + "]", blue);
				if (dialog.setDataSPL(allDiagramFiles, features.getConfigRep())) {
					Console.println(" > Generated data.");
				} else {
					Console.println(" > Couldn't generate data.");
					return null;
				}
			}
		} else {
			// set entries 
			if (dialog.setData(allDiagramFiles)) {
				Console.println(" > Generated data.");
			} else {
				Console.println(" > Couldn't generate data.");
				return null;
			}
		}
		Console.println("\nStatistics generation done.");
		long end = System.nanoTime();
		Console.println("Time needed: " + ((end - start) / 1000000) + "ms");
		dialog.open();
		return null;
	}
	
	private void collectAllDiagramFiles(List<IFile> allDiagramFiles, IResource selectedResource) {
		if(selectedResource instanceof IFile) {
			addFileToDiagramList(allDiagramFiles, selectedResource);
			//System.out.println("file");
		}
		else if(selectedResource instanceof IFolder) {
			getFilesWithinFolder(selectedResource, allDiagramFiles);
			//System.out.println("folder");
		}
		else if(selectedResource instanceof IProject) {
			//System.out.println("project");
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
