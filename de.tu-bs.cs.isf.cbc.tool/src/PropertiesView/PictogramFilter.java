package PropertiesView;

import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.platform.AbstractPropertySectionFilter;

/**
 * Class for activating the properties-view, when an CorC-Diagram is opened
 * 
 * @author David
 */
public class PictogramFilter extends AbstractPropertySectionFilter {
	@Override
	protected boolean accept(PictogramElement pe) {
		if (pe != null) {
			return true;
		}
		return false;
	}
}
