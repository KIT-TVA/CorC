package de.tu_bs.cs.isf.taxonomy.graphiti.features;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.features.impl.AbstractLayoutFeature;
import org.eclipse.graphiti.mm.algorithms.styles.PrecisionPoint;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.CurvedConnection;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

/**
 * Class to layout the connection
 * @author Tobias
 *
 */
public class LayoutConnectionFeature extends AbstractLayoutFeature {

	/**
     * Constructor of the class
     * @param fp	The FeatureProvider
     */
	public LayoutConnectionFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canLayout(ILayoutContext context) {
		PictogramElement pe = context.getPictogramElement();
		if (!(pe instanceof Connection)) {
			return false;
		}
		return true;
	}

	@Override
	public boolean layout(ILayoutContext context) {
		CurvedConnection connection = (CurvedConnection) context.getPictogramElement();
		ContainerShape start = (ContainerShape) connection.getStart().getParent();
		ContainerShape end = (ContainerShape) connection.getEnd().getParent();
		PrecisionPoint point1 = connection.getControlPoints().get(1);
		if (start.getGraphicsAlgorithm().getX() > end.getGraphicsAlgorithm().getX()) {
			point1.setY(50d);
			return true;
		} else {
			point1.setY(-50d);
			return true;
		}
	}
}
