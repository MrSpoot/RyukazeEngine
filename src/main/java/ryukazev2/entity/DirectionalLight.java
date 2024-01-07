package ryukazev2.entity;

import ryukazev2.component.DirectionalLightComponent;
import ryukazev2.component.TransformComponent;
import ryukazev2.core.Entity;

public class DirectionalLight extends Entity {

    public DirectionalLight(){
        this.linkComponent(new DirectionalLightComponent()).linkComponent(new TransformComponent());
    }

}
