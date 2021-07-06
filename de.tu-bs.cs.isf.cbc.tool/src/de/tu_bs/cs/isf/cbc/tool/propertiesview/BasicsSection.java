package de.tu_bs.cs.isf.cbc.tool.propertiesview;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.core.internal.resources.File;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.platform.GFPropertySection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.PartEventAction;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.impl.MethodImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbCFormulaImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.MethodSignatureImpl;
import de.tu_bs.cs.isf.cbc.tool.diagram.CbCDiagramTypeProvider;

public class BasicsSection extends GFPropertySection implements ITabbedPropertyConstants {
	

	Display display = Display.getCurrent();// for UI updating of back-process

	// Defining the UI properties
	private Label classLabel;
	private StyledText classLabelText;
	private boolean classLabelTextChanged = false;

	private Label invariantLabel;
	private StyledText invariantLabelText;
	private List invariantList;
	private boolean invariantLabelChanged = false;
	
	private Label methodSignatureLabel;
	private StyledText methodSignatureLabelText;
	private boolean methodSignatureLabelChanged = false;
	
	private Button saveButton;
	private Object bo;

	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		Device device = Display.getCurrent();
		TabbedPropertySheetWidgetFactory factory = getWidgetFactory();

		Composite composite = factory.createFlatFormComposite(parent);

		// Defining GridLayout for properties-view
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayout.verticalSpacing = 20;
		composite.setLayout(gridLayout);
			
		// classLabel
		classLabel = new Label(composite, SWT.PUSH);
		Color white = new Color (device, 255, 255, 255);
		classLabel.setBackground(white);
		classLabel.setText("Class: ");
		
		// classLabelText
		classLabelText = new StyledText(composite, SWT.WRAP | SWT.PUSH | SWT.BORDER);
		GridData classLabelTextGridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		classLabelText.setLayoutData(classLabelTextGridData);
		classLabelText.setBackground(white);
		
		classLabelText.addModifyListener(new ModifyListener() {
			
			

			@Override
			public void modifyText(ModifyEvent e) {
				classLabelTextChanged = true;
				saveButton.setEnabled(true);
			}
		});
		
		// methodSignatureLabel
		methodSignatureLabel = new Label(composite, SWT.PUSH);
		methodSignatureLabel.setText("MethodSignature: ");
		methodSignatureLabel.setBackground(white);
		
		// methodSignatureLabelText
		methodSignatureLabelText = new StyledText(composite, SWT.WRAP | SWT.PUSH | SWT.BORDER);
		GridData methodSignatureLabelTextGridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		methodSignatureLabelText.setEditable(true);
		methodSignatureLabelText.setLayoutData(methodSignatureLabelTextGridData);
		methodSignatureLabelText.setBackground(white);
		
		methodSignatureLabelText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				methodSignatureLabelChanged = true;
				saveButton.setEnabled(true);
			}
		});
		
		// invariantLabel
		invariantLabel = new Label(composite, SWT.PUSH);
		invariantLabel.setText("Invariants: ");
		invariantLabel.setBackground(white);

		// invariantLabelText
		 invariantList = new List(composite, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL);

	     invariantList.setItems();
	     int listHeight = invariantList.getItemHeight() * 5;

	     Rectangle trim = invariantList.computeTrim(0, 0, 0, listHeight);
	     GridData invariantLabelTextGridData = new GridData(SWT.FILL, SWT.FILL, true, false);
	     invariantLabelTextGridData.heightHint = trim.height;
	     invariantList.setLayoutData(invariantLabelTextGridData);
	     invariantList.setBackground(white);
	       
//		invariantLabelText = new StyledText(composite, SWT.WRAP | SWT.PUSH | SWT.BORDER);
//		GridData invariantLabelTextGridData = new GridData(SWT.FILL, SWT.FILL, true, false);
//		invariantLabelText.setLayoutData(invariantLabelTextGridData);
//		invariantLabelText.setBackground(white);
		
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
		
		// generateButton
		saveButton = new Button(composite, SWT.PUSH);
		saveButton.setText("Save");
		saveButton.setEnabled(false);

		saveButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(bo);
				domain.getCommandStack().execute(new RecordingCommand(domain) {
					@Override
					protected void doExecute() {
						if(classLabelTextChanged) {
							changeClass();
							classLabelTextChanged = false;
						}
						
						if(methodSignatureLabelChanged) {
							String text = methodSignatureLabelText.getText();
							changeMethodSignature(text);
							methodSignatureLabelChanged = false;
						}
						if(invariantLabelChanged) {
							String text = invariantLabelText.getText();
							changeInvariant(text);
							invariantLabelChanged = false;
						}
						saveButton.setEnabled(false);
					}


				});
			}
			
			// TODO update der Java-Klassen
		});
		
	}

	@Override
	public void refresh() {
		CbCDiagramTypeProvider diagramProvider = new CbCDiagramTypeProvider();
		PictogramElement pe = getSelectedPictogramElement();
		bo = diagramProvider.getFeatureProvider().getBusinessObjectForPictogramElement(pe);
		if(bo instanceof CbCFormulaImpl) {
			updateData(bo);			
		}
	}
	
	private void changeClass() {
		// TODO Auto-generated method stub
		
	}
	
	public void changeInvariant(String newInvariant) {
		if(bo instanceof CbCFormulaImpl) {
			//text in Liste von Invarianten
			
			//Liste neu befüllen
			
			((CbCFormulaImpl) bo).getMethodObj().getParentClass().getClassInvariants();
			getDiagramTypeProvider().getDiagramBehavior().refresh();
		}
	}
	
	public void changeMethodSignature(String newMethodSignature) {
//			((MethodSignatureImpl) bo).setMethodSignature(((MethodSignatureImpl) bo).getMethodSignature().replace(((MethodSignatureImpl) bo).getMethodSignature(), newMethodSignature));
			((CbCFormulaImpl) bo).getMethodObj().setSignature(newMethodSignature);
			getDiagramTypeProvider().getDiagramBehavior().refresh();
	}

	public void updateData(Object bo) {
		display.syncExec(new Runnable() {
			@Override
			public void run() {
				if(((CbCFormulaImpl) bo).getMethodObj() instanceof MethodImpl) {
					Method methodObj = (Method) ((CbCFormulaImpl) bo).getMethodObj();
					methodSignatureLabelText.setText(methodObj.getSignature());
					classLabelText.setText(methodObj.getParentClass().getName());
					EList<Condition> classInvariants = methodObj.getParentClass().getClassInvariants();
					String[] invariants = new String[classInvariants.size()];
					for(int i = 0; i<invariants.length; i++){
						invariants[i] = classInvariants.get(i).getName();
					}
					
					invariantList.setItems(invariants);
				}
			}
		});
	}

}
