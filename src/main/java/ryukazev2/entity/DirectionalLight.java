package ryukazev2.entity;

import ryukazev2.component.game.DirectionalLightComponent;
import ryukazev2.component.game.TransformComponent;
import ryukazev2.core.Entity;

public class DirectionalLight extends Entity {

    public DirectionalLight(){
        this.linkComponent(new DirectionalLightComponent()).linkComponent(new TransformComponent());
    }

}
