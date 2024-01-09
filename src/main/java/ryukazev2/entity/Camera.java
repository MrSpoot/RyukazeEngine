package ryukazev2.entity;

import ryukazev2.component.game.CameraComponent;
import ryukazev2.component.game.TransformComponent;
import ryukazev2.core.Entity;

public class Camera extends Entity {

    public Camera(){
        this.linkComponent(new CameraComponent()).linkComponent(new TransformComponent());
    }

}
