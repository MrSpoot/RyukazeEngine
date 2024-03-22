package org.spoot.ryukazev2.physic.manager;

import org.joml.Vector3f;
import org.spoot.ryukazev2.component.TransformComponent;
import org.spoot.ryukazev2.graphic.core.Entity;
import org.spoot.ryukazev2.graphic.manager.EntityManager;
import org.spoot.ryukazev2.manager.Manager;
import org.spoot.ryukazev2.physic.component.body.Rigidbody;
import org.spoot.ryukazev2.physic.component.collider.BoxCollider;
import org.spoot.ryukazev2.physic.component.collider.Collider;
import org.spoot.ryukazev2.physic.component.collider.SphereCollider;
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

        for(Entity entity : entities){
            for(Entity entity2 : entities){
                if(entity2.getId().equals(entity.getId())){
                    continue;
                }

                boolean canSkip = false;

                if(coupleEntitiesAlreadyProcess.containsKey(entity)){
                    if(coupleEntitiesAlreadyProcess.get(entity).contains(entity2)){
                        canSkip = true;
                    }
                }else{
                    coupleEntitiesAlreadyProcess.put(entity,new ArrayList<>());
                }

                if(coupleEntitiesAlreadyProcess.containsKey(entity2)){
                    if(coupleEntitiesAlreadyProcess.get(entity2).contains(entity)){
                        canSkip = true;
                    }
                }else{
                    coupleEntitiesAlreadyProcess.put(entity2,new ArrayList<>());
                }

                if(!canSkip){

                    coupleEntitiesAlreadyProcess.get(entity).add(entity2);
                    coupleEntitiesAlreadyProcess.get(entity2).add(entity);

                    Collider entityCollider = entity.getComponent(Collider.class);
                    Collider entity2Collider = entity2.getComponent(Collider.class);

                    boolean collision = false;

                    if(entityCollider instanceof BoxCollider box && entity2Collider instanceof SphereCollider sphere){
                        float closestX = Math.max(box.getMin().x, Math.min(sphere.getWorldTransform().x,box.getMax().x));
                        float closestY = Math.max(box.getMin().y, Math.min(sphere.getWorldTransform().y,box.getMax().y));
                        float closestZ = Math.max(box.getMin().z, Math.min(sphere.getWorldTransform().z,box.getMax().z));

                        Vector3f closestPoint = new Vector3f(closestX, closestY, closestZ);
                        float distance = closestPoint.sub(sphere.getWorldTransform()).length();
                        if (distance <= sphere.getRadius()) {
                            collision = true;
                        }
                    }

                    if(entityCollider instanceof SphereCollider sphere && entity2Collider instanceof BoxCollider box){
                        float closestX = Math.max(box.getMin().x, Math.min(sphere.getWorldTransform().x,box.getMax().x));
                        float closestY = Math.max(box.getMin().y, Math.min(sphere.getWorldTransform().y,box.getMax().y));
                        float closestZ = Math.max(box.getMin().z, Math.min(sphere.getWorldTransform().z,box.getMax().z));

                        Vector3f closestPoint = new Vector3f(closestX, closestY, closestZ);
                        float distance = closestPoint.sub(sphere.getWorldTransform()).length();
                        if (distance <= sphere.getRadius()) {
                            collision = true;
                        }
                    }

                    if(collision){

                        Rigidbody entityRigidbody = entity.getComponent(Rigidbody.class);

                        if(entityRigidbody != null){
                            entityRigidbody.getVelocity().mul(-0.8f);
                        }

                        Rigidbody entity2Rigidbody = entity2.getComponent(Rigidbody.class);

                        if(entity2Rigidbody != null){
                            entity2Rigidbody.getVelocity().mul(-0.8f);
                        }


                    }

                }
            }
        }
    }

}
