package de.tu_bs.cs.isf.cbc.tool.propertiesview;

import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.platform.AbstractPropertySectionFilter;

public class PictogramFilter extends AbstractPropertySectionFilter {
	@Override
	protected boolean accept(PictogramElement pictogramElement) {
		if (pictogramElement != null) {
			// basics tab should only be enabled for object oriented projects
			if (!pictogramElement.eResource().getURI().toString().contains("src/diagrams/")) {
				return true;
			}
		}
		return false;
	}
}