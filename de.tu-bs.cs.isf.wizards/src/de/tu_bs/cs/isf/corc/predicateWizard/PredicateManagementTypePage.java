package de.tu_bs.cs.isf.corc.predicateWizard;

import de.tu_bs.cs.isf.cbc.tool.helper.Predicate;
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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class PredicateManagementTypePage extends WizardPage {
	private ArrayList<Predicate> predicates;
	
	private static final String PROJECT_TYPE_NOTOO = "non object-oriented project"; // project/src/.diagram
	private static final String PROJECT_TYPE_OO = "object-oriented project"; // project/src/class/.diagram
	private static final String PROJECT_TYPE_SPL = "object-oriented spl"; // project/features/feature/class/.diagram
	
	private String projectType = "";
	private IResource resource;
	private String projectName;
	private String featureName;
	private String className;
	private String methodName;
	private boolean variationalProject;
	private Predicate currentPredicate = null;
	private String currentPredicateFeature;
	private String currentPredicateClass;
	private String currentPredicateMethod;
	
	private static List predicatesList;
	
	private Button buttonAddPredicate;
	private Button buttonDelPredicate;
	private Button buttonSave;
	private Button buttonRestore;
	
	private Combo combo_feature;
	private Combo combo_class;
	private Combo combo_method;
	
	private Text nameField;
	private Text signatureField;
	private Text findField;
	private Text replaceField;
	
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
		methodName = !parts[parts.length-1].replace(".diagram", "").equalsIgnoreCase(parts[parts.length-2]) ? parts[parts.length-1].replace(".diagram", "") : ""; 
		className = projectType.equals(PROJECT_TYPE_NOTOO) ? "" : parts[parts.length-2];
		featureName = projectType.equals(PROJECT_TYPE_SPL) ? parts[parts.length-3] : "";
		projectName = projectType.equals(PROJECT_TYPE_NOTOO) ? parts[parts.length-3] : (projectType.equals(PROJECT_TYPE_OO) ? parts[parts.length-4] : parts[parts.length-5]);
		setDescription("Manage the predicates of the project " + projectName + ".");
		variationalProject = projectType.equals(PROJECT_TYPE_SPL);		
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
		predicatesList = new List(groupList, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL);
		updateList();
		gridData_list_predicates.heightHint = predicatesList.computeTrim(0, 0, 0, predicatesList.getItemHeight() * 14).height;
	    predicatesList.setLayoutData(gridData_list_predicates);
		
	    // GROUP predicate information
	    Group groupInfo = new Group(composite, SWT.NULL);
		groupInfo.setText("Predicate properties");
	    GridLayout layout_group_predicate_info = new GridLayout();
	    layout_group_predicate_info.numColumns = 3;
	    groupInfo.setLayout(layout_group_predicate_info);
	    GridData gridData_group_predicate_info = new GridData();
	    gridData_group_predicate_info.horizontalAlignment = GridData.FILL;
	    gridData_group_predicate_info.verticalAlignment = GridData.FILL;
	    gridData_group_predicate_info.grabExcessHorizontalSpace = true;
	    groupInfo.setLayoutData(gridData_group_predicate_info);
	    
	    // LABEL + INPUT name
	    GridData gridData_label_name = new GridData();
	    gridData_label_name.grabExcessHorizontalSpace = true;
	    gridData_label_name.horizontalAlignment = GridData.FILL;
	    gridData_label_name.horizontalSpan = 2;
	    new Label(groupInfo, SWT.NULL).setText("Name:");
	    nameField = new Text(groupInfo, SWT.SINGLE | SWT.BORDER);
	    nameField.setLayoutData(gridData_label_name);
	    nameField.setEnabled(false);
	    
	    // LABEL + INPUT signature
	    GridData gridData_label_signature = new GridData();
	    gridData_label_signature.grabExcessHorizontalSpace = true;
	    gridData_label_signature.horizontalAlignment = GridData.FILL;
	    gridData_label_signature.horizontalSpan = 2;
	    new Label(groupInfo, SWT.NULL).setText("Signature:");
	    signatureField = new Text(groupInfo, SWT.SINGLE | SWT.BORDER);
	    signatureField.setLayoutData(gridData_label_signature);
	    signatureField.setEnabled(false);
	    
	    // LABEL + INPUT find
	    GridData gridData_label_find = new GridData();
	    gridData_label_find.grabExcessHorizontalSpace = true;
	    gridData_label_find.horizontalAlignment = GridData.FILL;
	    gridData_label_find.horizontalSpan = 2;
	    new Label(groupInfo, SWT.NULL).setText("Find:");
	    findField = new Text(groupInfo, SWT.SINGLE | SWT.BORDER);
	    findField.setLayoutData(gridData_label_find);
	    findField.setEnabled(false);
	    
	    // LABEL + INPUT replace
	    GridData gridData_label_replace = new GridData(GridData.FILL_BOTH);
	    gridData_label_replace.grabExcessHorizontalSpace = true;
	    gridData_label_replace.horizontalSpan = 2;
	    new Label(groupInfo, SWT.NULL).setText("Replace:");
	    replaceField = new Text(groupInfo, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
	    replaceField.setLayoutData(gridData_label_replace);
	    replaceField.setEnabled(false);
	    
	    // GROUP config predicate
	    Group groupConfig = new Group(groupInfo, SWT.NULL);
		groupConfig.setText("Availability of Predicate");
	    GridLayout layout_group_predicate_config = new GridLayout();
	    layout_group_predicate_config.numColumns = variationalProject ? 6 : 4;
	    groupConfig.setLayout(layout_group_predicate_config);
	    GridData gridData_group_predicate_config = new GridData();
	    gridData_group_predicate_config.horizontalAlignment = GridData.FILL;
	    gridData_group_predicate_config.verticalAlignment = GridData.FILL;
	    gridData_group_predicate_config.grabExcessHorizontalSpace = true;
	    gridData_group_predicate_config.horizontalSpan = 3;
	    groupConfig.setLayoutData(gridData_group_predicate_config);
	    
	    //LABEL + INPUT feature
	    if (variationalProject) {
	    	new Label(groupConfig, SWT.NULL).setText("Feature:");
	    	combo_feature = new Combo(groupConfig, SWT.DROP_DOWN | SWT.READ_ONLY);
	    	combo_feature.setEnabled(false);
	    	GridData gridData_combo_feature = new GridData();
	    	gridData_combo_feature.horizontalAlignment = GridData.FILL;
	    	gridData_combo_feature.grabExcessHorizontalSpace = true;
	    	gridData_combo_feature.widthHint = 90;
	    	combo_feature.setLayoutData(gridData_combo_feature);
	    }
	    	
		//LABEL + INPUT class
	    new Label(groupConfig, SWT.NULL).setText("Class:");
	    combo_class = new Combo(groupConfig, SWT.DROP_DOWN | SWT.READ_ONLY);
		combo_class.setEnabled(false);
		GridData gridData_combo_class = new GridData();
		gridData_combo_class.horizontalAlignment = GridData.FILL;
		gridData_combo_class.grabExcessHorizontalSpace = true;
    	gridData_combo_class.widthHint = 90;
		combo_class.setLayoutData(gridData_combo_class);
		
		//LABEL + INPUT method
	    new Label(groupConfig, SWT.NULL).setText("Method:");
	    combo_method = new Combo(groupConfig, SWT.DROP_DOWN | SWT.READ_ONLY);
		combo_method.setEnabled(false);
		GridData gridData_combo_method = new GridData();
		gridData_combo_method.horizontalAlignment = GridData.FILL;
		gridData_combo_method.grabExcessHorizontalSpace = true;
    	gridData_combo_method.widthHint = 90;
		combo_method.setLayoutData(gridData_combo_method);
		
		//BUTTON restore
		GridData gridData_button_restore = new GridData();
		gridData_button_restore.horizontalAlignment = GridData.END;
		gridData_button_restore.widthHint = 80;
		gridData_button_restore.horizontalSpan = 2;
		gridData_button_restore.horizontalIndent = 380;
		buttonRestore = new Button(groupInfo, SWT.PUSH);
		buttonRestore.setText("Restore");
		buttonRestore.setEnabled(false);
		buttonRestore.setLayoutData(gridData_button_restore);
		
		//BUTTON save
		GridData gridData_button_save = new GridData();
		gridData_button_save.horizontalAlignment = GridData.END;
		gridData_button_save.widthHint = 80;
		buttonSave = new Button(groupInfo, SWT.PUSH);
		buttonSave.setText("Save");
		buttonSave.setEnabled(false);
		buttonSave.setLayoutData(gridData_button_save);
		
	    // END LAYOUT----------------------------------------------------------------------------------
		
	    // LISTENER button add predicate
	    buttonAddPredicate.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				Predicate newPredicate = new Predicate("default:default:default");
				newPredicate.name = "newPredicate";
				newPredicate.def = "int";
				newPredicate.find = "newPredicate(var)";
				newPredicate.replace = "true";
				newPredicate.resolveVars();
				predicates.add(newPredicate);
				updateList();
				resetFields();
			}
		});
	    
	    //LISTENER button del predicate
	    buttonDelPredicate.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				for (Predicate p : predicates) {
					if (p.def.equals(predicatesList.getSelection()[0])) {
						predicates.remove(p);
						updateList();
						resetFields();
						return;
					}
				}
			}
		});
	    
	    //LISTENER button save
	    buttonSave.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				Predicate newPredicate = new Predicate(currentPredicateFeature + ":" + currentPredicateClass + ":" + currentPredicateMethod);
				newPredicate.name = nameField.getText();
				newPredicate.def = signatureField.getText();
				newPredicate.find = findField.getText();
				newPredicate.replace = replaceField.getText();
				newPredicate.resolveVars();
				predicates.set(predicatesList.getSelectionIndex(), newPredicate);
				updateList();
				resetFields();
			}
		});
	    
	    //LISTENER button restore
	    buttonRestore.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				resetFields();
	        	loadSelectedPredicate();
			}
		});
	    
	    //LISTENER predicates list
	    predicatesList.addListener(SWT.Selection, new Listener() {
	        public void handleEvent(Event e) {
	        	resetFields();
	        	loadSelectedPredicate();
	        }
	    });
	    
	    //LISTENER combo features
	    combo_feature.addSelectionListener(new SelectionAdapter() {
			@Override
            public void widgetSelected(SelectionEvent e) {
                int index = combo_feature.getSelectionIndex();
                if (index == 0) {
                	currentPredicateFeature = "default";
                	currentPredicateClass = "default";
                	currentPredicateMethod = "default";
                	combo_class.setItems(new String[0]);
                	combo_class.setEnabled(false);
                	combo_method.setItems(new String[0]);
                	combo_method.setEnabled(false);
                } else {
                	combo_class.setItems(loadAvailableClasses());
                	combo_class.setText("All classes");
                	combo_class.setEnabled(true);
                	combo_method.setItems(new String[0]);
                	combo_method.setEnabled(false);
                	currentPredicateFeature = combo_feature.getItem(index);
                }
            }
        });

	    //LISTENER combo classes
	    combo_class.addSelectionListener(new SelectionAdapter() {
			@Override
            public void widgetSelected(SelectionEvent e) {
                int index = combo_class.getSelectionIndex();
                if (index == 0) {
                	currentPredicateClass = "default";
                	currentPredicateMethod = "default";
                	combo_method.setItems(new String[0]);
                	combo_method.setEnabled(false);
                } else {
                	combo_method.setItems(loadAvailableMethods());
                	combo_method.setText("All methods");
                	combo_method.setEnabled(true);
                	currentPredicateClass = combo_class.getItem(index);
                }
            }
        });

	    //LISTENER combo methods
	    combo_method.addSelectionListener(new SelectionAdapter() {
			@Override
            public void widgetSelected(SelectionEvent e) {
                int index = combo_method.getSelectionIndex();
                if (index == 0) {
                	currentPredicateMethod = "default";
                } else {
                	currentPredicateMethod = combo_method.getItem(index);
                }
			}
        });
	    
	    setPageComplete(true);
	    setControl(composite);
	}
	
	private void loadSelectedPredicate() {
		for (Predicate p : predicates) {
    		if ((p.name + "(" + p.def + ")").equals(predicatesList.getSelection()[0])) {
    			currentPredicate = p;
    			nameField.setText(p.name);
    			nameField.setEnabled(true);
    			signatureField.setText(p.def);
    			signatureField.setEnabled(true);
    			findField.setText(p.find);
    			findField.setEnabled(true);
    			replaceField.setText(p.replace);
    			replaceField.setEnabled(true);
    			combo_feature.setEnabled(true);
    			buttonRestore.setEnabled(true);
    			buttonSave.setEnabled(true);
    			
    			combo_feature.setItems(loadAvailableFeatures());
    			if (currentPredicate.definedInFeature.equals("default")) {
    				combo_feature.setText("All features");
    			} else {
    				combo_feature.setText(currentPredicate.getDefinedInFeature());
    				combo_class.setItems(loadAvailableClasses());
    				combo_class.setEnabled(true);
    				if (currentPredicate.definedInClass.equals("default")) {
        				combo_class.setText("All classes");
        			} else {
        				combo_class.setText(currentPredicate.getDefinedInClass());
        				combo_method.setItems(loadAvailableMethods());
        				combo_method.setEnabled(true);
        				combo_method.setText(currentPredicate.getDefinedInMethod());
        			}	
    			}
    			
    			currentPredicateFeature = currentPredicate.definedInFeature;
				currentPredicateClass = currentPredicate.definedInClass;
				currentPredicateMethod = currentPredicate.definedInMethod;
    			return;
    		}
    	}
	}

	private void updateList() {
		String[] preds = new String[predicates.size()];
		for (int i = 0; i < predicates.size(); i++) {
			preds[i] = predicates.get(i).name + "(" + predicates.get(i).def + ")";
		}
		predicatesList.setItems(preds);
	}
	
	private void resetFields() {
		nameField.setEnabled(false);
		signatureField.setEnabled(false);
        findField.setEnabled(false);
        replaceField.setEnabled(false);
        nameField.setText("");
        signatureField.setText("");
        findField.setText("");
        replaceField.setText("");
        
        combo_feature.setEnabled(false);
        combo_feature.setItems(new String[0]);
        combo_class.setEnabled(false);
        combo_class.setItems(new String[0]);
        combo_method.setEnabled(false);
        combo_method.setItems(new String[0]);
        
        buttonRestore.setEnabled(false);
        buttonSave.setEnabled(false);
	}
	
	public void saveAndQuit() {
		String output = "";
		String predicatesOutput = "\\predicates {\n";
		String rulesOutput = "\\rules {\n";
		if (!predicates.isEmpty()) {
			for (Predicate p : predicates) {
				predicatesOutput += p.printPredicate() + "\n";
				rulesOutput += p.printRule() + "\n\n";
			}
			output = predicatesOutput + "}\n\n" + rulesOutput + "}";
		}
		writeToFile(output);
	}
	
	private String[] loadAvailableFeatures() {
		return new String[] {"All features", "f1", "f2"}; //TODO
	}
	
	private String[] loadAvailableClasses() {
		return new String[] {"All classes", "c1", "c2"}; //TODO
	}
	
	private String[] loadAvailableMethods() {
		return new String[] {"All methods", "m1", "m2"}; //TODO
	}
	
	private void writeToFile(String output) {
		String predicateFilePath = resource.getLocationURI().toString().replace("file:/", "").substring(0, resource.getLocationURI().toString().indexOf(projectName)) + "/helper.key";
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
		String predicateFilePath = resource.getLocationURI().toString().replace("file:/", "").substring(0, resource.getLocationURI().toString().indexOf(projectName)) + "/helper.key";
		File predicateFile = new File(predicateFilePath);
		ArrayList<Predicate> readPredicates = new ArrayList<>();
		boolean predicateDef = false;
		boolean ruleDef = false;
		
		if (predicateFile.exists()) {
			ArrayList<String> lines = readFile(predicateFile.getAbsolutePath());
			for (int i = 0; i < lines.size(); i++) {
				String line = lines.get(i);
				if (line.startsWith("\\predicates ")) {
					predicateDef = true;
				} else if (line.startsWith("\\rules ")) {
					ruleDef = true;
				}
				
				if (predicateDef) {
					i++;
					do {
						line = lines.get(i++);
						Predicate newPredicate = new Predicate(line.substring(line.indexOf("//") + 2).trim());
						newPredicate.name = line.trim().substring(0, line.indexOf("(") - 1);
						newPredicate.def = line.substring(line.indexOf("(") + 1, line.indexOf(")"));
						readPredicates.add(newPredicate);
					} while (!lines.get(i).contains("}"));
					predicateDef = false;
				} else if (ruleDef) {
					i++;
					do {
						line = lines.get(i++).trim();
						for (Predicate p : readPredicates) {
							if (p.name.equals(line.replace("{", ""))) {
								while (!lines.get(i).trim().equals("") && !lines.get(i).trim().equals("}")) {
									line = lines.get(i++).trim();
									String defType = line.substring(0, (line.indexOf(" ") == -1 ? 0 : line.indexOf(" ")));
									switch (defType) {
										case "\\schemaVar":
											if (line.startsWith("\\schemaVar \\term")) {
												p.varsTerms.add(line.replace("\\schemaVar \\term ", "").replace(";", ""));
											} else {
												p.varsFree.add(line.replace("\\schemaVar \\variable ", "").replace(";", ""));
											}
											break;
										case "\\find":
											p.find = line.replace("\\find (", "");
											p.find = p.find.substring(0, p.find.length() - 1);
											break;
										case "\\replacewith":
											p.replace = line.replace("\\replacewith (", "");
											p.replace = p.replace.substring(0, p.replace.length() - 1);
											break;
										default:
											break;
									}
								}
								break;
							}
						}
						i++;
					} while (i-1 < lines.size() && !lines.get(i-1).equals("}"));
					ruleDef = false;
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
}