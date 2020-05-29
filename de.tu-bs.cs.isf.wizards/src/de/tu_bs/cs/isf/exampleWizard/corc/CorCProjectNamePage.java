package de.tu_bs.cs.isf.exampleWizard.corc;

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
public class CorCProjectNamePage extends WizardPage implements Listener {
	
	public Text name; //path where the project should be created
	
	//Buttons and boolean (if marked or not):
	private Button linearSearchButton;
	public boolean linearSearch = false;
	private Button binarySearchButton;
	public boolean binarySearch = false;
	private Button dutchFlagButton;
	public boolean dutchFlag = false;
	private Button exponentationButton;
	public boolean exponentation = false;
	private Button factorialGraphicalButton;
	public boolean factorialGraphical = false;
	private Button logarithmButton;
	public boolean logarithm = false;
	private Button maxElementButton;
	public boolean maxElement = false;
	
	public CorCProjectNamePage() {
		super("CorC examples");
		setTitle("CorC example wizard:");
		setDescription("this wizard will create a new project with the selected name, that will contain the selected CorC examples");
	}

	@Override
	/**
	 * contains the layout and buttons where the user can control the wizard
	 */
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
		name.setText("de.tu_bs.cs.isf.corc.examples");
		
		//SEPERATOR LINE:
		Label separator = new Label(composite, SWT.HORIZONTAL | SWT.SEPARATOR);
		GridData sepData = new GridData(GridData.FILL_HORIZONTAL);
	 	sepData.horizontalSpan = columns;
	    separator.setLayoutData(sepData);
	    
	    //CHECKS TO CHOOSE THE EXAMPLES:
	    GridData exampleData = new GridData(GridData.FILL_HORIZONTAL);
	    exampleData.horizontalSpan = columns;
	    new Label (composite, SWT.NONE).setText("choose examples:");
	    //BINARY SEARCH:
	    /*
	    binarySearchButton = new Button(composite, SWT.CHECK);
	    binarySearchButton.setText("Binary Search");
	    binarySearchButton.setSelection(false);
	    binarySearchButton.setLayoutData(exampleData);
	    binarySearchButton.addListener(SWT.Selection, this);
	    */
		//LINEAR SEARCH:
		linearSearchButton = new Button(composite, SWT.CHECK);
	    linearSearchButton.setText("Linear Search");
	    linearSearchButton.setSelection(false);
	    linearSearchButton.setLayoutData(exampleData);
	    linearSearchButton.addListener(SWT.Selection, this);
	    //DUTCHFLAG
	    dutchFlagButton = new Button(composite, SWT.CHECK);
	    dutchFlagButton.setText("Dutch Flag");
	    dutchFlagButton.setSelection(false);
	    dutchFlagButton.setLayoutData(exampleData);
	    dutchFlagButton.addListener(SWT.Selection, this);
	    //EXPONENTATION
	    exponentationButton = new Button(composite, SWT.CHECK);
	    exponentationButton.setText("Exponentation");
	    exponentationButton.setSelection(false);
	    exponentationButton.setLayoutData(exampleData);
	    exponentationButton.addListener(SWT.Selection, this);
	    //FACTORIALGRAPHICAL
	    factorialGraphicalButton = new Button(composite, SWT.CHECK);
	    factorialGraphicalButton.setText("Factorial Graphical");
	    factorialGraphicalButton.setSelection(false);
	    factorialGraphicalButton.setLayoutData(exampleData);
	    factorialGraphicalButton.addListener(SWT.Selection, this);
	    //LOGARITHM
	    logarithmButton = new Button(composite, SWT.CHECK);
	    logarithmButton.setText("Logarithm");
	    logarithmButton.setSelection(false);
	    logarithmButton.setLayoutData(exampleData);
	    logarithmButton.addListener(SWT.Selection, this);
	    //MAXELEMENT
	    maxElementButton = new Button(composite, SWT.CHECK);
	    maxElementButton.setText("Max Element");
	    maxElementButton.setSelection(false);
	    maxElementButton.setLayoutData(exampleData);
	    maxElementButton.addListener(SWT.Selection, this);
		setControl(composite);
	}
	/**
	 * when a button is checked or unchecked, the boolean value will change
	 */
	public void handleEvent (Event e) {
		if (e.widget == binarySearchButton) {
			binarySearch = !binarySearch;
		}
		if (e.widget == linearSearchButton) {
			linearSearch = !linearSearch;
		}
		if (e.widget == dutchFlagButton) {
			dutchFlag = !dutchFlag;
		}
		if (e.widget == exponentationButton) {
			exponentation = !exponentation;
		}
		if (e.widget == factorialGraphicalButton) {
			factorialGraphical = !factorialGraphical;
		}
		if (e.widget == logarithmButton) {
			logarithm = !logarithm;
		}
		if (e.widget == maxElementButton) {
			maxElement = !maxElement;
		}
	}

}
