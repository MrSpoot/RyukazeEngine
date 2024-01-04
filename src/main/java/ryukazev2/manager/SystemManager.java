package ryukazev2.manager;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ryukazev2.Engine;
import ryukazev2.core.Loop;
import ryukazev2.core.Window;
import ryukazev2.utils.ServiceLocator;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class SystemManager extends Manager {

    private static final Logger LOGGER = LoggerFactory.getLogger(Engine.class);

    private Window window;
    private Loop loop;
    private float lastUpdateTime;

    public SystemManager() {
        this.window = new Window().build();
        this.loop = new Loop().build();
        this.lastUpdateTime = 0f;
        ServiceLocator.registerService(SystemManager.class,this);
    }

    public SystemManager setWindow(Window window){
        this.window = window;
        return this;
    }

    public void render(){
        if(this.window.shouldClose() || ((InputManager) this.services.get(InputManager.class)).isPressed("exit_engine")){
            stop();
        }

        ((InputManager) this.services.get(InputManager.class)).process();
        ((ScriptManager) this.services.get(ScriptManager.class)).render();

        this.window.preRender();

        ((RenderManager) this.services.get(RenderManager.class)).render();

        this.window.postRender();
    }

    public void update(){
        float currentFrame = (float)glfwGetTime();
        float deltaTime = currentFrame - lastUpdateTime;
        lastUpdateTime = currentFrame;

        ((ScriptManager) this.services.get(ScriptManager.class)).update(deltaTime);
    }

    public void run(){
        ((ScriptManager) this.services.get(ScriptManager.class)).init();

        this.loop.run();
    }

    public void stop(){
        LOGGER.info("\033[1;32m[STOP]\u001B[0m Engine instance");
        this.loop.stop();
    }
}
