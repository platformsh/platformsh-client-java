package sh.platform.client.project;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

class ProjectUpdate {

    @JsonProperty
    private final String title;

    @JsonProperty
    private final  String description;

    @JsonProperty
    private final  Map<String, Object> attributes;

    @JsonProperty("default_domain")
    private final  String region;

    ProjectUpdate(String title, String description, Map<String, Object> attributes, String region) {
        this.title = title;
        this.description = description;
        this.attributes = attributes;
        this.region = region;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public String getRegion() {
        return region;
    }

    @Override
    public String toString() {
        return "ProjectUpdate{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", attributes=" + attributes +
                ", region='" + region + '\'' +
                '}';
    }
}
