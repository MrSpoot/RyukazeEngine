package org.spoot.ryukazev2.physic.component.collider;

import org.joml.Vector3f;
import org.spoot.ryukazev2.component.TransformComponent;

public class AABBCollider extends Collider{

    public Vector3f getMin(){
        return new Vector3f(getCenter().x - getScale().x / 2, getCenter().y - getScale().y / 2, getCenter().z - getScale().z / 2);
    }

    public Vector3f getMax(){
        return new Vector3f(getCenter().x + getScale().x / 2, getCenter().y + getScale().y / 2, getCenter().z + getScale().z / 2);
    }

}
