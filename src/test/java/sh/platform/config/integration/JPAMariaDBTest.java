package sh.platform.config.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import sh.platform.config.Config;
import sh.platform.config.JPA;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Integration test between MariaDB and JPA
 */
@Tag("integration")
public class JPAMariaDBTest {

    private GenericContainer mariaDB =
            new GenericContainer("mariadb:latest")
                    .withExposedPorts(3306)
                    .withEnv("MYSQL_ROOT_PASSWORD", "password")
                    .withEnv("MYSQL_DATABASE", "people")
                    .waitingFor(Wait.defaultWaitStrategy());


    @Test
    @DisplayName("Should run the integration test between MYSQL and hibernate")
    public void shouldRunIntegrationTest() {
        mariaDB.start();
        System.setProperty("database.host", mariaDB.getContainerIpAddress());
        System.setProperty("database.port", Integer.toString(mariaDB.getFirstMappedPort()));
        System.setProperty("database.path", "people");
        System.setProperty("database.username", "root");
        System.setProperty("database.password", "password");

        Config config = new Config();
        final JPA credential = config.getCredential("database", JPA::new);
        try {
            final EntityManagerFactory managerFactory = credential.getMariaDB("jpa-example");
            Address address = new Address();
            address.setId(ThreadLocalRandom.current().nextInt());
            address.setCity("Dhaka");
            address.setCountry("Bangladesh");
            address.setPostcode("1000");
            address.setStreet("Poribagh");

            EntityManager entityManager = managerFactory.createEntityManager();
            final EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(address);
            transaction.commit();

            final List<Address> addresses = entityManager.createQuery("select a from Address a").getResultList();
            Assertions.assertFalse(addresses.isEmpty());
            entityManager.close();
            managerFactory.close();
        } catch (Exception exp) {
            Assertions.assertTrue(false, "An error when execute MySQL");
        } finally {
            System.clearProperty("database.host");
            System.clearProperty("database.port");
            System.clearProperty("database.path");
            System.clearProperty("database.username");
            System.clearProperty("database.password");
            mariaDB.close();
        }

    }
}
