package de.tu_bs.cs.isf.cbc.tool.properties_view;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.graphiti.ui.platform.GFPropertySection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
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

/**
 * Class for the Configurations-Tab of the Properties-View. Filter and shows
 * configurations of product-line
 * 
 * @author David
 */
public class ConfigurationsSection extends GFPropertySection implements ITabbedPropertyConstants {

	final List<Button> buttons = new ArrayList<Button>();
	// Defining the logical properties
	private String currentFeature;
	private FeatureModel featureModel;
	private List<String> featureNames = new ArrayList<String>();
	private List<String> FeatureConfigs = new ArrayList<String>();
	private String outputFormat = "";
	
	private Device device = Display.getCurrent ();
	private Color white = new Color (device, 255, 255, 255);

	// Defining the UI properties
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
		currentFeature = getCurrentFeature();
		featureModel = new FeatureModel(currentFeature);
		featureNames = featureModel.getAllFeatureNames();

		TabbedPropertySheetWidgetFactory factory = getWidgetFactory();

		Composite composite = factory.createFlatFormComposite(parent);

		// Defining GridLayout for properties-view
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayout.verticalSpacing = 20;
		composite.setLayout(gridLayout);

		// filterLabel
		filterLabel = new Label(composite, SWT.PUSH);
		filterLabel.setText("Filter: ");
		filterLabel.setBackground(white);

		// buttonGroup
		buttonGroup = new Group(composite, SWT.PUSH);
		buttonGroup.setText("Features");
		buttonGroup.setBackground(white);

		GridLayout buttonGroupLayout = new GridLayout();
		buttonGroupLayout.numColumns = 6;
		buttonGroup.setLayout(buttonGroupLayout);
		for (int i = 0; i < featureNames.size(); i++) {
			Button newButton = new Button(buttonGroup, SWT.CHECK);
			newButton.setText(featureNames.get(i));
			newButton.setBackground(white);
			if (featureNames.get(i).equals(currentFeature)) {
				newButton.setSelection(true);
			}
			buttons.add(newButton);
		}

		// generationLabel
		generationLabel = new Label(composite, SWT.PUSH);
		generationLabel.setText("Generation: ");
		generationLabel.setBackground(white);

		// buttonImage
		InputStream input = ConfigurationsSection.class.getResourceAsStream("generate16.png");
		buttonImage = new Image(null, input);

		// generateButton
		generateButton = new Button(composite, SWT.PUSH);
		generateButton.setImage(buttonImage);
		generateButton.setText("Generate");
		generateButton.setBackground(white);

		// outputLabel
		outputLabel = new Label(composite, SWT.PUSH);
		outputLabel.setText("Output: ");
		outputLabel.setBackground(white);
		
		// outputText
		outputText = new Text(composite, SWT.WRAP | SWT.PUSH | SWT.BORDER);
		GridData outputGridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		outputText.setText("");
		outputText.setLayoutData(outputGridData);
		outputText.setBackground(white);

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
				FeatureConfigs = featureModel.getConfigs(selectedFeatures);
				outputFormat = "";
				for (int d = 0; d < FeatureConfigs.size(); d++) {
					if (d == FeatureConfigs.size() - 1) {
						outputFormat = outputFormat + "Configuration<" + (d + 1) + ">:" + FeatureConfigs.get(d);
					} else {
						outputFormat = outputFormat + "Configuration<" + (d + 1) + ">:" + FeatureConfigs.get(d) + "\n";
					}
				}
				if (FeatureConfigs.isEmpty()) {
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
}
