package sh.platform.client.project;

import java.time.ZonedDateTime;

public class Author {

    private String name;

    private String email;

    private ZonedDateTime date;

    Author() {
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", date=" + date +
                '}';
    }
}
