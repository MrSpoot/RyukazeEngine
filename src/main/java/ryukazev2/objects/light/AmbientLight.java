package ryukazev2.objects.light;

import org.joml.Vector3f;
import ryukazev2.core.Engine;
import ryukazev2.objects.GameObject;

import static java.lang.Math.sin;
import static org.lwjgl.glfw.GLFW.glfwGetTime;

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
