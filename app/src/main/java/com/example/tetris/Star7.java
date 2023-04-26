package com.example.tetris;

public class Star7 implements Forme {
    private float X;
    private int typeT;
    private float[] starCoords;
    private float[] colors;

    public Star7(float[] pos, int type, float size) {
        X = size*2;
        typeT = type;

        float[] position = {0.0f, 0.0f};
        position[0] = pos[0];
        position[1] = pos[1];

        starCoords = new float[]{
                0.0f + position[0], 0.5f * X + position[1], 0.0f,
                0.09f * X + position[0], 0.18f * X + position[1], 0.0f,
                0.47f * X + position[0], 0.18f * X + position[1], 0.0f,
                0.18f * X + position[0], -0.12f * X + position[1], 0.0f,
                0.28f * X + position[0], -0.5f * X + position[1], 0.0f,
                0.0f + position[0], -0.28f * X + position[1], 0.0f,
                -0.28f * X + position[0], -0.5f * X + position[1], 0.0f,
                -0.18f * X + position[0], -0.12f * X + position[1], 0.0f,
                -0.47f * X + position[0], 0.18f * X + position[1], 0.0f,
                -0.09f * X + position[0], 0.18f * X + position[1], 0.0f};

        colors = new float[]{
                1.0f, 0.0f, 0.0f, 1.0f,
                1.0f, 0.0f, 0.0f, 1.0f,
                1.0f, 0.0f, 0.0f, 1.0f,
                1.0f, 0.0f, 0.0f, 1.0f,
                1.0f, 0.0f, 0.0f, 1.0f,
                1.0f, 0.0f, 0.0f, 1.0f,
                1.0f, 0.0f, 0.0f, 1.0f,
                1.0f, 0.0f, 0.0f, 1.0f,
                1.0f, 0.0f, 0.0f, 1.0f,
                1.0f, 0.0f, 0.0f, 1.0f
        };

    }

    public float[] getStarCoords() {
        return starCoords;
    }

    public float[] getColors() {
        return colors;
    }

    @Override
    public float[] getCoords() {
        return starCoords;
    }

    @Override
    public float[] getCouleur() {
        return colors;
    }

    public short[] getIndices() {
        return new short[]{
                0,1,9,1,2,3,3,4,5,5,6,7,7,8,9,1,3,7,1,9,7,3,5,7
        };
    }



}