package sh.platform.client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PlatformClientTest {

    private String token = "c4627994fbbc0892fe489daacdef5e20fb4aacf7";

    @Test
    public void shouldStartClient() {

        PlatformClient client = new PlatformClient(token);
        assertNotNull(client);

    }
}
