package sh.platform.client.project;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import sh.platform.client.AuthToken;
import sh.platform.client.PlatformClient;
import sh.platform.client.PlatformClientException;
import sh.platform.client.util.HttpClientExecutor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A builder to {@link Variable}
 */
public class VariableBuilder {

    private final JsonMapper mapper;
    private final String url;
    private final AuthToken token;


    VariableBuilder(JsonMapper mapper, String url, AuthToken token) {
        this.mapper = mapper;
        this.url = url;
        this.token = token;
    }

    public VariableBuilderName name(String name) {
        Objects.requireNonNull(name, "name is required");
        return new VariableBuilderName(mapper, url, token, name);
    }

    public static class VariableBuilderName {

        private final JsonMapper mapper;
        private final String url;
        private final AuthToken token;

        private final String name;


        private VariableBuilderName(JsonMapper mapper, String url,
                                    AuthToken token, String name) {
            this.mapper = mapper;
            this.url = url;
            this.token = token;
            this.name = name;
        }

        public VariableBuilderExecution value(Object value) {
            Objects.requireNonNull(value, "value is required");
            return new VariableBuilderExecution(mapper, url, token, name, value);
        }
    }

    public static class VariableBuilderExecution {
        private final JsonMapper mapper;
        private final String url;
        private final AuthToken token;
        private final String name;
        private final Object value;

        private boolean json;
        private boolean sensitive;
        private boolean build;
        private boolean runtime;
        private Map<String, Object> attributes;

        private VariableBuilderExecution(JsonMapper mapper, String url,
                                         AuthToken token, String name, Object value) {
            this.mapper = mapper;
            this.url = url;
            this.token = token;
            this.name = name;
            this.value = value;
        }

        public VariableBuilderExecution addAttribute(String key, Object value) {
            Objects.requireNonNull(key, "key is required");
            Objects.requireNonNull(value, "value is required");

            if (attributes == null) {
                this.attributes = new HashMap<>();
            }
            this.attributes.put(key, value);
            return this;
        }

        public VariableBuilderExecution enableJson() {
            this.json = true;
            return this;
        }

        public VariableBuilderExecution disableJson() {
            this.json = false;
            return this;
        }

        public VariableBuilderExecution enableSensitive() {
            this.sensitive = true;
            return this;
        }

        public VariableBuilderExecution disableSensitive() {
            this.sensitive = false;
            return this;
        }

        public VariableBuilderExecution enableVisibleOnBuild() {
            this.build = true;
            return this;
        }

        public VariableBuilderExecution disableVisibleONBuild() {
            this.build = false;
            return this;
        }

        public VariableBuilderExecution enableVisibleOnRunTime() {
            this.runtime = true;
            return this;
        }

        public VariableBuilderExecution disableVisibleOnRunTime() {
            this.runtime = false;
            return this;
        }

        public ProjectResponse create() {
            HttpPost request = new HttpPost(url);
            request.addHeader(PlatformClient.JSON_HEADER);
            request.addHeader("Authorization", token.getAuthorization());

            Variable entity = new Variable(name, attributes, value, json, sensitive, build, runtime);
            try {
                request.setEntity(new StringEntity(mapper.writeValueAsString(entity)));
            } catch (IOException exp) {
                throw new PlatformClientException("There is an error to load the project to update", exp);
            }
            return HttpClientExecutor.request(request, mapper, ProjectResponse.class);
        }
    }

}
