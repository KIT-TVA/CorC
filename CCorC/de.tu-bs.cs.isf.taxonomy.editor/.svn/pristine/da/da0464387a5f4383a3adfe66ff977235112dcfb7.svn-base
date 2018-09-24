package de.tu_bs.cs.isf.taxonomy.graphiti.features;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IDeleteContext;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.ui.features.DefaultDeleteFeature;

/**
 * Class to delete a DataType
 * @author Tobias
 *
 */
public class DeleteMethodFeature extends DefaultDeleteFeature{
	
	
	/**
	 * Constructor of the class
	 * @param fp
	 */
	public DeleteMethodFeature(IFeatureProvider fp) {
		super(fp);
	}
	
	@Override
	public void delete(IDeleteContext context) {
		Shape shape = (Shape) context.getPictogramElement();
		ContainerShape container = shape.getContainer();
		super.delete(context);
		updatePictogramElement(container);
	}
}
