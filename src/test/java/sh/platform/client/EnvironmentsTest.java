package sh.platform.client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sh.platform.client.environment.Environment;
import sh.platform.client.environment.Environments;
import sh.platform.client.project.Project;
import sh.platform.client.project.ProjectAdministration;
import sh.platform.client.project.Projects;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class EnvironmentsTest {

    private static final String TOKEN;

    private ProjectAdministration projectAdministration;

    private Environments environments;

    static {
        final PropertiesReader reader = PropertiesReader.INSTANCE;
        TOKEN = reader.get(Variables.TOKEN);
    }

    private PlatformClient client = new PlatformClient(TOKEN);

    @BeforeEach
    public void init() {
        this.projectAdministration = client.getProjectAdministration();
        this.environments = client.getEnvironments();
    }

    @Test
    public void shouldReturnEnvironments(){
        final String project = getFirstProject().map(Project::getId).orElse("id");
        final List<Environment> environments = this.environments.getEnvironments(project);
        Assertions.assertNotNull(environments);
    }

    @Test
    public void shouldRedeploy(){

    }


    private Optional<Project> getFirstProject() {
        Projects projects = projectAdministration.getProjects();
        if (projects.getCount() > 0) {
            return Optional.of(projects.getProjects().get(0));
        }
        return Optional.empty();
    }
}