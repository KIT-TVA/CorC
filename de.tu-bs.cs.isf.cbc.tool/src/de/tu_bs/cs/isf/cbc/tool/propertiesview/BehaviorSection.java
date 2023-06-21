package de.tu_bs.cs.isf.cbc.tool.propertiesview;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.graphiti.ui.platform.GFPropertySection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
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

import de.tu_bs.cs.isf.cbc.tool.helper.FileHandler;

/**
 * Class for the Behavior-Tab of the Properties-View.
 * 
 * @author Fynn Demmler
 */
public class BehaviorSection extends GFPropertySection implements ITabbedPropertyConstants {
	private final int COUNTEREXAMPLE = 0;
	private final String FILENAME = "behavior";
	
	final List<Button> buttons = new ArrayList<Button>();
	// Defining the logical properties
	private Composite parent;
	private TabbedPropertySheetPage tabbedPropertySheetPage;
	
	private Device device = Display.getCurrent ();
	private Color white = new Color (device, 255, 255, 255);

	// Defining the UI properties

	private Group buttonGroup;

	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);

		this.parent = parent;
		this.tabbedPropertySheetPage = tabbedPropertySheetPage;
		TabbedPropertySheetWidgetFactory factory = getWidgetFactory();
		
		Composite composite = factory.createFlatFormComposite(parent);
		
		// Defining GridLayout for properties-view
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		gridLayout.verticalSpacing = 20;
		composite.setLayout(gridLayout);

		// Verify Behavior
		buttonGroup = new Group(composite, SWT.PUSH);
		buttonGroup.setText("Verify Behavior");
		GridLayout buttonGroupLayout = new GridLayout();
		buttonGroupLayout.numColumns = 1;
		buttonGroup.setLayout(buttonGroupLayout);
		buttonGroup.setBackground(white);
		Button newButton = new Button(buttonGroup, SWT.CHECK);
		newButton.setText("Generate Counterexamples");
		newButton.setBackground(white);
		buttons.add(newButton);
		if (Behavior.canRead()) {
			Behavior b = Behavior.read();
			newButton.setSelection(b.getCounterExamples());
		}
		newButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				Behavior b = new Behavior();
				b.setCounterExamples(newButton.getSelection());
				b.save();
				parent.pack();
				tabbedPropertySheetPage.resizeScrolledComposite();
			}
		});
		
	}

	@Override
	public void refresh() {
		if (Behavior.canRead()) {
			Behavior b = Behavior.read();
			buttons.get(COUNTEREXAMPLE).setSelection(b.getCounterExamples());
		}
	}
}

