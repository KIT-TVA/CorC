package de.tu_bs.cs.isf.cbc.tool.propertiesview;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.platform.AbstractPropertySectionFilter;

import de.tu_bs.cs.isf.cbc.util.FileUtil;

public class PictogramFilterVariational extends AbstractPropertySectionFilter {
	@Override
	protected boolean accept(PictogramElement pictogramElement) {
		if (pictogramElement != null) {
			// check if current project is variational project
			IProject project = FileUtil.getProjectFromFileInProject(pictogramElement.eResource().getURI());
			try {
				if(project.getNature("de.ovgu.featureide.core.featureProjectNature") != null) {
					return true;
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}