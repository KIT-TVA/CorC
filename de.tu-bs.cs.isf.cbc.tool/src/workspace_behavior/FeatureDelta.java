package workspace_behavior;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import de.ovgu.featureide.fm.core.base.IFeature;
import de.ovgu.featureide.fm.core.base.IFeatureModel;

/**
 * Class for handling Feature specific informations
 * 
 * @author David
 */
public class FeatureDelta {
	public static final String ADDED = "Added Feature";
	public static final String REMOVED = "Removed Feature";

	private IFeature parentFeature;
	private String name;
	private IFolder featureDiagramFolder;
	private IFolder parentFeatureDiagramFolder;
	private IResource[] parentMethods;
	private List<String> parentMethodNames = new ArrayList<String>();

	public FeatureDelta(IFeatureModel fm, String featureName, IProject project) {
		this.name = featureName;
		this.featureDiagramFolder = project.getFolder("features/" + featureName + "/diagram");
		this.parentFeature = fm.getFeature(featureName).getStructure().getParent().getFeature();
		this.parentFeatureDiagramFolder = project.getFolder("features/" + parentFeature + "/diagram");
		setParentMethods();
	}

	private void setParentMethods() {
		try {
			parentMethods = parentFeatureDiagramFolder.members();
		} catch (CoreException e) {
			e.printStackTrace();
		}
		if (parentMethods != null) {
			for (int j = 0; j < parentMethods.length; j++) {
				String parentMethod = "";
				int pos = parentMethods[j].getName().lastIndexOf(".");
				if (pos > 0) {
					parentMethod = parentMethods[j].getName().substring(0, pos);
				}
				if (parentMethodNames.isEmpty()) {
					parentMethodNames.add(parentMethod);
				} else {
					if (parentMethodNames.contains(parentMethod) == false) {
						parentMethodNames.add(parentMethod);
					}
				}
			}
		}
	}

	public IFeature getParent() {
		return this.parentFeature;
	}

	public IFolder getFeatureDiagramFolder() {
		return this.featureDiagramFolder;
	}

	public IFolder getParentFeatureDiagramFolder() {
		return this.parentFeatureDiagramFolder;
	}

	public IResource[] getParentMethods() {
		return this.parentMethods;
	}

	public List<String> getParentMethodNames() {
		return this.parentMethodNames;
	}

	public String getName() {
		return this.name;
	}
}
