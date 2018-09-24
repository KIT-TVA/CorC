package de.tu_bs.cs.isf.cbc.tool.features;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IDeleteContext;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.ui.features.DefaultDeleteFeature;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;

/**
 * Class to delete a connection
 * @author Tobias
 *
 */
public class DeleteConnectionFeature extends DefaultDeleteFeature {

	/**
	 * Constructor of the class
	 * @param fp	The FeatureProvider
	 */
	public DeleteConnectionFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public void delete(IDeleteContext context) {
		Connection connection = (Connection) context.getPictogramElement();
		AbstractStatement parentStatement = (AbstractStatement) getBusinessObjectForPictogramElement(connection.getStart().getParent());
		AbstractStatement childStatement = (AbstractStatement) getBusinessObjectForPictogramElement(connection.getEnd().getParent());
		super.delete(context);
		parentStatement.setRefinement(null);
		childStatement.setParent(null);
	}
}
