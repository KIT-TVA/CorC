package de.tu_bs.cs.isf.cbc.tool.propertiesview;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import api.GPTAccess;
import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.tool.diagram.CbCDiagramTypeProvider;
import de.tu_bs.cs.isf.cbc.tool.helper.UpdateContractsToProve;
import de.tu_bs.cs.isf.cbc.tool.helper.UpdateMethodCallsToProve;
import de.tu_bs.cs.isf.cbc.tool.helper.UpdateOriginalCallsToProve;
import de.tu_bs.cs.isf.cbc.util.UpdateConditionsOfChildren;
import org.eclipse.graphiti.ui.platform.GFPropertySection;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;

public class AIAssistantSection extends GFPropertySection implements ITabbedPropertyConstants{
	final List<Button> buttons = new ArrayList<Button>();
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
	
	private StyledText aiText;
	private Label aiLabel;
	
	private Button generateButton;


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

		// codeText configuration
		codeText = new StyledText(composite, SWT.WRAP | SWT.PUSH | SWT.BORDER);
		GridData codeTextGridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		codeTextGridData.horizontalSpan = 2; // Span across both columns
		codeTextGridData.widthHint = 800;
		codeText.setText("Choose a code block");
		codeText.setLayoutData(codeTextGridData);
		codeText.setBackground(white);
		
		
		// generateButton
		generateButton = new Button(composite, SWT.PUSH);
		generateButton.setText("Generate");
		generateButton.setEnabled(false);
		
		// Make generateButton span across both columns
		GridData generateButtonGridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		generateButtonGridData.horizontalSpan = 2; // Span across both columns
		generateButton.setLayoutData(generateButtonGridData);
		
		aiLabel = new Label(composite, SWT.PUSH);
		aiLabel.setText("Response: ");
		aiLabel.setBackground(white);

		// aiText configuration
		aiText = new StyledText(composite, SWT.WRAP | SWT.PUSH | SWT.BORDER);
		GridData aiTextGridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		aiTextGridData.horizontalSpan = 2; // Span across both columns
		aiTextGridData.widthHint = 800;
		aiText.setText("GPTs response.");
		aiText.setLayoutData(aiTextGridData);
		aiText.setBackground(white);

		generateButton.addListener(SWT.Selection, new Listener() {
		    @Override
		    public void handleEvent(Event e) {
		        // Retrieve the text from codeText and prepare the prompt
		        String text = codeText.getText();
		        String singleLineText = text.replaceAll("\\s+", " ").trim();
		        singleLineText = singleLineText.replace("\\", "\\\\");
		        String prompt = "Could you please give me a very short description of the following condition: " + singleLineText;
		        System.out.println("Send to GPT: Could you please give me a very short description of the following condition: " + singleLineText);
		        // Initialize the GPTAccess class
		        GPTAccess gptAccess = new GPTAccess();

		        // Execute the call to getResponse in a transactional domain context
		        TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(bo);
		        domain.getCommandStack().execute(new RecordingCommand(domain) {
		            @Override
		            protected void doExecute() {
		                try {
		                    // Call getResponse and retrieve the output
		                    String response = gptAccess.getResponse(prompt);

			                // Regex to find the "content" value
		                    Pattern pattern = Pattern.compile("\"content\":\\s*\"(.*?)\"");
		                    Matcher matcher = pattern.matcher(response);

		                 // Extract and print the content
		                    if (matcher.find()) {
		                        String content = matcher.group(1); // Group 1 contains the matched content value
		                        aiText.setText(content);
		                    }
		                    System.out.println("Response from GPT: " + response);

		                    // Here you can integrate the response into the application if needed
		                    // For example, displaying it in a UI element or saving it
		                } catch (Exception ex) {
		                    // Handle exceptions, such as API call failures
		                    ex.printStackTrace();
		                }
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
		if ((bo instanceof Condition) || (bo instanceof AbstractStatement)) {
			String text = "";
			if (bo instanceof Condition) {
				text = formateCode(((Condition) bo).getName());
			} else if (bo instanceof AbstractStatement) {
				text = generateCode2(((AbstractStatement) bo).getName());
			}
			codeText.setText(text);
			codeText.setEditable(true);
			generateButton.setEnabled(true);
			styleText(codeText, display);
			parent.pack();
			tabbedPropertySheetPage.resizeScrolledComposite();
		} else {
			codeText.setText("Choose a code block");
			codeText.setEditable(false);
			generateButton.setEnabled(false);
			parent.pack();
			tabbedPropertySheetPage.resizeScrolledComposite();
		}
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
