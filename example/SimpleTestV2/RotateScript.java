package SimpleTestV2;

import org.joml.Math;
import org.joml.Vector3f;
import ryukazev2.component.TransformComponent;
import ryukazev2.core.Entity;
import ryukazev2.core.IScript;

public class RotateScript implements IScript {

    private Entity entity;
    private Vector3f lastRotation;
    private float rotationSpeed;

    public RotateScript(float rotationSpeed){
        this.rotationSpeed = rotationSpeed;
    }

    @Override
    public void init(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void update(float deltaTime) {
        this.lastRotation = this.entity.getComponent(TransformComponent.class).getRotation();
        this.entity.getComponent(TransformComponent.class).setRotation(lastRotation.x, lastRotation.y + Math.toRadians(rotationSpeed*deltaTime),lastRotation.z);
    }

    @Override
    public void render() {

    }

    @Override
    public void cleanup() {

    }
}
