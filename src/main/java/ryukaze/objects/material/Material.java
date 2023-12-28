package ryukaze.objects.material;


import lombok.Data;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Text;
import ryukaze.core.Engine;

import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL13.*;

@Data
public class Material {

    private static final Logger LOGGER = LoggerFactory.getLogger(Material.class);
    private Map<String,Texture> textures;
    private float shininess;

    static {
        Engine.getScene().getShader().useProgram();
        Engine.getScene().getShader().setUniform("material.diffuse",0);
        Engine.getScene().getShader().setUniform("material.specular",1);
    }

    public Material() {
        this.textures = new LinkedHashMap<>();
        this.textures.put("diffuse",new Texture(new Vector4f(1.0f,1.0f,1.0f,1.0f)));
        this.textures.put("specular",new Texture(new Vector4f(1.0f,1.0f,1.0f,1.0f)));
        this.shininess = 32f;
    }

    public void render(){
        int index = 0;
        for(Texture texture : textures.values()){
            glActiveTexture(GL_TEXTURE0 + index);
            glBindTexture(GL_TEXTURE_2D,texture.getTexture());
            index++;
        }
        Engine.getScene().getShader().setUniform("material.shininess", this.getShininess());
    }
}
