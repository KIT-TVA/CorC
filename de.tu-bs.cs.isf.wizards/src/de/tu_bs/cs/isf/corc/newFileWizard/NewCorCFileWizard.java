package de.tu_bs.cs.isf.corc.newFileWizard;



import java.io.File;
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
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

/**
 * wizard that will let you create a new corc diagram or textual
 */
public class NewCorCFileWizard extends Wizard implements INewWizard {
	
	private chooseDiagramTypePage typePage = new chooseDiagramTypePage();
	private Resource diagramResource;
	private Resource modelResource;
	private Diagram diagram;
	
	
	@Override
	public void addPages() {
		addPage(typePage);
	}
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle("create CorC file wizard");
	}
	
	/**
	 * creates diagram and resource for a new corc diagram or corc textual file
	 */
	@Override
	public boolean performFinish() {
		if (typePage.name.getText() == null) {
			return false;
		}
		
		//create a new corc diagram:
		if (typePage.corcdiagram) {		
			ResourceSet resourceSet = new ResourceSetImpl();
			URI diagramuri = URI.createURI("file:/"+ typePage.path.getText() + "/" + typePage.name.getText());
			URI modeluri = diagramuri;
			diagramuri = diagramuri.appendFileExtension("diagram");		
			modeluri = modeluri.appendFileExtension("cbcmodel");
			
			//create the diagram and its file:
			diagram = Graphiti.getPeCreateService().createDiagram("cbc", typePage.name.getText(), true);
			diagramResource = resourceSet.createResource(diagramuri);
			diagramResource.getContents().add(diagram);
			try {
				diagramResource.save(Collections.EMPTY_MAP);
				diagramResource.setTrackingModification(true);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			modelResource = resourceSet.createResource(modeluri);
			try {
				modelResource.save(Collections.EMPTY_MAP);
				modelResource.setTrackingModification(true);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			//refresh so you don't have to press f5
			try {
				ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
			} catch (CoreException e) {
				e.printStackTrace();
			}
			
			return true;
		} //create a new corc textual:
		else if (typePage.corctextual) {
			File cbctext = new File(typePage.path.getText(), typePage.name.getText() + ".cbctxt");
			try {
				cbctext.createNewFile();
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
		} else if (typePage.corcclass) {
			
			ResourceSet resourceSet = new ResourceSetImpl();
			String absolute = ResourcesPlugin.getWorkspace().getRoot().getLocationURI().toString().replace("file:/", "");
			URI diagramuri = URI.createPlatformResourceURI(typePage.path.getText().replace(absolute, "") + "/" + typePage.name.getText());
			URI modeluri = diagramuri;
			diagramuri = diagramuri.appendFileExtension("diagram");		
			modeluri = modeluri.appendFileExtension("cbcclass");
			
			//create the diagram and its file:
			diagram = Graphiti.getPeCreateService().createDiagram("cbcclass", typePage.name.getText(), true);

			diagramResource = resourceSet.createResource(diagramuri);
			diagramResource.getContents().add(diagram);
			
			try {
				diagramResource.save(Collections.EMPTY_MAP);
				diagramResource.setTrackingModification(true);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			diagramResource = resourceSet.createResource(modeluri);
			try {
				diagramResource.save(Collections.EMPTY_MAP);
				diagramResource.setTrackingModification(true);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			//refresh so you don't have to press f5
			try {
				ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
			} catch (CoreException e) {
				e.printStackTrace();
			}
			
			return true;
			
			
		}
		return false;
	}
	
	public WizardPage getFirstPage() {
		return typePage;
	}
	
	/**
	 * is null until the user selected corc diagram and pressed finish in the wizard
	 * @return the resource from the cbcmodel the user created
	 */
	public Resource getCreatedModelResource() {
		return modelResource;
	}
	
	/**
	 * is null until the user selected corc diagram and pressed finish in the wizard
	 * @return the resource from the diagram the user created
	 */
	public Resource getCreatedDiagramResource() {
		return diagramResource;
	}
	
	/**
	 * is null until the user selected corc diagram and pressed finish in the wizard
	 * @return the diagram the user created
	 */
	public Diagram getCreatedDiagram() {
		return diagram;
	}
}
