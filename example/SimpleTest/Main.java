package SimpleTest;

import org.joml.Vector3f;
import ryukazev2.core.Engine;
import ryukazev2.core.Scene;
import ryukazev2.graphics.Camera;
import ryukazev2.objects.model.Cube;
import ryukazev2.objects.model.Sphere;

import javax.print.attribute.standard.SheetCollate;

public class Main {

    public static void main(String[] args) {

        Engine.init();

        Scene scene = new Scene();
        Engine.setScene(scene);

        Sphere sphere = new Sphere();
        sphere.getTransform().setPosition(new Vector3f(0f,-2f,0f));
        scene.subscribe(sphere);
        Cube cube = new Cube();
        scene.subscribe(cube);

        Camera camera = new Camera(75,0.1f,100f);

        MyController controller = new MyController();
        controller.addChildren(camera);
        scene.subscribe(controller);


        Engine.run();



    }

}
