package sh.platform.config;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.util.Map;
import java.util.function.Supplier;

/**
 * A credential specialization that provides a {@link DataSource} using MySQL database
 */
public class MySQL extends SQLDatabase implements Supplier<DataSource> {

    static final String DRIVER = "com.mysql.jdbc.Driver";

    static final String PROVIDER =  "mysql";

    public MySQL(Map<String, Object> config) {
        super(config);
    }

    @Override
    public DataSource get() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setDatabaseName(getPath());
        dataSource.setUser(getUserName());
        dataSource.setPassword(getPassword());
        dataSource.setPort(getPort());
        dataSource.setServerName(getHost());
        return dataSource;
    }
}
