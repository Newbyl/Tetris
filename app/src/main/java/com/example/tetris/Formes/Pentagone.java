package com.example.tetris.Formes;

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

        float x1;
        float x2;
        float x3;

        switch (typeT){
            case 0:
            case 10:
                x1 = .0f;
                x2 = 1.0f;
                x3 = 1.0f;
                break;
            case 1:
            case 11:
                x1 = 1.0f;
                x2 = 1.0f;
                x3 = .0f;
                break;

            case 2:
            case 12:
                x1 = .67f;
                x2 = 0.03f;
                x3 = 1.0f;
                break;
            case 3:
            case 13:
                x1 = 1.0f;
                x2 = 0.67f;
                x3 = 0.06f;
                break;

            case 4:
            case 14:
                x1 = .0f;
                x2 = .0f;
                x3 = 1.0f;
                break;

            case 5:
            case 15:
                x1 = 1.0f;
                x2 = .0f;
                x3 = .0f;
                break;

            case 6:
            case 16:
                x1 = .0f;
                x2 = 1.0f;
                x3 = .0f;
                break;

            default:
                x1 = .0f;
                x2 = .0f;
                x3 = .0f;
                break;

        }

        pentagonCoords = new float[]{
                0.0f + position[0], X + position[1], 0.0f,
                0.951f*X + position[0], 0.309f*X + position[1], 0.0f,
                0.588f*X + position[0], -0.809f*X + position[1], 0.0f,
                -0.588f*X + position[0], -0.809f*X + position[1], 0.0f,
                -0.951f*X + position[0], 0.309f*X + position[1], 0.0f
        };

        colors = new float[]{
                x1, x2, x3, 1.0f,
                x1, x2, x3, 1.0f,
                x1, x2, x3, 1.0f,
                x1, x2, x3, 1.0f,
                x1, x2, x3, 1.0f
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
