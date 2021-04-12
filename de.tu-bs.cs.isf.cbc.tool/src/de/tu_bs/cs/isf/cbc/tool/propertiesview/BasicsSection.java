package de.tu_bs.cs.isf.cbc.tool.propertiesview;

import java.nio.file.Path;

import org.eclipse.core.internal.resources.File;
import org.eclipse.core.resources.IResource;
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
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
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

import cbcclass.Condition;
import cbcclass.impl.MethodImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbCFormulaImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.MethodSignatureImpl;
import de.tu_bs.cs.isf.cbc.tool.diagram.CbCDiagramTypeProvider;

public class BasicsSection extends GFPropertySection implements ITabbedPropertyConstants {
	

	Display display = Display.getCurrent();// for UI updating of back-process

	// Defining the UI properties
	private Label classLabel;
	private StyledText classLabelText;

	private Label methodLabel;
	private StyledText methodLabelText;
	private boolean methodLabelChanged = false;

	private Label invariantLabel;
	private StyledText invariantLabelText;
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

		// methodLabel
		methodLabel = new Label(composite, SWT.PUSH);
		methodLabel.setBackground(white);
		methodLabel.setText("Method: ");

		// methodLabelText
		methodLabelText = new StyledText(composite, SWT.WRAP | SWT.PUSH | SWT.BORDER);
		GridData methodLabelTextGridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		methodLabelText.setLayoutData(methodLabelTextGridData);
		methodLabelText.setEditable(true);
		methodLabelText.setBackground(white);
		
		methodLabelText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				methodLabelChanged = true;
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
				saveButton.setEnabled(true);
			}
		});
		
		// invariantLabel
		invariantLabel = new Label(composite, SWT.PUSH);
		invariantLabel.setText("Invariant: ");
		invariantLabel.setBackground(white);

		// invariantLabelText
		invariantLabelText = new StyledText(composite, SWT.WRAP | SWT.PUSH | SWT.BORDER);
		GridData invariantLabelTextGridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		invariantLabelText.setLayoutData(invariantLabelTextGridData);
		invariantLabelText.setBackground(white);
		
		//Zu beachten: Wenn methodSignature der Methodenname geändert wurde dann auch die Signatur anpassen und umgekert
		
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
						String text = methodSignatureLabelText.getText();
						if(methodLabelChanged) {
							changeMethod(text);
							methodLabelChanged = false;
						}
						if(methodSignatureLabelChanged) {
							changeMethodSignature(text);
							methodSignatureLabelChanged = false;
						}
						if(invariantLabelChanged) {
							changeInvariant();
							invariantLabelChanged = false;
						}
						saveButton.setEnabled(false);
					}
				});
			}
		});
		
	}

	@Override
	public void refresh() {
		CbCDiagramTypeProvider test = new CbCDiagramTypeProvider();
		PictogramElement pe = getSelectedPictogramElement();
		bo = test.getFeatureProvider().getBusinessObjectForPictogramElement(pe);
		if(bo instanceof CbCFormulaImpl) {
			updateData(bo);			
		}
	}
		
	public void changeMethod(String newMethodText) {
		
	}
	
	public void changeInvariant() {
		
	}
	
	public void changeMethodSignature(String newMethodSignature) {
		if(bo instanceof MethodSignatureImpl) {
			((MethodSignatureImpl) bo).setMethodSignature(((MethodSignatureImpl) bo).getMethodSignature().replace(((MethodSignatureImpl) bo).getMethodSignature(), newMethodSignature));
//			((CbCFormulaImpl) bo).getMethodObj().setSignature(newText);
			getDiagramTypeProvider().getDiagramBehavior().refresh();
		} else if (bo instanceof CbCFormulaImpl){
			System.out.println();
		}
	}

	public void updateData(Object bo) {
		display.syncExec(new Runnable() {
			@Override
			public void run() {
				if(((CbCFormulaImpl) bo).getMethodObj() instanceof MethodImpl) {
					MethodImpl methodObj = (MethodImpl) ((CbCFormulaImpl) bo).getMethodObj();
					methodSignatureLabelText.setText(methodObj.getSignature());
					methodLabelText.setText(methodObj.getCbcStartTriple().getName());
					classLabelText.setText(methodObj.getCbcStartTriple().getClassName());
					invariantLabelText.setText("");
					for(Condition invariant : methodObj.getParentClass().getClassInvariants()) {
						invariantLabelText.setText(invariantLabelText.getText() + invariant.getName() + "; ");
					}
				}
			}
		});
	}

}
