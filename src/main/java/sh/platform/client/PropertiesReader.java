package sh.platform.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toMap;

enum PropertiesReader {

    INSTANCE;

    private final Map<String, String> properties;

    PropertiesReader() {
        final InputStream stream = PropertiesReader.class.getClassLoader().getResourceAsStream("platform.properties");
        if (stream != null) {
            try {
                Properties properties = new Properties();
                properties.load(stream);
                this.properties = properties.entrySet().stream()
                        .collect(toMap(k -> k.getKey().toString(), v -> v.getValue().toString()));
            } catch (IOException exp) {
                throw new PlatformClientException("There is an issue to load the properties file", exp);
            }
        } else {
            this.properties = Collections.emptyMap();
        }
    }

    public String getAuthUrl() {
        return properties.get("account.url");
    }

    public String getServiceUrl() {
        return properties.get("service.url");
    }

    String get(String key) {
        return properties.get(key);
    }

    String get(Supplier<String> key) {
        return properties.get(key.get());
    }
}
