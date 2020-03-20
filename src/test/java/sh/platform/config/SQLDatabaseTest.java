package sh.platform.config;

import org.junit.jupiter.params.ParameterizedTest;
import sh.platform.config.provider.JSONBase64;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SQLDatabaseTest {


    @ParameterizedTest
    @JSONBase64("mysql.json")
    public void shouldConvertService(String base64Text) {
        Map<String, List<Map<String, Object>>> map = MapConverter.toService(base64Text);
        Map<String, Object> database = map.get("database").get(0);
        SQLDatabase service = new SQLDatabase(database);
        assertEquals("rjify4yjcwxaa-master-7rqtwti", service.getCluster());
        assertEquals("mysql.internal", service.getHost());
        assertEquals("169.254.150.231", service.getIp());
        assertEquals("mysql", service.getName());
        assertEquals("mysql", service.getRelationship());
        assertEquals("mysql", service.getScheme());
        assertEquals("mysql:10.2", service.getType());
        assertEquals(3306, service.getPort());
        assertNotNull(service.toMap());
        assertEquals("user", service.getUserName());
        assertEquals("main", service.getPath());
        assertEquals("", service.getPassword());
        assertEquals(Collections.singletonMap("is_master", "true"), service.getQuery());
    }

    @ParameterizedTest
    @JSONBase64("mysql.json")
    public void shouldReturnMySQLURL(String base64Text) {
        Map<String, List<Map<String, Object>>> map = MapConverter.toService(base64Text);
        Map<String, Object> database = map.get("database").get(0);
        SQLDatabase service = new SQLDatabase(database);
        assertEquals("jdbc:mysql://mysql.internal:3306/main", service.getJDBCURL("mysql"));
    }

    @ParameterizedTest
    @JSONBase64("postgre.json")
    public void shouldReturnPostgresSQLURL(String base64Text) {
        Map<String, List<Map<String, Object>>> map = MapConverter.toService(base64Text);
        Map<String, Object> database = map.get("database").get(0);
        SQLDatabase service = new SQLDatabase(database);
        assertEquals("jdbc:postgresql://postgresql.internal:5432/main", service.getJDBCURL("postgresql"));
    }
}