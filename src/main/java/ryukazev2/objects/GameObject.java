package ryukazev2.objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.joml.Matrix4f;
import ryukazev2.core.Engine;
import ryukazev2.core.Transform;
import ryukazev2.objects.mesh.Mesh;

import java.util.HashMap;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public abstract class GameObject extends Transform {

    private HashMap<String,GameObject> children;
    private Mesh mesh;

    public void render() {
        Engine.getScene().getShader().setUniform("model",new Matrix4f().translate(this.position));
        mesh.render();
        children.values().forEach(GameObject::render);
    }

    public void update() {
        children.values().forEach(GameObject::update);
    }
}
