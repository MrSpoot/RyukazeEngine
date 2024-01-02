package ryukazev2.core;

import lombok.Data;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ryukazev2.Engine;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

@Data
public class Window {

    private static final Logger LOGGER = LoggerFactory.getLogger(Window.class);

    private long windowHandle;
    private String title;
    private int width;
    private int height;
    private Engine engine;

    public Window(){
        this.title = "";
        this.width = 0;
        this.height = 0;
    }

    public void setEngine(Engine engine){
        this.engine = engine;
    }

    public Window setHeight(int height){
        this.height = height;
        return this;
    }

    public Window setWidth(int width){
        this.width = width;
        return this;
    }

    public Window setTitle(String title){
        this.title = title;
        return this;
    }

    public Window build(){
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);

        if (this.width > 0 && this.height > 0) {
            this.width = this.width;
            this.height = this.height;
        } else {
            glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);
            GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            assert vidMode != null;
            width = vidMode.width();
            height = vidMode.height();
        }

        windowHandle = glfwCreateWindow(width, height, "", NULL, NULL);
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
        /*if (options.fps > 0) {
            glfwSwapInterval(0);
        } else {
            glfwSwapInterval(1);
        }*/

        glViewport(0,0,width,height);

        glfwShowWindow(windowHandle);

        glfwSetWindowTitle(windowHandle,this.title);

        return this;
    }

    public void preRender(){
        glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
    }

    public void postRender(){
        glfwSwapBuffers(this.windowHandle);
        glfwPollEvents();
    }

    private void resize(long windowHandle, int width, int height){
        glViewport(0,0,width,height);
    }

    public boolean shouldClose(){
        return glfwWindowShouldClose(this.windowHandle);
    }
}
