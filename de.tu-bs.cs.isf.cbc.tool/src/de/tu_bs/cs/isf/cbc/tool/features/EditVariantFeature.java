package de.tu_bs.cs.isf.cbc.tool.features;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IDirectEditingContext;
import org.eclipse.graphiti.features.impl.AbstractDirectEditingFeature;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.MultiText;
import org.eclipse.graphiti.mm.pictograms.Shape;

import de.tu_bs.cs.isf.cbc.cbcmodel.Variant;

/**
 * Class to edit the name of a DataStructure
 * @author Tobias
 *
 */
public class EditVariantFeature extends AbstractDirectEditingFeature {

	/**
	 * Constructor of the class
	 * @param fp	The FeatureProvider
	 */
	public EditVariantFeature(IFeatureProvider fp) {
	    super(fp);
	}
	
	@Override
	public int getEditingType() {
	    return TYPE_MULTILINETEXT;
	}
	
	@Override
	public boolean canDirectEdit(IDirectEditingContext context) {
		Object domainObject = getBusinessObjectForPictogramElement(context.getPictogramElement());
		GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
		if (domainObject instanceof Variant && ga instanceof MultiText) {
			return true;
		}
		return false;
	}
	

	@Override
	public String getInitialValue(IDirectEditingContext context) {
		Variant variant = (Variant) getBusinessObjectForPictogramElement(context.getPictogramElement());
		return variant.getName();
	}

	@Override
	public String checkValueValid(String value, IDirectEditingContext context) {
		if (value == null || value.length() == 0) {
			return "Variant must not be empty";
		}
		return null;
	}

	@Override
	public void setValue(String value, IDirectEditingContext context) {
		Variant variant = (Variant) getBusinessObjectForPictogramElement(context.getPictogramElement());
		variant.setName(value);
		updatePictogramElement(((Shape) context.getPictogramElement()).getContainer());
	}
}
