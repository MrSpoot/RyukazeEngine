package ryukazev2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ryukazev2.core.Loop;
import ryukazev2.core.Window;
import ryukazev2.manager.*;
import ryukazev2.utils.ServiceLocator;

public class Engine {

    private static final Logger LOGGER = LoggerFactory.getLogger(Engine.class);

    public Engine build(){
        //Create manager
        new SystemManager();
        new EntityManager();
        new CameraManager();
        new SceneManager();
        new RenderManager();
        new InputManager();
        new ScriptManager();

        LOGGER.info("\033[1;32m[BUILD]\u001B[0m Engine instance");
        return this;
    }

    public void run(){
        LOGGER.info("\033[1;32m[RUN]\u001B[0m Engine instance");
        ServiceLocator.getService(SystemManager.class).run();
    }

}