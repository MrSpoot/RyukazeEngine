package ryukaze;

import lombok.Getter;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import ryukaze.core.RenderLoop;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ryukaze.core.StateManager;
import ryukaze.input.InputManager;
import ryukaze.scene.model.Cube;
import ryukaze.shaders.ShaderProgram;
import ryukaze.shaders.Texture;
import ryukaze.shaders.UniformsMap;

import static java.lang.Math.cos;
import static org.joml.Math.sin;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

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

    private static final Logger LOGGER = LoggerFactory.getLogger(Window.class);
    @Getter
    private long window;
    private int width;
    private int height;

    public Vector3f[] cubePosition = {
            new Vector3f( 0.0f, 0.0f,0.0f),
            new Vector3f( 0.0f, 1.0f,0.0f),
            new Vector3f( 0.0f, 2.0f,0.0f),
            new Vector3f( 1.0f, -1.0f,0.0f),
            new Vector3f( -1.0f, -1.0f,0.0f),
            /*new Vector3f( 2.0f, 5.0f,-15.0f),
            new Vector3f(-1.5f,-2.2f,-2.5f),
            new Vector3f(-3.8f,-2.0f,-12.3f),
            new Vector3f( 2.4f,-0.4f,-3.5f),
            new Vector3f(-1.7f, 3.0f,-7.5f),
            new Vector3f( 1.3f,-2.0f,-2.5f),
            new Vector3f( 1.5f, 2.0f,-2.5f),
            new Vector3f( 1.5f, 0.2f,-1.5f),
            new Vector3f(-1.3f, 1.0f,-1.5f),*/
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
        glfwSetFramebufferSizeCallback(window, (window,w,h) -> resize(w,h));

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

        program.useProgram();

        uniforms.setUniform("texture1", 0);
        uniforms.setUniform("texture2", 1);

        uniforms.setUniform("projection",proj);
        uniforms.setUniform("model",model);


        while (!glfwWindowShouldClose(window)) {

            float radius = 5.0f;
            float camX = (float) sin(glfwGetTime()) * radius;
            float camZ = (float) cos(glfwGetTime()) * radius;

            Matrix4f view = new Matrix4f().lookAt(new Vector3f(camX,3.0f,camZ),cameraTarget,up);

            uniforms.setUniform("model",model);
            uniforms.setUniform("view",view);

            program.useProgram();
            InputManager.processInput(window);

            glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            glActiveTexture(GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D, texture1.getTexture());
            glActiveTexture(GL_TEXTURE1);
            glBindTexture(GL_TEXTURE_2D, texture2.getTexture());


            glBindVertexArray(vao);
            //glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
            //glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);

            int i = 0;

            for(Vector3f v : cubePosition){

                Matrix4f _model = new Matrix4f().translate(v);//.rotateXYZ((20.0f * i),0.3f * (20.0f * i),0.5f * (20.0f * i));
                uniforms.setUniform("model",_model);
                glDrawArrays(GL_TRIANGLES,0,36);
                i++;
            }



            glfwSwapBuffers(window);
            glfwPollEvents();

        }

        glfwTerminate();

    }

    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
        glViewport(0, 0, width, height);
    }


}
