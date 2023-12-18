package SimpleTest;

import org.joml.Vector3f;
import ryukazev2.core.Engine;
import ryukazev2.core.Options;
import ryukazev2.core.Scene;
import ryukazev2.graphics.Camera;
import ryukazev2.objects.GameObject;
import ryukazev2.objects.light.AmbientLight;
import ryukazev2.objects.light.Light;
import ryukazev2.objects.material.Material;
import ryukazev2.objects.mesh.Mesh;
import ryukazev2.objects.mesh.SphereMesh;
import ryukazev2.objects.model.*;
import ryukazev2.physics.body.PhysicBody3D;

public class Main {

    public static void main(String[] args) {

        Engine.init(new Options(true, 0, 1280,720 , 60));

        Scene scene = new Scene();
        Engine.setScene(scene);

        Light light = new AmbientLight();
        light.getTransform().setPosition(new Vector3f(0f,6f,10f));
        light.getTransform().setScale(new Vector3f(2f));
        Mesh sphereMesh = new SphereMesh(15);
        light.setMesh(sphereMesh);

        GameObject camera = new Camera(75,0.1f,1000f);
        //camera.getTransform().setPosition(new Vector3f(0f,4.0f,-15f));

        MyController controller = new MyController();
        controller.addChildren(camera);

        GameObject custom = new CustomModel("src/main/resources/model/bunny.obj");
        //custom.getTransform().setPosition(new Vector3f(0f,3f,6f));
        //custom.getTransform().setScale(new Vector3f(1f));

        GameObject plane = new Plane();
        plane.getTransform().setPosition(new Vector3f(0f,-5,0f));
        plane.getTransform().setScale(new Vector3f(50f,0.5f,50f));

        GameObject sphere = new Sphere();
        sphere.getTransform().setPosition(new Vector3f(5f,3f,0f));
        sphere.getTransform().setRotation(new Vector3f(0f,(float)Math.toRadians(180),0f));

        GameObject cube = new Cube();
        cube.getTransform().setPosition(new Vector3f(0f,3f,0f));
        Material material = new Material();
        material.setAmbient(new Vector3f(0.7f,0.3f,0.5f));
        cube.getMesh().setMaterial(material);

        GameObject cylinder = new Cylinder();
        cylinder.getTransform().setPosition(new Vector3f(0f,3f,3f));
        //cylinder.getTransform().setRotation(new Vector3f(45f));

        Engine.run();

    }

}
