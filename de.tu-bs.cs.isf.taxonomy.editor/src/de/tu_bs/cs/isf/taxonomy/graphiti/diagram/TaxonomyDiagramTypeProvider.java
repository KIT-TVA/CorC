package de.tu_bs.cs.isf.taxonomy.graphiti.diagram;

import org.eclipse.graphiti.dt.AbstractDiagramTypeProvider;
import org.eclipse.graphiti.tb.IToolBehaviorProvider;

/**
 * Own implementation of a DigramTypeProvider
 * @author Tobias
 *
 */
public class TaxonomyDiagramTypeProvider extends AbstractDiagramTypeProvider{
	
	/**
	 * A list of ToolBehaviorProvider
	 */
	private IToolBehaviorProvider[] toolBehaviorProviders;
	
	/**
	 * Creates a DiagramTypeProvider and connects the FeatureProvider
	 */
	public TaxonomyDiagramTypeProvider() {
		super();
		setFeatureProvider(new TaxonomyFeatureProvider(this));
	}
	
	@Override
	public IToolBehaviorProvider[] getAvailableToolBehaviorProviders() {
		if (toolBehaviorProviders == null) {
			toolBehaviorProviders = new IToolBehaviorProvider[] { new TaxonomyToolBehaviorProvider(this) };
		}
		return toolBehaviorProviders;
	}
}
