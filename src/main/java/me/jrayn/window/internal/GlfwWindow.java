package me.jrayn.window.internal;

import lombok.Getter;
import me.jrayn.window.IWindow;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.system.MemoryStack.stackPush;

public class GlfwWindow implements IWindow {
    @Getter
    private String title;
    @Getter
    private int width, height;
    @Getter
    private boolean fullscreen;

    private long window;


    public GlfwWindow(String title, int width, int height, boolean fullscreen) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.fullscreen = fullscreen;
    }


    /**
     * Create the glfw window instance
     */
    public void createWindow() {
        if (!glfwInit()) {
            System.err.println("Failed to initialize glfw window");
            throw new IllegalStateException("Failed to create window");
        }
        loadHints();
        window = glfwCreateWindow(width, height, title, fullscreen ? glfwGetPrimaryMonitor() : 0, 0);
        if (window == 0) {
            System.err.println("Failed to initialize glfw window");
            throw new IllegalStateException("Failed to create window");
        }

        pushWindow();
        glfwMakeContextCurrent(window);
        glfwShowWindow(window);
        GL.createCapabilities();
        createCallbacks();
    }


    /**
     * register a resize callback
     */
    private void createCallbacks() {
        glfwSetFramebufferSizeCallback(window, (window, width, height) -> {
            this.width = width;
            this.height = height;
            glViewport(0, 0, width, height);
        });
    }

    /**
     * Get the thread stack and push a new frame, centers the window as well
     */
    private void pushWindow() {
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);
            glfwGetWindowSize(window, pWidth, pHeight);
            if (!fullscreen) {
                GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
                glfwSetWindowPos(window, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
            }
        }
    }

    /**
     * Specify parameters for the glfw window
     */
    private void loadHints() {
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_ANY_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);

        glfwWindowHint(GLFW_MAXIMIZED, fullscreen ? GLFW_TRUE : GLFW_FALSE);
        glfwWindowHint(GLFW_DECORATED, GLFW_TRUE);
    }

    /**
     * Dispose the window/close the window
     */
    public void disposeWindow() {
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
        System.out.println("Window disposed");
    }

    /**
     * Updates the glfw state, and input etc
     */
    public void update() {
        glfwSwapBuffers(window);
        glfwPollEvents();
    }

    /**
     * This should be true until a window close event is request
     *
     * @return closing state
     */
    public boolean shouldClose() {
        return glfwWindowShouldClose(window);
    }
}
