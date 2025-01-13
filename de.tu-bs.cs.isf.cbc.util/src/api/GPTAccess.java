package api;
//imports are not from new JAR files( already in project included I think)
//I only changed Import-Packages in MANIFEST.MF, did this also in mutation (common.io)?
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import java.util.concurrent.TimeUnit;

//build path access rule (java/lang/String)
public class GPTAccess {
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = "key";
    private static final int MAX_TOKENS = 4096; // Approximate token limit per OpenAI GPT model
    private static final int SAFETY_THRESHOLD_TOKENS = 3800; // Safety margin to prevent hitting the max limit
    private static final long REQUEST_DELAY_MS = 1000; // 1-second delay between requests

    private int requestCount = 0; // Counter to track requests

    
    public String getResponse(String prompt) throws Exception {
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
    	String requestBody = "{ \"model\": \"gpt-3.5-turbo\", \"messages\": [{ \"role\": \"user\", \"content\": \"" + prompt + "\" }] }";
    	 // Send the request
        String response = Request.Post(API_URL)
                .addHeader("Authorization", "Bearer " + API_KEY)
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