package ryukazev2.component;

import lombok.Getter;
import ryukazev2.core.Entity;

public abstract class Component {

    @Getter
    private Entity entity;

    public void linkEntity(Entity entity){
        this.entity = entity;
    }

}
