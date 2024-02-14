package de.tu_bs.cs.isf.corc.predicateWizard;

import de.tu_bs.cs.isf.cbc.tool.helper.Predicate;
import de.tu_bs.cs.isf.cbc.tool.helper.PredicateDefinition;

import javax.swing.JOptionPane;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

public class PredicateManagementTypePage extends WizardPage {
	private ArrayList<Predicate> predicates;
	
	private static final String PROJECT_TYPE_NOTOO = "non object-oriented project"; // project/src/.diagram
	private static final String PROJECT_TYPE_OO = "object-oriented project"; // project/src/class/.diagram
	private static final String PROJECT_TYPE_SPL = "object-oriented spl"; // project/features/feature/class/.diagram
	
	private String projectType = "";
	private IResource resource;
	private String projectName;
	private Predicate currentPredicate = null;
	
	private static List predicatesList;
	
	private TabFolder tab_folder;	
	private Group groupTabs;	
	private Button buttonAddPredicate;
	private Button buttonDelPredicate;	
	private Label label_blank_1;	
	
	protected PredicateManagementTypePage(IResource resource) {
		super("manage Predicates");		
		setTitle("Predicate Management");
		this.resource = resource;
		
		URI uri = resource.getLocationURI();
		String[] parts = uri.toString().split("/");
		if (parts[parts.length-2].equals("src")) projectType = PROJECT_TYPE_NOTOO;
		if (parts[parts.length-3].equals("src")) projectType = PROJECT_TYPE_OO;
		if (parts[parts.length-4].equals("features")) projectType = PROJECT_TYPE_SPL;
		projectName = projectType.equals(PROJECT_TYPE_NOTOO) ? parts[parts.length-3] : (projectType.equals(PROJECT_TYPE_OO) ? parts[parts.length-4] : parts[parts.length-5]);
		setDescription("Manage the predicates of the project " + projectName + ".");
		projectType.equals(PROJECT_TYPE_SPL);		
		predicates = loadPredicates();
	}
	
