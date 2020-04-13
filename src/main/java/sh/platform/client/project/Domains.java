package sh.platform.client.project;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.http.client.methods.HttpGet;
import sh.platform.client.AuthToken;
import sh.platform.client.PlatformClient;
import sh.platform.client.util.HttpClientExecutor;

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

    public static Map<String, Object> getDomain(JsonMapper mapper, String url, AuthToken token) {
        HttpGet request = new HttpGet(url);
        request.addHeader(PlatformClient.JSON_HEADER);
        request.addHeader("Authorization", token.getAuthorization());
        return HttpClientExecutor.request(request, mapper, new TypeReference<Map<String, Object>>() {});
    }
}
