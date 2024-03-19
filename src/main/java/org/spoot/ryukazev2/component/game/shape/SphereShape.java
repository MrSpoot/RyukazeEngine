package org.spoot.ryukazev2.component.game.shape;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class SphereShape implements IShape{

    private int indexCount;
    private float[] vertices;
    private int[] indices;

    public SphereShape(int precision) {
        generateSphere(precision, precision);
    }

    private void generateSphere(int stacks, int slices) {
        float stackAngleStep = (float) Math.PI / stacks;
        float sliceAngleStep = 2.0f * (float) Math.PI / slices;
        int vertexCount = (stacks + 1) * (slices + 1);
        indexCount = stacks * slices * 6;
        float[] verticesAndNormals = new float[vertexCount * 8];
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

                verticesAndNormals[vertexPointer++] = xPos;
                verticesAndNormals[vertexPointer++] = yPos;
                verticesAndNormals[vertexPointer++] = zPos;

                verticesAndNormals[vertexPointer++] = 1.0f;
                verticesAndNormals[vertexPointer++] = 1.0f;
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

        this.vertices = verticesAndNormals;
        this.indices = indices;
    }

    @Override
    public float[] getVertices() {
        return this.vertices;
    }

    @Override
    public int[] getIndices() {
        return this.indices;
    }
}
