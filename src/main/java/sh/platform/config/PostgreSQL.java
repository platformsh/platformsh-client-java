package sh.platform.config;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.util.Map;
import java.util.function.Supplier;

/**
 * A credential specialization that provides a {@link DataSource} using PostgreSQL database
 */
public class PostgreSQL extends SQLDatabase implements Supplier<DataSource> {

    static final String DRIVER = "org.postgresql.Driver";
    static final String PROVIDER = "postgresql";

    public PostgreSQL(Map<String, Object> config) {
        super(config);
    }

    @Override
    public DataSource get() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setDatabaseName(getPath());
        dataSource.setUser(getUserName());
        dataSource.setPassword(getPassword());
        dataSource.setPortNumber(getPort());
        dataSource.setServerName(getHost());
        return dataSource;
    }
}