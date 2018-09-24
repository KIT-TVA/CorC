package de.tu_bs.cs.isf.taxonomy.graphiti.features;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.Method;
import de.tu_bs.cs.isf.toolkit.support.compare.CompareMethodBodies;
import de.tu_bs.cs.isf.toolkit.support.output.Console;

/**
 * Class that allows to add code to methods
 * @author Tobias
 *
 */
public class EditPreConditionFeature extends AbstractCustomFeature {
	 
	/**
	 * boolean that indicate if something changes
	 */
    private boolean hasDoneChanges = false;
    
    /**
     * Constructor of the class
     * @param fp	The FeatureProvider
     */
    public EditPreConditionFeature(IFeatureProvider fp) {
        super(fp);
    }
 
    @Override
    public String getName() {
        return "Edit Pre-Condition of Algorithm or Method";
    }
 
    @Override
    public String getDescription() {
        return "Edit Pre-Condition of Algorithm or Method";
    }
 
    @Override
    public boolean canExecute(ICustomContext context) {
        boolean ret = false;
        PictogramElement[] pes = context.getPictogramElements();
        if (pes != null && pes.length == 1) {
            Object bo = getBusinessObjectForPictogramElement(pes[0]);
            if (bo instanceof Algorithm || bo instanceof Method) {
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
            	Algorithm algorithm = (Algorithm) bo;
                String currentPre = algorithm.getPreCondition();
                
                String newPre = askString(getName(), getDescription(),
                        currentPre);
                if (newPre != null && !newPre.equals(currentPre)) {
                	if (newPre != null && !newPre.equals(currentPre)) {
                    	if (CompareMethodBodies.readAndTestAssertWithJaMoPP(newPre.replaceAll("->", "&"))) {
                    		this.hasDoneChanges = true;
                            algorithm.setPreCondition(newPre);
                            updatePictogramElement(pes[0]);
                    	} else {
                    		this.hasDoneChanges = false;
                			Console.println("Precondition is not in correct format: " + newPre );
                    	}
                    }
                }
            } else if (bo instanceof Method) {
            	Method method = (Method) bo;
                String currentPre = method.getPreCondition();
                
                String newPre = askString(getName(), getDescription(),
                        currentPre);
                if (newPre != null && !newPre.equals(currentPre)) {
                	if (CompareMethodBodies.readAndTestAssertWithJaMoPP(newPre)) {
                		this.hasDoneChanges = true;
                        method.setPreCondition(newPre);
                        updatePictogramElement(pes[0]);
                	} else {
                		this.hasDoneChanges = false;
            			Console.println("Precondition is not in correct format: " + newPre );
                	}
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
     * @param initialValue	The current pre condition
     * @return	The new pre condition
     */
    public static String askString(String dialogTitle, String dialogMessage, String initialValue) {
		String ret = null;
		Shell shell = getShell();
		if (initialValue == null) {
			initialValue = "\n\n\n\n";
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