package de.tu_bs.cs.isf.taxonomy.exampleWizard;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

/**
 * first page from the wizard
 * lets the user choose a name for the path where a new project will be created
 * in this project the examples that where selected will be pasted
 */
public class TaxProjectNamePage extends WizardPage implements Listener {
	
	public Text name; //name that the project should get
	
	//examples and the boolean if they should be created or not
	private Button MadfaButton;
	public boolean madfa;
	private Button twoDMadfaButton;
	public boolean twoDMadfa;
	private Button DFAMinButton;
	public boolean DFAMin;
	private Button samplingButton;
	public boolean sampling;
	
	public TaxProjectNamePage() {
		super("taxonomy examples");
		setTitle("taxonomy example wizard");
		setDescription("this wizard will create a new project with the selected name, that will contain the selected taxonomy examples");
	}

	@Override
	public void createControl(Composite parent) {
		//composite with the widgets:
		Composite composite = new Composite(parent, SWT.NONE);
		//page layout: (swt gridlayout, not awt)
		GridLayout layout = new GridLayout();
		int columns = 2;
		layout.numColumns = columns;
		composite.setLayout(layout);
		//functions/widgets:
		
		//LINE FOR PROJECT PATH: (LABEL AND TEXT)
		new Label (composite, SWT.NONE).setText("choose project name:");
		name = new Text (composite, SWT.BORDER | SWT.SINGLE);	
		GridData pathData = new GridData();
		pathData.horizontalAlignment = GridData.FILL;
		pathData.grabExcessHorizontalSpace = true;
		name.setLayoutData(pathData); //lets text fill the whole line
		name.setText("de.tu_bs.cs.isf.taxonomy.examples");
				
		//SEPERATOR LINE:
		Label separator = new Label(composite, SWT.HORIZONTAL | SWT.SEPARATOR);
		GridData sepData = new GridData(GridData.FILL_HORIZONTAL);
		sepData.horizontalSpan = columns;
		separator.setLayoutData(sepData);
			    
	    //CHECKS TO CHOOSE THE EXAMPLES:
		GridData exampleData = new GridData(GridData.FILL_HORIZONTAL);
		exampleData.horizontalSpan = columns;
		new Label (composite, SWT.NONE).setText("choose examples:");
		//MADFA:
		MadfaButton = new Button(composite, SWT.CHECK);
		MadfaButton.setText("Madfa");
		MadfaButton.setSelection(false);
		MadfaButton.setLayoutData(exampleData);
		MadfaButton.addListener(SWT.Selection, this);	
		//2dMadfa
		twoDMadfaButton = new Button(composite, SWT.CHECK);
		twoDMadfaButton.setText("2dMadfa");
		twoDMadfaButton.setSelection(false);
		twoDMadfaButton.setLayoutData(exampleData);
		twoDMadfaButton.addListener(SWT.Selection, this);	
		//DFAMin
		DFAMinButton = new Button(composite, SWT.CHECK);
		DFAMinButton.setText("DFAMin");
		DFAMinButton.setSelection(false);
		DFAMinButton.setLayoutData(exampleData);
		DFAMinButton.addListener(SWT.Selection, this);
		//Sampling
		samplingButton = new Button(composite, SWT.CHECK);
		samplingButton.setText("Sampling");
		samplingButton.setSelection(false);
		samplingButton.setLayoutData(exampleData);
		samplingButton.addListener(SWT.Selection, this);
		
		setControl(composite);
		}
		/**
		* when a button is checked or unchecked, the boolean value will change
		*/
		public void handleEvent (Event e) {
			if (e.widget == MadfaButton) {
				madfa = !madfa;
			}
			if (e.widget == twoDMadfaButton) {
				twoDMadfa = !twoDMadfa;
			}
			if (e.widget == DFAMinButton) {
				DFAMin = !DFAMin;
			}
			if (e.widget == samplingButton) {
				sampling = !sampling;
			}
		}

}
