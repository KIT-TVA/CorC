package de.tu_bs.cs.isf.cbc.tool.propertiesview;

import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.platform.AbstractPropertySectionFilter;

public class PictogramFilter extends AbstractPropertySectionFilter {
	@Override
	protected boolean accept(PictogramElement pictogramElement) {
		// Wenn ein diagram geoeffnet wird, wird pe erkannt als Klasse DiagramImpl
		if (pictogramElement != null) {
			return true;
		}
		return false;
	}
}
