package org.spoot.ryukazev2.graphic.entity;

import org.spoot.ryukazev2.graphic.core.Entity;
import org.spoot.ryukazev2.graphic.component.game.SpotLightComponent;
import org.spoot.ryukazev2.graphic.component.game.TransformComponent;

public class SpotLight extends Entity {

    public SpotLight(){
        this.linkComponent(new SpotLightComponent()).linkComponent(new TransformComponent());
    }

}
