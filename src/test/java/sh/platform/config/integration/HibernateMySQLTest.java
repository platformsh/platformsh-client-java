package sh.platform.config.integration;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import sh.platform.config.Config;
import sh.platform.config.Hibernate;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.hibernate.cfg.Configuration;

import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Integration test between MySQL and Hibernate.
 */
@Tag("integration")
public class HibernateMySQLTest {

    private GenericContainer mysql =
            new GenericContainer("mysql:latest")
                    .withExposedPorts(3306)
                    .withEnv("MYSQL_ROOT_PASSWORD", "password")
                    .withEnv("MYSQL_DATABASE", "people")
                    .waitingFor(Wait.defaultWaitStrategy());


    @Test
    @DisplayName("Should run the integration test between MYSQL and Hibernate")
    public void shouldRunIntegrationTest() {
        mysql.start();
        System.setProperty("database.host", mysql.getContainerIpAddress());
        System.setProperty("database.port", Integer.toString(mysql.getFirstMappedPort()));
        System.setProperty("database.path", "people");
        System.setProperty("database.username", "root");
        System.setProperty("database.password", "password");

        Config config = new Config();
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Address.class);
        final Hibernate credential = config.getCredential("database", Hibernate::new);
        final SessionFactory sessionFactory = credential.getMySQL(configuration);
        try (Session session = sessionFactory.openSession()) {

            Transaction transaction = session.beginTransaction();
            Address address = new Address();
            address.setId(ThreadLocalRandom.current().nextInt());
            address.setCity("Dhaka MySQL");
            address.setCountry("Bangladesh");
            address.setPostcode("1000");
            address.setStreet("Poribagh");
            session.save(address);
            transaction.commit();

            final List<Address> addresses = session.createQuery("from Address", Address.class).list();
            assertFalse(addresses.isEmpty());
        } catch (Exception exp) {
            Assertions.assertTrue(false, "An error when execute MySQL");
        } finally {
            System.clearProperty("database.host");
            System.clearProperty("database.port");
            System.clearProperty("database.path");
            System.clearProperty("database.username");
            System.clearProperty("database.password");
            mysql.close();
        }

    }
}
