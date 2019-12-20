package de.tu_bs.cs.isf.taxonomy.graphiti.diagram;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.ICreateConnectionFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.context.IDoubleClickContext;
import org.eclipse.graphiti.features.context.IPictogramElementContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.Property;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.palette.IPaletteCompartmentEntry;
import org.eclipse.graphiti.palette.impl.ConnectionCreationToolEntry;
import org.eclipse.graphiti.palette.impl.ObjectCreationToolEntry;
import org.eclipse.graphiti.palette.impl.PaletteCompartmentEntry;
import org.eclipse.graphiti.tb.ContextMenuEntry;
import org.eclipse.graphiti.tb.DefaultToolBehaviorProvider;
import org.eclipse.graphiti.tb.IContextButtonPadData;
import org.eclipse.graphiti.tb.IContextMenuEntry;
import org.eclipse.graphiti.tb.IToolBehaviorProvider;

import de.tu_bs.cs.isf.taxonomy.graphiti.features.AddInitialValueToDataStructuresFeature;
import de.tu_bs.cs.isf.taxonomy.graphiti.features.AddPseudoCodeToMethodFeature;
import de.tu_bs.cs.isf.taxonomy.graphiti.features.ChangeAbstractAlgorithmFeature;

/**
 * Own implementation of a ToolBehaviorProvider
 * @author Tobias
 *
 */
public class TaxonomyToolBehaviorProvider extends DefaultToolBehaviorProvider implements IToolBehaviorProvider {

	/**
	 * Constructor of the class
	 * @param diagramTypeProvider	The DiagramTypeProvider
	 */
	public TaxonomyToolBehaviorProvider(IDiagramTypeProvider diagramTypeProvider) {
		super(diagramTypeProvider);
	}
	
	@Override
	public IContextButtonPadData getContextButtonPad(IPictogramElementContext context) {
		IContextButtonPadData data = super.getContextButtonPad(context);
		PictogramElement pe = context.getPictogramElement();
		boolean notDelete = false;
		for (Property property : pe.getGraphicsAlgorithm().getProperties()) {
			if (property.getValue().equals("dataStructureType") || property.getValue().equals("mainMethodNameText")) {
				notDelete = true;
			}
		}
		if (notDelete) {
			setGenericContextButtons(data, pe, CONTEXT_BUTTON_UPDATE);
		} else {
			setGenericContextButtons(data, pe, CONTEXT_BUTTON_DELETE | CONTEXT_BUTTON_UPDATE);
		}
		
		return data;
	}
	
	@Override
	public ICustomFeature getDoubleClickFeature(IDoubleClickContext context) {
	    ICustomFeature customFeature =
	        new AddPseudoCodeToMethodFeature(getFeatureProvider());
	    // canExecute() tests especially if the context contains a Method
	    if (customFeature.canExecute(context)) {
	        return customFeature;
	    }
	    
	    customFeature = new ChangeAbstractAlgorithmFeature(getFeatureProvider());
	    if (customFeature.canExecute(context)) {
	        return customFeature;
	    }
	    
	    customFeature = new AddInitialValueToDataStructuresFeature(getFeatureProvider());
	    if (customFeature.canExecute(context)) {
	        return customFeature;
	    }
	 
	    return super.getDoubleClickFeature(context);
	}
	
	@Override
	public IContextMenuEntry[] getContextMenu(ICustomContext context) {
	    // create a sub-menu for all custom features
	    ContextMenuEntry subMenu = new ContextMenuEntry(null, context);
	    subMenu.setText("Custom");
	    subMenu.setDescription("Custom features submenu");
	    // display sub-menu hierarchical or flat
	    subMenu.setSubmenu(true);

	    // create a menu-entry in the sub-menu for each custom feature
	    ICustomFeature[] customFeatures = getFeatureProvider().getCustomFeatures(context);
	    for (int i = 0; i < customFeatures.length; i++) {
	         ICustomFeature customFeature = customFeatures[i];
	         if (customFeature.canExecute(context)) {
	             ContextMenuEntry menuEntry = new ContextMenuEntry(customFeature, context);
	             subMenu.add(menuEntry);
	         }
	     }

	     IContextMenuEntry ret[] = new IContextMenuEntry[] { subMenu };
	     return ret;
	}
	
	public IPaletteCompartmentEntry[] getPalette() {
	    List<IPaletteCompartmentEntry> ret =
	        new ArrayList<IPaletteCompartmentEntry>();
	 
	    // add new compartment at the end of the existing compartments
	    PaletteCompartmentEntry compartmentAlgorithmEntry =
	        new PaletteCompartmentEntry("Algorithm", null);
	    ret.add(compartmentAlgorithmEntry);
	    
	    PaletteCompartmentEntry compartmentOtherEntry =
		        new PaletteCompartmentEntry("Other", null);
	    ret.add(compartmentOtherEntry);
	    
	    PaletteCompartmentEntry compartmentConnectionEntry =
		        new PaletteCompartmentEntry("Connections", null);
	    ret.add(compartmentConnectionEntry);
	 
	    // add all create-features to the new stack-entry
	    IFeatureProvider featureProvider = getFeatureProvider();
	    ICreateFeature[] createFeatures = featureProvider.getCreateFeatures();
	    for (ICreateFeature cf : createFeatures) {
	        ObjectCreationToolEntry objectCreationToolEntry =
	               new ObjectCreationToolEntry(cf.getCreateName(),
	                 cf.getCreateDescription(), cf.getCreateImageId(),
	                    cf.getCreateLargeImageId(), cf);
	        if (cf.getCreateName().contains("Variable") 
	        		|| cf.getCreateName().contains("Condition") || cf.getCreateName().contains("Renam")) {
	        	compartmentOtherEntry.addToolEntry(objectCreationToolEntry);
	        } else {
	        	compartmentAlgorithmEntry.addToolEntry(objectCreationToolEntry);
	        }
	    }
	       
	    // add all create-connection-features to the new stack-entry
	    ICreateConnectionFeature[] createConnectionFeatures =
	         featureProvider.getCreateConnectionFeatures();
	    for (ICreateConnectionFeature cf : createConnectionFeatures) {
	        ConnectionCreationToolEntry connectionCreationToolEntry =
	            new ConnectionCreationToolEntry(cf.getCreateName(), cf
	              .getCreateDescription(), cf.getCreateImageId(),
	                cf.getCreateLargeImageId());
	                    connectionCreationToolEntry.addCreateConnectionFeature(cf);
	        compartmentConnectionEntry.addToolEntry(connectionCreationToolEntry);
	    }
	 
	    return ret.toArray(new IPaletteCompartmentEntry[ret.size()]);
	}
}