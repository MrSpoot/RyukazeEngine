package org.spoot.ryukazev2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spoot.ryukazev2.graphic.GraphicsEngine;
import org.spoot.ryukazev2.manager.ScriptManager;
import org.spoot.ryukazev2.manager.SystemManager;
import org.spoot.ryukazev2.physic.PhysicsEngine;
import org.spoot.ryukazev2.utils.ServiceLocator;

public class Engine {

    private static final Logger LOGGER = LoggerFactory.getLogger(Engine.class);

    public Engine build() {

        GraphicsEngine graphicsEngine = new GraphicsEngine();
        PhysicsEngine physicsEngine = new PhysicsEngine();

        //Create manager
        new SystemManager();
        new ScriptManager();

        graphicsEngine.build();
        physicsEngine.build();

        LOGGER.info("\033[1;32m[BUILD]\u001B[0m Engine instance");

        return this;
    }

    public void run(){
        ServiceLocator.getService(SystemManager.class).run();
    }

}
