package ryukazev2.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joml.Vector3f;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transform {

    protected Vector3f position = new Vector3f(0f);
    protected Vector3f rotation = new Vector3f(0f);
    protected Vector3f scale= new Vector3f(1f);



}
