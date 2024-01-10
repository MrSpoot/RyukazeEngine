package ryukazev2.manager;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ryukazev2.component.game.*;
import ryukazev2.core.Entity;
import ryukazev2.core.Shader;
import ryukazev2.graphics.Material;
import ryukazev2.graphics.Texture;
import ryukazev2.utils.ServiceLocator;

import java.nio.FloatBuffer;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL33.glVertexAttribDivisor;

public class RenderManager extends Manager {

    private static final Logger LOGGER = LoggerFactory.getLogger(RenderManager.class);

    public RenderManager(){
        resetOpenGLConfig();
        ServiceLocator.registerService(RenderManager.class,this);
    }

    private void resetOpenGLConfig(){
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_STENCIL_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_CULL_FACE);
        glEnable(GL_MULTISAMPLE);
    }

    public void render(){
        resetOpenGLConfig();
        if(((CameraManager) this.services.get(CameraManager.class)).checkCameraExisting()){

            CameraManager camera = ((CameraManager) this.services.get(CameraManager.class));

            Matrix4f view = camera.getViewMatrix();
            Matrix4f projection = camera.getProjectionMatrix();

            List<Entity> entities = ((EntityManager) this.services.get(EntityManager.class)).getEntities();

            /*Collections.sort(entities, (e1, e2) -> {

                TransformComponent tc1 = e1.getComponent(TransformComponent.class);
                TransformComponent tc2 = e2.getComponent(TransformComponent.class);

                float distance1 = camera.getActiveCamera().getComponent(TransformComponent.class).getPosition().distance(tc1.getPosition());
                float distance2 = camera.getActiveCamera().getComponent(TransformComponent.class).getPosition().distance(tc2.getPosition());

                return Float.compare(distance1,distance2);
            });*/

            for(Entity entity : entities){
                renderEntity(entity, view,projection);
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

                for(Entity light : ((EntityManager) this.services.get(EntityManager.class)).getEntityByAnyComponent(DirectionalLightComponent.class, SpotLightComponent.class, PointLightComponent.class)){
                    renderLight(light, entity.getComponent(ShaderComponent.class).getShader());
                }

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

                glBindVertexArray(entity.getComponent(MeshComponent.class).getData().getVao());
                glDrawElements(GL_TRIANGLES, entity.getComponent(MeshComponent.class).getIndicesCount(), GL_UNSIGNED_INT, 0);
            } else {
                LOGGER.warn("\033[0;33m[Entity] \033[0m" + entity.getId() + " | Missing Component");
            }
        }
    }

    private void renderLight(Entity entity, Shader shader){

        if(entity.hasAllComponents(DirectionalLightComponent.class, TransformComponent.class)){
            DirectionalLightComponent light = entity.getComponent(DirectionalLightComponent.class);

            shader.setUniform("directionalLight.intensity", light.getIntensity());
            shader.setUniform("directionalLight.ambient", light.getAmbient());
            shader.setUniform("directionalLight.diffuse", light.getDiffuse());
            shader.setUniform("directionalLight.specular", light.getSpecular());
            shader.setUniform("directionalLight.direction",light.getDirection());
        }

        if(entity.hasAllComponents(PointLightComponent.class, TransformComponent.class)){
            PointLightComponent light = entity.getComponent(PointLightComponent.class);
            TransformComponent transform = entity.getComponent(TransformComponent.class);

            shader.setUniform("pointLight.intensity", light.getIntensity());
            shader.setUniform("pointLight.ambient", light.getAmbient());
            shader.setUniform("pointLight.diffuse", light.getDiffuse());
            shader.setUniform("pointLight.specular", light.getSpecular());
            shader.setUniform("pointLight.position",transform.getPosition());
            shader.setUniform("pointLight.linear",light.getLinear());
            shader.setUniform("pointLight.quadratic",light.getQuadratic());
        }

        if(entity.hasAllComponents(SpotLightComponent.class, TransformComponent.class)){
            SpotLightComponent light = entity.getComponent(SpotLightComponent.class);
            TransformComponent transform = entity.getComponent(TransformComponent.class);

            shader.setUniform("spotLight.ambient", light.getAmbient());
            shader.setUniform("spotLight.diffuse", light.getDiffuse());
            shader.setUniform("spotLight.specular", light.getSpecular());
            shader.setUniform("spotLight.intensity", light.getIntensity());

            shader.setUniform("spotLight.cutOff",light.getCutOff());
            shader.setUniform("spotLight.outerCutOff",light.getOutCutOff());
            shader.setUniform("spotLight.direction",light.getDirection());
            shader.setUniform("spotLight.position",transform.getPosition());

            shader.setUniform("spotLight.linear",light.getLinear());
            shader.setUniform("spotLight.quadratic",light.getQuadratic());
        }
    }

    private void prepareInstanceData(List<Entity> entities){



        FloatBuffer instanceDataBuffer = BufferUtils.createFloatBuffer(entities.size());
        instanceDataBuffer.put(instanceData).flip();

        glBindBuffer(GL_ARRAY_BUFFER, instanceVbo);
        glBufferData(GL_ARRAY_BUFFER, instanceDataBuffer, GL_STATIC_DRAW);

        int stride = 16 * Float.BYTES;
        int offset = 0;

        for (int i = 0; i < 4; i++) {
            glEnableVertexAttribArray(3 + i);
            glVertexAttribPointer(3 + i, 4, GL_FLOAT, false, stride, offset);
            glVertexAttribDivisor(3 + i, 1);
            offset += 4 * 4;
        }

        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

}
