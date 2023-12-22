package ryukaze.objects.light;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.joml.Vector3f;
import ryukaze.core.Engine;
import ryukaze.objects.GameObject;

@EqualsAndHashCode(callSuper = true)
@Data
public class PointLight extends Light{

    private float constant;
    private float linear;
    private float quadratic;

    public PointLight() {
        this(null);
    }

    public PointLight(GameObject parent) {
        super(parent);
        this.constant = 1.0f;
        this.linear = 0.09f;
        this.quadratic = 0.032f;
    }

    @Override
    public void render(){
        Engine.getScene().getShader().setUniform("pointLight.intensity", this.getIntensity());
        Engine.getScene().getShader().setUniform("pointLight.ambient", this.getAmbient());
        Engine.getScene().getShader().setUniform("pointLight.diffuse", this.getDiffuse());
        Engine.getScene().getShader().setUniform("pointLight.specular", this.getSpecular());
        Engine.getScene().getShader().setUniform("pointLight.position",this.getGlobalTransform().position);
        Engine.getScene().getShader().setUniform("pointLight.constant",this.constant);
        Engine.getScene().getShader().setUniform("pointLight.linear",this.linear);
        Engine.getScene().getShader().setUniform("pointLight.quadratic",this.quadratic);
    }
}
