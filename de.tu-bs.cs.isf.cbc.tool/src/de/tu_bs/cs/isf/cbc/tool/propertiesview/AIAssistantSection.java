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
	private Button responseButton;
	
	private String lastAiResponse = ""; // Store the last AI response
	private String lastCodeText = ""; // Tracks last code input


	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
	    super.createControls(parent, tabbedPropertySheetPage);
	    this.parent = parent;
	    this.tabbedPropertySheetPage = tabbedPropertySheetPage;

	    TabbedPropertySheetWidgetFactory factory = getWidgetFactory();
	    Composite mainComposite = factory.createFlatFormComposite(parent);
	    display = Display.getCurrent();

	    // Set main layout: Two equally sized columns
	    GridLayout mainLayout = new GridLayout(2, true);
	    mainLayout.verticalSpacing = 10;
	    mainComposite.setLayout(mainLayout);

	    // ==== LEFT GROUP (Code & Response) ====
	    Composite leftGroup = new Composite(mainComposite, SWT.BORDER);
	    leftGroup.setLayout(new GridLayout(2, false));
	    GridData leftGroupData = new GridData(SWT.FILL, SWT.FILL, true, true);
	    leftGroupData.widthHint = 400; // Adjusted to smaller width
	    leftGroup.setLayoutData(leftGroupData);

	    codeLabel = new Label(leftGroup, SWT.NONE);
	    codeLabel.setText("Code: ");

	    codeText = new StyledText(leftGroup, SWT.WRAP | SWT.PUSH | SWT.BORDER);
	    GridData codeTextGridData = new GridData(SWT.FILL, SWT.FILL, true, false);
	    codeTextGridData.horizontalSpan = 2;
	    codeTextGridData.widthHint = 500; // Reduced width
	    codeText.setText("Choose a code block");
	    codeText.setLayoutData(codeTextGridData);
	    codeText.setBackground(white);

	    // Generate Button
	    generateButton = new Button(leftGroup, SWT.PUSH);
	    generateButton.setText("Generate");
	    generateButton.setEnabled(false);
	    GridData generateButtonGridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
	    generateButtonGridData.horizontalSpan = 2;
	    generateButton.setLayoutData(generateButtonGridData);

	    // AI Response Label (Now below the Generate button)
	    aiLabel = new Label(leftGroup, SWT.NONE);
	    aiLabel.setText("Response:");
	    aiLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));

	    // AI Response Field (Read-Only)
	    aiText = new StyledText(leftGroup, SWT.WRAP | SWT.PUSH | SWT.BORDER | SWT.READ_ONLY);
	    GridData aiTextGridData = new GridData(SWT.FILL, SWT.FILL, true, false);
	    aiTextGridData.horizontalSpan = 2;
	    aiTextGridData.widthHint = 500;
	    aiTextGridData.heightHint = 80;
	    aiText.setText("GPT's response.");
	    aiText.setLayoutData(aiTextGridData);
	    aiText.setBackground(white);

	    // ==== RIGHT GROUP (Questions & Responses) ====
	    Composite rightGroup = new Composite(mainComposite, SWT.BORDER);
	    rightGroup.setLayout(new GridLayout(2, false));
	    GridData rightGroupData = new GridData(SWT.FILL, SWT.FILL, true, true);
	    rightGroupData.widthHint = 400; // Adjusted to smaller width
	    rightGroup.setLayoutData(rightGroupData);

	    // Question Label
	    Label questionLabel = new Label(rightGroup, SWT.NONE);
	    questionLabel.setText("Questions:");
	    questionLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));

	    // Question Text Field
	    StyledText questionText = new StyledText(rightGroup, SWT.WRAP | SWT.PUSH | SWT.BORDER);
	    GridData questionTextGridData = new GridData(SWT.FILL, SWT.FILL, true, false);
	    questionTextGridData.horizontalSpan = 2;
	    questionTextGridData.widthHint = 500;
	    questionTextGridData.heightHint = 80;
	    questionText.setText("Write your question here...");
	    questionText.setLayoutData(questionTextGridData);
	    questionText.setEditable(true);

	    // Checkbox for specification relevance
	    Button specificationCheckbox = new Button(rightGroup, SWT.CHECK);
	    specificationCheckbox.setText("Question relates to the specification");
	    GridData checkboxGridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
	    checkboxGridData.horizontalSpan = 2;
	    specificationCheckbox.setLayoutData(checkboxGridData);

	    // Answer Button
	    Button rightAnswerButton = new Button(rightGroup, SWT.PUSH);
	    rightAnswerButton.setText("Answer");
	    rightAnswerButton.setEnabled(true);
	    GridData rightAnswerButtonGridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
	    rightAnswerButtonGridData.horizontalSpan = 2;
	    rightAnswerButton.setLayoutData(rightAnswerButtonGridData);

	    // Question Response Label (Now below the Answer button)
	    Label questionResponseLabel = new Label(rightGroup, SWT.NONE);
	    questionResponseLabel.setText("Question Response:");
	    questionResponseLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));

	    // Question Response Text Field (Read-Only)
	    StyledText questionResponseText = new StyledText(rightGroup, SWT.WRAP | SWT.PUSH | SWT.BORDER | SWT.READ_ONLY);
	    GridData questionResponseTextGridData = new GridData(SWT.FILL, SWT.FILL, true, false);
	    questionResponseTextGridData.horizontalSpan = 2;
	    questionResponseTextGridData.widthHint = 500;
	    questionResponseTextGridData.heightHint = 80;
	    questionResponseText.setText("Response to your question...");
	    questionResponseText.setLayoutData(questionResponseTextGridData);
	    questionResponseText.setBackground(white);

	    // === Button Listeners ===
	    generateButton.addListener(SWT.Selection, event -> sendGPTRequest(codeText.getText(), aiText, false, false));

	    rightAnswerButton.addListener(SWT.Selection, event -> {
	        boolean includeSpecification = specificationCheckbox.getSelection();
	        sendGPTRequest(questionText.getText(), questionResponseText, includeSpecification, true);
	    });
	}


	/**
	 * Handles AI requests for both generating responses and answering user questions.
	 *
	 * @param inputText        The text input from the user.
	 * @param outputField      The text field where the AI response should be displayed.
	 * @param includeSpec      Whether to include the previous AI response (for follow-ups).
	 * @param isFollowUp       If the request is a follow-up or a new question.
	 */
	private void sendGPTRequest(String inputText, StyledText outputField, boolean includeSpec, boolean isFollowUp) {
	    String prompt;

	    if (isFollowUp && includeSpec) {
	        // Follow-up question including previous response
	        prompt = "Follow-up question based on the previous AI response: " + aiText.getText() + 
	                 "\nUser question: " + inputText + 
	                 "\nAnswer concisely.";
	    } else if (isFollowUp) {
	        // Follow-up question without context
	        prompt = "User question: " + inputText + 
	                 "\nAnswer concisely.";
	    } else {
	        // First-time request (for specification-related input)
	        prompt = "In one sentence explain the following formal specification: " + inputText;
	    }

	    GPTAccess gptAccess = new GPTAccess();
	    TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(bo);

	    domain.getCommandStack().execute(new RecordingCommand(domain) {
	        @Override
	        protected void doExecute() {
	            try {
	                String safePrompt = prompt
	                        .replace("\\", "\\\\")  // Escape backslashes
	                        .replace("\"", "\\\"")  // Escape double quotes
	                        .replace("\n", "\\n")   // Escape new lines properly
	                        .replace("\r", "")      // Remove carriage returns (if any)
	                        .replace("\t", " ")     // Replace tabs with a space
	                        .replace("≤", "<=")     // Normalize mathematical symbols
	                        .replace("≥", ">=")     // Normalize mathematical symbols
	                        .replace("→", "->")     // Normalize logical arrows
	                        .replace("∀", "\\forall") // Preserve universal quantifier
	                        .replace("∃", "\\exists"); // Preserve existential quantifier

	                String response = gptAccess.getResponse(safePrompt);

	                // Extract AI response
	                Pattern pattern = Pattern.compile("\"content\":\\s*\"(.*?)\"");
	                Matcher matcher = pattern.matcher(response);
	                if (matcher.find()) {
	                    String aiResponse = matcher.group(1).replace("\\n", " ");
	                    outputField.setText(aiResponse);
	                }
	            } catch (Exception ex) {
	                ex.printStackTrace();
	                outputField.setText("Error communicating with GPT.");
	            }
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
