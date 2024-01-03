package ryukazev2.graphics;


import lombok.Data;
import org.joml.Vector4f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class Material {

    private static final Logger LOGGER = LoggerFactory.getLogger(Material.class);
    private Map<String, Texture> textures;
    private float shininess;

    public Material() {
        this.textures = new LinkedHashMap<>();
        this.textures.put("diffuse",new Texture(new Vector4f(1.0f,1.0f,1.0f,1.0f)));
        this.textures.put("specular",new Texture(new Vector4f(1.0f,1.0f,1.0f,1.0f)));
        this.shininess = 32f;
    }

}
