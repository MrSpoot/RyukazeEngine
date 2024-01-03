package ryukazev2.core;

import lombok.Getter;
import ryukazev2.component.Component;
import ryukazev2.manager.EntityManager;
import ryukazev2.utils.ServiceLocator;
import ryukazev2.utils.UniqueIdGenerator;

import java.util.HashMap;
import java.util.Map;

public class Entity {
    
    @Getter
    private final String id;
    private final Map<Class<? extends Component>, Component> components;

    public Entity() {
        this.id = UniqueIdGenerator.generateUniqueID(15);
        this.components = new HashMap<>();
        ServiceLocator.getService(EntityManager.class).subscribe(this);
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
