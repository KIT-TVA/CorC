package de.tu_bs.cs.isf.cbc.tool.features.intermediate;

import org.eclipse.graphiti.features.ICreateConnectionFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.context.impl.CreateConnectionContext;
import org.eclipse.graphiti.features.context.impl.CreateContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.AbstractStatementImpl;
import de.tu_bs.cs.isf.cbc.tool.helper.UpdateConditionsOfChildren;

/**
 * Class that to create an intermediate composition statement
 * @author Tobias
 *
 */
public class BelowImplementationFeature extends AbstractCustomFeature {
    
    /**
     * Constructor of the class
     * @param fp	The FeatureProvider
     */
    public BelowImplementationFeature(IFeatureProvider fp) {
        super(fp);
    }
    
    @Override
    public String getName() {
        return "Implementation below";
    }
 
    @Override
    public String getDescription() {
        return "Generates a statement below this statement.";
    }
 
	@Override
	public boolean canExecute(ICustomContext context) {
		boolean ret = false;
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo.getClass().equals(AbstractStatementImpl.class) && ((AbstractStatement) bo).getRefinement() == null
					&& ((AbstractStatement) bo).eContainer() != null
					&& !((AbstractStatement) bo).eContainer().getClass().equals(AbstractStatementImpl.class)) {
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
            	Shape peStatement = (Shape) getFeatureProvider().getPictogramElementForBusinessObject(statement);
            	
            	CreateContext createContext = new CreateContext();
            	createContext.setX(peStatement.getContainer().getGraphicsAlgorithm().getX());
            	createContext.setY(peStatement.getContainer().getGraphicsAlgorithm().getY() + 400);
            	createContext.setTargetContainer(getDiagram());
            	ICreateFeature createFeature = getCreateFeature(getFeatureProvider().getCreateFeatures());
            	Object[] newChildren = createFeature.create(createContext);
            	AbstractStatement newChild = (AbstractStatement) newChildren[0];
            	
            	Anchor anchorStatement = peStatement.getAnchors().get(0);
            	
            	Shape peChildStatement = (Shape) getFeatureProvider().getPictogramElementForBusinessObject(newChild);
            	Anchor anchorChild = peChildStatement.getAnchors().get(0);
            	
            	CreateConnectionContext connectionContext = new CreateConnectionContext();
            	connectionContext.setSourceAnchor(anchorStatement);
            	connectionContext.setTargetAnchor(anchorChild);
            	connectionContext.setSourcePictogramElement(peStatement);
            	connectionContext.setTargetPictogramElement(peChildStatement);
            	ICreateConnectionFeature connectionFeature = getFeatureProvider().getCreateConnectionFeatures()[0];
            	connectionFeature.create(connectionContext);
            	
                UpdateConditionsOfChildren.updateRefinedStatement(statement, newChild);
                updatePictogramElement(pes[0]);
            }
        }
    }
    
    private ICreateFeature getCreateFeature(ICreateFeature[] features) {
    	for (ICreateFeature feature : features) {
    		if (feature.getCreateName().equals("Statement")) {
    			return feature;
    		}
    	}
		return null;
    	
    }
}