package sh.platform.config;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * Access information for an individual service that comes from {@link PlatformVariables#PLATFORM_RELATIONSHIPS}
 */
public class Credential {

    protected final Map<String, Object> config;

    Credential(Map<String, Object> config) {
        this.config = config;
    }

    protected Map<String, Object> getConfig() {
        return config;
    }

    public String getName() {
        return getString("service");
    }

    public String getScheme() {
        return getString("scheme");
    }

    public String getIp() {
        return getString("ip");
    }

    public String getType() {
        return getString("type");
    }

    public int getPort() {
        return getInt("port");
    }

    public String getHost() {
        return getString("host");
    }

    public String getCluster() {
        return getString("cluster");
    }

    public Map<String, Object> toMap() {
        return Collections.unmodifiableMap(config);
    }

    public String getRelationship() {
        return getString("rel");
    }


    protected String getString(String key) {
        return getStringSafe(key)
                .orElseThrow(() -> new PropertyNotFoundException("Property not found: " + key));
    }

    protected Integer getInt(String key) {
        return getIntSafe(key)
                .orElseThrow(() -> new PropertyNotFoundException("Property not found: " + key));
    }

    protected Optional<String> getStringSafe(String key) {
        return Optional
                .ofNullable(config.get(key))
                .map(Object::toString);
    }

    protected Optional<Integer> getIntSafe(String key) {
        return Optional
                .ofNullable(config.get(key))
                .map(Object::toString)
                .map(Double::parseDouble)
                .map(Double::intValue);
    }

    public String toString() {
        return "Credential{" +
                "config=" + config +
                '}';
    }
}
