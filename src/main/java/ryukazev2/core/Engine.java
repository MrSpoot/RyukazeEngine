package ryukazev2.core;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ryukazev2.input.InputManager;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Engine {

    private static final Logger LOGGER = LoggerFactory.getLogger(Engine.class);
    @Getter
    private static Window window;
    @Getter @Setter
    private static Scene scene;
    @Getter
    private static InputManager inputManager;

    public static void init() {
        LOGGER.info("Init Engine");
        window = new Window(new Window.WindowOptions(true, 60, 1280,720 , 60));
        inputManager = new InputManager();
        glfwSetCursorPosCallback(window.getWindowHandle(),(w,x,y) -> inputManager.processMouseInput((float)x,(float)y));
        inputManager.addNewInputTouch(new InputTouch("esc", GLFW_PRESS, GLFW_KEY_ESCAPE));
    }

    public static void run() {
        Loop.run();
    }

    public static void render() {
        //glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        inputManager.process(window.getWindowHandle());

        if(scene != null){
            scene.render();
        }

        window.setWindowTitle("DEBUG | FPS : " + Loop.getFps());

        glfwSwapBuffers(window.getWindowHandle());
        glfwPollEvents();
    }

    public static void update() {
        if(scene != null){
            scene.update();
        }
    }


}
