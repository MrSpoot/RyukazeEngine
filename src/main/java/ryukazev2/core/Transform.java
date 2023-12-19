package ryukazev2.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joml.Vector3f;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transform {

    public Vector3f position = new Vector3f(0f);
    public Vector3f rotation = new Vector3f(0f);
    public Vector3f scale = new Vector3f(1f);

    public Transform(Transform transform){
        this.position = transform.position;
        this.rotation = transform.rotation;
        this.scale = transform.scale;
    }

    public Transform combine(Transform otherTransform){
        Transform transform = new Transform(new Vector3f(position),new Vector3f(rotation),new Vector3f(scale));

        transform.setPosition(transform.getPosition().add(otherTransform.getPosition()));
        transform.setRotation(transform.getRotation().add(otherTransform.getRotation()));
        transform.setScale(transform.getScale().add(otherTransform.getScale()));

        return transform;
    }

}
