package PropertiesView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.platform.GFPropertySection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
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

import de.tu_bs.cs.isf.cbc.cbcmodel.impl.MethodStatementImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.OriginalStatementImpl;
import de.tu_bs.cs.isf.cbc.tool.diagram.CbCDiagramTypeProvider;

/**
 * Class for the Replacements-Tab of the Properties-View. Filter and shows valid
 * replacements of an original-call or mehtod call in the product line.
 * 
 * @author David
 */
public class ReplacementsSection extends GFPropertySection implements ITabbedPropertyConstants {

	final List<Button> buttons = new ArrayList<Button>();
	// Defining the logical properties
	private Composite parent;
	private TabbedPropertySheetPage tabbedPropertySheetPage;
	private String currentFeature;
	private FeatureModel featureModel;
	private List<String> configs = new ArrayList<String>();
	private String outputFormat = "";
	private List<String> originalFeatureNames = new ArrayList<String>();
	private List<String> methodFeatureNames = new ArrayList<String>();

	// Defining the UI properties
	private Label replacementLabel;
	private Text replacementText;

	private Label filterLabel;
	private Group buttonGroup;

	private Label generationLabel;
	private Button generateButton;
	private Image buttonImage;

	private Label outputLabel;
	private Text outputText;

	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);

		this.parent = parent;
		this.tabbedPropertySheetPage = tabbedPropertySheetPage;
		currentFeature = getCurrentFeature();
		featureModel = new FeatureModel(currentFeature);
		originalFeatureNames = featureModel.getOriginalFeatureNames(getCurrentMethod());
		TabbedPropertySheetWidgetFactory factory = getWidgetFactory();

		Composite composite = factory.createFlatFormComposite(parent);

		// Defining GridLayout for properties-view
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayout.verticalSpacing = 20;
		composite.setLayout(gridLayout);

		// replacementLabel
		replacementLabel = new Label(composite, SWT.PUSH);
		replacementLabel.setText("Replacement type: ");

		// replacementText
		replacementText = new Text(composite, SWT.WRAP | SWT.PUSH);
		GridData replacementTextGridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		replacementText.setText("");
		replacementText.setLayoutData(replacementTextGridData);

		// filterLabel
		filterLabel = new Label(composite, SWT.PUSH);
		filterLabel.setText("Filter: ");

		// buttonGroup
		buttonGroup = new Group(composite, SWT.PUSH);
		buttonGroup.setText("Features");
		GridLayout buttonGroupLayout = new GridLayout();
		buttonGroupLayout.numColumns = 6;
		buttonGroup.setLayout(buttonGroupLayout);

		// generationLabel
		generationLabel = new Label(composite, SWT.PUSH);
		generationLabel.setText("Generation: ");

		// buttonImage
		InputStream input = ConfigurationsSection.class.getResourceAsStream("generate16.png");
		buttonImage = new Image(null, input);

		// generateButton
		generateButton = new Button(composite, SWT.PUSH);
		generateButton.setImage(buttonImage);
		generateButton.setText("Generate");
		generateButton.setEnabled(false);

		// outputLabel
		outputLabel = new Label(composite, SWT.PUSH);
		outputLabel.setText("Output: ");

		// outputText
		outputText = new Text(composite, SWT.WRAP | SWT.PUSH);
		GridData outputGridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		outputText.setText("");
		outputText.setLayoutData(outputGridData);

		// Listener for generateButton
		generateButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				List<String> selectedFeatures = new ArrayList<String>();
				// iterate over saved buttons
				for (Button button : buttons) {
					if (button.getSelection() == true) {
						selectedFeatures.add(button.getText());
					}
				}
				if (replacementText.getText().equals("Original-Call")) {
//					configs = featureModel.getFeatureConfigsOriginal(selectedFeatures);
					configs = featureModel.getFeatureConfigs(selectedFeatures, "original");
				} else if (replacementText.getText().equals("Method-Call")) {
					configs = featureModel.getFeatureConfigs(selectedFeatures, "method");
				}
				outputFormat = "";
				for (int d = 0; d < configs.size(); d++) {
					if (d == configs.size() - 1) {
						outputFormat = outputFormat + "Configuration<" + (d + 1) + ">:" + configs.get(d);
					} else {
						outputFormat = outputFormat + "Configuration<" + (d + 1) + ">:" + configs.get(d) + "\n";
					}
				}
				if (configs.isEmpty()) {
					outputText.setText("No available Configuration");
				} else {
					outputText.setText(outputFormat);
				}
				parent.pack();
				tabbedPropertySheetPage.resizeScrolledComposite();
			}
		});
	}

	@Override
	public void refresh() {
		CbCDiagramTypeProvider test = new CbCDiagramTypeProvider();
		PictogramElement pe = getSelectedPictogramElement();
		Object bo = test.getFeatureProvider().getBusinessObjectForPictogramElement(pe);
		if (bo instanceof OriginalStatementImpl) {
			for (Control kid : buttonGroup.getChildren()) {
				kid.dispose();
			}
			buttons.clear();
			outputText.setText("");
			replacementText.setText("Original-Call");
			if (originalFeatureNames.isEmpty()) {
				generateButton.setEnabled(false);
				Label hint = new Label(buttonGroup, SWT.PUSH);
				hint.setText("No Features available because of smallest feature with current method");
			} else {
				generateButton.setEnabled(true);
				for (int i = 0; i < originalFeatureNames.size(); i++) {
					Button newButton = new Button(buttonGroup, SWT.CHECK);
					newButton.setText(originalFeatureNames.get(i));
					buttons.add(newButton);
				}
			}
		}
		// MethodStatement
		else if (bo instanceof MethodStatementImpl) {
			for (Control kid : buttonGroup.getChildren()) {
				kid.dispose();
			}
			buttons.clear();
			outputText.setText("");
			replacementText.setText("Method-Call");
			methodFeatureNames = featureModel.getMethodFeatureNames(((MethodStatementImpl) bo).getName());
			if (methodFeatureNames.isEmpty()) {
				generateButton.setEnabled(false);
				Label hint = new Label(buttonGroup, SWT.PUSH);
				hint.setText("No method replacement found");
			} else {
				generateButton.setEnabled(true);
				for (int i = 0; i < methodFeatureNames.size(); i++) {
					Button newButton = new Button(buttonGroup, SWT.CHECK);
					newButton.setText(methodFeatureNames.get(i));
					buttons.add(newButton);
				}
			}
		}
		// Other Elements
		else {
			buttons.clear();
			for (Control kid : buttonGroup.getChildren()) {
				kid.dispose();
			}
			generateButton.setEnabled(false);
			replacementText.setText("No valid replacement type");
			outputText.setText("");
		}
		buttonGroup.layout();
		parent.pack();
		tabbedPropertySheetPage.resizeScrolledComposite();
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
		; // ->diagramFolder->featureFolder
		String diagramName = diagramResource.getName().replaceFirst("[.][^.]+$", ""); // remove File extension
		return diagramName;
	}
}
