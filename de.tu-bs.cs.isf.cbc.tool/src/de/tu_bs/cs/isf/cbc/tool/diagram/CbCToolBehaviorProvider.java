package de.tu_bs.cs.isf.cbc.tool.diagram;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.ICreateConnectionFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.context.IDoubleClickContext;
import org.eclipse.graphiti.features.context.IPictogramElementContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
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

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Variant;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.AbstractStatementImpl;
import de.tu_bs.cs.isf.cbc.tool.features.AddPseudoCodeToMethodFeature;
import de.tu_bs.cs.isf.cbc.tool.features.RenameConditionFeature;
import de.tu_bs.cs.isf.cbc.tool.features.RenameRenamingFeature;
import de.tu_bs.cs.isf.cbc.tool.features.RenameStatementFeature;
import de.tu_bs.cs.isf.cbc.tool.features.RenameVariableFeature;
import de.tu_bs.cs.isf.cbc.tool.features.RenameVariantFeature;

/**
 * Own implementation of a ToolBehaviorProvider
 * @author Tobias
 *
 */
public class CbCToolBehaviorProvider extends DefaultToolBehaviorProvider implements IToolBehaviorProvider {

	/**
	 * Constructor of the class
	 * @param diagramTypeProvider	The DiagramTypeProvider
	 */
	public CbCToolBehaviorProvider(IDiagramTypeProvider diagramTypeProvider) {
		super(diagramTypeProvider);
	}
	
