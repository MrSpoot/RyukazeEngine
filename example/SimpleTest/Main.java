package SimpleTest;

import org.joml.Vector3f;
import ryukaze.core.Engine;
import ryukaze.core.Options;
import ryukaze.core.Scene;
import ryukaze.graphics.Camera;
import ryukaze.objects.GameObject;
import ryukaze.objects.light.AmbientLight;
import ryukaze.objects.light.Light;
import ryukaze.objects.material.Material;
import ryukaze.objects.mesh.Mesh;
import ryukaze.objects.mesh.SphereMesh;
import ryukaze.objects.model.*;

public class Main {

    public static void main(String[] args) {

        Engine.init(new Options(true, 0, 1280,720 , 60));

        Scene scene = new Scene();
        Engine.setScene(scene);

        Light light = new AmbientLight();
        light.getTransform().setPosition(new Vector3f(0f,50000f,0f));
        light.getTransform().setScale(new Vector3f(2f));
        Mesh sphereMesh = new SphereMesh(15);
        light.setMesh(sphereMesh);

        Axis axis = new Axis();
        axis.getTransform().setScale(new Vector3f(0.5f));
        axis.getTransform().setPosition(new Vector3f(0f,1f,0f));

        GameObject camera = new Camera(75,0.1f,1000f);
        camera.getTransform().setPosition(new Vector3f(0f,4.0f,-15f));

        MyController controller = new MyController();
        controller.addChildren(camera);
        //controller.addChildren(new Cube());
        controller.getTransform().setPosition(new Vector3f(0f,5f,0f));

        GameObject custom = new CustomModel("src/main/resources/model/bunny.obj");
        custom.getTransform().setPosition(new Vector3f(0f,3f,6f));
        //custom.getTransform().setScale(new Vector3f(1f,1f,0.1f));

        GameObject plane = new Plane();
        plane.getTransform().setPosition(new Vector3f(0f,0f,0f));
        plane.getTransform().setScale(new Vector3f(50f,1f,50f));

        GameObject sphere = new Sphere();
        sphere.getTransform().setPosition(new Vector3f(5f,3f,0f));

        GameObject cube = new Cube();
        cube.getTransform().setPosition(new Vector3f(0f,3f,0f));
        Material material = new Material();
        material.setAmbient(new Vector3f(0.7f,0.3f,0.5f));
        material.setDiffuse(new Vector3f(0.0f,0.0f,0.5f));
        cube.getMesh().setMaterial(material);

        GameObject cylinder = new Cylinder();
        cylinder.getTransform().setPosition(new Vector3f(0f,3f,3f));
        //cylinder.getTransform().setRotation(new Vector3f(45f));

        Engine.run();

    }

}
