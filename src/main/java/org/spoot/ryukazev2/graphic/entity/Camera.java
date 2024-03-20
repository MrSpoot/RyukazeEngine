package org.spoot.ryukazev2.graphic.entity;

import org.spoot.ryukazev2.graphic.core.Entity;
import org.spoot.ryukazev2.graphic.component.game.CameraComponent;
import org.spoot.ryukazev2.graphic.component.game.TransformComponent;

public class Camera extends Entity {

    public Camera(){
        this.linkComponent(new CameraComponent()).linkComponent(new TransformComponent());
    }

}
