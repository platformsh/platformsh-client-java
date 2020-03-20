package sh.platform.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import sh.platform.config.provider.JSONBase64;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MapConverterTest {

    @ParameterizedTest
    @JSONBase64("service.json")
    public void shouldConvertService(String base64Text) {
        Map<String, List<Map<String, Object>>> service = MapConverter.toService(base64Text);
        assertNotNull(service);

        Map<String, Object> database = service.get("database").get(0);
        assertEquals("database.internal", database.get("host"));
        assertEquals("246.0.97.91", database.get("ip"));
        assertEquals("main", database.get("path"));
        assertEquals(3306, ((Number) database.get("port")).intValue());
        assertEquals("mysql", database.get("scheme"));
        assertEquals("user", database.get("username"));

        Map<String, Object> redis = service.get("redis").get(0);
        assertEquals("redis.internal", redis.get("host"));
        assertEquals("246.0.97.88", redis.get("ip"));
    }

    @ParameterizedTest
    @JSONBase64("routes.json")
    public void shouldConvertRoutes(String base64Text) {
        Map<String, Route> routes = MapConverter.toRoute(base64Text);
        Assertions.assertNotNull(routes);
        Route route = routes.get("http://host.com/");
        assertEquals(true, route.isRestrictRobotsEnabled());
        assertEquals("http://{default}/", route.getOriginalUrl());
        assertEquals(false, route.isPrimary());
    }

}