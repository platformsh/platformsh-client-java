package sh.platform.config.provider;


import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;

import java.lang.reflect.Type;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ProviderTest {

    @ParameterizedTest
    @JSONBase64("service.json")
    public void test(String base64Text) {
        Assertions.assertNotNull(base64Text);
        Type type = new HashMap<String, List<Map<String, Object>>>() {
        }.getClass().getGenericSuperclass();
        String text = new String(Base64.getDecoder().decode(base64Text), UTF_8);

        Gson gson = new Gson();
        Map<String, List<Map<String, Object>>> json = gson.fromJson(text, type);
        Assertions.assertNotNull(json);

    }
}
