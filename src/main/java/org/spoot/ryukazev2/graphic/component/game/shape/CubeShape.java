package org.spoot.ryukazev2.graphic.component.game.shape;

public class CubeShape implements IShape{


    @Override
    public float[] getVertices() {
        return new float[]{
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
    }

    @Override
    public int[] getIndices() {
        return new int[]{
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
    }
}
