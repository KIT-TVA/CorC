package de.tu_bs.cs.isf.cbc.util;


import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import api.GPTAccess;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.exceptions.SettingsException;

public class CounterExampleProcessor {

    private final GPTAccess gptAccess;

    // Constructor to initialize GPTAccess
    public CounterExampleProcessor() {
        this.gptAccess = new GPTAccess();
    }

    /**
     * Processes a given counterexample, optionally translating it using GPT if enabled.
     *
     * @param originalCounterExample The raw counterexample to be processed.
     * @return The processed counterexample, translated if AI translation is enabled.
     */
    public String generateCounterExample(String originalCounterExample, JavaVariables vars) {
        try {
            // Check if AI Translated Counterexamples are enabled in the settings
            if (Settings.get().isAiTranslatedCounterExamplesEnabled()) {
                System.out.println("AI Translation enabled. Sending counterexample to GPT...");
                return translateCounterExampleWithGPT(originalCounterExample, vars);
            } else {
                // Return the original counterexample if AI translation is disabled
                return originalCounterExample;
            }
        } catch (SettingsException e) {
            e.printStackTrace();
            return originalCounterExample; // Fallback in case of settings retrieval failure
        } catch (Exception e) {
            System.err.println("Error during GPT translation. Returning original counterexample.");
            e.printStackTrace();
            return originalCounterExample; // Fallback in case of GPT API errors
        }
    }

    /**
     * Sends the counterexample to GPT for translation or enhancement.
     *
     * @param counterExample The raw counterexample to be translated.
     * @return The translated counterexample as returned by GPT.
     * @throws Exception If the API call fails.
     */
    private String translateCounterExampleWithGPT(String counterExample, JavaVariables vars) throws Exception {
        // Step 1: Simplify and clean up logical expressions
        String simplifiedCounterExample = simplifyLogicalExpressions(counterExample);

        // Step 2: Sanitize the input
        String sanitizedCounterExample = sanitizePrompt(simplifiedCounterExample);

        // Step 4: Construct the concise prompt
        List<JavaVariable> variables = vars.getVariables();
        String variableNames = variables.stream()
            .map(JavaVariable::getName)
            .collect(Collectors.joining(", "));
        String prompt = "Shortly give only the values of variables "+variableNames+" from the output of Z3 that calculated a counter example for given code and specification(ints in decimal numbers and bools in true or false): "+sanitizedCounterExample;

        // Log the final prompt for debugging
        System.out.println("Final Prompt Sent to GPT: " + prompt);

        // Step 5: Send the prompt to GPT with a max_tokens limit
        String response = gptAccess.getResponse(prompt);

        // Regex to find the "content" value
        Pattern pattern = Pattern.compile("\"content\":\\s*\"(.*?)\"");
        Matcher matcher = pattern.matcher(response);

        // Extract and print the content
        if (matcher.find()) {
            String content = matcher.group(1); // Group 1 contains the matched content value
            content = content.replace("\\n", " "); // Replace all whitespace sequences with a single space
            System.out.println("\u001B[34mResponse from GPT: " + content + "\u001B[0m"); //Output in Blue font
            response = "\u001B[34mResponse from GPT: " + content + "\u001B[0m";
        }
        return response;
    }
    private String simplifyLogicalExpressions(String counterExample) {
        if (counterExample == null || counterExample.isEmpty()) {
            return counterExample;
        }

        // Replace binary and hexadecimal values with human-readable descriptions
        counterExample = counterExample.replaceAll("#b(\\d+)", "binary $1")
                                       .replaceAll("#x([0-9a-fA-F]+)", "hexadecimal $1");

        // Remove unnecessary numeric prefixes (e.g., "3)", "4)")
        counterExample = counterExample.replaceAll("\\[\\d+\\)", "")
                                       .replaceAll("\\]", "")
                                       .replaceAll("\\[", "");

        // Replace excessive spaces and trim the result
        return counterExample.replaceAll("\\s+", " ").trim();
    }

    private String sanitizePrompt(String input) {
        return input.replace("\\", "\\\\") // Escape backslashes
                    .replace("\"", "\\\"") // Escape double quotes
                    .replace("\n", " ")   // Replace newlines with spaces
                    .replace("\r", " ")   // Replace carriage returns with spaces
                    .replace("\t", " ")   // Replace tabs with spaces
                    .replaceAll("\\|.*?\\|", "data reference") // Simplify vertical bar constructs
                    .replaceAll("\\s+", " ") // Collapse multiple spaces
                    .trim();
    }

    private String truncatePrompt(String input, int maxLength) {
        return input.length() > maxLength ? input.substring(0, maxLength) + "..." : input;
    }
}
