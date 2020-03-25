package sh.platform.client.project;

public class ProjectBuilder {


    public interface ProjectBuilderRegion {

        ProjectBuilderBuild region(String region);
    }

    public interface ProjectBuilderBuild {

        ProjectBuilderDescription description(String description);

        ProjectBuilderBuild domain(String description);

        ProjectBuilderBuild attributes(String key, Object value);

        ProjectCreate create();

    }

    public interface ProjectBuilderDescription {

        ProjectBuilderBuild domain(String description);

        ProjectBuilderBuild attributes(String key, Object value);

        ProjectCreate create();
    }

    public interface ProjectBuilderDomain {

        ProjectBuilderBuild description(String description);

        ProjectBuilderBuild attributes(String key, Object value);

        ProjectCreate create();

    }


}
