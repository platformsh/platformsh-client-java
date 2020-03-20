package sh.platform.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import static java.util.Collections.emptyMap;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static sh.platform.config.PlatformVariables.PLATFORM_APPLICATION_NAME;
import static sh.platform.config.PlatformVariables.PLATFORM_APP_DIR;
import static sh.platform.config.PlatformVariables.PLATFORM_BRANCH;
import static sh.platform.config.PlatformVariables.PLATFORM_DOCUMENT_ROOT;
import static sh.platform.config.PlatformVariables.PLATFORM_ENVIRONMENT;
import static sh.platform.config.PlatformVariables.PLATFORM_MODE;
import static sh.platform.config.PlatformVariables.PLATFORM_PROJECT;
import static sh.platform.config.PlatformVariables.PLATFORM_PROJECT_ENTROPY;
import static sh.platform.config.PlatformVariables.PLATFORM_ROUTES;
import static sh.platform.config.PlatformVariables.PLATFORM_SMTP_HOST;
import static sh.platform.config.PlatformVariables.PLATFORM_TREE_ID;
import static sh.platform.config.PlatformVariables.PLATFORM_VARIABLES;

/**
 * The object that provides access to the Platform.sh environment.
 */
public class Config {

    private static final Predicate<Route> IS_UPSTREAM = r -> "upstream".equals(r.getType());
    private final Map<String, String> variables;

    private final Map<String, Route> routes;

    private final Map<PlatformVariables, String> envs;

    private final Credentials credentials;

    Config(Map<String, String> envs) {
        this.variables = ofNullable(envs.get(PLATFORM_VARIABLES.get()))
                .map(MapConverter::toVariable).orElse(emptyMap());
        this.routes = ofNullable(envs.get(PLATFORM_ROUTES.get()))
                .map(MapConverter::toRoute).orElse(emptyMap());
        this.envs = PlatformVariables.toMap(envs);
        this.credentials = new Credentials(ServiceConverter.INSTANCE.apply(envs));

    }

    /**
     * Creates a new instance from the environments values that come from {@link PlatformVariables}.
     */
    public Config() {
        this(getEnvironments());
    }

    /**
     * @return object which keys are variables names and values are variable values from the the: PLATFORM_VARIABLES
     * OS environment
     */
    public Map<String, String> getVariables() {
        return Collections.unmodifiableMap(variables);
    }

    /**
     * @return the available credentials
     */
    public Map<String, Credential> getCredentials() {
        return Collections.unmodifiableMap(credentials.get());
    }

    /**
     * @return describes the routes
     */
    public Map<String, Route> getRoutes() {
        return Collections.unmodifiableMap(routes);
    }

    /**
     * Returns the single route that is marked primary
     *
     * @return The single route that is marked as primary or {@link Optional#empty()}
     */
    public Optional<Route> getPrimaryRoute() {
        return routes.values().stream()
                .filter(Route::isPrimary)
                .findFirst();
    }

    /**
     * Returns the single route from the id
     *
     * @param id the route id
     * @return The single route that is marked as primary or {@link Optional#empty()}
     */
    public Optional<Route> getRoute(String id) {
        return routes.values().stream()
                .filter(r -> id.equals(r.getId().orElse(null)))
                .findFirst();
    }

    /**
     * Returns all non-redirect routes
     *
     * @return Returns all non-redirect routes
     */
    public List<Route> getUpstreamRoutes() {
        return routes.values().stream()
                .filter(IS_UPSTREAM)
                .collect(collectingAndThen(toList(),
                        Collections::unmodifiableList));
    }

    /**
     * Returns all non-redirect routes whose upstream is the name app.
     *
     * @param name the application name
     * @return the list
     * @throws NullPointerException when name is null
     */
    public List<Route> getUpstreamRoutes(String name) {
        Objects.requireNonNull(name, "name is required");
        Predicate<Route> nameEquals = r -> name.equals(r.getUpstream().orElse(""));

        return routes.values().stream()
                .filter(IS_UPSTREAM.and(nameEquals))
                .collect(collectingAndThen(toList(),
                        Collections::unmodifiableList));
    }

    /**
     * @return @{@link PlatformVariables#PLATFORM_APPLICATION_NAME}
     */
    public String getApplicationName() {
        return toString(PLATFORM_APPLICATION_NAME);
    }

    /**
     * Returns <b>true</b> if the application is on dedicated cluster at Platform.sh.
     *
     * @return <b>true</b> if the application is on dedicated cluster at Platform.sh.
     */
    public boolean isDedicated() {
        return "enterprise".equals(getSafeString(PLATFORM_MODE).orElse(""));
    }

    /**
     * @return @{@link PlatformVariables#PLATFORM_APP_DIR}
     */
    public String getAppDir() {
        return toString(PLATFORM_APP_DIR);
    }

    /**
     * @return @{@link PlatformVariables#PLATFORM_PROJECT}
     */
    public String getProject() {
        return toString(PLATFORM_PROJECT);
    }

    /**
     * @return @{@link PlatformVariables#PLATFORM_TREE_ID}
     */
    public String getTreeID() {
        return toString(PLATFORM_TREE_ID);
    }

    /**
     * @return @{@link PlatformVariables#PLATFORM_PROJECT_ENTROPY}
     */
    public String getProjectEntropy() {
        return toString(PLATFORM_PROJECT_ENTROPY);
    }

    /**
     * @return @{@link PlatformVariables#PLATFORM_BRANCH}
     */
    public String getBranch() {
        return toString(PLATFORM_BRANCH);
    }

    /**
     * @return @{@link PlatformVariables#PLATFORM_DOCUMENT_ROOT}
     */
    public String getDocumentRoot() {
        return toString(PLATFORM_DOCUMENT_ROOT);
    }

    /**
     * @return @{@link PlatformVariables#PLATFORM_SMTP_HOST}
     */
    public String getSmtpHost() {
        return toString(PLATFORM_SMTP_HOST);
    }

    /**
     * @return @{@link PlatformVariables#PLATFORM_ENVIRONMENT}
     */
    public String getEnvironment() {
        return toString(PLATFORM_ENVIRONMENT);
    }


    /**
     * @return values to {@link Map}
     */
    public Map<PlatformVariables, String> toMap() {
        return Collections.unmodifiableMap(envs);
    }

    /**
     * A credential from a key
     *
     * @param key the key
     * @return a credential from the key
     */
    public Credential getCredential(String key) {
        Objects.requireNonNull(key, "key is required");
        return credentials.getCredential(key);
    }

    /**
     * A credential from a key
     *
     * @param key the key
     * @return a credential from the key
     */
    public <T> T getCredential(String key, CredentialFormatter<T> formatter) {
        Objects.requireNonNull(key, "key is required");
        Objects.requireNonNull(formatter, "formatter is required");
        return credentials.getCredential(key, formatter);
    }

    private static Map<String, String> getEnvironments() {
        Map<String, String> envs = new HashMap<>(System.getenv());
        return envs;
    }

    private String toString(PlatformVariables variable) {
        return getSafeString(variable)
                .orElseThrow(() -> new PlatformShException("Variable does not exist on environment: " + variable.get()));
    }

    private Optional<String> getSafeString(PlatformVariables variable) {
        return Optional
                .ofNullable(envs.get(variable))
                .map(Object::toString);
    }
}
