package org.spoot.ryukazev2.graphic.core;

import lombok.Getter;
import org.spoot.ryukazev2.graphic.component.ui.UIComponent;
import org.spoot.ryukazev2.graphic.manager.EntityManager;
import org.spoot.ryukazev2.utils.ServiceLocator;
import org.spoot.ryukazev2.utils.UniqueIdGenerator;

import java.util.*;

@Getter
public class UIEntity {

    private final String id;
    private final Map<Class<? extends UIComponent>, Map<String,UIComponent>> components;

    public UIEntity() {
        this.id = UniqueIdGenerator.generateUniqueID(15);
        this.components = new HashMap<>();
        ServiceLocator.getService(EntityManager.class).subscribe(this);
    }

    public UIEntity linkComponent(UIComponent component) {
        component.linkEntity(this);

        if(components.containsKey(component.getClass())){
            components.get(component.getClass()).put(component.getLabel(),component);
        }else{
            components.put(component.getClass(),new HashMap<>());
            components.get(component.getClass()).put(component.getLabel(),component);
        }
        return this;
    }

    public <T extends UIComponent> T getComponent(String label, Class<T> componentClass) {
        return componentClass.cast(components.get(componentClass).get(label));
    }

    public <T extends UIComponent> List<T> getComponentsByClass(Class<T> componentClass) {
        Map<String, UIComponent> componentMap = components.get(componentClass);

        ArrayList<T> matched = new ArrayList<>();

        if(componentClass != null){
            for(UIComponent cpl : componentMap.values()){
                matched.add((T) cpl);
            }
            return matched;
        }else{
            return Collections.emptyList();
        }
    }

    public <T extends UIComponent> Map<String,T> getComponents(Class<T> componentClass) {
        return (Map<String, T>) components.get(componentClass);
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
