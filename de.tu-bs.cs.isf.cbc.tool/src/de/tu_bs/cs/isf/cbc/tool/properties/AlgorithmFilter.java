package de.tu_bs.cs.isf.cbc.tool.properties;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.platform.AbstractPropertySectionFilter;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;

/**
 * Class to filter for statements
 * @author Tobias
 *
 */
public class AlgorithmFilter extends AbstractPropertySectionFilter {

	@Override
    protected boolean accept(PictogramElement pe) {
        EObject eObject =
            Graphiti.getLinkService()
            .getBusinessObjectForLinkedPictogramElement(pe);
        if (eObject instanceof AbstractStatement || eObject instanceof CbCFormula) {
            return true;
        }
        return false;
    }
}
