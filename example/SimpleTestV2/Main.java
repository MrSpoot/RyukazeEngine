package SimpleTestV2;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.spoot.ryukazev2.Engine;
import org.spoot.ryukazev2.graphic.GraphicsEngine;
import org.spoot.ryukazev2.graphic.component.game.MeshComponent;
import org.spoot.ryukazev2.component.ScriptComponent;
import org.spoot.ryukazev2.graphic.component.game.ShaderComponent;
import org.spoot.ryukazev2.component.TransformComponent;
import org.spoot.ryukazev2.graphic.component.game.shape.CubeShape;
import org.spoot.ryukazev2.graphic.component.game.shape.CustomShape;
import org.spoot.ryukazev2.graphic.component.ui.UICircleComponent;
import org.spoot.ryukazev2.graphic.component.ui.UIScriptComponent;
import org.spoot.ryukazev2.graphic.component.ui.UITextComponent;
import org.spoot.ryukazev2.graphic.core.Entity;
import org.spoot.ryukazev2.graphic.core.InputTouch;
import org.spoot.ryukazev2.graphic.core.UIEntity;
import org.spoot.ryukazev2.graphic.core.enumerations.Anchor;
import org.spoot.ryukazev2.graphic.entity.Camera;
import org.spoot.ryukazev2.graphic.entity.DirectionalLight;
import org.spoot.ryukazev2.graphic.entity.PointLight;
import org.spoot.ryukazev2.graphic.entity.SpotLight;
import org.spoot.ryukazev2.graphic.graphics.Material;
import org.spoot.ryukazev2.graphic.graphics.Texture;
import org.spoot.ryukazev2.graphic.graphics.Image;
import org.spoot.ryukazev2.physic.component.body.Rigidbody;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

public class Main {

    public static void main(String[] args) {
        Engine engine = new Engine().build();

        new InputTouch("exit_engine",GLFW_KEY_ESCAPE);

        new Camera().linkComponent(new ScriptComponent().linkScript(new Movement()).build());

        //new Entity().linkComponent(new ScriptComponent().linkScript(new TestScript()).build());

        new Entity().linkComponent(new Rigidbody()).linkComponent(new MeshComponent().applyShape(new CubeShape()).build()).linkComponent(new ShaderComponent().build()).linkComponent(new TransformComponent());
        //new Entity().linkComponent(new MeshComponent().applyShape(new CubeShape()).build()).linkComponent(new ShaderComponent().build()).linkComponent(new TransformComponent());

        int width = 20;
        int length = 20;

        Material mat = new Material();
        mat.setDiffuse(new Texture("src/main/resources/texture/container2.png",false));


        for(int x = -(width/2); x < (width/2); x++){
            for(int z = -(length/2); z < (length/2); z++){
                new Entity().linkComponent(new TransformComponent().setPosition(x,-5,z))
                        .linkComponent(new MeshComponent().setMaterial(mat).applyShape(new CubeShape()).build())
                        .linkComponent(new ShaderComponent().build());
            }
        }

        new DirectionalLight();

        /*int width = 10;
        int length = 10;

        Material mat = new Material();
        mat.getTextures().replace("diffuse", new Texture("src/main/resources/texture/container2.png",false));


        for(int x = -(width/2); x < (width/2); x++){
            for(int z = -(length/2); z < (length/2); z++){
                new Entity().linkComponent(new TransformComponent().setPosition(x,-2,z))
                        .linkComponent(new MeshComponent().setMaterial(mat).applyShape(new CubeShape()).build())
                        .linkComponent(new ShaderComponent().build());
            }
        }*/

        CustomShape shape = new CustomShape("src/main/resources/model/car_2.fbx");
        Material carMat = new Material();
        carMat.setDiffuse(new Texture("src/main/resources/model/carText2.png",false));

        new Camera().linkComponent(new ScriptComponent().linkScript(new Movement()).build());
        Entity dirLight =  new DirectionalLight()
                .linkComponent(new ShaderComponent().build())
                .linkComponent(new MeshComponent().applyShape(new CubeShape()).build());
        dirLight.getComponent(TransformComponent.class).setPosition(0,5,0f);

        Entity spotLight =  new SpotLight()
                .linkComponent(new ShaderComponent().build())
                .linkComponent(new MeshComponent().applyShape(new CubeShape()).build());
        spotLight.getComponent(TransformComponent.class).setPosition(5,2,0f);

        Entity pointLight =  new PointLight()
                .linkComponent(new ShaderComponent().build())
                .linkComponent(new MeshComponent().applyShape(new CubeShape()).build());

        pointLight.getComponent(TransformComponent.class).setPosition(-5,2,0f);

        Material red = new Material();
        red.setDiffuse(new Texture(new Vector4f(1.0f,0,0,1.0f)));

        Material green = new Material();
        green.setDiffuse(new Texture(new Vector4f(0,1.0f,0,1.0f)));

        Material blue = new Material();
        blue.setDiffuse(new Texture(new Vector4f(0,0,1.0f,1.0f)));

        Material gray = new Material();
        gray.setDiffuse(new Texture(new Vector4f(0.2f,0.2f,0.2f,1.0f)));
        gray.setDiffuse( new Texture(new Vector4f(0.2f,0.2f,0.2f,1.0f)));

        Material test = new Material();
        test.setDiffuse( new Texture("src/main/resources/texture/container2.png",false));

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

        new UIEntity()
                .linkComponent(new UITextComponent("fps").setSize(10f).setFont("Retro").setPosition(new Vector2f(5,10)).build())
                .linkComponent(new UITextComponent("frame").setSize(10f).setFont("Retro").setPosition(new Vector2f(500,10)).build())
                .linkComponent(new UITextComponent("ups").setSize(10f).setFont("Retro").setPosition(new Vector2f(5,20)).build())
                .linkComponent(new UITextComponent("position").setSize(10f).setFont("Retro").setPosition(new Vector2f(5,30)).build())
                .linkComponent(new UICircleComponent("center-pointer")
                        .setPosition(new Vector2f(640,360))
                        .setAnchor(Anchor.CENTER)
                        .setscale(new Vector2f(0.02f,0.02f))
                        .setImage(new Image(new Vector4f(0f,0f,0f,1f)))
                        .build())

                .linkComponent(new UIScriptComponent().linkScript(new UIScript()).build());

        engine.run();
    }

}
