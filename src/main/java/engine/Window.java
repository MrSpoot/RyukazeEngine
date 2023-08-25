package engine;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryUtil;
import org.pmw.tinylog.Logger;

import java.util.concurrent.Callable;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private final long windowHandle;
    private int height;
    private int width;
    private String title;
    private MouseInput mouseInput;
    private Callable<Void> resizeFunc;

    public Window(String title, WindowOptions opts, Callable<Void> resizeFunc) {
        this.resizeFunc = resizeFunc;
        this.title = title;
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 2);

        if (opts.width > 0 && opts.height > 0) {
            this.width = opts.width;
            this.height = opts.height;
        } else {
            glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);
            GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            width = vidMode.width();
            height = vidMode.height();
        }

        windowHandle = glfwCreateWindow(width, height, title, NULL, NULL);
        if (windowHandle == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        glfwSetFramebufferSizeCallback(windowHandle, (window, w, h) -> resized(w, h));

        glfwSetErrorCallback((int errorCode, long msgPtr) ->
                Logger.error("Error code [{}], msg [{}]", errorCode, MemoryUtil.memUTF8(msgPtr))
        );

        glfwSetKeyCallback(windowHandle, (window, key, scancode, action, mods) -> {
            keyCallBack(key, action);
        });

        glfwMakeContextCurrent(windowHandle);

        if (opts.fps > 0) {
            glfwSwapInterval(0);
        } else {
            glfwSwapInterval(1);
        }

        glfwShowWindow(windowHandle);

        int[] arrWidth = new int[1];
        int[] arrHeight = new int[1];
        glfwGetFramebufferSize(windowHandle, arrWidth, arrHeight);
        width = arrWidth[0];
        height = arrHeight[0];
        mouseInput = new MouseInput(windowHandle);
    }

    public void keyCallBack(int key, int action) {
        if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
            glfwSetWindowShouldClose(windowHandle, true);
        }
    }

    public void cleanup() {
        //glfwFreeCallbacks(windowHandle);
        glfwDestroyWindow(windowHandle);
        glfwTerminate();
        GLFWErrorCallback callback = glfwSetErrorCallback(null);
        if (callback != null) {
            callback.free();
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public long getWindowHandle() {
        return windowHandle;
    }

    public MouseInput getMouseInput() {
        return mouseInput;
    }

    public boolean isKeyPressed(int keyCode) {
        return glfwGetKey(windowHandle, keyCode) == GLFW_PRESS;
    }

    public void pollEvents() {
        glfwPollEvents();
    }

    protected void resized(int width, int height) {
        this.width = width;
        this.height = height;
        try {
            resizeFunc.call();
        } catch (Exception e) {
            Logger.error("Error calling resize callback", e);
        }
    }

    public void update() {
        glfwSwapBuffers(windowHandle);
    }

    public void updateFps(float fps){
        glfwSetWindowTitle(windowHandle, (title + " | FPS : "+String.format("%.1f",fps)));
    }

    public boolean windowShouldClose() {
        return glfwWindowShouldClose(windowHandle);
    }

    public static class WindowOptions {

        public boolean compatibleProfile;
        public int fps;
        public int height;
        public int width;
        public int ups = Engine.TARGET_UPS;

        public WindowOptions() {
        }

        public WindowOptions(boolean compatibleProfile, int fps, int height, int width, int ups) {
            this.compatibleProfile = compatibleProfile;
            this.fps = fps;
            this.height = height;
            this.width = width;
            this.ups = ups;
        }
    }
}
