package org.spoot.ryukaze.physics.collider;

import lombok.Getter;
import org.joml.Vector3f;
import org.spoot.ryukaze.objects.GameObject;

@Getter
public class SphereCollider extends Collider{

    public SphereCollider(GameObject parent) {
        super(parent);
    }

    @Override
    public boolean collidesWith(Collider collider){
        Vector3f distance = this.getGlobalTransform().position.sub(collider.getGlobalTransform().position);
        Vector3f radiiSum = this.getGlobalTransform().scale.div(2).add(collider.getGlobalTransform().scale.div(2));

        return (distance.x * distance.x) / (radiiSum.x * radiiSum.x) +
                (distance.y * distance.y) / (radiiSum.y * radiiSum.y) +
                (distance.z * distance.z) / (radiiSum.z * radiiSum.z) <= 1;
    }

}
