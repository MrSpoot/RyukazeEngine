package org.spoot.ryukaze.physics.collider;

import lombok.Data;
import org.spoot.ryukaze.core.Engine;
import org.spoot.ryukaze.core.Transform;
import org.spoot.ryukaze.utils.UniqueIdGenerator;
import org.spoot.ryukaze.objects.GameObject;

@Data
public abstract class Collider {

    private String id;
    private Transform transform;
    private GameObject parent;

    public Collider(GameObject parent){
        this.id = UniqueIdGenerator.generateUniqueID(15);
        this.transform = new Transform();
        this.parent = parent;
        Engine.getPhysicsEngine().subscribe(this);
    }

    public abstract boolean collidesWith(Collider collider);

    public final Transform getGlobalTransform(){
        if(parent == null){
            return this.transform;
        }else{
            return this.transform.combine(parent.getGlobalTransform());
        }
    }
}
