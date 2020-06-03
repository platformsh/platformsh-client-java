package sh.platform.client;

import java.util.function.Supplier;

public enum Variables implements Supplier<String> {

    TOKEN("user.token"),
    INTEGRATION("project.integration");

    private final String value;

    Variables(String value) {
        this.value = value;
    }

    @Override
    public String get() {
        return value;
    }
}
