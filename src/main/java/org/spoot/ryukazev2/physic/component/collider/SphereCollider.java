package org.spoot.ryukazev2.physic.component.collider;

import org.joml.Vector3f;

public class SphereCollider extends Collider{

    public float getRadius(){
        return getScale().x / 2;
    }

    public Vector3f getMin(){
        return new Vector3f(getCenter().x - getScale().x, getCenter().y - getScale().y, getCenter().z - getScale().z);
    }

    public Vector3f getMax(){
        return new Vector3f(getCenter().x + getScale().x, getCenter().y + getScale().y, getCenter().z + getScale().z);
    }
}
