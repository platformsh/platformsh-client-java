package sh.platform.client.project;

import sh.platform.client.AuthToken;
import sh.platform.client.AuthUser;
import sh.platform.client.PlatformClient;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static sh.platform.client.PlatformClient.MAPPER;
import static sh.platform.client.PlatformClient.SERVICE_URL;

/**
 * It handles the API to project administration.
 */
public final class ProjectAdministration {

    private static final String PROJECTS_URLS = SERVICE_URL + "projects/";
    private final AuthUser user;

    private final AuthToken token;

    @Deprecated
    /**
     * Use {@link PlatformClient#getProjectAdministration()} instead.
     */
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
        return ProjectResponse.cleanCache(MAPPER, PROJECTS_URLS + id + "/clear_build_cache", token);
    }

    /**
     * Create an empty project on the platform or Update the details about an existing project
     *
     * @param title the title
     * @return a builder to create a project
     * @throws NullPointerException when title is null
     */
    public ProjectCreateBuilder project(String title) {
        Objects.requireNonNull(title, "title is required");
        return new ProjectCreateBuilder(PROJECTS_URLS, title, token, MAPPER);
    }

    /**
     * Update the details about an existing project
     *
     * @param id the id
     * @return the result
     * @throws NullPointerException when title is null
     */
    public ProjectBuilder update(String id) {
        Objects.requireNonNull(id, "id is required");
        return new ProjectBuilder(PROJECTS_URLS + id, token, MAPPER);
    }

    /**
     * Delete a project from the platform
     *
     * @param id the id
     * @return the result
     * @throws NullPointerException when title is null
     */
    public ProjectResponse delete(String id) {
        Objects.requireNonNull(id, "id is required");
        return ProjectResponse.delete(MAPPER, PROJECTS_URLS + id, token);
    }


    /**
     * Retrieve a list of objects representing the user-defined variables within a project.
     *
     * @param projectId the project id
     * @return the list of {@link Variable}
     */
    public List<Variable> getVariables(String projectId) {
        Objects.requireNonNull(projectId, "projectId is required");
        return Variable.list(MAPPER, PROJECTS_URLS + projectId + "/variables/", token);
    }

    /**
     * Add a variable or update to a project. The value can be either a string or a JSON object (default: string),
     * as specified by the is_json boolean flag. See the Variables section in our documentation for more information.
     *
     * @param projectId the project id
     * @return the {@link VariableBuilder}
     */
    public VariableBuilder variable(String projectId) {
        Objects.requireNonNull(projectId, "projectId is required");
        return new VariableBuilder(MAPPER, PROJECTS_URLS + projectId + "/variables/", token);
    }

    /**
     * Delete a single user-defined project variable.
     *
     * @param projectId   the project id
     * @param variableKey the variable key
     * @return the response status
     */
    public ProjectResponse delete(String projectId, String variableKey) {
        Objects.requireNonNull(projectId, "projectId is required");
        Objects.requireNonNull(variableKey, "variableKey is required");
        return ProjectResponse.delete(MAPPER, PROJECTS_URLS + projectId + "/variables/" + variableKey, token);
    }

    /**
     * Retrieve a single user-defined project variable.
     *
     * @param projectId   the project id
     * @param variableKey the variable key
     * @return the response {@link Variable}
     */
    public Variable getVariable(String projectId, String variableKey) {
        Objects.requireNonNull(projectId, "projectId is required");
        Objects.requireNonNull(variableKey, "variableKey is required");

        return Variable.get(MAPPER, PROJECTS_URLS + projectId + "/variables/" + variableKey, token);
    }

}
