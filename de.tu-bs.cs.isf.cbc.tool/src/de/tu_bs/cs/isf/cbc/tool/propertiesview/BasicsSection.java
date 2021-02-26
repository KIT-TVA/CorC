package de.tu_bs.cs.isf.cbc.tool.propertiesview;

import java.nio.file.Path;

import org.eclipse.core.internal.resources.File;
import org.eclipse.core.resources.IResource;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.platform.GFPropertySection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
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
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import cbcclass.impl.MethodImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbCFormulaImpl;
import de.tu_bs.cs.isf.cbc.tool.diagram.CbCDiagramTypeProvider;

public class BasicsSection extends GFPropertySection implements ITabbedPropertyConstants {
	

	Display display = Display.getCurrent();// for UI updating of back-process

	// Defining the logical properties
	private String currentFeature;
	private String currentMethod;
	private String currentCompositionTechnique;

	// Defining the UI properties
	private Label classLabel;
	private Text classLabelText;

	private Label methodLabel;
	private Text methodLabelText;

	private Label invariantLabel;
	private Text invariantLabelText;
	
	private Label methodSignatureLabel;
	private StyledText methodSignatureLabelText;

	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		TabbedPropertySheetWidgetFactory factory = getWidgetFactory();

		Composite composite = factory.createFlatFormComposite(parent);

		// Get the logical properties
		currentFeature = getCurrentFeature();
		currentMethod = getCurrentMethod();

		// Defining GridLayout for properties-view
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayout.verticalSpacing = 20;
		composite.setLayout(gridLayout);
		
		
		// classLabel
		classLabel = new Label(composite, SWT.PUSH);
		classLabel.setText("Class: ");
		
		// classLabelText
		classLabelText = new Text(composite, SWT.WRAP | SWT.PUSH);
		GridData classLabelTextGridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		classLabelText.setText(currentMethod);
		classLabelText.setLayoutData(classLabelTextGridData);

		// methodLabel
		methodLabel = new Label(composite, SWT.PUSH);
		methodLabel.setText("Method: ");

		// methodLabelText
		methodLabelText = new Text(composite, SWT.WRAP | SWT.PUSH);
		GridData methodLabelTextGridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		methodLabelText.setText(currentMethod);
		methodLabelText.setLayoutData(methodLabelTextGridData);
		
		// methodSignatureLabel
		methodSignatureLabel = new Label(composite, SWT.PUSH);
		methodSignatureLabel.setText("methodSignature: ");
		
		// methodSignatureLabelText
		methodSignatureLabelText = new StyledText(composite, SWT.WRAP | SWT.PUSH | SWT.BORDER);
		GridData methodSignatureLabelTextGridData = new GridData(SWT.WRAP, SWT.WRAP, true, false);
		methodSignatureLabelText.setText("");
		methodSignatureLabelText.setEditable(true);
		methodSignatureLabelText.setLayoutData(methodSignatureLabelTextGridData);
		
		// invariantLabel
		invariantLabel = new Label(composite, SWT.PUSH);
		invariantLabel.setText("Invariant: ");

		// invariantLabelText
		invariantLabelText = new Text(composite, SWT.WRAP | SWT.PUSH);
		GridData invariantLabelTextGridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		invariantLabelText.setText(currentMethod);
		invariantLabelText.setLayoutData(invariantLabelTextGridData);
		
		//Zu beachten: Wenn methodSignature der Methodenname geändert wurde dann auch die Signatur anpassen und umgekert
		
//		methodSignatureLabelText.addTraverseListener(new TraverseListener() {
//			@Override
//			public void keyTraversed(final TraverseEvent event) {
//				if (event.detail == SWT.TRAVERSE_RETURN) {
//					changeMethodSignature(methodSignatureLabelText.getText());
//					methodSignatureLabelText.setText(methodSignatureLabelText.getText());
//				}
//			}
//		});
		
	}

	@Override
	public void refresh() {
		CbCDiagramTypeProvider test = new CbCDiagramTypeProvider();
		PictogramElement pe = getSelectedPictogramElement();
		Object bo = test.getFeatureProvider().getBusinessObjectForPictogramElement(pe);
		if(bo instanceof CbCFormulaImpl) {
			updateData(bo);			
		}
	}
	
//	public void changeMethodSignature(String newText) {
//		MethodImpl methodObj = getMethodObj();
//		if(methodObj != null) {
//			methodObj.getSignature().replace(methodObj.getSignature(), newText);
//			
//		}
//	}
//	
//	public MethodImpl getMethodObj() {
//		CbCDiagramTypeProvider test = new CbCDiagramTypeProvider();
//		PictogramElement pe = getSelectedPictogramElement();
//		Object bo = test.getFeatureProvider().getBusinessObjectForPictogramElement(pe);
//		if(bo instanceof CbCFormulaImpl) {
//			return (MethodImpl) ((CbCFormulaImpl) bo).getMethodObj();
//		}
//		return null;
//	}

	public String getCurrentFeature() {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage activePage = window.getActivePage();
		IEditorPart activeEditor = activePage.getActiveEditor();
		IEditorInput input = activeEditor.getEditorInput();
		IResource diagramResource = input.getAdapter(IResource.class);
		IResource featureResource = diagramResource.getParent().getParent(); // ->diagramFolder->featureFolder
		
		return featureResource.getName();
	}

	public String getCurrentMethod() {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage activePage = window.getActivePage();
		IEditorPart activeEditor = activePage.getActiveEditor();
		IEditorInput input = activeEditor.getEditorInput();
		IResource diagramResource = input.getAdapter(IResource.class);
		String diagramName = diagramResource.getName();
		diagramName = diagramName.substring(0, diagramName.lastIndexOf('.'));
		return diagramName;
	}

	public void updateData(Object bo) {
		display.syncExec(new Runnable() {
			@Override
			public void run() {
				if(((CbCFormulaImpl) bo).getMethodObj() instanceof MethodImpl) {
					MethodImpl methodObj = (MethodImpl) ((CbCFormulaImpl) bo).getMethodObj();
					methodSignatureLabelText.setText(methodObj.getSignature());
					classLabelText.setText(methodObj.getCbcStartTriple().getClassName());
				}
			}
		});
	}

}
