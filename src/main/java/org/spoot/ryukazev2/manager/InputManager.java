package org.spoot.ryukazev2.manager;

import org.spoot.ryukazev2.utils.ServiceLocator;
import org.spoot.ryukazev2.core.InputTouch;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.lwjgl.glfw.GLFW.*;

public class InputManager extends Manager{

    private final Map<String, InputTouch> inputTouchList;
    private final Map<String, InputTouch> pressedTouch;
    private float lastX = 0f;
    private float lastY = 0f;
    private float xAxis = 0f;
    private float yAxis = 0f;
    private boolean mouseIsInitiated = false;

    public InputManager(){
        this.inputTouchList = new ConcurrentHashMap<>();
        this.pressedTouch = new HashMap<>();
        ServiceLocator.registerService(InputManager.class,this);

        glfwSetCursorPosCallback(ServiceLocator.getService(SystemManager.class).getWindow().getWindowHandle(),(w, x, y) -> processMouseInput((float)x,(float)y));
    }

    public void process(){
        inputTouchList.values().forEach((input) -> {
            if(glfwGetKey(((SystemManager) this.services.get(SystemManager.class)).getWindow().getWindowHandle(),input.getTouch()) == GLFW_PRESS){
                if(!isPressed(input.getName())){
                    pressedTouch.put(input.getName(),input);
                }
            }else{
                if(isPressed(input.getName())){
                    pressedTouch.remove(input.getName());
                }
            }
        });
    }

    public void processMouseInput(float x, float y){
        if(!mouseIsInitiated){
            lastX = x;
            lastY = y;
            mouseIsInitiated = true;
        }
        this.xAxis = x - this.lastX;
        this.yAxis = y - this.lastY;

        this.lastX = x;
        this.lastY = y;
    }

    public float getXAxisRaw(){
        float x = this.xAxis;
        this.xAxis = 0;
        return x;
    }
    public float getYAxisRaw(){
        float y = this.yAxis;
        this.yAxis = 0;
        return y;
    }

    public boolean isPressed(String touchName){
        return this.pressedTouch.containsKey(touchName);
    }

    public void linkNewTouch(InputTouch inputTouch){
        if(this.inputTouchList.containsKey(inputTouch.getName())){
            this.inputTouchList.replace(inputTouch.getName(),inputTouch);
        }else {
            this.inputTouchList.put(inputTouch.getName(),inputTouch);
        }
    }

}
