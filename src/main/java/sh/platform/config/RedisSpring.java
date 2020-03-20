package sh.platform.config;

import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import java.util.Map;
import java.util.function.Supplier;

/**
 * A credential specialization that provides a {@link JedisConnectionFactory}
 */
public class RedisSpring extends Credential implements Supplier<JedisConnectionFactory> {

    public RedisSpring(Map<String, Object> config) {
        super(config);
    }

    @Override
    public JedisConnectionFactory get() {

        String host = getStringSafe("host").orElse(null);
        Integer port = getInt("port");
        String password = getStringSafe("password").orElse(null);

        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(host, port);
        configuration.setPassword(password);
        return new JedisConnectionFactory(configuration);
    }
}
