package sh.platform.config;

import java.util.Map;
import java.util.function.Function;

/**
 * <p> In some cases the library being used to connect to a service wants its credentials formatted in a specific way;</p>
 * <p>it could be a DSN string of some sort or it needs certain values concatenated to the database name, etc.</p>
 * <p>For those cases you can use "Credential Formatters".</p>
 *
 * <p>A Credential Formatter is a functional interface that takes a credentials array and returns any type, since the
 * library may want different types.</p>
 *
 * @param <T> the produced type
 */
@FunctionalInterface
public interface CredentialFormatter<T> extends Function<Map<String, Object>, T> {
}
