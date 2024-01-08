package ryukazev2.core;

import ryukazev2.component.UIComponent;
import ryukazev2.manager.EntityManager;
import ryukazev2.manager.UIManager;
import ryukazev2.utils.ServiceLocator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UIEntity {

    private final Map<Class<? extends UIComponent>, UIComponent> components;

    public UIEntity() {
        this.components = new HashMap<>();
        ServiceLocator.getService(UIManager.class).subscribe(this);
    }

    public UIEntity linkUIComponent(UIComponent component) {
        component.linkEntity(this);
        components.put(component.getClass(), component);
        return this;
    }

    public <T extends UIComponent> T getComponent(Class<T> componentClass) {
        return componentClass.cast(components.get(componentClass));
    }

    public <T extends UIComponent> List<T> getComponents(Class<T> componentClass) {
        List<T> matchedComponents = new ArrayList<>();
        for (UIComponent comp : components.values()) {
            if (componentClass.isInstance(comp)) {
                matchedComponents.add(componentClass.cast(comp));
            }
        }
        return matchedComponents;
    }

    @SafeVarargs
    public final boolean hasAllComponents(Class<? extends UIComponent>... componentClasses) {
        for (Class<? extends UIComponent> cls : componentClasses) {
            if (!components.containsKey(cls)) {
                return false;
            }
        }
        return true;
    }

    @SafeVarargs
    public final boolean hasAnyComponents(Class<? extends UIComponent>... componentClasses) {
        for (Class<? extends UIComponent> cls : componentClasses) {
            if (components.containsKey(cls)) {
                return true;
            }
        }
        return false;
    }
}
