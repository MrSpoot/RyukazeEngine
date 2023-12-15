package ryukazev2.core;

import lombok.Getter;
import lombok.Setter;

import static org.lwjgl.glfw.GLFW.*;

public class Loop {

    //With Getter
    @Getter
    private static int fps = 0;
    private static int RENDER_TICKS_PER_SECOND = 60;
    private static double RENDER_TIME = 1.0 / RENDER_TICKS_PER_SECOND;
    private static int GAME_TICKS_PER_SECOND = 60;
    private static double GAME_TIME = 1.0 / GAME_TICKS_PER_SECOND;

    //Without Getter
    private static int framesRendered = 0;
    private static double lastSecondTime = 0.0;
    private static long lastRenderTime = System.nanoTime();
    private static long lastGameUpdateTime = System.nanoTime();

    public static void setOptions(Options options){
        setFps(options.fps);
        setUps(options.ups);
    }

    public static void run(){
        while (!glfwWindowShouldClose(Engine.getWindow().getWindowHandle())) {
            long currentTime = System.nanoTime();
            double deltaTimeRender = (currentTime - lastRenderTime) / 1e9;
            double deltaTimeGame = (currentTime - lastGameUpdateTime) / 1e9;

            if (deltaTimeRender >= RENDER_TIME) {
                //RENDER
                Engine.render();
                lastRenderTime = currentTime;
                framesRendered++;
            }

            if (deltaTimeGame >= GAME_TIME) {
                //UPDATE
                Engine.update();
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

    public static void setFps(int fps){
        RENDER_TICKS_PER_SECOND = fps;
        RENDER_TIME = 1.0 / fps;
    }

    public static void setUps(int ups){
        GAME_TICKS_PER_SECOND = ups;
        GAME_TIME = 1.0 / ups;
    }
}
