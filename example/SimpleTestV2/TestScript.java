package SimpleTestV2;

import org.spoot.ryukazev2.component.game.MeshComponent;
import org.spoot.ryukazev2.component.game.ShaderComponent;
import org.spoot.ryukazev2.component.game.TransformComponent;
import org.spoot.ryukazev2.component.game.shape.CubeShape;
import org.spoot.ryukazev2.core.Entity;
import org.spoot.ryukazev2.core.interfaces.IScript;
import org.spoot.ryukazev2.graphics.Material;
import org.spoot.ryukazev2.graphics.Texture;

public class TestScript implements IScript {

    private int count = 0;

    private int t = 0;
    private Material mat;

    @Override
    public void init(Entity entity) {
        mat = new Material();
        mat.getTextures().replace("diffuse", new Texture("src/main/resources/texture/container2.png",false));
    }

    @Override
    public void update() {
        if(t > 50){

            for(int x = 0; x < 25; x++){
                new Entity().linkComponent(new TransformComponent().setPosition(x,-2,count))
                        .linkComponent(new MeshComponent().setMaterial(mat).applyShape(new CubeShape()).build())
                        .linkComponent(new ShaderComponent().build());
            }
            count++;
            t = 0;
        }else{
            t++;
        }



    }

    @Override
    public void render() {

    }

    @Override
    public void cleanup() {

    }
}
