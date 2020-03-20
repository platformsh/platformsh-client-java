package sh.platform.client;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import javax.net.ssl.SSLContext;
import java.security.NoSuchAlgorithmException;

final class HttpClientSupplier {

    private static final PoolingHttpClientConnectionManager POOL;
    private static final SSLConnectionSocketFactory FACTORY;

    static {
        try {
            FACTORY = new SSLConnectionSocketFactory(SSLContext.getDefault(),
                    NoopHostnameVerifier.INSTANCE);
        } catch (NoSuchAlgorithmException e) {
            throw new PlatformClientException("There is an error to load the SSL connection factory", e);
        }
        final Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", new PlainConnectionSocketFactory())
                .register("https", FACTORY)
                .build();

        POOL = new PoolingHttpClientConnectionManager(registry);
        POOL.setMaxTotal(100);
    }

    static CloseableHttpClient get() {
        return HttpClients.custom()
                .setSSLSocketFactory(FACTORY)
                .setConnectionManager(POOL)
                .build();
    }

    private HttpClientSupplier() {
    }
}
