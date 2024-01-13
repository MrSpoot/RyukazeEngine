package org.spoot.ryukaze.physics.body;

import org.spoot.ryukaze.objects.GameObject;
import org.spoot.ryukaze.physics.collider.Collider;

public abstract class PhysicBody {

    public Collider collider;

    public final void _update(GameObject object){
        update(object);
    }

    public void update(GameObject object){

    }


}
