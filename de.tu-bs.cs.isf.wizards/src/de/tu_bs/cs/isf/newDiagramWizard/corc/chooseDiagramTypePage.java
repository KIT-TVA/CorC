package de.tu_bs.cs.isf.newDiagramWizard.corc;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class chooseDiagramTypePage extends WizardPage {
	
	public boolean corctextual = false;
	public boolean corcdiagram = true;
	private String browsedPath = "";
	public Text path;
	public Text name;
	
	protected chooseDiagramTypePage() {
		super("new CorC file");
		setTitle("CorC file wizard");
		setDescription("with this wizard you can create a new CorC file");
	}

	@Override
	public void createControl(Composite parent) {
		setPageComplete(false);
		Composite composite = new Composite(parent, SWT.None);
		//page Layout:
		GridLayout layout = new GridLayout();
		int columns = 3;
		layout.numColumns = columns;
		composite.setLayout(layout);
		
		//grid layout initialize:
		new Label (composite, SWT.None).setText("file type:");
		Combo combo = new Combo(composite, SWT.DROP_DOWN | SWT.READ_ONLY); //choose diagram type
		new Label (composite, SWT.None).setText("diagram name:"); 
		name = new Text(composite, SWT.BORDER | SWT.SINGLE); //the name of the diagram
		new Label (composite, SWT.None).setText("selected path:"); 
		path = new Text (composite, SWT.BORDER | SWT.SINGLE ); //the path
		Button browseButton = new Button(composite, SWT.PUSH); //button to use browse function
		Label separator = new Label(composite, SWT.HORIZONTAL | SWT.SEPARATOR); //line
		
		Label error = new Label (composite, SWT.None); //error display if needed
		GridData errorData = new GridData(GridData.FILL_HORIZONTAL);
		errorData.horizontalSpan = columns;
		error.setLayoutData(errorData);
		Color redColor = new Color(null, 200,0,0);
		error.setForeground(redColor);
		
		//line that lets you choose the layout type:	
		String[] diagramTypes = new String[] {"CorC diagram", "CorC textual"};
		combo.setItems(diagramTypes);
		combo.setText("CorC diagram"); //standard value, if nothing was selected yet
		//layout for the combo:
		GridData comboData = new GridData();
		comboData.horizontalAlignment = GridData.FILL;
		comboData.grabExcessHorizontalSpace = true;
		comboData.horizontalSpan = 2;
		combo.setLayoutData(comboData);
		//listener that sets boolean to check which type is selected
		combo.addSelectionListener(new SelectionAdapter() {
			 
            @Override
            public void widgetSelected(SelectionEvent e) {
                int idx = combo.getSelectionIndex();
                String diagramType = combo.getItem(idx);
                if (diagramType.equals("CorC diagram")) {
                	corcdiagram = true;
                	corctextual = false;
                } else if (diagramType.equals("CorC textual")) {
                	corcdiagram = false;
                	corctextual = true;
                }
            }
        });
		
		//line that lets you choose the diagram name:
		GridData nameData = new GridData();
		nameData.horizontalAlignment = GridData.FILL;
		nameData.grabExcessHorizontalSpace = true;
		nameData.horizontalSpan = 2;
		name.setLayoutData(nameData); //lets text fill the whole line
		name.setText("newDiagram");
		
		//line that lets you choose the creation path:
		GridData pathData = new GridData();
		pathData.horizontalAlignment = GridData.FILL;
		pathData.grabExcessHorizontalSpace = true;
		path.setLayoutData(pathData); //lets text fill the whole line
		
		//browse button/function in the creation path:
		browseButton.setText("  Browse...  ");
		browseButton.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				setPageComplete(false);
				DirectoryDialog dialog = new DirectoryDialog(new Shell(), SWT.OPEN);
				dialog.setFilterPath(ResourcesPlugin.getWorkspace().getRoot().getLocation().toString());
				dialog.setMessage("choose a Folder in your Eclipse Workspace");
				browsedPath = dialog.open();
				path.setText(browsedPath);
				//check if browsedPath is in the eclipse workspace (have to cut / and \, because format differs)
				String temp = ResourcesPlugin.getWorkspace().getRoot().getLocation().toString().replace("/", "");
				if (browsedPath.replace("\\", "").contains(temp)) {
					if (browsedPath.replace("\\", "").equals(temp)) {
						error.setText("Error: [workspace root] you can't select the workspace root as your destination");
					}
					setPageComplete(true);
				}  else {
					error.setText("Error: [not in workspace] you have to choose a folder in your eclipse workspace");
				}
				
//				JFileChooser chooseFolder = new JFileChooser();
//				chooseFolder.setCurrentDirectory(new java.io.File("."));
//				chooseFolder.setDialogTitle("choose Folder");
//				chooseFolder.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				//gets stuck at this point - dont know why tho...
//				if (chooseFolder.showDialog(null, "Attach") == JFileChooser.APPROVE_OPTION) {
//					System.out.println(chooseFolder.getCurrentDirectory());
//				}
				
			}			
		});
			
		//the current selection (selected by the user)
		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
		IWorkbenchPage page = win.getActivePage();
		ISelection selected = page.getSelection();
		
		//cutting the toString from selection so it can be used as a path:
		String selection = selected.toString();
		selection = selection.substring(2, selection.length() - 1); //cuts first two and the last character [X, ]
		String currentPath = ResourcesPlugin.getWorkspace().getRoot().getLocation().toString();
		if (!selection.equals("mpty selection")) {
			currentPath = ResourcesPlugin.getWorkspace().getRoot().getLocation().toString() + selection;
			setPageComplete(true);
		} else {
			error.setText("Error: [workspace root] you can't select the workspace root as your destination");
		}
	    path.setText(currentPath);
	    	
		//SEPERATOR LINE:
		GridData sepData = new GridData(GridData.FILL_HORIZONTAL);
		sepData.horizontalSpan = columns;
		separator.setLayoutData(sepData);
		
		setControl(composite);
	}
	
}
