package Physics;

import org.joml.Vector3f;
import org.spoot.ryukazev2.component.game.MeshComponent;
import org.spoot.ryukazev2.component.game.ShaderComponent;
import org.spoot.ryukazev2.component.game.TransformComponent;
import org.spoot.ryukazev2.component.game.shape.SphereShape;
import org.spoot.ryukazev2.core.Entity;
import org.spoot.ryukazev2.core.Time;
import org.spoot.ryukazev2.core.interfaces.IScript;

import java.util.ArrayList;
import java.util.List;

public class PhysicsScript implements IScript {

    private Entity entity;
    private final float GRAVITY = -0.1f;

    private List<Entity> entities;

    @Override
    public void init(Entity entity) {
        this.entity = entity;

        this.entities = new ArrayList<>();

        this.entities.add(new Entity().linkComponent(new TransformComponent().setPosition(0,0,-20))
                .linkComponent(new MeshComponent().applyShape(new SphereShape(15)).build())
                .linkComponent(new ShaderComponent().build()));

        this.entities.add(new Entity().linkComponent(new TransformComponent().setPosition(0,-2,-20))
                .linkComponent(new MeshComponent().applyShape(new SphereShape(15)).build())
                .linkComponent(new ShaderComponent().build()));
    }

    @Override
    public void update() {
        for(Entity entity1 : entities){

            boolean collide = false;

            for(Entity entity2 : entities){
                if(collidesWith(entity1,entity2)){
                    collide = true;
                }
            }
            if(entities.size() < 2 || !collide){
                accelerate(entity1);
            }
            break;
        }
    }

    @Override
    public void render() {

    }

    @Override
    public void cleanup() {

    }

    public boolean collidesWith(Entity actual, Entity other) {
        if(actual.getId().equals(other.getId())) return false;
        float distanceSquared = actual.getComponent(TransformComponent.class).getPosition().distanceSquared(other.getComponent(TransformComponent.class).getPosition());
        float radiusSum = 1;
        return distanceSquared <= radiusSum * radiusSum;
    }

    private void accelerate(Entity entity){
        entity.getComponent(TransformComponent.class).translate(new Vector3f(0,GRAVITY * Time.deltaTime,0));
    }
}
