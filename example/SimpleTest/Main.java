package SimpleTest;

import org.joml.Vector3f;
import ryukazev2.core.Engine;
import ryukazev2.core.Options;
import ryukazev2.core.Scene;
import ryukazev2.graphics.Camera;
import ryukazev2.objects.GameObject;
import ryukazev2.objects.light.AmbientLight;
import ryukazev2.objects.material.Material;
import ryukazev2.objects.model.Cube;
import ryukazev2.objects.model.Sphere;

import javax.print.attribute.standard.SheetCollate;

public class Main {

    public static void main(String[] args) {

        Engine.init(new Options(true, 60, 1280,720 , 60));

        Scene scene = new Scene();
        Engine.setScene(scene);

        GameObject light = new AmbientLight();
        light.getTransform().setPosition(new Vector3f(0f,-50f,0f));

        GameObject camera = new Camera(75,0.1f,1000f);
        camera.getTransform().setPosition(new Vector3f(-2.0f,-4.0f,-15f));

        GameObject plane = new Cube();
        plane.getTransform().setPosition(new Vector3f(0,0f,0f));
        plane.getTransform().setScale(new Vector3f(50,0.5f,50f));

        GameObject sphere = new Sphere();
        sphere.getTransform().setPosition(new Vector3f(0f,-3f,0f));
        sphere.getMesh().setMaterial(new Material(new Vector3f(0.8f,0.5f,0.31f)));

        MyController controller = new MyController();
        controller.addChildren(camera);
        scene.subscribe(controller);


        Engine.run();



    }

}
