package ryukaze.objects.mesh;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class CylinderMesh extends Mesh {

    private int vao;
    private int vbo;
    private int indexCount;

    public CylinderMesh(int precision) {
        generateCylinder(precision);
    }

    private void generateCylinder(int precision) {
        // Ajout des centres des cercles supérieur et inférieur
        int vertexCount = (precision + 1) * 4 + 2;
        indexCount = precision * 12 + precision * 6; // Ajout des indices pour les faces supérieure et inférieure

        float[] verticesAndNormals = new float[vertexCount * 6];
        int[] indices = new int[indexCount];

        int pointer = 0;
        int indexPointer = 0;

        // Générer les sommets et les normales pour les côtés
        for (int i = 0; i <= precision; i++) {
            float angle = (float) (2 * Math.PI * i / precision);
            float cosAngle = (float) Math.cos(angle);
            float sinAngle = (float) Math.sin(angle);

            // Côté supérieur
            verticesAndNormals[pointer++] = 0.5f * cosAngle;
            verticesAndNormals[pointer++] = 1.0f / 2;
            verticesAndNormals[pointer++] = 0.5f * sinAngle;
            verticesAndNormals[pointer++] = cosAngle;
            verticesAndNormals[pointer++] = 0;
            verticesAndNormals[pointer++] = sinAngle;

            // Côté inférieur
            verticesAndNormals[pointer++] = 0.5f * cosAngle;
            verticesAndNormals[pointer++] = -1.0f / 2;
            verticesAndNormals[pointer++] = 0.5f * sinAngle;
            verticesAndNormals[pointer++] = cosAngle;
            verticesAndNormals[pointer++] = 0;
            verticesAndNormals[pointer++] = sinAngle;
        }

        // Générer les sommets pour les centres des cercles supérieur et inférieur
        // Centre supérieur
        verticesAndNormals[pointer++] = 0;
        verticesAndNormals[pointer++] = 5.0f / 2;
        verticesAndNormals[pointer++] = 0;
        verticesAndNormals[pointer++] = 0; // Normale x
        verticesAndNormals[pointer++] = -1; // Normale y
        verticesAndNormals[pointer++] = 0; // Normale z

        // Centre inférieur
        verticesAndNormals[pointer++] = 0;
        verticesAndNormals[pointer++] = -1.0f / 2;
        verticesAndNormals[pointer++] = 0;
        verticesAndNormals[pointer++] = 0; // Normale x
        verticesAndNormals[pointer++] = 1; // Normale y
        verticesAndNormals[pointer++] = 0; // Normale z

        // Générer les indices pour les côtés
        for (int i = 0; i < precision; i++) {
            int top1 = i * 2;
            int bottom1 = top1 + 1;
            int top2 = (i + 1) * 2;
            int bottom2 = top2 + 1;

            // Triangle 1
            indices[indexPointer++] = top1;
            indices[indexPointer++] = bottom1;
            indices[indexPointer++] = top2;

            // Triangle 2
            indices[indexPointer++] = top2;
            indices[indexPointer++] = bottom1;
            indices[indexPointer++] = bottom2;
        }

        // Indices pour la face supérieure et inférieure
        int topCenterIndex = vertexCount - 2;
        int bottomCenterIndex = vertexCount - 1;
        for (int i = 0; i < precision ; i++) {

            int bottomIndex = i * 2;
            int nextBottomIndex = bottomIndex + 2;

            int topIndex = bottomIndex + 1;
            int nextTopIndex = topIndex + 2;

            // Triangle supérieur
            indices[indexPointer++] = topCenterIndex;
            indices[indexPointer++] = nextTopIndex;
            indices[indexPointer++] = topIndex;

            // Triangle inférieur

            indices[indexPointer++] = bottomCenterIndex;
            indices[indexPointer++] = bottomIndex;
            indices[indexPointer++] = nextBottomIndex;
        }

        // Setup for rendering
        setupMesh(verticesAndNormals, indices);
    }

    private void setupMesh(float[] vertices, int[] indices) {
        vao = glGenVertexArrays();
        vbo = glGenBuffers();
        int ebo = glGenBuffers();

        glBindVertexArray(vao);

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        // Position attribute
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 24, 0);
        glEnableVertexAttribArray(0);
        // Normal attribute
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 24, 12);
        glEnableVertexAttribArray(1);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    @Override
    public void render() {
        glBindVertexArray(vao);
        glDrawElements(GL_TRIANGLES, indexCount, GL_UNSIGNED_INT, 0);
    }
}
