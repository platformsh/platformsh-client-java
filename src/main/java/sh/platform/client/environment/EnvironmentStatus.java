package sh.platform.client.environment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EnvironmentStatus {

    private final String code;

    private final String message;

    @JsonCreator
    EnvironmentStatus(@JsonProperty("code") String code,
                      @JsonProperty("message") String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ProjectStatus{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
