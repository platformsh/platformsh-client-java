package sh.platform.client.project;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

class ProjectCreate {

    @JsonProperty("title")
    private final String title;

    @JsonProperty("region")
    private final String region;

    @JsonProperty("attributes")
    private final Map<String, Object> attributes;

    @JsonProperty("description")
    private final String description;

    @JsonProperty("default_domain")
    private final String domain;

    ProjectCreate(String title, String region, Map<String, Object> attributes,
                  String description, String domain) {
        this.title = title;
        this.region = region;
        this.attributes = attributes;
        this.description = description;
        this.domain = domain;
    }

    public String getTitle() {
        return title;
    }

    public String getRegion() {
        return region;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public String getDescription() {
        return description;
    }

    public String getDomain() {
        return domain;
    }

    @JsonIgnore
    public void create() {

    }

    @Override
    public String toString() {
        return "ProjectCreate{" +
                "title='" + title + '\'' +
                ", region='" + region + '\'' +
                ", attributes=" + attributes +
                ", description='" + description + '\'' +
                ", domain='" + domain + '\'' +
                '}';
    }
}
