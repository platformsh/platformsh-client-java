package sh.platform.config.integration;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import sh.platform.config.Config;
import sh.platform.config.PostgreSQL;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Integration test on PostgreSQL
 */
@Tag("integration")
public class PostgreSQLTest {

    private GenericContainer postgres =
            new GenericContainer("postgres:latest")
                    .withExposedPorts(5432)
                    .withEnv("POSTGRES_PASSWORD", "password")
                    .withEnv("POSTGRES_DB", "people")
                    .waitingFor(Wait.defaultWaitStrategy());


    @Test
    @DisplayName("Should run the integration PostgreSQL")
    public void shouldRunIntegrationTest() {
        postgres.start();
        System.setProperty("database.host", postgres.getContainerIpAddress());
        System.setProperty("database.port", Integer.toString(postgres.getFirstMappedPort()));
        System.setProperty("database.path", "people");
        System.setProperty("database.username", "postgres");
        System.setProperty("database.password", "password");

        Config config = new Config();
        PostgreSQL database = config.getCredential("database", PostgreSQL::new);
        DataSource dataSource = database.get();
        try (Connection connection = dataSource.getConnection()) {

            String sql = "CREATE TABLE JAVA_FRAMEWORKS (" +
                    " id SERIAL PRIMARY KEY," +
                    "name VARCHAR(30) NOT NULL)";

            final Statement statement = connection.createStatement();
            statement.execute(sql);

            sql = "INSERT INTO JAVA_FRAMEWORKS (name) VALUES" +
                    "('Spring')," +
                    "('Jakarta EE')," +
                    "('Eclipse JNoSQL')";

            statement.execute(sql);

            // Show table.
            sql = "SELECT * FROM JAVA_FRAMEWORKS";
            final ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Assertions.assertNotNull(id);
                Assertions.assertNotNull(name);
            }
            statement.execute("DROP TABLE JAVA_FRAMEWORKS");
        } catch (Exception exp) {
            Assertions.assertTrue(false, "An error when execute PostgreSQL");
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
