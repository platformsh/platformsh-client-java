package sh.platform.client.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import sh.platform.client.PlatformClientException;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public final class HttpClientExecutor {

    private static final SSLConnectionSocketFactory FACTORY;
    private static final Registry<ConnectionSocketFactory> REGISTRY;
    private static final CloseableHttpClient CLIENT;

    private static final Logger LOGGER = Logger.getLogger(HttpClientExecutor.class.getName());

    private static final List<Integer> RESPONSES = Arrays.asList(HttpStatus.SC_OK, HttpStatus.SC_ACCEPTED,
            HttpStatus.SC_CREATED);

    static {
        try {
            FACTORY = new SSLConnectionSocketFactory(SSLContext.getDefault(),
                    NoopHostnameVerifier.INSTANCE);
        } catch (NoSuchAlgorithmException e) {
            throw new PlatformClientException("There is an error to load the SSL connection factory", e);
        }

        REGISTRY = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", new PlainConnectionSocketFactory())
                .register("https", FACTORY)
                .build();

        CLIENT = HttpClients.custom()
                .setSSLSocketFactory(FACTORY)
                .setConnectionManager(new PoolingHttpClientConnectionManager(REGISTRY))
                .build();


    }

    public static <T> T request(HttpUriRequest request, JsonMapper mapper, Class<T> type) {
        long start = System.currentTimeMillis();
        try (CloseableHttpResponse response = getClient().execute(request)) {
            StatusLine statusLine = response.getStatusLine();

            if (!RESPONSES.contains(statusLine.getStatusCode())) {
                throw new PlatformClientException(statusLine, request.toString());
            }
            String json = EntityUtils.toString(response.getEntity());
            return mapper.readValue(json, type);
        } catch (IOException e) {
            throw new PlatformClientException("There is an error to get the client", e);
        } finally {
            LOGGER.info("Time to execute " + request.toString() + " on " +
                    (System.currentTimeMillis() - start) + " ms");

        }
    }

    public static <T> T request(HttpUriRequest request, JsonMapper mapper, TypeReference<T> type) {
        long start = System.currentTimeMillis();
        try (CloseableHttpResponse response = getClient().execute(request)) {
            StatusLine statusLine = response.getStatusLine();
            if (!RESPONSES.contains(statusLine.getStatusCode())) {
                throw new PlatformClientException(statusLine, request.toString());
            }
            String json = EntityUtils.toString(response.getEntity());
            return mapper.readValue(json, type);
        } catch (IOException e) {
            throw new PlatformClientException("There is an error to get the client", e);
        } finally {
            LOGGER.info("Time to execute " + request.toString() + " on " +
                    (System.currentTimeMillis() - start) + " ms");

        }
    }

    private static CloseableHttpClient getClient() {
        return CLIENT;
    }

    private HttpClientExecutor() {
    }
}
