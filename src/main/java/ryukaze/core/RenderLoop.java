package ryukaze.core;

import ryukaze.input.InputManager;
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

    private final int RENDER_TICKS_PER_SECOND = 60;
    private final double RENDER_TIME = 1.0 / RENDER_TICKS_PER_SECOND;
    private final int GAME_TICKS_PER_SECOND = 20;
    private final double GAME_TIME = 1.0 / GAME_TICKS_PER_SECOND;
    private boolean isRunning = true;
    private long lastRenderTime = System.nanoTime();
    private long lastGameUpdateTime = System.nanoTime();
    private final Window window;
    private int framesRendered = 0;
    private double elapsedTime = 0.0;

    public RenderLoop(Window window) {
        this.window = window;
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
    }

    public void loop(){
        while (isRunning) {
            long currentTime = System.nanoTime();
            double deltaTimeRender = (currentTime - lastRenderTime) / 1e9;
            double deltaTimeGame = (currentTime - lastGameUpdateTime) / 1e9;

            if (deltaTimeRender >= RENDER_TIME) {
                processInput();
                render();
                lastRenderTime = currentTime;
            }

            if (deltaTimeGame >= GAME_TIME) {
                update();
                lastGameUpdateTime = currentTime;
            }

            glfwPollEvents();

            if (glfwWindowShouldClose(window.getWindowHandle())) {
                isRunning = false;
            }

            elapsedTime += deltaTimeRender;

            if (elapsedTime >= 1.0) {
                this.window.setFps(framesRendered);
                framesRendered = 0;
                elapsedTime = 0.0;
            }
        }

        this.window.cleanup();

    }

    public void render(){
        glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        if(window.getSceneManager().getActualScene() != null){
            window.getSceneManager().getActualScene().render();
        }

        glfwSwapBuffers(window.getWindowHandle());
    }

    public void update(){

    }

    public void processInput(){
        InputManager.processInput(window.getWindowHandle());
        if(window.getSceneManager().getActualScene() != null){
            window.getSceneManager().getActualScene().update();
        }
    }

}
