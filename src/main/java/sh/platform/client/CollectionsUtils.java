package sh.platform.client;

import java.util.Collections;
import java.util.Map;

final class CollectionsUtils {

    static Map<String, String> readOnly(Map<String, String> map) {
        if (map == null) {
            return Collections.emptyMap();
        }
        return Collections.unmodifiableMap(map);
    }

    private CollectionsUtils() {
    }
}
