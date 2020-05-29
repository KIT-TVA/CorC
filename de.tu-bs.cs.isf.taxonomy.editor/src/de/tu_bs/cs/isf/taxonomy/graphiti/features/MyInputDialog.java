package de.tu_bs.cs.isf.taxonomy.graphiti.features;

import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

/**
 * Own implementation of a Dialog
 * Extends InputDialog
 * @author Tobias
 *
 */
public class MyInputDialog extends InputDialog {

	/**
	 * Constructor of the dialog
	 * @param parentShell	The parent shell
	 * @param dialogTitle	The title of the dialog
	 * @param dialogMessage	A message inside the dialog
	 * @param initialValue	The initial value that can be edited
	 * @param validator		A validator
	 */
	public MyInputDialog(Shell parentShell, String dialogTitle, String dialogMessage, String initialValue,
			IInputValidator validator) {
		super(parentShell, dialogTitle, dialogMessage, initialValue, validator);
	}

	@Override
	protected int getInputTextStyle() {
		return SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL;
	}

}
