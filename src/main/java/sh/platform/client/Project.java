package sh.platform.client;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class Project {

    @JsonProperty("created_at")
    private LocalDateTime created;

    @JsonProperty("updated_at")
    private LocalDateTime updated;

    @JsonProperty
    private String title;

    @JsonProperty
    private String description;

    @JsonProperty
    private String region;

    @JsonProperty
    private Map<String, String> subscription;

    @JsonProperty
    private Map<String, String> repository;

    @JsonProperty
    private String owner;

    @JsonProperty("default_domain")
    private String domain;


    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getRegion() {
        return region;
    }

    public Map<String, String> getSubscription() {
        if (subscription == null) {
            return Collections.emptyMap();
        }
        return Collections.unmodifiableMap(subscription);
    }

    public Map<String, String> getRepository() {
        if (repository == null) {
            return Collections.emptyMap();
        }
        return Collections.unmodifiableMap(repository);
    }

    public String getOwner() {
        return owner;
    }

    public String getDomain() {
        return domain;
    }

    @Override
    public String toString() {
        return "Project{" +
                "created=" + created +
                ", updated=" + updated +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", region='" + region + '\'' +
                ", subscription=" + subscription +
                ", repository=" + repository +
                ", owner='" + owner + '\'' +
                ", domain='" + domain + '\'' +
                '}';
    }
}

