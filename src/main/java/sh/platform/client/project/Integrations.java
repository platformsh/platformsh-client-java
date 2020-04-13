package sh.platform.client.project;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import sh.platform.client.AuthToken;
import sh.platform.client.PlatformClient;
import sh.platform.client.PlatformClientException;
import sh.platform.client.util.HttpClientExecutor;

import java.io.IOException;
import java.util.Map;

final class Integrations {

    private Integrations() {
    }

    public static ProjectResponse delete(JsonMapper mapper, String url, AuthToken token) {
        HttpDelete request = new HttpDelete(url);
        request.addHeader(PlatformClient.JSON_HEADER);
        request.addHeader("Authorization", token.getAuthorization());
        return HttpClientExecutor.request(request, mapper, ProjectResponse.class);
    }

    public static ProjectResponse create(JsonMapper mapper, String url, AuthToken token,
                                         Map<String, Object> entity) {

        HttpPost request = new HttpPost(url);
        request.addHeader(PlatformClient.JSON_HEADER);
        try {
            request.setEntity(new StringEntity(mapper.writeValueAsString(entity)));
        } catch (IOException exp) {
            throw new PlatformClientException("There is an error to load the project integration", exp);
        }
        request.addHeader("Authorization", token.getAuthorization());
        return HttpClientExecutor.request(request, mapper, ProjectResponse.class);
    }


    public static ProjectResponse update(JsonMapper mapper, String url, AuthToken token, Map<String, Object> entity) {
        HttpPatch request = new HttpPatch(url);
        request.addHeader(PlatformClient.JSON_HEADER);
        try {
            request.setEntity(new StringEntity(mapper.writeValueAsString(entity)));
        } catch (IOException exp) {
            throw new PlatformClientException("There is an error to load the project integration", exp);
        }

        request.addHeader("Authorization", token.getAuthorization());
        return HttpClientExecutor.request(request, mapper, ProjectResponse.class);
    }
}
