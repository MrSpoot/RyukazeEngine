package ryukazev2.manager;

import org.joml.Matrix4f;
import ryukazev2.core.Entity;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;

public class RenderManager {

    public RenderManager(){
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_STENCIL_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_CULL_FACE);
    }

    public void render(){



    }

    private void renderEntity(Entity entity, Matrix4f viewMatrix, Matrix4f projectionMatrix) {
        // Logique spécifique pour rendre une entité
        // Utiliser les matrices de vue et de projection pour le rendu 3D
    }

}
