package ryukaze.core;

import ryukaze.core.Window;
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

    private static final int RENDER_TICKS_PER_SECOND = 60;
    private static final double RENDER_TIME = 1.0 / RENDER_TICKS_PER_SECOND;

    private static final int GAME_TICKS_PER_SECOND = 20;
    private static final double GAME_TIME = 1.0 / GAME_TICKS_PER_SECOND;

    private static boolean isRunning = true;

    private static long lastRenderTime = System.nanoTime();
    private static long lastGameUpdateTime = System.nanoTime();

    private final long window;
    private static int framesRendered = 0;
    private static double elapsedTime = 0.0;

    public RenderLoop(Window window) {
        this.window = window.getWindowHandle();
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

        StateManager.globalVAO = vao;
        StateManager.globalVBO = vbo;
    }

    public void loop(){
        while (isRunning) {
            long currentTime = System.nanoTime();
            double deltaTimeRender = (currentTime - lastRenderTime) / 1e9;
            double deltaTimeGame = (currentTime - lastGameUpdateTime) / 1e9;

            if (deltaTimeRender >= RENDER_TIME) {
                render();
                lastRenderTime = currentTime;
            }

            if (deltaTimeGame >= GAME_TIME) {
                //updateGameLogic();
                processInput();
                lastGameUpdateTime = currentTime;
            }

            glfwPollEvents();

            // Ajoutez ici tout autre traitement de la boucle principale

            if (glfwWindowShouldClose(window)) {
                isRunning = false;
            }

            elapsedTime += deltaTimeRender;

            // Mise à jour des FPS chaque seconde
            if (elapsedTime >= 1.0) {
                int fps = framesRendered;
                System.out.println("FPS: " + fps);
                framesRendered = 0;
                elapsedTime = 0.0;
            }
        }

        glfwTerminate();

    }

    public void render(){
        glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        glfwSwapBuffers(window);
    }

    public void processInput(){
        InputManager.processInput(window);
    }

}
