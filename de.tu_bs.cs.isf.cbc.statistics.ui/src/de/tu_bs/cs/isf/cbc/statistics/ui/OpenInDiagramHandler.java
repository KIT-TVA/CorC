package de.tu_bs.cs.isf.cbc.statistics.ui;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.FileEditorInput;

import de.tu_bs.cs.isf.cbc.statistics.StatisticsDatabase;
import de.tu_bs.cs.isf.cbc.statistics.StatisticsEntry;
import de.tu_bs.cs.isf.cbc.tool.helper.HighlightHelper;

public class OpenInDiagramHandler extends AbstractHandler {

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
		
		IFile file = (IFile)resourceList.get(0);
		
		StatisticsEntry dbEntry = StatisticsDatabase.instance.getLastEntrysRelatedToKeyFile(file);

		if (dbEntry == null)
			de.tu_bs.cs.isf.cbc.util.Console.println("Error: Could not find a matching statement.");
		
		Path path = new Path(dbEntry.getMapping().getCorcDiagramPath());
		IFile diagramFile = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
		
		showInEditor(diagramFile);
		HighlightHelper.instance.registerElementToHighlight(dbEntry.getMapping().getCorcElementId());
		
		return null;
	}
	
	private boolean showInEditor(IFile file) {
		IWorkbench workbench = PlatformUI.getWorkbench();
		if (file == null || workbench.getActiveWorkbenchWindow() == null)
			return false;

		IWorkbenchPage page = workbench.getActiveWorkbenchWindow().getActivePage();
		if (page == null)
			return false;

		IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(file.getName());
		try {
			page.openEditor(new FileEditorInput(file), desc.getId());
		} catch (PartInitException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
