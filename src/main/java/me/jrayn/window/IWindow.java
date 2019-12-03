package me.jrayn.window;

public interface IWindow {
    int getWidth();

    int getHeight();

    String getTitle();

    boolean isFullscreen();

    /**
     * Create the window that will be rendered to
     */
    void createWindow();

    /**
     * Destroy the window/shutdown the window
     */
    void disposeWindow();

    /**
     * This should update the windows buffers
     */
    void update();

    /**
     * This should be true until a window close event is request
     * @return closing state
     */
    boolean shouldClose();
}
