package org.spoot.ryukazev2.component.game;

import lombok.Getter;
import org.spoot.ryukazev2.core.Entity;

@Getter
public abstract class Component {

    private Entity entity;

    public void linkEntity(Entity entity){
        this.entity = entity;
    }

}
