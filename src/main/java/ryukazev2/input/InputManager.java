package ryukazev2.input;

import lombok.Getter;
import ryukazev2.core.Engine;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.lwjgl.glfw.GLFW.*;

public class InputManager {

    private final List<InputTouch> inputTouchList = new CopyOnWriteArrayList<>();
    private final Map<String,InputTouch> pressedTouch = new Hashtable<>();

    public void process(long windowHandle){
        inputTouchList.forEach((input) -> {
            if(glfwGetKey(windowHandle,input.getTouch()) == input.getAction()){
                if(!isPressed(input.getName())){
                    pressedTouch.put(input.getName(),input);
                }
            }else{
                if(isPressed(input.getName())){
                    pressedTouch.remove(input.getName());
                }
            }
        });

        if(isPressed("esc")){
            glfwSetWindowShouldClose(windowHandle,true);
        }
    }

    public void addNewInputTouch(InputTouch inputTouch){
        inputTouchList.add(inputTouch);
    }

    public InputTouch getPressedTouch(String inputName){
        return pressedTouch.get(inputName);
    }

    public boolean isPressed(String inputName){
        return pressedTouch.get(inputName) != null;
    }

}
