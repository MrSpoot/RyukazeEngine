package org.spoot.ryukazev2.graphic.core;

import lombok.Getter;
import org.spoot.ryukazev2.component.Component;
import org.spoot.ryukazev2.graphic.manager.EntityManager;
import org.spoot.ryukazev2.utils.ServiceLocator;
import org.spoot.ryukazev2.utils.UniqueIdGenerator;

import java.util.*;

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
        for (Component component : components.values()) {
            if (componentClass.isAssignableFrom(component.getClass())) {
                return componentClass.cast(component);
            }
        }
        return null;
    }

    public <T extends Component> List<T> getComponents(Class<T> componentClass) {
        List<T> matchedComponents = new ArrayList<>();
        for (Component comp : components.values()) {
            if (componentClass.isInstance(comp)) {
                matchedComponents.add(componentClass.cast(comp));
            }
        }
        return matchedComponents;
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

    @SafeVarargs
    public final boolean hasAnyComponents(Class<? extends Component>... componentClasses) {
        for (Class<? extends Component> cls : componentClasses) {
            if (components.containsKey(cls)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Entity other = (Entity) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() {
        return this.id != null ? this.id.hashCode() : 0;
    }
}
