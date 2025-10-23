package de.tu_bs.cs.isf.cbc.tool.propertiesview;

import java.util.stream.Collectors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.platform.GFPropertySection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import api.GrokAccess;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;

public class GrokAssistantSection extends GFPropertySection implements ITabbedPropertyConstants {

    private CbCFormula rootFormula; 
    private StyledText responseText;
    private TabbedPropertySheetPage tabbedPropertySheetPage;

    private StyledText zeroShotRoleText, zeroShotContextText, zeroShotTaskText, zeroShotOutputFormatText;
    
    private StyledText fewShotRoleText, fewShotExampleTaskText, fewShotGroundTruthText, fewShotContextText, fewShotTaskText, fewShotOutputFormatText;
    
    private StyledText cotRoleText, cotContextText, cotMainGoalText, cotOutputFormatText;
    private Text cotStep1Title, cotStep2Title, cotStep3Title;
    private StyledText cotStep1Instructions, cotStep2Instructions, cotStep3Instructions;
    
    private StyledText srRoleText, srContextText, srTaskText, srOutputFormatText, srFeedbackText;
    private Group srFeedbackGroup;
    
    private Composite techniquePanelParent;
    private StackLayout stackLayout;
    
    private Composite zeroShotComposite, fewShotComposite, cotComposite, selfRefinementComposite;

    @Override
    public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
    	
        super.createControls(parent, tabbedPropertySheetPage);
        this.tabbedPropertySheetPage = tabbedPropertySheetPage;

        TabbedPropertySheetWidgetFactory factory = getWidgetFactory();
        Composite mainComposite = factory.createFlatFormComposite(parent);
        mainComposite.setLayout(new GridLayout(1, false));

        Group techniqueSelectionGroup = new Group(mainComposite, SWT.NONE);
        techniqueSelectionGroup.setText("Select Prompt Engineering Technique");
        techniqueSelectionGroup.setLayout(new GridLayout(4, true));
        techniqueSelectionGroup.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
        
        GridData radioGridData = new GridData(SWT.FILL, SWT.CENTER, true, false);

        Button zeroShotRadio = new Button(techniqueSelectionGroup, SWT.RADIO);
        zeroShotRadio.setText("Zero-Shot");
        zeroShotRadio.setSelection(true);
        zeroShotRadio.setLayoutData(radioGridData);

        Button fewShotRadio = new Button(techniqueSelectionGroup, SWT.RADIO);
        fewShotRadio.setText("Few-Shot");
        fewShotRadio.setEnabled(true);
        fewShotRadio.setLayoutData(radioGridData);

        Button cotRadio = new Button(techniqueSelectionGroup, SWT.RADIO);
        cotRadio.setText("Chain-of-Thought");
        cotRadio.setEnabled(true);
        cotRadio.setLayoutData(radioGridData);
        
        Button selfRefineRadio = new Button(techniqueSelectionGroup, SWT.RADIO);
        selfRefineRadio.setText("Self-Refinement");
        selfRefineRadio.setEnabled(true);
        selfRefineRadio.setLayoutData(radioGridData);

        techniquePanelParent = new Composite(mainComposite, SWT.NONE);
        stackLayout = new StackLayout();
        techniquePanelParent.setLayout(stackLayout);
        techniquePanelParent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        zeroShotComposite = createZeroShotControls(techniquePanelParent, factory);
        fewShotComposite = createFewShotControls(techniquePanelParent, factory);
        cotComposite = createCoTControls(techniquePanelParent, factory);
        selfRefinementComposite = createSelfRefinementControls(techniquePanelParent, factory);
        
        stackLayout.topControl = zeroShotComposite;
        techniquePanelParent.layout();
        
        Label responseLabel = new Label(mainComposite, SWT.NONE);
        responseLabel.setText("Response of Grok:");
        
        Device device = Display.getCurrent();
        Color white = new Color(device, 255, 255, 255);
        responseText = new StyledText(mainComposite, SWT.WRAP | SWT.BORDER | SWT.READ_ONLY | SWT.V_SCROLL);
        GridData responseTextGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
        responseTextGridData.heightHint = 200;
        responseText.setLayoutData(responseTextGridData);
        responseText.setBackground(white);
        
