package ryukazev2.objects.material;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.joml.Vector3f;

@Data
public class Material {

    private Vector3f color;

    public Material() {
        this.color = new Vector3f(1.0f);
    }

    public Material(Vector3f color) {
        this.color = color;
    }
}
