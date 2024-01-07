package ryukazev2.entity;

import ryukazev2.component.DirectionalLightComponent;
import ryukazev2.component.SpotLightComponent;
import ryukazev2.component.TransformComponent;
import ryukazev2.core.Entity;

public class SpotLight extends Entity {

    public SpotLight(){
        this.linkComponent(new SpotLightComponent()).linkComponent(new TransformComponent());
    }

}
