package de.tu_bs.cs.isf.cbcclass.tool.features;

import org.eclipse.graphiti.features.IDirectEditingInfo;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method;
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodClass;

public class RenameMethodClassFeature extends AbstractCustomFeature{

	/**
	 * boolean that indicate if something changes
	 */
    private boolean hasDoneChanges = false;
    
    /**
     * Constructor of the class
     * @param fp	The FeatureProvider
     */
	public RenameMethodClassFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
    public String getName() {
        return "Edit Name of a Method Class";
    }
 
    @Override
    public String getDescription() {
        return "Edit Name of a Method Class";
    }
    
    @Override
    public boolean canExecute(ICustomContext context) {
        boolean ret = false;
        PictogramElement[] pes = context.getPictogramElements();
        if (pes != null && pes.length == 1) {
            Object bo = getBusinessObjectForPictogramElement(pes[0]);
            if (bo instanceof /*MethodClass */ Method) {
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
            if (/*bo instanceof MethodClass || */bo instanceof Method) {
            	IDirectEditingInfo directEditingInfo = getFeatureProvider().getDirectEditingInfo();
    			directEditingInfo.setMainPictogramElement(pes[0]);
    			directEditingInfo.setPictogramElement(pes[0]);
    			directEditingInfo.setGraphicsAlgorithm(pes[0].getGraphicsAlgorithm());
    			directEditingInfo.setActive(true);
            	getFeatureProvider().getDiagramTypeProvider().getDiagramBehavior().refresh();
            }
        }	
	}
	
	@Override
    public boolean hasDoneChanges() {
        return this.hasDoneChanges;
    }
}
