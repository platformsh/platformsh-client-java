package sh.platform.client;

import java.util.Objects;
import java.util.Optional;

import static sh.platform.client.PlatformClient.MAPPER;
import static sh.platform.client.PlatformClient.SERVICE_URL;

/**
 * It handles the API to project administration.
 */
public class ProjectAdministration {

    private final AuthUser user;

    private final AuthToken token;

    public ProjectAdministration(AuthUser user, AuthToken token) {
        this.user = user;
        this.token = token;
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
