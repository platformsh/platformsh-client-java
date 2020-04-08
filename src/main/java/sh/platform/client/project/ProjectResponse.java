package sh.platform.client.project;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import sh.platform.client.AuthToken;
import sh.platform.client.util.HttpClientExecutor;
import sh.platform.client.PlatformClient;

public class ProjectResponse {

    private final Integer code;

    private final String status;

    @JsonCreator
    ProjectResponse(@JsonProperty("code") Integer code,
                    @JsonProperty("status") String status) {

        this.code = code;
        this.status = status;
    }

    public Integer getCode() {
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

    static ProjectResponse delete(JsonMapper mapper, String url, AuthToken token) {
        HttpDelete request = new HttpDelete(url);
        request.addHeader(PlatformClient.JSON_HEADER);
        request.addHeader("Authorization", token.getAuthorization());
        return HttpClientExecutor.request(request, mapper, ProjectResponse.class);
    }
}
