package ryukazev2.builder;

import ryukazev2.component.Component;
import ryukazev2.core.Entity;

public class EntityBuilder {

    private final Entity entity;

    private EntityBuilder(){
        this.entity = new Entity();
    }

    public EntityBuilder linkComponent(Component component){
        component.linkEntity(this.entity);
        this.entity.linkComponent(component);
        return this;
    }

    public Entity build(){
        return this.entity;
    }

}
