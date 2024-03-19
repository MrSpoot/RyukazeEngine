package org.spoot.ryukazev2.component.game;

import lombok.Getter;
import org.joml.Vector3f;

@Getter
public class PointLightComponent extends Component {

    private Vector3f ambient;
    private Vector3f specular;
    private Vector3f diffuse;
    private float intensity;
    private float linear;
    private float quadratic;

    public PointLightComponent(){
        this.ambient = new Vector3f(1f);
        this.specular = new Vector3f(1f);
        this.diffuse = new Vector3f(1f);
        this.intensity = 1f;
        this.linear = 0.09f;
        this.quadratic = 0.032f;
    }

    public PointLightComponent setColor(Vector3f color){
        this.ambient = color;
        return this;
    }

    public PointLightComponent setDiffuseColor(Vector3f diffuseColor){
        this.diffuse = diffuseColor;
        return this;
    }

    public PointLightComponent setSpecularColor(Vector3f specularColor){
        this.specular = specularColor;
        return this;
    }

    public PointLightComponent setIntensity(float intensity){
        this.intensity = intensity;
        return this;
    }

    public PointLightComponent setLinear(float linear) {
        this.linear = linear;
        return this;
    }

    public PointLightComponent setQuadratic(float quadratic) {
        this.quadratic = quadratic;
        return this;
    }

    public PointLightComponent build(){
        return this;
    }

}
