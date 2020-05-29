package de.tu_bs.cs.isf.taxonomy.graphiti.features;

import org.eclipse.emf.common.util.EList;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IDirectEditingContext;
import org.eclipse.graphiti.features.impl.AbstractDirectEditingFeature;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;

import de.tu_bs.cs.isf.taxonomy.model.taxonomy.Method;

/**
 * Class to edit the name of the Method
 * @author Tobias
 *
 */
public class EditMethodFeature extends AbstractDirectEditingFeature {

	/**
	 * Constructor of the class
	 * @param fp	The FeatureProvider
	 */
	public EditMethodFeature(IFeatureProvider fp) {
	    super(fp);
	}
	
	@Override
	public int getEditingType() {
	    // there are several possible editor-types supported:
	    // text-field, checkbox, color-chooser, combobox, ...
	    return TYPE_TEXT;
	}
	
	@Override
	public boolean canDirectEdit(IDirectEditingContext context) {
	    PictogramElement pe = context.getPictogramElement();
	    Object bo = getBusinessObjectForPictogramElement(pe);
	    GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
	    // support direct editing, if it is a EClass, and the user clicked
	    // directly on the text and not somewhere else in the rectangle
	    if (bo instanceof Method && ga instanceof Text) {
	        return true;
	    }
	    // direct editing not supported in all other cases
	    return false;
	}
	
	@Override
	public String getInitialValue(IDirectEditingContext context) {
	    // return the current name of the EClass
	    PictogramElement pe = context.getPictogramElement();
	    Method method = (Method) getBusinessObjectForPictogramElement(pe);
	    return method.getName();
	}
	
	@Override
	public String checkValueValid(String value, IDirectEditingContext context) {
	    if (value.length() < 1)
	        return "Please enter any text as name.";
	    if (value.contains(" "))
	        return "Spaces are not allowed in names.";
	    if (value.contains("\n"))
	        return "Line breakes are not allowed in names.";
	    if (!value.contains("("))
	        return "Method must contain \"(\".";
	    if (!value.contains(")"))
	        return "Method must contain \")\".";
	
	    Method method = (Method) getBusinessObjectForPictogramElement(context.getPictogramElement());
		EList<Shape> children = ((Shape) context.getPictogramElement()).getContainer().getChildren();
		for (Shape child : children) {
			Object domainObject = getBusinessObjectForPictogramElement(child);
			if (domainObject instanceof Method) {
				if (!domainObject.equals(method) && value.equals(((Method) domainObject).getName())) {
					return "A Method with name '" + ((Method) domainObject).getName() + "' already exists.";
				}
			}
		}
		
	    // null means, that the value is valid
	    return null;
	}
	
	@Override
	public void setValue(String value, IDirectEditingContext context) {
	    // set the new name for the Method
	    PictogramElement pe = context.getPictogramElement();
	    Method method = (Method) getBusinessObjectForPictogramElement(pe);
	    method.setName(value);
	
	    // Explicitly update the shape to display the new value in the diagram
	    // Note, that this might not be necessary in future versions of Graphiti
	    // (currently in discussion)
	
	    // we know, that pe is the Shape of the Text, so its container is the
	    // main shape
	    updatePictogramElement(((Shape) pe).getContainer());
	}
}
