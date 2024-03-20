package org.spoot.ryukazev2.graphic.graphics;


import lombok.Data;
import org.joml.Vector4f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
public class Material {

    private static final Logger LOGGER = LoggerFactory.getLogger(Material.class);
    private Texture diffuse;
    private Texture specular;
    private float shininess;

    public Material() {
        this.diffuse = new Texture(new Vector4f(1.0f,1.0f,1.0f,1.0f));
        this.specular = new Texture(new Vector4f(1.0f,1.0f,1.0f,1.0f));
        this.shininess = 32f;
    }

}
