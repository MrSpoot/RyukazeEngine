package ryukazev2.objects.light;

import lombok.Getter;
import lombok.Setter;
import org.joml.Vector3f;
import ryukazev2.core.Transform;
import ryukazev2.objects.GameObject;

@Getter
public class Light extends GameObject {

    @Setter
    private Vector3f lightColor;

    public Light() {
        super(new Transform(), null, null);
        this.lightColor = new Vector3f(1.0f);
    }

    public Light(GameObject parent) {
        super(new Transform(), null, parent);
        this.lightColor = new Vector3f(1.0f);
    }
}
