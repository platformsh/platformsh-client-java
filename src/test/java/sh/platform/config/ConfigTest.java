package sh.platform.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import sh.platform.config.provider.JSONBase64;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static sh.platform.config.PlatformVariables.PLATFORM_APPLICATION_NAME;
import static sh.platform.config.PlatformVariables.PLATFORM_APP_DIR;
import static sh.platform.config.PlatformVariables.PLATFORM_MODE;
import static sh.platform.config.PlatformVariables.PLATFORM_PROJECT;
import static sh.platform.config.PlatformVariables.PLATFORM_RELATIONSHIPS;
import static sh.platform.config.PlatformVariables.PLATFORM_ROUTES;
import static sh.platform.config.PlatformVariables.PLATFORM_TREE_ID;
import static sh.platform.config.PlatformVariables.PLATFORM_VARIABLES;

class ConfigTest {

    @Test
    public void shouldReturnVariable() {
        Map<String, String> variables = getVariables();
        Config config = new Config(variables);
        Map<PlatformVariables, String> map = config.toMap();
        assertEquals("dir", map.get(PLATFORM_APP_DIR));
        assertEquals("project", map.get(PLATFORM_PROJECT));
        assertEquals("tree", map.get(PLATFORM_TREE_ID));
        assertEquals("name", map.get(PLATFORM_APPLICATION_NAME));
        assertTrue(config.getRoutes().isEmpty());
        assertTrue(config.getCredentials().isEmpty());
    }

    @Test
    public void shouldReturnIsDedicated() {
        Map<String, String> variables = getVariables();
        variables.put(PLATFORM_MODE.get(), "enterprise");
        Config config = new Config(variables);
        assertTrue(config.isDedicated());
    }

    @Test
    public void shouldNotReturnIsDedicated() {
        Map<String, String> variables = getVariables();
        Config config = new Config(variables);
        assertFalse(config.isDedicated());
    }


    @ParameterizedTest
    @JSONBase64("routes.json")
    public void shouldConvertRoutes(String base64Text) {
        Map<String, String> variables = getVariables();
        variables.put(PLATFORM_ROUTES.get(), base64Text);
        Config config = new Config(variables);
        Map<String, Route> routes = config.getRoutes();
        assertNotNull(routes);
        Route route = routes.get("http://host.com/");
        assertEquals(true, route.isRestrictRobotsEnabled());
        assertEquals("http://{default}/", route.getOriginalUrl());
        assertEquals(false, route.isPrimary());
    }

    @ParameterizedTest
    @JSONBase64("routes3.json")
    public void shouldReturnRoutes(String base64Text) {
        Map<String, String> variables = getVariables();
        variables.put(PLATFORM_ROUTES.get(), base64Text);
        Config config = new Config(variables);
        Map<String, Route> routes = config.getRoutes();
        assertNotNull(routes);
        assertEquals(4, routes.size());
    }

    @ParameterizedTest
    @JSONBase64("routes2.json")
    public void shouldReturnUpstreamRoutes(String base64Text) {
        Map<String, String> variables = getVariables();
        variables.put(PLATFORM_ROUTES.get(), base64Text);
        Config config = new Config(variables);
        List<Route> routes = config.getUpstreamRoutes();
        assertNotNull(routes);
        assertEquals(4, routes.size());

        final String[] upstreams = routes.stream()
                .map(Route::getUpstream)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .sorted()
                .toArray(String[]::new);

        assertArrayEquals(upstreams, new String[]{"client", "conference", "session", "speaker"});
    }

    @ParameterizedTest
    @JSONBase64("routes2.json")
    public void shouldReturnUpstreamRoutesFromName(String base64Text) {
        Map<String, String> variables = getVariables();
        variables.put(PLATFORM_ROUTES.get(), base64Text);
        Config config = new Config(variables);
        List<Route> routes = config.getUpstreamRoutes("client");
        assertNotNull(routes);
        assertEquals(1, routes.size());

        final String[] upstreams = routes.stream()
                .map(Route::getUpstream)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .sorted()
                .toArray(String[]::new);

        assertArrayEquals(upstreams, new String[]{"client"});
    }

    @ParameterizedTest
    @JSONBase64("routes2.json")
    public void shouldReturnEmptyUpstreamRoutes(String base64Text) {
        Map<String, String> variables = getVariables();
        variables.put(PLATFORM_ROUTES.get(), base64Text);
        Config config = new Config(variables);
        List<Route> routes = config.getUpstreamRoutes("empty");
        assertNotNull(routes);
        assertTrue(routes.isEmpty());
    }

    @ParameterizedTest
    @JSONBase64("routes2.json")
    public void shouldReturnPrimaryRoute(String base64Text) {
        Map<String, String> variables = getVariables();
        variables.put(PLATFORM_ROUTES.get(), base64Text);
        Config config = new Config(variables);
        Route route = config.getPrimaryRoute().get();

        assertEquals("conference", route.getId().get());
        assertEquals("https://{default}/conferences", route.getOriginalUrl());
        assertEquals("upstream", route.getType());
        assertEquals("conference", route.getUpstream().get());

    }

    @ParameterizedTest
    @JSONBase64("routes4.json")
    public void shouldReturnById(String base64Text) {
        Map<String, String> variables = getVariables();
        variables.put(PLATFORM_ROUTES.get(), base64Text);
        Config config = new Config(variables);
        Optional<Route> route = config.getRoute("upstream");
        assertNotNull(route);
        assertTrue(route.isPresent());
        assertTrue(route.map(Route::isPrimary).orElse(false));
    }

    @ParameterizedTest
    @JSONBase64("routes4.json")
    public void shouldReturnEmptyById(String base64Text) {
        Map<String, String> variables = getVariables();
        variables.put(PLATFORM_ROUTES.get(), base64Text);
        Config config = new Config(variables);
        Optional<Route> route = config.getRoute("not_found");
        assertNotNull(route);
        assertFalse(route.isPresent());
    }

    @ParameterizedTest
    @JSONBase64("variables.json")
    public void shouldConvertVariables(String base64Text) {
        Map<String, String> variables = getVariables();
        variables.put(PLATFORM_VARIABLES.get(), base64Text);
        Config config = new Config(variables);
        Map<String, String> map = config.getVariables();
        assertEquals("8", map.get("java.version"));
        assertEquals("value", map.get("variable"));
    }

    @ParameterizedTest
    @JSONBase64("service.json")
    public void shouldConvertServices(String base64Text) {
        Map<String, String> variables = getVariables();
        variables.put(PLATFORM_RELATIONSHIPS.get(), base64Text);
        Config config = new Config(variables);
        Map<String, Credential> services = config.getCredentials();
        Assertions.assertFalse(services.isEmpty());
        Credential database = services.get("database");
        assertNotNull(database);

    }

    private Map<String, String> getVariables() {
        Map<String, String> variables = new HashMap<>();
        variables.put("ignore", "value");
        variables.put("ignore2", "value");
        variables.put(PLATFORM_APP_DIR.get(), "dir");
        variables.put(PLATFORM_PROJECT.get(), "project");
        variables.put(PLATFORM_TREE_ID.get(), "tree");
        variables.put(PLATFORM_APPLICATION_NAME.get(), "name");
        return variables;
    }


}