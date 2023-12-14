package ryukazev2.objects.mesh;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.joml.Vector3f;
import ryukazev2.core.Engine;
import ryukazev2.objects.material.Material;

@EqualsAndHashCode
@Data
public abstract class Mesh {

    protected Material material;

    public Mesh(){
        this.material = new Material();
    }

    public final void _render(){
        Engine.getScene().getShader().setUniform("objectColor",this.material.getColor());
        render();
    }

    public void render(){

    }

}
