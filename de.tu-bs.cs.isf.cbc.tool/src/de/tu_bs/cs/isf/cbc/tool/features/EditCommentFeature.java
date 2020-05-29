package de.tu_bs.cs.isf.cbc.tool.features;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.taxonomy.graphiti.features.MyInputDialog;

/**
 * Class that allows to add and edit code of methods
 * @author Tobias
 *
 */
public class EditCommentFeature extends AbstractCustomFeature {
	
	/**
	 * boolean that indicate if something changes
	 */
    private boolean hasDoneChanges = false;
    
    /**
     * Constructor of the class
     * @param fp	The FeatureProvider
     */
    public EditCommentFeature(IFeatureProvider fp) {
        super(fp);
    }
 
    @Override
    public String getName() {
        return "Edit Comment";
    }
 
    @Override
    public String getDescription() {
        return "Edit Comment of a statement";
    }
 
    @Override
    public boolean canExecute(ICustomContext context) {
        boolean ret = false;
        PictogramElement[] pes = context.getPictogramElements();
        if (pes != null && pes.length == 1) {
            Object bo = getBusinessObjectForPictogramElement(pes[0]);
            if (bo instanceof AbstractStatement || bo instanceof CbCFormula) {
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
                String currentComment = statement.getComment();
                // ask user for a new comment
                String newComment = askString(getName(), getDescription(),
                        currentComment);
                if (newComment != null && !newComment.equals(currentComment)) {
            		this.hasDoneChanges = true;
                    statement.setComment(newComment);
                    updatePictogramElement(((Shape)pes[0]).getContainer());
                    getFeatureProvider().getDiagramTypeProvider().getDiagramBehavior().refresh();
                }
            } else if (bo instanceof CbCFormula) {
            	CbCFormula formula = (CbCFormula) bo;
                String currentComment = formula.getComment();
                // ask user for a new comment
                String newComment = askString(getName(), getDescription(),
                        currentComment);
                if (newComment != null && !newComment.equals(currentComment)) {
            		this.hasDoneChanges = true;
                    formula.setComment(newComment);
                    updatePictogramElement(((Shape)pes[0]).getContainer());
                    getFeatureProvider().getDiagramTypeProvider().getDiagramBehavior().refresh();
                    
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
     * @param initialValue	The current code of the Method
     * @return	The new code of the Method
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