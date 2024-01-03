package ryukazev2.component;

import lombok.Getter;
import ryukazev2.core.Shader;

public class ShaderComponent extends Component{

    @Getter
    private final Shader shader;

    public ShaderComponent(){
        this.shader = new Shader().setVertex("src/main/resources/shader/default.scene.vert").setFragment("src/main/resources/shader/default.scene.frag").build();
        this.shader.setUniform("material.diffuse",0);
        this.shader.setUniform("material.specular",1);
    }

    public ShaderComponent build(){
        return this;
    }

}
