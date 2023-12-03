package ryukaze;

import lombok.Getter;
import ryukaze.core.RenderLoop;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ryukaze.shaders.ShaderProgram;
import ryukaze.shaders.UniformsMap;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private static final Logger LOGGER = LoggerFactory.getLogger(Window.class);
    @Getter
    private long window;
    private int width;
    private int height;

    public Window(String title, int width, int height) {

        this.width = width;
        this.height = height;

        if (glfwInit()) {
            LOGGER.info("GLFW is initialized");
        } else {
            LOGGER.error("GLFW is not initialized");
        }

        glfwInitHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwInitHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwInitHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwSetErrorCallback((int errorCode, long msgPtr) ->
                LOGGER.error("Error code [" + errorCode + "], msg [" + MemoryUtil.memUTF8(msgPtr) + "]")
        );
        window = glfwCreateWindow(width, height, title, NULL, NULL);

        glfwMakeContextCurrent(window);
        glfwSetFramebufferSizeCallback(window, (window, w, h) -> resize(w, h));

        GL.createCapabilities();

        glViewport(0, 0, width, height);

        ShaderProgram program = new ShaderProgram();

        UniformsMap uniforms = new UniformsMap(program.getProgramId());

        program.useProgram();
        uniforms.createUniform("tex");
        uniforms.createUniform("projection");
        uniforms.createUniform("view");
        uniforms.createUniform("model");
        uniforms.setUniform("tex", 0);

        RenderLoop loop = new RenderLoop();

        loop.loop(this);

        glfwTerminate();

    }

    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
        glViewport(0, 0, width, height);
    }


}
