package ryukaze.physics.body;

import ryukaze.objects.GameObject;
import ryukaze.physics.collider.Collider;

public abstract class PhysicBody {

    public Collider collider;

    public final void _update(GameObject object){
        update(object);
    }

    public void update(GameObject object){

    }


}
