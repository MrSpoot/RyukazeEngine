package ryukazev2.entity;

import ryukazev2.component.game.SpotLightComponent;
import ryukazev2.component.game.TransformComponent;
import ryukazev2.core.Entity;

public class SpotLight extends Entity {

    public SpotLight(){
        this.linkComponent(new SpotLightComponent()).linkComponent(new TransformComponent());
    }

}
