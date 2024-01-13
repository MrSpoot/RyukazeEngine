package org.spoot.ryukazev2.entity;

import org.spoot.ryukazev2.core.Entity;
import org.spoot.ryukazev2.component.game.CameraComponent;
import org.spoot.ryukazev2.component.game.TransformComponent;

public class Camera extends Entity {

    public Camera(){
        this.linkComponent(new CameraComponent()).linkComponent(new TransformComponent());
    }

}
