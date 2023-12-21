package ryukaze.objects.light;

import org.joml.Vector4f;
import ryukaze.core.Engine;
import ryukaze.objects.GameObject;

public class DirectionalLight extends Light{

    public DirectionalLight() {
        super(null);
    }

    public DirectionalLight(GameObject parent) {
        super(parent);
    }

    @Override
    public void render(){
        Engine.getScene().getShader().setUniform("light.ambient", this.getAmbient());
        Engine.getScene().getShader().setUniform("light.diffuse", this.getDiffuse() );
        Engine.getScene().getShader().setUniform("light.specular", this.getSpecular());

        Vector4f direction = new Vector4f(this.getGlobalTransform().rotation,0.0f);

        Engine.getScene().getShader().setUniform("light.vector",direction);
    }
}
