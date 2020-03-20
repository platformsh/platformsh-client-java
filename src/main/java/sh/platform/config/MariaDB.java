package sh.platform.config;

import org.mariadb.jdbc.MariaDbDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;
import java.util.function.Supplier;

/**
 * A credential specialization that provides a {@link DataSource} using MySQL database
 */
public class MariaDB extends SQLDatabase implements Supplier<DataSource> {

    static final String DRIVER = "org.mariadb.jdbc.Driver";

    static final String PROVIDER = "mariadb";

    public MariaDB(Map<String, Object> config) {
        super(config);
    }

    @Override
    public DataSource get() {
        try {
            MariaDbDataSource dataSource = new MariaDbDataSource();
            dataSource.setDatabaseName(getPath());
            dataSource.setUser(getUserName());
            dataSource.setPassword(getPassword());
            dataSource.setPort(getPort());
            dataSource.setServerName(getHost());
            return dataSource;
        } catch (SQLException exp) {
            throw new PlatformShException("Invalid credentials: unable to start MariaDB DataSource", exp);
        }
    }
}
