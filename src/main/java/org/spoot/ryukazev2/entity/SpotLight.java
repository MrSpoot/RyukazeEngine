package org.spoot.ryukazev2.entity;

import org.spoot.ryukazev2.core.Entity;
import org.spoot.ryukazev2.component.game.SpotLightComponent;
import org.spoot.ryukazev2.component.game.TransformComponent;

public class SpotLight extends Entity {

    public SpotLight(){
        this.linkComponent(new SpotLightComponent()).linkComponent(new TransformComponent());
    }

}
