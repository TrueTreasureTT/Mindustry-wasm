package mindustry.web;

import org.teavm.jso.JSBody;

/**
 * Browser capability and lifecycle helpers for the Mindustry web target.
 *
 * This class deliberately contains only browser-facing code. The Arc/Mindustry
 * backend can use it without depending on desktop or native APIs.
 */
public final class WebPlatform {
    private WebPlatform() {}

    public static void install() {
        installBrowserDefaults();
    }

    public static int width() {
        return viewportWidth();
    }

    public static int height() {
        return viewportHeight();
    }

    public static double devicePixelRatio() {
        return pixelRatio();
    }

    public static void requestFrame() {
        requestAnimationFrame();
    }

    @JSBody(script = """
        document.documentElement.style.background = '#000';
        document.body.style.margin = '0';
        document.body.style.overflow = 'hidden';
        document.body.style.background = '#000';
        """)
    private static native void installBrowserDefaults();

    @JSBody(script = "return window.innerWidth;")
    private static native int viewportWidth();

    @JSBody(script = "return window.innerHeight;")
    private static native int viewportHeight();

    @JSBody(script = "return window.devicePixelRatio || 1;")
    private static native double pixelRatio();

    @JSBody(script = "window.requestAnimationFrame(function(){ });")
    private static native void requestAnimationFrame();
}
