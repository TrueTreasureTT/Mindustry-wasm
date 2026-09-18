package mindustry.web;

import org.teavm.jso.JSBody;

/**
 * localStorage bridge for lightweight Mindustry settings.
 */
public final class WebStorage {
    private WebStorage() {}

    public static void put(String key, String value) {
        putValue(key, value);
    }

    public static String get(String key, String fallback) {
        String value = getValue(key);
        return value == null ? fallback : value;
    }

    public static void remove(String key) {
        removeValue(key);
    }

    @JSBody(params = {"key", "value"}, script = "window.localStorage.setItem(key, value);")
    private static native void putValue(String key, String value);

    @JSBody(params = {"key"}, script = """
        const value = window.localStorage.getItem(key);
        return value === null ? null : value;
        """)
    private static native String getValue(String key);

    @JSBody(params = {"key"}, script = "window.localStorage.removeItem(key);")
    private static native void removeValue(String key);
}
