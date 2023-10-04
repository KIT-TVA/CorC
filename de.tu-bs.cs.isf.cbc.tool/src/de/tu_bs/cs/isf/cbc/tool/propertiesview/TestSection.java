package de.tu_bs.cs.isf.cbc.tool.propertiesview;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.graphiti.ui.platform.GFPropertySection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import de.tu_bs.cs.isf.cbc.exceptions.SettingsException;

public class TestSection extends GFPropertySection implements ITabbedPropertyConstants {	
	final List<Button> buttons = new ArrayList<Button>();
	// Defining the logical properties
	private Composite parent;
	private TabbedPropertySheetPage tabbedPropertySheetPage;
	
	private Device device = Display.getCurrent ();
	private Color white = new Color (device, 255, 255, 255);
	
	private final int NUM_GROUPS = 2;
	private StyledText byteText;
	private StyledText booleanText;
	private StyledText shortText;
	private StyledText intText;
	private StyledText longText;
	private StyledText charText;
	private StyledText strText;
	

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
		
		byteText = createLabeledTextbox(composite, "Byte");
		booleanText = createLabeledTextbox(composite, "Boolean");
		shortText = createLabeledTextbox(composite, "Short");
		intText = createLabeledTextbox(composite, "Integer");
		longText = createLabeledTextbox(composite, "Long");
		charText = createLabeledTextbox(composite, "Char");
		strText = createLabeledTextbox(composite, "String");

		TestValues values;
		if (!TestValues.canRead()) {
			TestValues.useDefaults();
		}
		try {
			values = TestValues.get();
			byteText.setText(values.getByteStr());
			booleanText.setText(values.getBooleanStr());
			shortText.setText(values.getShortStr());
			intText.setText(values.getIntStr());
			longText.setText(values.getLongStr());
			charText.setText(values.getCharStr());
			strText.setText(values.getStringStr());	
		} catch (SettingsException e1) {
			e1.printStackTrace();
		}
		var saveButton = createButton(composite, "Save");
		saveButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				try {
					var values = TestValues.get();
					values.setByteStr(byteText.getText());
					values.setBooleanStr(booleanText.getText());
					values.setShortStr(shortText.getText());
					values.setIntStr(intText.getText());
					values.setLongStr(longText.getText());
					values.setCharStr(charText.getText());
					values.setStringStr(strText.getText());
					byteText.setText(values.getByteStr());
					booleanText.setText(values.getBooleanStr());
					shortText.setText(values.getShortStr());
					intText.setText(values.getIntStr());
					longText.setText(values.getLongStr());
					charText.setText(values.getCharStr());
					strText.setText(values.getStringStr());
					values.save();
				} catch (SettingsException e1) {
					e1.printStackTrace();
				}
			}
		}
		);
	}
	
	private StyledText createLabeledTextbox(Composite parent, String labelName) {
		// methodSignatureLabel
		var label = new Label(parent, SWT.PUSH);
		label.setText(labelName + ": ");
		label.setBackground(white);
		
		// methodSignatureLabelText
		var textbox = new StyledText(parent, SWT.WRAP | SWT.PUSH | SWT.BORDER);
		GridData methodSignatureLabelTextGridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		textbox.setEditable(true);
		textbox.setLayoutData(methodSignatureLabelTextGridData);
		textbox.setBackground(white);
		return textbox;
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
	
	private Button createButton(Composite buttonGroup, String name) {
		Button newButton = new Button(buttonGroup, SWT.PUSH);
		newButton.setText(name);
		newButton.setBackground(white);
		buttons.add(newButton);
		return newButton;
	}
	
	@Override
	public void refresh() {
		if (TestValues.canRead()) {
			TestValues values;
			try {
				values = TestValues.get();
				this.byteText.setText(values.getByteStr());
				this.shortText.setText(values.getShortStr());
				this.intText.setText(values.getIntStr());
				this.longText.setText(values.getLongStr());
				this.charText.setText(values.getCharStr());
				this.strText.setText(values.getStringStr());
				this.booleanText.setText(values.getBooleanStr());
			} catch (SettingsException e) {
				e.printStackTrace();
				return;
			}
		}
	}
}
