package mindustry.web;

import org.teavm.jso.JSBody;

/**
 * Browser file/resource bridge.
 *
 * Network reads use fetch(); persistent small values use WebStorage.
 * Large save/mod file support belongs in the IndexedDB-backed Arc Files
 * implementation.
 */
public final class WebFiles {
    private WebFiles() {}

    public static String resourceUrl(String path) {
        return normalize(path);
    }

    public static void preload(String path) {
        fetch(path);
    }

    public static void writeText(String path, String value) {
        WebStorage.put("file:" + path, value);
    }

    public static String readText(String path, String fallback) {
        return WebStorage.get("file:" + path, fallback);
    }

    @JSBody(params = {"path"}, script = """
        let p = path || '';
        while (p.startsWith('/')) p = p.substring(1);
        return './' + p;
        """)
    private static native String normalize(String path);

    @JSBody(params = {"path"}, script = """
        fetch(path).catch(error => console.warn('Mindustry resource failed:', path, error));
        """)
    private static native void fetch(String path);
}
