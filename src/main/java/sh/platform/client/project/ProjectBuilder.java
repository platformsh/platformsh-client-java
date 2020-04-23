package sh.platform.client.project;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.entity.StringEntity;
import sh.platform.client.AuthToken;
import sh.platform.client.PlatformClient;
import sh.platform.client.PlatformClientException;
import sh.platform.client.util.HttpClientExecutor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public class ProjectBuilder {

    private final String url;

    private final AuthToken token;

    private final JsonMapper mapper;

    private String title;

    ProjectBuilder(String url, AuthToken token, JsonMapper mapper) {
        this.url = url;
        this.token = token;
        this.mapper = mapper;
    }


    public ProjectTitleBuilder withTitle(String title) {
        this.title = requireNonNull(title, "title is required");
        return new ProjectTitleBuilder(url, token, mapper, title);
    }

    public static class ProjectTitleBuilder {

        private final String url;

        private final AuthToken token;

        private final JsonMapper mapper;

        private final String title;

        private String description;

        private Map<String, Object> attributes;

        private String region;

        private ProjectTitleBuilder(String url, AuthToken token, JsonMapper mapper, String title) {
            this.url = url;
            this.token = token;
            this.mapper = mapper;
            this.title = title;
        }

        public ProjectTitleBuilder withDescription(String description) {
            this.description = requireNonNull(description, "description is required");
            return this;
        }

        public ProjectTitleBuilder withRegion(String region) {
            this.region = requireNonNull(region, "region is required");
            return this;
        }

        public ProjectTitleBuilder attributes(String key, Object value) {
            requireNonNull(key, "key is required");
            requireNonNull(value, "value is required");
            if (attributes == null) {
                this.attributes = new HashMap<>();
            }
            this.attributes.put(key, value);
            return this;
        }

        public ProjectResponse update() {
            ProjectUpdate entity = new ProjectUpdate(title, description, attributes, region);
            HttpPatch request = new HttpPatch(url);
            try {
                request.setEntity(new StringEntity(mapper.writeValueAsString(entity)));
            } catch (IOException exp) {
                throw new PlatformClientException("There is an error to load the project to update", exp);
            }
            request.addHeader(PlatformClient.JSON_HEADER);
            request.addHeader("Authorization", token.getAuthorization());
            return HttpClientExecutor.request(request, mapper, ProjectResponse.class);

        }
    }


}
