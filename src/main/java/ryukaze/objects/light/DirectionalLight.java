package ryukaze.objects.light;

import org.joml.Vector3f;
import org.joml.Vector4f;
import ryukaze.core.Engine;
import ryukaze.objects.GameObject;

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
        Engine.getScene().getShader().setUniform("directionalLight.ambient", this.getAmbient().mul(this.getBrightness()));
        Engine.getScene().getShader().setUniform("directionalLight.diffuse", this.getDiffuse().mul(this.getBrightness()));
        Engine.getScene().getShader().setUniform("directionalLight.specular", this.getSpecular().mul(this.getBrightness()));
        Engine.getScene().getShader().setUniform("directionalLight.direction",this.getGlobalTransform().rotation);
    }
}
