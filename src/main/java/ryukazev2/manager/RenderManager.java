package ryukazev2.manager;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ryukazev2.component.MeshComponent;
import ryukazev2.component.ShaderComponent;
import ryukazev2.component.TransformComponent;
import ryukazev2.core.Entity;
import ryukazev2.graphics.Material;
import ryukazev2.graphics.Texture;
import ryukazev2.utils.ServiceLocator;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class RenderManager extends Manager {

    private static final Logger LOGGER = LoggerFactory.getLogger(RenderManager.class);

    public RenderManager(){
        glEnable(GL_DEPTH_TEST);
        /*glEnable(GL_STENCIL_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_CULL_FACE);*/
        ServiceLocator.registerService(RenderManager.class,this);
    }

    public void render(){

        if(((CameraManager) this.services.get(CameraManager.class)).checkCameraExisting()){
            Matrix4f view = ((CameraManager) this.services.get(CameraManager.class)).getViewMatrix();
            Matrix4f projection = ((CameraManager) this.services.get(CameraManager.class)).getProjectionMatrix();
            for(Entity entity : ((EntityManager) this.services.get(EntityManager.class)).getEntities()){
                renderEntity(entity,view,projection);
            }
        }else{
            LOGGER.error("\033[0;33m[RENDER] \033[0m No camera");
            ServiceLocator.getService(SystemManager.class).stop();
        }

    }

    private void renderEntity(Entity entity, Matrix4f viewMatrix, Matrix4f projectionMatrix) {
        if(entity.hasAllComponents(MeshComponent.class)) {
            if (entity.hasAllComponents(ShaderComponent.class,TransformComponent.class)) {
                entity.getComponent(ShaderComponent.class).getShader().useProgram();
                entity.getComponent(ShaderComponent.class).getShader().setUniform("view", viewMatrix);
                entity.getComponent(ShaderComponent.class).getShader().setUniform("projection", projectionMatrix);
                entity.getComponent(ShaderComponent.class).getShader().setUniform("model", entity.getComponent(TransformComponent.class).getModelMatrix());
                entity.getComponent(ShaderComponent.class).getShader().setUniform("normalMatrix", new Matrix3f(entity.getComponent(TransformComponent.class).getModelMatrix()).invert(new Matrix3f()).transpose());

                Material material = entity.getComponent(MeshComponent.class).getMaterial();
                entity.getComponent(ShaderComponent.class).getShader().setUniform("material.shininess",material.getShininess());

                int index = 0;
                for(Texture texture : material.getTextures().values()){
                    glActiveTexture(GL_TEXTURE0 + index);
                    glBindTexture(GL_TEXTURE_2D,texture.getTexture());
                    index++;
                }

                glBindVertexArray(entity.getComponent(MeshComponent.class).getVao());
                glDrawElements(GL_TRIANGLES, entity.getComponent(MeshComponent.class).getIndicesCount(), GL_UNSIGNED_INT, 0);
            } else {
                LOGGER.warn("\033[0;33m[Entity] \033[0m" + entity.getId() + " | Missing Component");
            }
        }
    }

}
