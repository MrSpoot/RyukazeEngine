package ryukazev2.manager;

import lombok.Getter;
import ryukazev2.component.Component;
import ryukazev2.core.Entity;
import ryukazev2.core.Scene;
import ryukazev2.entity.Camera;
import ryukazev2.utils.ServiceLocator;

import java.util.ArrayList;
import java.util.List;

public class EntityManager extends Manager {

    @Getter
    private List<Entity> entities;

    public EntityManager() {
        this.entities = new ArrayList<>();
        ServiceLocator.registerService(EntityManager.class,this);
    }

    public void subscribe(Entity entity){
        this.entities.add(entity);
    }

    public void unsubscribe(Entity entity){
        this.entities.remove(entity);
    }

    @SafeVarargs
    public final List<Entity> getEntityByComponent(Class<? extends Component>... componentClasses) {
        List<Entity> matchingEntities = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity.hasAllComponents(componentClasses)) {
                matchingEntities.add(entity);
            }
        }
        return matchingEntities;
    }
}
