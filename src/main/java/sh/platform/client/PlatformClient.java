package sh.platform.client;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;

import javax.net.ssl.SSLContext;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public class PlatformClient {

    private static final String DEFAULT_URL = "https://accounts.platform.sh/";
    private static final JsonMapper MAPPER = new JsonMapper();
    static final BasicHeader JSON_HEADER = new BasicHeader("Content-Type", "application/json");

    private final String url;

    private final AuthUser user;

    private final AuthToken token;

    public PlatformClient(String url, String token) {
        this.url = requireNonNull(url, "url is required");
        this.user = new AuthUser(requireNonNull(token, "token is required"));
        this.token = AuthToken.of(MAPPER, this.url + "oauth2/token", user);
    }

    public PlatformClient(String token) {
        this.url = DEFAULT_URL;
        this.user = new AuthUser(requireNonNull(token, "token is required"));
        this.token = AuthToken.of(MAPPER, this.url + "oauth2/token", user);
    }


    public Projects getProjects() {
        return Projects.of(MAPPER, this.url + "projects", token);
    }


}
