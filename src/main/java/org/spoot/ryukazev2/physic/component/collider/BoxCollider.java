package org.spoot.ryukazev2.physic.component.collider;

import org.joml.Vector3f;

public class BoxCollider extends Collider{

    private Vector3f scale;

    public BoxCollider() {
        super();
        this.scale = new Vector3f(1f);
    }

    public BoxCollider setTransform(float x, float y, float z){
        this.transform = new Vector3f(x,y,z);
        return this;
    }

    public BoxCollider setScale(float x, float y, float z){
        this.scale = new Vector3f(x,y,z);
        return this;
    }

    public Vector3f getMin(){
        return new Vector3f(transform.x - scale.x / 2, transform.y - scale.y / 2, transform.z - scale.z / 2);
    }

    public Vector3f getMax(){
        return new Vector3f(transform.x + scale.x / 2, transform.y + scale.y / 2, transform.z + scale.z / 2);
    }

}
