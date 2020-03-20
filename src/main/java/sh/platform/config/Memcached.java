package sh.platform.config;

import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.MemcachedClient;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import static java.util.Collections.singletonList;
import static net.spy.memcached.auth.AuthDescriptor.typical;

/**
 * A credential specialization that provides information a {@link MemcachedClient}
 */
public class Memcached extends Credential implements Supplier<MemcachedClient> {

    public Memcached(Map<String, Object> config) {
        super(config);
    }

    @Override
    public MemcachedClient get() {
        ConnectionFactoryBuilder factoryBuilder = new ConnectionFactoryBuilder();

        final Optional<String> username =getStringSafe("username");
        final Optional<String> password = getStringSafe("password");

        username.ifPresent(u -> factoryBuilder.setAuthDescriptor(typical(u, password.orElse(null))));
        InetSocketAddress address = new InetSocketAddress(getHost(), getPort());

        try {
            return new MemcachedClient(factoryBuilder.build(), singletonList(address));
        } catch (IOException exp) {
            throw new PlatformShException("An error when read credential to start Memcached client", exp);
        }
    }
}
