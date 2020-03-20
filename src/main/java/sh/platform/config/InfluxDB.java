package sh.platform.config;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

import static org.influxdb.InfluxDBFactory.connect;

/**
 * A credential specialization that provides a {@link org.influxdb.InfluxDB}
 */
public class InfluxDB extends Credential implements Supplier<org.influxdb.InfluxDB> {

    private static final String URL = "http://%s:%d";

    public InfluxDB(Map<String, Object> config) {
        super(config);
    }

    @Override
    public org.influxdb.InfluxDB get() {
        final Optional<String> username = getStringSafe("username");
        final Optional<String> password = getStringSafe("password");
        if (username.isPresent()) {
            return connect(String.format(URL, getHost(), getPort()), username.orElse(null),
                    password.orElse(null));
        } else {
            return connect(String.format(URL, getHost(), getPort()));
        }
    }

    /**
     * Returns a {@link org.influxdb.InfluxDB} from user and password
     *
     * @param username the user
     * @param password the password
     * @return a InfluxDB connection
     */
    public org.influxdb.InfluxDB get(String username, String password) {
        Objects.requireNonNull(username, "username is required");
        Objects.requireNonNull(password, "password is required");
        return connect(String.format(URL, getHost(), getPort()), username, password);
    }

}
