package de.tu_bs.cs.isf.cbcclass.tool.propertiesview;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.platform.GFPropertySection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import de.tu_bs.cs.isf.cbc.cbcclass.Field;
import de.tu_bs.cs.isf.cbc.cbcclass.Method;
import de.tu_bs.cs.isf.cbc.cbcclass.ModelClass;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.cbc.util.ClassUtil;
import de.tu_bs.cs.isf.cbc.tool.diagram.CbCDiagramTypeProvider;

public class BasicsSection extends GFPropertySection implements ITabbedPropertyConstants {
	Display display = Display.getCurrent();// for UI updating of back-process
	boolean variationalProject = false;
	
	// Defining the UI properties
	private Label featureLabel;
	private Label featureLabelText;
	private Label blankLabel;
	
	private Label invariantLabel;
	private List invariantList;
	
	private Label fieldLabel;
	private List fieldList;
	
	private Label methodLabel;
	private List methodList;

	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		Device device = Display.getCurrent();
		TabbedPropertySheetWidgetFactory factory = getWidgetFactory();

		Composite composite = factory.createFlatFormComposite(parent);
				
		// Defining GridLayout for properties-view
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		gridLayout.verticalSpacing = 10;
		composite.setLayout(gridLayout);
		Color white = new Color (device, 255, 255, 255);
		
		// featureLabel
		featureLabel = new Label(composite, SWT.PUSH);
		featureLabel.setBackground(white);
		featureLabel.setText("Current Feature: ");
		
		// featureLabelText
		featureLabelText = new Label(composite, SWT.PUSH);
		featureLabelText.setBackground(white);
	
		// blankLabel
		blankLabel = new Label(composite, SWT.PUSH);
		blankLabel.setBackground(white);
		blankLabel.setForeground(white);
		blankLabel.setText("blank");
		
		// invariantLabel
		invariantLabel = new Label(composite, SWT.PUSH);
		invariantLabel.setBackground(white);
		invariantLabel.setText("Invariants from other features' implementation of this class: ");
		
		// fieldLabel
		fieldLabel = new Label(composite, SWT.PUSH);
		fieldLabel.setBackground(white);
		fieldLabel.setText("Fields from other features' implementation of this class: ");
				
		// methodLabel
		methodLabel = new Label(composite, SWT.PUSH);
		methodLabel.setBackground(white);
		methodLabel.setText("Methods from other features' implementation of this class: ");
				
		// invariantLabelText
		invariantList = new List(composite, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL);
		invariantList.setItems();
		int invariantListHeight = invariantList.getItemHeight() * 8;

		Rectangle invariantTrim = invariantList.computeTrim(0, 0, 0, invariantListHeight);
		GridData invariantLabelTextGridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		invariantLabelTextGridData.heightHint = invariantTrim.height;
		invariantList.setLayoutData(invariantLabelTextGridData);
		invariantList.setBackground(white);
		invariantList.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		
		// fieldLabelText
		fieldList = new List(composite, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL);
		fieldList.setItems();
	    int fieldListHeight = fieldList.getItemHeight() * 8;

