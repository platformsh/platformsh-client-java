package sh.platform.config.integration;

import net.spy.memcached.MemcachedClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import sh.platform.config.Config;
import sh.platform.config.Memcached;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Integration test on Memcached
 */
@Tag("integration")
public class MemcachedTest {

    private final GenericContainer memcached =
            new GenericContainer("memcached:latest")
                    .withExposedPorts(11211)
                    .waitingFor(Wait.defaultWaitStrategy());

    @Test
    @DisplayName("Should run the integration on Memcached")
    public void shouldRunIntegrationTest() {
        this.memcached.start();
        System.setProperty("memcached.host", memcached.getContainerIpAddress());
        System.setProperty("memcached.port", Integer.toString(memcached.getFirstMappedPort()));
        Config config = new Config();

        Memcached memcached = config.getCredential("memcached", Memcached::new);

        final MemcachedClient client = memcached.get();

        String key = "cloud";
        String value = "platformsh";

        client.set(key, 0, value);

        Object test = client.get(key);
        assertEquals(value, test);
        this.memcached.stop();

    }
}
