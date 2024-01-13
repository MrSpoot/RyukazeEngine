package org.spoot.ryukazev2.entity;

import org.spoot.ryukazev2.core.Entity;
import org.spoot.ryukazev2.component.game.DirectionalLightComponent;
import org.spoot.ryukazev2.component.game.TransformComponent;

public class DirectionalLight extends Entity {

    public DirectionalLight(){
        this.linkComponent(new DirectionalLightComponent()).linkComponent(new TransformComponent());
    }

}
