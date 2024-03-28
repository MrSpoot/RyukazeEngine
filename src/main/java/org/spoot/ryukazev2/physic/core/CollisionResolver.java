package org.spoot.ryukazev2.physic.core;

import org.spoot.ryukazev2.graphic.core.Entity;
import org.spoot.ryukazev2.physic.component.body.Rigidbody;

public class CollisionResolver {

    private CollisionResolver(){}

    public static void resolveCollision(Rigidbody rigidbody){
        rigidbody.getVelocity().mul(-0.9f);
    }

    public static void resolveCollision(Entity entity1, Entity entity2){

    }
}
