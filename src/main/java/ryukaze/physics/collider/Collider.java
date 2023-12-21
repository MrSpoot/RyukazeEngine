package ryukaze.physics.collider;

import lombok.Data;
import ryukaze.core.Engine;
import ryukaze.core.Transform;
import ryukaze.objects.GameObject;
import ryukaze.utils.UniqueIdGenerator;

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
