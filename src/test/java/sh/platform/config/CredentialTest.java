package sh.platform.config;

import org.junit.jupiter.params.ParameterizedTest;
import sh.platform.config.provider.JSONBase64;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CredentialTest {

    @ParameterizedTest
    @JSONBase64("mysql.json")
    public void shouldConvertService(String base64Text) {
        Map<String, List<Map<String, Object>>> map = MapConverter.toService(base64Text);
        Map<String, Object> database = map.get("database").get(0);
        Credential credential = new Credential(database);
        assertEquals("rjify4yjcwxaa-master-7rqtwti", credential.getCluster());
        assertEquals("mysql.internal", credential.getHost());
        assertEquals("169.254.150.231", credential.getIp());
        assertEquals("mysql", credential.getName());
        assertEquals("mysql", credential.getRelationship());
        assertEquals("mysql", credential.getScheme());
        assertEquals("mysql:10.2", credential.getType());
        assertEquals(3306, credential.getPort());
        assertNotNull(credential.toMap());

    }

}