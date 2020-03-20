package sh.platform.config;

import org.apache.solr.client.solrj.impl.HttpSolrClient;

import java.util.Map;
import java.util.function.Supplier;

/**
 * A credential specialization that provides information a {@link HttpSolrClient}
 */
public class Solr extends Credential implements Supplier<HttpSolrClient> {

    public Solr(Map<String, Object> config) {
        super(config);
    }

    @Override
    public HttpSolrClient get() {
        final String path = getStringSafe("path").orElse("");
        String host = String.format("http://%s:%d/%s", getHost(), getPort(), path);
        return new HttpSolrClient.Builder(host)
                .build();
    }

    /**
     * @return Returns the connection as root URL, in other words, a {@link HttpSolrClient}
     * without core path.
     */
    public HttpSolrClient getRoot() {
        final String path = getStringSafe("path").orElse("");
        String host = String.format("http://%s:%d/%s", getHost(), getPort(), path);
        return new HttpSolrClient.Builder(host.substring(0, host.lastIndexOf('/')))
                .build();
    }
}
