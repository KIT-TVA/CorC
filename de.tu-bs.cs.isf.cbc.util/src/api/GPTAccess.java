package api;
//imports are not from new JAR files( already in project included I think)
//I only changed Import-Packages in MANIFEST.MF, did this also in mutation (common.io)?
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;

//build path access rule (java/lang/String)
public class GPTAccess {
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = "key";

    public String getResponse(String prompt) throws Exception {
        String requestBody = "{ \"model\": \"gpt-3.5-turbo\", \"messages\": [{ \"role\": \"user\", \"content\": \"" + prompt + "\" }] }";

        String response = Request.Post(API_URL)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .bodyString(requestBody, ContentType.APPLICATION_JSON)
                .execute()
                .returnContent()
                .asString();

        return response;
    }
}