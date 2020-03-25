package sh.platform.client.util;

import java.util.Collections;
import java.util.Map;

public final class CollectionsUtils {

    public static <K, V> Map<K, V> readOnly(Map<K, V> map) {
        if (map == null) {
            return Collections.emptyMap();
        }
        return Collections.unmodifiableMap(map);
    }

    private CollectionsUtils() {
    }
}
