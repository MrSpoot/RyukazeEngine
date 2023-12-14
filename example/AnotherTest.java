import org.joml.Vector3f;
import ryukazev2.core.Engine;
import ryukazev2.core.Scene;
import ryukazev2.graphics.Camera;
import ryukazev2.input.InputTouch;
import ryukazev2.objects.GameObject;
import ryukazev2.objects.light.AmbientLight;
import ryukazev2.objects.model.Cube;
import ryukazev2.objects.model.Sphere;

import static org.lwjgl.glfw.GLFW.*;

public class AnotherTest {

    public static void main(String[] args) {

        Engine.init();

        Scene scene = new Scene();

        Engine.setScene(scene);

        GameObject light = new AmbientLight();
        light.getTransform().setPosition(new Vector3f(-2f,-3f,0f));

        GameObject camera = new Camera(75,0.1f,100f);
        camera.getTransform().setPosition(new Vector3f(-2.0f,-4.0f,-15f));

        GameObject sphere = new Sphere();
        sphere.getTransform().setPosition(new Vector3f(0f,-2f,0f));

        GameObject sphere1 = new Sphere(sphere);
        sphere1.getTransform().setPosition(new Vector3f(1f,-3f,0f));

        GameObject cube1 = new Cube();
        cube1.getTransform().setPosition(new Vector3f(0f,-4f,0f));

        GameObject cube2 = new Cube();
        cube2.getTransform().setPosition(new Vector3f(1f,-5f,0f));

        Engine.run();

    }
}
