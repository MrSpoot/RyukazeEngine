package org.spoot.ryukazev2.component.game.physics;

import org.spoot.ryukazev2.component.game.Component;
import org.spoot.ryukazev2.component.game.TransformComponent;
import org.spoot.ryukazev2.component.game.physics.collider.Collider;
import org.spoot.ryukazev2.component.game.physics.collider.SphereCollider;

public class ColliderComponent extends Component {
    private Collider collider;
    private TransformComponent transform;

    /*private boolean detectCollision(Collider collider){
        if(this.collider instanceof SphereCollider sphere1 && collider instanceof SphereCollider sphere2){
            float distance = sphere1.position.distance(sphere2.position);
            return distance < (sphere1.getRadius() + sphere2.getRadius());
        }

        return false;
    }*/

}
