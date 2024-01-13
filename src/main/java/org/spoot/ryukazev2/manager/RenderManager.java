package org.spoot.ryukazev2.manager;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spoot.ryukazev2.component.game.*;
import org.spoot.ryukazev2.core.Entity;
import org.spoot.ryukazev2.core.Shader;
import org.spoot.ryukazev2.graphics.Material;
import org.spoot.ryukazev2.graphics.Texture;
import org.spoot.ryukazev2.utils.ServiceLocator;

import java.nio.FloatBuffer;
import java.util.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL31.glDrawElementsInstanced;
import static org.lwjgl.opengl.GL33.glVertexAttribDivisor;

public class RenderManager extends Manager {

    private static final Logger LOGGER = LoggerFactory.getLogger(RenderManager.class);

    private boolean test = false;

    public RenderManager() {
        resetOpenGLConfig();
        ServiceLocator.registerService(RenderManager.class, this);
    }

    private void resetOpenGLConfig() {
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_STENCIL_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_CULL_FACE);
        glEnable(GL_MULTISAMPLE);
    }

    public void render() {
        resetOpenGLConfig();
        if (((CameraManager) this.services.get(CameraManager.class)).checkCameraExisting()) {
            Map<Integer, List<Entity>> entitiesByVao = new HashMap<>();

            CameraManager camera = ((CameraManager) this.services.get(CameraManager.class));

            Matrix4f view = camera.getViewMatrix();
            Matrix4f projection = camera.getProjectionMatrix();

            List<Entity> entities = ((EntityManager) this.services.get(EntityManager.class)).getEntityByComponent(TransformComponent.class, MeshComponent.class, ShaderComponent.class);

            entities.sort(Comparator.comparingInt(entity -> entity.getComponent(MeshComponent.class).getData().getVao()
            ));

            List<Entity> lights = ((EntityManager) this.services.get(EntityManager.class)).getEntityByAnyComponent(DirectionalLightComponent.class, SpotLightComponent.class, PointLightComponent.class);

            for (Entity entity : entities) {
                int vaoId = entity.getComponent(MeshComponent.class).getData().getVao();
                entitiesByVao.computeIfAbsent(vaoId, k -> new ArrayList<>()).add(entity);
            }

            for (Map.Entry<Integer, List<Entity>> entry : entitiesByVao.entrySet()) {
                glBindVertexArray(entry.getKey());
                prepareInstanceData(entry.getValue());

                entry.getValue().get(0).getComponent(ShaderComponent.class).getShader().useProgram();
                for (Entity light : lights) {
                    renderLight(light, entry.getValue().get(0).getComponent(ShaderComponent.class).getShader());
                }
                renderEntity(entry.getValue().get(0), view, projection, entry.getValue().size());
            }
        } else {
            LOGGER.error("\033[0;33m[RENDER] \033[0m No camera");
            ServiceLocator.getService(SystemManager.class).stop();
        }
    }

    private void renderEntity(Entity entity, Matrix4f viewMatrix, Matrix4f projectionMatrix, int amount) {

        entity.getComponent(ShaderComponent.class).getShader().setUniform("view", viewMatrix);
        entity.getComponent(ShaderComponent.class).getShader().setUniform("projection", projectionMatrix);
        //entity.getComponent(ShaderComponent.class).getShader().setUniform("model", entity.getComponent(TransformComponent.class).getModelMatrix());
        //entity.getComponent(ShaderComponent.class).getShader().setUniform("normalMatrix", new Matrix3f(entity.getComponent(TransformComponent.class).getModelMatrix()).invert(new Matrix3f()).transpose());

        Material material = entity.getComponent(MeshComponent.class).getMaterial();
        entity.getComponent(ShaderComponent.class).getShader().setUniform("material.shininess", material.getShininess());

        int index = 0;
        for (Texture texture : material.getTextures().values()) {
            glActiveTexture(GL_TEXTURE0 + index);
            glBindTexture(GL_TEXTURE_2D, texture.getTexture());
            index++;
        }
        glDrawElementsInstanced(GL_TRIANGLES, entity.getComponent(MeshComponent.class).getIndicesCount(), GL_UNSIGNED_INT, 0,amount);
    }

    private void renderLight(Entity entity, Shader shader) {

        if (entity.hasAllComponents(DirectionalLightComponent.class, TransformComponent.class)) {
            DirectionalLightComponent light = entity.getComponent(DirectionalLightComponent.class);

            shader.setUniform("directionalLight.intensity", light.getIntensity());
            shader.setUniform("directionalLight.ambient", light.getAmbient());
            shader.setUniform("directionalLight.diffuse", light.getDiffuse());
            shader.setUniform("directionalLight.specular", light.getSpecular());
            shader.setUniform("directionalLight.direction", light.getDirection());
        }

        if (entity.hasAllComponents(PointLightComponent.class, TransformComponent.class)) {
            PointLightComponent light = entity.getComponent(PointLightComponent.class);
            TransformComponent transform = entity.getComponent(TransformComponent.class);

            shader.setUniform("pointLight.intensity", light.getIntensity());
            shader.setUniform("pointLight.ambient", light.getAmbient());
            shader.setUniform("pointLight.diffuse", light.getDiffuse());
            shader.setUniform("pointLight.specular", light.getSpecular());
            shader.setUniform("pointLight.position", transform.getPosition());
            shader.setUniform("pointLight.linear", light.getLinear());
            shader.setUniform("pointLight.quadratic", light.getQuadratic());
        }

        if (entity.hasAllComponents(SpotLightComponent.class, TransformComponent.class)) {
            SpotLightComponent light = entity.getComponent(SpotLightComponent.class);
            TransformComponent transform = entity.getComponent(TransformComponent.class);

            shader.setUniform("spotLight.ambient", light.getAmbient());
            shader.setUniform("spotLight.diffuse", light.getDiffuse());
            shader.setUniform("spotLight.specular", light.getSpecular());
            shader.setUniform("spotLight.intensity", light.getIntensity());

            shader.setUniform("spotLight.cutOff", light.getCutOff());
            shader.setUniform("spotLight.outerCutOff", light.getOutCutOff());
            shader.setUniform("spotLight.direction", light.getDirection());
            shader.setUniform("spotLight.position", transform.getPosition());

            shader.setUniform("spotLight.linear", light.getLinear());
            shader.setUniform("spotLight.quadratic", light.getQuadratic());
        }
    }

    private void prepareInstanceData(List<Entity> entities) {
        int matrix4fSize = 16 * Float.BYTES; // 16 floats pour une Matrix4f
        int matrix3fSize = 9 * Float.BYTES; // 9 floats pour une Matrix3f
        int totalSizePerInstance = matrix4fSize + matrix3fSize;

        int totalBufferSize = entities.size() * totalSizePerInstance;
        FloatBuffer instanceDataBuffer = BufferUtils.createFloatBuffer(totalBufferSize);

        float[] data = new float[totalBufferSize/4];

        int index = 0;

        for (Entity entity : entities) {
            Matrix4f modelMatrix = entity.getComponent(TransformComponent.class).getModelMatrix();
            Matrix3f normalMatrix = new Matrix3f(entity.getComponent(TransformComponent.class).getModelMatrix()).invert(new Matrix3f()).transpose();

            for(int x = 0; x < 4; x++){
                for(int y = 0; y < 4; y++){
                    data[index] = modelMatrix.get(x,y);
                    index++;
                }
            }

            for(int x = 0; x < 3; x++){
                for(int y = 0; y < 3; y++){
                    data[index] = normalMatrix.get(x,y);
                    index++;
                }
            }

            /*modelMatrix.get(data);
            normalMatrix.get(data);

            modelMatrix.get(instanceDataBuffer);
            normalMatrix.get(instanceDataBuffer);*/
        }

        instanceDataBuffer.flip();

        glBindBuffer(GL_ARRAY_BUFFER, entities.get(0).getComponent(MeshComponent.class).getData().getVbo());
        glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);

        int attribIndex = 3;
        int offset = 0;

        for (int i = 0; i < 4; i++) {
            glVertexAttribPointer(attribIndex, 4, GL_FLOAT, false, totalSizePerInstance, offset);
            glVertexAttribDivisor(attribIndex, 1);
            glEnableVertexAttribArray(attribIndex);
            attribIndex++;
            offset += 4 * Float.BYTES;
        }

        for (int i = 0; i < 3; i++) {
            glVertexAttribPointer(attribIndex, 3, GL_FLOAT, false, totalSizePerInstance, offset);
            glVertexAttribDivisor(attribIndex, 1);
            glEnableVertexAttribArray(attribIndex);
            attribIndex++;
            offset += 3 * Float.BYTES;
        }

        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

}
