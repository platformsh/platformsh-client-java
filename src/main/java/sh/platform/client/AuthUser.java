package sh.platform.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.UnsupportedEncodingException;

public class AuthUser {

    @JsonProperty("client_id")
    private final String id;

    @JsonProperty("grant_type")
    private final String type;

    @JsonProperty("api_token")
    private final String token;

    AuthUser(String token) {
        this.id = "platform-api-user";
        this.type = "api_token";
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "AuthUser{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", token='" + token + '\'' +
                '}';
    }



}