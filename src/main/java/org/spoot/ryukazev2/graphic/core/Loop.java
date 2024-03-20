package org.spoot.ryukazev2.graphic.core;

import org.spoot.ryukazev2.graphic.manager.SystemManager;
import org.spoot.ryukazev2.graphic.utils.ServiceLocator;

import static org.lwjgl.glfw.GLFW.*;

public class Loop {

    //With Getter
    
    private int RENDER_TICKS_PER_SECOND = 500;
    private double RENDER_TIME = 1.0 / RENDER_TICKS_PER_SECOND;
    private int GAME_TICKS_PER_SECOND = 20;
    private double GAME_TIME = 1.0 / GAME_TICKS_PER_SECOND;

    //Without Getter
    private int framesRendered = 0;
    private int updateRendered = 0;
    private double lastSecondTime = 0.0;
    private long lastRenderTime = System.nanoTime();
    private long lastGameUpdateTime = System.nanoTime();
    private boolean shouldStop = false;

    public void run(){

        Statistics.updateStats("framesRendered",0);

        while (!shouldStop) {
            long currentTime = System.nanoTime();
            double deltaTimeRender = (currentTime - lastRenderTime) / 1e9;
            double deltaTimeGame = (currentTime - lastGameUpdateTime) / 1e9;

            if (RENDER_TICKS_PER_SECOND <= 0 || deltaTimeRender >= RENDER_TIME) {
                //RENDER
                ServiceLocator.getService(SystemManager.class).render();
                lastRenderTime = currentTime;
                Statistics.updateStats("framesRendered",Integer.valueOf(Statistics.getStats("framesRendered"))+framesRendered);
                framesRendered++;
            }

            if (GAME_TICKS_PER_SECOND <= 0 || deltaTimeGame >= GAME_TIME) {
                //UPDATE
                Time.update();
                ServiceLocator.getService(SystemManager.class).update();
                lastGameUpdateTime = currentTime;
                updateRendered++;
            }

            glfwPollEvents();

            if (currentTime - lastSecondTime >= 1e9) {
                Statistics.updateStats("fps",framesRendered);
                Statistics.updateStats("ups",updateRendered);

                framesRendered = 0;
                updateRendered = 0;
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
