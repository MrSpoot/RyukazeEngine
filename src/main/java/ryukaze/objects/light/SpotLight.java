package ryukaze.objects.light;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.joml.Vector3f;
import ryukaze.core.Engine;
import ryukaze.objects.GameObject;

@EqualsAndHashCode(callSuper = true)
@Data
public class SpotLight extends PointLight{

    private float cutOff;
    private float outCutOff;

    public SpotLight() {
        super();
        this.transform.setRotation(new Vector3f(0f,-1f,0f));
        this.cutOff = (float) Math.cos(Math.toRadians(12.5f));
        this.outCutOff = (float) Math.cos(Math.toRadians(25f));
    }

    public SpotLight(GameObject parent) {
        super(parent);
    }

    @Override
    public void render(){
        Engine.getScene().getShader().setUniform("spotLight.ambient", this.getAmbient());
        Engine.getScene().getShader().setUniform("spotLight.diffuse", this.getDiffuse());
        Engine.getScene().getShader().setUniform("spotLight.specular", this.getSpecular());
        Engine.getScene().getShader().setUniform("spotLight.intensity", this.getIntensity());

        Engine.getScene().getShader().setUniform("spotLight.cutOff",this.cutOff);
        Engine.getScene().getShader().setUniform("spotLight.outerCutOff",this.outCutOff);
        Engine.getScene().getShader().setUniform("spotLight.direction",this.getGlobalTransform().rotation);
        Engine.getScene().getShader().setUniform("spotLight.position",this.getGlobalTransform().position);

        Engine.getScene().getShader().setUniform("spotLight.constant",this.getConstant());
        Engine.getScene().getShader().setUniform("spotLight.linear",this.getLinear());
        Engine.getScene().getShader().setUniform("spotLight.quadratic",this.getQuadratic());
    }
}
