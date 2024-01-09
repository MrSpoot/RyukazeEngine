package SimpleTestV2;

import org.joml.Vector3f;
import ryukazev2.component.game.TransformComponent;
import ryukazev2.core.Entity;
import ryukazev2.core.Time;
import ryukazev2.core.interfaces.IScript;

public class RotateScript implements IScript {

    private Entity entity;
    private Vector3f lastRotation;
    private float rotationSpeed;
    private Vector3f rotationVector;

    public RotateScript(float rotationSpeed, Vector3f rotationVector){
        this.rotationSpeed = rotationSpeed;
        this.rotationVector = rotationVector;
    }

    @Override
    public void init(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void update() {

        //Vector3f v = new Vector3f(rotationVector.mul(rotationSpeed * Time.deltaTime));

        this.entity.getComponent(TransformComponent.class).rotate(0,0,rotationSpeed * Time.deltaTime);
    }

    @Override
    public void render() {

    }

    @Override
    public void cleanup() {

    }
}
