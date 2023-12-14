package ryukazev2.objects.mesh;

import ryukazev2.core.Engine;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class SphereMesh extends Mesh {

    private int vao;
    private int vbo;
    private int ibo;
    private int indexCount;

    public SphereMesh(int precision) {
        generateSphere(precision, precision);
    }

    private void generateSphere(int stacks, int slices) {
        float stackAngleStep = (float) Math.PI / stacks;
        float sliceAngleStep = 2.0f * (float) Math.PI / slices;
        int vertexCount = (stacks + 1) * (slices + 1);
        indexCount = stacks * slices * 6;
        float[] verticesAndNormals = new float[vertexCount * 6];
        int[] indices = new int[indexCount];

        int vertexPointer = 0;
        for(int stackCount = 0; stackCount <= stacks; ++stackCount) {
            float phi = stackCount * stackAngleStep;
            for(int sliceCount = 0; sliceCount <= slices; ++sliceCount) {
                float theta = sliceCount * sliceAngleStep;
                float xPos = (float) (Math.sin(phi) * Math.cos(theta));
                float yPos = (float) Math.cos(phi);
                float zPos = (float) (Math.sin(phi) * Math.sin(theta));
                verticesAndNormals[vertexPointer++] = xPos / 2;
                verticesAndNormals[vertexPointer++] = yPos / 2;
                verticesAndNormals[vertexPointer++] = zPos / 2;

                verticesAndNormals[vertexPointer++] = xPos; // Multiplié par 2 pour normaliser
                verticesAndNormals[vertexPointer++] = yPos;
                verticesAndNormals[vertexPointer++] = zPos;
            }
        }

        int indexPointer = 0;
        for(int stackCount = 0; stackCount < stacks; ++stackCount) {
            for(int sliceCount = 0; sliceCount < slices; ++sliceCount) {
                int first = (stackCount * (slices + 1)) + sliceCount;
                int second = first + slices + 1;
                indices[indexPointer++] = first;
                indices[indexPointer++] = second;
                indices[indexPointer++] = first + 1;

                indices[indexPointer++] = second;
                indices[indexPointer++] = second + 1;
                indices[indexPointer++] = first + 1;
            }
        }

        vao = glGenVertexArrays();
        vbo = glGenBuffers();
        glBindVertexArray(vao);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, verticesAndNormals, GL_STATIC_DRAW);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 24, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, 3, GL_FLOAT, false, 24, 12);
        glEnableVertexAttribArray(1);

        ibo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    @Override
    public void render() {
        glBindVertexArray(vao);
        glBindBuffer(GL_ARRAY_BUFFER,vbo);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glDrawElements(GL_TRIANGLES, indexCount, GL_UNSIGNED_INT, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }
}
