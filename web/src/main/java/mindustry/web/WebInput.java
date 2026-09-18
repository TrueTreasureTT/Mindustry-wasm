package mindustry.web;

import org.teavm.jso.JSBody;

/**
 * Browser keyboard/mouse input state.
 */
public final class WebInput {
    private WebInput() {}

    public static void install(String canvasId) {
        installInput(canvasId);
    }

    public static boolean keyDown(int keyCode) {
        return isKeyDown(keyCode);
    }

    public static double mouseX() {
        return getMouseX();
    }

    public static double mouseY() {
        return getMouseY();
    }

    public static boolean mouseDown() {
        return isMouseDown();
    }

    @JSBody(params = {"canvasId"}, script = """
        const canvas = document.getElementById(canvasId);
        if (!canvas) return;

        window.__mindustryKeys = window.__mindustryKeys || Object.create(null);
        window.__mindustryInput = window.__mindustryInput || {x: 0, y: 0, down: false};

        window.addEventListener('keydown', e => {
            window.__mindustryKeys[e.keyCode || e.which] = true;
        });

        window.addEventListener('keyup', e => {
            window.__mindustryKeys[e.keyCode || e.which] = false;
        });

        canvas.addEventListener('mousemove', e => {
            const r = canvas.getBoundingClientRect();
            window.__mindustryInput.x = e.clientX - r.left;
            window.__mindustryInput.y = e.clientY - r.top;
        });

        canvas.addEventListener('mousedown', e => {
            if (e.button === 0) window.__mindustryInput.down = true;
        });

        window.addEventListener('mouseup', e => {
            if (e.button === 0) window.__mindustryInput.down = false;
        });
        """)
    private static native void installInput(String canvasId);

    @JSBody(params = {"code"}, script = """
        return !!(window.__mindustryKeys && window.__mindustryKeys[code]);
        """)
    private static native boolean isKeyDown(int code);

    @JSBody(script = "return window.__mindustryInput ? window.__mindustryInput.x : 0;")
    private static native double getMouseX();

    @JSBody(script = "return window.__mindustryInput ? window.__mindustryInput.y : 0;")
    private static native double getMouseY();

    @JSBody(script = "return !!(window.__mindustryInput && window.__mindustryInput.down);")
    private static native boolean isMouseDown();
}
