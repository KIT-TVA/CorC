package PropertiesView;

import org.eclipse.core.resources.IResource;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.platform.GFPropertySection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbCFormulaImpl;
import de.tu_bs.cs.isf.cbc.tool.diagram.CbCDiagramTypeProvider;

/**
 * Class for the Basic-Tab of the Properties-View. Shows Basic informations
 * about the VarCorC-Diagram
 * 
 * @author David
 */
public class BasicsSection extends GFPropertySection implements ITabbedPropertyConstants {

	Display display = Display.getCurrent();// for UI updating of back-process

	// Defining the logical properties
	private String currentFeature;
	private String currentMethod;
	private String currentCompositionTechnique;

	// Defining the UI properties
	private Label featureLabel;
	private Text featureLabelText;

	private Label methodLabel;
	private Text methodLabelText;

	private Label compositionTechniqueLabel;
	private Text compositionTechniqueLabelText;

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

		// methodLabel
		methodLabel = new Label(composite, SWT.PUSH);
		methodLabel.setText("Method: ");

		// methodLabelText
		methodLabelText = new Text(composite, SWT.WRAP | SWT.PUSH);
		GridData methodLabelTextGridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		methodLabelText.setText(currentMethod);
		methodLabelText.setLayoutData(methodLabelTextGridData);

		// featureLabel
		featureLabel = new Label(composite, SWT.PUSH);
		featureLabel.setText("Feature: ");

		// featureLabelText
		featureLabelText = new Text(composite, SWT.WRAP | SWT.PUSH);
		GridData featureLabelTextGridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		featureLabelText.setText(currentFeature);
		featureLabelText.setLayoutData(featureLabelTextGridData);

		// compositionTechniqueLabel
		compositionTechniqueLabel = new Label(composite, SWT.PUSH);
		compositionTechniqueLabel.setText("Composition technique: ");

		// compositionTechniqueLabelText
		compositionTechniqueLabelText = new Text(composite, SWT.WRAP | SWT.PUSH);
		GridData compositionTechniqueLabelTextGridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		compositionTechniqueLabelText.setText("");
		compositionTechniqueLabelText.setLayoutData(compositionTechniqueLabelTextGridData);

	}

	@Override
	public void refresh() {
		CbCDiagramTypeProvider test = new CbCDiagramTypeProvider();
		PictogramElement pe = getSelectedPictogramElement();
		Object bo = test.getFeatureProvider().getBusinessObjectForPictogramElement(pe);
		if (currentCompositionTechnique == null) {
			if (bo instanceof CbCFormulaImpl) {
				updateCurrentCompositionTechnique(bo);
			}
		}
	}

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

	public void updateCurrentCompositionTechnique(Object bo) {
		display.syncExec(new Runnable() {
			@Override
			public void run() {
				if ((((CbCFormulaImpl) bo).getCompositionTechnique()).toString().equals("CONJUNCTIVE_CONTRACTING")) {
					compositionTechniqueLabelText.setText("Conjunctive Contract Refinement");
				} else if ((((CbCFormulaImpl) bo).getCompositionTechnique()).toString()
						.equals("EXPLICIT_CONTRACTING")) {
					compositionTechniqueLabelText.setText("Explicit Contracting");
				} else if ((((CbCFormulaImpl) bo).getCompositionTechnique()).toString().equals("CONTRACT_OVERRIDING")) {
					compositionTechniqueLabelText.setText("Contract Overriding");
				}
			}
		});
	}
}