	@Override
	public void createControl(Composite parent) {
		setPageComplete(false);
		Composite composite = new Composite(parent, SWT.None);	
		
		// START LAYOUT--------------------------------------------------------------------------------
		
		// PAGE
		GridLayout gridLayout_page = new GridLayout();
		gridLayout_page.numColumns = 2;
		composite.setLayout(gridLayout_page);
		GridData gridData_page = new GridData();
		gridData_page.widthHint = 3000;
		composite.setLayoutData(gridData_page);
		
		// GROUP predicates list
		Group groupList = new Group(composite, SWT.NULL);
		groupList.setText("Available Predicates");
	    GridLayout layout_group_list_predicates = new GridLayout();
	    layout_group_list_predicates.numColumns = 3;
	    groupList.setLayout(layout_group_list_predicates);
	    GridData gridData_group_list_predicates = new GridData();
	    gridData_group_list_predicates.horizontalAlignment = GridData.FILL;
	    gridData_group_list_predicates.verticalAlignment = GridData.FILL;
	    groupList.setLayoutData(gridData_group_list_predicates);
		
		// LABEL blank
	    label_blank_1 = new Label(groupList, SWT.None);
	    label_blank_1.setText("Available Predicates:");
	    label_blank_1.setVisible(false);
		
		// BUTTON add predicate
		GridData gridData_button_addPredicate = new GridData();
		Image image_button_addPredicate = new Image(null, "C:\\Users\\mko\\Documents\\ISF\\0_feat-CorC2.0variableSpecifications\\CorC\\de.tu-bs.cs.isf.wizards\\icons\\add.gif");
		buttonAddPredicate = new Button(groupList, SWT.PUSH);
		buttonAddPredicate.setImage(image_button_addPredicate);
		buttonAddPredicate.setLayoutData(gridData_button_addPredicate);
		
		// BUTTON del predicate
		GridData gridData_button_delPredicate = new GridData();
		gridData_button_delPredicate.horizontalSpan = 1;
		gridData_button_delPredicate.horizontalAlignment = GridData.FILL_HORIZONTAL;
		Image image_button_delPredicate = new Image(null, "C:\\Users\\mko\\Documents\\ISF\\0_feat-CorC2.0variableSpecifications\\CorC\\de.tu-bs.cs.isf.wizards\\icons\\remove.gif");
		buttonDelPredicate = new Button(groupList, SWT.PUSH);
		buttonDelPredicate.setImage(image_button_delPredicate);
		buttonDelPredicate.setLayoutData(gridData_button_delPredicate);
		
		// LIST predicates		
		GridData gridData_list_predicates = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_FILL);
		gridData_list_predicates.horizontalSpan = 3;
		gridData_list_predicates.horizontalAlignment = GridData.FILL;
		predicatesList = new List(groupList, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		updateList();
		gridData_list_predicates.heightHint = predicatesList.computeTrim(0, 0, 0, predicatesList.getItemHeight() * 18).height;
		predicatesList.setLayoutData(gridData_list_predicates);
		
	    // GROUP tabs
	    groupTabs = new Group(composite, SWT.NULL);
		groupTabs.setText("Predicate properties");
	    GridLayout layout_group_tabs = new GridLayout();
	    groupTabs.setLayout(layout_group_tabs);
	    GridData gridData_group_tabs = new GridData();
	    gridData_group_tabs.horizontalAlignment = GridData.FILL;
	    gridData_group_tabs.verticalAlignment = GridData.FILL;
	    gridData_group_tabs.grabExcessHorizontalSpace = true;
	    gridData_group_tabs.widthHint = 650;
	    groupTabs.setLayoutData(gridData_group_tabs);
	    
	    // TAB FOLDER tabs
	    tab_folder = new TabFolder(groupTabs, SWT.NONE);
	    GridData gridData_tab_folder = new GridData();
	    gridData_tab_folder.horizontalAlignment = GridData.FILL;
	    gridData_tab_folder.verticalAlignment = GridData.FILL;
	    gridData_tab_folder.grabExcessHorizontalSpace = true;
	    gridData_tab_folder.grabExcessVerticalSpace = true;
	    tab_folder.setLayoutData(gridData_tab_folder);
	    
	    // END LAYOUT----------------------------------------------------------------------------------
		
	    // LISTENER predicates list
	    predicatesList.addListener(SWT.Selection, new Listener() {
	        public void handleEvent(Event e) {
	        	resetTabFolder();
	        	loadTabFolder();
	        }
	    });
	    
	    // LISTENER button add predicate
	    buttonAddPredicate.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				for (Predicate p : predicates) {
					if (p.signature.equals("newPredicate(int[] array, int number, String word)")) {
						JOptionPane.showMessageDialog(null, "There is already a new predicate. Please change signature of predicate newPredicate to be able to create a new predicate.", "Add new predicate", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				}
				int input = JOptionPane.showConfirmDialog(null, "Unsaved changes will be disposed. Continue?", "Unsaved changes", JOptionPane.YES_NO_OPTION);
				if (input == JOptionPane.NO_OPTION) {
					return;
				}
				Predicate newPredicate = new Predicate("newPredicate(int[] array, int number, String word)");
				PredicateDefinition pDef = new PredicateDefinition("true", "default:default:default:default");
				newPredicate.definitions.add(pDef);
				predicates.add(newPredicate);
				updateList();
				predicatesList.setSelection(predicatesList.getItemCount() - 1);
				resetTabFolder();
				loadTabFolder();
			}
		});
	    
