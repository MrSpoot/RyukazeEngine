package org.spoot.ryukazev2.physic.component.body;

import lombok.Getter;
import org.joml.Vector3f;
import org.spoot.ryukazev2.component.Component;
import org.spoot.ryukazev2.component.TransformComponent;
import org.spoot.ryukazev2.core.Time;

public class Rigidbody extends Component {

    private final float DEFAULT_MASS = 0.0000000001f;

    @Getter
    private Vector3f velocity;
    @Getter
    private Vector3f force;
    private float mass;

    public Rigidbody() {
        this.velocity = new Vector3f();
        this.force = new Vector3f();
        this.mass = 1f;
    }

    public Rigidbody setVelocity(Vector3f velocity){
        this.velocity = velocity;
        return this;
    }

    public Rigidbody setForce(Vector3f force){
        this.force = force;
        return this;
    }

    public Rigidbody setMass(float mass){
        this.mass = mass;
        return this;
    }

    public float getMass(){
        return mass > 0 ? mass : DEFAULT_MASS;
    }

    public Vector3f processVelocity(Vector3f... forces){
        for(Vector3f f : forces){
            this.force.add(f);
        }
        this.velocity.add((new Vector3f(force.div(this.mass > 0 ? this.mass : DEFAULT_MASS)).mul(Time.deltaTime)));
        this.force.set(0,0,0);
        return this.velocity;
    }

    public void apply(){
        TransformComponent transform = this.getEntity().getComponent(TransformComponent.class);
        if(transform != null){
            transform.getPosition().add(new Vector3f(velocity).mul(Time.deltaTime));
        }
    }
}
