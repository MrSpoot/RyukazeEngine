package SimpleTestV2;

import org.joml.Vector4f;
import ryukazev2.Engine;
import ryukazev2.component.*;
import ryukazev2.component.shape.CubeShape;
import ryukazev2.component.shape.CustomShape;
import ryukazev2.core.Entity;
import ryukazev2.graphics.Material;
import ryukazev2.graphics.Texture;

public class Main {

    public static void main(String[] args) {

        Engine engine = new Engine().build();

        CustomShape shape = new CustomShape("src/main/resources/model/car_2.fbx");
        Material carMat = new Material();
        carMat.getTextures().replace("diffuse",new Texture("src/main/resources/model/carText2.png",false));

        new Entity().linkComponent(new CameraComponent()).linkComponent(new TransformComponent());

        Material red = new Material();
        red.getTextures().replace("diffuse",new Texture(new Vector4f(1.0f,0,0,1.0f)));

        Material test = new Material();

        test.getTextures().replace("diffuse", new Texture("src/main/resources/texture/container2.png",false));

        new Entity().linkComponent(new TransformComponent().setPosition(0,0,-3).setScale(0.1f,0.1f,0.1f))
                .linkComponent(new MeshComponent().setMaterial(carMat).applyShape(shape).build())
                .linkComponent(new ShaderComponent().build());

        new Entity().linkComponent(new TransformComponent().setPosition(2,0,-15).setScale(1,5,1))
                .linkComponent(new MeshComponent().setMaterial(test).applyShape(new CubeShape()).build())
                .linkComponent(new ShaderComponent().build());

        new Entity().linkComponent(new TransformComponent().setPosition(-2,0,-15).setScale(1,5,1))
                .linkComponent(new MeshComponent().setMaterial(test).applyShape(new CubeShape()).build())
                .linkComponent(new ShaderComponent().build());

        engine.run();
    }

}
