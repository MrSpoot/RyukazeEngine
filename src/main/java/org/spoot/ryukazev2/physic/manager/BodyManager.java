package org.spoot.ryukazev2.physic.manager;

import lombok.Getter;
import org.joml.Vector3f;
import org.spoot.ryukazev2.component.TransformComponent;
import org.spoot.ryukazev2.core.Time;
import org.spoot.ryukazev2.graphic.core.Entity;
import org.spoot.ryukazev2.graphic.manager.EntityManager;
import org.spoot.ryukazev2.manager.Manager;
import org.spoot.ryukazev2.physic.component.body.Rigidbody;
import org.spoot.ryukazev2.utils.ServiceLocator;

import java.util.List;

@Getter
public class BodyManager extends Manager {

    public BodyManager() {
        ServiceLocator.registerService(BodyManager.class, this);
    }

    public void processEntityPhysics(){

        List<Entity> entities = ((EntityManager) this.services.get(EntityManager.class)).getEntityByComponent(TransformComponent.class, Rigidbody.class);

        for(Entity entity : entities){

            Rigidbody rigidbody = entity.getComponent(Rigidbody.class);
            TransformComponent transformComponent = entity.getComponent(TransformComponent.class);

            float velocity = 1.81f * Time.deltaTime;

            transformComponent.getPosition().y -= velocity * Time.deltaTime;

        }
    }
}
