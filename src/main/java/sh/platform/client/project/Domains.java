package sh.platform.client.project;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import sh.platform.client.AuthToken;
import sh.platform.client.PlatformClient;
import sh.platform.client.PlatformClientException;
import sh.platform.client.util.HttpClientExecutor;

import java.io.IOException;
import java.util.List;
import java.util.Map;

final class Domains {
    Domains() {
    }


    static List<Map<String, Object>> getDomains(JsonMapper mapper, String url, AuthToken token) {
        HttpGet request = new HttpGet(url);
        request.addHeader(PlatformClient.JSON_HEADER);
        request.addHeader("Authorization", token.getAuthorization());
        return HttpClientExecutor.request(request, mapper, new TypeReference<List<Map<String, Object>>>() {});
    }

    static Map<String, Object> getDomain(JsonMapper mapper, String url, AuthToken token) {
        HttpGet request = new HttpGet(url);
        request.addHeader(PlatformClient.JSON_HEADER);
        request.addHeader("Authorization", token.getAuthorization());
        return HttpClientExecutor.request(request, mapper, new TypeReference<Map<String, Object>>() {});
    }

    static ProjectResponse delete(JsonMapper mapper, String url, AuthToken token) {
        HttpDelete request = new HttpDelete(url);
        request.addHeader(PlatformClient.JSON_HEADER);
        request.addHeader("Authorization", token.getAuthorization());
        return HttpClientExecutor.request(request, mapper, ProjectResponse.class);
    }


    static ProjectResponse create(JsonMapper mapper, String url, AuthToken token,
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


    static ProjectResponse update(JsonMapper mapper, String url, AuthToken token, Map<String, Object> entity) {
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
