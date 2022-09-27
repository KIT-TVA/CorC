package workspace_behavior;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.services.Graphiti;

/**
 * Class for adjusting the Project-structure based on the changes in the
 * feature-model
 * 
 * @author David
 */
public class ProjectStructure {
	private IProject project;

	public ProjectStructure(IProject project) {
		this.project = project;
	}

	public void update(FeatureDelta featureDelta, boolean createParentMethods) throws CoreException {
		IFolder featureFolder = project.getFolder("features/" + featureDelta.getName());
		IFolder featureFolderDiagram = project.getFolder("features/" + featureDelta.getName() + "/diagram");
		featureFolder.create(false, true, null);
		featureFolderDiagram.create(false, true, null);
		if (createParentMethods) {
			for (int i = 0; i < featureDelta.getParentMethodNames().size(); i++) {
				ResourceSet resourceSet = new ResourceSetImpl();
				URI uri = URI.createURI(featureFolder.getLocationURI() + "/" + "diagram/"
						+ featureDelta.getParentMethodNames().get(i) + ".diagram");
				// create the diagram and its file
				Diagram diagram = Graphiti.getPeCreateService().createDiagram("cbc",
						featureDelta.getParentMethodNames().get(i), true);
				Resource diagramResource = resourceSet.createResource(uri);
				diagramResource.getContents().add(diagram);
				try {
					diagramResource.save(Collections.EMPTY_MAP);
					diagramResource.setTrackingModification(true);
				} catch (IOException e) {
					e.printStackTrace();
				}
				// refresh
				try {
					ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
