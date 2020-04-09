package sh.platform.client;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import sh.platform.client.project.Blob;
import sh.platform.client.project.Commit;
import sh.platform.client.project.Project;
import sh.platform.client.project.ProjectAdministration;
import sh.platform.client.project.ProjectResponse;
import sh.platform.client.project.Projects;
import sh.platform.client.project.Variable;
import sh.platform.client.project.VariableBuilder;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProjectAdministrationTest {

    private static final String PROJECT;
    private static final String TOKEN;
    private static final String VARIABLE_KEY = "test-api-key";
    private static final String COMMIT;
    private static final String BLOB;
    private static final String TREE;
    private static final String INTEGRATION;

    private ProjectAdministration projectAdministration;

    static {
        ConfigurationUtil util = ConfigurationUtil.INSTANCE;
        PROJECT = util.get(TestProperties.PROJECT);
        TOKEN = util.get(TestProperties.TOKEN);
        COMMIT = util.get(TestProperties.COMMIT);
        BLOB = util.get(TestProperties.BLOB);
        TREE = util.get(TestProperties.TREE);
        INTEGRATION = util.get(TestProperties.INTEGRATION);
    }

    private PlatformClient client = new PlatformClient(TOKEN);

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
        Optional<Project> project = projectAdministration.getProject(PROJECT);
        assertNotNull(project);
        Assertions.assertTrue(project.isPresent());
    }

    @Test
    public void shouldCleanBuildCache() {
        ProjectResponse project = projectAdministration.clearProjectBuildCache(PROJECT);
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

    @Test
    public void shouldReturnRefList() {
        List<Map<String, Object>> refs = projectAdministration.getRepositoryRefs(PROJECT);
        Assertions.assertNotNull(refs);
    }

    @Test
    public void shouldReturnRef() {
        Map<String, Object> ref = projectAdministration.getRepositoryRef(PROJECT, "heads%2Fmaster");
        Assertions.assertNotNull(ref);
    }

    @Test
    public void shouldReturnTree() {
        Map<String, Object> tree = projectAdministration.getRepositoryTree(PROJECT, "heads%2Fmaster");
        Assertions.assertNotNull(tree);
    }


    @Test
    public void shouldReturnCommit() {
        Commit commit = projectAdministration.getRepositoryCommit(PROJECT, COMMIT);
        Assertions.assertNotNull(commit);
    }

    @Test
    public void shouldReturnBlog() {
        Blob blob = projectAdministration.getRepositoryBlob(PROJECT, COMMIT);
        Assertions.assertNotNull(blob);
    }

    @Test
    public void shouldReturnIntegrations() {
        List<Map<String, Object>> integrations = projectAdministration.getRepositoryIntegrations(PROJECT);
        Assertions.assertNotNull(integrations);
    }

    @Test
    public void shouldReturnIntegration() {
        Map<String, Object> integrations = projectAdministration.getRepositoryIntegration(PROJECT, INTEGRATION);
        Assertions.assertNotNull(integrations);
    }

}