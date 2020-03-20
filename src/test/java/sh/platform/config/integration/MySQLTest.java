package sh.platform.config.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import sh.platform.config.Config;
import sh.platform.config.MySQL;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Integration test on MySQL
 */
@Tag("integration")
public class MySQLTest {

    private GenericContainer mysql =
            new GenericContainer("mysql:latest")
                    .withExposedPorts(3306)
                    .withEnv("MYSQL_ROOT_PASSWORD", "password")
                    .withEnv("MYSQL_DATABASE", "people")
                    .waitingFor(Wait.defaultWaitStrategy());


    @Test
    @DisplayName("Should run the integration MySQL")
    public void shouldRunIntegrationTest() {
        mysql.start();
        System.setProperty("database.host", mysql.getContainerIpAddress());
        System.setProperty("database.port", Integer.toString(mysql.getFirstMappedPort()));
        System.setProperty("database.path", "people");
        System.setProperty("database.username", "root");
        System.setProperty("database.password", "password");

        Config config = new Config();
        MySQL database = config.getCredential("database", MySQL::new);
        DataSource dataSource = database.get();
        try (Connection connection = dataSource.getConnection()) {

            String sql = "CREATE TABLE JAVA_PEOPLE (" +
                    " id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(30) NOT NULL," +
                    "city VARCHAR(30) NOT NULL)";

            final Statement statement = connection.createStatement();
            statement.execute(sql);

            sql = "INSERT INTO JAVA_PEOPLE (name, city) VALUES" +
                    "('Neil Armstrong', 'Moon')," +
                    "('Buzz Aldrin', 'Glen Ridge')," +
                    "('Sally Ride', 'La Jolla')";

            statement.execute(sql);

            sql = "SELECT * FROM JAVA_PEOPLE";
            final ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String city = resultSet.getString("city");
                Assertions.assertNotNull(name);
                Assertions.assertNotNull(city);
            }
            statement.execute("DROP TABLE JAVA_PEOPLE");
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
