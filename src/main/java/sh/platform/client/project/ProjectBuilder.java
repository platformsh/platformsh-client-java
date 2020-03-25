package sh.platform.client.project;

public interface ProjectBuilder {

    ProjectBuilderBuild region(String region);

    public interface ProjectBuilderBuild {

        ProjectBuilderBuild description(String description);

        ProjectBuilderBuild domain(String description);

        ProjectBuilderBuild attributes(String key, Object value);

        ProjectResponse create();

    }
}
