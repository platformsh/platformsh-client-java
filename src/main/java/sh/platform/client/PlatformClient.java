package sh.platform.client;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.http.message.BasicHeader;

import static java.util.Objects.requireNonNull;

public class PlatformClient {

    private static final String AUTH_URL = "https://accounts.platform.sh/oauth2/token";
    private static final String SERVICE_URL = "https://api.platform.sh/";
    private static final JsonMapper MAPPER;
    static final BasicHeader JSON_HEADER = new BasicHeader("Content-Type", "application/json");

    static {
        MAPPER = new JsonMapper();
        MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        MAPPER.setVisibilityChecker(VisibilityChecker.Std.defaultInstance()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY));
    }

    private final AuthUser user;

    private final AuthToken token;

    public PlatformClient(String token) {
        this.user = new AuthUser(requireNonNull(token, "token is required"));
        this.token = AuthToken.of(MAPPER, AUTH_URL, user);
    }

    public Projects getProjects() {
        return Projects.of(MAPPER, SERVICE_URL + "projects", token);
    }


}
