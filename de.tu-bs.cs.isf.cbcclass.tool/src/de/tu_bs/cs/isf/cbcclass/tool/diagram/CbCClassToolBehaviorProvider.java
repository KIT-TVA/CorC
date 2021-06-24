package de.tu_bs.cs.isf.cbcclass.tool.diagram;

import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.context.IDoubleClickContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.tb.DefaultToolBehaviorProvider;
import org.eclipse.graphiti.tb.IToolBehaviorProvider;

import de.tu_bs.cs.isf.cbcclass.tool.features.RenameConditionFeature;
import de.tu_bs.cs.isf.cbcclass.tool.features.RenameStatementFeature;
import de.tu_bs.cs.isf.cbcclass.tool.features.RenameVariableFeature;


public class CbCClassToolBehaviorProvider extends DefaultToolBehaviorProvider implements IToolBehaviorProvider {

	public CbCClassToolBehaviorProvider(IDiagramTypeProvider diagramTypeProvider) {
		super(diagramTypeProvider);

	}
	
	
	@Override
	public ICustomFeature getDoubleClickFeature(IDoubleClickContext context) {
		
		ICustomFeature customFeature = new RenameStatementFeature(getFeatureProvider());
		if (customFeature.canExecute(context)) {
			return customFeature;
		}
		customFeature = new RenameConditionFeature(getFeatureProvider());
		if (customFeature.canExecute(context)) {
			return customFeature;
		}
		customFeature = new RenameVariableFeature(getFeatureProvider());
		if (customFeature.canExecute(context)) {
			return customFeature;
		}
		return super.getDoubleClickFeature(context);
	}

}
