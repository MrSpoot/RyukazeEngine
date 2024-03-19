package org.spoot.ryukazev2.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spoot.ryukazev2.component.game.physics.RigidBodyComponent;
import org.spoot.ryukazev2.core.Entity;
import org.spoot.ryukazev2.utils.ServiceLocator;

import java.util.List;

public class PhysicsManager extends Manager{

    private static final Logger LOGGER = LoggerFactory.getLogger(PhysicsManager.class);

    public PhysicsManager() {
        ServiceLocator.registerService(PhysicsManager.class, this);
    }

    public void update(){

        List<Entity> entities = ServiceLocator.getService(EntityManager.class).getEntityByComponent(RigidBodyComponent.class);

        for (Entity entity : entities){
            RigidBodyComponent body = entity.getComponent(RigidBodyComponent.class);
        }

    }
}
