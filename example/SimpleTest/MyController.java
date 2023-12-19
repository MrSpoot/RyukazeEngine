package SimpleTest;

import org.joml.Math;
import org.joml.Vector3f;
import ryukaze.core.Engine;
import ryukaze.input.InputManager;
import ryukaze.input.InputTouch;
import ryukaze.objects.controller.CharacterController;

import static org.joml.Math.cos;
import static org.joml.Math.sin;
import static org.lwjgl.glfw.GLFW.*;

public class MyController extends CharacterController {

    private final InputManager inputManager = Engine.getInputManager();
    private float lastFrame = 0.0f;
    private float lastX = (float) Engine.getWindow().getWidth() /2;
    private float lastY = (float) Engine.getWindow().getHeight() /2;
    private float yaw = 0f;
    private float pitch = 0f;

    public MyController() {
        Engine.getInputManager().addNewInputTouch(new InputTouch("forward", GLFW_PRESS, GLFW_KEY_W));
        Engine.getInputManager().addNewInputTouch(new InputTouch("back", GLFW_PRESS, GLFW_KEY_S));
        Engine.getInputManager().addNewInputTouch(new InputTouch("right", GLFW_PRESS, GLFW_KEY_D));
        Engine.getInputManager().addNewInputTouch(new InputTouch("left", GLFW_PRESS, GLFW_KEY_A));
        Engine.getInputManager().addNewInputTouch(new InputTouch("shift", GLFW_PRESS, GLFW_KEY_LEFT_SHIFT));
        Engine.getInputManager().addNewInputTouch(new InputTouch("jump", GLFW_PRESS, GLFW_KEY_SPACE));
        Engine.getInputManager().addNewInputTouch(new InputTouch("down", GLFW_PRESS, GLFW_KEY_LEFT_CONTROL));
    }

    public void processInput(){

        float currentFrame = (float)glfwGetTime();
        float deltaTime = currentFrame - lastFrame;
        lastFrame = currentFrame;

        Vector3f velocity = new Vector3f(0f);

        float cameraSpeed = 5f * deltaTime;
        if (inputManager.isPressed("shift")) {
            cameraSpeed *= 3f;
        }
        if (inputManager.isPressed("forward")) {
            velocity.sub(new Vector3f(this.getGlobalTransform().rotation));
        }
        if (inputManager.isPressed("back")) {
            velocity.add(new Vector3f(this.getGlobalTransform().rotation));
        }
        if (inputManager.isPressed("right")) {
            velocity.add(new Vector3f(new Vector3f(this.getGlobalTransform().rotation).cross(new Vector3f(0f, 1f, 0f))).normalize());
        }
        if (inputManager.isPressed("left")) {
            velocity.sub(new Vector3f(new Vector3f(this.getGlobalTransform().rotation).cross(new Vector3f(0f, 1f, 0f))).normalize());
        }
        if(inputManager.isPressed("jump")){
            velocity.add(new Vector3f(0f,1f,0f).mul(cameraSpeed * 2f));
        }
        if(inputManager.isPressed("down")){
            velocity.sub(new Vector3f(0f,1f,0f).mul(cameraSpeed * 2f));
        }


        velocity.mul(cameraSpeed);

        this.transform.position.add(velocity);
    }

    @Override
    public void processMouseInput(float x, float y){

        float xOffset = x - lastX;
        float yOffset = lastY - y;
        lastX = x;
        lastY = y;

        float sensitivity = 0.1f;
        xOffset *= sensitivity;
        yOffset *= sensitivity;

        yaw   -= xOffset;
        pitch -= yOffset;

        if(pitch > 89.0f)
            pitch = 89.0f;
        if(pitch < -89.0f)
            pitch = -89.0f;

        this.transform.rotation.x = cos(Math.toRadians(yaw)) * cos(Math.toRadians(pitch));//pitch yaw
        this.transform.rotation.y = sin(Math.toRadians(pitch));//pitch
        this.transform.rotation.z = sin(Math.toRadians(yaw)) * cos(Math.toRadians(pitch));//pitch yaw

        this.transform.rotation.normalize();
    }

    @Override
    public void render() {
        processInput();
    }
}
