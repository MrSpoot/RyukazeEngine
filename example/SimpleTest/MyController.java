package SimpleTest;

import org.joml.Math;
import org.joml.Vector3f;
import ryukazev2.core.Engine;
import ryukazev2.core.Transform;
import ryukazev2.input.InputManager;
import ryukazev2.input.InputTouch;
import ryukazev2.objects.controller.CharacterController;

import static org.joml.Math.cos;
import static org.joml.Math.sin;
import static org.lwjgl.glfw.GLFW.*;

public class MyController extends CharacterController {

    private final InputManager inputManager = Engine.getInputManager();

    public MyController() {
        Engine.getInputManager().addNewInputTouch(new InputTouch("forward", GLFW_PRESS, GLFW_KEY_W));
        Engine.getInputManager().addNewInputTouch(new InputTouch("back", GLFW_PRESS, GLFW_KEY_S));
        Engine.getInputManager().addNewInputTouch(new InputTouch("right", GLFW_PRESS, GLFW_KEY_D));
        Engine.getInputManager().addNewInputTouch(new InputTouch("left", GLFW_PRESS, GLFW_KEY_A));
        Engine.getInputManager().addNewInputTouch(new InputTouch("shift", GLFW_PRESS, GLFW_KEY_LEFT_SHIFT));
    }

    public void processInput(){

        Transform _transform = new Transform(this.transform);

        float cameraSpeed = 2.5f;
        if (inputManager.isPressed("shift") != null) {
            cameraSpeed = 10.0f;
        }
        if (inputManager.isPressed("forward") != null) {
            cameraSpeed *= inputManager.isPressed("forward").getDeltaTime();
            _transform.position.add(new Vector3f(0f, 0f, 1f).mul(cameraSpeed));
        }
        if (inputManager.isPressed("back") != null) {
            cameraSpeed *= inputManager.isPressed("back").getDeltaTime();
            _transform.position.sub(new Vector3f(0f, 0f, 1f).mul(cameraSpeed));
        }
        if (inputManager.isPressed("right") != null) {
            cameraSpeed *= inputManager.isPressed("right").getDeltaTime();
            _transform.position.add(new Vector3f(new Vector3f(0f, 0f, -1f).cross(new Vector3f(0f, 1f, 0f))).normalize().mul(cameraSpeed));
        }
        if (inputManager.isPressed("left") != null) {
            cameraSpeed *= inputManager.isPressed("left").getDeltaTime();
            _transform.position.sub(new Vector3f(new Vector3f(0f, 0f, -1f).cross(new Vector3f(0f, 1f, 0f))).normalize().mul(cameraSpeed));
        }

        this.transform = _transform;
    }

    @Override
    public void render() {
        processInput();
    }
}
