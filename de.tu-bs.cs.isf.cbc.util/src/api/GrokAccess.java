package api;

import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;


public class GrokAccess {
	private static final String API_URL = "https://api.x.ai/v1/chat/completions";
	private static final String API_KEY = "key";

    public String getResponse(String prompt) throws Exception {
    	String requestBody = "{ \"model\": \"grok-4-0709\", \"messages\": [{ \"role\": \"user\", \"content\": \"" + prompt + "\" }] }";

        String response = Request.Post(API_URL)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .bodyString(requestBody, ContentType.APPLICATION_JSON)
                .execute()
                .returnContent()
                .asString();

        return response;
    }
}