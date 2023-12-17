package ryukazev2.physics.body;

import org.joml.Vector3f;
import ryukazev2.objects.GameObject;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class PhysicBody3D extends PhysicBody{

    private float mass = 2;
    private Vector3f velocity = new Vector3f();
    private float lastFrame = 0f;
    private static final float GRAVITY = 9.81f;

    @Override
    public void update(GameObject object){

        float currentFrame = (float)glfwGetTime();
        float deltaTime = currentFrame - lastFrame;
        lastFrame = currentFrame;

        velocity.y += GRAVITY * deltaTime;

        object.getTransform().getPosition().add(velocity.mul(deltaTime));

    }

}
