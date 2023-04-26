package com.example.tetris;

public class Pentagone implements Forme {
    private float X;
    private int typeT;
    private float[] pentagonCoords;
    private float[] colors;

    public Pentagone(float[] pos, int type, float size) {
        X = size;
        typeT = type;

        float[] position = {0.0f, 0.0f};
        position[0] = pos[0];
        position[1] = pos[1];

        pentagonCoords = new float[]{
                0.0f + position[0], X + position[1], 0.0f,
                0.951f*X + position[0], 0.309f*X + position[1], 0.0f,
                0.588f*X + position[0], -0.809f*X + position[1], 0.0f,
                -0.588f*X + position[0], -0.809f*X + position[1], 0.0f,
                -0.951f*X + position[0], 0.309f*X + position[1], 0.0f
        };

        colors = new float[]{
                1.0f, 0.0f, 0.0f, 1.0f,
                0.0f, 1.0f, 0.0f, 1.0f,
                0.0f, 0.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 0.0f, 1.0f,
                1.0f, 0.0f, 1.0f, 1.0f
        };
    }

    public float[] getPentagonCoords() {
        return pentagonCoords;
    }

    public float[] getColors() {
        return colors;
    }

    @Override
    public float[] getCoords() {
        return pentagonCoords;
    }

    @Override
    public float[] getCouleur() {
        return colors;
    }

    public short[] getIndices() {
        return new short[]{
                0, 1, 2, 0, 2, 3, 0, 3, 4, 0, 4, 1
        };
    }
}
