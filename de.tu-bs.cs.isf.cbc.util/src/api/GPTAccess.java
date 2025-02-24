package api;
//imports are not from new JAR files( already in project included I think)
//I only changed Import-Packages in MANIFEST.MF, did this also in mutation (common.io)?
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import java.util.concurrent.TimeUnit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

//build path access rule (java/lang/String)
public class GPTAccess {
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY_FILE_PATH = "C:/Users/hanna/OneDrive/Dokumente/WS2425/Praktikum/apikey.txt"; // Change this to your desired location
    private static final int MAX_TOKENS = 4096; // Approximate token limit per OpenAI GPT model
    private static final int SAFETY_THRESHOLD_TOKENS = 3800; // Safety margin to prevent hitting the max limit
    private static final long REQUEST_DELAY_MS = 1000; // 1-second delay between requests

    private int requestCount = 0; // Counter to track requests
    private String apiKey;
    
    public GPTAccess() {
        this.apiKey = loadApiKey();
    }

    private String loadApiKey() {
        File file = new File(API_KEY_FILE_PATH);
        if (!file.exists()) {
            throw new RuntimeException("Error: API key file not found at " + API_KEY_FILE_PATH);
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String key = br.readLine().trim();
            if (key.isEmpty()) {
                throw new RuntimeException("Error: API key file is empty.");
            }
            return key;
        } catch (IOException e) {
            throw new RuntimeException("Error reading API key file: " + e.getMessage(), e);
        }
    }
    
    public String getResponse(String prompt) throws Exception {
    	if (apiKey == null || apiKey.isEmpty()) {
            throw new RuntimeException("Error: API key is missing or invalid.");
        }
    	// Estimate tokens in the input prompt (rough approximation)
        int estimatedTokens = estimateTokens(prompt);

        if (estimatedTokens > SAFETY_THRESHOLD_TOKENS) {
            throw new IllegalArgumentException("Prompt exceeds the token safety threshold. Please shorten the input.");
        }

        // Enforce delay between requests to avoid excessive usage
        if (requestCount > 0) {
            System.out.println("Delaying request to ensure safety...");
            TimeUnit.MILLISECONDS.sleep(REQUEST_DELAY_MS);
        } 
        // Build request body
    	String requestBody = "{ \"model\": \"gpt-4\", \"messages\": [{ \"role\": \"user\", \"content\": \"" + prompt + "\" }] }";
    	System.out.println("Request Body: " + requestBody);
    	 // Send the request
        String response = Request.Post(API_URL)
                .addHeader("Authorization", "Bearer " + apiKey)
                .bodyString(requestBody, ContentType.APPLICATION_JSON)
                .execute()
                .returnContent()
                .asString();
     // Increment request count
        requestCount++;
        return response;
    }
    private int estimateTokens(String text) {
        // Simple heuristic: 1 token ≈ 4 characters
        // This is a rough estimate and might vary based on text content
        return text.length() / 4;
    }

    public void resetRequestCount() {
        // Method to reset the request counter if needed
        requestCount = 0;
    }
}