package ryukaze.objects.light;

import lombok.Getter;
import lombok.Setter;
import org.joml.Vector3f;
import ryukaze.core.Transform;
import ryukaze.objects.GameObject;

@Getter
public class Light extends GameObject {

    @Setter
    private Vector3f ambient;
    @Setter
    private Vector3f specular;
    @Setter
    private Vector3f diffuse;

    public Light() {
        this(null);
    }

    public Light(GameObject parent) {
        super(new Transform(), null,null, parent);
        this.ambient = new Vector3f(0.5f);
        this.specular = new Vector3f(1.0f);
        this.diffuse = new Vector3f(1.0f);
    }

}
