package ryukazev2.core;

import lombok.Getter;
import ryukazev2.component.Component;
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
    }

    public void linkComponent(Component component) {
        components.put(component.getClass(), component);
    }

    public <T extends Component> T getComponent(Class<T> componentClass) {
        return componentClass.cast(components.get(componentClass));
    }
}
