package sh.platform.config;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A credential specialization that provides information to a SQL database.
 */
public class SQLDatabase extends Credential {

    private static final String URL = "jdbc:%s://%s:%d/%s";

    public SQLDatabase(Map<String, Object> config) {
        super(Objects.requireNonNull(config, "config is required"));
    }

    public String getUserName() {
        return getString("username");
    }

    public Optional<String> getFragment() {
        return getStringSafe("fragment");
    }

    public boolean isPublic() {
        return false;
    }

    public Map<String, String> getQuery() {
        Map<String, Object> config = getConfig();
        Map<String, Object> query = (Map<String, Object>) config.get("query");
        if (query == null) {
            return Collections.emptyMap();
        }
        return query.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        e -> e.getValue().toString()));
    }

    public String getPath() {
        return getString("path");
    }

    public String getPassword() {
        return getString("password");
    }

    public String getJDBCURL(String provider) {
        Objects.requireNonNull(provider, "provider is required");
        return String.format(URL, provider, getHost(), getPort(), getPath());
    }
}
