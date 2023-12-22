package ryukaze.objects.material;


import lombok.Data;
import org.joml.Vector3f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ryukaze.core.Engine;

import java.sql.SQLOutput;

@Data
public class Material {

    private static final Logger LOGGER = LoggerFactory.getLogger(Material.class);
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
            Engine.getScene().getShader().setUniform("material.diffuse",  texture.getTexture());
            Engine.getScene().getShader().setUniform("material.specular", texture.getTexture());
        }
        Engine.getScene().getShader().setUniform("material.shininess", this.getShininess());

    }

}
