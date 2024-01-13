package org.spoot.ryukaze.objects.light;

import org.joml.Vector3f;
import org.spoot.ryukaze.core.Engine;
import org.spoot.ryukaze.objects.GameObject;

public class DirectionalLight extends Light{

    public DirectionalLight() {
        super(null);
        this.transform.setRotation(new Vector3f(0f,-1f,0f));
    }

    public DirectionalLight(GameObject parent) {
        super(parent);
    }

    @Override
    public void render(){
        Engine.getScene().getShader().setUniform("directionalLight.intensity", this.getIntensity());
        Engine.getScene().getShader().setUniform("directionalLight.ambient", this.getAmbient());
        Engine.getScene().getShader().setUniform("directionalLight.diffuse", this.getDiffuse());
        Engine.getScene().getShader().setUniform("directionalLight.specular", this.getSpecular());
        Engine.getScene().getShader().setUniform("directionalLight.direction",this.getGlobalTransform().rotation);
    }
}
