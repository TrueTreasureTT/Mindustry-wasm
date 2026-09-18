package mindustry.web;

import org.teavm.jso.JSBody;

/**
 * Minimal Web Audio bridge. Audio playback can be layered on top of this
 * context by the Arc web audio implementation.
 */
public final class WebAudio {
    private WebAudio() {}

    public static void initialize() {
        createContext();
    }

    public static void resume() {
        resumeContext();
    }

    public static boolean available() {
        return hasContext();
    }

    @JSBody(script = """
        if (!window.__mindustryAudio) {
            const Ctx = window.AudioContext || window.webkitAudioContext;
            if (Ctx) window.__mindustryAudio = new Ctx();
        }
        """)
    private static native void createContext();

    @JSBody(script = """
        if (window.__mindustryAudio && window.__mindustryAudio.state === 'suspended') {
            window.__mindustryAudio.resume();
        }
        """)
    private static native void resumeContext();

    @JSBody(script = "return !!window.__mindustryAudio;")
    private static native boolean hasContext();
}
