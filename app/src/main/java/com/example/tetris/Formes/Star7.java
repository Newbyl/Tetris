package com.example.tetris.Formes;

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
                x1, x2, x3, 1.0f,
                x1, x2, x3, 1.0f,
                x1, x2, x3, 1.0f,
                x1, x2, x3, 1.0f,
                x1, x2, x3, 1.0f,
                x1, x2, x3, 1.0f,
                x1, x2, x3, 1.0f,
                x1, x2, x3, 1.0f,
                x1, x2, x3, 1.0f,
                x1, x2, x3, 1.0f
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
                0,1,9,1,2,3,3,4,5,5,6,7,7,8,9,1,3,7,1,9,7,3,5,7
        };
    }



}