package sh.platform.client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlatformClientTest {

    static final String PROJECT_ID = "2wyp5ovkhxdfc";
    static final String TOKEN = "c4627994fbbc0892fe489daacdef5e20fb4aacf7";

    @Test
    public void shouldStartClient() {
        PlatformClient client = new PlatformClient(TOKEN);
        assertNotNull(client);
    }

    @Test
    public void shouldReturnErrorWhenStart() {
        assertThrows(Exception.class,
                () -> new PlatformClient("error_error"));
    }

}
