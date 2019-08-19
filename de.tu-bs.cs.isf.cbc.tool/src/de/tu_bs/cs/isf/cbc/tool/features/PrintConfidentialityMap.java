package de.tu_bs.cs.isf.cbc.tool.features;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.util.Console;

/**
 * Class that prints the confidentiality maps
 * @author Tobias
 *
 */
public class PrintConfidentialityMap extends AbstractCustomFeature {
	
	/**
	 * boolean that indicate if something changes
	 */
    private boolean hasDoneChanges = false;
    
    /**
     * Constructor of the class
     * @param fp	The FeatureProvider
     */
    public PrintConfidentialityMap(IFeatureProvider fp) {
        super(fp);
    }
 
    @Override
    public String getName() {
        return "Print Confidentiality Maps";
    }
 
    @Override
    public String getDescription() {
        return "Prints Confidentiality Maps";
    }
 
    @Override
    public boolean canExecute(ICustomContext context) {
    	boolean ret = false;
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo != null && (bo instanceof AbstractStatement)) {
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
				Console.println(statement.getContext());
				Console.println(statement.getPreCondition().getConfToVarsMap());
				Console.println(statement.getPostCondition().getConfToVarsMap());
			}
		}
    }
 
    @Override
    public boolean hasDoneChanges() {
           return this.hasDoneChanges;
    }
}