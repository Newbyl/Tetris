package com.example.tetris;

public class T implements Forme{
    private float X;
    private int typeT;
    private float[] tCoords;
    private float[] colors;

    public T(float[] pos, int type, float size) {
        X = size;
        typeT = type;

        float[] position = {0.0f, 0.0f};
        position[0] = pos[0];
        position[1] = pos[1];

        tCoords = new float[]{
                -X + position[0], 0.0f + position[1], 0.0f,
                X + position[0], 0.0f + position[1], 0.0f,
                X + position[0], 0.5f*X + position[1], 0.0f,
                0.5f*X + position[0], 0.5f*X + position[1], 0.0f,
                0.5f*X + position[0], X + position[1], 0.0f,
                -0.5f*X + position[0], X + position[1], 0.0f,
                -0.5f*X + position[0], 0.5f*X + position[1], 0.0f,
                -X + position[0], 0.5f*X + position[1], 0.0f};

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

    public float[] getTCoords() {
        return tCoords;
    }

    public float[] getColors() {
        return colors;
    }

    @Override
    public float[] getCoords() {
        return tCoords;
    }

    @Override
    public float[] getCouleur() {
        return colors;
    }

    public short[] getIndices() {
        return new short[]{
                0, 1, 3,
                3, 1, 4,
                4, 1, 7,
                4, 7, 6,
                6, 7, 5,
                3, 4, 6,
        };
    }



}
