package sh.platform.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.UnsupportedEncodingException;

public class PlatformClient {

    private static final String DEFAULT_URL = "https://accounts.platform.sh/";
    private static final JsonMapper MAPPER = new JsonMapper();

    private final String url;

    private final String token;


    public PlatformClient(String url, String token) {
        this.url = url;
        this.token = token;
    }

    public PlatformClient(String token) {
        this.url = DEFAULT_URL;
        this.token = token;
    }

    public void init() {


    }


}
