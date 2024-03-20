package org.spoot.ryukazev2.graphic.component.game;

import lombok.Getter;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.spoot.ryukazev2.component.Component;
import org.spoot.ryukazev2.component.TransformComponent;

@Getter
public class DirectionalLightComponent extends Component {

    private Vector3f ambient;
    private Vector3f specular;
    private Vector3f diffuse;
    private float intensity;

    public DirectionalLightComponent(){
        this.ambient = new Vector3f(1f);
        this.specular = new Vector3f(1f);
        this.diffuse = new Vector3f(1f);
        this.intensity = 1f;
    }

    public Vector3f getDirection(){
        Vector3f forward = new Vector3f(0, -1, 0);
        Quaternionf rotation = this.getEntity().getComponent(TransformComponent.class).getRotation();
        forward.rotate(rotation);
        return forward;
    }

    public DirectionalLightComponent setColor(Vector3f color){
        this.ambient = color;
        return this;
    }

    public DirectionalLightComponent setDiffuseColor(Vector3f diffuseColor){
        this.diffuse = diffuseColor;
        return this;
    }

    public DirectionalLightComponent setSpecularColor(Vector3f specularColor){
        this.specular = specularColor;
        return this;
    }

    public DirectionalLightComponent setIntensity(float intensity){
        this.intensity = intensity;
        return this;
    }

    public DirectionalLightComponent build(){
        return this;
    }

}
