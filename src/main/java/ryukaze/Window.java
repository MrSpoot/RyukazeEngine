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
import ryukaze.graphics.Camera;
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
            -0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f,  0.5f, -0.5f,
            0.5f,  0.5f, -0.5f,
            -0.5f,  0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,

            -0.5f, -0.5f,  0.5f,
            0.5f, -0.5f,  0.5f,
            0.5f,  0.5f,  0.5f,
            0.5f,  0.5f,  0.5f,
            -0.5f,  0.5f,  0.5f,
            -0.5f, -0.5f,  0.5f,

            -0.5f,  0.5f,  0.5f,
            -0.5f,  0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,
            -0.5f, -0.5f,  0.5f,
            -0.5f,  0.5f,  0.5f,

            0.5f,  0.5f,  0.5f,
            0.5f,  0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, -0.5f,  0.5f,
            0.5f,  0.5f,  0.5f,

            -0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, -0.5f,  0.5f,
            0.5f, -0.5f,  0.5f,
            -0.5f, -0.5f,  0.5f,
            -0.5f, -0.5f, -0.5f,

            -0.5f,  0.5f, -0.5f,
            0.5f,  0.5f, -0.5f,
            0.5f,  0.5f,  0.5f,
            0.5f,  0.5f,  0.5f,
            -0.5f,  0.5f,  0.5f,
            -0.5f,  0.5f, -0.5f,
    };

    private static final Logger LOGGER = LoggerFactory.getLogger(Window.class);
    @Getter
    private long window;
    private int width;
    private int height;

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

        Camera camera = new Camera(70,(float)width/height,0.1f,1000f,new Vector3f(0.0f,0.0f,-3.0f));

        glfwMakeContextCurrent(window);
        glfwSetFramebufferSizeCallback(window, (window,w,h) -> resize(w,h));
        glfwSetInputMode(this.getWindow(),GLFW_CURSOR,GLFW_CURSOR_DISABLED);
        glfwSetCursorPosCallback(this.getWindow(),(w,x,y) -> processMouseInput(w,camera,(float)x,(float)y));
        GL.createCapabilities();

        glViewport(0, 0, width, height);

        ShaderProgram program = new ShaderProgram("src/main/resources/shader/vertex.vert","src/main/resources/shader/fragment.frag");
        ShaderProgram light = new ShaderProgram("src/main/resources/shader/vertex.vert","src/main/resources/shader/light.frag");

        int vao = glGenVertexArrays();
        int vbo = glGenBuffers();

        //int ebo = glGenBuffers();

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        glBindVertexArray(vao);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 12, 0);
        glEnableVertexAttribArray(0);

        int lightVao = glGenVertexArrays();

        glBindVertexArray(lightVao);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);

        glVertexAttribPointer(0, 3, GL_FLOAT, false,12, 0);
        glEnableVertexAttribArray(0);

        //glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        //glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        //glVertexAttribPointer(1, 2, GL_FLOAT, false, 20, 12);
        //glEnableVertexAttribArray(1);

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
        UniformsMap lightUniforms = new UniformsMap(light.getProgramId());

        Vector3f lightColor = new Vector3f(1.0f,1.0f,1.0f);

        //uniforms.createUniform("texture1");
       // uniforms.createUniform("texture2");
        uniforms.createUniform("projection");
        uniforms.createUniform("view");
        uniforms.createUniform("model");

        lightUniforms.createUniform("projection");
        lightUniforms.createUniform("view");
        lightUniforms.createUniform("model");
        lightUniforms.createUniform("lightColor");


        uniforms.createUniform("objectColor");
        uniforms.createUniform("lightColor");

        //Texture texture1 = new Texture("src/main/resources/texture/wall.jpg",false);
        //Texture texture2 = new Texture("src/main/resources/texture/awesomeface.png",true);

        program.useProgram();

        //uniforms.setUniform("texture1", 0);
        //uniforms.setUniform("texture2", 1);

        float deltaTime = 0.0f;
        float lastFrame = 0.0f;

        while (!glfwWindowShouldClose(window)) {


            float currentFrame = (float)glfwGetTime();
            deltaTime = currentFrame - lastFrame;
            lastFrame = currentFrame;

            InputManager.processInput(window);
            camera.processInput(this,deltaTime);

            //glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            /*glActiveTexture(GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D, texture1.getTexture());
            glActiveTexture(GL_TEXTURE1);
            glBindTexture(GL_TEXTURE_2D, texture2.getTexture());*/


            glBindVertexArray(vao);
            //glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
            //glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);

            program.useProgram();

            uniforms.setUniform("objectColor", new Vector3f(1.0f,0.5f,0.31f));
            uniforms.setUniform("lightColor", lightColor);
            uniforms.setUniform("projection",camera.getProjection());
            uniforms.setUniform("view",camera.getLookAt());


            Matrix4f _model = new Matrix4f().translate(new Vector3f(0.0f,0.0f,0.0f));
            uniforms.setUniform("model",_model);
            glDrawArrays(GL_TRIANGLES,0,36);

            light.useProgram();

            lightUniforms.setUniform("projection",camera.getProjection());
            lightUniforms.setUniform("view",camera.getLookAt());
            lightUniforms.setUniform("lightColor",lightColor);
            Matrix4f _modelLight = new Matrix4f().translate(new Vector3f(2.0f,0.0f,3.0f));
            lightUniforms.setUniform("model",_modelLight);

            glBindVertexArray(lightVao);
            glDrawArrays(GL_TRIANGLES,0,36);



            glfwSwapBuffers(window);
            glfwPollEvents();

        }

        glfwTerminate();

    }

    public void processMouseInput(long window,Camera camera, float xpos, float ypos){
        camera.processMouseInput(window,width,height,xpos,ypos);
    }

    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
        glViewport(0, 0, width, height);
    }


}
