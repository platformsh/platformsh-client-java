package sh.platform.client;

import java.util.function.Supplier;

public enum  TestProperties implements Supplier<String> {

    PROJECT("platform.api.project"), TOKEN("platform.api.token");

    private final String value;

    TestProperties(String value) {
        this.value = value;
    }

    @Override
    public String get() {
        return value;
    }
}
