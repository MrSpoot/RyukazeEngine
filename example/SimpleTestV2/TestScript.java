package SimpleTestV2;

import org.joml.Vector3f;
import org.spoot.ryukazev2.graphic.component.game.MeshComponent;
import org.spoot.ryukazev2.graphic.component.game.ShaderComponent;
import org.spoot.ryukazev2.component.TransformComponent;
import org.spoot.ryukazev2.graphic.component.game.shape.CubeShape;
import org.spoot.ryukazev2.graphic.core.Entity;
import org.spoot.ryukazev2.core.interfaces.IScript;
import org.spoot.ryukazev2.graphic.graphics.Material;
import org.spoot.ryukazev2.graphic.graphics.Texture;

public class TestScript implements IScript {

    private Entity entity;

    private boolean direction = false;

    @Override
    public void init(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void update() {
        if(direction){
            this.entity.getComponent(TransformComponent.class).translate(new Vector3f(0.05f,0,0));
        }else{
            this.entity.getComponent(TransformComponent.class).translate(new Vector3f(-0.05f,0,0));
        }

        if(this.entity.getComponent(TransformComponent.class).getPosition().x > 10f){
            direction = false;
        }else if(this.entity.getComponent(TransformComponent.class).getPosition().x < -10f){
            direction = true;
        }
    }

    @Override
    public void render() {

    }

    @Override
    public void cleanup() {

    }
}
