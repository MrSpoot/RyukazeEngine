package ryukazev2.input;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.lwjgl.glfw.GLFW.*;

public class InputManager {

    private final List<InputTouch> inputTouchList = new CopyOnWriteArrayList<>();
    private final List<InputTouch> pressedTouch = new CopyOnWriteArrayList<>();

    float deltaTime = 0.0f;
    float lastFrame = 0.0f;

    public void process(long windowHandle){
        float currentFrame = (float)glfwGetTime();
        deltaTime = currentFrame - lastFrame;
        lastFrame = currentFrame;

        inputTouchList.forEach((input) -> {
            if(glfwGetKey(windowHandle,input.getTouch()) == input.getAction()){
                input.setDeltaTime(deltaTime);
                pressedTouch.add(input);
            }else{
                pressedTouch.remove(input);
            }
        });

    }

    public void addNewInputTouch(InputTouch inputTouch){
        inputTouchList.add(inputTouch);
    }

    public InputTouch isPressed(InputTouch touch){
        if(pressedTouch.stream().anyMatch((input) -> input == touch)){
            return pressedTouch.stream().filter((input) -> input == touch).findFirst().get();
        }else{
            return null;
        }
    }

}
