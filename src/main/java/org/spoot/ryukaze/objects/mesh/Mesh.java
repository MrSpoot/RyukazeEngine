package org.spoot.ryukaze.objects.mesh;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.spoot.ryukaze.core.Engine;
import org.spoot.ryukaze.objects.material.Material;

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
        Engine.getScene().getShader().useProgram();
        render();
    }

    public void render(){

    }

}
