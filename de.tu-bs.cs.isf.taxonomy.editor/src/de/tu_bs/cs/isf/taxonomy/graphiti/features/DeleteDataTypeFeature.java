package de.tu_bs.cs.isf.taxonomy.graphiti.features;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IDeleteContext;
import org.eclipse.graphiti.mm.Property;
import org.eclipse.graphiti.mm.PropertyContainer;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.features.DefaultDeleteFeature;

import de.tu_bs.cs.isf.taxonomy.model.taxonomy.DataType;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.Method;

/**
 * Class to delete a DataType
 * @author Tobias
 *
 */
public class DeleteDataTypeFeature extends DefaultDeleteFeature{
	
	/**
	 * Key to get the property index
	 */
	protected static final String PROPERTY_KEY_INDEX = "org.eclipse.graphiti.pattern.id.index";
	
	/**
	 * Constructor of the class
	 * @param fp
	 */
	public DeleteDataTypeFeature(IFeatureProvider fp) {
		super(fp);
	}
	
	@Override
	public void delete(IDeleteContext context) {
		Shape shape = (Shape) context.getPictogramElement();
		int index = getIndex(shape.getGraphicsAlgorithm());
		DataType typ = (DataType) getBusinessObjectForPictogramElement(shape);
		Method method = (Method) typ.eContainer();
		for (DataType tmpType : method.getDataTypes()) {
			List<PictogramElement> listPE = Graphiti.getLinkService().getPictogramElements(getDiagram(), tmpType);
			PictogramElement pe = null;
			for (PictogramElement tmpPE : listPE) {
				if (getBusinessObjectForPictogramElement(tmpPE).equals(tmpType)) {
					pe = tmpPE;
				}
			}
			GraphicsAlgorithm ga = pe.getGraphicsAlgorithm();
			if (getIndex(ga) > index) {
				setIndex(ga, getIndex(ga) - 1);
			}
		}
		ContainerShape container = shape.getContainer();
		super.delete(context);
		layoutPictogramElement(container);
	}

	/**
	 * Gets the index of an element
	 * @param container	The element
	 * @return	The index of the element
	 */
	protected int getIndex(PropertyContainer container) {
		EList<Property> properties = container.getProperties();
		for (Property property : properties) {
			if (PROPERTY_KEY_INDEX.equals(property.getKey())) {
				return Integer.valueOf(property.getValue());
			}
		}
		return -1;
	}
	
	/**
	 * Sets the index of an element
	 * @param container	The element
	 * @param index	The new index of the element
	 */
	protected void setIndex(PropertyContainer container, int index) {
		Graphiti.getPeService().setPropertyValue(container, PROPERTY_KEY_INDEX, Integer.toString(index));
	}

}
