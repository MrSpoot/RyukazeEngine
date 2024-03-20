package org.spoot.ryukazev2.graphic.component.game;

import lombok.Getter;
import org.spoot.ryukazev2.graphic.core.Entity;

@Getter
public abstract class Component {

    private Entity entity;

    public void linkEntity(Entity entity){
        this.entity = entity;
    }

}
