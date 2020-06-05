package sh.platform.client.environment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.http.client.methods.HttpGet;
import sh.platform.client.AuthToken;
import sh.platform.client.PlatformClient;
import sh.platform.client.project.Projects;
import sh.platform.client.util.CollectionsUtils;
import sh.platform.client.util.HttpClientExecutor;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

public class Environment {

    private String project;

    private String name;

    @JsonProperty("machine_name")
    private String machine;

    private String title;

    private String parent;

    private String status;

    @JsonProperty("head_commit")
    private String headCommit;

    @JsonProperty("clone_parent_on_create")
    private boolean cloneParent;

    @JsonProperty("deployment_target")
    private String deploymentTarget;

    @JsonProperty("is_pr")
    private boolean isPullRequest;

    @JsonProperty("enable_smtp")
    private boolean enableSMTP;

    @JsonProperty("restrict_robots")
    private boolean restrictRobots;

    @JsonProperty("is_main")
    private boolean main;

    @JsonProperty("is_dirty")
    private boolean dirty;

    @JsonProperty("has_code")
    private boolean code;

    @JsonProperty("edge_hostname")
    private String edgeHostname;

    @JsonProperty("created_at")
    private ZonedDateTime created;

    @JsonProperty("updated_at")
    private ZonedDateTime updated;

    @JsonProperty("last_active_at")
    private ZonedDateTime lastActive;


    @JsonProperty("last_backup_at")
    private ZonedDateTime lastBackup;

    private Map<String, Object> attributes;

    @JsonProperty("http_access")
    private Map<String, Object> httpAccess;

    private Map<String, Object> backups;


    public String getProject() {
        return project;
    }

    public String getName() {
        return name;
    }

    public String getMachine() {
        return machine;
    }

    public String getTitle() {
        return title;
    }

    public String getParent() {
        return parent;
    }

    public String getStatus() {
        return status;
    }

    public String getHeadCommit() {
        return headCommit;
    }

    public boolean isCloneParent() {
        return cloneParent;
    }

    public String getDeploymentTarget() {
        return deploymentTarget;
    }

    public boolean isPullRequest() {
        return isPullRequest;
    }

    public boolean isEnableSMTP() {
        return enableSMTP;
    }

    public boolean isRestrictRobots() {
        return restrictRobots;
    }

    public boolean isMain() {
        return main;
    }

    public boolean isDirty() {
        return dirty;
    }

    public boolean isCode() {
        return code;
    }

    public String getEdgeHostname() {
        return edgeHostname;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public ZonedDateTime getUpdated() {
        return updated;
    }

    public ZonedDateTime getLastActive() {
        return lastActive;
    }

    public ZonedDateTime getLastBackup() {
        return lastBackup;
    }

    public Map<String, Object> getAttributes() {
        return CollectionsUtils.readOnly(attributes);
    }

    public Map<String, Object> getHttpAccess() {
        return CollectionsUtils.readOnly(httpAccess);
    }

    public Map<String, Object> getBackups() {
        return CollectionsUtils.readOnly(backups);
    }

    @Override
    public String toString() {
        return "Environment{" +
                "project='" + project + '\'' +
                ", name='" + name + '\'' +
                ", machine='" + machine + '\'' +
                ", title='" + title + '\'' +
                ", parent='" + parent + '\'' +
                ", status='" + status + '\'' +
                ", headCommit='" + headCommit + '\'' +
                ", cloneParent=" + cloneParent +
                ", deploymentTarget='" + deploymentTarget + '\'' +
                ", isPullRequest=" + isPullRequest +
                ", enableSMTP=" + enableSMTP +
                ", restrictRobots=" + restrictRobots +
                ", main=" + main +
                ", dirty=" + dirty +
                ", code=" + code +
                ", edgeHostname='" + edgeHostname + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                ", lastActive=" + lastActive +
                ", lastBackup=" + lastBackup +
                ", attributes=" + attributes +
                ", httpAccess=" + httpAccess +
                ", backups=" + backups +
                '}';
    }

    static List<Environment> of(JsonMapper mapper, String url, AuthToken token) {
        HttpGet request = new HttpGet(url);
        request.addHeader(PlatformClient.JSON_HEADER);

        request.addHeader("Authorization", token.getAuthorization());
        return HttpClientExecutor.request(request, mapper, new TypeReference<List<Environment>>() {});
    }
}