        zeroShotRadio.addListener(SWT.Selection, e -> { if (zeroShotRadio.getSelection()) switchPanel(zeroShotComposite); });
        fewShotRadio.addListener(SWT.Selection, e -> { if (fewShotRadio.getSelection()) switchPanel(fewShotComposite); });
        cotRadio.addListener(SWT.Selection, e -> { if (cotRadio.getSelection()) switchPanel(cotComposite); });
        selfRefineRadio.addListener(SWT.Selection, e -> { if (selfRefineRadio.getSelection()) switchPanel(selfRefinementComposite); });
    }
    
    private void switchPanel(Composite panel) {
        if (srFeedbackGroup != null && !srFeedbackGroup.isDisposed()) {
            srFeedbackGroup.setVisible(false);
            ((GridData) srFeedbackGroup.getLayoutData()).exclude = true;
        }
        stackLayout.topControl = panel;
        techniquePanelParent.layout();
    }

    private Composite createSelfRefinementControls(Composite parent, TabbedPropertySheetWidgetFactory factory) {
        Composite composite = factory.createFlatFormComposite(parent);
        composite.setLayout(new GridLayout(1, false));
        
        Group initialGroup = new Group(composite, SWT.NONE);
        initialGroup.setText("Step 1: Initial Generation");
        initialGroup.setLayout(new GridLayout(1, false));
        initialGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        Label roleLabel = factory.createLabel(initialGroup, "Role (Persona):");
        srRoleText = new StyledText(initialGroup, SWT.WRAP | SWT.BORDER);
        srRoleText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        srRoleText.setText("You are an expert in formal methods, Correctness-by-Construction (CbC), and the Java Modeling Language (JML).");

        Label contextLabel = factory.createLabel(initialGroup, "Context:");
        srContextText = new StyledText(initialGroup, SWT.WRAP | SWT.BORDER | SWT.V_SCROLL);
        srContextText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        
        Label taskLabel = factory.createLabel(initialGroup, "Task:");
        srTaskText = new StyledText(initialGroup, SWT.WRAP | SWT.BORDER | SWT.V_SCROLL);
        srTaskText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        
        Label outputFormatLabel = factory.createLabel(initialGroup, "Output Format:");
        srOutputFormatText = new StyledText(initialGroup, SWT.WRAP | SWT.BORDER);
        srOutputFormatText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        srOutputFormatText.setText("Write only the complete JML comment block.");
        
        Button generateInitialButton = factory.createButton(initialGroup, "Generate Initial Version", SWT.PUSH);
        generateInitialButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        srFeedbackGroup = new Group(composite, SWT.NONE);
        srFeedbackGroup.setText("Step 2: Provide Feedback");
        srFeedbackGroup.setLayout(new GridLayout(1, false));
        GridData feedbackGroupData = new GridData(SWT.FILL, SWT.FILL, true, true);
        feedbackGroupData.exclude = true;
        srFeedbackGroup.setLayoutData(feedbackGroupData);
        srFeedbackGroup.setVisible(false);

        Label feedbackLabel = factory.createLabel(srFeedbackGroup, "Your Feedback & Refinement Instructions:");
        srFeedbackText = new StyledText(srFeedbackGroup, SWT.WRAP | SWT.BORDER | SWT.V_SCROLL);
        srFeedbackText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        srFeedbackText.setText("Review the JML specification above. Is the 'assignable' clause missing or incorrect? Are all boundary conditions handled correctly? Can this specification be made more precise or complete?");

        Button refineButton = factory.createButton(srFeedbackGroup, "Refine", SWT.PUSH);
        refineButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        
        generateInitialButton.addListener(SWT.Selection, e -> {
            String initialPrompt = "[Role]\n" + srRoleText.getText() + "\n\n" +
                                   "[Context]\n" + srContextText.getText() + "\n\n" +
                                   "[Task]\n" + srTaskText.getText() + "\n\n" +
                                   "[Output Format]\n" + srOutputFormatText.getText();
            
            executeGrokRequest(initialPrompt, () -> {
                srFeedbackGroup.setVisible(true);
                ((GridData) srFeedbackGroup.getLayoutData()).exclude = false;
                composite.layout();
            });
        });
        
        refineButton.addListener(SWT.Selection, e -> {
            String previousResponse = responseText.getText();
            String feedback = srFeedbackText.getText();
            String refinementPrompt = "You previously generated the following JML specification:\n---\n" + previousResponse + "\n---\n" +
                                      "Now, critically review and improve it based on the following feedback:\n" + feedback +
                                      "\nProvide only the final, improved, and complete JML block.";
            
            executeGrokRequest(refinementPrompt, null);
        });
        
        return composite;
    }
    
    private Composite createCoTControls(Composite parent, TabbedPropertySheetWidgetFactory factory) {
        Composite composite = factory.createFlatFormComposite(parent);
        composite.setLayout(new GridLayout(1, false));

        Label roleLabel = factory.createLabel(composite, "Role (Persona):");
        cotRoleText = new StyledText(composite, SWT.WRAP | SWT.BORDER);
        cotRoleText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        cotRoleText.setText("You are an expert in formal methods, Correctness-by-Construction (CbC), and the Java Modeling Language (JML).");

        Label contextLabel = factory.createLabel(composite, "Main Task Context:");
        cotContextText = new StyledText(composite, SWT.WRAP | SWT.BORDER | SWT.V_SCROLL);
        cotContextText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        Group taskGroup = new Group(composite, SWT.NONE);
        taskGroup.setText("Task Definition");
        taskGroup.setLayout(new GridLayout(1, false));
        taskGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        Label mainGoalLabel = factory.createLabel(taskGroup, "Main Goal:");
        cotMainGoalText = new StyledText(taskGroup, SWT.WRAP | SWT.BORDER);
        cotMainGoalText.setText("Thinking step-by-step, create...");
        cotMainGoalText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        cotStep1Title = createStaticCoTStep(taskGroup, factory, 1, (instructions) -> cotStep1Instructions = instructions);
        cotStep2Title = createStaticCoTStep(taskGroup, factory, 2, (instructions) -> cotStep2Instructions = instructions);
        cotStep3Title = createStaticCoTStep(taskGroup, factory, 3, (instructions) -> cotStep3Instructions = instructions);

        Label outputFormatLabel = factory.createLabel(composite, "Output Format:");
        cotOutputFormatText = new StyledText(composite, SWT.WRAP | SWT.BORDER);
        cotOutputFormatText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        cotOutputFormatText.setText("First, write your step-by-step reasoning. Then, under a \"Final Result:\" heading, write the final JML block.");

        Button generateButton = factory.createButton(composite, "Generate", SWT.PUSH);
        generateButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        generateButton.addListener(SWT.Selection, event -> {
            StringBuilder taskBuilder = new StringBuilder();
            taskBuilder.append(cotMainGoalText.getText());
            appendStepToTask(taskBuilder, cotStep1Title, cotStep1Instructions);
            appendStepToTask(taskBuilder, cotStep2Title, cotStep2Instructions);
            appendStepToTask(taskBuilder, cotStep3Title, cotStep3Instructions);

            String prompt = "[Role]\n" + cotRoleText.getText() + "\n\n" + "[Context]\n" + cotContextText.getText() + "\n\n" + "[Task]\n" + taskBuilder.toString() + "\n\n" + "[Output Format]\n" + cotOutputFormatText.getText();
            executeGrokRequest(prompt, null);
        });

        return composite;
    }
    
    private interface InstructionWidgetConsumer { void accept(StyledText instructions); }
    
    private Text createStaticCoTStep(Composite parent, TabbedPropertySheetWidgetFactory factory, int stepNumber, InstructionWidgetConsumer consumer) {
        Group stepGroup = new Group(parent, SWT.NONE);
        stepGroup.setText("Reasoning Step " + stepNumber);
        stepGroup.setLayout(new GridLayout(2, false));
        stepGroup.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        new Label(stepGroup, SWT.NONE).setText("Step Title:");
        Text titleText = factory.createText(stepGroup, "");
        titleText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        new Label(stepGroup, SWT.NONE).setText("Instructions:");
        StyledText instructionsText = new StyledText(stepGroup, SWT.WRAP | SWT.BORDER | SWT.V_SCROLL);
        GridData instructionsGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
        instructionsGridData.heightHint = 40;
        instructionsGridData.horizontalSpan = 2;
        instructionsText.setLayoutData(instructionsGridData);

       
        switch (stepNumber) {
            case 1:
                titleText.setText("Precondition Reasoning");
                instructionsText.setText("Analyze the context, global conditions, and task to determine the necessary preconditions (`requires` clauses). What must be true *before* the method is called for it to work correctly?");
                break;
            case 2:
                titleText.setText("Postcondition Reasoning");
                instructionsText.setText("Based on the task, what must be true *after* the method completes successfully? Define the `ensures` clauses, considering the return value (`\\result`) and its properties (e.g., it's the *first* occurrence).");
                break;
            case 3:
                titleText.setText("Final Assembly and Purity");
                instructionsText.setText("Combine the preconditions and postconditions into a complete JML block. Critically consider side effects (the `assignable` clause) and whether the method is `pure`. If the method only calculates and returns a value without changing state, use `assignable \\nothing`.");
                break;
        }

        consumer.accept(instructionsText);
        return titleText;
    }

    private void appendStepToTask(StringBuilder builder, Text title, StyledText instructions) {
        if (title != null && instructions != null && !title.getText().isBlank() && !instructions.getText().isBlank()) {
            builder.append("\n\n").append(title.getText()).append(":\n").append(instructions.getText());
        }
    }
    
    private Composite createZeroShotControls(Composite parent, TabbedPropertySheetWidgetFactory factory) {
    	Composite composite = factory.createFlatFormComposite(parent);
        composite.setLayout(new GridLayout(1, false));
        Label roleLabel = factory.createLabel(composite, "Role (Persona):");
        zeroShotRoleText = new StyledText(composite, SWT.WRAP | SWT.BORDER);
        zeroShotRoleText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        zeroShotRoleText.setText("You are an expert in formal methods, Correctness-by-Construction (CbC), and the Java Modeling Language (JML).");
        Label contextLabel = factory.createLabel(composite, "Context:");
        zeroShotContextText = new StyledText(composite, SWT.WRAP | SWT.BORDER | SWT.V_SCROLL);
        zeroShotContextText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        Label taskLabel = factory.createLabel(composite, "Task:");
        zeroShotTaskText = new StyledText(composite, SWT.WRAP | SWT.BORDER | SWT.V_SCROLL);
        zeroShotTaskText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        Label outputFormatLabel = factory.createLabel(composite, "Output Format:");
        zeroShotOutputFormatText = new StyledText(composite, SWT.WRAP | SWT.BORDER);
        zeroShotOutputFormatText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        zeroShotOutputFormatText.setText("Write only the complete JML comment block.");
        Button generateButton = factory.createButton(composite, "Generate", SWT.PUSH);
        generateButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        generateButton.addListener(SWT.Selection, event -> {
            String prompt = "[Role]\n" + zeroShotRoleText.getText() + "\n\n" + "[Context]\n" + zeroShotContextText.getText() + "\n\n" + "[Task]\n" + zeroShotTaskText.getText() + "\n\n" + "[Output Format]\n" + zeroShotOutputFormatText.getText();
            executeGrokRequest(prompt, null);
        });
        return composite;
    }

    private Composite createFewShotControls(Composite parent, TabbedPropertySheetWidgetFactory factory) {
        Composite composite = factory.createFlatFormComposite(parent);
        composite.setLayout(new GridLayout(1, false));
        Label roleLabel = factory.createLabel(composite, "Role (Persona):");
        fewShotRoleText = new StyledText(composite, SWT.WRAP | SWT.BORDER);
        fewShotRoleText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        fewShotRoleText.setText("You are an expert in formal methods, Correctness-by-Construction (CbC), and the Java Modeling Language (JML).");
        Group exampleGroup = new Group(composite, SWT.NONE);
        exampleGroup.setText("In-Context Example");
        exampleGroup.setLayout(new GridLayout(1, false));
        exampleGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        Label exampleTaskLabel = factory.createLabel(exampleGroup, "Example Context and Task:");
        fewShotExampleTaskText = new StyledText(exampleGroup, SWT.WRAP | SWT.BORDER | SWT.V_SCROLL);
        fewShotExampleTaskText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        Label groundTruthLabel = factory.createLabel(exampleGroup, "Example Ground Truth (JML):");
        fewShotGroundTruthText = new StyledText(exampleGroup, SWT.WRAP | SWT.BORDER | SWT.V_SCROLL);
        fewShotGroundTruthText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        Label contextLabel = factory.createLabel(composite, "Main Task Context:");
        fewShotContextText = new StyledText(composite, SWT.WRAP | SWT.BORDER | SWT.V_SCROLL);
        fewShotContextText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        Label taskLabel = factory.createLabel(composite, "Main Task:");
        fewShotTaskText = new StyledText(composite, SWT.WRAP | SWT.BORDER | SWT.V_SCROLL);
        fewShotTaskText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        Label outputFormatLabel = factory.createLabel(composite, "Output Format:");
        fewShotOutputFormatText = new StyledText(composite, SWT.WRAP | SWT.BORDER);
        fewShotOutputFormatText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        fewShotOutputFormatText.setText("Write only the complete JML comment block.");
        Button generateButton = factory.createButton(composite, "Generate", SWT.PUSH);
        generateButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        generateButton.addListener(SWT.Selection, event -> {
            String prompt = "[Role]\n" + fewShotRoleText.getText() + "\n\n" + "[Context]\n" + "Example with ground truth:\n" + "Task: " + fewShotExampleTaskText.getText() + "\n" + "Ground Truth:\n" + fewShotGroundTruthText.getText() + "\n\n" + "Context for the main task:\n" + fewShotContextText.getText() + "\n\n" + "[Task]\n" + fewShotTaskText.getText() + "\n\n" + "[Output Format]\n" + fewShotOutputFormatText.getText();
            executeGrokRequest(prompt, null);
        });
        return composite;
    }
    
    private void executeGrokRequest(String prompt, Runnable onComplete) {

        if (rootFormula == null) {
            responseText.setText("Error: Please open a CbC diagram first.");
            return;
        }

        responseText.setText("Contacting Grok, please wait...");

        Job job = new Job("Grok API Request") {
            @Override
            protected IStatus run(IProgressMonitor monitor) {
                try {
                    GrokAccess grokAccess = new GrokAccess();

                    String safePrompt = prompt.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n");
                    String response = grokAccess.getResponse(safePrompt);

                    String aiResponse = extractContentFromJsonResponse(response);

                    if (aiResponse != null) {
                        aiResponse = processEscapeSequences(aiResponse);

                        aiResponse = aiResponse.replaceAll("^\\s*```[a-zA-Z]*\\s*\\n?", "");
                        aiResponse = aiResponse.replaceAll("\\n?\\s*```\\s*$", "");

                        final String finalResponse = aiResponse.trim();

                        Display.getDefault().asyncExec(() -> {
                            if (responseText.isDisposed()) return;
                            responseText.setText(finalResponse);
                            if (onComplete != null) {
                                onComplete.run();
                            }
                        });
                    } else {
                        Display.getDefault().asyncExec(() -> {
                            if (responseText.isDisposed()) return;
                            responseText.setText("Could not find content in the response. Full response: " + response);
                        });
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Display.getDefault().asyncExec(() -> {
                        if (responseText.isDisposed()) return;
                        responseText.setText("Error communicating with Grok: " + ex.getMessage());
                    });
                }
                return Status.OK_STATUS;
            }
        };
        job.schedule();
    }

    private String extractContentFromJsonResponse(String jsonResponse) {
        try {
            int contentIndex = jsonResponse.indexOf("\"content\":");
            if (contentIndex == -1) return null;

            int startQuote = jsonResponse.indexOf("\"", contentIndex + 10);
            if (startQuote == -1) return null;

            int endQuote = findClosingQuote(jsonResponse, startQuote + 1);
            if (endQuote == -1) return null;

            return jsonResponse.substring(startQuote + 1, endQuote);
        } catch (Exception e) {
            return null;
        }
    }

    private int findClosingQuote(String str, int startIndex) {
        for (int i = startIndex; i < str.length(); i++) {
            if (str.charAt(i) == '"' && (i == 0 || str.charAt(i - 1) != '\\')) {
                return i;
            }
        }
        return -1;
    }

    private String processEscapeSequences(String text) {
        text = text.replace("\\\\nothing", "__JML_NOTHING_PLACEHOLDER__");
        text = text.replace("\\\\", "\\");
        text = text.replace("\\n", "\n");
        text = text.replace("\\t", "\t");
        text = text.replace("\\\"", "\"");
        text = text.replace("\\/", "/");
        text = text.replace("__JML_NOTHING_PLACEHOLDER__", "\\nothing");
        return text;
    }
    
    @Override
    public void refresh() {
        this.rootFormula = null; 
        Diagram diagram = getDiagram();
        if (diagram != null) {
            Object rootBo = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(diagram);
            if (rootBo instanceof CbCFormula) this.rootFormula = (CbCFormula) rootBo;
        }
        if (this.rootFormula != null) {
            populateContextText(this.rootFormula);
        } else {
            String message = "Open a CbC diagram to see its global context.";
            if (zeroShotContextText != null && !zeroShotContextText.isDisposed()) zeroShotContextText.setText(message);
            if (fewShotContextText != null && !fewShotContextText.isDisposed()) fewShotContextText.setText(message);
            if (cotContextText != null && !cotContextText.isDisposed()) cotContextText.setText(message);
            if (srContextText != null && !srContextText.isDisposed()) srContextText.setText(message);
        }
    }

    private void populateContextText(CbCFormula formula) {
        if (formula == null || formula.eResource() == null) return;
        Resource resource = formula.eResource();
        JavaVariables vars = null;
        GlobalConditions conds = null;
        for (EObject obj : resource.getContents()) {
            if (obj instanceof JavaVariables) vars = (JavaVariables) obj;
            else if (obj instanceof GlobalConditions) conds = (GlobalConditions) obj;
        }
        StringBuilder contextBuilder = new StringBuilder();
        if (vars != null && !vars.getVariables().isEmpty()) {
            contextBuilder.append("Java Variables:\n");
            contextBuilder.append(vars.getVariables().stream()
                .map(eObj -> {
                    EStructuralFeature nameFeature = eObj.eClass().getEStructuralFeature("name");
                    if (nameFeature != null) {
                        Object value = eObj.eGet(nameFeature);
                        return value != null ? value.toString() : "";
                    }
                    return "";
                })
                .collect(Collectors.joining("\n")));
            contextBuilder.append("\n\n");
        }
        if (conds != null && !conds.getConditions().isEmpty()) {
            contextBuilder.append("Global Conditions:\n");
            contextBuilder.append(conds.getConditions().stream()
                .map(eObj -> {
                    EStructuralFeature nameFeature = eObj.eClass().getEStructuralFeature("name");
                    if (nameFeature != null) {
                        Object value = eObj.eGet(nameFeature);
                        return value != null ? value.toString() : "";
                    }
                    return "";
                })
                .collect(Collectors.joining("\n")));
        }
        String contextContent = contextBuilder.toString().trim();
        if (zeroShotContextText != null && !zeroShotContextText.isDisposed()) zeroShotContextText.setText(contextContent);
        if (fewShotContextText != null && !fewShotContextText.isDisposed()) fewShotContextText.setText(contextContent);
        if (cotContextText != null && !cotContextText.isDisposed()) cotContextText.setText(contextContent);
        if (srContextText != null && !srContextText.isDisposed()) srContextText.setText(contextContent);
    }
}