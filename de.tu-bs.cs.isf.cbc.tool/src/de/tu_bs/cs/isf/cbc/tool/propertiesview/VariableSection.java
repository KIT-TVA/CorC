package de.tu_bs.cs.isf.cbc.tool.propertiesview;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.platform.GFPropertySection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.tool.diagram.CbCDiagramTypeProvider;

public class VariableSection extends GFPropertySection implements ITabbedPropertyConstants {

	Display display = Display.getCurrent();// for UI updating of back-process
	boolean variationalProject = false;

	// Defining the UI properties

	private Button saveButton;
	private JavaVariables variables;

	Composite composite;

	private List<JavaVariable> variablesList = new ArrayList<>();
	private List<StyledText> variablesName = new ArrayList<>();
	private List<StyledText> variablesSecurity = new ArrayList<>();
	private List<StyledText> variablesModifier = new ArrayList<>();

	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		TabbedPropertySheetWidgetFactory factory = getWidgetFactory();

		composite = factory.createFlatFormComposite(parent);

		// Defining GridLayout for properties-view
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		gridLayout.verticalSpacing = 20;
		composite.setLayout(gridLayout);
	}

	@Override
	public void refresh() {
		Color white = new Color(Display.getCurrent(), 255, 255, 255);
		CbCDiagramTypeProvider diagramProvider = new CbCDiagramTypeProvider();
		PictogramElement pe = getSelectedPictogramElement();
		Object bo = diagramProvider.getFeatureProvider().getBusinessObjectForPictogramElement(pe);
		if (bo instanceof JavaVariables) {
			variables = (JavaVariables) bo;
		}

		if (variables != null) {
			for (JavaVariable var : variables.getVariables()) {
				if (!variablesList.contains(var)) {
					variablesList.add(var);

					StyledText variableSecurity = new StyledText(composite, SWT.WRAP | SWT.PUSH | SWT.BORDER);
					GridData variableSecurityGridData = new GridData(SWT.FILL, SWT.FILL, true, false);
					variableSecurity.setEditable(true);
					variableSecurity.setLayoutData(variableSecurityGridData);
					variableSecurity.setBackground(white);
					variablesSecurity.add(variableSecurity);
					variableSecurity.setText(var.getConfidentiality() == null ? "" : var.getConfidentiality());

					StyledText variableModifier = new StyledText(composite, SWT.WRAP | SWT.PUSH | SWT.BORDER);
					GridData variableModifierGridData = new GridData(SWT.FILL, SWT.FILL, true, false);
					variableModifier.setEditable(true);
					variableModifier.setLayoutData(variableModifierGridData);
					variableModifier.setBackground(white);
					variablesModifier.add(variableModifier);
					variableModifier.setText(var.getModifier() == null ? "" : var.getModifier());

					StyledText variableName = new StyledText(composite, SWT.WRAP | SWT.PUSH | SWT.BORDER);
					GridData variableNameGridData = new GridData(SWT.FILL, SWT.FILL, true, false);
					variableName.setEditable(false);
					variableName.setLayoutData(variableNameGridData);
					variableName.setBackground(white);
					variablesName.add(variableName);
					variableName.setText(var.getName());

				}
			}
		}

		// generateButton
		if (saveButton == null) {
			saveButton = new Button(composite, SWT.PUSH);
			saveButton.setText("Save");
			saveButton.setEnabled(true);
			saveButton.addListener(SWT.Selection, new Listener() {
				@Override
				public void handleEvent(Event e) {
					TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(variables);
					domain.getCommandStack().execute(new RecordingCommand(domain) {
						@Override
						protected void doExecute() {
							for (int i = 0; i < variablesList.size(); i++) {
								changeText(i);
							}
						}
					});
				}
			});
		}

	}

	public void changeText(int index) {
		JavaVariable var = variablesList.get(index);

		String newSecurity = variablesSecurity.get(index).getText();
		String newModifier = variablesModifier.get(index).getText();

		var.setConfidentiality(newSecurity);
		var.setModifier(newModifier);

	}

}