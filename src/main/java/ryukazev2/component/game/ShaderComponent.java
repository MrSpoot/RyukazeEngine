package ryukazev2.component.game;

import lombok.Getter;
import ryukazev2.core.Cache;
import ryukazev2.core.Shader;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ShaderComponent extends Component{

    private final Shader shader;

    public ShaderComponent(){

        Map<String, Object> shaderAttributeForCache = new HashMap<>();

        shaderAttributeForCache.put("vertexPath","src/main/resources/shader/default.scene.vert");
        shaderAttributeForCache.put("fragmentPath","src/main/resources/shader/default.scene.frag");

        if(Cache.isObjectCached(shaderAttributeForCache)){
            this.shader = Cache.getCacheObject(shaderAttributeForCache);
        }else{
            this.shader = new Shader((String) shaderAttributeForCache.get("vertexPath"), (String) shaderAttributeForCache.get("fragmentPath"));
            Cache.putCacheObject(shaderAttributeForCache,this.shader);
        }

        this.shader.setUniform("material.diffuse",0);
        this.shader.setUniform("material.specular",1);
    }

    public ShaderComponent build(){
        return this;
    }

}
