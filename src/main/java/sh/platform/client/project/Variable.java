package sh.platform.client.project;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import sh.platform.client.AuthToken;
import sh.platform.client.PlatformClient;
import sh.platform.client.util.CollectionsUtils;
import sh.platform.client.util.HttpClientExecutor;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * These endpoints manipulate user-defined variables which are bound to an entire project. These variables
 * are accessible to all environments within a single project, and they can be made available at both build time
 * and runtime. For more information on project variables, see the Variables section of the documentation.
 * <p>
 * https://docs.platform.sh/development/variables.html#project-variables
 */
@JsonInclude(NON_NULL)
public class Variable {

    @JsonProperty
    private String name;

    @JsonProperty
    private Map<String, Object> attributes;

    @JsonProperty
    private Object value;

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

    Variable() {
    }

    Variable(String name, Map<String, Object> attributes, Object value, boolean json, boolean sensitive,
             boolean build, boolean runtime) {
        this.name = name;
        this.attributes = attributes;
        this.value = value;
        this.json = json;
        this.sensitive = sensitive;
        this.build = build;
        this.runtime = runtime;
    }

    public String getName() {
        return name;
    }

    public Map<String, Object> getAttributes() {
        return CollectionsUtils.readOnly(attributes);
    }

    public Object getValue() {
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

    static List<Variable> list(JsonMapper mapper, String url, AuthToken token) {
        HttpGet request = new HttpGet(url);
        request.addHeader(PlatformClient.JSON_HEADER);
        request.addHeader("Authorization", token.getAuthorization());
        return HttpClientExecutor.request(request, mapper, new TypeReference<List<Variable>>() {
        });
    }
}
