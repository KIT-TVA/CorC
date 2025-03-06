package de.tu_bs.cs.isf.cbc.tool.propertiesview;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.platform.AbstractPropertySectionFilter;

import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;

/**
 * Class to filter for statements
 * 
 * @author Tobias
 *
 */
public class VariableFilter extends AbstractPropertySectionFilter {

	@Override
	protected boolean accept(PictogramElement pe) {
		EObject eObject = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe);
		if (eObject instanceof JavaVariables) {
			return true;
		}
		return false;
	}
}
