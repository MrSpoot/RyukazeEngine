package org.spoot.ryukazev2.graphic.entity;

import org.spoot.ryukazev2.graphic.core.Entity;
import org.spoot.ryukazev2.graphic.component.game.PointLightComponent;
import org.spoot.ryukazev2.component.TransformComponent;

public class PointLight extends Entity {

    public PointLight(){
        this.linkComponent(new PointLightComponent()).linkComponent(new TransformComponent());
    }

}
