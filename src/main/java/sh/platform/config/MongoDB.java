package sh.platform.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * A credential specialization that provides information a {@link MongoClient}
 */
public class MongoDB extends Credential implements Supplier<MongoClient> {

    public MongoDB(Map<String, Object> config) {
        super(config);
    }

    @Override
    public MongoClient get() {

        Optional<String> username = getStringSafe("username")
                .filter(s -> !s.isEmpty());

        int port = getIntSafe("port").orElse(0);
        String host = getString("host");

        Optional<char[]> password = getStringSafe("password")
                .map(String::toCharArray);

        if (username.isPresent()) {
            MongoCredential credential = MongoCredential.createCredential(username.get(),
                    getDatabase(), password.get());
            ServerAddress serverAddress = new ServerAddress(host, port);
            return new MongoClient(serverAddress, credential, MongoClientOptions.builder().build());

        }
        return new MongoClient(host, port);
    }

    public String getDatabase() {
        return getString("path");
    }
}
