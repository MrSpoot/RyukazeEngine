package ryukaze.core;

import lombok.Data;
import lombok.Setter;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ryukaze.scene.SceneManager;

import java.util.concurrent.Callable;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.system.MemoryUtil.NULL;

@Data
public class Window {

    private static final Logger LOGGER = LoggerFactory.getLogger(Window.class);
    private final long windowHandle;
    private int height;
    private int width;
    private String title;
    private RenderLoop renderLoop;
    private SceneManager sceneManager;
    private int fps = 0;

    public Window(String title, WindowOptions opts) {
        this.title = title;
        this.sceneManager = new SceneManager();
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);

        if (opts.width > 0 && opts.height > 0) {
            this.width = opts.width;
            this.height = opts.height;
        } else {
            glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);
            GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            assert vidMode != null;
            width = vidMode.width();
            height = vidMode.height();
        }

        windowHandle = glfwCreateWindow(width, height, title, NULL, NULL);
        if (windowHandle == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }else{
            StateManager.window = this;
        }

        glfwSetFramebufferSizeCallback(windowHandle, (window, w, h) -> resized(w, h));

        glfwSetErrorCallback((int errorCode, long msgPtr) ->
                LOGGER.error("Error code [{}], msg [{}]", errorCode, MemoryUtil.memUTF8(msgPtr))
        );

        glfwSetKeyCallback(windowHandle, (window, key, scancode, action, mods) -> {
            keyCallBack(key, action);
        });

        glfwMakeContextCurrent(windowHandle);
        GL.createCapabilities();

        glViewport(0,0,width,height);

        if (opts.fps > 0) {
            glfwSwapInterval(0);
        } else {
            glfwSwapInterval(1);
        }

        this.renderLoop = new RenderLoop(this);

        glfwShowWindow(windowHandle);

        int[] arrWidth = new int[1];
        int[] arrHeight = new int[1];
        glfwGetFramebufferSize(windowHandle, arrWidth, arrHeight);
        width = arrWidth[0];
        height = arrHeight[0];
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

    public void run(){
        this.renderLoop.loop();
    }

    protected void resized(int width, int height) {
        this.width = width;
        this.height = height;
        try {
            glViewport(0, 0, width, height);
        } catch (Exception e) {
            LOGGER.error("Error calling resize callback", e);
        }
    }

    public static class WindowOptions {

        public boolean compatibleProfile;
        public int fps;
        public int height;
        public int width;
        public int ups = 20;

        public WindowOptions() {
            this.width = 1280;
            this.height = 720;
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
