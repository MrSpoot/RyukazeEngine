package org.spoot.ryukazev2.component.game;

import lombok.Getter;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@Getter
public class SpotLightComponent extends Component {

    private Vector3f ambient;
    private Vector3f specular;
    private Vector3f diffuse;
    private float intensity;
    private float cutOff;
    private float outCutOff;
    private float linear;
    private float quadratic;

    public SpotLightComponent(){
        this.ambient = new Vector3f(1f);
        this.specular = new Vector3f(1f);
        this.diffuse = new Vector3f(1f);
        this.intensity = 1f;
        this.cutOff = (float) Math.cos(Math.toRadians(12.5f));
        this.outCutOff = (float) Math.cos(Math.toRadians(25f));
        this.linear = 0.09f;
        this.quadratic = 0.032f;
    }

    public Vector3f getDirection() {
        Vector3f forward = new Vector3f(0, -1, 0);
        Quaternionf rotation = this.getEntity().getComponent(TransformComponent.class).getRotation();
        forward.rotate(rotation);
        return forward;
    }

    public SpotLightComponent setColor(Vector3f color){
        this.ambient = color;
        return this;
    }

    public SpotLightComponent setDiffuseColor(Vector3f diffuseColor){
        this.diffuse = diffuseColor;
        return this;
    }

    public SpotLightComponent setSpecularColor(Vector3f specularColor){
        this.specular = specularColor;
        return this;
    }

    public SpotLightComponent setIntensity(float intensity){
        this.intensity = intensity;
        return this;
    }

    public SpotLightComponent setCutOff(float cutOff) {
        this.cutOff = cutOff;
        return this;
    }

    public SpotLightComponent setOutCutOff(float outCutOff) {
        this.outCutOff = outCutOff;
        return this;
    }

    public SpotLightComponent setLinear(float linear) {
        this.linear = linear;
        return this;
    }

    public SpotLightComponent setQuadratic(float quadratic) {
        this.quadratic = quadratic;
        return this;
    }

    public SpotLightComponent build(){
        return this;
    }

}
