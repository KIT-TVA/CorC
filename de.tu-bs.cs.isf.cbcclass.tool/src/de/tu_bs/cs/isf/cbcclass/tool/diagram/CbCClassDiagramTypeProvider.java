package de.tu_bs.cs.isf.cbcclass.tool.diagram;

import org.eclipse.graphiti.dt.AbstractDiagramTypeProvider;
import org.eclipse.graphiti.tb.IToolBehaviorProvider;

public class CbCClassDiagramTypeProvider extends AbstractDiagramTypeProvider {

	private IToolBehaviorProvider[] toolBehaviorProviders;

	public CbCClassDiagramTypeProvider() {
		super();
		setFeatureProvider(new CbCClassFeatureProvider(this));
	}

	@Override
	public IToolBehaviorProvider[] getAvailableToolBehaviorProviders() {
		if (toolBehaviorProviders == null) {
			toolBehaviorProviders = new IToolBehaviorProvider[]{new CbCClassToolBehaviorProvider(this)};
		}
		return toolBehaviorProviders;
	}

}
