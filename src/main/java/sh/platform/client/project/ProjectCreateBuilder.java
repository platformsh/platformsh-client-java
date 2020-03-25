package sh.platform.client.project;

public interface ProjectCreateBuilder {

    ProjectCreateOptionsBuilder region(String region);

    public interface ProjectCreateOptionsBuilder {

        ProjectCreateOptionsBuilder description(String description);

        ProjectCreateOptionsBuilder domain(String description);

        ProjectCreateOptionsBuilder attributes(String key, Object value);

        ProjectResponse create();

    }
}
