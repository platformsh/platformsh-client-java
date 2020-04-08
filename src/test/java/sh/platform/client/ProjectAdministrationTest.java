package sh.platform.client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import sh.platform.client.project.Project;
import sh.platform.client.project.ProjectAdministration;
import sh.platform.client.project.ProjectResponse;
import sh.platform.client.project.Projects;
import sh.platform.client.project.Variable;
import sh.platform.client.project.VariableBuilder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static sh.platform.client.PlatformClientTest.PROJECT_ID;
import static sh.platform.client.PlatformClientTest.TOKEN;

class ProjectAdministrationTest {

    private static final String PROJECT = "5pjgfuplukffs";
    private PlatformClient client = new PlatformClient(TOKEN);

    private ProjectAdministration projectAdministration;

    @BeforeEach
    public void init() {
        this.projectAdministration = client.getProjectAdministration();
    }

    @Test
    public void shouldStartProjects() {
        Projects projects = projectAdministration.getProjects();
        assertNotNull(projects);
    }

    @Test
    public void shouldGetProject() {
        Optional<Project> project = projectAdministration.getProject(PROJECT_ID);
        assertNotNull(project);
        Assertions.assertTrue(project.isPresent());
    }

    @Test
    public void shouldCleanBuildCache() {
        ProjectResponse project = projectAdministration.clearProjectBuildCache(PROJECT_ID);
        assertNotNull(project);
        assertEquals(200L, project.getCode());
        assertEquals("OK", project.getStatus());
    }

    @Test
    @Disabled
    public void shouldReturnEmptyWhenThereIsNotProject() {
        Optional<Project> project = projectAdministration.getProject("not_found");
        assertNotNull(project);
        Assertions.assertTrue(project.isPresent());
    }

    @Test
    public void shouldCreateProject() {
        ProjectResponse status = projectAdministration.create("title-sample")
                .region("eu-3.platform.sh")
                .description("That is a simple project")
                .create();

        assertEquals(200L, status.getCode());
        assertEquals("OK", status.getStatus());
    }

    @Test
    public void shouldDeleteProject() {
        ProjectResponse status = projectAdministration.delete(PROJECT);
        assertEquals(200L, status.getCode());
        assertEquals("OK", status.getStatus());
    }

    @Test
    public void shouldUpdate() {
        ProjectResponse status = projectAdministration.update(PROJECT)
                .withTitle("a title")
                .withDescription("update the description").update();
        assertEquals(200L, status.getCode());
        assertEquals("OK", status.getStatus());
    }


    @Test
    public void shouldListVariable() {
        final List<Variable> variables = projectAdministration.getVariables(PROJECT);
        Assertions.assertNotNull(variables);
    }


    @Test
    public void shouldCreateVariable() {
        final VariableBuilder builder = projectAdministration.createVariable(PROJECT);
        final ProjectResponse status = builder.name("test-api-key").value("a value").create();
        assertEquals(201L, status.getCode());
    }
}