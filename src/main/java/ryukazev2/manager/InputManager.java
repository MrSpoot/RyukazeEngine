package ryukazev2.manager;

import ryukazev2.core.InputTouch;
import ryukazev2.utils.ServiceLocator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.glfwGetKey;

public class InputManager extends Manager{

    private final List<InputTouch> inputTouchList;
    private final Map<String, InputTouch> pressedTouch;

    public InputManager(){
        this.inputTouchList = new CopyOnWriteArrayList<>();
        this.pressedTouch = new HashMap<>();
        ServiceLocator.registerService(InputManager.class,this);
    }

    public void process(){
        inputTouchList.forEach((input) -> {
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

    public boolean isPressed(String touchName){
        return this.pressedTouch.containsKey(touchName);
    }

    public void linkNewTouch(InputTouch inputTouch){
        this.inputTouchList.add(inputTouch);
    }

}
