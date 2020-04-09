package sh.platform.client;

import java.time.ZonedDateTime;

public class Committer {

    private ZonedDateTime date;

    private String name;

    private String email;

    Committer() {
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Committer{" +
                "date=" + date +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
