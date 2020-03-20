package sh.platform.config;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * A credential specialization that provides a {@link SessionFactory}.
 */
public class Hibernate extends Credential {

    public Hibernate(Map<String, Object> config) {
        super(config);
    }

    /**
     * Create and return an SessionFactory using MYSQL driver.
     *
     * @param configuration the hibernate properties configuration
     * @return a {@link SessionFactory}
     * @throws NullPointerException when properties is null
     */
    public SessionFactory getMySQL(Configuration configuration) {
        Objects.requireNonNull(configuration, "configuration is required");
        return getSessionFactory(configuration, MySQL.DRIVER, MySQL.PROVIDER);
    }

    /**
     * Create and return an SessionFactory using MYSQL driver.
     *
     * @return a {@link SessionFactory}
     */
    public SessionFactory getMySQL() {
        return getSessionFactory(new Configuration(), MySQL.DRIVER, MySQL.PROVIDER);
    }

    /**
     * Create and return an SessionFactory using MariaDB driver.
     *
     * @param configuration the hibernate properties configuration
     * @return a {@link SessionFactory}
     * @throws NullPointerException when properties is null
     */
    public SessionFactory getMariaDB(Configuration configuration) {
        Objects.requireNonNull(configuration, "configuration is required");
        return getSessionFactory(configuration, MariaDB.DRIVER, MariaDB.PROVIDER);
    }

    /**
     * Create and return an SessionFactory using MariaDB driver.
     *
     * @return a {@link SessionFactory}
     */
    public SessionFactory getMariaDB() {
        return getSessionFactory(new Configuration(), MariaDB.DRIVER, MariaDB.PROVIDER);
    }


    /**
     * Create and return an SessionFactory using PostgreSQL driver.
     *
     * @param configuration the hibernate properties configuration
     * @return {@link SessionFactory}
     * @throws NullPointerException when properties is null
     */
    public SessionFactory getPostgreSQL(Configuration configuration) {
        Objects.requireNonNull(configuration, "configuration is required");
        return getSessionFactory(configuration, PostgreSQL.DRIVER, PostgreSQL.PROVIDER);
    }

    /**
     * Create and return an SessionFactory using PostgreSQL driver.
     *
     * @return {@link SessionFactory}
     * @throws NullPointerException when properties is null
     */
    public SessionFactory getPostgreSQL() {
        return getSessionFactory(new Configuration(), PostgreSQL.DRIVER, PostgreSQL.PROVIDER);
    }

    private SessionFactory getSessionFactory(Configuration configuration, String driver, String provider) {
        SQLDatabase sqlDatabase = new SQLDatabase(config);

        Properties properties = configuration.getProperties();
        properties.put(Environment.DRIVER, driver);
        properties.put(Environment.URL, sqlDatabase.getJDBCURL(provider));
        properties.put(Environment.USER, sqlDatabase.getUserName());
        properties.put(Environment.PASS, sqlDatabase.getPassword());
        properties.putIfAbsent(Environment.HBM2DDL_AUTO, "create-drop");
        Properties settings = new Properties();
        settings.putAll(properties);
        configuration.setProperties(settings);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        return configuration.buildSessionFactory(serviceRegistry);
    }
}
