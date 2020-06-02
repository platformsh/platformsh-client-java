package sh.platform.client;

import java.util.function.Supplier;

public enum Variables implements Supplier<String> {

    PROJECT("project.id"),
    COMBO("project.combo"),
    TOKEN("user.token"),
    COMMIT("project.commit"),
    BLOB("project.blog"),
    TREE("project.tree"),
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
