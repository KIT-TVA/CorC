package workspace_behavior;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;

/**
 * Class for recognizing changes in the workspace
 * 
 * @author David
 */
class ProjectDelta implements IResourceDeltaVisitor {

	public IFolder featureFolder;
	public IFolder parentFeatureDiagramFolder;
	public IFolder featureDiagramFolder;
	public List<String> parentMethodNames = new ArrayList<String>();;

	@Override
	public boolean visit(IResourceDelta delta) throws CoreException {
		IResource res = delta.getResource();
		switch (delta.getKind()) {
		case IResourceDelta.ADDED:
			System.out.print("Resource ");
			System.out.print(res.getFullPath());
			System.out.println(" was added.");
			if (Objects.equals(res.getFullPath().lastSegment(), "model.xml")) {
				new FeatureModelDelta(res);
			}
			break;
		case IResourceDelta.REMOVED:
			break;
		case IResourceDelta.CHANGED:
			// only if feature-diagram changes
			if (Objects.equals(res.getFullPath().lastSegment(), "model.xml")) {
				new FeatureModelDelta(res);
			}
			break;
		}
		return true; // visit the children
	}
}
