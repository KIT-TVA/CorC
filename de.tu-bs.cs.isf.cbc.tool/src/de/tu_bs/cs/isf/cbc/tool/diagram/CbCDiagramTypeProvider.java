package de.tu_bs.cs.isf.cbc.tool.diagram;

import org.eclipse.graphiti.dt.AbstractDiagramTypeProvider;
import org.eclipse.graphiti.tb.IToolBehaviorProvider;

public class CbCDiagramTypeProvider extends AbstractDiagramTypeProvider {

	private IToolBehaviorProvider[] toolBehaviorProviders;

	public CbCDiagramTypeProvider() {
		super();
		setFeatureProvider(new CbCFeatureProvider(this));
	}

	@Override
	public IToolBehaviorProvider[] getAvailableToolBehaviorProviders() {
		if (toolBehaviorProviders == null) {
			toolBehaviorProviders = new IToolBehaviorProvider[] { new CbCToolBehaviorProvider(this) };
		}
		return toolBehaviorProviders;
	}
}
