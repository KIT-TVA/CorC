package de.tu_bs.cs.isf.cbc.tool.propertiesview;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.graphiti.ui.platform.GFPropertySection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
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

import de.tu_bs.cs.isf.cbc.tool.exceptions.SettingsException;
import de.tu_bs.cs.isf.cbc.tool.helper.FileHandler;

/**
 * Class for the Behavior-Tab of the Properties-View.
 * 
 * @author Fynn Demmler
 */
public class SettingsSection extends GFPropertySection implements ITabbedPropertyConstants {
	private final int COUNTEREXAMPLE = 0;
	
	final List<Button> buttons = new ArrayList<Button>();
	// Defining the logical properties
	private Composite parent;
	private TabbedPropertySheetPage tabbedPropertySheetPage;
	
	private Device device = Display.getCurrent ();
	private Color white = new Color (device, 255, 255, 255);
	
	private final int NUM_GROUPS = 2;
	private final String CEX_BUTTON_NAME = "Generate Counterexamples";
	private final String TWARN_BUTTON_NAME = "Show Warnings";
	

	// Defining the UI properties

	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);

		this.parent = parent;
		this.tabbedPropertySheetPage = tabbedPropertySheetPage;
		TabbedPropertySheetWidgetFactory factory = getWidgetFactory();
		
		Composite composite = factory.createFlatFormComposite(parent);
		
		// Defining GridLayout for properties-view
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = NUM_GROUPS;
		gridLayout.verticalSpacing = 5;
		composite.setLayout(gridLayout);
		
		// Verify Settings
		Group verifyGroup = createButtonGroup(composite, "Verify Settings");
		var counterButton = createButton(verifyGroup, CEX_BUTTON_NAME);
		
		// Test Settings
		Group testGroup = createButtonGroup(composite, "Test Settings");
		testGroup.setLayoutData(new GridData(SWT.FILL, SWT.RIGHT, false, false));
		var warningButton = createButton(testGroup, TWARN_BUTTON_NAME);
		
		try {
			readSettings();
		} catch (SettingsException e) {
			e.printStackTrace();
		}
		addListeners();	
	}
	
	private Group createButtonGroup(Composite parent, String name) {
		Group buttonGroup = new Group(parent, SWT.PUSH);
		buttonGroup.setText(name);
		GridLayout buttonGroupLayout = new GridLayout();
		buttonGroupLayout.numColumns = 1;
		buttonGroup.setLayout(buttonGroupLayout);
		buttonGroup.setBackground(white);
		return buttonGroup;
	}
	
	private Button createButton(Group buttonGroup, String name) {
		Button newButton = new Button(buttonGroup, SWT.CHECK);
		newButton.setText(name);
		newButton.setBackground(white);
		buttons.add(newButton);
		return newButton;
	}
	
	private void addListeners() {
		for (var button : this.buttons) {
			button.addListener(SWT.Selection, new Listener() {
				@Override
				public void handleEvent(Event e) {
					Settings b;
					try {
						b = Settings.get();
					} catch (SettingsException e1) {
						e1.printStackTrace();
						return;
					}
					if (button.getText().equals(CEX_BUTTON_NAME)) {
						b.setCounterExamples(button.getSelection());
					} else if (button.getText().equals(TWARN_BUTTON_NAME)) {
						b.setTestWarnings(button.getSelection());
					}
					b.save();
				}
			});
		}
	}
	
	private void readSettings() throws SettingsException {
		if (Settings.canRead()) {
			Settings b = Settings.get();
			for (var button : this.buttons) {
				if (button.getText().equals(CEX_BUTTON_NAME)) {
					button.setSelection(b.getCounterExamples());
				} else if (button.getText().equals(TWARN_BUTTON_NAME)) {
					button.setSelection(b.testWarningsEnabled());
				}
			}
		}
	}

	@Override
	public void refresh() {
		if (Settings.canRead()) {
			Settings b;
			try {
				b = Settings.get();
			} catch (SettingsException e) {
				e.printStackTrace();
				return;
			}
			for (var button : this.buttons) {
				if (button.getText().equals(CEX_BUTTON_NAME)) {
					button.setSelection(b.getCounterExamples());
				} else if (button.getText().equals(TWARN_BUTTON_NAME)) {
					button.setSelection(b.testWarningsEnabled());
				}
			}
		}
	}
}

