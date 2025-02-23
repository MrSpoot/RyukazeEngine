package org.spoot.ryukazev2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spoot.ryukazev2.manager.*;
import org.spoot.ryukazev2.utils.ServiceLocator;

public class Engine {

    private static final Logger LOGGER = LoggerFactory.getLogger(Engine.class);

    public Engine build() {
        //Create manager
        new SystemManager();
        new EntityManager();
        new CameraManager();
        new SceneManager();
        new RenderManager();
        new InputManager();
        new ScriptManager();
        new UIRenderManager().linkFont("Retro","src/main/resources/fonts/retro_gaming.ttf");

        LOGGER.info("\033[1;32m[BUILD]\u001B[0m Engine instance");

        return this;
    }

    public void run(){
        ServiceLocator.getService(SystemManager.class).run();
    }

}
