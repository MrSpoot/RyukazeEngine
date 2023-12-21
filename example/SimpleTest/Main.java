package SimpleTest;

import org.joml.Vector3f;
import ryukaze.core.Engine;
import ryukaze.core.Options;
import ryukaze.core.Scene;
import ryukaze.graphics.Camera;
import ryukaze.objects.GameObject;
import ryukaze.objects.light.AmbientLight;
import ryukaze.objects.light.DirectionalLight;
import ryukaze.objects.light.Light;
import ryukaze.objects.material.Material;
import ryukaze.objects.mesh.Mesh;
import ryukaze.objects.mesh.SphereMesh;
import ryukaze.objects.model.*;
import ryukaze.physics.collider.SphereCollider;

public class Main {

    public static void main(String[] args) {

        Engine.init(new Options(true, 0, 1280,720 , 60));

        Scene scene = new Scene();
        Engine.setScene(scene);

        /*Light light = new AmbientLight();
        light.getTransform().setPosition(new Vector3f(0f,50000f,0f));
        light.getTransform().setScale(new Vector3f(2f));
        Mesh sphereMesh = new SphereMesh(15);
        light.setMesh(sphereMesh);*/

        Light light = new DirectionalLight();
        light.getTransform().setRotation(new Vector3f(0f,-1f,0f));

        Axis axis = new Axis();
        axis.getTransform().setScale(new Vector3f(0.5f));
        axis.getTransform().setPosition(new Vector3f(0f,1f,0f));

        GameObject camera = new Camera(75,0.1f,1000f);
        camera.getTransform().setPosition(new Vector3f(0f,4.0f,-15f));

        MyController controller = new MyController();
        controller.addChildren(camera);
        controller.getTransform().setPosition(new Vector3f(0f,5f,0f));

        GameObject plane = new Plane();
        plane.getTransform().setPosition(new Vector3f(0f,0f,0f));
        plane.getTransform().setScale(new Vector3f(50f,1f,50f));

        GameObject sphere1 = new Sphere();
        sphere1.getTransform().setPosition(new Vector3f(0.1f,2f,0f));
        //sphere1.setCollider(new SphereCollider(sphere1));

        GameObject sphere2 = new Sphere();
        sphere2.getTransform().setPosition(new Vector3f(-1f,2f,0f));
        //sphere2.setCollider(new SphereCollider(sphere2));

        Engine.run();

    }

}
