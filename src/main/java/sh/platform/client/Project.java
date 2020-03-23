package sh.platform.client;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
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
    private String owner;

    @JsonProperty("default_domain")
    private String domain;

    @JsonProperty
    private ProjectStatus status;

    @JsonProperty("owner_info")
    private ProjectOwner ownerInfo;

    private String timezone;

    @JsonProperty
    private Map<String, String> subscription;

    @JsonProperty
    private Map<String, String> repository;

    @JsonProperty
    private Map<String, String> attributes;

    @JsonProperty
    private Map<String, String> settings;

    @JsonProperty
    private Map<String, String> capabilities;

    @JsonProperty
    private Map<String, String> integrations;

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

    public String getOwner() {
        return owner;
    }

    public String getDomain() {
        return domain;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public ProjectOwner getOwnerInfo() {
        return ownerInfo;
    }

    public String getTimezone() {
        return timezone;
    }

    public Map<String, String> getAttributes() {
        return CollectionsUtils.readOnly(attributes);
    }

    public Map<String, String> getSettings() {
        return CollectionsUtils.readOnly(settings);
    }

    public Map<String, String> getCapabilities() {
        return CollectionsUtils.readOnly(capabilities);
    }

    public Map<String, String> getIntegrations() {
        return CollectionsUtils.readOnly(integrations);
    }

    public Map<String, String> getSubscription() {
        return CollectionsUtils.readOnly(subscription);
    }

    public Map<String, String> getRepository() {
        return CollectionsUtils.readOnly(repository);
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

