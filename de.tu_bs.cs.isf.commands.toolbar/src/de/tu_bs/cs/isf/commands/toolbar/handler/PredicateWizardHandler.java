package de.tu_bs.cs.isf.commands.toolbar.handler;

import de.tu_bs.cs.isf.corc.predicateWizard.PredicateWizard;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

public class PredicateWizardHandler extends AbstractHandler {

	
	public Object execute(ExecutionEvent event) throws ExecutionException {
		System.out.println("----- Triggered Predicate Management -----");
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
		
		WizardDialog wizardDialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), new PredicateWizard(resourceList.get(0)));
		if (wizardDialog.open() == Window.OK) {
	        System.out.println("Ok pressed");
	    } else {
	        System.out.println("Cancel pressed");
	    }
		
		return null;
	}
}