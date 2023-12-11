import org.joml.Vector3f;
import ryukazev2.core.Engine;
import ryukazev2.core.Scene;
import ryukazev2.graphics.Camera;
import ryukazev2.input.InputTouch;
import ryukazev2.objects.GameObject;
import ryukazev2.objects.model.Cube;
import static org.lwjgl.glfw.GLFW.*;

public class AnotherTest {

    public static void main(String[] args) {

        Engine.init();

        Scene scene = new Scene();

        Engine.setScene(scene);

        InputTouch esc = new InputTouch("esc",GLFW_PRESS,GLFW_KEY_ESCAPE);

        Engine.getInputManager().addNewInputTouch(esc);

        GameObject camera = new Camera(75,0.1f,100f);
        camera.setPosition(new Vector3f(0.0f,-4.0f,-30f));
        scene.subscribe(camera);

        GameObject cube1 = new Cube();
        cube1.setPosition(new Vector3f(0f,0f,-5f));
        scene.subscribe(cube1);

        GameObject cube7 = new Cube();
        cube7.setPosition(new Vector3f(5f,-1f,5f));
        scene.subscribe(cube7);

        GameObject cube8 = new Cube();
        cube8.setPosition(new Vector3f(-5f,-1f,5f));
        scene.subscribe(cube8);

        GameObject cube2 = new Cube();
        cube2.setPosition(new Vector3f(0f,0f,5f));
        scene.subscribe(cube2);

        GameObject cube3 = new Cube();
        cube3.setPosition(new Vector3f(5f,0f,0f));
        scene.subscribe(cube3);

        GameObject cube4 = new Cube();
        cube4.setPosition(new Vector3f(-5f,0f,0f));
        scene.subscribe(cube4);
        Engine.run();

    }
}
