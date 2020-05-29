package de.tu_bs.cs.isf.taxonomy.property;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.platform.AbstractPropertySectionFilter;

import de.tu_bs.cs.isf.taxonomy.model.taxonomy.Method;

/**
 * Class to filter that the element is a method
 * @author Tobias
 *
 */
public class MethodFilter extends AbstractPropertySectionFilter {

	@Override
    protected boolean accept(PictogramElement pe) {
        EObject eObject =
            Graphiti.getLinkService()
            .getBusinessObjectForLinkedPictogramElement(pe);
        if (eObject instanceof Method) {
            return true;
        }
        return false;
    }
}
