package ryukazev2.objects.mesh;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class PlaneMesh extends Mesh{

    private static final int vao;
    private static final int vbo;

    private static float[] vertices = {
            // Premier Triangle
            -0.5f, 0.0f,  0.5f, 0.0f, 1.0f, 0.0f,  // Sommet 1
            0.5f, 0.0f,  0.5f, 0.0f, 1.0f, 0.0f, // Sommet 2
            -0.5f, 0.0f, -0.5f, 0.0f, 1.0f, 0.0f, // Sommet 3

            // Deuxième Triangle
            0.5f, 0.0f,  0.5f, 0.0f, 1.0f, 0.0f, // Sommet 1
            0.5f, 0.0f, -0.5f, 0.0f, 1.0f, 0.0f, // Sommet 2
            -0.5f, 0.0f, -0.5f, 0.0f, 1.0f, 0.0f,  // Sommet 3
    };

    static {
        vao = glGenVertexArrays();
        vbo = glGenBuffers();
        glBindVertexArray(vao);

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 24, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, 3, GL_FLOAT, false, 24, 12);
        glEnableVertexAttribArray(1);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    @Override
    public void render() {
        glBindVertexArray(vao);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glDrawArrays(GL_TRIANGLES,0,6);
    }

}
