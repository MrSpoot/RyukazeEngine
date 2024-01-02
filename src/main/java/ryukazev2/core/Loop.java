package ryukazev2.core;

import lombok.Getter;
import lombok.Setter;
import ryukazev2.Engine;

import static org.lwjgl.glfw.GLFW.*;

public class Loop {

    private Engine engine;

    //With Getter
    @Getter
    private int fps = 0;
    private int RENDER_TICKS_PER_SECOND = 60;
    private double RENDER_TIME = 1.0 / RENDER_TICKS_PER_SECOND;
    private int GAME_TICKS_PER_SECOND = 60;
    private double GAME_TIME = 1.0 / GAME_TICKS_PER_SECOND;

    //Without Getter
    private int framesRendered = 0;
    private double lastSecondTime = 0.0;
    private long lastRenderTime = System.nanoTime();
    private long lastGameUpdateTime = System.nanoTime();
    private boolean shouldStop = false;

    public Loop(Engine engine){
        this.engine = engine;
    }

    public void run(){
        while (!shouldStop) {
            long currentTime = System.nanoTime();
            double deltaTimeRender = (currentTime - lastRenderTime) / 1e9;
            double deltaTimeGame = (currentTime - lastGameUpdateTime) / 1e9;

            if (RENDER_TICKS_PER_SECOND <= 0 || deltaTimeRender >= RENDER_TIME) {
                //RENDER
                this.engine.render();
                lastRenderTime = currentTime;
                framesRendered++;
            }

            if (GAME_TICKS_PER_SECOND <= 0 || deltaTimeGame >= GAME_TIME) {
                //UPDATE
                this.engine.update();
                lastGameUpdateTime = currentTime;
            }

            glfwPollEvents();

            if (currentTime - lastSecondTime >= 1e9) {
                fps = framesRendered;
                framesRendered = 0;
                lastSecondTime = currentTime;
            }
        }
        glfwTerminate();
    }

    public void stop(){
        this.shouldStop = true;
    }

    public Loop setFps(int fps){
        RENDER_TICKS_PER_SECOND = fps;
        RENDER_TIME = 1.0 / fps;
        return this;
    }

    public Loop setUps(int ups){
        GAME_TICKS_PER_SECOND = ups;
        GAME_TIME = 1.0 / ups;
        return this;
    }

    public Loop build(){
        return this;
    }
}
