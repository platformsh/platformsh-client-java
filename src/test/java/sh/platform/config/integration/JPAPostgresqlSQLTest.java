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
 * Integration test between PostgreSQL and Hibernate
 */
@Tag("integration")
public class JPAPostgresqlSQLTest {

    private GenericContainer postgres =
            new GenericContainer("postgres:latest")
                    .withExposedPorts(5432)
                    .withEnv("POSTGRES_PASSWORD", "password")
                    .withEnv("POSTGRES_DB", "people")
                    .waitingFor(Wait.defaultWaitStrategy());


    @Test
    @DisplayName("Should run the integration between PostgreSQL and Hibernate")
    public void shouldRunIntegrationTest() {
        postgres.start();
        System.setProperty("database.host", postgres.getContainerIpAddress());
        System.setProperty("database.port", Integer.toString(postgres.getFirstMappedPort()));
        System.setProperty("database.path", "people");
        System.setProperty("database.username", "postgres");
        System.setProperty("database.password", "password");

        Config config = new Config();
        final JPA credential = config.getCredential("database", JPA::new);
        try {
            final EntityManagerFactory managerFactory = credential.getPostgreSQL("jpa-example");
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
            Assertions.assertTrue(false, "An error when execute PostgresSQL");
        } finally {
            System.clearProperty("database.host");
            System.clearProperty("database.port");
            System.clearProperty("database.path");
            System.clearProperty("database.username");
            System.clearProperty("database.password");
            postgres.close();
        }

    }
}
