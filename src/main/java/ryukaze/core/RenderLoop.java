package ryukaze.core;

import ryukaze.Window;
import ryukaze.input.InputManager;
import ryukaze.scene.model.Cube;
import ryukaze.shaders.Texture;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class RenderLoop {


    public RenderLoop() {
        init();
    }

    private void init(){
        int vao = glGenVertexArrays();
        int vbo = glGenBuffers();


        glBindVertexArray(vao);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 20, 0);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 20, 12);
        glEnableVertexAttribArray(1);
        glEnable(GL_DEPTH_TEST);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

        StateManager.setGlobalVAO(vao);
        StateManager.setGlobalVBO(vbo);
    }

    public void loop(Window window){

        Cube cube = new Cube(new Texture("src/main/resources/texture/wall.jpg",false));

        while(!glfwWindowShouldClose(window.getWindow())){

            InputManager.processInput(window.getWindow());

            glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            cube.render();

            glfwSwapBuffers(window.getWindow());
            glfwPollEvents();

        }

        glfwTerminate();

    }

}
