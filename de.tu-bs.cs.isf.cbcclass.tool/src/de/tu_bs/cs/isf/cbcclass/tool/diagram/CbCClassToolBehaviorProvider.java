package de.tu_bs.cs.isf.cbcclass.tool.diagram;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.ICreateConnectionFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IDoubleClickContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.palette.IPaletteCompartmentEntry;
import org.eclipse.graphiti.palette.impl.ConnectionCreationToolEntry;
import org.eclipse.graphiti.palette.impl.ObjectCreationToolEntry;
import org.eclipse.graphiti.palette.impl.PaletteCompartmentEntry;
import org.eclipse.graphiti.tb.DefaultToolBehaviorProvider;
import org.eclipse.graphiti.tb.IToolBehaviorProvider;

import de.tu_bs.cs.isf.cbc.tool.features.RenameConditionFeature;
import de.tu_bs.cs.isf.cbcclass.tool.features.RenameMethodClassFeature;
import de.tu_bs.cs.isf.cbcclass.tool.features.RenameModelClassInheritanceFeature;
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
		customFeature = new RenameMethodClassFeature(getFeatureProvider());
		if (customFeature.canExecute(context)) {
			return customFeature;
		}
		customFeature = new RenameModelClassInheritanceFeature(getFeatureProvider());
		if (customFeature.canExecute(context)) {
			return customFeature;
		}

		
		return super.getDoubleClickFeature(context);
	}
	
	@Override
	public IPaletteCompartmentEntry[] getPalette() {
		List<IPaletteCompartmentEntry> ret = new ArrayList<IPaletteCompartmentEntry>();

		// add new compartment at the end of the existing compartments
		PaletteCompartmentEntry compartmentObjectEntry = new PaletteCompartmentEntry("Objects", null);
		ret.add(compartmentObjectEntry);

		PaletteCompartmentEntry compartmentConnectionEntry = new PaletteCompartmentEntry("Connections", null);
		ret.add(compartmentConnectionEntry);

		// add all create-features to the new stack-entry
		IFeatureProvider featureProvider = getFeatureProvider();
		ICreateFeature[] createFeatures = featureProvider.getCreateFeatures();
		for (ICreateFeature cf : createFeatures) {
			ObjectCreationToolEntry objectCreationToolEntry = new ObjectCreationToolEntry(cf.getCreateName(),
					cf.getCreateDescription(), cf.getCreateImageId(), cf.getCreateLargeImageId(), cf);
			if (cf.getCreateName().contains("Class")){
				compartmentObjectEntry.addToolEntry(objectCreationToolEntry);
			} else if (cf.getCreateName().contentEquals("Refinement")) {
				compartmentConnectionEntry.addToolEntry(objectCreationToolEntry);
			}
		}

		// add all create-connection-features to the new stack-entry
		ICreateConnectionFeature[] createConnectionFeatures = featureProvider.getCreateConnectionFeatures();
		for (ICreateConnectionFeature cf : createConnectionFeatures) {
			ConnectionCreationToolEntry connectionCreationToolEntry = new ConnectionCreationToolEntry(
					cf.getCreateName(), cf.getCreateDescription(), cf.getCreateImageId(), cf.getCreateLargeImageId());
			connectionCreationToolEntry.addCreateConnectionFeature(cf);
			compartmentConnectionEntry.addToolEntry(connectionCreationToolEntry);
		}

		return ret.toArray(new IPaletteCompartmentEntry[ret.size()]);
	}

}
