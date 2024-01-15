package org.spoot.ryukazev2.component.game.shape;

public class PlaneShape implements IShape {
    @Override
    public float[] getVertices() {
        return new float[]{
                -0.5f, 0.0f,  0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,
                -0.5f, 0.0f, -0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f,
                0.5f, 0.0f,  0.5f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f,
                0.5f, 0.0f, -0.5f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f
        };
    }

    @Override
    public int[] getIndices() {
        return new int[]{
                0, 1, 2,
                2, 1, 3
        };
    }
}
