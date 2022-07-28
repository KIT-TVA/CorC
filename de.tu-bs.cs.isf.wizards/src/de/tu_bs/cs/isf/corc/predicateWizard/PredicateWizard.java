package de.tu_bs.cs.isf.corc.predicateWizard;

import java.net.URI;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;


public class PredicateWizard extends Wizard{
	
	protected PredicateManagementTypePage pmtPage;
	protected PredicateManagementTypePageSPL pmtSPLPage;
	IResource resource;

    public PredicateWizard(IResource iResource) {
        super();
        this.resource = iResource;
        setNeedsProgressMonitor(true);
    }

    @Override
    public String getWindowTitle() {
        return "Predicate Management";
    }

    @Override
    public void addPages() {
    	URI uri = resource.getLocationURI();
		String[] parts = uri.toString().split("/");
		if (parts[parts.length-4].equals("features")) { 
			pmtSPLPage = new PredicateManagementTypePageSPL(this.resource);
			addPage(pmtSPLPage);
		} else {
			pmtPage = new PredicateManagementTypePage(this.resource);
			addPage(pmtPage);
		}
    }

    @Override
    public boolean performFinish() {
        // Print the result to the console
    	if (pmtSPLPage != null) {
    		pmtSPLPage.saveAndQuit();
    	} else {
    		pmtPage.saveAndQuit();
    	}
        return true;
    }
}