package org.spoot.ryukazev2.physic.component.body;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.joml.Vector3f;
import org.spoot.ryukazev2.component.Component;

@EqualsAndHashCode(callSuper = true)
@Data
public class Rigidbody extends Component {

    private Vector3f velocity;
    private float mass;

    public Rigidbody() {
        this.velocity = new Vector3f(0f);
        this.mass = 0f;
    }
}
