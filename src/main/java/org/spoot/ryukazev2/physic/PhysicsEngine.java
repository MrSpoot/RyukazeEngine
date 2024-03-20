package org.spoot.ryukazev2.physic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spoot.ryukazev2.physic.manager.BodyManager;
import org.spoot.ryukazev2.physic.manager.ColliderManager;

public class PhysicsEngine {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhysicsEngine.class);

    public PhysicsEngine build() {
        //Create manager
        new BodyManager();
        new ColliderManager();

        LOGGER.info("\033[1;32m[BUILD]\u001B[0m Physic Engine instance");

        return this;
    }
}
