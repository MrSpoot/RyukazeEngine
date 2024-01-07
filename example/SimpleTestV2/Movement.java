package SimpleTestV2;

import org.joml.Quaternionf;
import org.joml.Vector3f;
import ryukazev2.component.TransformComponent;
import ryukazev2.core.Entity;
import ryukazev2.core.Input;
import ryukazev2.core.Time;
import ryukazev2.core.interfaces.IScript;
import ryukazev2.core.InputTouch;

import static org.lwjgl.glfw.GLFW.*;

public class Movement implements IScript {
    private Entity entity;
    private float xSens = 0.1f;
    private float ySens = 0.1f;
    private Quaternionf rotation;

    @Override
    public void init(Entity entity) {
        this.entity = entity;
        this.rotation = new Quaternionf();
        initTouch();
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        float mouseX = Input.getXAxisRaw() * Time.deltaTime * xSens;
        float mouseY = Input.getYAxisRaw() * Time.deltaTime * ySens;

        Vector3f forward = new Vector3f(0, 0, -1).rotate(this.entity.getComponent(TransformComponent.class).getRotation());
        Vector3f right = new Vector3f(forward).cross(new Vector3f(0, 1, 0)).normalize();

        Vector3f velocity = new Vector3f(0f);

        float cameraSpeed = 5f * Time.deltaTime;

        if(Input.isPressed("sprint")){
            cameraSpeed *= 5f;
        }
        if (Input.isPressed("forward")) {
            velocity.add(forward);
        }
        if (Input.isPressed("back")) {
            velocity.sub(forward);
        }
        if (Input.isPressed("right")) {
            velocity.sub(right);
        }
        if (Input.isPressed("left")) {
            velocity.add(right);
        }

        velocity.mul(cameraSpeed);

        this.entity.getComponent(TransformComponent.class).translate(velocity);
    }

    @Override
    public void cleanup() {

    }

    private void initTouch(){

        new InputTouch("forward",GLFW_KEY_W);
        new InputTouch("back",GLFW_KEY_S);
        new InputTouch("left",GLFW_KEY_A);
        new InputTouch("right",GLFW_KEY_D);
        new InputTouch("sprint",GLFW_KEY_LEFT_SHIFT);

    }
}
