package SimpleTestV2;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import ryukazev2.Engine;
import ryukazev2.component.*;
import ryukazev2.component.shape.CubeShape;
import ryukazev2.component.shape.CustomShape;
import ryukazev2.core.Entity;
import ryukazev2.core.InputTouch;
import ryukazev2.entity.Camera;
import ryukazev2.entity.DirectionalLight;
import ryukazev2.entity.PointLight;
import ryukazev2.entity.SpotLight;
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

        new Camera().linkComponent(new ScriptComponent().linkScript(new Movement()).build());
        Entity dirLight =  new DirectionalLight()
                .linkComponent(new ShaderComponent().build())
                .linkComponent(new MeshComponent().applyShape(new CubeShape()).build())
                .linkComponent(new ScriptComponent().linkScript(new RotateScript(-5f, new Vector3f(1,0,0))).build());

        dirLight.getComponent(TransformComponent.class).setPosition(0,5,0f).setRotation(0,0,0);

        Entity spotLight =  new SpotLight()
                .linkComponent(new ShaderComponent().build())
                .linkComponent(new MeshComponent().applyShape(new CubeShape()).build());

        spotLight.getComponent(TransformComponent.class).setPosition(5,5,0f).setRotation(0,0,0);

        Entity pointLight =  new PointLight()
                .linkComponent(new ShaderComponent().build())
                .linkComponent(new MeshComponent().applyShape(new CubeShape()).build());

        pointLight.getComponent(TransformComponent.class).setPosition(-5,2,0f).setRotation(0,0,0);

        Material red = new Material();
        red.getTextures().replace("diffuse",new Texture(new Vector4f(1.0f,0,0,1.0f)));

        Material green = new Material();
        green.getTextures().replace("diffuse",new Texture(new Vector4f(0,1.0f,0,1.0f)));

        Material blue = new Material();
        blue.getTextures().replace("diffuse",new Texture(new Vector4f(0,0,1.0f,1.0f)));

        Material gray = new Material();
        gray.getTextures().replace("diffuse", new Texture(new Vector4f(0.2f,0.2f,0.2f,1.0f)));
        gray.getTextures().replace("specular ", new Texture(new Vector4f(0.2f,0.2f,0.2f,1.0f)));

        Material test = new Material();
        test.getTextures().replace("diffuse", new Texture("src/main/resources/texture/container2.png",false));

        Entity t = new Entity().linkComponent(new TransformComponent().setPosition(0,-0.5f,0).setScale(10,1,10))
                .linkComponent(new MeshComponent().setMaterial(test).applyShape(new CubeShape()).build())
                .linkComponent(new ShaderComponent().build())
                .linkComponent(new ScriptComponent().linkScript(new RotateScript(-50f, new Vector3f(0,1,0))).build());

        Entity c = new Entity().linkComponent(new TransformComponent()
                        .setPosition(0,-1,-6)
                        .setScale(0.1f,0.1f,0.1f)
                        .setRotation(0,0,0))
                .linkComponent(new MeshComponent().setMaterial(carMat).applyShape(shape).build())
                .linkComponent(new ShaderComponent().build())
                .linkComponent(new ScriptComponent().linkScript(new RotateScript(51f,new Vector3f(0,1,0))).build())
                .linkChildren(t);

        new Entity().linkComponent(new TransformComponent().setPosition(-2,0,-15))
                .linkComponent(new MeshComponent().setMaterial(test).applyShape(new CubeShape()).build())
                .linkComponent(new ShaderComponent().build());

        new Entity().linkComponent(new TransformComponent().setPosition(3,0,-15))
                .linkComponent(new MeshComponent().setMaterial(red).applyShape(new CubeShape()).build())
                .linkComponent(new ShaderComponent().build());

        new Entity().linkComponent(new TransformComponent().setPosition(2,0,-15))
                .linkComponent(new MeshComponent().setMaterial(green).applyShape(new CubeShape()).build())
                .linkComponent(new ShaderComponent().build());

        new Entity().linkComponent(new TransformComponent().setPosition(1,0,-15))
                .linkComponent(new MeshComponent().setMaterial(blue).applyShape(new CubeShape()).build())
                .linkComponent(new ShaderComponent().build());


        new Entity().linkComponent(new TransformComponent().setPosition(0,-2,0).setScale(50,1,50))
                .linkComponent(new MeshComponent().setMaterial(gray).applyShape(new CubeShape()).build())
                .linkComponent(new ShaderComponent().build());

        new UITextComponent().setText("Test de mon Ui component").setSize(20f).setFont("Retro").setPosition(new Vector2f(20,20)).build();

        engine.run();
    }

}
