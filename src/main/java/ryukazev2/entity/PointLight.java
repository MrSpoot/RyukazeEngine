package ryukazev2.entity;

import ryukazev2.component.game.PointLightComponent;
import ryukazev2.component.game.TransformComponent;
import ryukazev2.core.Entity;

public class PointLight extends Entity {

    public PointLight(){
        this.linkComponent(new PointLightComponent()).linkComponent(new TransformComponent());
    }

}
