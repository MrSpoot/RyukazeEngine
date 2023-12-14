package ryukazev2.core;

import lombok.Data;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

@Data
public class Window {

    private static final Logger LOGGER = LoggerFactory.getLogger(Window.class);

    private long windowHandle;
    private int width;
    private int height;

    public Window(WindowOptions options){

        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);

        if (options.width > 0 && options.height > 0) {
            this.width = options.width;
            this.height = options.height;
        } else {
            glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);
            GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            assert vidMode != null;
            width = vidMode.width();
            height = vidMode.height();
        }

        windowHandle = glfwCreateWindow(width, height, "DEBUG", NULL, NULL);
        if (windowHandle == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        glfwSetFramebufferSizeCallback(windowHandle, this::resize);

        glfwSetErrorCallback((int errorCode, long msgPtr) ->
                LOGGER.error("Error code [{}], msg [{}]", errorCode, MemoryUtil.memUTF8(msgPtr))
        );
        glfwMakeContextCurrent(windowHandle);
        glfwSetInputMode(windowHandle,GLFW_CURSOR,GLFW_CURSOR_DISABLED);
        GL.createCapabilities();
        if (options.fps > 0) {
            glfwSwapInterval(0);
        } else {
            glfwSwapInterval(1);
        }

        glViewport(0,0,width,height);

        glEnable(GL_DEPTH_TEST);

        glfwShowWindow(windowHandle);
    }

    public void setWindowTitle(String title){
        glfwSetWindowTitle(windowHandle,title);
    }

    private void resize(long windowHandle, int width, int height){
        glViewport(0,0,width,height);
    }

    public static class WindowOptions {

        public boolean compatibleProfile;
        public int fps;
        public int height;
        public int width;
        public int ups = 20;

        public WindowOptions(boolean compatibleProfile, int fps, int width, int height , int ups) {
            this.compatibleProfile = compatibleProfile;
            this.fps = fps;
            this.height = height;
            this.width = width;
            this.ups = ups;
        }
    }

}
