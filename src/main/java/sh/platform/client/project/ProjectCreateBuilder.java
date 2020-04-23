package sh.platform.client.project;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import sh.platform.client.AuthToken;
import sh.platform.client.PlatformClient;
import sh.platform.client.PlatformClientException;
import sh.platform.client.util.HttpClientExecutor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ProjectCreateBuilder {

    private final String url;
    private final AuthToken token;
    private final JsonMapper mapper;
    private final String title;

    ProjectCreateBuilder(String url, String title, AuthToken token, JsonMapper mapper) {
        this.url = url;
        this.token = token;
        this.mapper = mapper;
        this.title = title;
    }

    public ProjectCreateOptionsBuilder region(String region) {
        Objects.requireNonNull(region, "region is required");
        return new ProjectCreateOptionsBuilder(url, title, token, mapper, region);
    }

    public static class ProjectCreateOptionsBuilder {

        private final String url;
        private final AuthToken token;
        private final JsonMapper mapper;
        private final String region;
        private final String title;

        private Map<String, Object> attributes;
        private String description;
        private String domain;

        private ProjectCreateOptionsBuilder(String url, String title, AuthToken token, JsonMapper mapper, String region) {
            this.url = url;
            this.token = token;
            this.mapper = mapper;
            this.region = region;
            this.title = title;
        }

        public ProjectCreateOptionsBuilder description(String description) {
            Objects.requireNonNull(description, "description is required");
            this.description = description;
            return this;
        }

        public ProjectCreateOptionsBuilder domain(String domain) {
            Objects.requireNonNull(domain, "domain is required");
            this.domain = domain;
            return this;
        }

        public ProjectCreateOptionsBuilder attributes(String key, Object value) {
            Objects.requireNonNull(key, "key is required");
            Objects.requireNonNull(value, "value is required");
            if (attributes == null) {
                this.attributes = new HashMap<>();
            }
            this.attributes.put(key, value);
            return this;
        }

        public ProjectResponse create() {
            ProjectCreate entity = new ProjectCreate(title, region, attributes, description, domain);
            HttpPost request = new HttpPost(url);
            try {
                request.setEntity(new StringEntity(mapper.writeValueAsString(entity)));
            } catch (IOException exp) {
                throw new PlatformClientException("There is an error to load the project creation", exp);
            }
            request.addHeader(PlatformClient.JSON_HEADER);
            request.addHeader("Authorization", token.getAuthorization());
            return HttpClientExecutor.request(request, mapper, ProjectResponse.class);
        }

    }
}
