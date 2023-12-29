package SimpleTest;

import org.joml.Math;
import org.joml.Vector3f;
import org.joml.Vector4f;
import ryukaze.core.Engine;
import ryukaze.core.Options;
import ryukaze.core.Scene;
import ryukaze.graphics.Camera;
import ryukaze.objects.GameObject;
import ryukaze.objects.light.*;
import ryukaze.objects.material.Material;
import ryukaze.objects.material.Texture;
import ryukaze.objects.mesh.Mesh;
import ryukaze.objects.mesh.SphereMesh;
import ryukaze.objects.model.*;
import ryukaze.physics.collider.SphereCollider;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;

public class Main {

    public static void main(String[] args) {

        Engine.init(new Options(true, 0, 1280,720 , 60));

        Scene scene = new Scene();
        Engine.setScene(scene);

        DirectionalLight directionalLight = new DirectionalLight();
        directionalLight.getTransform().setRotation(new Vector3f(0f,0f,0f));
        directionalLight.setIntensity(1f);

        SpotLight spotLight = new SpotLight();
        spotLight.getTransform().setPosition(new Vector3f(0f,5f,0f));
        spotLight.setIntensity(0f);

        /*Axis axis = new Axis();
        axis.getTransform().setScale(new Vector3f(0.5f));
        axis.getTransform().setPosition(new Vector3f(0f,5f,0f));*/

        GameObject camera = new Camera(100,0.1f,1000f);

        MyController controller = new MyController();
        controller.addChildren(camera);
        controller.getTransform().setPosition(new Vector3f(0f,2f,0f));

        PointLight pointlight = new PointLight();
        pointlight.setLinear(0.09f);
        pointlight.setQuadratic(0.032f);
        pointlight.getTransform().setPosition(new Vector3f(1f,2.5f,0f));

        GameObject bottom = new Plane();
        bottom.getTransform().setPosition(new Vector3f(0f,0f,0f));
        bottom.getTransform().setScale(new Vector3f(50,1f,50));
        bottom.getMesh().getMaterial().getTextures().replace("diffuse",new Texture(new Vector4f(0.2f,0.2f,0.2f,1f)));

        int precision = 5;

        for(int i = 0; i < precision; i++){
            float t = 360f / precision;
            Plane grass = new Plane();
            grass.getMesh().getMaterial().getTextures().replace("diffuse",new Texture("src/main/resources/texture/grass.png",false));
            grass.getTransform().setRotation(new Vector3f(Math.toRadians(90f),0f,Math.toRadians(t*i)));
            grass.getTransform().setPosition(new Vector3f(0f,0.5f,0f));
        }

        Plane window = new Plane();
        window.getMesh().getMaterial().getTextures().replace("diffuse",new Texture("src/main/resources/texture/blending_transparent_window.png",false));
        window.getTransform().setRotation(new Vector3f(Math.toRadians(90f),0f,Math.toRadians(90f)));
        window.getTransform().setPosition(new Vector3f(2f,0.5f,0f));


        Cube cube = new Cube();
        cube.getTransform().setPosition(new Vector3f(5f,0.5f,0f));
        cube.getMesh().getMaterial().getTextures().replace("diffuse",new Texture("src/main/resources/texture/container2.png",false));
        cube.getMesh().getMaterial().getTextures().replace("specular",new Texture("src/main/resources/texture/container2_specular.png",false));

        CustomModel customModel = new CustomModel("src/main/resources/model/Toot_Braustein.obj");
        customModel.getTransform().setPosition(new Vector3f(-1f,0f,0f));
        customModel.getTransform().setScale(new Vector3f(0.1f));
        customModel.getMesh().getMaterial().getTextures().replace("diffuse",new Texture("src/main/resources/model/Toot_Braustein.jpg",false));

        CustomModel customModel2 = new CustomModel("src/main/resources/model/car_2.fbx");
        customModel2.getTransform().setPosition(new Vector3f(-2f,0f,0f));
        customModel2.getTransform().setScale(new Vector3f(0.1f));
        customModel2.getMesh().getMaterial().getTextures().replace("diffuse",new Texture("src/main/resources/model/carText2.png",false));

        Engine.run();

    }

}
