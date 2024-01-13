package org.spoot.ryukazev2.entity;

import org.spoot.ryukazev2.core.Entity;
import org.spoot.ryukazev2.component.game.PointLightComponent;
import org.spoot.ryukazev2.component.game.TransformComponent;

public class PointLight extends Entity {

    public PointLight(){
        this.linkComponent(new PointLightComponent()).linkComponent(new TransformComponent());
    }

}
