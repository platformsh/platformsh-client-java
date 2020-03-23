package sh.platform.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

public class ProjectResponse {

    private final Long code;

    private final String status;

    @JsonCreator
    ProjectResponse(@JsonProperty("code") Long code,
                    @JsonProperty("status") String status) {

        this.code = code;
        this.status = status;
    }

    public Long getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "ProjectResponse{" +
                "code=" + code +
                ", status='" + status + '\'' +
                '}';
    }

    static ProjectResponse cleanCache(JsonMapper mapper, String url, AuthToken token) {
        HttpPost request = new HttpPost(url);
        request.addHeader(PlatformClient.JSON_HEADER);
        request.addHeader("Authorization", token.getAuthorization());
        return HttpClientExecutor.request(request, mapper, ProjectResponse.class);
    }
}
