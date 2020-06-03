package sh.platform.client.environment;

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
}
