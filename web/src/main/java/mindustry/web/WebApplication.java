package mindustry.web;

/**
 * Browser application bootstrap.
 *
 * This initializes the browser services needed by the eventual Arc web
 * backend, then starts the TeaVM runtime. The Arc ClientLauncher integration
 * is the next layer and must be supplied by the web backend rather than by
 * desktop SDL classes.
 */
public final class WebApplication {
    private WebApplication() {}

    public static void start(String canvasId) {
        WebPlatform.install();
        WebGraphics.initialize(canvasId);
        WebInput.install(canvasId);
        WebAudio.initialize();
        WebRuntime.start(canvasId);
    }
}
