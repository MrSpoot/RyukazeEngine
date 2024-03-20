package org.spoot.ryukazev2.graphic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spoot.ryukazev2.graphic.manager.*;
import org.spoot.ryukazev2.manager.ScriptManager;
import org.spoot.ryukazev2.manager.SystemManager;
import org.spoot.ryukazev2.utils.ServiceLocator;

public class GraphicsEngine {

    private static final Logger LOGGER = LoggerFactory.getLogger(GraphicsEngine.class);

    public GraphicsEngine build() {
        //Create manager
        new EntityManager();
        new CameraManager();
        new SceneManager();
        new RenderManager();
        new InputManager();
        new UIRenderManager().linkFont("Retro","src/main/resources/fonts/retro_gaming.ttf");

        LOGGER.info("\033[1;32m[BUILD]\u001B[0m Engine instance");

        return this;
    }

    public void run(){
        ServiceLocator.getService(SystemManager.class).run();
    }

}
