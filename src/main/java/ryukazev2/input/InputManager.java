package ryukazev2.input;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.lwjgl.glfw.GLFW.*;

public class InputManager {

    private final List<InputTouch> inputTouchList = new CopyOnWriteArrayList<>();
    private final Map<String,InputTouch> pressedTouch = new Hashtable<>();
    @Getter
    private final MouseInput mouseInput = new MouseInput(0f,0f);

    float deltaTime = 0.0f;
    float lastFrame = 0.0f;

    public void process(long windowHandle){
        float currentFrame = (float)glfwGetTime();
        deltaTime = currentFrame - lastFrame;
        lastFrame = currentFrame;

        inputTouchList.forEach((input) -> {
            if(glfwGetKey(windowHandle,input.getTouch()) == input.getAction()){
                if(isPressed(input.getName()) != null){
                    pressedTouch.get(input.getName()).setDeltaTime(deltaTime);
                }else{
                    input.setDeltaTime(deltaTime);
                    pressedTouch.put(input.getName(),input);
                }
            }else{
                pressedTouch.remove(input.getName());
            }
        });

        if(isPressed("esc") != null){
            glfwSetWindowShouldClose(windowHandle,true);
        }
    }

    public final void processMouseInput(float xPos, float yPos){
        this.mouseInput.setXPos(xPos);
        this.mouseInput.setYPos(yPos);
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
