package sh.platform.config;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

final class MapConverter {

    private static final Type SERVICE = new HashMap<String, List<Map<String, Object>>>() {
    }.getClass().getGenericSuperclass();

    private static final Type ROUTE = new HashMap<String, Route>() {
    }.getClass().getGenericSuperclass();

    private static final Type VARIABLES = new HashMap<String, String>() {
    }.getClass().getGenericSuperclass();

    private static final Gson GSON = new Gson();


    private MapConverter() {
    }

    static Map<String, List<Map<String, Object>>> toService(String relationship) {
        String text = new String(Base64.getDecoder().decode(relationship), UTF_8);
        return GSON.fromJson(text, SERVICE);
    }

    static Map<String, Route> toRoute(String routes) {
        String text = new String(Base64.getDecoder().decode(routes), UTF_8);
        return GSON.fromJson(text, ROUTE);
    }

    static Map<String, String> toVariable(String variable) {
        String text = new String(Base64.getDecoder().decode(variable), UTF_8);
        return GSON.fromJson(text, VARIABLES);
    }

}
