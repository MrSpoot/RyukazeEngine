package org.spoot.ryukazev2.physic.component.collider;

import lombok.Getter;
import org.joml.Vector3f;

public class SphereCollider extends Collider{

    @Getter
    private float radius;

    public SphereCollider() {
        this.radius = 1f;
    }

    public SphereCollider setTransform(float x, float y, float z){
        this.transform = new Vector3f(x,y,z);
        return this;
    }

    public SphereCollider setScale(float radius) {
        this.radius = radius;
        return this;
    }

    public Vector3f getMin(){
        return new Vector3f(transform.x - radius, transform.y - radius, transform.z - radius);
    }

    public Vector3f getMax(){
        return new Vector3f(transform.x + radius, transform.y + radius, transform.z + radius);
    }
}
