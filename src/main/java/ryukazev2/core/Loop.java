package ryukazev2.core;

import lombok.Getter;
import ryukazev2.core.utils.LoopObserver;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;

public class Loop {

    //With Getter
    @Getter
    private static int fps = 0;

    //Without Getter
    private static int framesRendered = 0;
    private static double elapsedTime = 0.0;
    private static final int RENDER_TICKS_PER_SECOND = 60;
    private static final double RENDER_TIME = 1.0 / RENDER_TICKS_PER_SECOND;
    private static final int GAME_TICKS_PER_SECOND = 20;
    private static final double GAME_TIME = 1.0 / GAME_TICKS_PER_SECOND;
    private static boolean isRunning = true;
    private static long lastRenderTime = System.nanoTime();
    private static long lastGameUpdateTime = System.nanoTime();
    private static List<LoopObserver> observers = new ArrayList<>();

    public static void run(){
        while (isRunning) {
            long currentTime = System.nanoTime();
            double deltaTimeRender = (currentTime - lastRenderTime) / 1e9;
            double deltaTimeGame = (currentTime - lastGameUpdateTime) / 1e9;

            if (deltaTimeRender >= RENDER_TIME) {
                //RENDER
                lastRenderTime = currentTime;
            }

            if (deltaTimeGame >= GAME_TIME) {
                //UPDATE
                lastGameUpdateTime = currentTime;
            }

            glfwPollEvents();

            elapsedTime += deltaTimeRender;

            if (elapsedTime >= 1.0) {
                fps = framesRendered;
                framesRendered = 0;
                elapsedTime = 0.0;
            }
        }
    }

    public static void stop(){
        isRunning = false;
    }

    private void render() {
        observers.forEach(LoopObserver::render);
    }

    private void update() {
        observers.forEach(LoopObserver::update);
    }
}
