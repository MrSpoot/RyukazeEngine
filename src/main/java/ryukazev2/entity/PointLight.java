package ryukazev2.entity;

import ryukazev2.component.PointLightComponent;
import ryukazev2.component.SpotLightComponent;
import ryukazev2.component.TransformComponent;
import ryukazev2.core.Entity;

public class PointLight extends Entity {

    public PointLight(){
        this.linkComponent(new PointLightComponent()).linkComponent(new TransformComponent());
    }

}
