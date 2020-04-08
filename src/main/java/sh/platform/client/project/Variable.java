package sh.platform.client.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import sh.platform.client.util.CollectionsUtils;

import java.time.ZonedDateTime;
import java.util.Map;

/**
 * These endpoints manipulate user-defined variables which are bound to an entire project. These variables
 * are accessible to all environments within a single project, and they can be made available at both build time
 * and runtime. For more information on project variables, see the Variables section of the documentation.
 *
 * https://docs.platform.sh/development/variables.html#project-variables
 */
public class Variable {

    @JsonProperty
    private String name;

    @JsonProperty
    private Map<String, String> attributes;

    @JsonProperty
    private String value;

    @JsonProperty("is_json")
    private boolean json;

    @JsonProperty("is_sensitive")
    private boolean sensitive;

    @JsonProperty("visible_build")
    private boolean build;

    @JsonProperty("visible_runtime")
    private boolean runtime;

    @JsonProperty("created_at")
    private ZonedDateTime created;

    @JsonProperty("updated_at")
    private ZonedDateTime updated;

    public String getName() {
        return name;
    }

    public Map<String, String> getAttributes() {
        return CollectionsUtils.readOnly(attributes);
    }

    public String getValue() {
        return value;
    }

    public boolean isJson() {
        return json;
    }

    public boolean isSensitive() {
        return sensitive;
    }

    public boolean isBuild() {
        return build;
    }

    public boolean isRuntime() {
        return runtime;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public ZonedDateTime getUpdated() {
        return updated;
    }

    @Override
    public String toString() {
        return "Variable{" +
                "name='" + name + '\'' +
                ", attributes=" + attributes +
                ", value='" + value + '\'' +
                ", json=" + json +
                ", sensitive=" + sensitive +
                ", build=" + build +
                ", runtime=" + runtime +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
}
