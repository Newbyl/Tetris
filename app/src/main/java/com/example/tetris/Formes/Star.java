package com.example.tetris.Formes;

public class Star implements Forme {
    private float X;
    private int typeT;
    private float[] starCoords;
    private float[] colors;

    public Star(float[] pos, int type, float size) {
        X = size;
        typeT = type;

        float[] position = {0.0f, 0.0f};
        position[0] = pos[0];
        position[1] = pos[1];

        starCoords = new float[]{
                0.0f + position[0], X + position[1], 0.0f,
                -0.25f*X + position[0], 0.25f*X + position[1], 0.0f,
                -X + position[0], 0.0f + position[1], 0.0f,
                -0.25f*X + position[0], -0.25f*X + position[1], 0.0f,
                0.0f + position[0], -X + position[1], 0.0f,
                0.25f*X + position[0], -0.25f*X + position[1], 0.0f,
                X + position[0], 0.0f + position[1], 0.0f,
                0.25f*X + position[0], 0.25f*X + position[1], 0.0f};

        colors = new float[]{
                1.0f, 0.0f, 0.0f, 1.0f,
                0.0f, 1.0f, 0.0f, 1.0f,
                0.0f, 0.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 0.0f, 1.0f,
                1.0f, 0.0f, 1.0f, 1.0f,
                0.0f, 1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f, 1.0f,
                0.0f, 0.0f, 0.0f, 1.0f
        };
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
                0,1,7,1,2,3,3,4,5,5,6,7,1,7,3,3,5,7
        };
    }
}
