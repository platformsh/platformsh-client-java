package sh.platform.client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlatformClientTest {

    private static final String PROJECT_ID;
    private static final String TOKEN;

    static {
        ConfigurationUtil util = ConfigurationUtil.INSTANCE;
        PROJECT_ID = util.get(TestProperties.PROJECT);
        TOKEN = util.get(TestProperties.TOKEN);
    }

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
