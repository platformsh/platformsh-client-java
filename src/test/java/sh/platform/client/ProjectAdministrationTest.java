package sh.platform.client;

import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProjectAdministrationTest {

    private static final String TOKEN;
    private static final String VARIABLE_KEY = "test-api-key";

    private ProjectAdministration projectAdministration;

    static {
        final PropertiesReader reader = PropertiesReader.INSTANCE;
        TOKEN = reader.get(Variables.TOKEN);
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

        final String id = getFirstProject().map(Project::getId).orElse("id");
        Optional<Project> project = projectAdministration.getProject(id);
        assertNotNull(project);
        Assertions.assertTrue(project.isPresent());
    }

    @Test
    public void shouldReturnNotFoundWhenProjectDoesNotExist() {
        Optional<Project> project = projectAdministration.getProject("d6mgc635zl111");
        Assertions.assertFalse(project.isPresent());

    }

    @Test
    public void shouldCleanBuildCache() {
        final String id = getFirstProject().map(Project::getId).orElse("id");
        ProjectResponse project = projectAdministration.clearProjectBuildCache(id);
        assertNotNull(project);
        assertEquals(HttpStatus.SC_OK, project.getCode());
        assertEquals("OK", project.getStatus());
    }

    @Test
    @Disabled
    public void shouldReturnEmptyWhenThereIsNotProject() {
        Optional<Project> project = projectAdministration.getProject("d6mgc635zl111");
        assertNotNull(project);
        Assertions.assertFalse(project.isPresent());
    }

    @Test
    @Disabled
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
        final String id = getFirstProject().map(Project::getId).orElse("id");
        ProjectResponse status = projectAdministration.delete(id);
        assertEquals(HttpStatus.SC_OK, status.getCode());
    }

    @Test
    public void shouldUpdate() {
        final String id = getFirstProject().map(Project::getId).orElse("id");
        ProjectResponse status = projectAdministration.update(id)
                .withTitle("a title")
                .withDescription("update the description").update();
        assertEquals(HttpStatus.SC_OK, status.getCode());
        assertEquals("OK", status.getStatus());
    }


    @Test
    public void shouldListVariable() {
        final String id = getFirstProject().map(Project::getId).orElse("id");
        final List<Variable> variables = projectAdministration.getVariables(id);
        Assertions.assertNotNull(variables);
    }

    @Test
    public void shouldDeleteVariable() {
        final String id = getFirstProject().map(Project::getId).orElse("id");
        final ProjectResponse status = projectAdministration.delete(id, VARIABLE_KEY);
        assertEquals(HttpStatus.SC_OK, status.getCode());
    }

    @Test
    public void shouldCreateVariable() {
        final String id = getFirstProject().map(Project::getId).orElse("id");
        final VariableBuilder builder = projectAdministration.variable(id);

        final ProjectResponse status = builder.name(VARIABLE_KEY).value("a value").create();
        assertEquals(HttpStatus.SC_CREATED, status.getCode());
    }


    @Test
    public void shouldReturnNotFoundWhenDeleteVariableThatDoesNotExist() {
        final String id = getFirstProject().map(Project::getId).orElse("id");
        final PlatformClientException exception = Assertions.assertThrows(PlatformClientException.class, () -> projectAdministration.delete(id, VARIABLE_KEY));
        final StatusLine statusLine = exception.getStatus().get();
        Assertions.assertEquals(404, statusLine.getStatusCode());
    }

    @Test
    public void shouldGetVariable() {
        final String id = getFirstProject().map(Project::getId).orElse("id");
        final List<Variable> variables = projectAdministration.getVariables(id);
        if (!variables.isEmpty()) {
            final Variable variable = projectAdministration.getVariable(id, variables.get(0).getName());
            Assertions.assertNotNull(variable);
        }

    }

    @Test
    public void shouldUpdateVariable() {
        final String id = getFirstProject().map(Project::getId).orElse("id");
        final List<Variable> variables = projectAdministration.getVariables(id);
        if (!variables.isEmpty()) {
            final VariableBuilder builder = projectAdministration.variable(id);
            final ProjectResponse status = builder.name(variables.get(0).getName()).value("an updated value").update();
            assertEquals(HttpStatus.SC_OK, status.getCode());
        }

    }

    @Test
    public void shouldReturnRefList() {
        final String id = getFirstProject().map(Project::getId).orElse("id");
        List<Map<String, Object>> refs = projectAdministration.getRepositoryRefs(id);
        Assertions.assertNotNull(refs);
    }

    @Test
    public void shouldReturnRef() {
        final String id = getFirstProject().map(Project::getId).orElse("id");
        Map<String, Object> ref = projectAdministration.getRepositoryRef(id, "heads%2Fmaster");
        Assertions.assertNotNull(ref);
    }

    @Test
    @Disabled
    public void shouldReturnTree() {

        final String id = getFirstProject().map(Project::getId).orElse("id");
        List<Map<String, Object>> refs = projectAdministration.getRepositoryRefs(id);
        if(!refs.isEmpty()) {
            final Map<String, Object> map = refs.get(0);
            final Map<String, String> object = (Map<String, String>) map.get("object");
            Commit commit = projectAdministration.getRepositoryCommit(id, object.get("sha"));
            Map<String, Object> tree = projectAdministration.getRepositoryTree(id, commit.getTree());
            Assertions.assertNotNull(tree);
        }
    }


    @Test
    public void shouldReturnCommit() {
        final String id = getFirstProject().map(Project::getId).orElse("id");
        List<Map<String, Object>> refs = projectAdministration.getRepositoryRefs(id);
        if(!refs.isEmpty()) {
            final Map<String, Object> map = refs.get(0);
            final Map<String, String> object = (Map<String, String>) map.get("object");
            Commit commit = projectAdministration.getRepositoryCommit(id, object.get("sha"));
            Assertions.assertNotNull(commit);
        }

    }

    @Test
    @Disabled
    public void shouldReturnBlog() {
        final String id = getFirstProject().map(Project::getId).orElse("id");
        List<Map<String, Object>> refs = projectAdministration.getRepositoryRefs(id);
        if(!refs.isEmpty()) {
            final Map<String, Object> map = refs.get(0);
            final Map<String, String> object = (Map<String, String>) map.get("object");
            Blob blob = projectAdministration.getRepositoryBlob(id, object.get("sha"));
            Assertions.assertNotNull(blob);
        }
    }

    @Test
    public void shouldReturnIntegrations() {
        final String id = getFirstProject().map(Project::getId).orElse("id");
        List<Map<String, Object>> integrations = projectAdministration.getRepositoryIntegrations(id);
        Assertions.assertNotNull(integrations);
    }

    @Test
    public void shouldReturnIntegration() {
        final String id = getFirstProject().map(Project::getId).orElse("id");
        List<Map<String, Object>> integrations = projectAdministration.getRepositoryIntegrations(id);
        if(!integrations.isEmpty()) {
            final Map<String, Object> map = integrations.get(0);
            Map<String, Object> integration = projectAdministration.getRepositoryIntegration(id, map.get("id").toString());
            Assertions.assertNotNull(integration);
        }

    }

    @Test
    @Disabled
    public void shouldDeleteIntegration() {
    }

    @Test
    @Disabled
    public void shouldCreateIntegration() {
    }


    @Test
    @Disabled
    public void shouldReturnDomains() {
        final String id = getFirstProject().map(Project::getId).orElse("id");
        List<Map<String, Object>> domains = projectAdministration.getDomains(id);
        Assertions.assertNotNull(domains);
    }

    @Test
    @Disabled
    public void shouldReturnDomain() {
    }

    @Test
    @Disabled
    public void shouldDeleteDomain() {
    }

    @Test
    @Disabled
    public void shouldCreateDomain() {
    }


    private Optional<Project> getFirstProject() {
        Projects projects = projectAdministration.getProjects();
        if (projects.getCount() > 0) {
            return Optional.of(projects.getProjects().get(0));
        }
        return Optional.empty();
    }
}