package sh.platform.config;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.function.Supplier;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toMap;

/**
 * The class who manage the Credentials from {@link PlatformVariables#PLATFORM_RELATIONSHIPS}.
 * It has a list of {@link Credential}
 */
final class Credentials implements Supplier<Map<String, Credential>> {

    private static final String SERVICE_JSON = "platform.properties";

    private final Map<String, Credential> credentials;

    private final Map<String, Object> properties;

    Credentials(Map<String, Credential> credentials) {
        this.credentials = credentials;
        this.properties = properties();
    }

    @Override
    public Map<String, Credential> get() {
        return credentials;
    }

    Credential getCredential(String key) {
        if (isTestEnable()) {
            return getProperties(key);
        } else {
            return ofNullable(credentials.get(key))
                    .orElseThrow(() -> new PlatformShException("Credential does not found: " + key));
        }
    }

    <T> T getCredential(String key, CredentialFormatter<T> formatter) {
        return ofNullable(getCredential(key))
                .map(Credential::toMap)
                .map(formatter::apply)
                .orElseThrow(() -> new PlatformShException("Credential does not found: " + key));
    }

    private Credential getProperties(String key) {
        final Map<String, Object> propertiesMap = properties.entrySet().stream()
                .filter(e -> e.getKey().startsWith(key))
                .collect(toMap(e -> e.getKey().replace(key.concat("."), "")
                        , Map.Entry::getValue));
        return new Credential(propertiesMap);
    }

    private boolean isTestEnable() {
        return credentials.isEmpty();
    }

    private static Map<String, Object> properties() {
        try {
            InputStream stream = MapConverter.class.getClassLoader().getResourceAsStream(SERVICE_JSON);
            Properties properties = new Properties();
            if (stream != null) {
                properties.load(stream);
            }
            Properties javaProperties = System.getProperties();
            javaProperties.keySet().stream().forEach(k -> properties.put(k, javaProperties.get(k)));
            return properties.keySet().stream()
                    .collect(toMap(Object::toString, properties::get));
        } catch (Exception exp) {
            throw new FallbackException("An error when load the default configuration", exp);
        }
    }
}
