package de.tu_bs.cs.isf.taxonomy.newFileWizard;



import java.io.IOException;
import java.util.Collections;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

/**
 * wizard that will let you create a new corc diagram or textual
 */
public class NewTaxFileWizard extends Wizard implements INewWizard {
	
	private chooseDiagramTypePage typePage = new chooseDiagramTypePage();
	
	@Override
	public void addPages() {
		addPage(typePage);
	}
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle("create CorC file wizard");
	}

	@Override
	public boolean performFinish() {
		//TODO: change "tax" in createDiagram

		ResourceSet resourceSet = new ResourceSetImpl();
		URI uri = URI.createURI("file:/"+ typePage.path.getText() + "/" + typePage.name.getText());
		uri = uri.appendFileExtension("diagram");			
		//create the diagram and its file:
		Diagram diagram = Graphiti.getPeCreateService().createDiagram("taxonomy", typePage.name.getText(), true);
		Resource diagramResource = resourceSet.createResource(uri);
		diagramResource.getContents().add(diagram);
		try {
			diagramResource.save(Collections.EMPTY_MAP);
			diagramResource.setTrackingModification(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//refresh so you don't have to press f5
		try {
			ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return true;
	}

}
