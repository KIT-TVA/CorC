package de.tu_bs.cs.isf.cbcclass.tool.propertiesview;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.platform.AbstractPropertySectionFilter;

import de.tu_bs.cs.isf.cbc.util.FileUtil;

public class PictogramFilter extends AbstractPropertySectionFilter {
	@Override
	protected boolean accept(PictogramElement pictogramElement) {
		// Wenn ein diagram geoeffnet wird, wird pe erkannt als Klasse DiagramImpl

		if (pictogramElement != null && pictogramElement instanceof Diagram) {
			URI uri = ((Diagram) pictogramElement).eResource().getURI();
			IProject project = FileUtil.getProjectFromFileInProject(uri);
			try {
				if (project.getNature("de.ovgu.featureide.core.featureProjectNature") != null) {
					return true;
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}