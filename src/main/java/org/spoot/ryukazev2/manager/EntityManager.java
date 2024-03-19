package org.spoot.ryukazev2.manager;

import lombok.Getter;
import org.spoot.ryukazev2.core.Entity;
import org.spoot.ryukazev2.core.UIEntity;
import org.spoot.ryukazev2.component.game.Component;
import org.spoot.ryukazev2.component.ui.UIComponent;
import org.spoot.ryukazev2.utils.ServiceLocator;

import java.util.ArrayList;
import java.util.List;

public class EntityManager extends Manager {

    @Getter
    private List<Entity> entities;
    @Getter
    private List<UIEntity> uiEntities;

    public EntityManager() {
        this.entities = new ArrayList<>();
        this.uiEntities = new ArrayList<>();
        ServiceLocator.registerService(EntityManager.class,this);
    }

    public void subscribe(Entity entity){
        this.entities.add(entity);
    }

    public void unsubscribe(Entity entity){
        this.entities.remove(entity);
    }

    public void subscribe(UIEntity entity){
        this.uiEntities.add(entity);
    }

    public void unsubscribe(UIEntity entity){
        this.uiEntities.remove(entity);
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

    @SafeVarargs
    public final List<Entity> getEntityByAnyComponent(Class<? extends Component>... componentClasses) {
        List<Entity> matchingEntities = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity.hasAnyComponents(componentClasses)) {
                matchingEntities.add(entity);
            }
        }
        return matchingEntities;
    }

    @SafeVarargs
    public final List<UIEntity> getUIEntityByComponent(Class<? extends UIComponent>... componentClasses) {
        List<UIEntity> matchingEntities = new ArrayList<>();
        for (UIEntity entity : uiEntities) {
            if (entity.hasAllComponents(componentClasses)) {
                matchingEntities.add(entity);
            }
        }
        return matchingEntities;
    }

    @SafeVarargs
    public final List<UIEntity> getUIEntityByAnyComponent(Class<? extends UIComponent>... componentClasses) {
        List<UIEntity> matchingEntities = new ArrayList<>();
        for (UIEntity entity : uiEntities) {
            if (entity.hasAnyComponents(componentClasses)) {
                matchingEntities.add(entity);
            }
        }
        return matchingEntities;
    }
}
