package SimpleTestV2;

import org.joml.Math;
import org.joml.Vector3f;
import ryukazev2.component.TransformComponent;
import ryukazev2.core.Entity;
import ryukazev2.core.Time;
import ryukazev2.core.interfaces.IScript;

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
    public void update() {
        this.entity.getComponent(TransformComponent.class).rotate(0,rotationSpeed * Time.deltaTime,0);
    }

    @Override
    public void render() {

    }

    @Override
    public void cleanup() {

    }
}