	@Override
	public IContextButtonPadData getContextButtonPad(IPictogramElementContext context) {
		IContextButtonPadData data = super.getContextButtonPad(context);
		PictogramElement pe = context.getPictogramElement();
		boolean notDelete = false;
		
		if(pe.getLink().getBusinessObjects() != null) {
			for (EObject object : pe.getLink().getBusinessObjects()) {
				if (object.getClass().equals(AbstractStatementImpl.class) && ! (pe instanceof ContainerShape)) {
					AbstractStatement statement = (AbstractStatement) object;
					if (!(statement.eContainer() instanceof SelectionStatement)) {
						notDelete = true;
					}
				} else if (object instanceof Variant) {
					notDelete = true;
				} else if (object instanceof Condition) {
					EObject parent = object.eContainer();
					if (parent instanceof SelectionStatement || parent instanceof SmallRepetitionStatement || parent.eContainer() instanceof CbCFormula) {
						notDelete = true;
					}
				}
				
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
	    customFeature = new RenameStatementFeature(getFeatureProvider());
	    if (customFeature.canExecute(context)) {
	        return customFeature;
	    }
	    customFeature = new RenameConditionFeature(getFeatureProvider());
	    if (customFeature.canExecute(context)) {
	        return customFeature;
	    }
	    customFeature = new RenameVariantFeature(getFeatureProvider());
	    if (customFeature.canExecute(context)) {
	        return customFeature;
	    }
	    customFeature = new RenameVariableFeature(getFeatureProvider());
	    if (customFeature.canExecute(context)) {
	        return customFeature;
	    }
	    customFeature = new RenameRenamingFeature(getFeatureProvider());
	    if (customFeature.canExecute(context)) {
	        return customFeature;
	    }
	 
	    return super.getDoubleClickFeature(context);
	}
	
	@Override
	public IContextMenuEntry[] getContextMenu(ICustomContext context) {
	    // create a sub-menu for all custom features
	    ContextMenuEntry subMenuVerify = new ContextMenuEntry(null, context);
	    subMenuVerify.setText("Verify");
	    subMenuVerify.setDescription("Verify features submenu");
	    // display sub-menu hierarchical or flat
	    subMenuVerify.setSubmenu(true);
	    
	    ContextMenuEntry subMenuOpen = new ContextMenuEntry(null, context);
	    subMenuOpen.setText("Open");
	    subMenuOpen.setDescription("Open features submenu");
	    subMenuOpen.setSubmenu(true);
	    
	    ContextMenuEntry subMenuPrint = new ContextMenuEntry(null, context);
	    subMenuPrint.setText("Edit");
	    subMenuPrint.setDescription("Edit features submenu");
	    subMenuPrint.setSubmenu(true);
	    
	    ContextMenuEntry subMenuAdd = new ContextMenuEntry(null, context);
	    subMenuAdd.setText("Add");
	    subMenuAdd.setDescription("Add features submenu");
	    // display sub-menu hierarchical or flat
	    subMenuAdd.setSubmenu(true);

	    // create a menu-entry in the sub-menu for each custom feature
	    ICustomFeature[] customFeatures = getFeatureProvider().getCustomFeatures(context);
	    for (int i = 0; i < customFeatures.length; i++) {
	         ICustomFeature customFeature = customFeatures[i];
	         if (customFeature.canExecute(context)) {
	        	 if (customFeature.getName().contains("above") 
	        			 || customFeature.getName().contains("below")) {
	        		 ContextMenuEntry menuEntry = new ContextMenuEntry(customFeature, context);
		             subMenuAdd.add(menuEntry); 
	        	 } else if (customFeature.getName().equals("Open associated Taxonomy")) {
	        		 ContextMenuEntry menuEntry = new ContextMenuEntry(customFeature, context);
	        		 subMenuOpen.add(menuEntry);
	        	 }
	        	 else if (!customFeature.getName().contains("Generate") 
	        			 && !customFeature.getName().contains("Extract")
	        			 && !customFeature.getName().contains("Edit")
	        			 && !customFeature.getName().contains("Layout")
	        			 && !customFeature.getName().contains("Open")) {
	        		 ContextMenuEntry menuEntry = new ContextMenuEntry(customFeature, context);
		             subMenuVerify.add(menuEntry); 
	        	 } else {
	        		 ContextMenuEntry menuEntry = new ContextMenuEntry(customFeature, context);
		             subMenuPrint.add(menuEntry); 
	        	 }
	         }
	     }
	     IContextMenuEntry ret[] = new IContextMenuEntry[] { subMenuVerify, subMenuPrint, subMenuAdd, subMenuOpen };

	     return ret;
	}
	
	@Override
	public IPaletteCompartmentEntry[] getPalette() {
	    List<IPaletteCompartmentEntry> ret =
	        new ArrayList<IPaletteCompartmentEntry>();
	 
	    // add compartments from super class
//	    IPaletteCompartmentEntry[] superCompartments =
//	        super.getPalette();
//	    for (int i = 0; i < superCompartments.length; i++)
//	        ret.add(superCompartments[i]);
	 
	    // add new compartment at the end of the existing compartments
	    PaletteCompartmentEntry compartmentStatementEntry =
	        new PaletteCompartmentEntry("Statements", null);
	    ret.add(compartmentStatementEntry);
	    
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
	        if (cf.getCreateName().equals("Method") || cf.getCreateName().contains("Variable") 
	        		|| cf.getCreateName().contains("Condition") || cf.getCreateName().contains("Renam")) {
	        	compartmentOtherEntry.addToolEntry(objectCreationToolEntry);
	        } else {
	        	compartmentStatementEntry.addToolEntry(objectCreationToolEntry);
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
	
	@Override
	public String getToolTip(GraphicsAlgorithm ga) {
	    PictogramElement pe = ga.getPictogramElement();
	    Object bo = getFeatureProvider().getBusinessObjectForPictogramElement(pe);
	    if (bo instanceof AbstractStatement) {
	        String comment = ((AbstractStatement)bo).getComment();
	        if (comment != null && !comment.isEmpty()) {
	            return comment;        
	        }
	    } else  if (bo instanceof CbCFormula) {
	        String comment = ((CbCFormula)bo).getComment();
	        if (comment != null && !comment.isEmpty()) {
	            return comment;        
	        }
	    }
	    return (String) super.getToolTip(ga);
	}
}