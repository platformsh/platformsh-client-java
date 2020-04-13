package sh.platform.client.project;

import sh.platform.client.AuthToken;
import sh.platform.client.AuthUser;
import sh.platform.client.PlatformClient;

import java.util.List;
import java.util.Map;
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
        return Projects.of(MAPPER, PROJECTS_URLS, token);
    }

    /**
     * Retrieve the details about an existing project
     *
     * @param project the project id
     * @return the Project
     * @throws NullPointerException when the project is null
     */
    public Optional<Project> getProject(String project) {
        Objects.requireNonNull(project, "project is required");
        return Optional.ofNullable(Project.of(MAPPER, PROJECTS_URLS + project, token));
    }

    /**
     * On rare occasions, a project's build cache can become corrupted. This endpoint will entirely flush the project's build cache.
     * More information on clearing the build cache can be found in our user documentation:
     * https://docs.platform.sh/development/troubleshoot.html#clear-the-build-cache
     *
     * @param project the project id
     * @return the result of clean cache
     * @throws NullPointerException when the project is null
     */
    public ProjectResponse clearProjectBuildCache(String project) {
        Objects.requireNonNull(project, "project is required");
        return ProjectResponse.cleanCache(MAPPER, PROJECTS_URLS + project + "/clear_build_cache", token);
    }

    /**
     * Create an empty project on the platform or Update the details about an existing project
     *
     * @param title the title
     * @return a builder to create a project
     * @throws NullPointerException when title is null
     */
    public ProjectCreateBuilder create(String title) {
        Objects.requireNonNull(title, "title is required");
        return new ProjectCreateBuilder(PROJECTS_URLS, title, token, MAPPER);
    }

    /**
     * Update the details about an existing project
     *
     * @param project the id
     * @return the result
     * @throws NullPointerException when title is null
     */
    public ProjectBuilder update(String project) {
        Objects.requireNonNull(project, "project is required");
        return new ProjectBuilder(PROJECTS_URLS + project, token, MAPPER);
    }

    /**
     * Delete a project from the platform
     *
     * @param project the id
     * @return the result
     * @throws NullPointerException when title is null
     */
    public ProjectResponse delete(String project) {
        Objects.requireNonNull(project, "project is required");
        return ProjectResponse.delete(MAPPER, PROJECTS_URLS + project, token);
    }


    /**
     * Retrieve a list of objects representing the user-defined variables within a project.
     *
     * @param project the project id
     * @return the list of {@link Variable}
     */
    public List<Variable> getVariables(String project) {
        Objects.requireNonNull(project, "project is required");
        return Variable.list(MAPPER, PROJECTS_URLS + project + "/variables/", token);
    }

    /**
     * Add a variable or update to a project. The value can be either a string or a JSON object (default: string),
     * as specified by the is_json boolean flag. See the Variables section in our documentation for more information.
     *
     * @param project the project id
     * @return the {@link VariableBuilder}
     */
    public VariableBuilder variable(String project) {
        Objects.requireNonNull(project, "project is required");
        return new VariableBuilder(MAPPER, PROJECTS_URLS + project + "/variables/", token);
    }

    /**
     * Delete a single user-defined project variable.
     *
     * @param project     the project id
     * @param variableKey the variable key
     * @return the response status
     */
    public ProjectResponse delete(String project, String variableKey) {
        Objects.requireNonNull(project, "project is required");
        Objects.requireNonNull(variableKey, "variableKey is required");
        return ProjectResponse.delete(MAPPER, PROJECTS_URLS + project + "/variables/" + variableKey, token);
    }

    /**
     * Retrieve a single user-defined project variable.
     *
     * @param project     the project id
     * @param variableKey the variable key
     * @return the response {@link Variable}
     */
    public Variable getVariable(String project, String variableKey) {
        Objects.requireNonNull(project, "project is required");
        Objects.requireNonNull(variableKey, "variableKey is required");

        return Variable.get(MAPPER, PROJECTS_URLS + project + "/variables/" + variableKey, token);
    }

    /**
     * Retrieve a list of refs/* in the repository backing a project. This endpoint functions
     * similarly to git show-ref, with each returned object containing a ref field with the ref's name,
     * and an object containing the associated commit ID.
     *
     * @param project the project id
     * @return the list of refs/*
     */
    public List<Map<String, Object>> getRepositoryRefs(String project) {
        Objects.requireNonNull(project, "project is required");
        return Repository.refs(MAPPER, PROJECTS_URLS + project + "/git/refs", token);
    }

    /**
     * Retrieve the details of a single refs object in the repository backing a project.
     * This endpoint functions similarly to git show-ref <pattern>, although the pattern
     * must be a full ref id, rather than a matching pattern.
     * NOTE: The {repositoryRefId} must be properly escaped.
     * That is, the ref refs/heads/master is accessible via /projects/{projectId}/git/refs/heads%2Fmaster.
     *
     * @param project the project id
     * @param ref     the ref properly escaped.
     * @return the ref
     */
    public Map<String, Object> getRepositoryRef(String project, String ref) {
        Objects.requireNonNull(project, "project is required");
        Objects.requireNonNull(ref, "ref is required");
        return Repository.ref(MAPPER, PROJECTS_URLS + project + "/git/refs/" + ref, token);
    }

    /**
     * Retrieve the list of integrations
     *
     * @param project the project id
     * @return the list of integration
     * @throws NullPointerException when project is null
     */
    public List<Map<String, Object>> getRepositoryIntegrations(String project) {
        Objects.requireNonNull(project, "project is required");
        return Repository.refs(MAPPER, PROJECTS_URLS + project + "/integrations", token);
    }

    /**
     * Retrive the integration from the ID
     *
     * @param project     the project id
     * @param integration the integration id
     * @return the integration
     */
    public Map<String, Object> getRepositoryIntegration(String project, String integration) {
        Objects.requireNonNull(project, "project is required");
        Objects.requireNonNull(integration, "integration is required");
        return Repository.ref(MAPPER, PROJECTS_URLS + project + "/integrations/" + integration, token);
    }

    /**
     * Deletes the integration
     *
     * @param project     the project id
     * @param integration the integration id
     * @return the {@link ProjectResponse}
     */
    public ProjectResponse deleteIntegration(String project, String integration) {
        Objects.requireNonNull(project, "project is required");
        Objects.requireNonNull(integration, "integration is required");
        return Integrations.delete(MAPPER, PROJECTS_URLS + project + "/integrations/" + integration, token);
    }

    /**
     * Retrieve a list of objects representing the user-specified domains associated with a project.
     * Note that this does not return the domains automatically assigned to a project that appear under
     * "Access site" on the user interface.
     *
     * @param project the project id
     * @return the domains from the project
     * @throws NullPointerException when project id null
     */
    public List<Map<String, Object>> getDomains(String project) {
        Objects.requireNonNull(project, "project is required");
        return Domains.getDomains(MAPPER, PROJECTS_URLS + project + "/domains/", token);
    }

    /**
     * Retrieve information about a single user-specified domain associated with a project.
     *
     * @param project the project id
     * @param domain  the domain id
     * @return the Domain information
     * @throws NullPointerException when there is null parameter
     */
    public Map<String, Object> getDomain(String project, String domain) {
        Objects.requireNonNull(project, "project is required");
        Objects.requireNonNull(domain, "domain is required");
        return Domains.getDomain(MAPPER, PROJECTS_URLS + project + "/domains/" + domain, token);
    }

    /**
     * Delete a single user-specified domain associated with a project.
     * @param project the project id
     * @param domain the domain id
     * @return {@link ProjectResponse}
     * @throws NullPointerException when there is null parameter
     */
    public ProjectResponse deleteDomain(String project, String domain) {
        Objects.requireNonNull(project, "project is required");
        Objects.requireNonNull(domain, "domain is required");
        return Domains.delete(MAPPER, PROJECTS_URLS + project + "/domains/" + domain, token);
    }

    /**
     * Add a single domain to a project. If the ssl field is left blank without
     * an object containing a PEM-encoded SSL certificate, a certificate will be provisioned for you via Let's Encrypt.
     * @param project the project id
     * @param settings the domain settings
     * @return {@link ProjectResponse}
     * @throws NullPointerException when there is null parameter
     */
    public ProjectResponse createDomain(String project, Map<String, Object> settings) {
        Objects.requireNonNull(project, "project is required");
        Objects.requireNonNull(settings, "settings is required");
        return Domains.create(MAPPER, PROJECTS_URLS + project + "/domains/", token, settings);
    }

    /**
     * Update the information associated with a single user-specified domain associated with a project.
     * @param project the project id
     * @param domain the domain id
     * @param settings the domain settings
     * @return {@link ProjectResponse}
     * @throws NullPointerException when there is null parameter
     */
    public ProjectResponse updateDomain(String project, String domain, Map<String, Object> settings) {
        Objects.requireNonNull(project, "project is required");
        Objects.requireNonNull(domain, "domain is required");
        Objects.requireNonNull(settings, "settings is required");
        return Domains.update(MAPPER, PROJECTS_URLS + project + "/domains/" + domain, token, settings);
    }

    /**
     * Create a third-integration
     *
     * @param project the project
     * @param third   the third configuration as map
     * @return the {@link ProjectResponse}
     */
    public ProjectResponse createIntegration(String project, Map<String, Object> third) {
        Objects.requireNonNull(project, "project is required");
        Objects.requireNonNull(third, "third is required");
        return Integrations.create(MAPPER, PROJECTS_URLS + project + "/integrations/", token, third);
    }

    /**
     * Updates the integration
     *
     * @param project     the project id
     * @param integration the integration id
     * @param third       the third configurations
     * @return the {@link ProjectResponse}
     */
    public ProjectResponse updateIntegration(String project, String integration, Map<String, Object> third) {
        Objects.requireNonNull(project, "project is required");
        Objects.requireNonNull(integration, "integration is required");
        Objects.requireNonNull(third, "third is required");
        return Integrations.update(MAPPER, PROJECTS_URLS + project + "/integrations/" + integration, token, third);
    }


    /**
     * Retrieve, by hash, the tree state represented by a commit.
     * The returned object's tree field contains a list of files and directories present in the tree.
     * <p>
     * Directories in the tree can be recursively retrieved by this endpoint
     * through their hashes. Files in the tree can be retrieved by the Get a blob object endpoint.
     *
     * @param project the project id
     * @param tree    the tree id
     * @return the tree result
     */
    public Map<String, Object> getRepositoryTree(String project, String tree) {
        return Repository.ref(MAPPER, PROJECTS_URLS + project + "/git/trees/" + tree, token);
    }

    /**
     * Retrieve, by hash, an object representing a commit in the repository backing a project.
     * This endpoint functions similarly to git cat-file -p <commit-id>. The returned object contains the
     * hash of the Git tree that it belongs to, as well as the ID of parent commits.
     * <p>
     * The commit represented by a parent ID can be retrieved using this endpoint,
     * while the tree state represented by this commit can be retrieved using the Get a tree object endpoint.
     *
     * @param project the project id
     * @param commit  the commit hash
     * @return the {@link Commit}
     */
    public Commit getRepositoryCommit(String project, String commit) {
        Objects.requireNonNull(project, "project is required");
        Objects.requireNonNull(commit, "commit is required");
        return Commit.get(MAPPER, PROJECTS_URLS + project + "/git/commits/" + commit, token);
    }

    /**
     * Retrieve, by hash, an object representing a blob in the repository backing a project. This endpoint allows direct
     * read-only access to the contents of files in a repo. It returns the file in the content
     * field of the response object, encoded according to the format in the encoding field, e.g. base64.
     *
     * @param project the project id
     * @param blob    the blob id
     * @return the {@link Blob}
     */
    public Blob getRepositoryBlob(String project, String blob) {
        Objects.requireNonNull(project, "project is required");
        Objects.requireNonNull(blob, "blob is required");
        return Blob.get(MAPPER, PROJECTS_URLS + project + "/git/blobs/" + blob, token);
    }

}
