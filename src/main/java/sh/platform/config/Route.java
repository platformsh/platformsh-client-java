package sh.platform.config;

import com.google.gson.annotations.SerializedName;

import java.util.Optional;

public class Route {

    private String id;

    private String upstream;

    @SerializedName("original_url")
    private String originalUrl;

    @SerializedName("restrict_robots")
    private boolean restrictRobots;

    private boolean primary;

    private String type;

    private String to;

    public Optional<String> getId() {
        return Optional.ofNullable(id);
    }

    public Optional<String> getUpstream() {
        return Optional.ofNullable(upstream);
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public boolean isPrimary() {
        return primary;
    }

    public String getType() {
        return type;
    }

    public String getTo() {
        return to;
    }

    public boolean isRestrictRobotsEnabled() {
        return restrictRobots;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id='" + id + '\'' +
                ", upstream='" + upstream + '\'' +
                ", originalUrl='" + originalUrl + '\'' +
                ", restrictRobots=" + restrictRobots +
                ", primary=" + primary +
                ", type='" + type + '\'' +
                ", to='" + to + '\'' +
                '}';
    }
}
