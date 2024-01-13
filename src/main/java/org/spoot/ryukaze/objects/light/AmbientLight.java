package org.spoot.ryukaze.objects.light;

import org.joml.Vector4f;
import org.spoot.ryukaze.core.Engine;
import org.spoot.ryukaze.objects.GameObject;

public class AmbientLight extends Light{

    public AmbientLight() {
        super(null);
    }

    public AmbientLight(GameObject parent) {
        super(parent);
    }

    @Override
    public void render(){
        Engine.getScene().getShader().setUniform("light.ambient", this.getAmbient());
        Engine.getScene().getShader().setUniform("light.diffuse", this.getDiffuse() );
        Engine.getScene().getShader().setUniform("light.specular", this.getSpecular());
        Engine.getScene().getShader().setUniform("light.vector",new Vector4f(this.getGlobalTransform().position,1.0f));
    }
}
