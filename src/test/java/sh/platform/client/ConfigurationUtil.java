package sh.platform.client;


import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Supplier;
import java.util.logging.Logger;

import static java.util.stream.Collectors.toMap;

public enum  ConfigurationUtil {

    INSTANCE;


    private Map<String, String> settings;

    private ConfigurationUtil() {
        this.settings = new HashMap<>();
        this.settings.putAll(loadFromFile());
        this.settings.putAll(map(System.getProperties()));
        this.settings.putAll(System.getenv());
    }

    public String get(String key) {
        return settings.get(key);
    }

    public String get(Supplier<String> key) {
        return settings.get(key.get());
    }

    private Map<String, String> loadFromFile() {
        Logger logger = Logger.getLogger(ConfigurationUtil.class.getName());
        logger.info("Check the test file");
        final InputStream stream = ConfigurationUtil.class.getClassLoader().getResourceAsStream("test.properties");
        if (stream != null) {
            logger.info("Loading file from test.properties");
            try {
                Properties properties = new Properties();
                properties.load(stream);
                return map(properties);
            } catch (IOException exp) {
                throw new PlatformClientException("There is an issue to load the properties file", exp);
            }
        }
        return Collections.emptyMap();
    }

    private Map<String, String> map(Properties properties) {
        return properties.entrySet().stream()
                .collect(toMap(k -> k.getKey().toString(), v -> v.getValue().toString()));
    }
}
