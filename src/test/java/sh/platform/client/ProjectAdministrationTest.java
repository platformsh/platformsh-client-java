package sh.platform.client;

import org.apache.http.HttpStatus;
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
    public static final String VARIABLE_KEY = "test-api-key";
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
        assertEquals(HttpStatus.SC_OK, project.getCode());
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

        assertEquals(HttpStatus.SC_OK, status.getCode());
        assertEquals("OK", status.getStatus());
    }

    @Test
    public void shouldDeleteProject() {
        ProjectResponse status = projectAdministration.delete(PROJECT);
        assertEquals(HttpStatus.SC_OK, status.getCode());
        assertEquals("OK", status.getStatus());
    }

    @Test
    public void shouldUpdate() {
        ProjectResponse status = projectAdministration.update(PROJECT)
                .withTitle("a title")
                .withDescription("update the description").update();
        assertEquals(HttpStatus.SC_OK, status.getCode());
        assertEquals("OK", status.getStatus());
    }


    @Test
    public void shouldListVariable() {
        final List<Variable> variables = projectAdministration.getVariables(PROJECT);
        Assertions.assertNotNull(variables);
    }


    @Test
    public void shouldCreateVariable() {
        final VariableBuilder builder = projectAdministration.variable(PROJECT);
        final ProjectResponse status = builder.name(VARIABLE_KEY).value("a value").create();
        assertEquals(HttpStatus.SC_CREATED, status.getCode());
    }

    @Test
    public void shouldDeleteVariable() {
        final ProjectResponse status = projectAdministration.delete(PROJECT, VARIABLE_KEY);
        assertEquals(HttpStatus.SC_OK, status.getCode());
    }

    @Test
    public void shouldGetVariable() {
        final Variable variable = projectAdministration.getVariable(PROJECT, VARIABLE_KEY);
        Assertions.assertNotNull(variable);
    }

    @Test
    public void shouldUpdateVariable() {
        final VariableBuilder builder = projectAdministration.variable(PROJECT);
        final ProjectResponse status = builder.name(VARIABLE_KEY).value("an updated value").update();
        assertEquals(HttpStatus.SC_OK, status.getCode());
    }

}