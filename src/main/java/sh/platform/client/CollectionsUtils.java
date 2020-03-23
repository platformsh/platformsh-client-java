package sh.platform.client;

import java.util.Collections;
import java.util.Map;

final class CollectionsUtils {

    static <K, V> Map<K, V> readOnly(Map<K, V> map) {
        if (map == null) {
            return Collections.emptyMap();
        }
        return Collections.unmodifiableMap(map);
    }

    private CollectionsUtils() {
    }
}
