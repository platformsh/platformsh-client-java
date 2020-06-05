package sh.platform.client.environment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import sh.platform.client.AuthToken;
import sh.platform.client.PlatformClient;
import sh.platform.client.util.HttpClientExecutor;

import java.util.List;

public class EnvironmentStatus {

    private final String code;

    private final String message;

    @JsonCreator
    EnvironmentStatus(@JsonProperty("code") String code,
                      @JsonProperty("message") String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ProjectStatus{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    static EnvironmentStatus redeploy(JsonMapper mapper, String url, AuthToken token) {
        HttpPost request = new HttpPost(url);
        request.addHeader(PlatformClient.JSON_HEADER);
        request.addHeader("Authorization", token.getAuthorization());
        return HttpClientExecutor.request(request, mapper, EnvironmentStatus.class);
    }
}
