package sh.platform.client.environment;

import sh.platform.client.AuthToken;
import sh.platform.client.AuthUser;
import sh.platform.client.PlatformClient;
import sh.platform.client.project.ProjectAdministration;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import static sh.platform.client.PlatformClient.MAPPER;

/**
 * On Platform.sh, an environment encompasses a single instance of your entire application stack,
 * the services used by the application, the application's data storage, and the environment's backups.
 * <p>
 * In general, an environment represents a single branch or merge request in the Git
 * repository backing a project. It is a virtual cluster of read-only application and
 * service containers with read-write mounts for application and service data.
 * <p>
 * On Platform.sh, the master branch is your production environment—thus, merging changes
 * to master will put those changes to production.
 */
public class Environments {

    private static final Logger LOGGER = Logger.getLogger(ProjectAdministration.class.getName());

    private final AuthUser user;

    private final AuthToken token;

    private final String environmentsURL;

    @Deprecated
    /**
     * Use {@link PlatformClient#getProjectAdministration()} instead.
     */
    public Environments(AuthUser user, AuthToken token, String serviceURL) {
        this.user = user;
        this.token = token;
        this.environmentsURL = serviceURL + "projects/";
    }

    /**
     * Redeploy an environment
     *
     * @param projectId   the project id
     * @param environment the environment id
     * @return the {@link EnvironmentStatus} with the result
     */
    public EnvironmentStatus redeploy(String projectId, String environment) {
        Objects.requireNonNull(projectId, "projectId is required");
        Objects.requireNonNull(environment, "environment is required");
        return null;
    }

    /**
     * Retrieve a list of a project's existing environments and the information associated with each environment.
     *
     * @param projectId the project id
     * @return the {@link List} of {@link Environment}
     */
    public List<Environment> getEnvironments(String projectId) {
        Objects.requireNonNull(projectId, "id is required");
        return Environment.of(MAPPER, environmentsURL + projectId + "/environments", token);
    }
}
