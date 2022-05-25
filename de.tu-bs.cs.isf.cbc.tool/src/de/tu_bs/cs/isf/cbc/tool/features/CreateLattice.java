package de.tu_bs.cs.isf.cbc.tool.features;

import java.util.Arrays;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.lattice.Lattice;
import de.tu_bs.cs.isf.lattice.Node;
import de.tu_bs.cs.isf.lattice.Transition;
import de.tu_bs.cs.isf.lattice.calculation.LeastUpperBound;

/**
 * Class that creates a predefined lattice
 * @author Tobias
 *
 */
public class CreateLattice extends AbstractCustomFeature {
	
	/**
	 * boolean that indicate if something changes
	 */
    private boolean hasDoneChanges = true;
    
    /**
     * Constructor of the class
     * @param fp	The FeatureProvider
     */
    public CreateLattice(IFeatureProvider fp) {
        super(fp);
    }
 
    @Override
    public String getName() {
        return "Create Lattice";
    }
 
    @Override
    public String getDescription() {
        return "Creates a default Lattice";
    }
 
    @Override
    public boolean canExecute(ICustomContext context) {
    	boolean ret = false;
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo != null && bo instanceof CbCFormula) {
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
			if (bo instanceof CbCFormula) {
				// TODO: Not needed anymore? 
		    	// LeastUpperBound.getLattice();
			}
		}
    }
 
    @Override
    public boolean hasDoneChanges() {
           return this.hasDoneChanges;
    }
}