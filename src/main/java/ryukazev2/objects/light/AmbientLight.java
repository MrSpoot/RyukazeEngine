package ryukazev2.objects.light;

import ryukazev2.core.Engine;
import ryukazev2.objects.GameObject;

public class AmbientLight extends Light{

    public AmbientLight() {
        super(null);
    }

    public AmbientLight(GameObject parent) {
        super(parent);
    }

    @Override
    public void render(){
        Engine.getScene().getShader().setUniform("lightColor",this.getLightColor());
        Engine.getScene().getShader().setUniform("lightPos",this.getGlobalTransform().position);
    }
}
