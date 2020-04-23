package sh.platform.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import sh.platform.client.util.HttpClientExecutor;

import java.io.IOException;

public class AuthToken {

    @JsonProperty("access_token")
    private final String token;

    @JsonProperty("expires_in")
    private final long expires;

    @JsonProperty("token_type")
    private final String type;

    @JsonProperty("scope")
    private final String scope;

    @JsonCreator
    AuthToken(@JsonProperty("access_token") String token,
              @JsonProperty("expires_in") long expires,
              @JsonProperty("token_type") String type,
              @JsonProperty("scope") String scope) {
        this.token = token;
        this.type = type;
        this.scope = scope;
        this.expires = expires;
    }

    public String getToken() {
        return token;
    }

    public long getExpires() {
        return expires;
    }

    public String getType() {
        return type;
    }

    public String getScope() {
        return scope;
    }

    public String getAuthorization() {
        return "Bearer " + token;
    }

    @Override
    public String toString() {
        return "AuthToken{" +
                "token='" + token + '\'' +
                ", expires=" + expires +
                ", type='" + type + '\'' +
                ", scope='" + scope + '\'' +
                '}';
    }


    static AuthToken of(JsonMapper mapper, String url, AuthUser user) {
        try {
            HttpPost request = new HttpPost(url);
            request.addHeader(PlatformClient.JSON_HEADER);
            request.setEntity(new StringEntity(mapper.writeValueAsString(user)));
            return HttpClientExecutor.request(request, mapper, AuthToken.class);
        } catch (IOException e) {
            throw new PlatformClientException("There is an error to get the client", e);
        }
    }


}
