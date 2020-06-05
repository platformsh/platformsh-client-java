package sh.platform.client;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.message.BasicHeader;
import sh.platform.client.environment.Environments;
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
    private final String authUrl;
    private final String serviceURL;

    private final ProjectAdministration projectAdministration;
    private final Environments environments;

    public PlatformClient(String token) {
        this.authUrl = PropertiesReader.INSTANCE.getAuthUrl();
        this.serviceURL = PropertiesReader.INSTANCE.getServiceUrl();
        this.user = new AuthUser(requireNonNull(token, "token is required"));
        this.token = AuthToken.of(MAPPER, authUrl, user);
        this.projectAdministration = new ProjectAdministration(this.user, this.token, serviceURL);
        this.environments = new Environments(this.user, this.token, serviceURL);
    }

    public ProjectAdministration getProjectAdministration() {
        return this.projectAdministration;
    }

    public Environments getEnvironments() {
        return this.environments;
    }
}
