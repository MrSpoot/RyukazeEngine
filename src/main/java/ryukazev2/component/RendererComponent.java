package ryukazev2.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class RendererComponent extends Component{

    private static final Logger LOGGER = LoggerFactory.getLogger(RendererComponent.class);

    public void render(){
        MeshComponent meshComponent = this.getEntity().getComponent(MeshComponent.class);
        if(meshComponent != null){
            glBindVertexArray(meshComponent.getVao());
            glDrawElements(GL_TRIANGLES, meshComponent.getIndices().length, GL_UNSIGNED_INT, 0);
        }else{
            LOGGER.warn("Can't render entity | No mesh found");
        }
    }

}
