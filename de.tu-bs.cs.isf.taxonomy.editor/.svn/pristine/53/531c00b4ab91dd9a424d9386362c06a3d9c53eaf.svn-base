package de.tu_bs.cs.isf.taxonomy.graphiti.features;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IReconnectionContext;
import org.eclipse.graphiti.features.impl.DefaultReconnectionFeature;

/**
 * Class thats forbids the reconnection
 * @author Tobias
 *
 */
public class ReconnectionFeature extends DefaultReconnectionFeature {

	/**
	 * Costructor of the class
	 * @param fp	The FeatureProvider
	 */
	public ReconnectionFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canReconnect(IReconnectionContext context) {
		return false;
	}

}
