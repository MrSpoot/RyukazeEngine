package ryukaze.objects.mesh;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ryukaze.objects.material.Material;

@EqualsAndHashCode
@Data
public abstract class Mesh {

    protected Material material;

    public Mesh(){
        this.material = new Material();
    }

    public final void _render(){
        if(material != null){
            material.render();
        }
        render();
    }

    public void render(){

    }

}
