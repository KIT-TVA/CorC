package de.tu_bs.cs.isf.commands.toolbar.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.internal.resources.File;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import de.tu_bs.cs.isf.cbc.util.GenerateModelFromCode;

public class GenerateDiagramsFromClassHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().getSelection();
		if (selection != null & selection instanceof IStructuredSelection) {
			IStructuredSelection strucSelection = (IStructuredSelection) selection;
			if (strucSelection.size() != 1) {
				throw new ExecutionException("Select only one file.");
			}
			
			IFile file = null;
			
			//for OO Projects
			if (strucSelection.getFirstElement() instanceof org.eclipse.jdt.internal.core.CompilationUnit) {
				org.eclipse.jdt.internal.core.CompilationUnit cu = ((org.eclipse.jdt.internal.core.CompilationUnit) strucSelection.getFirstElement());
				file = ResourcesPlugin.getWorkspace().getRoot().getFile(cu.getPath());
			//for SPL Projects
			} else if (strucSelection.getFirstElement() instanceof IResource) {
				IResource res = ((File) strucSelection.getFirstElement());
				file = ResourcesPlugin.getWorkspace().getRoot().getFile(res.getFullPath());
			}
			
			//IResource res = ((File) strucSelection.getFirstElement());
			//IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(res.getFullPath());

			final String PARM_MSG = "de.tu_bs.cs.isf.commands.toolbar.msg";
			//String msg = event.getParameter(PARM_MSG);
			
			GenerateModelFromCode gmfc = new GenerateModelFromCode();
			gmfc.execute(file);
		}
		return null;
	}
}
