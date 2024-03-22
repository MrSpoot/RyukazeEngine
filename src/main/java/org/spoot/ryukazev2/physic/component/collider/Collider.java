package org.spoot.ryukazev2.physic.component.collider;

import org.joml.Vector3f;
import org.spoot.ryukazev2.component.Component;
import org.spoot.ryukazev2.component.TransformComponent;

public abstract class Collider extends Component {


    protected Vector3f transform;

    public Collider() {
        this.transform = new Vector3f();
    }

    public Vector3f getWorldTransform(){

        TransformComponent entityTransform = this.getEntity().getComponent(TransformComponent.class);

        if(entityTransform != null){
            return new Vector3f(this.transform).add(entityTransform.getPosition());
        }else {
            return this.transform;
        }
    }
}
