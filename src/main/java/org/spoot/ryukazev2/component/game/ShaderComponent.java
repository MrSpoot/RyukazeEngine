package org.spoot.ryukazev2.component.game;

import lombok.Getter;
import org.spoot.ryukazev2.core.Cache;
import org.spoot.ryukazev2.core.Shader;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ShaderComponent extends Component{

    private final Shader shader;

    public ShaderComponent(){

        Map<String, Object> shaderAttributeForCache = new HashMap<>();

        shaderAttributeForCache.put("shaderPath","src/main/resources/shader/default.scene.glsl");

        if(Cache.isObjectCached(shaderAttributeForCache)){
            this.shader = Cache.getCacheObject(shaderAttributeForCache);
        }else{
            this.shader = new Shader((String) shaderAttributeForCache.get("shaderPath"));
            Cache.putCacheObject(shaderAttributeForCache,this.shader);
        }

        this.shader.setUniform("material.diffuse",0);
        this.shader.setUniform("material.specular",1);
    }

    public ShaderComponent build(){
        return this;
    }

}