	    Rectangle fieldTrim = fieldList.computeTrim(0, 0, 0, fieldListHeight);
	    GridData fieldLabelTextGridData = new GridData(SWT.FILL, SWT.FILL, true, false);
	    fieldLabelTextGridData.heightHint = fieldTrim.height;
	    fieldList.setLayoutData(fieldLabelTextGridData);
	    fieldList.setBackground(white);
	    fieldList.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub	
			}
		});	
	    
	    // methodLabelText
	 	methodList = new List(composite, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL);
	 	methodList.setItems();
	 	int methodListHeight = methodList.getItemHeight() * 8;

	 	Rectangle methodTrim = methodList.computeTrim(0, 0, 0, methodListHeight);
	 	GridData methodLabelTextGridData = new GridData(SWT.FILL, SWT.FILL, true, false);
	 	methodLabelTextGridData.heightHint = methodTrim.height;
	 	methodList.setLayoutData(methodLabelTextGridData);
	 	methodList.setBackground(white);
	 	methodList.addSelectionListener(new SelectionListener() {
	 		@Override
	 		public void widgetSelected(SelectionEvent e) {
	 			// TODO Auto-generated method stub
	 		}

	 		@Override
	 		public void widgetDefaultSelected(SelectionEvent e) {
	 			// TODO Auto-generated method stub	
	 		}
	 	});	
	}

	@Override
	public void refresh() {
		CbCDiagramTypeProvider diagramProvider = new CbCDiagramTypeProvider();
		PictogramElement pe = getSelectedPictogramElement();
		diagramProvider.getFeatureProvider().getBusinessObjectForPictogramElement(pe);
		if (pe instanceof Diagram) {
			updateData(pe);
		}
	}	

	public void updateData(Object pe) {
		display.syncExec(new Runnable() {
			@Override
			public void run() {
				// update current feature, invariants & fields from other features
				String className = ((Diagram) pe).getName();
				URI uri = getDiagram().eResource().getURI();
				IProject project = FileUtil.getProjectFromFileInProject(uri);
				
				String featureName = uri.segment(3);
				featureLabelText.setText(featureName);
				
				int sizeInvariants = 0;
				int sizeFields = 0;
				int sizeMethods = 0;
				
				java.util.List<IFile> classFiles = ClassUtil.getFilesOfType(project, ".cbcclass");
				for (IFile cbcclassFile : classFiles) {
					if (cbcclassFile.getName().replace(".cbcclass", "").equals(className)) {
					String cbcclassPath = project.getLocationURI().toString().substring(6);// + "/" + cbcclassFile.getFullPath().segment(1) + "/" + cbcclassFile.getFullPath().segment(2) + "/" + cbcclassFile.getFullPath().segment(3) + "/" + cbcclassFile.getFullPath().segment(4);	
					Resource resource = ClassUtil.getClassModelResource(cbcclassPath, className, cbcclassFile.getFullPath().segment(2));
					if (resource != null) {
					for (EObject obj: resource.getContents()) {
						if (obj instanceof ModelClass) {
							ModelClass mc = (ModelClass) obj;
							if (mc.getFeature().equals(featureName)) {
								break;
							}
							sizeInvariants += mc.getClassInvariants().size();
							sizeFields += mc.getFields().size();
							for (Method m : mc.getMethods()) {
								sizeMethods++;
							}
						}
					}
					}
					}
				}
				
				String[] invariants = new String[sizeInvariants];
				int invariantsCounter = 0;
				String[] fields = new String[sizeFields];
				int fieldsCounter = 0;
				String[] methods = new String[sizeMethods];
				int methodsCounter = 0;
				for (IFile cbcclassFile : classFiles) {
					if (cbcclassFile.getName().replace(".cbcclass", "").equals(className)) {
					String cbcclassPath = project.getLocationURI().toString().substring(6);
					Resource resource = ClassUtil.getClassModelResource(cbcclassPath, className, cbcclassFile.getFullPath().segment(2));
					if (resource != null) {
					for (EObject obj: resource.getContents()) {
						if (obj instanceof ModelClass) {
							ModelClass mc = (ModelClass) obj;
							if (mc.getFeature().equals(featureName)) {
								break;
							}
							EList<Condition> classInvariants = mc.getClassInvariants();
							for (int i = 0; i < classInvariants.size(); i++){
								invariants[invariantsCounter++] = classInvariants.get(i).getName() + " (" + mc.getFeature() + ")";
							}
							EList<Field> classFields = mc.getFields();
							for (int i = 0; i < classFields.size(); i++){
								fields[fieldsCounter++] = classFields.get(i).getDisplayedName() + " (" + mc.getFeature() + ")";
							}
							EList<Method> classMethods = mc.getMethods();
							for (int i = 0; i < classMethods.size(); i++){
								methods[methodsCounter++] = classMethods.get(i).getSignature()  + " (" + mc.getFeature() + ")";
							}
						}
					}
					}
					}
				}
				invariantList.setItems(invariants);
				fieldList.setItems(fields);
				methodList.setItems(methods);
			}
		});
	}
}