	    // LISTENER button del predicate
	    buttonDelPredicate.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				for (Predicate p : predicates) {
					if (p.getSignature(false).equals(predicatesList.getSelection()[0])) {
						int input = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the predicate " + p.getSignature(true) + " ?", "Delete", JOptionPane.YES_NO_OPTION);
						if (input == JOptionPane.YES_OPTION) {
							predicates.remove(p);
							updateList();
							resetTabFolder();
						}
						return;
					}
				}
			}
		});
	    
	    setPageComplete(true);
	    setControl(composite);	    
	}
	
	private void resetTabFolder() {
		for (TabItem tab: tab_folder.getItems()) {
			tab.dispose();
		}
	}
	
	private void loadTabFolder() {
		for (Predicate p : predicates) {
    		if ((p.getSignature(false).equals(predicatesList.getSelection()[0]))) {
    			currentPredicate = p;
    			boolean firstTab = true;
    			for (PredicateDefinition pDef : p.definitions) {
    				createTab(p.getSignature(true), pDef, firstTab, false);
    				firstTab = false;
    			}
    		}
    	}
	}
	
	private void createTab(String signature, PredicateDefinition pDef, boolean  firstTab, boolean extraTab) {
		TabItem tab = new TabItem(tab_folder, SWT.NONE);
		tab.setText(signature.substring(0, signature.indexOf("(")));
		 
	    // GROUP predicate information
	    Group groupInfo = new Group(tab_folder, SWT.NULL);
		groupInfo.setText("Predicate properties");
	    GridLayout layout_group_predicate_info = new GridLayout();
	    layout_group_predicate_info.numColumns = 3;
	    groupInfo.setLayout(layout_group_predicate_info);
	    GridData gridData_group_predicate_info = new GridData();
	    gridData_group_predicate_info.horizontalAlignment = GridData.FILL;
	    gridData_group_predicate_info.verticalAlignment = GridData.FILL;
	    gridData_group_predicate_info.grabExcessHorizontalSpace = true;
	    groupInfo.setLayoutData(gridData_group_predicate_info);
	    
	    // LABEL + INPUT signature
	    GridData gridData_label_signature = new GridData();
	    gridData_label_signature.grabExcessHorizontalSpace = true;
	    gridData_label_signature.horizontalAlignment = GridData.FILL;
	    gridData_label_signature.horizontalSpan = 2;
	    new Label(groupInfo, SWT.NULL).setText("Signature:");
	    Text signatureField = new Text(groupInfo, SWT.SINGLE | SWT.BORDER);
	    signatureField.setLayoutData(gridData_label_signature);
	    signatureField.setEnabled(true);
	    signatureField.setEditable(firstTab);
	    signatureField.setText(signature);
	    signatureField.setToolTipText("Provide signature for predicate" + (firstTab ? "" : ". Changes can be done in first tab."));
	    
	    // LABEL + INPUT definition
	    GridData gridData_label_definition = new GridData(GridData.FILL_BOTH);
	    gridData_label_definition.grabExcessHorizontalSpace = true;
	    gridData_label_definition.horizontalSpan = 2;
	    new Label(groupInfo, SWT.NULL).setText("Definition:");
	    Text definitionField = new Text(groupInfo, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
	    definitionField.setLayoutData(gridData_label_definition);
	    definitionField.setEnabled(true);
	    definitionField.setText(pDef != null ? pDef.replace : "");
	    
	    // COMPOSITE error/restore/save
		Composite compositeERS = new Composite(groupInfo, SWT.NONE);
	    GridLayout gridLayoutERS = new GridLayout();
	    gridLayoutERS.numColumns = 2;
	    compositeERS.setLayout(gridLayoutERS);
	    GridData gridData_composite_ers = new GridData();
	    gridData_composite_ers.horizontalSpan = 3;
	    compositeERS.setLayoutData(gridData_composite_ers);
	 	
		// BUTTON restore
		GridData gridData_button_restore = new GridData();
		gridData_button_restore.widthHint = 150;
		Button buttonRestore = new Button(compositeERS, SWT.PUSH);
		buttonRestore.setText("Restore Definition");
		buttonRestore.setLayoutData(gridData_button_restore);
		
		// BUTTON save
		GridData gridData_button_save = new GridData();
		gridData_button_save.widthHint = 150;
		Button buttonSave = new Button(compositeERS, SWT.PUSH);
		buttonSave.setText("Save Definition");
		buttonSave.setLayoutData(gridData_button_save);	
		
		// COMPOSITE error
		Composite compositeError = new Composite(compositeERS, SWT.NONE);
		GridLayout gridLayoutError = new GridLayout();
		gridLayoutError.numColumns = 1;
		compositeError.setLayout(gridLayoutError);
		GridData gridData_composite_error = new GridData();
		gridData_composite_error.horizontalSpan = 2;
		compositeError.setLayoutData(gridData_composite_error);
			    
		// LABEL error
		GridData gridData_label_error = new GridData();
		gridData_label_error.grabExcessHorizontalSpace = true;
		gridData_label_error.verticalSpan = 1;
		Label label_error = new Label(compositeError, SWT.None);
		label_error.setText("                                                                                                                                                      ");
		label_error.setVisible(true);
		label_error.setLayoutData(gridData_label_error);
	    
	    // LISTENER button restore
	    buttonRestore.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				signatureField.setText(signature);
				definitionField.setText(pDef.replace);
			}
		});
	    
	    // LISTENER button save
	    buttonSave.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				int tabIndex = tab_folder.getSelectionIndex();
				PredicateDefinition currentPDef;
				if (tabIndex == currentPredicate.definitions.size()) {
					currentPDef = new PredicateDefinition("true", "default:default:default:default");
				} else {
					currentPDef = currentPredicate.definitions.get(tabIndex);
				}
				
				String errorSignature = tabIndex == 0 ? currentPDef.checkValidSignature(signatureField.getText().trim()) : "";
				String errorReplace = currentPDef.checkValidReplace(definitionField.getText().trim());
				
				if (!errorSignature.equals("")) {
					displayError(label_error, errorSignature, false);
					return;
				}
				
				for (Predicate p : predicates) {
					if (p.removeNamesFromSignature(signatureField.getText().trim()).equals(p.getSignature(false)) && !p.equals(currentPredicate)) {
						displayError(label_error, "Error: This signature is already in use. Didn't save changes.", false);
						return;
					}
				}
				
				if (!errorReplace.equals("")) {
					displayError(label_error, errorReplace, false);
					return;
				}
				
				String replaceError = currentPDef.setReplace(definitionField.getText().trim()); 
				if (!replaceError.equals("")) {
					displayError(label_error, replaceError, false);
					return;
				}
				
				if (tabIndex == 0) {
					currentPredicate.signature = signatureField.getText().trim();
					currentPredicate.name = signatureField.getText().trim().substring(0, signature.indexOf("("));
					currentPredicate.resolveVars();
				}
				currentPDef.name = "default"; 
				
				//tab_folder.getItem(tabIndex).setText(currentPDef.name);
				int predIndex = predicatesList.getSelectionIndex();
				updateList();
				predicatesList.setSelection(predIndex);
				displayError(label_error, "Saved changes.", true);
			}
		});
		
		tab.setControl(groupInfo);    
	}
	
	private void updateList() {
		String[] preds = new String[predicates.size()];
		for (int i = 0; i < predicates.size(); i++) {
			preds[i] = predicates.get(i).getSignature(false);
		}
		predicatesList.setItems(preds);
	}
	
	private void displayError(Label label, String message, boolean green) {
		label.setForeground(green ? new Color(null, 0, 200,0) : new Color(null, 200, 0, 0));
		label.setText(message);
	}
	
	public void saveAndQuit() {
		String output = "+-+-+-+-+-+-+-DO NOT CHANGE THIS FILE MANUALLY - PLEASE USE PREDICATE MANAGEMENT WIZARD-+-+-+-+-+-+-+\n\\predicates {\n";
		for (Predicate p : predicates) {
			output += p.print();
		}
		output += "}\n+-+-+-+-+-+-+-DO NOT CHANGE THIS FILE MANUALLY - PLEASE USE PREDICATE MANAGEMENT WIZARD-+-+-+-+-+-+-+";
		writeToFile(output);
	}
	
	private void writeToFile(String output) {
		String predicateFilePath = resource.getLocationURI().toString().replace("file:/", "");
		predicateFilePath = predicateFilePath.substring(0, predicateFilePath.indexOf(projectName)) + projectName + "/predicates.def";
		File predicateFile = new File(predicateFilePath);
		File dir = new File(predicateFilePath.substring(0, predicateFilePath.lastIndexOf("/")));
		
		try {
			if (!predicateFile.exists()) {
				dir.mkdir();
			}
			FileWriter fw = new FileWriter(predicateFile);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(output);
			bw.close();
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IPath iLocation = Path.fromOSString(predicateFile.getAbsolutePath());
			IFile ifile = workspace.getRoot().getFileForLocation(iLocation);
			ifile.refreshLocal(0, null);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	private ArrayList<Predicate> loadPredicates() {
		String predicateFilePath = resource.getLocationURI().toString().replace("file:/", "");
		predicateFilePath = predicateFilePath.substring(0, predicateFilePath.indexOf(projectName)) + projectName + "/predicates.def";
		File predicateFile = new File(predicateFilePath);
		ArrayList<Predicate> readPredicates = new ArrayList<>();
		
		if (predicateFile.exists()) {
			Predicate newPredicate = null;
			ArrayList<String> lines = readFile(predicateFile.getAbsolutePath());
			if (lines.get(1).startsWith("\\predicates ")) {
				for (int i = 2; i < lines.size() - 1; i++) {
					String line = lines.get(i++);
					if (line.trim().equals("}")) break;
					String signature = line.trim().substring(0, line.trim().indexOf(" //") - 1);
					String config = line.trim().substring(line.trim().indexOf(" //") + 3);
					if (newPredicate == null || !newPredicate.signature.equals(signature)) {
						newPredicate = new Predicate(signature);
						readPredicates.add(newPredicate);
					}
					newPredicate.signature = signature;
					String replace = lines.get(i).trim().replace("\\replacewith (", "");
					replace = replace.substring(0, replace.length() - 1);
					PredicateDefinition pDef = new PredicateDefinition(replace, config);
					newPredicate.definitions.add(pDef);
				}
			}
		}		
		return readPredicates;
	}

	private ArrayList<String> readFile(String path) {
		ArrayList<String> lines = null;
		try {
			lines = (ArrayList<String>) Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}
	
	@Override
    public void performHelp() {
		Shell shell = new Shell(getShell());
        shell.setText("Predicate Management - Help");
        shell.setSize(1500, 350);
        shell.setLayout(new GridLayout());
        GridData gridData_shell = new GridData();
        shell.setLayoutData(gridData_shell);
     	
     	StyledText text = new StyledText(shell, SWT.MULTI | SWT.BORDER | SWT.WRAP);
     	text.setText("---Available Predicates---\r\n"
     			+ "Displays all predicates of the project the currently selected file is part of. Predicates can be added and deleted using the buttons above the list of predicates. The list displays the signature of the available predicates without the parameter's names. It is not possible to create duplicate predicates, even if the parameters differ.\r\n"
     			+ "\r\n"
     			+ "---Predicate Properties---\r\n"
     			+ "Enables the definition of predicates. For the definition, the following fields have to be set:\r\n"
     			+ "Signature: Signature of the predicate in method signature style. Provide name and parameters. For every parameter, provide type and name. The parameter's order is considered when replacing predicates by their definition at verification time. Example: newPredicate(int[] array, int number, String word).\r\n"
     			+ "Definition: The definition a predicate should be replaced by. The parameters declared in the signature will be replaced by the parameters provided by the predicate call in the program when the verification is started. Be aware that bound variables (\\forall, \\exists) must not exist in the terms the parameters of the predicate are replaced by. It is not allowed to use the keyword \\old in the definition of a predicate. Please provide old values of a field or variable by adding a new parameter to the predicate's definition. Example: data[data.length - 1] = newTop.\r\n"
     			+ "Restore Definition: Values of the currently displayed definition are restored to the last saved state.\r\n"
     			+ "Save Definition: Saves the current values of the currently displayed definition. Please consider error messages when saving definitions.");
        text.setEditable(false);
        text.setLocation(0, 0);
        text.setLayoutData(new GridData(GridData.FILL_BOTH));
     	text.setBackground(new Color(255,255,255));
        
     	StyleRange[] styles = new StyleRange[10];
     	                         //AvailablePreds, PredProps, Sign, SignEx, Def,  forExist, old,  DefEx, Res,  Save
     	int[] starts = new int[]  {0,              367,       491,  742,    795,  1044,     1176, 1330,  1363, 1467};
     	int[] lengths = new int[] {26,             26,        10,   50,     11,   16,       4,    30,    19,   16};
     	for (int i = 0; i < styles.length; i++) {
     		styles[i] = new StyleRange();
     		styles[i].start = starts[i];
            styles[i].length = lengths[i];
            if (i == 3 || i == 5 || i == 6 || i == 7) {
            	styles[i].fontStyle = SWT.ITALIC;
            } else {
            	styles[i].fontStyle = SWT.BOLD;
            }
     	}
        text.setStyleRanges(styles);
        shell.open();
    }
}