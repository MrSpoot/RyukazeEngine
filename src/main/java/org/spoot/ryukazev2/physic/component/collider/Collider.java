package org.spoot.ryukazev2.physic.component.collider;

import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.spoot.ryukazev2.component.Component;
import org.spoot.ryukazev2.component.TransformComponent;

public abstract class Collider extends Component {


    protected Vector3f center;
    protected Vector3f scale;
    protected Quaternionf rotation;

    protected Collider() {
        this.center = new Vector3f();
        this.scale = new Vector3f();
        this.rotation = new Quaternionf();
    }

    public Collider setCenter(float x, float y, float z) {
        this.center.set(x,y,z);
        return this;
    }

    public Collider setScale(float x, float y, float z) {
        this.scale.set(x,y,z);
        return this;
    }

    public Collider setRotation(Quaternionf rotation) {
        this.rotation = rotation;
        return this;
    }

    public Vector3f getCenter(){

        TransformComponent entityTransform = this.getEntity().getComponent(TransformComponent.class);

        if(entityTransform != null){
            return new Vector3f(this.center).add(entityTransform.getPosition());
        }else {
            return this.center;
        }
    }

    public Vector3f getScale(){

        TransformComponent entityTransform = this.getEntity().getComponent(TransformComponent.class);

        if(entityTransform != null){
            return new Vector3f(this.scale).add(entityTransform.getScale());
        }else {
            return this.scale;
        }
    }

    public Quaternionf getRotation(){

        TransformComponent entityTransform = this.getEntity().getComponent(TransformComponent.class);

        if(entityTransform != null){
            return new Quaternionf(this.rotation).add(entityTransform.getRotation());
        }else {
            return this.rotation;
        }
    }
}
