package de.tu_bs.cs.isf.cbc.tool.propertiesview;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.platform.GFPropertySection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
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
import org.eclipse.swt.widgets.List;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.tool.diagram.CbCDiagramTypeProvider;
import de.tu_bs.cs.isf.cbc.tool.helper.Predicate;
import de.tu_bs.cs.isf.cbc.tool.helper.PredicateDefinition;
import de.tu_bs.cs.isf.cbc.tool.helper.UpdateConditionsOfChildren;
import de.tu_bs.cs.isf.cbc.tool.helper.UpdateContractsToProve;
import de.tu_bs.cs.isf.cbc.tool.helper.UpdateMethodCallsToProve;
import de.tu_bs.cs.isf.cbc.tool.helper.UpdateOriginalCallsToProve;
import de.tu_bs.cs.isf.cbc.util.FileUtil;

/**
 * Class for the Code-Reader-Tab of the Properties-View. Formats CorC-Code to
 * formatted, editable code and translates it back to CorC-Diagram.
 * 
 * @author David
 */
public class CodeReaderSection extends GFPropertySection implements ITabbedPropertyConstants {

	// Defining the logical properties
	private Composite parent;
	private TabbedPropertySheetPage tabbedPropertySheetPage;
	private Display display;
	
	private Device device = Display.getCurrent ();
	private Color white = new Color (device, 255, 255, 255);

	private Object bo;
	
	// Defining the UI properties
	private StyledText codeText;
	private Label codeLabel;

	private Label predicateLabel;
	private static List predicatesList;	
	
	private Label actionLabel;
	private Button saveButton;


	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		this.parent = parent;
		this.tabbedPropertySheetPage = tabbedPropertySheetPage;

		TabbedPropertySheetWidgetFactory factory = getWidgetFactory();

		Composite composite = factory.createFlatFormComposite(parent);

		display = Display.getCurrent();

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayout.verticalSpacing = 20;
		composite.setLayout(gridLayout);

		codeLabel = new Label(composite, SWT.PUSH);
		codeLabel.setText("Code: ");
		codeLabel.setBackground(white);

		codeText = new StyledText(composite, SWT.WRAP | SWT.PUSH | SWT.BORDER);
		GridData outputGridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		outputGridData.widthHint = 800;
		codeText.setText("Choose a code block");
		codeText.setLayoutData(outputGridData);
		codeText.setBackground(white);
		
		predicateLabel = new Label(composite, SWT.PUSH);
		predicateLabel.setText("Available predicates: ");
		predicateLabel.setBackground(white);
		
