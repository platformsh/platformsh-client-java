package sh.platform.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

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
    public AuthToken(@JsonProperty("access_token") String token,
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

    @Override
    public String toString() {
        return "AuthToken{" +
                "token='" + token + '\'' +
                ", expires=" + expires +
                ", type='" + type + '\'' +
                ", scope='" + scope + '\'' +
                '}';
    }


    static AuthToken of(JsonMapper mapper, String url, String token, AuthUser user) {
        HttpPost request = new HttpPost(url);
        request.addHeader("Content-Type", "application/json");
        CloseableHttpClient client = HttpClients.createDefault();
        AuthUser authUser = new AuthUser(token);
        try {
            request.setEntity(new StringEntity(mapper.writeValueAsString(authUser)));
            try (CloseableHttpClient httpClient = HttpClients.createDefault();
                 CloseableHttpResponse response = httpClient.execute(request)) {

            }
            return null;
        } catch (IOException e) {
            throw new PlatformClientException("There is an error to get the client", e);
        }
    }
}
