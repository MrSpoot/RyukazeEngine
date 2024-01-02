package ryukaze.core;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ryukaze.input.InputManager;
import ryukaze.input.InputTouch;
import ryukaze.physics.PhysicsEngine;

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
    @Getter
    private static PhysicsEngine physicsEngine;

    public static void init(Options options) {
        LOGGER.info("Init Engine");
        window = new Window(options);
        inputManager = new InputManager();
        physicsEngine = new PhysicsEngine();
        inputManager.addNewInputTouch(new InputTouch("esc", GLFW_PRESS, GLFW_KEY_ESCAPE));
        Loop.setOptions(options);
    }

    public static void run() {
        Loop.run();
    }

    public static void render() {
        //glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);

        inputManager.process(window.getWindowHandle());

        if(scene != null){
            scene.render();
        }

        //window.setWindowTitle("DEBUG | FPS : " + Loop.getFps());

        glfwSwapBuffers(window.getWindowHandle());
        glfwPollEvents();
    }

    public static void update() {
        if(scene != null){
            scene.update();
        }
        if(physicsEngine != null){
            physicsEngine.update();
        }
    }

}
