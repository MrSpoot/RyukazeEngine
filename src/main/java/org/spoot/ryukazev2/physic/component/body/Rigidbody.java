package org.spoot.ryukazev2.physic.component.body;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.joml.Vector3f;
import org.spoot.ryukazev2.component.Component;

@EqualsAndHashCode(callSuper = true)
@Getter
public class Rigidbody extends Component {

    private Vector3f velocity;
    private Vector3f force;
    private float mass;

    public Rigidbody() {
        this.velocity = new Vector3f();
        this.force = new Vector3f();
        this.mass = 0f;
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
}
