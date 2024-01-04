package SimpleTestV2;

import org.joml.Vector4f;
import ryukazev2.Engine;
import ryukazev2.component.*;
import ryukazev2.component.shape.CubeShape;
import ryukazev2.component.shape.CustomShape;
import ryukazev2.core.Entity;
import ryukazev2.core.InputTouch;
import ryukazev2.graphics.Material;
import ryukazev2.graphics.Texture;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

public class Main {

    public static void main(String[] args) {

        Engine engine = new Engine().build();

        new InputTouch("exit_engine",GLFW_KEY_ESCAPE);

        CustomShape shape = new CustomShape("src/main/resources/model/car_2.fbx");
        Material carMat = new Material();
        carMat.getTextures().replace("diffuse",new Texture("src/main/resources/model/carText2.png",false));

        new Entity().linkComponent(new CameraComponent()).linkComponent(new TransformComponent());

        Material red = new Material();
        red.getTextures().replace("diffuse",new Texture(new Vector4f(1.0f,0,0,1.0f)));

        Material test = new Material();

        test.getTextures().replace("diffuse", new Texture("src/main/resources/texture/container2.png",false));

        Entity t = new Entity().linkComponent(new TransformComponent().setPosition(0,-0.5f,0).setScale(10,1,10))
                .linkComponent(new MeshComponent().setMaterial(test).applyShape(new CubeShape()).build())
                .linkComponent(new ShaderComponent().build())
                .linkComponent(new ScriptComponent().linkScript(new RotateScript(-50f)).build());

        Entity c = new Entity().linkComponent(new TransformComponent()
                        .setPosition(0,-1,-6)
                        .setScale(0.1f,0.1f,0.1f)
                        .setRotation(0,0,0))
                .linkComponent(new MeshComponent().setMaterial(carMat).applyShape(shape).build())
                .linkComponent(new ShaderComponent().build())
                .linkComponent(new ScriptComponent().linkScript(new RotateScript(51f)).build())
                .linkChildren(t);

        new Entity().linkComponent(new TransformComponent().setPosition(-2,0,-15))
                .linkComponent(new MeshComponent().setMaterial(test).applyShape(new CubeShape()).build())
                .linkComponent(new ShaderComponent().build());

        engine.run();
    }

}
