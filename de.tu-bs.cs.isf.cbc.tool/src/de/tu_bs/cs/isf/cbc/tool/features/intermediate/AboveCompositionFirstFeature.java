package de.tu_bs.cs.isf.cbc.tool.features.intermediate;

import org.eclipse.graphiti.features.ICreateConnectionFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.IReconnectionFeature;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.context.impl.CreateConnectionContext;
import org.eclipse.graphiti.features.context.impl.CreateContext;
import org.eclipse.graphiti.features.context.impl.ReconnectionContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.tool.helper.UpdateConditionsOfChildren;

/**
 * Class that to create an intermediate composition statement
 * @author Tobias
 *
 */
public class AboveCompositionFirstFeature extends AbstractCustomFeature {
    
    /**
     * Constructor of the class
     * @param fp	The FeatureProvider
     */
    public AboveCompositionFirstFeature(IFeatureProvider fp) {
        super(fp);
    }
    
    @Override
    public String getName() {
        return "Composition statement above (first)";
    }
 
    @Override
    public String getDescription() {
        return "Generates a composition statement above this statement (connected with the first statement).";
    }
 
	@Override
	public boolean canExecute(ICustomContext context) {
		boolean ret = false;
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof AbstractStatement && ((AbstractStatement) bo).getParent() != null) {
				ret = true;
			}
		}
		return ret;
	}
 
    @Override
    public void execute(ICustomContext context) {
        PictogramElement[] pes = context.getPictogramElements();
        if (pes != null && pes.length == 1) {
            Object bo = getBusinessObjectForPictogramElement(pes[0]);
            if (bo instanceof AbstractStatement) {
            	AbstractStatement statement = (AbstractStatement) bo;
            	AbstractStatement parentStatement = statement.getParent();
            	CreateContext createContext = new CreateContext();
            	createContext.setTargetContainer(getDiagram());
            	ICreateFeature createFeature = getCreateFeature(getFeatureProvider().getCreateFeatures());
            	Object[] newParents = createFeature.create(createContext);
            	CompositionStatement newParent = (CompositionStatement) newParents[0];
            	Shape peStatement = (Shape) getFeatureProvider().getPictogramElementForBusinessObject(statement);
            	Anchor anchorStatement = peStatement.getAnchors().get(0);
            	
            	Shape peOldParentStatement = (Shape) getFeatureProvider().getPictogramElementForBusinessObject(parentStatement);
            	Anchor anchorOldParent = peOldParentStatement.getAnchors().get(0);
            	
            	Shape peNewParentStatement = (Shape) getFeatureProvider().getPictogramElementForBusinessObject(newParent.getFirstStatement());
            	Anchor anchorNewParent = peNewParentStatement.getAnchors().get(0);
            	
            	ReconnectionContext reconnectionContext = new ReconnectionContext(anchorStatement.getIncomingConnections().get(0), anchorOldParent, anchorNewParent, null);
            	reconnectionContext.setReconnectType(ReconnectionContext.RECONNECT_SOURCE);
            	IReconnectionFeature reconnectionFeature = getFeatureProvider().getReconnectionFeature(reconnectionContext);
            	reconnectionFeature.reconnect(reconnectionContext);
            	
            	Shape peNewParentTopStatement = (Shape) getFeatureProvider().getPictogramElementForBusinessObject(newParent);
            	Anchor anchorNewParentTop = peNewParentTopStatement.getAnchors().get(0);
            	
            	CreateConnectionContext connectionContext = new CreateConnectionContext();
            	connectionContext.setSourceAnchor(anchorOldParent);
            	connectionContext.setTargetAnchor(anchorNewParentTop);
            	connectionContext.setSourcePictogramElement(peOldParentStatement);
            	connectionContext.setTargetPictogramElement(peNewParentTopStatement);
            	ICreateConnectionFeature connectionFeature = getFeatureProvider().getCreateConnectionFeatures()[0];
            	connectionFeature.create(connectionContext);
            	
                UpdateConditionsOfChildren.updateRefinedStatement(newParent.getParent(), newParent);
                updatePictogramElement(pes[0]);
            }
        }
    }
    
    private ICreateFeature getCreateFeature(ICreateFeature[] features) {
    	for (ICreateFeature feature : features) {
    		if (feature.getCreateName().equals("CompositionStatement")) {
    			return feature;
    		}
    	}
		return null;
    	
    }
}