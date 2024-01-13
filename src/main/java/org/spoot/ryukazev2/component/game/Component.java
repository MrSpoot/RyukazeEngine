package org.spoot.ryukazev2.component.game;

import lombok.Getter;
import org.spoot.ryukazev2.core.Entity;

public abstract class Component {

    @Getter
    private Entity entity;

    public void linkEntity(Entity entity){
        this.entity = entity;
    }

}
