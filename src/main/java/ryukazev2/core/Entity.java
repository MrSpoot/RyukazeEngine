package ryukazev2.core;

import lombok.Getter;
import ryukazev2.component.Component;
import ryukazev2.manager.EntityManager;
import ryukazev2.utils.ServiceLocator;
import ryukazev2.utils.UniqueIdGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Entity {

    private final String id;
    private Entity parent;
    private final List<Entity> children;
    private final Map<Class<? extends Component>, Component> components;

    public Entity() {
        this.id = UniqueIdGenerator.generateUniqueID(15);
        this.components = new HashMap<>();
        this.parent = null;
        this.children = new ArrayList<>();
        ServiceLocator.getService(EntityManager.class).subscribe(this);
    }

    private void linkParent(Entity entity){
        this.parent = entity;
    }

    public Entity linkChildren(Entity entity){
        entity.linkParent(this);
        this.children.add(entity);
        return this;
    }

    public Entity linkComponent(Component component) {
        component.linkEntity(this);
        components.put(component.getClass(), component);
        return this;
    }

    public <T extends Component> T getComponent(Class<T> componentClass) {
        return componentClass.cast(components.get(componentClass));
    }

    @SafeVarargs
    public final boolean hasAllComponents(Class<? extends Component>... componentClasses) {
        for (Class<? extends Component> cls : componentClasses) {
            if (!components.containsKey(cls)) {
                return false;
            }
        }
        return true;
    }
}
