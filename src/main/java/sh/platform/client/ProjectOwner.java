package sh.platform.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProjectOwner {

    @JsonProperty("user")
    private final String user;

    @JsonProperty("username")
    private final String username;

    @JsonProperty("display_name")
    private final String displayName;

    @JsonCreator
    ProjectOwner(@JsonProperty("user") String user,
                 @JsonProperty("username") String username,
                 @JsonProperty("display_name") String displayName) {
        this.user = user;
        this.username = username;
        this.displayName = displayName;
    }

    public String getUser() {
        return user;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return "ProjectOwner{" +
                "user='" + user + '\'' +
                ", username='" + username + '\'' +
                ", displayName='" + displayName + '\'' +
                '}';
    }
}
