package org.spoot.ryukazev2.graphic.entity;

import org.spoot.ryukazev2.graphic.core.Entity;
import org.spoot.ryukazev2.graphic.component.game.DirectionalLightComponent;
import org.spoot.ryukazev2.graphic.component.game.TransformComponent;

public class DirectionalLight extends Entity {

    public DirectionalLight(){
        this.linkComponent(new DirectionalLightComponent().setIntensity(0.8f)).linkComponent(new TransformComponent());
    }

}
