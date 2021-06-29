package de.tu_bs.cs.isf.commands.toolbar.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.internal.core.CompilationUnit;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import de.tu_bs.cs.isf.cbc.tool.helper.GenerateModelFromCode;

public class GenerateDiagramsFromClassHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().getSelection();
		if (selection != null & selection instanceof IStructuredSelection) {
			IStructuredSelection strucSelection = (IStructuredSelection) selection;
			if (strucSelection.size() != 1) {
				throw new ExecutionException("Select only one file.");
			}
			CompilationUnit u = (CompilationUnit) strucSelection.getFirstElement();
			IFile file = (IFile) u.getResource();

			final String PARM_MSG = "de.tu_bs.cs.isf.commands.toolbar.msg";
			String msg = event.getParameter(PARM_MSG);

			GenerateModelFromCode gmfc = new GenerateModelFromCode();
			gmfc.execute(file);
		}
		return null;
	}
	

}
