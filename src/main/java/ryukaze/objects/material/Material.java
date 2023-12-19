package ryukaze.objects.material;


import lombok.Data;
import org.joml.Vector3f;
import ryukaze.core.Engine;

@Data
public class Material {

    private Texture texture;
    private Vector3f ambient;
    private Vector3f diffuse;
    private Vector3f specular;
    private float shininess;

    public Material() {
        this.ambient = new Vector3f(0.5f);
        this.diffuse = new Vector3f(0.5f);
        this.specular = new Vector3f(0.5f);
        this.shininess = 32f;
    }

    public Material(Vector3f ambient, Vector3f diffuse, Vector3f specular, float shininess) {
        this.ambient = ambient;
        this.diffuse = diffuse;
        this.specular = specular;
        this.shininess = shininess;
    }

    public void render(){
        if(texture != null){
            texture.render();
        }
        Engine.getScene().getShader().setUniform("material.ambient", this.getAmbient());
        Engine.getScene().getShader().setUniform("material.diffuse",  this.getDiffuse());
        Engine.getScene().getShader().setUniform("material.specular", this.getSpecular());
        Engine.getScene().getShader().setUniform("material.shininess", this.getShininess());
    }

}
