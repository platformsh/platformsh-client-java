package sh.platform.client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlatformClientTest {

    private static final String PROJECT_ID = "2wyp5ovkhxdfc";
    private String token = "c4627994fbbc0892fe489daacdef5e20fb4aacf7";

    @Test
    public void shouldStartClient() {
        PlatformClient client = new PlatformClient(token);
        assertNotNull(client);
    }

    @Test
    public void shouldReturnErrorWhenStart() {
        assertThrows(Exception.class,
                () -> new PlatformClient("error_error"));
    }

    @Test
    public void shouldStartProjects() {
        PlatformClient client = new PlatformClient(token);
        Projects projects = client.getProjects();
        assertNotNull(projects);
    }

    @Test
    public void shouldGetProject() {
        PlatformClient client = new PlatformClient(token);
        Optional<Project> project = client.getProject(PROJECT_ID);
        assertNotNull(project);
        Assertions.assertTrue(project.isPresent());
    }

    @Test
    public void shouldCleanBuildCache() {
        PlatformClient client = new PlatformClient(token);
        ProjectResponse project = client.clearProjectBuildCache(PROJECT_ID);
        assertNotNull(project);
        assertEquals(200L, project.getCode());
        assertEquals("OK", project.getStatus());
    }


    @Test
    @Disabled
    public void shouldReturnEmptyWhenThereIsNotProject() {
        PlatformClient client = new PlatformClient(token);
        Optional<Project> project = client.getProject("not_found");
        assertNotNull(project);
        Assertions.assertTrue(project.isPresent());
    }
}
