package de.tu_bs.cs.isf.commands.toolbar.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.internal.core.CompilationUnit;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import de.tu_bs.cs.isf.cbc.util.GenerateClassFromInterfaces;
import jdk.internal.misc.FileSystemOption;

public class GenerateClassesFromInterfacesHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().getSelection();
		if (selection != null & selection instanceof IStructuredSelection) {
			IStructuredSelection strucSelection = (IStructuredSelection) selection;
			if (strucSelection.size() != 1) {
				throw new ExecutionException("Select only one file/project.");
			}
			GenerateClassFromInterfaces gen = new GenerateClassFromInterfaces();
			if (strucSelection.getFirstElement() instanceof IProject) {
				IProject project = (IProject) strucSelection.getFirstElement();
				gen.execute(project);
			} else if (strucSelection.getFirstElement() instanceof IFile) {
				IFile file = (IFile) strucSelection.getFirstElement();
				gen.execute(file);
			}		
		}
		return null;
	}

}