		GridData gridData_list_predicates = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_FILL);
		
		gridData_list_predicates.horizontalAlignment = GridData.FILL;
		predicatesList = new List(composite, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		updateList();
		gridData_list_predicates.heightHint = predicatesList.computeTrim(0, 0, 0, predicatesList.getItemHeight() * 5).height;
		predicatesList.setLayoutData(gridData_list_predicates);

		actionLabel = new Label(composite, SWT.PUSH);
		actionLabel.setText("Action: ");
		actionLabel.setBackground(white);
		
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
						String text = codeText.getText();
						text = text.replaceAll("\n", "");
						text = text.replaceAll("\t", "");
						if (bo instanceof Condition) {
							((Condition) bo).setName(text);
							UpdateConditionsOfChildren.updateConditionsofChildren((Condition) bo);
							UpdateOriginalCallsToProve.updateOriginalCallsToProve((Condition) bo); //varcorcXFeatureIDE
							UpdateMethodCallsToProve.updateMethodCallsToProve((Condition) bo);//varcorcXFeatureIDE
							UpdateContractsToProve.updateContractsToProve((Condition) bo);//varcorcXFeatureIDE
						} else if (bo instanceof AbstractStatement) {
							((AbstractStatement) bo).setName(text);
							((AbstractStatement) bo).setProven(false);
							;
						}
					}
				});
			}
		});
	}

	private void updateList() {
		if (bo == null) return;
		AbstractStatement stmt = null;
		if (bo instanceof Condition) {
			Condition cond = ((Condition) bo);
			if (cond.eContainer() instanceof GlobalConditions) return; 
			stmt = (AbstractStatement) cond.eContainer();
		} else if (bo instanceof AbstractStatement) {
			stmt = (AbstractStatement) bo;
		} else if (bo instanceof CbCFormula) {
			stmt = ((CbCFormula) bo).getStatement();
		} else {
			predicatesList.removeAll();
			predicatesList.add("Select a condition");
			return;
		}
		while (!(stmt.eContainer() instanceof CbCFormula)) {
			stmt = (AbstractStatement) stmt.eContainer();
		}
		FileUtil fu = new FileUtil(stmt.eResource().getURI().toPlatformString(true));
		CbCFormula formula = (CbCFormula) stmt.eContainer();
		String path = fu.getLocationString(stmt.eResource().getURI().toPlatformString(true));
		if (formula.getMethodObj() == null || formula.getMethodObj().getParentClass() == null || formula.getMethodObj().getParentClass().getFeature() == null || formula.getMethodObj().getParentClass().getFeature().equals("default")) {
			path = path.substring(0, path.indexOf("/src/")) + "/predicates.def";
		} else {
			path = path.substring(0, path.indexOf("/features/")) + "/predicates.def";
		}
		java.util.List<Predicate> readPredicates = fu.readPredicates(path);
		java.util.List<String> availablePreds = new java.util.ArrayList<String>();
		if (readPredicates != null) {
			for (Predicate p : readPredicates) {
				for (PredicateDefinition pDef : p.definitions) {
					availablePreds.add(p.getSignature(true) + (p.definitions.size() > 1 ? (": " + pDef.name) : ""));
				}
			}
		}
		String[] items = new String[availablePreds.size()];
		for (int i = 0; i < availablePreds.size(); i++) {
			items[i] = availablePreds.get(i);
		}
		predicatesList.setItems(items);
	}

	@Override
	public void refresh() {
		CbCDiagramTypeProvider test = new CbCDiagramTypeProvider();
		PictogramElement pe = getSelectedPictogramElement();
		bo = test.getFeatureProvider().getBusinessObjectForPictogramElement(pe);
		if ((bo instanceof Condition) || (bo instanceof AbstractStatement)) {
			String text = "";
			if (bo instanceof Condition) {
				text = formateCode(((Condition) bo).getName());
			} else if (bo instanceof AbstractStatement) {
				text = generateCode2(((AbstractStatement) bo).getName());
			}
			codeText.setText(text);
			codeText.setEditable(true);
			saveButton.setEnabled(true);
			styleText(codeText, display);
			parent.pack();
			tabbedPropertySheetPage.resizeScrolledComposite();
		} else {
			codeText.setText("Choose a code block");
			codeText.setEditable(false);
			saveButton.setEnabled(false);
			parent.pack();
			tabbedPropertySheetPage.resizeScrolledComposite();
		}
		updateList();
	}

	/**
	 * Method for highlighting of key-words.
	 */
	public static void styleText(StyledText codeText, Display display) {
		String[] lines = codeText.getText().split("\\n");
		String keyWord1 = "forall";
		String keyWord2 = "exists";
		Color blue = display.getSystemColor(SWT.COLOR_BLUE);
		Color green = display.getSystemColor(SWT.COLOR_DARK_GREEN);
		Color black = display.getSystemColor(SWT.COLOR_BLACK);
		int offset = 0;
		for (String line : lines) {
			int index1 = line.indexOf(keyWord1);
			int index2 = line.indexOf(keyWord2);
			if (index1 != -1) {
				StyleRange range = new StyleRange(index1 + offset, keyWord1.length(), blue, null, SWT.BOLD);
				codeText.setStyleRange(range);
			}
			if (index2 != -1) {
				StyleRange range = new StyleRange(index2 + offset, keyWord2.length(), green, null, SWT.BOLD);
				codeText.setStyleRange(range);
			}
			// The next two for loops are for marking ->,&,| if they are first or last char
			boolean firstChar = true;
			for (int i = 0; i < line.length(); i++) {
				if (line.charAt(i) == '&' || line.charAt(i) == '|') {
					if (firstChar) {
						StyleRange range = new StyleRange(i + offset, 1, black, null, SWT.BOLD);
						codeText.setStyleRange(range);
					}
				} else if (line.charAt(i) == '-' && line.charAt(i + 1) == '>') {
					if (firstChar) {
						StyleRange range = new StyleRange(i + offset, 2, black, null, SWT.BOLD);
						codeText.setStyleRange(range);
					}
				} else if (line.charAt(i) == ' ') {
				} else {
					firstChar = false;
				}
			}
			boolean lastChar = true;
			for (int i = line.length() - 1; i > 0; i--) {
				if (line.charAt(i) == '&' || line.charAt(i) == '|') {
					if (lastChar) {
						StyleRange range = new StyleRange(i + offset, 1, black, null, SWT.BOLD);
						codeText.setStyleRange(range);
					}
				} else if (line.charAt(i) == '>' && line.charAt(i - 1) == '-') {
					if (lastChar) {
						StyleRange range = new StyleRange(i - 1 + offset, 2, black, null, SWT.BOLD);
						codeText.setStyleRange(range);
					}
				} else if (line.charAt(i) == ' ') {

				} else {
					lastChar = false;
				}
			}
			offset += line.length() + 1; // +1 for the newline character
		}
	}

	/**
	 * Method to format condition-code.
	 */
	public static String formateCode(String unformatted) {
		unformatted = unformatted.replaceAll("\n", "");
		unformatted = unformatted.replaceAll("\r", "");
		String formatted = "";
		int bracketCounter = 0;
		// flag for tracking last char != '(' || ')'
		boolean noBracket = false;
		// flag for tracking last char == ')'
		boolean closeBracket = false;
		for (int i = 0; i < unformatted.length(); i++) {
			if (unformatted.charAt(i) == '(') {
				// To avoid newline before first char
				if (i != 0) {
					formatted += "\n";
				}
				for (int j = 0; j < bracketCounter; j++) {
					formatted += "\t";
				}
				formatted += "(";
				bracketCounter++;
			} else if (unformatted.charAt(i) == ')') {
				closeBracket = true;
				if (noBracket == true) {
					formatted += "\n";
				}
				bracketCounter--;
				for (int j = 0; j < bracketCounter; j++) {
					formatted += "\t";
				}
				formatted += ")";
				// To avoid newline after last char
				if (i != unformatted.length() - 1) {
					formatted += "\n";
				}
				noBracket = false;
			} else {
				noBracket = true;
				if (closeBracket == true) {
					for (int j = 0; j < bracketCounter; j++) {
						formatted += "\t";
					}
					formatted += unformatted.charAt(i);
				} else {
					formatted += unformatted.charAt(i);
				}
				closeBracket = false;
			}
		}
		return formatted;
	}

	/**
	 * Method to format statement-code.
	 */
	public static String generateCode2(String unformatted) {
		String formatted = "";
		unformatted = unformatted.replaceAll("\n", "");
		unformatted = unformatted.replaceAll("\r", "");
		for (int i = 0; i < unformatted.length(); i++) {
			if (unformatted.charAt(i) == ';') {
				formatted += unformatted.charAt(i);
				if (i != unformatted.length() - 1) {
					formatted = formatted + "\n";
				}
			} else {
				formatted = formatted + unformatted.charAt(i);
			}
		}
		return formatted;
	}
}
