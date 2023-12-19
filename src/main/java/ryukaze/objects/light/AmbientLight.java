package ryukaze.objects.light;

import ryukaze.core.Engine;
import ryukaze.objects.GameObject;

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
        Engine.getScene().getShader().setUniform("light.position",this.getGlobalTransform().position);
    }
}
