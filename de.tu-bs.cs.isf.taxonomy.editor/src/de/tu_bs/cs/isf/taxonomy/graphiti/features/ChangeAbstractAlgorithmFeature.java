package de.tu_bs.cs.isf.taxonomy.graphiti.features;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

import de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm;

/**
 * Class that changes the abstract value of algorithms
 * @author Tobias
 *
 */
public class ChangeAbstractAlgorithmFeature extends AbstractCustomFeature {
	 
	/**
	 * boolean that indicates if something changes
	 */
    private boolean hasDoneChanges = false;
    
    /**
     * Constructor of the class
     * @param fp	The FeatureProvider
     */
    public ChangeAbstractAlgorithmFeature(IFeatureProvider fp) {
        super(fp);
    }
 
    @Override
    public String getName() {
        return "Set Algorithm abstract/normal";
    }
 
    @Override
    public String getDescription() {
        return "Change if the Algorithm is abstract.";
    }
 
    @Override
    public boolean canExecute(ICustomContext context) {
        boolean ret = false;
        PictogramElement[] pes = context.getPictogramElements();
        if (pes != null && pes.length == 1) {
            Object bo = getBusinessObjectForPictogramElement(pes[0]);
            if (bo instanceof Algorithm) {
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
            if (bo instanceof Algorithm) {
            	this.hasDoneChanges = true;
            	Algorithm algorithm = (Algorithm) bo;
                algorithm.setAbstract(!algorithm.isAbstract());
                updatePictogramElement(pes[0]);
            }
        }
    }
 
    @Override
    public boolean hasDoneChanges() {
           return this.hasDoneChanges;
    }
}