package sh.platform.client.project;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.http.client.methods.HttpGet;
import sh.platform.client.AuthToken;
import sh.platform.client.HttpClientExecutor;
import sh.platform.client.PlatformClient;

import java.util.Collections;
import java.util.List;

/**
 * A collections of {@link Project}
 */
public class Projects {

    private final long count;

    private final List<Project> projects;

    @JsonCreator
    Projects(@JsonProperty("count") long count,
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

        request.addHeader("Authorization", token.getAuthorization());
        return HttpClientExecutor.request(request, mapper, Projects.class);
    }
}
