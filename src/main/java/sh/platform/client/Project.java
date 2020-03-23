package sh.platform.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * On Platform.sh, a Project is backed by a single Git repository and encompasses your entire application stack,
 * the services used by your application, the application's data storage, the production and staging environments,
 * and the backups of those environments.
 */
public class Project {

    @JsonProperty("id")
    private String id;

    @JsonProperty("endpoint")
    private String endpoint;

    @JsonProperty("subscription_id")
    private String subscriptionId;

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

    @JsonProperty
    private ProjectStatus status;

    @JsonProperty("owner_info")
    private ProjectOwner ownerInfo;

    public String getId() {
        return id;
    }

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

    public ProjectStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id='" + id + '\'' +
                ", endpoint='" + endpoint + '\'' +
                ", subscriptionId='" + subscriptionId + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", region='" + region + '\'' +
                ", subscription=" + subscription +
                ", repository=" + repository +
                ", owner='" + owner + '\'' +
                ", domain='" + domain + '\'' +
                ", status=" + status +
                ", ownerInfo=" + ownerInfo +
                '}';
    }
}

