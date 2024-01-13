package org.spoot.SimpleTestV2;

import org.joml.Vector3f;
import org.spoot.ryukazev2.component.game.TransformComponent;
import org.spoot.ryukazev2.core.Entity;
import org.spoot.ryukazev2.core.Input;
import org.spoot.ryukazev2.core.InputTouch;
import org.spoot.ryukazev2.core.Time;
import org.spoot.ryukazev2.core.interfaces.IScript;

import static org.lwjgl.glfw.GLFW.*;

public class Movement implements IScript {
    private Entity entity;
    private float xSens = 5f;
    private float ySens = 5f;

    @Override
    public void init(Entity entity) {
        this.entity = entity;
        initTouch();
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        float mouseX = Input.getXAxisRaw() * Time.deltaTime * xSens;
        float mouseY = Input.getYAxisRaw() * Time.deltaTime * ySens;

        this.entity.getComponent(TransformComponent.class).rotate(-mouseY,mouseX,0);

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
        if(Input.isPressed("reset")){
            this.entity.getComponent(TransformComponent.class).setPosition(0,0,0);
            this.entity.getComponent(TransformComponent.class).setRotation(0,0,0);
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
        new InputTouch("reset",GLFW_KEY_B);

    }
}
