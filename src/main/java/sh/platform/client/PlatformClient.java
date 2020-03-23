package sh.platform.client;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.message.BasicHeader;

import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class PlatformClient {

    private static final String AUTH_URL = "https://accounts.platform.sh/oauth2/token";
    private static final String SERVICE_URL = "https://api.platform.sh/";
    private static final JsonMapper MAPPER;
    static final BasicHeader JSON_HEADER = new BasicHeader("Content-Type", "application/json");

    static {
        MAPPER = new JsonMapper();
        MAPPER.registerModule(new JavaTimeModule());
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

    /**
     * Retrieve list of projects associated with account
     *
     * @return the {@link Projects}
     */
    public Projects getProjects() {
        return Projects.of(MAPPER, SERVICE_URL + "projects", token);
    }

    /**
     * Retrieve the details about an existing project
     *
     * @param id the project id
     * @return the Project
     * @throws NullPointerException when the id is null
     */
    public Optional<Project> getProject(String id) {
        Objects.requireNonNull(id, "id is required");
        return Optional.ofNullable(Project.of(MAPPER, SERVICE_URL + "projects/" + id, token));
    }

    /**
     * On rare occasions, a project's build cache can become corrupted. This endpoint will entirely flush the project's build cache.
     * More information on clearing the build cache can be found in our user documentation:
     * https://docs.platform.sh/development/troubleshoot.html#clear-the-build-cache
     *
     * @param id the project id
     * @return the result of clean cache
     * @throws NullPointerException when the id is null
     */
    public ProjectResponse clearProjectBuildCache(String id) {
        Objects.requireNonNull(id, "id is required");
        return ProjectResponse.cleanCache(MAPPER, SERVICE_URL + "projects/" + id + "/clear_build_cache", token);
    }


}
