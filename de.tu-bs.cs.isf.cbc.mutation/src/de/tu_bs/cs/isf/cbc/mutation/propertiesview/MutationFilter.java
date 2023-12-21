package de.tu_bs.cs.isf.cbc.mutation.propertiesview;

import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.platform.AbstractPropertySectionFilter;

public class MutationFilter extends AbstractPropertySectionFilter {
	@Override
	protected boolean accept(PictogramElement pictogramElement) {
		if (pictogramElement != null) {
			return true;
		}
		return false;
	}
}
