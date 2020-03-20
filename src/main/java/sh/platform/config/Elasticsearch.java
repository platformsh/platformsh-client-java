package sh.platform.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * A credential specialization that provides information a {@link RestHighLevelClient}
 */
public class Elasticsearch extends Credential implements Supplier<RestHighLevelClient> {

    public Elasticsearch(Map<String, Object> config) {
        super(config);
    }

    @Override
    public RestHighLevelClient get() {
        RestClientBuilder builder = RestClient.builder(new HttpHost(getHost(), getPort()));
        final Optional<String> username = getStringSafe("username");
        final Optional<String> password = getStringSafe("password");
        if(username.isPresent()) {
            final CredentialsProvider credentialsProvider =
                    new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY,
                    new UsernamePasswordCredentials(username.orElse(null), password.orElse(null)));
            builder.setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
                    .setDefaultCredentialsProvider(credentialsProvider));
        }
        return new RestHighLevelClient(builder);
    }
}
