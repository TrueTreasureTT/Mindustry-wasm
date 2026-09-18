package mindustry.web;

import org.teavm.jso.JSBody;

/**
 * Canvas/WebGL2 bridge used by the web target.
 *
 * The full Arc Graphics implementation is intentionally kept separate from
 * this browser bridge so the web module does not pull desktop SDL/OpenGL
 * implementations into TeaVM.
 */
public final class WebGraphics {
    private WebGraphics() {}

    public static void initialize(String canvasId) {
        configureCanvas(canvasId);
        if (!hasWebGL2(canvasId)) {
            throw new IllegalStateException("WebGL2 is required for Mindustry Web.");
        }
    }

    public static int width(String canvasId) {
        return canvasWidth(canvasId);
    }

    public static int height(String canvasId) {
        return canvasHeight(canvasId);
    }

    public static boolean supportsWebGL2(String canvasId) {
        return hasWebGL2(canvasId);
    }

    @JSBody(params = {"canvasId"}, script = """
        const canvas = document.getElementById(canvasId);
        if (!canvas) return;
        canvas.width = Math.max(1, Math.floor(window.innerWidth * (window.devicePixelRatio || 1)));
        canvas.height = Math.max(1, Math.floor(window.innerHeight * (window.devicePixelRatio || 1)));
        canvas.style.width = '100%';
        canvas.style.height = '100%';
        canvas.style.display = 'block';
        """)
    private static native void configureCanvas(String canvasId);

    @JSBody(params = {"canvasId"}, script = """
        const canvas = document.getElementById(canvasId);
        return !!canvas && !!canvas.getContext('webgl2');
        """)
    private static native boolean hasWebGL2(String canvasId);

    @JSBody(params = {"canvasId"}, script = """
        const canvas = document.getElementById(canvasId);
        return canvas ? canvas.width : 0;
        """)
    private static native int canvasWidth(String canvasId);

    @JSBody(params = {"canvasId"}, script = """
        const canvas = document.getElementById(canvasId);
        return canvas ? canvas.height : 0;
        """)
    private static native int canvasHeight(String canvasId);
}
