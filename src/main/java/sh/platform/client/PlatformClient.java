package sh.platform.client;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.message.BasicHeader;
import sh.platform.client.project.ProjectAdministration;

import static java.util.Objects.requireNonNull;

public class PlatformClient {

    private static final String AUTH_URL = "https://accounts.platform.sh/oauth2/token";
    public static final String SERVICE_URL = "https://api.platform.sh/";
    public static final JsonMapper MAPPER;
    public static final BasicHeader JSON_HEADER = new BasicHeader("Content-Type", "application/json");

    static {
        MAPPER = new JsonMapper();
        MAPPER.registerModule(new JavaTimeModule());
        MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        MAPPER.setVisibilityChecker(VisibilityChecker.Std.defaultInstance()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY));
    }

    private final AuthUser user;

    private final AuthToken token;

    private final ProjectAdministration projectAdministration;

    public PlatformClient(String token) {
        this.user = new AuthUser(requireNonNull(token, "token is required"));
        this.token = AuthToken.of(MAPPER, AUTH_URL, user);
        this.projectAdministration = new ProjectAdministration(this.user, this.token);
    }

    /**
     *
     * @return
     */
    public ProjectAdministration getProjectAdministration() {
        return projectAdministration;
    }
}
