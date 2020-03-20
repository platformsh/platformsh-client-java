package sh.platform.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Projects {

    private final long count;

    private final List<Project> projects;

    @JsonCreator
    public Projects(@JsonProperty("count") long count,
                    @JsonProperty("projects") List<Project> projects) {
        this.count = count;
        this.projects = projects;
    }

    public long getCount() {
        return count;
    }

    public List<Project> getProjects() {
        if (projects == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(projects);
    }

    @Override
    public String toString() {
        return "Projects{" +
                "count=" + count +
                ", projects=" + projects +
                '}';
    }

    static Projects of(JsonMapper mapper, String url, AuthToken token) {
        HttpGet request = new HttpGet(url);
        request.addHeader(PlatformClient.JSON_HEADER);
        CloseableHttpClient client = HttpClients.createDefault();

        try (CloseableHttpClient httpClient = HttpClientSupplier.get();
             CloseableHttpResponse response = httpClient.execute(request)) {
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() != HttpStatus.SC_OK) {
                throw new PlatformClientException("There is an error on the AuthToken, http return: " +
                        statusLine.getStatusCode() + " " + statusLine.getReasonPhrase());
            }
            String json = EntityUtils.toString(response.getEntity());
            return mapper.reader()
                    .forType(Projects.class)
                    .readValue(json);
        } catch (IOException e) {
            throw new PlatformClientException("There is an error to get the client", e);
        }
    }
}
