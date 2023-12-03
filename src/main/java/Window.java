import input.InputManager;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shaders.ShaderProgram;
import shaders.Texture;
import shaders.UniformsMap;
import utils.ImageReader;

import java.nio.IntBuffer;

import static java.lang.Math.sin;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private static final Logger LOGGER = LoggerFactory.getLogger(Window.class);
    private long window;

    float vertices[] = {
            -0.5f, -0.5f, -0.5f,  0.0f, 0.0f,
            0.5f, -0.5f, -0.5f,  1.0f, 0.0f,
            0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
            0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
            -0.5f,  0.5f, -0.5f,  0.0f, 1.0f,
            -0.5f, -0.5f, -0.5f,  0.0f, 0.0f,

            -0.5f, -0.5f,  0.5f,  0.0f, 0.0f,
            0.5f, -0.5f,  0.5f,  1.0f, 0.0f,
            0.5f,  0.5f,  0.5f,  1.0f, 1.0f,
            0.5f,  0.5f,  0.5f,  1.0f, 1.0f,
            -0.5f,  0.5f,  0.5f,  0.0f, 1.0f,
            -0.5f, -0.5f,  0.5f,  0.0f, 0.0f,

            -0.5f,  0.5f,  0.5f,  1.0f, 0.0f,
            -0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
            -0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
            -0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
            -0.5f, -0.5f,  0.5f,  0.0f, 0.0f,
            -0.5f,  0.5f,  0.5f,  1.0f, 0.0f,

            0.5f,  0.5f,  0.5f,  1.0f, 0.0f,
            0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
            0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
            0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
            0.5f, -0.5f,  0.5f,  0.0f, 0.0f,
            0.5f,  0.5f,  0.5f,  1.0f, 0.0f,

            -0.5f, -0.5f, -0.5f,  0.0f, 1.0f,
            0.5f, -0.5f, -0.5f,  1.0f, 1.0f,
            0.5f, -0.5f,  0.5f,  1.0f, 0.0f,
            0.5f, -0.5f,  0.5f,  1.0f, 0.0f,
            -0.5f, -0.5f,  0.5f,  0.0f, 0.0f,
            -0.5f, -0.5f, -0.5f,  0.0f, 1.0f,

            -0.5f,  0.5f, -0.5f,  0.0f, 1.0f,
            0.5f,  0.5f, -0.5f,  1.0f, 1.0f,
            0.5f,  0.5f,  0.5f,  1.0f, 0.0f,
            0.5f,  0.5f,  0.5f,  1.0f, 0.0f,
            -0.5f,  0.5f,  0.5f,  0.0f, 0.0f,
            -0.5f,  0.5f, -0.5f,  0.0f, 1.0f
    };

    public Window(String title, int width, int height) {

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
        glfwSetFramebufferSizeCallback(window, this::resize);

        GL.createCapabilities();

        glViewport(0, 0, width, height);

        ShaderProgram program = new ShaderProgram();

        int vao = glGenVertexArrays();
        int vbo = glGenBuffers();
        //int ebo = glGenBuffers();

        glBindVertexArray(vao);

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        //glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        //glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 20, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, 2, GL_FLOAT, false, 20, 12);
        glEnableVertexAttribArray(1);

        glEnable(GL_DEPTH_TEST);

        /*glVertexAttribPointer(2, 2, GL_FLOAT, false, 32, 24);
        glEnableVertexAttribArray(2);*/

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);


        if (window == 0) {
            glfwTerminate();
            System.exit(-1);
        }

        UniformsMap uniforms = new UniformsMap(program.getProgramId());

        uniforms.createUniform("texture1");
        uniforms.createUniform("texture2");
        uniforms.createUniform("projection");
        uniforms.createUniform("view");
        uniforms.createUniform("model");

        Texture texture1 = new Texture("src/main/resources/texture/wall.jpg",false);
        Texture texture2 = new Texture("src/main/resources/texture/awesomeface.png",true);

        Matrix4f proj = new Matrix4f().perspective(45, (float) width /height,0.1f,1000f);
        Matrix4f model = new Matrix4f().rotateX(0);
        Matrix4f view = new Matrix4f().translate(0.0f,0.0f,-3f);

        program.useProgram();

        uniforms.setUniform("texture1", 0);
        uniforms.setUniform("texture2", 1);

        uniforms.setUniform("projection",proj);
        uniforms.setUniform("view",view);
        uniforms.setUniform("model",model);


        while (!glfwWindowShouldClose(window)) {

            model.rotateX(0.01f).rotateY(0.01f);

            uniforms.setUniform("model",model);

            program.useProgram();
            InputManager.processInput(window);

            glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            glActiveTexture(GL_TEXTURE1);
            glBindTexture(GL_TEXTURE_2D, texture2.getTexture());
            glActiveTexture(GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D, texture1.getTexture());

            glBindVertexArray(vao);
            //glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
            //glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);

            glDrawArrays(GL_TRIANGLES,0,36);

            glfwSwapBuffers(window);
            glfwPollEvents();

        }

        glfwTerminate();

    }

    public void resize(long window, int width, int height) {
        glViewport(0, 0, width, height);
    }


}
