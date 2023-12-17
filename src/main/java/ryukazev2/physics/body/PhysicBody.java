package ryukazev2.physics.body;

import ryukazev2.objects.GameObject;
import ryukazev2.physics.collider.Collider;

import java.util.List;

public abstract class PhysicBody {

    public Collider collider;

    public final void _update(GameObject object){
        update(object);
    }

    public void update(GameObject object){

    }


}
