package org.spoot.ryukazev2.core;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Time {

    public static float deltaTime = 0f;
    private static float lastTime = 0.0f;

    static void update(){
        float currentFrame = (float)glfwGetTime();
        deltaTime = currentFrame - lastTime;
        lastTime = currentFrame;
    }
}
