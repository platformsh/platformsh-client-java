package sh.platform.client.project;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import sh.platform.client.AuthToken;
import sh.platform.client.PlatformClient;
import sh.platform.client.PlatformClientException;
import sh.platform.client.util.HttpClientExecutor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Objects;

class DefaultProjectBuilder implements ProjectBuilder, ProjectBuilder.ProjectBuilderBuild {

    private final String title;

    private final String url;

    private final AuthToken token;

    private final JsonMapper mapper;

    private String region;

    private Map<String, Object> attributes;

    private String description;

    private String domain;

    DefaultProjectBuilder(String url, String title, AuthToken token, JsonMapper mapper) {
        this.url = url;
        this.title = title;
        this.token = token;
        this.mapper = mapper;
    }


    @Override
    public ProjectBuilderBuild region(String region) {
        Objects.requireNonNull(region, "regions is required");
        this.region = region;
        return this;
    }

    @Override
    public ProjectBuilderBuild description(String description) {
        Objects.requireNonNull(description, "description is required");
        this.description = description;
        return this;
    }

    @Override
    public ProjectBuilderBuild domain(String domain) {
        Objects.requireNonNull(domain, "domain is required");
        this.domain = domain;
        return this;
    }

    @Override
    public ProjectBuilderBuild attributes(String key, Object value) {
        Objects.requireNonNull(key, "key is required");
        Objects.requireNonNull(value, "value is required");
        this.attributes.put(key, value);
        return this;
    }

    @Override
    public ProjectStatus create() {
        ProjectCreate entity = new ProjectCreate(title, region, attributes, description, domain);
        HttpPost request = new HttpPost(url);
        try {
            request.setEntity(new StringEntity(mapper.writeValueAsString(entity)));
        } catch (IOException exp) {
            throw new PlatformClientException("There is an error to load the project creation", exp);
        }
        request.addHeader(PlatformClient.JSON_HEADER);
        request.addHeader("Authorization", token.getAuthorization());
        return HttpClientExecutor.request(request, mapper, ProjectStatus.class);
    }
}
