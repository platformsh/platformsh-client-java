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

    private final String authUrl;
    private final String serviceURL;

    public PlatformClient(String token) {
        this.authUrl = PropertiesReader.INSTANCE.getAuthUrl();
        this.serviceURL = PropertiesReader.INSTANCE.getServiceUrl();
        this.user = new AuthUser(requireNonNull(token, "token is required"));
        this.token = AuthToken.of(MAPPER, authUrl, user);
        this.projectAdministration = new ProjectAdministration(this.user, this.token, serviceURL);
    }

    /**
     * @return
     */
    public ProjectAdministration getProjectAdministration() {
        return projectAdministration;
    }
}
