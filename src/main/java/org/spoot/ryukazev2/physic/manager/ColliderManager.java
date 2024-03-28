package org.spoot.ryukazev2.physic.manager;

import org.joml.Vector3f;
import org.spoot.ryukazev2.component.TransformComponent;
import org.spoot.ryukazev2.graphic.core.Entity;
import org.spoot.ryukazev2.graphic.manager.EntityManager;
import org.spoot.ryukazev2.manager.Manager;
import org.spoot.ryukazev2.physic.component.body.Rigidbody;
import org.spoot.ryukazev2.physic.component.collider.AABBCollider;
import org.spoot.ryukazev2.physic.component.collider.Collider;
import org.spoot.ryukazev2.physic.component.collider.SphereCollider;
import org.spoot.ryukazev2.physic.core.CollisionDetector;
import org.spoot.ryukazev2.physic.core.CollisionResolver;
import org.spoot.ryukazev2.utils.ServiceLocator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ColliderManager extends Manager {

    private List<Entity> entities;

    public ColliderManager() {
        ServiceLocator.registerService(ColliderManager.class, this);
    }

    public void processCollision(){

        entities = ((EntityManager) this.services.get(EntityManager.class)).getEntityByComponent(TransformComponent.class, Collider.class);

        HashMap<Entity, List<Entity>> coupleEntitiesAlreadyProcess = new HashMap<>();

        for(Entity entity1 : entities){
            for(Entity entity2 : entities){
                if(entity2.getId().equals(entity1.getId())){
                    continue;
                }

                boolean canSkip = false;

                if(coupleEntitiesAlreadyProcess.containsKey(entity1)){
                    if(coupleEntitiesAlreadyProcess.get(entity1).contains(entity2)){
                        canSkip = true;
                    }
                }else{
                    coupleEntitiesAlreadyProcess.put(entity1,new ArrayList<>());
                }

                if(coupleEntitiesAlreadyProcess.containsKey(entity2)){
                    if(coupleEntitiesAlreadyProcess.get(entity2).contains(entity1)){
                        canSkip = true;
                    }
                }else{
                    coupleEntitiesAlreadyProcess.put(entity2,new ArrayList<>());
                }

                if(!canSkip){

                    coupleEntitiesAlreadyProcess.get(entity1).add(entity2);
                    coupleEntitiesAlreadyProcess.get(entity2).add(entity1);

                    Collider collider1 = entity1.getComponent(Collider.class);
                    Collider collider2 = entity2.getComponent(Collider.class);

                    if(CollisionDetector.checkCollision(collider1,collider2)){

                        Rigidbody entity1Rigidbody = entity1.getComponent(Rigidbody.class);

                        if(entity1Rigidbody != null){
                            CollisionResolver.resolveCollision(entity1Rigidbody);
                        }

                        Rigidbody entity2Rigidbody = entity2.getComponent(Rigidbody.class);

                        if(entity2Rigidbody != null){
                            CollisionResolver.resolveCollision(entity2Rigidbody);
                        }


                    }

                }
            }
        }
    }

}
