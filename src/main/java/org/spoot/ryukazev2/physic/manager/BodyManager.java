package org.spoot.ryukazev2.physic.manager;

import lombok.Getter;
import org.joml.Vector3f;
import org.spoot.ryukazev2.component.TransformComponent;
import org.spoot.ryukazev2.graphic.core.Entity;
import org.spoot.ryukazev2.graphic.manager.EntityManager;
import org.spoot.ryukazev2.manager.Manager;
import org.spoot.ryukazev2.physic.component.body.Rigidbody;
import org.spoot.ryukazev2.utils.ServiceLocator;

import java.util.List;

@Getter
public class BodyManager extends Manager {

    private List<Entity> entities;

    public BodyManager() {
        ServiceLocator.registerService(BodyManager.class, this);
    }

    public void processVelocity(){

        entities = ((EntityManager) this.services.get(EntityManager.class)).getEntityByComponent(TransformComponent.class, Rigidbody.class);

        for(Entity entity : entities){
            Rigidbody rigidbody = entity.getComponent(Rigidbody.class);
            Vector3f gravity = new Vector3f(0,-9.81f,0).mul(rigidbody.getMass());
            //rigidbody.processVelocity();
            rigidbody.processVelocity(gravity);
            rigidbody.apply();
        }
    }

    public void applyVelocity(){
        for(Entity entity : entities){
            Rigidbody rigidbody = entity.getComponent(Rigidbody.class);
            rigidbody.apply();
        }
    }
}
