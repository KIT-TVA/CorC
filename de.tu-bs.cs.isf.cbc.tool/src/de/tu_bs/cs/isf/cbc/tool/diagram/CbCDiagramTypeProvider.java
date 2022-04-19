package de.tu_bs.cs.isf.cbc.tool.diagram;

import org.eclipse.graphiti.dt.AbstractDiagramTypeProvider;
import org.eclipse.graphiti.features.context.impl.CustomContext;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.platform.IDiagramBehavior;
import org.eclipse.graphiti.tb.IToolBehaviorProvider;

import de.tu_bs.cs.isf.cbc.tool.features.UpdateDiagramFeature;
import de.tu_bs.cs.isf.cbc.tool.helper.HighlightHelper;

public class CbCDiagramTypeProvider extends AbstractDiagramTypeProvider {

	private IToolBehaviorProvider[] toolBehaviorProviders;

	public CbCDiagramTypeProvider() {
		super();
		setFeatureProvider(new CbCFeatureProvider(this));
	}

	@Override
	public void init(Diagram diagram, IDiagramBehavior diagramBehavior) {
		super.init(diagram, diagramBehavior);
	}
	
	@Override
	public void postInit() {
		HighlightHelper.instance.registerHighlistListener(this);
//		triggerHighlight();
	}
	
	public void triggerHighlight() {
		PictogramElement[] elements = new PictogramElement[] { getDiagram() };
		getDiagramBehavior().executeFeature(new UpdateDiagramFeature(getFeatureProvider()), new CustomContext(elements));
		HighlightHelper.instance.highlightUpdateFinished();
	}
	
	@Override
	public void dispose() {
		super.dispose();
		HighlightHelper.instance.deleteHighlistListener(this);
	}

	@Override
	public boolean isAutoUpdateAtRuntime() {
		return true;
	}
	
	@Override
	public boolean isAutoUpdateAtStartup() {
		return true;
	}

	@Override
	public boolean isAutoUpdateAtRuntimeWhenEditorIsSaved() {
		return true;
	}
	
	@Override
	public IToolBehaviorProvider[] getAvailableToolBehaviorProviders() {
		if (toolBehaviorProviders == null) {
			toolBehaviorProviders = new IToolBehaviorProvider[] { new CbCToolBehaviorProvider(this) };
		}
		return toolBehaviorProviders;
	}
}
