package sh.platform.client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlatformClientTest {

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
        Assertions.assertNotNull(projects);

    }
}
