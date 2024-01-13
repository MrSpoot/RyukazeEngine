package org.spoot.SimpleTestV2;

import org.spoot.ryukazev2.component.game.MeshComponent;
import org.spoot.ryukazev2.component.game.ShaderComponent;
import org.spoot.ryukazev2.component.game.TransformComponent;
import org.spoot.ryukazev2.component.game.shape.CubeShape;
import org.spoot.ryukazev2.core.Entity;
import org.spoot.ryukazev2.core.Input;
import org.spoot.ryukazev2.core.InputTouch;
import org.spoot.ryukazev2.core.interfaces.IScript;
import org.spoot.ryukazev2.graphics.Material;
import org.spoot.ryukazev2.graphics.Texture;

import java.sql.SQLOutput;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_T;

public class TestScript implements IScript {

    private int count = 0;

    private int t = 0;
    private Material mat;
    private boolean stop = false;

    @Override
    public void init(Entity entity) {
        mat = new Material();
        mat.getTextures().replace("diffuse", new Texture("src/main/resources/texture/container2.png",false));

        new InputTouch("stop_gen",GLFW_KEY_T);
    }

    @Override
    public void update() {

        if(Input.isPressed("stop_gen")){
            stop = !stop;
        }

        if(stop && t > 15){

            for(int x = 0; x < 100; x++){
                new Entity().linkComponent(new TransformComponent().setPosition(x,-2,count))
                        .linkComponent(new MeshComponent().setMaterial(mat).applyShape(new CubeShape()).build())
                        .linkComponent(new ShaderComponent().build());
            }

            count++;

            System.out.println("Entity count : "+count * 100);

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
