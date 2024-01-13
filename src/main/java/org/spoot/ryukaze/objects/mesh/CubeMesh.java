package org.spoot.ryukaze.objects.mesh;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class CubeMesh extends Mesh {

    private static final int vao;
    private static final int vbo;

    private static final float[] vertices = {
            // Face avant
            // x,    y,    z,   nx,  ny,  nz,  u,  v
            -0.5f, -0.5f,  0.5f,  0f,  0f,  1f, 0f, 0f, // bas gauche
            0.5f, -0.5f,  0.5f,  0f,  0f,  1f, 1f, 0f, // bas droite
            0.5f,  0.5f,  0.5f,  0f,  0f,  1f, 1f, 1f, // haut droite
            -0.5f,  0.5f,  0.5f,  0f,  0f,  1f, 0f, 1f, // haut gauche

            // Face arrière
            -0.5f, -0.5f, -0.5f,  0f,  0f, -1f, 0f, 0f,
            0.5f, -0.5f, -0.5f,  0f,  0f, -1f, 1f, 0f,
            0.5f,  0.5f, -0.5f,  0f,  0f, -1f, 1f, 1f,
            -0.5f,  0.5f, -0.5f,  0f,  0f, -1f, 0f, 1f,

            // Face gauche
            -0.5f, -0.5f, -0.5f, -1f,  0f,  0f, 0f, 0f,
            -0.5f, -0.5f,  0.5f, -1f,  0f,  0f, 1f, 0f,
            -0.5f,  0.5f,  0.5f, -1f,  0f,  0f, 1f, 1f,
            -0.5f,  0.5f, -0.5f, -1f,  0f,  0f, 0f, 1f,

            // Face droite
            0.5f, -0.5f, -0.5f,  1f,  0f,  0f, 0f, 0f,
            0.5f, -0.5f,  0.5f,  1f,  0f,  0f, 1f, 0f,
            0.5f,  0.5f,  0.5f,  1f,  0f,  0f, 1f, 1f,
            0.5f,  0.5f, -0.5f,  1f,  0f,  0f, 0f, 1f,

            // Face supérieure
            -0.5f,  0.5f, -0.5f,  0f,  1f,  0f, 0f, 0f,
            -0.5f,  0.5f,  0.5f,  0f,  1f,  0f, 1f, 0f,
            0.5f,  0.5f,  0.5f,  0f,  1f,  0f, 1f, 1f,
            0.5f,  0.5f, -0.5f,  0f,  1f,  0f, 0f, 1f,

            // Face inférieure
            -0.5f, -0.5f, -0.5f,  0f, -1f,  0f, 0f, 0f,
            -0.5f, -0.5f,  0.5f,  0f, -1f,  0f, 1f, 0f,
            0.5f, -0.5f,  0.5f,  0f, -1f,  0f, 1f, 1f,
            0.5f, -0.5f, -0.5f,  0f, -1f,  0f, 0f, 1f
    };

    private static int[] indices = {
            // Face avant
            0, 2,1,  2, 0,3,
            // Face arrière
            4, 5, 6, 6, 7, 4,
            // Face gauche
            8, 10, 9, 10,8, 11,
            // Face droite
            12, 13, 14, 14, 15, 12,
            // Face supérieure
            16, 18, 17 ,18, 16, 19,
            // Face inférieure
            20, 21, 22, 22, 23, 20
    };

    static {
        vao = glGenVertexArrays();
        vbo = glGenBuffers();
        int ebo = glGenBuffers();
        glBindVertexArray(vao);

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);


        glVertexAttribPointer(0, 3, GL_FLOAT, false, 32, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, 3, GL_FLOAT, false, 32, 12);
        glEnableVertexAttribArray(1);

        glVertexAttribPointer(2, 2, GL_FLOAT, false, 32, 24);
        glEnableVertexAttribArray(2);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    @Override
    public void render() {
        glBindVertexArray(vao);
        glDrawElements(GL_TRIANGLES, 36, GL_UNSIGNED_INT, 0);
    }




}
