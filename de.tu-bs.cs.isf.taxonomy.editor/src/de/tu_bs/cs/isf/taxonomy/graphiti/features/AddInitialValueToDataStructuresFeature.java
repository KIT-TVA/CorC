package de.tu_bs.cs.isf.taxonomy.graphiti.features;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import de.tu_bs.cs.isf.taxonomy.model.taxonomy.DataStructure;

/**
 * Class that allows to add and edit initial values of data structures
 * @author Tobias
 *
 */
public class AddInitialValueToDataStructuresFeature extends AbstractCustomFeature {
	
	/**
	 * boolean that indicate if something changes
	 */
    private boolean hasDoneChanges = false;
    
    /**
     * Constructor of the class
     * @param fp	The FeatureProvider
     */
    public AddInitialValueToDataStructuresFeature(IFeatureProvider fp) {
        super(fp);
    }
 
    @Override
    public String getName() {
        return "Edit initial value of DataStructure";
    }
 
    @Override
    public String getDescription() {
        return "Edit initial value of DataStructure";
    }
 
    @Override
    public boolean canExecute(ICustomContext context) {
        boolean ret = false;
        PictogramElement[] pes = context.getPictogramElements();
        if (pes != null && pes.length == 1) {
            Object bo = getBusinessObjectForPictogramElement(pes[0]);
            if (bo instanceof DataStructure) {
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
            if (bo instanceof DataStructure) {
            	DataStructure data = (DataStructure) bo;
                String currentValue = data.getInitialValue();
                // ask user for a new class name
                String newValue = askString(getName(), getDescription(),
                		currentValue);
                if (newValue != null && !newValue.equals(currentValue)) {
                    this.hasDoneChanges = true;
                    data.setInitialValue(newValue);
                    updatePictogramElement(pes[0]);
                }
            }
        }
    }
 
    @Override
    public boolean hasDoneChanges() {
           return this.hasDoneChanges;
    }
    
    /**
     * Helper method that asks for a String as input
     * @param dialogTitle	The title of the dialog
     * @param dialogMessage	A message inside the dialog
     * @param initialValue	The value code of the DataStructure
     * @return	The new value of the DataStructure
     */
    public static String askString(String dialogTitle, String dialogMessage, String initialValue) {
		String ret = null;
		Shell shell = getShell();
		if (initialValue == null) {
			initialValue = "\n";
		}
		MyInputDialog inputDialog = new MyInputDialog(shell, dialogTitle, dialogMessage, initialValue, null);
		int retDialog = inputDialog.open();
		if (retDialog == Window.OK) {
			ret = inputDialog.getValue();
		}
		return ret;
	}
    
    /**
	 * Returns the currently active Shell.
	 * 
	 * @return The currently active Shell.
	 */
	private static Shell getShell() {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
	}
}