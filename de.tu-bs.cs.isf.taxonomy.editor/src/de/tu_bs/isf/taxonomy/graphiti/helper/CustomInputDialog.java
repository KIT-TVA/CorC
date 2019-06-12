package de.tu_bs.isf.taxonomy.graphiti.helper;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * custom dialog that asks user for a string input and returns it after "finished" is pressed
 * @author Domenik Eichhorn
 */
public class CustomInputDialog extends TitleAreaDialog {
	
	private Text input;
	private String finishedInput;
	
	/**
	 * @param parentShell where the dialog should be created
	 * @param message that should be displayed above the dialog area
	 * @param title of the dialog
	 */
	public CustomInputDialog(Shell parentShell, String message, String title) {
		super(parentShell);
		customCreate(message, title);
	}
	
	/**
	 * @param parentShell where the dialog should be created
	 */
	public CustomInputDialog(Shell parentShell) {
		super(parentShell);
		customCreate(null, null);
	}
	
	@Override
	public void create() {
		//outsourced in customCreate
		return;
	}
	
	/**
	 * sets message and title above the are dialog if user select one
	 * @param message
	 * @param title
	 */
	private void customCreate(String message, String title) {
		super.create();
		if (title != null) {
			setTitle(title);
		} 
		if (message != null) {
			setMessage(message);
		}
	}
	
	@Override
	protected Control createDialogArea (Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridLayout layout = new GridLayout(2, false);
		container.setLayout(layout);
        
        new Label(container, SWT.None).setText("Input: ");
        input = new Text(container, SWT.BORDER);
        
        GridData textData = new GridData();
        textData.grabExcessHorizontalSpace = true;
        textData.horizontalAlignment = GridData.FILL;
        input.setLayoutData(textData);
		return area;
	}
	
	/**
	 * sets input after the user pressed finish
	 */
	private void saveInput() {
		this.finishedInput = input.getText();
	}
	
	@Override
	protected void okPressed() {
		saveInput();
		super.okPressed();
	}
	
	/**
	 * @return input the user made after pressing finished, can be null
	 */
	public String getInput() {
		return finishedInput;
	}
}
